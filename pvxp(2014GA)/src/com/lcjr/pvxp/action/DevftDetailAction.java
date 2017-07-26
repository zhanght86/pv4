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

import com.lcjr.pvxp.actionform.DevftDetailForm;
import com.lcjr.pvxp.model.DevftModel;
import com.lcjr.pvxp.orm.Devftinfo;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 设备厂商详细信息Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2010
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.0 2010/09/26
 */
public final class DevftDetailAction extends Action {

	Logger log = Logger.getLogger(DevftDetailAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("execute开始");
		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String devftNo = (String) ((DevftDetailForm) form).getDevftNo();

		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");

		if (devftNo == null || devftNo.length() == 0) {
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
			} else if (authlist.length() > 0 && Integer.parseInt(authlist.substring(0, 1)) > 0) {
				// 有浏览以上级别权限
				myPower = authlist.substring(0, 1);
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

			Devftinfo devft = DevftModel.getDevftFromList(devftNo);
			// 设备厂商不存在
			if (devft == null) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.devft.no");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			CharSet charSet = new CharSet();

			String[] devftDetail = new String[9];
			devftDetail[0] = devft.getDevftno();
			devftDetail[1] = devft.getDevftname();
			devftDetail[2] = devft.getContact1();
			devftDetail[3] = devft.getPhone1();
			devftDetail[4] = devft.getMobile1();
			devftDetail[5] = devft.getContact2();
			devftDetail[6] = devft.getPhone2();
			devftDetail[7] = devft.getMobile2();
			devftDetail[8] = devft.getRemark4();

			devftDetail[0] = myPubUtil.dealNull(devftDetail[0]);
			devftDetail[1] = myPubUtil.dealNull(devftDetail[1]);
			devftDetail[2] = myPubUtil.dealNull(devftDetail[2]);
			devftDetail[3] = myPubUtil.dealNull(devftDetail[3]);
			devftDetail[4] = myPubUtil.dealNull(devftDetail[4]);
			devftDetail[5] = myPubUtil.dealNull(devftDetail[5]);
			devftDetail[6] = myPubUtil.dealNull(devftDetail[6]);
			devftDetail[7] = myPubUtil.dealNull(devftDetail[7]);
			devftDetail[8] = myPubUtil.dealNull(devftDetail[8]);

			devftDetail[1] = charSet.db2web(devftDetail[1]);
			devftDetail[2] = charSet.db2web(devftDetail[2]);
			devftDetail[3] = charSet.db2web(devftDetail[3]);
			devftDetail[4] = charSet.db2web(devftDetail[4]);
			devftDetail[5] = charSet.db2web(devftDetail[5]);
			devftDetail[6] = charSet.db2web(devftDetail[6]);
			devftDetail[7] = charSet.db2web(devftDetail[7]);
			devftDetail[8] = charSet.db2web(devftDetail[8]);

			request.setAttribute(Constants.REQUEST_DEVFT, devftDetail);

			myforward = mapping.findForward("Devft_Detail");
		} catch (Exception e) {
			log.error("execute", e);
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;

	}

}
