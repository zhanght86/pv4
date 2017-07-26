package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.util.Constants;

/**
 * 
 * <p>
 * Title: CashDetailAddForm.java
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
public class CashDetailAddForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 设备编号
	 */
	private String[] devlist;
	/**
	 * 报表名称
	 */
	private String repnm;

	/**
	 * 出卡日期
	 */
	private String outcarddate1;

	/**
	 * 银行卡卡号
	 */
	private String cardnum;

	/**
	 * 截止日期
	 */
	private String outcarddate2;
	/**
	 * 开始时间
	 */
	private String outcardtime1;
	/**
	 * 截止时间
	 */
	private String outcardtime2;

	/**
	 * 钞箱批次号
	 */
	private String batchid;

	/**
	 * @return the batchid
	 */
	public String getBatchid() {
		return batchid;
	}

	/**
	 * @param batchid
	 *            the batchid to set
	 */
	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	/**
	 * 统计交易状态
	 */
	private String[] tradestatus;
	/**
	 * 钞箱状态，清钞未清钞
	 */
	private String[] outboxstatus;

	/**
	 * 获得 设备编号
	 * 
	 * @return the devlist
	 */
	public String[] getDevlist() {
		return devlist;
	}

	/**
	 * 设置 设备编号
	 * 
	 * @param devlist
	 *            the devlist to set
	 */
	public void setDevlist(String[] devlist) {
		this.devlist = devlist;
	}

	/**
	 * 获得开始日期
	 * 
	 * @return the outcarddate1
	 */
	public String getOutcarddate1() {
		return outcarddate1;
	}

	/**
	 * 设置 开始日期
	 * 
	 * @param outcarddate1
	 *            the outcarddate1 to set
	 */
	public void setOutcarddate1(String outcarddate1) {
		this.outcarddate1 = outcarddate1;
	}

	/**
	 * 获得截止日期
	 * 
	 * @return the outcarddate2
	 */
	public String getOutcarddate2() {
		return outcarddate2;
	}

	/**
	 * 设置 截止日期
	 * 
	 * @param outcarddate2
	 *            the outcarddate2 to set
	 */
	public void setOutcarddate2(String outcarddate2) {
		this.outcarddate2 = outcarddate2;
	}

	/**
	 * 获得开始时间
	 * 
	 * @return the outcardtime1
	 */
	public String getOutcardtime1() {
		return outcardtime1;
	}

	/**
	 * 设置 开始时间
	 * 
	 * @param outcardtime1
	 *            the outcardtime1 to set
	 */
	public void setOutcardtime1(String outcardtime1) {
		this.outcardtime1 = outcardtime1;
	}

	/**
	 * 获得截止时间
	 * 
	 * @return the outcardtime2
	 */
	public String getOutcardtime2() {
		return outcardtime2;
	}

	/**
	 * 设置 截止时间
	 * 
	 * @param outcardtime2
	 *            the outcardtime2 to set
	 */
	public void setOutcardtime2(String outcardtime2) {
		this.outcardtime2 = outcardtime2;
	}

	/**
	 * 获得 报表名称
	 * 
	 * @return the repnm
	 */
	public String getRepnm() {
		return repnm;
	}

	/**
	 * 设置 报表名称
	 * 
	 * @param repnm
	 *            the repnm to set
	 */
	public void setRepnm(String repnm) {
		this.repnm = repnm;
	}

	/**
	 * 验证..
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if ((devlist == null) || (devlist.length == 0)) {
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devinfo.no.selected");
			request.removeAttribute(mapping.getAttribute());
			return errors;
		}
		return errors;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public String[] getTradestatus() {
		return tradestatus;
	}

	public void setTradestatus(String[] tradestatus) {
		this.tradestatus = tradestatus;
	}

	public String[] getOutboxstatus() {
		return outboxstatus;
	}

	public void setOutboxstatus(String[] outboxstatus) {
		this.outboxstatus = outboxstatus;
	}
}
