<%@page import="com.lcjr.pvxp.model.BankinfoModel"%>
<%@page import="com.lcjr.pvxp.orm.Bankinfo"%>
<%@page import="com.lcjr.pvxp.model.DevtypeModel"%>
<%@page import="com.lcjr.pvxp.orm.Devtype"%>
<%@page import="com.lcjr.pvxp.model.DevinfoModel"%>
<%@page import="org.apache.log4j.Logger"%>

<%@page import="com.lcjr.pvxp.orm.Devinfo"%>
<%@ include file="inc/taglib.jsp"%>
<%@page contentType="text/html;charset=gb2312" %>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<%@page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="2" />

<%

	Logger log = Logger.getLogger("web.Devinfo_Show_Moni.jsp");

	PubUtil myPubUtil = new PubUtil();
	CharSet myCharSet = new CharSet();
	String args = request.getParameter("args").trim();
	int index = args.indexOf(":");
	String devno = args.substring(0, index);
	log.error("args="+args);

//	设备信息
	Devinfo myDevinfo = (Devinfo) DevinfoModel.getDevFromList(devno);
	
	String myTypeno = "";
	String myDevip =  "";
	String myDevmac =  "";
	String myTypestate = "";
	String myPacktype =  "";
	String myLocaltag =  "";
	String myPolltag =  "";
	String myBankid =  "";
	String mySysid =  "";
	String myOrganno = "";
	String myTellerno =  "";
	String myDevaddr =  "";
	String myDutyname =  "";
	String myOrgancontact =  "";
	String myAutherno = "";
	try{ 
	
	 myTypeno = myPubUtil.dealNull(myDevinfo.getTypeno()).trim();
	 myDevip = myPubUtil.dealNull(myDevinfo.getDevip()).trim();
	 myDevmac = myPubUtil.dealNull(myDevinfo.getDevmac()).trim();
	 myTypestate = myPubUtil.dealNull(myDevinfo.getTypestate()).trim();
	 myPacktype = myPubUtil.dealNull(myDevinfo.getPacktype()).trim();
	 myLocaltag = myPubUtil.dealNull(myDevinfo.getLocaltag()).trim();
	 myPolltag = myPubUtil.dealNull(myDevinfo.getPolltag()).trim();
	 myBankid = myPubUtil.dealNull(myDevinfo.getBankid()).trim();
	 mySysid = myPubUtil.dealNull(myDevinfo.getSysid()).trim();
	 myOrganno = myPubUtil.dealNull(myDevinfo.getOrganno()).trim();
	 myTellerno = myPubUtil.dealNull(myDevinfo.getTellerno()).trim();
	 myDevaddr = myCharSet.db2web( myPubUtil.dealNull(myDevinfo.getDevaddr())).trim();
	 myDutyname = myCharSet.db2web( myPubUtil.dealNull(myDevinfo.getDutyname())).trim();
	 myOrgancontact = myCharSet.db2web( myPubUtil.dealNull(myDevinfo.getOrgancontact())).trim();
	 myAutherno = myPubUtil.dealNull(myDevinfo.getAutherno()) .trim();
	
	}catch(Exception e){
		log.info("Exception=",e);
	}
	
	log.info("----------");
//设备类型
	String myDevname = "";
	try {
		Devtype myDevtype = (Devtype) DevtypeModel.getDevTpFromList(myTypeno);

		myDevname = myCharSet.db2web((myDevtype.getDevname())).trim();
	} catch (Exception e) {
		log.error("Exception1=",e);
		myDevname = "";
	}

	String myBanknm = "";
	try {
	//机构信息
		Bankinfo myBankinfo = (Bankinfo) BankinfoModel.getBankinfoFromList(myBankid);
		myBanknm = myCharSet.db2web((myBankinfo.getBanknm())).trim();
	} catch (Exception e) {
		log.error("Exception2=",e);
		myBanknm = "";
	}

	log.error("myTypestate="+myTypestate+"  myPacktype="+myPacktype+"  myLocaltag="+myLocaltag+"  myPolltag"+myPolltag);
	System.out.println("myTypestate="+myTypestate+"  myPacktype="+myPacktype+"  myLocaltag="+myLocaltag+"  myPolltag"+myPolltag);
	
	if (myTypestate.equals("a") || myTypestate.equals("s")) {
		myTypestate = "pvxp.dev.state." + myTypestate;
	} else {
		myTypestate = "pvxp.dev.state.other";
	}

	if (myPacktype.equals("b") || myPacktype.equals("c")) {
		myPacktype = "pvxp.devtype.packtype." + myPacktype;
	} else {
		myPacktype = "pvxp.devtype.packtype.other";
	}

	if (myLocaltag.equals("0") || myLocaltag.equals("1")) {
		myLocaltag = "pvxp.dev.local." + myLocaltag;
	} else {
		myLocaltag = "pvxp.dev.local.other";
	}

	if (myPolltag.equals("0") || myPolltag.equals("1")
			|| myPolltag.equals("9")) {
		myPolltag = "pvxp.dev.poll." + myPolltag;
	} else {
		myPolltag = "pvxp.dev.poll.other";
	}
