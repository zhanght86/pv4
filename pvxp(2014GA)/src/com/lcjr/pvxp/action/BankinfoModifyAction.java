package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.lcjr.pvxp.actionform.BankinfoModifyForm;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.orm.Bankinfo;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> �����޸���ϢAction
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
 * @version 1.0 2005/3/2
 */
public final class BankinfoModifyAction extends Action {

	Logger log = Logger.getLogger(BankinfoModifyAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("execute��ʼ");
		ActionMessages errors = new ActionMessages();
		CharSet myCharSet = new CharSet();
		String mybankid = (String) ((BankinfoModifyForm) form).getBankid().trim();
		String mybankname = myCharSet.form2db((String) ((BankinfoModifyForm) form).getBankname());
		String mybankaddr = myCharSet.form2db((String) ((BankinfoModifyForm) form).getBankaddr());
		String mybanktel = ((BankinfoModifyForm) form).getBanktel();
		String mybankstate = "0";

		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");

		if (mybankid == null || mybankid.length() == 0) {
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.submit.wrong");
			request.removeAttribute(mapping.getAttribute());
			return myforward;
		}

		try {
			String bankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
			if (bankid.equals(mybankid)) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.cannot.opselfbank");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			String myPower = "";
			if (authlist.equals("*")) {
				// ��������Ա
				myPower = "*";
			} else if (authlist.length() > 0 && Integer.parseInt(authlist.substring(1, 2)) > 0) {
				// ��������ϼ���Ȩ��
				myPower = authlist.substring(1, 2);
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

			Bankinfo myBankinfo = BankinfoModel.getBankinfoFromList(mybankid);
			// ����Ա������
			if (myBankinfo == null) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.bankinfo.no");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			myBankinfo.setBanknm((String) mybankname);
			myBankinfo.setBankaddr((String) mybankaddr);
			myBankinfo.setBanktel((String) mybanktel);
			myBankinfo.setState((String) mybankstate);

			BankinfoModel.updateBank(myBankinfo, mybankid);

			// ��¼����Ա������ˮ
			String soperid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			OplogModel.insertOplog(soperid, "0", "1", "pvxp.oplog.bankinfo.modify|" + mybankid);

			request.setAttribute(Constants.REQUEST_BANKINFO, (Bankinfo) BankinfoModel.getBankinfoFromList(mybankid));

			myforward = mapping.findForward("Bankinfo_ModifyDone");
		} catch (Exception e) {
			log.error("execute", e);
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}