<%@ include file="inc/taglib.jsp" %>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*" %>
<%@page import="com.lcjr.pvxp.util.*" %>
<%@page import="com.lcjr.pvxp.orm.util.*" %>
<%@page import="com.lcjr.pvxp.orm.*" %>
<%@page import="com.lcjr.pvxp.model.*" %>

<html:html locale="true">
<head>
  <title>设备交易情况明细表</title>
  <link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>
<body onload="javascript:parent.hidit();">
<div align="center">

		<br><br><br>
		<table width="100%" cellspacing="1" cellpadding="2" class="list_table_border">
			<tr>
				<td class="list_td_title"><b>提示</b></td>
			</tr>
			<tr class="list_tr0">
				<td align="center">报表统计正在后台处理，详情请查看 <a href="#" onClick="javascript:parent.parent.goUrl('ReportFormList.do?statesort=21')"><font color="blue">报表列表</font></a> 。</td>
			</tr>
		</table>

<p></p>


</div>
</body>
</html:html>