<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.bean.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<app:validateCookie/>
<app:checkpower funcid="12" minpower="1" />
<%
	PubUtil myPubUtil = new PubUtil();
	String mbankid = myPubUtil.dealNull(request.getParameter("mbankid")).trim();
	String mincsubs = myPubUtil.dealNull(request.getParameter("mincsubs")).trim();
	
	String devtype = myPubUtil.dealNull(request.getParameter("mdevtype")).trim();
	if (devtype.equals(""))
		devtype = "A";
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
	
	
	//	获得数据库表 中 系统配置参数
	SysParam tmpSysParam = SysParamBean.getSysParam();
	
	String startday = "";
	String startmonth = "";
	String startyear = "";
	
	
	//wukp 20120307   设备 故障 统计 汇总 开始 时间
	if (tmpSysParam == null) {
		startday = "99991231";
		startmonth = "999912";
		startyear = "9999";
	} else {
		startday = myPubUtil.dealNull(tmpSysParam.getBdofsbtjday()).trim();
		startmonth = myPubUtil.dealNull(tmpSysParam.getBdofsbtjmonth()).trim();
		startyear = myPubUtil.dealNull(tmpSysParam.getBdofsbtjyear()).trim();
	}
	
	
	//wukp 20120307  结束时间都是当前时间的前一天，前一个月，前一年
	String endday = myPubUtil.getOtherDate(-1);
	String endmonth = myPubUtil.getPreMonth();
	String endyear = "";
	try {
		endyear = String.valueOf(Integer.parseInt((myPubUtil.getNowDate(1)).substring(0, 4)) - 1);
	} catch (Exception e) {
		System.out.println(e);
	}
%>

<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">

	<html:base />
	<script language="JavaScript" src="js/pv.js"></script>
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

function listDevs(){
	javascript:parent.showit();
	if( document.all.includeSubBank.checked==true){
		document.all.mincsubs.value="1";
	}else{
		document.all.mincsubs.value="0";
	}

	document.all.mbankid.value=document.all.bankid.value;
	document.all.mdevtype.value=document.all.devtp.value;
	document.all.relistdev.submit();
}

var hassall = false;


//    设备列表  全选功能
function selectAll(){
	if( document.all.devlist != null ){
		if( document.all.devlist.length != undefined ){
			if( hassall ){
				for( i=0;i<document.all.devlist.length;i++ ){
					if( document.all.devlist[i].checked == true ) document.all.devlist[i].checked=false;
				}
				hassall = false;
				document.all.sallbt_img.src="images/default/bt_selectall.gif";
			}else{
				for( i=0;i<document.all.devlist.length;i++ ){
					if( document.all.devlist[i].checked == false ) document.all.devlist[i].checked=true;
				}
				hassall = true;
				document.all.sallbt_img.src="images/default/bt_clear.gif";
			}

		}else{
		
			if( hassall ){
				if( document.all.devlist.checked == true ) document.all.devlist.checked=false;
				hassall = false;
				document.all.sallbt_img.src="images/default/bt_selectall.gif";
			}else{
				if( document.all.devlist.checked == false ) document.all.devlist.checked=true;
				hassall = true;
				document.all.sallbt_img.src="images/default/bt_clear.gif";
			}
		}
	}
}

//是否 需要 包含 子机构
var hassall1 = false;
function selectAllsub(){
	if( document.all.subdevice != null ){
		if( document.all.subdevice.length != undefined ){
			if( hassall1 ){
				for( i=0;i<document.all.subdevice.length;i++ ){
					if( document.all.subdevice[i].checked == true ) document.all.subdevice[i].checked=false;
				}
				hassall1 = false;
				document.all.sallsubbt_img.src="images/default/bt_selectall.gif";
			}else{
				for( i=0;i<document.all.subdevice.length;i++ ){
					if( document.all.subdevice[i].checked == false ) document.all.subdevice[i].checked=true;
				}
				hassall1 = true;
				document.all.sallsubbt_img.src="images/default/bt_clear.gif";
			}

		}else{
			if( hassall1 ){
				if( document.all.subdevice.checked == true ) document.all.subdevice.checked=false;
				hassall1 = false;
				document.all.sallsubbt_img.src="images/default/bt_selectall.gif";
			}else{
				if( document.all.subdevice.checked == false ) document.all.subdevice.checked=true;
				hassall1 = true;
				document.all.sallsubbt_img.src="images/default/bt_clear.gif";
			}
		}
	}
}


//显示选中的设备的详细信息
function Show_devinfo( devno ) {
      var xpos = event.screenX;
      var ypos = event.screenY+10;
      if ( (xpos+750) > 1024 ) xpos=1024-750-10;
      if ( (ypos+180) >  768 ) ypos=ypos-180-50;
      open("Devinfo_Show.jsp?devno="+devno,null,"height=180,width=750,left="+xpos+",top="+ypos+",status=no,toolbar=no,menubar=no,location=no");
}



//处理 报表 要统计的 时间
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
			b="<%=startday%>";
			e="<%=endday%>";
			break;
		case 'month':
			qb.maxLength=6;
			qe.maxLength=6;
			qb.value="";
			qe.value="";
			b="<%=startmonth%>";
			e="<%=endmonth%>";
			break;
		case 'year':
			qb.maxLength=4;
			qe.maxLength=4;
			qb.value="";
			qe.value="";
			b="<%=startyear%>";
			e="<%=endyear%>";
			break;
	}
	
	// 如果数据库中开始日期大于当前日期的前一天日期
	if( parseInt(b) > parseInt(e) ) {
 		document.all['delbt'].style.display='none';
		obj.checked=false;
		document.all.date_readme.innerHTML="<center><font color='red' size='5'>数据库中尚无可统计数据，请核查！</font></center>"
 	} else {
 		document.all['delbt'].style.display='';
	}
}


