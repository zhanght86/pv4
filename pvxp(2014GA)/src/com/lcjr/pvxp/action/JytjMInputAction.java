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
 * <b>Description:</b> 系统信息Action
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
 * @version 1.0 2005/3/28
 */
public final class JytjMInputAction extends Action {
	
	Logger log = Logger.getLogger(JytjMInputAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");

		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			String myPower = "";
			if (authlist.equals("*")) {
				// 超级管理员
				myPower = "*";
			} else if (authlist.length() > 0 && Integer.parseInt(authlist.substring(10, 11)) > 1) {
				// 有读写级别权限
				myPower = authlist.substring(10, 11);
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

			SysParamBean mySysParamBean = new SysParamBean();
			SysParam mySysParam = (SysParam) mySysParamBean.getSysParam();

			// 记录操作员操作流水
			/*
			 * OplogModel myOplogModel = new OplogModel(); String soperid =
			 * myPubUtil
			 * .dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID
			 * ,request)).trim(); int logflag = 0; logflag =
			 * myOplogModel.insertOplog( soperid, "0", "0104",
			 * "pvxp.oplog.operator.modify|"+myoperid );
			 */

			request.setAttribute(Constants.REQUEST_SYSPARAM, mySysParam);

			myforward = mapping.findForward("JytjMInput");
		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}