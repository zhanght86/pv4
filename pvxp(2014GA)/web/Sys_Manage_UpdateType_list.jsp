<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="13" minpower="2" />

<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.updatetype.list" />
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
				  alert("请选择至少一种更新类型");
				  return false;
				} else {
				  var isOK = confirm("是否确定要删除更新类型？");
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
					parent.parent.getUpdateTypeList(document.all.page.value)
				}
			}
		</script>
</head>
<%
	Vector updateTypeVector = (Vector) request.getAttribute(Constants.REQUEST_UPDATETYPE_VECTOR);
	int totalDevCount = ((Integer) request.getAttribute(Constants.REQUEST_UPDATETYPE_TOTALDEVCOUNT)).intValue();
	int totalPages = ((Integer) request.getAttribute(Constants.REQUEST_UPDATETYPE_TOTALPAGES)).intValue();
	int currentPage = ((Integer) request.getAttribute(Constants.REQUEST_UPDATETYPE_CURRENTPAGE)).intValue();
	int len = updateTypeVector.size();
	int power = new OperatorModel().getPower(0, request);
	boolean hasPower = power == 2 || power == 3;
%>
<body onload="parent.hidit();">
	<!-- head -->
	<html:form action="/UpdateTypeDel.do" method="post"
		onsubmit="return confirmDelete(updateTypeArry)">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location"><bean:message
							key="pvxp.updatetype.list" />
					</font>
				</td>
				<td align="center" width="100">
					<%
					if (hasPower) {
					%>
					<img src="images/default/bt_add.gif" style="cursor:hand"
						onclick="parent.parent.goUrl('AddUpdateType.jsp')">
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
				<td class="list_td_title" width="20%">
					<bean:message key="pvxp.updatetype.typeno" />
				</td>
				<td class="list_td_title" width="25%">
					<bean:message key="pvxp.updatetype.updatename" />
				</td>
				<td class="list_td_title" width="35%">
					<bean:message key="pvxp.updatetype.updateinfo" />
				</td>
				<%
				if (hasPower) {
				%>
				<td class="list_td_title" id="delbt">
					<input type="image" src="images/default/bt_delete.gif"
						align="absmiddle" />
					&nbsp;&nbsp;
					<img style="cursor:hand" src="images/default/bt_selectall.gif"
						onclick="selectAll(document.all.updateTypeArry, this)"
						align="absmiddle">
				</td>
				<%
				}
				%>
			</tr>
			<%
				String[] updateTypeItem = null;
				for (int i = 0; i < len; i++) {
					updateTypeItem = (String[]) updateTypeVector.get(i);
			%>
			<tr align="left" class="list_tr<%=i % 2%>">
				<td>
					&nbsp;
					<b><a href="#" alt="点击查看详细信息"
						onclick="javascript:parent.parent.getUpdateTypeDetail('<%=updateTypeItem[0]%>')"><%=updateTypeItem[0]%>
					</a>
					</b>
				</td>
				<td>
					&nbsp;
					<%=updateTypeItem[1]%>
				</td>
				<td>
					&nbsp;
					<%=updateTypeItem[2]%>
				</td>
				<%
				if (hasPower) {
				%>
				<td align="center">
					<html:multibox property="updateTypeArry" styleId="updateTypeArry"
						value="<%=updateTypeItem[0]%>" />
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
		if (totalDevCount >= 0) {
		%>
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<form>
						<font color=blue>PowerView XP</font> ---
						<font class="location"><bean:message
								key="pvxp.updatetype.list" />
						</font>
				</td>
				<td align="right" valign="center">
					更新类型
					<%=totalDevCount%>
					种 共
					<%=totalPages%>
					页 当前为第
					<%=currentPage%>
					页&nbsp;&nbsp;
					<%
					if (currentPage > 1) {
					%>
					<a href="#"
						onClick="parent.showit();parent.parent.getUpdateTypeList(<%=currentPage - 1%>)">[上一页]</a>&nbsp;&nbsp;
					<%
					}
					%>
					<%
					if (currentPage < totalPages) {
					%>
					<a href="#"
						onClick="parent.showit();parent.parent.getUpdateTypeList(<%=currentPage + 1%>)">[下一页]</a>&nbsp;&nbsp;
					<%
					}
					%>
					<%
					if (totalPages > 1) {
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
