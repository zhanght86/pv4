<%@ include file="inc/taglib.jsp" %>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*" %>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="2"/>

<html:html locale="true">
<head>
  <title><bean:message key="pvxp.bankinfo.delete"/></title>
  <link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>
<body onload="javascript:parent.hidit();">
<div align="center">
<% 
	String[] bankinfoArry = request.getParameterValues("bankinfoArry");
	String[] result = (String[])request.getAttribute(Constants.REQUEST_BANKINFO_DEL_DONE);
	if( bankinfoArry==null||result==null||bankinfoArry.length!=result.length ){
%>
System errors...
<%
	}else{
%>
<br><br><br>
<table width="75%" cellspacing="1" cellpadding="3" class="list_table_border">
  <tr align="center">
    <td class="list_td_title"><bean:message key="pvxp.bankinfo.bankid"/></td>
    <td class="list_td_title"><bean:message key="pvxp.bankinfo.delete"/></td>
  </tr>
<%		for(int i =0;i<bankinfoArry.length;i++){
			String tmpmsgstr = "pvxp.bankinfo.del."+result[i];
			if ( !result[i].equals("0") ){
%>
  <tr align="center" class="list_tr<%=i%2%>">
    <td><font color="red"><%=bankinfoArry[i]%></font></td>
    <td><font color="red"><bean:message key="<%=tmpmsgstr%>"/></font></td>
  </tr>
<%
			}else{
%>
  <tr align="center" class="list_tr<%=i%2%>">
    <td><%=bankinfoArry[i]%></td>
    <td><bean:message key="<%=tmpmsgstr%>"/></td>
  </tr>
<%
			}
		}
%>
</table>
<%
	}
%>
<p></p>
<table>
  <tr>
    <td id="delbt">
			<a href="javascript:parent.parent.getBankinfoList('1');" onFocus="this.blur()">
				<img src="images/default/bt_back.gif" border="0">
			</a>
    </td>
  </tr>
</table>

</div>
</body>
</html:html>