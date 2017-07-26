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

import com.lcjr.pvxp.actionform.CardDistillForm;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.CardDistillModel;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.CardLy;
import com.lcjr.pvxp.util.CardDistillResult;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PageBean;

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
 * @version 1.0 2011/10/12
 */
public class CardDistillAction extends Action {

	Logger log = Logger.getLogger(CardDistillAction.class.getName());
	// 建立页面控制和查询结果类集
	public PageBean pb;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 建立转向和公共方法库
		ActionForward myforward = null;

		try {
			// 从cook中提取登录用户所属机构 和 权限查询
			int myPower = new OperatorModel().getPower(9, request);

			// 没有权限报错
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			// 从 form 中提取数据 页码，选中的设备编号，起始日期，结束日期
			String page = request.getParameter("page");// 取得
			String[] termnum = ((CardDistillForm) form).getTermnum();
			String date1 = ((CardDistillForm) form).getDate1();
			String date2 = ((CardDistillForm) form).getDate2();

			// 如果页码为空
			if (page == null) {

				int totalRow = CardDistillModel.getInvoiceDistillCount(termnum, date1, date2);

				// 设置页码
				PageBean pb = new PageBean(totalRow, 12);
				// 将查询的结果按照需要，填写到结果实体类
				CardDistillResult[] result = getResult(pb, termnum, date1, date2);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
				request.setAttribute("devno", termnum);
				request.setAttribute("date1", date1);
				request.setAttribute("date2", date2);

				return mapping.findForward("Success");

				// 如果页码不为空
			} else {

				int totalRow = CardDistillModel.getInvoiceDistillCount(termnum, date1, date2);
				// 设置页码
				PageBean pb = new PageBean(totalRow, 12);
				// 定义当前的日期
				int currentPage;
				try {
					currentPage = Integer.parseInt(page);
				} catch (Exception e) {
					currentPage = 1;
				}

				pb.setCurrentPage(currentPage);
				// 将查询的结果按照需要，填写到结果实体类
				CardDistillResult[] result = getResult(pb, termnum, date1, date2);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
				request.setAttribute("devno", termnum);
				request.setAttribute("date1", date1);
				request.setAttribute("date2", date2);

				return mapping.findForward("Success");
			}
		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
		}
		// log.debug("Exit\n");
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}

	/**
	 * 查询相关数据库信息，将符合查询条件的数据封装到结果实体类中
	 * 
	 * @param pb
	 *            页面号码控制
	 * @param termnum
	 *            数量
	 * @param date1
	 *            起始时间
	 * @param date2
	 *            截止时间
	 * @return 符合查询条件的结果集
	 */
	private CardDistillResult[] getResult(PageBean pb, String[] termnum, String date1, String date2) {
		try {
			// 将查询出来的结果封装到类集中，等待保存到结果数组中
			List cardList = CardDistillModel.getCardDistillList(termnum, date1, date2, pb.beginRow, pb.getPageSize());
			// 建立与结果类集同样大小的结果数组
			CardDistillResult result[] = new CardDistillResult[cardList.size()];

			int i = 0;
			// 建立类的实例化
			CardLy cardlyItem;
			// 将类集转化为Iterator类型
			Iterator iter = cardList.iterator();
			while (iter.hasNext()) {
				cardlyItem = (CardLy) iter.next();

				CardDistillResult invdisResult = new CardDistillResult();

				invdisResult.setCardtype(cardlyItem.getCardtype());
				invdisResult.setOrganno(BankinfoModel.getBankinfoFromList(DevinfoModel.getDevFromList(cardlyItem.getDevno()).getBankid()).getBanknm());
				invdisResult.setLydate(cardlyItem.getLydate());
				invdisResult.setLytime(cardlyItem.getLytime());
				invdisResult.setDevno(cardlyItem.getDevno());
				// invdisResult.setFirstnum(cardlyItem.getFirstnum());
				// invdisResult.setLastnum(cardlyItem.getLastnum());
				invdisResult.setLystatus(cardlyItem.getLystatus());
				invdisResult.setLynums(cardlyItem.getLynums());
				invdisResult.setTypeno(cardlyItem.getTypeno());
				result[i++] = invdisResult;
			}
			return result;
		} catch (Exception e) {
			// log.debug("e=" + e);
			return null;
		}
	}
}