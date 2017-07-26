<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="com.lcjr.pvxp.model.*"%>
<%@ page import="com.lcjr.pvxp.orm.*"%>
<%@ page import="com.lcjr.pvxp.util.*"%>
<%@ page import="java.util.*"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>

<%
String statesort = (String) request.getAttribute("statesort");
%>

<html:html locale="true">
<head>
	<title>报表列表</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
			var isChecked = false;
			function toggleSelectAll(imgbt, Checkbox) {
				//if( CheckBox != undefined ) {
				try {
					if (isChecked) {
						if (isNaN(Checkbox.length)) {
							Checkbox.checked = false;
						} else {
							for (i=0; i<Checkbox.length; i++) {
								Checkbox[i].checked = false;
							}
						}
						isChecked = false;
						imgbt.src="images/default/bt_selectall.gif";
					} else {
						if (isNaN(Checkbox.length)) {
							Checkbox.checked = true;
						} else {
							for (i=0; i<Checkbox.length; i++) {
								Checkbox[i].checked = true;
							}
						}
						isChecked = true;
						imgbt.src="images/default/bt_remove.gif";
					}
				}catch ( e ) {}
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
				//if( CheckBox != undefined ) {
				try {
					if(!isSelectedMultiBox(CheckBox))	{
					  alert("请至少选择一个报表。");
					} else {
					  document.all.del.value = true;
					  var isOK = confirm("是否确定要删除选定的报表？");
					  if(isOK) {
					    parent.showit();
					    ReportFormList.submit();
					  }
					}
				}catch ( e ) {
				alert(e);
				}
			}

			function go() {
				var goPage = document.all.page.value;
				if (goPage.length>0 && goPage.match(/\D/)==null) {
					parent.showit();
					return true;
				} else {
					return false;
				}
			}
		</script>
</head>

