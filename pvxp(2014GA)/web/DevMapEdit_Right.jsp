<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />

<%
	PubUtil myPubUtil = new PubUtil();
	String bankid = myPubUtil.dealNull(request.getParameter("bankid")).trim();
%>
<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
	<script>
	var curEleType="";
	var curEleValue="";
	var curDevaddr="";
	var curDevip="";
	var curBankaddr="";
	var curBanktag="";
	var curDevtype="";
	function clearAll(){
		document.all['devnoselect'].value="";
		document.all['bankidselect'].value="";
		curEleValue="";
		curEleType="";
		curEleValue="";
		curDevaddr="";
		curDevip="";
		curBankaddr="";
		curBanktag="";
		curDevtype="";
		parent.devmap_edit_info.setCurXY("","");
		setCurObjTXT();
	}
	function hidpage(){
		document.body.scroll="no";
	}
	function showpage(){
		document.body.scroll="yes";
	}
	function OnBankChange(){
		document.all['devnoselect'].value="";
		curEleType = "bank";
		curEleValue=document.all['bankidselect'].value;
		
		if(parent.devmap_edit_main.document.all["bank_element_"+curEleValue]!=null&&parent.devmap_edit_main.document.all["bank_element_"+curEleValue].style.display!="none"){
			parent.devmap_edit_info.setCurXY(parent.devmap_edit_main.document.all["bank_element_"+curEleValue].xpos,parent.devmap_edit_main.document.all["bank_element_"+curEleValue].ypos);
		}else{
			parent.devmap_edit_info.setCurXY("","");
		}
		curBanktag = getOptionsAttribute(document.all['bankidselect'],curEleValue,"banktag");
		curBankaddr = getOptionsAttribute(document.all['bankidselect'],curEleValue,"bankaddr");
		setCurObjTXT();
	}
	function OnDevChange(){
		document.all['bankidselect'].value="";
		curEleType = "dev";
		curEleValue=document.all['devnoselect'].value;
		
		if(parent.devmap_edit_main.document.all["dev_element_"+curEleValue]!=null&&parent.devmap_edit_main.document.all["dev_element_"+curEleValue].style.display!="none"){
			parent.devmap_edit_info.setCurXY(parent.devmap_edit_main.document.all["dev_element_"+curEleValue].xpos,parent.devmap_edit_main.document.all["dev_element_"+curEleValue].ypos);
		}else{
			parent.devmap_edit_info.setCurXY("","");
		}
		curDevip = getOptionsAttribute(document.all['devnoselect'],curEleValue,"devip");
		curDevtype = getOptionsAttribute(document.all['devnoselect'],curEleValue,"typeno");
		curDevaddr = getOptionsAttribute(document.all['devnoselect'],curEleValue,"devaddr");
		setCurObjTXT();
	}
	function getOptionsAttribute(selectObjname,myvalue,myAttributename){
		var selectObj = eval(selectObjname);
		for (i=0; i<selectObj.options.length; i++) {
			if( selectObj.options(i).value==myvalue ){
				return selectObj.options(i)[myAttributename];
			}
		}
	}

	function getPreEle(mtype,mvalue){
		document.all['bankidselect'].value="";
		document.all['devnoselect'].value="";
		curEleType=mtype;
		curEleValue=mvalue;
		
		CurObj2select();
		if( mtype == "bank" ){
			curBanktag = getOptionsAttribute(document.all['bankidselect'],curEleValue,"banktag");
			curBankaddr = getOptionsAttribute(document.all['bankidselect'],curEleValue,"bankaddr");
		}else{
			curDevip = getOptionsAttribute(document.all['devnoselect'],curEleValue,"devip");
			curDevtype = getOptionsAttribute(document.all['devnoselect'],curEleValue,"typeno");
			curDevaddr = getOptionsAttribute(document.all['devnoselect'],curEleValue,"devaddr");
		}
		setCurObjTXT();
	}
	function CurObj2select(){
		var thisselectobjname = "";
		if( curEleType == "dev" )
			thisselectobjname = "devnoselect";
		else 
			thisselectobjname = "bankidselect";
		var thisselectobj = document.all[thisselectobjname];
		if( thisselectobj!=null ){
			for (i=0; i<thisselectobj.options.length; i++) {
		  	if( thisselectobj.options(i).value==curEleValue ){
		  		thisselectobj.options(i).selected=true;
		  	}
		  }
		}
		
	}
	function setCurObjTXT(){
		var mytxt = "";
		var mytxt1 = "";
		var myObjType = "";
		if( curEleValue!="" ){
			if( curEleType == "bank" ){
				myObjType="机构";
				mytxt1 = "机构地址&nbsp;&nbsp;"+curBankaddr+"&nbsp;&nbsp;|&nbsp;&nbsp;机构级别&nbsp;&nbsp;"+curBanktag;
			}else{
				myObjType="设备";
				mytxt1 = "安装地址&nbsp;&nbsp;"+curDevaddr+"&nbsp;&nbsp;|&nbsp;&nbsp;IP地址&nbsp;&nbsp;"+curDevip+"&nbsp;&nbsp;|&nbsp;&nbsp;设备类型&nbsp;&nbsp;"+curDevtype;
			}
			mytxt = "当前选中的"+myObjType+":"+getObjName();
		}else{
			mytxt = "尚未选择任何机构或设备";
			mytxt1 = "&nbsp;";
		}
		try{
			parent.devmap_edit_info.document.all['curobj_txt'].innerHTML=mytxt;
			parent.devmap_edit_info.document.all['curobj_detail_txt'].innerHTML=mytxt1;
		}catch(e){}
	}
	function getObjName(){
		if( curEleType=="dev" ) return curEleValue;
		var el = document.all['bankidselect'];
		if(el != null) {
		  for (i=0; i<el.options.length; i++) {
		  	if( el.options(i).value==curEleValue )
		  		return el.options(i).text;
		  }
		}
		return "";
	}
	function refleshimglist(thebankid,thefiletype){
		document.all.imglist.src="DevMapEditImg_List.jsp?bankid="+thebankid+"&filetype="+thefiletype;
	}
	
	function dealKeyEvent(){
		//alert(event.keyCode);
		if(event.keyCode==46||event.keyCode==68){		//删除元素(Del、D)
			parent.devmap_edit_main.deleteEle();
		}else if( event.keyCode==27||event.keyCode==81 ){		//取消选择(ESC、Q)
			clearAll();
		}
	}
