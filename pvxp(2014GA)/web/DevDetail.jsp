<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.model.*"%>
<%@ page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="2" />
 
<%
	String[] devDetail = (String[]) request.getAttribute(Constants.REQUEST_DEVDETAIL);
	int power = new OperatorModel().getPower(0, request);
	boolean hasPower = power == 2 || power == 3;
	
	devDetail[12] = "pvxp.dev.state." + devDetail[12];
	devDetail[13] = "pvxp.devtype.packtype." + devDetail[13];
	devDetail[14] = "pvxp.dev.local." + devDetail[14];
	devDetail[15] = "pvxp.dev.poll." + devDetail[15];
%>
<html:html locale="true">
<head>
	<title><bean:message key="pvxp.devtype.detail" /></title>
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
				<font class="location"><bean:message key="pvxp.dev.detail" />
				</font>
			</td>
		</tr>
	</table>

	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<!-- header -->
		<tr align="center">
			<td colspan="8" class="list_td_title">
				<b><bean:message key="pvxp.dev.detail" /> </b>
			</td>
		</tr>
		<!-- body -->
		<tr align="center">
			<td width="8%" class="list_td_prom">
				<b><bean:message key="prompt.devno" /> </b>
			</td>
			<td width="17%" class="list_td_detail">
				<%=devDetail[0]%>
			</td>
			<td width="8%" class="list_td_prom">
				<b><bean:message key="prompt.devip" /> </b>
			</td>
			<td width="17%" class="list_td_detail">
				<%=devDetail[1]%>
			</td>
			<td width="8%" class="list_td_prom">
				<b><bean:message key="prompt.devmac" /> </b>
			</td>
			<td width="17%" class="list_td_detail">
				<%=devDetail[2]%>
			</td>
			<td width="8%" class="list_td_prom">
				<b><bean:message key="prompt.typeno" /> </b>
			</td>
			<td width="17%" class="list_td_detail">
				<%=devDetail[3]%>
			</td>
		</tr>
		<tr align="center">
			<td class="list_td_prom">
				<b><bean:message key="prompt.auther" /> </b>
			</td>
			<td class="list_td_detail">
				<%=devDetail[11]%>
			</td>
			<td class="list_td_prom">
				<b><bean:message key="prompt.organno" /> </b>
			</td>
			<td class="list_td_detail">
				<%=devDetail[5]%>
			</td>
			<td class="list_td_prom">
				<b><bean:message key="prompt.tellerno" /> </b>
			</td>
			<td class="list_td_detail">
				<%=devDetail[6]%>
			</td>
			<td class="list_td_prom">
				<b><bean:message key="prompt.bank" /> </b>
			</td>
			<td class="list_td_detail">
				<%=devDetail[7]%>
			</td>
		</tr>
		<tr align="center">
			<td class="list_td_prom">
				<b><bean:message key="prompt.devaddr" /> </b>
			</td>
			<td class="list_td_detail">
				<%=devDetail[8]%>
			</td>
			<td class="list_td_prom">
				<b><bean:message key="prompt.dutyname" /> </b>
			</td>
			<td class="list_td_detail">
				<%=devDetail[9]%>
			</td>
			<td class="list_td_prom">
				<b><bean:message key="prompt.organcontact" /> </b>
			</td>
			<td class="list_td_detail">
				<%=devDetail[10]%>
			</td>
			<td class="list_td_prom">
				<b><bean:message key="prompt.sysid" /> </b>
			</td>
			<td class="list_td_detail">
				<%=devDetail[4]%>
			</td>
		</tr>
		<tr align="center">
			<td class="list_td_prom">
				<b><bean:message key="prompt.typestate" /> </b>
			</td>
			<td class="list_td_detail">
				<bean:message key="<%=devDetail[12]%>" />
			</td>
			<td class="list_td_prom">
				<b><bean:message key="prompt.datatype" /> </b>
			</td>
			<td class="list_td_detail">
				<bean:message key="<%=devDetail[13]%>" />
			</td>
			<td class="list_td_prom">
				<b><bean:message key="prompt.localtag" /> </b>
			</td>
			<td class="list_td_detail">
				<bean:message key="<%=devDetail[14]%>" />
			</td>
			<td class="list_td_prom">
				<b><bean:message key="prompt.polltag" /> </b>
			</td>
			<td class="list_td_detail">
				<bean:message key="<%=devDetail[15]%>" />
			</td>
		</tr>
		<tr align="center">
			<td class="list_td_prom">
				<b><bean:message key="prompt.devkey" /> </b>
			</td>
			<td class="list_td_detail">
				<%=devDetail[16]%>
			</td>
			<td class="list_td_prom">
				<b><bean:message key="prompt.pinkey" /> </b>
			</td>
			<td class="list_td_detail">
				<%=devDetail[17]%>
			</td>
			<td class="list_td_prom">
				<b>饱和运行时间</b>
			</td>
			<td class="list_td_detail">
				<%=devDetail[18]%>
				小时/天
			</td>
			<td class="list_td_prom">
				<b>&nbsp;</b>
			</td>
			<td class="list_td_detail">
				&nbsp;
			</td>
		</tr>
	</table>

	<table align="center" width="40%">
		<tr align="center">
			<%
			if (hasPower) {
			%>
			<form action="ModifyDev.jsp">
				<input type="hidden" name="devno" value="<%=devDetail[0].trim()%>" />
			<td>
				<html:image src="images/default/bt_modify.gif" />
			</td>
			</form>
			<html:form action="/DevDelete.do"
				onsubmit="return confirm('确定删除设备？');">
				<html:hidden property="devList" value="<%=devDetail[0]%>" />
				<td>
					<html:image src="images/default/bt_delete.gif" />
				</td>
			</html:form>
			<%
			}
			%>
			<td>
				<a href="javascript:history.back()"><img
						src="images/default/bt_back.gif" border="0"> </a>
			</td>
		</tr>
	</table>
</body>
</html:html>