//提交前 处理报表
function dealFormSubmit(){
	var flag = false;

	if( document.all.devlist != null ) {//设备信息是否有
		if( document.all.devlist.length != undefined ) {//是否有设备个数
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

//设备 零部件 列表
	flag = false;
	if( document.all.subdevice != null ) {
		if( document.all.subdevice.length != undefined ) {
			for( i=0; i<document.all.subdevice.length; i++ ) {
				if( document.all.subdevice(i).checked == true ) {
					flag = true;
				}
			}
			if( !flag ) {
				alert( "请至少选择一个子设备" );
				return false;
			}else {
				flag = false;
			}
		}else {
			if( document.all.subdevice.checked == true ) {
				flag = true;
			}
			if( !flag ) {
				alert( "请至少选择一个子设备" );
				document.all.subdevice(0).focus();
				return false;
			}else {
				flag = false;
			}
		}
	}else {
		alert( "请至少选择一个子设备" );
		return false;
	}


//报表名称
	if( trim(document.all.repnm.value)=="" ) {
		alert( "请输入<bean:message key='pvxp.jytj.repnm'/>" );
		document.all.repnm.focus();
		return false;
	}

//报表类型
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




//时间格式校验
function checktime(time) 
{    
  //  var re=/^(20[0-9]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/ ; 
     
    var re=/^([0-9][0-9][0-9][0-9](0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1]))$/; 
    if(re.test(time)) 
    { 
    	return 1;
    } 
    else 
    {
        return 2;
    }
}


//报表起始时间
	var qbv = document.all.qbegin.value;
	if( (document.all.qbegin.value=="") || (document.all.qbegin.value.length!=document.all.qbegin.maxLength) ) {
		alert( "请输入正确的<bean:message key='pvxp.jytj.begin'/>\n\n长度不正确" );
		document.all.qbegin.focus();
		return false;
	}else if( !isNumber(qbv) ) {
		alert( "请输入正确的<bean:message key='pvxp.jytj.begin'/>\n\n不应包含有非数字字符" );
		document.all.qbegin.focus();
		return false;
	}
	
	/*
	else if(checktime(qbv)==2) {//判读输入的时间是正确的格式
		alert( "请输入正确的<bean:message key='pvxp.jytj.begin'/>\n\输入正确时间格式" );
		document.all.qbegin.focus();
		return false;
	}
	*/
	
	else if( (parseInt(qbv) < parseInt(b)) || (parseInt(qbv) > parseInt(e)) ) {
		alert( "请输入正确的<bean:message key='pvxp.jytj.begin'/>\n\n超出可统计范围" );
		document.all.qbegin.focus();
		return false;
	}


//报表截至时间
	var ebv = document.all.qend.value;
	if( (document.all.qend.value=="") || (document.all.qend.value.length!=document.all.qend.maxLength) ) {
		alert( "请输入正确的<bean:message key='pvxp.jytj.end'/>\n\n长度不正确" );
		document.all.qend.focus();
		return false;
	}else if( !isNumber(ebv) ) {
		alert( "请输入正确的<bean:message key='pvxp.jytj.end'/>\n\n不应包含有非数字字符" );
		document.all.qend.focus();
		return false;
	}
/*	
	else if(checktime(ebv)==2) {//判读输入的时间是正确的格式
		alert( "请输入正确的<bean:message key='pvxp.jytj.begin'/>\n\输入正确时间格式" );
		document.all.qbegin.focus();
		return false;
	}
	*/
	
	else if( (parseInt(ebv) < parseInt(qbv)) || (parseInt(ebv) > parseInt(e)) ) {
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
	DevtjMDeal_form.reset();
}


</script>
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">

		<form name="DevtjMDeal_form" action="DevtjMDeal.do" target="pvmain"
			method="post" onsubmit="javascript:return dealFormSubmit();">

			<table width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="middle" width="30" height="40">
						<img src="images/default/nav.gif">
					</td>
					<td>
						<font color=blue>PowerView XP </font> ---
						<font class="location">设备故障统计</font>
					</td>
				</tr>
			</table>




			<table width="100%" cellspacing="1" cellpadding="2"
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
									src="images/default/bt_selectall.gif" border="0"
									id="sallbt_img" align="absmiddle"> </a>
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
										if (devtype.equals("A") || (myPubUtil.dealNull(tmp.getTypeno()).trim()).equals(devtype)) {
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
					%>
					<td width="20%">
						&nbsp;
						<input name="subdevice" id="subdevice" type="checkbox"
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


			<table width="100%" cellspacing="1" cellpadding="3"
				class="list_table_border">
				<tr align="center">
					<td class="list_td_title" colspan="4">
						<b>报表设置</b>
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
							onClick="selectType(1);dealQSeq(this);" checked>
						按日统计&nbsp;&nbsp;
						<input type="radio" name="qseq" id="qseq2" value="month"
							onClick="selectType(2);dealQSeq(this);">
						按月统计&nbsp;&nbsp;
						<input type="radio" name="qseq" id="qseq3" value="year"
							onClick="selectType(3);dealQSeq(this);">
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

					<form id="relistdev" action="">
						<input name="mbankid" type="hidden">
						<input name="mincsubs" type="hidden">
						<input name="mdevtype" type="hidden">

					</form>

					<script>
selectType(1);
dealQSeq(document.getElementById('qseq1'));
</script>
</body>
</html:html>
