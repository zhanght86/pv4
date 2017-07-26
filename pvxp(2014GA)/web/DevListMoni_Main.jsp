<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>


<app:validateCookie />
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />

<%
	//1229��ʼ�޸�
	DevMoniServer myDevMoniServer = new DevMoniServer();
	myDevMoniServer.startMoni();

	PubUtil myPubUtil = new PubUtil();

	//��ȡ��ֵ��������ţ��Ƿ�����ӻ������豸�б��ȼ�
	String bankid = myPubUtil.dealNull(request.getParameter("bankid"))
			.trim();
	String incsubs = myPubUtil
			.dealNull(request.getParameter("incsubs")).trim();
	String devstr = myPubUtil.dealNull(request.getParameter("devs"))
			.trim();
	String level = myPubUtil.dealNull(request.getParameter("level"))
			.trim();

	//�����ظ�ʱ�䣬
	String repeat = myPubUtil.dealNull(
			myPubUtil.ReadConfig("System", "DevMoniReTime", "600000",
					"PowerView.ini")).trim();

	if (repeat.equals(""))
		repeat = "600000";

	//��ض˿ں�
	String port = myPubUtil.dealNull(
			myPubUtil.ReadConfig("System", "DevJKPort", "12315",
					"PowerView.ini")).trim();
	if (port.equals(""))
		port = "12315";

	BankinfoModel myBankinfoModel = new BankinfoModel();

	//�Ƿ�����ӻ���
	if (incsubs.equals("1")) {
		bankid = myBankinfoModel.getBankRange(bankid);
	}

	String strlen = String.valueOf(("DevMoni" + bankid).length());
	strlen = PubUtil.strFormat(strlen, 8, 1, '0');
%>
<html:html locale="true">
<head>
<title>�豸�б���</title>
<link href="style/pvxp.css" rel="stylesheet" type="text/css">
<html:base />
<jsp:include page="DevStateCode_JS.jsp" flush="true" />
<jsp:include page="DevStateNo_JS.jsp" flush="true" />
<script LANGUAGE="VBscript">


Sub DevMoniFlash_FSCommand(ByVal command, ByVal args)
    call DevMoniFlash_DoFSCommand(command, args)
end sub


</script>

<script language="JavaScript" type="text/JavaScript">//////////////
function DealUndefined(str){
  if(str!=undefined){
    return str; 
  }else{
    return "";
  }
}


//�����ṩ�Ľڵ�idֵn���������ڵ����dΪ����Ĳ��ҷ�Χ��document
function MM_findObj(n, d) { 
 
  var p,i,x; 
   if(!d) d=document; 
   if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; 
    n=n.substring(0,p);
    }
    
  if(!(x=d[n])&&d.all) 
  x=d.all[n]; 
  
  for (i=0;!x&&i<d.forms.length;i++) 
  x=d.forms[i][n];
  
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) 
  x=MM_findObj(n,d.layers[i].document);
  
  if(!x && d.getElementById) 
  x=d.getElementById(n); 
  return x;
}


//////////////
function MM_setTextOfLayer(objName,x,newText) {
//	alert('MM_setTextOfLayer');
 //v4.01
  if ((obj=MM_findObj(objName))!=null) with (obj)
    if (document.layers) {document.write(unescape(newText)); document.close();}
    else innerHTML = unescape(newText);
}


//�������id������tb����table�����Ҳ����ڸ���id������mid����<tr />������һ����midΪidֵ��<tr />
function insertTr_Style(tb,mid,classname){
//	alert('insertTr_Style');
	
  if(MM_findObj(tb)!=null&&MM_findObj(mid)==null){
    var table = document.all[tb];
    //alert("start");
    var rowcount = table.rows.length;
    var row = table.insertRow(rowcount);
    row.id=mid;
    row.className=classname;
  }
}

///////////////////
function insertTr(tb,mid){
//	alert('insertTr');
	//����һ��tr
  insertTr_Style(tb,mid,"");
}

