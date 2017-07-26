package com.lcjr.pvxp.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.Session;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.lcjr.pvxp.actionform.DrtjMDealForm;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.DrtjCSDayServer;
import com.lcjr.pvxp.util.DrtjCSMonthServer;
import com.lcjr.pvxp.util.DrtjCSYearServer;
import com.lcjr.pvxp.util.DrtjDayServer;
import com.lcjr.pvxp.util.DrtjMonthServer;
import com.lcjr.pvxp.util.DrtjYearServer;
import com.lcjr.pvxp.util.DrtjjGDayServer;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> �豸������ͳ��Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2010
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.0 2010/08/02
 */
public final class DrtjMCSDealAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] devlist = (String[]) ((DrtjMDealForm) form).getDevlist();
		String repnm = (String) ((DrtjMDealForm) form).getRepnm().trim();
		String qseq = (String) ((DrtjMDealForm) form).getQseq();
		String qbegin = (String) ((DrtjMDealForm) form).getQbegin();
		String qend = (String) ((DrtjMDealForm) form).getQend();
		String statmode = (String) ((DrtjMDealForm) form).getStatmode();

		PubUtil myPubUtil = new PubUtil();
		CharSet myCharSet = new CharSet();
		ActionForward myforward = mapping.findForward("SystemError");

		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			String bankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
			String filepath = getServlet().getServletContext().getRealPath("") + "/statresult/";

			if (authlist.equals("*")) {
				// ��������Ա
				bankid = " ";
			} else if (Integer.parseInt(authlist.substring(10, 11)) < 2) {
				// û��Ȩ��
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			if ((devlist == null) || (devlist.length == 0)) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devinfo.no.selected");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			// �����豸���
			List devnoList = new ArrayList();
			for (int i = 0; i < devlist.length; i++) {
				devnoList.add(devlist[i]);
			}

			// ����HQL
//			String HQLstr = "from Devtj" + qseq + " where (";
			String devString = "";
			for (int i = 0; i < devlist.length; i++) {
				devString += "'"+devlist[i] +"',";
			}
			devString = devString.substring(0,devString.length()-1);
//
//			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and ";
//			HQLstr += " devdate>='" + qbegin + "' and devdate<='" + qend + "'";
//			if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
//				HQLstr += " order by devno, devdate";
			String HQLstr = "select cc.devname,sum(cc.times) as times,sum(cc.timelen) as timelen,sum(cc.normaltimes) as normaltimes,cc.devfactory from ((SELECT tjday.DEVNO,TJDAY.STATENO,TJDAY.DEVDATE,TJDAY.times,TJDAY.TIMELEN,DEV.REMARK1 as bankid,bank.banknm,devtype.devname,DEV.REMARK2 as normaltimes,devtype.devfactory FROM ZZT_DEVINFO dev join ZZT_DEVTJDAY tjday " +
					"ON(DEV.DEVNO=TJDAY.DEVNO) join ZZT_BANKINFO bank on(bank.bankid=DEV.REMARK1) join ZZT_DEVTYPE devtype on " +
					"(devtype.typeno=dev.typeno) WHERE TJDAY.DEVNO " +
					"IN ("+devString+") AND TJDAY.devdate " +
					"BETWEEN '"+qbegin+"' AND '"+qend+"') cc ) GROUP BY " +
					"cc.devname,cc.devfactory ORDER BY cc.devfactory";
			String sql = "SELECT bb.devfactory,bb.devname,count(bb.typeno) as devnum from ZZT_DEVINFO aa join ZZT_DEVTYPE bb on(aa.typeno=bb.typeno) " +
					" GROUP BY  bb.devfactory,bb.devname";
			String typeSql = "SELECT aa.devfactory,count(*) as num FROM ZZT_DEVTYPE aa GROUP BY aa.devfactory";
			if (qseq.equals("day")) {
				// ��ͳ�Ʊ���

				DrtjCSDayServer myDrtjDayServer = new DrtjCSDayServer();

				myDrtjDayServer.setHQLstr(HQLstr);
				myDrtjDayServer.setSql(sql);
				myDrtjDayServer.setTypesql(typeSql);
				myDrtjDayServer.setBankid(bankid);
				myDrtjDayServer.setOperid(operid);
				myDrtjDayServer.setRepnm(repnm);
				myDrtjDayServer.setQbegin(qbegin);
				myDrtjDayServer.setQend(qend);
				myDrtjDayServer.setFilepath(filepath);
				myDrtjDayServer.setStatmode(statmode);
				myDrtjDayServer.setDevnoList(devnoList);

				myDrtjDayServer.start();
			} else if (qseq.equals("month")) {
				HQLstr = "select cc.devname,sum(cc.times) as times,sum(cc.timelen) as timelen,sum(cc.normaltimes) as normaltimes,cc.devfactory from ((SELECT tjday.DEVNO,TJDAY.STATENO,TJDAY.DEVDATE,TJDAY.times,TJDAY.TIMELEN,DEV.REMARK1 as bankid,bank.banknm,devtype.devname,DEV.REMARK2 as normaltimes,devtype.devfactory FROM ZZT_DEVINFO dev join ZZT_DEVTJMONTH tjday " +
						"ON(DEV.DEVNO=TJDAY.DEVNO) join ZZT_BANKINFO bank on(bank.bankid=DEV.REMARK1) join ZZT_DEVTYPE devtype on " +
						"(devtype.typeno=dev.typeno) WHERE TJDAY.DEVNO " +
						"IN ("+devString+") AND TJDAY.devdate " +
						"BETWEEN '"+qbegin+"' AND '"+qend+"') cc ) GROUP BY " +
						"cc.devname,cc.devfactory ORDER BY cc.devfactory";
				// ��ͳ�Ʊ���
				DrtjCSMonthServer myDrtjMonthServer = new DrtjCSMonthServer();

				myDrtjMonthServer.setHQLstr(HQLstr);
				myDrtjMonthServer.setSql(sql);
				myDrtjMonthServer.setTypeSql(typeSql);
				myDrtjMonthServer.setBankid(bankid);
				myDrtjMonthServer.setOperid(operid);
				myDrtjMonthServer.setRepnm(repnm);
				myDrtjMonthServer.setQbegin(qbegin);
				myDrtjMonthServer.setQend(qend);
				myDrtjMonthServer.setFilepath(filepath);
				myDrtjMonthServer.setStatmode(statmode);
				myDrtjMonthServer.setDevnoList(devnoList);

				myDrtjMonthServer.start();

			} else if (qseq.equals("year")) {
				// ��ͳ�Ʊ���
				HQLstr = "select cc.devname,sum(cc.times) as times,sum(cc.timelen) as timelen,sum(cc.normaltimes) as normaltimes,cc.devfactory from ((SELECT tjday.DEVNO,TJDAY.STATENO,TJDAY.DEVDATE,TJDAY.times,TJDAY.TIMELEN,DEV.REMARK1 as bankid,bank.banknm,devtype.devname,DEV.REMARK2 as normaltimes,devtype.devfactory FROM ZZT_DEVINFO dev join ZZT_DEVTJYEAR tjday " +
						"ON(DEV.DEVNO=TJDAY.DEVNO) join ZZT_BANKINFO bank on(bank.bankid=DEV.REMARK1) join ZZT_DEVTYPE devtype on " +
						"(devtype.typeno=dev.typeno) WHERE TJDAY.DEVNO " +
						"IN ("+devString+") AND TJDAY.devdate " +
						"BETWEEN '"+qbegin+"' AND '"+qend+"') cc ) GROUP BY " +
						"cc.devname,cc.devfactory ORDER BY cc.devfactory";
				DrtjCSYearServer myDrtjYearServer = new DrtjCSYearServer();

				myDrtjYearServer.setHQLstr(HQLstr);
				myDrtjYearServer.setSql(sql);
				myDrtjYearServer.setTypeSql(typeSql);
				myDrtjYearServer.setBankid(bankid);
				myDrtjYearServer.setOperid(operid);
				myDrtjYearServer.setRepnm(repnm);
				myDrtjYearServer.setQbegin(qbegin);
				myDrtjYearServer.setQend(qend);
				myDrtjYearServer.setFilepath(filepath);
				myDrtjYearServer.setStatmode(statmode);
				myDrtjYearServer.setDevnoList(devnoList);

				myDrtjYearServer.start();
			}

			myforward = mapping.findForward("DrtjCSMDeal");

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}