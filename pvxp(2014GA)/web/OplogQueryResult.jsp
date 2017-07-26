<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="7" minpower="1" />
<%
	PubUtil myPubUtil = new PubUtil();
	List myList = (List) request.getAttribute(Constants.REQUEST_OPLOG_RESULT);
%>
<html:html locale="true">
<head>
	<title><bean:message key="pvxp.oplog.title" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>
	<script>
	function go() {
		var goPage = document.all.page.value;
		if (goPage.length>0 && goPage.match(/\D/)==null) {
			parent.hidit();
			return true;
		} else {
			return false;
		}
	}
</script>
</head>
<body onload="javascript:parent.hidit();">
	<div id="show" style="position:absolute; width="
		100%" z-index:1; left: 0px; top: 0px;" align="center">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font> ---
					<font class="location"><bean:message key="pvxp.oplog.title" />
					</font>
				</td>
			</tr>
		</table>

		<logic:present name="<%=Constants.REQUEST_OPLOG_RESULT%>">
			<table align="center" width="100%" border="0" cellpadding="1"
				cellspacing="1" class="list_table_border">
				<tr align="center" class="list_td_title">
					<td width="15%">
						<bean:message key="pvxp.operator.bankid" />
					</td>
					<td width="10%">
						<bean:message key="pvxp.operator.operid" />
					</td>
					<td width="10%">
						<bean:message key="pvxp.oplog.date" />
					</td>
					<td width="10%">
						<bean:message key="pvxp.oplog.time" />
					</td>
					<td width="10%">
						<bean:message key="pvxp.oplog.trcd" />
					</td>
					<td width="10%">
						<bean:message key="pvxp.oplog.type" />
					</td>
					<td width="35%">
						<bean:message key="pvxp.oplog.info" />
					</td>
				</tr>
				<%
					String myBankid = "";
					String myTrcd = "";
					String myType = "";
					String myInfo = "";
					String myHead = "";
					String param1 = "";
					String param2 = "";
					String param3 = "";
					String param4 = "";
					String param5 = "";
					for (int i = 0; i < myList.size(); i++) {
						Oplog myOplog = (Oplog) myList.get(i);
						myBankid = myPubUtil.dealNull(myOplog.getBankid()).trim();
						myTrcd = "pvxp.oplog.trcd." + myPubUtil.dealNull(myOplog.getTrcd()).trim();
						myType = "pvxp.oplog.type." + myPubUtil.dealNull(myOplog.getType()).trim();
						myInfo = myPubUtil.dealNull(myOplog.getInfo()).trim();//System.out.println("info="+myInfo);
						StringTokenizer wb = new StringTokenizer(myInfo, "|");
						try {
							myHead = wb.nextToken();
							param1 = wb.nextToken();
							param2 = wb.nextToken();
							param3 = wb.nextToken();
							param4 = wb.nextToken();
							param5 = wb.nextToken();
						} catch (Exception e) {
							//System.out.println("e="+e);
						}
				%>
				<tr class="list_tr<%=i % 2%>" align="center">
					<%
								if (myBankid.equals("")) {
								myBankid = "pvxp.operator.superop";
					%>
					<td>
						<bean:message key="<%=myBankid%>" />
					</td>
					<%
					} else {
					%>
					<td>
						<%=myOplog.getBankid()%>
					</td>
					<%
					}
					%>
					<td>
						<b><a href="#"
							onclick="javascript:parent.parent.getOperDetail('<%=myOplog.getOperid()%>')"
							alt="<bean:message key="pvxp.operator.more"/>"> <%=myOplog.getOperid()%>
						</a>
						</b>
					</td>
					<td>
						<%=(myOplog.getOpdate()).substring(0, 4)%>
						-
						<%=(myOplog.getOpdate()).substring(4, 6)%>
						-
						<%=(myOplog.getOpdate()).substring(6, 8)%>
					</td>
					<td>
						<%=(myOplog.getOptime()).substring(0, 2)%>
						:
						<%=(myOplog.getOptime()).substring(2, 4)%>
						:
						<%=(myOplog.getOptime()).substring(4, 6)%>
					</td>
					<td>
						<bean:message key="<%=myTrcd%>" />
					</td>
					<td>
						<bean:message key="<%=myType%>" />
					</td>
					<td>
						<bean:message key="<%=myHead%>" arg0="<%=param1%>"
							arg1="<%=param2%>" arg2="<%=param3%>" arg3="<%=param4%>"
							arg4="<%=param5%>" />
					</td>
				</tr>
				<%
				}
				%>
			</table>
		</logic:present>

		<logic:present name="<%=Constants.REQUEST_OPLOG_PAGEBEAN%>">
			<p align="right">
			<form action="OplogQuery.do" method="get" onsubmit="return go()">
				<bean:message key="pvxp.page.title.oplog" />
				<bean:write name="<%=Constants.REQUEST_OPLOG_PAGEBEAN%>"
					property="totalRow" />
				<bean:message key="pvxp.page.count" />
				<logic:notEqual name="<%=Constants.REQUEST_OPLOG_PAGEBEAN%>"
					property="totalPage" value="0">
					<bean:message key="pvxp.page.tt" />
					<bean:write name="<%=Constants.REQUEST_OPLOG_PAGEBEAN%>"
						property="totalPage" />
					<bean:message key="pvxp.page.page" />
					<bean:message key="pvxp.page.currentpage.pre" />
					<bean:write name="<%=Constants.REQUEST_OPLOG_PAGEBEAN%>"
						property="currentPage" />
					<bean:message key="pvxp.page.page" />
				</logic:notEqual>
				<logic:equal name="<%=Constants.REQUEST_OPLOG_PAGEBEAN%>"
					property="hasPrevious" value="true">&nbsp;<html:link
						href="#"
						onclick="parent.showit();page.value = --currentPage.value;submit();">
						<bean:message key="pvxp.page.prepage" />
					</html:link>&nbsp;</logic:equal>
				<logic:equal name="<%=Constants.REQUEST_OPLOG_PAGEBEAN%>"
					property="hasNext" value="true">&nbsp;<html:link
						href="#"
						onclick="parent.showit();page.value = ++currentPage.value;submit();">
						<bean:message key="pvxp.page.nextpage" />
					</html:link>&nbsp;</logic:equal>
				<logic:equal name="<%=Constants.REQUEST_OPLOG_PAGEBEAN%>"
					property="hasMultiPage" value="true">
					<bean:message key="pvxp.page.changepageto.pre" />
					<input type="text" name="page" size="3" class="page_input">
					<bean:message key="pvxp.page.page" />
					<input type="image" src="images/default/bt_go.gif"
						onFocus="this.blur()" align="absmiddle">
				</logic:equal>
				<html:hidden name="<%=Constants.REQUEST_OPLOG_PAGEBEAN%>"
					property="currentPage" />
				<html:hidden name="<%=Constants.REQUEST_OPLOG_PAGEBEAN%>"
					property="totalRow" />
				<input type="hidden" name="pagesize" value="10">
				<input type="hidden" name="operid"
					value="<%=request.getParameter("operid")%>">
				<input type="hidden" name="bopdate"
					value="<%=request.getParameter("bopdate")%>">
				<input type="hidden" name="eopdate"
					value="<%=request.getParameter("eopdate")%>">
				<input type="hidden" name="trcd"
					value="<%=request.getParameter("trcd")%>">
				<input type="hidden" name="type"
					value="<%=request.getParameter("type")%>">
			</form>
			</p>
		</logic:present>


	</div>
</body>
</html:html>
