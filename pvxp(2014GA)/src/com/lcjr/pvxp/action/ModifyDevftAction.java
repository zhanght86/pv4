package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.ModifyDevftForm;
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
 * <b>Description:</b> 修改设备厂商信息Action
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
public final class ModifyDevftAction extends Action {
	
	Logger log = Logger.getLogger(ModifyDevftAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward myForward = mapping.findForward("SystemError");
		PubUtil myPubUtil = new PubUtil();
		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (authlist.equals("*")) {
				// 超级管理员
			} else if (Integer.parseInt(authlist.substring(0, 1)) < 2) {
				// 没有权限
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myForward;
			}

			String modify = ((ModifyDevftForm) form).getModify();
			if (modify.equals("true")) {
				myForward = mapping.findForward("Modify_Devft");
				return myForward;
			}

			CharSet charSet = new CharSet();
			PubUtil util = new PubUtil();

			String devftNo = ((ModifyDevftForm) form).getDevftNo();
			String devftName = ((ModifyDevftForm) form).getDevftName();
			String contact1 = ((ModifyDevftForm) form).getContact1();
			String phone1 = ((ModifyDevftForm) form).getPhone1();
			String mobile1 = ((ModifyDevftForm) form).getMobile1();
			String contact2 = ((ModifyDevftForm) form).getContact2();
			String phone2 = ((ModifyDevftForm) form).getPhone2();
			String mobile2 = ((ModifyDevftForm) form).getMobile2();
			String addr = ((ModifyDevftForm) form).getAddr();

			devftNo = util.dealNull(devftNo).trim();
			devftName = util.dealNull(devftName).trim();
			contact1 = util.dealNull(contact1).trim();
			phone1 = util.dealNull(phone1).trim();
			mobile1 = util.dealNull(mobile1).trim();
			contact2 = util.dealNull(contact2).trim();
			phone2 = util.dealNull(phone2).trim();
			mobile2 = util.dealNull(mobile2).trim();
			addr = util.dealNull(addr).trim();

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

			// 获取设备厂商对象
			Devftinfo aDevft = DevftModel.getDevftFromList(devftNo);

			aDevft.setDevftname(devftName);
			aDevft.setContact1(contact1);
			aDevft.setPhone1(phone1);
			aDevft.setMobile1(mobile1);
			aDevft.setContact2(contact2);
			aDevft.setPhone2(phone2);
			aDevft.setMobile2(mobile2);
			aDevft.setRemark4(addr);

			// 更新设备厂商信息
			int ret = DevftModel.updateDevft(aDevft, devftNo);
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
			if (ret == -1) {
				OplogModel.insertOplog(operid, "0", "23", "pvxp.oplog.devft.modify.fail|" + devftNo);
				request.setAttribute(Constants.REQUEST_DEVFT_MESSAGE, "pvxp.devft.modify.fail");
			} else if (ret == 0) {
				OplogModel.insertOplog(operid, "0", "23", "pvxp.oplog.devft.modify.success|" + devftNo);
				request.setAttribute(Constants.REQUEST_DEVFT_MESSAGE, "pvxp.devft.modify.success");
			} else if (ret == 1) {
				DevftModel.resetNow();
				OplogModel.insertOplog(operid, "0", "23", "pvxp.oplog.devft.modify.needrebuild|" + devftNo);
				request.setAttribute(Constants.REQUEST_DEVFT_MESSAGE, "pvxp.devft.modify.needrebuild");
			}

			myForward = mapping.findForward("Modify_Devft_Done");
		} catch (Exception e) {
			myForward = mapping.findForward("SystemError");
		}

		return myForward;
	}
}
