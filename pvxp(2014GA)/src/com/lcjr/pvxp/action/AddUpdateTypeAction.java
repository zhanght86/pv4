package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.AddUpdateTypeForm;
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
 * <b>Description:</b> 添加更新类型Action
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
public final class AddUpdateTypeAction extends Action {

	Logger log = Logger.getLogger(AddUpdateTypeAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("execute开始");

		ActionForward myForward = mapping.findForward("SystemError");
		PubUtil pubUtil = new PubUtil();
		try {
			String authlist = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (authlist.equals("*")) {
				// 超级管理员
			} else {
				// 没有权限
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myForward;
			}

			myForward = mapping.findForward("Add_UpdateType_Done");
			CharSet charSet = new CharSet();

			String updateno = pubUtil.dealNull(((AddUpdateTypeForm) form).getUpdateno()).trim();
			String updatename = pubUtil.dealNull(((AddUpdateTypeForm) form).getUpdatename()).trim();
			String info = pubUtil.dealNull(((AddUpdateTypeForm) form).getInfo()).trim();

			updatename = charSet.form2db(updatename);
			info = charSet.form2db(info);

			// 检查设备类型是否已存在
			int index = UpdateTypeModel.indexOfUpdateTypeList(updateno);
			if (index != -1) {
				request.setAttribute(Constants.REQUEST_UPDATETYPE_MESSAGE, "pvxp.updatetype.add.exist");
				return myForward;
			}

			// 创建一个新的Updatetype
			Updatetype aUpdateType = new Updatetype();
			aUpdateType.setUpdateno(updateno);
			aUpdateType.setUpdatename(updatename);
			aUpdateType.setInfo(info);
			aUpdateType.setRemark1(" ");
			aUpdateType.setRemark2(" ");
			aUpdateType.setRemark3(" ");

			// 将aUpdateType添加到数据库
			int ret = UpdateTypeModel.addUpdateType(aUpdateType);
			String operid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
			if (ret == -1) {
				OplogModel.insertOplog(operid, "2", "40", "pvxp.oplog.updatetype.add.fail|" + updateno);
				request.setAttribute(Constants.REQUEST_UPDATETYPE_MESSAGE, "pvxp.updatetype.add.fail");
			} else if (ret == 0) {
				OplogModel.insertOplog(operid, "2", "40", "pvxp.oplog.updatetype.add.success|" + updateno);
				request.setAttribute(Constants.REQUEST_UPDATETYPE_MESSAGE, "pvxp.updatetype.add.success");
			} else if (ret == 1) {
				UpdateTypeModel.resetNow();
				OplogModel.insertOplog(operid, "2", "40", "pvxp.oplog.updatetype.add.needrebuild|" + updateno);
				request.setAttribute(Constants.REQUEST_UPDATETYPE_MESSAGE, "pvxp.updatetype.add.success");
			}
		} catch (Exception e) {
			log.error("execute", e);
			myForward = mapping.findForward("SystemError");
		}

		return myForward;
	}
}
