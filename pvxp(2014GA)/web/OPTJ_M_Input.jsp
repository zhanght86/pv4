<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.bean.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="12" minpower="1" />
<%
	PubUtil myPubUtil = new PubUtil();
	String mbankid = myPubUtil.dealNull(request.getParameter("mbankid")).trim();
	String mincsubs = myPubUtil.dealNull(request.getParameter("mincsubs")).trim();
	
	if (mincsubs.equals(""))
		mincsubs = "1";
	
	String opbank = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
	String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
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
	
	SysParamBean mySysParamBean = new SysParamBean();
	SysParam tmpSysParam = mySysParamBean.getSysParam();
	
	String startday = "";
	String startmonth = "";
	String startyear = "";
	if (tmpSysParam == null) {
		startday = "99991231";
		startmonth = "999912";
		startyear = "9999";
	} else {
		startday = myPubUtil.dealNull(tmpSysParam.getBdofjytjday()).trim();
		startmonth = myPubUtil.dealNull(tmpSysParam.getBdofjytjmonth()).trim();
		startyear = myPubUtil.dealNull(tmpSysParam.getBdofjytjyear()).trim();
	}
	
	String endday = myPubUtil.getNowDate(1);
	String endmonth = endday.substring(0, 6);
	String endyear = endday.substring(0, 4);
%>

<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>
	<html:base />

	<script>
function selectType(mtype){
	if( mtype == 1 ){
		document.all.date_readme.innerHTML="<bean:message key="pvxp.tj.range.day" arg0="<%=startday%>" arg1="<%=endday%>"/>";
	}else if( mtype == 2 ){
		document.all.date_readme.innerHTML="<bean:message key="pvxp.tj.range.month" arg0="<%=startmonth%>" arg1="<%=endmonth%>"/>";
	}else if( mtype == 3 ){
		document.all.date_readme.innerHTML="<bean:message key="pvxp.tj.range.year" arg0="<%=startyear%>" arg1="<%=endyear%>"/>";
	}
}

function listOpers(){
	javascript:parent.showit();
	if( document.all.includeSubBank.checked==true){
		document.all.mincsubs.value="1";
	}else{
		document.all.mincsubs.value="0";
	}

	document.all.mbankid.value=document.all.bankid.value;
	document.all.relistoper.submit();
}

var hassall = false;
function selectAll(){
	if( document.all.operlist != null ){
		if( document.all.operlist.length != undefined ){
			if( hassall ){
				for( i=0;i<document.all.operlist.length;i++ ){
					if( document.all.operlist[i].checked == true ) document.all.operlist[i].checked=false;
				}
				hassall = false;
				document.all.sallbt_img.src="images/default/bt_selectall.gif";
			}else{
				for( i=0;i<document.all.operlist.length;i++ ){
					if( document.all.operlist[i].checked == false ) document.all.operlist[i].checked=true;
				}
				hassall = true;
				document.all.sallbt_img.src="images/default/bt_clear.gif";
			}

		}else{
			if( hassall ){
				if( document.all.operlist.checked == true ) document.all.operlist.checked=false;
				hassall = false;
				document.all.sallbt_img.src="images/default/bt_selectall.gif";
			}else{
				if( document.all.operlist.checked == false ) document.all.operlist.checked=true;
				hassall = true;
				document.all.sallbt_img.src="images/default/bt_clear.gif";
			}
		}
	}
}

var hassall1 = false;
function selectAllsub(){
	if( document.all.trcd != null ){
		if( document.all.trcd.length != undefined ){
			if( hassall1 ){
				for( i=0;i<document.all.trcd.length;i++ ){
					if( document.all.trcd[i].checked == true ) document.all.trcd[i].checked=false;
				}
				hassall1 = false;
				document.all.sallsubbt_img.src="images/default/bt_selectall.gif";
			}else{
				for( i=0;i<document.all.trcd.length;i++ ){
					if( document.all.trcd[i].checked == false ) document.all.trcd[i].checked=true;
				}
				hassall1 = true;
				document.all.sallsubbt_img.src="images/default/bt_clear.gif";
			}

		}else{
			if( hassall1 ){
				if( document.all.trcd.checked == true ) document.all.trcd.checked=false;
				hassall1 = false;
				document.all.sallsubbt_img.src="images/default/bt_selectall.gif";
			}else{
				if( document.all.trcd.checked == false ) document.all.trcd.checked=true;
				hassall1 = true;
				document.all.sallsubbt_img.src="images/default/bt_clear.gif";
			}
		}
	}
}

