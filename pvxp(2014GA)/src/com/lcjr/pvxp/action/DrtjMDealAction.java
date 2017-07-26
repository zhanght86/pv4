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

import com.lcjr.pvxp.actionform.DrtjMDealForm;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.DrtjDayServer;
import com.lcjr.pvxp.util.DrtjMonthServer;
import com.lcjr.pvxp.util.DrtjYearServer;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 设备开机率统计Action
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
 * @version 1.0 2010/08/02
 */
public final class DrtjMDealAction extends Action {

	Logger log = Logger.getLogger(DrtjMDealAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] devlist = (String[]) ((DrtjMDealForm) form).getDevlist();
		String repnm = (String) ((DrtjMDealForm) form).getRepnm().trim();
		String qseq = (String) ((DrtjMDealForm) form).getQseq();
		String qbegin = (String) ((DrtjMDealForm) form).getQbegin();
		String qend = (String) ((DrtjMDealForm) form).getQend();
		String statmode = (String) ((DrtjMDealForm) form).getStatmode();

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
			} else if (Integer.parseInt(authlist.substring(10, 11)) < 2) {
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

			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and ";
			HQLstr += " devdate>='" + qbegin + "' and devdate<='" + qend + "'";
			if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
				HQLstr += " order by devno, devdate";

			if (qseq.equals("day")) {
				// 日统计报表

				DrtjDayServer myDrtjDayServer = new DrtjDayServer();

				myDrtjDayServer.setHQLstr(HQLstr);
				myDrtjDayServer.setBankid(bankid);
				myDrtjDayServer.setOperid(operid);
				myDrtjDayServer.setRepnm(repnm);
				myDrtjDayServer.setQbegin(qbegin);
				myDrtjDayServer.setQend(qend);
				myDrtjDayServer.setFilepath(filepath);
				myDrtjDayServer.setStatmode(statmode);
				myDrtjDayServer.setDevnoList(devnoList);

				myDrtjDayServer.start();
			} else if (qseq.equals("month")) {
				// 月统计报表
				DrtjMonthServer myDrtjMonthServer = new DrtjMonthServer();

				myDrtjMonthServer.setHQLstr(HQLstr);
				myDrtjMonthServer.setBankid(bankid);
				myDrtjMonthServer.setOperid(operid);
				myDrtjMonthServer.setRepnm(repnm);
				myDrtjMonthServer.setQbegin(qbegin);
				myDrtjMonthServer.setQend(qend);
				myDrtjMonthServer.setFilepath(filepath);
				myDrtjMonthServer.setStatmode(statmode);
				myDrtjMonthServer.setDevnoList(devnoList);

				myDrtjMonthServer.start();

			} else if (qseq.equals("year")) {
				// 年统计报表

				DrtjYearServer myDrtjYearServer = new DrtjYearServer();

				myDrtjYearServer.setHQLstr(HQLstr);
				myDrtjYearServer.setBankid(bankid);
				myDrtjYearServer.setOperid(operid);
				myDrtjYearServer.setRepnm(repnm);
				myDrtjYearServer.setQbegin(qbegin);
				myDrtjYearServer.setQend(qend);
				myDrtjYearServer.setFilepath(filepath);
				myDrtjYearServer.setStatmode(statmode);
				myDrtjYearServer.setDevnoList(devnoList);

				myDrtjYearServer.start();
			}

			myforward = mapping.findForward("DrtjMDeal");

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}