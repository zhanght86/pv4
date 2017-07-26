<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>
<%@ page errorPage="Exception.jsp"%>

<app:validateCookie/>
<app:checkpower funcid="1" minpower="1" />


<html:html locale="true">
<head>
	<title><bean:message key="pvxp.bankinfo.list" />
	</title>
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

	function go() {
		var page = document.all.gopage.value;
		if (page.length>0 && page.match(/\D/)==null) {
			parent.showit();
			parent.parent.getBankinfoList(page);
		}
	}
</script>
	<script language="JavaScript" src="js/pv.js"></script>
</head>
<body onload="javascript:parent.hidit();">
	<div id="show" style="left: 0px; top: 0px; width: 100%; height: 100%; position: absolute; z-index: 1;" align="center">
		<%
			String ttcount = (String) request.getAttribute(Constants.REQUEST_BANKINFO_TTCOUNT);
			String pagecount = (String) request.getAttribute(Constants.REQUEST_BANKINFO_PAGECOUNT);
			String mpage = (String) request.getAttribute(Constants.REQUEST_BANKINFO_PAGE);
			
			int int_ttcount = 0;
			int int_pagecount = 0;
			int int_page = 0;
			
			try {
				int_page = Integer.parseInt(mpage);
				int_pagecount = Integer.parseInt(pagecount);
			} catch (Exception e) {
			}
			Vector myVector = (Vector) request.getAttribute(Constants.REQUEST_BANKINFO_VECTOR);
			
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();
			
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			int myPower = 0;
			if (authlist.equals("*")) {
				myPower = 3;
			} else {
				myPower = Integer.parseInt(authlist.substring(1, 2));
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
							key="pvxp.bankinfo.list" /> </font>
				</td>
				<td align="center" width="100">
					<%
					if (authlist.equals("*") || myPower > 1) {
					%> <a href="#"
					onClick="javascript:parent.showit();parent.parent.goUrl('BankinfoAddShow.jsp');"
					onFocus="this.blur()"> <img src="images/default/bt_add.gif"
						border="0"> </a> <%
					}
					%>
				</td>
			</tr>
		</table>

		<table width="100%" cellspacing="1" cellpadding="3" class="list_table_border">
		
		
			<html:form action="/BankinfoDelete.do" method="post"  styleId="BankinfoList_del"
				onsubmit="javascript:try{if(!isSelectedMultiBox(bankinfoArry)){alert('请选择至少一个机构');return false;}else{var delok = confirm('删除此机构将删除所属机构及所属操作员和所属设备\\n\\n是否确定要删除机构？');if(delok){parent.showit();}return delok;}}catch(e){return false;}">
				<tr align="center">
					<td class="list_td_title" width="20%">
						<bean:message key="pvxp.bankinfo.bankid" />
					</td>
					<td class="list_td_title" width="10%">
						<bean:message key="pvxp.bankinfo.level" />
					</td>
					<td class="list_td_title" width="20%">
						<bean:message key="pvxp.bankinfo.name" />
					</td>
					<td class="list_td_title" width="25%">
						<bean:message key="pvxp.bankinfo.addr" />
					</td>
					<td class="list_td_title" width="15%">
						<bean:message key="pvxp.bankinfo.tel" />
					</td>
					<%
					if (authlist.equals("*") || myPower > 1) {
					%>
					<td class="list_td_title" id="delbt" width="10%">
						<nobr>
							<input type="image" src="images/default/bt_delete.gif"
								onFocus="this.blur()" align="top">
							<a href="#"
								onclick="javascript:selectall(document.all.bankinfoArry);"
								onFocus="this.blur()"> <img
									src="images/default/bt_selectall.gif" border="0" id="selall"
									align="top"> </a>
						</nobr>
					</td>
					<%
					}
					%>
				</tr>


				<%
						for (int i = 0; i < len; i++) {
						Bankinfo myBankinfo = (Bankinfo) myVector.get(i);
						
						String myBankid = myBankinfo.getBankid().trim();
						
						String myBanktag = myBankinfo.getBanktag().trim();
						if (myBanktag.length() > 0) {
							myBanktag = "pvxp.bankinfo.banktag." + myBankinfo.getBanktag().trim();
						} else {
							myBanktag = "pvxp.bankinfo.banktag.other";
						}
						
						String myBanknm = myBankinfo.getBanknm().trim();
						String myBankaddr = myBankinfo.getBankaddr().trim();
						String myBanktel = myBankinfo.getBanktel().trim();
						
						String myState = myBankinfo.getState().trim();
						if (myState.equals("0")) {
							myState = "pvxp.bankinfo.typestate.0";
						} else if (myState.equals("1")) {
							myState = "pvxp.bankinfo.typestate.1";
						} else {
							myState = "pvxp.bankinfo.typestate.other";
						}
				%>
				<tr align="left" class="list_tr<%=i % 2%>">
					<td>
						&nbsp;
						<b><a href="#"
							onclick="javascript:parent.parent.getBankinfoDetail('<%=myBankid%>')"
							alt="<bean:message key="pvxp.bankinfo.more"/>"><%=myBankid%>
						</a>
						</b>
					</td>
					<td>
						&nbsp;
						<bean:message key="<%=myBanktag%>" />
					</td>
					<td>
						&nbsp;
						<%=myCharSet.db2web(myBanknm)%>
					</td>
					<td>
						&nbsp;
						<%=myCharSet.db2web(myBankaddr)%>
					</td>
					<td>
						&nbsp;
						<%=myCharSet.db2web(myBanktel)%>
					</td>
					<%
					if (authlist.equals("*") || myPower > 1) {
					%>
					<td id="delcheck" align="center">
						&nbsp;
						<html:multibox property="bankinfoArry" styleId="bankinfoArry"
							value="<%=myBankid%>" />
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

		<table width="100%" cellspacing="0" cellpadding="0">
			<form onsubmit="go();return false;">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location"><bean:message
							key="pvxp.bankinfo.list" /> </font>
				</td>
				<td align="right" valign="center">
					机构
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
						onclick="javascript:parent.parent.getBankinfoList('<%=int_page - 1%>');">[上一页]</a>
					<%
					}
					%>

					<%
					if (int_page < int_pagecount) {
					%>
					&nbsp;&nbsp;
					<a href="#"
						onclick="javascript:parent.parent.getBankinfoList('<%=int_page + 1%>');">[下一页]</a>
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
						onFocus="this.blur()" align="middle">

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
