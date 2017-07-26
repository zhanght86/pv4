package com.lcjr.pvxp.action;

import javax.servlet.http.Cookie;
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

import com.lcjr.pvxp.actionform.ChangePWDForm;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 操作员修改密码Action
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
 * @author 杨旭
 * @version 1.0 2004/04/01
 */
public final class ChangePWDAction extends Action {

	Logger log = Logger.getLogger(ChangePWDAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();

		String operpwd = (String) ((ChangePWDForm) form).getOperpwd();
		String newpwd1 = (String) ((ChangePWDForm) form).getNewpwd1();

		PubUtil myPubUtil = new PubUtil();
		CharSet myCharSet = new CharSet();
		String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();

		ActionForward myforward = mapping.findForward("SystemError");
		try {
			OperatorModel myOpModel = new OperatorModel();
			int ret = myOpModel.changePasswd(operid, operpwd, newpwd1);

			if (ret == 0) {
				myforward = mapping.findForward("ChangePWD_Success");
				request.removeAttribute(mapping.getAttribute());
				Cookie opCookie = null;
				opCookie = new Cookie(Constants.COOKIE_OPER_OPERID, "");
				opCookie.setMaxAge(-1);
				opCookie.setPath("/");
				response.addCookie(opCookie);
				myOpModel.resetPassWDvalid(operid);
				myOpModel.resetState(operid, "0");
				return myforward;
			} else if (ret == -1) {
				errors.add("changepwd", new ActionMessage("pvxp.changepwd.errors.oldpwd.notmatch"));
				myforward = new ActionForward(mapping.getInput());
				saveErrors(request, errors);
				return myforward;
			} else if (ret == 1) {
				errors.add("changepwd", new ActionMessage("pvxp.changepwd.failed"));
				myforward = new ActionForward(mapping.getInput());
				saveErrors(request, errors);
				return myforward;
			} else {
				errors.add("changepwd", new ActionMessage("pvxp.errors.system.syserror"));
				myforward = new ActionForward(mapping.getInput());
				saveErrors(request, errors);
				return myforward;
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