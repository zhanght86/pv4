<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="com.lcjr.pvxp.model.*"%>
<%@ page import="com.lcjr.pvxp.orm.*"%>
<%@ page import="com.lcjr.pvxp.util.*"%>
<%@ page import="java.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="6" minpower="2" />
<%
	int power = new OperatorModel().getPower(10, request);
	boolean disabled = (power == 0 || power == 1);
%>

<html:html locale="true">
<head>
	<title>报表列表</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">

var isChecked = false;

function toggleSelectAll(Button, Checkbox){
      if(isChecked){
        if (isNaN(Checkbox.length)){
          Checkbox.checked = false;
        } else {
          for (i=0; i<Checkbox.length; i++){
            Checkbox[i].checked = false;
          }
        }
        isChecked = false;
        Button.src="images/default/bt_selectall.gif";
      } else {
        if (isNaN(Checkbox.length)) {
          Checkbox.checked = true;
        } else {
          for (i=0; i<Checkbox.length; i++) {
            Checkbox[i].checked = true;
          }
        }
        isChecked = true;
        Button.src="images/default/bt_remove.gif";
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
        document.all.del.value = true;
        document.all.closeflag.value = false;
        document.all.openflag.value = false;
        if(!isSelectedMultiBox(CheckBox)) {
          alert("请至少选择一个报表。");
          return false;
        } else {
          var isOK = confirm("是否确定要删除选定的报表？");
          if(isOK) {
            parent.showit();
            STATAReportList.submit();
          }else {
          	return false;
          }
        }
      }

function confirmClose(CheckBox) {
        document.all.del.value = false;
        document.all.closeflag.value = true;
        document.all.openflag.value = false;
        if(!isSelectedMultiBox(CheckBox)) {
          alert("请至少选择一个报表。");
          return false;
        } else {
          var isOK = confirm("是否确定要关闭选定的报表？");
          if(isOK) {
            parent.showit();
            STATAReportList.submit();
          }else {
          	return false;
          }
        }
      }

function confirmOpen(CheckBox) {
        document.all.del.value = false;
        document.all.closeflag.value = false;
        document.all.openflag.value = true;
        if(!isSelectedMultiBox(CheckBox)) {
          alert("请至少选择一个报表。");
          return false;
        } else {
          var isOK = confirm("是否确定要开启选定的报表？");
          if(isOK) {
            parent.showit();
            STATAReportList.submit();
          }else {
          	return false;
          }
        }
      }

function Show_Autosta( id ) {
	      open("Autosta_Show.jsp?id="+id,null,"height=180,width=750,status=no,toolbar=no,menubar=no,location=no");
	    }

function go() {
	    	var goPage = document.all.page.value;
	    	if (goPage.length>0 && goPage.match(/\D/)==null) {
	    		parent.showit();
	    		STATAReportList.submit();
	    	} else {
	    		return false;
	    	}
	    }
    </script>
</head>

<body onload="javascript:parent.hidit();">
	<html:form action="/STATAReportList.do" styleId="STATAReportList">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" align="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location"> 自动报表列表 </font>
				</td>
				<td align="right">
					<html:select property="opentag"
						onchange="query.value='true';submit()">
						<html:option value="1">开启</html:option>
						<html:option value="0">关闭</html:option>
					</html:select>
					<html:hidden property="query" value="false" />
				</td>
				<td align="right">
					<a href="javascript:parent.parent.goUrl('STAT_A_Input.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
				</td>
			</tr>
		</table>

		<logic:present name="Result">
			<table align="center" width="100%" border="0" cellpadding="1"
				cellspacing="1" class="list_table_border">
				<tr align="center" class="list_td_title">
					<td>
						任务ID
					</td>
					<td>
						报表名称
					</td>
					<td>
						报表分类
					</td>
					<td>
						报表类型
					</td>
					<td>
						所属机构
					</td>
					<td>
						开启标志
					</td>
					<%
						List result = (List) request.getAttribute("Result");

						if (result.size() == 0){
							disabled = true;
						}
					%>
					<td>
						<%
							String opentag1 = request.getParameter("opentag");
							if (opentag1 == null) {
								opentag1 = "1";
							}
							if (opentag1.equals("1")) {
						%>
						<input type="image" src="images/default/bt_close.gif"
							onFocus="this.blur()"
							onclick="return confirmClose(document.all.arryDel);"
							style="display:<%if(disabled){%>none<%}%>;">
						<%
						} else if (opentag1.equals("0")) {
						%>
						<input type="image" src="images/default/bt_open.gif"
							onFocus="this.blur()"
							onclick="return confirmOpen(document.all.arryDel);"
							style="display:<%if(disabled){%>none<%}%>;">
						<%
						}
						%>
						<input type="image" src="images/default/bt_delete.gif"
							onFocus="this.blur()"
							onclick="return confirmDelete(document.all.arryDel);"
							style="display:<%if(disabled){%>none<%}%>;">
						<a
							href="javascript:toggleSelectAll(document.all.selall, document.all.arryDel);"
							onFocus="this.blur()" style="display:<%if(disabled){%>none<%}%>;">
							<img src="images/default/bt_selectall.gif" border="0" id="selall">
						</a>
					</td>
				</tr>
				<%
					CharSet myCharSet = new CharSet();
					String id = "";
					String name = "";
					String statesort = "";
					String statetype = "";
					String bankid = "";
					String banknm = "";
					String opentag = "";

					for (int i = 0; i < result.size(); i++) {
						Autosta myAs = (Autosta) result.get(i);
						id = myAs.getId().trim();
						name = myAs.getStatename().trim();
						statesort = myAs.getStatesort().trim();
						statesort = "pvxp.stat.statesort." + statesort;
						statetype = myAs.getStatetype().trim();
						statetype = "pvxp.stat.statetype." + statetype;
						bankid = myAs.getBankid().trim();
						opentag = myAs.getOpentag().trim();
						opentag = "pvxp.stat.opentag." + opentag;
						try {
							if (!bankid.equals("")) {
						banknm = BankinfoModel.getBankinfoFromList(bankid).getBanknm().trim();
							}
						} catch (Exception e) {
							banknm = "";
						}
				%>
				<tr align="center" class="list_tr<%=i % 2%>">
					<td>
						<a href="javascript:Show_Autosta('<%=id%>');"><b><%=id%> </b>
						</a>
					</td>
					<td>
						<%=myCharSet.db2web(name)%>
					</td>
					<td>
						<bean:message key="<%=statesort%>" />
					</td>
					<td>
						<bean:message key="<%=statetype%>" />
					</td>
					<td>
						<%=myCharSet.db2web(banknm)%>
					</td>
					<td>
						<bean:message key="<%=opentag%>" />
					</td>
					<td>
						<%
						if (power >= 2) {
						%>
						<html:multibox property="arryDel" value="<%=id%>" />
						<%
						}
						%>
					</td>
				</tr>
				<%
				}
				%>
			</table>
		</logic:present>

		<logic:present name="PageBean">
			<p align="center">
				共有
				<bean:write name="PageBean" property="totalRow" />
				条结果 共
				<bean:write name="PageBean" property="totalPage" />
				页 第
				<bean:write name="PageBean" property="currentPage" />
				页
				<logic:equal name="PageBean" property="hasPrevious" value="true">
					<html:link href="#"
						onclick="page.value = --currentPage.value;submit();">前一页</html:link>
				</logic:equal>
				<logic:equal name="PageBean" property="hasNext" value="true">
					<html:link href="#"
						onclick="page.value = ++currentPage.value;submit();">后一页</html:link>
				</logic:equal>
				<logic:equal name="PageBean" property="hasMultiPage" value="true">
        	转到第 <input type="text" name="page" size="2" class="page_input"> 页
		      <input type="image" src="images/default/bt_go.gif"
						onfocus="this.blur()" onclick="return go();" align="absmiddle">
				</logic:equal>


				<html:hidden name="PageBean" property="currentPage" />
				<html:hidden name="PageBean" property="totalRow" />
				<html:hidden property="del" value="false" />
				<html:hidden property="closeflag" value="false" />
				<html:hidden property="openflag" value="false" />


			</p>
		</logic:present>
	</html:form>
</body>
</html:html>
