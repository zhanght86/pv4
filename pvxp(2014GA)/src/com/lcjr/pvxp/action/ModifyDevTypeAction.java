package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.ModifyDevTypeForm;
import com.lcjr.pvxp.model.DevtypeModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.orm.Devtype;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 修改设备类型Action</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 吴学坤
 * @version 1.0 2005/03/14
 */

/**
 * @author xucc
 * @Description:修改厂商名称为编号，去掉联系方式
 * @version 1.1 2010/09/28
 */
public final class ModifyDevTypeAction extends Action {

	Logger log = Logger.getLogger(ModifyDevTypeAction.class.getName());

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

			String modify = ((ModifyDevTypeForm) form).getModify();
			if (modify.equals("true")) {
				myForward = mapping.findForward("Modify_DevType");
				return myForward;
			}

			CharSet charSet = new CharSet();
			PubUtil util = new PubUtil();

			String devTypeNo = ((ModifyDevTypeForm) form).getDevTypeNo();
			String devTypeState = ((ModifyDevTypeForm) form).getDevTypeState();
			String devType = ((ModifyDevTypeForm) form).getDevType();
			String devName = ((ModifyDevTypeForm) form).getDevName();
			String devEquipt = ((ModifyDevTypeForm) form).getDevEquipt();
			String dataPackageType = ((ModifyDevTypeForm) form).getDataPackageType();
			String devftno = ((ModifyDevTypeForm) form).getDevftno();

			devTypeNo = util.dealNull(devTypeNo).trim();
			devTypeState = util.dealNull(devTypeState).trim();
			devType = util.dealNull(devType).trim();
			devName = util.dealNull(devName).trim();
			devEquipt = util.dealNull(devEquipt).trim();
			dataPackageType = util.dealNull(dataPackageType).trim();
			devftno = util.dealNull(devftno).trim();

			devType = charSet.form2db(devType);
			devName = charSet.form2db(devName);
			devEquipt = charSet.form2db(devEquipt);

			// 获取设备类型对象
			Devtype aDevType = DevtypeModel.getDevTpFromList(devTypeNo);

			aDevType.setTypestate(devTypeState);
			aDevType.setDevtype(devType);
			aDevType.setDevname(devName);
			aDevType.setDevinfo(devEquipt);
			aDevType.setPacktype(dataPackageType);
			aDevType.setDevftno(devftno);

			// 更新设备类型信息
			int ret = DevtypeModel.updateDevTp(aDevType, devTypeNo);
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
			if (ret == -1) {
				OplogModel.insertOplog(operid, "0", "20", "pvxp.oplog.devtype.modify.fail|" + devTypeNo);
				request.setAttribute(Constants.REQUEST_DEVTP_MESSAGE, "pvxp.devtype.modify.faile");
			} else if (ret == 0) {
				OplogModel.insertOplog(operid, "0", "20", "pvxp.oplog.devtype.modify.success|" + devTypeNo);
				request.setAttribute(Constants.REQUEST_DEVTP_MESSAGE, "pvxp.devtype.modify.success");
			} else if (ret == 1) {
				OplogModel.insertOplog(operid, "0", "20", "pvxp.oplog.devtype.modify.needrebuild|" + devTypeNo);
				DevtypeModel.resetNow();
				request.setAttribute(Constants.REQUEST_DEVTP_MESSAGE, "pvxp.devtype.modify.needrebuild");
			}

			myForward = mapping.findForward("Modify_DevType_Done");
		} catch (Exception e) {
			myForward = mapping.findForward("SystemError");
		}

		return myForward;
	}
}
