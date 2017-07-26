<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie />
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />

<%
	PubUtil myPubUtil = new PubUtil();
	String bankid = myPubUtil.dealNull(request.getParameter("bankid"))
			.trim();
	String strlen = String.valueOf(("DevMoni" + bankid).length());
	strlen = myPubUtil.strFormat(strlen, 8, 1, '0');

	String repeat = myPubUtil.dealNull(
			myPubUtil.ReadConfig("System", "DevMoniReTime", "600000",
					"PowerView.ini")).trim();
	if (repeat.equals(""))
		repeat = "600000";
 
	String port = myPubUtil.dealNull(
			myPubUtil.ReadConfig("System", "DevJKPort", "12315",
					"PowerView.ini")).trim();
	if (port.equals(""))
		port = "12315";
%>
<html:html locale="true">
<head>
<title></title>
<link href="style/pvxp.css" rel="stylesheet" type="text/css">
<jsp:include page="DevStateCode_JS.jsp" flush="true" />
<jsp:include page="DevStateNo_JS.jsp" flush="true" />
<script LANGUAGE="VBscript">
Sub DevMoniFlash_FSCommand(ByVal command, ByVal args)
    call parent.showMoni(command,args)
end sub
</script>
<script language="javascript">
function goOtherMoni(){
	if( document.all.bankid.value!=""&&document.all.bankid.value!="<%=bankid%>
	") {
			parent.location = "DevMapMoni_Fram.jsp?bankid="
					+ document.all.bankid.value;
		}
	}
</script>
</head>
<html:base />
<%
	DevMoniServer myDevMoniServer = new DevMoniServer();
		myDevMoniServer.startMoni();
%>
<body leftmargin="0" topmargin="0" onselectstart="return false;"
	ondragstart="return false;">
	<table id="ctrl_bar" width="100%" height="34" cellspacing="1"
		cellpadding="2" class="devmoni_ctrl">
		<tr>
			<td valign="middle" height="30"><EMBED id="crysound"
					style="WIDTH: 0px; HEIGHT: 0px" src="sound/devcry.mp3" width=0
					height=0 autostart="false" loop="true" type="audio/mpeg">
				警报开关: <input name="switch" type="radio" value="on" checked
				onClick="javascript:parent.devmap_moni_state.cryflag=true;parent.devmap_moni_state.changeCryLevel(parent.devmap_moni_state.crylevel);">
				开启 <input type="radio" name="switch" value="off"
				onClick="javascript:parent.devmap_moni_state.cryflag=false;parent.devmap_moni_state.crystop();">
				关闭&nbsp; 报警级别: <input id="cry_level2" name="level" type="radio"
				value="2" checked
				onClick="javascript:parent.devmap_moni_state.changeCryLevel(document.all.cry_level2.value);">
				故障 <input id="cry_level1" type="radio" name="level" value="1"
				onClick="javascript:parent.devmap_moni_state.changeCryLevel(document.all.cry_level1.value);">
				警告</td>
			<td valign="middle"><input id="pausecry" name="pausecry"
				type="image" src="images/default/pausecry.gif"
				onClick="javascript:parent.devmap_moni_state.crypause();"
				onFocus="this.blur()" style="display:none"></td>
			<td align="right" valign="middle"><OBJECT
					CLASSID="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" WIDTH="260"
					HEIGHT="19"
					CODEBASE="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab"
					ID=DevMoniFlash>
					<PARAM NAME="MOVIE"
						VALUE="swf/DevMoni.swf?port=<%=port%>&moniaskstr=<%=strlen%>DevMoni<%=bankid%>&repeat=<%=repeat%>">
					<PARAM NAME="menu" VALUE="false">
					<PARAM NAME="wmode" VALUE="transparent">
				</OBJECT></td>
			<td><app:bankselect property="bankid" key="pvxp.devmap.jump"
					keyValue="" onChange="goOtherMoni()" /></td>
		</tr>

	</table>
</body>
</html:html>
