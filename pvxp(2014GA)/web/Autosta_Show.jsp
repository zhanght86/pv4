<%@ include file="inc/taglib.jsp" %>
<%@ page contentType="text/html;charset=gb2312"%>

<%@page import="java.util.*" %>
<%@page import="com.lcjr.pvxp.util.*" %>
<%@page import="com.lcjr.pvxp.orm.*" %>
<%@page import="com.lcjr.pvxp.model.*" %>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="6" minpower="2" />

<%
 	PubUtil myPubUtil = new PubUtil();
	CharSet myCharSet = new CharSet();
	String id = request.getParameter("id").trim();
	AutostaModel myAsModel = new AutostaModel();
	List AsList = (List)myAsModel.getAutostaList();
	Autosta myAs = (Autosta)myAsModel.getAutostaFromList(id,AsList);

	String statename   = myPubUtil.dealNull(myAs.getStatename()).trim();
	String statesort   = myPubUtil.dealNull(myAs.getStatesort()).trim();
	String statetype   = myPubUtil.dealNull(myAs.getStatetype()).trim();
	String bankid	     = myPubUtil.dealNull(myAs.getBankid()).trim();
	String afterday    = myPubUtil.dealNull(myAs.getAfterday()).trim();
	String aftertime   = myPubUtil.dealNull(myAs.getAftertime()).trim();
	String opentag     = myPubUtil.dealNull(myAs.getOpentag()).trim();
	String count       = myPubUtil.dealNull(myAs.getCount()).trim();
	String laststat    = myPubUtil.dealNull(myAs.getLaststat()).trim();
	if( !laststat.equals("") ) {
		if( laststat.length() == 8 ) {
			laststat = laststat.substring(0,4) + "-" + laststat.substring(4,6) + "-" + laststat.substring(6,8);
		}else if( laststat.length() == 6 ) {
			laststat = laststat.substring(0,4) + "-" + laststat.substring(4,6);
		}
	}
	String info        = myPubUtil.dealNull(myAs.getInfo()).trim();
	String banknm      = "";

	statesort = "pvxp.stat.statesort." + statesort;
	statetype = "pvxp.stat.statetype." + statetype;
	opentag   = "pvxp.stat.opentag." + opentag;

	try {
		if (!bankid.equals("")) {
			banknm = BankinfoModel.getBankinfoFromList(bankid).getBanknm().trim();
		}
	}catch ( Exception e ) {
		banknm = "";
	}
%>
<html:html locale="true">
  <head>
    <title><bean:message key="pvxp.Autosta.detail"/></title>
    <link href="style/pvxp.css" rel="stylesheet" type="text/css">
    <html:base/>
  </head>

  <body leftmargin="0" topmargin="0">

    <table width="100%" cellspacing="1" cellpadding="3" class="list_table_border">
      <!-- header -->
      <tr align="center">
        <td colspan="8" class="list_td_title"><b><bean:message key="pvxp.Autosta.detail"/></b></td>
      </tr>
      <!-- body -->
      <tr align="center">
      	<td width="13%" class="list_td_prom"><b><bean:message key="prompt.id"/></b></td>
      	<td width="20%" class="list_td_detail"><%=id%></td>
      	<td width="13%" class="list_td_prom"><b><bean:message key="prompt.statename"/></b></td>
      	<td width="20%" class="list_td_detail"><%=myCharSet.db2web(statename)%></td>
      	<td width="13%" class="list_td_prom"><b><bean:message key="prompt.statesort"/></b></td>
      	<td width="20%" class="list_td_detail"><bean:message key="<%=statesort%>"/></td>
      </tr>
      <tr align="center">
      	<td class="list_td_prom"><b><bean:message key="prompt.statetype"/></b></td>
      	<td class="list_td_detail"><bean:message key="<%=statetype%>"/></td>
      	<td class="list_td_prom"><b><bean:message key="prompt.bank"/></b></td>
      	<td class="list_td_detail"><%=myCharSet.db2web(banknm)%></td>
      	<td class="list_td_prom"><b><bean:message key="prompt.afterday"/></b></td>
      	<td class="list_td_detail"><%=afterday%></td>
      </tr>
      <tr align="center">
      	<td class="list_td_prom"><b><bean:message key="prompt.aftertime"/></b></td>
      	<td class="list_td_detail"><%=aftertime.substring(0,2)%>:<%=aftertime.substring(2,4)%></td>
      	<td class="list_td_prom"><b><bean:message key="prompt.opentag"/></b></td>
      	<td class="list_td_detail"><bean:message key="<%=opentag%>"/></td>
      	<td class="list_td_prom"><b><bean:message key="prompt.count"/></b></td>
      	<td class="list_td_detail"><%=count%></td>
      </tr>
      <tr align="center">
      	<td class="list_td_prom"><b><bean:message key="prompt.laststat"/></b></td>
      	<td class="list_td_detail"><%=laststat%></td>
      	<td class="list_td_prom"><b><bean:message key="prompt.info"/></b></td>
      	<td class="list_td_detail" colspan="3"><%=myCharSet.db2web(info)%></td>
			</tr>
    </table>

    <!-- footer -->
      <table align="center" width="40%">
        <tr align="center">
          <td><input type="button" value="¹Ø±Õ´°¿Ú" onClick="javascript:window.close();"></td>
        </tr>
      </table>

  </body>
</html:html>
