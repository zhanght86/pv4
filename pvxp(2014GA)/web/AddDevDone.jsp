<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<app:validateCookie />
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="2" />

<%
String message = (String) request.getAttribute(Constants.REQUEST_ADDDEV_DONE);
%>

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.dev.add" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>

<body onload="javascript:parent.hidit();">

	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" align="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP</font> ---
				<font class="location"><bean:message key="pvxp.dev.modify" />
				</font>
			</td>
		</tr>
	</table>
	<table width="100%" cellspacing="1" cellpadding="2" class="list_table_border">
		<tr>
			<td class="list_td_title">
				<b>ÌבÊ¾</b>
			</td>
		</tr>
		<tr class="list_tr0">
			<td align="center">
				<center>
					<h2>
						<bean:message key="<%=message%>" />
					</h2>
					<center>
			</td>
		</tr>
	</table>
	<br>
	<br>
	<div align="center">
		<img src="images/default/bt_back.gif"
			onClick="parent.parent.getDevList('1')" style="cursor:hand">
	</div>

</body>
</html:html>
