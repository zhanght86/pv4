<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<app:checkpower funcid="9" minpower="1" />
<%@ page import="java.util.*"%>
<%@ page import="com.lcjr.pvxp.util.*"%>
<%@ page import="com.lcjr.pvxp.model.*"%>
<%@ page import="com.lcjr.pvxp.orm.*"%>
<%@ page import="org.apache.log4j.*"%>

<%
	//设备号
	String cardtype = "";
	//设备类型编号
	String typeno = "";
	//机构号
	String organno = "";
	//总领用张数
	String numlytotal = "";
	//总发卡张数
	String numouttotal = "";
	//当日领用张数
	String numly = "";
	//当日发卡张数
	String numout = "";
	//现在剩余张数
	String numrest = "";
	//备用字段1
	//备用字段2
	
	CharSet charSet = new CharSet();
	int i = 0;
	
	String temp = "";
%>

<html:html locale="true">
<head>
	<title>发卡状态查询</title>
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
					<font class="location">发卡状态查询</font>
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
					卡类型
				</td>
				<td class="list_td_title">
					设备类型号
				</td>
				<td class="list_td_title">
					柜员号
				</td>
				<td class="list_td_title">
					总领用张数
				</td>
				<td class="list_td_title">
					总发卡张数
				</td>
				<td class="list_td_title">
					当日领用张数
				</td>
				<td class="list_td_title">
					当日发卡张数
				</td>
				<td class="list_td_title">
					现在剩余张数
				</td>

				<logic:present name="Result">
					<logic:iterate id="element" name="Result">
						<%
							CardStatusResult invdis = (CardStatusResult) element;
							//设备号
							cardtype = invdis.getCardtype();
							//设备类型编号
							typeno = invdis.getTypeno();
							//机构号
							organno = charSet.db2web(invdis.getOrganno()).trim();
							//总领用张数
							numlytotal = invdis.getNumlytotal();
							//总发卡张数
							numouttotal = invdis.getNumouttotal();
							//当日领用张数
							numly = invdis.getNumly();
							//当日发卡张数
							numout = invdis.getNumout();
							//现在剩余张数
							numrest = invdis.getNumrest();
							cardtype = charSet.db2web(cardtype);
							if (cardtype.equals("1")) {
								cardtype = "借记卡";
							} else if (cardtype.equals("2")) {
								cardtype = "信用卡";
							} else if (cardtype.equals("3")) {
								cardtype = "其他卡种";
							} else {
								cardtype = "未知卡种";
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
