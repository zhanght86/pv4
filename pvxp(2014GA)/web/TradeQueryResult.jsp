<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<%@ page import="org.apache.log4j.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="1" />

<%
	Logger log = Logger.getLogger("com.lcjr.pvxp.TradeQueryResult_jsp");
	PubUtil pubUtil = new PubUtil();
	CharSet charSet = new CharSet();
	int i = 0;
	HashMap tradeCode = (HashMap) request.getAttribute("tradeCode");
	HashMap returnCode = (HashMap) request.getAttribute("returnCode");
	HashMap moneyType = (HashMap) request.getAttribute("moneyType");
	String f_trcdlog = (String) request.getAttribute("f_trcdlog");
	String f_mxb = (String) request.getAttribute("f_mxb");
	String f_mxb_tmp = (String) request.getAttribute("f_mxb_tmp");
	String condition = (String) request.getAttribute("condition");
	String cTrcdlog = (String) request.getAttribute("cTrcdlog");
	String cMxb = (String) request.getAttribute("cMxb");
	String cMxb_tmp = (String) request.getAttribute("cMxb_tmp");
	
	String tCode = "";
	String tType = "";
	String rCode = "";
	String rType = "";
	String money = "";
	String mCode = "";
	String mType = "";
	String tradeDate = "";
	String tradeTime = "";
	String bankid = "";
	String bankName = "";
	
	boolean b_trcdlog;
	boolean b_mxb;
	boolean b_mxb_tmp;
%>

