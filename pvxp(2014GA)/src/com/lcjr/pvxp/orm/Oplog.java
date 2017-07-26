package com.lcjr.pvxp.orm;

import java.io.Serializable;
import java.lang.Object;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 在hibernate中映射zzt_oplog表的类
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
public class Oplog implements Serializable {
	/**
	 * 所属机构
	 */
	private String bankid;
	
	
	
	/**
	 * 操作员编号
	 */
	private String operid;
	
	
	
	/**
	 * 操作日期
	 */
	private String opdate;
	
	
	
	/**
	 * 操作时间
	 */
	private String optime;
	
	
	
	/**
	 * 操作类型 0：修改 1：删除 2：添加
	 * 
	 */
	private String type;
	
	
	
	/**
	 * 操作编码
	 */
	private String trcd;
	
	
	
	/**
	 * 操作描述
	 */
	private String info;
	
	
	
	/**
	 * 备注1
	 */
	private String remark1;
	
	
	
	/**
	 * 备注2
	 */
	private String remark2;
	
	
	
	
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
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}
	
	
	
	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	
	
	
	/**
	 * @return the opdate
	 */
	public String getOpdate() {
		return opdate;
	}
	
	
	
	/**
	 * @param opdate
	 *            the opdate to set
	 */
	public void setOpdate(String opdate) {
		this.opdate = opdate;
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
	 * @return the optime
	 */
	public String getOptime() {
		return optime;
	}
	
	
	
	/**
	 * @param optime
	 *            the optime to set
	 */
	public void setOptime(String optime) {
		this.optime = optime;
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
	 * @return the trcd
	 */
	public String getTrcd() {
		return trcd;
	}
	
	
	
	/**
	 * @param trcd
	 *            the trcd to set
	 */
	public void setTrcd(String trcd) {
		this.trcd = trcd;
	}
	
	
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	
	
	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 
	 */
	public boolean equals(Object other) {
		if (!(other instanceof Oplog))
			return false;
		Oplog castOther = (Oplog) other;
		return new EqualsBuilder().append(this.getBankid(), castOther.getBankid()).append(this.getOperid(), castOther.getOperid()).append(this.getOpdate(), castOther.getOpdate()).append(
				this.getOptime(), castOther.getOptime()).append(this.getType(), castOther.getType()).append(this.getTrcd(), castOther.getTrcd()).append(this.getInfo(), castOther.getInfo()).isEquals();
	}
	
	/**
	 * 
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(getBankid()).append(getOperid()).append(getOpdate()).append(getOptime()).append(getType()).append(getTrcd()).append(getInfo()).toHashCode();
	}
	
}
