package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.actionform.*;
import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.bean.*;

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
 * <p><b>Description:</b> 操作员操作流水查询Action</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/3/22
 */
public final class OplogQueryAction extends Action{
	private PageBean pb;
	private List OplogList;

	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");
		
		try{
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST,request)).trim();
			String mybankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID,request));
			String myPower = "";
			if( authlist.equals("*") ){
				//超级管理员
				myPower = "*";
			}else if( authlist.length()>0&&Integer.parseInt(authlist.substring(7,8))>0 ){
				//有浏览以上级别权限
				myPower = authlist.substring(7,8);
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

			String page = request.getParameter("page");
			String pagesize = request.getParameter("pagesize");
			String ttcount = request.getParameter("totalRow");
			
			int intpage = 0;
			int intpagesize = Integer.parseInt(pagesize);
			int intmcount = 0;

			String operid = (String)((OplogQueryForm) form).getOperid().trim();
			String bopdate = (String)((OplogQueryForm) form).getBopdate().trim();
			String eopdate = (String)((OplogQueryForm) form).getEopdate().trim();
			String trcd = (String)((OplogQueryForm) form).getTrcd().trim();
			String type = (String)((OplogQueryForm) form).getType().trim();
			
			OplogModel myOplogModel = new OplogModel();
			
			if (page == null) { //第一次,取得个数
				intpage = 1;
			} else { //翻页
				intpage = Integer.parseInt(page);
			}

			intmcount = OplogBean.getOplogCount(operid, bopdate, eopdate, trcd, type);

			pb = new PageBean(intmcount, intpagesize);
			pb.setCurrentPage(intpage);
			int ttpage = pb.getTotalPage();
			if( intpage>ttpage ) intpage = ttpage;
			if( intpage<1 ) intpage = 1;

			OplogList = myOplogModel.getOplogs( operid, bopdate, eopdate, trcd, type, (intpage-1)*intpagesize, intpagesize );

			request.setAttribute( Constants.REQUEST_OPLOG_RESULT, OplogList);
			request.setAttribute(Constants.REQUEST_OPLOG_PAGEBEAN, pb);
			
			myforward = mapping.findForward("OplogQuery_Result");
			
		} catch(Exception e) {
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.system.syserror");
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;

	}
	
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 