///////////////////
function insertTd_Style(tr,mid,mywidth,classname){
//	alert('insertTd_Style');
	//����һ��td
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

////////////////
function insertTd(tr,mid){
//	alert('insertTd');
	//����һ��td
  insertTd_Style(tr,mid,"","");
}

/////////////////
function extendTable(imgid , trid){
//	alert('extendTable');
	//����<table />
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
}


///////////////////
function insertDevStateTable(tr,mid,style){

//	alert('insertDevStateTable');
	
  if(MM_findObj(tr)!=null&&MM_findObj(mid)==null){
    var m_tr = document.all[tr];
    //m_tr.innerHTML = "<td colspan=\"2\"><table width=\"100%\" id="+mid+" border=\"1\"></table></td>";
    
    var colcount = m_tr.cells.length;
    var cell = m_tr.insertCell(colcount);
    cell.id=mid+"_outtd";
    cell.innerHTML = "<table width=\"100%\" id="+mid+" border=\"0\" class=\"list_table_border\" cellspacing=\"1\" cellpadding=\"3\"></table>";
    return cell.id;
  }
}


var cryflag = true;
var crying  = false;
var crylevel="2";
/*��������*/

//////////////////
function cryaloud(){
  if(crying||!cryflag){
    return;
  }
  crying = true;
  document.all.crysound.play();
}

//////////////////
function crypause(){
  if(crying){
    cryflag=false;
    crystop();
    document.all.pausecry.src="images/default/cryagain.gif";
  }else{
    cryflag=true;
    changeCryLevel(crylevel);
    document.all.pausecry.src="images/default/pausecry.gif";
  }
}

//////////////////
function crystop(){
  document.all.crysound.stop();
  crying = false;
}



//////////////////
function changeCryLevel(m_level){

//	alert('changeCryLevel');
	
	try{
	  crystop();
	  crylevel=m_level;
	  var i = 0;
	  var table = document.all["showlist"];
	  var rowcount = table.rows.length;
	  for(i=0;i<rowcount;i++){
	    var row = table.rows(i);
	    var cellLength = row.cells.length;
	    for(var j=0;j<cellLength;j++){
	    	var cell = row.cells(j)
	    	var cellLevel = cell.level;
	    	if( cellLevel>=crylevel ){
		      cryaloud();
		      break;
		    }
	    }
	    
	  }
	}catch(e){
//	alert('����changeCryLevel'+e);
	}
}

///////////////////
function Show_devinfo(args) {

//	alert('Show_devinfo');
	
  /*var xpos = event.screenX;
  var ypos = event.screenY+10;
  if ( (xpos+750) > 1024 ) xpos=1024-750-10;
  if ( (ypos+180) >  768 ) ypos=ypos-180-50;*/
  var webHeight = 600;
  var webWidth = 800
  var height = window.screen.height;
  var width = window.screen.width;
  var left=0;
  var top=0;
  if(width>webWidth){
  	 left = (width-webWidth)/2;
  }
  if(height>webHeight){
  	 top = (height-webHeight)/2;
  }
  open("Devinfo_Show_Moni.jsp?args="+args,"_blank","height="+webHeight+",width="+webWidth+",left="+left+",top="+top+",status=no,toolbar=no,menubar=no,location=no,scrollbars=yes");
}

</script>
<script LANGUAGE="Javascript"> 

var tdWidth = 100;//��Ԫ�񳤶�

var numOfLine = Math.floor(window.screen.width/tdWidth);//һ����ʾ����

if(numOfLine%2!=0){//��numOfLineΪ������ת����ż��
	numOfLine=numOfLine-1;
}
var nowNum=0;//һ�е�ǰ��ʾ����,��0��ʼ

var lineNum=0;//����

var tempLineNum = lineNum;//��ʱ�������

var devTotalNum = 0;//�Ѿ���ʾ�豸����

var devnoStr = "<center><b>�豸���</b></center>";

var level="<%=level%>";

function format(str){
	var retStr = str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8)+" "+str.substring(8,10)+":"+str.substring(10,12)+":"+str.substring(12,14);
	return retStr;
}

