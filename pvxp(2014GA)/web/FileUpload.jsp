<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="3" minpower="2" />

<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">

	<script>
function chkin(){
	var filename = document.all.upfile.value;
	if( filename == "" ){
		alert('<bean:message key="pvxp.filedistribute.notice.nofile"/>');
		return false;
	}else{
		var a = filename.lastIndexOf( "\\" );
		var filename = filename.substring( a+1 );
		var dot = filename.split( "." );
		var re = /\w/;
		if ( dot.length > 2 ) {
			alert ( "请使用文件名格式为*.*的文件！" );
			return false;
		}else if ( dot.length == 2 ) {
			var b = filename.lastIndexOf( "." );
			var fnm = filename.substring( 0, b );
			var ext = filename.substring( b+1 );

			var fnmre = re.exec( fnm );
			var extre = re.exec( ext );
		}else if ( dot.length == 1 ) {
			var fnmre = re.exec( dot );
			var extre = "";
		}

		if ( fnmre==null || extre==null ) {
			alert ( "请不要使用非英文数字文件名！" );
			return false;
		}

		var iFsubmit = confirm('<bean:message key="pvxp.filedistribute.notice.overwrite"/>');
		if( iFsubmit ){
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

<body leftmargin="0" topmargin="0" onselectstart="return mselect();"
	class="list_td_detail">
	<table border="0">
		<tr id="upinput" align="left">
			<td valign="middle">
				<form id="upform" action="FileDoUpload.jsp" method="post"
					enctype="multipart/form-data" onsubmit="return chkin();">
					<input type="file" name="upfile" id="upfile" size="20"
						onkeydown="this.blur();">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="image" src="images/default/bt_uploadfile.gif"
						onFocus="this.blur()"
						value='<bean:message key="pvxp.filedistribute.uploadbt"/>'
						align="absmiddle">
					<b><bean:message key="pvxp.filedistribute.notice" /> </b>
				</form>
			</td>
		</tr>
		<tr id="uploading" style="display:none">
			<td valign="middle">
				<bean:message key="pvxp.filedistribute.uploading" />
			</td>
		</tr>
	</table>
</body>
</html:html>
