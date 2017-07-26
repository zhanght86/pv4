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

import com.lcjr.pvxp.actionform.JytjFeesMDealForm;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.JytjFeesDayServer;
import com.lcjr.pvxp.util.JytjFeesMonthServer;
import com.lcjr.pvxp.util.JytjFeesYearServer;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 交易手续费统计Action
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
 * @version 1.0 2007/12/10
 */
public final class JytjFeesMDealAction extends Action {

	Logger log = Logger.getLogger(JytjFeesMDealAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] devlist = (String[]) ((JytjFeesMDealForm) form).getDevlist();
		// String moneytype = (String)((JytjFeesMDealForm) form).getMoneyType();
		String repnm = (String) ((JytjFeesMDealForm) form).getRepnm().trim();
		String qseq = (String) ((JytjFeesMDealForm) form).getQseq();
		String qbegin = (String) ((JytjFeesMDealForm) form).getQbegin();
		String qend = (String) ((JytjFeesMDealForm) form).getQend();
		String strcdlist = (String) ((JytjFeesMDealForm) form).getStrcdlist();
		String statmode = (String) ((JytjFeesMDealForm) form).getStatmode();

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
			int tmpyear1 = 0;
			int tmpmonth1 = 0;
			int tmpday1 = 0;
			int tmpyear2 = 0;
			int tmpmonth2 = 0;
			int tmpday2 = 0;
			int totaldays = 0;
			int totalmonths = 0;
			int totalyears = 0;
			String tmpdate1 = "";
			String tmpdate2 = "";
			String tmpHQLstr = "";
			List HQLstring = new ArrayList();

