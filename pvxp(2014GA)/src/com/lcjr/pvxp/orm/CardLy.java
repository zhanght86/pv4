package com.lcjr.pvxp.orm;

import java.io.Serializable;

import java.lang.Object;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * �����������zzt_card_ly�����
 * 
 * @author ������
 * @version 20110929
 */
public class CardLy implements Serializable {
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
	 * ��������
	 */
	private String lydate;

	/**
	 * ����ʱ��
	 */
	private String lytime;

	/**
	 * ��������
	 */
	private String lynums;

	/**
	 * ����״̬��1������0�����ó�����
	 */
	private String lystatus;

	/**
	 * ����1�ֶ�
	 */
	private String remark1;

	/**
	 * ����2�ֶ�
	 */
	private String remark2;

	/**
	 * ��ÿ�����
	 * 
	 * @return
	 */
	public String getCardtype() {
		return cardtype;
	}

	/**
	 * ���ÿ�����
	 * 
	 * @param cardtype
	 *            ������
	 */
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	/**
	 * ����豸��
	 * 
	 * @return
	 */
	public String getDevno() {
		return devno;
	}

	/**
	 * �����豸��
	 * 
	 * @param devno
	 *            �豸��
	 */
	public void setDevno(String devno) {
		this.devno = devno;
	}

	/**
	 * �����������
	 * 
	 * @return
	 */
	public String getLydate() {
		return lydate;
	}

	/**
	 * ������������
	 * 
	 * @param lydate
	 *            ��������
	 */
	public void setLydate(String lydate) {
		this.lydate = lydate;
	}

	/**
	 * �����������
	 * 
	 * @return
	 */
	public String getLynums() {
		return lynums;
	}

	/**
	 * ������������
	 * 
	 * @param lynums
	 *            ��������
	 */
	public void setLynums(String lynums) {
		this.lynums = lynums;
	}

	/**
	 * �������״̬
	 * 
	 * @return
	 */
	public String getLystatus() {
		return lystatus;
	}

	/**
	 * ��������״̬
	 * 
	 * @param lystatus
	 *            ����״̬
	 */
	public void setLystatus(String lystatus) {
		this.lystatus = lystatus;
	}

	/**
	 * �������ʱ��
	 * 
	 * @return
	 */
	public String getLytime() {
		return lytime;
	}

	/**
	 * ��������ʱ��
	 * 
	 * @param lytime
	 *            ����ʱ��
	 */
	public void setLytime(String lytime) {
		this.lytime = lytime;
	}

	/**
	 * ��û�����
	 * 
	 * @return
	 */
	public String getOrganno() {
		return organno;
	}

	/**
	 * ���û�����
	 * 
	 * @param organno
	 *            ������
	 */
	public void setOrganno(String organno) {
		this.organno = organno;
	}

	/**
	 * ����1
	 * 
	 * @return
	 */
	public String getRemark1() {
		return remark1;
	}

	/**
	 * ����1
	 * 
	 * @param remark1
	 */
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	/**
	 * ����2
	 * 
	 * @return
	 */
	public String getRemark2() {
		return remark2;
	}

	/**
	 * ����2
	 * 
	 * @param remark2
	 */
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	/**
	 * ����豸���ͱ��
	 * 
	 * @return
	 */
	public String getTypeno() {
		return typeno;
	}

	/**
	 * �����豸���ͱ��
	 * 
	 * @param typeno
	 */
	public void setTypeno(String typeno) {
		this.typeno = typeno;
	}

	
	public boolean equals(Object other) {
		if (this == other)
			return true;
 		if (!(other instanceof CardLy))
 			return false;
	
 		CardLy cardly = (CardLy)other;
	
		if (!getDevno().equals(cardly.getDevno()))
			return false;
		if (!getCardtype().equals(cardly.getCardtype()))
			return false;
		if (!getTypeno().equals(cardly.getTypeno()))
			return false;
		if (!getOrganno().equals(cardly.getOrganno()))
			return false;
		if (!getLydate().equals(cardly.getLydate()))
			return false;
		if (!getLytime().equals(cardly.getLytime()))
			return false;
		if (!getLynums().equals(cardly.getLynums()))
			return false;
		if (!getLystatus().equals(cardly.getLystatus()))
			return false;
		if (!getRemark1().equals(cardly.getRemark1()))
			return false;
		if (!getRemark2().equals(cardly.getRemark2()))
			return false;
		return true;
	}
	

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getDevno())
			.append(getTypeno())
			.append(getCardtype())
			.append(getOrganno())
			.append(getLydate())
			.append(getLytime())
			.append(getLynums())
			.append(getLystatus())
			.append(getRemark1())
			.append(getRemark2())
			.toHashCode();
	}

}
