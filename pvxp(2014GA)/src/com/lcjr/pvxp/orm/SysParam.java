package com.lcjr.pvxp.orm;

import java.io.Serializable;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> ��hibernate��ӳ��zzt_sysparam�����
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2012
 * </p>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * 
 * @author ������
 * @version 1.0 2012/06/01
 */
public class SysParam implements Serializable {
	
	
	/**
	 * ϵͳ�汾
	 */
	private String sysver;
	
	
	
	/**
	 * ��ϸ��ʼ����
	 */
	private String bdofmxb;
	
	
	
	/**
	 * ����ͳ���ջ��ܿ�ʼ����
	 */
	private String bdofjytjday;
	
	
	
	/**
	 * ����ͳ���»��ܿ�ʼ�·�
	 */
	private String bdofjytjmonth;
	
	
	
	/**
	 * ����ͳ������ܿ�ʼ���
	 */
	private String bdofjytjyear;
	
	
	
	/**
	 * �豸ͳ���ջ��ܿ�ʼ����
	 */
	private String bdofsbtjday;
	
	
	
	/**
	 * �豸ͳ���»��ܿ�ʼ�·�
	 */
	private String bdofsbtjmonth;
	
	
	
	/**
	 * �豸ͳ������ܿ�ʼ���
	 */
	private String bdofsbtjyear;
	
	
	
	/**
	 * ��ϸ��������
	 */
	private String kmofmxb;
	
	
	
	/**
	 * ����ͳ���ջ��ܱ�������
	 */
	private String kmofjytjday;
	
	
	
	/**
	 * ����ͳ���»��ܱ�������
	 */
	private String kmofjytjmonth;
	
	
	
	/**
	 * ����ͳ������ܱ�������
	 */
	private String kmofjytjyear;
	
	
	
	/**
	 * �豸ͳ���ջ��ܱ�������
	 */
	private String kmofsbtjday;
	
	
	
	/**
	 * �豸ͳ���ջ��ܱ�������
	 */
	private String kmofsbtjmonth;
	
	
	
	/**
	 * �豸ͳ���»��ܱ�������
	 */
	private String kmofsbtjyear;
	
	
	
	/**
	 * ������ʷ��ϸ��״̬ F������ B�����ڵ���
	 * 
	 */
	private String statusoftmptable;
	
	
	
	/**
	 * ��ǰϵͳ��������ʷ������ϸ��ʱ�ļ���ʼ�·�
	 */
	private String tmpdatastart;
	
	
	
	/**
	 * ������ʷ��ϸ������������� 200403,200404,200412,200503 ���������24���µ�����
	 * 
	 */
	private String monthsoftmptable;
	
	
	
	
	/**
	 * @return the bdofjytjday
	 */
	public String getBdofjytjday() {
		return bdofjytjday;
	}
	
	
	
	/**
	 * @param bdofjytjday
	 *            the bdofjytjday to set
	 */
	public void setBdofjytjday(String bdofjytjday) {
		this.bdofjytjday = bdofjytjday;
	}
	
	
	
	/**
	 * @return the bdofjytjmonth
	 */
	public String getBdofjytjmonth() {
		return bdofjytjmonth;
	}
	
	
	
	/**
	 * @param bdofjytjmonth
	 *            the bdofjytjmonth to set
	 */
	public void setBdofjytjmonth(String bdofjytjmonth) {
		this.bdofjytjmonth = bdofjytjmonth;
	}
	
	
	
	/**
	 * @return the bdofjytjyear
	 */
	public String getBdofjytjyear() {
		return bdofjytjyear;
	}
	
	
	
	/**
	 * @param bdofjytjyear
	 *            the bdofjytjyear to set
	 */
	public void setBdofjytjyear(String bdofjytjyear) {
		this.bdofjytjyear = bdofjytjyear;
	}
	
	
	
	/**
	 * @return the bdofmxb
	 */
	public String getBdofmxb() {
		return bdofmxb;
	}
	
	
	
	/**
	 * @param bdofmxb
	 *            the bdofmxb to set
	 */
	public void setBdofmxb(String bdofmxb) {
		this.bdofmxb = bdofmxb;
	}
	
	
	
	/**
	 * @return the bdofsbtjday
	 */
	public String getBdofsbtjday() {
		return bdofsbtjday;
	}
	
	
	
	/**
	 * @param bdofsbtjday
	 *            the bdofsbtjday to set
	 */
	public void setBdofsbtjday(String bdofsbtjday) {
		this.bdofsbtjday = bdofsbtjday;
	}
	
	
	
	/**
	 * @return the bdofsbtjmonth
	 */
	public String getBdofsbtjmonth() {
		return bdofsbtjmonth;
	}
	
	
	
