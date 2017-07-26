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
 * <p><b>Description:</b> 自动报表定制Action</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/4/7
 */
public final class StatADealAction extends Action
{

	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] sortlist =  (String[])((StatADealForm) form).getSortname();
		String strcdlist =   (String)((StatADealForm) form).getStrcdlist();

		String mbankid =     (String)((StatADealForm) form).getBankid().trim();
		String statesort =   (String)((StatADealForm) form).getStatesort().trim();
		String statename =   (String)((StatADealForm) form).getStatename().trim();
		String statetype =   (String)((StatADealForm) form).getStatetype().trim();
		String afterday =    (String)((StatADealForm) form).getAfterday().trim();
		String aftertime =   (String)((StatADealForm) form).getAftertime().trim();
		String opentag =     (String)((StatADealForm) form).getOpentag().trim();
		String info =        (String)((StatADealForm) form).getInfo().trim();

		String nextstatday = "";
		String trcdtypes = "";

		PubUtil myPubUtil = new PubUtil();
		CharSet myCharSet = new CharSet();
		ActionForward myforward = mapping.findForward("SystemError");

		//处理交易类型名称
		for( int i=0; i<sortlist.length; i++ ) {
			trcdtypes += sortlist[i] + ",";
		}
		trcdtypes = trcdtypes.substring( 0, trcdtypes.length()-1 );

		//处理开启标志
		if( opentag.equals("on") ) {
			opentag = "1";
		}else if( opentag.equals("off") ) {
			opentag = "0";
		}

		//处理下次开始统计日期
		String nowdate = myPubUtil.getNowDate(1);
		String tmpdate = "";
		String tmpyear = "";

		if( afterday.length() == 1 ) {
			afterday = "0" + afterday;
		}

		if( statetype.equals("1") ) {//日报
			nextstatday = myPubUtil.getOtherDate(1);

		}else if( statetype.equals("2") ) {//月报
			tmpdate = nowdate.substring(0,6);
			tmpdate = Integer.toString(Integer.parseInt(tmpdate) + 1);
			if( myPubUtil.isYYYYMM( tmpdate ) ) {
				nextstatday = tmpdate + afterday;
			}else {
				tmpdate = Integer.toString(Integer.parseInt(tmpdate.substring(0,4)) + 1);
				nextstatday = tmpdate + "01";
				nextstatday += afterday;
			}

		}else if( statetype.equals("4") ) {//季报
			tmpdate = nowdate.substring(4,6);
			tmpyear = Integer.toString(Integer.parseInt(nowdate.substring(0,4)) + 1);
			int i_month = Integer.parseInt(tmpdate);
			if( i_month < 3 ) {
				nextstatday = nowdate.substring(0,4) + "04" + afterday;
			}else if( (i_month > 3) && (i_month <= 6) ) {
				nextstatday = nowdate.substring(0,4) + "07" + afterday;
			}else if( (i_month > 6) && (i_month <= 9) ) {
				nextstatday = nowdate.substring(0,4) + "10" + afterday;
			}else if( (i_month > 9) && (i_month <= 12) ) {
				nextstatday = tmpyear + "01" + afterday;
			}

		}else if( statetype.equals("5") ) {//半年报
			tmpdate = nowdate.substring(4,6);
			tmpyear = Integer.toString(Integer.parseInt(nowdate.substring(0,4)) + 1);
			int i_month = Integer.parseInt(tmpdate);
			if( i_month < 7 ) {
				nextstatday = nowdate.substring(0,4) + "07" + afterday;
			}else if( (i_month > 6) && (i_month <= 12) ) {
				nextstatday = tmpyear + "01" + afterday;
			}

		}else if( statetype.equals("3") ) {//年报
			tmpdate = nowdate.substring(0,4);
			tmpdate = Integer.toString(Integer.parseInt(tmpdate) + 1);
			nextstatday = tmpdate + "01";
			nextstatday += afterday;

		}

		List trcdnmlist = new ArrayList();
		StringTokenizer wb = new StringTokenizer( strcdlist, "," );
		while( wb.hasMoreTokens() ) {
			trcdnmlist.add( wb.nextToken() );
		}

		try{
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST,request)).trim();
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID,request)).trim();
			String bankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID,request)).trim();

			if( authlist.equals("*") ){
				//超级管理员
				bankid = " ";
			}else{
				//没有权限
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.op.no.power");
		  	request.removeAttribute(mapping.getAttribute());
		  	return myforward;
			}

			//处理autosta表数据
			AutostaModel myAutostaModel = new AutostaModel();
			Autosta myAutosta = new Autosta();

			myAutosta.setId( " " );//Need deal
			myAutosta.setStatename( myCharSet.form2db(statename) );
			myAutosta.setBankid( mbankid );
			myAutosta.setStatesort( statesort );
			myAutosta.setStatetype( statetype );
			myAutosta.setAfterday( Integer.toString(Integer.parseInt(afterday)) );
			myAutosta.setAftertime( aftertime );
			myAutosta.setNextstatday( nextstatday );
			myAutosta.setLaststat( " " );
			myAutosta.setCount( "0" );
			myAutosta.setAdddate( myPubUtil.getNowDate(1) );
			myAutosta.setOpentag( opentag );
			myAutosta.setTrcdtypes( trcdtypes );
			myAutosta.setInfo( myCharSet.form2db(info) );
			myAutosta.setRemark1( " " );
			myAutosta.setRemark2( " " );

			myAutostaModel.addAutosta( myAutosta );

			myforward = mapping.findForward("StatADeal");

		}catch(Exception e){
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}