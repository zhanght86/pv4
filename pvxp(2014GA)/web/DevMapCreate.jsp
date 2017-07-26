<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.operator.add" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script>
function createmap(){
	if(document.all.myimg.value==""){
		alert("请选择或上传图片");
		return;
	}
	parent.document.all['DevMapCreate_form'].bankid.value=document.all.bankidselect.value;
	parent.document.all['DevMapCreate_form'].imgname.value=document.all.myimg.value;
	parent.document.all['DevMapCreate_form'].submit();
}
function changebank(){
	document.all.imglist.src="DevMapImg_List.jsp?bankid="+document.all.bankidselect.value;	
}
function refleshimglist(mbank,mtype){
	document.all.imglist.src="DevMapImg_List.jsp?bankid="+mbank+"&filetype="+mtype;
}
</script>

</head>
<body onload="javascript:parent.hidit();">
	<%
		PubUtil myPubUtil = new PubUtil();
		String thisbankid = myPubUtil.dealNull(request.getParameter("bankid")).trim();
		OperatorModel myOperatorModel = new OperatorModel();
		int mypower = myOperatorModel.getPower(5, request);
		if (mypower > 1) {
	%>
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" valign="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP </font>---
				<font class="location"><bean:message key="pvxp.devmap.create" />
				</font>
			</td>
		</tr>
	</table>

	<table width="100%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<tr>
			<form onsubmit="createmap();return false;">
			<td valign="middle" class="list_td_title" colspan="2" height="28">
				<bean:message key="pvxp.bankinfo.select" />
				&nbsp;
				<app:bankselect property="bankidselect" styleId="bankstl"
					onChange="changebank()" defaultValue="<%=thisbankid%>" />
			</td>
		</tr>
		<tr>
			<td>
				<iframe id="upload" width="570" height="30" scrolling="no"
					src="DevMapUpload.jsp" frameborder="0"></iframe>
				<input id="myimg" type="hidden" size="20" value="">
			</td>
			<td valign="middle">
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<!--images list-->
				<iframe id="imglist" width="650" height="80" scrolling="no"
					src="DevMapImg_List.jsp" frameborder="0"></iframe>
			</td>
		</tr>

	</table>
	<br>
	<center>
		<input type="image" src="images/default/bt_create.gif"
			onFocus="this.blur()">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:parent.parent.getDevMapList(1)"
			onFocus="this.blur()"><img src="images/default/bt_back.gif"
				border="0">
		</a>
	</center>
	</form>

	<script>
<%
	if(thisbankid.length()>0){
%>
	document.all.bankidselect.value = "<%=thisbankid%>";
<%
	}
%>
changebank();
</script>
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
