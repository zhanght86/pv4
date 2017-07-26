<%@include file="inc/taglib.jsp"%>
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>
<%
	PubUtil myPubUtil = new PubUtil();
	String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
	String bankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request));
	String opertype = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERTYPE, request));
	String loginnum = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERLOGINNUM, request));
	String lastlogin = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERLASTLOGIN, request));

	OperatorModel myOperatorModel = new OperatorModel();
	CharSet myCharSet = new CharSet();
	Operator myOperator = myOperatorModel.getOperator(operid);
	String opername = myCharSet.db2web(myOperator.getName()).trim();

	//权限编码
	String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();

	//机构名称
	String bankname = "";
	if (!authlist.equals("*")) {
		BankinfoModel myBankinfoModel = new BankinfoModel();

		Bankinfo mybank = BankinfoModel.getBankinfoFromList(bankid);
		if (mybank != null)
			bankname = myCharSet.db2web(myPubUtil.dealNull(mybank.getBanknm()).trim());
	}
%>
<html:html locale="true">
<head>
<title>LeftMenu</title>

<script language="JavaScript">

function MFXinitMenu(){
//IE = document.all ? 1:0;
IE = 1;
NN = document.layers ? 1:0;
HIDDEN = (NN) ? 'hide' : 'hidden';
VISIBLE = (NN) ? 'show' : 'visible';
myLayer=new Array();
mySpeed=5;
subLeft=0;
closes=true;
myLayer[0]=(NN) ? document.MFX0 : document.all.MFX0.style;		// 第一组菜单
myLayer[1]=(NN) ? document.MFX1 : document.all.MFX1.style;		// 第一组内容
myLayer[2]=(NN) ? document.MFX2 : document.all.MFX2.style;		// 第二组
myLayer[3]=(NN) ? document.MFX3 : document.all.MFX3.style;
myLayer[4]=(NN) ? document.MFX4 : document.all.MFX4.style;		// 第三组
myLayer[5]=(NN) ? document.MFX5 : document.all.MFX5.style;
myLayer[6]=(NN) ? document.MFX6 : document.all.MFX6.style;		// 第四组
myLayer[7]=(NN) ? document.MFX7 : document.all.MFX7.style;
myLayer[8]=(NN) ? document.MFX8 : document.all.MFX8.style;　　　// 第五组
myLayer[9]=(NN) ? document.MFX9 : document.all.MFX9.style;
myLayer[10]=(NN) ? document.MFX10 : document.all.MFX10.style;　　　// 第六组
myLayer[11]=(NN) ? document.MFX11 : document.all.MFX11.style;
myLayer[12]=(NN) ? document.MFX12 : document.all.MFX12.style;　　　// 第七组
myLayer[13]=(NN) ? document.MFX13 : document.all.MFX13.style;
myLayer[14]=(NN) ? document.MFX14 : document.all.MFX14.style;　　　// 第八组
myLayer[15]=(NN) ? document.MFX15 : document.all.MFX15.style;
running=false;
whichOpen=-1;
lastMain=myLayer.length-2;
MFXmain=new Array();
for(i=0; i<myLayer.length; i++){
mainORsub= i % 2;
MFXmain[i] = mainORsub ? 0:1;
}
myTop=new Array();
myLeft=new Array();
myHeight=new Array();
myWidth=new Array();
mySlide=new Array();
for(i=0; i<myLayer.length; i++){
if(NN&&MFXmain[i]){
if(i==0){
myTop[i]=myLayer[i].top;
myLeft[i]=myLayer[i].left;}
else{
myLeft[i]=myLeft[i-2];
myTop[i]=myTop[i-2]+myHeight[i-2];}
myHeight[i]=myLayer[i].clip.height;
myWidth[i]=myLayer[i].clip.width;
myLayer[i].left=myLeft[i];
myLayer[i].top=myTop[i];
myLayer[i].visibility=VISIBLE;}
if(NN&&!MFXmain[i]){
myTop[i]=myTop[i-1]+myHeight[i-1];
myLeft[i]=myLeft[i-1];
myHeight[i]=myLayer[i].clip.height;
myWidth[i]=myLayer[i].clip.width;
mySlide[i]=myTop[i]+myHeight[i];
myLayer[i].left=myLeft[i]+subLeft;
myLayer[i].top=myTop[i];}
if(IE&&MFXmain[i]){
if(i==0){
myLeft[i]=myLayer[i].pixelLeft;
myTop[i]=myLayer[i].pixelTop;}
else{
myLeft[i]=myLeft[i-2];
myTop[i]=myTop[i-2]+myHeight[i-2];}
myHeight[i]=myLayer[i].pixelHeight;
myWidth[i]=myLayer[i].pixelWidth;
myLayer[i].left=myLeft[i];
myLayer[i].top=myTop[i];
myLayer[i].visibility=VISIBLE;}
if(IE&&!MFXmain[i]){
myTop[i]=myTop[i-1]+myHeight[i-1];
myLeft[i]=myLeft[i-1];
myHeight[i]=myLayer[i].pixelHeight;
myWidth[i]=myLayer[i].pixelWidth;
myLayer[i].pixelLeft=myLeft[i]+subLeft;
myLayer[i].pixelTop=myTop[i];
mySlide[i]=myTop[i]+myHeight[i];
}
}
}




