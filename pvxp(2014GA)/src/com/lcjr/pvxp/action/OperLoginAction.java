package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.actionform.OperLoginForm;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.Operator;

import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> ����Ա��¼����Action</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author ����
 * @version 1.1 2004/12/15
 */

/**
 * <p>
 * <b>Description:</b>���ε���¼ʱ�Բ���Ա״̬������
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.1 2007/09/20
 */
public final class OperLoginAction extends Action {
	
	Logger log = Logger.getLogger("web.com.lcjr.pvxp.action.OperLoginAction.java");
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();// ������Ϣ
		// ǿ��ת����OperLoginForm���͵�form��
		String operid = (String) ((OperLoginForm) form).getOperid();
		String operpwd = (String) ((OperLoginForm) form).getOperpwd();
		// ��ô��ļ�
		PubUtil myPubUtil = new PubUtil();
		// CharSet myCharSet = new CharSet();
		ActionForward myforward = mapping.findForward("LoginSuccess");
		
		OperatorModel myOpModel = new OperatorModel();
		String tmpoperid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
		if (!tmpoperid.equals("")) { // ����ַ�����Ϊ�գ���Ѱ��input��ָ���ķ��򣬷��ص�¼���棬������ʾ�Ѿ���¼���û����
			myforward = new ActionForward(mapping.getInput());
			request.removeAttribute(mapping.getAttribute());
			return myforward;
		}
		
		
		// ����ַ�����Ϊ�գ��ͽ��е�¼��֤
		try {
			Operator myOper = myOpModel.validateOper(operid, operpwd);// �õ�operator��
			String nowdate = myPubUtil.getNowDate(1);// ��õ�ǰ���ڣ�����1�����ͽ�����ʾ��¼����
			if (myOper == null) {// �����¼���ɹ�
				log.warn("��¼��ţ�" + operid + "--��¼ʧ��");
				errors.add("system", new ActionMessage("pvxp.errors.database.no.suchop"));
				myforward = new ActionForward(mapping.getInput());
			} else {// ��¼�ɹ�
				log.info("��¼��ţ�" + operid + "--��¼�ɹ�");
				/*
				 * if((myOper.getState()).equals("1")){ //����Ա״̬Ϊ������
				 * errors.add("system", new
				 * ActionMessage("pvxp.state.op.disable")); myforward = new
				 * ActionForward( mapping.getInput() ); }else
				 */

				if ((!((myPubUtil.dealNull(myOper.getAuthlist())).trim().equals("*"))) && Integer.parseInt(myPubUtil.dealNull(myOper.getAdddate()).trim()) <= Integer.parseInt(nowdate)) // ��֤�����Ƿ����
				{
					log.warn("��¼��ţ�" + operid + "--�������");
					errors.add("system", new ActionMessage("pvxp.state.op.passwd.outdate"));
					myforward = new ActionForward(mapping.getInput());
				} else {
					
					
					// Cookie���ֺ�ֵ�����ܰ����հ��ַ��Լ������ַ���[ ] ( ) = , " / ? @ : ;
					// ������Ӳ���Ա��ʱ��Ҫ���й���
					
					// ���cookie
					Cookie opCookie = null;
					// ����Ա���
					opCookie = new Cookie(Constants.COOKIE_OPER_OPERID, myOper.getOperid().trim());
					opCookie.setMaxAge(-1);
					opCookie.setPath("/");
					response.addCookie(opCookie);
					
					
					// ����Ա����
					/*
					 * Cookie���ܴ溺�� System.out.println("...Start save opname");
					 * opCookie = new Cookie(Constants.COOKIE_OPER_OPERNAME,
					 * myCharSet.db2web(myOper.getName()).trim());
					 * opCookie.setMaxAge(-1); opCookie.setPath("/");
					 * response.addCookie(opCookie); System.out.println("...End
					 * save opname");
					 */

					String myAuthlist = myOper.getAuthlist().trim();
					String mybankid = "";
					
					
					// ��õ�¼�û����ڵ����б��
					if (!myAuthlist.equals("*")) {
						mybankid = myOper.getBankid().trim();
						// BankinfoModel myBankinfoModel = new BankinfoModel();
						if (BankinfoModel.getBankinfoFromList(mybankid) == null) {
							log.warn("��¼��ţ�" + operid + "--��������δ֪");
							errors.add("system", new ActionMessage("pvxp.state.op.bankid.unknown"));
							myforward = new ActionForward(mapping.getInput());
							saveErrors(request, errors);
							request.removeAttribute(mapping.getAttribute());
							return myforward;
						}
					} else {
						mybankid = PubUtil.strFormat("0", Constants.INT_BANKID_LEN, 0, '0');
					}
					
					
					// ����ԱȨ�ޱ���
					opCookie = new Cookie(Constants.COOKIE_OPER_AUTHLIST, myAuthlist);
					opCookie.setMaxAge(-1);
					opCookie.setPath("/");
					response.addCookie(opCookie);
					
					
					// ����Ա�����������
					opCookie = new Cookie(Constants.COOKIE_OPER_BANKID, mybankid);
					opCookie.setMaxAge(-1);
					opCookie.setPath("/");
					response.addCookie(opCookie);
					
					
					// ����Ա����
					opCookie = new Cookie(Constants.COOKIE_OPER_OPERTYPE, myOper.getOpertype().trim());
					opCookie.setMaxAge(-1);
					opCookie.setPath("/");
					response.addCookie(opCookie);
					// ����Ա��¼����
					String strloginnum = myPubUtil.dealNull(myOper.getLoginnum()).trim();
					int loginnum = 0;
					try {
						loginnum = Integer.parseInt(strloginnum) + 1;
						strloginnum = String.valueOf(loginnum);
					} catch (Exception e) {
						loginnum = 1;
						strloginnum = "1";
					}
					opCookie = new Cookie(Constants.COOKIE_OPER_OPERLOGINNUM, strloginnum);
					opCookie.setMaxAge(-1);
					opCookie.setPath("/");
					response.addCookie(opCookie);
					// ����¼ʱ��
					String lastlogin = myPubUtil.dealNull(myOper.getLastlogin()).trim();
					opCookie = new Cookie(Constants.COOKIE_OPER_OPERLASTLOGIN, lastlogin);
					opCookie.setMaxAge(-1);
					opCookie.setPath("/");
					response.addCookie(opCookie);
					
					
					// ���²���Ա��Ϣ
					myOper.setLoginnum(strloginnum);
					myOper.setState("1");
					lastlogin = myPubUtil.getNowDate(3) + " " + myPubUtil.getNowTime();
					myOper.setLastlogin(lastlogin);
					myOper.setName(myPubUtil.dealNull(myOper.getName()).trim());
					OperatorModel.updateOper(myOper, myOper.getOperid());
				}
			}
		} catch (Exception mex) {
			log.warn("WARN", mex);
			errors.add("system", new ActionMessage("pvxp.errors.system.syserror"));
			myforward = new ActionForward(mapping.getInput());
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}