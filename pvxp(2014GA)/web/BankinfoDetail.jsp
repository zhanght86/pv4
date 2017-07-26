<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="1" />


<html:html locale="true">
<head>
	<title><bean:message key="pvxp.bankinfo.detail" /></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location"><bean:message
							key="pvxp.bankinfo.detail" /> </font>
				</td>
			</tr>
		</table>
		<%
			PubUtil myPubUtil = new PubUtil();
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			int myPower = 0;
			if (authlist.equals("*")) {
				myPower = 3;
			} else {
				myPower = Integer.parseInt(authlist.substring(1, 2));
			}
			Bankinfo myBankinfo = (Bankinfo) request.getAttribute(Constants.REQUEST_BANKINFO);
			String myBankid = "";
			if (myBankinfo != null) {
				CharSet myCharSet = new CharSet();
				
				myBankid = myBankinfo.getBankid().trim();
				String myBanknm = myBankinfo.getBanknm().trim();
				String myBankaddr = myBankinfo.getBankaddr().trim();
				String myBanktel = myBankinfo.getBanktel().trim();
				
				String myBanktag = myBankinfo.getBanktag().trim();
				if (myBanktag.length() > 0) {
					myBanktag = "pvxp.bankinfo.banktag." + myBankinfo.getBanktag().trim();
				} else {
					myBanktag = "pvxp.bankinfo.banktag.other";
				}
				
				String myadddate = myBankinfo.getAdddate().trim();
				String myAdddate = myadddate.substring(0, 4) + "年" + Integer.parseInt(myadddate.substring(4, 6)) + "月" + Integer.parseInt(myadddate.substring(6, 8)) + "日";
				
				String myState = myBankinfo.getState().trim();
				if (myState.equals("0")) {
					myState = "pvxp.bankinfo.typestate.0";
				} else if (myState.equals("1")) {
					myState = "pvxp.bankinfo.typestate.1";
				} else {
					myState = "pvxp.bankinfo.typestate.other";
				}
		%>
		<table width="100%" cellspacing="1" cellpadding="3"
			class="list_table_border">
			<tr align="center">
				<td class="list_td_title" colspan="4">
					<b><bean:message key="pvxp.bankinfo.detail" /> </b>
				</td>
			</tr>
			<tr>
				<td class="list_td_prom" width="20%" align="center">
					<b><bean:message key="pvxp.bankinfo.bankid" /> </b>
				</td>
				<td class="list_td_detail" width="30%">
					&nbsp;&nbsp;
					<%=myBankid%>
				</td>
				<td class="list_td_prom" width="20%" align="center">
					<b><bean:message key="pvxp.bankinfo.name" /> </b>
				</td>
				<td class="list_td_detail" width="30%">
					&nbsp;&nbsp;
					<%=myCharSet.db2web(myBanknm)%>
				</td>
			</tr>
			<tr>
				<td class="list_td_prom" width="20%" align="center">
					<b><bean:message key="pvxp.bankinfo.addr" /> </b>
				</td>
				<td class="list_td_detail" width="30%">
					&nbsp;&nbsp;
					<%=myCharSet.db2web(myBankaddr)%>
				</td>
				<td class="list_td_prom" width="20%" align="center">
					<b><bean:message key="pvxp.bankinfo.tel" /> </b>
				</td>
				<td class="list_td_detail" width="30%">
					&nbsp;&nbsp;
					<%=myCharSet.db2web(myBanktel)%>
				</td>
			</tr>
			<tr>
				<td class="list_td_prom" width="20%" align="center">
					<b><bean:message key="pvxp.bankinfo.adddate" /> </b>
				</td>
				<td class="list_td_detail" width="30%">
					&nbsp;&nbsp;
					<%=myAdddate%>
				</td>
				<td class="list_td_prom" width="20%" align="center">
					<b><bean:message key="pvxp.bankinfo.level" /> </b>
				</td>
				<td class="list_td_detail" width="30%">
					&nbsp;&nbsp;
					<bean:message key="<%=myBanktag%>" />
				</td>
			</tr>
		</table>
		<%
		}
		%>

		<p></p>
		<script>
function _onSubmit() {
	var delok = confirm('删除此机构将删除所属机构及所属操作员和所属设备\n\n是否确定要删除机构？');
	if(delok){
		parent.showit();
		BankinfoList_del.submit();
	}
}
</script>
		<table>
			<tr>
				<td id="delbt">
					<html:form action="/BankinfoDelete.do" method="post"
						styleId="BankinfoList_del">
						<%
						if (authlist.equals("*") || myPower > 1) {
						%>
						<a
							href="javascript:parent.parent.getBankinfoModifyShow('<%=myBankid%>');"
							onClick="parent.showit();" onFocus="this.blur()"> <img
								src="images/default/bt_modify.gif" border="0"> </a>
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	<a href="javascript:_onSubmit();" onClick="//parent.showit();"
							onFocus="this.blur()"> <img
								src="images/default/bt_delete.gif" border="0"> </a>
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <%
        }
        %>
						<a href="javascript:history.go(-1);" onClick="parent.showit();"
							onFocus="this.blur()"> <img src="images/default/bt_back.gif"
								border="0"> </a>
						<input type="hidden" value="<%=myBankid%>" name="bankinfoArry"
							id="bankinfoArry">
					</html:form>
				</td>
			</tr>
			<table>

				</div>
</body>
</html:html>
