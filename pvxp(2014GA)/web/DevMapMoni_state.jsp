<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.pojo.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />

<%
	PubUtil myPubUtil = new PubUtil();
	String bankid = myPubUtil.dealNull(request.getParameter("bankid")).trim();
	String strlen = String.valueOf(("DevMoni" + bankid).length());
	strlen = PubUtil.strFormat(strlen, 8, 1, '0');
	String path = request.getRealPath("");
	java.io.File mapfile = new java.io.File(path + "/map", bankid + ".map");
	if (mapfile.exists()) {
%>
<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<jsp:include page="DevStateCode_JS.jsp" flush="true" />
	<jsp:include page="DevStateNo_JS.jsp" flush="true" />
	<script language="JavaScript" type="text/JavaScript">
<!--
function DealUndefined(str){
	if(str!=undefined){
		return str;
	}else{
		return "";
	}
}
function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_setTextOfLayer(objName,x,newText) { //v4.01
  if ((obj=MM_findObj(objName))!=null) with (obj)
    if (document.layers) {document.write(unescape(newText)); document.close();}
    else innerHTML = unescape(newText);
}
function insertTr_Style(tb,mid,classname){
	if(MM_findObj(tb)!=null&&MM_findObj(mid)==null){
		var table = document.all[tb];
		//alert("start");
	  var rowcount = table.rows.length;
	  var row = table.insertRow(rowcount);
	  row.id=mid;
	  row.className=classname;
	}
}
function insertTr(tb,mid){
	insertTr_Style(tb,mid,"");
}
function insertTd_Style(tr,mid,mywidth,classname){
	if(MM_findObj(tr)!=null&&MM_findObj(mid)==null){
		var m_tr = document.all[tr];
		var colcount = m_tr.cells.length;
		var cell = m_tr.insertCell(colcount);
		//cell.height="10";
		cell.id=mid;
		cell.className=classname;
		cell.width=mywidth;
		cell.innerHTML = "&nbsp;";
	}
}
function insertTd(tr,mid){
	insertTd_Style(tr,mid,"","");
}
function extendTable(imgid , trid){
	try{
		if(MM_findObj(trid)!=null){
			var m_img = document.all[imgid];
			var m_tr = document.all[trid];
			if( m_tr.style.display=="" ){
				m_img.src="images/default/more.gif";
				m_tr.style.display="none";
			}else{
				m_img.src="images/default/simple.gif";
				m_tr.style.display="";
			}
		}
	}catch(e){}
}
var cryflag = true;
var crying  = false;
var crylevel="2";
/*警报发声*/
function cryaloud(){
	if(crying||!cryflag){
		return;
	}
	crying = true;
	parent.devmap_moni_console.crysound.play();
}
function crypause(){
	if(crying){
		cryflag=false;
		crystop();
		parent.devmap_moni_console.pausecry.src="images/default/cryagain.gif";
	}else{
		cryflag=true;
		changeCryLevel(crylevel);
		parent.devmap_moni_console.pausecry.src="images/default/pausecry.gif";
	}
}
function crystop(){
	parent.devmap_moni_console.crysound.stop();
	crying = false;
}
function changeCryLevel(m_level){
	crystop();
	crylevel=m_level;
	var i = 0;
	var table = document.all["showlist"];
	var rowcount = table.rows.length;
	for(i=0;i<rowcount;i++){
		var row = table.rows(i);
		if( row.level!=undefined ){
			var rowlevel = row.level;
			if( rowlevel>=crylevel ){
				cryaloud();
				break;
			}
		}
	}
}
function hideButMe(mtype,mno){
	
	var myid = "";
	var fatherid = "";
	if( mtype == "dev" ){
		myid = "dev_"+mno+"_show";
		fatherid = "showlist";
	}else{
		myid = "bank_"+mno+"_show";
		fatherid = "banklist";
	}
	var table = document.all[fatherid];
	var rowcount = table.rows.length;

	for(i=0;i<rowcount;i++){
		var row = table.rows(i);
		if( row.id!=myid ){
			row.style.display="none";
		}else{
			row.style.display="";
		}
		
	}
}
function insertDevStateTable(tr,mid){
	if(MM_findObj(tr)!=null&&MM_findObj(mid)==null){
		var m_tr = document.all[tr];
		var colcount = m_tr.cells.length;
		var cell = m_tr.insertCell(colcount);
		cell.id=mid+"_outtd";
		//cell.colSpan="10";
		cell.innerHTML = "<table width=\"100%\" id="+mid+" border=\"0\" class=\"list_table_border\" cellspacing=\"1\" cellpadding=\"3\"></table>";
		return cell.id;
	}
}
function ltrim(str)
{
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
    if (whitespace.indexOf(s.charAt(0)) != -1)
    {
        var j=0, i = s.length;
        while (j < i && whitespace.indexOf(s.charAt(j)) != -1)
        {
            j++;
        }
        s = s.substring(j, i);
    }
    return s;
}
function rtrim(str)
{
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
    if (whitespace.indexOf(s.charAt(s.length-1)) != -1)
    {
        var i = s.length - 1;
        while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1)
        {
            i--;
        }
        s = s.substring(0, i+1);
    }
    return s;
}
function DevMoniFlash_DoFSCommand(command, args){
	try{
		if (command == "setDevState") {
			var mpos = args.indexOf(":");
		 	var devno = rtrim(ltrim(args.substring(0,mpos)));
		 	var dev_tr = "dev_"+devno;
		 	var allstr = args.substring(mpos+1);
		 	var myTTlastTime = "0";
		 	var myTTstate ="-1";		//默认未知状态
		 	if( MM_findObj(dev_tr)!=null&&devno.length>0 ){
				var statesArray = allstr.split("|");
		 		for(var i=0;i<statesArray.length;i++){
		 		var recordArray = statesArray[i].split(",");
		 		if( MM_findObj("state_mx_"+devno+"_"+recordArray[0])==null ){
			 		insertTr_Style("devstl_"+devno,"state_mx_"+devno+"_"+recordArray[0],"list_tr"+i%2);								//状态明细
			 		insertTd("state_mx_"+devno+"_"+recordArray[0],"devobj_"+devno+"_"+recordArray[0]);											
			 		insertTd("state_mx_"+devno+"_"+recordArray[0],"devmstate_"+devno+"_"+recordArray[0]);											
			 		insertTd("state_mx_"+devno+"_"+recordArray[0],"devstart_"+devno+"_"+recordArray[0]);											
			 		insertTd("state_mx_"+devno+"_"+recordArray[0],"devlast_"+devno+"_"+recordArray[0]);											
		 		}
		 		var ifwrite = true;
		 		try{
		 			if(document.all["devlast_"+devno+"_"+recordArray[0]].value>recordArray[4]+recordArray[5]){
		 				ifwrite = false;
		 				return;
		 			}
		 		}catch(e){
		 			ifwrite=true;
		 		}
		 		
		 		if(ifwrite){
				 	document.all["devlast_"+devno+"_"+recordArray[0]].value=recordArray[4]+recordArray[5];
				 	var tmpobjname = "";
				 	try{
				 		tmpobjname = eval("StateNo_"+recordArray[0]);
				 	}catch(e){
				 		tmpobjname = recordArray[0];
				 	}
				 	MM_setTextOfLayer("devobj_"+devno+"_"+recordArray[0],'',DealUndefined(tmpobjname));
				 	var tmpcodeArray;
				 	var tmpstatename = "";
				 	var tmpstatelevel= "-1";
				 	var tmplevelname = "";
				 	try{
				 		tmpstatename= eval("Code_"+recordArray[1]);
				 		tmpcodeArray = tmpstatename.split(",");
				 		tmpstatename=tmpcodeArray[0];
				 		tmpstatelevel = tmpcodeArray[1];
				 	}catch(e){
				 		tmpstatename = recordArray[1];
				 		tmpstatelevel = "-1";
				 	}
				 	
				 	if(myTTstate<tmpstatelevel){
				 		myTTstate= tmpstatelevel;
				 	}

				 	//document.all["state_mx_"+devno+"_"+recordArray[0]].level=tmpstatelevel;
				 	
					if( tmpstatelevel=="0" ){
						MM_setTextOfLayer("devmstate_"+devno+"_"+recordArray[0],'',DealUndefined(tmpstatename));
					}else if( tmpstatelevel=="1" ){
						MM_setTextOfLayer("devmstate_"+devno+"_"+recordArray[0],'',"<font color=#CBC90E>"+DealUndefined(tmpstatename)+"</font>");
					}else if( tmpstatelevel=="2" ){
						MM_setTextOfLayer("devmstate_"+devno+"_"+recordArray[0],'',"<font color=red>"+DealUndefined(tmpstatename)+"</font>");
					}else{
						MM_setTextOfLayer("devmstate_"+devno+"_"+recordArray[0],'',"<font color=#999999>"+DealUndefined(tmpstatename)+"</font>");
					}
				 	
				 	
				 	var startDateTime = "";
				 	if( recordArray[2].length==8 )	startDateTime = recordArray[2].substring(0,4)+"-"+recordArray[2].substring(4,6)+"-"+recordArray[2].substring(6);
				 	if( recordArray[3].length==6 )	startDateTime+= " "+recordArray[3].substring(0,2)+":"+recordArray[3].substring(2,4)+":"+recordArray[3].substring(4);
				 	MM_setTextOfLayer("devstart_"+devno+"_"+recordArray[0],'',DealUndefined(startDateTime));
				 	
				 	var endDateTime = "";
				 	var endDateTimevalue = recordArray[4]+recordArray[5];
				 	
					if( recordArray[4].length==8&&recordArray[5].length==6 ){
						endDateTime = recordArray[4].substring(0,4)+"-"+recordArray[4].substring(4,6)+"-"+recordArray[4].substring(6);
				 		endDateTime+= " "+recordArray[5].substring(0,2)+":"+recordArray[5].substring(2,4)+":"+recordArray[5].substring(4);
				 	}				 	
				 	
				 	if(endDateTime>myTTlastTime) myTTlastTime = endDateTime;
				 	MM_setTextOfLayer("devlast_"+devno+"_"+recordArray[0],'',DealUndefined(endDateTime));
				}
		 		}
			 	var myTTstatestr = "";
			 	if( myTTstate=="0" ){
					myTTstatestr = "正  常";
					MM_setTextOfLayer(dev_tr+"_devnovalue",'',devno);
				}else if( myTTstate=="1" ){
					myTTstatestr = "<b><font color=#F5F328>警  告</font></b>";
					MM_setTextOfLayer(dev_tr+"_devnovalue",'',"<b><font color=#F5F328>"+devno+"</font></b>");
				}else if( myTTstate=="2" ){
					myTTstatestr = "<b><font color=red>故  障</font></b>";
					MM_setTextOfLayer(dev_tr+"_devnovalue",'',"<b><font color=red>"+devno+"</font></b>");
				}else{
					myTTstatestr = "<font color=#999999>未  知</font>";
					MM_setTextOfLayer(dev_tr+"_devnovalue",'',"<b><font color=#999999>"+devno+"</font></b>");
				}
				document.all[dev_tr+"_show"].level=myTTstate;
				parent.devmap_moni_main.eval("dev_ico_"+devno).src="images/default/devico_"+myTTstate+".gif";

				changeCryLevel(crylevel);
				
				
			 	MM_setTextOfLayer(dev_tr+"_devnostvalue",'',DealUndefined(myTTstatestr));

			 	MM_setTextOfLayer(dev_tr+"_devlastvalue",'',DealUndefined(myTTlastTime));
			}
		}
	}catch(e){}
}
// -->
</script>
</head>
<html:base />
<body leftmargin="0" topmargin="0" bgcolor="#9799FF">
	<table id="inittable" width="100%" height="100%" cellspacing="1"
		cellpadding="2" class="devmoni_ctrl">
		<tr>
			<td>
				请在地图上选取设备或机构，查看详细信息
			</td>
		</tr>
	</table>

	<%
			DevMapModel mymapModel = new DevMapModel(bankid);
			DevMap mymap = mymapModel.getDevMap();
			int devnum = mymap.getDevnum();
			int banknum = mymap.getBanknum();
			DevPosition[] devs = mymap.getDevs();
			DevinfoModel myDevinfoModel = new DevinfoModel();
			BankPosition[] banks = mymap.getBanks();
			BankinfoModel myBankinfoModel = new BankinfoModel();
			CharSet myCharSet = new CharSet();
	%>


	<table id="showlist" width="100%" cellspacing="1" cellpadding="2"
		style="display:none">
		<%
					for (int i = 0; i < devnum; i++) {
					String thisdevno = devs[i].getDevno();
					Devinfo thisdev = myDevinfoModel.getDevFromList(thisdevno);
					if (thisdev != null) {
		%>
		<!--设备显示-->
		<tr id="dev_<%=thisdevno%>_show">
			<td>
				<table width="100%" cellspacing="1" cellpadding="2">
					<tr>
						<td>
							<table width="100%" cellspacing="1" cellpadding="3"
								class="list_table_border">
								<tr>
									<td class="list_td_title" width="60">
										<center>
											<b>设备编号</b>
										</center>
									</td>
									<td width="100" class="list_td_title"
										id="dev_<%=thisdevno%>_devnovalue">
										<%=myPubUtil.dealNull(thisdevno)%>
									</td>
									<td class="list_td_title" width="60">
										<center>
											<b>设备类型</b>
										</center>
									</td>
									<td width="80" class="list_td_title"
										id="dev_<%=thisdevno%>_devnovalue">
										<%=myPubUtil.dealNull(thisdev.getTypeno()).trim()%>
									</td>
									<td class="list_td_title" width="50">
										<center>
											<b>负责人</b>
										</center>
									</td>
									<td width="60" class="list_td_title">
										<%=(myCharSet.db2web(myPubUtil.dealNull(thisdev.getDutyname()))).trim()%>
									</td>
									<td class="list_td_title" width="60">
										<center>
											<b>联系电话</b>
										</center>
									</td>
									<td width="90" class="list_td_title">
										<%=(myCharSet.db2web(myPubUtil.dealNull(thisdev.getOrgancontact()))).trim()%>
									</td>
									<td class="list_td_title" width="60">
										<center>
											<b>安装地址</b>
										</center>
									</td>
									<td class="list_td_title">
										<%=(myCharSet.db2web(myPubUtil.dealNull(thisdev.getDevaddr()))).trim()%>
									</td>

								</tr>
							</table>
						</td>
					</tr>
					<tr id="dev_<%=thisdevno%>_tmp">
						<td>
							<table width="100%" id="tt_table_<%=thisdevno%>" border="0"
								class="list_table_border" cellspacing="1" cellpadding="3">
								<tr id="dev_<%=thisdevno%>">
									<td class="list_td_title" width="60">
										<center>
											<b>IP地址</b>
										</center>
									</td>
									<td width="100" class="list_td_title">
										<%=(myPubUtil.dealNull(thisdev.getDevip())).trim()%>
									</td>
									<td id="dev_<%=thisdevno%>_devnostate" class="list_td_title"
										width="60">
										<center>
											<b>总状态</b>
										</center>
									</td>
									<td id="dev_<%=thisdevno%>_devnostvalue" class="list_td_title"
										width="80">
										等待...
									</td>
									<td id="dev_<%=thisdevno%>_devstlasttime" class="list_td_title"
										width="117">
										<center>
											<b>最后检测时间</b>
										</center>
									</td>
									<td width="157" id="dev_<%=thisdevno%>_devlastvalue"
										class="list_td_title">
										&nbsp;
									</td>
									<td id="dev_<%=thisdevno%>_cheknow" class="list_td_title"
										align="center">
										<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
											codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0"
											width="102" height="24" id="DevCheck" align="middle">
											<param name="allowScriptAccess" value="sameDomain" />
											<param name="movie"
												value="swf/DevCheck.swf?devno=<%=thisdevno%>" />
											<param name="quality" value="high" />
											<param name="menu" value="false" />
											<param name="wmode" value="transparent" />
											<embed src="swf/DevCheck.swf" quality="high"
												bgcolor="#ffffff" width="102" height="24" name="DevCheck"
												align="middle" allowScriptAccess="sameDomain"
												type="application/x-shockwave-flash"
												pluginspage="http://www.macromedia.com/go/getflashplayer" />
										</object>
									</td>
									<td id="dev_<%=thisdevno%>_more" width="50"
										class="list_td_title">
										<center>
											<img id="bt_dev_<%=thisdevno%>" src="images/default/more.gif"
												style="cursor:hand"
												onClick="javascript:extendTable('bt_dev_<%=thisdevno%>','dev_<%=thisdevno%>_statelist')">
											<center>
									</td>
								</tr>
								<tr id="dev_<%=thisdevno%>_statelist" style="display:none">
									<td colspan="8">
										<table width="100%" id="devstl_<%=thisdevno%>" border="0"
											class="list_table_border" cellspacing="1" cellpadding="3">
											<tr id="state_title_<%=thisdevno%>" class="list_tr2">
												<td id="obj_<%=thisdevno%>">
													子设备
												</td>
												<td id="mstate_<%=thisdevno%>">
													状态
												</td>
												<td id="start_<%=thisdevno%>">
													开始时间
												</td>
												<td id="last_<%=thisdevno%>">
													最后检测时间
												</td>
											</tr>

										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>

		<%
				}
				}
		%>
	</table>


	<!--机构显示-->
	<table id="banklist" width="100%" Style="display:none">
		<%
					for (int i = 0; i < banknum; i++) {
					String thisbankid = banks[i].getBankid();
					Bankinfo thisbank = myBankinfoModel.getBankinfoFromList(thisbankid);
					if (thisbank != null) {
				String thisbanktag = thisbank.getBanktag().trim();
		%>
		<tr id="bank_<%=thisbankid%>_show">
			<td>
				<table width="100%">
					<tr>
						<td>
							<table border="0" width="100%" cellspacing="1" cellpadding="3"
								class="list_table_border">
								<tr>
									<td class="list_td_title" width="100">
										<center>
											<b>机构名称</b>
										</center>
									</td>
									<td class="list_td_title" width="150">
										<%=(myCharSet.db2web(myPubUtil.dealNull(thisbank.getBanknm()))).trim()%>
									</td>
									<td class="list_td_title" width="100">
										<center>
											<b>机构级别</b>
										</center>
									</td>
									<td class="list_td_title">
										<%=thisbanktag%>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" width="100%" cellspacing="1" cellpadding="3"
								class="list_table_border">
								<tr>
									<td class="list_td_title" width="100">
										<center>
											<b>地址</b>
										</center>
									</td>
									<td class="list_td_title" width="150">
										<%=(myCharSet.db2web(myPubUtil.dealNull(thisbank.getBankaddr()))).trim()%>
									</td>
									<td class="list_td_title" width="100">
										<center>
											<b>联系电话</b>
										</center>
									</td>
									<td class="list_td_title" width="120">
										<%=(myCharSet.db2web(myPubUtil.dealNull(thisbank.getBanktel()))).trim()%>
									</td>
									<td class="list_td_title">
										<center>
											<a href="DevMapMoni_Fram.jsp?bankid=<%=thisbankid%>"
												target="_parent">进入该机构地图监控</a>
										</center>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<%
				}
				}
		%>

	</table>

</body>
</html:html>
<%
}
%>
