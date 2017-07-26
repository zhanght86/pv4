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

import com.lcjr.pvxp.actionform.BankinfoDetailForm;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.orm.Bankinfo;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 机构详细信息Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author 刘太沛
 * @version 1.0 2005/3/2
 */
public final class BankinfoDetailAction extends Action {

	Logger log = Logger.getLogger(BankinfoDetailAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("execute开始");

		ActionMessages errors = new ActionMessages();
		String mybankid = (String) ((BankinfoDetailForm) form).getBankid();

		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");

		if (mybankid == null || mybankid.length() == 0) {
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.submit.wrong");
			request.removeAttribute(mapping.getAttribute());
			return myforward;
		}

		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			String myPower = "";
			if (authlist.equals("*")) {
				// 超级管理员
				myPower = "*";
			} else if (authlist.length() > 0 && Integer.parseInt(authlist.substring(1, 2)) > 0) {
				// 有浏览以上级别权限
				myPower = authlist.substring(1, 2);
			} else {
				// 没有权限
				myPower = "0";
			}
			// 保存当前操作的权限
			request.setAttribute(Constants.REQUEST_OPER_POWER, myPower);
			// 没有权限报错
			if (myPower.equals("0")) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			Bankinfo temp = BankinfoModel.getBankinfoFromList(mybankid);
			// 管理员不存在
			if (temp == null) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.bankinfo.no");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			request.setAttribute(Constants.REQUEST_BANKINFO, temp);

			myforward = mapping.findForward("Bankinfo_Detail");
		} catch (Exception e) {
			log.error("execute", e);
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}