<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />

<html:html locale="true">
<title></title>
<link href="style/pvxp.css" rel="stylesheet" type="text/css">

<script>
function chkin(){
	if( document.all.upfile.value=="" ){
		alert("请选择或输入文件名后上传");
		return false;
	}else{
		var iFsubmit = confirm("上传文件将覆盖已有同格式图片\n是否确定上传？");
		if( iFsubmit ){
			document.all.bankid.value=parent.document.all['bankidselect'].value;
			document.all.upform.action+="?bankid="+document.all.bankid.value;
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
		<tr id="upinput" align="left">
			<td valign="middle">
				<form id="upform" action="DevMapDoUpload.jsp" method="post"
					enctype="multipart/form-data" onsubmit="return chkin();">
					&nbsp;&nbsp;&nbsp;上传图片
					<input type="file" name="upfile" id="upfile" size="19"
						onFocusIn="cans=true;" onFocusOut="cans=false;">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" value="上传">
					（文件格式必须是jpg/png/gif中的一种）
					<input type="hidden" name="bankid" id="bankid">
				</form>
			</td>
		</tr>
		<tr id="uploading" style="display:none">
			<td valign="middle">
				正在上传...
			</td>
		</tr>
	</table>
</body>
</html:html>
