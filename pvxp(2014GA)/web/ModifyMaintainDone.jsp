<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="2" />

<%
String message = (String) request.getAttribute(Constants.REQUEST_MAINTAIN_MESSAGE);
%>
<html:html locale="true">
<head>
	<title>报修记录修改</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>

<body onload="javascript:parent.hidit();">
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" valign="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP</font> ---
				<font class="location">报修记录修改</font>
			</td>
		</tr>
	</table>
	<center>
		<h2>
			<bean:message key="<%=message%>" />
		</h2>
		<center>
			<center>
				<img src="images/default/bt_back.gif"
					onClick="parent.parent.getMaintainList('1')" style="cursor:hand">
			</center>
</body>
</html:html>
