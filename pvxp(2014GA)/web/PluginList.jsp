<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>


<app:validateCookie/>
<app:checkpower funcid="14" minpower="1" />


<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">

</head>

<body onload="javascript:parent.hidit();">
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" align="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP </font>---
				<font class="location"><bean:message key="pvxp.plugin.title" />
				</font>
			</td>
		</tr>
	</table>


	<logic:present name="<%=Constants.REQUEST_PLUGINMANAGE_LIST%>">
		<table align="center" width="100%" border="0" cellpadding="1"
			cellspacing="1" class="list_table_border">
			<tr align="center" class="list_td_title">
				<td>
					<b><bean:message key="pvxp.plugin.plugname" />
					</b>
				</td>
				<td>
					<b><bean:message key="pvxp.plugin.info" />
					</b>
				</td>
				<td width="260">
					<b><bean:message key="pvxp.plugin.operation" />
					</b>
				</td>
			</tr>
			<%
				int i = 0;
				List tmp = (List) request.getAttribute(Constants.REQUEST_PLUGINMANAGE_LIST);
				PubUtil myPubUtil = new PubUtil();
				CharSet myCharSet = new CharSet();
				String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
				String alist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
				
				if (tmp != null && !tmp.isEmpty()) {
					for (int j = 0; j < tmp.size(); j++) {
						Plugin tmpPlug = (Plugin) tmp.get(j);
						if (tmpPlug != null) {
					String plugintype = myPubUtil.dealNull(tmpPlug.getPlugtype()).trim();
					String userslist = myPubUtil.dealNull(tmpPlug.getUserslist()).trim();
					String plugintypekey = "pvxp.plugin.plugintype." + plugintype;
					if ((!alist.equals("*")) && plugintype.equals("3") && userslist.indexOf("," + operid + ",") == -1) {
			%>
			<tr align="center" class="list_tr<%=j % 2%>" height="30">
				<td>
					<%=myCharSet.db2web(myPubUtil.dealNull(tmpPlug.getPlugname())).trim()%>
				</td>
				<td>
					<%=myCharSet.db2web(myPubUtil.dealNull(tmpPlug.getInfo())).trim()%>
				</td>
				<td>
					<bean:message key="pvxp.plugin.plugintype.nopower" />
				</td>
			</tr>

			<%
			} else {
			%>
			<tr align="center" class="list_tr<%=j % 2%>" height="30">
				<td>
					<html:link
						href="<%=myPubUtil.dealNull(tmpPlug.getFirsturl()).trim()%>"
						onclick="javascript:parent.showit();" onfocus="this.blur()">
						<%=myCharSet.db2web(myPubUtil.dealNull(tmpPlug.getPlugname())).trim()%>
					</html:link>
				</td>
				<td>
					<%=myCharSet.db2web(myPubUtil.dealNull(tmpPlug.getInfo())).trim()%>
				</td>
				<td>
					<html:link
						href="<%=myPubUtil.dealNull(tmpPlug.getFirsturl()).trim()%>"
						onclick="javascript:parent.showit();" onfocus="this.blur()">
						<img src="images/default/bt_look.gif" border="0">
					</html:link>
				</td>
			</tr>

			<%
						}
						}
					}
				}
			%>

		</table>
	</logic:present>

	<logic:present name="<%=Constants.REQUEST_PLUGINMANAGE_PAGEBEAN%>">
		<p align="right">
		<form action="SystemPlugin.do" method="get"
			onsubmit="javascript:if(page.value==''){ return false;parent.hidit(); }else{parent.showit();}">
			<bean:message key="pvxp.page.title.plugin" />
			<bean:write name="<%=Constants.REQUEST_PLUGINMANAGE_PAGEBEAN%>"
				property="totalRow" />
			<bean:message key="pvxp.page.count" />
			<logic:notEqual name="<%=Constants.REQUEST_PLUGINMANAGE_PAGEBEAN%>"
				property="totalPage" value="0">
				<bean:message key="pvxp.page.tt" />
				<bean:write name="<%=Constants.REQUEST_PLUGINMANAGE_PAGEBEAN%>"
					property="totalPage" />
				<bean:message key="pvxp.page.page" />
				<bean:message key="pvxp.page.currentpage.pre" />
				<bean:write name="<%=Constants.REQUEST_PLUGINMANAGE_PAGEBEAN%>"
					property="currentPage" />
				<bean:message key="pvxp.page.page" />
			</logic:notEqual>
			<logic:equal name="<%=Constants.REQUEST_PLUGINMANAGE_PAGEBEAN%>"
				property="hasPrevious" value="true">&nbsp;<html:link
					href="#"
					onclick="parent.showit();page.value = --currentPage.value;submit();">
					<bean:message key="pvxp.page.prepage" />
				</html:link>&nbsp;</logic:equal>
			<logic:equal name="<%=Constants.REQUEST_PLUGINMANAGE_PAGEBEAN%>"
				property="hasNext" value="true">&nbsp;<html:link
					href="#"
					onclick="parent.showit();page.value = ++currentPage.value;submit();">
					<bean:message key="pvxp.page.nextpage" />
				</html:link>&nbsp;</logic:equal>
			<logic:equal name="<%=Constants.REQUEST_PLUGINMANAGE_PAGEBEAN%>"
				property="hasMultiPage" value="true">
				<bean:message key="pvxp.page.changepageto.pre" />
				<input type="text" name="page" size="3" class="page_input">
				<bean:message key="pvxp.page.page" />
				<input type="submit" value="Go">
			</logic:equal>
			<html:hidden name="<%=Constants.REQUEST_PLUGINMANAGE_PAGEBEAN%>"
				property="currentPage" />
			<html:hidden name="<%=Constants.REQUEST_PLUGINMANAGE_PAGEBEAN%>"
				property="totalRow" />
			<input type="hidden" name="pagesize" value="10">
		</form>
		</p>
	</logic:present>

</body>
</html:html>