<html:html locale="true">
<head>
	<title>��ѯ���</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">

	<script language="JavaScript">
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
	<html:form action="/TradeQuery.do" styleId="TradeQuery_id" onsubmit="return go()">
		<input type="hidden" name="f_trcdlog" value="<%=f_trcdlog%>">
		<input type="hidden" name="f_mxb" value="<%=f_mxb%>">
		<input type="hidden" name="f_mxb_tmp" value="<%=f_mxb_tmp%>">
		<input type="hidden" name="condition" value="<%=condition%>">
		<input type="hidden" name="cTrcdlog" value="<%=cTrcdlog%>">
		<input type="hidden" name="cMxb" value="<%=cMxb%>">
		<input type="hidden" name="cMxb_tmp" value="<%=cMxb_tmp%>">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="middle" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location">��ѯ���</font>
				</td>
			</tr>
		</table>

		<table align="center" width="100%" cellspacing="1" cellpadding="3"
			border="0" class="list_table_border">
			<tr align="center">
				<td class="list_td_title">
					<nobr>
						�豸���
					</nobr>
				</td>
				<td class="list_td_title">
					<nobr>
						��������
					</nobr>
				</td>
				<td class="list_td_title">
					<nobr>
						Դ�����ʺ�
					</nobr>
				</td>
				<td class="list_td_title">
					<nobr>
						Ŀ�꽻���ʺ�
					</nobr>
				</td>
				<td class="list_td_title">
					<nobr>
						��������
					</nobr>
				</td>
				<td class="list_td_title">
					<nobr>
						���׽��(�������)
					</nobr>
				</td>
				<td class="list_td_title">
					<nobr>
						������
					</nobr>
				</td>
				<td class="list_td_title">
					<nobr>
						����(���Ҷ�/��Ʊ����)
					</nobr>
				</td>
				<td class="list_td_title">
					<nobr>
						���׽��
					</nobr>
				</td>
				<td class="list_td_title">
					<nobr>
						��������
					</nobr>
				</td>
				<td class="list_td_title">
					<nobr>
						����ʱ��
					</nobr>
				</td>
			</tr>

			<logic:present name="Result">
				<logic:iterate id="element" name="Result">
					<%
						String temp;
						b_trcdlog = element.getClass().getName().equals("com.lcjr.pvxp.orm.Trcdlog");
						b_mxb = element.getClass().getName().equals("com.lcjr.pvxp.orm.Mxb");
						b_mxb_tmp = element.getClass().getName().equals("com.lcjr.pvxp.orm.Mxb_tmp");
						
						if (b_trcdlog) {
							Trcdlog trcdmx = (Trcdlog) element;
							
							tCode = trcdmx.getDevtrcd().trim();
							tType = (String) tradeCode.get(tCode);
							if (tType == null) {
								tType = tCode;
							}
							
							rCode = trcdmx.getReturnno().trim();
							rType = (String) returnCode.get(rCode);
							if (rType == null) {
								rType = rCode;
							}
							
							money = pubUtil.dealNull(trcdmx.getMoney1()).trim();
							if (money.length() > 0) {
								mCode = pubUtil.dealNull(trcdmx.getMoneytype1()).trim();
							} else {
								money = pubUtil.dealNull(trcdmx.getOther2()).trim();
								mCode = pubUtil.dealNull(trcdmx.getOther3()).trim();
							}
							mType = (String) moneyType.get(mCode);
							if (mType == null) {
								mType = mCode;
							}
							
							temp = pubUtil.dealNull(trcdmx.getDevdate()).trim();
							tradeDate = temp.substring(0, 4) + "-" + temp.substring(4, 6) + "-" + temp.substring(6, 8);
							temp = pubUtil.dealNull(trcdmx.getDevtime()).trim();
							tradeTime = temp.substring(0, 2) + ":" + temp.substring(2, 4) + ":" + temp.substring(4, 6);
							bankid = pubUtil.dealNull(trcdmx.getOther1()).trim();
							
							Bankinfo bankInfo = BankinfoModel.getBankinfoFromList(bankid);
							if (bankInfo != null) {
								bankName = charSet.db2web(bankInfo.getBanknm());
							} else {
								bankName = bankid;
							}
							
						} else if (b_mxb) {
							Mxb trcdmx = (Mxb) element;
							
							tCode = trcdmx.getDevtrcd().trim();
							tType = (String) tradeCode.get(tCode);
							if (tType == null) {
								tType = tCode;
							}
							
							rCode = trcdmx.getReturnno().trim();
							rType = (String) returnCode.get(rCode);
							if (rType == null) {
								rType = rCode;
							}
							
							money = pubUtil.dealNull(trcdmx.getMoney1()).trim();
							if (money.length() > 0) {
								mCode = pubUtil.dealNull(trcdmx.getMoneytype1()).trim();
							} else {
								money = pubUtil.dealNull(trcdmx.getOther2()).trim();
								mCode = pubUtil.dealNull(trcdmx.getOther3()).trim();
							}
							mType = (String) moneyType.get(mCode);
							if (mType == null) {
								mType = mCode;
							}
							
							temp = pubUtil.dealNull(trcdmx.getDevdate()).trim();
							tradeDate = temp.substring(0, 4) + "-" + temp.substring(4, 6) + "-" + temp.substring(6, 8);
							temp = pubUtil.dealNull(trcdmx.getDevtime()).trim();
							tradeTime = temp.substring(0, 2) + ":" + temp.substring(2, 4) + ":" + temp.substring(4, 6);
							bankid = pubUtil.dealNull(trcdmx.getOther1()).trim();
							
							Bankinfo bankInfo = BankinfoModel.getBankinfoFromList(bankid);
							if (bankInfo != null) {
								bankName = charSet.db2web(bankInfo.getBanknm());
							} else {
								bankName = bankid;
							}
							
						} else if (b_mxb_tmp) {
							Mxb_tmp trcdmx = (Mxb_tmp) element;
							
							tCode = trcdmx.getDevtrcd().trim();
							tType = (String) tradeCode.get(tCode);
							if (tType == null) {
								tType = tCode;
							}
							
							rCode = trcdmx.getReturnno().trim();
							rType = (String) returnCode.get(rCode);
							if (rType == null) {
								rType = rCode;
							}
							
							money = pubUtil.dealNull(trcdmx.getMoney1()).trim();
							if (money.length() > 0) {
								mCode = pubUtil.dealNull(trcdmx.getMoneytype1()).trim();
							} else {
								money = pubUtil.dealNull(trcdmx.getOther2()).trim();
								mCode = pubUtil.dealNull(trcdmx.getOther3()).trim();
							}
							mType = (String) moneyType.get(mCode);
							if (mType == null) {
								mType = mCode;
							}
							
							temp = pubUtil.dealNull(trcdmx.getDevdate()).trim();
							tradeDate = temp.substring(0, 4) + "-" + temp.substring(4, 6) + "-" + temp.substring(6, 8);
							temp = pubUtil.dealNull(trcdmx.getDevtime()).trim();
							tradeTime = temp.substring(0, 2) + ":" + temp.substring(2, 4) + ":" + temp.substring(4, 6);
							bankid = pubUtil.dealNull(trcdmx.getOther1()).trim();
							
							Bankinfo bankInfo = BankinfoModel.getBankinfoFromList(bankid);
							if (bankInfo != null) {
								bankName = charSet.db2web(bankInfo.getBanknm());
							} else {
								bankName = bankid;
							}
						}
					%>
					<tr align="center" class="list_tr<%=i++ % 2%>">
						<td>
							<bean:write name="element" property="devno" />
						</td>
						<td>
							<nobr>
								<%=bankName%>
							</nobr>
						</td>
						<td>
							<bean:write name="element" property="accno1" />
						</td>
						<td>
							<bean:write name="element" property="accno2" />
						</td>
						<td>
							<nobr>
								<%=tType%>
							</nobr>
						</td>
						<td>
							<%=money%>
						</td>
						<td>
							<bean:write name="element" property="money2" />
						</td>
						<td>
							<%=mType%>
						</td>
						<td>
							<nobr>
								<%=rType%>
							</nobr>
						</td>
						<td>
							<nobr>
								<%=tradeDate%>
							</nobr>
						</td>
						<td>
							<nobr>
								<%=tradeTime%>
							</nobr>
						</td>
					</tr>
				</logic:iterate>
			</logic:present>
		</table>
		<table align="center">
			<tr>
				<logic:present name="PageBean">
					<logic:greaterThan name="PageBean" property="totalPage" value="0">
						<td>
							����
							<bean:write name="PageBean" property="totalRow" />
							����� ��
							<bean:write name="PageBean" property="totalPage" />
							ҳ ��
							<bean:write name="PageBean" property="currentPage" />
							ҳ
							<logic:equal name="PageBean" property="hasPrevious" value="true">
								<html:link href="#"
									onclick="parent.showit();document.all.page.value = --document.all.currentPage.value;TradeQuery_id.submit();">ǰһҳ</html:link>
							</logic:equal>
							<logic:equal name="PageBean" property="hasNext" value="true">
								<html:link href="#"
									onclick="parent.showit();document.all.page.value = ++document.all.currentPage.value;TradeQuery_id.submit();">��һҳ</html:link>
							</logic:equal>
							<logic:equal name="PageBean" property="hasMultiPage" value="true">ת���� <input
									type="text" name="page" class="page_input"> ҳ
						
						</td>
						<td>
							<input type="image" src="images/default/bt_go.gif"
								onfocus="this.blur()">
							</logic:equal>
							&nbsp;&nbsp;
						</td>
						<html:hidden name="PageBean" property="currentPage" />
						<html:hidden name="PageBean" property="totalRow" />
						</td>
					</logic:greaterThan>
				</logic:present>
				<td>
					<image src="images/default/bt_back.gif"
						onclick="parent.parent.goUrl('TradeQuery.jsp')"
						style="cursor:hand">
				</td>
			</tr>
		</table>

	</html:form>
</body>
</html:html>
