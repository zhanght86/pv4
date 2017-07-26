package com.lcjr.pvxp.orm;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 发卡管理，与表zzt_card_out表关联
 * 
 * @author 武坤鹏
 * @version 20110929
 */
public class CardOut implements Serializable {
	/**
	 * 卡类型（1：借记卡，2：信用卡，3：其他）
	 */
	private String cardtype;
	
	
	
	/**
	 * 设备号
	 */
	private String devno;
	
	
	
	/**
	 * 设备类型编号
	 */
	private String typeno;
	
	
	
	/**
	 * 机构号
	 */
	private String organno;
	
	
	
	/**
	 * 身份证号
	 */
	private String idcardno;
	
	
	
	/**
	 * 卡密码
	 */
	private String passwd;
	
	
	
	/**
	 * 条码号
	 */
	private String strcode;
	
	
	
	/**
	 * 卡号
	 */
	private String outcardno;
	
	
	
	/**
	 * 出卡日期
	 */
	private String outcarddate;
	
	
	
	/**
	 * 出卡时间
	 */
	private String outcardtime;
	
	
	
	/**
	 * 出卡状态（1：成功0：失败）
	 */
	private String outcardstatus;
	
	

	
	/**
	 * 备用字段
	 */
	private String remark1;
	
	
	
	/**
	 * 备用字段
	 */
	private String remark2;
	
	
	
	/**
	 * 备用字段
	 */
	private String remark3;
	
	
	
	/**
	 * 备用字段
	 */
	private String remark4;
	
	
	
	
	// 使用获得和设置
	
	
	/**
	 * 获得 卡类型（1：借记卡，2：信用卡，3：其他）
	 * 
	 * @return the cardtype
	 */
	public String getCardtype() {
		return cardtype;
	}
	
	
	
	/**
	 * 设置 卡类型（1：借记卡，2：信用卡，3：其他）
	 * 
	 * @param cardtype
	 *            the cardtype to set
	 */
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	
	
	
	/**
	 * 获得 设备号
	 * 
	 * @return the devno
	 */
	public String getDevno() {
		return devno;
	}
	
	
	
	/**
	 * 设置 设备号
	 * 
	 * @param devno
	 *            the devno to set
	 */
	public void setDevno(String devno) {
		this.devno = devno;
	}
	
	
	
	/**
	 * 获得 身份证号
	 * 
	 * @return the idcardno
	 */
	public String getIdcardno() {
		return idcardno;
	}
	
	
	
	/**
	 * 设置 身份证号
	 * 
	 * @param idcardno
	 *            the idcardno to set
	 */
	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}
	
	
	
	/**
	 * 获得 机构号
	 * 
	 * @return the organno
	 */
	public String getOrganno() {
		return organno;
	}
	
	
	
	/**
	 * 设置 机构号
	 * 
	 * @param organno
	 *            the organno to set
	 */
	public void setOrganno(String organno) {
		this.organno = organno;
	}
	
	
	
	/**
	 * 获得 出卡日期
	 * 
	 * @return the outcarddate
	 */
	public String getOutcarddate() {
		return outcarddate;
	}
	
	
	
	/**
	 * 设置 出卡日期
	 * 
	 * @param outcarddate
	 *            the outcarddate to set
	 */
	public void setOutcarddate(String outcarddate) {
		this.outcarddate = outcarddate;
	}
	
	
	
	/**
	 * 获得 发出卡的卡号
	 * 
	 * @return the outcardno
	 */
	public String getOutcardno() {
		return outcardno;
	}
	
	
	
	/**
	 * 设置 发出卡的卡号
	 * 
	 * @param outcardno
	 *            the outcardno to set
	 */
	public void setOutcardno(String outcardno) {
		this.outcardno = outcardno;
	}
	
	
	
	/**
	 * 获得 出卡状态（1：成功0：失败）
	 * 
	 * @return the outcardstatus
	 */
	public String getOutcardstatus() {
		return outcardstatus;
	}
	
	
	
	/**
	 * 设置 出卡状态（1：成功0：失败）
	 * 
	 * @param outcardstatus
	 *            the outcardstatus to set
	 */
	public void setOutcardstatus(String outcardstatus) {
		this.outcardstatus = outcardstatus;
	}
	
	
	
	/**
	 * 获得 出卡时间
	 * 
	 * @return the outcardtime
	 */
	public String getOutcardtime() {
		return outcardtime;
	}
	
	
	
	/**
	 * 设置 出卡时间
	 * 
	 * @param outcardtime
	 *            the outcardtime to set
	 */
	public void setOutcardtime(String outcardtime) {
		this.outcardtime = outcardtime;
	}
	
	
	
	/**
	 * 获得 卡密码
	 * 
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}
	
	
	
	/**
	 * 设置 卡密码
	 * 
	 * @param passwd
	 *            the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	
	
	/**
	 * 获得remark1
	 * 
	 * @return the remark1
	 */
	public String getRemark1() {
		return remark1;
	}
	
	
	
	/**
	 * 设置 remark1
	 * 
	 * @param remark1
	 *            the remark1 to set
	 */
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	
	
	/**
	 * 获得remark2
	 * 
	 * @return the remark2
	 */
	public String getRemark2() {
		return remark2;
	}
	
	
	
	/**
	 * 设置 remark2
	 * 
	 * @param remark2
	 *            the remark2 to set
	 */
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	
	
	/**
	 * 获得remark3
	 * 
	 * @return the remark3
	 */
	public String getRemark3() {
		return remark3;
	}
	
	
	
	/**
	 * 设置 remark3
	 * 
	 * @param remark3
	 *            the remark3 to set
	 */
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	
	
	
	/**
	 * 获得remark4
	 * 
	 * @return the remark4
	 */
	public String getRemark4() {
		return remark4;
	}
	
	
	
	/**
	 * 设置 remark4
	 * 
	 * @param remark4
	 *            the remark4 to set
	 */
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	
	
	
	/**
	 * 获得 条码号
	 * 
	 * @return the strcode
	 */
	public String getStrcode() {
		return strcode;
	}
	
	
	
	/**
	 * 设置 条码号
	 * 
	 * @param strcode
	 *            the strcode to set
	 */
	public void setStrcode(String strcode) {
		this.strcode = strcode;
	}
	
	
	
	/**
	 * 获得 设备类型编号
	 * 
	 * @return the typeno
	 */
	public String getTypeno() {
		return typeno;
	}
	
	
	
	/**
	 * 设置 设备类型编号
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
