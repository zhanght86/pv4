<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<app:validateCookie />
<app:checkpower funcid="13" minpower="2" />
<%@ page errorPage="Exception.jsp"%>


<%
String message = (String) request.getAttribute(Constants.REQUEST_DEVFT_MESSAGE);
%>

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.devft.add" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>

<body onload="javascript:parent.hidit();">


	<br>
	<br>
	<br>
	<table width="100%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<tr>
			<td class="list_td_title">
				<b>ÌבÊ¾</b>
			</td>
		</tr>
		<tr class="list_tr0">
			<td align="center">
				<bean:message key="<%=message%>" />
			</td>
		</tr>
	</table>
	<br>
	<br>
	<div align="center">
		<img src="images/default/bt_back.gif"
			onClick="parent.parent.getDevftList('1')" style="cursor:hand">
	</div>




</body>
</html:html>
