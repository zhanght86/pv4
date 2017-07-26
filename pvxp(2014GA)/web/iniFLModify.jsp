<%@ include file="inc/taglib.jsp" %>
<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="com.lcjr.pvxp.util.*" %>
<%@ page import="org.apache.log4j.*" %>
<%@page import="com.lcjr.pvxp.ini.IniOperation"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="1" />


<%
	String key = request.getParameter("key");
	IniOperation ini = (IniOperation)session.getAttribute("ini");
	String value = ini.getValue("FL",key);
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
function pattern1(s){
		var patternStr = /^[0-9]+\.[0-9]{2}$/;
		//var pattern = /^[A-Z]+$/;
		return patternStr.test(s);
	}
function pattern2(s){
		var patternStr = /^[0-9]+\.[0-9]{3}$/;
		//var pattern = /^[A-Z]+$/;
		return patternStr.test(s);
	}
function check(){
	var value1 = document.all['value1'].value;
	var value2 = document.all['value2'].value;
	var value3 = document.all['value3'].value;
	var value4 = document.all['value4'].value;
	var value5 = document.all['value5'].value;
	var value6 = document.all['value6'].value;
	if(!pattern2(trim(value1))){
		alert("请您输入正确格式的转出手续费费率，填写格式为\"0.005\"");
		document.all['value1'].focus();
		return false;
	} else if(!pattern1(trim(value2))){
		alert("请您输入正确格式的转出手续费下限，填写格式为\"1.00\"");
		document.all['value2'].focus();
		return false;
	} else if(!pattern1(trim(value3))){
		alert("请您输入正确格式的转出手续费上限，填写格式为\"1.00\"");
		document.all['value3'].focus();
		return false;
	} else if(!pattern2(trim(value4))){
		alert("请您输入正确格式的转入手续费费率，填写格式为\"0.005\"");
		document.all['value4'].focus();
		return false;
	} else if(!pattern1(trim(value5))){
		alert("请您输入正确格式的转入手续费下限，填写格式为\"1.00\"");
		document.all['value5'].focus();
		return false;
	} else if(!pattern1(trim(value6))){
		alert("请您输入正确格式的转入手续费上限，填写格式为\"1.00\"");
		document.all['value6'].focus();
		return false;
	} else {
		return true;
	}
}
</script>
  <body onload="javascript:parent.hidit();">
  <form action="iniFLModified.jsp" onsubmit="javascript:return check()">
  	<html:hidden property="key" value="<%=key%>"/>
  	<html:hidden property="remark" value="<%=myUtil.file2gb(array[6])%>"/><!-- 中文说明 -->
    <table width="100%" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" valign="center" width="30" height="40"><img src="images/default/nav.gif"></td>
        <td><font color=blue>PowerView XP</font> --- <font class="location">转账费率修改</font></td>
      </tr>
    </table>

	
    <table width="100%" cellspacing="1" cellpadding="3" class="list_table_border">
      <!-- header -->
      <tr align="center">
        <td colspan="8" class="list_td_title"><b><%=myUtil.file2gb(array[6]) %></b></td>
      </tr>
      <!-- body -->
      <tr>
        <td width="15%" class="list_td_prom"><center><b>转出手续费费率</b></center></td>
        <td width="18%" class="list_td_detail">&nbsp;<input id="value1" type="text" name="value1" value=<%=array[0] %> /></td>
        <td width="15%" class="list_td_prom"><center><b>转出手续费下限</b></center></td>
        <td width="18%" class="list_td_detail">&nbsp;<input id="value2" type="text" name="value2" value=<%=array[1] %> /></td>
        <td width="15%" class="list_td_prom"><center><b>转出手续费上限</b></center></td>
        <td width="19%" class="list_td_detail">&nbsp;<input id="value3" type="text" name="value3" value=<%=array[2] %> /></td>
      </tr>
       <tr>
        <td width="8%" class="list_td_prom"><center><b>转入手续费费率</b></center></td>
        <td width="17%" class="list_td_detail">&nbsp;<input id="value4" type="text" name="value4" value=<%=array[3] %> /></td>
        <td width="8%" class="list_td_prom"><center><b>转入手续费下限</b></center></td>
        <td width="17%" class="list_td_detail">&nbsp;<input id="value5" type="text" name="value5" value=<%=array[4] %> /></td>
        <td width="8%" class="list_td_prom"><center><b>转入手续费上限</b></center></td>
        <td width="17%" class="list_td_detail">&nbsp;<input id="value6" type="text" name="value6" value=<%=array[5] %> /></td>
      </tr>
      <tr align="left">
        <td colspan="8" class="list_td_title"><font color="red">注意：费率的输入格式为"0.005"，手续费的输入格式为"1.00"，请您正确填写</font></td>
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
