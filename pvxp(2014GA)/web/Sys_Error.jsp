<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<html:html locale="true">
<head>
	<title>Error Page</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
</head>
<body onload="javascript:parent.hidit();" ondragstart="return false"
	onselectstart="return false" onselect="document.selection.empty()">

	<br>
	<br>
	<br>
	<table width="100%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<tr>
			<td class="list_td_title">
				<b>´íÎó</b>
			</td>
		</tr>
		<%
		String essmsg = (String) request.getAttribute(Constants.REQUEST_SYSTEM_ERRMSG);
		%>
		<tr class="list_tr0">
			<td align="center">
				<bean:message key="<%=essmsg%>" />
			</td>
		</tr>
	</table>

	<br>
	<br>
	<div align="center">
		<a href="javascript:history.back()" onFocus="this.blur()"><img
				src="images/default/bt_back.gif" border="0">
		</a>
	</div>
</body>
</html:html>
