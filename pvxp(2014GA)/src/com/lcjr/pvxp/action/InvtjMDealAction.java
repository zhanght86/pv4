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

import com.lcjr.pvxp.actionform.InvtjMDealForm;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.InvtjDayServer;
import com.lcjr.pvxp.util.InvtjMonthServer;
import com.lcjr.pvxp.util.InvtjYearServer;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 发票打印统计条件统计Action
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
 * @author
 * @version 1.0 2006/01/17
 */
public final class InvtjMDealAction extends Action {

	Logger log = Logger.getLogger(InvtjMDealAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] devlist = (String[]) ((InvtjMDealForm) form).getDevlist();
		String[] invtypelist = (String[]) ((InvtjMDealForm) form).getInvtypelist();
		String repnm = (String) ((InvtjMDealForm) form).getRepnm().trim();
		String qseq = (String) ((InvtjMDealForm) form).getQseq();
		String qbegin = (String) ((InvtjMDealForm) form).getQbegin();
		String qend = (String) ((InvtjMDealForm) form).getQend();

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

			if (qseq.equals("day")) {
				// 日统计报表

				InvtjDayServer myInvtjDayServer = new InvtjDayServer();

				// 生成HQL
				String HQLstr = "from InvLog where (";
				for (int i = 0; i < devlist.length; i++) {
					HQLstr += " devno='" + devlist[i] + "' or";
				}
				HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and (";
				for (int i = 0; i < invtypelist.length; i++) {
					HQLstr += " type='" + invtypelist[i] + "' or";
				}
				HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and ";
				HQLstr += " pdate>='" + qbegin + "' and pdate<='" + qend + "'";
				if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
					HQLstr += " order by devno,pdate,type";

				myInvtjDayServer.setHQLstr(HQLstr);
				myInvtjDayServer.setBankid(bankid);
				myInvtjDayServer.setOperid(operid);
				myInvtjDayServer.setRepnm(repnm);
				myInvtjDayServer.setQbegin(qbegin);
				myInvtjDayServer.setQend(qend);
				myInvtjDayServer.setFilepath(filepath);
				myInvtjDayServer.setDevnoList(devnoList);

				myInvtjDayServer.start();
			} else if (qseq.equals("month")) {
				// 月统计报表
				InvtjMonthServer myInvtjMonthServer = new InvtjMonthServer();
				qbegin += "01";
				qend += "31";
				// 生成HQL
				String HQLstr = "from InvLog where (";
				for (int i = 0; i < devlist.length; i++) {
					HQLstr += " devno='" + devlist[i] + "' or";
				}
				HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and (";
				for (int i = 0; i < invtypelist.length; i++) {
					HQLstr += " type='" + invtypelist[i] + "' or";
				}
				HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and ";
				HQLstr += " pdate>='" + qbegin + "' and pdate<='" + qend + "'";
				if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
					HQLstr += " order by devno,pdate,type";

				myInvtjMonthServer.setHQLstr(HQLstr);
				myInvtjMonthServer.setBankid(bankid);
				myInvtjMonthServer.setOperid(operid);
				myInvtjMonthServer.setRepnm(repnm);
				myInvtjMonthServer.setQbegin(qbegin);
				myInvtjMonthServer.setQend(qend);
				myInvtjMonthServer.setFilepath(filepath);
				myInvtjMonthServer.setDevnoList(devnoList);

				myInvtjMonthServer.start();

			} else if (qseq.equals("year")) {
				// 年统计报表
				InvtjYearServer myInvtjYearServer = new InvtjYearServer();
				qbegin += "0101";
				qend += "1231";
				// 生成HQL
				String HQLstr = "from InvLog where (";
				for (int i = 0; i < devlist.length; i++) {
					HQLstr += " devno='" + devlist[i] + "' or";
				}
				HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and (";
				for (int i = 0; i < invtypelist.length; i++) {
					HQLstr += " type='" + invtypelist[i] + "' or";
				}
				HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and ";
				HQLstr += " pdate>='" + qbegin + "' and pdate<='" + qend + "'";
				if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
					HQLstr += " order by devno,pdate,type";

				myInvtjYearServer.setHQLstr(HQLstr);
				myInvtjYearServer.setBankid(bankid);
				myInvtjYearServer.setOperid(operid);
				myInvtjYearServer.setRepnm(repnm);
				myInvtjYearServer.setQbegin(qbegin);
				myInvtjYearServer.setQend(qend);
				myInvtjYearServer.setFilepath(filepath);
				myInvtjYearServer.setDevnoList(devnoList);

				myInvtjYearServer.start();

			}

			myforward = mapping.findForward("InvtjMDeal");

		} catch (Exception e) {
			log.error("ERROR", e);
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}