<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="2" />

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.bankinfo.modify" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
   function startBankinfoModify(bankid){

      parent.document.all['BankinfoModify_form'].bankid.value=bankid;
      parent.document.all['BankinfoModify_form'].bankname.value=BankinfoModify_form.bankname.value;
      parent.document.all['BankinfoModify_form'].bankaddr.value=BankinfoModify_form.bankaddr.value;
      parent.document.all['BankinfoModify_form'].banktel.value=BankinfoModify_form.banktel.value;
      
      var tel=BankinfoModify_form.banktel.value;
      var strP= /(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{3,4}\-[0-9]{7,8}\-[0-9]{2,6}$)|(^[0-9]{7,8}$)|(^[0-9]{3,4}[0-9]{7,8}$)|(^0{0,1}1[0-9]{10}$)/;
      if(!strP.test(tel)){
      	alert('输入的电话号码格式错误');
      	return false; 
      }

      parent.document.all['BankinfoModify_form'].submit();
    }
  </script>
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">
		<form name="BankinfoModify_form" action="#" target="pvmain">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="center" width="30" height="40">
						<img src="images/default/nav.gif">
					</td>
					<td>
						<font color=blue>PowerView XP </font>---
						<font class="location"><bean:message
								key="pvxp.bankinfo.modify" /> </font>
					</td>
				</tr>
			</table>
			<%
				PubUtil myPubUtil = new PubUtil();
				String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
				Bankinfo myBankinfo = (Bankinfo) request.getAttribute(Constants.REQUEST_BANKINFO);
				if (myBankinfo != null) {
					CharSet myCharSet = new CharSet();
					
					String myBankid = myBankinfo.getBankid().trim();
					String myBanknm = myBankinfo.getBanknm().trim();
					String myBankaddr = myBankinfo.getBankaddr().trim();
					String myBanktel = myBankinfo.getBanktel().trim();
					
					String myBanktag = myBankinfo.getBanktag().trim();
					if (myBanktag.length() > 0) {
						myBanktag = "pvxp.bankinfo.banktag." + myBankinfo.getBanktag().trim();
					} else {
						myBanktag = "pvxp.bankinfo.banktag.other";
					}
					
					String myState = myBankinfo.getState().trim();
					String myadddate = myBankinfo.getAdddate().trim();
					String myAdddate = myadddate.substring(0, 4) + "年" + Integer.parseInt(myadddate.substring(4, 6)) + "月" + Integer.parseInt(myadddate.substring(6, 8)) + "日";
			%>
			<table width="100%" cellspacing="1" cellpadding="3"
				class="list_table_border">
				<tr align="center">
					<td class="list_td_title" colspan="4">
						<b><bean:message key="pvxp.bankinfo.modify" /> </b>
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
						<input type="text" name="bankname" size="16" maxlength="50"
							value="<%=myCharSet.db2web(myBanknm)%>">
						<html:errors property="bankname" />
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.bankinfo.addr" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<input type="text" name="bankaddr" size="16" maxlength="64"
							value="<%=myCharSet.db2web(myBankaddr)%>">
						<html:errors property="bankaddr" />
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.bankinfo.tel" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<input type="text" name="banktel" size="16" maxlength="24"
							value="<%=myCharSet.db2web(myBanktel)%>">
						<html:errors property="banktel" />
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.bankinfo.level" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<bean:message key="<%=myBanktag%>" />
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.bankinfo.adddate" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<%=myAdddate%>
					</td>
				</tr>
			</table>

			<p></p>
			<table>
				<tr>
					<td id="delbt">
						<img src="images/default/bt_ok.gif" onFocus="this.blur()"
							onclick="javascript:startBankinfoModify('<%=myBankid.trim()%>');"
							style="cursor:hand">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/default/bt_reset.gif" onFocus="this.blur()"
							onclick="javascript:reset();" style="cursor:hand">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/default/bt_back.gif" onFocus="this.blur()"
							onclick="javascript:history.back();" style="cursor:hand">
					</td>
				</tr>
			</table>
		</form>

		<%
		}
		%>

	</div>
	<script>
<%if( !authlist.equals("*") ){
if(!(authlist.substring(1,2)).equals("2")){%>
	if(document.all['delbt'])	document.all['delbt'].style.display='none';
<%}}%>
</script>
</body>
</html:html>
