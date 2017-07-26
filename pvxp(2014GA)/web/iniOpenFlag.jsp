<%@include file="inc/taglib.jsp"%>
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.ini.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="1" />

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.bankinfo.list" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script>
	function deal(obj){
		if(obj.checked=="checked"||obj.checked==true){
			document.all['flag'].disabled=false;
			document.all.button.style.display="";
		} else {
			document.all['flag'].disabled=true;
			document.all.button.style.display="none";
		}
	}
	
	function reSet(){
		document.all.form.reset();
		document.all['flag'].disabled=true;
		document.all.button.style.display="none";
	}
</script>
	<script language="JavaScript" src="js/pv.js"></script>
</head>
<body onload="javascript:parent.hidit();">
	<div id="show" style="position:absolute; width="
		100%" z-index:1; left: 0px; top: 0px;" align="center">
		<%
			PubUtil util = new PubUtil();
			String path = util.ReadConfig("iniPath", "path", "", "PowerView.ini");
			//System.out.println(path);
			//#������=������|˵��
			IniOperation ini = (IniOperation) session.getAttribute("ini");
			if (ini == null) {
				ini = new IniOperation(path);
				session.setAttribute("ini", ini);
			}
			List list = ini.getValue("SXFFLAG");
			String str = (String) list.get(0);
			String[] flag = str.split("=");
		%>
		<form action="iniOpenFlagModified.jsp" onsubmit="" id="form">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" align="center" width="30" height="40">
						<img src="images/default/nav.gif">
					</td>
					<td>
						<font color=blue>PowerView XP </font>---
						<font class="location">ת��������ά��</font>
					</td>
				</tr>
			</table>
			<html:hidden property="key" value="<%=flag[0]%>" />
			<table cellspacing="1" cellpadding="3" class="list_table_border"
				width="100%">
				<tr>
					<td colspan="2" align="center" class="list_td_title">
						<b>����������ά��</b>
					</td>
				</tr>
				<tr align="left">
					<td align="center" width="25%" class="list_td_prom">
						<b>ת������������״̬</b>
					</td>
					<td class="list_td_detail">
						&nbsp;
						<select name="flag" id="flag"
							onMouseWheel="javascript:return false;" disabled="disabled">
							<%
							if ("1".equals(flag[1])) {
							%>
							<option value="1" selected="selected">
								����
							</option>
							<option value="0">
								������
							</option>
							<%
							} else {
							%>
							<option value="1">
								����
							</option>
							<option value="0" selected="selected">
								������
							</option>
							<%
							}
							%>
						</select>
						&nbsp;&nbsp;
						<input name="checkbox" type="checkbox" onclick="deal(this)" />
						�޸�״̬&nbsp;
					</td>
				</tr>
			</table>
			<br>
			<table id="button" align="center" width="30%" style="display:none">
				<tr align="center">
					<td>
						<input type="image" src="images/default/bt_ok.gif" onclick="">
					</td>
					<td>
						<img src="images/default/bt_reset.gif" onclick="reSet()"
							style="cursor:hand">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script>
if( document.all['gopage'] )
document.all['gopage'].focus();
</script>
</body>
</html:html>
