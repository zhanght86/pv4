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
		//result = result.replaceAll("<DIR>","Ŀ¼ ");
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
				<b>�ļ����б�</b>
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
				<b>���ն�ͨѶ�쳣</b>
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
				<b>�ļ���</b>
			</td>
			<td>
				<b>�ļ�����</b>
			</td>
			<td>
				<b>�ļ���С</b>
			</td>
			<td>
				<b>�޸�����</b>
			</td>
			<td>
				<b>�޸�ʱ��</b>
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
						tmpdir = "�ļ�";
						pos = tmp.indexOf(" ");
						if (pos != -1) {
					tmplen = tmp.substring(0, pos);
					tmpname = tmp.substring(pos + 1);
						}
					} else {
						tmplen = " ";
						tmpdir = "Ŀ¼";
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
