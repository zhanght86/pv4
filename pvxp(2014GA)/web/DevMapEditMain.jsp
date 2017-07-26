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
	var tmpstr = "<div id=\"dev_element_"+devno+"\" type=\"dev\" value=\""+devno+"\" xpos=\""+devx+"\" ypos=\""+devy+"\" style=\"position:absolute;z-index:1;left:"+devx+";top:"+devy+"\">\n";
	tmpstr += "<img id=\"dev_ico_"+devno+"\" src=\"images/default/devico_0.gif\" onClick=\"parent.devmap_edit_right.getPreEle('dev','"+devno+"');parent.devmap_edit_info.setCurXY(document.all.dev_element_"+devno+".xpos,document.all.dev_element_"+devno+".ypos);\">\n";
	tmpstr += "</div>\n";
	document.all['showelements'].innerHTML+=tmpstr;
	document.all['showelements'].devnum++;
}

function initBank(bankid,bankx,banky,banktag){
	bankid=String(bankid);
	var tmpstr = "<div id=\"bank_element_"+bankid+"\" type=\"bank\" value=\""+bankid+"\" xpos=\""+bankx+"\" ypos=\""+banky+"\" style=\"position:absolute;z-index:1;left:"+bankx+";top:"+banky+"\">\n";
	tmpstr += "<img id=\"bank_ico_"+bankid+"\" src=\"images/default/bankico_"+banktag+".gif\" onClick=\"parent.devmap_edit_right.getPreEle('bank','"+bankid+"');parent.devmap_edit_info.setCurXY(document.all.bank_element_"+bankid+".xpos,document.all.bank_element_"+bankid+".ypos);\">\n";
	tmpstr += "</div>\n";
	document.all['showelements'].innerHTML+=tmpstr;
	document.all['showelements'].banknum++;
}
<!--�༭״̬-->
//�ı䱳��ͼƬ
function changeImg(newimgname){
	 document.all['mappic'].value = newimgname;
	 document.all['mapimg'].src= "map/"+newimgname;
}
//��ӻ��ƶ���ǰѡ�е�Ԫ��
function addEle(myxpos,myypos){
	if( myxpos==""||myypos=="" ) return;
	try{
		var myvalue = parent.devmap_edit_right.curEleValue;
		var mytype = parent.devmap_edit_right.curEleType;
		if( myvalue!=""&&mytype!="" ){
			if( document.all[mytype+"_element_"+myvalue]!=null ){
				document.all[mytype+"_element_"+myvalue].style.display="";
				document.all[mytype+"_element_"+myvalue].style.left=myxpos+"px";
				document.all[mytype+"_element_"+myvalue].xpos=myxpos;
				document.all[mytype+"_element_"+myvalue].style.top=myypos+"px";
				document.all[mytype+"_element_"+myvalue].ypos=myypos;
			}else{
				if( mytype=="bank" ) initBank(myvalue,myxpos,myypos,"2");
				else initDev(myvalue,myxpos,myypos);
			}
			parent.devmap_edit_info.setCurXY(myxpos,myypos);
		}
	}catch(e){}
}
//��ʾ��굱ǰλ��
function reportMove(){
	try{
		parent.devmap_edit_info.setMouseXY(window.event.offsetX,window.event.offsetY);
	}catch(e){}
}
//�ӵ�ͼ��ɾ��ѡ�е�Ԫ��
function deleteEle(){
	try{
		var myvalue = parent.devmap_edit_right.curEleValue;
		var mytype = parent.devmap_edit_right.curEleType;
		document.all[mytype+"_element_"+myvalue].style.display="none";
		if( mytype=="bank" )
			document.all['showelements'].banknum--;
		else if( mytype == "dev" )
			document.all['showelements'].devnum--;
	}catch(e){}
}
<!--��ͼ����-->
//��ȡ������ͼ����
function prepareStr(){
	devstr  = document.all['showelements'].devnum;
	bankstr = document.all['showelements'].banknum;
	imgstr  = document.all['mappic'].value;

	for(i=0; i<showelements.all.length; i++) {
	var myobj = showelements.all(i);
	var myTagname = myobj.tagName;
  if( myTagname == "DIV" && myobj.style.display!="none"  )
  	if( myobj.type=="dev" ) devstr+="|"+String(myobj.value)+","+myobj.xpos+","+myobj.ypos;
  	else if( myobj.type=="bank" ) bankstr+="|"+String(myobj.value)+","+myobj.xpos+","+myobj.ypos;
	}
}
//��������¼�
function dealKeyEvent(){
	if(event.keyCode==46||event.keyCode==68){		//ɾ��Ԫ��(Del��D)
			parent.devmap_edit_main.deleteEle();
	}else if( event.keyCode==27||event.keyCode==81 ){							//ȡ��ѡ��(ESC��Q)
		parent.devmap_edit_right.clearAll();
	}else if( event.keyCode==32||event.keyCode==13||event.keyCode==65 ){  //���(space��Enter��A)
		addEle(event.offsetX,event.offsetY);
	}
	
}
// -->
</script>
</head>

<body leftmargin="0" topmargin="0" text="#000000"
	onselectstart="return false;" onkeydown="dealKeyEvent()"
	ondragstart="initdrag();return false;" style="CURSOR:hand;"
	onmousemove="this.focus();startdrag();reportMove()"
	onmouseup="enddrag()">
	<%
		PubUtil myPubUtil = new PubUtil();
		String bankid = myPubUtil.dealNull(request.getParameter("bankid")).trim();
		DevMapModel mymapModel = new DevMapModel(bankid);
		String mymapname = mymapModel.getMapname();
		DevMap mymap = mymapModel.getDevMap();
		if (mymapname == null || mymap == null) {
	%>
	�޷���ȡ��ͼ�ļ�
	<%
			} else {
			String myimgname = mymap.getImgname();
	%>


	<!--ͼƬ��-->
	<div id="mappic" value="<%=myimgname%>"
		style="position:absolute;z-index:1;left:0;top:0;width:100%;height:100%;">
		<img id="mapimg" src="map/<%=myimgname%>"
			onClick="addEle(window.event.offsetX,window.event.offsetY);">
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
