package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.actionform.*;
import com.lcjr.pvxp.bean.*;
import com.lcjr.pvxp.orm.*;

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
 * <p><b>Description:</b> 操作记录统计Action</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2011</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2011/03/04
 */
public final class OptjMDealAction extends Action
{

	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] operlist = (String[])((OptjMDealForm) form).getOperlist();
		String[] trcd = (String[])((OptjMDealForm) form).getTrcd();
		String[] type = (String[])((OptjMDealForm) form).getType();
		String repnm = (String)((OptjMDealForm) form).getRepnm().trim();
		String qseq = (String)((OptjMDealForm) form).getQseq();
		String qbegin = (String)((OptjMDealForm) form).getQbegin();
		String qend = (String)((OptjMDealForm) form).getQend();

		PubUtil myPubUtil = new PubUtil();
		CharSet myCharSet = new CharSet();
		ActionForward myforward = mapping.findForward("SystemError");

		try{
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST,request)).trim();
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID,request)).trim();
			String bankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID,request)).trim();
			String filepath = getServlet().getServletContext().getRealPath("") + "/statresult/";

			if( authlist.equals("*") ){
				//超级管理员
				bankid = " ";
			}else if( Integer.parseInt(authlist.substring(10,11)) < 2 ){
				//没有权限
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			if( (operlist==null) || (operlist.length==0) ) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.operator.no.selected");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			//处理设备编号
			List operList = new ArrayList();
			for( int i = 0; i < operlist.length; i++ ) {
				operList.add( operlist[i] );
			}
			
			//处理部件
			List trcdList = new ArrayList();
			for( int i = 0; i < trcd.length; i++ ) {
				trcdList.add( trcd[i] );
			}

			//处理状态
			List typeList = new ArrayList();
			for( int i = 0; i < type.length; i++ ) {
				typeList.add( type[i] );
			}
			
			//生成HQL
			String HQLstr = "from Oplog where (";
			for( int i=0; i<operlist.length; i++ ) {
				HQLstr += " operid='" + operlist[i] + "' or";
			}
			HQLstr = HQLstr.substring(0,HQLstr.length()-3) + " ) and (";
			for( int i=0; i<trcd.length; i++ ) {
				HQLstr += " trcd='" + trcd[i] + "' or";
			}
			HQLstr = HQLstr.substring(0,HQLstr.length()-3) + " ) and (";
			for( int i=0; i<type.length; i++ ) {
				HQLstr += " type='" + type[i] + "' or";
			}
			HQLstr = HQLstr.substring(0,HQLstr.length()-3) + " ) and ";

			if( qseq.equals("day") ) {
				//日统计报表
				
				HQLstr += " opdate>='" + qbegin + "' and opdate<='" + qend + "'";
				if( ( myPubUtil.dealNull(Constants.DB_OP_TYPE) ).equals("1") )
					HQLstr += " order by operid, opdate, trcd";

				OptjDayServer myOptjDayServer = new OptjDayServer();

				myOptjDayServer.setHQLstr( HQLstr );
				myOptjDayServer.setBankid( bankid );
				myOptjDayServer.setOperid( operid );
				myOptjDayServer.setRepnm( repnm );
				myOptjDayServer.setQbegin( qbegin );
				myOptjDayServer.setQend( qend );
				myOptjDayServer.setFilepath( filepath );
				myOptjDayServer.setOperList( operList );
				myOptjDayServer.setTrcd( trcdList);
				myOptjDayServer.setType( typeList);

				myOptjDayServer.start();
			}else if( qseq.equals("month") ) {
				//月统计报表
				
				qbegin += "01";
				qend += "31";
				
				HQLstr += " opdate>='" + qbegin + "' and opdate<='" + qend + "'";
				if( ( myPubUtil.dealNull(Constants.DB_OP_TYPE) ).equals("1") )
					HQLstr += " order by operid, opdate, trcd";

				OptjMonthServer myOptjMonthServer = new OptjMonthServer();

				myOptjMonthServer.setHQLstr( HQLstr );
				myOptjMonthServer.setBankid( bankid );
				myOptjMonthServer.setOperid( operid );
				myOptjMonthServer.setRepnm( repnm );
				myOptjMonthServer.setQbegin( qbegin );
				myOptjMonthServer.setQend( qend );
				myOptjMonthServer.setFilepath( filepath );
				myOptjMonthServer.setOperList( operList );
				myOptjMonthServer.setTrcd( trcdList);
				myOptjMonthServer.setType( typeList);

				myOptjMonthServer.start();

			}else if( qseq.equals("year") ) {
				//年统计报表
				qbegin += "0101";
				qend += "1231";
				
				HQLstr += " opdate>='" + qbegin + "' and opdate<='" + qend + "'";
				if( ( myPubUtil.dealNull(Constants.DB_OP_TYPE) ).equals("1") )
					HQLstr += " order by operid, opdate, trcd";

				OptjYearServer myOptjYearServer = new OptjYearServer();

				myOptjYearServer.setHQLstr( HQLstr );
				myOptjYearServer.setBankid( bankid );
				myOptjYearServer.setOperid( operid );
				myOptjYearServer.setRepnm( repnm );
				myOptjYearServer.setQbegin( qbegin );
				myOptjYearServer.setQend( qend );
				myOptjYearServer.setFilepath( filepath );
				myOptjYearServer.setOperList( operList );
				myOptjYearServer.setTrcd( trcdList);
				myOptjYearServer.setType( typeList);

				myOptjYearServer.start();
			
			}

			myforward = mapping.findForward("OptjMDeal");

		}catch(Exception e){
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}