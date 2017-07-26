<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.bean.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>
 
<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="11" minpower="1" />
<%
	PubUtil myPubUtil = new PubUtil();

	SysParamBean mySysParamBean = new SysParamBean();
	SysParam tmpSysParam = mySysParamBean.getSysParam();

	String jytjday = "";
	String jytjmonth = "";
	String jytjyear = "";
	if( tmpSysParam == null ) {
		jytjday = "99991231";
		jytjmonth = "999912";
		jytjyear = "9999";
	}else {
		jytjday = myPubUtil.dealNull(tmpSysParam.getBdofjytjday()).trim();
		jytjmonth = myPubUtil.dealNull(tmpSysParam.getBdofjytjmonth()).trim();
		jytjyear = myPubUtil.dealNull(tmpSysParam.getBdofjytjyear()).trim();
	}

	String nowjytjday = myPubUtil.getOtherDate(-1);
	String nowjytjmonth = myPubUtil.getPreMonth();
	String nowjytjyear = "";

	try{
		nowjytjyear = String.valueOf( Integer.parseInt( (myPubUtil.getNowDate(1)).substring(0,4) )-1 );
	}catch(Exception e){
	}

	String mbankid = myPubUtil.dealNull(request.getParameter("mbankid")).trim();
	String mincsubs  = myPubUtil.dealNull(request.getParameter("mincsubs")).trim();
	String devtype = myPubUtil.dealNull(request.getParameter("mdevtype")).trim();
	if( devtype.equals("") ) devtype = "A";
	if( mincsubs.equals("") ) mincsubs = "1";

	String opbank = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID,request)).trim();
  String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST,request)).trim();
	if( mbankid.equals("") ) mbankid = opbank;
	String mkey = "";
  String mkeyvalue = "";
	boolean showsub = true;
  if( authlist.equals("*") ){
  	mkey = "pvxp.bankinfo.select.all";
  	mkeyvalue = opbank;
  	if( mbankid.equals(opbank) ){
  		showsub = false;
  		mincsubs = "1";
  	}
  }
%>
<html:html locale="true">
<head>
	<title>设备总体运行情况统计</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>
	<script language="JavaScript">
