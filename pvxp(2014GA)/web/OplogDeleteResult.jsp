<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="2" minpower="2" />


<html:html locale="true">
<head>
	<title><bean:message key="pvxp.oplog.delete" />
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
					<font class="location"><bean:message key="pvxp.oplog.delete" />
					</font>
				</td>
			</tr>
		</table>
		<table>
			<tr id="delbt" align="center">
				<td>
					<%
						String result = (String) request.getAttribute("RESULT");
						if (result.equals("0")) {
					%>
					ɾ������Ա������¼�ɹ���
					<%
					} else if (result.equals("-1")) {
					%>
					ɾ������Ա������¼ʧ�ܣ�
					<%
					} else {
					%>
					δ֪����
					<%
					}
					%>
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" value="���ز���Ա������¼��ѯ"
						onclick="javascript:parent.parent.goUrl('OplogQuery.jsp')">
				</td>
			</tr>
		</table>

	</div>
</body>
</html:html>
