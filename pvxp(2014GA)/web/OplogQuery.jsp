<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="7" minpower="1" />


<%
	PubUtil myPubUtil = new PubUtil();

	String authlist = myPubUtil.dealNull(
			myPubUtil.getValueFromCookie(
			Constants.COOKIE_OPER_AUTHLIST, request)).trim();
	String bankid = myPubUtil.dealNull(
			myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID,
			request)).trim();

	String deldate = myPubUtil.getOtherDate(-7);

	OperTrcdUtil myOperTrcdUtil = new OperTrcdUtil();
	String[][] tmpArray = myOperTrcdUtil.getOperTrcd();
%>
<html:html locale="true">
<head>
	<title><bean:message key="pvxp.oplog.title" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>
	<script>
	
	//处理要提交的表数据
	function dealFormSubmit(){
		var e = <%=myPubUtil.getNowDate(1)%>
		var qbv = document.all.bopdate.value;
		if( (qbv=="") || (qbv.length!=document.all.bopdate.maxLength) ) {
			alert( "请输入正确的 <bean:message key="pvxp.oplog.bdate"/>\n\n长度不正确" );
			document.all.bopdate.focus();
			return false;
		}else if( !isNumber(qbv) ) {
			alert( "请输入正确的 <bean:message key="pvxp.oplog.bdate"/>\n\n不应包含有非数字字符" );
			document.all.bopdate.focus();
			return false;
		}else if( (parseInt(qbv) > parseInt(e)) ) {
			alert( "请输入正确的 <bean:message key="pvxp.oplog.bdate"/>\n\n超出可查询范围" );
			document.all.bopdate.focus();
			return false;
		}
		var ebv = document.all.eopdate.value;
		if( (ebv=="") || (ebv.length!=document.all.eopdate.maxLength) ) {
			alert( "请输入正确的 <bean:message key="pvxp.oplog.edate"/>\n\n长度不正确" );
			document.all.eopdate.focus();
			return false;
		}else if( !isNumber(ebv) ) {
			alert( "请输入正确的 <bean:message key="pvxp.oplog.edate"/>\n\n不应包含有非数字字符" );
			document.all.eopdate.focus();
			return false;
		}else if( (parseInt(ebv) < parseInt(qbv)) || (parseInt(ebv) > parseInt(e)) ) {
			alert( "请输入正确的 <bean:message key="pvxp.oplog.edate"/>\n\n<bean:message key="pvxp.oplog.edate"/> 不能小于 <bean:message key="pvxp.oplog.bdate"/>" );
			document.all.eopdate.focus();
			return false;
		}
		parent.showit();
		OplogQuery_form.submit();
	}
	
	</script>
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">
		<html:form action="/OplogQuery.do" styleId="OplogQuery_form"
			target="pvmain" method="post">
			<input type="hidden" name="pagesize" id="pagesize" value="10">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" align="center" width="30" height="40">
						<img src="images/default/nav.gif">
					</td>
					<td>
						<font color=blue>PowerView XP </font>---
						<font class="location"><bean:message key="pvxp.oplog.title" />
						</font>
					</td>
					<td align="center" width="100">
					</td>
				</tr>
			</table>
			<table width="100%" cellspacing="1" cellpadding="3"
				class="list_table_border">
				<tr align="center">
					<td class="list_td_title" colspan="4">
						<b><bean:message key="pvxp.oplog.title" /> </b>
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.operator.operid" /> </b>
					</td>
					<td class="list_td_detail" colspan="4">
						&nbsp;&nbsp;
						<input type="text" name="operid" size="16" maxlength="4" value="">
				</tr>
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.oplog.bdate" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<input type="text" name="bopdate" size="16" maxlength="8"
							value="<%=myPubUtil.getNowDate(1)%>">
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.oplog.edate" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<input type="text" name="eopdate" size="16" maxlength="8"
							value="<%=myPubUtil.getNowDate(1)%>">
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.oplog.trcd" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<select name="trcd">
							<%
							if (tmpArray == null || tmpArray.length == 0) {
							%>
							<option value="null">
								没有对象
							</option>
							<%
							} else {
							%>
							<option value="">
								全部对象
							</option>
							<%
								int trcdnum = tmpArray.length;
								for (int i = 0; i < trcdnum; i++) {
							%>
							<option value="<%=tmpArray[i][0]%>">
								<%=tmpArray[i][1]%>
							</option>
							<%
							}}
							%>
						</select>
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.oplog.type" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<select name="type">
							<option value="">
								全部类型
							</option>
							<option value="0">
								修改
							</option>
							<option value="1">
								删除
							</option>
							<option value="2">
								添加
							</option>
						</select>
					</td>
				</tr>
			</table>

			<p></p>
			<table>
				<tr>
					<td id="delbt">
						<img src="images/default/bt_ok.gif" onFocus="this.blur()"
							onclick="javascript:dealFormSubmit();" style="cursor:hand">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/default/bt_reset.gif" onFocus="this.blur()"
							onclick="javascript:reset()" style="cursor:hand">
					</td>
				</tr>
				<table>
					</html:form>


					</div>
					<!--
<script>
<%if( !authlist.equals("*") ){%>
if(document.all['delbt'])
	document.all['delbt'].style.display='none';
<%}%>
-->
					</script>
</body>
</html:html>
