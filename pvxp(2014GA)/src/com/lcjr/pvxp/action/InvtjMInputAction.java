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

import com.lcjr.pvxp.bean.SysParamBean;
import com.lcjr.pvxp.orm.SysParam;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> �豸����ͳ��ϵͳ��ϢAction
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2006
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * <br>
 * 
 * @author
 * @version 1.0 2006/01/17
 */
public final class InvtjMInputAction extends Action {

	Logger log = Logger.getLogger(InvtjMInputAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");

		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			String myPower = "";
			if (authlist.equals("*")) {
				// ��������Ա
				myPower = "*";
			} else if (authlist.length() > 0 && Integer.parseInt(authlist.substring(10, 11)) > 1) {
				// �ж�д����Ȩ��
				myPower = authlist.substring(10, 11);
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

			SysParamBean mySysParamBean = new SysParamBean();
			SysParam mySysParam = (SysParam) mySysParamBean.getSysParam();

			request.setAttribute(Constants.REQUEST_SYSPARAM, mySysParam);

			myforward = mapping.findForward("InvtjMInput");
		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}