var b="<%=startday%>";
var e="<%=endday%>";
function dealQSeq( obj ) {
	var qb = document.all['qbegin'];
	var qe = document.all['qend'];
	switch( obj.value ) {
		case 'day':
			qb.maxLength=8;
			qe.maxLength=8;
			qb.value="";
			qe.value="";
			//qb.value=<%=startday%>;
			//qe.value=<%=endday%>;
			b="<%=startday%>";
			e="<%=endday%>";
			break;
		case 'month':
			qb.maxLength=6;
			qe.maxLength=6;
			qb.value="";
			qe.value="";
			//qb.value=<%=startmonth%>;
			//qe.value=<%=endmonth%>;
			b="<%=startmonth%>";
			e="<%=endmonth%>";
			break;
		case 'year':
			qb.maxLength=4;
			qe.maxLength=4;
			qb.value="";
			qe.value="";
			//qb.value=<%=startyear%>;
			//qe.value=<%=endyear%>;
			b="<%=startyear%>";
			e="<%=endyear%>";
			break;
	}
	if( parseInt(b) > parseInt(e) ) {
//		document.all['delbt'].style.display='none';
		obj.checked=false;
		document.all.date_readme.innerHTML="<font color='red'>没有可统计的数据</font>"
		//obj.disabled=true;
//		qb.maxLength=0;
//		qb.value="";
//		qe.maxLength=0;
//		qe.value="";
//		alert( "此种类型报表暂时没有数据！" );
//	} else {
//		document.all['delbt'].style.display='';
	}
}
function dealFormSubmit(){
	var flag = false;

	if( document.all.operlist != null ) {
		if( document.all.operlist.length != undefined ) {
			for( i=0; i<document.all.operlist.length; i++ ) {
				if( document.all.operlist(i).checked == true ) {
					flag = true;
				}
			}
			if( !flag ) {
				alert( "请至少选择一个操作员" );
				return false;
			}else {
				flag = false;
			}
		}else {
			if( document.all.operlist.checked == true ) {
				flag = true;
			}
			if( !flag ) {
				alert( "请至少选择一个操作员" );
				document.all.operlist(0).focus();
				return false;
			}else {
				flag = false;
			}
		}
	}else {
		alert( "请至少选择一个操作员" );
		return false;
	}

	//处理统计对象列表
	flag = false;
	if( document.all.trcd != null ) {
		if( document.all.trcd.length != undefined ) {
			for( i=0; i<document.all.trcd.length; i++ ) {
				if( document.all.trcd(i).checked == true ) {
					flag = true;
				}
			}
			if( !flag ) {
				alert( "请至少选择一个统计对象" );
				return false;
			}else {
				flag = false;
			}
		}else {
			if( document.all.trcd.checked == true ) {
				flag = true;
			}
			if( !flag ) {
				alert( "请至少选择一个统计对象" );
				document.all.trcd(0).focus();
				return false;
			}else {
				flag = false;
			}
		}
	}else {
		alert( "请至少选择一个统计对象" );
		return false;
	}
	
	//处理统计对象列表
	flag = false;
	if( document.all.type != null ) {
		if( document.all.type.length != undefined ) {
			for( i=0; i<document.all.type.length; i++ ) {
				if( document.all.type(i).checked == true ) {
					flag = true;
				}
			}
			if( !flag ) {
				alert( "请至少选择一个操作类型" );
				return false;
			}else {
				flag = false;
			}
		}else {
			if( document.all.type.checked == true ) {
				flag = true;
			}
			if( !flag ) {
				alert( "请至少选择一个操作类型" );
				document.all.type(0).focus();
				return false;
			}else {
				flag = false;
			}
		}
	}else {
		alert( "请至少选择一个操作类型" );
		return false;
	}

	if( trim(document.all.repnm.value)=="" ) {
		alert( "请输入<bean:message key='pvxp.jytj.repnm'/>" );
		document.all.repnm.focus();
		return false;
	}

	for( i=0; i<document.all.qseq.length; i++ ) {
		if( document.all.qseq(i).checked ) {
			flag = true;
		}
	}
	if( !flag ) {
		alert( "请选择<bean:message key='pvxp.jytj.reptp'/>" );
		document.all.qseq(0).focus();
		return false;
	}else {
		flag = false;
	}

	var qbv = document.all.qbegin.value;
	if( (document.all.qbegin.value=="") || (document.all.qbegin.value.length!=document.all.qbegin.maxLength) ) {
		alert( "请输入正确的<bean:message key='pvxp.jytj.begin'/>\n\n长度不正确" );
		document.all.qbegin.focus();
		return false;
	}else if( !isNumber(qbv) ) {
		alert( "请输入正确的<bean:message key='pvxp.jytj.begin'/>\n\n不应包含有非数字字符" );
		document.all.qbegin.focus();
		return false;
	}else if( (parseInt(qbv) < parseInt(b)) || (parseInt(qbv) > parseInt(e)) ) {
		alert( "请输入正确的<bean:message key='pvxp.jytj.begin'/>\n\n超出可统计范围" );
		document.all.qbegin.focus();
		return false;
	}
	var ebv = document.all.qend.value;
	if( (document.all.qend.value=="") || (document.all.qend.value.length!=document.all.qend.maxLength) ) {
		alert( "请输入正确的<bean:message key='pvxp.jytj.end'/>\n\n长度不正确" );
		document.all.qend.focus();
		return false;
	}else if( !isNumber(ebv) ) {
		alert( "请输入正确的<bean:message key='pvxp.jytj.end'/>\n\n不应包含有非数字字符" );
		document.all.qend.focus();
		return false;
	}else if( (parseInt(ebv) < parseInt(qbv)) || (parseInt(ebv) > parseInt(e)) ) {
		alert( "请输入正确的<bean:message key='pvxp.jytj.end'/>\n\n超出可统计范围" );
		document.all.qend.focus();
		return false;
	}

	parent.showit();
	return true;
}