function disTips( divid ) {
	document.all['tjday'].style.display="none";
	document.all['tjmonth'].style.display="none";
	document.all['tjyear'].style.display="none";
	document.all[divid].style.display="";
}
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
		<form name="YxtjMDeal_form" action="YxtjMDeal.do" target="pvmain"
			method="post" onsubmit="javascript:return dealFormSubmit();">
			<input type="hidden" name="strcdlist" id="strcdlist">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="center" width="30" height="40">
						<img src="images/default/nav.gif">
					</td>
					<td>
						<font color=blue>PowerView XP </font>---
						<font class="location">设备总体运行情况统计</font>
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
								keyValue="<%=mkeyvalue%>" onChange="listDevs();" /> &nbsp;&nbsp;
							<input type="checkbox" onClick="listDevs()" <%if(!showsub){%>
								style="display:none" <%}%> id="includeSubBank"
								<%if(mincsubs.equals("1")){%> checked <%}%>> <%if(showsub){%>
							包含子机构 <%}%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>
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
	if( mincsubs.equals("1") ) mbankid = myBankinfoModel.getBankRange(mbankid);
	DevinfoModel myDevinfoModel = new DevinfoModel();
	List myDevList = myDevinfoModel.getDevListOfBank(mbankid);
	Vector myVector = new Vector();
	if( myDevList!=null && !myDevList.isEmpty() ){
		int devnum = myDevList.size();



    for( int i=0;i<devnum;i++ ){
      Devinfo tmp = (Devinfo)myDevList.get(i);
      if( devtype.equals("A")||(myPubUtil.dealNull(tmp.getTypeno()).trim()).equals(devtype) ){
				myVector.add(tmp);
      }
    }
    devnum = myVector.size();


		int trnum = devnum/5;
		if( devnum%5>0 ) trnum++;
		for( int i=0;i<trnum;i++ ){
%>
							<tr class="list_tr<%=i%2%>">
								<%
			for(int j=0;j<5;j++){
				if( i*5+j<devnum ){
					Devinfo tmpdev = (Devinfo)myVector.get(i*5+j);


							String tmpdevno = myPubUtil.dealNull(tmpdev.getDevno()).trim();
							String tmpopentag = myPubUtil.dealNull(tmpdev.getOpentag()).trim();
%>
								<td width="20%">
									&nbsp;
									<input name="devlist" id="devlist" type="checkbox"
										value="<%=tmpdevno%>">
									&nbsp;
									<a href="#" onClick="javascript:Show_devinfo('<%=tmpdevno%>');">
										<%
								if( tmpopentag.equals("0") ) {
%> <font color="red"><%=tmpdevno%>
									</font> <%
								}else {
%> <font color="black"><%=tmpdevno%>
									</font> <%
								}
%> </a>
								</td>
								<%
				}else{
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
	if( myVector==null || myVector.isEmpty() ){
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
						<b>设备总体运行情况统计</b>
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
						<bean:message key="pvxp.tj.range.day" arg0="<%=jytjday%>"
							arg1="<%=nowjytjday%>" />
					</td>
				</tr>
			</table>
			<br>
			<!--交易统计分类-->
			<table width="100%" cellspacing="1" cellpadding="3"
				class="list_table_border">
				<tr id="trcdmode">
					<td class="list_td_title" width="20%" align="center">
						<b><bean:message key="pvxp.jytj.sortmode" />
						</b>
					</td>
					<td class="list_td_title" colspan="3">
						&nbsp;
						<select name="sortmode" id="sortmode"
							onChange="javascript:dealSortMode(this);"
							onMouseWheel="javascript:return false;">
							<%
	int sortmodenum = Integer.parseInt( myPubUtil.ReadConfig("TradeType","TypeSortNum","0","Trade.ini") );

	for( int i=1; i<=sortmodenum; i++ ) {
%>
							<option value="<%=i%>">
								<%=myPubUtil.ReadConfig("TradeType","Type"+i+"_Name","","Trade.ini")%>
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
	for( int i=1; i<=sortmodenum; i++ ) {
		int sortnamenum = Integer.parseInt( myPubUtil.ReadConfig("TradeType","Type"+i+"_Num","0","Trade.ini") );
%>
				<tr class="list_tr<%=i%2%>" name="Type<%=i%>" id="Type<%=i%>">
					<td colspan="4">
						<%
		for( int j=1; j<=sortnamenum; j++ ) {
			String tmpstr = myPubUtil.ReadConfig("TradeType","Type"+i+"_"+j,"","Trade.ini");
			StringTokenizer wb = new StringTokenizer( tmpstr, "|" );
			String sortname = wb.nextToken();
			if( wb.hasMoreTokens() ) {
				tmpstr = wb.nextToken();
			}
%>
						<input type="checkbox" name="sortname" id="sortname"
							value="<%=tmpstr%>">
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

					</div>
					</form>
					<form id="relistdev" action="">
						<input name="mbankid" type="hidden">
						<input name="mincsubs" type="hidden">
						<input name="mdevtype" type="hidden">
						<input name="ifsdevs" type="hidden">
					</form>
					<script>
<%if( !authlist.equals("*") ){
if(!(authlist.substring(10,11)).equals("2")){%>
if(document.all['delbt'])
	document.all['delbt'].style.display='none';
<%}}%>
dealSortMode(document.all['sortmode']);
function _reset(){
	YxtjMDeal_form.reset();
	document.all.sallbt1.src='images/default/bt_selectall.gif';
	document.all.selall.src='images/default/bt_selectall.gif';
	hassall = false;
	selall = false;
}
selectType(1);
dealQSeq(document.getElementById('qseq1'));
</script>
</body>
</html:html>
