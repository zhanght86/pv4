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

import com.lcjr.pvxp.actionform.BankinfoDeleteForm;
import com.lcjr.pvxp.bean.AutostaBean;
import com.lcjr.pvxp.bean.BankinfoBean;
import com.lcjr.pvxp.bean.DevinfoBean;
import com.lcjr.pvxp.bean.OperatorBean;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> ����ɾ��Action
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
 * @version 1.1 2005/3/3
 */
public final class BankinfoDeleteAction extends Action {

	Logger log = Logger.getLogger(BankinfoDeleteAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("execute��ʼ");
		ActionMessages errors = new ActionMessages();
		String[] bankinfoArry = (String[]) ((BankinfoDeleteForm) form).getBankinfoArry();

		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");
		try {
			String bankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
			String mybankid = "";
			for (int i = 0; i < bankinfoArry.length; i++) {
				mybankid = bankinfoArry[i].trim();
				if (bankid.equals(mybankid)) {
					request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.cannot.opselfbank");
					request.removeAttribute(mapping.getAttribute());
					return myforward;
				}
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

			if (bankinfoArry == null || bankinfoArry.length == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.bankinfo.no.selected");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			String[] result = null;
			mybankid = "";

			try {
				BankinfoModel myBankinfoModel = new BankinfoModel();


				int num = bankinfoArry.length;
				result = new String[num];
				String BankRange = "";

				// ɾ��
				try {
					for (int i = 0; i < bankinfoArry.length; i++) {
						BankRange = myBankinfoModel.getBankRange(bankinfoArry[i]);
						if (BankRange.indexOf("A") == -1) {
							if (BankinfoBean.deleteBankinfo(BankRange)) {
								result[i] = "0";
							} else {
								result[i] = "-1";
							}
						} else {
							BankRange = BankRange.substring(0, BankRange.length() - 1);
							if (BankinfoBean.deleteSomeBankinfo(BankRange)) {
								result[i] = "0";
							} else {
								result[i] = "-1";
							}
						}

						// ɾ�������豸
						DevinfoBean.deleteSomeDevinfo(BankRange);
						// ɾ����������Ա
						OperatorBean.deleteSomeOperator(BankRange);
						// ɾ�������Զ�����
						AutostaBean.deleteSomeAutosta(BankRange);
					}
					BankinfoModel.resetNow();
					DevinfoModel.resetNow();
				} catch (Exception e) {
				}

				// ��¼����Ա������ˮ
				String soperid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
				mybankid = "";
				for (int i = 0; i < bankinfoArry.length; i++) {
					mybankid += bankinfoArry[i] + ", ";
				}
				mybankid = mybankid.substring(0, mybankid.length() - 2);
				OplogModel.insertOplog(soperid, "1", "1", "pvxp.oplog.bankinfo.delete|" + mybankid);

			} catch (Exception e) {
				log.error("execute", e);
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			request.setAttribute(Constants.REQUEST_BANKINFO_DEL_DONE, result);
			myforward = mapping.findForward("Bankinfo_DeleteDone");

		} catch (Exception e) {
			log.error("execute", e);
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}