<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="3" minpower="1" />


<html:html locale="true">
<head>
	<title><bean:message key="pvxp.remotecontrol.title" /></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" align="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location"><bean:message
							key="pvxp.remotecontrol.title" /> </font>
				</td>
			</tr>
		</table>
		<%
			String[] devlist = request.getParameterValues("devlist");
			String[] result = (String[]) request.getAttribute(Constants.REQUEST_REMOTECONTROL_RESULT);
			if (devlist == null || result == null || devlist.length != result.length) {
		%>
		System errors...
		<%
		} else {
		%>
		<table width="75%" cellspacing="1" cellpadding="3"
			class="list_table_border">
			<tr align="center">
				<td class="list_td_title">
					<bean:message key="pvxp.dev.no" />
				</td>
				<td class="list_td_title">
					<bean:message key="pvxp.remotecontrol.title" />
				</td>
			</tr>
			<%
						for (int i = 0; i < devlist.length; i++) {
						if (result[i] != null) {
					String tmpmsgstr = "pvxp.remotecontrol.op." + result[i];
					if (result[i].equals("0")) {
			%>
			<tr align="center" class="list_tr<%=i % 2%>">
				<td>
					<%=devlist[i]%>
				</td>
				<td>
					<bean:message key="<%=tmpmsgstr%>" />
				</td>
			</tr>
			<%
			} else if (result[i].equals("-1")) {
			%>
			<tr align="center" class="list_tr<%=i % 2%>">
				<td>
					<font color="red"><%=devlist[i]%> </font>
				</td>
				<td>
					<font color="red"><bean:message key="<%=tmpmsgstr%>" /> </font>
				</td>
			</tr>
			<%
			} else {
			%>
			<tr align="center" class="list_tr<%=i % 2%>">
				<td>
					<font color="red"><%=devlist[i]%> </font>
				</td>
				<td>
					<font color="red"><bean:message key="<%=result[i]%>" /> </font>
				</td>
			</tr>
			<%
						}
						}
					}
			%>
		</table>
		<%
		}
		%>
		<p></p>
		<table>
			<tr>
				<td id="delbt">
					<img src="images/default/bt_back.gif" style="cursor:hand"
						onFocus="this.blur()"
						onclick="javascript:parent.parent.goUrl('Sys_Manage_RemoteControl.jsp');">
				</td>
			</tr>
		</table>
		<form>
	</div>
</body>
</html:html>
