<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.ini.IniOperation"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="1" />

<%
	String card = (String) request.getParameter("card").trim();
	String cardType = (String) request.getParameter("cardType").trim();
	String subjectNo = (String) request.getParameter("subjectNo").trim();
	String remark = (String) request.getParameter("remark");
	if ("".equals(remark)) {
		remark = " ";
	}
	remark = PubUtil.native2unicode(remark);
	IniOperation ini = (IniOperation) session.getAttribute("ini");
	String key = (String) request.getParameter("key");
	
	String value = card + "|" + cardType + "|" + subjectNo + "|" + remark;
	StringBuffer all = ini.getAll();
	String allStr = all.substring(0, all.length());
	String message = "卡类型信息修改成功";
	ini.modify("CARDTYPE", key, value);
	try {
		ini.write();
	} catch (Exception e) {
		ini.setAll(new StringBuffer(allStr));
		message = "卡类型信息修改失败，请您在尝试一次";
	}
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
			onClick="parent.parent.parent.goUrl('iniCardType.jsp')"
			style="cursor:hand">
	</div>
</body>
</html:html>
