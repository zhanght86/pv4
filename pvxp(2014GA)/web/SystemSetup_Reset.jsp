<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="13" minpower="3" />

<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>
<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
</head>
<body onload="javascript:parent.hidit();">
	<%

	PubUtil myPubUtil = new PubUtil();
	//String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST,request)).trim();

%>

	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" valign="middle" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP </font>---
				<font class="location"><bean:message
						key="pvxp.syssetup.title" /> </font>---
				<font class="location"><bean:message
						key="pvxp.syssetup.resetcach" /> </font>
			</td>
		</tr>
	</table>


	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr>
			<td class="list_td_title" height="29">
				<b>˵��</b>
			</td>
		</tr>
		<tr class="list_tr0">
			<td>
				<bean:message key="pvxp.syssetup.resetcach.readme" />
			</td>
		</tr>
	</table>
	<br>
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr>
			<td class="list_td_title" height="29" colspan="4">
				<b>����</b>
			</td>
		</tr>
		<tr align="center" class="list_tr1">
			<td>
				<a href="?func=1" onclick="javascript:parent.showit();">�ؽ�������Ϣ����</a>
			</td>
			<td>
				<a href="?func=2" onclick="javascript:parent.showit();">�ؽ��豸���ͻ���</a>
			</td>
			<td>
				<a href="?func=3" onclick="javascript:parent.showit();">�ؽ��豸��Ϣ����</a>
			</td>
			<td>
				<a href="?func=4" onclick="javascript:parent.showit();">�ؽ��豸���̻���</a>
			</td>
		</tr>
	</table>
	<br>
	<%
	String func = myPubUtil.dealNull(request.getParameter("func")).trim();
	
	if( func.equals("1") ){
		//BankinfoModel myBankinfoModel = new BankinfoModel();
		if( BankinfoModel.resetNow() ){
%>
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_tr0" align="center">
			<td>
				<font color=blue><b>�ؽ�������Ϣ����ɹ�</b> </font>
			</td>
		</tr>
	</table>
	<%
		}else{
%>
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_tr0" align="center">
			<td>
				<font color=red><b>�ؽ�������Ϣ����ʧ��</b> </font>
			</td>
		</tr>
	</table>
	<%
		}
	}else if( func.equals("2") ){
		//DevtypeModel myDevtypeModel = new DevtypeModel();
		if( DevtypeModel.resetNow() ){
%>
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_tr0" align="center">
			<td>
				<font color=blue><b>�ؽ��豸���ͻ���ɹ�</b> </font>
			</td>
		</tr>
	</table>
	<%
		}else{
%>
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_tr0" align="center">
			<td>
				<font color=red><b>�ؽ��豸���ͻ���ʧ��</b> </font>
			</td>
		</tr>
	</table>
	<%
		}
	}else if( func.equals("3") ){
	//	DevinfoModel myDevinfoModel = new DevinfoModel();
		if( DevinfoModel.resetNow() ){
%>
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_tr0" align="center">
			<td>
				<font color=blue><b>�ؽ��豸��Ϣ����ɹ�</b> </font>
			</td>
		</tr>
	</table>
	<%
		}else{
%>
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_tr0" align="center">
			<td>
				<font color=red><b>�ؽ��豸��Ϣ����ʧ��</b> </font>
			</td>
		</tr>
	</table>
	<%
		}
	}else if( func.equals("4") ){
		//DevftModel myDevftModel = new DevftModel();
		if( DevftModel.resetNow() ){
%>
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_tr0" align="center">
			<td>
				<font color=blue><b>�ؽ��豸������Ϣ����ɹ�</b> </font>
			</td>
		</tr>
	</table>
	<%
		}else{
%>
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_tr0" align="center">
			<td>
				<font color=red><b>�ؽ��豸������Ϣ����ʧ��</b> </font>
			</td>
		</tr>
	</table>
	<%
		}
	}
%>

</body>
</html:html>
