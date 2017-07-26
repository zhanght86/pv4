package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> ��ƾ֤���ò�ѯActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2011</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author ������
 * @version 1.0 2011/10/08
 */

public final class CardDistillForm extends ActionForm {
	/**
	 * ��ʼ����
	 */
	private String date1;
	/**
	 * ��ֹ����
	 */
	private String date2;
	/**
	 * �豸���
	 */
	private String[] termnum;
	/**
	 * �������
	 */
	private String bankID;
	

	/**
	 * ���bankID
	 * @return the bankID
	 */
	public String getBankID() {
		return bankID;
	}


	/**
	 * ���� bankID 
	 * @param bankID the bankID to set
	 */
	public void setBankID(String bankID) {
		this.bankID = bankID;
	}


	/**
	 * ���date1
	 * @return the date1
	 */
	public String getDate1() {
		return date1;
	}


	/**
	 * ���� date1 
	 * @param date1 the date1 to set
	 */
	public void setDate1(String date1) {
		this.date1 = date1;
	}


	/**
	 * ���date2
	 * @return the date2
	 */
	public String getDate2() {
		return date2;
	}


	/**
	 * ���� date2 
	 * @param date2 the date2 to set
	 */
	public void setDate2(String date2) {
		this.date2 = date2;
	}


	/**
	 * ���ѡ�е��豸���
	 * @return the termnum
	 */
	public String[] getTermnum() {
		return termnum;
	}


	/**
	 * ���� termnum 
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
