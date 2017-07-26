<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="2" />

<%
	PubUtil myPubUtil = new PubUtil();
	String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
	String bankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
	BankinfoModel myBankinfoModel = new BankinfoModel();
	Bankinfo myBankinfo = new Bankinfo();
	String myBanktag = "";
	CharSet myCharSet = new CharSet();
	
	if (authlist.equals("*")) {
		myBanktag = "0";
	} else {
		myBankinfo = (Bankinfo) BankinfoModel.getBankinfoFromList(bankid);
		myBanktag = myBankinfo.getBanktag().trim();
	}
	
	String banktag = "";
	try {
		banktag = request.getParameter("banktag").trim();
	} catch (Exception e) {
		banktag = myBanktag;
		if (myBanktag.equals("0")) {
			banktag = "1";
		}
	}
	String bankname = "";
	try {
		bankname = myCharSet.db2web(request.getParameter("bankname").trim());
	} catch (Exception e) {
		bankname = "";
	}
	String bankaddr = "";
	try {
		bankaddr = myCharSet.db2web(request.getParameter("bankaddr").trim());
	} catch (Exception e) {
		bankaddr = "";
	}
	String banktel = "";
	try {
		banktel = myCharSet.db2web(request.getParameter("banktel").trim());
	} catch (Exception e) {
		banktel = "";
	}
	
	Vector myVector = (Vector) myBankinfoModel.getSubBank(bankid, 1);
	String temp = "";
	int iBankLen = 0;
	int iBankLevel = 0;
	for (int j = 0; j < myVector.size(); j++) {
		temp = myBankinfoModel.getBankRange(((Bankinfo) myVector.get(j)).getBankid().trim());
		if (temp.equals("00A")) {
			iBankLevel = 0;
		}
		for (int k = 0; k < Constants.INT_BANKLEVEL_TOTAL; k++) {
			iBankLen = 0;
			for (int i = 0; i <= k; i++) {
		iBankLen = iBankLen + Constants.INT_BANKLEVEL_LEN[i];
			}
			if (((temp.length() - 1) == iBankLen) && (iBankLevel <= k)) {
		iBankLevel = k + 1;
		if (iBankLevel == Constants.INT_BANKLEVEL_TOTAL) {
			break;
		}
			}
		}
	}
	
	if (Integer.parseInt(myBanktag) > iBankLevel) {
%>
<html:html locale="true">
<head>
	<title><bean:message key="pvxp.bankinfo.add" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">

		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" align="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location"><bean:message key="pvxp.bankinfo.add" />
					</font>
				</td>
			</tr>
		</table>

		<br>
		<br>

		<table width="100%" cellspacing="1" cellpadding="2"
			class="list_table_border">
			<tr>
				<td class="list_td_title">
					<b>提示</b>
				</td>
			</tr>
			<tr class="list_tr0">
				<td align="center">
					没有可添加的机构
				</td>
			</tr>
		</table>

		<br>
		<br>
		<div align="center">
			<a href="javascript:parent.parent.getBankinfoList('1');"
				onFocus="this.blur()"> <img src="images/default/bt_back.gif"
					border="0"> </a>
		</div>
	</div>
</body>
</html:html>
<%
} else {
%>

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.bankinfo.add" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
    function startBankinfoAdd(){
			if( document.all.bankname.value=="" ) {
				alert( "请输入 机构名称" );
				document.all.bankname.focus();
				return false;
			}

			if( document.all.bankaddr.value=="" ) {
				alert( "请输入 机构地址" );
				document.all.bankaddr.focus();
				return false;
			}

			if( document.all.banktel.value=="" ) {
				alert( "请输入 联系电话" );
				document.all.banktel.focus();
				return false;
			}
			 var tel=document.all.banktel.value;
      			var strP= /(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{3,4}\-[0-9]{7,8}\-[0-9]{2,6}$)|(^[0-9]{7,8}$)|(^[0-9]{3,4}[0-9]{7,8}$)|(^0{0,1}1[0-9]{10}$)/;
     			 if(!strP.test(tel)){
      			alert('输入的电话号码格式错误');
      			document.all.banktel.focus();
      			return false; 
      }

      parent.showit();
      parent.document.all['BankinfoAdd_form'].banklevel.value=BankinfoAdd_form.banklevel.value;
      parent.document.all['BankinfoAdd_form'].parent_bankid.value=BankinfoAdd_form.parent_bankid.value;
      parent.document.all['BankinfoAdd_form'].bankname.value=BankinfoAdd_form.bankname.value;
      parent.document.all['BankinfoAdd_form'].bankaddr.value=BankinfoAdd_form.bankaddr.value;
      parent.document.all['BankinfoAdd_form'].banktel.value=BankinfoAdd_form.banktel.value;
      parent.document.all['BankinfoAdd_form'].submit();
      
    }
  </script>
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">
		<form name="BankinfoAdd_form" action="#" target="pvmain">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" align="center" width="30" height="40">
						<img src="images/default/nav.gif">
					</td>
					<td>
						<font color=blue>PowerView XP</font>---
						<font class="location"><bean:message
								key="pvxp.bankinfo.add" /> </font>
					</td>
				</tr>
			</table>


			<table width="100%" cellspacing="1" cellpadding="3"
				class="list_table_border">
				<tr align="center">
					<td class="list_td_title" colspan="4">
						<b><bean:message key="pvxp.bankinfo.add" /> </b>
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="15%" align="center">
						<b><bean:message key="pvxp.bankinfo.level" /> </b>
					</td>
					<td class="list_td_detail" width="35%">
						&nbsp;
						<nobr>
							<select name="banklevel"
								onChange="javascript:window.location='BankinfoAddShow.jsp?banktag='+banklevel.value+'&bankname='+bankname.value+'&bankaddr='+bankaddr.value+'&banktel='+banktel.value+'&first=false';"
								onMouseWheel="return false;">
								<%
											for (int i = Integer.parseInt(myBanktag) + 1; i <= iBankLevel + 1; i++) {
											String tmpmsgstr = "pvxp.bankinfo.banktag." + Integer.toString(i);
								%>
								<option value="<%=i%>" <%if(Integer.parseInt(banktag)==i){%>
									selected <%}%>>
									<bean:message key="<%=tmpmsgstr%>" />
								</option>
								<%
								}
								%>
							</select>
							<%
									String myBanktag1 = "";
									String myBankid = "";
									String myBanknm = "";
							%>
							&nbsp;
							<span id="blabel" name="blabel"></span>&nbsp;
							<select name="parent_bankid" onMouseWheel="return false;">
								<%
											try {
											String first = request.getParameter("first").trim();
										} catch (Exception e) {
											banktag = Integer.toString(Integer.parseInt(myBanktag) + 1);
										}
										for (int i = 0; i < myVector.size(); i++) {
											myBankinfo = (Bankinfo) myVector.get(i);
											myBanktag1 = myBankinfo.getBanktag().trim();
											if (Integer.parseInt(banktag) == Integer.parseInt(myBanktag1) + 1) {
										myBankid = myBankinfo.getBankid().trim();
										myBanknm = myBankinfo.getBanknm().trim();
										//System.out.println( "myBanknm = [" + myBanknm + "]\n" );
								%>
								<script>
document.all['blabel'].innerText = "隶属于";
</script>
								<option value="<%=myBankid%>">
									<%=myCharSet.db2web(myBanknm)%>
								</option>
								<%
										}
										}
								%>
							</select>
						</nobr>
					</td>
					<td class="list_td_prom" width="10%" align="center">
						<b><bean:message key="pvxp.bankinfo.name" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<input type="text" name="bankname" size="16" maxlength="25"
							value="<%=bankname%>">
						<html:errors property="bankname" />
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="15%" align="center">
						<b><bean:message key="pvxp.bankinfo.addr" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<input type="text" name="bankaddr" size="16" maxlength="32"
							value="<%=bankaddr%>">
						<html:errors property="bankaddr" />
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.bankinfo.tel" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<input type="text" name="banktel" size="16" maxlength="20"
							value="<%=banktel%>">
						<html:errors property="banktel" />
					</td>
				</tr>
			</table>

			<p></p>
			<table>
				<tr>
					<td id="delbt">
						<img src="images/default/bt_ok.gif" onFocus="this.blur()"
							onclick="javascript:startBankinfoAdd();" style="cursor:hand">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/default/bt_reset.gif" onFocus="this.blur()"
							onclick="javascript:reset();" style="cursor:hand">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/default/bt_back.gif" onFocus="this.blur()"
							onclick="javascript:parent.parent.getBankinfoList('1');"
							style="cursor:hand">
					</td>
				</tr>
				<table>
					<form>

						</div>
						<script>
<%if( !authlist.equals("*") ){
if(!(authlist.substring(1,2)).equals("2")){%>
if(document.all['delbt'])
	document.all['delbt'].style.display='none';
<%}}%>
if ( document.all['banklevel'].value == "1" ) {
	document.all['parent_bankid'].style.display='none';
}
if ( document.all['parent_bankid'].value == "" ) {
	document.all['parent_bankid'].style.display='none';
}
</script>
</body>
</html:html>

<%
}
%>
