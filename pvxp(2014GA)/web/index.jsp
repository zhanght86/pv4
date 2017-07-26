<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.application.title.main" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
</head>
<body scroll="no" ondragstart="return false"
	onselectstart="return false" onselect="document.selection.empty()"
	background="images/default/login_bk.jpg">


	<center>
		<table width="100%" height="100%" border="0">
			<tr>
				<td>
					<table align="center" width="90%" border="0" cellpadding="1"
						cellspacing="1" class="list_table_border">
						<tr class="list_td_title">
							<td>&nbsp;&nbsp;&nbsp;&nbsp;PowerView XP --- <b><bean:message
										key="pvxp.application.title.oplogin" /> </b></td>
						</tr>
					</table>

					<table align="center" width="90%" border="0" cellpadding="1"
						cellspacing="1" bgcolor="#FFFFFF">
						<tr>
							<td><img src="images/default/welcome.jpg"></td>
							<td>
								<%
									PubUtil myPubUtil = new PubUtil();
															String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
										if (!operid.equals("")) {
								%> 操作员 <font color=blue><%=operid%> </font> 已经在本计算机上登录，请先 <a
								href="LogOut.jsp?func=1"><font color=red>[退出]</font> </a> 再登录 <%
								} else {
								%>
								<table width="100%" border="0">
									<tr>
										<html:form action="/OPLogin.do" focus="operid"
											styleId="opform">
											<td height="20%"><nobr>
													<bean:message key="pvxp.input.prompt.operid" />
												</nobr></td>
											<td align="right"><html:text property="operid" size="16"
													maxlength="6" style="width:110px" /></td>
											<td width="50"></td>
									</tr>
									<tr>
										<td height="20%"><nobr>
												<bean:message key="pvxp.input.prompt.operpasswd" />
											</nobr></td>
										<td align="right"><html:password property="operpwd"
												size="16" maxlength="6" style="width:110px"
												redisplay="false" /></td>
										<td width="50"></td>
									</tr>
									<tr>
										<td height="40">&nbsp;</td>
										<td align="right"><input type="image"
											src="images/default/bt_login.gif" onFocus="this.blur()">
										</td>
										<td width="50"></td>
										</html:form>
									</tr>
									<tr>
										<td colspan="3"><html:errors property="system" /> &nbsp;
										</td>
									</tr>
								</table> <%
								}
								%>
							</td>
						</tr>
					</table>

					<table align="center" width="90%" border="0" cellpadding="1"
						cellspacing="1" class="list_table_border">
						<tr align="center" class="list_td_title">
							<td>:::.:: Powered by 浪潮集团 金融事业部 Copyright &copy; 2014,
								2016&nbsp;&nbsp;&nbsp; <font color=blue><b>PowerView XP--BOLD 5.2.0.0 版本</b> </font> 2014.12.18 :::..</td>
						</tr>
					</table> <br> <br> <br> <br> <br> <br></td>
			</tr>
		</table>
	</center>
</body>

</html:html>
