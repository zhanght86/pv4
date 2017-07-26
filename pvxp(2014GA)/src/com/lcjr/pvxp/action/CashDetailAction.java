package com.lcjr.pvxp.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.stereotype.Component;

import com.lcjr.pvxp.actionform.CashDetailForm;
import com.lcjr.pvxp.bean.interfac.CashDetailDAO;
import com.lcjr.pvxp.orm.CashDetail;

@Component("/cashDetail")
public class CashDetailAction extends DispatchAction {
	@Resource
	private CashDetailDAO cashDetailDAO;

	Logger log = Logger.getLogger("web.com.lcjr.pvxp.action.CashDetailAction.java");

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CashDetailForm forms = (CashDetailForm) form;
		CashDetail t = getBeans(forms);
		String other = "";
		log.info("��ʼ��ѯCashDetailAction");
		// �豸״̬
		if (forms.getTermnum() != null && forms.getTermnum().length > 0) {
			String devnoString = "";
			for (int i = 0; i < forms.getTermnum().length; i++) {
				devnoString += "'" + forms.getTermnum()[i].trim() + "',";
			}
			other += " and devno in (" + devnoString + ")";

			other = other.substring(0, other.length() - 3) + "')";
		}
		log.info("�豸״̬other==" + other);
		// ����״̬
		if (forms.getOutboxstatus() != null && forms.getOutboxstatus().length > 0) {
			String outboxString = "";
			for (int i = 0; i < forms.getOutboxstatus().length; i++) {
				outboxString += "'" + forms.getOutboxstatus()[i].trim() + "',";
			}
			other += " and dzflag in (" + outboxString + ")";
			other = other.substring(0, other.length() - 3) + "')";
		}

		log.info("����״̬other==" + other);
		// ����״̬
		if (forms.getTradestatus() != null && forms.getTradestatus().length > 0) {
			String tradeString = "";
			for (int i = 0; i < forms.getTradestatus().length; i++) {
				tradeString += "'" + forms.getTradestatus()[i].trim() + "',";
			}
			other += " and retflag in (" + tradeString + ")";
			other = other.substring(0, other.length() - 3) + "')";
		}

		log.info("����״̬other==" + other);
		if (forms.getStartdate() != null && !forms.getStartdate().equals("")) {

			if (forms.getStarttime() != null && !forms.getStarttime().equals("")) {
				other += " and trcddate>=" + forms.getStartdate() + " and trcdtime>=" + forms.getStarttime();
			} else {
				other += " and trcddate >=" + forms.getStartdate();
			}
		}

		log.info("���׿�ʼʱ��other==" + other);
		if (forms.getEnddate() != null && !forms.getEnddate().equals("")) {

			if (forms.getEndtime() != null && !forms.getEndtime().equals("")) {
				other += " and trcddate<=" + forms.getEnddate() + " and trcdtime<=" + forms.getEndtime();
			} else {
				other += " and trcddate <=" + forms.getEnddate();
			}
		}

		log.info("���׽���ʱ��other==" + other);
		if (forms.getBatchid() != null && !forms.getBatchid().equals("")) {
			other += " and batchid='" + forms.getBatchid() + "'  ";
		}
		log.info("�������κ�other==" + other);

		log.info("��ѯsql�����==" + other);
		List<CashDetail> cashdetailList = cashDetailDAO.select(t, other);

		log.info("��õļ�¼�����ǣ�" + cashdetailList.size());

		// // ��ǰҳ��
		// int currentPage;
		// int pageSize = 1;
		// // ��ҳ��
		// int totalPages;
		// // ����
		// int totalDevCount = cashdetailList.size();
		// pageSize = Integer.parseInt(((CashDetailForm) form).getPagesize());
		// totalPages = (totalDevCount / pageSize) + (totalDevCount % pageSize
		// == 0 ? 0 : 1);
		// currentPage = Integer.parseInt(((CashDetailForm) form).getPage());
		// if (currentPage > totalPages) {
		// currentPage = totalPages;
		// }
		// if (currentPage < 1) {
		// currentPage = 1;
		// }

		// // ������Ҫ���豸��Ϣ
		// int begin = pageSize * (currentPage - 1);
		// int end = begin + pageSize;
		// if (end > totalDevCount) {
		// end = totalDevCount;
		// }
		List<CashDetail> pageList = new ArrayList<CashDetail>();
		// for (int i = begin; i < end; i++) {
		// pageList.add(cashdetailList.get(i));
		// }
		// log.info("cashDetailDAO==" + cashDetailDAO.toString());
		// List<CashDetail> list = cashDetailDAO.select(t, other);
		// log.info("���CashDetail�ļ�¼������list��" + list.size());
		// request.setAttribute("detailResult", list);
		ActionForward forward = mapping.findForward("Success");
		// request.setAttribute("currentpage", new Integer(currentPage));
		request.setAttribute("detailResult", cashdetailList);
		// request.setAttribute("totalDevCount", new Integer(totalDevCount));
		// request.setAttribute("totalPages", new Integer(totalPages));
		return (forward);
	}

	/**
	 * ����ֽ���ʵ��
	 * 
	 * @param form
	 * @return
	 */
	private CashDetail getBeans(CashDetailForm form) {
		CashDetail t = new CashDetail();
		t.setCardno(form.getCardnum());
		t.setRetflag(form.getCardstate());
		t.setDzflag(form.getBoxstate());
		t.setIdcardno(form.getPersonnum());
		t.setBatchid(form.getBatchid());
		return t;
	}

}
