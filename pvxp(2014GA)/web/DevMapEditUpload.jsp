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
		alert("��ѡ��������ļ������ϴ�");
		return false;
	}else{
		var iFsubmit = confirm("�ϴ��ļ�����������ͬ��ʽͼƬ\n�Ƿ�ȷ���ϴ���");
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
������jpg/png/gif�е�һ��<br><center><input type="image" src="images/default/bt_uploadimg.gif" onFocus="this.blur()"></center>
<input type="hidden" name="mybankid" id="mybankid">
</td></tr>
</form>
<tr id="uploading" style="display:none">
<td valign="middle">
�����ϴ�...
</td></tr>
</table>
</body>
</html:html>
