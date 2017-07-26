package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.ModifyUpdateTypeForm;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.model.UpdateTypeModel;
import com.lcjr.pvxp.orm.Updatetype;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 修改更新类型Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2009
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.0 2009/12/07
 */
public final class ModifyUpdateTypeAction extends Action {

	Logger log = Logger.getLogger(ModifyUpdateTypeAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward myForward = mapping.findForward("SystemError");
		PubUtil myPubUtil = new PubUtil();
		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (authlist.equals("*")) {
				// 超级管理员
			} else {
				// 没有权限
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myForward;
			}

			String modify = ((ModifyUpdateTypeForm) form).getModify();
			if (modify.equals("true")) {
				myForward = mapping.findForward("Modify_UpdateType");
				return myForward;
			}

			CharSet charSet = new CharSet();
			PubUtil util = new PubUtil();

			String updateno = ((ModifyUpdateTypeForm) form).getUpdateno();
			String updatename = ((ModifyUpdateTypeForm) form).getUpdatename();
			String info = ((ModifyUpdateTypeForm) form).getInfo();

			updateno = util.dealNull(updateno).trim();
			updatename = util.dealNull(updatename).trim();
			info = util.dealNull(info).trim();

			updatename = charSet.form2db(updatename);
			info = charSet.form2db(info);
			// 获取更新类型对象
			Updatetype aUpdateType = UpdateTypeModel.getUpdateTypeFromList(updateno);

			// aUpdateType.setUpdateno(updateno);
			aUpdateType.setUpdatename(updatename);
			aUpdateType.setInfo(info);

			// 更新类型信息
			int ret = UpdateTypeModel.updateUpdateType(aUpdateType, updateno);
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
			if (ret == -1) {
				OplogModel.insertOplog(operid, "0", "40", "pvxp.oplog.updatetype.modify.fail|" + updateno);
				request.setAttribute(Constants.REQUEST_UPDATETYPE_MESSAGE, "pvxp.updatetype.modify.fail");
			} else if (ret == 0) {
				OplogModel.insertOplog(operid, "0", "40", "pvxp.oplog.updatetype.modify.success|" + updateno);
				request.setAttribute(Constants.REQUEST_UPDATETYPE_MESSAGE, "pvxp.updatetype.modify.success");
			} else if (ret == 1) {
				OplogModel.insertOplog(operid, "0", "40", "pvxp.oplog.updatetype.modify.needrebuild|" + updateno);
				UpdateTypeModel.resetNow();
				request.setAttribute(Constants.REQUEST_UPDATETYPE_MESSAGE, "pvxp.updatetype.modify.needrebuild");
			}

			myForward = mapping.findForward("Modify_UpdateType_Done");
		} catch (Exception e) {
			log.error("ERROR", e);
			myForward = mapping.findForward("SystemError");
		}

		return myForward;
	}
}
