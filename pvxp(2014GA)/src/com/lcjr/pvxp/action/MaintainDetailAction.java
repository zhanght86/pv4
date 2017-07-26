package com.lcjr.pvxp.action;

import java.util.List;

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

import com.lcjr.pvxp.actionform.MaintainDetailForm;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.MaintainModel;
import com.lcjr.pvxp.orm.Bankinfo;
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
 * <b>Description:</b> 报修记录详细信息Action
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
public final class MaintainDetailAction extends Action {

	Logger log = Logger.getLogger(MaintainDetailAction.class.getName());

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
			} else if (authlist.length() > 0 && Integer.parseInt(authlist.substring(0, 1)) > 0) {
				// 有浏览以上级别权限
				myPower = authlist.substring(0, 1);
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

			String devno = (String) ((MaintainDetailForm) form).getDevno();
			String trbtype = (String) ((MaintainDetailForm) form).getTrbtype();
			String trbdate = (String) ((MaintainDetailForm) form).getTrbdate();
			String trbtime = (String) ((MaintainDetailForm) form).getTrbtime();

			if (devno == null || devno.length() == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.submit.wrong");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			List maintainlist = MaintainModel.getSomeMaintain(devno, trbtype, trbdate, trbtime);
			Maintain maintain = (Maintain) maintainlist.get(0);

			// 报修记录是否已存在
			if (maintain == null) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.maintain.no");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			CharSet charSet = new CharSet();

			String[] maintainDetail = new String[12];

			Bankinfo tmp = null;
			maintainDetail[0] = myPubUtil.dealNull(maintain.getDevno()).trim();

			tmp = BankinfoModel.getBankinfoFromList(myPubUtil.dealNull(maintain.getBankid()).trim());
			if (tmp != null)
				maintainDetail[1] = tmp.getBanknm();
			else
				maintainDetail[1] = myPubUtil.dealNull(maintain.getBankid()).trim();

			maintainDetail[2] = maintain.getTrbtype();
			maintainDetail[3] = maintain.getTrbdate();
			maintainDetail[4] = maintain.getTrbtime();
			maintainDetail[5] = maintain.getState();
			maintainDetail[6] = maintain.getRepairs();
			maintainDetail[7] = maintain.getTrbphen();
			maintainDetail[8] = maintain.getDutyno();
			maintainDetail[9] = maintain.getObvidate();
			maintainDetail[10] = maintain.getObvitime();
			maintainDetail[11] = maintain.getRemark();

			maintainDetail[0] = myPubUtil.dealNull(maintainDetail[0]);
			maintainDetail[1] = myPubUtil.dealNull(maintainDetail[1]);
			maintainDetail[2] = myPubUtil.dealNull(maintainDetail[2]);
			maintainDetail[3] = myPubUtil.dealNull(maintainDetail[3]);
			maintainDetail[4] = myPubUtil.dealNull(maintainDetail[4]);
			maintainDetail[5] = myPubUtil.dealNull(maintainDetail[5]);
			maintainDetail[6] = myPubUtil.dealNull(maintainDetail[6]);
			maintainDetail[7] = myPubUtil.dealNull(maintainDetail[7]);
			maintainDetail[8] = myPubUtil.dealNull(maintainDetail[8]);
			maintainDetail[9] = myPubUtil.dealNull(maintainDetail[9]);
			maintainDetail[10] = myPubUtil.dealNull(maintainDetail[10]);
			maintainDetail[11] = myPubUtil.dealNull(maintainDetail[11]);

			request.setAttribute(Constants.REQUEST_MAINTAIN, maintainDetail);

			myforward = mapping.findForward("Maintain_Detail");

		} catch (Exception e) {
			log.error("ERROR", e);
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;

	}

}
