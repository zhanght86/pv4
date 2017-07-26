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

import com.lcjr.pvxp.actionform.DevftDelForm;
import com.lcjr.pvxp.model.DevftModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 设备厂商删除Action
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
public final class DevftDelAction extends Action {

	Logger log = Logger.getLogger(DevftDelAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("execute开始");
		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] devftArry = (String[]) ((DevftDelForm) form).getDevftArry();

		PubUtil myPubUtil = new PubUtil();
		String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
		ActionForward myforward = mapping.findForward("SystemError");
		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (authlist.equals("*")) {
				// 超级管理员
			} else if (Integer.parseInt(authlist.substring(23, 24)) < 2) {
				// 没有权限
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			if (devftArry == null || devftArry.length == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devft.no.selected");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			String[] result = null;

			try {
				DevftModel myDevftModel = new DevftModel();
				int num = devftArry.length;
				result = new String[num];

				boolean success;
				for (int i = 0; i < num; i++) {
					result[i] = String.valueOf(myDevftModel.deleteDevft(devftArry[i]));
					if (result[i].equals("-1")) {
						OplogModel.insertOplog(operid, "1", "23", "pvxp.oplog.devft.delete.fail|" + devftArry[i]);

					} else if (result[i].equals("0")) {
						OplogModel.insertOplog(operid, "1", "23", "pvxp.oplog.devft.delete.success|" + devftArry[i]);

					} else if (result[i].equals("1")) {
						OplogModel.insertOplog(operid, "1", "23", "pvxp.oplog.devft.delete.needrebuild|" + devftArry[i]);

					}

				}
			} catch (Exception e) {
				log.error("execute", e);
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			request.setAttribute("devftList", devftArry);
			request.setAttribute(Constants.REQUEST_DEVFT_DEL_DONE, result);
			myforward = mapping.findForward("DevftDelDone");

		} catch (Exception e) {
			log.error("execute", e);
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}

}
