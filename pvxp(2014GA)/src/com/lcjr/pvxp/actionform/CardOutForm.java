package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 发卡记录流水查询CardOut 表 zzt_card_out
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
 * @author 武坤鹏
 * @version 1.0 2011/10/10
 */
public class CardOutForm extends ActionForm {
	/**
	 * 起始日期
	 */
	private String date1;

	/**
	 * 截止日期
	 */
	private String date2;
	/**
	 * 起始时间
	 */
	private String time1;
	/**
	 * 截止时间
	 */
	private String time2;
	/**
	 * 身份证号
	 */
	private String id;
	/**
	 * 发卡卡号
	 */
	private String cardno;

	/**
	 * 设备编号
	 */
	private String[] termnum;

	/**
	 * 机构编号
	 */
	private String bankID;

	/**
	 * 获得机构编号
	 * 
	 * @return the bankID
	 */
	public String getBankID() {
		return bankID;
	}

	/**
	 * 设置 机构编号
	 * 
	 * @param bankID
	 *            the bankID to set
	 */
	public void setBankID(String bankID) {
		this.bankID = bankID;
	}

	/**
	 * 获得起始日期
	 * 
	 * @return the date1
	 */
	public String getDate1() {
		return date1;
	}

	/**
	 * 设置 起始日期
	 * 
	 * @param date1
	 *            the date1 to set
	 */
	public void setDate1(String date1) {
		this.date1 = date1;
	}

	/**
	 * 获得截止时间
	 * 
	 * @return the date2
	 */
	public String getDate2() {
		return date2;
	}

	/**
	 * 设置 截止时间
	 * 
	 * @param date2
	 *            the date2 to set
	 */
	public void setDate2(String date2) {
		this.date2 = date2;
	}

	/**
	 * 获得选中的设备编号
	 * 
	 * @return the termnum
	 */
	public String[] getTermnum() {
		return termnum;
	}

	/**
	 * 设置 termnum
	 * 
	 * @param termnum
	 *            the termnum to set
	 */
	public void setTermnum(String[] termnum) {
		this.termnum = termnum;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		return errors;
	}

	/**
	 * 获得 发卡卡号
	 * @return the cardno
	 */
	public String getCardno() {
		return cardno;
	}

	/**
	 * 设置 发卡卡号 
	 * @param cardno the cardno to set
	 */
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	/**
	 * 获得身份证号
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 身份证号 
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获得开始时间
	 * @return the time1
	 */
	public String getTime1() {
		return time1;
	}

	/**
	 * 设置 开始时间 
	 * @param time1 the time1 to set
	 */
	public void setTime1(String time1) {
		this.time1 = time1;
	}

	/**
	 * 获得截止时间
	 * @return the time2
	 */
	public String getTime2() {
		return time2;
	}

	/**
	 * 设置 截止时间 
	 * @param time2 the time2 to set
	 */
	public void setTime2(String time2) {
		this.time2 = time2;
	}
}