package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.actionform.OperModifyForm;
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
 * <p><b>Description:</b> ����Ա�޸���ϢAction</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author ��̫��
 * @version 1.0 2005/2/28
 */
public final class OperModifyAction extends Action
{

	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		PubUtil myPubUtil = new PubUtil();
		CharSet myCharSet = new CharSet();
		MD5 myMD5 = new MD5();
		String myoperid = (String)((OperModifyForm) form).getOperid().trim();
		String myopername = myCharSet.form2db((String)((OperModifyForm) form).getOpername());
		String myoperstate = (String)((OperModifyForm) form).getOperstate();
		String myopertype = (String)((OperModifyForm) form).getOpertype().trim();
		String myauthlist = (String)((OperModifyForm) form).getAuthlist().trim();
		String myoperpasswd = myPubUtil.dealNull((String)((OperModifyForm) form).getOperpasswd()).trim();

		int setpswdflag = 0;
		if( myoperpasswd.equals("") ) {
			setpswdflag = 0;
		}else {
			setpswdflag = 1;
		}

		ActionForward myforward = mapping.findForward("SystemError");


		if(myoperid==null||myoperid.length()==0){
		    	request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.submit.wrong");
		    	request.removeAttribute(mapping.getAttribute());
		    	return myforward;
 		}


		try{
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID,request)).trim();
			if ( operid.equals(myoperid) ) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.cannot.opselfoper");
	    			request.removeAttribute(mapping.getAttribute());
	    			return myforward;
			}

			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST,request)).trim();
			String myPower = "";
			if( authlist.equals("*") ){
				//��������Ա
				myPower = "*";
			}else if( authlist.length()>0&&Integer.parseInt(authlist.substring(2,3))>1 ){
				//�ж�д����Ȩ��
				myPower = authlist.substring(2,3);
			}else{
				//û��Ȩ��
				myPower = "0";
			}
			//���浱ǰ������Ȩ��
			request.setAttribute(Constants.REQUEST_OPER_POWER,myPower);
			//û��Ȩ�ޱ���
			if( myPower.equals("0") ){
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.op.no.power");
	    			request.removeAttribute(mapping.getAttribute());
	    			return myforward;
			}


			OperatorModel myOperatorModel = new OperatorModel();
			Operator temp = myOperatorModel.getOperator(myoperid);
			//����Ա������
			if(temp==null){
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.operator.no");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			temp.setName((String)myopername);
			temp.setState((String)myoperstate);
			temp.setOpertype((String)myopertype);
			temp.setAuthlist((String)myauthlist);
			//�ж��Ƿ���������
			if( setpswdflag == 1 ) {
				String afterdays = myPubUtil.ReadConfig( "Users", "WarningBefore", "3", "PowerView.ini" );
				String validdate = myPubUtil.getOtherDate(Integer.parseInt(afterdays));
				temp.setAdddate(validdate);
				temp.setPassword((String)myMD5.getMD5ofStr(myoperpasswd));
			}


			int iRetflag = myOperatorModel.updateOper( temp, myoperid );
			//�����Ϣд���ݿ�ʧ��
			if( iRetflag != 0 ){
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.operator.modifyerror");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			//��¼����Ա������ˮ
			OplogModel myOplogModel = new OplogModel();
			String soperid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID,request)).trim();
			int logflag = 0;
			logflag = myOplogModel.insertOplog( soperid, "0", "2", "pvxp.oplog.operator.modify|"+myoperid );

			request.setAttribute(Constants.REQUEST_OPERATOR,(Operator)myOperatorModel.getOperator(myoperid));

		}catch(Exception e){
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		if( setpswdflag == 1 ) {
			myforward = mapping.findForward("Operator_SetpswdDone");
			request.removeAttribute(mapping.getAttribute());
		}else if( setpswdflag == 0 ) {
			myforward = mapping.findForward("Operator_ModifyDone");
			request.removeAttribute(mapping.getAttribute());
		}else {
			myforward = mapping.findForward("SystemError");
		}

		return myforward;

	}
}