//ȥ��strǰ�˵�" ","\t","\n","\r"�ַ�
function ltrim(str){
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
    if (whitespace.indexOf(s.charAt(0)) != -1)
    //�ж�s�ĵ�һ���ַ��Ƿ���whitespace�ַ����е��ַ�
    {
        var j=0,i = s.length;
        while (j < i && whitespace.indexOf(s.charAt(j)) != -1)
        {
            j++;
            //jΪs�ַ���ǰ�˼����ַ���whitespace��ĳ���ַ�������
        }
        s = s.substring(j, i);
    }
    return s;
}



//ȥ��str�ַ���ĩβ��" ","\t","\n","\r"�ַ�
function rtrim(str){
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





//��� ��ص��豸
function DevMoniFlash_DoFSCommand(command, args){

//   alert('DevMoniFlash_DoFSCommand');

   var alloweddevs = "<%=devstr%>";

  try{

    if (command == "setDevState") {

       var mpos = args.indexOf(":");

       var devno = rtrim(ltrim(args.substring(0,mpos))); //����豸���

       var devpos = alloweddevs.indexOf(","+devno+",");

       if( alloweddevs=="A"||devpos!=-1 ){

         //��ʾ���з��������豸����豸��������б���

       }else{
         return true;
       }

      var dev_td = "dev_"+devno;
	  var allstr = args.substring(mpos+1);//���豸��������е�״̬��Ϣ

	  var myTTstate ="-1";    //Ĭ��δ֪״̬

	 var statesArray = allstr.split("|");
	   var thisState;
		for(var i=0;i<statesArray.length;i++){
		    	var recordArray = statesArray[i].split(",");
		   		try{
		   			var thisStateArray = eval("Code_"+recordArray[1]).split(",");
		   			var thisState = thisStateArray[1];
		   			//alert(thisState);
		   		} catch(e){
		   			//alert('����DevMoniFlash_DoFSCommand��'+e);
		   			thisState = -1;
		   		}

		   		if(myTTstate<thisState){
		   			myTTstate = thisState;
		   		}
		}


		 if(myTTstate<level){
		  	 return;
		 }

	      if(MM_findObj(dev_td)==null){//�����ж��Ƿ����dev_td�Ķ���û�м���ö���nowNum��1������������ӿհ�tr������
	      	if(nowNum==0){
			  	lineNum=lineNum+1;
			  	//tempLineNum = lineNum;
		      	insertTr("showlist","tr_"+lineNum);      	
	      	}
	      	insertTd_Style("tr_"+lineNum,dev_td,tdWidth,"list_td_prom");
	      	
	      	//��ʱ���
		    var myflashbtstr = "<object classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" codebase=\"http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0\" width=\"102\" height=\"24\" id=\"DevCheck\" align=\"middle\">\n";
	        myflashbtstr+="<param name=\"allowScriptAccess\" value=\"sameDomain\" />\n";
	        myflashbtstr+="<param name=\"movie\" value=\"swf/DevCheck.swf?devno="+devno+"\" />\n";
	        myflashbtstr+="<param name=\"quality\" value=\"high\" />\n";
	        myflashbtstr+="<param name=\"menu\" value=\"false\" />\n";
	        myflashbtstr+="<param name=\"wmode\" value=\"transparent\" />\n";
	        myflashbtstr+="<embed src=\"swf/DevCheck.swf\" quality=\"high\" bgcolor=\"#ffffff\" width=\"102\" height=\"24\" name=\"DevCheck\" align=\"middle\" allowScriptAccess=\"sameDomain\" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" />\n";
	        myflashbtstr+="</object>\n";
	      	insertTd_Style("tr_"+lineNum,dev_td+"_checknow","110","list_td_prom");
         	MM_setTextOfLayer(dev_td+"_checknow",'','<center>'+DealUndefined(myflashbtstr)+'</center>');
	      	
	      	nowNum=nowNum+2;
	      	devTotalNum = devTotalNum+1;
		  }
	      
	      
	      if(nowNum-numOfLine==0){
	      	 nowNum=0;
	      }
	      
	      if( myTTstate=="0" ){
	        MM_setTextOfLayer(dev_td,'',"<a href=\"#\" onClick=\"Show_devinfo('"+args+"')\"><center>"+devno+"</center></a>");
	      }else if( myTTstate=="1" ){
	        MM_setTextOfLayer(dev_td,'',"<a href=\"#\" onClick=\"Show_devinfo('"+args+"')\"><b><font color=#F5F328><center>"+devno+"</center></font></b></a>");
	      }else if( myTTstate=="2" ){
	        MM_setTextOfLayer(dev_td,'',"<a href=\"#\" onClick=\"Show_devinfo('"+args+"')\"><b><font color=red><center>"+devno+"</center></font></b></a>");
	      }else{
	        MM_setTextOfLayer(dev_td,'',"<a href=\"#\" onClick=\"Show_devinfo('"+args+"')\"><b><font color=#999999><center>"+devno+"</center></font></b></a>");
	      }
	      
	      var ss = (alloweddevs.split(",")).length-2//��Ҫ��ʾ���豸����
	      if(ss<numOfLine&&devTotalNum==ss){
	      	 for(var i=0;i<numOfLine-ss-1;i++){
	      	 	insertTd_Style("tr_"+lineNum,"",tdWidth,"");
	      	 }
	      	 devTotalNum = 0;
          }
	      
	      document.all[dev_td].level=myTTstate;
	      changeCryLevel(crylevel);
    }
    //alert(devTotalNum);
  }catch(e){
  // alert('����DevMoniFlash_DoFSCommand��'+e);
  }
}

</script>
</HEAD>
<BODY BGCOLOR="#FFFFFF">
	<center>

		<table id="ctrl_bar" width="100%" cellspacing="1" cellpadding="2"
			class="devmoni_ctrl">
			<tr>
				<td valign="middle"><EMBED id="crysound" name="crysound"
						style="WIDTH: 0px; HEIGHT: 0px" src="sound/devcry.mp3" width=0
						height=0 autostart="false" loop="true" type="audio/mpeg">
					�������أ� <input name="switch" type="radio" value="on" checked
					onClick="javascript:cryflag=true;changeCryLevel(crylevel);">
					���� <input type="radio" name="switch" value="off"
					onClick="javascript:cryflag=false;crystop();">
					�ر�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; �������� <input id="cry_level2"
					name="level" type="radio" value="2" checked
					onClick="javascript:changeCryLevel(document.all.cry_level2.value);">
					���� <input id="cry_level1" type="radio" name="level" value="1"
					onClick="javascript:changeCryLevel(document.all.cry_level1.value);">
					����</td>
				<td valign="middle" width="200"><input id="pausecry"
					name="pausecry" type="image" src="images/default/pausecry.gif"
					onClick="javascript:crypause();" onFocus="this.blur()"
					style="display:none"></td>
				<td align="right" valign="middle"><OBJECT
						CLASSID="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" WIDTH="260"
						HEIGHT="19"
						CODEBASE="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab"
						ID=DevMoniFlash>
						<PARAM NAME="MOVIE"
							VALUE="swf/DevMoni.swf?port=<%=port%>&moniaskstr=<%=strlen%>DevMoni<%=bankid%>&repeat=<%=repeat%>">
						<PARAM NAME="menu" VALUE="false">
						<PARAM NAME="wmode" VALUE="transparent">
					</OBJECT></td>
			</tr>
		</table>
		<br>
		<table id="showlist" width="100%" cellspacing="1" cellpadding="2"></table>


		<br>
		<table id="footbar" width="100%" cellspacing="1" cellpadding="2"
			class="devmoni_ctrl">
			<tr>
				<td>:::.:: Powered by �˳����� ������ҵ�� Copyright &copy; 2004,
					2010&nbsp;&nbsp;&nbsp; <font color=#CCCCCC>PowerView XP</font>
					:::.. �豸�б���</td>
			</tr>
		</table>
	</center>

</body>
</html:html>
