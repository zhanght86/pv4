<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>
<%@ page errorPage="Exception.jsp"%>


<app:validateCookie/>
<app:checkpower funcid="2" minpower="1" />


<html:html locale="true">
<head>
	<title><bean:message key="pvxp.operator.list" /></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script>
var isselected = false;							// 标志是否全选(true-是,false-否)

	function selectall(box)
	{
		try{
		var i = 0;
		if(isNaN(box.length))
		{
			if(isselected)
			{
				box.checked = false;
				isselected = false;
				document.all.selall.src = 'images/default/bt_selectall.gif';
			}
			else
			{
				box.checked = true;
				isselected = true;
				document.all.selall.src = 'images/default/bt_remove.gif';
			}
		}
		else
		{
			if(isselected)
			{
				for(;i<box.length;i++)
					box[i].checked = false;
				isselected = false;
				document.all.selall.src = 'images/default/bt_selectall.gif';
			}
			else
			{
				for(;i<box.length;i++)
					box[i].checked = true;
				isselected = true;
				document.all.selall.src = 'images/default/bt_remove.gif';
			}
		}
		}catch(e){
		}
	}

	function checkForm() {
		var page = document.all.gopage.value;
		if (page.length>0 && page.match(/\D/)==null) {
			parent.showit();
			parent.parent.getOperList(document.all.gopage.value)
		}
	}
</script>
	<script language="JavaScript" src="js/pv.js"></script>
