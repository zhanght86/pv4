<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<app:checkpower funcid="9" minpower="1" />
<%@ page import="java.util.*"%>
<%@ page import="com.lcjr.pvxp.util.*"%>
<%@ page import="com.lcjr.pvxp.model.*"%>
<%@ page import="com.lcjr.pvxp.orm.*"%>
<%@ page import="org.apache.log4j.*"%>

<%
	//�豸��
	String cardtype = "";
	//�豸���ͱ��
	String typeno = "";
	//������
	String organno = "";
	//����������
	String numlytotal = "";
	//�ܷ�������
	String numouttotal = "";
	//������������
	String numly = "";
	//���շ�������
	String numout = "";
	//����ʣ������
	String numrest = "";
	//�����ֶ�1
	//�����ֶ�2
	
	CharSet charSet = new CharSet();
	int i = 0;
	
	String temp = "";
%>

<html:html locale="true">
<head>
	<title>����״̬��ѯ</title>
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
	<html:form action="/CardStatus.do">
	
	
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" align="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location">����״̬��ѯ</font>
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
					������
				</td>
				<td class="list_td_title">
					�豸���ͺ�
				</td>
				<td class="list_td_title">
					��Ա��
				</td>
				<td class="list_td_title">
					����������
				</td>
				<td class="list_td_title">
					�ܷ�������
				</td>
				<td class="list_td_title">
					������������
				</td>
				<td class="list_td_title">
					���շ�������
				</td>
				<td class="list_td_title">
					����ʣ������
				</td>

				<logic:present name="Result">
					<logic:iterate id="element" name="Result">
						<%
							CardStatusResult invdis = (CardStatusResult) element;
							//�豸��
							cardtype = invdis.getCardtype();
							//�豸���ͱ��
							typeno = invdis.getTypeno();
							//������
							organno = charSet.db2web(invdis.getOrganno()).trim();
							//����������
							numlytotal = invdis.getNumlytotal();
							//�ܷ�������
							numouttotal = invdis.getNumouttotal();
							//������������
							numly = invdis.getNumly();
							//���շ�������
							numout = invdis.getNumout();
							//����ʣ������
							numrest = invdis.getNumrest();
							cardtype = charSet.db2web(cardtype);
							if (cardtype.equals("1")) {
								cardtype = "��ǿ�";
							} else if (cardtype.equals("2")) {
								cardtype = "���ÿ�";
							} else if (cardtype.equals("3")) {
								cardtype = "��������";
							} else {
								cardtype = "δ֪����";
							}
						%>
						<tr align="center" class="list_tr<%=i++ % 2%>">
							<td>
								<bean:write name="element" property="devno" />
							</td>
							<td>
								<%=cardtype%>
							</td>
							<td>
								<%=typeno%>
							</td>
							<td>
								<%=organno%>
							</td>
							<td>
								<%=numlytotal%>
							</td>
							<td>
								<%=numouttotal%>
							</td>
							<td>
								<%=numly%>
							</td>
							<td>
								<%=numout%>
							</td>
							<td>
								<%=numrest%>
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
							String[] devNo = (String[]) request.getAttribute("devno");
							String date1 = (String) request.getAttribute("date1");
							String date2 = (String) request.getAttribute("date2");
							
							for (i = 0; i < devNo.length; i++) {
						%>
						<html:hidden property="termnum" value="<%=devNo[i]%>" />
						<%
						}
						%>
					</logic:greaterThan>
				</logic:present>


				<td>
					<img src="images/default/bt_back.gif"
						onclick="parent.parent.goUrl('CardStatus.jsp')"
						style="cursor:hand">
				</td>
			</tr>
		</table>



	</html:form>
</body>
</html:html>
