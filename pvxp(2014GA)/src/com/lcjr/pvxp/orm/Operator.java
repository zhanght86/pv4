package com.lcjr.pvxp.orm;

import java.io.Serializable;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 在hibernate中映射zzt_operator表的类
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2012
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author 武坤鹏
 * @version 1.0 2012/06/01(儿童节)
 */
public class Operator implements Cloneable, Serializable{
	
	
	/**
	 * 操作员编号
	 */
	private String operid;
	
	
	
	/**
	 * 所属机构
	 */
	private String bankid;
	
	
	
	/**
	 * 操作员密码
	 */
	private String password;
	
	
	
	/**
	 * 操作员名称
	 */
	private String name;
	
	
	
	/**
	 * 操作员类型：0—系统管理员 1— 维护管理员 2— 查询浏览管理员 3—自定义
	 * 
	 */
	private String opertype;
	
	
	
	/**
	 * 权限编码
	 */
	private String authlist;
	
	
	
	/**
	 * 操作员状态，用于判断操作员是否登录： 0—可用 1—不可用
	 * 
	 */
	private String state;
	
	
	
	/**
	 * 密码过期日。 过期时长见PowerView.ini。 新添加操作员和重置操作员密码后，下次该操作员登录需修改密码。
	 * 
	 */
	private String adddate;
	
	
	
	/**
	 * remark1暂时存储登录次数loginnum
	 */
	private String loginnum;
	
	
	
	/**
	 * remark2暂时存储上次登录时间lastlogin
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
	 * 克隆
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
