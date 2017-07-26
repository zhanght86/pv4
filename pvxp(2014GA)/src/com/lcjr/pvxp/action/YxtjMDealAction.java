package com.lcjr.pvxp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.lcjr.pvxp.actionform.YxtjMDealForm;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;
import com.lcjr.pvxp.util.YxtjDayServer;
import com.lcjr.pvxp.util.YxtjMonthServer;
import com.lcjr.pvxp.util.YxtjYearServer;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 运行统计条件统计Action
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
 * @version 1.0 2005/4/4
 */
public final class YxtjMDealAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] devlist = (String[]) ((YxtjMDealForm) form).getDevlist();
		String[] sortlist = (String[]) ((YxtjMDealForm) form).getSortname();
		String strcdlist = (String) ((YxtjMDealForm) form).getStrcdlist();
		String repnm = (String) ((YxtjMDealForm) form).getRepnm().trim();
		String qseq = (String) ((YxtjMDealForm) form).getQseq();
		String qbegin = (String) ((YxtjMDealForm) form).getQbegin();
		String qend = (String) ((YxtjMDealForm) form).getQend();

		List trcdlist = new ArrayList();
		String tmptrcd = "";
		StringTokenizer wb;

		PubUtil myPubUtil = new PubUtil();
		CharSet myCharSet = new CharSet();

		for (int i = 0; i < sortlist.length; i++) {
			tmptrcd = sortlist[i];
			wb = new StringTokenizer(tmptrcd, ",");
			while (wb.hasMoreTokens()) {
				trcdlist.add(wb.nextToken());
			}
		}

		List trcdnmlist = new ArrayList();
		wb = new StringTokenizer(myCharSet.form2GB(strcdlist), ",");
		while (wb.hasMoreTokens()) {
			trcdnmlist.add(wb.nextToken());
		}

		ActionForward myforward = mapping.findForward("SystemError");
		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			String bankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
			String filepath = getServlet().getServletContext().getRealPath("") + "/statresult/";

			if (authlist.equals("*")) {
				// 超级管理员
				bankid = " ";
			} else if (Integer.parseInt(authlist.substring(12, 13)) < 2) {
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
			String HQLstr = "from Trcdtj" + qseq + " where (";
			for (int i = 0; i < devlist.length; i++) {
				HQLstr += " devno='" + devlist[i] + "' or";
			}
			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and (";
			for (int i = 0; i < trcdlist.size(); i++) {
				HQLstr += " trcdtype='" + trcdlist.get(i) + "' or";
			}
			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and ";
			HQLstr += " devdate>='" + qbegin + "' and devdate<='" + qend + "'";
			if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
				HQLstr += " order by devno, devdate, moneytype";

			if (qseq.equals("day")) {
				// 日统计报表

				YxtjDayServer myYxtjDayServer = new YxtjDayServer();

				myYxtjDayServer.setHQLstr(HQLstr);
				myYxtjDayServer.setBankid(bankid);
				myYxtjDayServer.setOperid(operid);
				myYxtjDayServer.setRepnm(repnm);
				myYxtjDayServer.setQbegin(qbegin);
				myYxtjDayServer.setQend(qend);
				myYxtjDayServer.setFilepath(filepath);
				myYxtjDayServer.setSortlist(sortlist);
				myYxtjDayServer.setTrcdnmlist(trcdnmlist);
				myYxtjDayServer.setDevnoList(devnoList);

				myYxtjDayServer.start();
			} else if (qseq.equals("month")) {
				// 月统计报表

				YxtjMonthServer myYxtjMonthServer = new YxtjMonthServer();

				myYxtjMonthServer.setHQLstr(HQLstr);
				myYxtjMonthServer.setBankid(bankid);
				myYxtjMonthServer.setOperid(operid);
				myYxtjMonthServer.setRepnm(repnm);
				myYxtjMonthServer.setQbegin(qbegin);
				myYxtjMonthServer.setQend(qend);
				myYxtjMonthServer.setFilepath(filepath);
				myYxtjMonthServer.setSortlist(sortlist);
				myYxtjMonthServer.setTrcdnmlist(trcdnmlist);
				myYxtjMonthServer.setDevnoList(devnoList);

				myYxtjMonthServer.start();

			} else if (qseq.equals("year")) {
				// 年统计报表

				YxtjYearServer myYxtjYearServer = new YxtjYearServer();

				myYxtjYearServer.setHQLstr(HQLstr);
				myYxtjYearServer.setBankid(bankid);
				myYxtjYearServer.setOperid(operid);
				myYxtjYearServer.setRepnm(repnm);
				myYxtjYearServer.setQbegin(qbegin);
				myYxtjYearServer.setQend(qend);
				myYxtjYearServer.setFilepath(filepath);
				myYxtjYearServer.setSortlist(sortlist);
				myYxtjYearServer.setTrcdnmlist(trcdnmlist);
				myYxtjYearServer.setDevnoList(devnoList);

				myYxtjYearServer.start();

			}

			myforward = mapping.findForward("YxtjMDeal");

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}