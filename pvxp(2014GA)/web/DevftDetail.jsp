<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="13" minpower="1" />

<%

  String[] devftDetail = (String[])request.getAttribute(Constants.REQUEST_DEVFT);
  
  int power	= new OperatorModel().getPower(0, request);
  boolean hasPower	= power==2 || power==3;
%>
<html:html locale="true">
<head>
	<title><bean:message key="pvxp.devft.detail" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>

<body onload="javascript:parent.hidit();">
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" valign="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP</font> ---
				<font class="location"><bean:message key="pvxp.devft.detail" />
				</font>
			</td>
		</tr>
	</table>

	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<!-- header -->
		<tr align="center">
			<td colspan="6" class="list_td_title">
				<b><bean:message key="pvxp.devft.detail" />
				</b>
			</td>
		</tr>
		<!-- body -->
		<tr align="center">
			<td width="15%" class="list_td_prom">
				<b><bean:message key="pvxp.devft.devftno" />
				</b>
			</td>
			<td width="15%" class="list_td_detail">
				<%=devftDetail[0]%>
			</td>
			<td width="15%" class="list_td_prom">
				<b><bean:message key="pvxp.devft.devftname" />
				</b>
			</td>
			<td width="20%" class="list_td_detail">
				<%=devftDetail[1]%>
			</td>
			<td width="15%" class="list_td_prom">
				<b><bean:message key="pvxp.devft.addr" />
				</b>
			</td>
			<td width="20%" class="list_td_detail">
				<%=devftDetail[8]%>
			</td>
		</tr>
		<tr align="center">
			<td class="list_td_prom">
				<b><bean:message key="pvxp.devft.contact1" />
				</b>
			</td>
			<td class="list_td_detail">
				<%=devftDetail[2]%>
			</td>
			<td class="list_td_prom">
				<b><bean:message key="pvxp.devft.phone" />
				</b>
			</td>
			<td class="list_td_detail">
				<%=devftDetail[3]%>
			</td>
			<td class="list_td_prom">
				<b><bean:message key="pvxp.devft.mobile" />
				</b>
			</td>
			<td class="list_td_detail">
				<%=devftDetail[4]%>
			</td>
		</tr>
		<tr align="center">
			<td class="list_td_prom">
				<b><bean:message key="pvxp.devft.contact2" />
				</b>
			</td>
			<td class="list_td_detail">
				<%=devftDetail[5]%>
			</td>
			<td class="list_td_prom">
				<b><bean:message key="pvxp.devft.phone" />
				</b>
			</td>
			<td class="list_td_detail">
				<%=devftDetail[6]%>
			</td>
			<td class="list_td_prom">
				<b><bean:message key="pvxp.devft.mobile" />
				</b>
			</td>
			<td class="list_td_detail">
				<%=devftDetail[7]%>
			</td>
		</tr>
	</table>

	<!-- footer -->
	<p>
		<html:form action="ModifyDevft.do" method="post">
			<html:hidden property="modify" value="true" />
			<html:hidden property="devftNo" value="<%=devftDetail[0]%>" />
			<html:hidden property="devftName" value="<%=devftDetail[1]%>" />
			<html:hidden property="contact1" value="<%=devftDetail[2]%>" />
			<html:hidden property="phone1" value="<%=devftDetail[3]%>" />
			<html:hidden property="mobile1" value="<%=devftDetail[4]%>" />
			<html:hidden property="contact2" value="<%=devftDetail[5]%>" />
			<html:hidden property="phone2" value="<%=devftDetail[6]%>" />
			<html:hidden property="mobile2" value="<%=devftDetail[7]%>" />
			<html:hidden property="addr" value="<%=devftDetail[8]%>" />

			<table align="center" width="40%">
				<tr align="center">
					<%if(hasPower){%>
					<td>
						<input type="image" src="images/default/bt_modify.gif">
					</td>
					<%}%>
					</html:form>
					<%if(hasPower){%>
					<html:form action="/DevftDel.do" method="post"
						onsubmit="return confirm('确定要删除该设备厂商吗？')">
						<html:hidden property="devftArry" value="<%=devftDetail[0]%>" />
						<td>
							<input type="image" src="images/default/bt_delete.gif">
						</td>
					</html:form>
					<%}%>
					<td>
						<img src="images/default/bt_back.gif" onClick="history.back()"
							style="cursor:hand">
					</td>
				</tr>
			</table>
</body>
</html:html>
