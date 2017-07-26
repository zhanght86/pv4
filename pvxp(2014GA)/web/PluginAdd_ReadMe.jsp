<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="13" minpower="2" />
<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>

</head>

<body onload="javascript:parent.hidit();">
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" valign="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP </font>---
				<font class="location"><bean:message
						key="pvxp.syssetup.title" /> </font>---
				<font class="location"><bean:message
						key="pvxp.plugin.add.readme.title" /> </font>
			</td>
		</tr>
	</table>
	<table align="center" width="100%" border="0" cellpadding="1"
		cellspacing="1" class="list_table_border">
		<tr align="center" class="list_td_title">
			<td>
				<b><bean:message key="pvxp.syssetup.plugin.add" /> <bean:message
						key="pvxp.readme" /> </b>
			</td>
		</tr>
		<tr align="left" class="list_tr0">
			<td>
				<bean:message key="pvxp.plugin.add.readme" />
			</td>
		</tr>
	</table>
	<br>
	<center>
		<input type="image" src="images/default/bt_back.gif"
			onClick="javascript:history.back();">
	</center>
</body>
</html:html>
