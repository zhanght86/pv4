package com.lcjr.pvxp.orm;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * �������������zzt_card_out������
 * 
 * @author ������
 * @version 20110929
 */
public class CardOut implements Serializable {
	/**
	 * �����ͣ�1����ǿ���2�����ÿ���3��������
	 */
	private String cardtype;
	
	
	
	/**
	 * �豸��
	 */
	private String devno;
	
	
	
	/**
	 * �豸���ͱ��
	 */
	private String typeno;
	
	
	
	/**
	 * ������
	 */
	private String organno;
	
	
	
	/**
	 * ����֤��
	 */
	private String idcardno;
	
	
	
	/**
	 * ������
	 */
	private String passwd;
	
	
	
	/**
	 * �����
	 */
	private String strcode;
	
	
	
	/**
	 * ����
	 */
	private String outcardno;
	
	
	
	/**
	 * ��������
	 */
	private String outcarddate;
	
	
	
	/**
	 * ����ʱ��
	 */
	private String outcardtime;
	
	
	
	/**
	 * ����״̬��1���ɹ�0��ʧ�ܣ�
	 */
	private String outcardstatus;
	
	

	
	/**
	 * �����ֶ�
	 */
	private String remark1;
	
	
	
	/**
	 * �����ֶ�
	 */
	private String remark2;
	
	
	
	/**
	 * �����ֶ�
	 */
	private String remark3;
	
	
	
	/**
	 * �����ֶ�
	 */
	private String remark4;
	
	
	
	
	// ʹ�û�ú�����
	
	
	/**
	 * ��� �����ͣ�1����ǿ���2�����ÿ���3��������
	 * 
	 * @return the cardtype
	 */
	public String getCardtype() {
		return cardtype;
	}
	
	
	
	/**
	 * ���� �����ͣ�1����ǿ���2�����ÿ���3��������
	 * 
	 * @param cardtype
	 *            the cardtype to set
	 */
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	
	
	
	/**
	 * ��� �豸��
	 * 
	 * @return the devno
	 */
	public String getDevno() {
		return devno;
	}
	
	
	
	/**
	 * ���� �豸��
	 * 
	 * @param devno
	 *            the devno to set
	 */
	public void setDevno(String devno) {
		this.devno = devno;
	}
	
	
	
	/**
	 * ��� ����֤��
	 * 
	 * @return the idcardno
	 */
	public String getIdcardno() {
		return idcardno;
	}
	
	
	
