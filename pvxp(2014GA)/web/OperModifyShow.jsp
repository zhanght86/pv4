<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="2" minpower="2" />

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.operator.modify" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
    function startOperModify(operid){
      var plist = document.all.item("powerlist");
      var pdetail = document.all.item("powerdetail");
      var pstr = "";
      if( document.all['opertype'].value == "0" ) {
        for ( i=0; i<plist.length; i++ ) {
          pstr = pstr + "0";
        }
      }else if( document.all['opertype'].value == "1" ) {
        for ( i=0; i<plist.length; i++ ) {
          pstr = pstr + "1";
        }
      }else if( document.all['opertype'].value == "2" ) {
        for ( i=0; i<plist.length; i++ ) {
          pstr = pstr + "2";
        }
      }else if( document.all['opertype'].value == "3" ) {
        for ( i=0; i<plist.length; i++ ) {
          if ( plist(i).checked ) {
            pstr = pstr + pdetail(i).value;
          }else {
            pstr = pstr + "0";
          }
        }
      }

      parent.showit();
      parent.document.all['OperModify_form'].operid.value=operid;
      parent.document.all['OperModify_form'].opername.value=OperModify_form.opername.value;
      parent.document.all['OperModify_form'].operstate.value=OperModify_form.operstate.value;
      parent.document.all['OperModify_form'].opertype.value=OperModify_form.opertype.value;
      parent.document.all['OperModify_form'].authlist.value=pstr;
      parent.document.all['OperModify_form'].operpasswd.value=OperModify_form.operpasswd.value;
      parent.document.all['OperModify_form'].submit();
    }
    function deal_custompower() {
      if( document.all['opertype'].value == "3" ) {
        document.all['custompower'].style.display = 'block';
      }else {
        document.all['custompower'].style.display = 'none';
      }
    }
    function powerlist_onClick( a ) {
      if ( a.checked ) {
        document.all[a.sourceIndex+2].selectedIndex=1;
      }else {
        document.all[a.sourceIndex+2].selectedIndex=0;
      }
    }
    function powerdetail_onChange( a ) {
      if ( a.value == '0' ) {
        document.all[a.sourceIndex-2].checked=false;
      }else {
        document.all[a.sourceIndex-2].checked=true;
      }
    }
    function form_onReset() {
      var plist = document.all.item("powerlist");
      var pdetail = document.all.item("powerdetail");
      for ( i=0; i<plist.length; i++ ) {
        plist(i).checked = false;
        pdetail(i).selectedIndex=0;
      }
      document.all['custompower'].style.display = 'none';
    }
  </script>
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">
		<form name="OperModify_form" action="#" target="pvmain">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="center" width="30" height="40">
						<img src="images/default/nav.gif">
					</td>
					<td>
						<font color=blue>PowerView XP </font>---
						<font class="location"><bean:message
								key="pvxp.operator.modify" />
						</font>
					</td>
				</tr>
			</table>
			<%
				PubUtil myPubUtil = new PubUtil();
				String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
				Operator temp = (Operator) request.getAttribute(Constants.REQUEST_OPERATOR);
				if (temp != null) {
					CharSet myCharSet = new CharSet();
					String pdstr = "";
					String myOperid = temp.getOperid().trim();
					String myState = temp.getState().trim();
					String myBankid = temp.getBankid().trim();
					String myOpertype = temp.getOpertype().trim();
					String myAuthlist = temp.getAuthlist().trim();
					String myadddate = temp.getAdddate().trim();
					String myAdddate = "";
					if (myadddate.length() < 8) {
						myAdddate = myadddate;
					} else {
						myAdddate = myadddate.substring(0, 4) + "年" + Integer.parseInt(myadddate.substring(4, 6)) + "月" + Integer.parseInt(myadddate.substring(6, 8)) + "日";
					}
					
					BankinfoModel myBankinfoModel = new BankinfoModel();
					Bankinfo myBankinfo = (Bankinfo) myBankinfoModel.getBankinfoFromList(myBankid);
					String myBanknm = myBankinfo.getBanknm().trim();
			%>
			<table width="100%" cellspacing="1" cellpadding="3"
				class="list_table_border">
				<tr align="center">
					<td class="list_td_title" colspan="4">
						<b><bean:message key="pvxp.operator.modify" />
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
						<%=myOperid.trim()%>
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.operator.name" />
						</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<input type="text" name="opername" size="16" maxlength="10"
							value="<%=myCharSet.db2web(temp.getName().trim())%>">
						<html:errors property="opername" />
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.operator.state" />
						</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<select name="operstate">
							<option value="0" <%if(myState.equals("0")){%> selected <%}%>>
								<bean:message key="pvxp.operator.typestate.0" />
							</option>
							<option value="1" <%if(myState.equals("1")){%> selected <%}%>>
								<bean:message key="pvxp.operator.typestate.1" />
							</option>
						</select>
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
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<%=myCharSet.db2web(myBanknm.trim())%>
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b>重置密码</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<input type="password" name="operpasswd" id="operpasswd" size="16"
							maxlength="6">
						<font color="red"><b>留空不重置密码</b>
						</font>
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.operator.authlist" />
						</b>
					</td>
					<td class="list_td_detail" colspan="3">
						&nbsp;
						<select name="opertype" onChange="javascript:deal_custompower();">
							<option value="0" <%if(myOpertype.equals("0")){%> selected <%}%>>
								<bean:message key="pvxp.operator.opertype.0" />
							</option>
							<option value="1" <%if(myOpertype.equals("1")){%> selected <%}%>>
								<bean:message key="pvxp.operator.opertype.1" />
							</option>
							<option value="2" <%if(myOpertype.equals("2")){%> selected <%}%>>
								<bean:message key="pvxp.operator.opertype.2" />
							</option>
							<option value="3" <%if(myOpertype.equals("3")){%> selected <%}%>>
								<bean:message key="pvxp.operator.opertype.3" />
							</option>
						</select>
					</td>
				</tr>
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
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(0,1)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(0,1);%>>
												设备信息管理
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1" <%if(pdstr.equals("1")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.2" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(1,2)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(1,2);%>>
												机构信息管理
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1" <%if(pdstr.equals("1")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.2" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(2,3)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(2,3);%>>
												操作员管理
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1" <%if(pdstr.equals("1")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.2" />
													</option>
												</select>
											</td>
										</tr>
										<tr>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(3,4)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(3,4);%>>
												远程管理
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1" <%if(pdstr.equals("1")){%>selected<%}%>><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(4,5)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(4,5);%>>
												交易监控
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1" <%if(pdstr.equals("1")){%>selected<%}%>><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(5,6)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(5,6);%>>
												设备监控
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1" <%if(pdstr.equals("1")){%>selected<%}%>><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
										</tr>
										<tr>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(6,7)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(6,7);%>>
												交易查询
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1" <%if(pdstr.equals("1")){%>selected<%}%>><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(7,8)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(7,8);%>>
												操作员操作查询
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1" <%if(pdstr.equals("1")){%>selected<%}%>><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(8,9)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(8,9);%>>
												吞卡记录查询
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1" <%if(pdstr.equals("1")){%>selected<%}%>><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
										</tr>
										<tr>
											<td class="list_td_detail" style="display:none;">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(9,10)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(9,10);%>>
												发票打印查询
											</td>
											<td class="list_td_detail" style="display:none;">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1" <%if(pdstr.equals("1")){%>selected<%}%>><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(10,11)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(10,11);%>>
												报表统计
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1" <%if(pdstr.equals("1")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.2" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(11,12)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(11,12);%>>
												设备交易统计
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1" <%if(pdstr.equals("1")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.2" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(12,13)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(12,13);%>>
												设备总体运行情况统计
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1" <%if(pdstr.equals("1")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.2" />
													</option>
												</select>
											</td>
										</tr>
										<tr>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(13,14)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(13,14);%>>
												设备故障统计
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1" <%if(pdstr.equals("1")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.2" />
													</option>
												</select>
											</td>
											<td class="list_td_detail" style="display:none;">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(14,15)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(14,15);%>>
												系统管理
											</td>
											<td class="list_td_detail" style="display:none;">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1" <%if(pdstr.equals("1")){%>selected<%}%>><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(15,16)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(15,16);%>>
												插件
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1" <%if(pdstr.equals("1")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.2" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(16,17)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(16,17);%>>
												发票领用查询
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1" <%if(pdstr.equals("1")){%>selected<%}%>><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
										</tr>
										<tr>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(17,18)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(17,18);%>>
												发票状态查询
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1" <%if(pdstr.equals("1")){%>selected<%}%>><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(18,19)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(18,19);%>>
												发票明细查询
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1" <%if(pdstr.equals("1")){%>selected<%}%>><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);"
													<%if(!(myAuthlist.substring(19,20)).equals("0")){%> checked
													<%}pdstr=myAuthlist.substring(19,20);%>>
												发票打印统计
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0" <%if(pdstr.equals("0")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1" <%if(pdstr.equals("1")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2" <%if(pdstr.equals("2")){%> selected <%}%>>
														<bean:message key="pvxp.operator.power.has" />
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
			</table>

			<p></p>
			<table>
				<tr>
					<td id="delbt">
						<img src="images/default/bt_ok.gif" onFocus="this.blur()"
							onclick="javascript:startOperModify('<%=myOperid.trim()%>');"
							style="cursor:hand">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/default/bt_reset.gif" onFocus="this.blur()"
							onclick="javascript:reset();" style="cursor:hand">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/default/bt_back.gif" onFocus="this.blur()"
							onclick="javascript:history.back();" style="cursor:hand">
					</td>
				</tr>
				<table>
					<form>

						<%
						}
						%>

						</div>
						<script>
<%if( !authlist.equals("*") ){
if(!(authlist.substring(2,3)).equals("2")){%>
if(document.all['delbt'])
	document.all['delbt'].style.display='none';
<%}}%>
if ( document.all['opertype'].value == "3" ) {
	document.all['custompower'].style.display = 'block';
}
</script>
</body>
</html:html>
