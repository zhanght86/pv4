<%@ include file="inc/taglib.jsp" %>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*" %>
<%@page import="com.lcjr.pvxp.util.*" %>
<%@page import="com.lcjr.pvxp.orm.*" %>
<%@page import="com.lcjr.pvxp.model.*" %>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="2" />

<html:html locale="true">
<body>
<% 
	String outstr = "&";
	CharSet myCharSet = new CharSet();
	BankinfoModel myBankinfoModel = new BankinfoModel();
	List mylist = myBankinfoModel.getBankinfoList();
	
	if( mylist!=null ){
		int len = mylist.size();
		for( int i=0;i<len;i++ ){
			Bankinfo temp=(Bankinfo)mylist.get(i);
			outstr = outstr+"BNM_"+temp.getBankid().trim()+"="+myCharSet.db2web(temp.getBanknm()).trim()+"&";
		}
	}
	out.println(outstr);
%>
</body>
</html:html>