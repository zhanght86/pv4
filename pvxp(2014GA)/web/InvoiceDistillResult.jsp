<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="15" minpower="1" />

<%@ page import="java.util.*"%>
<%@ page import="com.lcjr.pvxp.util.*"%>
<%@ page import="com.lcjr.pvxp.model.*"%>
<%@ page import="com.lcjr.pvxp.orm.*"%>
<%@ page import="org.apache.log4j.*"%>

<%
	CharSet charSet = new CharSet();
	int i = 0;
	String bank = "";
	String date = "";
	String time = "";
	String temp = "";
	String firstnum = "";
	String lastnum = "";
	String applytoken = "";
	String appynum = "";
%>

<html:html locale="true">
<head>
	<title>��Ʊ���ò�ѯ</title>
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
	<html:form action="/InvoiceDistill.do">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" align="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location">��Ʊ���ò�ѯ</font>
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
					��������
				</td>
				<td class="list_td_title">
					����ʱ��
				</td>
				<td class="list_td_title">
					��ʼ��
				</td>
				<td class="list_td_title">
					��ֹ��
				</td>
				<td class="list_td_title">
					״̬
				</td>
				<td class="list_td_title">
					����
				</td>
				<logic:present name="Result">
					<logic:iterate id="element" name="Result">
						<%
							InvoiceDistillResult invdis = (InvoiceDistillResult) element;
							bank = charSet.db2web(invdis.getBank()).trim();
							firstnum = charSet.db2web(invdis.getFirstnum()).trim();
							lastnum = charSet.db2web(invdis.getLastnum()).trim();
							applytoken = charSet.db2web(invdis.getApplytoken()).trim();
							if (applytoken.equals("1")) {
								applytoken = "����";
							} else if (applytoken.equals("0")) {
								applytoken = "����";
							} else {
								applytoken = "δ֪";
							}
							
							appynum = charSet.db2web(invdis.getAppynum()).trim();
							temp = invdis.getDate().trim();
							date = temp.substring(0, 4) + "-" + temp.substring(4, 6) + "-" + temp.substring(6, 8);
							temp = invdis.getTime().trim();
							time = temp.substring(0, 2) + ":" + temp.substring(2, 4) + ":" + temp.substring(4, 6);
						%>
						<tr align="center" class="list_tr<%=i++ % 2%>">
							<td>
								<bean:write name="element" property="termnum" />
							</td>
							<td>
								<%=bank%>
							</td>
							<td>
								<%=date%>
							</td>
							<td>
								<%=time%>
							</td>
							<td>
								<%=firstnum%>
							</td>
							<td>
								<%=lastnum%>
							</td>
							<td>
								<%=applytoken%>
							</td>
							<td>
								<%=appynum%>
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
							
							for (i = 0; i < devno.length; i++) {
						%>
						<html:hidden property="termnum" value="<%=devno[i]%>" />
						<%
						}
						%>
						<html:hidden property="date1" value="<%=date1%>" />
						<html:hidden property="date2" value="<%=date2%>" />
					</logic:greaterThan>
				</logic:present>

				<td>
					<img src="images/default/bt_back.gif"
						onclick="parent.parent.goUrl('InvoiceDistill.jsp')"
						style="cursor:hand">
				</td>

			</tr>
		</table>

	</html:form>
</body>
</html:html>
