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
 * �ֽ�ģ��Action
 * 
 * @author ������
 * @version pvxp
 * @date 2014-3-18
 */
@Component("/cashStock")
public class CashStockAction extends DispatchAction {

	Logger log = Logger.getLogger("web.com.lcjr.pvxp.action.CashStockAction.java");

	@Resource
	private CashStockDAO cashStockDAO;

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// ����ת��͹���������
		ActionForward myforward = null;

		try {
			// ��cook����ȡ��¼�û��������� �� Ȩ�޲�ѯ
			int myPower = new OperatorModel().getPower(9, request);

			// û��Ȩ�ޱ���
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			// �� form ����ȡ���� ѡ�е��豸��ţ���ʼ���ڣ���������
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
			// ���ҳ�벻Ϊ��

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
							log.error("��ʽ��ʱ�䱨��", e);
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