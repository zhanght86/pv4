<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.ini.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<app:checkpower funcid="1" minpower="1" />

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.bankinfo.list" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script>
var isselected = false;							// 标志是否全选(true-是,false-否)

	function selectall(box)
	{
		try{
		var i = 0;
		if(isNaN(box.length))
		{
			if(isselected)
			{
				box.checked = false;
				isselected = false;
				document.all.selall.src = 'images/default/bt_selectall.gif';
			}
			else
			{
				box.checked = true;
				isselected = true;
				document.all.selall.src = 'images/default/bt_remove.gif';
			}
		}
		else
		{
			if(isselected)
			{
				for(;i<box.length;i++)
					box[i].checked = false;
				isselected = false;
				document.all.selall.src = 'images/default/bt_selectall.gif';
			}
			else
			{
				for(;i<box.length;i++)
					box[i].checked = true;
				isselected = true;
				document.all.selall.src = 'images/default/bt_remove.gif';
			}
		}
		}catch(e){
		}
	}

	function go() {
		var page = document.all.gopage.value;
		if (page.length>0 && page.match(/\D/)==null) {
			parent.showit();
			parent.parent.getBankinfoList(page);
		}
	}
</script>
	<script language="JavaScript" src="js/pv.js"></script>
</head>
<body onload="javascript:parent.hidit();">
	<div id="show" style="position:absolute; width="
		100%" z-index:1; left: 0px; top: 0px;" align="center">
		<%
			PubUtil util = new PubUtil();
			String path = util.ReadConfig("iniPath", "path", "", "PowerView.ini");
			IniOperation ini = (IniOperation) session.getAttribute("ini");
			if (ini == null) {
				try {
					ini = new IniOperation(path);
				} catch (Exception e) {
					e.printStackTrace();
				}
				session.setAttribute("ini", ini);
			}
			//转出手续费费率|转出手续费下线|转出手续费上线|转入手续费费率|转入手续费下线|转入手续费上线|说明
			List list = ini.getValue("FL");
			PubUtil myUtil = new PubUtil();
		%>
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location">转账费率查询</font>
				</td>
			</tr>
		</table>

		<table width="100%" cellspacing="1" cellpadding="3"
			class="list_table_border">
			<tr align="center">
				<td class="list_td_title" width="18%">
					种类
				</td>
				<td class="list_td_title">
					转出手续费费率
				</td>
				<td class="list_td_title">
					转出手续费下限
				</td>
				<td class="list_td_title">
					转出手续费上限
				</td>
				<td class="list_td_title">
					转入手续费费率
				</td>
				<td class="list_td_title">
					转入手续费下限
				</td>
				<td class="list_td_title">
					转入手续费上限
				</td>

				<%
						for (int i = 0; i < list.size(); i++) {
						//System.out.println((String)list.get(i));
						String[] oneFL = ((String) list.get(i)).split("=");
						String[] strArray = oneFL[1].split("\\|");
				%>
			
			<tr align="left" class="list_tr<%=i % 2%>">
				<td>
					&nbsp;
					<b><a href="#"
						onclick="javascript:parent.parent.goUrl('iniFLModify.jsp?key=<%=oneFL[0]%>')"><%=myUtil.file2gb(strArray[6])%>
					</a>
					</b>
				</td>
				<td>
					&nbsp;
					<%=strArray[0]%>
				</td>
				<td>
					&nbsp;
					<%=strArray[1]%>
				</td>
				<td>
					&nbsp;
					<%=strArray[2]%>
				</td>
				<td>
					&nbsp;
					<%=strArray[3]%>
				</td>
				<td>
					&nbsp;
					<%=strArray[4]%>
				</td>
				<td>
					&nbsp;
					<%=strArray[5]%>
				</td>
			</tr>
			<%
			}
			%>
		</table>

		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location">转账费率查询</font>
				</td>
				<td align="right" valign="center">
				</td>
			</tr>
		</table>

	</div>
	<script>
if( document.all['gopage'] )
document.all['gopage'].focus();
</script>
</body>
</html:html>
