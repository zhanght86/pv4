package com.lcjr.pvxp.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.lcjr.pvxp.actionform.CardStaAddForm;
import com.lcjr.pvxp.util.CardTjServer;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> ����ʧ�ܼ�¼ͳ�Ʊ����б�
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * <br>
 * 
 * @author ������
 * @version 1.0 20111011
 */
public final class CardStaAddAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionMessages errors = new ActionMessages();

		// ��������
		String repnm = (String) ((CardStaAddForm) form).getRepnm();
		// ������
		String[] cardtype = (String[]) ((CardStaAddForm) form).getCardtype();
		// �豸���
		String[] devlist = (String[]) ((CardStaAddForm) form).getDevlist();
		// ��������
		String outcarddate1 = (String) ((CardStaAddForm) form).getOutcarddate1();
		// ��ֹ����
		String outcarddate2 = (String) ((CardStaAddForm) form).getOutcarddate2();
		// ��ʼʱ��
		String outcardtime1 = (String) ((CardStaAddForm) form).getOutcardtime1();
		// ��ֹʱ��
		String outcardtime2 = (String) ((CardStaAddForm) form).getOutcardtime2();
		// ͳ�Ƴ���״̬
		String[] outcardstatus = (String[]) ((CardStaAddForm) form).getOutcardstatus();
		// String[] qunit = (String[])((JytjMDealForm) form).getQunit(); ͳ�Ƴ���״̬
		// String repnm = (String)((JytjMDealForm) form).getRepnm().trim();
		// String qseq = (String)((JytjMDealForm) form).getQseq();
		// String qbegin = (String)((JytjMDealForm) form).getQbegin();
		// String qend = (String)((JytjMDealForm) form).getQend();
		// String strcdlist = (String)((JytjMDealForm) form).getStrcdlist();
		// String statmode = (String)((JytjMDealForm) form).getStatmode();

		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemErrorr");

		try {
			// Cookie�д洢�Ĳ���ԱȨ�ޱ���
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			// Cookie�д洢�Ĳ���Ա���2
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			// Cookie�д洢�Ĳ���Ա�����������
			String bankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
			// ����ļ��洢λ��
			String filepath = getServlet().getServletContext().getRealPath("") + "/statresult/";

			// ��������Ա
			if (authlist.equals("*")) {
				bankid = " ";
				// û��Ȩ��
			} else if (Integer.parseInt(authlist.substring(10, 11)) < 2) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			// ����豸���Ϊ��
			if ((devlist == null) || (devlist.length == 0)) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devinfo.no.selected");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			// �����豸��� ���豸��Ŵ�����д�뵽�༯
			List devnoList = new ArrayList();
			for (int i = 0; i < devlist.length; i++) {
				devnoList.add(devlist[i]);
			}

			// ����HQL
			// ��sql���������豸���
			String HQLstr = "from CardOut where  (";
			for (int i = 0; i < devlist.length; i++) {
				HQLstr += " devno='" + devlist[i] + "' or";
			}

			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " )";

			// û�п�ʼ���ڣ��Ͳ�����sql���
			if (!outcarddate1.equals("")) {
				HQLstr += " and  outcarddate>='" + outcarddate1 + "'";
			}

			// û�������ڣ��Ͳ�����sql���
			if (!outcarddate2.equals("")) {
				HQLstr += " and  outcarddate<='" + outcarddate2 + "'";
			}

			// û�п�ʼʱ�䣬�Ͳ�����sql���
			if (!outcardtime1.equals("")) {
				HQLstr += " and  outcardtime>='" + outcardtime1 + "'";
			}

			// û�н���ʱ�䣬�Ͳ�����sql���
			if (!outcardtime2.equals("")) {
				HQLstr += " and  outcardtime<='" + outcardtime2 + "'";
			}

			// û�п����� ���Ͳ�����sql���

			for (int i = 0; i < cardtype.length; i++) {
				if (i == 0) {
					HQLstr += " and cardtype in (";
				}
				HQLstr += " '" + cardtype[i] + "' ,";
			}

			HQLstr = HQLstr.substring(0, HQLstr.length() - 1) + " ) ";

			// û�г���״̬ ���Ͳ�����sql���

			// for (int i = 0; i < outcardstatus.length; i++) {
			// if (i == 0) {
			// HQLstr += " and outcardstatus in (";
			// }
			// HQLstr += "'" + outcardstatus[i] + "' ,";
			// }
			// HQLstr = HQLstr.substring(0, HQLstr.length() - 1)+" ) ";
			HQLstr = HQLstr + " and outcardstatus in ('0','1','2')";

			// ��ͳ�Ʊ���
			CardTjServer cardtjserver = new CardTjServer();
			cardtjserver.setHQLstr(HQLstr);// ��ѯsql���
			cardtjserver.setBankid(bankid);// ����Ա��������
			cardtjserver.setOperid(operid);// ����Ա���
			cardtjserver.setRepnm(repnm);// ��������
			cardtjserver.setDevnoList(devnoList);// �����豸���
			cardtjserver.setFilepath(filepath);// �ļ�·��
			cardtjserver.setOutcarddate1(outcarddate1);// ��ʼ����
			cardtjserver.setOutcarddate2(outcarddate2);// ��ֹ����
			cardtjserver.setOutcardstatus(outcardstatus);// ����״̬
			cardtjserver.setOutcardtime1(outcardtime1);// ��ʼʱ��
			cardtjserver.setOutcardtime2(outcardtime2);// ����ʱ��
			cardtjserver.setCardtype(cardtype);// ������

			cardtjserver.start();

			myforward = mapping.findForward("Success");

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}

}
