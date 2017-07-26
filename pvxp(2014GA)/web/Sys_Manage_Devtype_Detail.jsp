<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="13" minpower="1" />


<html:html locale="true">
<head>
	<title><bean:message key="pvxp.devtype.list" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>
<body onload="javascript:parent.hidit();">
	<div id="show" style="position:absolute; width="
		100%" z-index:1; left: 0px; top: 0px;" align="center">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" align="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location"><bean:message key="pvxp.devtype.list" />
					</font>
				</td>
			</tr>
		</table>
		<%
			String typeno = request.getParameter("typeno");
			DevtypeModel myDevtypeModel = new DevtypeModel();
			Devtype temp = DevtypeModel.getDevTpFromList(typeno);
			
			int int_ttcount = 0;
			int int_pagecount = 0;
			int int_page = 0;
			
			try {
				int_page = Integer.parseInt(mpage);
				int_pagecount = Integer.parseInt(pagecount);
			} catch (Exception e) {
				
			}
			
			Vector myVector = (Vector) request.getAttribute(Constants.REQUEST_DEVTP_VECTOR);
			
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();
			
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			int len = 0;
			if (myVector != null) {
				len = myVector.size();
			}
		%>
		<table width="100%" cellspacing="1" cellpadding="3"
			class="list_table_border">
			<html:form action="/DevTpDel.do" method="post"
				styleId="DevTpList_del" onsubmit="javascript:parent.showit();">
				<tr align="center">
					<td class="list_td_title" width="100">
						<bean:message key="pvxp.devtype.typeno" />
					</td>
					<td class="list_td_title" width="100">
						<bean:message key="pvxp.devtype.typestate" />
					</td>
					<td class="list_td_title" width="100">
						<bean:message key="pvxp.devtype.devtype" />
					</td>
					<td class="list_td_title" width="100">
						<bean:message key="pvxp.devtype.devname" />
					</td>
					<td class="list_td_title">
						<bean:message key="pvxp.devtype.devfactory" />
					</td>
					<td class="list_td_title">
						<bean:message key="pvxp.devtype.contact" />
					</td>
					<td class="list_td_title" id="delbt">
						<input type="submit" value="删除" <%if(!authlist.equals("*")){%>
							disabled <%}%>>
					</td>
				</tr>


				<%
						for (int i = 0; i < len; i++) {
						Devtype temp = (Devtype) myVector.get(i);
						String myTypeno = temp.getTypeno();
						String myTypestate = temp.getTypestate();
						if (myTypestate.equals("a")) {
							myTypestate = "pvxp.devtype.typestate.a";
						} else if (myTypestate.equals("s")) {
							myTypestate = "pvxp.devtype.typestate.s";
						} else {
							myTypestate = "pvxp.devtype.typestate.other";
						}
						
						String myDevtype = temp.getDevtype();
						String myDevname = temp.getDevname();
						String myDevfactory = temp.getDevfactory();
						String myContact1 = temp.getContact1();
				%>
				<tr align="left" class="list_tr<%=i % 2%>">
					<td>
						&nbsp;
						<b><%=myTypeno%> </b>
					</td>
					<td>
						&nbsp;
						<bean:message key="<%=myTypestate%>" />
					</td>
					<td>
						&nbsp;
						<%=myCharSet.db2web(myDevtype)%>
					</td>
					<td>
						&nbsp;
						<%=myCharSet.db2web(myDevname)%>
					</td>
					<td>
						&nbsp;
						<%=myCharSet.db2web(myDevfactory)%>
					</td>
					<td>
						&nbsp;
						<%=myCharSet.db2web(myContact1)%>
					</td>
					<td id="delcheck">
						&nbsp;
						<html:multibox property="devtpArry" value="<%=myTypeno%>" />
					</td>
				</tr>
				<%
				}
				%>
			</html:form>
		</table>

		<table width="100%" cellspacing="0" cellpadding="0">
			<form
				onsubmit="javascript:parent.parent.getDevTpList(document.all.gopage.value);return false;">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location"><bean:message key="pvxp.devtype.list" />
					</font>
				</td>
				<td align="right" valign="center">
					设备类型
					<%=ttcount%>
					种 共
					<%=pagecount%>
					页 当前为第
					<%=mpage%>
					页
					<%
				if (int_page > 1) {
				%>
					&nbsp;&nbsp;
					<a href="#"
						onclick="javascript:parent.parent.getDevTpList('<%=int_page - 1%>');">[上一页]</a>
					<%
					}
					%>

					<%
					if (int_page < int_pagecount) {
					%>
					&nbsp;&nbsp;
					<a href="#"
						onclick="javascript:parent.parent.getDevTpList('<%=int_page + 1%>');">[下一页]</a>
					<%
					}
					%>

					<%
					if (int_pagecount > 1) {
					%>
					&nbsp;&nbsp;&nbsp;转到第
					<input id="gopage" type=txt size=3 class="page_input">
					页
					<input type=submit value="Go">

					<%
					}
					%>
				</td>
			</tr>
			</form>
		</table>

	</div>
	<script>
<%if(!authlist.equals("*")){%>
document.all['delbt'].style.display='none';
document.all['delcheck'].style.display='none';
<%}%>
if( document.all['gopage'] )
document.all['gopage'].focus();
</script>
	<html:errors property="delerrors" />
</body>
</html:html>
