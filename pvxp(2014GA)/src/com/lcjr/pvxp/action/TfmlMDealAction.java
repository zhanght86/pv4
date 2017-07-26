package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.actionform.*;
import com.lcjr.pvxp.bean.*;
import com.lcjr.pvxp.orm.*;

import java.util.Locale;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 设备交易失败情况明细表Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2006
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.0 2006/12/18
 */
public final class TfmlMDealAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] devlist = (String[]) ((TfmlMDealForm) form).getDevlist();
		String repnm = (String) ((TfmlMDealForm) form).getRepnm().trim();
		String qseq = (String) ((TfmlMDealForm) form).getQseq();
		String qbegin = (String) ((TfmlMDealForm) form).getQbegin();
		String qend = (String) ((TfmlMDealForm) form).getQend();
		String strcdlist = (String) ((TfmlMDealForm) form).getStrcdlist();
		String statmode = (String) ((TfmlMDealForm) form).getStatmode();

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
			String HQLstr = "";
			String HQLstr1 = "from Trcdlog where (";
			String HQLstr2 = "from Mxb where (";
			String HQLstr3 = "from Mxb_tmp where (";

			for (int i = 0; i < devlist.length; i++) {
				HQLstr += " devno='" + devlist[i] + "' or";
			}
			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and (";
			for (int i = 0; i < trcdlist.size(); i++) {
				HQLstr += " devtrcd='" + trcdlist.get(i) + "' or";
			}
			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and ";
			HQLstr += " devdate>='" + qbegin + "' and devdate<='" + qend + "'";
			if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
				HQLstr += " order by devno, devdate";

			HQLstr1 += HQLstr;
			HQLstr2 += HQLstr;
			HQLstr3 += HQLstr;

			if (qseq.equals("day")) {

				TfmlDayServer myTfmlDayServer = new TfmlDayServer();

				myTfmlDayServer.setHQLstr1(HQLstr1);
				myTfmlDayServer.setHQLstr2(HQLstr2);
				myTfmlDayServer.setHQLstr3(HQLstr3);
				myTfmlDayServer.setBankid(bankid);
				myTfmlDayServer.setOperid(operid);
				myTfmlDayServer.setRepnm(repnm);
				myTfmlDayServer.setQbegin(qbegin);
				myTfmlDayServer.setQend(qend);
				myTfmlDayServer.setFilepath(filepath);
				myTfmlDayServer.setTrcdlist(trcdlist);
				myTfmlDayServer.setStatmode(statmode);
				myTfmlDayServer.setDevnoList(devnoList);

				myTfmlDayServer.start();
			}

			myforward = mapping.findForward("TfmlMDeal");

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}