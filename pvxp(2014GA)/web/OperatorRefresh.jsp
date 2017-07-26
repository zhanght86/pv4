<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.model.*"%>
<html:html locale="true">

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="2" minpower="2" />
<%
if (BankinfoModel.resetNow()) {
%>
<body onload="javascript:parent.hidit();parent.parent.getOperList('1')">
	<%
	} else {
	%>

<body>
	刷新操作员信息失败！
	<table>
		<tr>
			<td id="delbt">
				<input type="button" value="返回操作员信息列表"
					onclick="javascript:parent.parent.getOperList('1')">
			</td>
		</tr>
		<table>
			<%
			}
			%>
		
</body>
</html:html>
