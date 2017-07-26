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
 * <b>Description:</b> ��Ʊ���ò�ѯAction
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2006
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * <br>
 * 
 * @author
 * @version 1.0 2011/10/12
 */
public class CardDistillAction extends Action {

	Logger log = Logger.getLogger(CardDistillAction.class.getName());
	// ����ҳ����ƺͲ�ѯ����༯
	public PageBean pb;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

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

			// �� form ����ȡ���� ҳ�룬ѡ�е��豸��ţ���ʼ���ڣ���������
			String page = request.getParameter("page");// ȡ��
			String[] termnum = ((CardDistillForm) form).getTermnum();
			String date1 = ((CardDistillForm) form).getDate1();
			String date2 = ((CardDistillForm) form).getDate2();

			// ���ҳ��Ϊ��
			if (page == null) {

				int totalRow = CardDistillModel.getInvoiceDistillCount(termnum, date1, date2);

				// ����ҳ��
				PageBean pb = new PageBean(totalRow, 12);
				// ����ѯ�Ľ��������Ҫ����д�����ʵ����
				CardDistillResult[] result = getResult(pb, termnum, date1, date2);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
				request.setAttribute("devno", termnum);
				request.setAttribute("date1", date1);
				request.setAttribute("date2", date2);

				return mapping.findForward("Success");

				// ���ҳ�벻Ϊ��
			} else {

				int totalRow = CardDistillModel.getInvoiceDistillCount(termnum, date1, date2);
				// ����ҳ��
				PageBean pb = new PageBean(totalRow, 12);
				// ���嵱ǰ������
				int currentPage;
				try {
					currentPage = Integer.parseInt(page);
				} catch (Exception e) {
					currentPage = 1;
				}

				pb.setCurrentPage(currentPage);
				// ����ѯ�Ľ��������Ҫ����д�����ʵ����
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
	 * ��ѯ������ݿ���Ϣ�������ϲ�ѯ���������ݷ�װ�����ʵ������
	 * 
	 * @param pb
	 *            ҳ��������
	 * @param termnum
	 *            ����
	 * @param date1
	 *            ��ʼʱ��
	 * @param date2
	 *            ��ֹʱ��
	 * @return ���ϲ�ѯ�����Ľ����
	 */
	private CardDistillResult[] getResult(PageBean pb, String[] termnum, String date1, String date2) {
		try {
			// ����ѯ�����Ľ����װ���༯�У��ȴ����浽���������
			List cardList = CardDistillModel.getCardDistillList(termnum, date1, date2, pb.beginRow, pb.getPageSize());
			// ���������༯ͬ����С�Ľ������
			CardDistillResult result[] = new CardDistillResult[cardList.size()];

			int i = 0;
			// �������ʵ����
			CardLy cardlyItem;
			// ���༯ת��ΪIterator����
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