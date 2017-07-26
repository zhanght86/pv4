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

import com.lcjr.pvxp.actionform.EatCardLogDeleteForm;
import com.lcjr.pvxp.model.EatCardLogModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> ÍÌ¿¨¼ÇÂ¼É¾³ýAction
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> ÀË³±½ðÈÚÊÂÒµ²¿(LCJR)
 * </p>
 * <br>
 * 
 * @author ÑîÐñ
 * @version 1.0 2005/3/31
 */
public final class EatCardLogDeleteAction extends Action {

	Logger log = Logger.getLogger(EatCardLogDeleteAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");

		try {
			// OplogModel myOplogModel = new OplogModel();
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (!authlist.equals("*")) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			String fromdate = (String) ((EatCardLogDeleteForm) form).getFromdate().trim();

			// EatCardLogModel myEatCardLogModel = new EatCardLogModel();
			if (EatCardLogModel.deleteEcLog(fromdate)) {
				OplogModel.insertOplog(operid, "1", "sys", "pvxp.oplog.syssetup.deleclog.success|" + fromdate);
				errors.add("deleatcardlog", new ActionMessage("pvxp.syssetup.deleclog.success"));
			} else {
				OplogModel.insertOplog(operid, "1", "sys", "pvxp.oplog.syssetup.deleclog.failed");
				errors.add("deleatcardlog", new ActionMessage("pvxp.syssetup.deleclog.failed"));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
			}
			myforward = new ActionForward(mapping.getInput());
			// request.removeAttribute(mapping.getAttribute());
			return myforward;
		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
			return myforward;
		}
	}
}