<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="2" />


<%@ page import="java.util.*"%>
<%@ page import="com.lcjr.pvxp.model.*"%>
<%
	boolean canAdd = true;

	BankinfoModel bankinfoModel = new BankinfoModel();
	List banks = bankinfoModel.getBankinfoList();
	if (banks == null || banks.size() == 0) {
		canAdd = false;
	}

	DevtypeModel devtypeModel = new DevtypeModel();
	List devtypes = devtypeModel.getDevTpList();
	if (devtypes == null || devtypes.size() == 0) {
		canAdd = false;
	}

	UpdateTypeModel updateTypeModel = new UpdateTypeModel();
	List updatetypes = updateTypeModel.getUpdateTypeList();
	if (updatetypes == null || updatetypes.size() == 0) {
		canAdd = false;
	}
%>
<html:html locale="true">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<title><bean:message key="pvxp.dev.add" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>
	<script language="JavaScript">
    
    
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


//为数字
function isNumber2(oNum) 
   { 

  if(!oNum) return 2; 
 // var strP=/^\d+(\.\d+)?$/; 可以为小数，但不保证输入的开头不是0
  var strP=/^(0|[1-9][0-9]*)$/;
  if(!strP.test(oNum)) return 2; 
  return 1; 
   }

      function validate() {
        if (document.all.devno.value == "") {
          alert("请输入设备编号。");
          document.all.devno.focus();
          return false;
        }

        if (document.all.devno.value.match(/\W/) != null) {
          alert("设备编号只能包含字母和数字。");
          document.all.devno.focus();
          return false;
        }

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



        //MAC校验
        /**
        if (document.all.devmac.value == "") {
        	alert("请输入MAC地址");
        	document.all.devmac.focus();
        	return false;
        } else if (CheckMac(document.all.devmac.value) == 2) {
        	alert("输入的MAC地址格式错误");
        	document.all.devmac.focus();
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
        
//电话号码校验
        if( document.all.organcontact.value=="" ) {
			alert( "请输入 联系电话" );
			document.all.organcontact.focus();
			return false;
		}
		 var tel=document.all.organcontact.value;
   			var strP= /(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{3,4}\-[0-9]{7,8}\-[0-9]{2,6}$)|(^[0-9]{7,8}$)|(^[0-9]{3,4}[0-9]{7,8}$)|(^0{0,1}1[0-9]{10}$)/;
  			if(!strP.test(tel)){
   			alert('输入的电话号码格式错误');
   			document.all.organcontact.focus();
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
				<font class="location"><bean:message key="pvxp.dev.add" /> </font>
			</td>
		</tr>
	</table>

	<%
	if (canAdd) {
	%>
	<table width="100%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<html:form action="/AddDev.do" focus="devno"
			onsubmit="javascript:parent.showit();">
			<html:hidden property="isShow" value="false" />
			<tr align="center">
				<td colspan="8" class="list_td_title">
					<b><bean:message key="pvxp.dev.add" /> </b>
				</td>
			</tr>
			<tr>
				<td width="8%" class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.devno" /> </b>
				</td>
				<td width="17%" class="list_td_detail">
					&nbsp;
					<html:text property="devno" size="15" maxlength="10" />
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
					<app:devtpselect property="typeno" showName="1" />
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
					<app:bankselect property="bankid" />
				</td>
			</tr>
			<tr>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.devaddr" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="devaddr" size="15" maxlength="30" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.dutyname" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="dutyname" size="15" maxlength="10" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.organcontact" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="organcontact" size="20" maxlength="20" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.sysid" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<app:updatetypeselect property="sysid" />
				</td>
			</tr>
			<!--<tr>
        <td class="list_td_prom">&nbsp;<nobr><b><bean:message key="prompt.devkey"/></b></nobr></td>
        <td class="list_td_detail">&nbsp;<html:text property="devkey" size="15" maxlength="16"/></td>
        <td class="list_td_prom">&nbsp;<b><bean:message key="prompt.pinkey"/></b></td>
        <td class="list_td_detail">&nbsp;<html:text property="pinkey" size="15" maxlength="16"/></td>
        <td class="list_td_prom">&nbsp;<b><bean:message key="prompt.mackey"/></b></td>
        <td class="list_td_detail">&nbsp;<html:text property="mackey" size="15" maxlength="16"/></td>
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
						<%--<html:option value="s">停用</html:option>--%>
						<html:option value="t">测试</html:option>
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
						<%--<html:option value="9">立刻轮询</html:option>--%>
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

	<p>
		<html:errors property="addDev" />
	</p>
	<%
	} else {
	%>
	<table width="100%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<tr>
			<td class="list_td_title">
				<b>错误</b>
			</td>
		</tr>
		<%
		if (banks == null || banks.size() == 0) {
		%>
		<tr class="list_tr0">
			<td align="center">
				没有机构，请先添加机构。
			</td>
		</tr>
		<%
				}

				if (devtypes == null || devtypes.size() == 0) {
		%>
		<tr class="list_tr0">
			<td align="center">
				没有设备类型，请先添加设备类型。
			</td>
		</tr>
	</table>
	<%
			}

			if (updatetypes == null || updatetypes.size() == 0) {
	%>
	<tr class="list_tr0">
		<td align="center">
			没有设备更新类型，请先添加设备更新类型。
		</td>
	</tr>
	</table>
	<%
		}
		}
	%>
</body>
</html:html>
