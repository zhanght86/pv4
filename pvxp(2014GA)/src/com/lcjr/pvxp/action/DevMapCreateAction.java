package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.lcjr.pvxp.actionform.DevMapCreateForm;
import com.lcjr.pvxp.model.DevMapModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 设备类型列表Action
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
 * @version 1.0 2004/12/31
 */
public final class DevMapCreateAction extends Action {

	Logger log = Logger.getLogger(DevMapCreateAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("execute开始");
		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();

		PubUtil myPubUtil = new PubUtil();
		String bankid = myPubUtil.dealNull((String) ((DevMapCreateForm) form).getBankid()).trim();
		String imgname = myPubUtil.dealNull((String) ((DevMapCreateForm) form).getImgname()).trim();
		OplogModel myOplogModel = new OplogModel();
		ActionForward myforward = mapping.findForward("DevMapCreate_Fail");
		try {
			if (bankid.length() < 1 || imgname.length() < 1) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.submit.wrong");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
			OperatorModel myOperatorModel = new OperatorModel();
			int mypower = myOperatorModel.getPower(5, request);
			if (mypower < 2) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
			DevMapModel myDevMapModel = new DevMapModel(bankid);
			if (myDevMapModel.getMapname() != null) { // 已经存在
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devmap.exits");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
			int ret = myDevMapModel.creatBlankMap(operid, imgname);
			if (ret == 1) { // 已经存在
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devmap.exits");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			} else if (ret == -1) { // 创建失败
				myOplogModel.insertOplog(operid, "2", "5", "pvxp.oplog.devmap.create.failed|" + bankid);
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devmap.createfail");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			} else if (ret == 0) { // 创建成功
				myOplogModel.insertOplog(operid, "2", "5", "pvxp.oplog.devmap.create.success|" + bankid);
				request.setAttribute(Constants.REQUEST_DEVMAP_BANKID, bankid);
				myforward = mapping.findForward("DevMapCreate_Success");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			} else { // 其它(永远不出现)
				myforward = mapping.findForward("SystemError");
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
		} catch (Exception e) {
			log.info("execute", e);
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
			request.removeAttribute(mapping.getAttribute());
			return myforward;
		}

	}

}