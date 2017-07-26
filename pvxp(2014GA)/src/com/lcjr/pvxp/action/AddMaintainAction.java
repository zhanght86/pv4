package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.AddMaintainForm;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.MaintainModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.orm.Devinfo;
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
 * <b>Description:</b> 添加报修记录Action
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
 * @version 1.0 2011/02/24
 */
public final class AddMaintainAction extends Action {

	Logger log = Logger.getLogger(AddMaintainAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("execute开始");
		ActionForward myForward = mapping.findForward("SystemError");
		PubUtil pubUtil = new PubUtil();
		try {
			String operid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
			int myPower = new OperatorModel().getPower(0, request);
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			myForward = mapping.findForward("Add_Maintain_Done");
			CharSet charSet = new CharSet();

			String nowdate = pubUtil.getNowDate(1);
			String nowtime = pubUtil.getNowTime();

			String devno = pubUtil.dealNull(((AddMaintainForm) form).getDevno()).trim();
			String trbphen = pubUtil.dealNull(((AddMaintainForm) form).getTrbphen()).trim();
			String trbdate = pubUtil.dealNull(((AddMaintainForm) form).getTrbdate()).trim();
			String trbtime = pubUtil.dealNull(((AddMaintainForm) form).getTrbtime()).trim();
			String repairs = pubUtil.dealNull(((AddMaintainForm) form).getRepairs()).trim();
			String repbank = pubUtil.dealNull(((AddMaintainForm) form).getRepbank()).trim();
			String[] subdevice = ((AddMaintainForm) form).getSubdevice();

			trbphen = charSet.form2db(trbphen);
			repairs = charSet.form2db(repairs);
			trbdate = charSet.form2db(trbdate);
			trbtime = charSet.form2db(trbtime);

			Devinfo devinfo = DevinfoModel.getDevFromList(devno);
			String bankid = pubUtil.dealNull(devinfo.getBankid()).trim();

			if (trbphen == null || trbphen.length() == 0) {
				trbphen = "-";
			}
			if (trbdate == null || trbdate.length() == 0) {
				trbdate = nowdate;
			}
			if (trbtime == null || trbtime.length() == 0) {
				trbtime = nowtime;
			}

			if (repairs == null || repairs.length() == 0) {
				repairs = "-";
			}
			if (repbank == null || repbank.length() == 0) {
				repbank = "-";
			}
			if (bankid == null || bankid.length() == 0) {
				bankid = "-";
			}

			for (int i = 0; i < subdevice.length; i++) {
				String part = subdevice[i].trim();

				// 创建一个新的maintain
				Maintain aMaintian = new Maintain();
				aMaintian.setDevno(devno);
				aMaintian.setBankid(repbank);
				aMaintian.setTrbtype(part);
				aMaintian.setTrbdate(trbdate);
				aMaintian.setTrbtime(trbtime);
				aMaintian.setState("0");
				aMaintian.setRepairs(repairs);
				aMaintian.setTrbphen(trbphen);

				aMaintian.setDutyno("");
				aMaintian.setObvidate("");
				aMaintian.setObvitime("");
				aMaintian.setRemark("");

				aMaintian.setRemark1("");
				aMaintian.setRemark2("");
				aMaintian.setRemark3("");
				aMaintian.setRemark4("");

				// 将aMaintian添加到数据库
				int ret = MaintainModel.addMaintain(aMaintian);
				if (ret == -1) {
					OplogModel.insertOplog(operid, "2", "31", "pvxp.oplog.maintain.add.fail|" + devno);
					request.setAttribute(Constants.REQUEST_MAINTAIN_MESSAGE, "pvxp.maintain.add.fail");
				} else if (ret == 0) {
					OplogModel.insertOplog(operid, "2", "31", "pvxp.oplog.maintain.add.success|" + devno);
					request.setAttribute(Constants.REQUEST_MAINTAIN_MESSAGE, "pvxp.maintain.add.success");
				}
			}
		} catch (Exception e) {
			log.warn("WARN", e);
			myForward = mapping.findForward("SystemError");
		}
		return myForward;
	}
}