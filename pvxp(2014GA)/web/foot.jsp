<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>


<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0">

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td bgcolor="#000000" height="1" colspan="2"></td>
		</tr>
		<tr>
			<td background="images/default/title_bk.jpg">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<SCRIPT language=javascript>
<!--
function  Year_Month(){
        var  now  =  new  Date();  
        var  yy  =  now.getYear();  
        var  mm  =  now.getMonth();  
	var  mmm=new  Array();
	mmm[0]="January";
	mmm[1]="February  ";
	mmm[2]="March";
	mmm[3]="April";
	mmm[4]="May";
	mmm[5]="June";
	mmm[6]="July";
	mmm[7]="August";
	mmm[8]="September";
	mmm[9]="October";
	mmm[10]="November";
	mmm[11]="December";
	mm=mmm[mm];
        return(mm  );  }
        
        
function  thisYear(){
        var  now  =  new  Date();  
        var  yy  =  now.getYear();  
        return(yy  );  }
        
  function  Date_of_Today(){  
        var  now  =  new  Date();  
        return(now.getDate()  );  }
        
  function  CurentTime(){  
        var  now  =  new  Date();  
        var  hh  =  now.getHours();  
        var  mm  =  now.getMinutes();  
        var  ss  =  now.getTime()  %  60000;  
        ss  =  (ss  -  (ss  %  1000))  /  1000;  
        var  clock  =  hh+':';  
        if  (mm  <  10)  clock  +=  '0';  
        clock  +=  mm+':';  
        if  (ss  <  10)  clock  +=  '0';  
        clock  +=  ss;  
        return(clock);  }  
        
function  refreshCalendarClock(){
document.all.calendarClock1.innerHTML  =  Year_Month();  
document.all.calendarClock2.innerHTML  =  Date_of_Today();  
document.all.calendarClock3.innerHTML  =thisYear();  
document.all.calendarClock4.innerHTML  =  CurentTime();  }


document.write('<font  id="calendarClock1"  >  </font>&nbsp;');
document.write('<font  id="calendarClock2"  >  </font>,');
document.write('<font  id="calendarClock3"  >  </font>&nbsp;');
document.write('<font  id="calendarClock4"  >  </font>');
setInterval('refreshCalendarClock()',1000);
//-->
</SCRIPT>
			</td>
			<td background="images/default/title_bk.jpg" height="23"
				align="center" valign="center">
				:::.:: Powered by 浪潮集团 金融事业部 Copyright &copy; 2014,
				2016&nbsp;&nbsp;&nbsp;BOLD 5.2.0.0 版本
				<font color=#CCCCCC>PowerView XP</font> 2014.12.18 :::..
			</td>
		</tr>
		<tr>
			<td bgcolor="#000000" height="1" colspan="2"></td>
		</tr>
	</table>
</body>
</html:html>
