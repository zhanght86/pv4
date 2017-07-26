package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.actionform.OplogDeleteForm;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.orm.Oplog;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.orm.Bankinfo;

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
 * <p><b>Description:</b> 操作员操作流水删除Action</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/3/22
 */
public final class OplogDeleteAction extends Action {

	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");
		OplogModel myOplogModel = new OplogModel();
		String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID,request));
		try{
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST,request)).trim();
			if(!authlist.equals("*")){
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
			
			String fromdate = (String)((OplogDeleteForm) form).getFromdate().trim();
			long bdate = 0;
			long ndate = 0;
			long mdate = 0;
			
			ndate = Long.parseLong( myPubUtil.getNowDate(1) );
			mdate = Long.parseLong( myPubUtil.getOtherDate(-7) );
			bdate = Long.parseLong( fromdate );
			
			if( mdate < bdate ) {
				fromdate = Long.toString( mdate );
			}
			
			if ( myOplogModel.deleteOplog( fromdate ) == 0 ) {
				myOplogModel.insertOplog(operid,"1","sys","pvxp.oplog.syssetup.deloplog.success|"+fromdate);
				errors.add("deleteoperlog", new ActionMessage("pvxp.syssetup.deloplog.success"));
			} else {
				myOplogModel.insertOplog(operid,"1","sys","pvxp.oplog.syssetup.deloplog.failed");
				errors.add("deleteoperlog", new ActionMessage("pvxp.syssetup.deloplog.failed"));
			}
			if( !errors.isEmpty() ) {
				saveErrors( request , errors );
			}
			myforward = new ActionForward( mapping.getInput() );
			//request.removeAttribute(mapping.getAttribute());
			return myforward;
		} catch(Exception e) {
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.system.syserror");
			return myforward;
		}
	}
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 