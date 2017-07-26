<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />

<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
</head>
<body leftmargin="0" topmargin="0" scroll="no"
	ondragstart="return false" onselectstart="return false"
	onselect="document.selection.empty()">
	<table border="0" bgcolor="#9799FF" width="100%">
		<tr id="bttr">
			<td valign="middle" height="20">
				±£¥Ê ß∞‹£°
			</td>
		</tr>
	</table>
	<script>
alert("±£¥Ê ß∞‹£°");
history.back();
</script>
</body>
</html:html>
