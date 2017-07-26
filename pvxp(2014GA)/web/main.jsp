<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>

</head>

<body onload="javascript:parent.hidit();">
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" align="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP </font>---
				<font class="location"><bean:message
						key="pvxp.application.welcome.title" />
				</font>
			</td>
		</tr>
	</table>
	<table align="center" width="100%" border="0" cellpadding="1"
		cellspacing="1" class="list_table_border">
		<tr align="center" class="list_td_title">
			<td>
				<b><bean:message key="pvxp.application.welcome.main.title" />
				</b>
			</td>
		</tr>
		<tr align="left" class="list_tr0">
			<td>
				<bean:message key="pvxp.application.welcome.main.readme" />
			</td>
		</tr>
	</table>
</body>
</html:html>
