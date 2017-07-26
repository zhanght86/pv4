<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%@ page import="java.io.*"%>
<%@ page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie />
<app:checkpower funcid="0" minpower="1" />


<html>

<head>
<title><bean:message key="pvxp.dev.list" />
</title>
<link href="style/pvxp.css" rel="stylesheet" type="text/css">
<script src="js/pv.js"></script>
</head>


<body onload="javascript:parent.hidit();">

	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" align="center" width="30" height="40"><img
				src="images/default/nav.gif">
			</td>
			<td><font color=blue>PowerView XP </font>--- <font
				class="location"><bean:message
						key="pvxp.application.welcome.title" /> </font>
			</td>
		</tr>
	</table>
	<table align="center" width="100%" border="0" cellpadding="1"
		cellspacing="1" class="list_table_border">
		<tr align="center" class="list_td_title">
			<td><b><bean:message
						key="pvxp.application.welcome.main.title" /> </b>
			</td>
		</tr>
		<tr align="left" class="list_tr0">
			<td><bean:message key="pvxp.application.welcome.main.readme" />
			</td>
		</tr>
	</table>

	<%
		String fname = "设备信息导出文件";
		OutputStream os = response.getOutputStream();
		response.reset();

		//下面是对中文文件名的处理
		response.setCharacterEncoding("UTF-8");
		fname = java.net.URLEncoder.encode(fname, "UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(fname.getBytes("UTF-8"), "GBK") + ".xls");
		response.setContentType("application/msexcel");
		DevinfoExcelWrite sw = new DevinfoExcelWrite();
		sw.createExcel(os);
	%>
</body>
</html>