<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="java.util.*"%>
<%@page import="com.lcjr.pvxp.util.*"%>
<%@page import="com.lcjr.pvxp.orm.*"%>
<%@page import="com.lcjr.pvxp.bean.*"%>
<%@page import="com.lcjr.pvxp.model.*"%>
<%@ page import="org.apache.log4j.*"%>

<app:validateCookie />
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="6" minpower="1" />

<%
	Logger log = Logger.getLogger("com.lcjr.pvxp.TradeQuery_jsp");

	PubUtil pubUtil = new PubUtil();

	//�����һҳ���е� ������ţ��Ƿ�����Ի�������ʾ�豸���豸����
	String mbankid = pubUtil.dealNull(request.getParameter("mbankid")).trim();
	String mincsubs = pubUtil.dealNull(request.getParameter("mincsubs")).trim();
	String slctdevs = pubUtil.dealNull(request.getParameter("ifsdevs")).trim();
	String devtype = pubUtil.dealNull(request.getParameter("mdevtype")).trim();

	//�豸����Ϊ�գ���ѡ��ȫ���豸����
	if (devtype.equals(""))
		devtype = "A";
	if (mincsubs.equals(""))
		mincsubs = "1";

	//��cookie���ҳ�����id���û�Ȩ��
	String opbank = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID,request)).trim();
	String authlist = pubUtil.dealNull(	pubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST,request)).trim();

	//ҳ��bankidΪ�գ���ѡ�û���¼��id
	if (mbankid.equals(""))
		mbankid = opbank;

	String mkey = "";
	String mkeyvalue = "";
	boolean showsub = true;

	if (authlist.equals("*")) {
		mkey = "pvxp.bankinfo.select.all";
		mkeyvalue = opbank;
		if (mbankid.equals(opbank)) {
			showsub = false;
			mincsubs = "1";
		}
	}
%>

<html:html locale="true">
<head>
	<title></title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
	<script language="JavaScript" src="js/pv.js"></script>
	<script>
	
//
function listDevs(){
  parent.showit();
  if( document.all.includeSubBank.checked==true){
    document.all.mincsubs.value="1";
  }else{
    document.all.mincsubs.value="0";
  }
  if(document.all.slctdevs.checked==true){
    document.all.ifsdevs.value="1";
  }else{
    document.all.ifsdevs.value="0";
  }
  document.all.mbankid.value=document.all.bankid.value;
  document.all.mdevtype.value=document.all.devtp.value;
  document.all.relistdev.submit();
}


//���豸��ѯ
function changelistdev(){
  if( document.all.slctdevs.checked==true ){
    document.all.ifsdevs.value="1";
    document.all.devlistarea.style.display="";
    document.all.sallbt.style.display="";
  }else{
    document.all.ifsdevs.value="0";
    document.all.devlistarea.style.display="none";
    document.all.sallbt.style.display="none";
  }
}


//ȫѡ
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

//
function collapse(content,checkBox, btn, flag) {
  if (flag == "show") {
    content.style.display = "block";
    btn.style.visibility = "visible";
  } else {
  	btn.src = "images/default/bt_clear.gif";
  	selectAll(checkBox, btn);
    content.style.display = "none";
    btn.style.visibility = "hidden";
  }
}


//
function isDate(date) {
	if (date.length != 8) {
		return false;
	}

	if (date.match(/\D/) != null) {
		return false;
	}

	var m = date.substr(4, 2);
	var d = date.substr(6, 2);

	if (m > 12) {
		return false;
	}

	if (d > 31) {
		return false;
	}

	return true;
}


