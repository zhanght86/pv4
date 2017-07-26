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
 * @version 1.0 2011/10/09
 */
public class CardStatusAction extends Action {
	// ����ҳ����ƺͲ�ѯ����༯
	public PageBean pb;
	public List cardLog;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// ����ת��͹���������
		ActionForward myforward = null;
		PubUtil pubUtil = new PubUtil();

		try {
			// ��cook����ȡ��¼�û��������� �� Ȩ�޲�ѯ
			String mybankid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
			int myPower = new OperatorModel().getPower(9, request);

			// û��Ȩ�ޱ���
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			// �� form ����ȡ���� ҳ�룬ѡ�е��豸��ţ���ʼ���ڣ���������
			String page = request.getParameter("page");
			String[] termnum = ((CardStatusForm) form).getTermnum();

			// ���ҳ��Ϊ��
			if (page == null) {

				int totalRow = CardStatusModel.getCardStatusCount(termnum);

				// ����ҳ��
				PageBean pb = new PageBean(totalRow, 12);
				// ����ѯ�Ľ��������Ҫ����д�����ʵ����
				CardStatusResult[] result = getResult(pb, termnum);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
				request.setAttribute("devno", termnum);

				return mapping.findForward("Success");

				// ���ҳ�벻Ϊ��
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
	 * @return
	 */
	private CardStatusResult[] getResult(PageBean pb, String[] termnum) {
		try {
			// ����ѯ�����Ľ����װ���༯�У��ȴ����浽���������
			List cardList = CardStatusModel.getCardStatusList(termnum, pb.beginRow, pb.getPageSize());

			// ���������༯ͬ����С�Ľ������
			CardStatusResult result[] = new CardStatusResult[cardList.size()];

			int i = 0;
			// �������ʵ����
			CardStatus cardStatusItem;
			// ���༯ת��ΪIterator����
			Iterator iter = cardList.iterator();
			while (iter.hasNext()) {
				cardStatusItem = (CardStatus) iter.next();

				CardStatusResult invdisResult = new CardStatusResult();
				// ������
				invdisResult.setCardtype(cardStatusItem.getCardtype());
				// �豸��
				invdisResult.setDevno(cardStatusItem.getDevno());
				// �豸���ͱ��
				invdisResult.setTypeno(cardStatusItem.getTypeno());
				// ������
				invdisResult.setOrganno(cardStatusItem.getOrganno());
				// ����������
				invdisResult.setNumlytotal(cardStatusItem.getNumlytotal());
				// �ܷ�������
				invdisResult.setNumouttotal(cardStatusItem.getNumouttotal());
				// ������������
				invdisResult.setNumly(cardStatusItem.getNumly());
				// ���շ�������
				invdisResult.setNumout(cardStatusItem.getNumout());
				// ����ʣ������
				invdisResult.setNumrest(cardStatusItem.getNumrest());
				// �����ֶ�1
				// �����ֶ�2

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
			System.out.println("������..");
			return null;
		}
	}
}