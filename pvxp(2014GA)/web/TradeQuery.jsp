<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.bean.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>
<%@ page import="org.apache.log4j.*"%>

<app:validateCookie />
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="6" minpower="1" />

<%
	Logger log = Logger.getLogger("com.lcjr.pvxp.TradeQuery_jsp");

	PubUtil pubUtil = new PubUtil();

	//获得上一页面中的 机构编号，是否包含自机构，显示设备，设备类型
	String mbankid = pubUtil.dealNull(request.getParameter("mbankid")).trim();
	String mincsubs = pubUtil.dealNull(request.getParameter("mincsubs")).trim();
	String slctdevs = pubUtil.dealNull(request.getParameter("ifsdevs")).trim();
	String devtype = pubUtil.dealNull(request.getParameter("mdevtype")).trim();

	//设备类型为空，则选上全部设备类型
	if (devtype.equals(""))
		devtype = "A";
	if (mincsubs.equals(""))
		mincsubs = "1";

	//从cookie中找出机构id，用户权限
	String opbank = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID,request)).trim();
	String authlist = pubUtil.dealNull(	pubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST,request)).trim();

	//页面bankid为空，就选用户登录的id
	if (mbankid.equals(""))
		mbankid = opbank;

	String mkey = "";
	String mkeyvalue = "";
	boolean showsub = true;

	if (authlist.equals("*")) {
		mkey = "pvxp.bankinfo.select.all";
		mkeyvalue = opbank;
		if (mbankid.equals(opbank)) {
			showsub = false;
			mincsubs = "1";
		}
	}
%>

<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
	<script language="JavaScript" src="js/pv.js"></script>
	<script>
	
//
function listDevs(){
  parent.showit();
  if( document.all.includeSubBank.checked==true){
    document.all.mincsubs.value="1";
  }else{
    document.all.mincsubs.value="0";
  }
  if(document.all.slctdevs.checked==true){
    document.all.ifsdevs.value="1";
  }else{
    document.all.ifsdevs.value="0";
  }
  document.all.mbankid.value=document.all.bankid.value;
  document.all.mdevtype.value=document.all.devtp.value;
  document.all.relistdev.submit();
}


//按设备查询
function changelistdev(){
  if( document.all.slctdevs.checked==true ){
    document.all.ifsdevs.value="1";
    document.all.devlistarea.style.display="";
    document.all.sallbt.style.display="";
  }else{
    document.all.ifsdevs.value="0";
    document.all.devlistarea.style.display="none";
    document.all.sallbt.style.display="none";
  }
}


//全选
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

//
function collapse(content,checkBox, btn, flag) {
  if (flag == "show") {
    content.style.display = "block";
    btn.style.visibility = "visible";
  } else {
  	btn.src = "images/default/bt_clear.gif";
  	selectAll(checkBox, btn);
    content.style.display = "none";
    btn.style.visibility = "hidden";
  }
}


//
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