			if (qseq.equals("day")) {
				// 日统计报表
				JytjFeesDayServer myJytjFeesDayServer = new JytjFeesDayServer();

				tmpyear1 = Integer.parseInt(qbegin.substring(0, 4));
				tmpmonth1 = Integer.parseInt(qbegin.substring(4, 6));
				tmpday1 = Integer.parseInt(qbegin.substring(6, 8));
				tmpyear2 = Integer.parseInt(qend.substring(0, 4));
				tmpmonth2 = Integer.parseInt(qend.substring(4, 6));
				tmpday2 = Integer.parseInt(qend.substring(6, 8));
				totaldays = (tmpyear2 - tmpyear1) * 372 + (tmpmonth2 - tmpmonth1) * 31 + (tmpday2 - tmpday1) + 1;

				if ((totaldays % 3) > 0) {
					totaldays = totaldays / 3 + 1;
				} else {
					totaldays = totaldays / 3;
				}

				for (int i = 0; i < totaldays; i++) {
					tmpdate1 = "";
					tmpdate2 = "";
					if (tmpday1 >= 32) {
						tmpday1 = 1;
						tmpmonth1++;
						if (tmpmonth1 == 13) {
							tmpmonth1 = 1;
							tmpyear1++;
							tmpdate1 = String.valueOf(tmpyear1) + "0101";
						} else {
							if (tmpmonth1 >= 10) {
								tmpdate1 = String.valueOf(tmpyear1) + String.valueOf(tmpmonth1) + "01";
							} else {
								tmpdate1 = String.valueOf(tmpyear1) + "0" + String.valueOf(tmpmonth1) + "01";
							}
						}
					} else {
						if (tmpmonth1 >= 10) {
							if (tmpday1 >= 10) {
								tmpdate1 = String.valueOf(tmpyear1) + String.valueOf(tmpmonth1) + String.valueOf(tmpday1);
							} else {
								tmpdate1 = String.valueOf(tmpyear1) + String.valueOf(tmpmonth1) + "0" + String.valueOf(tmpday1);
							}
						} else {
							if (tmpday1 >= 10) {
								tmpdate1 = String.valueOf(tmpyear1) + "0" + String.valueOf(tmpmonth1) + String.valueOf(tmpday1);
							} else {
								tmpdate1 = String.valueOf(tmpyear1) + "0" + String.valueOf(tmpmonth1) + "0" + String.valueOf(tmpday1);
							}
						}
					}

					tmpday2 = tmpday1 + 2;
					if (tmpday2 >= 32) {
						tmpday2 -= 31;
						tmpmonth1++;
						if (tmpmonth1 == 13) {
							tmpmonth1 = 1;
							tmpyear1++;
							if (tmpday2 >= 10) {
								tmpdate2 = String.valueOf(tmpyear1) + "01" + String.valueOf(tmpday2);
							} else {
								tmpdate2 = String.valueOf(tmpyear1) + "01" + "0" + String.valueOf(tmpday2);
							}
						} else {
							if (tmpmonth1 >= 10) {
								if (tmpday2 >= 10) {
									tmpdate2 = String.valueOf(tmpyear1) + String.valueOf(tmpmonth1) + String.valueOf(tmpday2);
								} else {
									tmpdate2 = String.valueOf(tmpyear1) + String.valueOf(tmpmonth1) + "0" + String.valueOf(tmpday2);
								}
							} else {
								if (tmpday2 >= 10) {
									tmpdate2 = String.valueOf(tmpyear1) + "0" + String.valueOf(tmpmonth1) + String.valueOf(tmpday2);
								} else {
									tmpdate2 = String.valueOf(tmpyear1) + "0" + String.valueOf(tmpmonth1) + "0" + String.valueOf(tmpday2);
								}
							}
						}
					} else {
						if (tmpmonth1 >= 10) {
							if (tmpday1 >= 10) {
								tmpdate2 = String.valueOf(tmpyear1) + String.valueOf(tmpmonth1) + String.valueOf(tmpday2);
							} else {
								tmpdate2 = String.valueOf(tmpyear1) + String.valueOf(tmpmonth1) + "0" + String.valueOf(tmpday2);
							}
						} else {
							if (tmpday2 >= 10) {
								tmpdate2 = String.valueOf(tmpyear1) + "0" + String.valueOf(tmpmonth1) + String.valueOf(tmpday2);
							} else {
								tmpdate2 = String.valueOf(tmpyear1) + "0" + String.valueOf(tmpmonth1) + "0" + String.valueOf(tmpday2);
							}
						}
					}

					if (Integer.parseInt(tmpdate2) > Integer.parseInt(qend)) {
						tmpdate2 = qend;
					}

					tmpHQLstr = HQLstr + " devdate>='" + tmpdate1 + "' and devdate<='" + tmpdate2 + "'";
					if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
						tmpHQLstr += " order by devno, devdate";

					HQLstring.add(tmpHQLstr);
					tmpday1 = tmpday2 + 1;

				}

				myJytjFeesDayServer.setHQLstr(HQLstring);
				myJytjFeesDayServer.setBankid(bankid);
				myJytjFeesDayServer.setOperid(operid);
				myJytjFeesDayServer.setRepnm(repnm);
				myJytjFeesDayServer.setQbegin(qbegin);
				myJytjFeesDayServer.setQend(qend);
				myJytjFeesDayServer.setFilepath(filepath);
				myJytjFeesDayServer.setTrcdlist(trcdlist);
				myJytjFeesDayServer.setStatmode(statmode);
				myJytjFeesDayServer.setDevnoList(devnoList);

				myJytjFeesDayServer.start();
			} else if (qseq.equals("month")) {
				// 月统计报表
				JytjFeesMonthServer myJytjFeesMonthServer = new JytjFeesMonthServer();

				tmpyear1 = Integer.parseInt(qbegin.substring(0, 4));
				tmpmonth1 = Integer.parseInt(qbegin.substring(4, 6));
				tmpyear2 = Integer.parseInt(qend.substring(0, 4));
				tmpmonth2 = Integer.parseInt(qend.substring(4, 6));
				totalmonths = (tmpyear2 - tmpyear1) * 12 + tmpmonth2 - tmpmonth1 + 1;

				if ((totalmonths % 3) > 0) {
					totalmonths = totalmonths / 3 + 1;
				} else {
					totalmonths = totalmonths / 3;
				}

				for (int i = 0; i < totalmonths; i++) {
					tmpdate1 = "";
					tmpdate2 = "";

					if (tmpmonth1 >= 13) {
						tmpmonth1 = 1;
						tmpyear1++;
						tmpdate1 = String.valueOf(tmpyear1) + "01";
					} else {
						if (tmpmonth1 >= 10) {
							tmpdate1 = String.valueOf(tmpyear1) + String.valueOf(tmpmonth1);
						} else {
							tmpdate1 = String.valueOf(tmpyear1) + "0" + String.valueOf(tmpmonth1);
						}
					}

					tmpmonth2 = tmpmonth1 + 2;
					if (tmpmonth2 >= 13) {
						tmpmonth2 -= 12;
						tmpyear1++;
						if (tmpmonth2 >= 10) {
							tmpdate2 = String.valueOf(tmpyear1) + String.valueOf(tmpmonth2);
						} else {
							tmpdate2 = String.valueOf(tmpyear1) + "0" + String.valueOf(tmpmonth2);
						}
					} else {
						if (tmpmonth2 >= 10) {
							tmpdate2 = String.valueOf(tmpyear1) + String.valueOf(tmpmonth2);
						} else {
							tmpdate2 = String.valueOf(tmpyear1) + "0" + String.valueOf(tmpmonth2);
						}
					}

					if (Integer.parseInt(tmpdate2) > Integer.parseInt(qend)) {
						tmpdate2 = qend;
					}

					tmpHQLstr = HQLstr + " devdate>='" + tmpdate1 + "' and devdate<='" + tmpdate2 + "'";
					if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
						tmpHQLstr += " order by devno, devdate";

					HQLstring.add(tmpHQLstr);
					tmpmonth1 = tmpmonth2 + 1;

				}

				myJytjFeesMonthServer.setHQLstr(HQLstring);
				myJytjFeesMonthServer.setBankid(bankid);
				myJytjFeesMonthServer.setOperid(operid);
				myJytjFeesMonthServer.setRepnm(repnm);
				myJytjFeesMonthServer.setQbegin(qbegin);
				myJytjFeesMonthServer.setQend(qend);
				myJytjFeesMonthServer.setFilepath(filepath);
				myJytjFeesMonthServer.setTrcdlist(trcdlist);
				myJytjFeesMonthServer.setStatmode(statmode);
				myJytjFeesMonthServer.setDevnoList(devnoList);

				myJytjFeesMonthServer.start();

			} else if (qseq.equals("year")) {
				// 年统计报表
				JytjFeesYearServer myJytjFeesYearServer = new JytjFeesYearServer();

				tmpyear1 = Integer.parseInt(qbegin);
				tmpyear2 = Integer.parseInt(qend);
				totalyears = tmpyear2 - tmpyear1 + 1;

				if ((totalyears % 3) > 0) {
					totalyears = totalyears / 3 + 1;
				} else {
					totalyears = totalyears / 3;
				}

				for (int i = 0; i < totalyears; i++) {
					tmpdate1 = "";
					tmpdate2 = "";

					tmpdate1 = String.valueOf(tmpyear1);

					tmpyear2 = tmpyear1 + 2;
					tmpdate2 = String.valueOf(tmpyear2);

					if (Integer.parseInt(tmpdate2) > Integer.parseInt(qend)) {
						tmpdate2 = qend;
					}

					tmpHQLstr = HQLstr + " devdate>='" + tmpdate1 + "' and devdate<='" + tmpdate2 + "'";
					if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
						tmpHQLstr += " order by devno, devdate";

					HQLstring.add(tmpHQLstr);
					tmpyear1 = tmpyear2 + 1;
				}

				myJytjFeesYearServer.setHQLstr(HQLstring);
				myJytjFeesYearServer.setBankid(bankid);
				myJytjFeesYearServer.setOperid(operid);
				myJytjFeesYearServer.setRepnm(repnm);
				myJytjFeesYearServer.setQbegin(qbegin);
				myJytjFeesYearServer.setQend(qend);
				myJytjFeesYearServer.setFilepath(filepath);
				myJytjFeesYearServer.setTrcdlist(trcdlist);
				myJytjFeesYearServer.setStatmode(statmode);
				myJytjFeesYearServer.setDevnoList(devnoList);

				myJytjFeesYearServer.start();
			}

			myforward = mapping.findForward("JytjFeesMDeal");

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}