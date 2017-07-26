<%@ include file="inc/taglib.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page session="true"%>
<%@page errorPage="error.jsp"%>
<%@page import="java.io.*"%>
<%@page import="java.io.File"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="3" minpower="2" />

<%
	String path = request.getParameter("filepath");
	String filesname = request.getParameter("filename");
	
	if (filesname != null) {
		filesname = path + filesname;
		File file1 = new File(filesname);
		filesname = new String(filesname.getBytes("ISO-8859-1"));
		File file = new File(filesname);
		DataOutputStream outp = null;
		DataInputStream inp = null;
		new FileInputStream(file.getPath());
		
		response.reset();
		response.setContentType("application/octet-stream; ");
		response.addHeader("Content-Disposition", "attachment;filename=" + file1.getName());
		response.setContentLength((int) file.length());
		
		try {
			outp = new DataOutputStream(response.getOutputStream());
			inp = new DataInputStream(new FileInputStream(file.getPath()));
			
			byte[] b = new byte[1024];
			int i = 0;
			
			while ((i = inp.read(b)) > 0) {
		outp.write(b, 0, i);
			}
			outp.flush();
			
		} catch (Exception e) {
			System.out.println("Exception = " + e);
			
		} finally {
			if (inp != null)
		inp.close();
			if (outp != null)
		outp.close();
		}
	}
%>
