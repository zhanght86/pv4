<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.actionform.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="1" />
<%
	CharSet charSet = new CharSet();
	ModifyDevTypeForm devType = (ModifyDevTypeForm) request.getAttribute("ModifyDevTypeForm");
	devType.setDevType(charSet.form2GB(devType.getDevType().trim()));
	devType.setDevName(charSet.form2GB(devType.getDevName().trim()));
	devType.setDevEquipt(charSet.form2GB(devType.getDevEquipt().trim()));
	devType.setDevftno(charSet.form2GB(devType.getDevftno().trim()));
%>
<html:html locale="true">
<head>
	<title><bean:message key="pvxp.devtype.modify" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>
<body onload="javascript:parent.hidit();">
	<html:form action="/ModifyDevType.do">
		<html:hidden property="modify" value="false" />
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location"><bean:message
							key="pvxp.devtype.modify" />
					</font>
				</td>
			</tr>
		</table>

		<table width="100%" cellspacing="1" cellpadding="3"
			class="list_table_border">
			<!-- header -->
			<tr align="center">
				<td colspan="4" class="list_td_title">
					<b><bean:message key="pvxp.devtype.modify" />
					</b>
				</td>
			</tr>
			<!-- body -->
			<tr>
				<td width="15%" class="list_td_prom" width="150" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devtype.typeno" />
					</b>
				</td>
				<td width="35%" class="list_td_detail">
					&nbsp;
					<bean:write name="ModifyDevTypeForm" property="devTypeNo" />
					<html:hidden property="devTypeNo" />
				</td>
				<td width="15%" class="list_td_prom" width="150" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devtype.typestate" />
					</b>
				</td>
				<td width="35%" class="list_td_detail">
					&nbsp;
					<html:select property="devTypeState">
						<html:option value="a">正常</html:option>
						<html:option value="s">停用</html:option>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="list_td_prom" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devtype.devtype" />
					</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="30" property="devType" maxlength="20" />
				</td>
				<td class="list_td_prom" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devtype.devname" />
					</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="30" property="devName" maxlength="60" />
				</td>
			</tr>
			<tr>
				<td class="list_td_prom" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devtype.devinfo" />
					</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="30" property="devEquipt" maxlength="200" />
				</td>
				<td class="list_td_prom" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devtype.packtype" />
					</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:select property="dataPackageType">
						<html:option value="b">B/S结构数据包</html:option>
						<html:option value="c">C/S结构数据包</html:option>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="list_td_prom" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devtype.devfactory" />
					</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<app:devftselect property="devftno" />
				</td>
				<td class="list_td_prom" nowrap>
					&nbsp;
					<b></b>
				</td>
				<td class="list_td_detail">
					&nbsp;
				</td>
			</tr>
		</table>

		<!-- footer -->
		<p>
		<table align="center" width="40%">
			<tr align="center">
				<td>
					<input type="image" src="images/default/bt_ok.gif">
				</td>
				<td>
					<img src="images/default/bt_reset.gif" onclick="reset()"
						style="cursor:hand">
				</td>
				<td>
					<img src="images/default/bt_back.gif" onClick="history.back()"
						style="cursor:hand">
				</td>
			</tr>
		</table>
	</html:form>

</body>
</html:html>
