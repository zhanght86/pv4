package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.DevDeleteForm;
import com.lcjr.pvxp.model.DevinfoModel;
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
 * <b>Description:</b> 删除设备Action
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
 * @author 吴学坤
 * @version 1.0 2005/03/10
 */
public final class DevDeleteAction extends Action {

	Logger log = Logger.getLogger(DevDeleteAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward myForward = mapping.findForward("SystemError");
		PubUtil pubUtil = new PubUtil();
		try {
			String operid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
			int myPower = new OperatorModel().getPower(0, request);
			// 没有权限报错
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			String[] devList = ((DevDeleteForm) form).getDevList();

			if (devList.length == 0) {
				request.removeAttribute(mapping.getAttribute());
				return myForward;
			} else {
				int[] result = new int[devList.length];
				for (int i = 0; i < devList.length; i++) {
					result[i] = DevinfoModel.deleteDev(devList[i]);

					switch (result[i]) {
					case -1:
						OplogModel.insertOplog(operid, "1", "0", "pvxp.oplog.devinfo.delete.fail|" + devList[i]);
						break;
					case 0:
						OplogModel.insertOplog(operid, "1", "0", "pvxp.oplog.devinfo.delete.success|" + devList[i]);
						break;
					case 1:
						OplogModel.insertOplog(operid, "1", "0", "pvxp.oplog.devinfo.delete.success|" + devList[i]);
						break;
					}
				}

				request.setAttribute("devList", devList);
				request.setAttribute(Constants.REQUEST_DEVDELETE_DONE, result);
				myForward = mapping.findForward("Dev_Delete_Done");
			}

		} catch (Exception e) {
			log.error("ERROR", e);
			myForward = mapping.findForward("SystemError");
		}

		request.removeAttribute(mapping.getAttribute());
		return myForward;
	}
}