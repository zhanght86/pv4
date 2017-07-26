<%@include file="inc/taglib.jsp"%>
<%@page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="java.io.File"%>
<%@page import="java.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp" %>
<app:checkpower funcid="3" minpower="2" />

<%@page session="true"%>
<%@page buffer="8kb"%>
<%@page autoFlush="true"%>

<%@page import="java.util.*,java.io.*,java.net.*"%>
<%
	PubUtil myPubUtil = new PubUtil();
	String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
	
	String fileop = myPubUtil.dealNull(request.getParameter("fileop")).trim();
	String filename = request.getParameter("filename");
	
	String path = request.getRealPath("") + "/upload/" + operid;
	
	if (fileop.equals("delfile")) {
		String filearray = request.getParameter("filearray");
		StringTokenizer wb = new StringTokenizer(filearray, ",");
		Vector fileVector = new Vector();
		
		while (wb.hasMoreTokens()) {
			fileVector.add(wb.nextToken());
		}
		
		File filedel = null;
		String filedelname = "";
		for (int i = 0; i < fileVector.size(); i++) {
			filedelname = path + "/" + fileVector.get(i);
			filedel = new File(filedelname);
			if (filedel.exists()) {
		filedel.delete();
			}
		}
%>
<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" onselectstart="return false;"
	class="list_td_detail">
	<bean:message key="pvxp.filedistribute.notice.succdelete" />
	<script>
    setTimeout('window.location="FileList.jsp"',3000);
  </script>
</body>
</html:html>
<%
	return;
	}
	
	if (filename != null) {
		filename = path + "/" + filename;
		File file = new File(filename);
		
		response.reset();
		response.setContentType("application/octet-stream; charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=" + file.getName());
		response.setContentLength((int) file.length());
		byte[] buffer = new byte[4096];
		
		DataOutputStream output = null;
		DataInputStream input = null;
		
		
		//写缓冲区
		try {
			output = new DataOutputStream(response.getOutputStream());
			input = new DataInputStream(new FileInputStream(file.getPath()));
			
			while (true) {
		byte b = input.readByte();
		output.writeByte(b);
			}
		} catch (Exception e) // maybe user cancelled download
		{
			//fileout.println("Exception = "+e);
		} finally {
			if (input != null)
		input.close();
			if (output != null)
		output.close();
		}
	}
%>
<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script>
    function deal_del_file() {
      var filechecklist = document.all.item("filecheck");
      var filearray = new Array();

 			if( document.all.filecheck != null ) {
				if( document.all.filecheck.length != undefined ) {
					for( i=0; i<document.all.filecheck.length; i++ ) {
						if( document.all.filecheck(i).checked == true ) {
							filearray[filearray.length] = document.all[filechecklist(i).sourceIndex+1].innerText;
						}
					}
				}else {
					if( document.all.filecheck.checked == true ) {
						filearray[filearray.length] = document.all[document.all.filecheck.sourceIndex+1].innerText;
					}
				}
			}else {
				alert( "<bean:message key='pvxp.filedistribute.notice.nofilesel'/>" );
				return false;
			}

     if( filearray.length < 1 ) {
        alert( '<bean:message key="pvxp.filedistribute.notice.nofilesel"/>' );
        return false;
      }

			var delflag = confirm("将要删除文件，是否继续?");
			if( delflag ) {
	      document.all['fileop'].value = "delfile";
	      document.all['filearray'].value = filearray;
	      document.all['filelistarray'].submit();
	      return filearray;
			}
    }
    function deal_dis_file() {
      var filechecklist = document.all.item("filecheck");
      var filearray = new Array();

 			if( document.all.filecheck != null ) {
				if( document.all.filecheck.length != undefined ) {
					for( i=0; i<document.all.filecheck.length; i++ ) {
						if( document.all.filecheck(i).checked == true ) {
							filearray[filearray.length] = document.all[filechecklist(i).sourceIndex+1].innerText;
						}
					}
				}else {
					if( document.all.filecheck.checked == true ) {
						filearray[filearray.length] = document.all[document.all.filecheck.sourceIndex+1].innerText;
					}
				}
			}else {
				alert( "<bean:message key='pvxp.filedistribute.notice.nofilesel'/>" );
				return false;
			}

      if( filearray.length < 1 ) {
        alert( '<bean:message key="pvxp.filedistribute.notice.nofilesel"/>' );
        filearray = new Array();
        return filearray;
      }

      return filearray;
    }
  </script>
</head>
<body leftmargin="0" topmargin="0" onselectstart="return false;"
	class="list_td_detail">
	<%
		File cdir = new File(path);
		if (!cdir.exists()) {
	%>
	<bean:message key="pvxp.filedistribute.notice.nofilelist" />
	<%
			} else {
			File[] cfiles = cdir.listFiles();
			if (cfiles.length > 0) {
				for (int i = 0; i < cfiles.length; i++) {
	%>
	<form name="file<%=i%>" method="post">
		<input type="hidden" name="filename"
			value="<%=(cfiles[i].getName()).trim()%>">
	</form>
	<%
	}
	%>
	<form name="filelistarray" method="post"
		onsubmit="javascript:deal_del_file();">
		<input type="hidden" name="fileop">
		<input type="hidden" name="filearray">
		<table width="100%">
			<%
			for (int i = 0; i < cfiles.length; i++) {
			%>
			<tr>
				<td width="50%">
					<input type="checkbox" name="filecheck" id="filecheck">
					<a href="javascript:file<%=i%>.submit();" tartget="_blank"><%=(cfiles[i].getName()).trim()%>
					</a>
					<br>
				</td>
				<%
							if ((i + 1) < cfiles.length) {
							i++;
				%>
				<td width="50%">
					<input type="checkbox" name="filecheck" id="filecheck">
					<a href="javascript:file<%=i%>.submit();" tartget="_blank"><%=(cfiles[i].getName()).trim()%>
					</a>
					<br>
				</td>
				<%
				}
				%>
			</tr>
			<%
			}
			%>
		</table>
		<%
		} else {
		%>
		<bean:message key="pvxp.filedistribute.notice.nofilelist" />
		<%
			}
			}
		%>
		<table style="display:none;">
			<tr>
				<td id="delbt">
					<input type="submit"value"删除文件"">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="reset"value"复位">
					>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<table>
				</form>
</body>
</html:html>
