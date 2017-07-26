package com.lcjr.pvxp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.model.PluginModel;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PageBean;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 系统插件管理-插件删除Action
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
 * @version 1.0 2005/03/29
 */
public class SystemPluginDelAction extends Action {

	public List plugins;
	public PageBean pb;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward myforward = mapping.findForward("SystemError");
		PubUtil myPubUtil = new PubUtil();
		try {
			OplogModel myOplogModel = new OplogModel();
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();

			if (!authlist.equals("*")) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				myforward = mapping.findForward("SystemError");
				return myforward;
			}
			String plugid = request.getParameter("plugid");

			PluginModel myPluginModel = new PluginModel();

			if (myPluginModel.deletePlugin(plugid) == 0) {
				myOplogModel.insertOplog(operid, "1", "sys", "pvxp.oplog.syssetup.plugin.delete.success|" + plugid);
				myforward = mapping.findForward("PluginDel_Success");
			} else {
				myOplogModel.insertOplog(operid, "1", "sys", "pvxp.oplog.syssetup.plugin.delete.failed");
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.syssetup.plugin.delete.failed");
			}
		} catch (Exception e) {
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
		}
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}

}
