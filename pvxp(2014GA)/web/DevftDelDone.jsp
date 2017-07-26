<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="2" />

<%
	String[] devftList = (String[]) request.getAttribute("devftList");
	String[] result = (String[]) request.getAttribute(Constants.REQUEST_DEVFT_DEL_DONE);
%>
<html:html locale="true">
<head>
	<title>删除设备厂商</title>
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
				<font class="location">删除设备厂商</font>
			</td>
		</tr>
	</table>

	<table align="center" width="90%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<tr align="center">
			<td class="list_td_title" colspan="2">
				<b>删除设备厂商</b>
			</td>
		</tr>
		<tr align="center" class="list_td_prom">
			<td>
				设备厂商编号
			</td>
			<td>
				删除结果
			</td>
		</tr>
		<%
			String isDel = "";
			for (int i = 0; i < devftList.length; i++) {
				
				isDel = "pvxp.devft.del." + result[i];
		%>
		<tr align="center" class="list_tr<%=i % 2%>">
			<td>
				<%=devftList[i]%>
			</td>
			<td>
				<bean:message key="<%=isDel%>" />
			</td>
		</tr>
		<%
		}
		%>
	</table>

	<p align="center">
		<img src="images/default/bt_back.gif"
			onclick="parent.parent.getDevftList(1)" style="cursor:hand">
	</p>
</body>
</html:html>
