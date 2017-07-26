<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="2" minpower="2" />

<%
	PubUtil myPubUtil = new PubUtil();
	String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
	String bankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
	BankinfoModel myBiModel = new BankinfoModel();
	Vector v_SubBank = myBiModel.getSubBank(bankid, 0);
	
	if ((v_SubBank != null) && (v_SubBank.size() != 0)) {
%>

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.operator.add" /></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>
	<script language="JavaScript">
    function startOperAdd(){
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

			var opid = document.all.operid.value;
			if( opid=="" ) {
				alert( "请输入正确的 操作员编号\n\n长度固定4位" );
				document.all.operid.focus();
				return false;
			}else if( !isNumber(opid) ) {
				alert( "请输入正确的 操作员编号\n\n不应包含有非数字字符" );
				document.all.operid.focus();
				return false;
			}else if( document.all.operid.value.length!=document.all.operid.maxLength ) {
				alert( "请输入正确的 操作员编号\n\n长度固定4位" );
				document.all.operid.focus();
				return false;
			}

			if( document.all.opername.value=="" ) {
				alert( "请输入 操作员名称" );
				document.all.opername.focus();
				return false;
			}

      var operpwd = document.all['OperAdd_form'].operpasswd.value;
      if ( operpwd=="" || operpwd.length<6 ) {
        alert( "<bean:message key="pvxp.errors.form.no.operpwd"/><bean:message key="pvxp.operator.passwd.promp"/>" );
        document.all['OperAdd_form'].operpasswd.focus();
        return false;
      }

      parent.showit();
      parent.document.all['OperAdd_form'].operid.value=OperAdd_form.operid.value;
      parent.document.all['OperAdd_form'].opername.value=OperAdd_form.opername.value;
      parent.document.all['OperAdd_form'].operstate.value=OperAdd_form.operstate.value;
      parent.document.all['OperAdd_form'].operpasswd.value=OperAdd_form.operpasswd.value;
      parent.document.all['OperAdd_form'].bankid.value=OperAdd_form.bankid.value;
      parent.document.all['OperAdd_form'].opertype.value=OperAdd_form.opertype.value;
      parent.document.all['OperAdd_form'].authlist.value=pstr;
      parent.document.all['OperAdd_form'].submit();
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
		<form name="OperAdd_form" action="#" target="pvmain"
			onReset="javascript:form_onReset()">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="center" width="30" height="40">
						<img src="images/default/nav.gif">
					</td>
					<td>
						<font color=blue>PowerView XP </font>---
						<font class="location"><bean:message
								key="pvxp.operator.add" /> </font>
					</td>
				</tr>
			</table>


			<table width="100%" cellspacing="1" cellpadding="3"
				class="list_table_border">
				<tr align="center">
					<td class="list_td_title" colspan="4">
						<b><bean:message key="pvxp.operator.add" /> </b>
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.operator.operid" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<input type="text" name="operid" size="16" maxlength="6">
						<html:errors property="operid" />
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.operator.name" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<input type="text" name="opername" size="16" maxlength="5">
						<html:errors property="opername" />
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.operator.state" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;
						<select name="operstate">
							<option value="0">
								<bean:message key="pvxp.operator.typestate.0" />
							</option>
							<option value="1">
								<bean:message key="pvxp.operator.typestate.1" />
							</option>
						</select>
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.operator.passwd" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<input type="password" name="operpasswd" size="16" maxlength="6">
						<b><bean:message key="pvxp.operator.passwd.promp" /> </b>
						<html:errors property="operpwd" />
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.operator.bankid" /> </b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
						<app:bankselect property="bankidselect" styleId="bankid"
							incSelf="false" />
					</td>
					<td class="list_td_prom" width="20%" align="center">
						<b>&nbsp;</b>
					</td>
					<td class="list_td_detail" width="30%">
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td class="list_td_prom" width="20%" align="center">
						<b><bean:message key="pvxp.operator.authlist" /> </b>
					</td>
					<td class="list_td_detail" colspan="3">
						&nbsp;
						<select name="opertype" onChange="javascript:deal_custompower();">
							<option value="0">
								<bean:message key="pvxp.operator.opertype.0" />
							</option>
							<option value="1">
								<bean:message key="pvxp.operator.opertype.1" />
							</option>
							<option value="2">
								<bean:message key="pvxp.operator.opertype.2" />
							</option>
							<option value="3">
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
									<b><bean:message key="pvxp.operator.authlist_custom" /> </b>
								</td>
								<td class="list_td_detail" width="*">
									<table width="100%">
										<tr>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);"
													onChange="javacsript:powerlist_onClick(this);">
												设备信息管理
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1">
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2">
														<bean:message key="pvxp.operator.power.2" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												机构信息管理
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1">
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2">
														<bean:message key="pvxp.operator.power.2" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												操作员管理
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1">
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2">
														<bean:message key="pvxp.operator.power.2" />
													</option>
												</select>
											</td>
										</tr>
										<tr>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												远程管理
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1"><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2">
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												交易监控
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1"><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2">
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												设备监控
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1"><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2">
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
										</tr>
										<tr>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												交易查询
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1"><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2">
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												操作员操作查询
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1"><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2">
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												吞卡记录查询
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1"><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2">
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
										</tr>
										<tr>
											<td class="list_td_detail" style="display:none;">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												发票打印查询
											</td>
											<td class="list_td_detail" style="display:none;">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1"><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2">
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												报表统计
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1">
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2">
														<bean:message key="pvxp.operator.power.2" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												设备交易统计
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1">
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2">
														<bean:message key="pvxp.operator.power.2" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												设备总体运行情况统计
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1">
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2">
														<bean:message key="pvxp.operator.power.2" />
													</option>
												</select>
											</td>
										</tr>
										<tr>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												设备故障统计
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1">
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2">
														<bean:message key="pvxp.operator.power.2" />
													</option>
												</select>
											</td>
											<td class="list_td_detail" style="display:none;">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												系统管理
											</td>
											<td class="list_td_detail" style="display:none;">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1"><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2">
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												插件
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1">
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2">
														<bean:message key="pvxp.operator.power.2" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												发票领用查询
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1"><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2">
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
										</tr>
										<tr>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												发票状态查询
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1"><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2">
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												发票明细查询
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<!--<option value="1"><bean:message key="pvxp.operator.power.1"/></option>-->
													<option value="2">
														<bean:message key="pvxp.operator.power.has" />
													</option>
												</select>
											</td>
											<td class="list_td_detail">
												<input type="checkbox" name="powerlist" id="powerlist"
													style="display:none;"
													onClick="javacsript:powerlist_onClick(this);">
												发票打印统计
											</td>
											<td class="list_td_detail">
												<select name="powerdetail" id="powerdetail"
													onChange="javascript:powerdetail_onChange(this);">
													<option value="0">
														<bean:message key="pvxp.operator.power.0" />
													</option>
													<option value="1">
														<bean:message key="pvxp.operator.power.1" />
													</option>
													<option value="2">
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
							onclick="javascript:startOperAdd();" style="cursor:hand">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/default/bt_reset.gif" onFocus="this.blur()"
							onclick="javascript:reset();" style="cursor:hand">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="images/default/bt_back.gif" onFocus="this.blur()"
							onclick="javascript:history.back();" style="cursor:hand">
					</td>
				</tr>
				<table>
					</form>

					</div>
					<!--
<script>
<%if( !authlist.equals("*") ){%>
if(document.all['delbt'])
	document.all['delbt'].style.display='none';
<%}%>
</script>
-->
</body>
</html:html>

<%
} else {
%>

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.operator.add" /></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">

		<br>
		<br>
		<br>

		<table width="100%" cellspacing="1" cellpadding="2"
			class="list_table_border">
			<tr>
				<td class="list_td_title">
					<b>提示</b>
				</td>
			</tr>
			<tr class="list_tr0">
				<td align="center">
					没有可用机构，所以不能添加操作员，请先
					<a href="#"
						onclick="javascript:parent.parent.goUrl('BankinfoAddShow.jsp');"><font
						color="blue">添加机构</font> </a>。
				</td>
			</tr>
		</table>

		<br>
		<br>
		<div align="center">
			<a href="javascript:parent.parent.getOperList('1');"
				onFocus="this.blur()"> <img src="images/default/bt_back.gif"
					border="0"> </a>
		</div>

	</div>
</body>
</html:html>

<%
}
%>

