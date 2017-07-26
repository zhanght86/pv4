<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="6" minpower="2" />

<html:html locale="true">
<head>
	<title>自动报表任务</title>
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
					<b>提示</b>
				</td>
			</tr>
			<tr class="list_tr0">
				<td align="center">
					自动报表任务添加成功，详情请查看
					<a href="#"
						onClick="javascript:parent.parent.goUrl('STATAReportList.do')"><font
						color="blue">报表列表</font> </a> 。
				</td>
			</tr>
		</table>

	</div>
</body>
</html:html>
