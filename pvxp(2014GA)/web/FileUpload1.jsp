<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="3" minpower="2" />

<%@page import="com.lcjr.pvxp.util.*"%>

<%
	PubUtil myPubUtil = new PubUtil();
	String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
%>

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.filedistribute.title" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
    function validate() {
    //验证文件的正确性
    	var filename = document.all.upfile.value;
        if ( filename == null || filename.length==0) {
          alert("请选择文件后上传！");
          document.all.upfile.focus();
          return false;
        }else{
		var a = filename.lastIndexOf( "\\" );
		var filename = filename.substring( a+1 );
		var dot = filename.split( "." );
		if ( dot.length < 2 ) {
			alert ( "请使用文件名格式为*.*的文件！");
			return false;
		}
	}

	//验证文件路径的正确性
      	var filepath = document.all.filepath.value;
      	if (filepath == null||filepath.length == 0){
          alert("请输入文件上传后的保存路径！");
          document.all.filepath.focus();
          return false;
        }
        
      	if ( filepath.charAt(filepath.length-1) != "/" && filepath.charAt(filepath.length-1) != "\\") {
          alert("请完善文件路径，Linux系统在末尾加“/”，windows系统在末尾加“\\”");
          document.all.filepath.focus();
          return false;
      	}
     	return true;
      }
  </script>
</head>
<body onload="javascript:parent.hidit();">
	<form id="upform" action="Filedoupload2.jsp" method="post"
		enctype="multipart/form-data" onsubmit="javascript:parent.showit();">

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
					<font class="location">文件上传</font>&nbsp;&nbsp;
					<a href="#"
						onClick="javascript:parent.parent.goUrl('FileState.jsp');">目录内容查询</a>&nbsp;&nbsp;
					<a href="#"
						onClick="javascript:parent.parent.goUrl('DevReboot.jsp');">终端重启关机时间修改</a>&nbsp;&nbsp;
					<a href="#"
						onClick="javascript:parent.parent.goUrl('DevRemote.jsp');">终端重启关机标志修改</a>
					<%
					if (authlist.equals("*")) {
					%>
					<a href="#"
						onClick="javascript:parent.parent.goUrl('ServLogDownload.jsp');">服务器文件下载</a>
					<%
					}
					%>
				</td>
			</tr>
		</table>

		<table width="100%" cellspacing="1" cellpadding="2"
			class="list_table_border">

			<tr>
				<td class="list_td_title" align="center" colspan="5">
					<b>文&nbsp;&nbsp;件&nbsp;&nbsp;上&nbsp;&nbsp;传</b>
				</td>
			</tr>

			<tr>
				<td class="list_td_prom" width="20%" align="center">
					<b>保存路径</b>
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
					<input type="file" name="upfile" id="upfile" size="20"
						onkeydown="this.blur();">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		<p></p>
		<table align="center">
			<tr>
				<td id="delbt" align="center">
					<input type="image" src="images/default/bt_uploadfile.gif"
						onFocus="this.blur()"
						value='<bean:message key="pvxp.filedistribute.uploadbt"/>'
						align="absmiddle" onclick="return validate()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html:html>
