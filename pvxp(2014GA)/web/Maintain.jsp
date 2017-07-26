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
	<title>���޼�¼¼��</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
    <!--
	function validate() {
		flag = false;
		var devno = document.all.devno.value;
		if( devno.length == 0){
			alert("�������豸���");
			document.all.devno.focus();
			return false;
		}
		
		var trbdate = document.all.trbdate.value;
        	var trbtime = document.all.trbtime.value;
        	if(trbdate.length==0){
        		alert("�����뱨������");
        		document.all.trbdate.focus();
			return false;
		}else if(!isDate(trbdate)){
        		alert("��������ڸ�ʽ����ȷ��");
        		document.all.trbdate.focus();
			return false;
		}
		if(trbtime.length==0){
			alert("�����뱨��ʱ��");
			document.all.trbtime.focus();
			return false;
		}else if(!isTime(trbtime)){
        		alert("�����ʱ���ʽ����ȷ��");
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
					alert( "������ѡ��һ�����ϲ���" );
					return false;
				}else {
					flag = false;
				}
			}else {
				if( document.all.subdevice.checked == true ) {
					flag = true;
				}
				if( !flag ) {
					alert( "������ѡ��һ�����ϲ���" );
					document.all.subdevice(0).focus();
					return false;
				}else {
					flag = false;
				}
			}
		}else {
			alert( "������ѡ��һ�����ϲ���" );
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
					<font class="location">���޼�¼¼��</font>
				</td>
			</tr>
		</table>

		<table border="0" width="100%" border="1" cellspacing="1"
			cellpadding="2" class="list_table_border">
			<tr>
				<td class="list_td_title" style="font: bold" align="center">
					���޼�¼¼��
				</td>
			</tr>
			<tr>
				<td class="list_td_prom">
					���ڸ�ʽΪ
					<font color="red">MMMMYYDD</font>����
					<font color="red">20110214</font>��ʱ���ʽΪ
					<font color="red">HHMMSS</font>����
					<font color="red">120704</font>��Ĭ����ʾ��ǰʱ�䡣
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
					<b>�豸���</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="20" property="devno" maxlength="10" />
				</td>

				<td class="list_td_prom" align="center">
					&nbsp;
					<b>��������</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="30" property="trbphen" maxlength="100" />
				</td>
			</tr>

			<tr>
				<td class="list_td_prom" nowrap align="center">
					&nbsp;
					<b>��������</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="trbdate" size="20" maxlength="8"
						value="<%=tmpdate%>" />
					</font>
				</td>
				<td class="list_td_prom" nowrap align="center">
					&nbsp;
					<b>����ʱ��</b>
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
					<b>��&nbsp;��&nbsp;��</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="repairs" size="20" maxlength="20" />
				</td>

				<td class="list_td_prom" nowrap align="center">
					&nbsp;
					<b>���޻���</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<app:bankselect property="repbank" />
				</td>
			</tr>

			<tr>
				<td class="list_td_prom" nowrap align="center">
					&nbsp;
					<b>���ϲ���</b>
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
								û�п�ѡ����
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

