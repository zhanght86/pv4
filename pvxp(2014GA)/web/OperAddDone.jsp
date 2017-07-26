<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="2" minpower="2" />

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.operator.add" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">
		<%
			PubUtil myPubUtil = new PubUtil();
			Operator temp = (Operator) request.getAttribute(Constants.REQUEST_OPERATOR);
			if (temp != null) {
		%>
		<br>
		<br>
		<br>
		<table width="100%" cellspacing="1" cellpadding="2"
			class="list_table_border">
			<tr>
				<td class="list_td_title">
					<b>提示</b>
				</td>
			</tr>
			<tr class="list_tr0">
				<td align="center">
					添加操作员成功，
					<font color="red">请在<%=myPubUtil.ReadConfig("Users", "WarningBefore", "3", "PowerView.ini")%>天内登录，否则密码失效</font>。
				</td>
			</tr>
		</table>

		<br>
		<br>
		<div align="center">
			<a href="javascript:parent.parent.getOperList('1');"
				onFocus="this.blur()"> <img src="images/default/bt_back.gif"
					border="0"> </a>
		</div>
		<%
		}
		%>

	</div>
</body>
</html:html>
