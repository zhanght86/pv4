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

import com.lcjr.pvxp.actionform.FileForm;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 文件上传Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2008
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.0 2008/5/5
 */
public final class FileAction extends Action {

	
	Logger log = Logger.getLogger(FileAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String filepath = (String) ((FileForm) form).getFilepath().trim();

		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");
		try {
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (authlist.equals("*")) {
				// 超级管理员
			} else if (authlist.length() > 0 && Integer.parseInt(authlist.substring(3, 4)) > 1) {
				// 有管理权限
			} else {
				// 没有权限
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			request.setAttribute(Constants.REQUEST_FILEUPLOAD_RESULT, filepath);
			myforward = mapping.findForward("file_result");

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}