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
import org.apache.struts.util.MessageResources;

import com.lcjr.pvxp.actionform.JytjMDealForm;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.JytjDayServer;
import com.lcjr.pvxp.util.JytjMonthServer;
import com.lcjr.pvxp.util.JytjYearServer;
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
 * @author 刘太沛
 * @version 1.0 2005/3/30
 */
public final class JytjMDealAction extends Action {

	Logger log = Logger.getLogger(JytjMDealAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] devlist = (String[]) ((JytjMDealForm) form).getDevlist();
		String[] qunit = (String[]) ((JytjMDealForm) form).getQunit();
		String repnm = (String) ((JytjMDealForm) form).getRepnm().trim();
		String qseq = (String) ((JytjMDealForm) form).getQseq();
		String qbegin = (String) ((JytjMDealForm) form).getQbegin();
		String qend = (String) ((JytjMDealForm) form).getQend();
		String strcdlist = (String) ((JytjMDealForm) form).getStrcdlist();
		String statmode = (String) ((JytjMDealForm) form).getStatmode();

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

			// 处理统计单位
			String uflag = "";
			if (qunit.length == 1) {
				if (qunit[0].equals("bal")) {
					uflag = "2";
				} else if (qunit[0].equals("trs")) {
					uflag = "1";
				}
			} else if (qunit.length == 2) {
				uflag = "3";
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
			// 向sql语句中添加设备编号
			String HQLstr = "from Trcdtj" + qseq + " where (";
			for (int i = 0; i < devlist.length; i++) {
				HQLstr += " devno='" + devlist[i] + "' or";
			}

			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and (";

			// 向sql语句中添加业务编号
			for (int i = 0; i < trcdlist.size(); i++) {
				HQLstr += " trcdtype='" + trcdlist.get(i) + "' or";
			}
			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and ";

			HQLstr += " devdate>='" + qbegin + "' and devdate<='" + qend + "'";
			if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
				HQLstr += " order by devno, devdate, moneytype";

			if (qseq.equals("day")) {
				// 日统计报表

				JytjDayServer myJytjDayServer = new JytjDayServer();

				myJytjDayServer.setHQLstr(HQLstr);
				myJytjDayServer.setBankid(bankid);
				myJytjDayServer.setOperid(operid);
				myJytjDayServer.setRepnm(repnm);
				myJytjDayServer.setQbegin(qbegin);
				myJytjDayServer.setQend(qend);
				myJytjDayServer.setUflag(uflag);
				myJytjDayServer.setFilepath(filepath);
				myJytjDayServer.setTrcdlist(trcdlist);
				myJytjDayServer.setStatmode(statmode);
				myJytjDayServer.setDevnoList(devnoList);

				myJytjDayServer.start();
			} else if (qseq.equals("month")) {
				// 月统计报表

				JytjMonthServer myJytjMonthServer = new JytjMonthServer();

				myJytjMonthServer.setHQLstr(HQLstr);
				myJytjMonthServer.setBankid(bankid);
				myJytjMonthServer.setOperid(operid);
				myJytjMonthServer.setRepnm(repnm);
				myJytjMonthServer.setQbegin(qbegin);
				myJytjMonthServer.setQend(qend);
				myJytjMonthServer.setUflag(uflag);
				myJytjMonthServer.setFilepath(filepath);
				myJytjMonthServer.setTrcdlist(trcdlist);
				myJytjMonthServer.setStatmode(statmode);
				myJytjMonthServer.setDevnoList(devnoList);

				myJytjMonthServer.start();
			} else if (qseq.equals("year")) {
				// 年统计报表

				JytjYearServer myJytjYearServer = new JytjYearServer();

				myJytjYearServer.setHQLstr(HQLstr);
				myJytjYearServer.setBankid(bankid);
				myJytjYearServer.setOperid(operid);
				myJytjYearServer.setRepnm(repnm);
				myJytjYearServer.setQbegin(qbegin);
				myJytjYearServer.setQend(qend);
				myJytjYearServer.setUflag(uflag);
				myJytjYearServer.setFilepath(filepath);
				myJytjYearServer.setTrcdlist(trcdlist);
				myJytjYearServer.setStatmode(statmode);
				myJytjYearServer.setDevnoList(devnoList);

				myJytjYearServer.start();
			}

			myforward = mapping.findForward("JytjMDeal");

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}