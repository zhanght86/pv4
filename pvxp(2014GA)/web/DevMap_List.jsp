<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.pojo.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="1" />

<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script>
function goFullscreen(targeturl,mtarget){
	var newwin=window.open(targeturl,mtarget,'scrollbars');
	if(document.all){
		newwin.moveTo(0,0);
		newwin.resizeTo(screen.width,screen.height);
	}
}
function goEditMap(bankid){
	window.location="DevMapEdit.jsp?bankid="+bankid;
}
function goMapMoni(bankid){
	goFullscreen('DevMapMoni_Fram.jsp?bankid='+bankid,'DevMapMoni');
}

function go() {
	var goPage = document.all.page.value;
	if (goPage.length>0 && goPage.match(/\D/)==null) {
		parent.parent.getDevMapList(goPage);
	}
	return false;
}

</script>
</head>
<%
	PubUtil myPubUtil = new PubUtil();
	CharSet myCharSet = new CharSet();
	Vector devMapBankVector = (Vector) request.getAttribute(Constants.REQUEST_DEVMAP_VECTOR);
	int totalDevMapCount = ((Integer) request.getAttribute(Constants.REQUEST_DEVMAP_TOTALDEVCOUNT)).intValue();
	int totalPages = ((Integer) request.getAttribute(Constants.REQUEST_DEVMAP_TOTALPAGES)).intValue();
	int currentPage = ((Integer) request.getAttribute(Constants.REQUEST_DEVMAP_CURRENTPAGE)).intValue();
	int len = 0;
	String myoperid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
	if (devMapBankVector != null)
		len = devMapBankVector.size();
	OperatorModel myOperatorModel = new OperatorModel();
	int mypower = myOperatorModel.getPower(5, request);
%>
<body onload="javascript:parent.hidit();">

	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" align="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP </font> ---
				<font class="location"><bean:message key="pvxp.devmap.list" />
				</font>
			</td>
			<td align="right">
				<!--<a href="DevMapCreate.jsp" onClick="parent.showit();" onFocus="this.blur()"><img src="images/default/bt_add.gif" border="0"></a>-->
			</td>
		</tr>
	</table>
	<!-- body -->
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr align="center">
			<td class="list_td_title">
				<center>
					<b>所属机构</b>
				</center>
			</td>
			<td class="list_td_title">
				<center>
					<b>创建者</b>
				</center>
			</td>
			<td class="list_td_title">
				<center>
					<b>最后修改者</b>
				</center>
			</td>
			<td class="list_td_title">
				<center>
					<b>最后修改时间</b>
				</center>
			</td>
			<td class="list_td_title">
				<center>
					<b>操作</b>
				</center>
			</td>
		</tr>
		<%
				for (int i = 0; i < len; i++) {
				Bankinfo mybank = (Bankinfo) devMapBankVector.get(i);
				if (mybank != null) {
					DevMapModel tmpDevMapModel = new DevMapModel(myPubUtil.dealNull(mybank.getBankid()).trim());
					if (tmpDevMapModel != null) {
				DevMap myDevMap = tmpDevMapModel.getDevMap();
				//if( myDevMap!=null ){
				String ownerid = "";
				String ownername = "";
				String editerid = "";
				String lastedittime = "";
				if (myDevMap != null) {
					try {
						ownerid = myPubUtil.dealNull(myDevMap.getOwner()).trim();
						editerid = myPubUtil.dealNull(myDevMap.getLastediter()).trim();
						lastedittime = myPubUtil.dealNull(myDevMap.getLastedittime()).trim();
					} catch (Exception e) {
						ownerid = "";
						editerid = "";
						lastedittime = "";
					}
				}
				try {
					ownername = myCharSet.db2web(((new OperatorModel()).getOperator(ownerid)).getName()).trim();
				} catch (Exception e) {
					ownername = "";
				}
				
				String editername = "";
				try {
					editername = myCharSet.db2web(((new OperatorModel()).getOperator(editerid)).getName()).trim();
				} catch (Exception e) {
					editername = "";
				}
		%>
		<tr align="left" class="list_tr<%=i % 2%>">
			<td>
				&nbsp;
				<%=myCharSet.db2web(myPubUtil.dealNull(mybank.getBanknm())).trim()%>
			</td>
			<td>
				&nbsp;
				<%=ownername%>
			</td>
			<td>
				&nbsp;
				<%=editername%>
			</td>
			<td>
				&nbsp;
				<%=lastedittime%>
			</td>
			<%
			if (myDevMap != null) {
			%>
			<td align="center">
				<%
				if (mypower > 0) {
				%>
				<a href="#"
					onClick="goMapMoni('<%=myPubUtil.dealNull(mybank.getBankid()).trim()%>')"
					onFocus="this.blur()"><img src="images/default/bt_moni.gif"
						border="0"> </a>
				<%
				}
				%>
				<%
				if (mypower > 1) {
				%>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#"
					onClick="goEditMap('<%=myPubUtil.dealNull(mybank.getBankid()).trim()%>')"
					onFocus="this.blur()"><img src="images/default/bt_modify.gif"
						border="0"> </a>
				<%
				}
				%>
			</td>
			<%
			} else {
			%>
			<td align="center">
				<%
				if (mypower > 1) {
				%>
				<a
					href="DevMapCreate.jsp?bankid=<%=myPubUtil.dealNull(mybank.getBankid()).trim()%>"
					onFocus="this.blur()"><img src="images/default/bt_create.gif"
						border="0"> </a>
				<%
				}
				%>
			</td>
			<%
			}
			%>
		</tr>
		<%
					//}
					}
				}
			}
		%>
	</table>





	<!-- foot -->
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" valign="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<form onsubmit="return go()">
					<font color=blue>PowerView XP</font> ---
					<font class="location"><bean:message key="pvxp.devmap.list" />
					</font>
			</td>
			<td align="right" valign="center">
				设备地图
				<%=totalDevMapCount%>
				张 共
				<%=totalPages%>
				页 当前为第
				<%=currentPage%>
				页&nbsp;&nbsp;
				<%
				if (currentPage > 1) {
				%>
				<a href="#"
					onClick="parent.parent.getDevMapList(<%=currentPage - 1%>)">[上一页]</a>&nbsp;&nbsp;
				<%
				}
				%>
				<%
				if (currentPage < totalPages) {
				%>
				<a href="#"
					onClick="parent.parent.getDevMapList(<%=currentPage + 1%>)">[下一页]</a>&nbsp;&nbsp;
				<%
				}
				%>
				<%
				if (totalPages > 1) {
				%>
				转到第
				<input type="text" name="page" size="2" value="<%=currentPage%>"
					class="page_input">
				页
				<input type="image" src="images/default/bt_go.gif"
					onfocus="this.blur()" align="absmiddle">
				<%
				}
				%>
			</td>
			</form>
		</tr>
	</table>

</body>
</html:html>
