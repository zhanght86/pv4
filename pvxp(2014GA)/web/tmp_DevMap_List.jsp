<%@ page contentType="text/html;charset=gb2312"%>
<%@ include file="inc/taglib.jsp"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="5" minpower="2" />

<html>
	<head>
		<title></title>
		<link href="style/pvxp.css" rel="stylesheet" type="text/css">
		<script>
function goFullscreen(targeturl,mtarget){
	var newwin=window.open(targeturl,mtarget,'scrollbars');
	if(document.all){
		newwin.moveTo(0,0);
		newwin.resizeTo(screen.width,screen.height);
	}
}
function doMapMoni(){
	goFullscreen('',document.all['sform'].target);
	return true;
}
</script>
	</head>
	<body onload="javascript:parent.hidit();">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location"><bean:message key="pvxp.devmap.list" />
					</font>
				</td>
			</tr>
		</table>
		<table width="100%" cellspacing="1" cellpadding="2"
			class="list_table_border">
			<tr>
				<td class="list_td_title">
					<center>
						<b>��������</b>
					</center>
				</td>
				<td class="list_td_title">
					<center>
						<b>������</b>
					</center>
				</td>
				<td class="list_td_title">
					<center>
						<b>����������</b>
					</center>
				</td>
				<td class="list_td_title">
					<center>
						<b>ֱ���豸��</b>
					</center>
				</td>
				<td class="list_td_title">
					<center>
						<b>����޸���</b>
					</center>
				</td>
				<td class="list_td_title">
					<center>
						<b>����޸�ʱ��</b>
					</center>
				</td>
				<td class="list_td_title">
					<center>
						<b>����</b>
					</center>
				</td>
			</tr>
	</body>
</html>
