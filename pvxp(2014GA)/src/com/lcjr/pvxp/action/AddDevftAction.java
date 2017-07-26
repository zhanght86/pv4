package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.AddDevftForm;
import com.lcjr.pvxp.model.DevftModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.orm.Devftinfo;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 添加设备厂商Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2010
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.0 2010/09/27
 */
public final class AddDevftAction extends Action {

	Logger log = Logger.getLogger(AddDevftAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("execute开始");
		ActionForward myForward = mapping.findForward("SystemError");
		PubUtil pubUtil = new PubUtil();
		try {
			String authlist = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (authlist.equals("*")) {
				// 超级管理员
			} else if (Integer.parseInt(authlist.substring(0, 1)) < 2) {
				// 没有权限
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myForward;
			}

			myForward = mapping.findForward("Add_Devft_Done");
			CharSet charSet = new CharSet();

			String devftNo = pubUtil.dealNull(((AddDevftForm) form).getDevftNo()).trim();
			String devftName = pubUtil.dealNull(((AddDevftForm) form).getDevftName()).trim();
			String contact1 = pubUtil.dealNull(((AddDevftForm) form).getContact1()).trim();
			String phone1 = pubUtil.dealNull(((AddDevftForm) form).getPhone1()).trim();
			String mobile1 = pubUtil.dealNull(((AddDevftForm) form).getMobile1()).trim();
			String contact2 = pubUtil.dealNull(((AddDevftForm) form).getContact2()).trim();
			String phone2 = pubUtil.dealNull(((AddDevftForm) form).getPhone2()).trim();
			String mobile2 = pubUtil.dealNull(((AddDevftForm) form).getMobile2()).trim();
			String addr = pubUtil.dealNull(((AddDevftForm) form).getAddr()).trim();

			devftName = charSet.form2db(devftName);
			contact1 = charSet.form2db(contact1);
			phone1 = charSet.form2db(phone1);
			mobile1 = charSet.form2db(mobile1);
			contact2 = charSet.form2db(contact2);
			phone2 = charSet.form2db(phone2);
			mobile2 = charSet.form2db(mobile2);
			addr = charSet.form2db(addr);

			if (contact1 == null || contact1.length() == 0)
				contact1 = "-";
			if (phone1 == null || phone1.length() == 0)
				phone1 = "-";
			if (mobile1 == null || mobile1.length() == 0)
				mobile1 = "-";
			if (contact2 == null || contact2.length() == 0)
				contact2 = "-";
			if (phone2 == null || phone2.length() == 0)
				phone2 = "-";
			if (mobile2 == null || mobile2.length() == 0)
				mobile2 = "-";
			if (addr == null || addr.length() == 0)
				addr = "-";

			// 检查设备厂商是否已存在
			int index = DevftModel.indexOfDevftList(devftNo);
			if (index != -1) {
				request.setAttribute(Constants.REQUEST_DEVFT_MESSAGE, "pvxp.devft.add.exist");
				return myForward;
			}

			// 创建一个新的Devftinfo
			Devftinfo aDevft = new Devftinfo();
			aDevft.setDevftno(devftNo);
			aDevft.setDevftname(devftName);
			aDevft.setContact1(contact1);
			aDevft.setPhone1(phone1);
			aDevft.setMobile1(mobile1);
			aDevft.setContact2(contact2);
			aDevft.setPhone2(phone2);
			aDevft.setMobile2(mobile2);

			aDevft.setRemark1("remark1");
			aDevft.setRemark2("remark2");
			aDevft.setRemark3("remark3");
			aDevft.setRemark4(addr);

			// 将aDevft添加到数据库
			int ret = DevftModel.addDevft(aDevft);
			String operid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
			if (ret == -1) {
				OplogModel.insertOplog(operid, "2", "23", "pvxp.oplog.devft.add.fail|" + devftNo);
				request.setAttribute(Constants.REQUEST_DEVFT_MESSAGE, "pvxp.devft.add.fail");
			} else if (ret == 0) {
				OplogModel.insertOplog(operid, "2", "23", "pvxp.oplog.devft.add.success|" + devftNo);
				request.setAttribute(Constants.REQUEST_DEVFT_MESSAGE, "pvxp.devft.add.success");
			} else if (ret == 1) {
				DevftModel.resetNow();
				OplogModel.insertOplog(operid, "2", "23", "pvxp.oplog.devft.add.needrebuild|" + devftNo);
				request.setAttribute(Constants.REQUEST_DEVFT_MESSAGE, "pvxp.devft.add.needrebuild");
			}
		} catch (Exception e) {
			log.error("execute()", e);
			myForward = mapping.findForward("SystemError");
		}

		return myForward;
	}
}
