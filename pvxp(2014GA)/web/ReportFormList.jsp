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
	<title>�����б�</title>
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
					  alert("������ѡ��һ������");
					} else {
					  document.all.del.value = true;
					  var isOK = confirm("�Ƿ�ȷ��Ҫɾ��ѡ���ı���");
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
 %> ����ͳ�Ʊ����б� <%
 } else if (statesort.equals("02")) {
 %> �豸�����������ͳ�Ʊ����б� <%
 } else if (statesort.equals("03")) {
 %> �豸����ͳ�Ʊ����б� <%
 } else if (statesort.equals("04")) {
 %> ��Ʊ��ӡͳ�Ʊ����б� <%
 } else if (statesort.equals("05")) {
 %> �豸����ͳ�Ʊ����б� <%
 } else if (statesort.equals("06")) {
 %> ��������ͳ�Ʊ����б� <%
 } else if (statesort.equals("09")) {
 %> ����ʧ�������ϸ���б� <%
 } else if (statesort.equals("10")) {
 %> �豸������ͳ�Ʊ����б� <%
 } else if (statesort.equals("11")) {
 %> �豸���ͽ���ͳ�Ʊ����б� <%
 } else if (statesort.equals("12")) {
 %> ���޼�¼ͳ���б� <%
 } else if (statesort.equals("14")) {
 %> ����������ͳ�Ʊ����б� <%
 } else if (statesort.equals("15")) {
 %> ������ϸ���б� <%
 } else if (statesort.equals("18")) {
 %> ������¼ͳ�Ʊ����б� <%
 } else if (statesort.equals("19")) {
 %> �豸���״�����ϸ <%
 } else if (statesort.equals("20")) {
 %> ����ת�˽�����ϸ <%
 } else if (statesort.equals("21")) {
 %> ����״̬��¼ͳ���б� <%
 }else if(statesort.equals("22")){
 	%>�ֽ�����ϸͳ���б�<%
 }else if(statesort.equals("25")){
 
 %> ����������ͳ��<%}else if(statesort.equals("26")){ %>
 ���̹���ͳ��<%} %></font>
				</td>
				<td>
					�鿴�������ͣ�
					<html:select property="currentflag"
						onchange="query.value='true';submit()">
						<html:option value="0">�ɹ�</html:option>
						<html:option value="1">����ִ��</html:option>
						<html:option value="2">ʧ��</html:option>
						<html:option value="-1">ȫ��</html:option>
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
						��������
					</td>
					<td>
						��������
					</td>
					<td>
						����������
					</td>
					<td>
						�������ʱ��
					</td>
					<td>
						��������
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
							bank = "δ֪";
						}
						
						try {
							if (owner.equals("SYS")) {
						owner = "�Զ�����";
							} else {
						owner = new OperatorModel().getOperator(owner).getName().trim();
						owner = myCharSet.db2web(owner);
							}
						} catch (Exception e) {
							owner = "δ֪";
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
					����
					<bean:write name="PageBean" property="totalRow" />
					����� ��
					<bean:write name="PageBean" property="totalPage" />
					ҳ ��
					<bean:write name="PageBean" property="currentPage" />
					ҳ
					<logic:equal name="PageBean" property="hasPrevious" value="true">
						<html:link href="#"
							onclick="parent.showit();page.value = --currentPage.value;submit();">ǰһҳ</html:link>
					</logic:equal>
					<logic:equal name="PageBean" property="hasNext" value="true">
						<html:link href="#"
							onclick="parent.showit();page.value = ++currentPage.value;submit();">��һҳ</html:link>
					</logic:equal>
					<logic:equal name="PageBean" property="hasMultiPage" value="true">
        	ת���� <input type="text" name="page" size="2" class="page_input"> ҳ
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
