<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>
<%@page import="com.lcjr.pvxp.pojo.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />



<%
	PubUtil myPubUtil = new PubUtil();
	String bankid = myPubUtil.dealNull(request.getParameter("bankid")).trim();
	String filetype = myPubUtil.dealNull(request.getParameter("filetype")).trim();
	String srcimgname = "";
	boolean iFhasimg = false;
	if (filetype.equals("")) {
		DevMap mymap = null;
		DevMapModel mymapModel = new DevMapModel(bankid);
		if (mymapModel != null)
			mymap = mymapModel.getDevMap();
		if (mymap != null)
			srcimgname = mymap.getImgname();
	}
%>
<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
	<script>
var selimgname="";
</script>
</head>
<body leftmargin="0" topmargin="0" onselectstart="return false;">
	<table border="0" width="100%">

		<%
		if (bankid.length() == 0) {
		%>
		<tr>
			<td>
				机构不存在
			</td>
		</tr>
		<%
				} else {
				String path = request.getRealPath("");
				java.io.File jpgimg = new java.io.File(path + "/map", bankid + ".jpg");
				boolean iFexists1 = jpgimg.exists();
				java.io.File gifimg = new java.io.File(path + "/map", bankid + ".gif");
				boolean iFexists2 = gifimg.exists();
				java.io.File pngimg = new java.io.File(path + "/map", bankid + ".png");
				boolean iFexists3 = pngimg.exists();
				String tt = "";
				if (iFexists1 || iFexists2 || iFexists3)
					tt = "可选图片列表";
				else
					tt = "暂无图片，请上传";
		%>
		<tr>
			<td>
				<%=tt%>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td>
				<table width="100%" border="0">
					<tr>
						<td>
							<%
										if (jpgimg.exists()) {
										out.println("<input name=\"mapimgname\" id=\"img_" + bankid + ".jpg\" onclick=\"javascript:selimgname='" + bankid + ".jpg'\" type=\"radio\" value=\"" + bankid + ".jpg"
										+ "\">&nbsp;&nbsp;<a href=\"/map/" + bankid + ".jpg\" target=\"_blank\">" + bankid + ".jpg</a><br>");
										iFhasimg = true;
									}
									if (gifimg.exists()) {
										out.println("<input name=\"mapimgname\" id=\"img_" + bankid + ".gif\" onclick=\"javascript:selimgname='" + bankid + ".gif'\" type=\"radio\" value=\"" + bankid + ".gif"
										+ "\">&nbsp;&nbsp;<a href=\"/map/" + bankid + ".gif\" target=\"_blank\">" + bankid + ".gif</a><br>");
										iFhasimg = true;
									}
									if (pngimg.exists()) {
										out.println("<input name=\"mapimgname\" id=\"img_" + bankid + ".png\" type=\"radio\" onclick=\"javascript:selimgname='" + bankid + ".png'\" value=\"" + bankid + ".png"
										+ "\">&nbsp;&nbsp;<a href=\"/map/" + bankid + ".png\" target=\"_blank\">" + bankid + ".png</a><br>");
										iFhasimg = true;
									}
								}
							%>
						</td>
					</tr>
					<tr>
						<td>

							<%
									if (iFhasimg) {
									out
									.println("<center><a href=\"javascript:parent.parent.devmap_edit_main.changeImg(selimgname)\" onFocus=\"this.blur()\"><img src=\"images/default/bt_setimg.gif\" border=\"0\"></a></center>");
								}
							%>

						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
	<script>
<%
if( filetype.length()>0 ){
%>
	try{
		document.all['img_<%=bankid%>.<%=filetype%>'].click();
	}catch(e){}
<%
}else if( srcimgname.length()>0 ){
%>
	try{
		document.all['img_<%=srcimgname%>'].click();
	}catch(e){}
<%
}
%>
</script>
</body>
</html:html>
