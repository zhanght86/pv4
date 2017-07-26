<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="2" />

<%

	BankinfoModel myBankinfoModel = new BankinfoModel();
	
	String bankid = (String) request.getParameter("bankid");
%>
str = [
<%=bankid%>
]
<br>
<%
String sBankRange = myBankinfoModel.getBankRange(bankid);
%>
BankRange = [
<%=sBankRange%>
]
<br>
<%
	String banktag = "";
	if ((sBankRange.length() - 1) == 2) {
		banktag = "1";
	} else if ((sBankRange.length() - 1) == 6) {
		banktag = "2";
	} else if (sBankRange.length() == 10) {
		banktag = "3";
	}
%>
Banktag = [
<%=banktag%>
]
<br>
<br>
<%
	Vector myVector = (Vector) myBankinfoModel.getSubBank(bankid, banktag);
	for (int i = 0; i < myVector.size(); i++) {
%>
myVector.get(
<%=i%>
) = [
<%=myVector.get(i)%>
]
<br>
<%
}
%>
