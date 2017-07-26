<%@include file="inc/taglib.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.bean.*"%>
<%@page import="net.sf.json.JSONObject"%>

<%
	PubUtil myPubUtil = new PubUtil();
	CharSet myCharSet = new CharSet();
	String args = request.getParameter("args").trim();
	JSONObject  devStateJson;
	String devno = "";
	DevsStateBean aDevsStateBean=new DevsStateBean();
	try{
		devStateJson=JSONObject.fromObject(args);
		devno=devStateJson.getString("DevNo");
	}catch(Exception e){
		devno=args;
		devStateJson=JSONObject.fromObject("{\"DevNo\":\""+devno+"\"}");
		DevsState dss=aDevsStateBean.select("DevsState", "DevNo", "'"+devno+"'");
		
		if(dss==null){
			devStateJson.put("DevState","N/A");
			devStateJson.put("Prt_State", "N/A");
			devStateJson.put("IdCard_State","N/A");
			devStateJson.put("BrushCard_State","N/A");
			devStateJson.put("Bill_State", "N/A");
			devStateJson.put("Server_Date", "N/A");
			devStateJson.put("Server_Time", " ");
		}else{
		
			devStateJson.put("DevState",dss.getDevState());
			devStateJson.put("Prt_State", dss.getPrt_State());
			devStateJson.put("IdCard_State", dss.getIdCard_State());
			devStateJson.put("BrushCard_State", dss.getBrushCard_State());
			devStateJson.put("Bill_State", dss.getBill_State());
			devStateJson.put("Server_Date", dss.getBdate().toLocaleString());
			devStateJson.put("Server_Time", "");
			}
		}
	
	
	//	设备信息
	Devinfo myDevinfo = (Devinfo) DevinfoModel.getDevFromList(devno);


	String myTypeno = myPubUtil.dealNull(myDevinfo.getTypeno()).trim();
	String myDevip = myPubUtil.dealNull(myDevinfo.getDevip()).trim();
	String myDevmac = myPubUtil.dealNull(myDevinfo.getDevmac()).trim();
	String myTypestate = myPubUtil.dealNull(myDevinfo.getTypestate()).trim();
	String myPacktype = myPubUtil.dealNull(myDevinfo.getPacktype()).trim();
	String myLocaltag = myPubUtil.dealNull(myDevinfo.getLocaltag()).trim();
	String myPolltag = myPubUtil.dealNull(myDevinfo.getPolltag()).trim();
	String myBankid = myPubUtil.dealNull(myDevinfo.getBankid()).trim();
	String mySysid = myPubUtil.dealNull(myDevinfo.getSysid()).trim();
	String myOrganno = myPubUtil.dealNull(myDevinfo.getOrganno()).trim();
	String myTellerno = myPubUtil.dealNull(myDevinfo.getTellerno()).trim();
	String myDevaddr = myCharSet.db2web(myPubUtil.dealNull(myDevinfo.getDevaddr())).trim();
	String myDutyname = myCharSet.db2web(myPubUtil.dealNull(myDevinfo.getDutyname())).trim();
	String myOrgancontact = myCharSet.db2web(myPubUtil.dealNull(myDevinfo.getOrgancontact())).trim();
	String myAutherno = myPubUtil.dealNull(myDevinfo.getAutherno()).trim();


	//设备类型
	String myDevname = "";
	try {
		Devtype myDevtype = (Devtype) DevtypeModel.getDevTpFromList(myTypeno);
		myDevname = myCharSet.db2web((myDevtype.getDevname())).trim();
	} catch (Exception e) {
		myDevname = "";
	}


	String myBanknm = "";
	try {
		//机构信息
		Bankinfo myBankinfo = (Bankinfo) BankinfoModel.getBankinfoFromList(myBankid);
		myBanknm = myCharSet.db2web((myBankinfo.getBanknm())).trim();
	} catch (Exception e) {
		myBanknm = "";
	}

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

	if (myPolltag.equals("0") || myPolltag.equals("1") || myPolltag.equals("9")) {
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
<script type="text/javascript" src="js/jquery.js"></script>
</head>
<script>
//-->
//设置监控表格

function log() {
	var console = $("#stateTable");
	var message =<%=devStateJson%>;
	if(message.DevState==null)
		message.DevState="离线";
	$("#stateTable").append("<tr class='list_tr1'><td align='center'>总状态</td><td align='center'>"+message.DevState+"</td><td align='center'>"+message.Server_Date+" "+message.Server_Time+"</td></tr>");
	$("#stateTable").append("<tr class='list_tr1'><td align='center'>激光打印机</td><td align='center'>"+message.Prt_State+"</td><td align='center'>"+message.Server_Date+" "+message.Server_Time+"</td></tr>");
	$("#stateTable").append("<tr class='list_tr1'><td align='center'>IC卡机</td><td align='center'>"+message.IdCard_State+"</td><td align='center'>"+message.Server_Date+" "+message.Server_Time+"</td></tr>");
	$("#stateTable").append("<tr class='list_tr1'><td align='center'>二代证卡机</td><td align='center'>"+message.BrushCard_State+"</td><td align='center'>"+message.Server_Date+" "+message.Server_Time+"</td></tr>");
	$("#stateTable").append("<tr class='list_tr1'><td align='center'>凭条打印机</td><td align='center'>"+message.Bill_State+"</td><td align='center'>"+message.Server_Date+" "+message.Server_Time+"</td></tr>");
}
 
</script>
<body leftmargin="0" topmargin="0" onload="log()">

	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<!-- header -->
		<tr align="center">
			<td colspan="6" class="list_td_title"><b><bean:message
						key="pvxp.dev.detail" /> </b>
			</td>
		</tr>
		<!-- body -->
		<tr align="center">
			<td width="10%" class="list_td_prom"><b><bean:message
						key="prompt.devno" /> </b>
			</td>
			<td width="15%" class="list_td_detail"><%=devno%></td>
			<td width="10%" class="list_td_prom"><b><bean:message
						key="prompt.typeno" /> </b>
			</td>
			<td width="15%" class="list_td_detail"><nobr>
					<%=myDevname%>
				</nobr>
			</td>
			<td width="10%" class="list_td_prom"><b><bean:message
						key="prompt.devip" /> </b>
			</td>
			<td width="15%" class="list_td_detail"><%=myDevip%></td>
		</tr>
		<tr align="center">
			<td class="list_td_prom" align="center"><b><bean:message
						key="prompt.typestate" /> </b>
			</td>
			<td class="list_td_detail" id="totalState"><bean:message
					key="<%=myTypestate%>" /></td>

			<td class="list_td_prom"><b><bean:message
						key="prompt.dutyname" /> </b>
			</td>
			<td class="list_td_detail"><%=myDutyname%></td>
			<td class="list_td_prom">&nbsp;<b><bean:message
						key="prompt.organcontact" /> </b>
			</td>
			<td class="list_td_detail"><%=myOrgancontact%></td>
		</tr>
		<tr align="center">
			<td class="list_td_prom"><b><bean:message key="prompt.bank" />
			</b>
			</td>
			<td class="list_td_detail" colspan="5"><nobr>
					<%=myBanknm%>
				</nobr>
			</td>
		</tr>
		<tr>
			<td class="list_td_prom">
				<center>
					<b><bean:message key="prompt.devaddr" /> </b>
				</center>
			</td>
			<td class="list_td_detail" colspan='5'>
				<center>
					<%=myDevaddr%>
				</center>
			</td>
		</tr>
	</table>
	<hr>
	<table id="stateTable" width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr align="center">
			<td colspan="6" class="list_td_title"><b>历史状态</b>
			</td>
		</tr>
		<tr class="list_td_title">
			<td align="center">子设备</td>
			<td align="center">状态</td>
			<td align="center">最后检测时间</td>
		</tr>
	</table>

	<!-- footer -->
	<table align="center" width="40%">
		<tr align="center">
			<td><input type="button" value="关闭窗口"
				onClick="javascript:window.close();">
			</td>
		</tr>
	</table>

</body>
</html:html>
