<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="2" />

<html:html locale="true">
<head>
	<title></title>
	<html:base />
</head>
<body>

	<%
		PubUtil myPubUtil = new PubUtil();
		String outstr = "";
		String bankid = request.getParameter("bankid");
		if (bankid == null)
			bankid = "A";
		DevinfoModel myDevinfoModel = new DevinfoModel();
		List mylist = myDevinfoModel.getDevListOfBank(bankid);
		if (mylist == null || mylist.size() == 0) {
			outstr = outstr + "&";
		} else {
			int len = mylist.size();
			for (int i = 0; i < len; i++) {
				Devinfo temp = (Devinfo) mylist.get(i);
				String devno = myPubUtil.dealNull(temp.getDevno()).trim();
				if (devno == null || devno.length() == 0) {
				} else {
			outstr = outstr + devno + ",";
				}
			}
			if (outstr.indexOf(",") != -1) {
				outstr = outstr.substring(0, outstr.length() - 1);
			}
// 			outstr = outstr + "&";
		System.out.println(outstr);
		}
	%>
	<%=outstr%>
</body>
</html:html>
