<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="1" />

<html:html locale="true">
<head>
	<title></title>
	<html:base />
</head>
<body>
	<% 
		PubUtil myPubUtil = new PubUtil();
		String mbankid = myPubUtil.dealNull(request.getParameter("mbankid")).trim();
		String mincsubs = myPubUtil.dealNull(request.getParameter("mincsubs")).trim();
		String slctdevs = myPubUtil.dealNull(request.getParameter("ifsdevs")).trim();
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

<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script>
function goFullscreen(targeturl,mtarget){
// alert('1'+targeturl);
//alert('goFullscreen');
  var newwin=window.open(targeturl,mtarget,'scrollbars');
 //  alert('2'+newwin);
  if(document.all){
    newwin.moveTo(0,0);
    newwin.resizeTo(screen.width,screen.height);
  }
}



/////////
function doMoni(){
//alert('doMoni');
try{
  if( document.all.includeSubBank.checked==true){
    document.all.incsubs.value="1";
  }else{
    document.all.incsubs.value="0";
  }
  
  if( document.all.slctdevs.checked==false ){
    if(document.all.devarray != null){
      document.all.devs.value="A"
      goFullscreen('',document.all['sform'].target);
      return true;
    }else{
      alert("所选机构（范围）暂时没有设备");  
      return false;
    }
  }
  
  var len = 0;
  try{
    if( document.all.devarray != null ){
      if( document.all.devarray.length != undefined ){
        len = document.all.devarray.length;
      }else{
        len = -1;
      }
    }else{
      len = 0;
    }
  }catch(e){
    len = 0;
  }
  if( len==0 ){
    alert("所选机构（范围）暂时没有设备");  
    return false;
  }
  if( len==-1 ){
    if( document.all.devarray.checked==true ){
      document.all.devs.value=","+document.all.devarray.value+",";
      goFullscreen('',document.all['sform'].target);
      return true;
    }else{
      alert("请至少选择一台设备");
      return false;
    }
  }

  document.all.devs.value=",";
  for( i=0;i<len;i++ ){
    if( document.all.devarray[i].checked==true )
      document.all.devs.value += document.all.devarray[i].value+",";
  }
  if( document.all.devs.value == "," ){
    alert("请至少选择一台设备");
    return false;
  }else{
    goFullscreen('',document.all['sform'].target);
    return true;
  }
}catch(e){
  alert("系统故障"+e);
  return false;
}
}



//////////////
function listDevs(){
//alert('listDevs');
  javascript:parent.showit();
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



//////////
function changelistdev(){
//alert('changelistdev');
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



/////////
var hassall = false;
function selectAll(){
//alert('selectAll');
  if( document.all.devarray != null ){
    if( document.all.devarray.length != undefined ){
      if( hassall ){
        for( i=0;i<document.all.devarray.length;i++ ){
          if( document.all.devarray[i].checked == true ) document.all.devarray[i].checked=false;
        }
        hassall = false;
        document.all.sallbt_img.src="images/default/bt_selectall.gif";
      }else{
        for( i=0;i<document.all.devarray.length;i++ ){
          if( document.all.devarray[i].checked == false ) document.all.devarray[i].checked=true;
        }
        hassall = true;
        document.all.sallbt_img.src="images/default/bt_clear.gif";
      }

    }else{
      if( hassall ){
        if( document.all.devarray.checked == true ) document.all.devarray.checked=false;
        hassall = false;
        document.all.sallbt_img.src="images/default/bt_selectall.gif";
      }else{
        if( document.all.devarray.checked == false ) document.all.devarray.checked=true;
        hassall = true;
        document.all.sallbt_img.src="images/default/bt_clear.gif";
      }
    }
  }
}


//处理异常信息
	function displayErrorMsg(e) {
		tmp = "<hr>";
		for (x in e) {
			tmp += "" + x + "=>" + e[x] + "<br/>/n";
		}

		alert(tmp);
	}

</script>
</head>
<body onload="javascript:parent.hidit();">


	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" align="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP </font>---
				<font class="location"><bean:message
						key="pvxp.devlistmoni.title" /> </font>
			</td>
		</tr>
	</table>

	<table width="100%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<form id="sform" action="DevListMoni_Main.jsp" target="DevListMoni"
			onsubmit="return doMoni()" method="post">
		<tr>
			<td class="list_td_title" width="470">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr valign="middle">
						<td>
							监控机构
						</td>
						<td>
							&nbsp;
							<app:bankselect property="bankid" defaultValue="<%=mbankid%>"
								onChange="listDevs();" key="<%=mkey%>" keyValue="<%=mkeyvalue%>" />
						</td>
						<td>
							&nbsp;&nbsp;
							<input type="checkbox" onClick="listDevs()" <%if(!showsub){%>
								style="display:none" <%}%> id="includeSubBank"
								<%if(mincsubs.equals("1")){%> checked <%}%>>
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
							按设备监控
						</td>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="image" src="images/default/bt_ok.gif"
								onFocus="this.blur()">
						</td>
					</tr>
				</table>
			</td>
			<td class="list_td_title" width="250">
				&nbsp;监控级别
				<input type="radio" name="level" value="0" checked="checked" />
				&nbsp;全部
				<input type="radio" name="level" value="1" />
				&nbsp;警告
				<input type="radio" name="level" value="2" />
				&nbsp;故障
			</td>
			<td class="list_td_title" align="left">
				<div id="sallbt" <%if(!slctdevs.equals("1")){%> style="display:none"
					<%}%>>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr valign="middle">
							<td>
								设备类型
							</td>
							<td>
								&nbsp;
								<app:devtpselect property="devtp" key="pvxp.devtype.select.all"
									keyValue="A" onChange="listDevs();" defaultValue="<%=devtype%>"
									showName="1" />
							</td>
							<td>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<img id="sallbt_img" style="cursor:hand"
									src="images/default/bt_selectall.gif" onclick="selectAll()">
							</td>
						</tr>
					</table>
				</div>
				<html:hidden property="incsubs" value="" />
				<html:hidden property="devs" value="" />
			</td>
		</tr>

		</form>
		<tr id="devlistarea" <%if(!slctdevs.equals("1")){%>
			style="display:none" <%}%>>
			<td colspan="3">
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
								if (tmp != null && (devtype.equals("A") || (myPubUtil.dealNull(tmp.getTypeno()).trim()).equals(devtype))) {
							
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
						%>
						<td width="20%">
							&nbsp;
							<input name="devarray" id="devarray" type="checkbox"
								value="<%=tmpdevno%>">
							&nbsp;
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
	<form id="relistdev" action="">
		<input name="mbankid" type="hidden">
		<input name="mincsubs" type="hidden">
		<input name="mdevtype" type="hidden">
		<input name="ifsdevs" type="hidden">
		</from>
</body>
</html:html>
