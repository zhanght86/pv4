<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="3" minpower="2" />

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.fileupload.title" />
	</title>
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
							key="pvxp.fileupload.title" />
					</font>
				</td>
			</tr>
		</table>
		<%
			String[] devno = request.getParameterValues("devno");
			String result = (String) request.getAttribute(Constants.REQUEST_FILEUPLOAD_RESULT);
			if ((devno == null) || (devno.equals(""))) {
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
					<bean:message key="pvxp.fileupload.title" />
				</td>
			</tr>
			<%
						if (result != null) {
						String tmpmsgstr = "pvxp.fileupload.op." + result;
						if (result.equals("0")) {
			%>
			<tr align="center" class="list_tr0">
				<td>
					<%=devno[0]%>
				</td>
				<td>
					<bean:message key="<%=tmpmsgstr%>" />
				</td>
			</tr>
			<%
			} else if (result.equals("-1")) {
			%>
			<tr align="center" class="list_tr0">
				<td>
					<font color="red"><%=devno[0]%>
					</font>
				</td>
				<td>
					<font color="red"><bean:message key="<%=tmpmsgstr%>" />
					</font>
				</td>
			</tr>
			<%
			} else {
			%>
			<tr align="center" class="list_tr0">
				<td>
					<font color="red"><%=devno[0]%>
					</font>
				</td>
				<td>
					<font color="red"><%=result%>
					</font>
				</td>
			</tr>
			<%
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
						onclick="javascript:parent.parent.goUrl('Sys_Manage_FileUpload.jsp');">
				</td>
			</tr>
		</table>
		<form>
	</div>
</body>
</html:html>
