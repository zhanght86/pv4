package com.lcjr.pvxp.ini;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;
/**
 * 卡卡转账交易明细
 * @author ibmsz
 *
 */
public class CczzjymxAction extends Action {
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] devlist = (String[])((CczzjymxForm) form).getDevlist();
		String repnm = (String)((CczzjymxForm) form).getRepnm().trim();
		String qseq = (String)((CczzjymxForm) form).getQseq();
		String qbegin = (String)((CczzjymxForm) form).getQbegin();
		String qend = (String)((CczzjymxForm) form).getQend();
		String strcdlist = (String)((CczzjymxForm) form).getStrcdlist();
		String statmode = (String)((CczzjymxForm) form).getStatmode();

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

			if( (devlist==null) || (devlist.length==0) ) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.devinfo.no.selected");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			//处理交易代码
			StringTokenizer wb = new StringTokenizer( strcdlist, "," );
			List trcdlist = new ArrayList();
			while( wb.hasMoreTokens() ) {
				trcdlist.add( wb.nextToken() );
			}

			//处理设备编号
			List devnoList = new ArrayList();
			for( int i=0; i<devlist.length; i++ ) {
				devnoList.add( devlist[i] );
			}


			//生成HQL
			String HQLstr = "";
			String HQLstr1 = "from Trcdlog where (";
			String HQLstr2 = "from Mxb where (";
			String HQLstr3 = "from Mxb_tmp where (";
			
			for( int i=0; i<devlist.length; i++ ) {
				HQLstr += " devno='" + devlist[i] + "' or";
			}
			HQLstr = HQLstr.substring(0,HQLstr.length()-3) + " ) and (";
			for( int i=0; i<trcdlist.size(); i++ ) {
				HQLstr += " devtrcd='" + trcdlist.get(i) + "' or";
			}
			HQLstr = HQLstr.substring(0,HQLstr.length()-3) + " ) and ";
			HQLstr += " devdate>='" + qbegin + "' and devdate<='" + qend + "'";
			if( ( myPubUtil.dealNull(Constants.DB_OP_TYPE) ).equals("1") )
				HQLstr += " order by devno, devdate";
				
			HQLstr1 += HQLstr;
			HQLstr2 += HQLstr;
			HQLstr3 += HQLstr;

			if( qseq.equals("day") ) {
				

				CczzjymxDayServer myJymlDayServer = new CczzjymxDayServer();

				myJymlDayServer.setHQLstr1( HQLstr1 );
				myJymlDayServer.setHQLstr2( HQLstr2 );
				myJymlDayServer.setHQLstr3( HQLstr3 );
				myJymlDayServer.setBankid( bankid );
				myJymlDayServer.setOperid( operid );
				myJymlDayServer.setRepnm( repnm );
				myJymlDayServer.setQbegin( qbegin );
				myJymlDayServer.setQend( qend );
				myJymlDayServer.setFilepath( filepath );
				myJymlDayServer.setTrcdlist( trcdlist );
				myJymlDayServer.setStatmode( statmode );
				myJymlDayServer.setDevnoList( devnoList );

				myJymlDayServer.start();
			}

			myforward = mapping.findForward("cczzjymxDone");

		}catch(Exception e){
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}