function _reset() {
	hassall1 = false;
	document.all.sallsubbt_img.src="images/default/bt_selectall.gif";
	hassall = false;
	document.all.sallbt_img.src="images/default/bt_selectall.gif";
	OptjMDeal_form.reset();
}
</script>
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">

		<form name="OptjMDeal_form" action="OptjMDeal.do" target="pvmain"
			method="post" onsubmit="javascript:return dealFormSubmit();">

			<table width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="middle" width="30" height="40">
						<img src="images/default/nav.gif">
					</td>
					<td>
						<font color=blue>PowerView XP </font> ---
						<font class="location">操作记录统计</font>
					</td>
				</tr>
			</table>




			<table width="100%" cellspacing="1" cellpadding="2"
				class="list_table_border">
				<tr>
					<td class="list_td_title" colspan="2" align="left">
						<b>选择机构 &nbsp; <app:bankselect property="bankid"
								defaultValue="<%=mbankid%>" key="<%=mkey%>"
								keyValue="<%=mkeyvalue%>" onChange="listOpers();" />
							&nbsp;&nbsp; <input type="checkbox" onClick="listOpers()"
								<%if(!showsub){%> style="display:none" <%}%> id="includeSubBank"
								<%if(mincsubs.equals("1")){%> checked <%}%>> <%
 if (showsub) {
 %>
							包含子机构 <%
 }
 %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
					</td>
					<td class="list_td_title" align="right">
						<img id="sallsubbt_img" style="cursor:hand"
							src="images/default/bt_selectall.gif" onclick="selectAll()">
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr id="operlistarea">
					<td class="list_tr1" colspan="4">
						<table width="100%" border="0" cellspacing="1" cellpadding="2">
							<%
								BankinfoModel myBankinfoModel = new BankinfoModel();
								if (mincsubs.equals("1"))
									mbankid = myBankinfoModel.getBankRange(mbankid);
								OperatorModel myOperatorModel = new OperatorModel();
								List myOperList = myOperatorModel.getOperListOfBank(mbankid);
								Vector myVector = new Vector();
								if (myOperList != null && !myOperList.isEmpty()) {
									int opernum = myOperList.size();
									
									int trnum = opernum / 5;
									if (opernum % 5 > 0)
										trnum++;
									
									for (int i = 0; i < trnum; i++) {
							%>
							<tr class="list_tr<%=i % 2%>">
								<%
										for (int j = 0; j < 5; j++) {
										if (i * 5 + j < opernum) {
											Operator tmpoper = (Operator) myOperList.get(i * 5 + j);
											
											String tmpoperid = myPubUtil.dealNull(tmpoper.getOperid()).trim();
											String tmpopname = myPubUtil.dealNull(tmpoper.getName()).trim();
								%>
								<td width="20%">
									&nbsp;
									<input name="operlist" id="operlist" type="checkbox"
										value="<%=tmpoperid%>">
									&nbsp;


									<font color="black"><%=tmpopname%>
									</font>


									</a>
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
								} else {
							%>
							<tr class="list_tr0">
								<td colspan="5" align="center">
									&nbsp;没有要统计的操作员
								</td>
							</tr>
							<%
							}
							%>

						</table>
					</td>
				</tr>
			</table>
			<br>



			<table width="100%" cellspacing="1" cellpadding="2"
				class="list_table_border">
				<tr class="list_td_title">
					<td colspan="5">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<b>选择要统计的对象</b>
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
				<!--
	<tr class="list_tr0">
	
	<td width="20%">&nbsp;<input name="trcd" id="trcd" type="checkbox" value="0">&nbsp;设备信息管理</td>
	<td width="20%">&nbsp;<input name="trcd" id="trcd" type="checkbox" value="1">&nbsp;机构信息管理</td>
	<td width="20%">&nbsp;<input name="trcd" id="trcd" type="checkbox" value="2">&nbsp;操作员管理</td>
	<td width="20%">&nbsp;<input name="trcd" id="trcd" type="checkbox" value="3">&nbsp;远程管理</td>
	<td width="20%">&nbsp;<input name="trcd" id="trcd" type="checkbox" value="14">&nbsp;系统设置</td>
	</tr>
	<tr class="list_tr1">
	<td width="20%">&nbsp;<input name="trcd" id="trcd" type="checkbox" value="20">&nbsp;设备类型管理</td>
	<td width="20%">&nbsp;<input name="trcd" id="trcd" type="checkbox" value="23">&nbsp;设备厂商管理</td>
	<td width="20%">&nbsp;<input name="trcd" id="trcd" type="checkbox" value="40">&nbsp;更新类型管理</td>
	<td width="20%">&nbsp;<input name="trcd" id="trcd" type="checkbox" value="31">&nbsp;报修记录管理</td>
	<td width="20%">&nbsp;<input name="trcd" id="trcd" type="checkbox" value="15">&nbsp;插件管理</td>
-->
				<%
					OperTrcdUtil myOperTrcdUtil = new OperTrcdUtil();
					String[][] tmpArray = myOperTrcdUtil.getOperTrcd();
					if (tmpArray == null || tmpArray.length == 0) {
				%>
				<tr class="list_tr0">
					<td colspan="5" align="center">
						没有可选统计对象
					</td>
				</tr>
				<%
						} else {
						int trcdnum = tmpArray.length;
						int subtrnum = trcdnum / 5;
						if (trcdnum % 5 > 0)
							subtrnum++;
						for (int i = 0; i < subtrnum; i++) {
				%>
				<tr class="list_tr<%=i % 2%>">
					<%
							for (int j = 0; j < 5; j++) {
							int thisnum = i * 5 + j;
							if (thisnum < trcdnum) {
					%>
					<td width="20%">
						&nbsp;
						<input name="trcd" id="trcd" type="checkbox"
							value="<%=tmpArray[thisnum][0]%>">
						&nbsp;
						<%=tmpArray[thisnum][1]%>
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
				%>
			</table>

			<br>
			<table align="center" width="100%" cellspacing="1" cellpadding="3"
				border="0" class="list_table_border">
				<tr>
					<td class="list_td_prom">
						<b>&nbsp;操作类型</b>
					</td>
					<td class="list_td_detail">
						&nbsp;
						<input name="type" id="type" type="checkbox" value="0">
						&nbsp;修改
					</td>
					<td class="list_td_detail">
						&nbsp;
						<input name="type" id="type" type="checkbox" value="1">
						&nbsp;删除
					</td>
					<td class="list_td_detail">
						&nbsp;
						<input name="type" id="type" type="checkbox" value="2">
						&nbsp;添加
					</td>
				</tr>
			</table>

			<br>

			<table width="100%" cellspacing="1" cellpadding="3"
				class="list_table_border">
				<tr align="center">
					<td class="list_td_title" colspan="4">
						<b>报表设置</b>
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.jytj.repnm" />
						</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="text" name="repnm" id="repnm" size="16"
							maxlength="25">
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.jytj.reptp" />
						</b>
					</td>
					<td class="list_td_detail" width="30%">
						<input type="radio" name="qseq1" id="qseq" value="day"
							onClick="selectType(1);dealQSeq(this);" checked>
						按日统计&nbsp;&nbsp;
						<input type="radio" name="qseq2" id="qseq" value="month"
							onClick="selectType(2);dealQSeq(this);">
						按月统计&nbsp;&nbsp;
						<input type="radio" name="qseq3" id="qseq" value="year"
							onClick="selectType(3);dealQSeq(this);">
						按年统计
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.jytj.begin" />
						</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="text" name="qbegin" id="qbegin" size="16"
							maxlength="8">
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.jytj.end" />
						</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="text" name="qend" id="qend" size="16" maxlength="8">
					</td>
				</tr>
				<tr class="list_tr1">
					<td id="date_readme" colspan="4">
						<bean:message key="pvxp.tj.range.day" arg0="<%=startday%>"
							arg1="<%=endday%>" />
					</td>
				</tr>
			</table>

			<p></p>
			<table>
				<tr>
					<td id="delbt">
						<input type="image" src="images/default/bt_ok.gif"
							onFocus="this.blur()">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<!--<img src="images/default/bt_reset.gif" onFocus="this.blur()" onclick="javascript:_reset()" style="cursor:hand">-->
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/default/bt_back.gif" onFocus="this.blur()"
							onclick="javascript:history.back();" style="cursor:hand">
				</tr>
				<table>

					</form>
					</div>

					<form id="relistoper" action="">
						<input name="mbankid" type="hidden">
						<input name="mincsubs" type="hidden">

					</form>

					<script>
selectType(1);
dealQSeq(document.getElementById('qseq1'));
</script>
</body>
</html:html>
