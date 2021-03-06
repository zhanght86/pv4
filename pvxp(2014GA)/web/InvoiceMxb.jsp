<%@ include file="inc/taglib.jsp" %>
<%@ page contentType="text/html;charset=gb2312"%>

<%@ page import="java.util.*"%>
<%@ page import="com.lcjr.pvxp.util.*"%>
<%@ page import="com.lcjr.pvxp.model.*"%>
<%@ page import="com.lcjr.pvxp.orm.*"%>
<%@ page import="org.apache.log4j.*" %>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="17" minpower="1"/>

<%
	Logger 	log 	= Logger.getLogger("com.lcjr.pvxp");
	PubUtil pubUtil = new PubUtil();

	String devtype	= request.getParameter("devtype");
	if (devtype == null) {
		devtype = "";
	}
	String incSubBank = request.getParameter("incSubBank");
	if (incSubBank == null) {
		incSubBank = "0";
	}

	String bankid	= request.getParameter("bankid");

	String opbank 	= pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID,request)).trim();
	String authlist = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST,request)).trim();
	
	
	if( bankid == null ) bankid = opbank;
	String mkey 	= "";
	String mkeyvalue= "";
	boolean showsub = true;
	if( authlist.equals("*") ){
		mkey = "pvxp.bankinfo.select.all";
		mkeyvalue = opbank;
		if( bankid.equals(opbank) ){
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

	ArrayList 	devs = new ArrayList();
	Iterator 	iter = lsDevs.iterator();
	while (iter.hasNext()) {
		Devinfo dev = (Devinfo)iter.next();
		if (devtype.equals("") || devtype.equals(pubUtil.dealNull(dev.getTypeno()).trim())) {
			devs.add(dev.getDevno());
		}
	}

	int rows = (devs.size()/5) + ((devs.size()%5==0)?0:1);
%>

<html:html locale="true">
	<head>
		<title>发票明细查询</title>
		<link href="style/pvxp.css" rel="stylesheet" type="text/css">
			<script language="JavaScript" src="js/pv.js"></script>
			<script language="JavaScript">
				function toggleSubBank(checkBox) {
					if (checkBox.checked == true) {
						document.all.incSubBank.value = "1";
					} else {
						document.all.incSubBank.value = "0";
					}
					InvoiceMxb.submit();
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

					var pdate1 = document.all.date1.value;
					var pdate2 = document.all.date2.value;
				
					if (pdate1.length == 0){
						alert("请输入起始日期");
						return false;
					}
				
					if (pdate1.length > 0 && !isDate(pdate1)) {
						alert("输入的日期格式不正确。");
						return false;
					}

					if (pdate2.length == 0){
						alert("请输入截止日期");
						return false;
					}

					if (pdate2.length>0 && !isDate(pdate2)) {
						alert("输入的日期格式不正确。");
						return false;
					}

					if (pdate1.length>0 && pdate2.length>0 && pdate1 > pdate2) {
						alert("开始日期要小于结束日期。");
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
								btn.src = "images/default/bt_clear.gif";
							}
						}
					}
				}
			</script>
	</head>

	<body onload="javascript:parent.hidit();">
		<form name="InvLog">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="center" width="30" height="40"><img src="images/default/nav.gif"></td>
					<td><font color=blue>PowerView XP</font> --- <font class="location">发票明细查询</font></td>
				</tr>
			</table>

			<table border="0" width="100%" border="1" cellspacing="1" cellpadding="2" class="list_table_border">
				<tr>
					<td class="list_td_title" style="font: bold">发票明细查询</td>
				</tr>
				<tr>
					<td class="list_td_prom">您可以查询在指定日期范围内所选设备的发票打印记录，日期格式为<font color="red">MMMMYYDD</font>，如<font color="red">20050306</font>，开始日期要早于结束日期。可以指定发票的起止号和缴费卡号，如果不输入则查询所有符合条件的发票打印记录。</td>
				</tr>
			</table>

			<p></p>

			<table align="center" width="100%" cellspacing="1" cellpadding="3" border="0" class="list_table_border">
				<tr align="center">
					<td class="list_td_title" colspan="2">机构名称&nbsp;&nbsp;&nbsp;<app:bankselect property="bankid" onChange="InvLog.submit()" defaultValue="<%=bankid%>" key="<%=mkey%>" keyValue="<%=mkeyvalue%>"/>
					<input type="checkbox" onClick="toggleSubBank(this)" <%if(!showsub){%>style="display:none"<%}%>  <%if (incSubBank.equals("1")){%>checked<%}%>><%if(showsub){%>包含子机构<%}%></td>
					<td class="list_td_title" colspan="2">设备类型&nbsp;&nbsp;
					<app:devtpselect property="devtype" showName="1" key="pvxp.devtype.select.all" onChange="InvLog.submit()"  defaultValue="<%=devtype%>"/></td>
					<td class="list_td_title"><img style="cursor:hand" src="images/default/bt_selectall.gif" onClick="selectAll(document.all.devno, this)"></td>
					<input type="hidden" name="incSubBank" value="<%=incSubBank%>">
				</tr>
		</form>
		<html:form action="/InvLog.do" onsubmit="return confirmCheckBox(document.all.devno)">
		<%if (rows == 0) {%>
			<tr align="center">
				<td class="list_td_detail" colspan="5">所选机构（设备类型）暂无设备</td>
				<input type="hidden" name="nodev" value="true">
			</tr>
		<%}%>
		<%for (int i=0; i<rows; i++) {%>
			<tr class="list_tr<%=i%2%>">
			<%
				for (int j=0; j<5; j++) {%>
					<td width="20%"><%if ((5*i+j)<devs.size()){%>&nbsp;<html:multibox property="devno" value="<%=(String)devs.get(5*i+j)%>"/><%=devs.get(5*i+j)%><%}%></td>
				<%}%>
			</tr>
		<%}%>
			<tr>	  
				<td class="list_td_prom">发票打印日期</td>
				<td class="list_td_detail" colspan="2">起始日期：<html:text property="date1" size="13" maxlength="8"/></td>
				<td class="list_td_detail" colspan="2">截止日期：<html:text property="date2" size="13" maxlength="8"/></td>
			</tr>
		</table>
    
		<br>
		<table align="center" width="100%" cellspacing="1" cellpadding="3" border="0" class="list_table_border">
			<tr>	
				<td class="list_td_prom" >发票起始号：</td>
				<td class="list_td_detail"><html:text property="invno1" size="16" maxlength="16"/></td>
				<td class="list_td_prom" >发票终止号：</td>
				<td class="list_td_detail" ><html:text property="invno2" size="16" maxlength="16"/></td>
			</tr>
    
			<tr>	  
				<td class="list_td_prom">缴费卡号：</td>
				<td class="list_td_detail" colspan="4"><html:text property="accno" size="30" maxlength="21"/></td>
			</tr>
    
		</table>
		<p align="center"><input type="image" src="images/default/bt_ok.gif" onfocus="this.blur()">&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/default/bt_reset.gif" onclick="reset()" style="cursor:hand"></p>
		</html:form>
	</body>
</html:html>
