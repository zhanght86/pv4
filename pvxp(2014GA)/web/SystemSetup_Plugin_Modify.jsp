<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>


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
function checkModifyPlugin(){
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
	String plugid = request.getParameter("plugid");
	PluginModel myPluginModel = new PluginModel();
	Plugin tmp = myPluginModel.getPlugin(plugid);
%>
<body onload="javascript:parent.hidit();">
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" align="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP </font>---
				<font class="location"><bean:message
						key="pvxp.syssetup.title" /> </font>---
				<font class="location"><bean:message
						key="pvxp.syssetup.plugin.modify" /> </font>
			</td>
		</tr>
	</table>
	<%
			if (tmp != null) {
			CharSet myCharSet = new CharSet();
			String userslist = myPubUtil.dealNull(tmp.getUserslist());
			String[] usersArray = myPubUtil.split(userslist, ",");
			String tmpuserslist = "";
			for (int i = 0; i < usersArray.length; i++) {
				String tmpstr = usersArray[i].trim();
				if (tmpstr.length() > 0)
			tmpuserslist += (tmpstr + "\n");
			}
	%>
	<table id="modify_table" align="center" width="100%" border="0"
		cellpadding="1" cellspacing="1" class="list_table_border">
		<tr align="center" class="list_td_title">
			<td colspan="4">
				<b><bean:message key="pvxp.syssetup.plugin.modify" /> </b>
			</td>
		</tr>
		<tr>
			<html:form action="/SystemPluginModify.do"
				onsubmit="return checkModifyPlugin();">
				<td class="list_td_prom" align="center" width="150">
					<b><bean:message key="pvxp.plugin.plugid" /> </b>
				</td>
				<td class="list_tr0" width="300">
					&nbsp;&nbsp;
					<%=plugid%>
					<html:hidden property="plugid" value="<%=plugid%>" />
					&nbsp;&nbsp;
					<html:errors property="plugid" />
				</td>

				<td class="list_td_prom" align="center" width="150">
					<b><bean:message key="pvxp.plugin.plugname" /> </b>
				</td>
				<td class="list_tr0">
					&nbsp;&nbsp;
					<html:text styleId="plugname" property="plugname" maxlength="10"
						size="15"
						value="<%=myCharSet.db2web(myPubUtil.dealNull(tmp.getPlugname())).trim()%>" />
				</td>
		</tr>

		<tr>
			<td class="list_td_prom" align="center" width="150">
				<b><bean:message key="pvxp.plugin.firsturl" /> </b>
			</td>
			<td class="list_tr0">
				&nbsp;&nbsp;
				<html:text styleId="firsturl" property="firsturl" maxlength="30"
					size="15" value="<%=myPubUtil.dealNull(tmp.getFirsturl()).trim()%>" />
			</td>

			<td class="list_td_prom" align="center" width="150">
				<b><bean:message key="pvxp.plugin.plugintype" /> </b>
			</td>
			<td class="list_tr0">
				&nbsp;&nbsp;
				<html:select property="plugintype" styleId="plugintype"
					onchange="setusersDisplay()">
					<html:option value="2" styleId="type_2">
						<bean:message key="pvxp.plugin.plugintype.2" />
					</html:option>
					<html:option value="1" styleId="type_1">
						<bean:message key="pvxp.plugin.plugintype.1" />
					</html:option>
					<html:option value="3" styleId="type_3">
						<bean:message key="pvxp.plugin.plugintype.3" />
					</html:option>
				</html:select>
			</td>
		</tr>

		<tr>
			<td class="list_td_prom" align="center" width="150">
				<b><bean:message key="pvxp.plugin.info" /> </b>
			</td>
			<td class="list_tr0">
				&nbsp;&nbsp;
				<html:textarea property="info" styleId="info" rows="5"
					style="width:230"
					value="<%=myCharSet.db2web(myPubUtil.dealNull(tmp.getInfo())).trim()%>" />
			</td>
			<td id="userslisttd" class="list_td_prom" align="center" width="150">
				&nbsp;
			</td>
			<td class="list_tr0">
				&nbsp;&nbsp;
				<html:textarea property="userslist" styleId="userslist" rows="5"
					style="width:230;visibility:hidden" value="<%=tmpuserslist%>" />
			</td>
		</tr>

		<tr>
			<td align="center" colspan="4" height="30">
				<input type="image" src="images/default/bt_modify.gif"
					onFocus="this.blur()">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:parent.parent.systemPlugin()"
					onFocus="this.blur()"><img src="images/default/bt_back.gif"
						border="0"> </a>
			</td>
		</tr>
	</table>

	</html:form>
	<div id="result">
		<center>
			<html:errors property="plugmodifyresult" />
		</center>
	</div>
	<script>
document.all['type_<%=myPubUtil.dealNull(tmp.getPlugtype()).trim()%>'].selected=true;
setusersDisplay();
</script>
	<%
	}
	%>
</body>
</html:html>
