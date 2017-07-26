<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="com.lcjr.pvxp.model.*"%>
<%@ page import="com.lcjr.pvxp.orm.*"%>
<%@ page import="com.lcjr.pvxp.util.*"%>
<%@ page import="org.apache.log4j.*"%>


<app:validateCookie />
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="1" />


<%

	Logger log = Logger.getLogger("org.apache.struts");
	
	PubUtil pubUtil = new PubUtil();
	CharSet charSet = new CharSet();
	
	String devno = pubUtil.dealNull(request.getParameter("devno")).trim();
	Devinfo dev = DevinfoModel.getDevFromList(devno);
	
	dev.setDevip(dev.getDevip().trim());
	dev.setDevmac(dev.getDevmac().trim());
	dev.setSysid(dev.getSysid().trim());
	dev.setOrganno(dev.getOrganno().trim());
	dev.setDevaddr(charSet.db2web(dev.getDevaddr().trim()));
	dev.setDutyname(charSet.db2web(dev.getDutyname().trim()));
	dev.setOrgancontact(dev.getOrgancontact().trim());
	dev.setAutherno(dev.getAutherno().trim());
	dev.setDevkey(dev.getDevkey().trim());
	dev.setPinkey(dev.getPinkey().trim());
	dev.setMackey(dev.getMackey().trim());
	dev.setTellerno(dev.getTellerno().trim());
	dev.setRemark2(dev.getRemark2().trim());
	
	request.setAttribute("ModifyDevForm", dev);
	
	String typeno = pubUtil.dealNull(dev.getTypeno()).trim();
	String bankid = pubUtil.dealNull(dev.getBankid()).trim();
	String updateno = pubUtil.dealNull(dev.getSysid()).trim();
	log.error("11ModifyDev:devno--" + dev.getDevno());
%>


<html:html locale="true">
<head>
	<title><bean:message key="pvxp.dev.modify" /></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>
	<script language="JavaScript">
    <!--


//为电话号码 3到4位区号-7到8位号码  或  只有7到8位号码    或  3到4位区号7-8位号码    或  的11位手机号
function isNumber(oNum) 
{ 
  if(!oNum) return 2; 
  var strP= /(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{3,4}\-[0-9]{7,8}\-[0-9]{2,6}$)|(^[0-9]{7,8}$)|(^[0-9]{3,4}[0-9]{7,8}$)|(^0{0,1}1[0-9]{10}$)/;
  if(!strP.test(oNum)) return 2; 
  return 1; 
}

//为数字
function isNumber2(oNum) 
   { 
  if(!oNum) return 2; 
 // var strP=/^\d+(\.\d+)?$/; 可以为小数，但不保证输入的开头不是0
  var strP=/^(0|[1-9][0-9]*)$/;
  if(!strP.test(oNum)) return 2; 
  return 1; 
   }


 //IP校验
    function checkip(ip) 
{    
    var re=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/ ; 
    if(re.test(ip)) 
    { 
    	return 1;
    } 
    else 
    {
        return 2;
    }
}


//mac地址校验
 function CheckMac(mac)  
{
	var reg=/[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}/;  
	if(reg.test(mac)){
	return 1;
	}else {
	return 2
	}
}

      function validate() {
        if (document.all.devip.value == "") {
        	alert("请输入IP地址");
        	document.all.devip.focus();
        	return false;
        }

        if (checkip(document.all.devip.value) == 2) {
        	alert("输入的IP地址格式错误");
        	document.all.devip.focus();
        	return false;
        }
       /**
       //MAC校验
        if (document.all.devmac.value == "") {
        	alert("请输入MAC地址");
        	document.all.devmac.focus();
        	return false;
        }

        if (CheckMac(document.all.devmac.value) == 2) {
        	alert("输入的MAC地址格式错误");
        	document.all.devmac.focus();
        	return false;
        } 


        //电话号码校验
        if (document.all.organcontact.value == "") {
        	alert("请输入电话号码");
        	document.all.organcontact.focus();
        	return false;
        }

        if (isNumber(document.all.organcontact.value) == 2) {
        	alert("输入的电话号码格式错误");
        	document.all.organcontact.focus();
        	return false;
        }
        **/
        
        if (document.all.organno.value == "") {
          alert("请输入网点编号");
          document.all.organno.focus();
          return false;
        }
        
        if (document.all.tellerno.value == "") {
          alert("请输入柜员编号");
          document.all.tellerno.focus();
          return false;
        }

        if (document.all.dutyname.value == "") {
        	alert("请输入维护人");
        	document.all.dutyname.focus();
        	return false;
        }
        
        var remark2=document.all.remark2.value;
        if (document.all.remark2.value == "") {
        	alert("请输入饱和运行时间");
        	document.all.remark2.focus();
        	return false;
        }else if(isNumber2(remark2)==2 ){
       	    alert("请输入正确数字");
        	document.all.remark2.focus();
        	return false;
        }else if(remark2<0||remark2>24){
        	alert("饱和运行时间在0-24之间");
        	document.all.remark2.focus();
        	return false;
        }
        
        return true;
      }
      
    //-->
    </script>
</head>

