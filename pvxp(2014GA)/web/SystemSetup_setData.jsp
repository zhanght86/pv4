<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="13" minpower="3" />


<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
	<script language="JavaScript">
function Checkform(){
	try{
		if( document.all.kmofmxb.value<=0 ){
			alert("明细保留月数必须大于0");
			document.all.kmofmxb.focus();
			return false;
		}
		if( document.all.kmofjytjday.value<=0 ){
			alert("交易日汇总保留月数必须大于0");
			document.all.kmofjytjday.focus();
			return false;
		}
		if( document.all.kmofjytjmonth.value<=0 ){
			alert("交易月汇总保留年数必须大于0");
			document.all.kmofjytjmonth.focus();
			return false;
		}
		if( document.all.kmofjytjyear.value<=0 ){
			alert("交易年汇总保留年数必须大于0");
			document.all.kmofjytjyear.focus();
			return false;
		}
		
		
		
		
		if( document.all.kmofsbtjday.value<=0 ){
			alert("设备故障日汇总保留月数必须大于0");
			document.all.kmofsbtjday.focus();
			return false;
		}
		if( document.all.kmofsbtjmonth.value<=0 ){
			alert("交易年汇总保留年数必须大于0");
			document.all.kmofsbtjmonth.focus();
			return false;
		}
		if( document.all.kmofsbtjyear.value<=0 ){
			alert("交易年汇总保留年数必须大于0");
			document.all.kmofsbtjyear.focus();
			return false;
		}
		
		
		
		
		return true;
		parent.showit();
	}catch(e){
		alert("请正确输入设置数值");
		return false;
	}
}
</script>
</head>
<body onload="javascript:parent.hidit();">
	<%
		//PubUtil myPubUtil = new PubUtil();
		//String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
	%>
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" valign="middle" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP </font>---
				<font class="location"><bean:message
						key="pvxp.syssetup.title" /> </font>---
				<font class="location"><bean:message
						key="pvxp.syssetup.setdata" /> </font>
			</td>
		</tr>
	</table>
	<!------显示表单开始------->
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_td_title">
			<td height="29">
				<b><bean:message key="pvxp.syssetup.setdata.trcdlog" /> </b>
			</td>
			<td colspan="2">
				&nbsp;&nbsp;
				<html:errors property="kmofmxb" />
			</td>
		</tr>
		<tr class="list_tr0">
			<td colspan="3">
				<bean:message key="pvxp.syssetup.setdata.trcdlog.readme" />
			</td>
		</tr>
		<tr class="list_tr1">
			<html:form action="/SystemSetData.do" method="post"
				onsubmit="return Checkform();" styleId="setDataForm">
				<td width="33.3333%">
					<bean:message key="pvxp.syssetup.setdata.trcdlog.keepmonth" />
					&nbsp;
					<html:text property="kmofmxb" size="10" maxlength="4" />
				</td>
				<td width="33.3333%">
					&nbsp;
				</td>
				<td width="33.3333%">
					&nbsp;
				</td>
		</tr>
	</table>
	<br>

	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_td_title">
			<td height="29">
				<b><bean:message key="pvxp.syssetup.setdata.trcdsta" /> </b>
			</td>
			<td colspan="2">
				&nbsp;&nbsp;
				<html:errors property="kmofjytj" />
			</td>
		</tr>
		<tr class="list_tr0">
			<td colspan="3">
				<bean:message key="pvxp.syssetup.setdata.trcdsta.readme" />
			</td>
		</tr>
		<tr class="list_tr1">
			<td>
				<bean:message key="pvxp.syssetup.setdata.daysta.keepmonth" />
				&nbsp;
				<html:text property="kmofjytjday" size="10" maxlength="4" />
			</td>
			<td>
				<bean:message key="pvxp.syssetup.setdata.monthsta.keepyear" />
				&nbsp;
				<html:text property="kmofjytjmonth" size="10" maxlength="4" />
			</td>
			<td>
				<bean:message key="pvxp.syssetup.setdata.yearsta.keepyear" />
				&nbsp;
				<html:text property="kmofjytjyear" size="10" maxlength="4" />
			</td>
		</tr>
	</table>
	<br>

	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_td_title">
			<td height="29">
				<b><bean:message key="pvxp.syssetup.setdata.deverrsta" /> </b>
			</td>
			<td colspan="2">
				&nbsp;&nbsp;
				<html:errors property="kmofsbtj" />
			</td>
		</tr>
		<tr class="list_tr0">
			<td colspan="3">
				<bean:message key="pvxp.syssetup.setdata.deverrsta.readme" />
			</td>
		</tr>
		<tr class="list_tr1">
			<td>
				<bean:message key="pvxp.syssetup.setdata.daysta.keepmonth" />
				&nbsp;
				<html:text property="kmofsbtjday" size="10" maxlength="4" />
			</td>
			<td>
				<bean:message key="pvxp.syssetup.setdata.monthsta.keepyear" />
				&nbsp;
				<html:text property="kmofsbtjmonth" size="10" maxlength="4" />
			</td>
			<td>
				<bean:message key="pvxp.syssetup.setdata.yearsta.keepyear" />
				&nbsp;
				<html:text property="kmofsbtjyear" size="10" maxlength="4" />
			</td>
		</tr>
	</table>
	<br>

	<table width="100%" cellspacing="1" cellpadding="3" border="0">
		<tr>
			<td align="center">
				<input type="image" src="images/default/bt_ok.gif"
					onFocus="this.blur()">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" onClick="document.all['setDataForm'].reset()"
					onFocus="this.blur()"><img src="images/default/bt_reset.gif"
						border="0"> </a>
			</td>
		</tr>
	</table>
	<br>

	<table width="100%" cellspacing="1" cellpadding="3" border="0">
		<tr>
			<td align="center">
				<html:errors property="result" />
			</td>
		</tr>
	</table>
	</html:form>

	<!------显示表单结束------->

</body>
</html:html>
