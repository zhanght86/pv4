<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>


<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<%@page import="com.lcjr.pvxp.util.TradeMoniServer"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="4" minpower="1" />


<%
	OperatorModel myOperatorModel = new OperatorModel();
	int myPower = myOperatorModel.getPower(4, request);
	
	PubUtil myPubUtil = new PubUtil();
	String bankid = myPubUtil.dealNull(request.getParameter("bankid")).trim();
	String incsubs = myPubUtil.dealNull(request.getParameter("incsubs")).trim();
	String devstr = myPubUtil.dealNull(request.getParameter("devs")).trim();
	String port = myPubUtil.dealNull(myPubUtil.ReadConfig("System", "TradeMoniPort", "8088", "PowerView.ini")).trim();
	if (port.equals(""))
		port = "8088";
	
	boolean startOk = false;
	if (myPower > 0) {
		TradeMoniServer myTradeMoniServer = new TradeMoniServer();
		startOk = myTradeMoniServer.startMoni();
		
		BankinfoModel myBankinfoModel = new BankinfoModel();
		
		if (incsubs.equals("1")) {
			bankid = myBankinfoModel.getBankRange(bankid);
		}
	}
%>

<html:html locale="true">
<head>
	<title>交易监控</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
</head>
<body leftmargin="0" topmargin="0" ondragstart="return false"
	onselectstart="return false" onselect="document.selection.empty()">

	<%
	if (myPower == 0) {
	%>
	<bean:message key="pvxp.errors.op.no.power" />
	<%
	}
	%>
	<%
	if (startOk) {
	%>
	<!-- 影片中使用的 URL-->
	<!-- 影片中使用的文本-->
	<table width="100%" height="100%">
		<tr>
			<td align="center" valign="center">
				<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
					codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0"
					width="1150" height="600" id="test" align="middle">
					<param name="allowScriptAccess" value="sameDomain" />
					<param name="movie"
						value="swf/Monitor.swf?port=<%=port%>&mybankid=<%=bankid%>&mydevstr=<%=devstr%>" />
					<param name="quality" value="high" />
					<param name="menu" value="false" />
					<param name="bgcolor" value="#ffffff" />
					<embed src="swf/TradeMoni.swf" quality="high" bgcolor="#ffffff"
						width="950" height="550" name="test" align="middle"
						allowScriptAccess="sameDomain"
						type="application/x-shockwave-flash"
						pluginspage="http://www.macromedia.com/go/getflashplayer" />
				</object>
			</td>
		</tr>
	</table>
	<%
	} else {
	%>
	<bean:message key="pvxp.errors.trademoni.starterr" />
	<%
	}
	%>

</body>
</html:html>
