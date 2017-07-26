<%@ include file="../../inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<app:checkpower funcid="16" minpower="1" />
<%@ page import="java.util.*"%>
<%@ page import="com.lcjr.pvxp.util.*"%>
<%@ page import="com.lcjr.pvxp.model.*"%>
<%@ page import="com.lcjr.pvxp.orm.*"%>
<%@ page import="org.apache.log4j.*"%>

<%
	Logger log = Logger.getLogger("com.lcjr.pvxp");
	PubUtil pubUtil = new PubUtil();
	
	String devtype = request.getParameter("devtype");
	if (devtype == null) {
		devtype = "";
	}
	String incSubBank = request.getParameter("incSubBank");
	if (incSubBank == null) {
		incSubBank = "0";
	}
	
	String bankid = request.getParameter("bankid");
	
	String opbank = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
	String authlist = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
	
	if (bankid == null)
		bankid = opbank;
	String mkey = "";
	String mkeyvalue = "";
	boolean showsub = true;
	if (authlist.equals("*")) {
		mkey = "pvxp.bankinfo.select.all";
		mkeyvalue = opbank;
		if (bankid.equals(opbank)) {
			showsub = false;
			incSubBank = "1";
		}
	}
	
	List lsDevs;
	if (incSubBank.equals("1")) {
		lsDevs = new DevinfoModel().getDevListOfBank(new BankinfoModel().getBankRange(bankid));
	} else {
		lsDevs = new DevinfoModel().getDevListOfBank(bankid);
	}
	
	ArrayList devs = new ArrayList();
	Iterator iter = lsDevs.iterator();
	while (iter.hasNext()) {
		Devinfo dev = (Devinfo) iter.next();
		if (devtype.equals("") || devtype.equals(pubUtil.dealNull(dev.getTypeno()).trim())) {
			devs.add(dev.getDevno());
		}
	}
	
	int rows = (devs.size() / 5) + ((devs.size() % 5 == 0) ? 0 : 1);

%>

<html:html locale="true">
<head>
	<title>现金模块交易明细查询</title>
	<link href="../../style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="../../js/pv.js"></script>
	<script language="JavaScript">
				function toggleSubBank(checkBox) {
					if (checkBox.checked == true) {
						document.all.incSubBank.value = "1";
					} else {
						document.all.incSubBank.value = "0";
					}
					InvoiceState.submit();
				}

				function isDate(date) {
					if (date.length != 8) {
						return false;
					}

					if (date.match(/\D/) != null) {
						return false;
					}

					var m = date.substr(4, 2);
					var d = date.substr(6, 2);

					if (m > 12) {
						return false;
					}

					if (d > 31) {
						return false;
					}

					return true;
				}

				function confirmCheckBox(CheckBox) {
					if (document.all.nodev != undefined) {
						return false;
					}
					if(!isSelectedMultiBox(CheckBox))	{
						alert("请选择设备");
						return false;
					}

					parent.showit();
					return true;
				}

				function selectAll(checkBox, btn){
					var hassall = btn.src.substr(btn.src.lastIndexOf("/")) == "/bt_clear.gif";
					if( checkBox != null ){
						if( checkBox.length != undefined ){
							if( hassall ){
								for( i = 0; i < checkBox.length; i++ ){
									if( checkBox[i].checked == true ) checkBox[i].checked = false;
								}
								hassall = false;
								btn.src = "images/default/bt_selectall.gif";
							}else{
								for( i = 0; i < checkBox.length; i++ ){
									if( checkBox[i].checked == false ) checkBox[i].checked = true;
								}
								hassall = true;
								btn.src = "images/default/bt_clear.gif";
							}

						}else{
							if( hassall ){
								if( checkBox.checked == true ) checkBox.checked = false;
								hassall = false;
								btn.src = "images/default/bt_selectall.gif";
							}else{
								if( checkBox.checked == false ) checkBox.checked = true;
								hassall = true;
								btn.src= "images/default/bt_clear.gif";
							}
						}
					}
				}
			</script>
</head>

