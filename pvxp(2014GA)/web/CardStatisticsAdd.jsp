<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.bean.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>
<app:checkpower funcid="10" minpower="1" />
<%
		try {
		PubUtil myPubUtil = new PubUtil();

		SysParamBean mySysParamBean = new SysParamBean();
		SysParam tmpSysParam = mySysParamBean.getSysParam();

		String jytjday = "";
		String jytjmonth = "";
		String jytjyear = "";
		if (tmpSysParam == null) {
			jytjday = "99991231";
			jytjmonth = "999912";
			jytjyear = "9999";
		} else {
			jytjday = myPubUtil.dealNull(tmpSysParam.getBdofjytjday())
			.trim();
			jytjmonth = myPubUtil.dealNull(
			tmpSysParam.getBdofjytjmonth()).trim();
			jytjyear = myPubUtil
			.dealNull(tmpSysParam.getBdofjytjyear()).trim();
		}

		String nowjytjday = myPubUtil.getNowDate(1);
		String nowjytjmonth = myPubUtil.getPreMonth();
		String nowjytjyear = "";

		try {
			nowjytjyear = String.valueOf(Integer.parseInt((myPubUtil
			.getNowDate(1)).substring(0, 4)) - 1);
		} catch (Exception e) {
		}

		String mbankid = myPubUtil.dealNull(
		request.getParameter("mbankid")).trim();
		String mincsubs = myPubUtil.dealNull(
		request.getParameter("mincsubs")).trim();
		String devtype = myPubUtil.dealNull(
		request.getParameter("mdevtype")).trim();
		if (devtype.equals(""))
			devtype = "A";
		if (mincsubs.equals(""))
			mincsubs = "1";

		String opbank = myPubUtil.dealNull(
		myPubUtil.getValueFromCookie(
				Constants.COOKIE_OPER_BANKID, request)).trim();
		String authlist = myPubUtil.dealNull(
		myPubUtil.getValueFromCookie(
				Constants.COOKIE_OPER_AUTHLIST, request))
		.trim();
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
	<title>出卡状态记录统计报表</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>
	<script language="JavaScript">
function Show_devinfo( devno ) {
      var xpos = event.screenX;
      var ypos = event.screenY+10;
      if ( (xpos+750) > 1024 ) xpos=1024-750-10;
      if ( (ypos+180) >  768 ) ypos=ypos-180-50;
      open("Devinfo_Show.jsp?devno="+devno,null,"height=180,width=750,left="+xpos+",top="+ypos+",status=no,toolbar=no,menubar=no,location=no");
}

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
			b="<%=jytjday%>";
			e="<%=nowjytjday%>";
			break;
		case 'month':
			qb.maxLength=6;
			qe.maxLength=6;
			qb.value="";
			qe.value="";
			b="<%=jytjmonth%>";
			e="<%=nowjytjmonth%>";
			break;
		case 'year':
			qb.maxLength=4;
			qe.maxLength=4;
			qb.value="";
			qe.value="";
			b="<%=jytjyear%>";
			e="<%=nowjytjyear%>";
			break;
	}
	if( parseInt(b) > parseInt(e) ) {
		obj.checked=false;
		document.all.date_readme.innerHTML="<font color='red'>没有可统计的数据</font>"
	}
}


