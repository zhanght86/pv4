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
 * <b>Description:</b> ������¼��ˮ��ѯAction
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
 * @version 1.0 2011/10/10
 */
public class CardOutAction extends Action {

	// ����ҳ����ƺͲ�ѯ����༯
	public PageBean pb;

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
			String page = request.getParameter("page"); // ��ȡҳ��
			String[] termnum = ((CardOutForm) form).getTermnum(); // ��ȡ�豸���
			String date1 = pubUtil.dealNull(((CardOutForm) form).getDate1()); // ��ȡ��ʼ����
			String date2 = pubUtil.dealNull(((CardOutForm) form).getDate2()); // ��ȡ��ֹ����
			String id = pubUtil.dealNull(((CardOutForm) form).getId()); // ��ȡ���֤��
			String cardno = pubUtil.dealNull(((CardOutForm) form).getCardno()); // ��ȡ����
			String time1 = pubUtil.dealNull(((CardOutForm) form).getTime1()); // ��ȡ
																				// ��ʼʱ��
			String time2 = pubUtil.dealNull(((CardOutForm) form).getTime2()); // ��ȡ
																				// ��ֹʱ��
			// ���ҳ��Ϊ��
			if (page == null) {

				int totalRow = CardOutModel.getCardOutCount(termnum, date1, date2, time1, time2, id, cardno);

				// ����ҳ��
				PageBean pb = new PageBean(totalRow, 12);
				// ����ѯ�Ľ��������Ҫ����д�����ʵ����
				CardOutResult[] result = getResult(pb, termnum, date1, date2, time1, time2, id, cardno);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
				request.setAttribute("devno", termnum);
				request.setAttribute("date1", date1);
				request.setAttribute("date2", date2);

				return mapping.findForward("Success");

				// ���ҳ�벻Ϊ��
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
	 * ��ѯ������ݿ���Ϣ�������ϲ�ѯ���������ݷ�װ�����ʵ������
	 * 
	 * @param pb
	 *            ҳ��������
	 * @param termnum
	 *            ����
	 * @param date1
	 *            ��ʼ����
	 * @param date2
	 *            ��ֹ����
	 * @param cardno
	 *            ����
	 * @param id
	 *            ���֤��
	 * @param time2
	 *            ��ֹʱ��
	 * @param time1
	 *            ��ʼʱ��
	 * @return
	 */
	private CardOutResult[] getResult(PageBean pb, String[] termnum, String date1, String date2, String time1, String time2, String id, String cardno) {
		try {

			PubUtil pubUtil = new PubUtil();
			// ����ѯ�����Ľ����װ���༯�У��ȴ����浽���������
			List cardList = CardOutModel.getCardOutList(termnum, date1, date2, time1, time2, id, cardno, pb.beginRow, pb.getPageSize());
			// ���������༯ͬ����С�Ľ������
			CardOutResult result[] = new CardOutResult[cardList.size()];

			int i = 0;
			// �������ʵ����
			CardOut cardoutItem;
			// ���༯ת��ΪIterator����
			Iterator iter = cardList.iterator();
			while (iter.hasNext()) {
				cardoutItem = (CardOut) iter.next();

				CardOutResult cardoutResult = new CardOutResult();

				// �����ͣ�1����ǿ���2�����ÿ���3��������
				cardoutResult.setCardtype(cardoutItem.getCardtype());
				// �豸��
				cardoutResult.setDevno(cardoutItem.getDevno());
				// �豸���ͱ��
				cardoutResult.setTypeno(cardoutItem.getTypeno());
				// ������
				// cardoutResult.setOrganno(BankinfoModel.getBankinfoFromList(cardoutItem.getOrganno()).getBanknm().trim());
				cardoutResult.setOrganno(cardoutItem.getOrganno());
				// ���֤��
				cardoutResult.setIdcardno(cardoutItem.getIdcardno());
				// ������
				cardoutResult.setPasswd(cardoutItem.getPasswd());
				// �����
				cardoutResult.setStrcode(cardoutItem.getStrcode());
				// ����
				cardoutResult.setOutcardno(cardoutItem.getOutcardno());
				// ��������
				cardoutResult.setOutcarddate(cardoutItem.getOutcarddate());
				// ����ʱ��
				cardoutResult.setOutcardtime(cardoutItem.getOutcardtime());
				// ����״̬��1���ɹ�0��ʧ�ܣ�
				cardoutResult.setOutcardstatus(cardoutItem.getOutcardstatus());
				// �����ֶ�
				cardoutResult.setRemark1(pubUtil.dealNull(cardoutItem.getRemark1()));

				cardoutResult.setOrganno(pubUtil.dealNull(cardoutItem.getOrganno()));

				// cardoutResult.setSerialno(pubUtil.dealNull(cardoutItem.getSerialno()));

				// ���Ա
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