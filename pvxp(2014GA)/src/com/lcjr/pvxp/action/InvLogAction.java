package com.lcjr.pvxp.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.InvLogForm;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.InvLogModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.InvLog;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.InvLogResult;
import com.lcjr.pvxp.util.PageBean;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 发票明细查询Action
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
 * @author
 * @version 1.0 2006/01/14
 */
public class InvLogAction extends Action {

	Logger log = Logger.getLogger(InvLogAction.class.getName());
	
	public PageBean pb;
	public List invLog;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// log.debug("Enter");

		ActionForward myforward = null;
		PubUtil pubUtil = new PubUtil();
		try {
			String mybankid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
			int myPower = new OperatorModel().getPower(17, request);

			// 没有权限报错
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			String page = request.getParameter("page");
			String[] devno = ((InvLogForm) form).getDevno();
			String date1 = ((InvLogForm) form).getDate1();
			;
			String date2 = ((InvLogForm) form).getDate2();
			String invno1 = ((InvLogForm) form).getInvno1();
			String invno2 = ((InvLogForm) form).getInvno2();
			String accno = ((InvLogForm) form).getAccno();

			if (page == null) {

				int totalRow = InvLogModel.getInvLogCount(devno, date1, date2, invno1, invno2, accno);

				PageBean pb = new PageBean(totalRow, 12);
				InvLogResult[] result = getResult(pb, devno, date1, date2, invno1, invno2, accno);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
				request.setAttribute("devno", devno);
				request.setAttribute("date1", date1);
				request.setAttribute("date2", date2);
				request.setAttribute("invno1", invno1);
				request.setAttribute("invno2", invno2);
				request.setAttribute("accno", accno);

				return mapping.findForward("InvLog");

			} else {

				int totalRow = InvLogModel.getInvLogCount(devno, date1, date2, invno1, invno2, accno);

				PageBean pb = new PageBean(totalRow, 12);
				int currentPage;
				try {
					currentPage = Integer.parseInt(page);
				} catch (Exception e) {
					currentPage = 1;
				}

				pb.setCurrentPage(currentPage);
				InvLogResult[] result = getResult(pb, devno, date1, date2, invno1, invno2, accno);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
				request.setAttribute("devno", devno);
				request.setAttribute("date1", date1);
				request.setAttribute("date2", date2);
				request.setAttribute("invno1", invno1);
				request.setAttribute("invno2", invno2);
				request.setAttribute("accno", accno);

				return mapping.findForward("InvLog");
			}

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
		}

		// log.debug("Exit\n");
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}

	private InvLogResult[] getResult(PageBean pb, String[] devno, String date1, String date2, String invno1, String invno2, String accno) {
		try {
			List invLog = InvLogModel.getInvLog(devno, date1, date2, invno1, invno2, accno, pb.beginRow, pb.getPageSize());

			InvLogResult result[] = new InvLogResult[invLog.size()];

			int i = 0;
			InvLog invItem;
			Iterator iter = invLog.iterator();
			while (iter.hasNext()) {
				invItem = (InvLog) iter.next();

				InvLogResult invLogResult = new InvLogResult();

				invLogResult.setDevno(invItem.getDevno());
				invLogResult.setBank(BankinfoModel.getBankinfoFromList(DevinfoModel.getDevFromList(invItem.getDevno()).getBankid()).getBanknm());
				invLogResult.setDate(invItem.getPdate());
				invLogResult.setTime(invItem.getPtime());
				invLogResult.setInvoiceno(invItem.getInvoiceno());
				invLogResult.setType(invItem.getType());
				invLogResult.setAccno1(invItem.getAccno1());
				invLogResult.setMoney(invItem.getMoney());
				invLogResult.setPrintresult(invItem.getPrintresult());
				invLogResult.setPrinttoken(invItem.getPrinttoken());

				result[i++] = invLogResult;
			}
			return result;
		} catch (Exception e) {
			// log.debug("e=" + e);
			return null;
		}
	}
}
