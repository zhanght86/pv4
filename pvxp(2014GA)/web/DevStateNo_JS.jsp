<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>


<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="2" />

<html:html locale="true">
<head>
	<title></title>
	<html:base />
</head>
<body>
	<script language="javascript">
<%

	PubUtil myPubUtil = new PubUtil();
	int tcnum = 0;
	try{
		tcnum = Integer.parseInt(myPubUtil.ReadConfig("StateNo","NoNum","0","DevStateNo.ini"));
	}catch(Exception e){
		tcnum = 0;
	}
	String tmpstr = "";
	String outstr = "";
	String tmpreadystr = ",";
	for(int i=0;i<tcnum;i++){
		tmpstr = myPubUtil.dealNull(myPubUtil.ReadConfig("StateNo","StateNo"+String.valueOf(i+1),",","DevStateNo.ini")).trim();
		try{
			String[] tmpArray = myPubUtil.split(tmpstr,",");
			String myvar = "StateNo_"+tmpArray[0];
			if( tmpreadystr.indexOf(","+myvar+",")==-1 ){
				outstr ="var "+myvar+"=\""+tmpArray[1]+"\"";
				tmpreadystr+=myvar+",";
				out.println(outstr+";\n");
			}
			
		}catch(Exception e){}
	}
	
%>
</script>
</body>
</html:html>
