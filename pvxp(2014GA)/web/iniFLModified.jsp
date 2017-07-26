<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.ini.IniOperation"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="1" />


<%
	//String message = (String)request.getAttribute(Constants.REQUEST_MODIFYDEV_DONE);
	String value1 = (String) request.getParameter("value1").trim();
	String value2 = (String) request.getParameter("value2").trim();
	String value3 = (String) request.getParameter("value3").trim();
	String value4 = (String) request.getParameter("value4").trim();
	String value5 = (String) request.getParameter("value5").trim();
	String value6 = (String) request.getParameter("value6").trim();
	IniOperation ini = (IniOperation) session.getAttribute("ini");
	String key = (String) request.getParameter("key");
	String remark = (String) request.getParameter("remark");
	remark = PubUtil.native2unicode(remark);
	//System.out.println(key);
	String value = value1 + "|" + value2 + "|" + value3 + "|" + value4 + "|" + value5 + "|" + value6 + "|" + remark;
	StringBuffer all = ini.getAll();
	String allStr = all.substring(0, all.length());
	ini.modify("FL", key, value);
	String message = remark + "修改完成";
	try {
		ini.write();
		//ini.reRead();
	} catch (Exception e) {
		ini.setAll(new StringBuffer(allStr));
		message = remark + "修改失败，请您再尝试一次";
	}
	
	
	//session.invalidate();
%>
<html:html locale="true">

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
				<%=message%>
			</td>
		</tr>
	</table>
	<br>
	<br>
	<div align="center">
		<img src="images/default/bt_back.gif"
			onClick="parent.parent.parent.goUrl('iniFL.jsp')" style="cursor:hand">
	</div>

</body>
<!-- <head>
    <title><bean:message key="pvxp.dev.modify"/></title>
    <link href="style/pvxp.css" rel="stylesheet" type="text/css">
  </head>

  <body onload="javascript:parent.hidit();">
    <table width="100%" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" valign="center" width="30" height="40"><img src="images/default/nav.gif"></td>
        <td><font color=blue>PowerView XP</font> --- <font class="location">转账费率修改</font></td>
      </tr>
    </table>
	  <center><h2><%=remark%>修改完成</h2><center>
	  <center><img src="images/default/bt_back.gif" onclick="parent.parent.goUrl('iniFL.jsp')" style="cursor:hand"></center>
  </body> -->
</html:html>
