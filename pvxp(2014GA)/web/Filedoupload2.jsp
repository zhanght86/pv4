<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp" %>
<app:checkpower funcid="3" minpower="2" />

<%@ page import="com.jspsmart.upload.SmartUpload"%>
<%
	SmartUpload mySmartUpload = new SmartUpload();
	String tem = "�����쳣���������Ա��ϵ";

	mySmartUpload.initialize(pageContext);
	mySmartUpload.service(request, response);
	mySmartUpload.upload();

	String path = mySmartUpload.getRequest().getParameter("filepath");

	if (path == null)
		throw new Exception("·��Ϊ��");
	int i = mySmartUpload.save(path);
	if (i >= 1) {
		tem = "�ɹ��ϴ�"+i+"���ļ�";
	} else {
		tem = "�ϴ�ʧ��";
	}
%>
<html:html locale="true">


<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>

<body onload="javascript:parent.hidit();">
	<br>
	<br>
	<br>
	<table width="100%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<tr>
			<td class="list_td_title">
				<b>��ʾ</b>
			</td>
		</tr>
		<tr class="list_tr0">
			<td align="center">
				<%=tem%>
			</td>
		</tr>
	</table>
	<br>
	<br>
	<div align="center">
		<img src="images/default/bt_back.gif"
			onClick="javascript:parent.parent.goUrl('FileUpload1.jsp')"
			style="cursor:hand">
	</div>
</body>
</html:html>