%>

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.dev.detail" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
	<jsp:include page="DevStateCode_JS.jsp" flush="true" />
	<jsp:include page="DevStateNo_JS.jsp" flush="true" />
</head>
<script language="javascript" type="text/javascript">
<!--

//-->

function ltrim(str)
//去除str前端的" ","\t","\n","\r"字符
{
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
    if (whitespace.indexOf(s.charAt(0)) != -1)
    //判断s的第一个字符是否是whitespace字符串中的字符
    {
        var j=0,i = s.length;
        while (j < i && whitespace.indexOf(s.charAt(j)) != -1)
        {
            j++;
            //j为s字符串前端几个字符是whitespace中某个字符的数量
        }
        s = s.substring(j, i);
    }
    return s;
}
function rtrim(str)
//去除str字符串末尾的" ","\t","\n","\r"字符
{
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
    if (whitespace.indexOf(s.charAt(s.length-1)) != -1)
    {
        var i = s.length - 1;
        while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1)
        {
            i--;
        }
        s = s.substring(0, i+1);
    }
    return s;
}

function format(str){
	var retStr = str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8)+" "+str.substring(8,10)+":"+str.substring(10,12)+":"+str.substring(12,14);
	return retStr;
}

function load(){
	DevMoniFlash_DoFSCommand();
}

//制图
function DevMoniFlash_DoFSCommand(command, args){
	
	var lastTime = 0;
	var table = document.getElementById("stateTable");
	if(table == null){
		table = document.createElement("stateTable");
	}

	/*var titleTr = document.getElementById("title");
	if(titleTr == null){
		
		titleTr = table.insertRow(0);
		titleTr.id = "title";
		var cell1 = titleTr.insertCell(0);
		cell1.className = "list_td_title";
		cell1.innerHTML = "<center><b>设备编号：</b></center>"
	}*/


	var stateString = '';
	if(args==null||args==''){
		stateString = "<%=args%>";
	} else {
		stateString = args;
	}

	var i = stateString.indexOf(":");
	var devno =rtrim(ltrim(stateString.substring(0,i)));
	stateString = stateString.substring(i+1,stateString.length);
	var stateArray = stateString.split("|");
	var totalState = '-1';
	var startTime ;
	var lastTime;
	
	for(var i = 0;i<stateArray.length;i++){

		var array = stateArray[i].split(",");
		array[2] = array[2]+array[3];
		array[3] = array[4]+array[5];

		if(lastTime < array[3]){
			lastTime = array[3];
		}


		var tr = document.getElementById(devno+i);
		if(tr==null){
			tr = table.insertRow(i+1);
			tr.id = devno+i;
			tr.className = "list_tr"+i%2;
		}
		
		for(var j = 0;j<array.length-2;j++){
			var cell = document.getElementById(devno+i+j);
			if(cell == null){
				cell = tr.insertCell(j);
				cell.id = devno+i+j;
			}
		    if(j==0){
		    	var subDevName;
		    	try{
		    		subDevName = eval("StateNo_"+array[j]);
		    	} catch(e) {
		    		subDevName = array[0];
		    	}
		    	cell.innerHTML = "<center>"+subDevName+"</center>";
		    } else if(j==1){
				var thisState = -1;
				var thisStateName;
				try{
					var thisStateString = eval("Code_"+array[j]);
					var thisStateArray = thisStateString.split(",");
					thisState = thisStateArray[1];
					thisStateName = thisStateArray[0];
				} catch(e) {
					thisStateName = array[j];
					thisState = "-1"
				}
				if(thisState=="0"){
					cell.innerHTML = "<center>"+thisStateName+"</center>";
				} else if(thisState=="1"){
					cell.innerHTML = "<center><font color=#CBC90E>"+thisStateName+"</font></center>"
				} else if(thisState=="2"){
					cell.innerHTML = "<center><font color=red>"+thisStateName+"</font></center>"
				} else {
					cell.innerHTML = "<center><font color=#999999>"+thisStateName+"</font></center>"
				}
				if(totalState<thisState){
					totalState = thisState;
				}
			} else if(j==2||j==3){
				cell.innerHTML = "<center>"+format(array[j])+"</center>";
			} else {
				cell.innerHTML = "<center>"+array[j]+"</center>";
			}
		}
	}
	var totalStateTr = document.getElementById("totalState");
	if(totalState==0){
		totalStateTr.innerHTML = "<center>正&nbsp&nbsp&nbsp常</center>";
	} else if(totalState==1){
		totalStateTr.innerHTML = "<center><b><font color=#F5F328>警&nbsp&nbsp&nbsp告</font></b></center>";
	} else if(totalState==2){
		totalStateTr.innerHTML = "<center><b><font color=red>故&nbsp&nbsp&nbsp障</font></b></center>";
	} else {
		totalStateTr.innerHTML = "<center><b><font color=#999999>未&nbsp&nbsp&nbsp知</font></b></center>";
	}
}

