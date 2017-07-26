<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<app:checkpower funcid="9" minpower="1" />
<%@ page import="com.lcjr.pvxp.util.*"%>
<%@ page import="org.apache.log4j.*"%>

<%
	CharSet charSet = new CharSet();
	
	PubUtil pubUtil = new PubUtil();
	
	BufferedRandomAccessFile braf = pubUtil.openFile( "ini", "CardType.ini" );
	
	String[][] cardType = pubUtil.Ini2Array("ini","CardType.ini","CardCode","CCode_Num","CCode_",",","");
	String[][] FKReturnCode = pubUtil.Ini2Array("ini","CardType.ini","FKReturnCode","FKRCode_Num","FKRCode_",",","");
	String[][] ReturnCode = pubUtil.Ini2Array("ini","CardType.ini","ReturnCode","RCode_Num","RCode_",",","");
    
    int FKReturnCodeNum = Integer.parseInt( pubUtil.ReadConfig("FKReturnCode","FKRCode_Num","0",braf) );
	int cardTypeNum = Integer.parseInt( pubUtil.ReadConfig("CardCode","CCode_Num","0",braf) );
	int ReturnCodeNum = Integer.parseInt( pubUtil.ReadConfig("ReturnCode","RCode_Num","0",braf) );
	
	
	
	
	int i = 0;
	//卡类型（1：借记卡，2：信用卡，3：其他）

	String cardtype = "";
	// 设备号

	String devno = "";
	//设备类型编号

	String typeno = "";
	// 机构号

	String organno = "";
	// 身份证号

	String idcardno = "";
	// 卡密码

	//条码号
	String strcode = "";
	// 卡号

	String outcardno = "";
	//旧卡卡号
	String oldcardno = "";
	// 出卡日期

	String outcarddate = "";
	// 出卡时间

	String outcardtime = "";
	// 出卡状态（1：成功0：失败）
	String outcardstatus = "";

	String temp = "";
	
	String duanxin = "";
	String dianhua = "";
	
%>

<html:html locale="true">
<head>
	<title>发卡记录流水查询</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script>
	
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
	<html:form action="/CardOut.do">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location">发卡记录流水查询</font>
				</td>
			</tr>
		</table>

		<table align="center" width="100%" cellspacing="1" cellpadding="3"
			border="0" class="list_table_border">
			<tr align="center">
				<td class="list_td_title">
					开卡日期
				</td>
				<td class="list_td_title">
					时间
				</td>
				<td class="list_td_title">
					客户姓名
				</td>
				<td class="list_td_title">
					新卡卡号
				</td>
				<td class="list_td_title">
					审核员
				</td>

				<td class="list_td_title">
					发卡状态
				</td>
				<td class="list_td_title">
					银信通
				</td>
				<td class="list_td_title">
					电话银行
				</td>
				<td class="list_td_title">
					旧卡卡号
				</td>
				<logic:present name="Result">
					<logic:iterate id="element" name="Result">
						<%
							CardOutResult cardout = (CardOutResult) element;

							//卡类型（1：借记卡，2：信用卡，3：其他）
							cardtype = cardout.getCardtype();
						    //发卡类型
							for(int num=0;num<cardTypeNum;num++){
						   	 if (cardtype.equals(cardType[num][0].trim())) {
								 cardtype = cardType[num][1].trim();
							   }
							}

							// 设备号
							devno = cardout.getDevno();
							//设备类型编号
							typeno = cardout.getTypeno();
							// 机构号
							organno = cardout.getRemark2();							
							// 身份证号
							idcardno = cardout.getIdcardno();
							//条码号
							strcode = cardout.getStrcode();
							// 卡号
							outcardno = cardout.getOutcardno();
							// 旧卡卡号
							oldcardno = cardout.getRemark3();		
							// 出卡日期
							temp = cardout.getOutcarddate();
							outcarddate = temp.substring(0, 4) + "-" + temp.substring(4, 6)
									+ "-" + temp.substring(6, 8);
							// 出卡时间
							temp = cardout.getOutcardtime();
							outcardtime = temp.substring(0, 2) + ":" + temp.substring(2, 4)
									+ ":" + temp.substring(4, 6);
							// 出卡状态（1：成功0：失败）
							outcardstatus = cardout.getOutcardstatus();
							
							
							//发卡类型
							for(int num=0;num<FKReturnCodeNum;num++){
						   	 if (outcardstatus.equals(FKReturnCode[num][0].trim())) {
								 outcardstatus = FKReturnCode[num][1].trim();
							   }
							}

							temp = cardout.getRemark1();
							if(!temp.equals("")){

							temp=temp.trim();
							duanxin=temp.substring(0,1);
							for(int num=0;num<ReturnCodeNum;num++){
						   	 if (duanxin.equals(ReturnCode[num][0].trim())) {
								 duanxin = ReturnCode[num][1].trim();
							   }
							}
							
							dianhua=temp.substring(1,2);
							for(int num=0;num<ReturnCodeNum;num++){
						   	 if (dianhua.equals(ReturnCode[num][0].trim())) {
								 dianhua = ReturnCode[num][1].trim();
							   }
							}
							
							}else{

							
							duanxin="";
							dianhua="";

							}
							
							
							
						%>
						<tr align="center" class="list_tr<%=i++ % 2%>">
 
							<td>
								<%=outcarddate%>
							</td>
							<td>
								<%=outcardtime%>
							</td>
							<td>
								<%=strcode%>
							</td>
							<td>
								<%=outcardno%>
							</td>
							<td>
								<%=organno%>
							</td>
							<td>
								<%=outcardstatus  %>
							</td>
							<td>
								<%=duanxin%>
							</td>
							<td>
								<%=dianhua%>
							</td>
							<td>
								<%=oldcardno%>
							</td>
						</tr>
					</logic:iterate>
				</logic:present>
			</tr>
		</table>

		<table align="center">
			<tr>
				<logic:present name="PageBean">
					<logic:greaterThan name="PageBean" property="totalRow" value="0">
						<td>
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
							<logic:equal name="PageBean" property="hasMultiPage" value="true">转到第 <input
									type="text" name="page" size="2" class="page_input"> 页
					
						
						
						
						
						</td>
						<td>
							<input type="image" src="images/default/bt_go.gif"
								onfocus="this.blur()" onclick="return go()">
							</logic:equal>
						</td>


						<html:hidden name="PageBean" property="currentPage" />
						<html:hidden name="PageBean" property="totalRow" />
						<%
							String[] devNo = (String[]) request.getAttribute("devno");
							String date1 = (String) request.getAttribute("date1");
							String date2 = (String) request.getAttribute("date2");
							String id = (String) request.getAttribute("id");
							String cardno = (String) request.getAttribute("cardno");
							String time1 = (String) request.getAttribute("time1");
							String time2 = (String) request.getAttribute("time2");
							
							for (i = 0; i < devNo.length; i++) {
						%>
						<html:hidden property="termnum" value="<%=devNo[i]%>" />
						<%
						}
						%>
						<html:hidden property="date1" value="<%=date1%>" />
						<html:hidden property="date2" value="<%=date2%>" />
						<html:hidden property="id" value="<%=id%>" />
						<html:hidden property="cardno" value="<%=cardno%>" />
						<html:hidden property="time1" value="<%=time1%>" />
						<html:hidden property="time2" value="<%=time2%>" />

					</logic:greaterThan>
				</logic:present>

				<td>
					<img src="images/default/bt_back.gif"
						onclick="parent.parent.goUrl('CardOut.jsp')" style="cursor:hand">
				</td>

			</tr>
		</table>

	</html:form>
</body>
</html:html>
