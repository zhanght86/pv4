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

import com.lcjr.pvxp.actionform.InvoiceDistillForm;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.InvoiceDistillModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.InvoiceDistill;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.InvoiceDistillResult;
import com.lcjr.pvxp.util.PageBean;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 发票领用查询Action
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
 * @version 1.0 2006/01/09
 */
public class InvoiceDistillAction extends Action {

	Logger log = Logger.getLogger(InvoiceDistillAction.class.getName());
	
	public PageBean pb;
	public List invdisLog;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// log.debug("Enter");

		ActionForward myforward = null;
		PubUtil pubUtil = new PubUtil();
		try {
			String mybankid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
			int myPower = new OperatorModel().getPower(9, request);

			// 没有权限报错
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			String page = request.getParameter("page");
			String[] termnum = ((InvoiceDistillForm) form).getTermnum();
			String date1 = ((InvoiceDistillForm) form).getDate1();
			;
			String date2 = ((InvoiceDistillForm) form).getDate2();

			if (page == null) {

				int totalRow = InvoiceDistillModel.getInvoiceDistillCount(termnum, date1, date2);

				PageBean pb = new PageBean(totalRow, 12);
				InvoiceDistillResult[] result = getResult(pb, termnum, date1, date2);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
				request.setAttribute("devno", termnum);
				request.setAttribute("date1", date1);
				request.setAttribute("date2", date2);

				return mapping.findForward("InvoiceDistill");

			} else {

				int totalRow = InvoiceDistillModel.getInvoiceDistillCount(termnum, date1, date2);

				PageBean pb = new PageBean(totalRow, 12);
				int currentPage;
				try {
					currentPage = Integer.parseInt(page);
				} catch (Exception e) {
					currentPage = 1;
				}

				pb.setCurrentPage(currentPage);
				InvoiceDistillResult[] result = getResult(pb, termnum, date1, date2);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
				request.setAttribute("devno", termnum);
				request.setAttribute("date1", date1);
				request.setAttribute("date2", date2);

				return mapping.findForward("InvoiceDistill");
			}

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
		}

		// log.debug("Exit\n");
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}

	private InvoiceDistillResult[] getResult(PageBean pb, String[] termnum, String date1, String date2) {
		try {
			List invdisLog = InvoiceDistillModel.getInvoiceDistill(termnum, date1, date2, pb.beginRow, pb.getPageSize());

			InvoiceDistillResult result[] = new InvoiceDistillResult[invdisLog.size()];

			int i = 0;
			InvoiceDistill invdisItem;
			Iterator iter = invdisLog.iterator();
			while (iter.hasNext()) {
				invdisItem = (InvoiceDistill) iter.next();

				InvoiceDistillResult invdisResult = new InvoiceDistillResult();

				invdisResult.setTermnum(invdisItem.getTermnum());
				invdisResult.setBank(BankinfoModel.getBankinfoFromList(DevinfoModel.getDevFromList(invdisItem.getTermnum()).getBankid()).getBanknm());
				invdisResult.setDate(invdisItem.getPdate());
				invdisResult.setTime(invdisItem.getPtime());
				invdisResult.setFirstnum(invdisItem.getFirstnum());
				invdisResult.setLastnum(invdisItem.getLastnum());
				invdisResult.setApplytoken(invdisItem.getApplytoken());
				invdisResult.setAppynum(invdisItem.getAppynum());

				result[i++] = invdisResult;
			}
			return result;
		} catch (Exception e) {
			// log.debug("e=" + e);
			return null;
		}
	}
}
