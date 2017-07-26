<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="com.lcjr.pvxp.util.*"%>
<%@ page import="com.jspsmart.upload.SmartUpload"%>
<%@ page import="java.io.File"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="3" minpower="2" />

<html:html locale="true">

<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="0" topmargin="0" class="list_td_detail">
	<table border="0">
		<tr align="left">
			<td valign="middle">
				<%
					PubUtil myPubUtil = new PubUtil();
					String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
					SmartUpload mySmartUpload = new SmartUpload();
					try {
						mySmartUpload.initialize(pageContext);
						mySmartUpload.service(request, response);
						mySmartUpload.upload();
						
						String path = request.getRealPath("");
						
						File pDir = new File(path + "/upload");
						if (!pDir.exists()) {
							pDir.mkdir();
						}
						
						File cDir = new File(path + "/upload/" + operid);
						if (!cDir.exists()) {
							cDir.mkdir();
						}
						
						mySmartUpload.save("upload/" + operid + "/");
				%>
				<bean:message key="pvxp.filedistribute.notice.succupload" />
				<script>
          parent.document.all['filelist'].Refresh();
          setTimeout('window.location="FileUpload.jsp"',3000);
        </script>
				<%
				} catch (Exception e) {
				%>
				<bean:message key="pvxp.filedistribute.notice.errupload" />
				&nbsp;&nbsp;
				<a href=\"javascript:history.back();\"> <font color=red>
						[<bean:message key="pvxp.filedistribute.notice.reupload" />] </font> </a>
				<script>
          parent.document.all['filelist'].Refresh();
        </script>
				<%
				}
				%>
			</td>
		</tr>
	</table>
</body>
</html:html>
