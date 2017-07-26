<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />


<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script>
function goFullscreen(targeturl,mtarget){
	var newwin=window.open(targeturl,mtarget,'scrollbars');
	if(document.all){
		newwin.moveTo(0,0);
		newwin.resizeTo(screen.width,screen.height);
	}
}
function doMapEdit(){
	goFullscreen('',document.all['sform'].target);
	return true;
}
</script>
</head>
<%
	PubUtil myPubUtil = new PubUtil();
	String bankid = myPubUtil.dealNull(request.getParameter("bankid")).trim();
	OperatorModel myOperatorModel = new OperatorModel();
	int mypower = myOperatorModel.getPower(5, request);
%>
<body onload="javascript:parent.hidit();">
	<%
	if (mypower > 1) {
	%>
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" valign="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP </font>---
				<font class="location"><bean:message key="pvxp.devmap.edit" />
				</font>
			</td>
		</tr>
	</table>
	<table width="100%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<tr>
			<form id="sform" action="DevMapEditFram.jsp" target="DevMapEdit"
				onsubmit="return doMapEdit()" method="post">
			<td class="list_td_title">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="80">
							<bean:message key="pvxp.bankinfo.select" />
						</td>
						<td width="200">
							&nbsp;
							<app:bankselect property="bankid" defaultValue="<%=bankid%>" />
						</td>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="image" src="images/default/bt_modify.gif"
								onFocus="this.blur()">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:history.back()" onFocus="this.blur()"><img
									src="images/default/bt_back.gif" border="0">
							</a>
						</td>
					</tr>
				</table>
			</td>
			</form>
		</tr>
	</table>

	<%
	} else {
	%>
	<div align="center">
		对不起，您没有该操作的权限
		<br>
		<br>
		<font color=red><a href="javascript:history.back()">[返回]</a>
		</font>
	</div>
	<%
	}
	%>
</body>
</html:html>
