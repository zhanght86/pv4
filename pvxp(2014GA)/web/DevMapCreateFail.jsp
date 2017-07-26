<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />

<html:html locale="true">
<head>
	<title></title>
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
				<b>ÌבÊ¾</b>
			</td>
		</tr>
		<tr class="list_tr0">
			<%
			String essmsg = (String) request.getAttribute(Constants.REQUEST_SYSTEM_ERRMSG);
			%>
			<td align="center">
				<bean:message key="<%=essmsg%>" />
			</td>
		</tr>
	</table>
	<br>
	<br>
	<div align="center">
		<a href="javascript:parent.parent.getDevMapList(1)"
			onFocus="this.blur()"><img src="images/default/bt_back.gif"
				border="0">
		</a>
	</div>
</body>
</html:html>
