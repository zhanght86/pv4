<%@ include file="inc/taglib.jsp" %>
<%@ page contentType="text/html;charset=gb2312"%>

<%@page import="com.lcjr.pvxp.util.*" %>
<%@page import="com.lcjr.pvxp.model.*" %>
<%@page import="com.lcjr.pvxp.orm.*" %>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />

<%
	PubUtil myPubUtil = new PubUtil();
	CharSet mycs = new CharSet();
	String bankid = myPubUtil.dealNull(request.getParameter("bankid")).trim();
	BankinfoModel myBankinfoModel = new BankinfoModel();
	Bankinfo tmp = myBankinfoModel.getBankinfoFromList(bankid);
	String bankname = mycs.db2web(myPubUtil.dealNull(tmp.getBanknm())).trim();
%>
<html:html locale="true">
<head>
<title>Éè±¸µØÍ¼¼à¿Ø -- <%=bankname%></title>
<link href="style/pvxp.css" rel="stylesheet" type="text/css">
<html:base/>
<script language="JavaScript">
function showMoni(command,args){
	try{
		devmap_moni_state.DevMoniFlash_DoFSCommand(command, args);	
	}catch(e){}
}
</script>
</head>


<frameset rows="34,*,80" cols="*"  frameborder="1">
  <frame id="devmap_moni_console"  frameborder="1"   src="DevMapMoni_console.jsp?bankid=<%=bankid%>" noresize scrolling="no">
  <frame id="devmap_moni_main"     frameborder="1"   src="DevMapMoni_main.jsp?bankid=<%=bankid%>"  scrolling="no" onmouseout="try{devmap_moni_main.enddrag();}catch(e){}">
  <frame id="devmap_moni_state"    frameborder="1"   src="DevMapMoni_state.jsp?bankid=<%=bankid%>" scrolling="yes">
</frameset>


<noframes>
<body>
</body>
</noframes>

</html:html>
