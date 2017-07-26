<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<app:checkpower funcid="9" minpower="1" />
<%@ page import="com.lcjr.pvxp.orm.CashStock"%>

<html:html locale="true">
<head>
	<title>钞箱记录查询</title>
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
					<font class="location">钞箱记录查询</font>
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
					钞箱批次号
				</td>
				<td class="list_td_title">
					钞箱状态
				</td>
				<td class="list_td_title">
					清钞日期
				</td>
				<td class="list_td_title">
					清钞时间
				</td>
				<td class="list_td_title">
					钞箱内总张数
				</td>
				<td class="list_td_title">
					钞箱内总金额
				</td>

				  	<logic:iterate id="element" name="Vec">
						<%
								CashStock invdis = (CashStock) element;

								String  state=invdis.getState().trim();
								  if(state.equals("1")){
								 	 state="已清理钞箱";
								  }else if(state.equals("0")){
								  	 state="钞箱正在使用";
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