function MFXrunMenu(myName,newspeed){
ieStep=0;
thereS=false;
thereC=false;
if(newspeed>0){mySpeed=newspeed;}
first=myName;
if(whichOpen==-1&&!running&&MFXmain[myName]&&!(whichOpen==myName)){
running=true;
if(NN){
myLayer[myName+1].clip.height=0;
myLayer[myName+1].visibility=VISIBLE;
}
if(IE){
myLayer[myName+1].clip= "rect(" + ("auto") +" "+ ("auto") +" "+ (0) +" "+ ("auto") +")";
myLayer[myName+1].visibility=VISIBLE;
}
MFXopenMenuS(myName);
MFXopenMenuC(myName);
}
if(whichOpen>=0&&!running&&!(whichOpen==myName)){
running=true;
second=whichOpen;
ieStep1=myHeight[second+1];
thereCS=false;
thereCC=false;
MFXcloseMenuS(second);
MFXcloseMenuC(second);
}
if(whichOpen>=0&&!running&&whichOpen==myName&&closes){
running=true;
second=whichOpen;
ieStep1=myHeight[second+1];
thereCS=false;
thereCC=false;
MFXcloseMenuS(second);
MFXcloseMenuC(second);
	}
}





function MFXstopCloseS(myName){
running=false;
thereCS=true;
if(closes&&first==whichOpen){whichOpen=-1;}
else{whichOpen=-1;
MFXrunMenu(first);
}
}




function MFXstopOpenS(myName){
running=false;
thereS=true;
if(IE){myLayer[myName+1].clip= "rect(" + ("auto") +" "+ ("auto") +" "+ ("auto") +" "+ ("auto") +")";}
whichOpen=myName;
}




function MFXopenMenuS(myName){
myStep=mySpeed;
if(NN&&!thereS&&!(first==lastMain)){
if(myLayer[first+2].top+myStep>mySlide[first+1]){
myStep=mySlide[first+1]-myLayer[first+2].top;
}
for(i=first+2; i<myLayer.length; i+=2){
myLayer[i].top+=myStep;
}
if(myLayer[first+2].top==mySlide[first+1]){
MFXstopOpenS(first)
}
if(running)setTimeout('MFXopenMenuS(first)',10);
}
if(IE&&!thereS&&!(first==lastMain)){
if(myLayer[first+2].pixelTop+myStep>mySlide[first+1]){
myStep=mySlide[first+1]-myLayer[first+2].pixelTop;
}
for(i=first+2; i<myLayer.length; i+=2){
myLayer[i].pixelTop+=myStep;
}
if(myLayer[first+2].pixelTop==mySlide[first+1]){
MFXstopOpenS(first)
}
if(running)setTimeout('MFXopenMenuS(first)',10);
}
}




function MFXopenMenuC(myName){
myStep=mySpeed;
if(NN&&!thereC){
if ((myLayer[first+1].clip.height+myStep)>myHeight[first+1]){
myLayer[first+1].clip.height=myHeight[first+1]
}
if(myLayer[first+1].clip.height==myHeight[first+1]){
thereC=true;
whichOpen=first;
MFXstopOpenS(first)

}else{
myLayer[first+1].clip.height+=myStep;

}
if(running)setTimeout('MFXopenMenuC(first)',10);
}
if(IE&&!thereC){
ieStep+=myStep;
myLayer[myName+1].clip= "rect(" + ("auto") +" "+ ("auto") +" "+ (ieStep) +" "+ ("auto") +")";

if(ieStep>=myHeight[first+1]){
thereC=true;
whichOpen=first;
MFXstopOpenS(first)
}
if(running)setTimeout('MFXopenMenuC(first)',10);
}
}





function MFXcloseMenuS(myName){
myStep=mySpeed;
if(NN&&!thereCS&&!(second==lastMain)){
if(myLayer[second+2].top-myStep<myTop[second+2]){
myStep=myLayer[second+2].top-myTop[second+2];
}
for(i=second+2; i<myLayer.length; i+=2){
myLayer[i].top-=myStep;

}
if(myLayer[second+2].top==myTop[second+2]){
MFXstopCloseS(second);
}
if(running)setTimeout('MFXcloseMenuS(second)',10);
}
if(IE&&!thereCS&&!(second==lastMain)){
if(myLayer[second+2].pixelTop-myStep<myTop[second+2]){
myStep=myLayer[second+2].pixelTop-myTop[second+2];
}
for(i=second+2; i<myLayer.length; i+=2){
myLayer[i].pixelTop-=myStep;

}
if(myLayer[second+2].pixelTop==myTop[second+2]){
MFXstopCloseS(second);
}
if(running)setTimeout('MFXcloseMenuS(second)',10);
}
}