function dealFormSubmit(){
	var flag = false;

	if( document.all.devlist != null ) {
		if( document.all.devlist.length != undefined ) {
			for( i=0; i<document.all.devlist.length; i++ ) {
				if( document.all.devlist(i).checked == true ) {
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
			if( document.all.devlist.checked == true ) {
				flag = true;
			}
			if( !flag ) {
				alert( "<bean:message key='pvxp.errors.devinfo.no.selected'/>" );
				document.all.devlist(0).focus();
				return false;
			}else {
				flag = false;
			}
		}
	}else {
		alert( "<bean:message key='pvxp.errors.devinfo.no.selected'/>" );
		return false;
	}
	
//检查报表名称是否填写完成..
	if( trim(document.all.repnm.value)=="" ) {
		alert( "请输入<bean:message key='pvxp.jytj.repnm'/>" );
		document.all.repnm.focus();
		return false;
	}
	
//查看是否选择卡类型
	for( i=0; i<document.all.cardtype.length; i++ ) {
			if( document.all.cardtype(i).checked ) {
				flag = true;
			}
		}
		if( !flag ) {
			alert( "请选择至少一种卡类型" );
			document.all.cardtype(0).focus();
			return false;
		}else {
			flag = false;
		}

//验证日期是否正确
	var qbv = document.all.outcarddate1.value;
	if( (document.all.outcarddate1.value=="") || (document.all.outcarddate1.value.length!=document.all.outcarddate1.maxLength) ) {
		alert( "请输入正确的起始日期\n\n长度不正确" );
		document.all.outcarddate1.focus();
		return false;
	}else if( !isNumber(qbv) ) {
		alert( "请输入正确的起始日期\n\n不应包含有非数字字符" );
		document.all.outcarddate1.focus();
		return false;
	}else if( (parseInt(qbv) < parseInt(b)) || (parseInt(qbv) > parseInt(e)) ) {
		alert( "请输入正确的起始日期\n\n超出可统计范围" );
		return false;
	}
	
	var ebv = document.all.outcarddate2.value;
	if( (document.all.outcarddate2.value=="") || (document.all.outcarddate2.value.length!=document.all.outcarddate2.maxLength) ) {
		alert( "请输入正确的截止日期\n\n长度不正确" );
		document.all.outcarddate2.focus();
		return false;
	}else if( !isNumber(ebv) ) {
		alert( "请输入正确的截止日期\n\n不应包含有非数字字符" );
		document.all.outcarddate2.focus();
		return false;
	}else if( (parseInt(ebv) < parseInt(qbv)) || (parseInt(ebv) > parseInt(e)) ) {
		alert( "请输入正确的截止日期\n\n超出可统计范围" );
		document.all.outcarddate2.focus();
		return false;
	}
	
	//验证时间是否正确
	var qbv = document.all.outcardtime1.value;
	//alert("开始时间");
	if( (document.all.outcardtime1.value=="") || (document.all.outcardtime1.value.length!=document.all.outcardtime1.maxLength) ) {
		alert( "请输入正确的起始时间\n\n长度不正确" );
		document.all.outcardtime1.focus();
		return false;
	}else if( !isNumber(qbv) ) {
		alert( "请输入正确的起始时间\n\n不应包含有非数字字符" );
		document.all.outcardtime1.focus();
		return false;
	}
	
	var ebv = document.all.outcardtime2.value;
	//alert("截止时间");
	if( (document.all.outcardtime2.value=="") || (document.all.outcardtime2.value.length!=document.all.outcardtime2.maxLength) ) {
		alert( "请输入正确的截止时间\n\n长度不正确" );
		document.all.outcardtime2.focus();
		return false;
	}else if( !isNumber(ebv) ) {
		alert( "请输入正确的截止时间\n\n不应包含有非数字字符");
		document.all.outcardtime2.focus();
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

function listDevs(){
	javascript:parent.showit();
	if( document.all.includeSubBank.checked==true){
		document.all.mincsubs.value="1";
	}else{
		document.all.mincsubs.value="0";
	}
	document.all.ifsdevs.value="1";
	document.all.mbankid.value=document.all.bankid.value;
	document.all.mdevtype.value=document.all.devtp.value;
	document.all.relistdev.submit();
}
function changelistdev(){
		document.all.ifsdevs.value="1";
		document.all.devlistarea.style.display="";
		document.all.sallbt.style.display="";
}
var hassall = false;
function selectAll(){
	if( document.all.devlist != null ){
		if( document.all.devlist.length != undefined ){
			if( hassall ){
				for( i=0;i<document.all.devlist.length;i++ ){
					if( document.all.devlist[i].checked == true ) document.all.devlist[i].checked=false;
				}
				hassall = false;
				document.all.sallbt1.src="images/default/bt_selectall.gif";
			}else{
				for( i=0;i<document.all.devlist.length;i++ ){
					if( document.all.devlist[i].checked == false ) document.all.devlist[i].checked=true;
				}
				hassall = true;
				document.all.sallbt1.src="images/default/bt_remove.gif";
			}

		}else{
			if( hassall ){
				if( document.all.devlist.checked == true ) document.all.devlist.checked=false;
				hassall = false;
				document.all.sallbt1.src="images/default/bt_selectall.gif";
			}else{
				if( document.all.devlist.checked == false ) document.all.devlist.checked=true;
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
		<form name="JymlMDeal_form" action="CardStaAdd.do" target="pvmain"
			method="post" onsubmit="javascript:return dealFormSubmit();">
			<input type="hidden" name="strcdlist" id="strcdlist">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="center" width="30" height="40">
						<img src="images/default/nav.gif">
					</td>
					<td>
						<font color=blue>PowerView XP </font>---
						<font class="location">出卡状态记录统计报表</font>
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
						<b>选择机构 &nbsp; <app:bankselect property="bankid"
								defaultValue="<%=mbankid%>" key="<%=mkey%>"
								keyValue="<%=mkeyvalue%>" onChange="listDevs();" />
							&nbsp;&nbsp; <input type="checkbox" onClick="listDevs()"
								<%if(!showsub){%> style="display:none" <%}%> id="includeSubBank"
								<%if(mincsubs.equals("1")){%> checked <%}%>> <%
 if (showsub) {
 %> 包含子机构 <%
 }
 %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
					</td>
					<td class="list_td_title" colspan="2" align="center">
						<div id="sallbt">
							&nbsp;
							<b>设备类型:</b>
							<app:devtpselect property="devtp" key="pvxp.devtype.select.all"
								keyValue="A" onChange="listDevs();" defaultValue="<%=devtype%>"
								showName="1" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:selectAll();" onClick="//parent.showit();"
								onFocus="this.blur()"> <img
									src="images/default/bt_selectall.gif" border="0" id="sallbt1"
									align="absmiddle"> </a>
						</div>
						<html:hidden property="incsubs" value="" />
						<html:hidden property="devs" value="" />
					</td>
				</tr>
				<tr id="devlistarea">
					<td class="list_tr1" colspan="4">
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
									if (devtype.equals("A")
											|| (myPubUtil.dealNull(tmp.getTypeno()).trim())
											.equals(devtype)) {
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
												Devinfo tmpdev = (Devinfo) myVector.get(i * 5
												+ j);

												String tmpdevno = myPubUtil.dealNull(
												tmpdev.getDevno()).trim();
												String tmpopentag = myPubUtil.dealNull(
												tmpdev.getOpentag()).trim();
								%>
								<td width="20%">
									&nbsp;
									<input name="devlist" id="devlist" type="checkbox"
										value="<%=tmpdevno%>">
									&nbsp;
									<a href="#" onClick="javascript:Show_devinfo('<%=tmpdevno%>');">
										<%
										if (tmpopentag.equals("0")) {
										%> <font color="red"><%=tmpdevno%> </font> <%
 } else {
 %> <font color="black"><%=tmpdevno%> </font> <%
 }
 %> </a>
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
			<br>
			<table width="100%" cellspacing="1" cellpadding="3"
				class="list_table_border">
				<tr align="center">
					<td class="list_td_title" colspan="4">
						<b>出卡失败记录统计报表列表</b>
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.jytj.repnm" /> <!-- 报表名称 --> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="text" name="repnm" id="repnm" size="16"
							maxlength="25">
					<td class="list_td_prom" width="20%" align="center">
						<b>卡类型</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="checkbox" name="cardtype" id="cardtype" value="1">
						借记卡&nbsp;&nbsp;
						<input type="checkbox" name="cardtype" id="cardtype" value="2">
						信用卡
						<input type="checkbox" name="cardtype" id="cardtype" value="3">
						其他
					</td>
				</tr>
				<!-- 日期 -->
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b>起始日期 </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="text" name="outcarddate1" id="outcarddate1" size="16"
							maxlength="8">
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b>截止日期</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="text" name="outcarddate2" id="outcarddate2" size="16"
							maxlength="8">
					</td>
				</tr>
				<!--  时间  -->
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b>起始时间</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="text" name="outcardtime1" id="outcardtime1" size="16"
							maxlength="6">
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b>截止时间</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="text" name="outcardtime2" id="outcardtime2" size="16"
							maxlength="6">
					</td>
				</tr>

				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b>统计出卡状态</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<input type="checkbox" name="outcardstatus" id="outcardstatus"
							value="1">
						发卡成功&nbsp;&nbsp;
						<input type="checkbox" name="outcardstatus" id="outcardstatus"
							value="0">
						发卡失败

					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b></b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
					</td>
				</tr>

				<!-- 介绍说明 -->
				<tr class="list_tr1">
					<td id="date_readme" colspan="4">
						<bean:message key="pvxp.tj.range.day" arg0="<%=jytjday%>"
							arg1="<%=nowjytjday%>" />
					</td>
				</tr>
			</table>
			<br>

			<p></p>
			<table>
				<tr>
					<td id="delbt">
						<input type="image" src="images/default/bt_ok.gif"
							onFocus="this.blur()">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/default/bt_back.gif" onFocus="this.blur()"
							onclick="javascript:history.back();" style="cursor:hand">
				</tr>
			</table>
		</form>
	</div>
	</form>
	<form id="relistdev" action="">
		<input name="mbankid" type="hidden">
		<input name="mincsubs" type="hidden">
		<input name="mdevtype" type="hidden">
		<input name="ifsdevs" type="hidden">
	</form>

</body>
</html:html>
<%
		} catch (Exception ex) {
		System.out.println("Exception = [" + ex + "]");
	}
%>
