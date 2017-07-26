package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.AddDevTypeForm;
import com.lcjr.pvxp.model.DevtypeModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.orm.Devtype;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 添加设备类型Action</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 吴学坤
 * @version 1.0 2005/03/12
 */

/**
 * @author xucc
 * @version 1.1 2010/09/29
 */

/**
 * @author 武坤鹏
 * @version 1.1 2012/07/11
 */
public final class AddDevTypeAction extends Action {

	Logger log = Logger.getLogger(AddDevTypeAction.class.getName());

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

			myForward = mapping.findForward("Add_DevType_Done");
			CharSet charSet = new CharSet();

			String devTypeNo = pubUtil.dealNull(((AddDevTypeForm) form).getDevTypeNo()).trim();
			String devTypeState = pubUtil.dealNull(((AddDevTypeForm) form).getDevTypeState()).trim();
			String devType = pubUtil.dealNull(((AddDevTypeForm) form).getDevType()).trim();
			String devName = pubUtil.dealNull(((AddDevTypeForm) form).getDevName()).trim();
			String devEquipt = pubUtil.dealNull(((AddDevTypeForm) form).getDevEquipt()).trim();
			String dataPackageType = pubUtil.dealNull(((AddDevTypeForm) form).getDataPackageType()).trim();
			String devftno = pubUtil.dealNull(((AddDevTypeForm) form).getDevftno()).trim();

			devType = charSet.form2db(devType);
			devName = charSet.form2db(devName);
			devEquipt = charSet.form2db(devEquipt);

			// 检查设备类型是否已存在
			int index = DevtypeModel.indexOfDevTpList(devTypeNo);
			if (index != -1) {
				request.setAttribute(Constants.REQUEST_DEVTP_MESSAGE, "pvxp.devtype.add.exist");
				return myForward;
			}

			// 创建一个新的Devtype
			Devtype aDevType = new Devtype();
			aDevType.setTypeno(devTypeNo);
			aDevType.setTypestate(devTypeState);
			aDevType.setDevtype(devType);
			aDevType.setDevname(devName);
			aDevType.setDevinfo(devEquipt);
			aDevType.setPacktype(dataPackageType);
			aDevType.setDevftno(devftno);
			aDevType.setContact1("contact1");

			aDevType.setRemark1("remark1");
			aDevType.setRemark2("remark2");
			aDevType.setRemark3("remark3");
			aDevType.setRemark4("remark4");
			aDevType.setContact2("contact2");
			aDevType.setFilepath("filepath");

			// 将aDevType添加到数据库
			int ret = DevtypeModel.addDevTp(aDevType);
			String operid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
			if (ret == -1) {
				OplogModel.insertOplog(operid, "2", "20", "pvxp.oplog.devtype.add.fail|" + devTypeNo);
				request.setAttribute(Constants.REQUEST_DEVTP_MESSAGE, "pvxp.devtype.add.faile");
			} else if (ret == 0) {
				OplogModel.insertOplog(operid, "2", "20", "pvxp.oplog.devtype.add.success|" + devTypeNo);
				request.setAttribute(Constants.REQUEST_DEVTP_MESSAGE, "pvxp.devtype.add.success");
			} else if (ret == 1) {
				DevtypeModel.resetNow();
				OplogModel.insertOplog(operid, "2", "20", "pvxp.oplog.devtype.add.needrebuild|" + devTypeNo);
				request.setAttribute(Constants.REQUEST_DEVTP_MESSAGE, "pvxp.devtype.add.success");
			}
		} catch (Exception e) {
			log.info("execute", e);
			myForward = mapping.findForward("SystemError");
		}

		return myForward;
	}
}
