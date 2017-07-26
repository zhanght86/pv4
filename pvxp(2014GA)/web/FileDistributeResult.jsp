<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="3" minpower="1" />

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.filedistribute.title" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location"><bean:message
							key="pvxp.filedistribute.title" />
					</font>
				</td>
			</tr>
		</table>
		<%
			String[] devlist = request.getParameterValues("devlist");
			String[][][] result = (String[][][]) request.getAttribute(Constants.REQUEST_FILEDISTRIBUTE_RESULT);
			Vector fileVector = (Vector) request.getAttribute(Constants.REQUEST_FILEDISTRIBUTE_FILES);
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
					<bean:message key="pvxp.filedistribute.filelist" />
				</td>
				<td class="list_td_title">
					<bean:message key="pvxp.filedistribute.title" />
				</td>
			</tr>
			<%
						for (int i = 0; i < devlist.length; i++) {
						for (int j = 0; j < fileVector.size(); j++) {
					if (result[i][j][0] != null) {
						String tmpmsgstr = "pvxp.filedistribute.op." + result[i][j][0];
						//System.out.println("result返回的结果是： "+result[i][j][0]);
						if (result[i][j][0].equals("0")) {
			%>
			<tr align="center" class="list_tr<%=i % 2%>">
				<td>
					<%=devlist[i]%>
				</td>
				<td>
					<%=fileVector.get(j)%>
				</td>
				<td>
					<bean:message key="<%=tmpmsgstr%>" />
				</td>
			</tr>
			<%
			} else if (result[i][j][0].equals("-1")) {
			%>
			<tr align="center" class="list_tr<%=i % 2%>">
				<td>
					<font color="red"><%=devlist[i]%>
					</font>
				</td>
				<td>
					<font color="red"><%=fileVector.get(j)%>
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
			<tr align="center" class="list_tr<%=i % 2%>">
				<td>
					<font color="red"><%=devlist[i]%>
					</font>
				</td>
				<td>
					<font color="red"><%=fileVector.get(j)%>
					</font>
				</td>
				<td>
					<font color="red"><%=result[i][j][0]%>
					</font>
				</td>
			</tr>
			<%
					}
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
						onclick="javascript:parent.parent.goUrl('Sys_Manage_FileDistribute.jsp');">
				</td>
			</tr>
		</table>
		<form>
	</div>
</body>
</html:html>
