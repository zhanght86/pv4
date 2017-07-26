<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />

<%@page import="com.lcjr.pvxp.util.*"%>
<%
	PubUtil myPubUtil = new PubUtil();
	String bankid = myPubUtil.dealNull(request.getParameter("bankid")).trim();
%>
<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
	<script>
function goUpdateMap(){
	try{
		parent.parent.devmap_edit_main.prepareStr();
		document.all.banksstr.value = parent.parent.devmap_edit_main.bankstr;
		document.all.devsstr.value = parent.parent.devmap_edit_main.devstr;
		document.all.imgnmstr.value = parent.parent.devmap_edit_main.imgstr;
		document.all.bankid.value = "<%=bankid%>";
	}catch(e){}
	document.all.bttr.style.display="none";
	document.all.savingtr.style.display="";
	setTimeout('document.all.updateDMap.submit()',1000);
}
</script>
</head>
<body leftmargin="0" topmargin="0" scroll="no"
	ondragstart="return false" onselectstart="return false"
	onselect="document.selection.empty()">
	<table border="0" bgcolor="#9799FF" width="100%">
		<tr id="bttr">
			<td valign="middle" height="20">
				<a href="javascript:goUpdateMap()" onFocus="this.blur()"><img
						src="images/default/bt_save.gif" border="0"> </a>
			</td>
		</tr>
		<tr id="savingtr" style="display:none">
			<td height="20">
				正在保存，请稍后...
			</td>
		</tr>
	</table>

	<html:form action="/DevMapUpdate.do" styleId="updateDMap">
		<html:hidden property="bankid" />
		<html:hidden property="banksstr" />
		<html:hidden property="devsstr" />
		<html:hidden property="imgnmstr" />
	</html:form>

</body>
</html:html>
