package com.lcjr.pvxp.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.stereotype.Component;

import com.lcjr.pvxp.actionform.CashStockForm;
import com.lcjr.pvxp.bean.interfac.CashStockDAO;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.CashStock;
import com.lcjr.pvxp.util.Constants;

/**
 * 现金模块Action
 * 
 * @author 武坤鹏
 * @version pvxp
 * @date 2014-3-18
 */
@Component("/cashStock")
public class CashStockAction extends DispatchAction {

	Logger log = Logger.getLogger("web.com.lcjr.pvxp.action.CashStockAction.java");

	@Resource
	private CashStockDAO cashStockDAO;

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

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

			// 从 form 中提取数据 选中的设备编号，起始日期，结束日期
			String bTime = ((CashStockForm) form).getTrcddate1().trim();
			String eTime = ((CashStockForm) form).getTrcddate2().trim();
			String[] termnum = ((CashStockForm) form).getTermnum();
			String sql = "CashStock order by devno";

			// CashStockModel myCashStockService = (CashStockModel)
			// ApplicationContextProvider.getBean("cashStockModelImpl");
			List<CashStock> myLis = cashStockDAO.list("CashStock");

			System.err.println("size=" + myLis.size());
			// Iterator iterator=null;

			Vector<CashStock> vec = null;
			vec = cutDown(myLis, termnum, bTime, eTime);
			request.setAttribute("Vec", vec);
			return mapping.findForward("Success");
			// 如果页码不为空

		} catch (Exception e) {
			System.err.println(e);
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
		}
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}

	/**
	 * 
	 * @param devno
	 * @param bDate
	 * @param eDate
	 * @return
	 * @throws HibernateException
	 */
	public Vector<CashStock> cutDown(List<CashStock> myList, String[] devno, String bDate, String eDate) throws HibernateException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
		log.info("bDate=" + bDate);
		log.info("eDate=" + eDate);
		Date bdate = null;
		Date edate = null;
		Date cdate = null;
		Vector<CashStock> vec = new Vector();
		for (CashStock cs : myList) {
			for (int i = 0; i < devno.length; i++) {
				if (cs.getDevno().trim().equals(devno[i].trim())) {
					if ((cs.getTrcddate() == null || cs.getTrcddate().trim().equals("")) || (bDate.equals("") || eDate.equals(""))) {
						vec.add(cs);
						break;
					} else {
						try {
							cdate = sdf.parse(cs.getTrcddate().trim());
							bdate = sdf.parse(bDate);
							edate = sdf.parse(eDate);
						} catch (ParseException e) {
							log.error("格式化时间报错：", e);
							break;
						}
						if (cdate.before(bdate) || cdate.after(edate)) {
							break;
						} else
							vec.add(cs);
					}
				} else {
					continue;
				}
			}
		}
		return vec;
	}
}