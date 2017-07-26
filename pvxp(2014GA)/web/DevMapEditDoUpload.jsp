<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<%@ page import="com.lcjr.pvxp.util.*"%>
<%@ page import="com.jspsmart.upload.SmartUpload"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />


<%
	PubUtil myPubUtil = new PubUtil();
	String bankid = myPubUtil.dealNull(request.getParameter("bankid")).trim();
	SmartUpload mySmartUpload = new SmartUpload();
%>
<html:html locale="true">
<head>
	<title></title>
	<html:base />
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" onselectstart="return mselect();">
	<table border="0">
		<tr align="left">
			<td valign="middle">
				<%
try
{

mySmartUpload.initialize(pageContext); 
mySmartUpload.service(request,response); 
mySmartUpload.upload(); 

String upfn = mySmartUpload.getFiles().getFile(0).getFileName();
String filetype = null;

String path=request.getRealPath("");
java.io.File mapfile = new java.io.File(path+"/map",bankid+".map");



java.io.File sourcefile = new java.io.File(path+"/map",upfn);

if(new ExtensionFileFilter("jpg").accept(sourcefile))	filetype = "jpg";
else if(new ExtensionFileFilter("gif").accept(sourcefile))	filetype = "gif";
else if(new ExtensionFileFilter("png").accept(sourcefile))	filetype = "png";




if( filetype!=null ){
	String newfn = bankid.trim()+"."+filetype;

	mySmartUpload.saveAs("/map/",newfn);//文件保存的目录为map

	
	out.println("已经成功上传图片为：<font color=blue>["+newfn+"]</font>，图片列表已更新，2秒后自动返回...</font></a>");
%>
				<script>
var mytype = "<%=filetype%>";
var mybank = "<%=bankid%>";
parent.parent.devmap_edit_main.changeImg(mybank+"."+mytype);
parent.refleshimglist(mybank,mytype);
setTimeout('window.location="DevMapEditUpload.jsp?bankid=<%=bankid%>"',2000);
</script>


				<%


}else{
	out.println("图片格式错误，必须是jpg/png/gif中的一种&nbsp;&nbsp;<a href=\"javascript:history.back();\"><font color=red>[点击这里重新上传]</font></a>");
}

}catch(Exception e)
{
	out.println("图片上传失败！&nbsp;&nbsp;<a href=\"javascript:history.back();\"><font color=red>[点击这里重新上传]</font></a>");
}

%>
			</td>
		</tr>
	</table>
</body>
</html:html>
