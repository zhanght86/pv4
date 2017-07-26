<%@ include file="inc/taglib.jsp" %>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*" %>
<%@page import="com.lcjr.pvxp.model.*" %>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="2" />

<%

  String[] devTypeDetail = (String[])request.getAttribute(Constants.REQUEST_DEVTP);
  String typeState		= devTypeDetail[1];
  String packageType	= devTypeDetail[5];
  devTypeDetail[1] 		= "pvxp.devtype.typestate." + devTypeDetail[1];
  devTypeDetail[5] 		= "pvxp.devtype.packtype." + devTypeDetail[5];
  int power						= new OperatorModel().getPower(0, request);
  boolean hasPower		= power==2 || power==3;
%>
<html:html locale="true">
  <head>
    <title><bean:message key="pvxp.devtype.detail"/></title>
    <link href="style/pvxp.css" rel="stylesheet" type="text/css">
  </head>

  <body onload="javascript:parent.hidit();">
    <table width="100%" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" valign="center" width="30" height="40"><img src="images/default/nav.gif"></td>
        <td><font color=blue>PowerView XP</font> --- <font class="location"><bean:message key="pvxp.devtype.detail"/></font></td>
      </tr>
    </table>

    <table width="100%" cellspacing="1" cellpadding="3" class="list_table_border">
      <!-- header -->
      <tr align="center">
        <td colspan="4" class="list_td_title"><b><bean:message key="pvxp.devtype.detail"/></b></td>
      </tr>
      <!-- body -->
      <tr align="center">
        <td width="15%" class="list_td_prom"><b><bean:message key="pvxp.devtype.typeno"/></b></td>
        <td width="35%" class="list_td_detail"><%=devTypeDetail[0]%></td>
        <td width="15%" class="list_td_prom"><b><bean:message key="pvxp.devtype.typestate"/></b></td>
        <td width="35%" class="list_td_detail"><bean:message key="<%=devTypeDetail[1]%>"/></td>
      </tr>
      <tr align="center">
        <td class="list_td_prom"><b><bean:message key="pvxp.devtype.devtype"/></b></td>
        <td class="list_td_detail"><%=devTypeDetail[2]%></td>
        <td class="list_td_prom"><b><bean:message key="pvxp.devtype.devname"/></b></td>
        <td class="list_td_detail"><%=devTypeDetail[3]%></td>
      </tr>
      <tr align="center">
        <td class="list_td_prom"><b><bean:message key="pvxp.devtype.devinfo"/></b></td>
        <td class="list_td_detail"><%=devTypeDetail[4]%></td>
        <td class="list_td_prom"><b><bean:message key="pvxp.devtype.packtype"/></b></td>
        <td class="list_td_detail"><bean:message key="<%=devTypeDetail[5]%>"/></td>
      </tr>
      <tr align="center">
        <td class="list_td_prom"><b><bean:message key="pvxp.devtype.devfactory"/></b></td>
        <td class="list_td_detail"><%=devTypeDetail[6]%></td>
        <td class="list_td_prom"><b></b></td>
        <td class="list_td_detail"></td>
      </tr>
    </table>

    <!-- footer -->
    <p>
    <html:form action="ModifyDevType.do" method="post">
    	<html:hidden property="modify" value="true"/>
    	<html:hidden property="devTypeNo" value="<%=devTypeDetail[0]%>"/>
    	<html:hidden property="devTypeState" value="<%=typeState%>"/>
    	<html:hidden property="devType" value="<%=devTypeDetail[2]%>"/>
    	<html:hidden property="devName" value="<%=devTypeDetail[3]%>"/>
    	<html:hidden property="devEquipt" value="<%=devTypeDetail[4]%>"/>
    	<html:hidden property="dataPackageType" value="<%=packageType%>"/>
    	<html:hidden property="devftno" value="<%=devTypeDetail[6]%>"/>
      <table align="center" width="40%">
        <tr align="center">
        <%if(hasPower){%>
          <td><input type="image" src="images/default/bt_modify.gif"></td>
        <%}%>
         </html:form>
        <%if(hasPower){%>
         <html:form action="/DevTpDel.do" method="post" onsubmit="return confirm('删除设备类型将删除所有该类型的设备，是否确定要删除设备类型？')">
         <html:hidden property="devtpArry" value="<%=devTypeDetail[0]%>"/>
          <td><input type="image" src="images/default/bt_delete.gif"></td>
         </html:form>
				<%}%>
          <td><img src="images/default/bt_back.gif" onClick="history.back()" style="cursor:hand"></td>
        </tr>
      </table>

  </body>
</html:html>
