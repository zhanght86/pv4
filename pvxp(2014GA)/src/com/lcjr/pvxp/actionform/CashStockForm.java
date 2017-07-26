package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @author 武坤鹏
 * @version pvxp
 * @date 2014-3-18
 */
public class CashStockForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 设备编号
	 */
	private String[] termnum;

	/**
	 * 开始日期
	 */
	private String trcddate1;

	/**
	 * 结束日期
	 */
	private String trcddate2;

	/**
	 * 
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		return errors;
	}

	/**
	 * @return the termnum
	 */
	public String[] getTermnum() {
		return termnum;
	}

	/**
	 * @param termnum
	 *            the termnum to set
	 */
	public void setTermnum(String[] termnum) {
		this.termnum = termnum;
	}

	/**
	 * @return the trcddate1
	 */
	public String getTrcddate1() {
		return trcddate1;
	}

	/**
	 * @param trcddate1
	 *            the trcddate1 to set
	 */
	public void setTrcddate1(String trcddate1) {
		this.trcddate1 = trcddate1;
	}

	/**
	 * @return the trcddate2
	 */
	public String getTrcddate2() {
		return trcddate2;
	}

	/**
	 * @param trcddate2
	 *            the trcddate2 to set
	 */
	public void setTrcddate2(String trcddate2) {
		this.trcddate2 = trcddate2;
	}
}
