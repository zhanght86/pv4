<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="0" minpower="2" />


<%@ page import="java.util.*"%>
<%@ page import="com.lcjr.pvxp.model.*"%>
<%
	boolean canAdd = true;

	BankinfoModel bankinfoModel = new BankinfoModel();
	List banks = bankinfoModel.getBankinfoList();
	if (banks == null || banks.size() == 0) {
		canAdd = false;
	}

	DevtypeModel devtypeModel = new DevtypeModel();
	List devtypes = devtypeModel.getDevTpList();
	if (devtypes == null || devtypes.size() == 0) {
		canAdd = false;
	}

	UpdateTypeModel updateTypeModel = new UpdateTypeModel();
	List updatetypes = updateTypeModel.getUpdateTypeList();
	if (updatetypes == null || updatetypes.size() == 0) {
		canAdd = false;
	}
%>
<html:html locale="true">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<title><bean:message key="pvxp.dev.add" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>
	<script language="JavaScript">
    
    
     //IPУ��
    function checkip(ip) 
{    
    var re=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/ ; 
    if(re.test(ip)) 
    { 
    	return 1;
    } 
    else 
    {
        return 2;
    }
}



//mac��ַУ��
 function CheckMac(mac)  
{  
	var reg=/[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}-[A-F\d]{2}/;  
	if(reg.test(mac)){
	return 1;
	}else {
	return 2
	}
}


//Ϊ����
function isNumber2(oNum) 
   { 

  if(!oNum) return 2; 
 // var strP=/^\d+(\.\d+)?$/; ����ΪС����������֤����Ŀ�ͷ����0
  var strP=/^(0|[1-9][0-9]*)$/;
  if(!strP.test(oNum)) return 2; 
  return 1; 
   }

      function validate() {
        if (document.all.devno.value == "") {
          alert("�������豸��š�");
          document.all.devno.focus();
          return false;
        }

        if (document.all.devno.value.match(/\W/) != null) {
          alert("�豸���ֻ�ܰ�����ĸ�����֡�");
          document.all.devno.focus();
          return false;
        }

        if (document.all.devip.value == "") {
          alert("������IP��ַ");
          document.all.devip.focus();
          return false;
        }

        if (checkip(document.all.devip.value) == 2) {
          alert("�����IP��ַ��ʽ����");
          document.all.devip.focus();
          return false;
        }



        //MACУ��
        /**
        if (document.all.devmac.value == "") {
        	alert("������MAC��ַ");
        	document.all.devmac.focus();
        	return false;
        } else if (CheckMac(document.all.devmac.value) == 2) {
        	alert("�����MAC��ַ��ʽ����");
        	document.all.devmac.focus();
        	return false;
        }
**/


        if (document.all.organno.value == "") {
          alert("������������");
          document.all.organno.focus();
          return false;
        }
        
        if (document.all.tellerno.value == "") {
          alert("�������Ա���");
          document.all.tellerno.focus();
          return false;
        }

        if (document.all.dutyname.value == "") {
          alert("������ά����");
          document.all.dutyname.focus();
          return false;
        }
        
//�绰����У��
        if( document.all.organcontact.value=="" ) {
			alert( "������ ��ϵ�绰" );
			document.all.organcontact.focus();
			return false;
		}
		 var tel=document.all.organcontact.value;
   			var strP= /(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{3,4}\-[0-9]{7,8}\-[0-9]{2,6}$)|(^[0-9]{7,8}$)|(^[0-9]{3,4}[0-9]{7,8}$)|(^0{0,1}1[0-9]{10}$)/;
  			if(!strP.test(tel)){
   			alert('����ĵ绰�����ʽ����');
   			document.all.organcontact.focus();
   			return false; 
      }

		var remark2=document.all.remark2.value;
        if (document.all.remark2.value == "") {
        	alert("�����뱥������ʱ��");
        	document.all.remark2.focus();
        	return false;
        }else if(isNumber2(remark2)==2 ){
       	    alert("��������ȷ����");
        	document.all.remark2.focus();
        	return false;
        }else if(remark2<0||remark2>24){
        	alert("��������ʱ����0-24֮��");
        	document.all.remark2.focus();
        	return false;
        }
        
        return true;
      }
    </script>
