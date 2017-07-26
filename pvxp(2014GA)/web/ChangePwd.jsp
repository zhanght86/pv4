<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>

<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
</head>
<body onload="javascript:parent.hidit();">
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" align="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP </font> ---
				<font class="location"><bean:message
						key="pvxp.changepwd.title" />
				</font>
			</td>
		</tr>
	</table>

	<!------显示表单开始------->
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_td_title">
			<td height="29" width="200" align="center">
				<b>修改密码</b>
			</td>
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<html:errors property="changepwd" />
			</td>
		</tr>
		<tr class="list_tr0">
			<html:form action="/ChangePWD.do"
				onsubmit="javascript:parent.showit();" styleId="changePWD_form">
				<td>
					请输入当前密码：
				</td>
				<td>
					<html:password property="operpwd" size="16" maxlength="6" />
				</td>
		</tr>

		<tr class="list_tr1">

			<td>
				请输入新密码：
			</td>
			<td>
				<html:password property="newpwd1" size="16" maxlength="6" />
			</td>
		</tr>

		<tr class="list_tr0">
			<td>
				请再次输入新密码：
			</td>
			<td>
				<html:password property="newpwd2" size="16" maxlength="6" />
			</td>

		</tr>

	</table>
	<br>
	<center>
		<input type="image" src="images/default/bt_ok.gif">
	</center>
	</html:form>
	<!------显示表单结束------->
</body>
</html:html>
