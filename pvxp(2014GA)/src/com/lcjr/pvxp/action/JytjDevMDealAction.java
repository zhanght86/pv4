package com.lcjr.pvxp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.lcjr.pvxp.actionform.JytjDevMDealForm;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.JytjDevDayServer;
import com.lcjr.pvxp.util.JytjDevMonthServer;
import com.lcjr.pvxp.util.JytjDevYearServer;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 交易统计条件统计Action
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
 * @author xucc
 * @version 1.0 2006/7/31
 */
public final class JytjDevMDealAction extends Action {

	Logger log = Logger.getLogger(JytjDevMDealAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionMessages errors = new ActionMessages();
		String[] devlist = (String[]) ((JytjDevMDealForm) form).getDevlist();
		String moneytype = (String) ((JytjDevMDealForm) form).getMoneyType();
		String repnm = (String) ((JytjDevMDealForm) form).getRepnm().trim();
		String qseq = (String) ((JytjDevMDealForm) form).getQseq();
		String qbegin = (String) ((JytjDevMDealForm) form).getQbegin();
		String qend = (String) ((JytjDevMDealForm) form).getQend();
		String strcdlist = (String) ((JytjDevMDealForm) form).getStrcdlist();
		String statmode = (String) ((JytjDevMDealForm) form).getStatmode();

		if (moneytype.equals("")) {
			moneytype = request.getParameter("moneytype");
		}

		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");

		try {

			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			String bankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();

			String filepath = getServlet().getServletContext().getRealPath("") + "/statresult/";

			if (authlist.equals("*")) {

				// 超级管理员
				bankid = " ";
			} else if (Integer.parseInt(authlist.substring(11, 12)) < 2) {

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

			// 处理交易代码
			StringTokenizer wb = new StringTokenizer(strcdlist, ",");
			List trcdlist = new ArrayList();
			while (wb.hasMoreTokens()) {
				trcdlist.add(wb.nextToken());
			}

			// 处理设备编号
			List devnoList = new ArrayList();
			for (int i = 0; i < devlist.length; i++) {
				devnoList.add(devlist[i]);
			}

			// 生成HQL
			String HQLstr = "from Trcdtj" + qseq + " where (";
			for (int i = 0; i < devlist.length; i++) {
				HQLstr += " devno='" + devlist[i] + "' or";
			}
			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and (";
			for (int i = 0; i < trcdlist.size(); i++) {
				HQLstr += " trcdtype='" + trcdlist.get(i) + "' or";
			}
			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and ";

			// 生成HQL
			String tmpHQLstr = "";

			if (qseq.equals("day")) {
				// 日统计报表
				JytjDevDayServer myJytjDevDayServer = new JytjDevDayServer();

				tmpHQLstr = HQLstr + " devdate>='" + qbegin + "' and devdate<='" + qend + "'";
				if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
					tmpHQLstr += " order by devno, devdate, moneytype";

				myJytjDevDayServer.setHQLstr(tmpHQLstr);
				myJytjDevDayServer.setBankid(bankid);
				myJytjDevDayServer.setOperid(operid);
				myJytjDevDayServer.setRepnm(repnm);
				myJytjDevDayServer.setQbegin(qbegin);
				myJytjDevDayServer.setQend(qend);
				myJytjDevDayServer.setMoneyType(moneytype);
				myJytjDevDayServer.setFilepath(filepath);
				myJytjDevDayServer.setTrcdlist(trcdlist);
				myJytjDevDayServer.setStatmode(statmode);
				myJytjDevDayServer.setDevnoList(devnoList);

				myJytjDevDayServer.start();
			} else if (qseq.equals("month")) {
				// 月统计报表
				JytjDevMonthServer myJytjDevMonthServer = new JytjDevMonthServer();

				tmpHQLstr = HQLstr + " devdate>='" + qbegin + "' and devdate<='" + qend + "'";
				if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
					tmpHQLstr += " order by devno, devdate, moneytype";

				myJytjDevMonthServer.setHQLstr(tmpHQLstr);
				myJytjDevMonthServer.setBankid(bankid);
				myJytjDevMonthServer.setOperid(operid);
				myJytjDevMonthServer.setRepnm(repnm);
				myJytjDevMonthServer.setQbegin(qbegin);
				myJytjDevMonthServer.setQend(qend);
				myJytjDevMonthServer.setMoneyType(moneytype);
				myJytjDevMonthServer.setFilepath(filepath);
				myJytjDevMonthServer.setTrcdlist(trcdlist);
				myJytjDevMonthServer.setStatmode(statmode);
				myJytjDevMonthServer.setDevnoList(devnoList);

				myJytjDevMonthServer.start();

			} else if (qseq.equals("year")) {
				// 年统计报表
				JytjDevYearServer myJytjDevYearServer = new JytjDevYearServer();

				tmpHQLstr = HQLstr + " devdate>='" + qbegin + "' and devdate<='" + qend + "'";
				if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
					tmpHQLstr += " order by devno, devdate, moneytype";

				myJytjDevYearServer.setHQLstr(tmpHQLstr);
				myJytjDevYearServer.setBankid(bankid);
				myJytjDevYearServer.setOperid(operid);
				myJytjDevYearServer.setRepnm(repnm);
				myJytjDevYearServer.setQbegin(qbegin);
				myJytjDevYearServer.setQend(qend);
				myJytjDevYearServer.setMoneyType(moneytype);
				myJytjDevYearServer.setFilepath(filepath);
				myJytjDevYearServer.setTrcdlist(trcdlist);
				myJytjDevYearServer.setStatmode(statmode);
				myJytjDevYearServer.setDevnoList(devnoList);

				myJytjDevYearServer.start();

			}

			myforward = mapping.findForward("JytjDevMDeal");

		} catch (Exception e) {
			log.error("ERROR", e);
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}