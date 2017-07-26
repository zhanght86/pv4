<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="3" minpower="2" />

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
%>
<html:html locale="true">
<head>
	<title><bean:message key="pvxp.fileupload.title" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>
	<script language="JavaScript">
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
				document.all.sallbt1.src="images/default/bt_clear.gif";
			}

		}else{
			if( hassall ){
				if( document.all.devlist.checked == true ) document.all.devlist.checked=false;
				hassall = false;
				document.all.sallbt1.src="images/default/bt_selectall.gif";
			}else{
				if( document.all.devlist.checked == false ) document.all.devlist.checked=true;
				hassall = true;
				document.all.sallbt1.src="images/default/bt_clear.gif";
			}
		}
	}
}
    function deal_devicelist( bankid ) {
      var devlist = document.all.item("devlist");
      if ( devlist != null ) {
        for ( k=0; k<devlist.length; k++ ) {
          devlist(k).checked = false;
        }
      }else {
        document.all['sbmt'].disabled=true;
        document.all['rst'].disabled=true;
        var rc_ops = document.all.item("rc_operation");
        for ( i=0; i<rc_ops.length; i++ ) {
          rc_ops(i).disabled=true;
        }
      }
      for ( i=0; i<document.all['bankstl'].length; i++ ) {
        if ( bankid == document.all.item("bankstl")(i).value ){
          document.all['bankid'+bankid].style.display = '';
          for ( j=0; j<document.all['bankstl'].length; j++ ) {
            if ( bankid != document.all.item("bankstl")(j).value ){
              var mybankid = document.all.item("bankstl")(j).value;
              document.all['bankid'+mybankid].style.display = 'none';
            }
          }
        }
      }
    }
    function Show_devinfo( devno ) {
      var xpos = event.screenX;
      var ypos = event.screenY+10;
      if ( (xpos+750) > 1024 ) xpos=1024-750-10;
      if ( (ypos+180) >  768 ) ypos=ypos-180-50;
      open("Devinfo_Show.jsp?devno="+devno,null,"height=180,width=750,left="+xpos+",top="+ypos+",status=no,toolbar=no,menubar=no,location=no");
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
				return false;
			}else {
				flag = false;
			}
		}
	}else {
		alert( "<bean:message key='pvxp.errors.devinfo.no.selected'/>" );
		return false;
	}

	
	parent.showit();
	document.all['Devmaint'].submit();
}

function _reset() {
	hassall = false;
	document.all.sallbt1.src="images/default/bt_selectall.gif";
	Devmaint.reset();
}
    
	
	</script>
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
					<a href="#"
						onClick="javascript:parent.parent.goUrl('Sys_Manage_Remote.jsp');">远程设置</a>&nbsp;&nbsp;
					<a href="#"
						onClick="javascript:parent.parent.goUrl('NewsTrans.jsp');">消息发送</a>&nbsp;&nbsp;
					<font class="location">远程维护</font>
				</td>
			</tr>
		</table>

		<table width="100%" cellspacing="1" cellpadding="2"
			class="list_table_border">
			<html:form action="/DevMaint.do" method="post" styleId="Devmaint">

				<tr>
					<td class="list_td_title" width="473">
						选择机构 &nbsp;
						<app:bankselect property="bankid" defaultValue="<%=mbankid%>"
							key="<%=mkey%>" keyValue="<%=mkeyvalue%>" onChange="listDevs();" />
						&nbsp;&nbsp;
						<input type="checkbox" onClick="listDevs()" <%if(!showsub){%>
							style="display:none" <%}%> id="includeSubBank"
							<%if(mincsubs.equals("1")){%> checked <%}%>>
						<%
						if (showsub) {
						%>
						包含子机构
						<%
						}
						%>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td class="list_td_title" align="left">
						<div id="sallbt">
							&nbsp;设备类型:
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
										%> <font color="red"><%=tmpdevno%>
									</font> <%
 } else {
 %> <font color="black"><%=tmpdevno%>
									</font> <%
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

							<tr>
								<td class="list_td_prom" width="20%" align="center">
									<b>EWF保护</b>
								</td>
								<td class="list_td_detail" colspan="4">
									<input type="radio" name="maintflag" id="maintflag" value="00"
										checked>
									关闭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="maintflag" id="maintflag" value="01">
									开启&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

								</td>
							</tr>

						</table>
		</table>

		<p></p>
		<table align="center">
			<tr>
				<td id="delbt">
					<img src="images/default/bt_ok.gif" onFocus="this.blur()"
						onclick="javascript:dealFormSubmit();" style="cursor:hand">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="images/default/bt_reset.gif" onFocus="this.blur()"
						onclick="javascript:_reset()" style="cursor:hand">
				</td>
			</tr>
		</table>

	</div>

	</html:form>
	<form id="relistdev" action="">
		<input name="mbankid" type="hidden">
		<input name="mincsubs" type="hidden">
		<input name="mdevtype" type="hidden">
		<input name="ifsdevs" type="hidden">
	</form>

</body>
</html:html>
