<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="2" />

<html:html locale="true">
<%
if (BankinfoModel.resetNow()) {
%>
<body
	onload="javascript:parent.hidit();parent.parent.getBankinfoList('1')">
	<%
	} else {
	%>

<body>
	ˢ�»�����Ϣʧ�ܣ�
	<table>
		<tr>
			<td id="delbt">
				<input type="button" value="���ػ�����Ϣ�б�"
					onclick="javascript:parent.parent.getBankinfoList('1')">
			</td>
		</tr>
	</table>
	<%
	}
	%>

</body>
</html:html>
