<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="11" minpower="1" />


<html:html locale="true">
<head>
	<title>�豸�����������ͳ��</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">

		<br>
		<br>
		<br>
		<table width="100%" cellspacing="1" cellpadding="2"
			class="list_table_border">
			<tr>
				<td class="list_td_title">
					<b>��ʾ</b>
				</td>
			</tr>
			<tr class="list_tr0">
				<td align="center">
					����ͳ�����ں�̨����������鿴
					<a href="#"
						onClick="javascript:parent.parent.goUrl('ReportFormList.do?statesort=02')"><font
						color="blue">�����б�</font> </a> ��
				</td>
			</tr>
		</table>

	</div>
</body>
</html:html>
