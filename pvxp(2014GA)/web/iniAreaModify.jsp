<%@include file="inc/taglib.jsp" %>
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*" %>
<%@page import="org.apache.log4j.*" %>
<%@page import="com.lcjr.pvxp.ini.IniOperation"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="1" />


<%
	String key = request.getParameter("key");
	IniOperation ini = (IniOperation)session.getAttribute("ini");
	String value = ini.getValue("AREANO",key);
	String[] array = value.split("\\|");
	Logger log = Logger.getLogger("com.lcjr.pvxp.iniFLModify");
	PubUtil myUtil = new PubUtil();
%>
<html:html locale="true">
  <head>
    <title><bean:message key="pvxp.dev.modify"/></title>
    <link href="style/pvxp.css" rel="stylesheet" type="text/css">
    <script language="JavaScript" src="js/pv.js"></script>
  </head>
<script type="text/javascript">
function pattern3(s){
		var patternStr = /^[A-Z]+$/;
		return patternStr.test(s);
	}
function check(){
	var areaNo = document.all['areaNo'].value;
	var areaName = document.all['areaName'].value;
	var subjectNo = document.all['subjectNo'].value;
	if(!isNumber(trim(areaNo))){
		alert("�������ӦΪ����������");
		document.all['areaNo'].focus();
		return false;
	} else if(trim(areaName)=""){
		alert("�������Ʋ���Ϊ��");
		document.all['areaName'].focus();
		return false;
	} else {
		return true;
	}
}
</script>
  <body onload="javascript:parent.hidit();">
  <form action="iniAreaModified.jsp" onsubmit="return check()">
  	<html:hidden property="key" value="<%=key%>"/>
    <table width="100%" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" valign="center" width="30" height="40"><img src="images/default/nav.gif"></td>
        <td><font color=blue>PowerView XP</font> --- <font class="location">�޸ĵ�����Ϣ</font></td>
      </tr>
    </table>

	
    <table width="100%" cellspacing="1" cellpadding="3" class="list_table_border">
      <!-- header -->
      <tr align="center">
        <td colspan="8" class="list_td_title"><b>�����������Ϣ</b></td>
      </tr>
      <!-- body -->
      <tr>
        <td width="20%" class="list_td_prom"><center><b>������ţ�</b></center></td>
        <td width="30%" class="list_td_detail">&nbsp;<input id="areaNo" type="text" name="areaNo" value="<%=array[0] %>" /></td>
        <td width="20%" class="list_td_prom"><center><b>��������</b></center></td>
        <td width="30%" class="list_td_detail">&nbsp;<input id="areaName" type="text" name="areaName" value="<%=myUtil.file2gb(array[1]) %>" /></td>
      </tr>
      <tr align="left">
        <td colspan="8" class="list_td_title"><font color="red">ע�⣺������ű���Ϊ���֣��������Ʋ���Ϊ�ա�</font></td>
      </tr>
    </table>

  	<table align="center" width="40%">
      <tr align="center">
        <td><input type="image" src="images/default/bt_ok.gif"></td>
        <td><img src="images/default/bt_reset.gif" onclick="reset()" style="cursor:hand"></td>
        <td><img src="images/default/bt_back.gif" onClick="history.back()" style="cursor:hand"></td>
      </tr>
    </table>
	</form>
  </body>
</html:html>
