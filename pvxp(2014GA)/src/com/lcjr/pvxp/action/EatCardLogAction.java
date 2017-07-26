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

import com.lcjr.pvxp.actionform.EatCardLogForm;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.EatCardLogModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.EatCardLog;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.EcLogResult;
import com.lcjr.pvxp.util.PageBean;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 吞卡记录查询Action
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
 * @author 吴学坤
 * @version 1.0 2005/03/20
 */
public class EatCardLogAction extends Action {
	Logger log = Logger.getLogger(EatCardLogAction.class.getName());
	public PageBean pb;

	public List ecLog;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// log.debug("Enter");

		ActionForward myforward = null;
		PubUtil pubUtil = new PubUtil();
		try {
			String mybankid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
			int myPower = new OperatorModel().getPower(10, request);

			// 没有权限报错
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			String page = request.getParameter("page");
			String[] devno = ((EatCardLogForm) form).getDevNo();
			String cardNo = ((EatCardLogForm) form).getCardNo();
			String date1 = ((EatCardLogForm) form).getDate1();
			String date2 = ((EatCardLogForm) form).getDate2();
			String flag = ((EatCardLogForm) form).getFlag();

			if (page == null) {

				int totalRow = EatCardLogModel.getEatCardLogCount(devno, date1, date2, cardNo, flag);

				PageBean pb = new PageBean(totalRow, 12);
				EcLogResult[] result = getResult(pb, devno, date1, date2, cardNo, flag);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
				request.setAttribute("devno", devno);
				request.setAttribute("cardNo", cardNo);
				request.setAttribute("date1", date1);
				request.setAttribute("date2", date2);

				return mapping.findForward("EatCardLog");

			} else {

				int totalRow = EatCardLogModel.getEatCardLogCount(devno, date1, date2, cardNo, flag);

				PageBean pb = new PageBean(totalRow, 12);
				int currentPage;
				try {
					currentPage = Integer.parseInt(page);
				} catch (Exception e) {
					currentPage = 1;
				}

				pb.setCurrentPage(currentPage);
				EcLogResult[] result = getResult(pb, devno, date1, date2, cardNo, flag);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
				request.setAttribute("devno", devno);
				request.setAttribute("cardNo", cardNo);
				request.setAttribute("date1", date1);
				request.setAttribute("date2", date2);
				request.setAttribute("flag", flag);

				return mapping.findForward("EatCardLog");
			}

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
		}

		// log.debug("Exit\n");
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}

	private EcLogResult[] getResult(PageBean pb, String[] devno, String date1, String date2, String cardNo, String flag) {
		try {
			List ecLog = EatCardLogModel.getEatCardLog(devno, date1, date2, cardNo, pb.beginRow, pb.getPageSize(), flag);

			EcLogResult result[] = new EcLogResult[ecLog.size()];

			int i = 0;
			EatCardLog ecLogItem;
			Iterator iter = ecLog.iterator();
			while (iter.hasNext()) {
				ecLogItem = (EatCardLog) iter.next();
				EcLogResult ecLogResult = new EcLogResult();

				ecLogResult.setDevNo(ecLogItem.getDevno());
				ecLogResult.setBank(BankinfoModel.getBankinfoFromList(DevinfoModel.getDevFromList(ecLogItem.getDevno()).getBankid()).getBanknm());
				ecLogResult.setCardNo(ecLogItem.getAccno());
				ecLogResult.setDate(ecLogItem.getEdate());
				ecLogResult.setTime(ecLogItem.getEtime());
				ecLogResult.setMessage(ecLogItem.getMsg());
				ecLogResult.setFlag(ecLogItem.getFlag());

				result[i++] = ecLogResult;
			}
			return result;
		} catch (Exception e) {
			// log.debug("e=" + e);
			return null;
		}
	}
}
