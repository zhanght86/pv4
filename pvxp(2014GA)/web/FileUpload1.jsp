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
    //��֤�ļ�����ȷ��
    	var filename = document.all.upfile.value;
        if ( filename == null || filename.length==0) {
          alert("��ѡ���ļ����ϴ���");
          document.all.upfile.focus();
          return false;
        }else{
		var a = filename.lastIndexOf( "\\" );
		var filename = filename.substring( a+1 );
		var dot = filename.split( "." );
		if ( dot.length < 2 ) {
			alert ( "��ʹ���ļ�����ʽΪ*.*���ļ���");
			return false;
		}
	}

	//��֤�ļ�·������ȷ��
      	var filepath = document.all.filepath.value;
      	if (filepath == null||filepath.length == 0){
          alert("�������ļ��ϴ���ı���·����");
          document.all.filepath.focus();
          return false;
        }
        
      	if ( filepath.charAt(filepath.length-1) != "/" && filepath.charAt(filepath.length-1) != "\\") {
          alert("�������ļ�·����Linuxϵͳ��ĩβ�ӡ�/����windowsϵͳ��ĩβ�ӡ�\\��");
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
					<font class="location">�ļ��ϴ�</font>&nbsp;&nbsp;
					<a href="#"
						onClick="javascript:parent.parent.goUrl('FileState.jsp');">Ŀ¼���ݲ�ѯ</a>&nbsp;&nbsp;
					<a href="#"
						onClick="javascript:parent.parent.goUrl('DevReboot.jsp');">�ն������ػ�ʱ���޸�</a>&nbsp;&nbsp;
					<a href="#"
						onClick="javascript:parent.parent.goUrl('DevRemote.jsp');">�ն������ػ���־�޸�</a>
					<%
					if (authlist.equals("*")) {
					%>
					<a href="#"
						onClick="javascript:parent.parent.goUrl('ServLogDownload.jsp');">�������ļ�����</a>
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
					<b>��&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;��</b>
				</td>
			</tr>

			<tr>
				<td class="list_td_prom" width="20%" align="center">
					<b>����·��</b>
				</td>
				<td class="list_td_detail" colspan="4">
					<input type="text" name="filepath" id="filepath" size="50">
					<b>&nbsp;&nbsp;(����������·��,Linuxϵͳ��ĩβ�ӡ�/����windowsϵͳ��ĩβ�ӡ�\��)</b>
				</td>
			</tr>

			<tr>
				<td class="list_td_prom" width="20%" align="center">
					<b>��&nbsp;��&nbsp;��</b>
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