	/**
	 * @param bdofsbtjmonth
	 *            the bdofsbtjmonth to set
	 */
	public void setBdofsbtjmonth(String bdofsbtjmonth) {
		this.bdofsbtjmonth = bdofsbtjmonth;
	}
	
	
	
	/**
	 * @return the bdofsbtjyear
	 */
	public String getBdofsbtjyear() {
		return bdofsbtjyear;
	}
	
	
	
	/**
	 * @param bdofsbtjyear
	 *            the bdofsbtjyear to set
	 */
	public void setBdofsbtjyear(String bdofsbtjyear) {
		this.bdofsbtjyear = bdofsbtjyear;
	}
	
	
	
	/**
	 * @return the kmofjytjday
	 */
	public String getKmofjytjday() {
		return kmofjytjday;
	}
	
	
	
	/**
	 * @param kmofjytjday
	 *            the kmofjytjday to set
	 */
	public void setKmofjytjday(String kmofjytjday) {
		this.kmofjytjday = kmofjytjday;
	}
	
	
	
	/**
	 * @return the kmofjytjmonth
	 */
	public String getKmofjytjmonth() {
		return kmofjytjmonth;
	}
	
	
	
	/**
	 * @param kmofjytjmonth
	 *            the kmofjytjmonth to set
	 */
	public void setKmofjytjmonth(String kmofjytjmonth) {
		this.kmofjytjmonth = kmofjytjmonth;
	}
	
	
	
	/**
	 * @return the kmofjytjyear
	 */
	public String getKmofjytjyear() {
		return kmofjytjyear;
	}
	
	
	
	/**
	 * @param kmofjytjyear
	 *            the kmofjytjyear to set
	 */
	public void setKmofjytjyear(String kmofjytjyear) {
		this.kmofjytjyear = kmofjytjyear;
	}
	
	
	
	/**
	 * @return the kmofmxb
	 */
	public String getKmofmxb() {
		return kmofmxb;
	}
	
	
	
	/**
	 * @param kmofmxb
	 *            the kmofmxb to set
	 */
	public void setKmofmxb(String kmofmxb) {
		this.kmofmxb = kmofmxb;
	}
	
	
	
	/**
	 * @return the kmofsbtjday
	 */
	public String getKmofsbtjday() {
		return kmofsbtjday;
	}
	
	
	
	/**
	 * @param kmofsbtjday
	 *            the kmofsbtjday to set
	 */
	public void setKmofsbtjday(String kmofsbtjday) {
		this.kmofsbtjday = kmofsbtjday;
	}
	
	
	
	/**
	 * @return the kmofsbtjmonth
	 */
	public String getKmofsbtjmonth() {
		return kmofsbtjmonth;
	}
	
	
	
	/**
	 * @param kmofsbtjmonth
	 *            the kmofsbtjmonth to set
	 */
	public void setKmofsbtjmonth(String kmofsbtjmonth) {
		this.kmofsbtjmonth = kmofsbtjmonth;
	}
	
	
	
	/**
	 * @return the kmofsbtjyear
	 */
	public String getKmofsbtjyear() {
		return kmofsbtjyear;
	}
	
	
	
	/**
	 * @param kmofsbtjyear
	 *            the kmofsbtjyear to set
	 */
	public void setKmofsbtjyear(String kmofsbtjyear) {
		this.kmofsbtjyear = kmofsbtjyear;
	}
	
	
	
	/**
	 * @return the monthsoftmptable
	 */
	public String getMonthsoftmptable() {
		return monthsoftmptable;
	}
	
	
	
	/**
	 * @param monthsoftmptable
	 *            the monthsoftmptable to set
	 */
	public void setMonthsoftmptable(String monthsoftmptable) {
		this.monthsoftmptable = monthsoftmptable;
	}
	
	
	
	/**
	 * @return the statusoftmptable
	 */
	public String getStatusoftmptable() {
		return statusoftmptable;
	}
	
	
	
	/**
	 * @param statusoftmptable
	 *            the statusoftmptable to set
	 */
	public void setStatusoftmptable(String statusoftmptable) {
		this.statusoftmptable = statusoftmptable;
	}
	
	
	
	/**
	 * @return the sysver
	 */
	public String getSysver() {
		return sysver;
	}
	
	
	
	/**
	 * @param sysver
	 *            the sysver to set
	 */
	public void setSysver(String sysver) {
		this.sysver = sysver;
	}
	
	
	
	/**
	 * @return the tmpdatastart
	 */
	public String getTmpdatastart() {
		return tmpdatastart;
	}
	
	
	
	/**
	 * @param tmpdatastart
	 *            the tmpdatastart to set
	 */
	public void setTmpdatastart(String tmpdatastart) {
		this.tmpdatastart = tmpdatastart;
	}
	
	
	
	/**
	 * 
	 */
	public Object clone() throws CloneNotSupportedException {
		SysParam result = (SysParam) super.clone();
		return result;
	}
}
