package com.lcjr.pvxp.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.CardStatusForm;
import com.lcjr.pvxp.model.CardStatusModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.CardStatus;
import com.lcjr.pvxp.util.CardStatusResult;
import com.lcjr.pvxp.util.Constants;
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
 * @version 1.0 2011/10/09
 */
public class CardStatusAction extends Action {
	// 建立页面控制和查询结果类集
	public PageBean pb;
	public List cardLog;

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
			String page = request.getParameter("page");
			String[] termnum = ((CardStatusForm) form).getTermnum();

			// 如果页码为空
			if (page == null) {

				int totalRow = CardStatusModel.getCardStatusCount(termnum);

				// 设置页码
				PageBean pb = new PageBean(totalRow, 12);
				// 将查询的结果按照需要，填写到结果实体类
				CardStatusResult[] result = getResult(pb, termnum);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
				request.setAttribute("devno", termnum);

				return mapping.findForward("Success");

				// 如果页码不为空
			} else {

				int totalRow = CardStatusModel.getCardStatusCount(termnum);

				PageBean pb = new PageBean(totalRow, 12);
				int currentPage;
				try {
					currentPage = Integer.parseInt(page);
				} catch (Exception e) {
					currentPage = 1;
				}

				pb.setCurrentPage(currentPage);
				CardStatusResult[] result = getResult(pb, termnum);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
				request.setAttribute("devno", termnum);

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
	 * @return
	 */
	private CardStatusResult[] getResult(PageBean pb, String[] termnum) {
		try {
			// 将查询出来的结果封装到类集中，等待保存到结果数组中
			List cardList = CardStatusModel.getCardStatusList(termnum, pb.beginRow, pb.getPageSize());

			// 建立与结果类集同样大小的结果数组
			CardStatusResult result[] = new CardStatusResult[cardList.size()];

			int i = 0;
			// 建立类的实例化
			CardStatus cardStatusItem;
			// 将类集转化为Iterator类型
			Iterator iter = cardList.iterator();
			while (iter.hasNext()) {
				cardStatusItem = (CardStatus) iter.next();

				CardStatusResult invdisResult = new CardStatusResult();
				// 卡类型
				invdisResult.setCardtype(cardStatusItem.getCardtype());
				// 设备号
				invdisResult.setDevno(cardStatusItem.getDevno());
				// 设备类型编号
				invdisResult.setTypeno(cardStatusItem.getTypeno());
				// 机构号
				invdisResult.setOrganno(cardStatusItem.getOrganno());
				// 总领用张数
				invdisResult.setNumlytotal(cardStatusItem.getNumlytotal());
				// 总发卡张数
				invdisResult.setNumouttotal(cardStatusItem.getNumouttotal());
				// 当日领用张数
				invdisResult.setNumly(cardStatusItem.getNumly());
				// 当日发卡张数
				invdisResult.setNumout(cardStatusItem.getNumout());
				// 现在剩余张数
				invdisResult.setNumrest(cardStatusItem.getNumrest());
				// 备用字段1
				// 备用字段2

				// invdisResult.setCardtype(cardlyItem.getCardtype());
				// invdisResult.setOrganno(BankinfoModel.getBankinfoFromList(DevinfoModel.getDevFromList(cardlyItem.getDevno()).getBankid()).getBanknm());
				// invdisResult.setLydate(cardlyItem.getLydate());
				// invdisResult.setLytime(cardlyItem.getLytime());
				// invdisResult.setDevno(cardlyItem.getDevno());
				// invdisResult.setFirstnum(cardlyItem.getFirstnum());
				// invdisResult.setLastnum(cardlyItem.getLastnum());
				// invdisResult.setLystatus(cardlyItem.getLystatus());
				// invdisResult.setLynums(cardlyItem.getLynums());
				// invdisResult.setTypeno(cardlyItem.getTypeno());

				result[i++] = invdisResult;
			}
			return result;
		} catch (Exception e) {
			// log.debug("e=" + e);
			System.out.println("出错了..");
			return null;
		}
	}
}