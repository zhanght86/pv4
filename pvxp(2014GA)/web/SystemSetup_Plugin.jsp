<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="13" minpower="3" />

<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
function delPlugin(pid){
	if(confirm("删除插件只删除系统链接，并不删除程序包。\n确定要删除吗？")){
		parent.document.all.SysPluginDel_form.plugid.value=pid;
		parent.showit();
		parent.document.all.SysPluginDel_form.submit();
	}
}
</script>
</head>

<body onload="javascript:parent.hidit();">
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" valign="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP </font>---
				<font class="location"><bean:message
						key="pvxp.syssetup.title" />
				</font>---
				<font class="location"><bean:message
						key="pvxp.syssetup.plugin" />
				</font>
			</td>
			<td align="right">
				<a href="SystemSetup_Plugin_Add.jsp" onClick="parent.showit();"
					onFocus="this.blur()"><img src="images/default/bt_add.gif"
						border="0">
				</a>&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
	</table>


	<logic:present name="<%=Constants.REQUEST_PLUGINMANAGE_LIST%>">
		<table align="center" width="100%" border="0" cellpadding="1"
			cellspacing="1" class="list_table_border">
			<tr align="center" class="list_td_title">
				<td>
					<b><bean:message key="pvxp.plugin.plugid" />
					</b>
				</td>
				<td>
					<b><bean:message key="pvxp.plugin.plugname" />
					</b>
				</td>
				<td>
					<b><bean:message key="pvxp.plugin.firsturl" />
					</b>
				</td>
				<td>
					<b><bean:message key="pvxp.plugin.plugintype" />
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
				if (tmp != null && !tmp.isEmpty()) {
					for (int j = 0; j < tmp.size(); j++) {
						Plugin tmpPlug = (Plugin) tmp.get(j);
						if (tmpPlug != null) {
					String plugintypekey = "pvxp.plugin.plugintype." + myPubUtil.dealNull(tmpPlug.getPlugtype()).trim();
			%>
			<tr align="center" class="list_tr<%=j % 2%>" height="35">
				<td>
					<%=myPubUtil.dealNull(tmpPlug.getPlugid()).trim()%>
				</td>
				<td>
					<%=myCharSet.db2web(myPubUtil.dealNull(tmpPlug.getPlugname())).trim()%>
				</td>
				<td>
					<%=myPubUtil.dealNull(tmpPlug.getFirsturl()).trim()%>
				</td>
				<td>
					<bean:message key="<%=plugintypekey%>" />
				</td>
				<td>
					<html:link
						href="<%=myPubUtil.dealNull(tmpPlug.getFirsturl()).trim()%>"
						onclick="javascript:parent.showit();" onfocus="this.blur()">
						<img src="images/default/bt_look.gif" border="0">
					</html:link>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a
						href="SystemSetup_Plugin_Modify.jsp?plugid=<%=myPubUtil.dealNull(tmpPlug.getPlugid()).trim()%>"
						onfocus="this.blur()" onclick="parent.showit();"><img
							src="images/default/bt_modify.gif" border="0">
					</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="#"
						onclick="javascript:delPlugin('<%=myPubUtil.dealNull(tmpPlug.getPlugid()).trim()%>');"
						onfocus="this.blur()"><img src="images/default/bt_delete.gif"
							border="0">
					</a>
				</td>
			</tr>

			<%
					}
					}
				}
			%>

		</table>
	</logic:present>

	<logic:present name="<%=Constants.REQUEST_PLUGINMANAGE_PAGEBEAN%>">
		<p align="right">
		<form action="/SystemPlugin.do" method="get"
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
