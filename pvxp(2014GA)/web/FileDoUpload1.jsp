<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="3" minpower="2" />


<html>

	<head>
		<title><bean:message key="pvxp.dev.add" />
		</title>
		<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	</head>

	<body onload="javascript:parent.hidit();">

		<br>
		<br>
		<br>
		<table width="100%" cellspacing="1" cellpadding="2"
			class="list_table_border">
			<tr>
				<td class="list_td_title">
					<b>提示</b>
				</td>
			</tr>
			<tr class="list_tr0">
				<td align="center">
					上传成功
				</td>
			</tr>
		</table>
		<br>
		<br>
		<div align="center">
			<img src="images/default/bt_back.gif"
				onClick="javascript:parent.parent.goUrl('FileUpload1.jsp')"
				style="cursor:hand">
		</div>
	</body>
</html>