	/**
	 * ���� ����֤��
	 * 
	 * @param idcardno
	 *            the idcardno to set
	 */
	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}
	
	
	
	/**
	 * ��� ������
	 * 
	 * @return the organno
	 */
	public String getOrganno() {
		return organno;
	}
	
	
	
	/**
	 * ���� ������
	 * 
	 * @param organno
	 *            the organno to set
	 */
	public void setOrganno(String organno) {
		this.organno = organno;
	}
	
	
	
	/**
	 * ��� ��������
	 * 
	 * @return the outcarddate
	 */
	public String getOutcarddate() {
		return outcarddate;
	}
	
	
	
	/**
	 * ���� ��������
	 * 
	 * @param outcarddate
	 *            the outcarddate to set
	 */
	public void setOutcarddate(String outcarddate) {
		this.outcarddate = outcarddate;
	}
	
	
	
	/**
	 * ��� �������Ŀ���
	 * 
	 * @return the outcardno
	 */
	public String getOutcardno() {
		return outcardno;
	}
	
	
	
	/**
	 * ���� �������Ŀ���
	 * 
	 * @param outcardno
	 *            the outcardno to set
	 */
	public void setOutcardno(String outcardno) {
		this.outcardno = outcardno;
	}
	
	
	
	/**
	 * ��� ����״̬��1���ɹ�0��ʧ�ܣ�
	 * 
	 * @return the outcardstatus
	 */
	public String getOutcardstatus() {
		return outcardstatus;
	}
	
	
	
	/**
	 * ���� ����״̬��1���ɹ�0��ʧ�ܣ�
	 * 
	 * @param outcardstatus
	 *            the outcardstatus to set
	 */
	public void setOutcardstatus(String outcardstatus) {
		this.outcardstatus = outcardstatus;
	}
	
	
	
	/**
	 * ��� ����ʱ��
	 * 
	 * @return the outcardtime
	 */
	public String getOutcardtime() {
		return outcardtime;
	}
	
	
	
	/**
	 * ���� ����ʱ��
	 * 
	 * @param outcardtime
	 *            the outcardtime to set
	 */
	public void setOutcardtime(String outcardtime) {
		this.outcardtime = outcardtime;
	}
	
	
	
	/**
	 * ��� ������
	 * 
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}
	
	
	
	/**
	 * ���� ������
	 * 
	 * @param passwd
	 *            the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	
	
	/**
	 * ���remark1
	 * 
	 * @return the remark1
	 */
	public String getRemark1() {
		return remark1;
	}
	
	
	
	/**
	 * ���� remark1
	 * 
	 * @param remark1
	 *            the remark1 to set
	 */
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	
	
	/**
	 * ���remark2
	 * 
	 * @return the remark2
	 */
	public String getRemark2() {
		return remark2;
	}
	
	
	
	/**
	 * ���� remark2
	 * 
	 * @param remark2
	 *            the remark2 to set
	 */
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	
	
	/**
	 * ���remark3
	 * 
	 * @return the remark3
	 */
	public String getRemark3() {
		return remark3;
	}
	
	
	
	/**
	 * ���� remark3
	 * 
	 * @param remark3
	 *            the remark3 to set
	 */
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	
	
	
	/**
	 * ���remark4
	 * 
	 * @return the remark4
	 */
	public String getRemark4() {
		return remark4;
	}
	
	
	
	/**
	 * ���� remark4
	 * 
	 * @param remark4
	 *            the remark4 to set
	 */
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	
	
	
	/**
	 * ��� �����
	 * 
	 * @return the strcode
	 */
	public String getStrcode() {
		return strcode;
	}
	
	
	
	/**
	 * ���� �����
	 * 
	 * @param strcode
	 *            the strcode to set
	 */
	public void setStrcode(String strcode) {
		this.strcode = strcode;
	}
	
	
	
	/**
	 * ��� �豸���ͱ��
	 * 
	 * @return the typeno
	 */
	public String getTypeno() {
		return typeno;
	}
	
	
	
	/**
	 * ���� �豸���ͱ��
	 * 
	 * @param typeno
	 *            the typeno to set
	 */
	public void setTypeno(String typeno) {
		this.typeno = typeno;
	}
	
	
	public Object clone() throws CloneNotSupportedException {
		CardOut result = (CardOut) super.clone();
		return result;
	}
	
	
	public boolean equals(Object other) {
		if (this == other)
			return true;
 		if (!(other instanceof CardOut))
 			return false;
	
 		CardOut cardout = (CardOut)other;
	try {
		

		if (!getDevno().equals(cardout.getDevno()))
			return false;
	} catch (Exception e) {
		System.out.println(e);
		e.printStackTrace();
	}
		if (!getCardtype().equals(cardout.getCardtype()))
			return false;
		if (!getTypeno().equals(cardout.getTypeno()))
			return false;
		if (!getOrganno().equals(cardout.getOrganno()))
			return false;
		if (!getOutcardno().equals(cardout.getOutcardno()))
			return false;
		if (!getOutcarddate().equals(cardout.getOutcarddate()))
			return false;
		if (!getOutcardtime().equals(cardout.getOutcardtime()))
			return false;
		if (!getIdcardno().equals(cardout.getIdcardno()))
			return false;
		if (!getPasswd().equals(cardout.getPasswd()))
			return false;
		if (!getStrcode().equals(cardout.getStrcode()))
			return false;
		if (!getOutcardstatus().equals(cardout.getOutcardstatus()))
			return false;
		if (!getRemark1().equals(cardout.getRemark1()))
			return false;
		if (!getRemark2().equals(cardout.getRemark2()))
			return false;
		if (!getRemark3().equals(cardout.getRemark3()))
			return false;
		if (!getRemark4().equals(cardout.getRemark4()))
			return false;
		return true;
	}
	

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getDevno())
			.append(getTypeno())
			.append(getCardtype())
			.append(getOrganno())
			.append(getOutcardno())
			.append(getOutcarddate())
			.append(getOutcardtime())
			.append(getIdcardno())
			.append(getPasswd())
			.append(getStrcode())
			.append(getOutcardstatus())
			.append(getRemark1())
			.append(getRemark2())
			.append(getRemark3())
			.append(getRemark4())
			.toHashCode();
	}


 
	
}