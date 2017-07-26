<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />

<html:html locale="true">
<head>
	<title>修改地图</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
	<script language="JavaScript">
var out=1;
var rightw = "";
function pagebar()
{
	if(out==1){
		rightw = document.all['devmap_edit_top'].cols;
		document.all['devmap_edit_top'].cols="*,8";
		devmap_edit_right.hidpage();
		devmap_edit_right.pbar.src="images/default/in.gif";
		devmap_edit_right.tdbar.background="images/default/in_bk.gif";
		out = 0;
	}else{
		document.all['devmap_edit_top'].cols=rightw;
		devmap_edit_right.showpage();
		devmap_edit_right.pbar.src="images/default/out.gif";
		devmap_edit_right.tdbar.background="images/default/out_bk.gif";
		out = 1;
	}
}


var down=1;
var bth = "";
function vpagebar()
{
	if(down==1){
		bth = document.all['devmap_edit_all'].rows;
		document.all['devmap_edit_all'].rows="*,8";
		devmap_edit_info.vpbar.src="images/default/up.gif";
		devmap_edit_info.vtdbar.background="images/default/up_bk.gif";
		down = 0;
	}else{
		document.all['devmap_edit_all'].rows=bth;
		devmap_edit_info.vpbar.src="images/default/down.gif";
		devmap_edit_info.vtdbar.background="images/default/down_bk.gif";
		down = 1;
	}
}
</script>
</head>

<%
	PubUtil myPubUtil = new PubUtil();
	String bankid = myPubUtil.dealNull(request.getParameter("bankid")).trim();
	String path = request.getRealPath("");
	java.io.File mapfile = new java.io.File(path + "/map", bankid + ".map");
	if (!mapfile.exists()) {
%>
<script>
window.moveTo(screen.width/2-200,screen.height/2-50);
window.resizeTo(400,100);
</script>
<body scroll="no">
	该机构尚无地图，请先创建地图
</body>
<%
} else {
%>
<frameset rows='*' border='false' frameBorder='0' frameSpacing='0'
	onload=''>
	<frameset id="devmap_edit_all" rows="*,64" cols="*" framespacing="1"
		border="1" bordercolor="#666666">
		<frameset id="devmap_edit_top" rows="*" cols="*,200" framespacing="0"
			border="0">
			<frame id="devmap_edit_main"
				src="DevMapEditMain.jsp?bankid=<%=bankid%>" scrolling="no" noresize
				onmouseout="try{devmap_edit_main.enddrag();}catch(e){}">
			<frame id="devmap_edit_right"
				src="DevMapEdit_Right.jsp?bankid=<%=bankid%>" noresize>
		</frameset>
		<frame id="devmap_edit_info"
			src="DevMapEdit_Info.jsp?bankid=<%=bankid%>" noresize scrolling="yes">
	</frameset>
</frameset>

<noframes>
	<body>
	</body>
</noframes>
<%
}
%>
</html:html>
