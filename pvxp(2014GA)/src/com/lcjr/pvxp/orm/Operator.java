package com.lcjr.pvxp.orm;

import java.io.Serializable;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> ��hibernate��ӳ��zzt_operator�����
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2012
 * </p>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * 
 * @author ������
 * @version 1.0 2012/06/01(��ͯ��)
 */
public class Operator implements Cloneable, Serializable{
	
	
	/**
	 * ����Ա���
	 */
	private String operid;
	
	
	
	/**
	 * ��������
	 */
	private String bankid;
	
	
	
	/**
	 * ����Ա����
	 */
	private String password;
	
	
	
	/**
	 * ����Ա����
	 */
	private String name;
	
	
	
	/**
	 * ����Ա���ͣ�0��ϵͳ����Ա 1�� ά������Ա 2�� ��ѯ�������Ա 3���Զ���
	 * 
	 */
	private String opertype;
	
	
	
	/**
	 * Ȩ�ޱ���
	 */
	private String authlist;
	
	
	
	/**
	 * ����Ա״̬�������жϲ���Ա�Ƿ��¼�� 0������ 1��������
	 * 
	 */
	private String state;
	
	
	
	/**
	 * ��������ա� ����ʱ����PowerView.ini�� ����Ӳ���Ա�����ò���Ա������´θò���Ա��¼���޸����롣
	 * 
	 */
	private String adddate;
	
	
	
	/**
	 * remark1��ʱ�洢��¼����loginnum
	 */
	private String loginnum;
	
	
	
	/**
	 * remark2��ʱ�洢�ϴε�¼ʱ��lastlogin
	 */
	private String lastlogin;
	
	
	
	
	/**
	 * @return the adddate
	 */
	public String getAdddate() {
		return adddate;
	}
	
	
	
	/**
	 * @param adddate
	 *            the adddate to set
	 */
	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}
	
	
	
	/**
	 * @return the authlist
	 */
	public String getAuthlist() {
		return authlist;
	}
	
	
	
	/**
	 * @param authlist
	 *            the authlist to set
	 */
	public void setAuthlist(String authlist) {
		this.authlist = authlist;
	}
	
	
	
	/**
	 * @return the bankid
	 */
	public String getBankid() {
		return bankid;
	}
	
	
	
	/**
	 * @param bankid
	 *            the bankid to set
	 */
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	
	
	
	/**
	 * @return the lastlogin
	 */
	public String getLastlogin() {
		return lastlogin;
	}
	
	
	
	/**
	 * @param lastlogin
	 *            the lastlogin to set
	 */
	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}
	
	
	
	/**
	 * @return the loginnum
	 */
	public String getLoginnum() {
		return loginnum;
	}
	
	
	
	/**
	 * @param loginnum
	 *            the loginnum to set
	 */
	public void setLoginnum(String loginnum) {
		this.loginnum = loginnum;
	}
	
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
	
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	/**
	 * @return the operid
	 */
	public String getOperid() {
		return operid;
	}
	
	
	
	/**
	 * @param operid
	 *            the operid to set
	 */
	public void setOperid(String operid) {
		this.operid = operid;
	}
	
	
	/**
	 * ��¡
	 */
	public Object clone() throws CloneNotSupportedException {
		Operator result = (Operator)super.clone();
		return result;
	}
	
	
	/**
	 * @return the opertype
	 */
	public String getOpertype() {
		return opertype;
	}
	
	
	
	/**
	 * @param opertype
	 *            the opertype to set
	 */
	public void setOpertype(String opertype) {
		this.opertype = opertype;
	}
	
	
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	
	
	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	
	
	
	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
}