<body onload="javascript:parent.hidit();">
	<html:form action="/ReportFormList.do" onsubmit="return go()"
		styleId="ReportFormList">
		<input type="hidden" name="statesort" value="<%=statesort%>" />
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location"> <%
 if (statesort.equals("01")) {
 %> 交易统计报表列表 <%
 } else if (statesort.equals("02")) {
 %> 设备总体运行情况统计报表列表 <%
 } else if (statesort.equals("03")) {
 %> 设备故障统计报表列表 <%
 } else if (statesort.equals("04")) {
 %> 发票打印统计报表列表 <%
 } else if (statesort.equals("05")) {
 %> 设备交易统计报表列表 <%
 } else if (statesort.equals("06")) {
 %> 机构交易统计报表列表 <%
 } else if (statesort.equals("09")) {
 %> 交易失败情况明细表列表 <%
 } else if (statesort.equals("10")) {
 %> 设备开机率统计报表列表 <%
 } else if (statesort.equals("11")) {
 %> 设备类型交易统计报表列表 <%
 } else if (statesort.equals("12")) {
 %> 报修记录统计列表 <%
 } else if (statesort.equals("14")) {
 %> 交易手续费统计报表列表 <%
 } else if (statesort.equals("15")) {
 %> 交易明细表列表 <%
 } else if (statesort.equals("18")) {
 %> 操作记录统计报表列表 <%
 } else if (statesort.equals("19")) {
 %> 设备交易次数明细 <%
 } else if (statesort.equals("20")) {
 %> 卡卡转账交易明细 <%
 } else if (statesort.equals("21")) {
 %> 出卡状态记录统计列表 <%
 }else if(statesort.equals("22")){
 	%>现金交易明细统计列表<%
 }else if(statesort.equals("25")){
 
 %> 机构开机率统计<%}else if(statesort.equals("26")){ %>
 厂商故障统计<%} %></font>
				</td>
				<td>
					查看任务类型：
					<html:select property="currentflag"
						onchange="query.value='true';submit()">
						<html:option value="0">成功</html:option>
						<html:option value="1">正在执行</html:option>
						<html:option value="2">失败</html:option>
						<html:option value="-1">全部</html:option>
					</html:select>
					<html:hidden property="query" value="false" />
				</td>
				<td>
					<%
					if (statesort.equals("01")) {
					%>
					<a href="javascript:parent.parent.goUrl('JYTJ_M_Input.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					} else if (statesort.equals("02")) {
					%>
					<a href="javascript:parent.parent.goUrl('YXTJ_M_Input.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					} else if (statesort.equals("03")) {
					%>
					<a href="javascript:parent.parent.goUrl('DEVTJ_M_Input.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					} else if (statesort.equals("04")) {
					%>
					<a href="javascript:parent.parent.goUrl('INVTJ_M_Input.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					} else if (statesort.equals("05")) {
					%>
					<a href="javascript:parent.parent.goUrl('JYTJ_M_Input1.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					} else if (statesort.equals("06")) {
					%>
					<a href="javascript:parent.parent.goUrl('JYTJ_M_Input2.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					} else if (statesort.equals("09")) {
					%>
					<a href="javascript:parent.parent.goUrl('TFML_M_Input.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					} else if (statesort.equals("10")) {
					%>
					<a href="javascript:parent.parent.goUrl('DRTJ_M_Input.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					} else if (statesort.equals("11")) {
					%>
					<a href="javascript:parent.parent.goUrl('JYTJ_M_Input3.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					} else if (statesort.equals("12")) {
					%>
					<a href="javascript:parent.parent.goUrl('MQTJ_M_Input.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					} else if (statesort.equals("14")) {
					%>
					<a href="javascript:parent.parent.goUrl('JYTJ_M_Input4.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					} else if (statesort.equals("15")) {
					%>
					<a href="javascript:parent.parent.goUrl('JYML_M_Input.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					} else if (statesort.equals("18")) {
					%>
					<a href="javascript:parent.parent.goUrl('OPTJ_M_Input.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					} else if (statesort.equals("20")) {
					%>
					<a href="javascript:parent.parent.goUrl('CCZZJYMX.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					} else if (statesort.equals("19")) {
					%>
					<a href="javascript:parent.parent.goUrl('CCZZJYMX.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					} else if (statesort.equals("21")) {
					%>
					<a href="javascript:parent.parent.goUrl('CardStatisticsAdd.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					}else if(statesort.equals("22")){
					%>
					<a href="javascript:parent.parent.goUrl('page/cash/CashDetailAdd.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
					<%
					}else if(statesort.equals("25")){
					%>
				
					<a href="javascript:parent.parent.goUrl('DRTJJG_M_Input.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
							<%} else if(statesort.equals("26")){
							%>
							<a href="javascript:parent.parent.goUrl('DRTJCS_M_Input.jsp');"
						onClick="parent.showit();" onFocus="this.blur()"> <img
							src="images/default/bt_add.gif" border="0"> </a>
							<%} %>
				</td>
			</tr>
		</table>

		<logic:present name="Result">
			<table align="center" width="100%" border="0" cellpadding="1"
				cellspacing="1" class="list_table_border">
				<tr align="center" class="list_td_title">
					<td>
						报表名称
					</td>
					<td>
						所属机构
					</td>
					<td>
						创建者名称
					</td>
					<td>
						报表产生时间
					</td>
					<td>
						产生类型
					</td>

					<td>
						<a href="javascript:confirmDelete(document.all.arryDel);"
							onClick="//parent.showit();" onFocus="this.blur()"><img
								src="images/default/bt_delete.gif" border="0" align="absmiddle">
						</a>
						<a href="javascript:toggleSelectAll(document.all.selall, document.all.arryDel);"
							onClick="//parent.showit();" onFocus="this.blur()"><img
								src="images/default/bt_selectall.gif" border="0" align="absmiddle" id="selall">
						</a>
					</td>
				</tr>
				<%
					CharSet myCharSet = new CharSet();
					List result = (List) request.getAttribute("Result");
					
					String name;
					String bank;
					String owner;
					String createTime;
					String type;
					String flage;
					String fileName;
					String link;
					String stasort;
					for (int i = 0; i < result.size(); i++) {
						StaMission item = (StaMission) result.get(i);
						name = item.getStatename();
						if (name != null)
							name = name.trim();
						bank = item.getBankid();
						if (bank != null)
							bank = bank.trim();
						owner = item.getOwnerid();
						if (owner != null)
							owner = owner.trim();
						createTime = item.getTimeid();
						if (createTime != null)
							createTime = createTime.trim();
						type = item.getCreatetype();
						if (type != null)
							type = type.trim();
						flage = item.getCurrentflag();
						if (flage != null)
							flage = flage.trim();
						stasort = item.getStatesort();
						if (stasort != null)
							stasort = stasort.trim();
						fileName = type + "_" + stasort + "_" + owner + "_" + createTime + ".htm";
						link = "statresult/" + fileName;
						
						try {
							if (!bank.equals("")) {
						bank = BankinfoModel.getBankinfoFromList(bank).getBanknm().trim();
						bank = myCharSet.db2web(bank);
							}
						} catch (Exception e) {
							bank = "未知";
						}
						
						try {
							if (owner.equals("SYS")) {
						owner = "自动报表";
							} else {
						owner = new OperatorModel().getOperator(owner).getName().trim();
						owner = myCharSet.db2web(owner);
							}
						} catch (Exception e) {
							owner = "未知";
						}
						type = "pvxp.reportformlist." + type;
				%>
				<tr align="center" class="list_tr<%=i % 2%>">
					<td>
						<%
						if (flage.equals("0")) {
						%>
						<a href="<%=link%>" target="_blank"><b><%=myCharSet.db2web(name)%>
						</b> </a>
						<%
						} else {
						%>
						<%=myCharSet.db2web(name)%>
						<%
						}
						%>
					</td>
					<td>
						<%=bank%>
					</td>
					<td>
						<%=owner%>
					</td>
					<td>
						<%=createTime.substring(0, 4)%>
						-
						<%=createTime.substring(4, 6)%>
						-
						<%=createTime.substring(6, 8)%>
						<%=createTime.substring(8, 10)%>
						:
						<%=createTime.substring(10, 12)%>
						:
						<%=createTime.substring(12, 14)%>
					</td>
					<td>
						<bean:message key="<%=type%>" />
					</td>
					<td>
						<%
						if (flage.equals("0") || flage.equals("2")) {
						%>
						<html:multibox property="arryDel" value="<%=fileName%>" />
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
			<logic:greaterThan name="PageBean" property="totalPage" value="0">
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
							onclick="parent.showit();page.value = --currentPage.value;submit();">前一页</html:link>
					</logic:equal>
					<logic:equal name="PageBean" property="hasNext" value="true">
						<html:link href="#"
							onclick="parent.showit();page.value = ++currentPage.value;submit();">后一页</html:link>
					</logic:equal>
					<logic:equal name="PageBean" property="hasMultiPage" value="true">
        	转到第 <input type="text" name="page" size="2" class="page_input"> 页
		      <input type="image" src="images/default/bt_go.gif"
							onfocus="this.blur()" align="absmiddle">
					</logic:equal>
					<html:hidden name="PageBean" property="currentPage" />
					<html:hidden name="PageBean" property="totalRow" />
					<html:hidden property="del" value="false" />
				</p>
			</logic:greaterThan>
		</logic:present>
	</html:form>
</body>
</html:html>
<%
//log.debug("Exit\n");
%>
