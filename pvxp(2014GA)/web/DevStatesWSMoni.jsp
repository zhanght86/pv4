<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<html>
<head>
<title>WEB SOCKET DEV MONI</title>
<style type="text/css">
#connect-container {
	width: 100%;
}

#connect-container div {
	padding: 5px;
	width: 100%;
}

#console-container {
	float: left;
	margin-left: 15px;
	width: 100%;
}

#console {
	border: 1px solid #CCCCCC;
	border-right-color: #999999;
	border-bottom-color: #999999;
	overflow-y: scroll;
	padding: 5px;
	height: 600px;
	width: 100%;
}

.green {
	color: green;
}

.red {
	color: red;
}

#console p {
	padding: 0;
	margin: 0;
}

#console p:hover {
	color: green;
	background-color: #cccaaa;
}
</style>

<script type="text/javascript" src="../js/jquery-2.1.1.min.js"></script>
<link rel="stylesheet" href="style/pvxp.css" type="text/css"></link>
<script type="text/javascript">
	var ws = null;
	var jsonMsg="";
	var timeID;

	function connect() {
		var target = 'ws://' + window.location.host + "/pvxp/websocket/devStatesMsg";

		if ('WebSocket' in window) {
			ws = new WebSocket(target);
		} else if ('MozWebSocket' in window) {
			ws = new MozWebSocket(target);
		} else {
			tiShi('本浏览器不支持WebSocket，请使用IE10+浏览器，WebSocket is not supported by this browser.',0);
			return;
		}

		ws.onopen = function() {
			tiShi('提示: 交易监控已经成功连接。', 0);
		};

		ws.onmessage = function(event) {
		if(event==null){
			alert("==="+event);
		}
			jsonMsg=event.data;
			var message = JSON.parse(event.data);
			change(message);
		};

		ws.onclose = function() {
			tiShi('提示: 交易监控已经关闭。onclose()', 1);
		};

		ws.onerror = function(e) {
			tiShi('提示: 交易监控发生错误', 1);
			displayErrorMsg(e);
		};
	}

	function disconnect() {
		if (ws != null) {
			ws.close();
			ws = null;
		}

		change('提示: 设备状态监控已经关闭。disconnect()');
	}

	var i = 0;
	//提示用户
	function tiShi(message, clazz) {

		//$("#root").parents().css({"color":"red","border":"2px solid red"});
		var root = document.getElementById('root');
		var p = document.createElement('p');
		p.style.wordWrap = 'break-word';

		if (clazz == 1)
			p.setAttribute("class", "red");
		else
			p.setAttribute("class", "green");

		p.appendChild(document.createTextNode(message));
		root.appendChild(p);
		root.scrollTop = root.scrollHeight;
	}

	//设定窗口
	function change(message) {
		
		var console = $("#" + message["DevNo"]);
		
		console.children("img").attr("style","cursor:hand");
		
		switch (parseInt(message["DevState"])) {
		case 0:
			console.children("img").attr("src","images/BlackBerry/bb_normal.png");
			break;
		case 1:
			console.children("img").attr("src", "images/BlackBerry/bb_free.png");
			break;
		case 2:
			console.children("img")
					.attr("src", "images/BlackBerry/bb_busy.png");
			break;
		case 3:
			console.children("img")
					.attr("src", "images/BlackBerry/bb_warn.png");
			break;
		case 4:
			console.children("img").attr("src",
					"images/BlackBerry/bb_error.png");
			break;
		default:
			console.children("img").attr("src",
					"images/BlackBerry/bb_off_line.png");
		}
		
		timeID=console.children("img").attr("timeId");
		
		if(timeID!=null){
			clearTimeout(timeID);
		}
		

		timeID=setTimeout(function(){console.children("img").attr("src","images/BlackBerry/bb_off_line.png");}, 30000);
		
		console.children("img").attr("timeId",timeID);
		console.children("img").attr("jsonMsg",jsonMsg);
		var tmp = message["DevNo"]+":";
		
		for (x in message) {
			tmp += x + "=" + message[x] + "|";
		}

		console.children("img").click(function() {
			Show_devinfo(console.children("img").attr("jsonMsg"));
		});
	}

	/**
	 * 用于显示详细信息
	 **/
	function Show_devinfo(args) {

		/*var xpos = event.screenX;
		var ypos = event.screenY+10;
		if ( (xpos+750) > 1024 ) xpos=1024-750-10;
		if ( (ypos+180) >  768 ) ypos=ypos-180-50;*/
		var webHeight = 600;
		var webWidth = 800
		var height = window.screen.height;
		var width = window.screen.width;
		var left = 0;
		var top = 0;
		if (width > webWidth) {
			left = (width - webWidth) / 2;
		}
		if (height > webHeight) {
			top = (height - webHeight) / 2;
		}
		open("DeviMoniWS_Show.jsp?args=" + args, "_blank", "height="
				+ webHeight + ",width=" + webWidth + ",left=" + left + ",top="
				+ top
				+ ",status=no,toolbar=no,menubar=no,location=no,scrollbars=yes");
	}

	//处理异常信息
	function displayErrorMsg(e) {
		tmp = "";
		for (x in e) {
			tmp += "  " + x + "=>" + e[x] + "  ";
		}

		tiShi(tmp, 1);
	}
</script>

<script type="text/javascript" src="js/jquery.js"></script>
</head>
<body onload="javascript:parent.hidit();connect()">
	<noscript>
		<h2 style="color: #ff0000">Seems your browser doesn't support
			Javascript! Websockets rely on Javascript being enabled. Please
			enable Javascript and reload this page!</h2>
	</noscript>
	<div>
		<div>
			<table>
				<tr>

					<td align="left" width="30" height="40"><br> <img
						src="images/default/nav.gif">
					</td>

					<td><font color=blue>PowerView XP </font>--- <font
						class="location">交易实时监控 </font>
					</td>

				</tr>
			</table>
		</div>
		<div id="root"></div>



		<div id="moni-table">

			<table class="list_table_border">
				<%
					int i = -1;
				%>

				<logic:iterate id="devinfos" name="devInfoList"
					type="com.lcjr.pvxp.orm.Devinfo">

					<%
						i++;
							if (i % 7 == 0) {
					%>
					<tr>
						<%
							}
						%>


						<td ><div id="<%=devinfos.getDevno().trim()%>" onClick="Show_devinfo('<%=devinfos.getDevno().trim()%>')">
								<img src="images/BlackBerry/bb_off_line.png" style="cursor:hand">
								<center>
									<b> <%=devinfos.getDevno().trim()%></b></center>
							</div>
						</td>
				</logic:iterate>
				<%
					if (i % 5 == 0) {
				%>
				</tr>
				<%
					}
				%>

			</table>
		</div>
	</div>

</body>
</html>