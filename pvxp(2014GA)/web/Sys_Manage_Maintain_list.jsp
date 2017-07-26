<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="1" />

<%@page import="java.util.*,java.text.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>


<html:html locale="true">
<head>
	<title>报修记录列表</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
			function getPage(goPage) {
				document.MaintainList_form.page.value = goPage;
				document.MaintainList_form.submit();
			}

			function go() {
				var page = document.all.goPage.value;
				if (page.length>0 && page.match(/\D/)==null) {
					parent.showit();
					getPage(document.all.goPage.value)
				}
			}

			function find() {
				document.all.devno.value = document.all.no.value;
				document.all.date1.value = document.all.da1.value;
				document.all.date2.value = document.all.da2.value;
				
				parent.showit();
				getPage(1)
			}
			
			
		</script>
</head>
<%
	Vector maintainVector = (Vector) request.getAttribute(Constants.REQUEST_MAINTAIN_VECTOR);
	String totalMaintainCount = (String) request.getAttribute(Constants.REQUEST_MAINTAIN_TOTALMAINTAINCOUNT);
	String totalPages = (String) request.getAttribute(Constants.REQUEST_MAINTAIN_TOTALPAGES);
	String currentPage = (String) request.getAttribute(Constants.REQUEST_MAINTAIN_CURRENTPAGE);
	int int_totalMaintainCount = 0;
	int int_totalPages = 0;
	int int_currentPage = 0;
	int len = 0;
	
	try {
		int_totalPages = Integer.parseInt(totalPages);
		int_currentPage = Integer.parseInt(currentPage);
	} catch (Exception e) {
		
	}
	
	if (maintainVector != null) {
		len = maintainVector.size();
	}
	
	PubUtil myPubUtil = new PubUtil();
	CharSet charSet = new CharSet();
	int power = new OperatorModel().getPower(0, request);
	boolean hasPower = power == 2 || power == 3;
%>
<body onload="parent.hidit();">
	<!-- head -->
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" valign="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP </font>---
				<font class="location">报修记录列表</font>
			</td>
		</tr>
	</table>
	<table width="100%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<tr class="list_td_title">
			<html:form action="/MaintainList.do" styleId="MaintainList_form"
				target="pvmain">
				<td>
					设备编号：
					<input type="text" name="no" size="10" maxlength="12" />
					&nbsp;&nbsp; 起始日期：
					<input type="text" name="da1" size="15" maxlength="8" />
					&nbsp;&nbsp; 结束日期：
					<input type="text" name="da2" size="15" maxlength="8" />
					&nbsp;&nbsp;
					<font color="red">（默认显示所有记录）</font>
				</td>
				<td width="150" align="center">
					<img src="images/default/bt_find.gif" style="cursor:hand"
						onclick="find();">
					<%
					if (hasPower) {
					%>
					<img src="images/default/bt_add.gif" style="cursor:hand"
						onclick="parent.parent.goUrl('Maintain.jsp')">
					<%
					}
					%>
				</td>
				<html:hidden property="devno" />
				<html:hidden property="date1" />
				<html:hidden property="date2" />
				<html:hidden property="page" />
				<html:hidden property="pagesize" value="10" />
			</html:form>
		</tr>
	</table>

	<script>
			document.all.no.value = document.all.devno.value;
			document.all.da1.value = document.all.date1.value;
			document.all.da2.value = document.all.date2.value;
		</script>

	<br>

	<!-- body -->
	<table width="100%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<tr align="center">
			<td class="list_td_title">
				设备编号
			</td>
			<td class="list_td_title">
				所属机构
			</td>
			<td class="list_td_title">
				故障部件
			</td>
			<td class="list_td_title">
				故障现象
			</td>
			<td class="list_td_title">
				报修日期
			</td>
			<td class="list_td_title">
				报修时间
			</td>
			<td class="list_td_title">
				报修人
			</td>
			<td class="list_td_title">
				状态
			</td>
		</tr>
		<%
			String[] maintainItem = null;
			String bank = "";
			String part = "";
			String trbphen = "";
			String temp = "";
			String trbdate = "";
			String trbtime = "";
			String state = "";
			String repairs = "";
			DevErrorsUtil myDevErrorsUtil = new DevErrorsUtil();
			String[][] tmpArray = myDevErrorsUtil.getSubDevice();
			int devicenum = tmpArray.length;
			for (int i = 0; i < len; i++) {
				maintainItem = (String[]) maintainVector.get(i);
				bank = charSet.db2web(maintainItem[1]);
				repairs = charSet.db2web(maintainItem[6]);
				trbphen = charSet.db2web(maintainItem[7]);
				
				part = maintainItem[2];
				
				for (int j = 0; j < devicenum; j++) {
					if (part.equals(tmpArray[j][0])) {
				part = tmpArray[j][1];
					}
				}
				
				temp = maintainItem[3];
				if (temp != null && temp.length() == 8) {
					trbdate = temp.substring(0, 4) + "-" + temp.substring(4, 6) + "-" + temp.substring(6, 8);
				}
				temp = maintainItem[4];
				if (temp != null && temp.length() == 6) {
					trbtime = temp.substring(0, 2) + ":" + temp.substring(2, 4) + ":" + temp.substring(4, 6);
				}
				
				state = maintainItem[5];
				if (state.equals("0")) {
					state = "未处理";
				} else if (state.equals("1")) {
					state = "已处理";
				}
		%>
		<tr align="left" class="list_tr<%=i % 2%>">
			<td>
				&nbsp;
				<b><a href="#" alt="点击查看详细信息"
					onclick="javascript:parent.parent.getMaintainDetail('<%=maintainItem[0]%>', '<%=maintainItem[2]%>', '<%=maintainItem[3]%>', '<%=maintainItem[4]%>')"><%=maintainItem[0]%>
				</a> </b>
			</td>
			<td>
				&nbsp;
				<%=bank%>
			</td>
			<td>
				&nbsp;
				<%=part%>
			</td>
			<td>
				&nbsp;
				<%=trbphen%>
			</td>
			<td>
				&nbsp;
				<%=trbdate%>
			</td>
			<td>
				&nbsp;
				<%=trbtime%>
			</td>
			<td>
				&nbsp;
				<%=repairs%>
			</td>
			<td>
				&nbsp;
				<%=state%>
			</td>
		</tr>
		<%
		}
		%>
	</table>

	<!-- foot -->
	<%
	if (int_totalMaintainCount >= 0) {
	%>
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" valign="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<form>
					<font color=blue>PowerView XP</font> ---
					<font class="location">报修记录列表</font>
			</td>
			<td align="right" valign="center">
				报修记录
				<%=totalMaintainCount%>
				条 共
				<%=totalPages%>
				页 当前为第
				<%=currentPage%>
				页&nbsp;&nbsp;
				<%
				if (int_currentPage > 1) {
				%>
				<a href="#"
					onClick="parent.showit();getPage(<%=int_currentPage - 1%>)">[上一页]</a>&nbsp;&nbsp;
				<%
				}
				%>
				<%
				if (int_currentPage < int_totalPages) {
				%>
				<a href="#"
					onClick="parent.showit();getPage(<%=int_currentPage + 1%>)">[下一页]</a>&nbsp;&nbsp;
				<%
				}
				%>
				<%
				if (int_totalPages > 1) {
				%>
				转到第
				<input type="text" name="goPage" size="2" class="page_input">
				页
			</td>
			<td>
				<img src="images/default/bt_go.gif" onClick="go()"
					style="cursor:hand">
				<%
				}
				%>
			</td>
			</form>

		</tr>
	</table>
	<%
	}
	%>
</body>
</html:html>
