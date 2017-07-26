<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="3" minpower="2" />

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.filedistribute.title" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
    function validate(){
	//验证路径正确性
      var filepath = document.all.filepath.value;
      	if (filepath == null||filepath.length == 0) {
          alert("请输入日志所在绝对路径..");
          document.all.filepath.focus();
          return false;
        }
      	if ( filepath.charAt(filepath.length-1) != "\\" && filepath.charAt(filepath.length-1) != "/") {
          alert("请完善日志所在绝对路径..");
          document.all.filepath.focus();
          return false;
      	}

      //验证文件名称正确性
     var filename = document.all.filename.value;
        if ( filename == null || filename.length==0) {
          alert("请输入日志名称..");
          document.all.filename.focus();
          return false;
        }else{
			var dot = filename.split(".");
			if ( dot.length < 2 ) {
				alert ( "请输入文件名格式为*.*的文件.." );
				return false;
		    }
	    }
     	return true;
      }
  </script>
</head>
<body onload="javascript:parent.hidit();">
	<form id="upform" action="ServLDResult.jsp" method="post">

		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<a href="#"
						onClick="javascript:parent.parent.goUrl('Sys_Manage_RemoteControl.jsp');"><bean:message
							key="pvxp.remotecontrol.title" /> </a>&nbsp;&nbsp;
					<a href="#"
						onClick="javascript:parent.parent.goUrl('Sys_Manage_FileDistribute.jsp');"><bean:message
							key="pvxp.filedistribute.title" /> </a>&nbsp;&nbsp;
					<a href="#"
						onClick="javascript:parent.parent.goUrl('Sys_Manage_FileUpload.jsp');"><bean:message
							key="pvxp.fileupload.title" /> </a>&nbsp;&nbsp;
					<a href="#"
						onClick="javascript:parent.parent.goUrl('FileUpload1.jsp');">文件上传</a>&nbsp;&nbsp;
					<a href="#"
						onClick="javascript:parent.parent.goUrl('FileState.jsp');">目录内容查询</a>&nbsp;&nbsp;
					<a href="#"
						onClick="javascript:parent.parent.goUrl('DevReboot.jsp');">终端重启关机时间修改</a>&nbsp;&nbsp;
					<a href="#"
						onClick="javascript:parent.parent.goUrl('DevRemote.jsp');">终端重启关机标志修改</a>
					<font class="location">服务器文件下载</font>&nbsp;&nbsp;
				</td>
			</tr>
		</table>


		<table width="100%" cellspacing="1" cellpadding="2"
			class="list_table_border">

			<tr>
				<td class="list_td_title" align="center" colspan="5">
					<b>文&nbsp;&nbsp;件&nbsp;&nbsp;下&nbsp;&nbsp;载</b>
				</td>
			</tr>

			<tr>
				<td class="list_td_prom" width="20%" align="center">
					<b>文件所在服务器的路径</b>
				</td>
				<td class="list_td_detail" colspan="4">
					<input type="text" name="filepath" id="filepath" size="50">
					<b>&nbsp;&nbsp;(服务器绝对路径,Linux系统在末尾加“/”，windows系统在末尾加“\”)</b>
				</td>
			</tr>

			<tr>
				<td class="list_td_prom" width="20%" align="center">
					<b>文&nbsp;件&nbsp;名</b>
				</td>
				<td class="list_td_detail" colspan="4">
					<input type="text" name="filename" id="filename" size="35">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		<p></p>
		<table align="center">
			<tr>
				<td id="delbt" align="center">
					<input type="image" src="images/default/bt_upfile.gif"
						value='<bean:message key="pvxp.filedistribute.uploadbt"/>'
						align="absmiddle" onclick="return validate()">
				</td>
			</tr>
		</table>

	</form>
</body>
</html:html>
