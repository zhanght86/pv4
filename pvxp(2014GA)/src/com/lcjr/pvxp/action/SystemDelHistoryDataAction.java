package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.actionform.SystemDelHistoryDataForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 系统设置-删除历史数据文件Action
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
 * @version 1.0 2005/03/31
 */
public final class SystemDelHistoryDataAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		
		PubUtil myPubUtil = new PubUtil();
		String months = myPubUtil.dealNull((String) ((SystemDelHistoryDataForm) form).getMonths()).trim();
		String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
		// wukp20120312 OplogModel myOplogModel = new OplogModel();
		ActionForward myforward = mapping.findForward("SystemError");
		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (!authlist.equals("*")) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
			CommUtil myCommUtil = new CommUtil();
			String ret = myCommUtil.delHistoryDataFile(months);
			if (ret == null || (ret.indexOf("00") != 0)) {
				OplogModel.insertOplog(operid, "1", "sys", "pvxp.oplog.syssetup.deldata.failed");
				errors.add("deletedata", new ActionMessage("pvxp.syssetup.deldata.failed"));
			} else {
				OplogModel.insertOplog(operid, "1", "sys", "pvxp.oplog.syssetup.deldata.success|" + months);
				errors.add("deletedata", new ActionMessage("pvxp.syssetup.deldata.success"));
			}
			
			myforward = new ActionForward(mapping.getInput());
			
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
			}
			
			
			// request.removeAttribute(mapping.getAttribute());
			return myforward;
		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
			request.removeAttribute(mapping.getAttribute());
			return myforward;
		}
		
	}
	
}