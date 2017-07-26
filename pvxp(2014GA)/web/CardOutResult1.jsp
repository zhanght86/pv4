<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<app:checkpower funcid="9" minpower="1" />
<%@ page import="com.lcjr.pvxp.util.*"%>
<%@ page import="org.apache.log4j.*"%>

<%
	CharSet charSet = new CharSet();
	
	PubUtil pubUtil = new PubUtil();
	
	BufferedRandomAccessFile braf = pubUtil.openFile( "ini", "CardType.ini" );
	
	String[][] cardType = pubUtil.Ini2Array("ini","CardType.ini","CardCode","CCode_Num","CCode_",",","");
	String[][] FKReturnCode = pubUtil.Ini2Array("ini","CardType.ini","FKReturnCode","FKRCode_Num","FKRCode_",",","");
	String[][] ReturnCode = pubUtil.Ini2Array("ini","CardType.ini","ReturnCode","RCode_Num","RCode_",",","");
    
    int FKReturnCodeNum = Integer.parseInt( pubUtil.ReadConfig("FKReturnCode","FKRCode_Num","0",braf) );
	int cardTypeNum = Integer.parseInt( pubUtil.ReadConfig("CardCode","CCode_Num","0",braf) );
	int ReturnCodeNum = Integer.parseInt( pubUtil.ReadConfig("ReturnCode","RCode_Num","0",braf) );
	
	
	
	
	int i = 0;
	//�����ͣ�1����ǿ���2�����ÿ���3��������

	String cardtype = "";
	// �豸��

	String devno = "";
	//�豸���ͱ��

	String typeno = "";
	// ������

	String organno = "";
	// ���֤��

	String idcardno = "";
	// ������

	//�����
	String strcode = "";
	// ����

	String outcardno = "";
	//�ɿ�����
	String oldcardno = "";
	// ��������

	String outcarddate = "";
	// ����ʱ��

	String outcardtime = "";
	// ����״̬��1���ɹ�0��ʧ�ܣ�
	String outcardstatus = "";

	String temp = "";
	
	String duanxin = "";
	String dianhua = "";
	
%>

<html:html locale="true">
<head>
	<title>������¼��ˮ��ѯ</title>
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
	<html:form action="/CardOut.do">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location">������¼��ˮ��ѯ</font>
				</td>
			</tr>
		</table>

		<table align="center" width="100%" cellspacing="1" cellpadding="3"
			border="0" class="list_table_border">
			<tr align="center">
				<td class="list_td_title">
					��������
				</td>
				<td class="list_td_title">
					ʱ��
				</td>
				<td class="list_td_title">
					�ͻ�����
				</td>
				<td class="list_td_title">
					�¿�����
				</td>
				<td class="list_td_title">
					���Ա
				</td>

				<td class="list_td_title">
					����״̬
				</td>
				<td class="list_td_title">
					����ͨ
				</td>
				<td class="list_td_title">
					�绰����
				</td>
				<td class="list_td_title">
					�ɿ�����
				</td>
				<logic:present name="Result">
					<logic:iterate id="element" name="Result">
						<%
							CardOutResult cardout = (CardOutResult) element;

							//�����ͣ�1����ǿ���2�����ÿ���3��������
							cardtype = cardout.getCardtype();
						    //��������
							for(int num=0;num<cardTypeNum;num++){
						   	 if (cardtype.equals(cardType[num][0].trim())) {
								 cardtype = cardType[num][1].trim();
							   }
							}

							// �豸��
							devno = cardout.getDevno();
							//�豸���ͱ��
							typeno = cardout.getTypeno();
							// ������
							organno = cardout.getRemark2();							
							// ���֤��
							idcardno = cardout.getIdcardno();
							//�����
							strcode = cardout.getStrcode();
							// ����
							outcardno = cardout.getOutcardno();
							// �ɿ�����
							oldcardno = cardout.getRemark3();		
							// ��������
							temp = cardout.getOutcarddate();
							outcarddate = temp.substring(0, 4) + "-" + temp.substring(4, 6)
									+ "-" + temp.substring(6, 8);
							// ����ʱ��
							temp = cardout.getOutcardtime();
							outcardtime = temp.substring(0, 2) + ":" + temp.substring(2, 4)
									+ ":" + temp.substring(4, 6);
							// ����״̬��1���ɹ�0��ʧ�ܣ�
							outcardstatus = cardout.getOutcardstatus();
							
							
							//��������
							for(int num=0;num<FKReturnCodeNum;num++){
						   	 if (outcardstatus.equals(FKReturnCode[num][0].trim())) {
								 outcardstatus = FKReturnCode[num][1].trim();
							   }
							}

							temp = cardout.getRemark1();
							if(!temp.equals("")){

							temp=temp.trim();
							duanxin=temp.substring(0,1);
							for(int num=0;num<ReturnCodeNum;num++){
						   	 if (duanxin.equals(ReturnCode[num][0].trim())) {
								 duanxin = ReturnCode[num][1].trim();
							   }
							}
							
							dianhua=temp.substring(1,2);
							for(int num=0;num<ReturnCodeNum;num++){
						   	 if (dianhua.equals(ReturnCode[num][0].trim())) {
								 dianhua = ReturnCode[num][1].trim();
							   }
							}
							
							}else{

							
							duanxin="";
							dianhua="";

							}
							
							
							
						%>
						<tr align="center" class="list_tr<%=i++ % 2%>">
 
							<td>
								<%=outcarddate%>
							</td>
							<td>
								<%=outcardtime%>
							</td>
							<td>
								<%=strcode%>
							</td>
							<td>
								<%=outcardno%>
							</td>
							<td>
								<%=organno%>
							</td>
							<td>
								<%=outcardstatus  %>
							</td>
							<td>
								<%=duanxin%>
							</td>
							<td>
								<%=dianhua%>
							</td>
							<td>
								<%=oldcardno%>
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
							String id = (String) request.getAttribute("id");
							String cardno = (String) request.getAttribute("cardno");
							String time1 = (String) request.getAttribute("time1");
							String time2 = (String) request.getAttribute("time2");
							
							for (i = 0; i < devNo.length; i++) {
						%>
						<html:hidden property="termnum" value="<%=devNo[i]%>" />
						<%
						}
						%>
						<html:hidden property="date1" value="<%=date1%>" />
						<html:hidden property="date2" value="<%=date2%>" />
						<html:hidden property="id" value="<%=id%>" />
						<html:hidden property="cardno" value="<%=cardno%>" />
						<html:hidden property="time1" value="<%=time1%>" />
						<html:hidden property="time2" value="<%=time2%>" />

					</logic:greaterThan>
				</logic:present>

				<td>
					<img src="images/default/bt_back.gif"
						onclick="parent.parent.goUrl('CardOut.jsp')" style="cursor:hand">
				</td>

			</tr>
		</table>

	</html:form>
</body>
</html:html>
