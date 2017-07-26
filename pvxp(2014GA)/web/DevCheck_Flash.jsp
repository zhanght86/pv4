<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie />
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="1" />

<html:html locale="true">
<body>
	<%
		CommUtil myCommUtil = new CommUtil();
		String ret = "";
		String devno = request.getParameter("devno");
		
		if (devno == null) {
			ret = "error";
		} else {
			devno = devno.trim();
			try {
				//	System.out.println("开始要读取ENC.DLL了..");
				ret = myCommUtil.getDevStateNow(devno);
				//		System.out.println("读取ENC.DLL成功2..");
				//ret="ZZ01,040,myDTime|ZZ02,000,myDTime";
				if (ret == null || ret.indexOf("006100") != 0) {
			ret = "error";
				} else {
			PubUtil myPubUtil = new PubUtil();
			ret = (ret.substring(6)).trim();
			String mydate = myPubUtil.getNowDate(1);
			String mytime = myPubUtil.getNowTime();
			mytime = myPubUtil.replace(mytime, ":", "");
			String myDTstr = mydate + "," + mytime + "," + mydate + "," + mytime;
			ret = myPubUtil.replace(ret.trim(), "myDTime", myDTstr);
			if (ret.length() == 0)
				ret = "error";
			if (!ret.equals("error"))
				ret += "|Z001,000," + myDTstr;
				}
				
			} catch (Exception e) {
				ret = "error";
			}
		}
		
		ret = "&ret=" + ret + "&";
		out.println(ret);
	%>
</body>
</html:html>
