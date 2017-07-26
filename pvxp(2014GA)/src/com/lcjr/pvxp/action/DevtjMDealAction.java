package com.lcjr.pvxp.action;

import java.util.ArrayList;
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

import com.lcjr.pvxp.actionform.DevtjMDealForm;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.DevtjDayServer;
import com.lcjr.pvxp.util.DevtjMonthServer;
import com.lcjr.pvxp.util.DevtjYearServer;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 设备故障统计条件统计Action
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
 * @author 刘太沛
 * @version 1.0 2005/4/5
 */
public final class DevtjMDealAction extends Action {

	Logger log = Logger.getLogger(DevtjMDealAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] devlist = (String[]) ((DevtjMDealForm) form).getDevlist();
		String[] subdevice = (String[]) ((DevtjMDealForm) form).getSubdevice();
		String repnm = (String) ((DevtjMDealForm) form).getRepnm().trim();
		String qseq = (String) ((DevtjMDealForm) form).getQseq();
		String qbegin = (String) ((DevtjMDealForm) form).getQbegin();
		String qend = (String) ((DevtjMDealForm) form).getQend();

		PubUtil myPubUtil = new PubUtil();
		CharSet myCharSet = new CharSet();
		ActionForward myforward = mapping.findForward("SystemError");

		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			String bankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
			String filepath = getServlet().getServletContext().getRealPath("") + "/statresult/";

			if (authlist.equals("*")) {
				// 超级管理员
				bankid = " ";
			} else if (Integer.parseInt(authlist.substring(13, 14)) < 2) {
				// 没有权限
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			if ((devlist == null) || (devlist.length == 0)) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devinfo.no.selected");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			// 处理设备编号
			List devnoList = new ArrayList();
			for (int i = 0; i < devlist.length; i++) {
				devnoList.add(devlist[i]);
			}

			// 生成HQL
			String HQLstr = "from Devtj" + qseq + " where (";

			for (int i = 0; i < devlist.length; i++) {
				HQLstr += " devno='" + devlist[i] + "' or";
			}

			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and (";

			HQLstr += " stateno='Z001' or";
			for (int i = 0; i < subdevice.length; i++) {
				HQLstr += " stateno='" + subdevice[i] + "' or";
			}

			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and ";
			HQLstr += " devdate>='" + qbegin + "' and devdate<='" + qend + "'";
			if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
				HQLstr += " order by devno, devdate, stateno";

			if (qseq.equals("day")) {
				// 日统计报表

				DevtjDayServer myDevtjDayServer = new DevtjDayServer();

				myDevtjDayServer.setHQLstr(HQLstr);
				myDevtjDayServer.setBankid(bankid);
				myDevtjDayServer.setOperid(operid);
				myDevtjDayServer.setRepnm(repnm);
				myDevtjDayServer.setQbegin(qbegin);
				myDevtjDayServer.setQend(qend);
				myDevtjDayServer.setFilepath(filepath);
				myDevtjDayServer.setDevnoList(devnoList);

				myDevtjDayServer.start();

			} else if (qseq.equals("month")) {
				// 月统计报表

				DevtjMonthServer myDevtjMonthServer = new DevtjMonthServer();

				myDevtjMonthServer.setHQLstr(HQLstr);
				myDevtjMonthServer.setBankid(bankid);
				myDevtjMonthServer.setOperid(operid);
				myDevtjMonthServer.setRepnm(repnm);
				myDevtjMonthServer.setQbegin(qbegin);
				myDevtjMonthServer.setQend(qend);
				myDevtjMonthServer.setFilepath(filepath);
				myDevtjMonthServer.setDevnoList(devnoList);

				myDevtjMonthServer.start();
			} else if (qseq.equals("year")) {
				// 年统计报表

				DevtjYearServer myDevtjYearServer = new DevtjYearServer();

				myDevtjYearServer.setHQLstr(HQLstr);
				myDevtjYearServer.setBankid(bankid);
				myDevtjYearServer.setOperid(operid);
				myDevtjYearServer.setRepnm(repnm);
				myDevtjYearServer.setQbegin(qbegin);
				myDevtjYearServer.setQend(qend);
				myDevtjYearServer.setFilepath(filepath);
				myDevtjYearServer.setDevnoList(devnoList);

				myDevtjYearServer.start();
			}

			myforward = mapping.findForward("DevtjMDeal");

		} catch (Exception e) {
			log.error("execute", e);
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}