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
			String statusoftmptable = "F"; //������ʷ��ϸ��״̬ F-���� B-���ڵ���
			String monthsoftmptable = ""; //������ʷ��ϸ�����������(�磺200411,200412,200502)
			String tmpdatastart = ""; //���е���ʷ������ʼ����
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
						datarangestr = "��ǰ�����������ļ�����Ϊ��</font><font color=red><b><u>" + tmpdataend + "</u></b></font>";
						tmpdatastart = tmpdataend;
					} else {
						datarangestr = "��ǰ�����������ļ�����Ϊ��</font><font color=red><b><u>" + tmpdatastart + " - " + tmpdataend + "</u></b></font>";
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				startdatastr = tmpdatastart;
			} else {
				datarangestr = "��ǰ��ʱû�б����������ļ�</font>";
				startdatastr = "999999";
			}
	%>


	<!------��ʾ����ʼ------->
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_td_title">
			<td height="29"><b>��ʷ������ϸ����&nbsp;&nbsp;&nbsp;</b></td>
			<td width="450">&nbsp;&nbsp;&nbsp;&nbsp; <html:errors
					property="getdata" /></td>
		</tr>


		<tr class="list_tr0">
			<td colspan="2">
				���ݽ�����ϸ�������������õı����·ݣ����ڵ����ݽ��������ļ���ʽ����������Խ���Щ�ļ����뽻����ʷ��ϸ����Ϊ���ײ�ѯ��ʱʹ�õ����ݡ�
				<%
				if (statusoftmptable.equals("B")) {
			%> <font color=red><b>ϵͳ���ڽ��д�����Ƚ������ٵ��롣</b> </font> <%
 	} else {
 %> ���������ɵ������£��ö��ŷָ�磺 <font color=blue>
					200411,200501,200502,200504</font> �ĸ�ʽ��������� <b><font color=red>12</font>
			</b>�����¡� <br> <font color=blue>#@ <%
 	if (!monthsoftmptable.equals("")) {
 %>��ǰ������ʷ��ϸ���е�����Ϊ��</font><font color=red><b><u><%=monthsoftmptable%>
					</u> </b> </font> <%
 	} else {
 %> ��ǰ������ʷ��ϸ����û������ <%
 	}
 %> </font> <br> <font color=blue>#@ <%=datarangestr%> <br> <font
					color=red> ��������ʱ��ɾ��������ʷ��ϸ����ԭ�е���ʱ���ݣ�</font> <%
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
							<td width="120"><html:hidden property="tmpdatastart"  value="<%=startdatastr%>" /> �����������£�</td>
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
	<!------��ʾ������------->

	<!------��ʾ����ʼ------->
	<table width="100%" cellspacing="1" cellpadding="3"
		class="list_table_border">
		<tr class="list_td_title">
			<td height="29"><b>��ʷ�����ļ�ɾ��&nbsp;&nbsp;&nbsp;</b></td>
			<td width="430">&nbsp;&nbsp;&nbsp;&nbsp; <html:errors
					property="deletedata" /></td>
		</tr>
		<tr class="list_tr0">
			<td colspan="2">�����ļ���ʽ����ʷ������ϸҲ���Ա�ɾ����������ֹ�����磺 <font color=blue>200412</font>��ϵͳ��ɾ������֮ǰ���������£������д浵�ļ���
				<font color=red><b><u>ɾ�������ݲ��ɻָ���������ִ�С�</u> </b> </font> <%
 	if (statusoftmptable.equals("B")) {
 %> <font color=red><b>ϵͳ���ڽ��д�����Ƚ������ٲ�����</b> </font> <%
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
							<td width="120"><html:hidden property="tmpdatastart" value="<%=startdatastr%>" /> ɾ���ļ���ֹ���£�</td>
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
	<!------��ʾ������------->

</body>
</html:html>
