<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />

<%
	PubUtil myPubUtil = new PubUtil();
	String bankid = myPubUtil.dealNull(request.getParameter("bankid")).trim();
	String filetype = myPubUtil.dealNull(request.getParameter("filetype")).trim();
%>
<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
	<script>
parent.document.all['myimg'].value="";
function setImg(){
	try{
		parent.document.all['myimg'].value=document.all['mapimgname'].value;
	}catch(e){}
}
</script>
</head>
<body leftmargin="0" topmargin="0" onselectstart="return false;">
	<table border="0" width="100%">

		<%
		if (bankid.length() == 0) {
		%>
		<tr>
			<td>
				请先选择机构再选择图片
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
					tt = "请选择图片";
				else
					tt = "暂无图片，请上传";
		%>
		<tr>
			<td>
				<%=tt%>
			</td>
		</tr>

		<tr>
			<td>
				<table width="100%" border="0">
					<tr>
						<%
									if (jpgimg.exists()) {
									out.println("<td width=\"20\"><input name=\"mapimgname\" id=\"img_jpg\" onclick=\"setImg()\" type=\"radio\" value=\"" + bankid + ".jpg"
									+ "\"></td><td width=\"50\"><a href=\"/map/" + bankid + ".jpg\" target=\"_blank\"><img src=\"images/default/jpg.gif\" border=\"0\"></a></td><td><a href=\"/map/" + bankid
									+ ".jpg\" target=\"_blank\">" + bankid + ".jpg</a></td>");
								}
								if (gifimg.exists()) {
									out.println("<td width=\"20\"><input name=\"mapimgname\" id=\"img_gif\" onclick=\"setImg()\" type=\"radio\" value=\"" + bankid + ".gif"
									+ "\"></td><td width=\"50\"><a href=\"/map/" + bankid + ".gif\" target=\"_blank\"><img src=\"images/default/gif.gif\" border=\"0\"></a></td><td><a href=\"/map/" + bankid
									+ ".gif\" target=\"_blank\">" + bankid + ".gif</a></td>");
								}
								if (pngimg.exists()) {
									out.println("<td width=\"20\"><input name=\"mapimgname\" id=\"img_png\" onclick=\"setImg()\" type=\"radio\" value=\"" + bankid + ".png"
									+ "\"></td><td width=\"50\"><a href=\"/map/" + bankid + ".png\" target=\"_blank\"><img src=\"images/default/png.gif\" border=\"0\"></a></td><td><a href=\"/map/" + bankid
									+ ".png\" target=\"_blank\">" + bankid + ".png</a></td>");
								}
							}
						%>
					</tr>
				</table>
			</td>
		</tr>

	</table>
	<%
	if (filetype.length() > 0) {
	%>
	<script>
	try{
		document.all['img_<%=filetype%>'].click();
	}catch(e){}
	</script>
	<%
	}
	%>
</body>
</html:html>
