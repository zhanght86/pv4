<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>
<%@page import="com.lcjr.pvxp.actionform.*"%>

<app:validateCookie />
<app:checkpower funcid="0" minpower="1" />

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.dev.list" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
			function toggleSelectAll(Button, Checkbox) {
				var hassall = Button.src.substr(Button.src.lastIndexOf("/")) == "/bt_clear.gif";
				if( Checkbox != null ){
			    if( Checkbox.length != undefined ){
			      if( hassall ){
			        for( i=0;i<Checkbox.length;i++ ){
			          if( Checkbox[i].checked == true ) Checkbox[i].checked=false;
			        }
			        hassall = false;
			        Button.src="images/default/bt_selectall.gif";
			      }else{
			        for( i=0;i<Checkbox.length;i++ ){
			          if( Checkbox[i].checked == false ) Checkbox[i].checked=true;
			        }
			        hassall = true;
			        Button.src="images/default/bt_clear.gif";
			      }
			    }else{
			      if( hassall ){
			        if( Checkbox.checked == true ) Checkbox.checked=false;
			        hassall = false;
			        Button.src="images/default/bt_selectall.gif";
			      }else{
			        if( Checkbox.checked == false ) Checkbox.checked=true;
			        hassall = true;
			        Button.src="images/default/bt_clear.gif";
			      }
			    }
			  }
			}

			function confirmDelete(CheckBox) {
				if(!isSelectedMultiBox(CheckBox))	{
				  alert("请选择至少一种设备");
				  return false;
				} else {
				  var isOK = confirm("是否确定要删除设备？");
				  if(isOK) {
				    parent.showit();
				  }
				  return isOK;
				}
			}

			function getPage(goPage) {
				document.DevList_form.page.value = goPage;
				document.DevList_form.submit();
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
				document.all.devip.value = document.all.ip.value;
				document.all.tellerno.value = document.all.Tellerno.value;
				parent.showit();
				getPage(1)
			}

			function changeBank() {
				if (document.all.bankID.value == "A") {
					document.all.includeSubBanklay.style.visibility="hidden";
				} else {
					document.all.includeSubBanklay.style.visibility="visible";
				}
			}
		</script>
	<script language="JavaScript" src="js/pv.js"></script>
</head>
<%
	PubUtil pubUtil = new PubUtil();
	//log.debug("devVector=" + devVector);
	String bankID = ((DevListForm) request.getAttribute("DevListForm")).getBankID();
	Vector devVector = (Vector) request.getAttribute(Constants.REQUEST_DEVLIST_DEVVECTOR);
	int totalDevCount = ((Integer) request.getAttribute(Constants.REQUEST_DEVLIST_TOTALDEVCOUNT)).intValue();
	int totalPages = ((Integer) request.getAttribute(Constants.REQUEST_DEVLIST_TOTALPAGES)).intValue();
	int currentPage = ((Integer) request.getAttribute(Constants.REQUEST_DEVLIST_CURRENTPAGE)).intValue();
	int len = devVector.size();
	int power = new OperatorModel().getPower(0, request);
	boolean hasPower = power == 2 || power == 3;
