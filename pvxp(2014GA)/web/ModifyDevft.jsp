<%@ page import="com.lcjr.pvxp.actionform.*"%>
<%@ page import="com.lcjr.pvxp.util.*"%>
<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="1" />

<%
	CharSet charSet = new CharSet();
	ModifyDevftForm devft = (ModifyDevftForm) request.getAttribute("ModifyDevftForm");
	
	devft.setDevftName(charSet.form2GB(devft.getDevftName().trim()));
	devft.setContact1(charSet.form2GB(devft.getContact1().trim()));
	devft.setPhone1(charSet.form2GB(devft.getPhone1().trim()));
	devft.setMobile1(charSet.form2GB(devft.getMobile1().trim()));
	devft.setContact2(charSet.form2GB(devft.getContact2().trim()));
	devft.setPhone2(charSet.form2GB(devft.getPhone2().trim()));
	devft.setMobile2(charSet.form2GB(devft.getMobile2().trim()));
	devft.setAddr(charSet.form2GB(devft.getAddr().trim()));
%>

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.devft.modify" /></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">

	<script language="JavaScript">
    <!--

	function validate() {
	
		if (document.all.devftName.value == "") {
			alert("请输入设备厂商名称。");
			document.all.devftName.focus();
			return false;
	        }

	   	if (document.all.addr.value == "") {
			alert("请输入设备厂商地址。");
			document.all.devftName.focus();
			return false;
	        }

//联系人1   姓名  校验
	   	if (document.all.contact1.value == "") {
			alert("请输入联系人（一）。");
			document.all.contact1.focus();
			return false;
	        }

//联系人1   固定电话 校验
	   	if (!document.all.phone1.value == "") {
	   		var phone1=document.all.phone1.value;
			if(CheckTelPhone(phone1)==1){
				alert("输入的固定电话号码格式有误。");
				document.all.phone1.focus();
				return false;
			}
			}


//联系人1   移动电话  校验
	   	if (!document.all.mobile1.value == "") {
	   		var phone1=document.all.mobile1.value;
			if(CheckTelPhone(phone1)==1){
				alert("输入的固定电话号码格式有误。");
				document.all.mobile1.focus();
				return false;
			}
			}
			
			
//联系人2   固定电话 校验
	   	if (!document.all.phone2.value == "") {
	   		var phone2=document.all.phone2.value;
			if(CheckTelPhone(phone2)==1){
				alert("输入的固定电话号码格式有误。");
				document.all.phone2.focus();
				return false;
			}
			}


//联系人2   移动电话  校验
	   	if (!document.all.mobile2.value == "") {
	   		var mobile2=document.all.mobile2.value;
			if(CheckTelPhone(mobile2)==1){
				alert("输入的固定电话号码格式有误。");
				document.all.mobile2.focus();
				return false;
			}
			}

	        return true;
	}
	
	
	
//电话号码校验  通过校验返回0  没有通过校验返回1
 function CheckTelPhone(tel)  
{
	var strP= /(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{3,4}\-[0-9]{7,8}\-[0-9]{2,6}$)|(^[0-9]{7,8}$)|(^[0-9]{3,4}[0-9]{7,8}$)|(^0{0,1}1[0-9]{10}$)|(-)/;
	if(!strP.test(tel)){
		return 1; 
     }else{
     return 0; 
     }
}

    //-->
    </script>
</head>
<body onload="javascript:parent.hidit();">
	<html:form action="/ModifyDevft.do">
		<html:hidden property="modify" value="false" />
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location"><bean:message key="pvxp.devft.modify" />
					</font>
				</td>
			</tr>
		</table>

		<table width="100%" cellspacing="1" cellpadding="3"
			class="list_table_border">
			<!-- header -->
			<tr align="center">
				<td colspan="6" class="list_td_title">
					<b><bean:message key="pvxp.devft.modify" /> </b>
				</td>
			</tr>
			<!-- body -->
			<tr>
				<td class="list_td_prom" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devft.devftno" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<bean:write name="ModifyDevftForm" property="devftNo" />
					<html:hidden property="devftNo" />
				</td>

				<td class="list_td_prom" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devft.devftname" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="20" property="devftName" maxlength="50" />
				</td>
				<td class="list_td_prom" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devft.addr" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="20" property="addr" maxlength="100" />
				</td>
			</tr>
			<tr>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="pvxp.devft.contact1" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="15" property="contact1" maxlength="10" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="pvxp.devft.phone" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="20" property="phone1" maxlength="20" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="pvxp.devft.mobile" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="20" property="mobile1" maxlength="20" />
				</td>
			</tr>
			<tr>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="pvxp.devft.contact2" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="15" property="contact2" maxlength="10" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="pvxp.devft.phone" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="20" property="phone2" maxlength="20" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="pvxp.devft.mobile" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="20" property="mobile2" maxlength="20" />
				</td>
			</tr>
		</table>

		<!-- footer -->
		<p>
		<table align="center" width="40%">
			<tr align="center">
				<td>
					<input type="image" src="images/default/bt_ok.gif"
						onClick="return validate()">
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
		</table>
	</html:form>

</body>
</html:html>
