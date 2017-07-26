<%@include file="inc/taglib.jsp"%>
<%@page contentType="text/html;charset=gb2312"%>

<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.ini.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="1" minpower="1" />

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.bankinfo.list" /></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script>
var isselected = false;							// 标志是否全选(true-是,false-否)

	function toggleSelectAll(Button, Checkbox) {
				var hassall = Button.src.substr(Button.src.lastIndexOf("/")) == "/bt_clear.gif";
				if( Checkbox != null ){
			    if( Checkbox.length != undefined ){
			      if( hassall ){
			        for( i=0;i<Checkbox.length;i++ ){
			          if( Checkbox[i].checked == true ) Checkbox[i].checked=false;
			        }
			        hassall = false;
			        Button.src="images/default/bt_selectall.gif";
			      }else{
			        for( i=0;i<Checkbox.length;i++ ){
			          if( Checkbox[i].checked == false ) Checkbox[i].checked=true;
			        }
			        hassall = true;
			        Button.src="images/default/bt_clear.gif";
			      }
			    }else{
			      if( hassall ){
			        if( Checkbox.checked == true ) Checkbox.checked=false;
			        hassall = false;
			        Button.src="images/default/bt_selectall.gif";
			      }else{
			        if( Checkbox.checked == false ) Checkbox.checked=true;
			        hassall = true;
			        Button.src="images/default/bt_clear.gif";
			      }
			    }
			  }
			}
	function confirmDelete(CheckBox) {
				if(!isSelectedMultiBox(CheckBox))	{
				  alert("请选择至少一个地区");
				  return false;
				} else {
				  var isOK = confirm("是否确定要删除该地区？");
				  if(isOK) {
				    parent.showit();
				  }
				  return isOK;
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
			//System.out.println(path);
			//#地区号=地区号|说明
			IniOperation ini = (IniOperation) session.getAttribute("ini");
			if (ini == null) {
				ini = new IniOperation(path);
				session.setAttribute("ini", ini);
			}
			List list = ini.getValue("AREANO");
			PubUtil myUtil = new PubUtil();
			//System.out.println(list);
		%>
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location">地区查询</font>
				</td>
			</tr>
		</table>

		<table cellspacing="1" cellpadding="3" class="list_table_border"
			width="100%">
			<html:form action="/iniDeleteArea.do"
				onsubmit="return confirmDelete(areaList)">
				<tr align="center">
					<td class="list_td_title" width="10%">
						地区号
					</td>
					<td class="list_td_title" width="50%">
						地区名
					</td>
					<td width="25%" class="list_td_title">
						<img src="images/default/bt_add.gif" style="cursor:hand"
							id="iniAddArea" onclick="parent.parent.goUrl('iniAddArea.jsp')">
						<html:image src="images/default/bt_delete.gif" />
						&nbsp;
						<img src="images/default/bt_selectall.gif" style="cursor:hand"
							id="btn_selectAll"
							onclick="toggleSelectAll(document.all.btn_selectAll, document.all.areaList)">
					</td>
				</tr>
				<%
					//System.out.println("list");
					for (int i = 0; i < list.size(); i++) {
						//System.out.println((String)list.get(i));
						String[] oneArea = ((String) list.get(i)).split("=");
						String[] strArray = oneArea[1].split("\\|");
						//System.out.println("strArray："+strArray[0]);
				%>
				<tr align="left" class="list_tr<%=i % 2%>">
					<td align="center" width="10%">
						&nbsp;
						<b><a href="#"
							onclick="javascript:parent.parent.goUrl('iniAreaModify.jsp?key=<%=strArray[0]%>')"><%=strArray[0]%>
						</a> </b>
					</td>
					<td>
						&nbsp;
						<%=myUtil.file2gb(strArray[1])%>
					</td>
					<td align="center">
						<input name="areaList" type="checkbox" value="<%=strArray[0]%>" />
					</td>
				</tr>
				<%
				}
				%>
			</html:form>
		</table>

		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location">地区查询</font>
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
