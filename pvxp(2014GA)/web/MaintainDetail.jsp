<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="1" />

<%
	String[] maintainDetail = (String[]) request.getAttribute(Constants.REQUEST_MAINTAIN);
	CharSet charSet = new CharSet();
	PubUtil util = new PubUtil();
	
	int power = new OperatorModel().getPower(0, request);
	boolean hasPower = power == 2 || power == 3;
	
	String part = maintainDetail[2].trim();
	DevErrorsUtil myDevErrorsUtil = new DevErrorsUtil();
	String[][] tmpArray = myDevErrorsUtil.getSubDevice();
	int devicenum = tmpArray.length;
	for (int j = 0; j < devicenum; j++) {
		if (part.equals(tmpArray[j][0].trim())) {
			part = tmpArray[j][1];
		}
	}
	
	String tmpdate = util.getNowDate(1);
	String tmptime = util.getNowTime2();
	
	String obvidate = maintainDetail[9].trim();
	String obvitime = maintainDetail[10].trim();
	
	if (obvidate.equals("")) {
		obvidate = tmpdate;
	}
	if (obvitime.equals("")) {
		obvitime = tmptime;
	}
%>
<html:html locale="true">
<head>
	<title>报修记录</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
    <!--
	function validate() {
		
		
		var obvidate = document.all.obvidate.value;
        	var obvitime = document.all.obvitime.value;
        	if(obvidate.length>0 && !isDate(obvidate)){
        		alert("输入的日期格式不正确。");
        		document.all.obvidate.focus();
			return false;
		}
		if(obvitime.length>0 && !isTime(obvitime)){
        		alert("输入的时间格式不正确。");
        		document.all.obvitime.focus();
			return false;
		}
        
		return true;
	}
	
	function isDate(date) {
		if (date.length != 8) {
			return false;
		}

		if (date.match(/\D/) != null) {
			return false;
		}

		var m = date.substr(4, 2);
		var d = date.substr(6, 2);

		if (m > 12) {
			return false;
		}

		if (d > 31) {
			return false;
		}

		return true;
	}
		
	function isTime(time) {
		if (time.length != 6) {
			return false;
		}

		if (time.match(/\D/) != null) {
			return false;
		}

		var h = time.substr(0, 2);
		var m = time.substr(2, 2);
		var s = time.substr(4, 2);

		if (h > 23) {
			return false;
		}

		if (m > 59) {
			return false;
		}
		
		if (s > 59) {
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
			<td align="left" valign="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP</font> ---
				<font class="location">报修记录</font>
			</td>
		</tr>
	</table>

	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<html:form action="ModifyMaintain.do" method="post"
			onsubmit="javascript:return validate();">
			<!-- header -->
			<tr align="center">
				<td colspan="4" class="list_td_title">
					<b>报修记录信息</b>
				</td>
			</tr>
			<!-- body -->
			<tr align="center">
				<td class="list_td_prom">
					<b>设备编号</b>
				</td>
				<td class="list_td_detail">
					<%=maintainDetail[0]%>
				</td>
				<td class="list_td_prom">
					<b>所属机构</b>
				</td>
				<td class="list_td_detail">
					<%=charSet.db2web(maintainDetail[1])%>
				</td>
			</tr>
			<tr align="center">

				<td class="list_td_prom">
					<b>故障部件</b>
				</td>
				<td class="list_td_detail">
					<%=part%>
				</td>
				<td class="list_td_prom">
					<b>故障现象</b>
				</td>
				<td class="list_td_detail">
					<%=charSet.db2web(maintainDetail[7])%>
				</td>

			</tr>
			<tr align="center">
				<td class="list_td_prom">
					<b>报修日期</b>
				</td>
				<td class="list_td_detail">
					<%=maintainDetail[3]%>
				</td>
				<td class="list_td_prom">
					<b>报修时间</b>
				</td>
				<td class="list_td_detail">
					<%=maintainDetail[4]%>
				</td>
			</tr>
			<tr align="center">

				<td class="list_td_prom">
					<b>报&nbsp;修&nbsp;人</b>
				</td>
				<td class="list_td_detail">
					<%=charSet.db2web(maintainDetail[6])%>
				</td>

				<html:hidden property="devno" value="<%=maintainDetail[0]%>" />
				<html:hidden property="trbtype" value="<%=maintainDetail[2]%>" />
				<html:hidden property="trbdate" value="<%=maintainDetail[3]%>" />
				<html:hidden property="trbtime" value="<%=maintainDetail[4]%>" />

				<td class="list_td_prom">
					<b>处理类型</b>
				</td>
				<td class="list_td_detail">
					<select name="state" id="state">
						<%
						if (maintainDetail[5].trim().equals("1")) {
						%>
						<option value="0">
							未处理
						</option>
						<option value="1" selected>
							已处理
						</option>

						<%
						} else {
						%>
						<option value="0" selected>
							未处理
						</option>
						<option value="1">
							已处理
						</option>
						<%
						}
						%>
					</select>
				</td>
			</tr>
			<tr align="center">
				<td class="list_td_prom">
					<b>维&nbsp;护&nbsp;人</b>
				</td>
				<td class="list_td_detail">
					<html:text property="dutyno" size="20" maxlength="50"
						value="<%=charSet.db2web(maintainDetail[8].trim())%>" />
				</td>


				<td class="list_td_prom">
					<b>故障原因</b>
				</td>
				<td class="list_td_detail">
					<html:text property="remark" size="20" maxlength="100"
						value="<%=charSet.db2web(maintainDetail[11].trim())%>" />
				</td>
			</tr>
			<tr align="center">
				<td class="list_td_prom">
					<b>故障排除日期</b>
				</td>
				<td class="list_td_detail">
					<html:text property="obvidate" size="20" maxlength="8"
						value="<%=obvidate%>" />
				</td>
				<td class="list_td_prom">
					<b>故障排除时间</b>
				</td>
				<td class="list_td_detail">
					<html:text property="obvitime" size="20" maxlength="6"
						value="<%=obvitime%>" />
				</td>

			</tr>
	</table>




	<!-- footer -->
	<p>
	<table align="center" width="40%">
		<tr align="center">
			<%
			if (hasPower) {
			%>
			<td>
				<input type="image" src="images/default/bt_modify.gif">
			</td>
			<%
			}
			%>


			</html:form>

			<td>
				<img src="images/default/bt_back.gif" onClick="history.back()"
					style="cursor:hand">
			</td>
		</tr>
	</table>

</body>
</html:html>
