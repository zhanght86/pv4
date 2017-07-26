package com.lcjr.pvxp.orm;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;
/**
 * 发卡管理，与表zzt_card_status表关联
 * @author 武坤鹏
 *@version 20110929
 */

public class CardStatus implements Serializable {
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
	 * 总领用张数
	 */
	private String numlytotal;
	/**
	 * 总发卡张数
	 */
	private String numouttotal;
	/**
	 * 当日领用张数
	 */
	private String numly;
	/**
	 * 当日发卡张数
	 */
	private String numout;
	/**
	 * 现在剩余张数
	 */
	private String numrest;
	/**
	 * 备用1字段
	 */
	private String remark1;
	/**
	 * 备用2字段
	 */
	private String remark2;
	
	
	//设置和获得
	
	/**
	 * 获得 卡类型（1：借记卡，2：信用卡，3：其他）
	 * @return the cardtype
	 */
	public String getCardtype() {
		return cardtype;
	}
	/**
	 * 设置 卡类型（1：借记卡，2：信用卡，3：其他） 
	 * @param cardtype the cardtype to set
	 */
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	/**
	 * 获得 设备号
	 * @return the devno
	 */
	public String getDevno() {
		return devno;
	}
	/**
	 * 设置 设备号 
	 * @param devno the devno to set
	 */
	public void setDevno(String devno) {
		this.devno = devno;
	}
	/**
	 * 获得 当日领用张数
	 * @return the numly
	 */
	public String getNumly() {
		return numly;
	}
	/**
	 * 设置 当日领用张数 
	 * @param numly the numly to set
	 */
	public void setNumly(String numly) {
		this.numly = numly;
	}
	/**
	 * 获得 总领用张数
	 * @return the numlytotal
	 */
	public String getNumlytotal() {
		return numlytotal;
	}
	/**
	 * 设置 总领用张数 
	 * @param numlytotal the numlytotal to set
	 */
	public void setNumlytotal(String numlytotal) {
		this.numlytotal = numlytotal;
	}
	/**
	 * 获得 当日发卡张数
	 * @return the numout
	 */
	public String getNumout() {
		return numout;
	}
	/**
	 * 设置 当日发卡张数 
	 * @param numout the numout to set
	 */
	public void setNumout(String numout) {
		this.numout = numout;
	}
	/**
	 * 获得 总发卡张数
	 * @return the numouttotal
	 */
	public String getNumouttotal() {
		return numouttotal;
	}
	/**
	 * 设置 总发卡张数 
	 * @param numouttotal the numouttotal to set
	 */
	public void setNumouttotal(String numouttotal) {
		this.numouttotal = numouttotal;
	}
	/**
	 * 获得 现在剩余张数
	 * @return the numrest
	 */
	public String getNumrest() {
		return numrest;
	}
	/**
	 * 设置 现在剩余张数 
	 * @param numrest the numrest to set
	 */
	public void setNumrest(String numrest) {
		this.numrest = numrest;
	}
	/**
	 * 获得 机构号
	 * @return the organno
	 */
	public String getOrganno() {
		return organno;
	}
	/**
	 * 设置 机构号 
	 * @param organno the organno to set
	 */
	public void setOrganno(String organno) {
		this.organno = organno;
	}
	/**
	 * 获得remark1
	 * @return the remark1
	 */
	public String getRemark1() {
		return remark1;
	}
	/**
	 * 设置 remark1 
	 * @param remark1 the remark1 to set
	 */
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	/**
	 * 获得remark2
	 * @return the remark2
	 */
	public String getRemark2() {
		return remark2;
	}
	/**
	 * 设置 remark2 
	 * @param remark2 the remark2 to set
	 */
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	/**
	 * 获得 设备类型编号
	 * @return the typeno
	 */
	public String getTypeno() {
		return typeno;
	}
	/**
	 * 设置 设备类型编号 
	 * @param typeno the typeno to set
	 */
	public void setTypeno(String typeno) {
		this.typeno = typeno;
	}

	
	/**
	 * 
	 */
	public boolean equals(Object other) {
		if (this == other)
			return true;
 		if (!(other instanceof CardStatus))
 			return false;
	
 		CardStatus cardstatus = (CardStatus)other;
	
		if (!getDevno().equals(cardstatus.getDevno()))
			return false;
		if (!getCardtype().equals(cardstatus.getCardtype()))
			return false;
		if (!getTypeno().equals(cardstatus.getTypeno()))
			return false;
		if (!getOrganno().equals(cardstatus.getOrganno()))
			return false;
		if (!getNumlytotal().equals(cardstatus.getNumlytotal()))
			return false;
		if (!getNumouttotal().equals(cardstatus.getNumouttotal()))
			return false;
		if (!getNumly().equals(cardstatus.getNumly()))
			return false;
		if (!getNumout().equals(cardstatus.getNumout()))
			return false;
		if (!getNumrest().equals(cardstatus.getNumrest()))
			return false;
		if (!getRemark1().equals(cardstatus.getRemark1()))
			return false;
		if (!getRemark2().equals(cardstatus.getRemark2()))
			return false;
		return true;
	}
	

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getDevno())
			.append(getTypeno())
			.append(getCardtype())
			.append(getOrganno())
			.append(getNumlytotal())
			.append(getNumouttotal())
			.append(getNumly())
			.append(getNumout())
			.append(getNumrest())
			.append(getRemark1())
			.append(getRemark2())
			.toHashCode();
	}
	
	
}
