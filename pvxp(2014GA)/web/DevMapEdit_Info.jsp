<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />

<%
	PubUtil myPubUtil = new PubUtil();
	String bankid = myPubUtil.dealNull(request.getParameter("bankid")).trim();
%>
<html:html locale="true">
<head>
	<title>修改地图</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
	<script>
function hidpage(){
	document.body.scroll="no";
}
function showpage(){
	document.body.scroll="yes";
}
function setCurXY(x,y){
	document.all.curxpos.value=x;
	document.all.curypos.value=y;
}
function setMouseXY(x,y){
	try{
		document.all.mousexypos.value=x+":"+y;
	}catch(e){}
}
</script>
</head>
<body leftmargin="0" topmargin="0">
	<table width="100%" height="100%" border="0" cellpadding="0"
		cellspacing="0" id="bttable">
		<tr>
			<td id="vtdbar" align="center" height="1" style="CURSOR: hand"
				background="images/default/down_bk.gif"
				onClick="javascript:parent.vpagebar()">
				<img src="images/default/down.gif" name="vpbar" border="0"
					id="vpbar">
			</td>
		</tr>

		<tr height="30">
			<td width="100%" align="center" valign="bottom">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					bgcolor="#9799FF">
					<tr>
						<td width="10">
							&nbsp;
						</td>
						<td id="curobj_txt" width="270" align="left" valign="center">
							尚未选择任何机构或设备
							<!--选中元素的描述-->
						</td>
						<form
							onsubmit="parent.devmap_edit_main.addEle(document.all.curxpos.value,document.all.curypos.value);return false;">
						<td height="30" align="left" valign="center">

							<!--选中元素的坐标-->
							当前选中元素坐标&nbsp;&nbsp;X:
							<input name="curxpos" type="text" size="3" class="devmap_XY_pos">
							&nbsp;&nbsp;Y:
							<input name="curypos" type="text" size="3" class="devmap_XY_pos">
							&nbsp;&nbsp;
							<input type="image" src="images/default/bt_location.gif"
								onFocus="this.blur()">

							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:parent.devmap_edit_main.deleteEle()"
								onFocus="this.blur()"><img
									src="images/default/bt_remove.gif" border="0"> </a>


						</td>
						</form>
						<td height="30" align="left" valign="center" width="150">
							<!--鼠标的坐标-->
							当前鼠标坐标&nbsp;&nbsp;
							<input name="mousexypos" type="text" class="devmap_XY_pos"
								size="7">
						</td>
					</tr>
					<tr>
						<td bgcolor="#000000" height="1" colspan="4"></td>
					</tr>
					<tr>
						<td width="10">
							&nbsp;
						</td>
						<td id="curobj_detail_txt" align="left" align="center" colspan="2">
							&nbsp;
						</td>
						<td align="left" align="center" width="150">
							<!--保存地图的ifram-->
							<iframe id="update" width="100%" height="30" scrolling="yes"
								src="DevMapEditSave.jsp?bankid=<%=bankid%>" frameborder="0"></iframe>
						</td>
					</tr>
					<tr>
						<td bgcolor="#000000" height="1" colspan="4"></td>
					</tr>
				</table>
			</td>
		</tr>

		<tr>
			<td></td>
		</tr>
	</table>
</body>
</html:html>
