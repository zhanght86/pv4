package com.lcjr.pvxp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.ModifyMaintainForm;
import com.lcjr.pvxp.model.MaintainModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.orm.Maintain;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 修改报修记录信息Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2011
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.0 2011/03/01
 */
public final class ModifyMaintainAction extends Action {

	Logger log = Logger.getLogger(ModifyMaintainAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward myForward = mapping.findForward("SystemError");
		PubUtil myPubUtil = new PubUtil();
		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (authlist.equals("*")) {
				// 超级管理员
			} else if (authlist.length() > 0 && Integer.parseInt(authlist.substring(0, 1)) > 0) {
				// 没有权限
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myForward;
			}

			CharSet charSet = new CharSet();
			PubUtil util = new PubUtil();

			String devno = ((ModifyMaintainForm) form).getDevno();
			String trbtype = ((ModifyMaintainForm) form).getTrbtype();
			String trbdate = ((ModifyMaintainForm) form).getTrbdate();
			String trbtime = ((ModifyMaintainForm) form).getTrbtime();

			String obvidate = ((ModifyMaintainForm) form).getObvidate();
			String obvitime = ((ModifyMaintainForm) form).getObvitime();
			String remark = ((ModifyMaintainForm) form).getRemark();
			String dutyno = ((ModifyMaintainForm) form).getDutyno();
			String state = ((ModifyMaintainForm) form).getState();

			devno = util.dealNull(devno).trim();
			trbtype = util.dealNull(trbtype).trim();
			trbdate = util.dealNull(trbdate).trim();
			trbtime = util.dealNull(trbtime).trim();

			obvidate = util.dealNull(obvidate).trim();
			obvitime = util.dealNull(obvitime).trim();
			remark = util.dealNull(remark).trim();
			dutyno = util.dealNull(dutyno).trim();
			state = util.dealNull(state).trim();

			obvidate = charSet.form2db(obvidate);
			obvitime = charSet.form2db(obvitime);
			remark = charSet.form2db(remark);
			dutyno = charSet.form2db(dutyno);

			List maintainlist = MaintainModel.getSomeMaintain(devno, trbtype, trbdate, trbtime);

			Maintain maintain = (Maintain) maintainlist.get(0);

			maintain.setState(state);
			maintain.setDutyno(dutyno);
			maintain.setObvidate(obvidate);
			maintain.setObvitime(obvitime);
			maintain.setRemark(remark);

			maintain.setBankid(util.dealNull(maintain.getBankid()).trim());
			maintain.setRepairs(util.dealNull(maintain.getRepairs()).trim());
			maintain.setTrbphen(util.dealNull(maintain.getTrbphen()).trim());

			// 更新记录信息
			int ret = MaintainModel.updateMaintain(maintain, devno, trbtype, trbdate, trbtime);
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
			if (ret == -1) {
				OplogModel.insertOplog(operid, "0", "31", "pvxp.oplog.maintain.modify.fail|" + devno);
				request.setAttribute(Constants.REQUEST_MAINTAIN_MESSAGE, "pvxp.maintain.modify.fail");
			} else if (ret == 0) {
				OplogModel.insertOplog(operid, "0", "31", "pvxp.oplog.maintain.modify.success|" + devno);
				request.setAttribute(Constants.REQUEST_MAINTAIN_MESSAGE, "pvxp.maintain.modify.success");

			}

			myForward = mapping.findForward("Modify_Maintain_Done");
		} catch (Exception e) {
			log.error("ERROR", e);
			myForward = mapping.findForward("SystemError");
		}

		return myForward;
	}
}
