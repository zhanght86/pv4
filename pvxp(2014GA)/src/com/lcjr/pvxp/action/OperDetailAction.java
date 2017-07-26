package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.actionform.OperDetailForm;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.Operator;

import java.util.Locale;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;




/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 操作员详细信息Action</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/2/25
 */
public final class OperDetailAction extends Action{

	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String myoperid = (String)((OperDetailForm) form).getOperid();

		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");


		if(myoperid==null||myoperid.length()==0){
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.submit.wrong");
			request.removeAttribute(mapping.getAttribute());
			return myforward;
 		}


		try{
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST,request)).trim();
			String myPower = "";
			if( authlist.equals("*") ){
				//超级管理员
				myPower = "*";
			}else if( authlist.length()>0&&Integer.parseInt(authlist.substring(2,3))>0 ){
				//有浏览以上级别权限
				myPower = authlist.substring(2,3);
			}else{
				//没有权限
				myPower = "0";
			}
			//保存当前操作的权限
			request.setAttribute(Constants.REQUEST_OPER_POWER,myPower);
			//没有权限报错
			if( myPower.equals("0") ){
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
		
		
			OperatorModel myOperatorModel = new OperatorModel();
			Operator temp = myOperatorModel.getOperator(myoperid);
			//管理员不存在
			if(temp==null){
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.operator.no");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
		
		
			request.setAttribute(Constants.REQUEST_OPERATOR,temp);
		
			myforward = mapping.findForward("Operator_Detail");
		}catch(Exception e){
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}
		
		
		request.removeAttribute(mapping.getAttribute());
		return myforward;
		
	}

}