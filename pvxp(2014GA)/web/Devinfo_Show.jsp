<%@ include file="inc/taglib.jsp" %>
<%@ page contentType="text/html;charset=gb2312"%>

<%@page import="com.lcjr.pvxp.util.*" %>
<%@page import="com.lcjr.pvxp.orm.*" %>
<%@page import="com.lcjr.pvxp.model.*" %>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="2" />

<%
	PubUtil myPubUtil = new PubUtil();
	CharSet myCharSet = new CharSet();
	String devno = request.getParameter("devno").trim();
	Devinfo myDevinfo = (Devinfo)DevinfoModel.getDevFromList(devno);

	String myTypeno = myPubUtil.dealNull(myDevinfo.getTypeno()).trim();
	String myDevip = myPubUtil.dealNull(myDevinfo.getDevip()).trim();
	String myDevmac = myPubUtil.dealNull(myDevinfo.getDevmac()).trim();
	String myTypestate = myPubUtil.dealNull(myDevinfo.getTypestate()).trim();
	String myPacktype = myPubUtil.dealNull(myDevinfo.getPacktype()).trim();
	String myLocaltag = myPubUtil.dealNull(myDevinfo.getLocaltag()).trim();
	String myPolltag = myPubUtil.dealNull(myDevinfo.getPolltag()).trim();
	String myBankid = myPubUtil.dealNull(myDevinfo.getBankid()).trim();
	String mySysid = myPubUtil.dealNull(myDevinfo.getSysid()).trim();
	String myOrganno = myPubUtil.dealNull(myDevinfo.getOrganno()).trim();
	String myTellerno = myPubUtil.dealNull(myDevinfo.getTellerno()).trim();
	String myDevaddr = myCharSet.db2web(myPubUtil.dealNull(myDevinfo.getDevaddr())).trim();
	String myDutyname = myCharSet.db2web(myPubUtil.dealNull(myDevinfo.getDutyname())).trim();
	String myOrgancontact = myCharSet.db2web(myPubUtil.dealNull(myDevinfo.getOrgancontact())).trim();
	String myAutherno = myPubUtil.dealNull(myDevinfo.getAutherno()).trim();

	String myDevname = "";
	try {
		Devtype myDevtype = (Devtype)DevtypeModel.getDevTpFromList(myTypeno);
		myDevname = myCharSet.db2web((myDevtype.getDevname())).trim();
	}catch (Exception e) {
		myDevname = "";
	}

	String myBanknm = "";
	try {
		Bankinfo myBankinfo = (Bankinfo)BankinfoModel.getBankinfoFromList(myBankid);
		myBanknm = myCharSet.db2web((myBankinfo.getBanknm())).trim();
	}catch (Exception e) {
		myBanknm = "";
	}

	if ( myTypestate.equals("a") || myTypestate.equals("s") ) {
		myTypestate = "pvxp.dev.state." + myTypestate;
	}else {
		myTypestate = "pvxp.dev.state.other";
	}

	if ( myPacktype.equals("b") || myPacktype.equals("c") ) {
		myPacktype = "pvxp.devtype.packtype." + myPacktype;
	}else {
		myPacktype = "pvxp.devtype.packtype.other";
	}

	if ( myLocaltag.equals("0") || myLocaltag.equals("1") ) {
		myLocaltag = "pvxp.dev.local." + myLocaltag;
	}else {
		myLocaltag = "pvxp.dev.local.other";
	}

	if ( myPolltag.equals("0") || myPolltag.equals("1") || myPolltag.equals("9") ) {
		myPolltag = "pvxp.dev.poll." + myPolltag;
	}else {
		myPolltag = "pvxp.dev.poll.other";
	}

%>
<html:html locale="true">
  <head>
    <title><bean:message key="pvxp.dev.detail"/></title>
    <link href="style/pvxp.css" rel="stylesheet" type="text/css">
    <html:base/>
  </head>

  <body leftmargin="0" topmargin="0">

    <table width="100%" cellspacing="1" cellpadding="3" class="list_table_border">
      <!-- header -->
      <tr align="center">
        <td colspan="8" class="list_td_title"><b><bean:message key="pvxp.dev.detail"/></b></td>
      </tr>
      <!-- body -->
      <tr align="center">
      	<td width="10%" class="list_td_prom"><b><bean:message key="prompt.devno"/></b></td>
      	<td width="15%" class="list_td_detail"><%=devno%></td>
      	<td width="10%" class="list_td_prom"><b><bean:message key="prompt.typeno"/></b></td>
      	<td width="15%" class="list_td_detail"><nobr><%=myDevname%></nobr></td>
      	<td width="10%" class="list_td_prom"><b><bean:message key="prompt.devip"/></b></td>
      	<td width="15%" class="list_td_detail"><%=myDevip%></td>
      	<td width="10%" class="list_td_prom"><b><bean:message key="prompt.devmac"/></b></td>
      	<td width="15%" class="list_td_detail"><%=myDevmac%></td>
      </tr>
      <tr align="center">
      	<td class="list_td_prom"><b><bean:message key="prompt.typestate"/></b></td>
      	<td class="list_td_detail"><bean:message key="<%=myTypestate%>"/></td>
      	<td class="list_td_prom"><b><bean:message key="prompt.datatype"/></b></td>
      	<td class="list_td_detail"><bean:message key="<%=myPacktype%>"/></td>
      	<td class="list_td_prom"><b><bean:message key="prompt.localtag"/></b></td>
      	<td class="list_td_detail"><bean:message key="<%=myLocaltag%>"/></td>
      	<td class="list_td_prom"><b><bean:message key="prompt.polltag"/></b></td>
      	<td class="list_td_detail"><bean:message key="<%=myPolltag%>"/></td>
      </tr>
      <tr align="center">
      	<td class="list_td_prom"><b><bean:message key="prompt.bank"/></b></td>
      	<td class="list_td_detail"><nobr><%=myBanknm%></nobr></td>
      	<td class="list_td_prom"><b><bean:message key="prompt.sysid"/></b></td>
      	<td class="list_td_detail"><%=mySysid%></td>
      	<td class="list_td_prom"><b><bean:message key="prompt.organno"/></b></td>
      	<td class="list_td_detail"><%=myOrganno%></td>
      	<td class="list_td_prom"><b><bean:message key="prompt.tellerno"/></b></td>
      	<td class="list_td_detail"><%=myTellerno%></td>
      </tr>
      <tr align="center">
      	<td class="list_td_prom"><b><bean:message key="prompt.devaddr"/></b></td>
      	<td class="list_td_detail"><%=myDevaddr%></td>
      	<td class="list_td_prom"><b><bean:message key="prompt.dutyname"/></b></td>
      	<td class="list_td_detail"><%=myDutyname%></td>
      	<td class="list_td_prom"><b><bean:message key="prompt.organcontact"/></b></td>
      	<td class="list_td_detail"><%=myOrgancontact%></td>
      	<td class="list_td_prom"><b><bean:message key="prompt.auther"/></b></td>
      	<td class="list_td_detail"><%=myAutherno%></td>
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
