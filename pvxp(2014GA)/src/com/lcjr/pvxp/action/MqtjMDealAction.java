package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.actionform.*;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 报修记录统计Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2011
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.0 2011/03/02
 */
public final class MqtjMDealAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] devlist = (String[]) ((MqtjMDealForm) form).getDevlist();
		String[] subdevice = (String[]) ((MqtjMDealForm) form).getSubdevice();
		String[] state = (String[]) ((MqtjMDealForm) form).getState();
		String repnm = (String) ((MqtjMDealForm) form).getRepnm().trim();
		String qseq = (String) ((MqtjMDealForm) form).getQseq();
		String qbegin = (String) ((MqtjMDealForm) form).getQbegin();
		String qend = (String) ((MqtjMDealForm) form).getQend();
		
		PubUtil myPubUtil = new PubUtil();
		CharSet myCharSet = new CharSet();
		ActionForward myforward = mapping.findForward("SystemError");
		
		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			String bankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
			String filepath = getServlet().getServletContext().getRealPath("") + "/statresult/";
			
			if (authlist.equals("*")) {
				// 超级管理员
				bankid = " ";
			} else if (Integer.parseInt(authlist.substring(10, 11)) < 2) {
				// 没有权限
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
			
			if ((devlist == null) || (devlist.length == 0)) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devinfo.no.selected");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
			
			
			// 处理设备编号
			List devnoList = new ArrayList();
			for (int i = 0; i < devlist.length; i++) {
				devnoList.add(devlist[i]);
			}
			
			
			// 处理部件
			List subdeviceList = new ArrayList();
			for (int i = 0; i < subdevice.length; i++) {
				subdeviceList.add(subdevice[i]);
			}
			
			
			// 处理状态
			List stateList = new ArrayList();
			for (int i = 0; i < state.length; i++) {
				stateList.add(state[i]);
			}
			
			
			// 生成HQL
			String HQLstr = "from Maintain where (";
			for (int i = 0; i < devlist.length; i++) {
				HQLstr += " devno='" + devlist[i] + "' or";
			}
			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and (";
			for (int i = 0; i < subdevice.length; i++) {
				HQLstr += " trbtype='" + subdevice[i] + "' or";
			}
			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and (";
			for (int i = 0; i < state.length; i++) {
				HQLstr += " state='" + state[i] + "' or";
			}
			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " ) and ";
			
			if (qseq.equals("day")) {
				// 日统计报表
				
				HQLstr += " trbdate>='" + qbegin + "' and trbdate<='" + qend + "'";
				if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
					HQLstr += " order by devno, trbdate, trbtype";
				
				MqtjDayServer myMqtjDayServer = new MqtjDayServer();
				
				myMqtjDayServer.setHQLstr(HQLstr);
				myMqtjDayServer.setBankid(bankid);
				myMqtjDayServer.setOperid(operid);
				myMqtjDayServer.setRepnm(repnm);
				myMqtjDayServer.setQbegin(qbegin);
				myMqtjDayServer.setQend(qend);
				myMqtjDayServer.setFilepath(filepath);
				myMqtjDayServer.setDevnoList(devnoList);
				myMqtjDayServer.setSubdevice(subdeviceList);
				myMqtjDayServer.setMqtjState(stateList);
				
				myMqtjDayServer.start();
			} else if (qseq.equals("month")) {
				// 月统计报表
				
				qbegin += "01";
				qend += "31";
				
				HQLstr += " trbdate>='" + qbegin + "' and trbdate<='" + qend + "'";
				if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
					HQLstr += " order by devno, trbdate, trbtype";
				
				MqtjMonthServer myMqtjMonthServer = new MqtjMonthServer();
				
				myMqtjMonthServer.setHQLstr(HQLstr);
				myMqtjMonthServer.setBankid(bankid);
				myMqtjMonthServer.setOperid(operid);
				myMqtjMonthServer.setRepnm(repnm);
				myMqtjMonthServer.setQbegin(qbegin);
				myMqtjMonthServer.setQend(qend);
				myMqtjMonthServer.setFilepath(filepath);
				myMqtjMonthServer.setDevnoList(devnoList);
				myMqtjMonthServer.setSubdevice(subdeviceList);
				myMqtjMonthServer.setState(stateList);
				
				myMqtjMonthServer.start();
				
			} else if (qseq.equals("year")) {
				// 年统计报表
				qbegin += "0101";
				qend += "1231";
				
				HQLstr += " trbdate>='" + qbegin + "' and trbdate<='" + qend + "'";
				if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
					HQLstr += " order by devno, trbdate, trbtype";
				
				MqtjYearServer myMqtjYearServer = new MqtjYearServer();
				
				myMqtjYearServer.setHQLstr(HQLstr);
				myMqtjYearServer.setBankid(bankid);
				myMqtjYearServer.setOperid(operid);
				myMqtjYearServer.setRepnm(repnm);
				myMqtjYearServer.setQbegin(qbegin);
				myMqtjYearServer.setQend(qend);
				myMqtjYearServer.setFilepath(filepath);
				myMqtjYearServer.setDevnoList(devnoList);
				myMqtjYearServer.setSubdevice(subdeviceList);
				myMqtjYearServer.setState(stateList);
				
				myMqtjYearServer.start();
				
			}
			
			myforward = mapping.findForward("MqtjMDeal");
			
		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}
		
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}