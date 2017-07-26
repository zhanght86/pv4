package com.lcjr.pvxp.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.CardOutForm;
import com.lcjr.pvxp.model.CardOutModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.CardOut;
import com.lcjr.pvxp.util.CardOutResult;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PageBean;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 发卡记录流水查询Action
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
 * @version 1.0 2011/10/10
 */
public class CardOutAction extends Action {

	// 建立页面控制和查询结果类集
	public PageBean pb;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 建立转向和公共方法库
		ActionForward myforward = null;
		PubUtil pubUtil = new PubUtil();

		try {
			// 从cook中提取登录用户所属机构 和 权限查询
			String mybankid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
			int myPower = new OperatorModel().getPower(9, request);

			// 没有权限报错
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			// 从 form 中提取数据 页码，选中的设备编号，起始日期，结束日期
			String page = request.getParameter("page"); // 获取页面
			String[] termnum = ((CardOutForm) form).getTermnum(); // 获取设备编号
			String date1 = pubUtil.dealNull(((CardOutForm) form).getDate1()); // 获取起始日期
			String date2 = pubUtil.dealNull(((CardOutForm) form).getDate2()); // 获取截止日期
			String id = pubUtil.dealNull(((CardOutForm) form).getId()); // 获取身份证号
			String cardno = pubUtil.dealNull(((CardOutForm) form).getCardno()); // 获取卡号
			String time1 = pubUtil.dealNull(((CardOutForm) form).getTime1()); // 获取
																				// 开始时间
			String time2 = pubUtil.dealNull(((CardOutForm) form).getTime2()); // 获取
																				// 截止时间
			// 如果页码为空
			if (page == null) {

				int totalRow = CardOutModel.getCardOutCount(termnum, date1, date2, time1, time2, id, cardno);

				// 设置页码
				PageBean pb = new PageBean(totalRow, 12);
				// 将查询的结果按照需要，填写到结果实体类
				CardOutResult[] result = getResult(pb, termnum, date1, date2, time1, time2, id, cardno);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
				request.setAttribute("devno", termnum);
				request.setAttribute("date1", date1);
				request.setAttribute("date2", date2);

				return mapping.findForward("Success");

				// 如果页码不为空
			} else {

				int totalRow = CardOutModel.getCardOutCount(termnum, date1, date2, time1, time2, id, cardno);

				PageBean pb = new PageBean(totalRow, 12);
				int currentPage;
				try {
					currentPage = Integer.parseInt(page);
				} catch (Exception e) {
					currentPage = 1;
				}

				pb.setCurrentPage(currentPage);
				CardOutResult[] result = getResult(pb, termnum, date1, date2, time1, time2, id, cardno);

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
	 *            开始日期
	 * @param date2
	 *            截止日期
	 * @param cardno
	 *            卡号
	 * @param id
	 *            身份证号
	 * @param time2
	 *            截止时间
	 * @param time1
	 *            开始时间
	 * @return
	 */
	private CardOutResult[] getResult(PageBean pb, String[] termnum, String date1, String date2, String time1, String time2, String id, String cardno) {
		try {

			PubUtil pubUtil = new PubUtil();
			// 将查询出来的结果封装到类集中，等待保存到结果数组中
			List cardList = CardOutModel.getCardOutList(termnum, date1, date2, time1, time2, id, cardno, pb.beginRow, pb.getPageSize());
			// 建立与结果类集同样大小的结果数组
			CardOutResult result[] = new CardOutResult[cardList.size()];

			int i = 0;
			// 建立类的实例化
			CardOut cardoutItem;
			// 将类集转化为Iterator类型
			Iterator iter = cardList.iterator();
			while (iter.hasNext()) {
				cardoutItem = (CardOut) iter.next();

				CardOutResult cardoutResult = new CardOutResult();

				// 卡类型（1：借记卡，2：信用卡，3：其他）
				cardoutResult.setCardtype(cardoutItem.getCardtype());
				// 设备号
				cardoutResult.setDevno(cardoutItem.getDevno());
				// 设备类型编号
				cardoutResult.setTypeno(cardoutItem.getTypeno());
				// 机构号
				// cardoutResult.setOrganno(BankinfoModel.getBankinfoFromList(cardoutItem.getOrganno()).getBanknm().trim());
				cardoutResult.setOrganno(cardoutItem.getOrganno());
				// 身份证号
				cardoutResult.setIdcardno(cardoutItem.getIdcardno());
				// 卡密码
				cardoutResult.setPasswd(cardoutItem.getPasswd());
				// 条码号
				cardoutResult.setStrcode(cardoutItem.getStrcode());
				// 卡号
				cardoutResult.setOutcardno(cardoutItem.getOutcardno());
				// 出卡日期
				cardoutResult.setOutcarddate(cardoutItem.getOutcarddate());
				// 出卡时间
				cardoutResult.setOutcardtime(cardoutItem.getOutcardtime());
				// 出卡状态（1：成功0：失败）
				cardoutResult.setOutcardstatus(cardoutItem.getOutcardstatus());
				// 备用字段
				cardoutResult.setRemark1(pubUtil.dealNull(cardoutItem.getRemark1()));

				cardoutResult.setOrganno(pubUtil.dealNull(cardoutItem.getOrganno()));

				// cardoutResult.setSerialno(pubUtil.dealNull(cardoutItem.getSerialno()));

				// 审核员
				cardoutResult.setRemark2(pubUtil.dealNull(cardoutItem.getRemark2()));

				cardoutResult.setRemark3(pubUtil.dealNull(cardoutItem.getRemark3()));

				result[i++] = cardoutResult;
			}
			return result;
		} catch (Exception e) {
			// log.debug("e=" + e);
			return null;
		}
	}
}