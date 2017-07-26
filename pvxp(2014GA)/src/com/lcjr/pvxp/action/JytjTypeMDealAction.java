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

import com.lcjr.pvxp.actionform.JytjTypeMDealForm;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.JytjTypeDayServer;
import com.lcjr.pvxp.util.JytjTypeMonthServer;
import com.lcjr.pvxp.util.JytjTypeYearServer;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 设备类型交易统计Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2007
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.0 2007/03/05
 */
public final class JytjTypeMDealAction extends Action {

	Logger log = Logger.getLogger(JytjTypeMDealAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] typenolist = (String[]) ((JytjTypeMDealForm) form).getTypelist();
		String[] qunit = (String[]) ((JytjTypeMDealForm) form).getQunit();
		String repnm = (String) ((JytjTypeMDealForm) form).getRepnm().trim();
		String qseq = (String) ((JytjTypeMDealForm) form).getQseq();
		String qbegin = (String) ((JytjTypeMDealForm) form).getQbegin();
		String qend = (String) ((JytjTypeMDealForm) form).getQend();
		String strcdlist = (String) ((JytjTypeMDealForm) form).getStrcdlist();
		String statmode = (String) ((JytjTypeMDealForm) form).getStatmode();

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

			if ((typenolist == null) || (typenolist.length == 0)) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devtype.no.selected");
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

			// 处理设备类型编号
			List typelist = new ArrayList();
			for (int i = 0; i < typenolist.length; i++) {
				typelist.add(typenolist[i]);
			}

			// 处理设备编号
			List devlist = new ArrayList();
			DevinfoModel myDevinfoModel = new DevinfoModel();
			List myDevList = myDevinfoModel.getDevList();
			if (myDevList != null && !myDevList.isEmpty()) {
				int devnum = myDevList.size();
				String devno = "";
				for (int i = 0; i < devnum; i++) {
					Devinfo tmp = (Devinfo) myDevList.get(i);
					devno = myPubUtil.dealNull(tmp.getDevno()).trim();
					String tmpstr = myPubUtil.dealNull(tmp.getTypeno()).trim();
					for (int j = 0; j < typenolist.length; j++) {
						if (tmpstr.equals(typenolist[j])) {
							devlist.add(devno);
						}
					}
				}
			}

			// 生成HQL
			String HQLstr = "from Trcdtj" + qseq + " where (";
			for (int i = 0; i < devlist.size(); i++) {
				HQLstr += " devno='" + devlist.get(i) + "' or";
			}
			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and (";
			for (int i = 0; i < trcdlist.size(); i++) {
				HQLstr += " trcdtype='" + trcdlist.get(i) + "' or";
			}
			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and ";
			HQLstr += " devdate>='" + qbegin + "' and devdate<='" + qend + "'";
			if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
				HQLstr += " order by devno, devdate";

			if (qseq.equals("day")) {
				// 日统计报表

				JytjTypeDayServer myJytjTypeDayServer = new JytjTypeDayServer();

				myJytjTypeDayServer.setHQLstr(HQLstr);
				myJytjTypeDayServer.setBankid(bankid);
				myJytjTypeDayServer.setOperid(operid);
				myJytjTypeDayServer.setRepnm(repnm);
				myJytjTypeDayServer.setQbegin(qbegin);
				myJytjTypeDayServer.setQend(qend);
				myJytjTypeDayServer.setUflag(uflag);
				myJytjTypeDayServer.setFilepath(filepath);
				myJytjTypeDayServer.setTrcdlist(trcdlist);
				myJytjTypeDayServer.setStatmode(statmode);
				myJytjTypeDayServer.setTypelist(typelist);

				myJytjTypeDayServer.start();
			} else if (qseq.equals("month")) {
				// 月统计报表

				JytjTypeMonthServer myJytjTypeMonthServer = new JytjTypeMonthServer();

				myJytjTypeMonthServer.setHQLstr(HQLstr);
				myJytjTypeMonthServer.setBankid(bankid);
				myJytjTypeMonthServer.setOperid(operid);
				myJytjTypeMonthServer.setRepnm(repnm);
				myJytjTypeMonthServer.setQbegin(qbegin);
				myJytjTypeMonthServer.setQend(qend);
				myJytjTypeMonthServer.setUflag(uflag);
				myJytjTypeMonthServer.setFilepath(filepath);
				myJytjTypeMonthServer.setTrcdlist(trcdlist);
				myJytjTypeMonthServer.setStatmode(statmode);
				myJytjTypeMonthServer.setTypelist(typelist);

				myJytjTypeMonthServer.start();
			} else if (qseq.equals("year")) {
				// 年统计报表

				JytjTypeYearServer myJytjTypeYearServer = new JytjTypeYearServer();

				myJytjTypeYearServer.setHQLstr(HQLstr);
				myJytjTypeYearServer.setBankid(bankid);
				myJytjTypeYearServer.setOperid(operid);
				myJytjTypeYearServer.setRepnm(repnm);
				myJytjTypeYearServer.setQbegin(qbegin);
				myJytjTypeYearServer.setQend(qend);
				myJytjTypeYearServer.setUflag(uflag);
				myJytjTypeYearServer.setFilepath(filepath);
				myJytjTypeYearServer.setTrcdlist(trcdlist);
				myJytjTypeYearServer.setStatmode(statmode);
				myJytjTypeYearServer.setTypelist(typelist);

				myJytjTypeYearServer.start();
			}

			myforward = mapping.findForward("JytjTypeMDeal");

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}