</head>
<body onload="javascript:parent.hidit();">
	<div id="show" style="left: 0px; top: 0px; width: 100%; height: 100%; position: absolute; z-index: 1;" align="center">

		<%
			String ttcount = (String) request.getAttribute(Constants.REQUEST_OPERATOR_TTCOUNT);
			String pagecount = (String) request.getAttribute(Constants.REQUEST_OPERATOR_PAGECOUNT);
			String mpage = (String) request.getAttribute(Constants.REQUEST_OPERATOR_PAGE);
			
			int int_ttcount = 0;
			int int_pagecount = 0;
			int int_page = 0;
			
			try {
				int_page = Integer.parseInt(mpage);
				int_pagecount = Integer.parseInt(pagecount);
			} catch (Exception e) {
				
			}
			
			Vector myVector = (Vector) request.getAttribute(Constants.REQUEST_OPERATOR_VECTOR);
			
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();
			
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			int myPower = 0;
			if (authlist.equals("*")) {
				myPower = 3;
			} else {
				myPower = Integer.parseInt(authlist.substring(2, 3));
			}
			int len = 0;
			if (myVector != null) {
				len = myVector.size();
			}
		%>
		
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location"><bean:message
							key="pvxp.operator.list" /> </font>
				</td>
				<td align="center" width="100">
					<%
					if (authlist.equals("*") || (authlist.substring(2, 3)).equals("2")) {
					%>
					<a href="#"
						onclick="javascript:parent.showit();parent.parent.goUrl('OperAddShow.jsp');"
						onFocus="this.blur()"> <img src="images/default/bt_add.gif"
							border="0"> </a>
					<%
					}
					%>
				</td>
			</tr>
		</table>
		
		
		<table width="100%" cellspacing="1" cellpadding="3"  class="list_table_border">
			
			<html:form action="/OperDelete.do" method="post" 	styleId="OperList_del"
				onsubmit="javascript:try{if(!isSelectedMultiBox(operArry)){alert('请选择至少一个操作员');return false;}else{var delok = confirm('是否确定要删除操作员？');if(delok){parent.showit();}return delok;}}catch(e){return false;}">
				<tr align="center">
					<td class="list_td_title" width="20%">
						<bean:message key="pvxp.operator.operid" />
					</td>
					<td class="list_td_title" width="30%">
						<bean:message key="pvxp.operator.bankid" />
					</td>
					<td class="list_td_title" width="20%">
						<bean:message key="pvxp.operator.name" />
					</td>
					<td class="list_td_title" width="15%">
						<bean:message key="pvxp.operator.state" />
					</td>
					<%
					if (authlist.equals("*") || myPower > 1) {
					%>
					<td class="list_td_title" id="delbt" width="15%">
						<nobr>
							<input type="image" src="images/default/bt_delete.gif"
								onFocus="this.blur()" align="top">
							<a href="#"
								onclick="javascript:selectall(document.all.operArry);"
								onFocus="this.blur()"> <img
									src="images/default/bt_selectall.gif" border="0" id="selall"
									align="absmiddle"> </a>
						</nobr>
					</td>
					<%
					}
					%>
				</tr>


				<%
						for (int i = 0; i < len; i++) {
						Operator temp = (Operator) myVector.get(i);
						
						String myOperid = temp.getOperid();
						String myBankid = temp.getBankid();
						String myName = temp.getName();
						//String myAuthlist = temp.getAuthlist();
						String myState = temp.getState();
						if (myState.equals("0")) {
							myState = "pvxp.operator.typestate.0";
						} else if (myState.equals("1")) {
							myState = "pvxp.operator.typestate.1";
						} else {
							myState = "pvxp.operator.typestate.other";
						}
						
						BankinfoModel myBankinfoModel = new BankinfoModel();
						Bankinfo myBankinfo = (Bankinfo) myBankinfoModel.getBankinfoFromList(myBankid);
						if (myBankinfo != null) {
							String myBanknm = myBankinfo.getBanknm();
				%>
				<tr align="left" class="list_tr<%=i % 2%>">
					<td>
						&nbsp;
						<b><a href="#"
							onclick="javascript:parent.parent.getOperDetail('<%=myOperid%>')"
							alt="<bean:message key="pvxp.operator.more"/>"><%=myOperid%>
						</a> </b>
					</td>
					<td>
						&nbsp;
						<%=myCharSet.db2web(myBanknm)%>
					</td>
					<td>
						&nbsp;
						<%=myCharSet.db2web(myName)%>
					</td>
					<td>
						&nbsp;
						<bean:message key="<%=myState%>" />
					</td>
					<%
					if (authlist.equals("*") || myPower > 1) {
					%>
					<td id="delcheck" align="center">
						&nbsp;
						<html:multibox property="operArry" styleId="operArry"
							value="<%=myOperid%>" />
					</td>
					<%
					}
					%>
				</tr>
				<%
				} else {
				%>
				<tr align="left" class="list_tr<%=i % 2%>">
					<td>
						&nbsp;
						<b><a href="#"
							onclick="javascript:parent.parent.getOperDetail('<%=myOperid%>')"
							alt="<bean:message key="pvxp.operator.more"/>"><%=myOperid%>
						</a> </b>
					</td>
					<td>
						&nbsp;未知
					</td>
					<td>
						&nbsp;
						<%=myCharSet.db2web(myName)%>
					</td>
					<td>
						&nbsp;
						<bean:message key="<%=myState%>" />
					</td>
					<td id="delcheck" align="center">
						&nbsp;
						<html:multibox property="operArry" styleId="operArry"
							value="<%=myOperid%>" />
					</td>
				</tr>
				<%
					}
					}
				%>
			</html:form>
		</table>

		<table width="100%" cellspacing="0" cellpadding="0">
			<form onsubmit="return false">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location"><bean:message
							key="pvxp.operator.list" /> </font>
				</td>
				<td align="right" valign="center">
					操作员
					<%=ttcount%>
					个 共
					<%=pagecount%>
					页 当前为第
					<%=mpage%>
					页
					<%
				if (int_page > 1) {
				%>
					&nbsp;&nbsp;
					<a href="#"
						onclick="javascript:parent.parent.getOperList('<%=int_page - 1%>');">[上一页]</a>
					<%
					}
					%>

					<%
					if (int_page < int_pagecount) {
					%>
					&nbsp;&nbsp;
					<a href="#"
						onclick="javascript:parent.parent.getOperList('<%=int_page + 1%>');">[下一页]</a>
					<%
					}
					%>

					<%
					if (int_pagecount > 1) {
					%>
					&nbsp;&nbsp;&nbsp;转到第
					<input id="gopage" type=txt size=3 class="page_input">
					页
					<input type="image" src="images/default/bt_go.gif"
						onFocus="this.blur()" onclick="checkForm()" align="middle">

					<%
					}
					%>
				</td>
			</tr>
			</form>
		</table>

	</div>
	<script>
if( document.all['gopage'] )
document.all['gopage'].focus();
</script>
</body>
</html:html>
