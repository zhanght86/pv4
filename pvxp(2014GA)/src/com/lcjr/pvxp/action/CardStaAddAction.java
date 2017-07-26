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
 * <b>Description:</b> 出卡失败记录统计报表列表
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author 武坤鹏
 * @version 1.0 20111011
 */
public final class CardStaAddAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionMessages errors = new ActionMessages();

		// 报表名称
		String repnm = (String) ((CardStaAddForm) form).getRepnm();
		// 卡类型
		String[] cardtype = (String[]) ((CardStaAddForm) form).getCardtype();
		// 设备编号
		String[] devlist = (String[]) ((CardStaAddForm) form).getDevlist();
		// 出卡日期
		String outcarddate1 = (String) ((CardStaAddForm) form).getOutcarddate1();
		// 截止日期
		String outcarddate2 = (String) ((CardStaAddForm) form).getOutcarddate2();
		// 开始时间
		String outcardtime1 = (String) ((CardStaAddForm) form).getOutcardtime1();
		// 截止时间
		String outcardtime2 = (String) ((CardStaAddForm) form).getOutcardtime2();
		// 统计出卡状态
		String[] outcardstatus = (String[]) ((CardStaAddForm) form).getOutcardstatus();
		// String[] qunit = (String[])((JytjMDealForm) form).getQunit(); 统计出卡状态
		// String repnm = (String)((JytjMDealForm) form).getRepnm().trim();
		// String qseq = (String)((JytjMDealForm) form).getQseq();
		// String qbegin = (String)((JytjMDealForm) form).getQbegin();
		// String qend = (String)((JytjMDealForm) form).getQend();
		// String strcdlist = (String)((JytjMDealForm) form).getStrcdlist();
		// String statmode = (String)((JytjMDealForm) form).getStatmode();

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
			String HQLstr = "from CardOut where  (";
			for (int i = 0; i < devlist.length; i++) {
				HQLstr += " devno='" + devlist[i] + "' or";
			}

			HQLstr = HQLstr.substring(0, HQLstr.length() - 3) + " )";

			// 没有开始日期，就不加入sql语句
			if (!outcarddate1.equals("")) {
				HQLstr += " and  outcarddate>='" + outcarddate1 + "'";
			}

			// 没截至日期，就不加入sql语句
			if (!outcarddate2.equals("")) {
				HQLstr += " and  outcarddate<='" + outcarddate2 + "'";
			}

			// 没有开始时间，就不加入sql语句
			if (!outcardtime1.equals("")) {
				HQLstr += " and  outcardtime>='" + outcardtime1 + "'";
			}

			// 没有截至时间，就不加入sql语句
			if (!outcardtime2.equals("")) {
				HQLstr += " and  outcardtime<='" + outcardtime2 + "'";
			}

			// 没有卡类型 ，就不加入sql语句

			for (int i = 0; i < cardtype.length; i++) {
				if (i == 0) {
					HQLstr += " and cardtype in (";
				}
				HQLstr += " '" + cardtype[i] + "' ,";
			}

			HQLstr = HQLstr.substring(0, HQLstr.length() - 1) + " ) ";

			// 没有出卡状态 ，就不加入sql语句

			// for (int i = 0; i < outcardstatus.length; i++) {
			// if (i == 0) {
			// HQLstr += " and outcardstatus in (";
			// }
			// HQLstr += "'" + outcardstatus[i] + "' ,";
			// }
			// HQLstr = HQLstr.substring(0, HQLstr.length() - 1)+" ) ";
			HQLstr = HQLstr + " and outcardstatus in ('0','1','2')";

			// 年统计报表
			CardTjServer cardtjserver = new CardTjServer();
			cardtjserver.setHQLstr(HQLstr);// 查询sql语句
			cardtjserver.setBankid(bankid);// 操作员所属银行
			cardtjserver.setOperid(operid);// 操作员编号
			cardtjserver.setRepnm(repnm);// 报表名称
			cardtjserver.setDevnoList(devnoList);// 传输设备编号
			cardtjserver.setFilepath(filepath);// 文件路径
			cardtjserver.setOutcarddate1(outcarddate1);// 开始日期
			cardtjserver.setOutcarddate2(outcarddate2);// 截止日期
			cardtjserver.setOutcardstatus(outcardstatus);// 出卡状态
			cardtjserver.setOutcardtime1(outcardtime1);// 开始时间
			cardtjserver.setOutcardtime2(outcardtime2);// 截至时间
			cardtjserver.setCardtype(cardtype);// 卡类型

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
