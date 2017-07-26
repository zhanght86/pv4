<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="13" minpower="1" />


<html:html locale="true">
<body>
	<%@page import="com.lcjr.pvxp.util.*"%>
	<%
	String outstr = "&";

	PubUtil myPubUtil = new PubUtil();
	int tcnum = 0;
	try{
		tcnum = Integer.parseInt(myPubUtil.ReadConfig("ReturnCode","RCode_Num","0","Trade.ini"));
	}catch(Exception e){
		tcnum = 0;
	}

	
	String tmpstr = "";
	int pos = 0;
	for(int i=0;i<tcnum;i++){
		tmpstr = myPubUtil.dealNull(myPubUtil.ReadConfig("ReturnCode","RCode_"+String.valueOf(i+1),",","Trade.ini")).trim();

		pos = tmpstr.indexOf(",");
		if(pos!=-1){
			outstr = "&RC_"+tmpstr.substring(0,pos)+"="+tmpstr.substring(pos+1)+"&";
			out.print(outstr);
		}
	}
	
%>


</body>

</html:html>
<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<html:html locale="true">
<body>

	<%
	String outstr = "&";
	CharSet myCharSet = new CharSet();
	PubUtil myPubUtil = new PubUtil();
	iniread myiniread = new iniread("Trade.ini");
	
	//BufferedRandomAccessFile braf = myPubUtil.openFile( "ini", "Trade.ini" );
	
	int tcnum = 0;
	try{
		//tcnum = Integer.parseInt(myPubUtil.ReadConfig("ReturnCode","RCode_Num","0",braf));
		tcnum = Integer.parseInt(myiniread.getIni("RCode_Num").trim());
	}catch(Exception e){
		tcnum = 0;
	}

	
	String tmpstr = "";
	int pos = 0;
	for(int i=0;i<tcnum;i++){
		//tmpstr = myPubUtil.dealNull(myPubUtil.ReadConfig("ReturnCode","RCode_"+String.valueOf(i+1),",",braf)).trim();
		tmpstr = myPubUtil.dealNull(myiniread.getIni("RCode_"+String.valueOf(i+1)));
//		tmpstr = myCharSet.form2GB(tmpstr.trim());
		pos = tmpstr.indexOf(",");
		if(pos!=-1){
			outstr = "&RC_"+tmpstr.substring(0,pos)+"="+tmpstr.substring(pos+1)+"&";
			out.print(outstr);
		}
	}
	
%>


</body>

</html:html>


