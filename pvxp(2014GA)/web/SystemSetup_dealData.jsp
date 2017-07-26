<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie />
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="13" minpower="3" />


<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.bean.*"%>
<html:html locale="true">
<head>
<title></title>
<link href="style/pvxp.css" rel="stylesheet" type="text/css">
<html:base />
</head>
<body onload="javascript:parent.hidit();">
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" valign="middle" width="30" height="40"><img
				src="images/default/nav.gif"></td>
			<td><font color=blue>PowerView XP </font>--- <font
				class="location"><bean:message key="pvxp.syssetup.title" />
			</font>--- <font class="location"><bean:message
						key="pvxp.syssetup.getdata.title" /> </font></td>
		</tr>
	</table>


	<%
		PubUtil myPubUtil = new PubUtil();
			SysParam tmpSysParam = SysParamBean.getSysParam();
			String statusoftmptable = "F"; //交易历史明细表状态 F-可用 B-正在导入
			String monthsoftmptable = ""; //交易历史明细表中数据情况(如：200411,200412,200502)
			String tmpdatastart = ""; //现有的历史数据起始年月
			if (tmpSysParam != null) {
				statusoftmptable = myPubUtil.dealNull(tmpSysParam.getStatusoftmptable()).trim();
				monthsoftmptable = myPubUtil.dealNull(tmpSysParam.getMonthsoftmptable()).trim();
				tmpdatastart = myPubUtil.dealNull(tmpSysParam.getTmpdatastart()).trim();
			}
			//statusoftmptable = "B";
			String datarangestr = "";
			String tmpdataend = "";
			//tmpdatastart = "";
			String startdatastr = "";
			if (tmpdatastart.length() > 0) {
				tmpdataend = myPubUtil.getBeforePreMonth();

				try {
					if (Integer.parseInt(tmpdatastart) >= Integer.parseInt(tmpdataend)) {
						datarangestr = "当前保留的数据文件年月为：</font><font color=red><b><u>" + tmpdataend + "</u></b></font>";
						tmpdatastart = tmpdataend;
					} else {
						datarangestr = "当前保留的数据文件年月为：</font><font color=red><b><u>" + tmpdatastart + " - " + tmpdataend + "</u></b></font>";
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				startdatastr = tmpdatastart;
			} else {
				datarangestr = "当前暂时没有保留的数据文件</font>";
				startdatastr = "999999";
			}
	%>


	<!------显示表单开始------->
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_td_title">
			<td height="29"><b>历史交易明细导入&nbsp;&nbsp;&nbsp;</b></td>
			<td width="450">&nbsp;&nbsp;&nbsp;&nbsp; <html:errors
					property="getdata" /></td>
		</tr>


		<tr class="list_tr0">
			<td colspan="2">
				根据交易明细数据设置中设置的保留月份，过期的数据将被导成文件形式。在这里可以将这些文件导入交易历史明细表，做为交易查询临时使用的数据。
				<%
				if (statusoftmptable.equals("B")) {
			%> <font color=red><b>系统正在进行处理，请等结束后再导入。</b> </font> <%
 	} else {
 %> 请输入若干导入年月，用逗号分割，如： <font color=blue>
					200411,200501,200502,200504</font> 的格式，最多输入 <b><font color=red>12</font>
			</b>个年月。 <br> <font color=blue>#@ <%
 	if (!monthsoftmptable.equals("")) {
 %>当前交易历史明细表中的数据为：</font><font color=red><b><u><%=monthsoftmptable%>
					</u> </b> </font> <%
 	} else {
 %> 当前交易历史明细表中没有数据 <%
 	}
 %> </font> <br> <font color=blue>#@ <%=datarangestr%> <br> <font
					color=red> 导入数据时将删除交易历史明细表中原有的临时数据！</font> <%
 	}
 %>
			
			</td>
		</tr>

		<%
			if (!statusoftmptable.equals("B")) {
		%>
		<tr class="list_tr1">
			<html:form action="/SystemGetHistoryData.do"
				onsubmit="javascript:parent.showit();" styleId="getdata_form">
				<td colspan="2">
					<table width="100%" border="0">
						<tr>
							<td width="120"><html:hidden property="tmpdatastart"  value="<%=startdatastr%>" /> 导入数据年月：</td>
							<td>&nbsp; <html:text property="months" size="10" /> </td>
							<td align="right">
							<input type="image" src="images/default/bt_ok.gif" onFocus="this.blur()">
								&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
			</html:form>
		</tr>
		<%
			}
		%>
	</table>
	<br>
	<!------显示表单结束------->

	<!------显示表单开始------->
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_td_title">
			<td height="29"><b>历史数据文件删除&nbsp;&nbsp;&nbsp;</b></td>
			<td width="430">&nbsp;&nbsp;&nbsp;&nbsp; <html:errors
					property="deletedata" /></td>
		</tr>
		<tr class="list_tr0">
			<td colspan="2">导成文件形式的历史交易明细也可以被删除。输入终止年月如： <font color=blue>200412</font>，系统将删除该月之前（包括该月）的所有存档文件。
				<font color=red><b><u>删除后数据不可恢复，请慎重执行。</u> </b> </font> <%
 	if (statusoftmptable.equals("B")) {
 %> <font color=red><b>系统正在进行处理，请等结束后再操作。</b> </font> <%
 	} else {
 %> <br> <font color=blue>#@ <%=datarangestr%> <%
 	}
 %>
			
			</td>
		</tr>

		<%
			if (!statusoftmptable.equals("B")) {
		%>
		<tr class="list_tr1">
			<html:form action="/SystemDelHistoryData.do"
				onsubmit="javascript:parent.showit();" styleId="deldata_form">
				<td colspan="2">
					<table width="100%" border="0">
						<tr>
							<td width="120"><html:hidden property="tmpdatastart" value="<%=startdatastr%>" /> 删除文件终止年月：</td>
							<td>&nbsp; <html:text property="months" size="10" maxlength="6" />
							</td>
							<td align="right"><input type="image" 	src="images/default/bt_ok.gif" onFocus="this.blur()">
								&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table></td>
			</html:form>
		</tr>
		<%
			}
		%>
	</table>
	<br>
	<!------显示表单结束------->

</body>
</html:html>
