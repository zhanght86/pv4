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
	ˢ�²���Ա��Ϣʧ�ܣ�
	<table>
		<tr>
			<td id="delbt">
				<input type="button" value="���ز���Ա��Ϣ�б�"
					onclick="javascript:parent.parent.getOperList('1')">
			</td>
		</tr>
		<table>
			<%
			}
			%>
		
</body>
</html:html>
