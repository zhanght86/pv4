<%@ include file="inc/taglib.jsp" %>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>


<%
	Cookie opCookie = null;
	PubUtil myPubUtil = new PubUtil();
	String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID,request));
	
	//��cookies���ֵ��Ϊ��
	
	opCookie = new Cookie(Constants.COOKIE_OPER_OPERID, "");
	opCookie.setMaxAge(-1);
	opCookie.setPath("/");
	response.addCookie(opCookie);

	opCookie = new Cookie(Constants.COOKIE_OPER_AUTHLIST, "");
	opCookie.setMaxAge(-1);
	opCookie.setPath("/");
	response.addCookie(opCookie);
	
	opCookie = new Cookie(Constants.COOKIE_OPER_BANKID, "" );
	opCookie.setMaxAge(-1);
	opCookie.setPath("/");
	response.addCookie(opCookie);
	OperatorModel myOperatorModel = new OperatorModel();
	boolean myres = myOperatorModel.resetState(operid,"0");

	String func = request.getParameter("func");
%>

<html:html locale="true">
  <head>
    <title><bean:message key="pvxp.application.title.main"/></title>
      <link href="style/pvxp.css" rel="stylesheet" type="text/css">
      <html:base/>
  </head>
<body>
<%
	if(!myres){
%>

<center>
�˳�ϵͳʧ��,����ϵ�߼�����Ա��������״̬
</center>

<%
	}else{
%>
<%
	if(func==null){
%>
<script>
parent.parent.window.location="index.jsp";
</script>
<%
	}else if(func.equals("1")){
%>
<script>
window.location="index.jsp";
</script>
<%
	}else{
%>
<center>
�����ύ�Ƿ�������
</center>
<%
	}
%>
<%
	}
%>
</body>
</html:html>