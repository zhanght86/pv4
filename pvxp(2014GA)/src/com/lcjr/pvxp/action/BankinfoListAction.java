package com.lcjr.pvxp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.BankinfoListForm;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.orm.Bankinfo;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 机构列表Action
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
 * @version 1.0 2005/3/2
 */
public final class BankinfoListAction extends Action {

	Logger log = Logger.getLogger(BankinfoListAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("execute开始");
		String page = (String) ((BankinfoListForm) form).getPage();
		String pagesize = (String) ((BankinfoListForm) form).getPagesize();
		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");

		int intpage = 0;
		int intpagesize = 0;
		int intpagecount = 0;
		String pagecount = "0";
		String ttcount = "0";
		int intstart = 0;
		int intend = 0;

		try {
			intpage = Integer.parseInt(page);
			intpagesize = Integer.parseInt(pagesize);
		} catch (Exception e) {
			log.error("页码问题", e);
			// 页数错误
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.submit.wrong");
			request.removeAttribute(mapping.getAttribute());
			return myforward;
		}
		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			String mybankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request));
			String myPower = "";
			if (authlist.equals("*")) {
				// 超级管理员
				myPower = "*";
			} else if (authlist.length() > 0 && Integer.parseInt(authlist.substring(1, 2)) > 0) {
				// 有浏览以上级别权限
				myPower = authlist.substring(1, 2);
			} else {
				// 没有权限
				myPower = "0";
			}
			// 保存当前操作的权限
			request.setAttribute(Constants.REQUEST_OPER_POWER, myPower);
			// 没有权限报错
			if (myPower.equals("0")) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			Vector<Bankinfo> myVector = new Vector<Bankinfo>();

			BankinfoModel myBankinfoModel = new BankinfoModel();

			Vector<Bankinfo> BankVector = myBankinfoModel.getSubBank(mybankid, 1);
			// List BankList = myBankinfoModel.getBankinfoList();
			List<Bankinfo> mylist = new ArrayList<Bankinfo>();
			// Bankinfo myBankinfo1 = new Bankinfo();
			// Bankinfo myBankinfo2 = new Bankinfo();

			if (authlist.equals("*")) {
				mylist = myBankinfoModel.getBankinfoList();
			} else {
				if (BankVector == null) {
					// mylist.add(myBankinfoModel.getBankinfoFromList(mybankid));
				} else {
					for (int i = 0; i < BankVector.size(); i++) {
						mylist.add(BankVector.get(i));
					}
				}
			}

			if (mylist == null || mylist.size() == 0) {
				// 没有管理员
				ttcount = "0";
				pagecount = "0";
			} else {
				int len = mylist.size();
				intpagecount = ((len - 1) / intpagesize) + 1;
				ttcount = String.valueOf(len);
				pagecount = String.valueOf(intpagecount);

				if (intpage < 1) {
					intpage = 1;
					page = "1";
				} else if (intpage > intpagecount) {
					intpage = intpagecount;
					page = pagecount;
				}

				intstart = intpagesize * (intpage - 1);
				intend = intstart + intpagesize;
				if (intend > len)
					intend = len;

				for (int i = intstart; i < intend; i++) {
					Bankinfo temp = (Bankinfo) mylist.get(i);
					myVector.add(temp);
				}
			}
			request.setAttribute(Constants.REQUEST_BANKINFO_TTCOUNT, ttcount);
			request.setAttribute(Constants.REQUEST_BANKINFO_PAGECOUNT, pagecount);
			request.setAttribute(Constants.REQUEST_BANKINFO_PAGE, page);
			request.setAttribute(Constants.REQUEST_BANKINFO_VECTOR, myVector);
			myforward = mapping.findForward("Bankinfo_List");

		} catch (Exception e) {
			log.error("execute", e);
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
		}
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}