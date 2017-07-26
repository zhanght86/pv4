<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<html>
<head>
<title>Apache Tomcat WebSocket Examples: Echo</title>
<style type="text/css">
#connect-container {
	float: left;
	height: 500px;
	width: 100%;
}

#connect-container div {
	height: 500px;
	padding: 10px;
}

#connect-container table {
	border: 1px solid #CCCCCC;
	width: 100%;
	overflow-y: scroll;
	padding: 5px;
	margin: 0;
	text-align: center;
	height: auto;
}

#connect-container tr {
	height: 25px;
}

#connect-container tr:hover {
	color: green;
	background-color: #cccaaa;
	text-align: center;
}

#connect-container td {
	height: 25px;
}

#connect-container td:hover {
	color: red;
	background-color: #eeeaaa;
	text-align: center;
}

#console {
	border: 1px solid #CCCCCC;
	border-right-color: #999999;
	border-bottom-color: #999999;
	height: 100%;
	width: 100%;
	overflow-y: scroll;
	padding: 5px;
}

.green {
	color: green;
}

.red {
	color: red;
}

.fixedtd {
	background-color: #D9D919;
}

.ou {
	background-color: #ADEAEA;
}

.ji {
	background-color: #C0D9D9;
}
</style>
<script type="text/javascript">
	var ws = null;
	//建立连接
	function connect() {
		var target = 'ws://' + window.location.host
				+ "/pvxp/websocket/tradeMsg";

		if ('WebSocket' in window) {
			ws = new WebSocket(target);
		} else if ('MozWebSocket' in window) {
			ws = new MozWebSocket(target);
		} else {
			tiShi('本浏览器不支持WebSocket，请使用IE10+浏览器，WebSocket is not supported by this browser.', 1);
			return;
		}

		ws.onopen = function() {
			tiShi('提示: 交易监控已经成功连接。', 0);
		};

		ws.onmessage = function(event) {
			var message = JSON.parse(event.data);
			log(message);
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

		tiShi('提示: 交易监控已经关闭。disconnect()', 1);
	}

	var i = 1;

	//提示用户
	function tiShi(message, clazz) {

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
	function log(message) {

		var console = document.getElementById('console');

		var tr = document.createElement('tr');
		if (i % 2 == 0)
			tr.setAttribute("class", "ou")
		else
			tr.setAttribute("class", "ji")

		var td = document.createElement('td');
		td.appendChild(document.createTextNode(i));
		i++;
		var td1 = document.createElement('td');
		td1.appendChild(document.createTextNode(message.Server_Date + ' '
				+ message.Server_Time));
		var td2 = document.createElement('td');
		td2.appendChild(document
				.createTextNode(message['ENTITY.XML_TYPE_NAME']));
		var td3 = document.createElement('td');
		td3.appendChild(document.createTextNode(message['ENTITY.IP']));
		var td4 = document.createElement('td');
		td4.appendChild(document.createTextNode(message['ENTITY.TERMNO']));

		tr.appendChild(td);
		tr.appendChild(td1);
		tr.appendChild(td2);
		tr.appendChild(td3);
		tr.appendChild(td4);

		console.appendChild(tr);

		while (console.childNodes.length > 50) {
			console.removeChild(console.firstChild.nextSibling.nextSibling);
		}

		console.scrollTop = console.scrollHeight;

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
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>

</head>
<body onload="javascript:parent.hidit();connect()">

	<noscript>
		<h2 style="color: #ff0000">Seems your browser doesn't support
			Javascript! Websockets rely on Javascript being enabled. Please
			enable Javascript and reload this page!</h2>
	</noscript>

	<div style="height: 100%;width: 100%;">
		<div>
			<table>
				<tr>

					<td align="left" width="30" height="40"><img
						src="images/default/nav.gif"></td>

					<td><font color=blue>PowerView XP </font>--- <font
						class="location">交易实时监控 </font></td>

				</tr>
			</table>
		</div>
		<div id="root"></div>

		<div id="connect-container">
			<table id="console">
				<tr>
					<td class="fixedtd"><B>序列号</B>
					</td>
					<td class="fixedtd"><B>时间</B>
					</td>
					<td class="fixedtd"><B>业务编号</B>
					</td>
					<td class="fixedtd"><B>IP</B>
					</td>
					<td class="fixedtd"><B>设备编号</B>
					</td>
				</tr>

			</table>
		</div>
	</div>

</body>
</html>