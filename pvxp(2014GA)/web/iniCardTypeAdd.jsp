<%@include file="inc/taglib.jsp"%>
<%@page contentType="text/html;charset=gb2312"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="1" />

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.dev.modify" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>
</head>
<script type="text/javascript">
function pattern3(s){
		var patternStr = /^[A-Z]+$/;
		return patternStr.test(s);
	}
function check(){
	var card = document.all['card'].value;
	var cardType = document.all['cardType'].value;
	var subjectNo = document.all['subjectNo'].value;
	if(!isNumber(trim(card))){
		alert("卡类型标识应为数字型数据");
		document.all['card'].focus();
		return false;
	} else if(!pattern3(trim(cardType))){
		alert("卡类型应为大写英文字母");
		document.all['cardType'].focus();
		return false;
	} else if(!isNumber(trim(subjectNo))){
		alert("科目号应为数字型数据");
		document.all['subjectNo'].focus();
		return false;
	} else {
		return true;
	}
}
</script>
<body onload="javascript:parent.hidit();">
	<html:form action="/iniCardTypeAdd.do" onsubmit="return check()">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location">添加卡类型信息</font>
				</td>
			</tr>
		</table>


		<table width="100%" cellspacing="1" cellpadding="3"
			class="list_table_border">
			<!-- header -->
			<tr align="center">
				<td colspan="8" class="list_td_title">
					<b>请输入卡类型信息</b>
				</td>
			</tr>
			<!-- body -->
			<tr>
				<td width="10%" class="list_td_prom">
					<center>
						<b>卡类型标识</b>
					</center>
				</td>
				<td width="23%" class="list_td_detail">
					&nbsp;
					<input id="card" type="text" name="card" value="" />
				</td>
				<td width="10%" class="list_td_prom">
					<center>
						<b>卡类型</b>
					</center>
				</td>
				<td width="23%" class="list_td_detail">
					&nbsp;
					<input id="cardType" type="text" name="cardType" value="" />
				</td>
			</tr>
			<tr>
				<td width="15%" class="list_td_prom">
					<center>
						<b>科目号</b>
					</center>
				</td>
				<td width="19%" class="list_td_detail">
					&nbsp;
					<input id="subjectNo" type="text" name="subjectNo" value="" />
				</td>
				<td width="10%" class="list_td_prom">
					<center>
						<b>说明</b>
					</center>
				</td>
				<td width="24%" class="list_td_detail">
					&nbsp;
					<input id="remark" type="text" name="remark" value=" " />
				</td>
			</tr>
			<tr align="left">
				<td colspan="8" class="list_td_title">
					<font color="red">注意：卡类型标识必须为数字，卡类型必须为英文大写字母</font>
				</td>
			</tr>
		</table>

		<table align="center" width="40%">
			<tr align="center">
				<td>
					<input type="image" src="images/default/bt_ok.gif">
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
