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

import com.lcjr.pvxp.actionform.JytjBankMDealForm;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.JytjBankDayServer;
import com.lcjr.pvxp.util.JytjBankMonthServer;
import com.lcjr.pvxp.util.JytjBankYearServer;
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
 * @version 1.0 2006/8/10
 */
public final class JytjBankMDealAction extends Action {

	Logger log = Logger.getLogger(JytjBankMDealAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();

		// 从from中提取数据项
		String[] banklist = (String[]) ((JytjBankMDealForm) form).getBanklist();
		String moneytype = (String) ((JytjBankMDealForm) form).getMoneyType();
		String repnm = (String) ((JytjBankMDealForm) form).getRepnm().trim();
		// 统计类型 日，月，年
		String qseq = (String) ((JytjBankMDealForm) form).getQseq();
		String qbegin = (String) ((JytjBankMDealForm) form).getQbegin();
		String qend = (String) ((JytjBankMDealForm) form).getQend();
		String strcdlist = (String) ((JytjBankMDealForm) form).getStrcdlist();
		String statmode = (String) ((JytjBankMDealForm) form).getStatmode();
		String banktag = (String) ((JytjBankMDealForm) form).getBanktag();

		if (moneytype.equals("")) {
			moneytype = request.getParameter("moneytype");
		}

		PubUtil myPubUtil = new PubUtil();
		CharSet myCharSet = new CharSet();
		ActionForward myforward = mapping.findForward("SystemError");

		try {
			// 取得操作员信息，权限，机构编号，文件地址
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
			// 机构编号为空
			if ((banklist == null) || (banklist.length == 0)) {
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
			List banknoList = new ArrayList();
			List devlist = new ArrayList();

			for (int i = 0; i < banklist.length; i++) {
				banknoList.add(banklist[i]);
			}

			// 获得机构列表
			DevinfoModel myDevinfoModel = new DevinfoModel();
			List myDevList = myDevinfoModel.getDevList();
			if (myDevList != null && !myDevList.isEmpty()) {
				int devnum = myDevList.size();
				String bankno = "";
				String devno = "";

				// 从数据库中找出页面所选机构的所有设备编号
				for (int i = 0; i < devnum; i++) {
					Devinfo tmp = (Devinfo) myDevList.get(i);
					// 设备编号
					devno = myPubUtil.dealNull(tmp.getDevno()).trim();
					// 所属机构
					String tmpstr = myPubUtil.dealNull(tmp.getBankid()).trim();

					for (int j = 0; j < banknoList.size(); j++) {
						bankno = (String) banknoList.get(j);
						if (banktag.equals("1")) {
							if ((tmpstr.substring(0, 2)).equals(bankno.substring(0, 2))) {
								devlist.add(devno);
							}
						} else if (banktag.equals("2")) {
							if ((tmpstr.substring(0, 6)).equals(bankno.substring(0, 6))) {
								devlist.add(devno);
							}
						} else if (banktag.equals("3")) {
							if (tmpstr.equals(bankno)) {
								devlist.add(devno);
							}
						} else {
						}
					}
				}
			}

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

			String HQLstr = "from Trcdtj" + qseq + " where (";
			for (int i = 0; i < devlist.size(); i++) {
				HQLstr += " devno='" + devlist.get(i) + "' or";
			}
			if(devlist.size()>0){
				HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and (";
			}
			
			for (int i = 0; i < trcdlist.size(); i++) {
				HQLstr += " trcdtype='" + trcdlist.get(i) + "' or";
			}
			if(trcdlist.size()>0){
				HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and ";
			}else{
				HQLstr = HQLstr.substring(0, HQLstr.length() - 1);
			}
			

			if (qseq.equals("day")) {
				// 日统计报表

				JytjBankDayServer myJytjBankDayServer = new JytjBankDayServer();

				tmpyear1 = Integer.parseInt(qbegin.substring(0, 4));
				tmpmonth1 = Integer.parseInt(qbegin.substring(4, 6));
				tmpday1 = Integer.parseInt(qbegin.substring(6, 8));
				tmpyear2 = Integer.parseInt(qend.substring(0, 4));
				tmpmonth2 = Integer.parseInt(qend.substring(4, 6));
				tmpday2 = Integer.parseInt(qend.substring(6, 8));

				// 统计的区间总天数
				totaldays = (tmpyear2 - tmpyear1) * 372 + (tmpmonth2 - tmpmonth1) * 31 + (tmpday2 - tmpday1) + 1;

				if ((totaldays % 3) > 0) {
					totaldays = totaldays / 3 + 1;
				} else {
					totaldays = totaldays / 3;
				}

				for (int i = 0; i < totaldays; i++) {
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

					// 将时间的检索条件添加到sql语句中
					tmpHQLstr = HQLstr + " devdate>='" + tmpdate1 + "' and devdate<='" + tmpdate2 + "'";
					if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
						tmpHQLstr += " order by devno, devdate, moneytype";

					HQLstring.add(tmpHQLstr);
					tmpday1 = tmpday2 + 1;

				}

				myJytjBankDayServer.setHQLstr(HQLstring);
				myJytjBankDayServer.setBankid(bankid);
				myJytjBankDayServer.setOperid(operid);
				myJytjBankDayServer.setRepnm(repnm);
				myJytjBankDayServer.setQbegin(qbegin);
				myJytjBankDayServer.setQend(qend);
				myJytjBankDayServer.setMoneyType(moneytype);
				myJytjBankDayServer.setFilepath(filepath);
				myJytjBankDayServer.setTrcdlist(trcdlist);
				myJytjBankDayServer.setStatmode(statmode);
				myJytjBankDayServer.setBanknoList(banknoList);
				myJytjBankDayServer.setBanktag(banktag);

				myJytjBankDayServer.start();

			} else if (qseq.equals("month")) {
				// 月统计报表
				JytjBankMonthServer myJytjBankMonthServer = new JytjBankMonthServer();

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

					if (tmpmonth1 >= 13) {
						tmpmonth1 = 1;
						tmpyear1++;
						tmpdate1 = String.valueOf(tmpyear1) + "01";
					} else if (tmpmonth1 >= 10) {
						tmpdate1 = String.valueOf(tmpyear1) + String.valueOf(tmpmonth1);
					} else {
						tmpdate1 = String.valueOf(tmpyear1) + "0" + String.valueOf(tmpmonth1);
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
						tmpHQLstr += " order by devno, devdate, moneytype";
					HQLstring.add(tmpHQLstr);
					tmpmonth1 = tmpmonth2 + 1;
				}

				myJytjBankMonthServer.setHQLstr(HQLstring);
				myJytjBankMonthServer.setBankid(bankid);
				myJytjBankMonthServer.setOperid(operid);
				myJytjBankMonthServer.setRepnm(repnm);
				myJytjBankMonthServer.setQbegin(qbegin);
				myJytjBankMonthServer.setQend(qend);
				myJytjBankMonthServer.setMoneyType(moneytype);
				myJytjBankMonthServer.setFilepath(filepath);
				myJytjBankMonthServer.setTrcdlist(trcdlist);
				myJytjBankMonthServer.setStatmode(statmode);
				myJytjBankMonthServer.setBanknoList(banknoList);
				myJytjBankMonthServer.setBanktag(banktag);

				myJytjBankMonthServer.start();

			} else if (qseq.equals("year")) {
				// 年统计报表
				JytjBankYearServer myJytjBankYearServer = new JytjBankYearServer();

				tmpyear1 = Integer.parseInt(qbegin);
				tmpyear2 = Integer.parseInt(qend);
				totalyears = tmpyear2 - tmpyear1 + 1;

				if ((totalyears % 3) > 0) {
					totalyears = totalyears / 3 + 1;
				} else {
					totalyears = totalyears / 3;
				}

				for (int i = 0; i < totalyears; i++) {

					tmpdate1 = String.valueOf(tmpyear1);

					tmpyear2 = tmpyear1 + 2;
					tmpdate2 = String.valueOf(tmpyear2);

					if (Integer.parseInt(tmpdate2) > Integer.parseInt(qend)) {
						tmpdate2 = qend;
					}

					tmpHQLstr = HQLstr + " devdate>='" + tmpdate1 + "' and devdate<='" + tmpdate2 + "'";
					if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
						tmpHQLstr += " order by devno, devdate, moneytype";

					HQLstring.add(tmpHQLstr);
					tmpyear1 = tmpyear2 + 1;
				}

				myJytjBankYearServer.setHQLstr(HQLstring);
				myJytjBankYearServer.setBankid(bankid);
				myJytjBankYearServer.setOperid(operid);
				myJytjBankYearServer.setRepnm(repnm);
				myJytjBankYearServer.setQbegin(qbegin);
				myJytjBankYearServer.setQend(qend);
				myJytjBankYearServer.setMoneyType(moneytype);
				myJytjBankYearServer.setFilepath(filepath);
				myJytjBankYearServer.setTrcdlist(trcdlist);
				myJytjBankYearServer.setStatmode(statmode);
				myJytjBankYearServer.setBanknoList(banknoList);
				myJytjBankYearServer.setBanktag(banktag);

				myJytjBankYearServer.start();

			}

			myforward = mapping.findForward("JytjBankMDeal");

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}