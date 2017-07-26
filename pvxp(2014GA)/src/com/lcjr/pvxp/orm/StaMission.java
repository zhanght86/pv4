package com.lcjr.pvxp.orm;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 在hibernate中映射zzt_stamission表的类
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
public class StaMission implements Serializable {
	/**
	 * 统计开始时间
	 */
	private String timeid;
	
	
	
	/**
	 * 报表名称
	 */
	private String statename;
	
	
	
	/**
	 * 所属机构
	 */
	private String bankid;
	
	
	
	/**
	 * 操作员编号
	 */
	private String ownerid;
	
	
	
	/**
	 * 报表分类
	 */
	private String statesort;
	
	
	
	/**
	 * 报表产生类型
	 */
	private String createtype;
	
	
	
	/**
	 * 任务当前状态
	 */
	private String currentflag;
	
	
	
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
	 * @return the createtype
	 */
	public String getCreatetype() {
		return createtype;
	}
	
	
	
	/**
	 * @param createtype
	 *            the createtype to set
	 */
	public void setCreatetype(String createtype) {
		this.createtype = createtype;
	}
	
	
	
	/**
	 * @return the currentflag
	 */
	public String getCurrentflag() {
		return currentflag;
	}
	
	
	
	/**
	 * @param currentflag
	 *            the currentflag to set
	 */
	public void setCurrentflag(String currentflag) {
		this.currentflag = currentflag;
	}
	
	
	
	/**
	 * @return the ownerid
	 */
	public String getOwnerid() {
		return ownerid;
	}
	
	
	
	/**
	 * @param ownerid
	 *            the ownerid to set
	 */
	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
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
	 * @return the statename
	 */
	public String getStatename() {
		return statename;
	}
	
	
	
	/**
	 * @param statename
	 *            the statename to set
	 */
	public void setStatename(String statename) {
		this.statename = statename;
	}
	
	
	
	/**
	 * @return the statesort
	 */
	public String getStatesort() {
		return statesort;
	}
	
	
	
	/**
	 * @param statesort
	 *            the statesort to set
	 */
	public void setStatesort(String statesort) {
		this.statesort = statesort;
	}
	
	
	
	/**
	 * @return the timeid
	 */
	public String getTimeid() {
		return timeid;
	}
	
	
	
	/**
	 * @param timeid
	 *            the timeid to set
	 */
	public void setTimeid(String timeid) {
		this.timeid = timeid;
	}
	
	
	public boolean equals(Object other) {
		if (!(other instanceof StaMission))
			return false;
		StaMission castOther = (StaMission) other;
		return new EqualsBuilder().append(this.getTimeid(), castOther.getTimeid()).append(this.getOwnerid(), castOther.getOwnerid()).append(this.getStatesort(), castOther.getStatesort()).append(
				this.getCreatetype(), castOther.getCreatetype()).isEquals();
	}
	
	
	public int hashCode() {
		return new HashCodeBuilder().append(getTimeid()).append(getOwnerid()).append(getStatesort()).append(getCreatetype()).toHashCode();
	}
	
}
