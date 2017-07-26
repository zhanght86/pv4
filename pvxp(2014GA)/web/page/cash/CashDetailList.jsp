<%@ include file="../../inc/taglib.jsp"%>
<%@page import="com.lcjr.pvxp.orm.CashDetail"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@page import="com.lcjr.pvxp.util.CharSet"%>
<%@page import="com.lcjr.pvxp.util.PubUtil"%>
<%@page import="com.lcjr.pvxp.util.Constants"%>
<%@page import="java.util.List"%>
<%@ page import="org.apache.log4j.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
    
    <title>CashDetailList.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link href="../../style/pvxp.css" rel="stylesheet" type="text/css">
<style type="text/css">
	.a{
		cursor:pointer;
	}
</style>
<script type="text/javascript">
	function checkForm() {
		var page = document.all.gopage.value;
		if (page.length>0 && page.match(/\D/)==null) {
			parent.showit();
			parent.parent.getDetailList(document.all.gopage.value);
		}
	}
</script>
  </head>
  
  <body onload="javascript:parent.hidit();">
	<form>
	<%
			Logger log = Logger.getLogger("web.CashDetailList.jsp");
			String totalDevCount =String.valueOf(request.getAttribute("totalDevCount"));
			String totalPages = String.valueOf(request.getAttribute("totalPages"));
			String currentpage = String.valueOf(request.getAttribute("currentpage"));
			int int_ttcount = 0;
			int int_pagecount = 0;
			int int_page = 0;
			
			try {
				int_page = Integer.parseInt(currentpage);
				int_pagecount = Integer.parseInt(totalPages);
			} catch (Exception e) {
				log.error("页数wrong",e);
			}

			
		%>

		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" align="center" width="30" height="40">
					<img src="../../images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location">现金模块交易明细</font>
				</td>
			</tr>
		</table>


		<table align="center" width="100%" cellspacing="1" cellpadding="3"
			border="0" class="list_table_border">
			<tr align="center">
				<td class="list_td_title">
					交易类型
				</td>
				<td class="list_td_title">
					设备号
				</td>
				<td class="list_td_title">
					交易日期
				</td>
				<td class="list_td_title">
					交易时间
				</td>
				<td class="list_td_title">
					卡号
				</td>
				<td class="list_td_title">
					交易状态
				</td>
				<td class="list_td_title">
					交易金额
				</td>
				<td class="list_td_title">
					钞箱状态
				</td>
				<td class="list_td_title">
					钞箱批次号
				</td>

				  	<logic:iterate id="element" name="detailResult">
				  	<%
				  		log.info("test");
				  		CashDetail cd = (CashDetail)element;
				  		String tctype = cd.getTrcdtype().trim();
				  		if(tctype.equals("0001")){
				  			tctype="小额存款";
				  		}
				  		String tradetypes = cd.getRetflag().trim();
				  		if(tradetypes.equals("0")){
				  			tradetypes="设备收到现金但是交易失败";
				  		}else if(tradetypes.equals("1")){
				  			tradetypes="交易成功";
				  		}
				  		String boxtypes = cd.getDzflag();
				  		if(boxtypes.equals("0")){
				  			boxtypes="未清钞";
				  		}else if(boxtypes.equals("1")){
				  			boxtypes="已清钞";
				  		}
				  	 %>
						<tr align="left"  class="list_tr1">
					<td><%=tctype %></td>
					<td><%=cd.getDevno() %></td>
					<td><%=cd.getTrcddate() %></td>
					<td><%=cd.getTrcdtime() %></td>
					<td><%=cd.getCardno() %></td>
					<td><%=tradetypes %></td>
					<td><%=cd.getTotalamount() %></td>
					<td><%=boxtypes %></td>
					<td><%=cd.getBatchid() %></td>
				</tr>
					</logic:iterate>
			</tr>
		</table>
	</form>
	<table width="100%" cellspacing="0" cellpadding="0">
			<form onsubmit="return false">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="../../images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location">现金模块交易明细列表 </font>
				</td>
			</tr>
			</form>
		</table>
		<script>
if( document.all['gopage'] )
document.all['gopage'].focus();
</script>
</body>
</html:html>