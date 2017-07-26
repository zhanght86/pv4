<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.bean.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="10" minpower="1" />

<%
		try {
		PubUtil myPubUtil = new PubUtil();
		CharSet charSet = new CharSet();
		//获得交易类型
		BufferedRandomAccessFile braf = myPubUtil.openFile("ini", "Trade.ini");
		//获得币种
		BufferedRandomAccessFile braf1 = myPubUtil.openFile("ini", "MoneyType.ini");
		
		SysParamBean mySysParamBean = new SysParamBean();
		SysParam tmpSysParam = mySysParamBean.getSysParam();//获得SYSPARAM对象
		
		String jytjday = "";//交易统计天
		String jytjmonth = "";//交易统计月
		String jytjyear = "";//交易统计年
		
		//交易统计天 月 年
		if (tmpSysParam == null) {//如果数据库中不存在相关数据
			jytjday = "99991231";
			jytjmonth = "999912";
			jytjyear = "9999";
		} else {
			jytjday = myPubUtil.dealNull(tmpSysParam.getBdofjytjday()).trim();
			jytjmonth = myPubUtil.dealNull(tmpSysParam.getBdofjytjmonth()).trim();
			jytjyear = myPubUtil.dealNull(tmpSysParam.getBdofjytjyear()).trim();
		}
		
		String nowjytjday = myPubUtil.getOtherDate(-1);//获取当前日期之前一天
		String nowjytjmonth = myPubUtil.getPreMonth();//获取当前日期之前一个月
		String nowjytjyear = "";
		
		try {//如果当前日期不能转换为整形时，报出异常
			nowjytjyear = String.valueOf(Integer.parseInt((myPubUtil.getNowDate(1)).substring(0, 4)) - 1);
		} catch (Exception e) {
		}
		
		
		//银行标志
		String mbanktag = myPubUtil.dealNull(request.getParameter("mbanktag")).trim();
		if (mbanktag.equals(""))
			mbanktag = "1";
%>
<html:html locale="true">
<head>
	<title><bean:message key="pvxp.jytj.input" /></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>
	<script language="JavaScript">


function selectType(mtype){
	if( mtype == 1 ){
		document.all.date_readme.innerHTML="<bean:message key="pvxp.tj.range.day" arg0="<%=jytjday%>" arg1="<%=nowjytjday%>"/>";
	}else if( mtype == 2 ){
		document.all.date_readme.innerHTML="<bean:message key="pvxp.tj.range.month" arg0="<%=jytjmonth%>" arg1="<%=nowjytjmonth%>"/>";
	}else if( mtype == 3 ){
		document.all.date_readme.innerHTML="<bean:message key="pvxp.tj.range.year" arg0="<%=jytjyear%>" arg1="<%=nowjytjyear%>"/>";
	}
}


var b = "<%=jytjday%>";
var e = "<%=nowjytjday%>";


function dealQSeq( obj ) {
	var qb = document.all['qbegin'];
	var qe = document.all['qend'];
	switch( obj.value ) {
		case 'day':
			qb.maxLength=8;
			qe.maxLength=8;
			qb.value="";
			qe.value="";
			//qb.value=<%=jytjday%>;
			//qe.value=<%=nowjytjday%>;
			b="<%=jytjday%>";
			e="<%=nowjytjday%>";
			break;
		case 'month':
			qb.maxLength=6;
			qe.maxLength=6;
			qb.value="";
			qe.value="";
			//qb.value=<%=jytjmonth%>;
			//qe.value=<%=nowjytjmonth%>;
			b="<%=jytjmonth%>";
			e="<%=nowjytjmonth%>";
			break;
		case 'year':
			qb.maxLength=4;
			qe.maxLength=4;
			qb.value="";
			qe.value="";
			//qb.value=<%=jytjyear%>;
			//qe.value=<%=nowjytjyear%>;
			b="<%=jytjyear%>";
			e="<%=nowjytjyear%>";
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

	if( document.all.banklist != null ) {
		if( document.all.banklist.length != undefined ) {
			for( i=0; i<document.all.banklist.length; i++ ) {
				if( document.all.banklist(i).checked == true ) {
					flag = true;
				}
			}
			if( !flag ) {
				alert( "<bean:message key='pvxp.errors.devinfo.no.selected'/>" );
				return false;
			}else {
				flag = false;
			}
		}else {
			if( document.all.banklist.checked == true ) {
				flag = true;
			}
			if( !flag ) {
				alert( "<bean:message key='pvxp.errors.devinfo.no.selected'/>" );
				document.all.banklist(0).focus();
				return false;
			}else {
				flag = false;
			}
		}
	}else {
		alert( "<bean:message key='pvxp.errors.devinfo.no.selected'/>" );
		return false;
	}

	if( trim(document.all.repnm.value)=="" ) {
		alert( "请输入<bean:message key='pvxp.jytj.repnm'/>" );
		document.all.repnm.focus();
		return false;
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


//处理交易列表
	var dtrcdlist = new Array();
	if( document.all['statmode'].value!="all" ) {
		for( i=0; i<document.all.length; i++ ) {
			if( (document.all(i).tagName == "INPUT") ) {
				if( (document.all(i).id).substring(0,8) == "trcdlist" ) {
					if( document.all(i).checked ) {
						dtrcdlist[dtrcdlist.length] = document.all(i).value;
					}
				}
			}
		}
	}else if( document.all['statmode'].value=="all" ) {
		for( i=0; i<document.all.length; i++ ) {
			if( (document.all(i).tagName == "INPUT") ) {
				if( (document.all(i).id).substring(0,8) == "trcdlist" ) {
						dtrcdlist[dtrcdlist.length] = document.all(i).value;
				}
			}
		}
	}
	document.getElementById("strcdlist").value = dtrcdlist;

	if( dtrcdlist.length == 0 ) {
		alert( "请至少选择一个交易" );
		return false;
	}

	parent.showit();
	return true;
}

function disTips( divid ) {
	document.all['tjday'].style.display="none";
	document.all['tjmonth'].style.display="none";
	document.all['tjyear'].style.display="none";
	document.all[divid].style.display="";
}
function dealStatMode( obj ) {
	if( obj.value == "trcd" ) {
		trcdmode.style.display="";
		dealSortMode( document.all['sortmode'] );
	} else {
		trcdmode.style.display="none";
		for( i=0; i<document.all.length; i++ ) {
			if( (document.all(i).tagName == "TR") ) {
				if( (document.all(i).id).substring(0,4) == "Type" ) {
					document.all(i).style.display="none";
				}
			}
		}
	}
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
	for( i=0; i<document.all.length; i++ ) {
		if( (document.all(i).tagName == "INPUT") ) {
			if( (document.all(i).id).substring(0,8) == "trcdlist" ) {
				document.all(i).checked=false;
			}
		}
	}
	for( i=0; i<document.all.length; i++ ) {
		if( (document.all(i).tagName == "IMG") ) {
			if( (document.all(i).id).substring(0,6) == "chkall" ) {
				document.all(i).src="images/default/bt_selectall.gif";
			}
		}
	}
}

function listBanks(obj){
	var a = obj.value; 
	javascript:parent.showit();
	document.all.mbanktag.value=a;
	document.all.relistbank.submit();
}
function changelistdev(){
		document.all.ifsdevs.value="1";
		document.all.banklistarea.style.display="";
		document.all.sallbt.style.display="";
}
var hassall = false;

function selectAll(){
	if( document.all.banklist != null ){
		if( document.all.banklist.length != undefined ){
			if( hassall ){
				for( i=0;i<document.all.banklist.length;i++ ){
					if( document.all.banklist[i].checked == true ) document.all.banklist[i].checked=false;
				}
				hassall = false;
				document.all.sallbt1.src="images/default/bt_selectall.gif";
			}else{
				for( i=0;i<document.all.banklist.length;i++ ){
					if( document.all.banklist[i].checked == false ) document.all.banklist[i].checked=true;
				}
				hassall = true;
				document.all.sallbt1.src="images/default/bt_remove.gif";
			}

		}else{
			if( hassall ){
				if( document.all.banklist.checked == true ) document.all.banklist.checked=false;
				hassall = false;
				document.all.sallbt1.src="images/default/bt_selectall.gif";
			}else{
				if( document.all.banklist.checked == false ) document.all.banklist.checked=true;
				hassall = true;
				document.all.sallbt1.src="images/default/bt_remove.gif";
			}
		}
	}
}


  </script>
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">
		<form name="JytjBankMDeal_form" action="JytjBankMDeal.do"
			target="pvmain" method="post"
			onsubmit="javascript:return dealFormSubmit();">
			<input type="hidden" name="strcdlist" id="strcdlist">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="center" width="30" height="40">
						<img src="images/default/nav.gif">
					</td>
					<td>
						<font color=blue>PowerView XP </font>---
						<font class="location"><bean:message key="pvxp.jytj.input" />
						</font>
					</td>
					<td width="61.8%" style="display:none;">
						<marquee onMouseOver="javascript:this.stop();"
							onMouseOut="javascript:this.start();">
							<font color="red">友情提示：请先选择机构和设备，然后再选择或填写其他项目！</font>
						</marquee>
					</td>
				</tr>
			</table>

			<table width="100%" cellspacing="1" cellpadding="3"
				class="list_table_border">
				<tr>
					<td class="list_td_title" colspan="2" align="center">
						<b>机构类别&nbsp;&nbsp;&nbsp;&nbsp;</b>
						<select name="banktag" id="banktag" onChange="listBanks(this);">
							<%
							if (mbanktag.equals("1")) {
							%>
							<option value="1" selected>
								一级机构
							</option>
							<option value="2">
								二级机构
							</option>
							<option value="3">
								三级机构
							</option>
							<%
							} else if (mbanktag.equals("2")) {
							%>
							<option value="1">
								一级机构
							</option>
							<option value="2" selected>
								二级机构
							</option>
							<option value="3">
								三级机构
							</option>
							<%
							} else if (mbanktag.equals("3")) {
							%>

							<option value="1">
								一级机构
							</option>
							<option value="2">
								二级机构
							</option>
							<option value="3" selected>
								三级机构
							</option>
							<%
							}
							%>

						</select>
					</td>
					<td class="list_td_title" colspan="2" align="center">
						<a href="javascript:selectAll();" onClick="//parent.showit();"
							onFocus="this.blur()"> <img
								src="images/default/bt_selectall.gif" border="0" id="sallbt1"
								align="absmiddle"> </a>
					</td>
				</tr>
				<tr id="banklistarea">
					<td class="list_tr1" colspan="4">
						<table width="100%" border="0" cellspacing="1" cellpadding="2">
							<%
									BankinfoModel myBankinfoModel = new BankinfoModel();
									List myBankList = myBankinfoModel.getBankinfoList();
									Vector myVector = new Vector();
									if (myBankList != null && !myBankList.isEmpty()) {
										int banknum = myBankList.size();
										for (int i = 0; i < banknum; i++) {
									Bankinfo tmp = (Bankinfo) myBankList.get(i);
									
									
									//如果机构的等级与选择的等级一致，就添加到类集中
									if ((myPubUtil.dealNull(tmp.getBanktag()).trim()).equals(mbanktag))
										myVector.add(tmp);
										}
										
										banknum = myVector.size();
										
										int trnum = banknum / 5;
										if (banknum % 5 > 0)
									trnum++;
										for (int i = 0; i < trnum; i++) {
							%>
							<tr class="list_tr<%=i % 2%>">
								<%
											for (int j = 0; j < 5; j++) {
											if (i * 5 + j < banknum) {
												Bankinfo tmpbank = (Bankinfo) myVector.get(i * 5 + j);
												String tmpbankno = myPubUtil.dealNull(tmpbank.getBankid()).trim();
												String tmpbankname = charSet.db2web(myPubUtil.dealNull(tmpbank.getBanknm()).trim());
								%>
								<td width="20%">
									&nbsp;
									<input name="banklist" id="banklist" type="checkbox"
										value="<%=tmpbankno%>">

									<font color="black"><%=tmpbankname%> </font>

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
									}
									if (myVector == null || myVector.isEmpty()) {
							%>
							<tr class="list_tr0">
								<td colspan="5" align="center">
									&nbsp;暂无需要统计的机构
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
			<table width="100%" cellspacing="1" cellpadding="3"
				class="list_table_border">
				<tr align="center">
					<td class="list_td_title" colspan="4">
						<b><bean:message key="pvxp.jytj.input" /> </b>
					</td>
				</tr>

				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.jytj.repnm" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="text" name="repnm" id="repnm" size="16"
							maxlength="25">
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.jytj.reptp" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						<input type="radio" name="qseq" id="qseq1" value="day"
							onClick="javascript:selectType(1);dealQSeq(this);" checked>
						按日统计&nbsp;&nbsp;
						<input type="radio" name="qseq" id="qseq2" value="month"
							onClick="javascript:selectType(2);dealQSeq(this);">
						按月统计&nbsp;&nbsp;
						<input type="radio" name="qseq" id="qseq3" value="year"
							onClick="javascript:selectType(3);dealQSeq(this);">
						按年统计
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.jytj.begin" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="text" name="qbegin" id="qbegin" size="16"
							maxlength="8">
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.jytj.end" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="text" name="qend" id="qend" size="16" maxlength="8">
					</td>
				</tr>
				<tr class="list_tr1">
					<td id="date_readme" colspan="4">
						<bean:message key="pvxp.tj.range.day" arg0="<%=jytjday%>"
							arg1="<%=nowjytjday%>" />
					</td>
				</tr>
			</table>
			<br>
			<!--交易统计方式-->
			<table width="100%" cellspacing="1" cellpadding="3"
				class="list_table_border">
				<tr>
					<td class="list_td_title" width="20%" align="center">
						<b><bean:message key="pvxp.jytj.statmode" /> </b>
					</td>
					<td class="list_td_title" width="30%">
						&nbsp;
						<select name="statmode" id="statmode"
							onChange="javascript:dealSortMode(this);"
							onMouseWheel="javascript:return false;">
							<option value="all">
								<bean:message key="pvxp.jytj.statall" />
							</option>
							<%
										try {
										String[][] tradecode = myPubUtil.Ini2Array("ini", "Trade.ini", "TradeCode", "TCode_Num", "TCode_", ",", "");
										
										int sortmodenum = Integer.parseInt(myPubUtil.ReadConfig("TradeType", "TypeSortNum", "0", braf));
										
										for (int i = 1; i <= sortmodenum; i++) {
							%>
							<option value="<%=i%>">
								<%=myPubUtil.ReadConfig("TradeType", "Type" + i + "_Name", "", braf)%>
							</option>
							<%
							}
							%>
						
					</td>

					<td class="list_td_title" width="20%" align="center">
						<b>货币类型</b>
					</td>
					<td class="list_td_title" width="30%">
						&nbsp;
						<select name="moneytype" id="moneytype">
							<%
										int moneytypenum = Integer.parseInt(myPubUtil.ReadConfig("MoneyType", "MoneyTypeNum", "0", braf1));
										String tmp = "";
										for (int i = 1; i <= moneytypenum; i++) {
									tmp = myPubUtil.ReadConfig("MoneyType", "Type" + i + "_Name", "", braf1);
							%>
							<option value="<%=i%>">
								<%=tmp%>
							</option>
							<%
							}
							%>
						
					</td>
				</tr>

				<%
						for (int i = 1; i <= sortmodenum; i++) {
						int sortnamenum = Integer.parseInt(myPubUtil.ReadConfig("TradeType", "Type" + i + "_Num", "0", braf));
						for (int j = 1; j <= sortnamenum; j++) {
							String tmpstr = myPubUtil.ReadConfig("TradeType", "Type" + i + "_" + j, "", braf);
							StringTokenizer wb = new StringTokenizer(tmpstr, "|");
							String sortname = wb.nextToken();
				%>
				<!--交易分类明细-->
				<tr class="list_tr<%=j % 2%>" id="<%="Type" + i + "_" + j%>"
					style="display:none;">
					<script>
		var chkall<%=i%><%=j%> = false;
		function chkallfunc<%=i%><%=j%>() {
			if( document.all.trcdlist<%=i%><%=j%> != null ) {
				if( document.all.trcdlist<%=i%><%=j%>.length != undefined ) {
					if( chkall<%=i%><%=j%> ){
						for( i=0; i<document.all.trcdlist<%=i%><%=j%>.length; i++ ) {
							document.all.trcdlist<%=i%><%=j%>[i].checked = false;
						}
						chkall<%=i%><%=j%> = false;
						document.all.chkall<%=i%><%=j%>.src = "images/default/bt_selectall.gif";
					}else {
						for( i=0; i<document.all.trcdlist<%=i%><%=j%>.length; i++ ) {
							document.all.trcdlist<%=i%><%=j%>[i].checked = true;
						}
						chkall<%=i%><%=j%> = true;
						document.all.chkall<%=i%><%=j%>.src = "images/default/bt_remove.gif";
					}
				}else {
					if( chkall<%=i%><%=j%> ){
						document.all.trcdlist<%=i%><%=j%>.checked = false;
						chkall<%=i%><%=j%> = false;
						document.all.chkall<%=i%><%=j%>.src = "images/default/bt_selectall.gif";
					}else {
						document.all.trcdlist<%=i%><%=j%>.checked = true;
						chkall<%=i%><%=j%> = true;
						document.all.chkall<%=i%><%=j%>.src = "images/default/bt_remove.gif";
					}
				}
			}
		}
		</script>
					<td width="20%" align="center">
						<b><%=sortname%> </b>
						<br>
						<a href="javascript:chkallfunc<%=i%><%=j%>();"
							onClick="//parent.showit();" onFocus="this.blur()"> <img
								src="images/default/bt_selectall.gif" border="0"
								name="chkall<%=i%><%=j%>" id="chkall<%=i%><%=j%>"
								align="absmiddle"> </a>
					</td>
					<td colspan="3" vAlign="top">
						<table width="100%" border="0" cellspacing="1" cellpadding="2">
							<%
											if (wb.hasMoreTokens()) {
											tmpstr = wb.nextToken();
										}
										StringTokenizer wb1 = new StringTokenizer(tmpstr, ",");
										String trcdno = "";
										String trcdnm = "";
										
										int tnum = wb1.countTokens();
										int ttrnum = tnum / 5;
										if (tnum % 5 > 0)
											ttrnum++;
										
										for (int z = 0; z < ttrnum; z++) {
							%>
							<tr class="list_tr<%=z % 2%>">
								<%
											for (int y = 0; y < 5; y++) {
											if ((z * 5 + y) < tnum) {
												trcdno = wb1.nextToken();
												for (int k = 0; k < tradecode.length; k++) {
													String trcdnotmp = tradecode[k][0];
													if (trcdno.equals(tradecode[k][0])) {
												trcdnm = tradecode[k][1];
												break;
													}
												}
								%>
								<td width="20%">
									<nobr>
										<input type="checkbox" name="trcdlist<%=i%><%=j%>"
											id="trcdlist<%=i%><%=j%>" value="<%=trcdno%>">
										<%=trcdnm%>
									</nobr>
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
							%>
						</table>
					</td>
				</tr>
				<%
							}
							}
							
						} catch (Exception e) {
							System.out.println("Exception = [" + e + "]");
						} finally {
							try {
						braf1.close();
						braf.close();
							} catch (Exception e) {
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
						<!--<img src="images/default/bt_reset.gif" onFocus="this.blur()" onclick="javascript:reset()" style="cursor:hand">-->
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/default/bt_back.gif" onFocus="this.blur()"
							onclick="javascript:history.back();" style="cursor:hand">
				</tr>
				<table>

					</form>
					</div>
					</form>
					<form id="relistbank" action="">
						<input name="mbanktag" type="hidden">
					</form>
					<script>
selectType(1);
dealQSeq(document.getElementById('qseq1'));
</script>
</body>
</html:html>
<%
		} catch (Exception ex) {
		System.out.println("Exception = [" + ex + "]");
	}
%>
