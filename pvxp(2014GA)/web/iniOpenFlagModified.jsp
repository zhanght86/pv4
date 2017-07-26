<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.ini.IniOperation"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="1" />


<%
	String flag = (String) request.getParameter("flag").trim();
	IniOperation ini = (IniOperation) session.getAttribute("ini");
	String key = (String) request.getParameter("key").trim();
	
	String value = flag;
	StringBuffer all = ini.getAll();
	String allStr = all.substring(0, all.length());
	String message = "手续费启用标志修改成功";
	ini.modify("SXFFLAG", key, value);
	
	try {
		ini.write();
		//ini.reRead();
	} catch (Exception e) {
		ini.setAll(new StringBuffer(allStr));
		message = "手续费启用标志修改失败，请您再尝试一次";
	}
	//session.setAttribute("ini",ini);
	//session.invalidate();
%>
<html:html locale="true">
<head>
	<title><bean:message key="pvxp.dev.modify" />
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
				<%=message%>
			</td>
		</tr>
	</table>
	<br>
	<br>
	<div align="center">
		<img src="images/default/bt_back.gif"
			onClick="parent.parent.parent.goUrl('iniOpenFlag.jsp')"
			style="cursor:hand">
	</div>

</body>
</html:html>
