<%@ include file="inc/taglib.jsp" %>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="2" />

<html:html locale="true">
  <head> 
    <title><bean:message key="pvxp.updatetype.add"/></title>
    <link href="style/pvxp.css" rel="stylesheet" type="text/css">
    <script language="JavaScript">
    <!--
      function validate() {
        if (document.all.updateno.value == "") {
          alert("请输入更新类型编号。");
          document.all.updateno.focus();
          return false;
        }

        if (document.all.updateno.value.match(/\W/) != null) {
        	alert("更新类型编号只能包含字母和数字。");
        	document.all.updateno.focus();
        	return false;
        }

        if (document.all.updatename.value == "") {
        	alert("请输入更新名称。");
        	document.all.updatename.focus();
        	return false;
        }

        return true;
      }
    //-->
    </script>
  </head>
  <body onload="javascript:parent.hidit();">
    <html:form action="/AddUpdateType.do" focus="updateno" onsubmit="javascript:parent.showit();">
      <table width="100%" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left" valign="center" width="30" height="40"><img src="images/default/nav.gif"></td>
          <td><font color=blue>PowerView XP</font> --- <font class="location"><bean:message key="pvxp.updatetype.add"/></font></td>
        </tr>
      </table>

      <table width="100%" cellspacing="1" cellpadding="3" class="list_table_border">
        <!-- header -->
        <tr align="center">
          <td colspan="4" class="list_td_title"><b><bean:message key="pvxp.updatetype.add"/></b></td>
        </tr>
        <!-- body -->
        <tr>
          <td width="30%" class="list_td_prom" nowrap>&nbsp;<b><bean:message key="pvxp.updatetype.typeno"/></b></td>
          <td width="70%"class="list_td_detail">&nbsp;<html:text property="updateno" size="40" maxlength="10"/></td>
          
        </tr>
        <tr>
          <td class="list_td_prom" nowrap>&nbsp;<b><bean:message key="pvxp.updatetype.updatename"/></b></td>
          <td class="list_td_detail">&nbsp;<html:text size="40" property="updatename" maxlength="50"/></td>
        </tr>
       
        <tr>
          <td class="list_td_prom" nowrap>&nbsp;<b><bean:message key="pvxp.updatetype.updateinfo"/></b></td>
          <td class="list_td_detail">&nbsp;<html:text size="40" property="info" maxlength="200"/></td>
        </tr>
      </table>

      <!-- footer -->
      <p>
      <table align="center" width="40%">
        <tr align="center">
          <td><input type="image" src="images/default/bt_ok.gif" onClick="return validate()"></td>
          <td><img src="images/default/bt_reset.gif" onclick="reset()" style="cursor:hand"></td>
          <td><img src="images/default/bt_back.gif" onClick="history.back()" style="cursor:hand"></td>
        </tr>
      </table>
    </html:form>

  </body>
</html:html>