//检查页面
function checkForm(formName) {
  var money1 = formName.money1.value;
  var money2 = formName.money2.value;
  var date1  = formName.date1.value;
  var date2  = formName.date2.value;
  var currentDateMin= formName.currentDateMin.value;
  var currentDateMax= formName.currentDateMax.value;
  var historyDate 	= formName.historyDate.value.split(",");
  var tempDate1  	= date1.substr(0, 6);
  var tempDate2  	= date2.substr(0, 6);

  if (money1.length>0 && money2.length>0 && money1>money2) {
  	alert("输入的金额错误。");
  	return false;
  }

  if (date1.length>0 && !isDate(date1)) {
		alert("输入的日期格式不正确。");
		return false;
  }

  if (date2.length>0 && !isDate(date2)) {
		alert("输入的日期格式不正确。");
		return false;
  }

  if (date1.length>0 && date2.length>0 && date1>date2) {
  	alert("开始日期要小于结束日期。");
  	return false;
  }

  formName.f_trcdlog.value = "false";
  //	没有截至日期  且 开始日期为空或小于当前日期									截止日期等于当前日期   且   开始日期为空或小于当前日期							开始日期 等于 当前日期   且  截止日期为空或小于当前日期
  if ((date2.length==0 && (date1.length==0 || date1<=currentDateMax)) || (date2==currentDateMax && (date1.length==0 || date1<=currentDateMax)) || (date1==currentDateMax && (date2.length==0 || date2<=currentDateMax)) ) 
 {
  	formName.f_trcdlog.value = "true";
  }

	formName.f_mxb.value = "false"; 
	// 截至日期为空  或   截止日期大于历史日期 且  截至日期小于等于当前日期               且        开始日期为空或小于当前日期
  if ((date2.length==0 || (date2>=currentDateMin && date2<=currentDateMax)) && (date1.length==0 || date1<=currentDateMax)  ) {
  	formName.f_mxb.value = "true";
  }

	formName.f_mxb_tmp.value = "false";
	
	if (tempDate1.length == 0) {
		formName.f_mxb_tmp.value = true;
	} else {
		for (i=0; i<historyDate.length; i++) {
			if (tempDate1 == historyDate[i]) {
				formName.f_mxb_tmp.value = "true";
				break;
			}
		}
	}

	if (formName.f_trcdlog.value=="false" && formName.f_mxb.value=="false" && formName.f_mxb_tmp.value=="false") {
		alert("当前可查询的交易明细数据为：" + currentDateMin + "至" + currentDateMax + "\n" +
					"当前可查询的临时明细数据为：" + formName.historyDate.value);
		return false;
	} else {
		parent.showit();
		return true;
	}
}
</script>
</head>
<body onload="javascript:parent.hidit();">

	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" valign="middle" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP</font> ---
				<font class="location">交易查询</font>
			</td>
		</tr>
	</table>
	<%
		SysParam sysParam = SysParamBean.getSysParam();
		String bdofmxb;
		String monthsoftmptable;
		if (sysParam == null) {
			bdofmxb = "";
			monthsoftmptable = "";
		} else {
			bdofmxb = pubUtil.dealNull(sysParam.getBdofmxb()).trim();
			monthsoftmptable = pubUtil.dealNull(sysParam.getMonthsoftmptable()).trim();
		}

		if (monthsoftmptable.length() == 0) {
			monthsoftmptable = "当前没有可查询的临时明细数据。";
		}
		String today = pubUtil.getNowDate(1);
		String availableData;
		if (bdofmxb.equals("")) {
			availableData = today + "之前。";
		} else {
			availableData = bdofmxb + " 到 " + today;
		}
	%>

	<table border="0" width="100%" border="1" cellspacing="1"
		cellpadding="2" class="list_table_border">
		<tr>
			<td class="list_td_title" style="font: bold">
				交易查询
			</td>
		</tr>
		<tr>
			<td class="list_td_prom">
				您可以查询自助终端所做交易的历史记录，可以根据操作员给定的查询条件查询到具体的交易内容。如果不输入某项查询条件，则该条件不受限制。源帐号和目标帐号不需要输入完整。交易日期的格式为
				<font color="red">YYYYMMDD</font>，如
				<font color="red">20120312</font>，开始日期要早于结束日期。
				<br>
				如果需要查询历史明细，请联系系统管理员导入历史数据。
			</td>
		</tr>
	</table>

	<p></p>

	<html:form action="/TradeQuery.do" onsubmit="return checkForm(this);">
		<table border="0" width="100%" border="1" cellspacing="1"
			cellpadding="2" class="list_table_border">
			<tr>
				<td class="list_td_prom" width="180">
					当前可查询的交易明细数据为：
				</td>
				<td class="list_td_detail" style="color: red">
					<%=availableData%>
				</td>
			</tr>
			<tr>
				<td class="list_td_prom">
					当前可查询的临时明细数据为：
				</td>
				<td class="list_td_detail" style="color: red">
					<%=monthsoftmptable%>
				</td>
			</tr>

			<input type="hidden" name="currentDateMin" value="<%=bdofmxb%>">
			<input type="hidden" name="currentDateMax" value="<%=today%>">
			<input type="hidden" name="historyDate" value="<%=monthsoftmptable%>">
			<input type="hidden" name="f_trcdlog" value="false">
			<input type="hidden" name="f_mxb" value="false">
			<input type="hidden" name="f_mxb_tmp" value="false">
		</table>



		<table border="0" width="100%" border="1" cellspacing="1"
			cellpadding="2" class="list_table_border">
			<tr>
				<td class="list_td_title" width="473">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr valign="middle">
							<td>
								机构
							</td>
							<td>
								&nbsp;
								<app:bankselect property="bankid" defaultValue="<%=mbankid%>"
									onChange="listDevs();" key="<%=mkey%>"
									keyValue="<%=mkeyvalue%>" />
							</td>
							<td>
								&nbsp;&nbsp;
								<input type="checkbox" onClick="listDevs()" <%if(!showsub){%>
									style="display:none" <%}%> name="includeSubBank"
									id="includeSubBank" <%if(mincsubs.equals("1")){%> checked <%}%>>
							</td>
							<td>
								<%
								if (showsub) {
								%>
								包含子机构
								<%
								}
								%>
							</td>
							<td>
								&nbsp;
								<input type="checkbox" onClick="changelistdev()" id="slctdevs"
									<%if(slctdevs.equals("1")){%> checked <%}%>>
							</td>
							<td>
								按设备查询
							</td>
						</tr>
					</table>
				</td>


				<td class="list_td_title" align="left">
					<div id="sallbt" <%if(!slctdevs.equals("1")){%>
						style="display:none" <%}%>>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr valign="middle">
								<td>
									设备类型
								</td>
								<td>
									&nbsp;
									<app:devtpselect property="devtp" key="pvxp.devtype.select.all"
										keyValue="A" onChange="listDevs();"
										defaultValue="<%=devtype%>" showName="1" />
								</td>
								<td>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<img style="cursor:hand" src="images/default/bt_selectall.gif"
										onclick="selectAll(document.all.devno, this)">
								</td>
							</tr>
						</table>
					</div>
					<html:hidden property="incsubs" value="" />
					<html:hidden property="devs" value="" />
				</td>
			</tr>
			<tr id="devlistarea" <%if(!slctdevs.equals("1")){%>
				style="display:none" <%}%>>
				<td colspan="2">
					<table width="100%" border="0" cellspacing="1" cellpadding="2">
						<%
							BankinfoModel myBankinfoModel = new BankinfoModel();
							if (mincsubs.equals("1"))
								mbankid = myBankinfoModel.getBankRange(mbankid);
								
							DevinfoModel myDevinfoModel = new DevinfoModel();
							List myDevList = myDevinfoModel.getDevListOfBank(mbankid);
							
							Vector myVector = new Vector();
							if (myDevList != null && !myDevList.isEmpty()) {
								int devnum = myDevList.size();

								for (int i = 0; i < devnum; i++) {
									Devinfo tmp = (Devinfo) myDevList.get(i);
									if (devtype.equals("A")	|| (pubUtil.dealNull(tmp.getTypeno()).trim()).equals(devtype)) {
								myVector.add(tmp);
									}
								}
								devnum = myVector.size();

								int trnum = devnum / 5;
								if (devnum % 5 > 0)
									trnum++;
								for (int i = 0; i < trnum; i++) {
						%>
						<tr class="list_tr<%=i % 2%>">
							<%
									for (int j = 0; j < 5; j++) {
									if (i * 5 + j < devnum) {
										Devinfo tmpdev = (Devinfo) myVector.get(i * 5 + j);
										String tmpdevno = pubUtil.dealNull(	tmpdev.getDevno()).trim();
							%>
							<td width=20%">
								<input name="devno" id="devno" type="checkbox"
									value="<%=tmpdevno%>">
								<%=tmpdevno%>
							</td>
							<%
							} else {
							%>
							<td width="20%">
								&nbsp;
							</td>
							<%
										}
										}
							%>
						</tr>
						<%
							}
							}
							if (myVector == null || myVector.isEmpty()) {
						%>
						<tr class="list_tr0">
							<td colspan="5" align="center">
								&nbsp;
								<bean:message key="pvxp.trademoni.no.devs" />
							</td>
						</tr>
						<%
						}
						%>
					</table>
				</td>
			</tr>
		</table>

		<p></p>

		<table border="0" width="100%" cellspacing="1" cellpadding="2"
			class="list_table_border">
			<tr align="center">
				<td class="list_td_prom">
					源帐号
				</td>
				<td class="list_td_detail">
					<html:text property="accno1" size="25" />
				</td>
				<td class="list_td_prom">
					交易金额
				</td>
				<td class="list_td_detail">
					大于等于：
					<html:text property="money1" size="10" />
					小于等于：
					<html:text property="money2" size="10" />
				</td>
			</tr>
			<tr align="center">
				<td class="list_td_prom">
					目标帐号
				</td>
				<td class="list_td_detail">
					<html:text property="accno2" size="25" />
				</td>
				<td class="list_td_prom">
					交易日期
				</td>
				<td class="list_td_detail">
					开始日期：
					<html:text property="date1" size="10" maxlength="8" />
					结束日期：
					<html:text property="date2" size="10" maxlength="8" />
				</td>
			</tr>
		</table>

		<p></p>


		<p></p>

		<%-- 交易类型 --%>
		<table border="0" width="100%" cellspacing="1" cellpadding="2"
			class="list_table_border">
			<tr>
				<td class="list_td_title" width="40%">
					交易类型
					<input type="radio" name="ttype"
						onclick="collapse(document.all.TradeType, document.all.devtrcd, document.all.selectTradeType, 'hide')"
						checked>
					全部
					<input type="radio" name="ttype"
						onclick="collapse(document.all.TradeType, document.all.devtrcd, document.all.selectTradeType, 'show')">
					部分
				</td>
				<td class="list_td_title">
					<img id="selectTradeType" style="visibility:hidden; cursor:hand"
						src="images/default/bt_selectall.gif"
						onclick="selectAll(document.all.devtrcd, this)">
				</td>
			</tr>
		</table>
		<table id="TradeType" style="display:none; border-top-style:none"
			border="0" width="100%" cellspacing="1" cellpadding="2"
			class="list_table_border">
			<%
				CharSet myCharSet = new CharSet();
				iniread myiniread = new iniread("Trade.ini");
				//int totalType = Integer.parseInt(pubUtil.ReadConfig("TradeCode", "TCode_Num", "", "ini", "Trade.ini"));
				int totalType = Integer.parseInt(myiniread.getIni("TCode_Num").trim());
				int col = 5;
				int row = (totalType / col) + (totalType % col == 0 ? 0 : 1);

				String tempStr = "";
				String code = "";
				String type = "";
				int beginCol;
				int endCol;

				for (int i = 0; i < row; i++) {
			%>
			<tr class="list_tr<%=i % 2%>">
				<%
						beginCol = i * col;
						endCol = beginCol + col;
						for (int j = beginCol; j < endCol; j++) {
						if (j < totalType) {
						//tempStr = pubUtil.ReadConfig("TradeCode", "TCode_" + (j+1), "", "ini", "Trade.ini");
						tempStr = pubUtil.dealNull(myiniread.getIni("TCode_"+ String.valueOf(j + 1)));
						//tempStr = myCharSet.form2GB(tempStr.trim());

						code = tempStr.substring(0, tempStr.indexOf(','));
						type = tempStr.substring(tempStr.indexOf(',') + 1);
							}
				%>
				<td width="20%">
					<%
					if (j < totalType) {
					%>
					<html:multibox property="devtrcd" value="<%=code%>" />
					<%=type%>
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

		<p></p>




		<p align="center">
			<input type="image" src="images/default/bt_ok.gif"
				onfocus="this.blur()">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/default/bt_reset.gif" style="cursor:hand"
				onclick="reset()">
		</p>
	</html:form>


	<form id="relistdev" action="TradeQuery.jsp">
		<input name="mbankid" type="hidden">
		<input name="mincsubs" type="hidden">
		<input name="mdevtype" type="hidden">
		<input name="ifsdevs" type="hidden">
	</form>

</body>
</html:html>
