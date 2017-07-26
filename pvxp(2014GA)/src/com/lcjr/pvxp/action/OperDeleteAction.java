package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.actionform.OperDeleteForm;
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
 * <p><b>Description:</b> 操作员删除Action</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/2/28
 */
public final class OperDeleteAction extends Action
{

	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] operArry = (String[])((OperDeleteForm) form).getOperArry();

		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");
		try{
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID,request)).trim();
			String myoperid = "";
			for ( int i=0; i<operArry.length; i++ ) {
				myoperid = operArry[i].trim();
				if ( operid.equals(myoperid) ) {
					request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.cannot.opselfoper");
		    			request.removeAttribute(mapping.getAttribute());
		    			return myforward;
				}
			}

			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST,request)).trim();
			String myPower = "";
			if( authlist.equals("*") ){
				//超级管理员
				myPower = "*";
			}else if( authlist.length()>0&&Integer.parseInt(authlist.substring(2,3))>1 ){
				//有读写级别权限
				myPower = authlist.substring(2,3);
			}else{
				//没有权限
				myPower = "０";
			}
			//保存当前操作的权限
			request.setAttribute(Constants.REQUEST_OPER_POWER,myPower);
			//没有权限报错
			if( myPower.equals("0") ){
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.op.no.power");
	    			request.removeAttribute(mapping.getAttribute());
	    			return myforward;
			}


			if(operArry==null||operArry.length==0){
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.operator.no.selected");
			    	request.removeAttribute(mapping.getAttribute());
			    	return myforward;
			}

		  	String[] result = null;
		  	myoperid = "";

			try{
				OperatorModel myOperatorModel = new OperatorModel();
				int num = operArry.length;
				result = new String[num];
				for(int i=0;i<num;i++){
					result[i] = String.valueOf(OperatorModel.deleteOper(operArry[i]));
					if ( i==0 ) {
						myoperid = operArry[i].trim();
					}else {
						myoperid = myoperid + "|" + operArry[i].trim();
					}
				}
			}catch(Exception e){
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.system.syserror");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			//记录操作员操作流水
			OplogModel myOplogModel = new OplogModel();
			String soperid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID,request)).trim();
			int logflag = 0;
				myoperid = "";
				for( int i=0; i<operArry.length; i++ ) {
					myoperid += operArry[i] + ", ";
				}
				myoperid = myoperid.substring(0,myoperid.length()-2);
			logflag = myOplogModel.insertOplog( soperid, "1", "2", "pvxp.oplog.operator.delete|"+myoperid );

			request.setAttribute(Constants.REQUEST_OPERATOR_DEL_DONE,result);
			myforward = mapping.findForward("Operator_DeleteDone");

		}catch(Exception e){
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}