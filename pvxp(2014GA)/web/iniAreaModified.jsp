<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.ini.IniOperation"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="1" />

<%
	//String message = (String)request.getAttribute(Constants.REQUEST_MODIFYDEV_DONE);
	String areaNo = (String) request.getParameter("areaNo").trim();
	String areaName = (String) request.getParameter("areaName").trim();
	areaName = PubUtil.native2unicode(areaName);
	IniOperation ini = (IniOperation) session.getAttribute("ini");
	String key = (String) request.getParameter("key");
	//System.out.println("+"+key+"+");
	String value = areaNo + "|" + areaName;
	StringBuffer all = ini.getAll();
	String allStr = all.substring(0, all.length());
	ini.remove("AREANO", key);
	String message = "地区信息修改成功";
	if (ini.getValue("AREANO", areaNo) == null) {
		ini.add("AREANO", areaNo, value);
		//System.out.println(ini.getAll());
		try {
			ini.write();
			//ini.reRead();
		} catch (Exception e) {
			ini.setAll(new StringBuffer(allStr));
			message = "地区信息修改失败，请您再尝试一次";
		}
	} else {
		ini.setAll(new StringBuffer(allStr));
		message = "地区信息修改失败，该地区号已被占用";
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
			onClick="parent.parent.parent.goUrl('iniArea.jsp')"
			style="cursor:hand">
	</div>

</body>
<!--    <body onload="javascript:parent.hidit();">
    <table width="100%" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" valign="center" width="30" height="40"><img src="images/default/nav.gif"></td>
        <td><font color=blue>PowerView XP</font> --- <font class="location">地区信息修改</font></td>
      </tr>
    </table>
	  <center><h2><%=message%></h2><center>
	  <center><img src="images/default/bt_back.gif" onclick="parent.parent.goUrl('iniSpecialCard.jsp')" style="cursor:hand"></center>
  </body>-->
</html:html>
