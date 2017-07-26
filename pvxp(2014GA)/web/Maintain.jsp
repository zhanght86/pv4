<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="2" />

<%@page import="com.lcjr.pvxp.util.*"%>
<%
	PubUtil util = new PubUtil();
	String tmpdate = util.getNowDate(1);
	String tmptime = util.getNowTime2();
%>

<html:html locale="true">
<head>
	<title>报修记录录入</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
    <!--
	function validate() {
		flag = false;
		var devno = document.all.devno.value;
		if( devno.length == 0){
			alert("请输入设备编号");
			document.all.devno.focus();
			return false;
		}
		
		var trbdate = document.all.trbdate.value;
        	var trbtime = document.all.trbtime.value;
        	if(trbdate.length==0){
        		alert("请输入报修日期");
        		document.all.trbdate.focus();
			return false;
		}else if(!isDate(trbdate)){
        		alert("输入的日期格式不正确。");
        		document.all.trbdate.focus();
			return false;
		}
		if(trbtime.length==0){
			alert("请输入报修时间");
			document.all.trbtime.focus();
			return false;
		}else if(!isTime(trbtime)){
        		alert("输入的时间格式不正确。");
        		document.all.trbtime.focus();
			return false;
		}
		
		
		
		if( document.all.subdevice != null ) {
			if( document.all.subdevice.length != undefined ) {
				for( i=0; i<document.all.subdevice.length; i++ ) {
					if( document.all.subdevice(i).checked == true ) {
						flag = true;
					}
				}
				if( !flag ) {
					alert( "请至少选择一个故障部件" );
					return false;
				}else {
					flag = false;
				}
			}else {
				if( document.all.subdevice.checked == true ) {
					flag = true;
				}
				if( !flag ) {
					alert( "请至少选择一个故障部件" );
					document.all.subdevice(0).focus();
					return false;
				}else {
					flag = false;
				}
			}
		}else {
			alert( "请至少选择一个故障部件" );
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

	<html:form action="/AddMaintain" focus="devno"
		onsubmit="javascript:parent.showit();">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location">报修记录录入</font>
				</td>
			</tr>
		</table>

		<table border="0" width="100%" border="1" cellspacing="1"
			cellpadding="2" class="list_table_border">
			<tr>
				<td class="list_td_title" style="font: bold" align="center">
					报修记录录入
				</td>
			</tr>
			<tr>
				<td class="list_td_prom">
					日期格式为
					<font color="red">MMMMYYDD</font>，如
					<font color="red">20110214</font>，时间格式为
					<font color="red">HHMMSS</font>，如
					<font color="red">120704</font>，默认显示当前时间。
				</td>
			</tr>
		</table>

		<p></p>


		<table width="100%" cellspacing="1" cellpadding="3"
			class="list_table_border">
			<!-- header -->

			<!-- body -->
			<tr>
				<td class="list_td_prom" align="center">
					&nbsp;
					<b>设备编号</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="20" property="devno" maxlength="10" />
				</td>

				<td class="list_td_prom" align="center">
					&nbsp;
					<b>故障现象</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="30" property="trbphen" maxlength="100" />
				</td>
			</tr>

			<tr>
				<td class="list_td_prom" nowrap align="center">
					&nbsp;
					<b>报修日期</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="trbdate" size="20" maxlength="8"
						value="<%=tmpdate%>" />
					</font>
				</td>
				<td class="list_td_prom" nowrap align="center">
					&nbsp;
					<b>报修时间</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="30" property="trbtime" maxlength="10"
						value="<%=tmptime%>" />
					</font>
				</td>

			</tr>



			<tr>
				<td class="list_td_prom" nowrap align="center">
					&nbsp;
					<b>报&nbsp;修&nbsp;人</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="repairs" size="20" maxlength="20" />
				</td>

				<td class="list_td_prom" nowrap align="center">
					&nbsp;
					<b>报修机构</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<app:bankselect property="repbank" />
				</td>
			</tr>

			<tr>
				<td class="list_td_prom" nowrap align="center">
					&nbsp;
					<b>故障部件</b>
				</td>
				<td class="list_td_detail" colspan="3">
					&nbsp;
					<table>
						<%
							DevErrorsUtil myDevErrorsUtil = new DevErrorsUtil();
							String[][] tmpArray = myDevErrorsUtil.getSubDevice();
							if (tmpArray == null || tmpArray.length == 0) {
						%>
						<tr>
							<td colspan="5" align="center">
								没有可选对象
							</td>
						</tr>
						<%
								} else {
								int devicenum = tmpArray.length;
								int subtrnum = devicenum / 5;
								if (devicenum % 5 > 0)
									subtrnum++;
								for (int i = 0; i < subtrnum; i++) {
						%>
						<tr>
							<%
									for (int j = 0; j < 5; j++) {
									int thisnum = i * 5 + j;
									if (thisnum < devicenum) {
							%>
							<td width="20%">
								&nbsp;
								<input name="subdevice" id="subdevice" type="checkbox"
									value="<%=tmpArray[thisnum][0]%>">
								&nbsp;
								<%=tmpArray[thisnum][1]%>
							</td>
							<%
							} else {
							%>
							<td width="20%">
								&nbsp;
							</td>

							<%
										}
										}
							%>
						</tr>
						<%
							}
							}
						%>

					</table>
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

