package com.lcjr.pvxp.orm;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 在hibernate中映射zzt_invstate表的类
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2012
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author 武坤鹏
 * @version 1.0 2012/06/01（儿童节）
 */
public class InvState implements Serializable {
	
	
	/**
	 * 终端号（自助终端号码）
	 */
	private String devno;
	
	
	
	/**
	 * 柜员号
	 */
	private String tellerno;
	
	
	
	/**
	 * 发票领用日期
	 */
	private String pdate;
	
	
	
	/**
	 * 发票领用时间
	 */
	private String ptime;
	
	
	
	/**
	 * 起始发票号
	 */
	private String firstnum;
	
	
	
	/**
	 * 终止发票号
	 */
	private String lastnum;
	
	
	
	/**
	 * 当前发票号
	 */
	private String currentnum;
	
	
	
	/**
	 * 作废发票张数
	 */
	private String wastenum;
	
	
	
	/**
	 * 是否可用（0，不可用，1，可打印测试页，2，可以打印正常发票
	 */
	private String ifvaluable;
	
	
	
	/**
	 * 备注1
	 */
	private String remark1;
	
	
	
	/**
	 * 备注2
	 */
	private String remark2;
	
	
	
	/**
	 * 备注3
	 */
	private String remark3;
	
	
	
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof InvState))
			return false;
		
		InvState invState = (InvState) other;
		
		if (!getDevno().equals(invState.getDevno()))
			return false;
		
		return true;
	}
	
	
	public int hashCode() {
		return new HashCodeBuilder().append(getDevno()).toHashCode();
	}
	
	
	
	/**
	 * @return the currentnum
	 */
	public String getCurrentnum() {
		return currentnum;
	}
	
	
	
	/**
	 * @param currentnum
	 *            the currentnum to set
	 */
	public void setCurrentnum(String currentnum) {
		this.currentnum = currentnum;
	}
	
	
	
	/**
	 * @return the devno
	 */
	public String getDevno() {
		return devno;
	}
	
	
	
	/**
	 * @param devno
	 *            the devno to set
	 */
	public void setDevno(String devno) {
		this.devno = devno;
	}
	
	
	
	/**
	 * @return the firstnum
	 */
	public String getFirstnum() {
		return firstnum;
	}
	
	
	
	/**
	 * @param firstnum
	 *            the firstnum to set
	 */
	public void setFirstnum(String firstnum) {
		this.firstnum = firstnum;
	}
	
	
	
	/**
	 * @return the ifvaluable
	 */
	public String getIfvaluable() {
		return ifvaluable;
	}
	
	
	
	/**
	 * @param ifvaluable
	 *            the ifvaluable to set
	 */
	public void setIfvaluable(String ifvaluable) {
		this.ifvaluable = ifvaluable;
	}
	
	
	
	/**
	 * @return the lastnum
	 */
	public String getLastnum() {
		return lastnum;
	}
	
	
	
	/**
	 * @param lastnum
	 *            the lastnum to set
	 */
	public void setLastnum(String lastnum) {
		this.lastnum = lastnum;
	}
	
	
	
	/**
	 * @return the pdate
	 */
	public String getPdate() {
		return pdate;
	}
	
	
	
	/**
	 * @param pdate
	 *            the pdate to set
	 */
	public void setPdate(String pdate) {
		this.pdate = pdate;
	}
	
	
	
	/**
	 * @return the ptime
	 */
	public String getPtime() {
		return ptime;
	}
	
	
	
	/**
	 * @param ptime
	 *            the ptime to set
	 */
	public void setPtime(String ptime) {
		this.ptime = ptime;
	}
	
	
	
	/**
	 * @return the remark1
	 */
	public String getRemark1() {
		return remark1;
	}
	
	
	
	/**
	 * @param remark1
	 *            the remark1 to set
	 */
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	
	
	/**
	 * @return the remark2
	 */
	public String getRemark2() {
		return remark2;
	}
	
	
	
	/**
	 * @param remark2
	 *            the remark2 to set
	 */
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	
	
	/**
	 * @return the remark3
	 */
	public String getRemark3() {
		return remark3;
	}
	
	
	
	/**
	 * @param remark3
	 *            the remark3 to set
	 */
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	
	
	
	/**
	 * @return the tellerno
	 */
	public String getTellerno() {
		return tellerno;
	}
	
	
	
	/**
	 * @param tellerno
	 *            the tellerno to set
	 */
	public void setTellerno(String tellerno) {
		this.tellerno = tellerno;
	}
	
	
	
	/**
	 * @return the wastenum
	 */
	public String getWastenum() {
		return wastenum;
	}
	
	
	
	/**
	 * @param wastenum
	 *            the wastenum to set
	 */
	public void setWastenum(String wastenum) {
		this.wastenum = wastenum;
	}
}
