<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="1" />


<%%>
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
	var areaNo = document.all['areaNo'].value;
	var areaName = document.all['areaName'].value;
	if(!isNumber(trim(areaNo))){
		alert("地区编号应为数字型数据");
		document.all['areaNo'].focus();
		return false;
	} else if(trim(areaName)=""){
		alert("地区名称不能为空");
		document.all['areaName'].focus();
		return false;
	} else {
		return true;
	}
}
</script>
<body onload="javascript:parent.hidit();">
	<html:form action="/iniAddArea.do" onsubmit="return check()">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location">添加地区</font>
				</td>
			</tr>
		</table>


		<table width="100%" cellspacing="1" cellpadding="3"
			class="list_table_border">
			<!-- header -->
			<tr align="center">
				<td colspan="8" class="list_td_title">
					<b>请输入地区信息</b>
				</td>
			</tr>
			<!-- body -->
			<tr>
				<td width="20%" class="list_td_prom">
					<center>
						<b>地区编号：</b>
					</center>
				</td>
				<td width="30%" class="list_td_detail">
					&nbsp;
					<input id="areaNo" type="text" name="areaNo" value="" />
				</td>
				<td width="20%" class="list_td_prom">
					<center>
						<b>地区名称</b>
					</center>
				</td>
				<td width="30%" class="list_td_detail">
					&nbsp;
					<input id="areaName" type="text" name="areaName" value="" />
				</td>
			</tr>
			<tr align="left">
				<td colspan="8" class="list_td_title">
					<font color="red">注意：地区编号、科目号必须为数字，地区名称不能为空。</font>
				</td>
			</tr>
		</table>

		<table align="center" width="40%">
			<tr align="center">
				<td>
					<input type="image" src="images/default/bt_add.gif">
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