%>
<body onload="javascript:parent.hidit();">
	<!-- head -->
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr valign="middle">
			<td align="left" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP </font>---
				<font class="location"><bean:message key="pvxp.dev.list" />
				</font>
			</td>
		</tr>
	</table>

	<table width="100%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<tr class="list_td_title">
			<html:form action="/DevList.do" styleId="DevList_form"
				target="pvmain">
				<td>
					请选择机构
					<%
				if (power == 3) {
				%>
					<app:bankselect property="bankID" onChange="changeBank()"
						defaultValue="<%=bankID%>" key="pvxp.bankinfo.select.all"
						keyValue="A" />
					<%
					} else {
					%>
					<app:bankselect property="bankID" onChange="changeBank()"
						defaultValue="<%=bankID%>" />
					<%
					}
					%>
					<span id="includeSubBanklay"><html:checkbox
							property="includeSubBank" />包含子机构</span>&nbsp;&nbsp;&nbsp;&nbsp;
					<bean:message key="pvxp.dev.no" />
					<input type="text" name="no" size="10" maxlength="10" />
					&nbsp;&nbsp;
					<bean:message key="prompt.tellerno" />
					<input type="text" name="Tellerno" size="10" maxlength="10" />
					&nbsp;&nbsp;
					<bean:message key="pvxp.dev.ip" />
					<input type="text" name="ip" size="15" maxlength="15" />
				</td>
				
			
				<td width="150" align="center">
				
					
					<img src="images/default/bt_find.gif" style="cursor:hand" onClick="find()">
					&nbsp;
					
					<img src="images/default/bt_add.gif" style="cursor:hand" onclick="parent.parent.goUrl('AddDev.jsp')">
					&nbsp;
					<img src="images/default/dout.gif" style="cursor:hand" 	 onclick="parent.parent.goUrl('DevInfoToExcel.jsp')">
					&nbsp;
				</td>
				<html:hidden property="devno" />
				<html:hidden property="devip" />
				<html:hidden property="tellerno" />
				<html:hidden property="page" />
				<html:hidden property="pagesize" value="10" />
			</html:form>
		</tr>
	</table>

	<script>
			document.all.no.value = document.all.devno.value;
			document.all.ip.value = document.all.devip.value;
		</script>

	<br>
	<!-- body -->
	<table width="100%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<html:form action="/DevDelete.do"
			onsubmit="return confirmDelete(devList)">
			<tr align="center" class="list_td_title">
				<td>
					<bean:message key="pvxp.dev.no" />
				</td>
				<td>
					<bean:message key="pvxp.dev.type" />
				</td>
				<td>
					<bean:message key="pvxp.dev.bankinfo" />
				</td>
				<td>
					<bean:message key="pvxp.dev.ip" />
				</td>
				<td>
					<bean:message key="pvxp.dev.state" />
				</td>
				<td>
					<bean:message key="pvxp.dev.address" />
				</td>
				<td>
					<bean:message key="prompt.devkey" />
				</td>
				<%
				if (hasPower) {
				%>
				<td width="150">
					<html:image src="images/default/bt_delete.gif" />
					&nbsp;
					<img src="images/default/bt_selectall.gif" style="cursor:hand"
						id="btn_selectAll"
						onclick="toggleSelectAll(document.all.btn_selectAll, document.all.devList)">
				</td>
				<%
				}
				%>
			</tr>
			<%
				String[] devItem = null;
				for (int i = 0; i < len; i++) {
					devItem = (String[]) devVector.get(i);
					
					String devNo = devItem[0];
					String devType = devItem[1];
					String devBank = devItem[2];
					String devIp = devItem[3];
					String devState = "pvxp.dev.state." + devItem[4];
					String devAddress = devItem[5];
					String edition = devItem[6];
			%>
			<tr align="left" class="list_tr<%=i % 2%>">
				<td>
					&nbsp;
					<b><a href="#"
						onclick="parent.parent.getDevDetail('<%=devNo%>')"><%=devNo%>
					</a> </b>
				</td>
				<td>
					&nbsp;
					<%=devType%>
				</td>
				<td>
					&nbsp;
					<%=devBank%>
				</td>
				<td>
					&nbsp;
					<%=devIp%>
				</td>
				<td>
					&nbsp;
					<bean:message key="<%=devState%>" />
				</td>
				<td>
					&nbsp;
					<%=devAddress%>
				</td>
				<td>
					&nbsp;
					<%=edition%>
				</td>
				<%
				if (hasPower) {
				%>
				<td align="center">
					<html:multibox property="devList" styleId="devList"
						value="<%=devNo%>" />
				</td>
				<%
				}
				%>
			</tr>
			<%
			}
			%>
		</html:form>
	</table>

	<!-- foot -->
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" align="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP</font> ---
				<font class="location"><bean:message key="pvxp.dev.list" />
				</font>
			</td>
			<td align="right" align="center">
				设备
				<%=totalDevCount%>
				台&nbsp;&nbsp;共
				<%=totalPages%>
				页&nbsp;&nbsp;当前为第
				<%=currentPage%>
				页&nbsp;&nbsp;
				<%
				if (currentPage > 1) {
				%>
				<a href="#" onClick="parent.showit();getPage(<%=currentPage - 1%>)">[上一页]</a>&nbsp;&nbsp;
				<%
				}
				%>
				<%
				if (currentPage < totalPages) {
				%>
				<a href="#" onClick="parent.showit();getPage(<%=currentPage + 1%>)">[下一页]</a>&nbsp;&nbsp;
				<%
				}
				%>
				<%
				if (totalPages > 1) {
				%>
				转到第
				<input type="text" name="goPage" size="2" class="page_input">
				页
			</td>
			<td>
				<img src="images/default/bt_go.gif" onclick="go()"
					style="cursor:hand">
				<%
				}
				%>
			</td>
		</tr>
	</table>

	<script>
			if(document.all.bankID.value=="A"){
				document.all.includeSubBanklay.style.visibility="hidden";
			}
			if (<%=!hasPower%>) {
				document.all.AddDev.style.display="none";
			}
		</script>
</body>
</html:html>
