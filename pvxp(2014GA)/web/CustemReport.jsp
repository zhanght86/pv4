<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<html:html locale="true">
<head>
	<title>�ͻ�����</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>
	<script language="JavaScript">
	
	//��֤����
	function checkEmail(email)
	{
		 myReg = /^([-_A-Za-z0-9\.]+)@([-_A-Za-z0-9]+\.)+[_A-Za-z0-9]{2,3}$/;
		  
	         if(myReg.test( email )){
	          return true;
	          }
	         return false;
	}

    //��֤�ֻ�
	function checkMobileNo(mobile1)
	{
		 myReg = /^([-_0-9]+)$/;
	         if(myReg.test( mobile1 )){
			 // alert ("mobile"+mobile1);
			 return true;
			 }
	         return false;		
	}


//  ҳ����Ϣ�ύУ��
	function submit_end()
	{
		var feedBackType = document.all.feedBackType.value;
		if(feedBackType=="")
		{
			alert("��ѡ�������");
            document.all.feedBackType.focus();
			return false;
		}

		var feedBackArea = document.all.feedBackArea.value;
		if(feedBackArea=="")
		{
			alert("��ѡ������");
            document.all.feedBackArea.focus();
			return false;
		}
		
        var feedBackName = document.all.feedBackName.value;
		if((feedBackName.replace(/(^\s*)|(\s*$)/g, ""))=="")
		{
			alert("��������������");
            document.all.feedBackName.focus();
			return false;
		}
		
       else if(feedBackName.indexOf('<')>=0)
       {
            alert("�����������зǷ��ַ�<");
            document.all.feedBackName.focus();
			return false;
       }
      
        var feedBackMobile = document.all.feedBackMobile.value;
		if(feedBackMobile=="")
		{
			alert("�������ֻ�����!");
            document.all.feedBackMobile.focus();
			return false;
		}
        else 
        {
            if(!checkMobileNo(feedBackMobile)||feedBackMobile.length!=11)
            {
                  alert("�����ֻ����벻��ȷ!");
                  document.all.feedBackMobile.focus();
		  return false;
            }
        }
        
         var feedBackTel = document.all.feedBackTel.value;
         if(feedBackTel!=""&&!checkMobileNo(feedBackTel))
         {
             alert("����Ĺ̶��绰����ȷ!");
             document.all.feedBackTel.focus();
              return false;
         }
        
        
        var email = document.all.feedBackEmail.value;
        
		if(!checkEmail(email))
		{	
			alert("�������������������������");
            document.all.feedBackEmail.focus();	
			return false;
		}
		
  /*      var feedBackAddr = document.all.feedBackAddr.value;
        if(feedBackAddr.indexOf('<')>=0)
        {
            alert("��ϵ��ַ���зǷ��ַ�<");
            document.all.feedBackAddr.focus();
    		return false;
        }
  */

		var feedBackContent = document.all.feedBackContent.value;
		if((feedBackContent.replace(/(^\s*)|(\s*$)/g, ""))=="")
		{
			alert("��������������!");
            document.all.feedBackContent.focus();
			return false;
		}
         else if(feedBackContent.length>500)
         {
                 alert("�����������Ϊ500����!");
                 document.all.feedBackContent.focus();
          return false;
         }
 /*        
         else if(feedBackContent.indexOf('<')>=0)
         {
                 alert("�������Ժ��зǷ��ַ�<");
                 document.all.feedBackContent.focus();
          return false;
         }
     */    
     
         return true;
	}
