<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="2" minpower="2" />


<html:html locale="true">
<head>
	<title><bean:message key="pvxp.operator.delete" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">
		<%
			String[] operArry = request.getParameterValues("operArry");
			String[] result = (String[]) request.getAttribute(Constants.REQUEST_OPERATOR_DEL_DONE);
			if (operArry == null || result == null || operArry.length != result.length) {
		%>
		System errors...
		<%
		} else {
		%>
		<br>
		<br>
		<br>
		<table width="75%" cellspacing="1" cellpadding="3"
			class="list_table_border">
			<tr align="center">
				<td class="list_td_title">
					<bean:message key="pvxp.operator.operid" />
				</td>
				<td class="list_td_title">
					<bean:message key="pvxp.operator.delete" />
				</td>
			</tr>
			<%
						for (int i = 0; i < operArry.length; i++) {
						String tmpmsgstr = "pvxp.operator.del." + result[i];
						if (!result[i].equals("0")) {
			%>
			<tr align="center" class="list_tr<%=i % 2%>">
				<td>
					<font color=red><%=operArry[i]%>
					</font>
				</td>
				<td>
					<font color=red><bean:message key="<%=tmpmsgstr%>" />
					</font>
				</td>
			</tr>
			<%
			} else {
			%>
			<tr align="center" class="list_tr<%=i % 2%>">
				<td>
					<%=operArry[i]%>
				</td>
				<td>
					<bean:message key="<%=tmpmsgstr%>" />
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
					<a href="javascript:parent.parent.getOperList('1');"
						onFocus="this.blur()"> <img src="images/default/bt_back.gif"
							border="0"> </a>
				</td>
			</tr>
		</table>
		<form>
	</div>
</body>
</html:html>
