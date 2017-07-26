<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>


<app:validateCookie />
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="6" minpower="2" />
<%
	PubUtil myPubUtil = new PubUtil();
	String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie( 	Constants.COOKIE_OPER_AUTHLIST, request)).trim();
	String bankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(
			Constants.COOKIE_OPER_BANKID, request));

	BankinfoModel myBiModel = new BankinfoModel();
	Vector v_SubBank = myBiModel.getSubBank(bankid, 0);

	if ((v_SubBank != null) && (v_SubBank.size() != 0)) {
%>
<html:html locale="true">
<head>
	<title>自动报表定制</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>
	<script language="JavaScript">



var flagsubmit=1;//  1  代表能够成功提交

function dealQSeq( obj ) {
	
	var statesort= document.all['statesort'];
	var statesortvalue=statesort.value;
	var statetypetips = document.all['statetypetips'];

	if(statesortvalue=='15' && obj.value!='1'){
		statetypetips.innerHTML="<font color='red'>交易明细表统计，恒为日报，请选用日报</font>";
		flagsubmit=0;
	}else{
		statetypetips.innerHTML="<font color='red'></font>";
		flagsubmit=1;
	}

	var ad = document.all['afterday'];
	var adtips = document.all['adtips'];
	ad.value="1";
	ad.readOnly=true;
	adtips.innerText="日报：恒为1天";
	
	switch( obj.value ) {
		case '1':
			ad.maxLength=1;
			ad.value="1";
			ad.readOnly=true;
			adtips.innerText="日报：恒为1天";
			break;
		case '2':
			ad.maxLength=2;
			ad.value="";
			ad.readOnly=false;
			adtips.innerText="月报：最多15天";
			break;
		case '3':
			ad.maxLength=2;
			ad.value="";
			ad.readOnly=false;
			adtips.innerText="年报：最多30天";
			break;
		case '4':
			ad.maxLength=2;
			ad.value="";
			ad.readOnly=false;
			adtips.innerText="季报：最多15天";
			break;
		case '5':
			ad.maxLength=2;
			ad.value="";
			ad.readOnly=false;
			adtips.innerText="半年报：最多15天";
			break;  
	}
}


function Show_devinfo( devno ) {
      var xpos = event.screenX;
      var ypos = event.screenY+10;
      if ( (xpos+750) > 1024 ) xpos=1024-750-10;
      if ( (ypos+180) >  768 ) ypos=ypos-180-50;
      open("Devinfo_Show.jsp?devno="+devno,null,"height=180,width=750,left="+xpos+",top="+ypos+",status=no,toolbar=no,menubar=no,location=no");
}


function dealSortMode( obj ) {
	var a = obj.value;

	for( i=0; i<document.all.length; i++ ) {
		if( (document.all(i).tagName == "TR") ) {
			if( (document.all(i).id).substring(0,4) == "Type" ) {
				document.all(i).style.display="none";
				if( (document.all(i).id).substring(0,5) == "Type"+a ) {
					document.all(i).style.display="";
				}
			}
		}
	}

	var sortlist = document.all['sortname'];
	for( i=0; i<sortlist.length; i++ ) {
		sortlist(i).checked = false;
	}

	selall = false;
	document.all.selall.src="images/default/bt_selectall.gif";
}
var hassall1 = false;


//用来处理 是交易统计  交易明细表  设备故障统计 
function DealStatsort( obj ) {
	var a = obj.value;
	var statetypetips = document.all['statetypetips'];

	if( a=="03" ) {//故障统计
		document.all['state1'].style.display = "";
		document.all['state2'].style.display = "none";
		document.all['subdevice'].style.display = "";
		document.all['trcdsort'].style.display = "none";
		statetypetips.innerHTML="<font color='red'></font>";
		flagsubmit=1;
		
	}else if( a=="01" ) {//交易统计
		document.all['state1'].style.display = "";
		document.all['state2'].style.display = "";
		document.all['subdevice'].style.display = "none";
		document.all['trcdsort'].style.display = "";
		statetypetips.innerHTML="<font color='red'></font>";
		flagsubmit=1;
		
	}else if( a=="15" ){//交易明细列表
		document.all['state1'].style.display = "";
		document.all['state2'].style.display = "";
		document.all['subdevice'].style.display = "none";
		document.all['trcdsort'].style.display = "";
		
		if(document.all['statetype'].value!='1'){
			statetypetips.innerHTML="<font color='red'>交易明细表统计，恒为日报，请选用日报</font>";
			flagsubmit=0;// 不允许 提交表格数据
		}else{
			statetypetips.innerHTML="<font color='red'></font>";
			flagsubmit=1;
	}
	}

	var sortlist = document.all['sortname'];
	for( i=0; i<sortlist.length; i++ ) {
		sortlist(i).checked = false;
	}
}



//提交表格
function dealFormSubmit(){
	
	
	if(flagsubmit!=1){
		alert( "交易明细列表只有日报，请修改报表类型为日报" );
		document.all.statetype.focus();
		return false;
	}

	var sortlist = document.all['sortname'];
	var altlist = document.all['sortalt'];
	var trcdname = "";
	for( i=0; i<sortlist.length; i++ ) {
		if( sortlist(i).checked == true ) {
			trcdname += altlist(i).value + ",";
		}
	}
	 
	trcdname = trcdname.substring( 0, trcdname.length-1 );
	document.all['strcdlist'].value = trcdname;


	var flag = false;

	if( document.all.statename.value=="" ) {
		alert( "请输入<bean:message key='pvxp.jytj.repnm'/>" );
		document.all.statename.focus();
		return false;
	}

	for( i=0; i<document.all.statetype.length; i++ ) {
		if( document.all.statetype(i).checked ) {
			flag = true;
		}
	}

	var afdy = document.all.afterday.value;
	var stype = document.all.statetype.value;

	if( (afdy=="") ) {
		alert( "请输入正确的 统计延迟天数" );
		document.all.afterday.focus();
		return false;
	}else if( !isNumber(afdy) ) {
		alert( "请输入正确的 统计延迟天数\n\n不应包含有非数字字符" );
		document.all.afterday.focus();
		return false;
	}else if( (parseInt(stype) == 2) && (parseInt(afdy) > 15) ) {
		alert( "请输入正确的 统计延迟天数\n\n不能大于15" );
		document.all.afterday.focus();
		return false;
	}else if( (parseInt(stype) == 4) && (parseInt(afdy) > 15) ) {
		alert( "请输入正确的 统计延迟天数\n\n不能大于15" );
		document.all.afterday.focus();
		return false;
	}else if( (parseInt(stype) == 5) && (parseInt(afdy) > 15) ) {
		alert( "请输入正确的 统计延迟天数\n\n不能大于15" );
		document.all.afterday.focus();
		return false;
	}else if( (parseInt(stype) == 3) && (parseInt(afdy) > 30) ) {
		alert( "请输入正确的 统计延迟天数\n\n不能大于30" );
		document.all.afterday.focus();
		return false;
	}

	var aftm = document.all.aftertime.value;

	if( (aftm=="") || (document.all.aftertime.value.length!=document.all.aftertime.maxLength) ) {
		alert( "请输入正确的 执行时间\n\n例如1:30应输入0130" );
		document.all.aftertime.focus();
		return false;
	}else if( !isNumber(aftm) ) {
		alert( "请输入正确的 执行时间\n\n不应包含有非数字字符" );
		document.all.aftertime.focus();
		return false;
	}else if( parseInt(aftm) > 2359 ) {
		alert( "请输入正确的 执行时间\n\n不能大于23:59" );
		document.all.aftertime.focus();
		return false;
	}else if( parseInt(aftm.substring(2,4)) > 59 ) {
		alert( "请输入正确的 执行时间\n\n分钟不能大于59" );
		document.all.aftertime.focus();
		return false;
	}


//处理交易列表
	var sortlist = document.all['sortname'];
	flag = false;
	for( i=0; i<sortlist.length; i++ ) {
		if( (sortlist(i).checked == true) ) {
			flag = true;
		}
	}
	if( !flag ) {
		alert( "请至少选择一个交易类型" );
		return false;
	}

	parent.showit();
	return true;
}


  </script>
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">
		<form name="StatADeal_form" action="StatADeal.do" target="pvmain"
			method="post" onsubmit="javascript:return dealFormSubmit();">
			<input type="hidden" name="strcdlist" id="strcdlist">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="center" width="30" height="40">
						<img src="images/default/nav.gif">
					</td>
					<td>
						<font color=blue>PowerView XP </font>---
						<font class="location">自动报表定制</font>
					</td>
				</tr>
			</table>

			<table width="100%" cellspacing="1" cellpadding="3"
				class="list_table_border">
				<tr align="center">
					<td class="list_td_title" colspan="4">
						<b>自动报表定制</b>
					</td>
				</tr>

				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.bankinfo.select" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<app:bankselect property="bankid" defaultValue="<%=bankid%>" />
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b>统计内容</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<select name="statesort" id="statesort"
							onChange="javascript:DealStatsort(this);">
							<option value="01">
								交易统计
							</option>
							<!--<option value="02">设备总体运行情况统计</option>-->
							<option value="03">
								设备故障统计
							</option>
							<option value="15">
								交易明细列表
							</option>
						</select>
					</td>
				</tr>

				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.jytj.repnm" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="text" name="statename" id="statename" size="16"
							maxlength="25">
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.jytj.reptp" /> </b>
					</td>
					<td class="list_td_detail" width="30%" id="state1">
						&nbsp;
						<select name="statetype" id="statetype"
							onchange="javascript:dealQSeq(this);"
							onmousewheel="javascript:return false;">
							<option value="1">
								日报
							</option>
							<option value="2">
								月报
							</option>
							<option value="4">
								季报
							</option>
							<option value="5">
								半年报
							</option>
							<option value="3">
								年报
							</option>
						</select>
						<span name="statetypetips" id="statetypetips"></span>
					</td>

				</tr>


				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b>统计延迟天数</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="text" name="afterday" id="afterday" size="16"
							maxlength="2">
						<span name="adtips" id="adtips"></span>
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b>执行时间</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="text" name="aftertime" id="aftertime" size="16"
							maxlength="4">
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b>开启标志</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="radio" name="opentag" id="opentag" value="on" checked>
						开&nbsp;&nbsp;
						<input type="radio" name="opentag" id="opentag" value="off">
						关&nbsp;&nbsp;
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b>描述信息</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="text" name="info" id="info" size="32" maxlength="64">
					</td>
				</tr>
			</table>
			<br>

			<!--交易统计分类-->
			<table width="100%" cellspacing="1" cellpadding="3" name="trcdsort"
				id="trcdsort" class="list_table_border">
				<tr id="trcdmode">
					<td class="list_td_title" width="20%" align="center">
						<b><bean:message key="pvxp.jytj.sortmode" /> </b>
					</td>
					<td class="list_td_title" colspan="3" id="state2">
						&nbsp;
						<select name="sortmode" id="sortmode"
							onChange="javascript:dealSortMode(this);"
							onMouseWheel="javascript:return false;">
							<%
									int sortmodenum = Integer.parseInt(myPubUtil.ReadConfig(
									"TradeType", "TypeSortNum", "0", "Trade.ini"));

									for (int i = 1; i <= sortmodenum; i++) {
							%>
							<option value="<%=i%>">
								<%=myPubUtil.ReadConfig("TradeType", "Type" + i
									+ "_Name", "", "Trade.ini")%>
							</option>
							<%
							}
							%>
						</select>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<script>
var selall = false;
function _selall(){
	var kynm = "Type" + document.all['sortmode'].value;
	if( document.all.sortname != null ){
		if( document.all.sortname.length != undefined ){
			if( selall ){
				for( i=0;i<document.all.sortname.length;i++ ){
					if( document.all.sortname[i].parentElement.parentElement.name == kynm ) {
						document.all.sortname[i].checked=false;
					}
				}
				selall = false;
				document.all.selall.src="images/default/bt_selectall.gif";
			}else{
				for( i=0;i<document.all.sortname.length;i++ ){
					if( document.all.sortname[i].parentElement.parentElement.name == kynm ) {
						document.all.sortname[i].checked=true;
					}
				}
				selall = true;
				document.all.selall.src="images/default/bt_remove.gif";
			}
		}else{
			if( selall ){
				document.all.sortname.checked=false;
				selall = false;
				document.all.selall.src="images/default/bt_selectall.gif";
			}else{
				document.all.sortname.checked=true;
				selall = true;
				document.all.selall.src="images/default/bt_remove.gif";
			}
		}
	}
}
</script>
						<a href="javascript:_selall();" onClick="//parent.showit();"
							onFocus="this.blur()"> <img
								src="images/default/bt_selectall.gif" border="0" id="selall"
								align="absmiddle"> </a>
					</td>
				</tr>

				<!--交易分类明细-->
				<%
						int sortcount = 0;
						for (int i = 1; i <= sortmodenum; i++) {
							int sortnamenum = Integer
							.parseInt(myPubUtil.ReadConfig("TradeType", "Type"
							+ i + "_Num", "0", "Trade.ini"));
							sortcount += sortnamenum;
				%>
				<tr class="list_tr<%=i % 2%>" name="Type<%=i%>" id="Type<%=i%>">
					<td colspan="4">
						<%
								for (int j = 1; j <= sortnamenum; j++) {
								String tmpstr = myPubUtil.ReadConfig("TradeType",
										"Type" + i + "_" + j, "", "Trade.ini");
								StringTokenizer wb = new StringTokenizer(tmpstr, "|");
								String sortname = wb.nextToken();
								if (wb.hasMoreTokens()) {
									tmpstr = wb.nextToken();
								}
						%>
						<input type="checkbox" name="sortname" id="sortname"
							value="<%=("Type" + i + "_" + j)%>">
						<%=sortname%>
						<input type="hidden" name="sortalt" id="sortalt"
							value="<%=sortname%>">
						<%
						}
						%>
					</td>
				</tr>
				<%
				}
				%>
			</table>
			<br>
			<script>
function selectAllsub(){
	if( document.all.sortname != null ){
		if( document.all.sortname.length != undefined ){
			if( hassall1 ){
				for( i=0;i<document.all.sortname.length;i++ ){
					if( document.all.sortname[i].checked == true ) document.all.sortname[i].checked=false;
				}
				hassall1 = false;
				document.all.sallsubbt_img.src="images/default/bt_selectall.gif";
			}else{
				for( i=<%=sortcount%>;i<document.all.sortname.length;i++ ){
					if( document.all.sortname[i].checked == false ) document.all.sortname[i].checked=true;
				}
				hassall1 = true;
				document.all.sallsubbt_img.src="images/default/bt_clear.gif";
			}

		}else{
			if( hassall1 ){
				if( document.all.sortname.checked == true ) document.all.sortname.checked=false;
				hassall1 = false;
				document.all.sallsubbt_img.src="images/default/bt_selectall.gif";
			}else{
				if( document.all.sortname.checked == false ) document.all.sortname.checked=true;
				hassall1 = true;
				document.all.sallsubbt_img.src="images/default/bt_clear.gif";
			}
		}
	}
}
</script>
			<!--子设备列表-->
			<table width="100%" cellspacing="1" cellpadding="2" name="subdevice"
				id="subdevice" class="list_table_border" style="display: none;">
				<tr class="list_td_title">
					<td colspan="5">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<b>选择要统计的子设备</b>
								</td>
								<td align="right">
									<img id="sallsubbt_img" style="cursor:hand"
										src="images/default/bt_selectall.gif" onclick="selectAllsub()">
									&nbsp;&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<%
						DevErrorsUtil myDevErrorsUtil = new DevErrorsUtil();
						String[][] tmpArray = myDevErrorsUtil.getSubDevice2();
						if (tmpArray == null || tmpArray.length == 0) {
				%>
				<tr class="list_tr0">
					<td colspan="5" align="center">
						没有可选统计对象
					</td>
				</tr>
				<%
						} else {
							int devicenum = tmpArray.length;
							int subtrnum = devicenum / 5;
							if (devicenum % 5 > 0)
							subtrnum++;
							
							for (int i = 0; i < subtrnum; i++) {
				%>
				<tr class="list_tr<%=i % 2%>">
					<%
								for (int j = 0; j < 5; j++) {
								int thisnum = i * 5 + j;
								if (thisnum < devicenum) {
									if (!tmpArray[thisnum][0].trim().equals("Z001")) {
					%>
					<td width="20%">
						&nbsp;
						<input name="sortname" id="sortname" type="checkbox"
							value="<%=tmpArray[thisnum][0]%>">
						&nbsp;
						<%=tmpArray[thisnum][1]%>
						<input type="hidden" name="sortalt" id="sortalt"
							value="<%=tmpArray[thisnum][1]%>">
					</td>
					<%
								}else{
						%>
					<td width="20%">
						&nbsp;
					</td>
					<%
								}
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
				%>
			</table>
			<p></p>
			<table>
				<tr>
					<td id="delbt">
						<input type="image" src="images/default/bt_ok.gif"
							onFocus="this.blur()">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/default/bt_reset.gif" onFocus="this.blur()"
							onclick="javascript:reset();DealStatsort(document.all['statesort']);"
							style="cursor:hand">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/default/bt_back.gif" onFocus="this.blur()"
							onclick="javascript:history.back();" style="cursor:hand">
					</td>
				</tr>
			</table>
	</div>
	</form>

	<script>
dealSortMode(document.all['sortmode']);
dealQSeq(document.all['statetype']);
</script>
</body>
</html:html>

<%
} else {
%>

<html:html locale="true">
<head>
	<title>自动报表定制</title>
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
					<font class="location">自动报表定制</font>
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
					没有可统计的机构，请先
					<a href="#"
						onclick="javascript:parent.parent.goUrl('BankinfoAddShow.jsp');"><font
						color="blue">添加机构</font> </a>。
				</td>
			</tr>
		</table>

		<br>
		<br>
		<div align="center">
			<a href="#"
				onclick="javascript:parent.parent.goUrl('STATAReportList.do');"
				onFocus="this.blur()"> <img src="images/default/bt_back.gif"
					border="0"> </a>
		</div>

	</div>
</body>
</html:html>

<%
}
%>
