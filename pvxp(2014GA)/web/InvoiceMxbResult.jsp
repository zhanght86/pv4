<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<%@ page import="java.util.*"%>
<%@ page import="com.lcjr.pvxp.util.*"%>
<%@ page import="com.lcjr.pvxp.model.*"%>
<%@ page import="com.lcjr.pvxp.orm.*"%>
<%@ page import="org.apache.log4j.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="17" minpower="1" />


<%
	CharSet charSet = new CharSet();
	PubUtil pubUtil = new PubUtil();
	int i = 0;
	int total = 0;
	String bank = "";
	String date = "";
	String time = "";
	String temp = "";
	String type = "";
	String code = "";
	String invty = "";
	String money = "";
	String accno1 = "";
	String tempstr = "";
	String totalType = "";
	String invoiceno = "";
	String printresult = "";
	String printtoken = "";
%>

<html:html locale="true">
<head>
	<title>��Ʊ��ϸ��ѯ</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script>
			function go() {
				var goPage = document.all.page.value;
				if (goPage.length>0 && goPage.match(/\D/)==null) {
					parent.showit();
					return true;
				} else {
					return false;
				}
			}
    
		</script>
</head>

<body onload="javascript:parent.hidit();">
	<html:form action="/InvLog.do">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location">��Ʊ��ϸ��ѯ</font>
				</td>
			</tr>
		</table>

		<table align="center" width="100%" cellspacing="1" cellpadding="3"
			border="0" class="list_table_border">
			<tr align="center">
				<td class="list_td_title">
					�豸���
				</td>
				<td class="list_td_title">
					��������
				</td>
				<td class="list_td_title">
					��Ʊ����
				</td>
				<td class="list_td_title">
					��Ʊ����
				</td>
				<td class="list_td_title">
					�ɷѿ���
				</td>
				<td class="list_td_title">
					�ɷѽ��
				</td>
				<td class="list_td_title">
					ʹ�÷�ʽ
				</td>
				<td class="list_td_title">
					��ӡ���
				</td>
				<td class="list_td_title">
					��ӡ����
				</td>
				<td class="list_td_title">
					��ӡʱ��
				</td>
				<logic:present name="Result">
					<logic:iterate id="element" name="Result">
						<%
							InvLogResult invLogResult = (InvLogResult) element;
							bank = charSet.db2web(invLogResult.getBank()).trim();
							type = charSet.db2web(invLogResult.getType()).trim();
							totalType = (String) pubUtil.ReadConfig("InvoiceType", "InvoiceType_Num", "", "ini", "Invoice.ini");
							total = Integer.parseInt(totalType);
							for (int j = 0; j < total; j++) {
								tempstr = (String) pubUtil.ReadConfig("InvoiceType", "Type_" + (j + 1), "", "ini", "Invoice.ini");
								code = tempstr.substring(0, tempstr.indexOf(','));
								invty = tempstr.substring(tempstr.indexOf(',') + 1);
								if (code.equals(type)) {
									type = invty;
								}
							}
							
							money = charSet.db2web(invLogResult.getMoney()).trim();
							accno1 = charSet.db2web(invLogResult.getAccno1()).trim();
							invoiceno = charSet.db2web(invLogResult.getInvoiceno()).trim();
							
							printresult = charSet.db2web(invLogResult.getPrintresult()).trim();
							if (printresult.equals("0000")) {
								printresult = "�ɹ�";
							} else {
								printresult = "ʧ��";
							}
							
							printtoken = charSet.db2web(invLogResult.getPrinttoken()).trim();
							if (printtoken.equals("0000")) {
								printtoken = "�ͻ���ӡ";
							} else {
								printtoken = "����";
							}
							
							temp = invLogResult.getDate().trim();
							date = temp.substring(0, 4) + "-" + temp.substring(4, 6) + "-" + temp.substring(6, 8);
							temp = invLogResult.getTime().trim();
							time = temp.substring(0, 2) + ":" + temp.substring(2, 4) + ":" + temp.substring(4, 6);
						%>
						<tr align="center" class="list_tr<%=i++ % 2%>">
							<td>
								<bean:write name="element" property="devno" />
							</td>
							<td>
								<%=bank%>
							</td>
							<td>
								<%=invoiceno%>
							</td>
							<td>
								<%=type%>
							</td>
							<td>
								<%=accno1%>
							</td>
							<td>
								<%=money%>
							</td>
							<td>
								<%=printtoken%>
							</td>
							<td>
								<%=printresult%>
							</td>
							<td>
								<%=date%>
							</td>
							<td>
								<%=time%>
							</td>
						</tr>
					</logic:iterate>
				</logic:present>
			</tr>
		</table>

		<table align="center">
			<tr>
				<logic:present name="PageBean">
					<logic:greaterThan name="PageBean" property="totalRow" value="0">
						<td>
							����
							<bean:write name="PageBean" property="totalRow" />
							����� ��
							<bean:write name="PageBean" property="totalPage" />
							ҳ ��
							<bean:write name="PageBean" property="currentPage" />
							ҳ
							<logic:equal name="PageBean" property="hasPrevious" value="true">
								<html:link href="#"
									onclick="parent.showit();page.value = --currentPage.value;submit();">ǰһҳ</html:link>
							</logic:equal>
							<logic:equal name="PageBean" property="hasNext" value="true">
								<html:link href="#"
									onclick="parent.showit();page.value = ++currentPage.value;submit();">��һҳ</html:link>
							</logic:equal>
							<logic:equal name="PageBean" property="hasMultiPage" value="true">ת���� <input
									type="text" name="page" size="2" class="page_input"> ҳ
					
						</td>
						<td>
							<input type="image" src="images/default/bt_go.gif"
								onfocus="this.blur()" onclick="return go()">
							</logic:equal>
						</td>
						<html:hidden name="PageBean" property="currentPage" />
						<html:hidden name="PageBean" property="totalRow" />
						<%
							String[] devno = (String[]) request.getAttribute("devno");
							String date1 = (String) request.getAttribute("date1");
							String date2 = (String) request.getAttribute("date2");
							String invno1 = (String) request.getAttribute("invno1");
							String invno2 = (String) request.getAttribute("invno2");
							String accno = (String) request.getAttribute("accno");
							
							for (i = 0; i < devno.length; i++) {
						%>
						<html:hidden property="devno" value="<%=devno[i]%>" />
						<html:hidden property="date1" value="<%=date1%>" />
						<html:hidden property="date2" value="<%=date2%>" />
						<html:hidden property="invno1" value="<%=invno1%>" />
						<html:hidden property="invno2" value="<%=invno2%>" />
						<html:hidden property="accno" value="<%=accno%>" />
						<%
						}
						%>
					</logic:greaterThan>
				</logic:present>

				<td>
					<img src="images/default/bt_back.gif"
						onclick="parent.parent.goUrl('InvoiceMxb.jsp')"
						style="cursor:hand">
				</td>

			</tr>
		</table>

	</html:form>
</body>
</html:html>
