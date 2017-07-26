<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp" %>
<app:checkpower funcid="3" minpower="2" />

<%@ page import="com.jspsmart.upload.SmartUpload"%>
<%
	SmartUpload mySmartUpload = new SmartUpload();
	String tem = "出现异常，请与管理员联系";

	mySmartUpload.initialize(pageContext);
	mySmartUpload.service(request, response);
	mySmartUpload.upload();

	String path = mySmartUpload.getRequest().getParameter("filepath");

	if (path == null)
		throw new Exception("路径为空");
	int i = mySmartUpload.save(path);
	if (i >= 1) {
		tem = "成功上传"+i+"个文件";
	} else {
		tem = "上传失败";
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
				<b>提示</b>
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