</script>
</head>
<body onload="javascript:parent.hidit();">
	<div align="center">
		<form name="JymlMDeal_form" action="CusRep.do" method="post"
			onsubmit="javascript:return submit_end();">
			<%PubUtil myPubUtil = new PubUtil(); %>
			<table width="100%" border="1" cellpadding="0" cellspacing="0">
				<tr>
					<td height="23" style="width:100px;">
						�������
					</td>
					<td style="width:150px;">
					<select name="feedBackType" id="feedBackType">
					    <option value="02" selected>Ͷ�� </option>
					    <option value="01" >��ѯ</option>
					    <option value="03" >����</option>
					    <option value="04" >����</option>
					</select>
					<font color='red'>*</font>
					</td>
					<td style="width:100px;">
						<span id="lb_1">��������</span>
					</td>
					<td style="width:150px;">
						<select name="feedBackClass" id="feedBackClass">
						 	<option value="02" selected>��Ʒ��� </option>
					    	<option value="01" >��������</option>
					    	<option value="03" >�ƶ�������</option>
					   	 	<option value="04" >����</option>
						</select>
					</td>
				</tr>

				<tr>
					<td>
						ҵ���漰����
					</td>
					<td colspan="3">

						<select name="feedBackArea" id="feedBackArea">
							<option value="01" selected>��� </option>
						</select>

						<font color='red'>*</font> ��ע��: ����
						<font color='red'>*</font>�Ŀո�Ϊ���
					</td>
				</tr>

			<tr>
					<td>
						��������
					</td>
					<td>
						<input type="text" name="feedBackName" id="feedBackName"
							style="width:100px;" maxlength="30">
						<font color='red'>*</font>
					</td>
					<td colspan="2">
						<font color='red'>*</font>��ν&nbsp;&nbsp;
						<input type="radio" name="feedBackSex" id="feedBackSex" value="1">
						����
						<input type="radio" name="feedBackSex" id="feedBackSex" value="0"
							checked>
						Ůʿ
					</td>
				</tr>
				<tr>
					<td>
						�̶��绰
					</td>
					<td title="��ʽ��010-88888888">
						<input type="text" name="feedBackTel" id="feedBackTel"
							title="��ʽ��010-88888888" style="width:130px;" maxlength="20">
					</td>
					<td title="��������ȷ11λ�ֻ�����">
						��&nbsp;&nbsp;&nbsp;&nbsp;��
					</td>
					<td>
						<input type="text" name="feedBackMobile" id="feedBackMobile"
							title="��������ȷ11λ�ֻ�����" style="width:120px;" maxlength="11">
						<font color='red'>*</font>
					</td>
				</tr>
				<tr>
					<td>
						�����ʼ�
					</td>
					<td colspan="3">
						<input name="feedBackEmail" id="feedBackEmail" type="text"
							style="width:130px;" maxlength="50">
						<font color='red'>*</font>����д��Ч����,�Ա�����������ϵ
					</td>
				</tr>
				<tr>
					<td>
						��ϵ��ַ
					</td>
					<td colspan="3">
						<input name="feedBackAddr" id="feedBackAddr" type="text"
							style="width:330px;" maxlength="150">
					</td>
				</tr>
				<tr>
					<td>
						��������
					</td>
					<td colspan="3">
						<font color='red'>Ϊȷ�������˻���ȫ,�����������������롢�˻���Ϣ�����ҵ
							��ָ�������Ҫ����ͨ��ǡ��;������</font>
						<br>

						<textarea name="feedBackContent" id="feedBackContent" cols="65"
							title="������500������.." rows="8" style="width:310px;"></textarea>

						<font color='red'>*</font>
					</td>
				</tr>
				<tr>
					<td>
						�ظ���ʽ
					</td>
					<td colspan="3">

						<input type="radio" id="feedBackReply" name="feedBackReply"
							value="1" checked>
						�ظ�
						<input type="radio" name="feedBackReply" id="feedBackReply"
							value="0">
						���ظ�&nbsp;&nbsp;
						<font color='red'>*</font>
					</td>
				</tr>
			</table>
			<p align="center">
				<input type="submit" name="Submit1" value=" �� ��">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="reset" name="Submit2" value=" �� ��">
				<br>
		</form>
	</div>
</body>
</html:html>
