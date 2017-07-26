<%@ include file="inc/taglib.jsp" %>
<%@ page contentType="text/html;charset=gb2312"%>

<%@page import="com.lcjr.pvxp.util.*" %>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />

<%
	PubUtil myPubUtil = new PubUtil();
	String bankid = myPubUtil.dealNull(request.getParameter("bankid")).trim();
%>
<html:html locale="true">
  <head>
    <title></title>
    <link href="style/pvxp.css" rel="stylesheet" type="text/css">
    <html:base/>
<script>
function chkin(){
	if( document.all.upfile.value=="" ){
		alert("请选择或输入文件名后上传");
		return false;
	}else{
		var iFsubmit = confirm("上传文件将覆盖已有同格式图片\n是否确定上传？");
		if( iFsubmit ){
			document.all.mybankid.value=<%=bankid%>;
			document.all.upform.action+="?bankid=<%=bankid%>";
			document.all.upinput.style.display='none';
			document.all.uploading.style.display='';
		}
		return iFsubmit;
	}
}
var cans = false;
function mselect(){
	return cans;
}
</script>
</head>

<body leftmargin="0" topmargin="0" onselectstart="return mselect();">
<table border="0">
<form id="upform" action="DevMapEditDoUpload.jsp" method="post" enctype="multipart/form-data" onsubmit="return chkin();">
<tr id="upinput" align="left"><td valign="middle">
<td>
<input type="file" name="upfile" id="upfile" size="10" onFocusIn="cans=true;" onFocusOut="cans=false;"><br>
必须是jpg/png/gif中的一种<br><center><input type="image" src="images/default/bt_uploadimg.gif" onFocus="this.blur()"></center>
<input type="hidden" name="mybankid" id="mybankid">
</td></tr>
</form>
<tr id="uploading" style="display:none">
<td valign="middle">
正在上传...
</td></tr>
</table>
</body>
</html:html>
