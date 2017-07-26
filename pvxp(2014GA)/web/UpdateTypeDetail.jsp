<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="2" />
<%
	
	String[] updateTypeDetail = (String[])request.getAttribute(Constants.REQUEST_UPDATETYPE);
	
	int power		= new OperatorModel().getPower(0, request);
	boolean hasPower	= power==2 || power==3;
%>
<html:html locale="true">
<head>
	<title><bean:message key="pvxp.updatetype.detail" />
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
				<font class="location"><bean:message
						key="pvxp.updatetype.detail" />
				</font>
			</td>
		</tr>
	</table>

	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<!-- header -->
		<tr align="center">
			<td colspan="4" class="list_td_title">
				<b><bean:message key="pvxp.updatetype.detail" />
				</b>
			</td>
		</tr>
		<!-- body -->
		<tr align="center">
			<td width="30%" class="list_td_prom">
				<b><bean:message key="pvxp.updatetype.typeno" />
				</b>
			</td>
			<td width="70%" class="list_td_detail">
				<%=updateTypeDetail[0]%>
			</td>
		</tr>
		<tr align="center">
			<td class="list_td_prom">
				<b><bean:message key="pvxp.updatetype.updatename" />
				</b>
			</td>
			<td class="list_td_detail">
				<%=updateTypeDetail[1]%>
			</td>
		</tr>
		<tr align="center">
			<td class="list_td_prom">
				<b><bean:message key="pvxp.updatetype.updateinfo" />
				</b>
			</td>
			<td class="list_td_detail">
				<%=updateTypeDetail[2]%>
			</td>
		</tr>
	</table>

	<!-- footer -->
	<p>
		<html:form action="ModifyUpdateType.do" method="post">
			<html:hidden property="modify" value="true" />
			<html:hidden property="updateno" value="<%=updateTypeDetail[0]%>" />
			<html:hidden property="updatename" value="<%=updateTypeDetail[1]%>" />
			<html:hidden property="info" value="<%=updateTypeDetail[2]%>" />
			<table align="center" width="40%">
				<tr align="center">
					<%if(hasPower){%>
					<td>
						<input type="image" src="images/default/bt_modify.gif">
					</td>
					<%}%>
					</html:form>
					<%if(hasPower){%>
					<html:form action="/UpdateTypeDel.do" method="post"
						onsubmit="return confirm('是否确定要删除更新类型？')">
						<html:hidden property="updateTypeArry"
							value="<%=updateTypeDetail[0]%>" />
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
