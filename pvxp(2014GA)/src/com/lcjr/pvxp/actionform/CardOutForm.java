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
 * <b>Description:</b> ������¼��ˮ��ѯCardOut �� zzt_card_out
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2011
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * <br>
 * 
 * @author ������
 * @version 1.0 2011/10/10
 */
public class CardOutForm extends ActionForm {
	/**
	 * ��ʼ����
	 */
	private String date1;

	/**
	 * ��ֹ����
	 */
	private String date2;
	/**
	 * ��ʼʱ��
	 */
	private String time1;
	/**
	 * ��ֹʱ��
	 */
	private String time2;
	/**
	 * ���֤��
	 */
	private String id;
	/**
	 * ��������
	 */
	private String cardno;

	/**
	 * �豸���
	 */
	private String[] termnum;

	/**
	 * �������
	 */
	private String bankID;

	/**
	 * ��û������
	 * 
	 * @return the bankID
	 */
	public String getBankID() {
		return bankID;
	}

	/**
	 * ���� �������
	 * 
	 * @param bankID
	 *            the bankID to set
	 */
	public void setBankID(String bankID) {
		this.bankID = bankID;
	}

	/**
	 * �����ʼ����
	 * 
	 * @return the date1
	 */
	public String getDate1() {
		return date1;
	}

	/**
	 * ���� ��ʼ����
	 * 
	 * @param date1
	 *            the date1 to set
	 */
	public void setDate1(String date1) {
		this.date1 = date1;
	}

	/**
	 * ��ý�ֹʱ��
	 * 
	 * @return the date2
	 */
	public String getDate2() {
		return date2;
	}

	/**
	 * ���� ��ֹʱ��
	 * 
	 * @param date2
	 *            the date2 to set
	 */
	public void setDate2(String date2) {
		this.date2 = date2;
	}

	/**
	 * ���ѡ�е��豸���
	 * 
	 * @return the termnum
	 */
	public String[] getTermnum() {
		return termnum;
	}

	/**
	 * ���� termnum
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
	 * ��� ��������
	 * @return the cardno
	 */
	public String getCardno() {
		return cardno;
	}

	/**
	 * ���� �������� 
	 * @param cardno the cardno to set
	 */
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	/**
	 * ������֤��
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * ���� ���֤�� 
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * ��ÿ�ʼʱ��
	 * @return the time1
	 */
	public String getTime1() {
		return time1;
	}

	/**
	 * ���� ��ʼʱ�� 
	 * @param time1 the time1 to set
	 */
	public void setTime1(String time1) {
		this.time1 = time1;
	}

	/**
	 * ��ý�ֹʱ��
	 * @return the time2
	 */
	public String getTime2() {
		return time2;
	}

	/**
	 * ���� ��ֹʱ�� 
	 * @param time2 the time2 to set
	 */
	public void setTime2(String time2) {
		this.time2 = time2;
	}
}