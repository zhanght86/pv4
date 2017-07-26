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

		// 报表名称
		String repnm = (String) ((CashDetailAddForm) form).getRepnm();

		// 设备编号
		String[] devlist = (String[]) ((CashDetailAddForm) form).getDevlist();
		// 出卡日期
		String outcarddate1 = (String) ((CashDetailAddForm) form).getOutcarddate1();
		// 截止日期
		String outcarddate2 = (String) ((CashDetailAddForm) form).getOutcarddate2();
		// 开始时间
		String outcardtime1 = (String) ((CashDetailAddForm) form).getOutcardtime1();
		// 截止时间
		String outcardtime2 = (String) ((CashDetailAddForm) form).getOutcardtime2();

		// 钞箱批次号
		String batchid = (String) ((CashDetailAddForm) form).getBatchid();

		// 统计交易状态
		String[] tradestatus = (String[]) ((CashDetailAddForm) form).getTradestatus();
		// 统计钞箱状态
		String[] outboxstatus = (String[]) ((CashDetailAddForm) form).getOutboxstatus();
		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemErrorr");

		try {
			// Cookie中存储的操作员权限编码
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			// Cookie中存储的操作员编号2
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			// Cookie中存储的操作员所属机构编号
			String bankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
			// 获得文件存储位置
			String filepath = getServlet().getServletContext().getRealPath("") + "/statresult/";

			// 超级管理员
			if (authlist.equals("*")) {
				bankid = " ";
				// 没有权限
			} else if (Integer.parseInt(authlist.substring(10, 11)) < 2) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			// 如果设备编号为空
			if ((devlist == null) || (devlist.length == 0)) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devinfo.no.selected");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			// 处理设备编号 将设备编号从数组写入到类集
			List devnoList = new ArrayList();
			for (int i = 0; i < devlist.length; i++) {
				devnoList.add(devlist[i]);
			}

			// 生成HQL
			// 向sql语句中添加设备编号
			String HQLstr = "select count(*),cd.devno,sum(cd.totalamount) from CashDetail cd where  (";
			for (int i = 0; i < devlist.length; i++) {
				HQLstr += " devno='" + devlist[i] + "' or";
			}

			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " )";

			// 没有开始日期，就不加入sql语句
			if (!outcarddate1.equals("")) {
				HQLstr += " and  trcddate>='" + outcarddate1 + "'";
			}

			// 没截至日期，就不加入sql语句
			if (!outcarddate2.equals("")) {
				HQLstr += " and  trcddate<='" + outcarddate2 + "'";
			}

			// 没有开始时间，就不加入sql语句
			if (!outcardtime1.equals("")) {
				HQLstr += " and  trcdtime>='" + outcardtime1 + "'";
			}

			// batchid 钞箱流水号
			if (!batchid.equals("")) {
				HQLstr += " and  batchid='" + batchid + "'";
			}

			// 没有截至时间，就不加入sql语句
			if (!outcardtime2.equals("")) {
				HQLstr += " and  trcdtime<='" + outcardtime2 + "'";
			}
			// 钞箱状态
			for (int i = 0; i < outboxstatus.length; i++) {
				if (i == 0) {
					HQLstr += " and dzflag in (";
				}
				HQLstr += " '" + outboxstatus[i] + "' ,";
			}
			HQLstr = HQLstr.substring(0, HQLstr.length() - 1) + " ) ";
			// 交易状态
			for (int i = 0; i < tradestatus.length; i++) {
				if (i == 0) {
					HQLstr += " and retflag in (";
				}
				HQLstr += " '" + tradestatus[i] + "' ,";
			}
			HQLstr = HQLstr.substring(0, HQLstr.length() - 1) + " ) ";
			HQLstr += " group by cd.devno";
			// 现金交易明细统计报表
			CashTjServer cashtjserver = new CashTjServer();
			cashtjserver.setHQLstr(HQLstr);// 查询sql语句
			cashtjserver.setBankid(bankid);// 操作员所属银行
			cashtjserver.setOperid(operid);// 操作员编号
			cashtjserver.setRepnm(repnm);// 报表名称
			cashtjserver.setDevnoList(devnoList);// 传输设备编号
			cashtjserver.setFilepath(filepath);// 文件路径
			cashtjserver.setOutcarddate1(outcarddate1);// 开始日期
			cashtjserver.setOutcarddate2(outcarddate2);// 截止日期
			cashtjserver.setTradestatus(tradestatus);
			cashtjserver.setOutboxstatus(outboxstatus);
			cashtjserver.setOutcardtime1(outcardtime1);// 开始时间
			cashtjserver.setOutcardtime2(outcardtime2);// 截至时间
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