function MFXcloseMenuC(myName){
myStep=-mySpeed;
ieStep1-=mySpeed;
if(NN&&!thereCC){
if ((myLayer[second+1].clip.bottom+myStep)<0){
myLayer[second+1].clip.bottom=0;
}
if(myLayer[second+1].clip.bottom==0){
thereCC=true;

if(second==lastMain)MFXstopCloseS(second);
}else{
myLayer[second+1].clip.bottom+=myStep;

}
if(running)setTimeout('MFXcloseMenuC(second)',10);
}
if(IE&&!thereCC){
if(ieStep1<=0){
myLayer[myName+1].clip= "rect(" + ("auto") +" "+ ("auto") +" "+ (0) +" "+ ("auto") +")";
thereCC=true;
if(second==lastMain)MFXstopCloseS(second);
}else{
myLayer[myName+1].clip= "rect(" + ("auto") +" "+ ("auto") +" "+ (ieStep1) +" "+ ("auto") +")";

}
if(running)setTimeout('MFXcloseMenuC(second)',10);
}
}


//处理异常信息
	function displayErrorMsg(e) {
		tmp = "";
		for (x in e) {
			tmp += "  " + x + "=>" + e[x] + "  ";
		}

		tiShi(tmp, 1);
	}


function MM_findObj(n, d) { //v4.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
</script>
<link href="style/pvxp.css" rel="stylesheet" type="text/css">

</head>
<body onLoad="MFXinitMenu();MFXrunMenu(0,100)"
	onBeforeUnload="javascript:parent.logOutByBrowser()" leftmargin="0"
	topmargin="0">
	<div id="Layer1"
		style="position:absolute; width:100%; height:100%; z-index:1; left: -18; top: -48;">

		<DIV id="MFX0"
			style="position:absolute; width:150px; height:25px; z-index:2; left:20px; top: 50px; visibility: hidden">
			<table width="156" border="0" cellspacing="0" cellpadding="0">
				<tr onClick="MFXrunMenu(0,15)" style="CURSOR: hand">
					<td background="images/default/left_background_01.gif" height="25"
						align="right">个人信息</td>
					<td background="images/default/left_background_02.gif" width="21"
						height="25"></td>
					<td width="25"><img src="images/default/left_arrow_down.gif"
						width="25" height="25" border="0"></td>
				</tr>
			</table>
		</DIV>
		<div id="MFX1"
			style="position:absolute; width:158; height:130px; z-index:1; left:20px; top: 75px; visibility: hidden">
			<table width="156" border="0" cellspacing="0" cellpadding="0"
				align="center">
				<tr>
					<td width="1" bgcolor="FFFFFF"></td>
					<td width="154" bgcolor="D6DFF7" height="150" align="left"
						valign="top"><font color=red>&nbsp;<%=opername%> </font>( <%=operid%>
						) <br> <%
 	if (!authlist.equals("*")) {
 %> &nbsp; <font color=blue><%=bankname%> </font> <br> <%
 	} else {
 %> &nbsp; <font color=blue>系统管理员</font> <br> <%
 	}
 %> &nbsp;这是您第 <font color=red><%=loginnum%> </font>次登录 <br> <%
 	if (!loginnum.equals("1")) {
 %> &nbsp;您上次登录时间是 <br> &nbsp; <font color=blue><%=lastlogin%>
					</font> <%
 	}
 %> <br> &nbsp;&nbsp; <a href="#"
						onClick="javascript:parent.goUrl('ChangePwd.jsp');">[修改密码]</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="#" onClick="javascript:parent.logOut();">[退出系统]</a></td>
					<td width="1" bgcolor="FFFFFF"></td>
				</tr>
				<tr bgcolor="FFFFFF">
					<td colspan="3" height="1"></td>
				</tr>
			</table>
		</div>
		<div id="MFX2"
			style="position:absolute; width:150px; height:38px; z-index:2; left:20px; top: 201px; visibility: hidden">
			<table width="156" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="13" colspan="3"></td>
				</tr>
				<tr onClick="MFXrunMenu(2,15)" style="CURSOR: hand">
					<td background="images/default/left_background_01.gif" height="25"
						align="right">基本管理</td>
					<td background="images/default/left_background_02.gif" width="21"
						height="25"></td>
					<td width="25"><img src="images/default/left_arrow_down.gif"
						width="25" height="25" border="0"></td>
				</tr>
			</table>
		</div>
		<DIV id="MFX3"
			style="position:absolute; width:170px; height:63px; z-index:1; left:20px; top: 226px; visibility: hidden">
			<table width="170" border="0" cellspacing="0" cellpadding="0"
				align="center">
				<tr>
					<td width="1" bgcolor="FFFFFF"></td>
					<td width="170" bgcolor="D6DFF7" height="80" align="left"
						valign="top">
						<table width="100%">
							<tr>
								<td height="2" colspan="2"></td>
							</tr>
							<tr>
								<td align="left" valign="center"><img
									src="images/default/left_func.gif"> &nbsp; <a href="#"
									onClick="javascript:parent.getBankinfoList('1')">机构信息</a></td>
								<td align="left" valign="center"><img
									src="images/default/left_func.gif"> &nbsp; <a href="#"
									onClick="javascript:parent.getDevList(1)">设备信息</a></td>
							</tr>

							<tr>
								<td height="2" colspan="2"></td>
							</tr>
							<tr>
								<td align="left" valign="center"><img
									src="images/default/left_func.gif"> &nbsp; <a href="#"
									onClick="javascript:parent.getOperList('1')">操作员管理</a></td>

								<td align="left" valign="center"><img
									src="images/default/left_func.gif"> &nbsp; <a href="#"
									onClick="javascript:parent.goUrl('Sys_Manage_RemoteControl.jsp');">远程管理</a>
								</td>

							</tr>

							<!--  wukp 20110923
<DIV id="MFX3" style="position:absolute; width:158px; height:63px; z-index:1; left:20px; top: 226px; visibility: hidden">
	<table width="156" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td width="1" bgcolor="FFFFFF"></td>
			<td width="154" bgcolor="D6DFF7" height="80" align="left" valign="top">
				<table width="100%">
					<tr>
						<td height="2" colspan="2"></td></tr>
					<tr>
						<td align="left" valign="center">
							<img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.getBankinfoList('1')">机构信息</a>
						</td>
						<td align="left" valign="center">
							<img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.getDevList(1)">设备信息</a>
						</td>
					</tr>
					<tr><td height="2" colspan="2"></td></tr>
					<tr>
						<td align="left" valign="center">
							<img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.getOperList('1')">操作员管理</a>
						</td>
			 	
						<td align="left" valign="center">
							<img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('Sys_Manage_RemoteControl.jsp');">远程管理</a>
						</td>
					
					</tr>
					
					<!--  wukp 20110923
					<tr><td height="2" colspan="2"></td></tr>
					<tr>
						<td align="left" valign="center">
							<img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('Sys_Manage_Remote.jsp');">远程设置</a>
						</td>
					</tr>
					wukp 20110923-->

							<!--<tr><td height="2" colspan="2"></td></tr>
					<tr>
						<td align="left" valign="center">
							<img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('iniFL.jsp');">转账费率</a>
						</td>
						<td align="left" valign="center">
							<img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('iniArea.jsp');">地区管理</a>
						</td>
					</tr>
					<tr><td height="2" colspan="2"></td></tr>
					<tr>
						<td align="left" valign="center">
							<img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('iniCardType.jsp');">卡类型管理</a>
						</td>
						<td align="left" valign="center">
							<img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('iniAccount.jsp');">账户管理</a>
						</td>
					</tr>
					<tr><td height="2" colspan="2"></td></tr>
					<tr>
						<td align="left" valign="center" colspan="2">
							<img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('iniOpenFlag.jsp');">手续费启用管理</a>
						</td>
					</tr>
					<tr><td height="2" colspan="2"></td></tr>
					<tr>
						<td align="left" valign="center" colspan="2">
							<img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('iniSpecialCard.jsp');">特殊卡管理</a>
						</td>
					</tr>-->

							<tr>
								<td height="2" colspan="2"></td>
							</tr>
						</table></td>
					<td width="1" bgcolor="FFFFFF"></td>
				</tr>
				<tr bgcolor="FFFFFF">
					<td colspan="3" height="1"></td>
				</tr>
			</table>
		</DIV>



		<DIV id="MFX4"
			style="position:absolute; width:150px; height:38px; z-index:2; left:20px; top: 302px; visibility: hidden">

			<table width="156" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="13" colspan="3"></td>
				</tr>


				  <tr onClick="javascript:parent.goUrl('TradeMoni.jsp')" style="CURSOR: hand">
			<!--	<tr onClick="javascript:parent.goUrl('TradeWSMoni.jsp')"-->
					
					<td background="images/default/left_background_01.gif" height="25"
						align="right">交易监控</td>
					<td background="images/default/left_background_02.gif" width="21"
						height="25"></td>
					<td width="25"><img src="images/default/left_arrow_down.gif"
						width="25" height="25" border="0">
					</td>
				</tr>
			</table>

		</DIV>


		<DIV id="MFX5"></DIV>

		<DIV id="MFX6"
			style="position:absolute; width:150px; height:38px; z-index:2; left:20px; top: 463px; visibility: hidden">
			<!--wukp 20110923  
      <DIV id="MFX6" style="position:absolute; width:150px; height:0px; z-index:2; left:20px; top: 463px; visibility: hidden">
        wukp 20110923  -->

			<table width="156" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="13" colspan="3"></td>
				</tr>

				 <tr onClick="javascript:parent.goUrl('DevListMoni.jsp')" style="CURSOR: hand">
			
				<!-- <tr onClick="javascript:parent.getDevWSMoniList('<%=operid%>')"
						-->
					<td background="images/default/left_background_01.gif" height="25"
						align="right">设备监控</td>
					<td background="images/default/left_background_02.gif" width="21"
						height="25"></td>
					<td width="25"><img src="images/default/left_arrow_down.gif"
						width="25" height="25" border="0"></td>
				</tr>
			</table>
		</DIV>
		<DIV id="MFX7"
			style="position:absolute; width:158px; height:30px; z-index:1; left:20px; top: 488px; visibility: hidden">
			<!-- <table width="156" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
      <td width="1" bgcolor="FFFFFF"></td>
      <td width="154" bgcolor="D6DFF7" height="60" align="left" valign="top">
              <table width="100%">
              <tr><td height="10" colspan="2"></td></tr>
              <tr>
              <td height="5">
              <img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('DevListMoni.jsp')">列表监控</a>
              </td>
              <td height="5">
              <img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.getDevMapList(1)">地图监控</a>
              </td>
              </tr>
          		<tr><td height="5" colspan="2"></td></tr>
              </table>
              </td>
      <td width="1" bgcolor="FFFFFF"></td>
    </tr>
    <tr bgcolor="FFFFFF">
      <td colspan="3" height="1"></td>
    </tr>
  </table>-->
		</DIV>



		<DIV id="MFX8"
			style="position:absolute; width:150px; height:38px; z-index:2; left:20px; top: 624px; visibility: hidden">
			<table width="156" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="13" colspan="3"></td>
				</tr>
				<tr onClick="MFXrunMenu(8,15)" style="CURSOR: hand">
					<td background="images/default/left_background_01.gif" height="25"
						align="right">查询统计</td>
					<td background="images/default/left_background_02.gif" width="21"
						height="25"></td>
					<td width="25"><img src="images/default/left_arrow_down.gif"
						width="25" height="25" border="0"></td>
				</tr>
			</table>
		</DIV>
		<!-- width:158px; height:380px; z-index:1; left:20px; top: 649px; -->
		<DIV id="MFX9"
			style="position:absolute; width:158px; height:270px; z-index:1; left:20px; top: 649px; visibility: hidden">
			<table width="156" border="0" cellspacing="0" cellpadding="0"
				align="center">
				<tr bgcolor="D6DFF7">
					<td colspan="3" height="3"></td>
				</tr>
				<tr>
					<td width="1" bgcolor="FFFFFF"></td>
					<td width="154" bgcolor="D6DFF7" height="250" align="left"
						valign="top">

						<table width="100%" border="0" cellspacing="0" cellpadding="5"
							align="center">

							<tr>

								<td align="left" valign="center" bgcolor="D6DFF7" colspan="2">
									<img src="images/default/left_func.gif"> <a href="#"
									onClick="javascript:parent.goUrl('TradeQuery.jsp')">交易查询</a></td>
							</tr>
							<tr>
								<td align="left" valign="center" bgcolor="D6DFF7" colspan="2">
									<img src="images/default/left_func.gif"> <a href="#"
									onClick="javascript:parent.goUrl('OplogQuery.jsp')">操作记录查询</a>
								</td>
							</tr>
							
						<tr>
							<td align="left" valign="center" bgcolor="D6DFF7" colspan="2"><img 

src="images/default/left_func.gif"> <a href="#" onClick="javascript:parent.goUrl('ReportFormList.do?statesort=01')">交易统计

</a></td>

                        </tr>



							 
						<tr>
							<td align="left" valign="center" bgcolor="D6DFF7" colspan="2"><img 

src="images/default/left_func.gif"> <a href="#" onClick="javascript:parent.goUrl('ReportFormList.do?statesort=06')">机构交易

统计</a></td>
						</tr> 
						<tr>
							<td align="left" valign="center" bgcolor="D6DFF7" colspan="2"><img src="images/default/left_func.gif"> <a href="#" onClick="javascript:parent.goUrl('ReportFormList.do?statesort=10')">设备开机率统计</a></td>
						</tr>
						<tr>
							<td align="left" valign="center" bgcolor="D6DFF7" colspan="2"><img src="images/default/left_func.gif"> <a href="#" onClick="javascript:parent.goUrl('ReportFormList.do?statesort=25')">机构开机率统计</a></td>
						</tr>
						<tr>
							<td align="left" valign="center" bgcolor="D6DFF7" colspan="2"><img src="images/default/left_func.gif"> <a href="#" onClick="javascript:parent.goUrl('ReportFormList.do?statesort=03')">设备故障统计</a></td>
						</tr>
						<tr>
							<td align="left" valign="center" bgcolor="D6DFF7" colspan="2"><img src="images/default/left_func.gif"> <a href="#" onClick="javascript:parent.goUrl('ReportFormList.do?statesort=26')">厂商故障统计</a></td>
						</tr>
							<!-- 
						<tr>
							<td align="left" valign="center" bgcolor="D6DFF7" colspan="2"><img 

src="images/default/left_func.gif"> <a href="#" onClick="javascript:parent.goUrl('ReportFormList.do?statesort=11')">设备类型

交易统计</a></td>
						</tr> -->


							
						<tr>
							<td align="left" valign="center" bgcolor="D6DFF7" colspan="2"><img 

src="images/default/left_func.gif"> <a href="#" onClick="javascript:parent.goUrl('ReportFormList.do?statesort=15')">交易明细

表</a></td>
						</tr>
						<!--  
							<tr>
								<td align="left" valign="center" bgcolor="D6DFF7" colspan="2">
									<img src="images/default/left_func.gif"> <a href="#"
									onClick="javascript:parent.goUrl('ReportFormList.do?statesort=18')">操作记录

										统计</a></td>
							</tr>
-->

						<!--  	<tr>
								<td align="left" valign="center" bgcolor="D6DFF7" colspan="2">
									<img src="images/default/left_func.gif"> <a href="#"
									onClick="javascript:parent.goUrl('CardDistill.jsp')">卡凭证领用记录查询</a>
								</td>
							</tr>

							<tr>
								<td align="left" valign="center" bgcolor="D6DFF7" colspan="2">
									<img src="images/default/left_func.gif"> <a href="#"
									onClick="javascript:parent.goUrl('CardOut.jsp')">发卡记录流水查询</a></td>
							</tr>
							<tr>
								<td align="left" valign="center" bgcolor="D6DFF7" colspan="2">
									<img src="images/default/left_func.gif"> <a href="#"
									onClick="javascript:parent.goUrl('CardStatus.jsp')">卡凭证状态查询</a>
								</td>
							</tr>
							<tr>
								<td align="left" valign="center" bgcolor="D6DFF7" colspan="2">
									<img src="images/default/left_func.gif"> <a href="#"
									onClick="javascript:parent.goUrl('ReportFormList.do?statesort=21')">出卡状态记录统计</a>
								</td>
							</tr>

							<tr>
								<td align="left" valign="center" bgcolor="D6DFF7" colspan="2">
									<img src="images/default/left_func.gif"> <a href="#"
									onClick="javascript:parent.goUrl('CashStock.jsp')">钞箱记录查询</a></td>
							</tr>
							<tr>
								<td align="left" valign="center" bgcolor="D6DFF7" colspan="2">
									<img src="images/default/left_func.gif"> <a href="#"
									onClick="javascript:parent.goUrl('page/cash/CashCheck.jsp')">现金交易明细查询</a>
								</td>
							</tr>
							<tr>
								<td align="left" valign="center" bgcolor="D6DFF7" colspan="2">
									<img src="images/default/left_func.gif"> <a href="#"
									onClick="javascript:parent.goUrl('ReportFormList.do?statesort=22')">现金交易明细统计</a>
								</td>
							</tr>
						
							<tr>
								<td height="3" colspan="2"></td>

							</tr>-->




							<!-- WUKP20110930 为发卡管理而屏蔽
						 <tr>
							<td align="left" valign="center" bgcolor="D6DFF7" colspan="2"><img src="images/default/left_func.gif"> <a href="#" onClick="javascript:parent.goUrl('InvoiceDistill.jsp')">发票领用查询</a></td>
						</tr>
						<tr>
							<td align="left" valign="center" bgcolor="D6DFF7" colspan="2"><img src="images/default/left_func.gif"> <a href="#" onClick="javascript:parent.goUrl('InvoiceState.jsp')">发票状态查询</a></td>
						</tr>
						<tr>
							<td align="left" valign="center" bgcolor="D6DFF7" colspan="2"><img src="images/default/left_func.gif"> <a href="#" onClick="javascript:parent.goUrl('InvoiceMxb.jsp')">发票明细查询</a></td>
						</tr>
						<tr>
							<td align="left" valign="center" bgcolor="D6DFF7" colspan="2"><img src="images/default/left_func.gif"> <a href="#" onClick="javascript:parent.goUrl('ReportFormList.do?statesort=04')">发票打印统计</a></td>
						</tr>
					
						 <tr><td height="3" colspan="2"></td></tr>
						 
						 -->
						</table></td>
					<td width="1" bgcolor="FFFFFF"></td>
				</tr>
				<tr bgcolor="FFFFFF">
					<td colspan="3" height="1"></td>
				</tr>
			</table>
		</DIV>



		<!--wukp 20110923  
<DIV id="MFX10" style="position:absolute; width:150px; height:38px; z-index:2; left:20px; top: 785px; visibility: hidden;">
wukp 20110923  -->
		<DIV id="MFX10"
			style="position:absolute; width:150px; height:0px; z-index:2; left:20px; top: 785px; visibility: hidden;">


			<table width="156" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="13" colspan="3"></td>
				</tr>
				<tr onClick="javascript:parent.plugin()" style="CURSOR: hand">
					<td background="images/default/left_background_01.gif" height="25"
						align="right">Ｐｖ插件</td>
					<td background="images/default/left_background_02.gif" width="21"
						height="25"></td>
					<td width="25"><img src="images/default/left_arrow_down.gif"
						width="25" height="25" border="0"></td>
				</tr>
			</table>
		</DIV>
		<DIV id="MFX11"
			style="position:absolute; width:158px; height:80px; z-index:1; left:20px; top: 810px; visibility: hidden;">
			<table width="156" border="0" cellspacing="0" cellpadding="0"
				align="center">
				<tr>
					<td width="1" bgcolor="FFFFFF"></td>
					<td width="154" bgcolor="D6DFF7" height="150" align="center">
					</td>
					<td width="1" bgcolor="FFFFFF"></td>
				</tr>
				<tr bgcolor="FFFFFF">
					<td colspan="3" height="1"></td>
				</tr>
			</table>
		</DIV>


		<!--wukp 20110923  
<DIV id="MFX12" style="position:absolute; width:150px; height:38px; z-index:2; left:20px; top: 785px; visibility: hidden;">
wukp 20110923  -->
		<DIV id="MFX12"
			style="position:absolute; width:150px; height:0px; z-index:2; left:20px; top: 785px; visibility: hidden;">

			<table width="156" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="13" colspan="3"></td>
				</tr>
				<tr onClick="MFXrunMenu(12,15)" style="CURSOR: hand">
					<td background="images/default/left_background_01.gif" height="25"
						align="right">报修记录</td>
					<td background="images/default/left_background_02.gif" width="21"
						height="25"></td>
					<td width="25"><img src="images/default/left_arrow_down.gif"
						width="25" height="25" border="0"></td>
				</tr>
			</table>
		</DIV>
		<DIV id="MFX13"
			style="position:absolute; width:158px; height:25px; z-index:1; left:20px; top: 810px; visibility: hidden;">
			<table width="156" border="0" cellspacing="0" cellpadding="0"
				align="center">
				<tr>
					<td width="1" bgcolor="FFFFFF"></td>
					<td width="154" bgcolor="D6DFF7" height="50" align="center"
						valign="top">
						<table width="100%">
							<tr>
								<td height="2" colspan="2"></td>
							</tr>

							<tr>
								<td align="left" valign="center" bgcolor="D6DFF7"><img
									src="images/default/left_func.gif"> <a href="#"
									onClick="javascript:parent.getMaintainList('1')">报修记录</a></td>
								<td align="left" valign="center" bgcolor="D6DFF7"><img
									src="images/default/left_func.gif"> <a href="#"
									onClick="javascript:parent.goUrl('ReportFormList.do?statesort=12')">记录统计</a>
								</td>
							</tr>


						</table></td>
					<td width="1" bgcolor="FFFFFF"></td>
				</tr>
				<tr bgcolor="FFFFFF">
					<td colspan="3" height="1"></td>
				</tr>
			</table>
		</DIV>




		<DIV id="MFX14"
			style="position:absolute; width:150px; height:38px; z-index:2; left:20px;top:946;visibility: hidden;<%if (!authlist.equals("*")) {%>display:none;<%}%>">
			<table width="156" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="13" colspan="3"></td>
				</tr>
				<tr onClick="MFXrunMenu(14,15)" style="CURSOR: hand">
					<td background="images/default/left_background_01.gif" height="25"
						align="right">系统管理</td>
					<td background="images/default/left_background_02.gif" width="21"
						height="25"></td>
					<td width="25"><img src="images/default/left_arrow_down.gif"
						width="25" height="25" border="0"></td>
				</tr>
			</table>
		</DIV>
		<DIV id="MFX15"
			style="position:absolute; width:158px; height:116px; z-index:1; left:20px; top: 971px; visibility: hidden;<%if (!authlist.equals("*")) {%>display:none;<%}%>">
			<table width="156" border="0" cellspacing="0" cellpadding="0"
				align="center">
				<tr>
					<td width="1" bgcolor="FFFFFF"></td>
					<td width="154" bgcolor="D6DFF7" height="80" align="center"
						valign="top">
						<table width="100%">
							<tr>
								<td height="2" colspan="2"></td>
							</tr>


							<tr>

								<td height="5"><img src="images/default/left_func.gif">
									&nbsp; <a href="#"
									onClick="javascript:parent.getDevftList('1')">设备厂商</a></td>
								<td height="5"><img src="images/default/left_func.gif">
									&nbsp; <a href="#"
									onClick="javascript:parent.getDevTpList('1')">设备类型</a></td>

							</tr>
							<tr>
								<td height="2" colspan="2"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/default/left_func.gif">
									&nbsp; <a href="#"
									onClick="javascript:parent.getUpdateTypeList('1')">更新类型</a></td>
									 <td height="5" >
      <img src="images/default/left_func.gif">&nbsp;&nbsp;<a href="#" onClick="javascript:parent.goUrl('FileUpload1.jsp')"> 信息上传</a>
      </td>
								<!--   wukp 20110923  
      <td height="5">
      <img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.systemPlugin()">插件管理</a>
      </td>
      </tr>
      <tr><td height="2" colspan="2"></td></tr>
      <tr>
      <td height="5">
      <img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('SystemSetup_setData.jsp')">数据设置</a>
      </td>
      <td height="5">
      <img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('SystemSetup_dealData.jsp')">历史数据</a>
      </td>
      </tr>
      <tr><td height="2" colspan="2"></td></tr>
      <tr>
      <td height="5">
      <img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('SystemSetup_ClearEatCardLog.jsp')">吞卡流水</a>
      </td>
      <td height="5">
      <img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('SystemSetup_ClearOPLog.jsp')">操作流水</a>
      </td>
      </tr>
      <tr><td height="2" colspan="2"></td></tr>
      
      <td height="5">
      <img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('SystemSetup_Reset.jsp')">重建缓存</a>
      </td>
      <td height="5">
      <img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('STATAReportList.do')">自动报表</a>
      </td>
      <tr><td height="2" colspan="2"></td></tr>
      <tr>
      <td height="5">
      <img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('STATAReportList.do')">业务类型</a>
      </td>
      </t>
	wukp 20110923  -->

							</tr>
							<!--  <tr>
      <td height="2" colspan="2">
      <img src="images/default/left_func.gif">&nbsp;<a href="#" onClick="javascript:parent.goUrl('FileUpload1.jsp')"> 信息上传</a>
      </td>
      </tr> -->
							<tr>
								<td height="2" colspan="2"></td>
							</tr>
						</table></td>
					<td width="1" bgcolor="FFFFFF"></td>
				</tr>
				<tr bgcolor="FFFFFF">
					<td colspan="3" height="1"></td>
				</tr>
			</table>

		</DIV>

	</div>

	<%
			String myoutdate = myPubUtil.dealNull(myOperator.getAdddate()).trim();
			
			System.out.println("myoutdate="+myoutdate);
			
			String mybefore = myPubUtil.dealNull(myPubUtil.ReadConfig("Users", "WarningBefore", "2", "PowerView.ini")).trim();
			
			System.out.println("mybefore="+mybefore);
			
			try {
				if ((Integer.parseInt(myoutdate) - Integer.parseInt(myPubUtil.getOtherDate(Integer.parseInt(mybefore)))) <= 0) {
	%>
	<script>
alert("您的密码即将过期，请立即修改！");
parent.goUrl('ChangePwd.jsp');
</script>
	<%
		}
			} catch (Exception e) {
				e.printStackTrace();
	%>
	<script>
alert("您的密码即将过期，请立即修改！");
parent.goUrl('ChangePwd.jsp');
</script>
	<%
		}
	%>


	<!--
第一步：定义菜单的个数
第一个菜单分为两个图层，一个用来显示菜单，另一个用于显示菜单内容
myLayer[0]=(NN) ? document.MFX0 : document.all.MFX0.style;　　　// 第一组
myLayer[1]=(NN) ? document.MFX1 : document.all.MFX1.style;
myLayer[2]=(NN) ? document.MFX2 : document.all.MFX2.style;　　　// 第二组
myLayer[3]=(NN) ? document.MFX3 : document.all.MFX3.style;
myLayer[4]=(NN) ? document.MFX4 : document.all.MFX4.style;　　　// 第三组
myLayer[5]=(NN) ? document.MFX5 : document.all.MFX5.style;
myLayer[6]=(NN) ? document.MFX6 : document.all.MFX6.style;　　　// 第四组
myLayer[7]=(NN) ? document.MFX7 : document.all.MFX7.style;
如果要删除一组显示三个菜单的话，则将MFX6和MFX7这一组删除即可。
添加一组菜单则在上面的基础上再添加如下：
myLayer[8]=(NN) ? document.MFX8 : document.all.MFX8.style;　　　// 第五组
myLayer[9]=(NN) ? document.MFX9 : document.all.MFX9.style;


--------------------------------------------------------------------------------
第二步：定义菜单名称及内容
在所有图层的后面加上一组菜单的名称：
<DIV id="MFX8" style="position:absolute; width:150px; height:38px; z-index:2; left:20px; top: 463px; visibility: hidden">
　<table width="156" border="0" cellspacing="0" cellpadding="0">
　　<tr>
　　　<td height="13">
　　　　<a href="#"  onClick="MFXrunMenu(8,5)">菜单名称</a>　// 8是ID名，5是速度
　　　</td>
　　</tr>
　</table>
</DIV>
在MFX8图层后面再定义一个菜单内容的图层：
<DIV id="MFX9" style="position:absolute; width:150px; height:116px; z-index:1; left:20px; top: 488px; visibility: hidden">
　<table width="156" border="0" cellspacing="0" cellpadding="0">
　　<tr>
　　　<td height="13">菜单内容</td>
　　</tr>
　</table>
</DIV>

-->





</body>
</html:html>
