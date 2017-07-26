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
function pattern1(s){
		var patternStr = /^[0-9]+\.[0-9]{2}$/;
		//var pattern = /^[A-Z]+$/;
		return patternStr.test(s);
	}
function pattern2(s){
		var patternStr = /^[0-9]+\.[0-9]{3}$/;
		//var pattern = /^[A-Z]+$/;
		return patternStr.test(s);
	}
function pattern3(s){
		var patternStr = /^[A-Z]+$/;
		return patternStr.test(s);
	}
function check(){
	var card = document.all['card'].value;
	var cardType = document.all['cardType'].value;
	var value1 = document.all['outRate'].value;
	var value2 = document.all['outRateLow'].value;
	var value3 = document.all['outRateUp'].value;
	var value4 = document.all['inRate'].value;
	var value5 = document.all['inRateLow'].value;
	var value6 = document.all['inRateUp'].value;
	if(!isNumber(trim(card))){
		alert("卡类型标识应为数字型数据");
		document.all['card'].focus();
		return false;
	} else if(!pattern3(trim(cardType))){
		alert("卡类型应为大写英文字母");
		document.all['cardType'].focus();
		return false;
	} else if(!pattern2(trim(value1))){
		alert("请您输入正确格式的转出手续费费率，填写格式为\"0.005\"");
		document.all['outRate'].focus();
		return false;
	} else if(!pattern1(trim(value2))){
		alert("请您输入正确格式的转出手续费下限，填写格式为\"1.00\"");
		document.all['outRateLow'].focus();
		return false;
	} else if(!pattern1(trim(value3))){
		alert("请您输入正确格式的转出手续费上限，填写格式为\"1.00\"");
		document.all['outRateUp'].focus();
		return false;
	} else if(!pattern2(trim(value4))){
		alert("请您输入正确格式的转入手续费费率，填写格式为\"0.005\"");
		document.all['inRate'].focus();
		return false;
	} else if(!pattern1(trim(value5))){
		alert("请您输入正确格式的转入手续费下限，填写格式为\"1.00\"");
		document.all['inRateLow'].focus();
		return false;
	} else if(!pattern1(trim(value6))){
		alert("请您输入正确格式的转入手续费上限，填写格式为\"1.00\"");
		document.all['inRateUp'].focus();
		return false;
	} else {
		return true;
	}
}
</script>
<body onload="javascript:parent.hidit();">
	<html:form action="/specialCardAdd.do" onsubmit="return check()">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location">修改特殊卡类型信息</font>
				</td>
			</tr>
		</table>


		<table width="100%" cellspacing="1" cellpadding="3"
			class="list_table_border">
			<!-- header -->
			<tr align="center">
				<td colspan="8" class="list_td_title">
					<b>请输入特殊卡类型信息</b>
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
				<td width="10%" class="list_td_prom">
					<center>
						<b>转出手续费费率</b>
					</center>
				</td>
				<td width="24%" class="list_td_detail">
					&nbsp;
					<input id="outRate" type="text" name="outRate" value="0.000" />
				</td>
			</tr>
			<tr>
				<td width="10%" class="list_td_prom">
					<center>
						<b>转出手续费下限</b>
					</center>
				</td>
				<td width="23%" class="list_td_detail">
					&nbsp;
					<input id="outRateLow" type="text" name="outRateLow" value="0.00" />
				</td>
				<td width="10%" class="list_td_prom">
					<center>
						<b>转出手续费上限</b>
					</center>
				</td>
				<td width="23%" class="list_td_detail">
					&nbsp;
					<input id="outRateUp" type="text" name="outRateUp" value="0.00" />
				</td>
				<td width="10%" class="list_td_prom">
					<center>
						<b>转入手续费费率</b>
					</center>
				</td>
				<td width="24%" class="list_td_detail">
					&nbsp;
					<input id="inRate" type="text" name="inRate" value="0.000" />
				</td>
			</tr>
			<tr>
				<td width="10%" class="list_td_prom">
					<center>
						<b>转入手续费下限</b>
					</center>
				</td>
				<td width="23%" class="list_td_detail">
					&nbsp;
					<input id="inRateLow" type="text" name="inRateLow" value="0.00" />
				</td>
				<td width="10%" class="list_td_prom">
					<center>
						<b>转入手续费上限</b>
					</center>
				</td>
				<td width="23%" class="list_td_detail">
					&nbsp;
					<input id="inRateUp" type="text" name="inRateUp" value="0.00" />
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
					<font color="red">注意：卡类型标识必须为数字，卡类型必须为英文大写字母，费率的输入格式为"0.005"，手续费的输入格式为"1.00"，请您正确填写</font>
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
