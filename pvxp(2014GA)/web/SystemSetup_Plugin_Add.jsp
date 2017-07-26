<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="13" minpower="3" />


<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>
	<script language="JavaScript">
function setusersDisplay(){
	if( document.all.plugintype.value=='3' ){
		document.all.userslisttd.innerHTML='<b><bean:message key="pvxp.plugin.userslist"/></b>';
		document.all.userslist.style.visibility='visible';
	}else{
		document.all.userslisttd.innerHTML='&nbsp;';
		document.all.userslist.style.visibility='hidden';
	}
}
function checkAddPlugin(){
	if( document.all.plugid.value=="" ){
		alert("请填写插件ID");	
		document.all.plugid.focus();
		return false;
	}else{
		if(!isNumber(document.all.plugid.value)){
			alert("插件ID必须为数字");	
			document.all.plugid.focus();
			return false;	
		}
	}
	if( document.all.plugname.value=="" ){
		alert("请填写插件名称");	
		document.all.plugname.focus();
		return false;
	}
	if( document.all.firsturl.value=="" ){
		alert("请填写插件入口地址");	
		document.all.firsturl.focus();
		return false;
	}
	return true;
}
</script>

</head>
<%
	PubUtil myPubUtil = new PubUtil();
	String flag = (String) request.getAttribute(Constants.REQUEST_PLUGINADD_FLAG);
	if (flag == null)
		flag = "";
%>
<body onload="javascript:parent.hidit();">
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" valign="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP </font>---
				<font class="location"><bean:message
						key="pvxp.syssetup.title" />
				</font>---
				<font class="location"><bean:message
						key="pvxp.syssetup.plugin.add" />
				</font>
			</td>
		</tr>
	</table>
	<table align="center" width="100%" border="0" cellpadding="1"
		cellspacing="1" class="list_table_border">
		<tr align="center" class="list_td_title">
			<td>
				<b><bean:message key="pvxp.syssetup.plugin.add" />
					<bean:message key="pvxp.readme" />
				</b>
			</td>
		</tr>
		<tr align="left" class="list_tr0">
			<td>
				<bean:message key="pvxp.syssetup.plugin.add.readme" />
			</td>
		</tr>
	</table>
	<br>
	<center>
		<input type="radio" id="my_agree" name="agreement" value="true"
			onClick="javascript:document.all['add_table'].style.display='';document.all['result'].style.display='none';document.all.plugid.focus();">
		<bean:message key="pvxp.syssetup.plugin.add.agree" />
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" id="my_disagree" name="agreement" value="false"
			onClick="javascript:document.all['add_table'].style.display='none';document.all['result'].style.display='';parent.parent.systemPlugin()">
		<bean:message key="pvxp.syssetup.plugin.add.disagree" />
	</center>
	<br>
	<table id="add_table" style="display:none" align="center" width="100%"
		border="0" cellpadding="1" cellspacing="1" class="list_table_border">
		<tr align="center" class="list_td_title">
			<td colspan="4">
				<b><bean:message key="pvxp.syssetup.plugin.add" />
				</b>
			</td>
		</tr>

		<tr>
			<html:form action="/SystemPluginAdd.do"
				onsubmit="return checkAddPlugin();">
				<td class="list_td_prom" align="center" width="150">
					<b><bean:message key="pvxp.plugin.plugid" />
					</b>
				</td>
				<td class="list_tr0" width="300">
					&nbsp;&nbsp;
					<html:text property="plugid" styleId="plugid" maxlength="10"
						size="15" />
					&nbsp;&nbsp;
					<html:errors property="plugid" />
				</td>

				<td class="list_td_prom" align="center" width="150">
					<b><bean:message key="pvxp.plugin.plugname" />
					</b>
				</td>
				<td class="list_tr0">
					&nbsp;&nbsp;
					<html:text styleId="plugname" property="plugname" maxlength="10"
						size="15" />
				</td>
		</tr>

		<tr>
			<td class="list_td_prom" align="center" width="150">
				<b><bean:message key="pvxp.plugin.firsturl" />
				</b>
			</td>
			<td class="list_tr0">
				&nbsp;&nbsp;
				<html:text styleId="firsturl" property="firsturl" maxlength="30"
					size="15" />
			</td>

			<td class="list_td_prom" align="center" width="150">
				<b><bean:message key="pvxp.plugin.plugintype" />
				</b>
			</td>
			<td class="list_tr0">
				&nbsp;&nbsp;
				<html:select property="plugintype" styleId="plugintype"
					onchange="setusersDisplay()">
					<html:option value="2">
						<bean:message key="pvxp.plugin.plugintype.2" />
					</html:option>
					<html:option value="1">
						<bean:message key="pvxp.plugin.plugintype.1" />
					</html:option>
					<html:option value="3">
						<bean:message key="pvxp.plugin.plugintype.3" />
					</html:option>
				</html:select>
			</td>
		</tr>

		<tr>
			<td class="list_td_prom" align="center" width="150">
				<b><bean:message key="pvxp.plugin.info" />
				</b>
			</td>
			<td class="list_tr0">
				&nbsp;&nbsp;
				<html:textarea property="info" styleId="info" rows="5"
					style="width:230" />
			</td>
			<td id="userslisttd" class="list_td_prom" align="center" width="150">
				&nbsp;
			</td>
			<td class="list_tr0">
				&nbsp;&nbsp;
				<html:textarea property="userslist" styleId="userslist" rows="5"
					style="width:230;visibility:hidden" />
			</td>
		</tr>

		<tr>
			<td align="center" colspan="4" height="30">
				<input type="image" src="images/default/bt_add.gif"
					onFocus="this.blur()">
			</td>
		</tr>
	</table>

	</html:form>
	<div id="result">
		<center>
			<html:errors property="plugaddresult" />
		</center>
	</div>
	<script>
<%
	if(!flag.equals("")){
%>

<%
		if(flag.equals("1")){
%>
document.all.my_agree.click();
<%
		}
		if(flag.equals("0")){
%>


<%
		}
	}
%>
</script>
</body>
</html:html>
