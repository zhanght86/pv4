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
		alert("��ѡ��������ļ������ϴ�");
		return false;
	}else{
		var iFsubmit = confirm("�ϴ��ļ�����������ͬ��ʽͼƬ\n�Ƿ�ȷ���ϴ���");
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
					&nbsp;&nbsp;&nbsp;�ϴ�ͼƬ
					<input type="file" name="upfile" id="upfile" size="19"
						onFocusIn="cans=true;" onFocusOut="cans=false;">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" value="�ϴ�">
					���ļ���ʽ������jpg/png/gif�е�һ�֣�
					<input type="hidden" name="bankid" id="bankid">
				</form>
			</td>
		</tr>
		<tr id="uploading" style="display:none">
			<td valign="middle">
				�����ϴ�...
			</td>
		</tr>
	</table>
</body>
</html:html>
