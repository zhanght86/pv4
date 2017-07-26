package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.actionform.OperListForm;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.orm.Operator;
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
 * <p><b>Description:</b> 操作员列表Action</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/2/25
 */
public final class OperListAction extends Action{

	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String page = (String)((OperListForm) form).getPage();
		String pagesize = (String)((OperListForm) form).getPagesize();
		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");
		
		int intpage = 0;
		int intpagesize = 0;
		int intpagecount = 0;
		String pagecount = "0";
		int intttcount = 0;
		String ttcount = "0";
		int intstart = 0;
		String start = "0";
		int intend = 0;
		String end = "0";
		
		try{
			intpage = Integer.parseInt(page);
			intpagesize = Integer.parseInt(pagesize);
		}catch(Exception e){
			//页数错误
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.submit.wrong");
			request.removeAttribute(mapping.getAttribute());
			return myforward;
		}
		try{
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST,request)).trim();
			String mybankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID,request));
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
			BankinfoModel myBankinfoModel = new BankinfoModel();
			
			Vector BankVector = myBankinfoModel.getSubBank(mybankid);
			List OperList = myOperatorModel.getOperList();
			List mylist = new ArrayList();
			Operator myOperator = new Operator();
			Bankinfo myBankinfo = new Bankinfo();
			Vector myVector = new Vector();
			
			for ( int i=0; i<OperList.size(); i++ ) {
				myOperator = (Operator)OperList.get(i);
				if ( (myOperator.getAuthlist().trim()).equals("*") ) {
					OperList.remove(i);
				}
			}
			
			if ( authlist.equals("*") ) {
				mylist = OperList;
			}else {
				if ( BankVector==null ) {
					for ( int j=0; j<OperList.size(); j++ ) {
						myOperator = (Operator)OperList.get(j);
						if ( (myOperator.getBankid().trim()).equals(mybankid) ) {
							mylist.add(myOperator);
						}
					}
				}else {
					for ( int i=0; i<BankVector.size(); i++ ) {
						myBankinfo = (Bankinfo)BankVector.get(i);
						for ( int j=0; j<OperList.size(); j++ ) {
							myOperator = (Operator)OperList.get(j);
							if ( (myOperator.getBankid()).equals(myBankinfo.getBankid()) ) {
								mylist.add(myOperator);
							}
						}
					}
				}
			}
			
			if( mylist==null||mylist.size()==0 ){
				//没有管理员
				ttcount = "0";
				pagecount = "0";
			}else{
				int len = mylist.size();
				intpagecount = ( (len-1)/intpagesize ) + 1;
				ttcount = String.valueOf(len);
				pagecount = String.valueOf(intpagecount);
				
				if(intpage<1){
					intpage = 1;
					page = "1";
				}else if( intpage>intpagecount ){
					intpage = intpagecount;
					page = pagecount;
				}
				
				
				intstart = intpagesize*( intpage -1 );
				intend = intstart + intpagesize;
				if( intend>len ) intend = len;
				
				for( int i=intstart;i<intend;i++ ){
					myOperator=(Operator)mylist.get(i);
					myVector.add(myOperator);
				}
			}
			request.setAttribute(Constants.REQUEST_OPERATOR_TTCOUNT,ttcount);
			request.setAttribute(Constants.REQUEST_OPERATOR_PAGECOUNT,pagecount);
			request.setAttribute(Constants.REQUEST_OPERATOR_PAGE,page);
			request.setAttribute(Constants.REQUEST_OPERATOR_VECTOR,myVector);
			
			myforward = mapping.findForward("Operator_List");
			
		}catch(Exception e){
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.system.syserror");
		}
		
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}