<body onload="javascript:parent.hidit();">
	<form name="InvState">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="../../images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location">现金交易明细查询</font>
				</td>
			</tr>
		</table>

		<table border="0" width="100%" border="1" cellspacing="1"
			cellpadding="2" class="list_table_border">
			<tr>
				<td class="list_td_title" style="font: bold">
					现金交易明细查询
				</td>
			</tr>
			<tr>
				<td class="list_td_prom">
					您可以根据设备号、卡号、交易日期和时间查询交易明细。日期格式为
					<font color="red">MMMMYYDD</font>，如
					<font color="red">20050306</font>，开始日期要早于结束日期。如果不输入日期范围，则查询所有符合条件的已发卡流水记录。
					<br>
					卡号支持模糊查询，如 “
					<font color="red">622202</font><font color="blue">1602084630570</font>”
					，系统以输入号码作为卡号首几位进行查询（红色部分），蓝色部分为模糊查询部分。
				</td>
			</tr>
		</table>

		<p></p>

		<table align="center" width="100%" cellspacing="1" cellpadding="3"
			border="0" class="list_table_border">
			<tr align="center">
				<td class="list_td_title" colspan="2">
					机构名称&nbsp;&nbsp;&nbsp;
					<app:bankselect property="bankid" onChange="InvState.submit()"
						defaultValue="<%=bankid%>" key="<%=mkey%>"
						keyValue="<%=mkeyvalue%>" />
					<input type="checkbox" onClick="toggleSubBank(this)"
						<%if(!showsub){%> style="display:none" <%}%>
						<%if (incSubBank.equals("1")){%> checked <%}%>>
					<%
					if (showsub) {
					%>
					包含子机构
					<%
					}
					%>
				</td>
				<td class="list_td_title" colspan="2">
					设备类型&nbsp;&nbsp;
					<app:devtpselect property="devtype" showName="1"
						key="pvxp.devtype.select.all" onChange="InvState.submit()"
						defaultValue="<%=devtype%>" />
				</td>
				<td class="list_td_title">
					<img style="cursor:hand" src="../../images/default/bt_selectall.gif"
						onClick="selectAll(document.all.termnum, this)">
				</td>
				<input type="hidden" name="incSubBank" value="<%=incSubBank%>">
			</tr>
			</form>
			<html:form action="/cashDetail.do?method=list" onsubmit="return confirmCheckBox(document.all.termnum)">
				<%
				if (rows == 0) {
				%>
				<tr align="center">
					<td class="list_td_detail" colspan="5">
						所选机构（设备类型）暂无设备
					</td>
					<input type="hidden" name="nodev" value="true">
				</tr>
				<%
				}
				%>
				<%
				for (int i = 0; i < rows; i++) {
				%>
				<tr class="list_tr<%=i % 2%>">
					<%
					for (int j = 0; j < 5; j++) {
					%>
					<td width="20%">
						<%
						if ((5 * i + j) < devs.size()) {
						%>
						&nbsp;
						<html:multibox property="termnum" 	value="<%=(String) devs.get(5 * i + j)%>" />
						<%=devs.get(5 * i + j)%>
						<%
						}
						%>
					</td>
					<%
					}
					%>
				</tr>
				<%
				}
				%>
			
		</table>
		<br>
		<table align="center" width="100%" cellspacing="1" cellpadding="3"
			border="0" class="list_table_border">
			<tr>
				<td class="list_td_prom">
					请选择一段时间范围
					
				</td>
				<td class="list_td_detail" >
					起始日期：
					<html:text property="startdate" maxlength="8"></html:text>
				</td>
				<td class="list_td_detail" >
					起始时间：
					<html:text property="starttime"  maxlength="6"></html:text>
				</td>
				<td class="list_td_detail" >
					截止日期：
				<html:text property="enddate" maxlength="8"></html:text>
				</td>
				<td class="list_td_detail" >
					截止时间：
					<html:text property="endtime" maxlength="6"></html:text>
				</td>
			</tr>
		</table>
		<br>
		<table align="center" width="100%" cellspacing="1" cellpadding="3"
			border="0" class="list_table_border">
			<tr>
				<td class="list_td_prom">
					<b>卡片号码：</b>
				</td>
				<td class="list_td_detail" >
					<html:text property="cardnum"  maxlength="30"></html:text>
				</td>
				<td class="list_td_prom" width="10%" align="center">
						<b>钞箱状态</b>
					</td>
					<td class="list_td_detail" width="15%">
						&nbsp;
						<input type="checkbox" name="outboxstatus" id="outcardstatus"
							value="0">
						未清钞&nbsp;&nbsp;
						<input type="checkbox" name="outboxstatus" id="outcardstatus"
							value="1">
						已清钞

					</td>
					<td class="list_td_prom" width="10%" align="center">
						<b>交易状态</b>
					</td>
					<td class="list_td_detail" width="15%">
						&nbsp;
						<input type="checkbox" name="tradestatus" id="tradestatus"
							value="0">
						交易失败&nbsp;&nbsp;
						<input type="checkbox" name="tradestatus" id="tradestatus"
							value="1">
						交易成功
					</td>
				
			</tr>
			
			
			<tr>
				<td class="list_td_prom">
					<b>证件号码：</b>
				</td>
				<td class="list_td_detail" >
				<html:text property="personnum" maxlength="30"></html:text>
				</td>
				<td class="list_td_prom" width="10%" align="center">
						<b>钞箱批次号</b>
				</td>
				<td class="list_td_detail" width="15%">
				<html:text property="batchid" maxlength="30"></html:text>
				</td>
				<td class="list_td_prom" width="10%" align="center">
						<b>&nbsp;</b>
				</td>
				<td class="list_td_detail" width="15%">
				&nbsp;
				</td>
				 
			</tr>
			
			
		</table>
		<br>
		<p align="center">
			<input type="image" src="../../images/default/bt_ok.gif"
				onfocus="this.blur()">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="../../images/default/bt_reset.gif" onclick="reset()"
				style="cursor:hand">
		</p>
		</html:form>
		
</body>
</html:html>