</script>
</head>
<body leftmargin="0" topmargin="0" onkeydown="dealKeyEvent();">
	<table width="100%" height="100%" border="0" cellpadding="0"
		cellspacing="0" id="maintable">
		<tr>
			<td id="tdbar" width="1" style="CURSOR: hand"
				background="images/default/out_bk.gif"
				onClick="javascript:parent.pagebar()">
				<img src="images/default/out.gif" name="pbar" width="8" height="194"
					border="0" id="pbar">
			</td>
			<td width="100%" valign="top" align="center">
				<table width=100% border=0>
					<tr>
						<td width="100%" align="center" height="5"></td>
					</tr>
					<tr>
						<td>
							可选机构列表
						</td>
					</tr>
					<tr>
						<td>
							<app:bankselect property="bankidselect"
								onChange="OnBankChange();" currentBankid="<%=bankid%>"
								incSelf="false" size="5"
								style="border: 0 solid #000000;width:100%" />
						</td>
					</tr>

					<tr>
						<td>
							可选设备列表
						</td>
					</tr>
					<tr>
						<td>
							<app:devselect property="devnoselect" onChange="OnDevChange();"
								bankRange="<%=bankid%>" size="10"
								style="border: 0 solid #000000;width:100%" />
						</td>
					</tr>
					<tr>
						<td align="center">
							<a href="javascript:clearAll()" onFocus="this.blur()"><img
									src="images/default/bt_clearselect.gif" border="0">
							</a>
						</td>
					</tr>
					<tr>
						<td align="center" height="5"></td>
					</tr>
					<tr>
						<td align="center" height="1" bgcolor="#000000"></td>
					</tr>
					<tr>
						<td align="center" height="5"></td>
					</tr>
					<tr>
						<td>
							<iframe id="imglist" width="100%" height="140" scrolling="no"
								src="DevMapEditImg_List.jsp?bankid=<%=bankid%>" frameborder="0"></iframe>
						</td>
					</tr>
					<tr>
						<td>
							<iframe id="upload" width="100%" height="80" scrolling="no"
								src="DevMapEditUpload.jsp?bankid=<%=bankid%>" frameborder="0"></iframe>
						</td>
					</tr>

				</table>
			</td>
		</tr>
	</table>
	<script>
setCurObjTXT();
</script>
</body>
</html:html>
