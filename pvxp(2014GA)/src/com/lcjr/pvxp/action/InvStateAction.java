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

import com.lcjr.pvxp.actionform.InvStateForm;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.InvStateModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.InvState;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.InvStateResult;
import com.lcjr.pvxp.util.PageBean;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 发票状态查询Action
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
 * @version 1.0 2006/01/13
 */
public class InvStateAction extends Action {

	Logger log = Logger.getLogger(InvStateAction.class.getName());

	public PageBean pb;
	public List invStateLog;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// log.debug("Enter");

		ActionForward myforward = null;
		PubUtil pubUtil = new PubUtil();
		try {
			String mybankid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
			int myPower = new OperatorModel().getPower(16, request);

			// 没有权限报错
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			String page = request.getParameter("page");
			String[] devno = ((InvStateForm) form).getDevno();

			if (page == null) {

				int totalRow = InvStateModel.getInvStateCount(devno);

				PageBean pb = new PageBean(totalRow, 12);
				InvStateResult[] result = getResult(pb, devno);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
				request.setAttribute("devno", devno);

				return mapping.findForward("InvState");

			} else {

				int totalRow = InvStateModel.getInvStateCount(devno);

				PageBean pb = new PageBean(totalRow, 12);
				int currentPage;
				try {
					currentPage = Integer.parseInt(page);
				} catch (Exception e) {
					currentPage = 1;
				}

				pb.setCurrentPage(currentPage);
				InvStateResult[] result = getResult(pb, devno);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
				request.setAttribute("devno", devno);

				return mapping.findForward("InvState");
			}

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
		}

		// log.debug("Exit\n");
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}

	private InvStateResult[] getResult(PageBean pb, String[] devno) {
		try {
			List invStateLog = InvStateModel.getInvState(devno, pb.beginRow, pb.getPageSize());

			InvStateResult result[] = new InvStateResult[invStateLog.size()];

			int i = 0;
			InvState invStateItem;
			Iterator iter = invStateLog.iterator();
			while (iter.hasNext()) {
				invStateItem = (InvState) iter.next();

				InvStateResult invStateResult = new InvStateResult();

				invStateResult.setDevno(invStateItem.getDevno());
				invStateResult.setBank(BankinfoModel.getBankinfoFromList(DevinfoModel.getDevFromList(invStateItem.getDevno()).getBankid()).getBanknm());
				invStateResult.setDate(invStateItem.getPdate());
				invStateResult.setTime(invStateItem.getPtime());
				invStateResult.setFirstnum(invStateItem.getFirstnum());
				invStateResult.setLastnum(invStateItem.getLastnum());
				invStateResult.setCurrentnum(invStateItem.getCurrentnum());
				invStateResult.setWastenum(invStateItem.getWastenum());
				invStateResult.setIfvaluable(invStateItem.getIfvaluable());

				result[i++] = invStateResult;
			}
			return result;
		} catch (Exception e) {
			// log.debug("e=" + e);
			return null;
		}
	}
}
