o<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="6" minpower="1" />


<html:html locale="true">
<head>
	<title></title>
	<html:base />
</head>
<body>

	<%
		//通过jsp来读取ini文件
		CharSet myCharSet = new CharSet();
		PubUtil myPubUtil = new PubUtil();
		iniread myiniread = new iniread("Trade.ini");
		int tcnum = 0;
		try {
			
			tcnum = Integer.parseInt(myiniread.getIni("TCode_Num").trim());
		} catch (Exception e) {
			tcnum = 0;
		}
		//将查询到的交易类型数据转化为json格式
		Gson gson = new Gson();
		JsonArray arrjson = new JsonArray();
		JsonObject obj; 
		for (int i = 0; i < tcnum; i++) {
			obj = new JsonObject();
			String tmpstr = myPubUtil.dealNull(myiniread.getIni("TCode_" + String.valueOf(i + 1)));
//			tmpstr = myCharSet.form2GB(tmpstr.trim());
			String[] strarr = tmpstr.split(",");
			obj.addProperty("tode", strarr[0]);
			obj.addProperty("tvalue", strarr[1]);
			arrjson.add(obj);
		}
		String outstr = gson.toJson(arrjson);
		System.out.println(outstr);
	%>
	<%=outstr%>
</body>
</html:html>
