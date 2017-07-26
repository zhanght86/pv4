<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="3" minpower="2" />


<html:html locale="true">

<%
	String result = (String) request.getAttribute(Constants.REQUEST_REMOTECONTROL_RESULT);
	String[] res = null;
	String flag = "";
	if (result.length() > 6) {
		flag = result.substring(4, 6);
		result = result.substring(6);
		//result = result.replaceAll("<DIR>","目录 ");
		res = result.split("\r\n");
	}
%>

<head>
	<title><bean:message key="pvxp.dev.add" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>

<body onload="javascript:parent.hidit();">

	<br>
	<br>
	<br>
	<table width="100%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<tr>
			<td class="list_td_title" colspan='5' align="center">
				<b>文件名列表</b>
			</td>
		</tr>

		<%
			String tmp = "";
			String tmpname = "";
			String tmpdate = "";
			String tmptime = "";
			String tmpdir = "";
			String tmplen = "";
			int pos = 0;
			
			if (result.equals("-1")) {
		%>
		<tr class="list_tr0">

			<td colspan='5'>
				<b>与终端通讯异常</b>
			</td>
		</tr>
		<%
				} else {
				if (!flag.equals("00")) {
		%>
		<tr class="list_tr0">

			<td colspan='5'>
				<b><%=result%>
				</b>
			</td>
		</tr>
		<%
		} else {
		%>
		<tr class="list_tr0">

			<td>
				<b>文件名</b>
			</td>
			<td>
				<b>文件类型</b>
			</td>
			<td>
				<b>文件大小</b>
			</td>
			<td>
				<b>修改日期</b>
			</td>
			<td>
				<b>修改时间</b>
			</td>
		</tr>
		<%
					CharSet myCharSet = new CharSet();
					PubUtil myPubUtil = new PubUtil();
					for (int i = 0; i < res.length; i++) {
				tmp = res[i];
				if (tmp.length() > 17) {
					tmpdate = tmp.substring(0, 10);
					tmptime = tmp.substring(12, 17);
					tmp = tmp.substring(17).trim();
					
					pos = tmp.indexOf("<DIR>");
					if (pos == -1) {
						tmpdir = "文件";
						pos = tmp.indexOf(" ");
						if (pos != -1) {
					tmplen = tmp.substring(0, pos);
					tmpname = tmp.substring(pos + 1);
						}
					} else {
						tmplen = " ";
						tmpdir = "目录";
						tmpname = tmp.substring(pos + 5).trim();
					}
		%>
		<tr class="list_tr0">
			<td>
				<%=myPubUtil.file2gb(tmpname)%>
			</td>
			<td>
				<%=tmpdir%>
			</td>
			<td>
				<%=tmplen%>
			</td>
			<td>
				<%=tmpdate%>
			</td>
			<td>
				<%=tmptime%>
			</td>
		</tr>
		<%
					}
					}
				}
			}
		%>
	</table>
	<br>
	<br>
	<div align="center">
		<img src="images/default/bt_back.gif"
			onClick="javascript:parent.parent.goUrl('FileState.jsp')"
			style="cursor:hand">
	</div>

</body>
</html:html>