//���ҳ��
function checkForm(formName) {
  var money1 = formName.money1.value;
  var money2 = formName.money2.value;
  var date1  = formName.date1.value;
  var date2  = formName.date2.value;
  var currentDateMin= formName.currentDateMin.value;
  var currentDateMax= formName.currentDateMax.value;
  var historyDate 	= formName.historyDate.value.split(",");
  var tempDate1  	= date1.substr(0, 6);
  var tempDate2  	= date2.substr(0, 6);

  if (money1.length>0 && money2.length>0 && money1>money2) {
  	alert("����Ľ�����");
  	return false;
  }

  if (date1.length>0 && !isDate(date1)) {
		alert("��������ڸ�ʽ����ȷ��");
		return false;
  }

  if (date2.length>0 && !isDate(date2)) {
		alert("��������ڸ�ʽ����ȷ��");
		return false;
  }

  if (date1.length>0 && date2.length>0 && date1>date2) {
  	alert("��ʼ����ҪС�ڽ������ڡ�");
  	return false;
  }

  formName.f_trcdlog.value = "false";
  //	û�н�������  �� ��ʼ����Ϊ�ջ�С�ڵ�ǰ����									��ֹ���ڵ��ڵ�ǰ����   ��   ��ʼ����Ϊ�ջ�С�ڵ�ǰ����							��ʼ���� ���� ��ǰ����   ��  ��ֹ����Ϊ�ջ�С�ڵ�ǰ����
  if ((date2.length==0 && (date1.length==0 || date1<=currentDateMax)) || (date2==currentDateMax && (date1.length==0 || date1<=currentDateMax)) || (date1==currentDateMax && (date2.length==0 || date2<=currentDateMax)) ) 
 {
  	formName.f_trcdlog.value = "true";
  }

	formName.f_mxb.value = "false"; 
	// ��������Ϊ��  ��   ��ֹ���ڴ�����ʷ���� ��  ��������С�ڵ��ڵ�ǰ����               ��        ��ʼ����Ϊ�ջ�С�ڵ�ǰ����
  if ((date2.length==0 || (date2>=currentDateMin && date2<=currentDateMax)) && (date1.length==0 || date1<=currentDateMax)  ) {
  	formName.f_mxb.value = "true";
  }

	formName.f_mxb_tmp.value = "false";
	
	if (tempDate1.length == 0) {
		formName.f_mxb_tmp.value = true;
	} else {
		for (i=0; i<historyDate.length; i++) {
			if (tempDate1 == historyDate[i]) {
				formName.f_mxb_tmp.value = "true";
				break;
			}
		}
	}

	if (formName.f_trcdlog.value=="false" && formName.f_mxb.value=="false" && formName.f_mxb_tmp.value=="false") {
		alert("��ǰ�ɲ�ѯ�Ľ�����ϸ����Ϊ��" + currentDateMin + "��" + currentDateMax + "\n" +
					"��ǰ�ɲ�ѯ����ʱ��ϸ����Ϊ��" + formName.historyDate.value);
		return false;
	} else {
		parent.showit();
		return true;
	}
}
</script>
</head>
<body onload="javascript:parent.hidit();">

	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" valign="middle" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP</font> ---
				<font class="location">���ײ�ѯ</font>
			</td>
		</tr>
	</table>
	<%
		SysParam sysParam = SysParamBean.getSysParam();
		String bdofmxb;
		String monthsoftmptable;
		if (sysParam == null) {
			bdofmxb = "";
			monthsoftmptable = "";
		} else {
			bdofmxb = pubUtil.dealNull(sysParam.getBdofmxb()).trim();
			monthsoftmptable = pubUtil.dealNull(sysParam.getMonthsoftmptable()).trim();
		}

		if (monthsoftmptable.length() == 0) {
			monthsoftmptable = "��ǰû�пɲ�ѯ����ʱ��ϸ���ݡ�";
		}
		String today = pubUtil.getNowDate(1);
		String availableData;
		if (bdofmxb.equals("")) {
			availableData = today + "֮ǰ��";
		} else {
			availableData = bdofmxb + " �� " + today;
		}
	%>

	<table border="0" width="100%" border="1" cellspacing="1"
		cellpadding="2" class="list_table_border">
		<tr>
			<td class="list_td_title" style="font: bold">
				���ײ�ѯ
			</td>
		</tr>
		<tr>
			<td class="list_td_prom">
				�����Բ�ѯ�����ն��������׵���ʷ��¼�����Ը��ݲ���Ա�����Ĳ�ѯ������ѯ������Ľ������ݡ����������ĳ���ѯ��������������������ơ�Դ�ʺź�Ŀ���ʺŲ���Ҫ�����������������ڵĸ�ʽΪ
				<font color="red">YYYYMMDD</font>����
				<font color="red">20120312</font>����ʼ����Ҫ���ڽ������ڡ�
				<br>
				�����Ҫ��ѯ��ʷ��ϸ������ϵϵͳ����Ա������ʷ���ݡ�
			</td>
		</tr>
	</table>

	<p></p>

	<html:form action="/TradeQuery.do" onsubmit="return checkForm(this);">
		<table border="0" width="100%" border="1" cellspacing="1"
			cellpadding="2" class="list_table_border">
			<tr>
				<td class="list_td_prom" width="180">
					��ǰ�ɲ�ѯ�Ľ�����ϸ����Ϊ��
				</td>
				<td class="list_td_detail" style="color: red">
					<%=availableData%>
				</td>
			</tr>
			<tr>
				<td class="list_td_prom">
					��ǰ�ɲ�ѯ����ʱ��ϸ����Ϊ��
				</td>
				<td class="list_td_detail" style="color: red">
					<%=monthsoftmptable%>
				</td>
			</tr>

			<input type="hidden" name="currentDateMin" value="<%=bdofmxb%>">
			<input type="hidden" name="currentDateMax" value="<%=today%>">
			<input type="hidden" name="historyDate" value="<%=monthsoftmptable%>">
			<input type="hidden" name="f_trcdlog" value="false">
			<input type="hidden" name="f_mxb" value="false">
			<input type="hidden" name="f_mxb_tmp" value="false">
		</table>



		<table border="0" width="100%" border="1" cellspacing="1"
			cellpadding="2" class="list_table_border">
			<tr>
				<td class="list_td_title" width="473">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr valign="middle">
							<td>
								����
							</td>
							<td>
								&nbsp;
								<app:bankselect property="bankid" defaultValue="<%=mbankid%>"
									onChange="listDevs();" key="<%=mkey%>"
									keyValue="<%=mkeyvalue%>" />
							</td>
							<td>
								&nbsp;&nbsp;
								<input type="checkbox" onClick="listDevs()" <%if(!showsub){%>
									style="display:none" <%}%> name="includeSubBank"
									id="includeSubBank" <%if(mincsubs.equals("1")){%> checked <%}%>>
							</td>
							<td>
								<%
								if (showsub) {
								%>
								�����ӻ���
								<%
								}
								%>
							</td>
							<td>
								&nbsp;
								<input type="checkbox" onClick="changelistdev()" id="slctdevs"
									<%if(slctdevs.equals("1")){%> checked <%}%>>
							</td>
							<td>
								���豸��ѯ
							</td>
						</tr>
					</table>
				</td>


				<td class="list_td_title" align="left">
					<div id="sallbt" <%if(!slctdevs.equals("1")){%>
						style="display:none" <%}%>>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr valign="middle">
								<td>
									�豸����
								</td>
								<td>
									&nbsp;
									<app:devtpselect property="devtp" key="pvxp.devtype.select.all"
										keyValue="A" onChange="listDevs();"
										defaultValue="<%=devtype%>" showName="1" />
								</td>
								<td>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<img style="cursor:hand" src="images/default/bt_selectall.gif"
										onclick="selectAll(document.all.devno, this)">
								</td>
							</tr>
						</table>
					</div>
					<html:hidden property="incsubs" value="" />
					<html:hidden property="devs" value="" />
				</td>
			</tr>
			<tr id="devlistarea" <%if(!slctdevs.equals("1")){%>
				style="display:none" <%}%>>
				<td colspan="2">
					<table width="100%" border="0" cellspacing="1" cellpadding="2">
						<%
							BankinfoModel myBankinfoModel = new BankinfoModel();
							if (mincsubs.equals("1"))
								mbankid = myBankinfoModel.getBankRange(mbankid);
								
							DevinfoModel myDevinfoModel = new DevinfoModel();
							List myDevList = myDevinfoModel.getDevListOfBank(mbankid);
							
							Vector myVector = new Vector();
							if (myDevList != null && !myDevList.isEmpty()) {
								int devnum = myDevList.size();

								for (int i = 0; i < devnum; i++) {
									Devinfo tmp = (Devinfo) myDevList.get(i);
									if (devtype.equals("A")	|| (pubUtil.dealNull(tmp.getTypeno()).trim()).equals(devtype)) {
								myVector.add(tmp);
									}
								}
								devnum = myVector.size();

								int trnum = devnum / 5;
								if (devnum % 5 > 0)
									trnum++;
								for (int i = 0; i < trnum; i++) {
						%>
						<tr class="list_tr<%=i % 2%>">
							<%
									for (int j = 0; j < 5; j++) {
									if (i * 5 + j < devnum) {
										Devinfo tmpdev = (Devinfo) myVector.get(i * 5 + j);
										String tmpdevno = pubUtil.dealNull(	tmpdev.getDevno()).trim();
							%>
							<td width=20%">
								<input name="devno" id="devno" type="checkbox"
									value="<%=tmpdevno%>">
								<%=tmpdevno%>
							</td>
							<%
							} else {
							%>
							<td width="20%">
								&nbsp;
							</td>
							<%
										}
										}
							%>
						</tr>
						<%
							}
							}
							if (myVector == null || myVector.isEmpty()) {
						%>
						<tr class="list_tr0">
							<td colspan="5" align="center">
								&nbsp;
								<bean:message key="pvxp.trademoni.no.devs" />
							</td>
						</tr>
						<%
						}
						%>
					</table>
				</td>
			</tr>
		</table>

		<p></p>

		<table border="0" width="100%" cellspacing="1" cellpadding="2"
			class="list_table_border">
			<tr align="center">
				<td class="list_td_prom">
					Դ�ʺ�
				</td>
				<td class="list_td_detail">
					<html:text property="accno1" size="25" />
				</td>
				<td class="list_td_prom">
					���׽��
				</td>
				<td class="list_td_detail">
					���ڵ��ڣ�
					<html:text property="money1" size="10" />
					С�ڵ��ڣ�
					<html:text property="money2" size="10" />
				</td>
			</tr>
			<tr align="center">
				<td class="list_td_prom">
					Ŀ���ʺ�
				</td>
				<td class="list_td_detail">
					<html:text property="accno2" size="25" />
				</td>
				<td class="list_td_prom">
					��������
				</td>
				<td class="list_td_detail">
					��ʼ���ڣ�
					<html:text property="date1" size="10" maxlength="8" />
					�������ڣ�
					<html:text property="date2" size="10" maxlength="8" />
				</td>
			</tr>
		</table>

		<p></p>


		<p></p>

		<%-- �������� --%>
		<table border="0" width="100%" cellspacing="1" cellpadding="2"
			class="list_table_border">
			<tr>
				<td class="list_td_title" width="40%">
					��������
					<input type="radio" name="ttype"
						onclick="collapse(document.all.TradeType, document.all.devtrcd, document.all.selectTradeType, 'hide')"
						checked>
					ȫ��
					<input type="radio" name="ttype"
						onclick="collapse(document.all.TradeType, document.all.devtrcd, document.all.selectTradeType, 'show')">
					����
				</td>
				<td class="list_td_title">
					<img id="selectTradeType" style="visibility:hidden; cursor:hand"
						src="images/default/bt_selectall.gif"
						onclick="selectAll(document.all.devtrcd, this)">
				</td>
			</tr>
		</table>
		<table id="TradeType" style="display:none; border-top-style:none"
			border="0" width="100%" cellspacing="1" cellpadding="2"
			class="list_table_border">
			<%
				CharSet myCharSet = new CharSet();
				iniread myiniread = new iniread("Trade.ini");
				//int totalType = Integer.parseInt(pubUtil.ReadConfig("TradeCode", "TCode_Num", "", "ini", "Trade.ini"));
				int totalType = Integer.parseInt(myiniread.getIni("TCode_Num").trim());
				int col = 5;
				int row = (totalType / col) + (totalType % col == 0 ? 0 : 1);

				String tempStr = "";
				String code = "";
				String type = "";
				int beginCol;
				int endCol;

				for (int i = 0; i < row; i++) {
			%>
			<tr class="list_tr<%=i % 2%>">
				<%
						beginCol = i * col;
						endCol = beginCol + col;
						for (int j = beginCol; j < endCol; j++) {
						if (j < totalType) {
						//tempStr = pubUtil.ReadConfig("TradeCode", "TCode_" + (j+1), "", "ini", "Trade.ini");
						tempStr = pubUtil.dealNull(myiniread.getIni("TCode_"+ String.valueOf(j + 1)));
						//tempStr = myCharSet.form2GB(tempStr.trim());

						code = tempStr.substring(0, tempStr.indexOf(','));
						type = tempStr.substring(tempStr.indexOf(',') + 1);
							}
				%>
				<td width="20%">
					<%
					if (j < totalType) {
					%>
					<html:multibox property="devtrcd" value="<%=code%>" />
					<%=type%>
					<%
					}
					%>
				</td>
				<%
				}
				%>
			</tr>
			<%
			}
			%>
		</table>

		<p></p>




		<p align="center">
			<input type="image" src="images/default/bt_ok.gif"
				onfocus="this.blur()">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/default/bt_reset.gif" style="cursor:hand"
				onclick="reset()">
		</p>
	</html:form>


	<form id="relistdev" action="TradeQuery.jsp">
		<input name="mbankid" type="hidden">
		<input name="mincsubs" type="hidden">
		<input name="mdevtype" type="hidden">
		<input name="ifsdevs" type="hidden">
	</form>

</body>
</html:html>
