<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<app:checkpower funcid="9" minpower="1" />
<%@ page import="com.lcjr.pvxp.orm.CashStock"%>

<html:html locale="true">
<head>
	<title>�����¼��ѯ</title>
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
	<form>


		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" align="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location">�����¼��ѯ</font>
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
					�������κ�
				</td>
				<td class="list_td_title">
					����״̬
				</td>
				<td class="list_td_title">
					�峮����
				</td>
				<td class="list_td_title">
					�峮ʱ��
				</td>
				<td class="list_td_title">
					������������
				</td>
				<td class="list_td_title">
					�������ܽ��
				</td>

				  	<logic:iterate id="element" name="Vec">
						<%
								CashStock invdis = (CashStock) element;

								String  state=invdis.getState().trim();
								  if(state.equals("1")){
								 	 state="��������";
								  }else if(state.equals("0")){
								  	 state="��������ʹ��";
								  }else
								  	state="N/A";
								 
								 if(invdis.getTrcddate()==null){
									 invdis.setTrcddate("N/A");
								 }
								 if(invdis.getTrcdtime()==null){
									 invdis.setTrcdtime("N/A");
								 }
								  
						%>
						<tr align="center" class="list_tr1">
							<td>
								<%=invdis.getDevno()%>
							</td>
							<td>
								<%=invdis.getBatchid()%>
							</td>
							<td>
								<%=state%>
							</td>
							<td>
								<%=invdis.getTrcddate()%>
							</td>
							<td>
								<%=invdis.getTrcdtime()%>
							</td>
							<td>
								<%=invdis.getCashcount()%>
							</td>
							<td>
								<%=invdis.getTotalcash()%>
							</td>
						</tr>
					</logic:iterate>
			</tr>
		</table>

	</form>
</body>
</html:html>