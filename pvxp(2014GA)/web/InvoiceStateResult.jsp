<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="16" minpower="1" />

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
	String firstnum   = "";
	String lastnum    = "";
	String currentnum = "";
	String wastenum   = "";
	String ifvaluable = "";
  
%>

<html:html locale="true">
<head>
	<title>发票状态查询</title>
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
	<html:form action="/InvState.do">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" align="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location">发票状态查询</font>
				</td>
			</tr>
		</table>

		<table align="center" width="100%" cellspacing="1" cellpadding="3"
			border="0" class="list_table_border">
			<tr align="center">
				<td class="list_td_title">
					设备编号
				</td>
				<td class="list_td_title">
					所属机构
				</td>
				<td class="list_td_title">
					领用日期
				</td>
				<td class="list_td_title">
					领用时间
				</td>
				<td class="list_td_title">
					起始发票号
				</td>
				<td class="list_td_title">
					终止发票号
				</td>
				<td class="list_td_title">
					当前发票号
				</td>
				<td class="list_td_title">
					废票张数
				</td>
				<td class="list_td_title">
					剩余张数
				</td>
				<td class="list_td_title">
					发票状态
				</td>
				<logic:present name="Result">
					<logic:iterate id="element" name="Result">
						<%
					InvStateResult invdis = (InvStateResult)element;
					bank = charSet.db2web(invdis.getBank()).trim();
					firstnum = charSet.db2web(invdis.getFirstnum()).trim();
					lastnum = charSet.db2web(invdis.getLastnum()).trim();
					currentnum = charSet.db2web(invdis.getCurrentnum()).trim();
					wastenum = charSet.db2web(invdis.getWastenum()).trim();
				      	     
					long  remainnum  = 0 ;
					try{
						remainnum = Long.parseLong(lastnum)-Long.parseLong(currentnum)+1;
					}catch(NumberFormatException e){}
							  
					ifvaluable = charSet.db2web(invdis.getIfvaluable()).trim();
					if(ifvaluable.equals("0")){
						ifvaluable="不可用";
					}else if(ifvaluable.equals("1")){
						ifvaluable="可打印测试页";
					}else if(ifvaluable.equals("2")){
						ifvaluable="正常";
					}else{
						ifvaluable="不可用";
					}
      	
					temp = invdis.getDate().trim();
					date = temp.substring(0, 4) + "-" + temp.substring(4, 6) + "-" + temp.substring(6, 8);
					temp = invdis.getTime().trim();
					time = temp.substring(0, 2) + ":" + temp.substring(2, 4) + ":" + temp.substring(4, 6);
				%>
						<tr align="center" class="list_tr<%=i++%2%>">
							<td>
								<bean:write name="element" property="devno" />
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
								<%=currentnum%>
							</td>
							<td>
								<%=wastenum%>
							</td>
							<td>
								<%=remainnum%>
							</td>
							<td>
								<%=ifvaluable%>
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
							共有
							<bean:write name="PageBean" property="totalRow" />
							条结果 共
							<bean:write name="PageBean" property="totalPage" />
							页 第
							<bean:write name="PageBean" property="currentPage" />
							页
							<logic:equal name="PageBean" property="hasPrevious" value="true">
								<html:link href="#"
									onclick="parent.showit();page.value = --currentPage.value;submit();">前一页</html:link>
							</logic:equal>
							<logic:equal name="PageBean" property="hasNext" value="true">
								<html:link href="#"
									onclick="parent.showit();page.value = ++currentPage.value;submit();">后一页</html:link>
							</logic:equal>
							<logic:equal name="PageBean" property="hasMultiPage" value="true">转到第 <input
									type="text" name="page" size="2" class="page_input"> 页
					
						
						</td>
						<td>
							<input type="image" src="images/default/bt_go.gif"
								onfocus="this.blur()" onclick="return go()">
							</logic:equal>
						</td>
						<html:hidden name="PageBean" property="currentPage" />
						<html:hidden name="PageBean" property="totalRow" />
						<% 	String[] devno = (String[])request.getAttribute("devno");
				
						for (i=0; i<devno.length; i++) {
					%>
						<html:hidden property="devno" value="<%=devno[i]%>" />
						<%
						}
					%>
					</logic:greaterThan>
				</logic:present>

				<td>
					<img src="images/default/bt_back.gif"
						onclick="parent.parent.goUrl('InvoiceState.jsp')"
						style="cursor:hand">
				</td>

			</tr>
		</table>

	</html:form>
</body>
</html:html>
