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

import com.lcjr.pvxp.actionform.CashDetailAddForm;
import com.lcjr.pvxp.util.CashTjServer;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * 
 * <p>
 * Title: CashDetailAddAction.java
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: inspur
 * </p>
 * 
 * @author wang-jl
 * @date 2014-3-21
 * @version 1.0
 */
public class CashDetailAddAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionMessages errors = new ActionMessages();

		// ��������
		String repnm = (String) ((CashDetailAddForm) form).getRepnm();

		// �豸���
		String[] devlist = (String[]) ((CashDetailAddForm) form).getDevlist();
		// ��������
		String outcarddate1 = (String) ((CashDetailAddForm) form).getOutcarddate1();
		// ��ֹ����
		String outcarddate2 = (String) ((CashDetailAddForm) form).getOutcarddate2();
		// ��ʼʱ��
		String outcardtime1 = (String) ((CashDetailAddForm) form).getOutcardtime1();
		// ��ֹʱ��
		String outcardtime2 = (String) ((CashDetailAddForm) form).getOutcardtime2();

		// �������κ�
		String batchid = (String) ((CashDetailAddForm) form).getBatchid();

		// ͳ�ƽ���״̬
		String[] tradestatus = (String[]) ((CashDetailAddForm) form).getTradestatus();
		// ͳ�Ƴ���״̬
		String[] outboxstatus = (String[]) ((CashDetailAddForm) form).getOutboxstatus();
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
			String HQLstr = "select count(*),cd.devno,sum(cd.totalamount) from CashDetail cd where  (";
			for (int i = 0; i < devlist.length; i++) {
				HQLstr += " devno='" + devlist[i] + "' or";
			}

			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " )";

			// û�п�ʼ���ڣ��Ͳ�����sql���
			if (!outcarddate1.equals("")) {
				HQLstr += " and  trcddate>='" + outcarddate1 + "'";
			}

			// û�������ڣ��Ͳ�����sql���
			if (!outcarddate2.equals("")) {
				HQLstr += " and  trcddate<='" + outcarddate2 + "'";
			}

			// û�п�ʼʱ�䣬�Ͳ�����sql���
			if (!outcardtime1.equals("")) {
				HQLstr += " and  trcdtime>='" + outcardtime1 + "'";
			}

			// batchid ������ˮ��
			if (!batchid.equals("")) {
				HQLstr += " and  batchid='" + batchid + "'";
			}

			// û�н���ʱ�䣬�Ͳ�����sql���
			if (!outcardtime2.equals("")) {
				HQLstr += " and  trcdtime<='" + outcardtime2 + "'";
			}
			// ����״̬
			for (int i = 0; i < outboxstatus.length; i++) {
				if (i == 0) {
					HQLstr += " and dzflag in (";
				}
				HQLstr += " '" + outboxstatus[i] + "' ,";
			}
			HQLstr = HQLstr.substring(0, HQLstr.length() - 1) + " ) ";
			// ����״̬
			for (int i = 0; i < tradestatus.length; i++) {
				if (i == 0) {
					HQLstr += " and retflag in (";
				}
				HQLstr += " '" + tradestatus[i] + "' ,";
			}
			HQLstr = HQLstr.substring(0, HQLstr.length() - 1) + " ) ";
			HQLstr += " group by cd.devno";
			// �ֽ�����ϸͳ�Ʊ���
			CashTjServer cashtjserver = new CashTjServer();
			cashtjserver.setHQLstr(HQLstr);// ��ѯsql���
			cashtjserver.setBankid(bankid);// ����Ա��������
			cashtjserver.setOperid(operid);// ����Ա���
			cashtjserver.setRepnm(repnm);// ��������
			cashtjserver.setDevnoList(devnoList);// �����豸���
			cashtjserver.setFilepath(filepath);// �ļ�·��
			cashtjserver.setOutcarddate1(outcarddate1);// ��ʼ����
			cashtjserver.setOutcarddate2(outcarddate2);// ��ֹ����
			cashtjserver.setTradestatus(tradestatus);
			cashtjserver.setOutboxstatus(outboxstatus);
			cashtjserver.setOutcardtime1(outcardtime1);// ��ʼʱ��
			cashtjserver.setOutcardtime2(outcardtime2);// ����ʱ��
			cashtjserver.start();

			myforward = mapping.findForward("Success");

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}

}
