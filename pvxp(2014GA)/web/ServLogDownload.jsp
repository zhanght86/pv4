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
	//��֤·����ȷ��
      var filepath = document.all.filepath.value;
      	if (filepath == null||filepath.length == 0) {
          alert("��������־���ھ���·��..");
          document.all.filepath.focus();
          return false;
        }
      	if ( filepath.charAt(filepath.length-1) != "\\" && filepath.charAt(filepath.length-1) != "/") {
          alert("��������־���ھ���·��..");
          document.all.filepath.focus();
          return false;
      	}

      //��֤�ļ�������ȷ��
     var filename = document.all.filename.value;
        if ( filename == null || filename.length==0) {
          alert("��������־����..");
          document.all.filename.focus();
          return false;
        }else{
			var dot = filename.split(".");
			if ( dot.length < 2 ) {
				alert ( "�������ļ�����ʽΪ*.*���ļ�.." );
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
						onClick="javascript:parent.parent.goUrl('FileUpload1.jsp');">�ļ��ϴ�</a>&nbsp;&nbsp;
					<a href="#"
						onClick="javascript:parent.parent.goUrl('FileState.jsp');">Ŀ¼���ݲ�ѯ</a>&nbsp;&nbsp;
					<a href="#"
						onClick="javascript:parent.parent.goUrl('DevReboot.jsp');">�ն������ػ�ʱ���޸�</a>&nbsp;&nbsp;
					<a href="#"
						onClick="javascript:parent.parent.goUrl('DevRemote.jsp');">�ն������ػ���־�޸�</a>
					<font class="location">�������ļ�����</font>&nbsp;&nbsp;
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
					<b>�ļ����ڷ�������·��</b>
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
