<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="13" minpower="3" />

<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
</head>
<body onload="javascript:parent.hidit();">
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
						key="pvxp.syssetup.deleclog.title" /> </font>
			</td>
		</tr>
	</table>


	<!------��ʾ����ʼ------->
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_td_title">
			<td height="29">
				<b>�豸�̿���ˮɾ��&nbsp;&nbsp;&nbsp;</b>
			</td>
			<td width="300">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<html:errors property="deleatcardlog" />
			</td>
		</tr>
		<tr class="list_tr0">
			<td colspan="2">
				�̿���¼�����������������ɾ��������ɾ����ֹ�����磺
				<font color=blue>20041203</font>��ϵͳ��ɾ����֮ǰ���̿���¼
				<font color=red><b><u>�����ɻָ���</u> </b> </font>�������Զ��������һ�ܵ����ݡ�
			</td>
		</tr>


		<tr class="list_tr1">
			<html:form action="/EatCardLogDelete.do"
				onsubmit="javascript:parent.showit();" styleId="OplogDelete_form">
				<td colspan="2">
					<table width="100%" border="0">
						<tr>
							<td width="120">
								ɾ����ˮ��ֹ���ڣ�
							</td>
							<td>
								&nbsp;
								<html:text property="fromdate" size="10"   maxlength="8" />
							</td>
							<td align="right">
								<input type="image" src="images/default/bt_ok.gif">
								&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</html:form>
		</tr>

	</table>
	<br>
	<!------��ʾ������------->



</body>
</html:html>