</script>
<body leftmargin="0" topmargin="0" onload="load()">

	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<!-- header -->
		<tr align="center">
			<td colspan="6" class="list_td_title">
				<b><bean:message key="pvxp.dev.detail" /> </b>
			</td>
		</tr>
		<!-- body -->
		<tr align="center">
			<td width="10%" class="list_td_prom">
				<b><bean:message key="prompt.devno" /> </b>
			</td>
			<td width="15%" class="list_td_detail">
				<%=devno%>
			</td>
			<td width="10%" class="list_td_prom">
				<b><bean:message key="prompt.typeno" /> </b>
			</td>
			<td width="15%" class="list_td_detail">
				<nobr>
					<%=myDevname%>
				</nobr>
			</td>
			<td width="10%" class="list_td_prom">
				<b><bean:message key="prompt.devip" /> </b>
			</td>
			<td width="15%" class="list_td_detail">
				<%=myDevip%>
			</td>
			<!-- 	<td width="10%" class="list_td_prom">
				<b><bean:message key="prompt.devmac" /> </b>
			</td>
			<td width="15%" class="list_td_detail"><%=myDevmac%></td> -->
		</tr>
		<!-- <tr align="center">
			<td class="list_td_prom">
				<b><bean:message key="prompt.typestate" /> </b>
			</td>
			<td class="list_td_detail">
				<bean:message key="<%=myTypestate%>" />
			</td>
			<td class="list_td_prom">
				<b><bean:message key="prompt.datatype" /> </b>
			</td>
			<td class="list_td_detail">
				<bean:message key="<%=myPacktype%>" />
			</td>
			<td class="list_td_prom">
				<b><bean:message key="prompt.localtag" /> </b>
			</td>
			<td class="list_td_detail">
				<bean:message key="<%=myLocaltag%>" />
			</td>
			<td class="list_td_prom">
				<b><bean:message key="prompt.polltag" /> </b>
			</td>
			<td class="list_td_detail">
				<bean:message key="<%=myPolltag%>" />
			</td>
		</tr> -->
		<tr align="center">
			<td class="list_td_prom" align="center">
				<b>总状态</b>
			</td>
			<td class="list_td_detail" id="totalState">
			</td>
			<td class="list_td_prom">
				<b><bean:message key="prompt.dutyname" /> </b>
			</td>
			<td class="list_td_detail">
				<%=myDutyname%>
			</td>
			<td class="list_td_prom">
				<b><bean:message key="prompt.organcontact" /> </b>
			</td>
			<td class="list_td_detail">
				<%=myOrgancontact%>
			</td>
			<!-- 	<td class="list_td_prom">
				<b><bean:message key="prompt.auther" /> </b>
			</td>
			<td class="list_td_detail"><%=myAutherno%></td> -->
		</tr>
		<tr align="center">
			<td class="list_td_prom">
				<b><bean:message key="prompt.bank" /> </b>
			</td>
			<td class="list_td_detail" colspan="3">
				<nobr>
					<%=myBanknm%>
				</nobr>
			</td>
			<td class="list_td_prom" rowspan="2">
				<b>检测</b>
			</td>
			<td class="list_td_detail" rowspan="2">
				<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
					codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0"
					width="102" height="24" id="DevCheck" align="middle">
					<param name="allowScriptAccess" value="sameDomain" />
					<param name="movie" value="swf/DevCheck.swf?devno=<%=devno%>" />
					<param name="quality" value="high" />
					<param name="menu" value="false" />
					<param name="wmode" value="transparent" />
					<embed src="swf/DevCheck.swf" quality="high" bgcolor="#ffffff"
						width="102" height="24" name="DevCheck" align="middle"
						allowScriptAccess="sameDomain"
						type="application/x-shockwave-flash"
						pluginspage="http://www.macromedia.com/go/getflashplayer" />
				</object>
			</td>
			<!--	<td class="list_td_prom">
				<b><bean:message key="prompt.sysid" /> </b>
			</td>
			<td class="list_td_detail"><%=mySysid%></td> 
			<td class="list_td_prom">
				<b><bean:message key="prompt.organno" /> </b>
			</td>
			<td class="list_td_detail"><%=myOrganno%></td>
			<td class="list_td_prom">
				<b><bean:message key="prompt.tellerno" /> </b>
			</td>
			<td class="list_td_detail"><%=myTellerno%></td>-->
		</tr>
		<tr>
			<td class="list_td_prom">
				<center>
					<b><bean:message key="prompt.devaddr" /> </b>
				</center>
			</td>
			<td class="list_td_detail" colspan='3'>
				<center>
					<%=myDevaddr%>
				</center>
			</td>
		</tr>
	</table>
	<hr>
	<table id="stateTable" width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_td_title">
			<td align="center">
				子设备
			</td>
			<td align="center">
				状态
			</td>
			<td align="center">
				开始时间
			</td>
			<td align="center">
				最后检测时间
			</td>
		</tr>
	</table>


	<!-- footer -->
	<table align="center" width="40%">
		<tr align="center">
			<td>
				<input type="button" value="关闭窗口"
					onClick="javascript:window.close();">
			</td>
		</tr>
	</table>

</body>
</html:html>
