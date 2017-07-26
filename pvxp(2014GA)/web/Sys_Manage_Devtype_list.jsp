<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<app:checkpower funcid="13" minpower="2" />

<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.devtype.list" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
	function selectAll(checkBox, btn){
	  var hassall = btn.src.substr(btn.src.lastIndexOf("/")) == "/bt_clear.gif";
	  if( checkBox != null ){
	    if( checkBox.length != undefined ){
	      if( hassall ){
	        for( i=0;i<checkBox.length;i++ ){
	          if( checkBox[i].checked == true ) checkBox[i].checked=false;
	        }
	        hassall = false;
	        btn.src="images/default/bt_selectall.gif";
	      }else{
	        for( i=0;i<checkBox.length;i++ ){
	          if( checkBox[i].checked == false ) checkBox[i].checked=true;
	        }
	        hassall = true;
	        btn.src="images/default/bt_clear.gif";
	      }
	    }else{
	      if( hassall ){
	        if( checkBox.checked == true ) checkBox.checked=false;
	        hassall = false;
	        btn.src="images/default/bt_selectall.gif";
	      }else{
	        if( checkBox.checked == false ) checkBox.checked=true;
	        hassall = true;
	        btn.src="images/default/bt_clear.gif";
	      }
	    }
	  }
	}

	function isSelectedMultiBox(CheckBox){
		if(isNaN(CheckBox.length)){
			if (CheckBox.checked == true) {
				return true;
			} else {
				return false;
			}
		} else {
			for(var i=0;i<CheckBox.length;i++){
				if(CheckBox[i].checked == true){
					return true;
				}
			}
			return false;
		}
	}

	function confirmDelete(CheckBox) {
		if(!isSelectedMultiBox(CheckBox))	{
		  alert("��ѡ������һ���豸����");
		  return false;
		} else {
		  var isOK = confirm("ɾ���豸���ͽ�ɾ�����и����͵��豸���Ƿ�ȷ��Ҫɾ���豸���ͣ�");
		  if(isOK) {
		    parent.showit();
		  }
		  return isOK;
		}
	}

	function go() {
		var page = document.all.page.value;
		if (page.length>0 && page.match(/\D/)==null) {
			parent.showit();
			parent.parent.getDevTpList(document.all.page.value)
		}
	}
		</script>
</head>
<%
	Vector devTypeVector = (Vector) request.getAttribute(Constants.REQUEST_DEVTP_VECTOR);
	int totalDevCount = ((Integer) request.getAttribute(Constants.REQUEST_DEVTP_TOTALDEVCOUNT)).intValue();
	int totalPages = ((Integer) request.getAttribute(Constants.REQUEST_DEVTP_TOTALPAGES)).intValue();
	int currentPage = ((Integer) request.getAttribute(Constants.REQUEST_DEVTP_CURRENTPAGE)).intValue();
	int len = devTypeVector.size();
	int power = new OperatorModel().getPower(0, request);
	boolean hasPower = power == 2 || power == 3;
%>
<body onload="parent.hidit();"><!-- �������ظ�������ʾ��waitingͼ�Σ��ֲ�����mainfram.jsp���Ҳ�����ԭ������ -->
	<!-- head -->
	<html:form action="/DevTpDel.do" method="post" onsubmit="return confirmDelete(devtpArry)">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP </font>---
					<font class="location"><bean:message key="pvxp.devtype.list" />
					</font>
				</td>
				<td align="center" width="100">
					<%
					if (hasPower) {
					%>
					<img src="images/default/bt_add.gif" style="cursor:hand" 				onclick="parent.parent.goUrl('AddDevType.jsp')">
					<%
					}
					%>
				</td>
			</tr>
		</table>
		<!-- body -->
		<table width="100%" cellspacing="1" cellpadding="3"
			class="list_table_border">
			<tr align="center">
				<td class="list_td_title" width="15%">
					<bean:message key="pvxp.devtype.typeno" />
				</td>
				<td class="list_td_title" width="15%">
					<bean:message key="pvxp.devtype.typestate" />
				</td>
				<td class="list_td_title" width="15%">
					<bean:message key="pvxp.devtype.devtype" />
				</td>
				<td class="list_td_title" width="15%">
					<bean:message key="pvxp.devtype.devname" />
				</td>
				<td class="list_td_title" width="20%">
					<bean:message key="pvxp.devtype.devfactory" />
				</td>
				<%
				if (hasPower) {
				%>
				<td class="list_td_title" id="delbt">
					<input type="image" src="images/default/bt_delete.gif"
						align="absmiddle" />
					&nbsp;&nbsp;
					<img style="cursor:hand" src="images/default/bt_selectall.gif"
						onclick="selectAll(document.all.devtpArry, this)"
						align="absmiddle">
				</td>
				<%
				}
				%>
			</tr>
			<%
				String[] devTypeItem = null;
				for (int i = 0; i < len; i++) {
					devTypeItem = (String[]) devTypeVector.get(i);
					devTypeItem[1] = "pvxp.devtype.typestate." + devTypeItem[1];
			%>
			<tr align="left" class="list_tr<%=i % 2%>">
				<td>
					&nbsp;
					<b><a href="#" alt="����鿴��ϸ��Ϣ"
						onclick="javascript:parent.parent.getDevTpDetail('<%=devTypeItem[0]%>')"><%=devTypeItem[0]%>
					</a> </b>
				</td>
				<td>
					&nbsp;
					<bean:message key="<%=devTypeItem[1]%>" />
				</td>
				<td>
					&nbsp;
					<%=devTypeItem[2]%>
				</td>
				<td>
					&nbsp;
					<%=devTypeItem[3]%>
				</td>
				<td>
					&nbsp;
					<%=devTypeItem[4]%>
				</td>

				<%
				if (hasPower) {
				%>
				<td align="center">
					<html:multibox property="devtpArry" styleId="devtpArry"
						value="<%=devTypeItem[0]%>" />
				</td>
				<%
				}
				%>
			</tr>
			<%
			}
			%>
		</table>


		<!-- foot -->
		<%
		if (totalDevCount >= 0) {
		%>
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<form>
						<font color=blue>PowerView XP</font> ---
						<font class="location"><bean:message
								key="pvxp.devtype.list" /> </font>
				</td>
				<td align="right" valign="center">
					�豸����
					<%=totalDevCount%>
					�� ��
					<%=totalPages%>
					ҳ ��ǰΪ��
					<%=currentPage%>
					ҳ&nbsp;&nbsp;
					<%
					if (currentPage > 1) {
					%>
					<a href="#"
						onClick="parent.showit();parent.parent.getDevTpList(<%=currentPage - 1%>)">[��һҳ]</a>&nbsp;&nbsp;
					<%
					}
					%>
					<%
					if (currentPage < totalPages) {
					%>
					<a href="#"
						onClick="parent.showit();parent.parent.getDevTpList(<%=currentPage + 1%>)">[��һҳ]</a>&nbsp;&nbsp;
					<%
					}
					%>
					<%
					if (totalPages > 1) {
					%>
					ת����
					<input type="text" name="page" size="2" class="page_input">
					ҳ
				</td>
				<td>
					<img src="images/default/bt_go.gif" onClick="go()"
						style="cursor:hand">
					<%
					}
					%>
				</td>
				</form>
			</tr>
		</table>
		<%
		}
		%>
	</html:form>
</body>
</html:html>
