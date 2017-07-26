<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="13" minpower="1" />


<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>
<html:html locale="true">
<head>
	<title><bean:message key="pvxp.devft.list" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
			function selectAll(checkBox, btn){
			  var hassall = btn.src.substr(btn.src.lastIndexOf("/")) == "/bt_clear.gif";
			  if( checkBox != null ){
			    if( checkBox.length != undefined ){
			      if( hassall ){
			        for( i=0;i<checkBox.length;i++ ){
			          if( checkBox[i].checked == true ) checkBox[i].checked=false;
			        }
			        hassall = false;
			        btn.src="images/default/bt_selectall.gif";
			      }else{
			        for( i=0;i<checkBox.length;i++ ){
			          if( checkBox[i].checked == false ) checkBox[i].checked=true;
			        }
			        hassall = true;
			        btn.src="images/default/bt_clear.gif";
			      }
			    }else{
			      if( hassall ){
			        if( checkBox.checked == true ) checkBox.checked=false;
			        hassall = false;
			        btn.src="images/default/bt_selectall.gif";
			      }else{
			        if( checkBox.checked == false ) checkBox.checked=true;
			        hassall = true;
			        btn.src="images/default/bt_clear.gif";
			      }
			    }
			  }
			}

			function isSelectedMultiBox(CheckBox){
				if(isNaN(CheckBox.length)){
					if (CheckBox.checked == true) {
						return true;
					} else {
						return false;
					}
				} else {
					for(var i=0;i<CheckBox.length;i++){
						if(CheckBox[i].checked == true){
							return true;
						}
					}
					return false;
				}
			}

			function confirmDelete(CheckBox) {
				if(!isSelectedMultiBox(CheckBox))	{
				  alert("请至少选择一家设备厂商");
				  return false;
				} else {
				  var isOK = confirm("确定要删除设备厂商信息？");
				  if(isOK) {
				    parent.showit();
				  }
				  return isOK;
				}
			}

			function go() {
				var page = document.all.page.value;
				if (page.length>0 && page.match(/\D/)==null) {
					parent.showit();
					parent.parent.getDevftList(document.all.page.value)
				}
			}
		</script>
</head>
<%
	Vector devftVector = (Vector) request.getAttribute(Constants.REQUEST_DEVFT_VECTOR);
	String totalDevftCount = (String) request.getAttribute(Constants.REQUEST_DEVFT_TOTALDEVFTCOUNT);
	String totalPages = (String) request.getAttribute(Constants.REQUEST_DEVFT_TOTALPAGES);
	String currentPage = (String) request.getAttribute(Constants.REQUEST_DEVFT_CURRENTPAGE);
	int int_totalDevftCount = 0;
	int int_totalPages = 0;
	int int_currentPage = 0;
	int len = 0;
	
	try {
		int_totalPages = Integer.parseInt(totalPages);
		int_currentPage = Integer.parseInt(currentPage);
	} catch (Exception e) {
		
	}
	
	if (devftVector != null) {
		len = devftVector.size();
	}
	
	int power = new OperatorModel().getPower(0, request);
	boolean hasPower = power == 2 || power == 3;
%>
<body onload="parent.hidit();">
	<!-- head -->
	<html:form action="/DevftDel.do" method="post"
		onsubmit="return confirmDelete(devftArry)">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location"><bean:message key="pvxp.devft.list" />
					</font>
				</td>
				<td align="center" width="100">
					<%
					if (hasPower) {
					%>
					<img src="images/default/bt_add.gif" style="cursor:hand"
						onclick="parent.parent.goUrl('AddDevft.jsp')">
					<%
					}
					%>
				</td>
			</tr>
		</table>
		<!-- body -->
		<table width="100%" cellspacing="1" cellpadding="3"
			class="list_table_border">
			<tr align="center">
				<td class="list_td_title" width="15%">
					<bean:message key="pvxp.devft.devftno" />
				</td>
				<td class="list_td_title" width="15%">
					<bean:message key="pvxp.devft.devftname" />
				</td>
				<td class="list_td_title" width="15%">
					<bean:message key="pvxp.devft.addr" />
				</td>
				<td class="list_td_title" width="15%">
					<bean:message key="pvxp.devft.contact" />
				</td>
				<td class="list_td_title" width="15%">
					<bean:message key="pvxp.devft.phone" />
				</td>
				<td class="list_td_title" width="15%">
					<bean:message key="pvxp.devft.mobile" />
				</td>

				<%
				if (hasPower) {
				%>
				<td class="list_td_title" id="delbt">
					<input type="image" src="images/default/bt_delete.gif"
						align="absmiddle" />
					&nbsp;&nbsp;
					<img style="cursor:hand" src="images/default/bt_selectall.gif"
						onclick="selectAll(document.all.devftArry, this)"
						align="absmiddle">
				</td>
				<%
				}
				%>
			</tr>
			<%
				String[] devftItem = null;
				for (int i = 0; i < len; i++) {
					devftItem = (String[]) devftVector.get(i);
			%>
			<tr align="left" class="list_tr<%=i % 2%>">
				<td>
					&nbsp;
					<b><a href="#" alt="点击查看详细信息"
						onclick="javascript:parent.parent.getDevftDetail('<%=devftItem[0]%>')"><%=devftItem[0]%>
					</a>
					</b>
				</td>
				<td>
					&nbsp;
					<%=devftItem[1]%>
				</td>
				<td>
					&nbsp;
					<%=devftItem[8]%>
				</td>
				<td>
					&nbsp;
					<%=devftItem[2]%>
				</td>
				<td>
					&nbsp;
					<%=devftItem[3]%>
				</td>
				<td>
					&nbsp;
					<%=devftItem[4]%>
				</td>
				<%
				if (hasPower) {
				%>
				<td align="center">
					<html:multibox property="devftArry" styleId="devftArry"
						value="<%=devftItem[0]%>" />
				</td>
				<%
				}
				%>
			</tr>
			<%
			}
			%>
		</table>


		<!-- foot -->
		<%
		if (int_totalDevftCount >= 0) {
		%>
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<form>
						<font color=blue>PowerView XP</font> ---
						<font class="location"><bean:message key="pvxp.devft.list" />
						</font>
				</td>
				<td align="right" valign="center">
					设备厂商
					<%=totalDevftCount%>
					家 共
					<%=totalPages%>
					页 当前为第
					<%=currentPage%>
					页&nbsp;&nbsp;
					<%
					if (int_currentPage > 1) {
					%>
					<a href="#"
						onClick="parent.showit();parent.parent.getDevftList(<%=int_currentPage - 1%>)">[上一页]</a>&nbsp;&nbsp;
					<%
					}
					%>
					<%
					if (int_currentPage < int_totalPages) {
					%>
					<a href="#"
						onClick="parent.showit();parent.parent.getDevftList(<%=int_currentPage + 1%>)">[下一页]</a>&nbsp;&nbsp;
					<%
					}
					%>
					<%
					if (int_totalPages > 1) {
					%>
					转到第
					<input type="text" name="page" size="2" class="page_input">
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
	</html:form>
</body>
</html:html>
