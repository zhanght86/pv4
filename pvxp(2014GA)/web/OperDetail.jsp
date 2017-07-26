<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="2" minpower="1" />


<html:html locale="true">
<head>
	<title><bean:message key="pvxp.operator.detail" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script>
    function show_custompower() {
        document.all['custompower'].style.display = 'block';
        document.all['show_cp'].style.display = 'none';
        document.all['close_cp'].style.display = 'inline';
    }
    function close_custompower() {
        document.all['custompower'].style.display = 'none';
        document.all['close_cp'].style.display = 'none';
        document.all['show_cp'].style.display = 'inline';
    }
  </script>
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
					<font class="location"><bean:message
							key="pvxp.operator.detail" />
					</font>
				</td>
			</tr>
		</table>
		<%
			PubUtil myPubUtil = new PubUtil();
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			int myPower = 0;
			if (authlist.equals("*")) {
				myPower = 3;
			} else {
				myPower = Integer.parseInt(authlist.substring(2, 3));
			}
			Operator temp = (Operator) request.getAttribute(Constants.REQUEST_OPERATOR);
			String myOperid = "";
			if (temp != null) {
				CharSet myCharSet = new CharSet();
				myOperid = temp.getOperid();
				String pdstr = "";
				String myadddate = temp.getAdddate().trim();
				String myAdddate = "";
				String myAuthlist = temp.getAuthlist().trim();
				if (myadddate.length() < 8) {
					myAdddate = myadddate;
				} else {
					myAdddate = myadddate.substring(0, 4) + "年" + Integer.parseInt(myadddate.substring(4, 6)) + "月" + Integer.parseInt(myadddate.substring(6, 8)) + "日";
				}
				String myState = temp.getState();
				if (myState.equals("0")) {
					myState = "pvxp.operator.typestate.0";
				} else if (myState.equals("1")) {
					myState = "pvxp.operator.typestate.1";
				} else {
					myState = "pvxp.operator.typestate.other";
				}
				String myOpertype = temp.getOpertype().trim();
				if (myOpertype.equals("0")) {
					myOpertype = "pvxp.operator.opertype.0";
				} else if (myOpertype.equals("1")) {
					myOpertype = "pvxp.operator.opertype.1";
				} else if (myOpertype.equals("2")) {
					myOpertype = "pvxp.operator.opertype.2";
				} else if (myOpertype.equals("3")) {
					myOpertype = "pvxp.operator.opertype.3";
				}
		%>
		<table width="100%" cellspacing="1" cellpadding="3"
			class="list_table_border">
			<tr align="center">
				<td class="list_td_title" colspan="4">
					<b><bean:message key="pvxp.operator.detail" />
					</b>
				</td>
			</tr>
			<tr>
				<td class="list_td_prom" width="20%" align="center">
					<b><bean:message key="pvxp.operator.operid" />
					</b>
				</td>
				<td class="list_td_detail" width="30%">
					&nbsp;&nbsp;
					<%=myOperid%>
				</td>
				<td class="list_td_prom" width="20%" align="center">
					<b><bean:message key="pvxp.operator.name" />
					</b>
				</td>
				<td class="list_td_detail" width="30%">
					&nbsp;&nbsp;
					<%=myCharSet.db2web(temp.getName())%>
				</td>
			</tr>
			<tr>
				<td class="list_td_prom" width="20%" align="center">
					<b><bean:message key="pvxp.operator.state" />
					</b>
				</td>
				<td class="list_td_detail" width="30%">
					&nbsp;&nbsp;
					<bean:message key="<%=myState%>" />
				</td>
				<td class="list_td_prom" width="20%" align="center">
					<b><bean:message key="pvxp.operator.adddate" />
					</b>
				</td>
				<td class="list_td_detail" width="30%">
					&nbsp;&nbsp;
					<%=myAdddate%>
				</td>
			</tr>
			<tr>
				<td class="list_td_prom" width="20%" align="center">
					<b><bean:message key="pvxp.operator.bankid" />
					</b>
				</td>
				<%
				if (myAuthlist.equals("*")) {
				%>
				<td class="list_td_detail" width="30%">
					&nbsp;&nbsp;
					<bean:message key="pvxp.operator.superop" />
				</td>
				<%
							} else {
							String myBankid = temp.getBankid();
							BankinfoModel myBankinfoModel = new BankinfoModel();
							Bankinfo myBankinfo = (Bankinfo) myBankinfoModel.getBankinfoFromList(myBankid);
							String myBanknm = myBankinfo.getBanknm().trim();
				%>
				<td class="list_td_detail" width="30%">
					&nbsp;&nbsp;
					<%=myCharSet.db2web(myBanknm)%>
				</td>
				<%
				}
				%>
				<td class="list_td_prom" width="20%" align="center">
					<b>&nbsp;</b>
				</td>
				<td class="list_td_detail" width="30%">
					&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_td_prom" width="20%" align="center">
					<b><bean:message key="pvxp.operator.authlist" />
					</b>
				</td>
				<td class="list_td_detail" colspan="3">
					&nbsp;
					<%
					if (myAuthlist.equals("*")) {
					%>
					<bean:message key="pvxp.operator.supervisor" />
					<%
					} else {
					%>
					<a id="show_cp" href="#" onClick="javascript:show_custompower();">
						<u><bean:message key="<%=myOpertype%>" />
					</u> </a>
					<a id="close_cp" href="#" onClick="javascript:close_custompower();"
						style="display:none;"> <u><bean:message
								key="<%=myOpertype%>" />
					</u> </a>
					<%
					}
					%>
				</td>
			</tr>
			<%
			if (!myAuthlist.equals("*")) {
			%>
			<tr id="custompower" style="display:none;">
				<td class="list_td_prom" colspan="4">
					<table width="100%">
						<tr>
							<td class="list_td_prom" width="22" align="center">
								<b><bean:message key="pvxp.operator.authlist_custom" />
								</b>
							</td>
							<td class="list_td_detail" width="*">
								<table width="100%">
									<tr>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(0,1)).equals("0")){%> checked
												<%}pdstr="pvxp.operator.power."+myAuthlist.substring(0,1);%>>
											设备信息管理
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(1,2)).equals("0")){%> checked
												<%}pdstr="pvxp.operator.power."+myAuthlist.substring(1,2);%>>
											机构信息管理
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(2,3)).equals("0")){%> checked
												<%}pdstr="pvxp.operator.power."+myAuthlist.substring(2,3);%>>
											操作员管理
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(3,4)).equals("0")){%> checked
												<%}if(myAuthlist.substring(3,4).equals("2")){pdstr="pvxp.operator.power.has";}else{pdstr="pvxp.operator.power."+myAuthlist.substring(3,4);}%>>
											远程管理
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(4,5)).equals("0")){%> checked
												<%}if(myAuthlist.substring(4,5).equals("2")){pdstr="pvxp.operator.power.has";}else{pdstr="pvxp.operator.power."+myAuthlist.substring(4,5);}%>>
											交易监控
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(5,6)).equals("0")){%> checked
												<%}if(myAuthlist.substring(5,6).equals("2")){pdstr="pvxp.operator.power.has";}else{pdstr="pvxp.operator.power."+myAuthlist.substring(5,6);}%>>
											设备监控
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(6,7)).equals("0")){%> checked
												<%}if(myAuthlist.substring(6,7).equals("2")){pdstr="pvxp.operator.power.has";}else{pdstr="pvxp.operator.power."+myAuthlist.substring(6,7);}%>>
											交易查询
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(7,8)).equals("0")){%> checked
												<%}if(myAuthlist.substring(7,8).equals("2")){pdstr="pvxp.operator.power.has";}else{pdstr="pvxp.operator.power."+myAuthlist.substring(7,8);}%>>
											操作员操作查询
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(8,9)).equals("0")){%> checked
												<%}if(myAuthlist.substring(8,9).equals("2")){pdstr="pvxp.operator.power.has";}else{pdstr="pvxp.operator.power."+myAuthlist.substring(8,9);}%>>
											吞卡记录查询
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="list_td_detail" style="display:none;">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(9,10)).equals("0")){%> checked
												<%}if(myAuthlist.substring(9,10).equals("2")){pdstr="pvxp.operator.power.has";}else{pdstr="pvxp.operator.power."+myAuthlist.substring(9,10);}%>>
											发票打印查询
										</td>
										<td class="list_td_detail" style="display:none;">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(10,11)).equals("0")){%> checked
												<%}pdstr="pvxp.operator.power."+myAuthlist.substring(10,11);%>>
											报表统计
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(11,12)).equals("0")){%> checked
												<%}pdstr="pvxp.operator.power."+myAuthlist.substring(11,12);%>>
											设备交易统计
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(12,13)).equals("0")){%> checked
												<%}pdstr="pvxp.operator.power."+myAuthlist.substring(12,13);%>>
											设备总体运行情况统计
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(13,14)).equals("0")){%> checked
												<%}pdstr="pvxp.operator.power."+myAuthlist.substring(13,14);%>>
											设备故障统计
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
										<td class="list_td_detail" style="display:none;">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(14,15)).equals("0")){%> checked
												<%}if(myAuthlist.substring(14,15).equals("2")){pdstr="pvxp.operator.power.has";}else{pdstr="pvxp.operator.power."+myAuthlist.substring(14,15);}%>>
											系统管理
										</td>
										<td class="list_td_detail" style="display:none;">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(15,16)).equals("0")){%> checked
												<%}pdstr="pvxp.operator.power."+myAuthlist.substring(15,16);%>>
											插件
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(16,17)).equals("0")){%> checked
												<%}if(myAuthlist.substring(16,17).equals("2")){pdstr="pvxp.operator.power.has";}else{pdstr="pvxp.operator.power."+myAuthlist.substring(16,17);}%>>
											发票领用查询
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(17,18)).equals("0")){%> checked
												<%}if(myAuthlist.substring(17,18).equals("2")){pdstr="pvxp.operator.power.has";}else{pdstr="pvxp.operator.power."+myAuthlist.substring(17,18);}%>>
											发票状态查询
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(18,19)).equals("0")){%> checked
												<%}if(myAuthlist.substring(18,19).equals("2")){pdstr="pvxp.operator.power.has";}else{pdstr="pvxp.operator.power."+myAuthlist.substring(18,19);}%>>
											发票明细查询
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
										<td class="list_td_detail">
											<input type="checkbox" name="powerlist" id="powerlist"
												style="display:none;" disabled
												<%if(!(myAuthlist.substring(19,20)).equals("0")){%> checked
												<%}pdstr="pvxp.operator.power."+myAuthlist.substring(19,20);%>>
											发票打印统计
										</td>
										<td class="list_td_detail">
											<select name="powerdetail" id="powerdetail" disabled>
												<option value="0">
													<bean:message key="<%=pdstr%>" />
												</option>
											</select>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<%
			}
			%>
		</table>
		<%
		}
		%>

		<p></p>
		<script>
function _onSubmit() {
	var delok = confirm('是否确定要删除操作员？');
	if(delok){
		parent.showit();
		OperList_del.submit();
	}
}
</script>
		<table>
			<tr>
				<td id="delbt">
					<html:form action="/OperDelete.do" method="post"
						styleId="OperList_del">
						<%
						if (authlist.equals("*") || myPower > 1) {
						%>
						<a
							href="javascript:parent.parent.getOperModifyShow('<%=temp.getOperid()%>');"
							onClick="parent.showit();" onFocus="this.blur()"> <img
								src="images/default/bt_modify.gif" border="0"> </a>
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	<a href="javascript:_onSubmit();" onClick="//parent.showit();"
							onFocus="this.blur()"> <img
								src="images/default/bt_delete.gif" border="0"> </a>
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <%
        }
        %>
						<a href="javascript:history.go(-1);" onClick="parent.showit();"
							onFocus="this.blur()"> <img src="images/default/bt_back.gif"
								border="0"> </a>
						<input type="hidden" value="<%=myOperid%>" name="operArry"
							id="operArry">
					</html:form>
				</td>
			</tr>
			<table>

				</div>
</body>
</html:html>
