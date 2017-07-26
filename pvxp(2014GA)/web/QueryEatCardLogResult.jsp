<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>


<%@ page import="java.util.*"%>
<%@ page import="com.lcjr.pvxp.util.*"%>
<%@ page import="com.lcjr.pvxp.model.*"%>
<%@ page import="com.lcjr.pvxp.orm.*"%>
<%@ page import="org.apache.log4j.*"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="8" minpower="1" />

<%
	CharSet charSet = new CharSet();
	int i = 0;
	String bank = "";
	String message = "";
	String date = "";
	String time = "";
	String temp = "";
%>

<html:html locale="true">
<head>
	<title>�̿���¼��ѯ</title>
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
      function writeFlag(flag){
        try{
          if( flag == 1 ){
            document.write("δȡ��");
          }else if( flag == 0 ){
            document.write("��ȡ��");
          }else{
            document.write("δ&nbsp;&nbsp;֪");
          }
        }catch(e){
          document.write("δ&nbsp;&nbsp;֪");
        }
      }
    </script>
</head>

<body onload="javascript:parent.hidit();">
	<html:form action="/EatCardLog.do">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location">�̿���¼��ѯ</font>
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
					����
				</td>
				<td class="list_td_title">
					�̿�����
				</td>
				<td class="list_td_title">
					�̿�ʱ��
				</td>
				<td class="list_td_title">
					�̿�ԭ��
				</td>
				<td class="list_td_title">
					״̬
				</td>

				<logic:present name="Result">
					<logic:iterate id="element" name="Result">
						<%
							EcLogResult ecLog = (EcLogResult) element;
							bank = charSet.db2web(ecLog.getBank());
							message = charSet.db2web(ecLog.getMessage());
							temp = ecLog.getDate();
							date = temp.substring(0, 4) + "-" + temp.substring(4, 6) + "-" + temp.substring(6, 8);
							temp = ecLog.getTime();
							time = temp.substring(0, 2) + ":" + temp.substring(2, 4) + ":" + temp.substring(4, 6);
						%>
						<tr align="center" class="list_tr<%=i++ % 2%>">
							<td>
								<bean:write name="element" property="devNo" />
							</td>
							<td>
								<%=bank%>
							</td>
							<td>
								<bean:write name="element" property="cardNo" />
							</td>
							<td>
								<%=date%>
							</td>
							<td>
								<%=time%>
							</td>
							<td>
								<%=message%>
							</td>
							<td>
								<script>
writeFlag(<bean:write name="element" property="flag"/>);
</script>
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
							String cardNo = (String) request.getAttribute("cardNo");
							String date1 = (String) request.getAttribute("date1");
							String date2 = (String) request.getAttribute("date2");
							String flag = (String) request.getAttribute("flag");
							
							for (i = 0; i < devno.length; i++) {
						%>
						<html:hidden property="devNo" value="<%=devno[i]%>" />
						<%
						}
						%>
						<html:hidden property="cardNo" value="<%=cardNo%>" />
						<html:hidden property="date1" value="<%=date1%>" />
						<html:hidden property="date2" value="<%=date2%>" />
						<html:hidden property="flag" value="<%=flag%>" />
					</logic:greaterThan>
				</logic:present>

				<td>
					<img src="images/default/bt_back.gif"
						onclick="parent.parent.goUrl('QueryEatCardLog.jsp')"
						style="cursor:hand">
				</td>

			</tr>
		</table>

	</html:form>
</body>
</html:html>
