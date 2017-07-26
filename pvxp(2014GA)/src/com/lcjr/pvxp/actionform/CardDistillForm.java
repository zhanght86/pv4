package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 卡凭证领用查询ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2011</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 武坤鹏
 * @version 1.0 2011/10/08
 */

public final class CardDistillForm extends ActionForm {
	/**
	 * 起始日期
	 */
	private String date1;
	/**
	 * 截止日期
	 */
	private String date2;
	/**
	 * 设备编号
	 */
	private String[] termnum;
	/**
	 * 机构编号
	 */
	private String bankID;
	

	/**
	 * 获得bankID
	 * @return the bankID
	 */
	public String getBankID() {
		return bankID;
	}


	/**
	 * 设置 bankID 
	 * @param bankID the bankID to set
	 */
	public void setBankID(String bankID) {
		this.bankID = bankID;
	}


	/**
	 * 获得date1
	 * @return the date1
	 */
	public String getDate1() {
		return date1;
	}


	/**
	 * 设置 date1 
	 * @param date1 the date1 to set
	 */
	public void setDate1(String date1) {
		this.date1 = date1;
	}


	/**
	 * 获得date2
	 * @return the date2
	 */
	public String getDate2() {
		return date2;
	}


	/**
	 * 设置 date2 
	 * @param date2 the date2 to set
	 */
	public void setDate2(String date2) {
		this.date2 = date2;
	}


	/**
	 * 获得选中的设备编号
	 * @return the termnum
	 */
	public String[] getTermnum() {
		return termnum;
	}


	/**
	 * 设置 termnum 
	 * @param termnum the termnum to set
	 */
	public void setTermnum(String[] termnum) {
		this.termnum = termnum;
	}


	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		return errors;
	}
}