<body onload="javascript:parent.hidit();">
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" align="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP</font> ---
				<font class="location"><bean:message key="pvxp.dev.modify" />
				</font>
			</td>
		</tr>
	</table>


	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<html:form action="/ModifyDev.do">
			<html:hidden property="isShowModifyForm" value="false" />
			<!-- header -->
			<tr align="center">
				<td colspan="8" class="list_td_title">
					<b><bean:message key="pvxp.dev.modify" /> </b>
				</td>
			</tr>
			<!-- body -->
			<tr>
				<td width="8%" class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.devno" /> </b>
				</td>
				<td width="17%" class="list_td_detail">
					&nbsp;
					<bean:write name="ModifyDevForm" property="devno" />
					<html:hidden property="devno" />
				</td>
				<td width="8%" class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.devip" /> </b>
				</td>
				<td width="17%" class="list_td_detail">
					&nbsp;
					<html:text property="devip" size="15" maxlength="15" />
				</td>
				<td width="8%" class="list_td_prom">
					<nobr>
						&nbsp;
						<b><bean:message key="prompt.devmac" /> </b>
					</nobr>
				</td>
				<td width="17%" class="list_td_detail">
					&nbsp;
					<html:text property="devmac" size="15" maxlength="64" />
				</td>
				<td width="8%" class="list_td_prom">
					<nobr>
						&nbsp;
						<b><bean:message key="prompt.typeno" /> </b>
					</nobr>
				</td>
				<td width="17%" class="list_td_detail">
					&nbsp;
					<app:devtpselect property="typeno" showName="1"
						defaultValue="<%=typeno%>" />
				</td>
			</tr>
			<tr>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.auther" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="autherno" size="15" maxlength="10" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.organno" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="organno" size="15" maxlength="20" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.tellerno" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="tellerno" size="15" maxlength="10" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.bank" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<app:bankselect property="bankid" defaultValue="<%=bankid%>" />
				</td>
			</tr>
			<tr>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.devaddr" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="devaddr" size="15" maxlength="60" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.dutyname" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="dutyname" size="15" maxlength="20" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.organcontact" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="organcontact" size="15" maxlength="20" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.sysid" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<app:updatetypeselect property="sysid" showName="1"
						defaultValue="<%=updateno%>" />
				</td>
			</tr>
			<!-- <tr>
        <td class="list_td_prom">&nbsp;<nobr><b><bean:message key="prompt.devkey"/></b></nobr></td>
        <td class="list_td_detail">&nbsp;<html:text property="devkey" size="15" maxlength="60"/></td>
        <td class="list_td_prom">&nbsp;<b><bean:message key="prompt.pinkey"/></b></td>
        <td class="list_td_detail">&nbsp;<html:text property="pinkey" size="15" maxlength="20"/></td>
        <td class="list_td_prom">&nbsp;<b><bean:message key="prompt.mackey"/></b></td>
        <td class="list_td_detail">&nbsp;<html:text property="mackey" size="15" maxlength="20"/></td>
        <td class="list_td_prom">&nbsp;&nbsp;</td>
        <td class="list_td_detail">&nbsp;&nbsp;</td>
      </tr>-->
			<html:hidden property="devkey" />
			<html:hidden property="pinkey" />
			<html:hidden property="mackey" />
			<tr>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.typestate" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:select property="typestate">
						<html:option value="a">正常</html:option>
						<html:option value="s">停用</html:option>
					</html:select>
				</td>
				<td class="list_td_prom">
					<nobr>
						&nbsp;
						<b><bean:message key="prompt.datatype" /> </b>
					</nobr>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:select property="packtype">
						<html:option value="b">B/S结构</html:option>
						<html:option value="c">C/S结构</html:option>
					</html:select>
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.localtag" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:select property="localtag">
						<html:option value="0">本地</html:option>
						<html:option value="1">服务器</html:option>
					</html:select>
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.polltag" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:select property="polltag">
						<html:option value="1">需要轮询</html:option>
						<html:option value="0">不需轮询</html:option>
						<html:option value="9">立刻轮询</html:option>
					</html:select>
				</td>
			</tr>

			<tr>
				<td class="list_td_prom">
					&nbsp;
					<nobr>
						<b>饱和运行时间</b>
					</nobr>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="remark2" size="15" maxlength="2" />
					(小时/天)
				</td>
				<td class="list_td_prom">
					&nbsp;&nbsp;
				</td>
				<td class="list_td_detail">
					&nbsp;&nbsp;
				</td>
				<td class="list_td_prom">
					&nbsp;&nbsp;
				</td>
				<td class="list_td_detail">
					&nbsp;&nbsp;
				</td>
				<td class="list_td_prom">
					&nbsp;&nbsp;
				</td>
				<td class="list_td_detail">
					&nbsp;&nbsp;
				</td>
			</tr>
	</table>

	<table align="center" width="40%">
		<tr align="center">
			<td>
				<input type="image" src="images/default/bt_ok.gif"
					onclick="return validate()">
			</td>
			<td>
				<img src="images/default/bt_reset.gif" onclick="reset()"
					style="cursor:hand">
			</td>
			<td>
				<img src="images/default/bt_back.gif" onClick="history.back()"
					style="cursor:hand">
			</td>
		</tr>
		</html:form>
	</table>

</body>
</html:html>
