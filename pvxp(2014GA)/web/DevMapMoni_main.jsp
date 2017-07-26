<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.pojo.*"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />

<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
	<!--ҳ���϶�-->
	<script language="JavaScript">
<!--
var scrollcount=0;
var xscrollcount=0;
var dragy=0;
var dragx=0;

//0-����ƶ� 1-��ѡ��λԪ�� 2-�϶�
var mouseFlag = "0";


function initdrag() {
scrollcount=1;
xscrollcount=1;
dragy=event.clientY;
dragx=event.clientX;
}

function startdrag() {
if (scrollcount==1) {
window.scrollBy(0,dragy-event.clientY);

dragy=event.clientY;
}

if (xscrollcount==1) {
window.scrollBy(dragx-event.clientX,0);

dragx=event.clientX;
}
}

function enddrag() {
	try{
		scrollcount=0;
		xscrollcount=0;
	}catch(e){}
}
// -->
</script>

	<script language="JavaScript">
<!--
var devstr  = "";
var bankstr = "";
var imgstr  = "";
<!--ҳ���ʼ��-->
function initDev(devno,devx,devy){
	devno=String(devno);
	var tmpstr = "<div id=\"dev_element_"+devno+"\" style=\"position:absolute;z-index:1;left:"+devx+";top:"+devy+"\">\n";
	tmpstr += "<img id=\"dev_ico_"+devno+"\" src=\"images/default/devico_0.gif\" onClick=\"showDev('"+devno+"')\">\n";
	tmpstr += "</div>\n";
	document.all['showelements'].innerHTML+=tmpstr;
	document.all['showelements'].devnum++;
}

function initBank(bankid,bankx,banky,banktag){
	bankid=String(bankid);
	var tmpstr = "<div id=\"bank_element_"+bankid+"\" style=\"position:absolute;z-index:1;left:"+bankx+";top:"+banky+"\">\n";
	tmpstr += "<img id=\"bank_ico_"+bankid+"\" src=\"images/default/bankico_"+banktag+".gif\" onClick=\"showBank('"+bankid+"')\" onDblClick=\"parent.location='DevMapMoni_Fram.jsp?bankid="+bankid+"'\">\n";
	tmpstr += "</div>\n";
	document.all['showelements'].innerHTML+=tmpstr;
	document.all['showelements'].banknum++;
}

//���������¼�
function dealKeyEvent(){

	
}
function showDev(devno){
	parent.devmap_moni_state.inittable.style.display="none";
	parent.devmap_moni_state.banklist.style.display="none";
	parent.devmap_moni_state.showlist.style.display="";
	parent.devmap_moni_state.hideButMe("dev",devno);
}

function showBank(bankid){
	parent.devmap_moni_state.inittable.style.display="none";
	parent.devmap_moni_state.banklist.style.display="";
	parent.devmap_moni_state.showlist.style.display="none";
	parent.devmap_moni_state.hideButMe("bank",bankid);
}
// -->
</script>

</head>

<body leftmargin="0" topmargin="0" text="#000000"
	onselectstart="return false;" onkeydown="dealKeyEvent()"
	ondragstart="initdrag();return false;" style="CURSOR:hand;"
	onmousemove="this.focus();startdrag();" onmouseup="enddrag()">
	<%
		PubUtil myPubUtil = new PubUtil();
		String bankid = myPubUtil.dealNull(request.getParameter("bankid")).trim();
		DevMapModel mymapModel = new DevMapModel(bankid);
		String mymapname = mymapModel.getMapname();
		DevMap mymap = mymapModel.getDevMap();
		if (mymapname == null || mymap == null) {
	%>
	�û������޵�ͼ
	<%
			} else {
			String myimgname = mymap.getImgname();
	%>


	<!--ͼƬ��-->
	<div id="mappic" value="<%=myimgname%>"
		style="position:absolute;z-index:1;left:0;top:0;width:100%;height:100%;">
		<img id="mapimg" src="map/<%=myimgname%>">
	</div>
	<!--��ʾ�豸�ͻ����Ĳ�-->
	<div id="showelements" banknum=0 devnum=0
		style="position:absolute;z-index:3;width:100%;height:100%;left:0;top:0">

	</div>
	<!--��ȡ��ǰ��ͼ���ò���ʼ��-->
	<script>
<%
		int devnum	= mymap.getDevnum();
		int banknum = mymap.getBanknum();
		DevPosition[] devs = mymap.getDevs();
		DevinfoModel myDevinfoModel = new DevinfoModel();
		BankPosition[] banks = mymap.getBanks();
		BankinfoModel myBankinfoModel = new BankinfoModel();
		for( int i=0;i<devnum;i++ ){
			String thisdevno = devs[i].getDevno();
			Devinfo thisdev = myDevinfoModel.getDevFromList(thisdevno);
			if( thisdev!=null&& ( ( myPubUtil.dealNull( thisdev.getBankid() ) ).trim() ).equals(bankid) ){
%>
				initDev("<%=thisdevno%>",<%=devs[i].getXpos()%>,<%=devs[i].getYpos()%>);
<%
			}
		}
		for( int i=0;i<banknum;i++ ){
			String thisbankid = banks[i].getBankid();
			Bankinfo thisbank = myBankinfoModel.getBankinfoFromList(thisbankid);
			if( thisbank!=null ){
				String thisbanktag = thisbank.getBanktag().trim();
%>
				initBank("<%=thisbankid%>",<%=banks[i].getXpos()%>,<%=banks[i].getYpos()%>,"<%=thisbanktag%>");
<%
			}
		}
%>

</script>
	<%
	}
	%>

</body>
</html:html>