</head>
<body onload="javascript:parent.hidit();">
	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" align="center" width="30" height="40">
				<img src="images/default/nav.gif">
			</td>
			<td>
				<font color=blue>PowerView XP</font> ---
				<font class="location"><bean:message key="pvxp.dev.add" /> </font>
			</td>
		</tr>
	</table>

	<%
	if (canAdd) {
	%>
	<table width="100%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<html:form action="/AddDev.do" focus="devno"
			onsubmit="javascript:parent.showit();">
			<html:hidden property="isShow" value="false" />
			<tr align="center">
				<td colspan="8" class="list_td_title">
					<b><bean:message key="pvxp.dev.add" /> </b>
				</td>
			</tr>
			<tr>
				<td width="8%" class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.devno" /> </b>
				</td>
				<td width="17%" class="list_td_detail">
					&nbsp;
					<html:text property="devno" size="15" maxlength="10" />
				</td>
				<td width="8%" class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.devip" /> </b>
				</td>
				<td width="17%" class="list_td_detail">
					&nbsp;
					<html:text property="devip" size="15" maxlength="15" />
				</td>
				<td width="8%" class="list_td_prom">
					<nobr>
						&nbsp;
						<b><bean:message key="prompt.devmac" /> </b>
					</nobr>
				</td>
				<td width="17%" class="list_td_detail">
					&nbsp;
					<html:text property="devmac" size="15" maxlength="64" />
				</td>
				<td width="8%" class="list_td_prom">
					<nobr>
						&nbsp;
						<b><bean:message key="prompt.typeno" /> </b>
					</nobr>
				</td>
				<td width="17%" class="list_td_detail">
					&nbsp;
					<app:devtpselect property="typeno" showName="1" />
				</td>
			</tr>
			<tr>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.auther" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="autherno" size="15" maxlength="10" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.organno" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="organno" size="15" maxlength="20" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.tellerno" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="tellerno" size="15" maxlength="10" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.bank" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<app:bankselect property="bankid" />
				</td>
			</tr>
			<tr>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.devaddr" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="devaddr" size="15" maxlength="30" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.dutyname" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="dutyname" size="15" maxlength="10" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.organcontact" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="organcontact" size="20" maxlength="20" />
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.sysid" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<app:updatetypeselect property="sysid" />
				</td>
			</tr>
			<!--<tr>
        <td class="list_td_prom">&nbsp;<nobr><b><bean:message key="prompt.devkey"/></b></nobr></td>
        <td class="list_td_detail">&nbsp;<html:text property="devkey" size="15" maxlength="16"/></td>
        <td class="list_td_prom">&nbsp;<b><bean:message key="prompt.pinkey"/></b></td>
        <td class="list_td_detail">&nbsp;<html:text property="pinkey" size="15" maxlength="16"/></td>
        <td class="list_td_prom">&nbsp;<b><bean:message key="prompt.mackey"/></b></td>
        <td class="list_td_detail">&nbsp;<html:text property="mackey" size="15" maxlength="16"/></td>
        <td class="list_td_prom">&nbsp;&nbsp;</td>
        <td class="list_td_detail">&nbsp;&nbsp;</td>
      </tr>-->
			<html:hidden property="devkey" />
			<html:hidden property="pinkey" />
			<html:hidden property="mackey" />
			<tr>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.typestate" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:select property="typestate">
						<html:option value="a">����</html:option>
						<%--<html:option value="s">ͣ��</html:option>--%>
						<html:option value="t">����</html:option>
					</html:select>
				</td>
				<td class="list_td_prom">
					<nobr>
						&nbsp;
						<b><bean:message key="prompt.datatype" /> </b>
					</nobr>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:select property="packtype">
						<html:option value="b">B/S�ṹ</html:option>
						<html:option value="c">C/S�ṹ</html:option>
					</html:select>
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.localtag" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:select property="localtag">
						<html:option value="0">����</html:option>
						<html:option value="1">������</html:option>
					</html:select>
				</td>
				<td class="list_td_prom">
					&nbsp;
					<b><bean:message key="prompt.polltag" /> </b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:select property="polltag">
						<html:option value="1">��Ҫ��ѯ</html:option>
						<html:option value="0">������ѯ</html:option>
						<%--<html:option value="9">������ѯ</html:option>--%>
					</html:select>
				</td>
			</tr>

			<tr>
				<td class="list_td_prom">
					&nbsp;
					<nobr>
						<b>��������ʱ��</b>
					</nobr>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text property="remark2" size="15" maxlength="2" />
					(Сʱ/��)
				</td>
				<td class="list_td_prom">
					&nbsp;&nbsp;
				</td>
				<td class="list_td_detail">
					&nbsp;&nbsp;
				</td>
				<td class="list_td_prom">
					&nbsp;&nbsp;
				</td>
				<td class="list_td_detail">
					&nbsp;&nbsp;
				</td>
				<td class="list_td_prom">
					&nbsp;&nbsp;
				</td>
				<td class="list_td_detail">
					&nbsp;&nbsp;
				</td>
			</tr>
	</table>

	<table align="center" width="40%">
		<tr align="center">
			<td>
				<input type="image" src="images/default/bt_ok.gif"
					onclick="return validate()">
			</td>
			<td>
				<img src="images/default/bt_reset.gif" onclick="reset()"
					style="cursor:hand">
			</td>
			<td>
				<img src="images/default/bt_back.gif" onClick="history.back()"
					style="cursor:hand">
			</td>
		</tr>
		</html:form>
	</table>

	<p>
		<html:errors property="addDev" />
	</p>
	<%
	} else {
	%>
	<table width="100%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<tr>
			<td class="list_td_title">
				<b>����</b>
			</td>
		</tr>
		<%
		if (banks == null || banks.size() == 0) {
		%>
		<tr class="list_tr0">
			<td align="center">
				û�л�����������ӻ�����
			</td>
		</tr>
		<%
				}

				if (devtypes == null || devtypes.size() == 0) {
		%>
		<tr class="list_tr0">
			<td align="center">
				û���豸���ͣ���������豸���͡�
			</td>
		</tr>
	</table>
	<%
			}

			if (updatetypes == null || updatetypes.size() == 0) {
	%>
	<tr class="list_tr0">
		<td align="center">
			û���豸�������ͣ���������豸�������͡�
		</td>
	</tr>
	</table>
	<%
		}
		}
	%>
</body>
</html:html>
