<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@page import="com.lcjr.pvxp.util.*"%>

<html:html locale="true">
<head>
	<title>客户留言</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript" src="js/pv.js"></script>
	<script language="JavaScript">
	
	//验证邮箱
	function checkEmail(email)
	{
		 myReg = /^([-_A-Za-z0-9\.]+)@([-_A-Za-z0-9]+\.)+[_A-Za-z0-9]{2,3}$/;
		  
	         if(myReg.test( email )){
	          return true;
	          }
	         return false;
	}

    //验证手机
	function checkMobileNo(mobile1)
	{
		 myReg = /^([-_0-9]+)$/;
	         if(myReg.test( mobile1 )){
			 // alert ("mobile"+mobile1);
			 return true;
			 }
	         return false;		
	}


//  页面信息提交校验
	function submit_end()
	{
		var feedBackType = document.all.feedBackType.value;
		if(feedBackType=="")
		{
			alert("请选择反馈类别");
            document.all.feedBackType.focus();
			return false;
		}

		var feedBackArea = document.all.feedBackArea.value;
		if(feedBackArea=="")
		{
			alert("请选择区域");
            document.all.feedBackArea.focus();
			return false;
		}
		
        var feedBackName = document.all.feedBackName.value;
		if((feedBackName.replace(/(^\s*)|(\s*$)/g, ""))=="")
		{
			alert("请输入您的姓名");
            document.all.feedBackName.focus();
			return false;
		}
		
       else if(feedBackName.indexOf('<')>=0)
       {
            alert("您的姓名含有非法字符<");
            document.all.feedBackName.focus();
			return false;
       }
      
        var feedBackMobile = document.all.feedBackMobile.value;
		if(feedBackMobile=="")
		{
			alert("请输入手机号码!");
            document.all.feedBackMobile.focus();
			return false;
		}
        else 
        {
            if(!checkMobileNo(feedBackMobile)||feedBackMobile.length!=11)
            {
                  alert("输入手机号码不正确!");
                  document.all.feedBackMobile.focus();
		  return false;
            }
        }
        
         var feedBackTel = document.all.feedBackTel.value;
         if(feedBackTel!=""&&!checkMobileNo(feedBackTel))
         {
             alert("输入的固定电话不正确!");
             document.all.feedBackTel.focus();
              return false;
         }
        
        
        var email = document.all.feedBackEmail.value;
        
		if(!checkEmail(email))
		{	
			alert("您输入的邮箱有误，请重新输入");
            document.all.feedBackEmail.focus();	
			return false;
		}
		
  /*      var feedBackAddr = document.all.feedBackAddr.value;
        if(feedBackAddr.indexOf('<')>=0)
        {
            alert("联系地址含有非法字符<");
            document.all.feedBackAddr.focus();
    		return false;
        }
  */

		var feedBackContent = document.all.feedBackContent.value;
		if((feedBackContent.replace(/(^\s*)|(\s*$)/g, ""))=="")
		{
			alert("请输入留言正文!");
            document.all.feedBackContent.focus();
			return false;
		}
         else if(feedBackContent.length>500)
         {
                 alert("您的留言最多为500汉字!");
                 document.all.feedBackContent.focus();
          return false;
         }
 /*        
         else if(feedBackContent.indexOf('<')>=0)
         {
                 alert("您的留言含有非法字符<");
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
						反馈类别
					</td>
					<td style="width:150px;">
					<select name="feedBackType" id="feedBackType">
					    <option value="02" selected>投诉 </option>
					    <option value="01" >咨询</option>
					    <option value="03" >建议</option>
					    <option value="04" >表扬</option>
					</select>
					<font color='red'>*</font>
					</td>
					<td style="width:100px;">
						<span id="lb_1">反馈类型</span>
					</td>
					<td style="width:150px;">
						<select name="feedBackClass" id="feedBackClass">
						 	<option value="02" selected>产品设计 </option>
					    	<option value="01" >服务质量</option>
					    	<option value="03" >制度与流程</option>
					   	 	<option value="04" >其他</option>
						</select>
					</td>
				</tr>

				<tr>
					<td>
						业务涉及地区
					</td>
					<td colspan="3">

						<select name="feedBackArea" id="feedBackArea">
							<option value="01" selected>天津 </option>
						</select>

						<font color='red'>*</font> 请注意: 带有
						<font color='red'>*</font>的空格为必填。
					</td>
				</tr>

			<tr>
					<td>
						您的姓名
					</td>
					<td>
						<input type="text" name="feedBackName" id="feedBackName"
							style="width:100px;" maxlength="30">
						<font color='red'>*</font>
					</td>
					<td colspan="2">
						<font color='red'>*</font>称谓&nbsp;&nbsp;
						<input type="radio" name="feedBackSex" id="feedBackSex" value="1">
						先生
						<input type="radio" name="feedBackSex" id="feedBackSex" value="0"
							checked>
						女士
					</td>
				</tr>
				<tr>
					<td>
						固定电话
					</td>
					<td title="格式：010-88888888">
						<input type="text" name="feedBackTel" id="feedBackTel"
							title="格式：010-88888888" style="width:130px;" maxlength="20">
					</td>
					<td title="请输入正确11位手机号码">
						手&nbsp;&nbsp;&nbsp;&nbsp;机
					</td>
					<td>
						<input type="text" name="feedBackMobile" id="feedBackMobile"
							title="请输入正确11位手机号码" style="width:120px;" maxlength="11">
						<font color='red'>*</font>
					</td>
				</tr>
				<tr>
					<td>
						电子邮件
					</td>
					<td colspan="3">
						<input name="feedBackEmail" id="feedBackEmail" type="text"
							style="width:130px;" maxlength="50">
						<font color='red'>*</font>请填写有效邮箱,以便我们与您联系
					</td>
				</tr>
				<tr>
					<td>
						联系地址
					</td>
					<td colspan="3">
						<input name="feedBackAddr" id="feedBackAddr" type="text"
							style="width:330px;" maxlength="150">
					</td>
				</tr>
				<tr>
					<td>
						留言正文
					</td>
					<td colspan="3">
						<font color='red'>为确保您的账户安全,此渠道不受理开户申请、账户信息变更等业
							务指令。如有需要，请通过恰当途径办理。</font>
						<br>

						<textarea name="feedBackContent" id="feedBackContent" cols="65"
							title="请输入500字以内.." rows="8" style="width:310px;"></textarea>

						<font color='red'>*</font>
					</td>
				</tr>
				<tr>
					<td>
						回复方式
					</td>
					<td colspan="3">

						<input type="radio" id="feedBackReply" name="feedBackReply"
							value="1" checked>
						回复
						<input type="radio" name="feedBackReply" id="feedBackReply"
							value="0">
						不回复&nbsp;&nbsp;
						<font color='red'>*</font>
					</td>
				</tr>
			</table>
			<p align="center">
				<input type="submit" name="Submit1" value=" 提 交">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="reset" name="Submit2" value=" 重 置">
				<br>
		</form>
	</div>
</body>
</html:html>
