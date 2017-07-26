<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="3" minpower="2" />

<html:html locale="true">

<head>
	<title>终端重启关机时间修改</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location">终端重启关机时间修改</font>
				</td>
			</tr>
		</table>

		<%	
	String[] devlist = request.getParameterValues("devlist");
	
	String[] result = (String[])request.getAttribute(Constants.REQUEST_REMOTECONTROL_RESULT);
	
%>

		<table width="75%" cellspacing="1" cellpadding="3"
			class="list_table_border">
			<tr align="center">
				<td class="list_td_title">
					<bean:message key="pvxp.dev.no" />
				</td>
				<td class="list_td_title">
					操作结果
				</td>
			</tr>
			<%		for(int i = 0; i < devlist.length; i++){
			
			String flag = "";
			String ret = result[i];
			if(ret.length() > 6){ 
				flag = ret.substring(4,6);
				ret = ret.substring(6);
				
			}
			if ( ret.equals("-1") ) {
%>
			<tr align="center" class="list_tr<%=i%2%>">
				<td>
					<font color="red"><%=devlist[i]%>
					</font>
				</td>
				<td>
					<font color="red">与终端通讯异常</font>
				</td>
			</tr>
			<%
			}else {
				if(!flag.equals("00")){
%>
			<tr align="center" class="list_tr<%=i%2%>">
				<td>
					<font color="red"><%=devlist[i]%>
					</font>
				</td>
				<td>
					<font color="red"><%=ret%>
					</font>
				</td>
			</tr>
			<%
				}else{
%>
			<tr align="center" class="list_tr<%=i%2%>">
				<td>
					<%=devlist[i]%>
				</td>
				<td>
					修改成功
				</td>
			</tr>
			<%						
				}
					
				
			}
		}
%>
		</table>
		<br>
		<br>
		<div align="center">
			<img src="images/default/bt_back.gif"
				onClick="javascript:parent.parent.goUrl('DevReboot.jsp')"
				style="cursor:hand">
		</div>
</body>
</html:html>
