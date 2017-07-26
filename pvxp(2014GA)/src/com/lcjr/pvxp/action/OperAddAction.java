package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.actionform.OperAddForm;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.Operator;
import com.lcjr.pvxp.model.OplogModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> ��Ӳ���ԱAction
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * <br>
 * 
 * @author ��̫��
 * @version 1.0 2005/3/1
 */
public final class OperAddAction extends Action {
	
	Logger log = Logger.getLogger("web.com.lcjr.pvxp.action.OperAddAction.java");
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		CharSet myCharSet = new CharSet();
		
		String myoperid = (String) ((OperAddForm) form).getOperid().trim();
		String mybankid = (String) ((OperAddForm) form).getBankid().trim();
		String myoperpasswd = (String) ((OperAddForm) form).getOperpasswd().trim();
		String myopername = myCharSet.form2db((String) ((OperAddForm) form).getOpername()).trim();
		String myoperstate = (String) ((OperAddForm) form).getOperstate().trim();
		String myopertype = (String) ((OperAddForm) form).getOpertype().trim();
		String myauthlist = (String) ((OperAddForm) form).getAuthlist().trim();
		
		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");
		
		if (myoperid == null || myoperid.length() == 0) {
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.submit.wrong");
			request.removeAttribute(mapping.getAttribute());
			return myforward;
		}
		
		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			String myPower = "";
			if (authlist.equals("*")) {
				// ��������Ա
				myPower = "*";
			} else if (authlist.length() > 0 && Integer.parseInt(authlist.substring(2, 3)) > 1) {
				// �ж�д����Ȩ��
				myPower = authlist.substring(2, 3);
			} else {
				// û��Ȩ��
				myPower = "0";
			}
			// ���浱ǰ������Ȩ��
			request.setAttribute(Constants.REQUEST_OPER_POWER, myPower);
			// û��Ȩ�ޱ���
			if (myPower.equals("0")) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
			
			OperatorModel myOperatorModel = new OperatorModel();
			
			Operator temp = myOperatorModel.getOperator(myoperid);
			// ����Ա������
			if (temp != null) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.operator.exist");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
			
			Operator myOperator = new Operator();
			MD5 myMD5 = new MD5();
			
			myOperator.setOperid((String) myoperid);
			myOperator.setBankid((String) mybankid);
			myOperator.setPassword((String) myMD5.getMD5ofStr(myoperpasswd));
			myOperator.setName((String) myopername);
			myOperator.setOpertype(myopertype);
			myOperator.setAuthlist(myauthlist);
			myOperator.setState((String) myoperstate);
			// �������������
			String afterdays = myPubUtil.ReadConfig("Users", "WarningBefore", "3", "PowerView.ini");
			String validdate = myPubUtil.getOtherDate(Integer.parseInt(afterdays));
			myOperator.setAdddate(validdate);
			
			myOperator.setLoginnum("");
			myOperator.setLastlogin("");
			
			int iRetflag = myOperatorModel.addOper(myOperator);
			// �����Ϣд���ݿ�ʧ��
			if (iRetflag != 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.operator.adderror");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
			
			
			// ��¼����Ա������ˮ
			OplogModel myOplogModel = new OplogModel();
			String soperid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			int logflag = 0;
			logflag = myOplogModel.insertOplog(soperid, "2", "2", "pvxp.oplog.operator.add|" + myoperid);
			
			request.setAttribute(Constants.REQUEST_OPERATOR, (Operator) myOperatorModel.getOperator(myoperid));
			
			myforward = mapping.findForward("Operator_AddDone");
		} catch (Exception e) {
			log.error("ERROR", e);
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}
		
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}