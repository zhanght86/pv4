package com.lcjr.pvxp.orm;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.lcjr.pvxp.util.*;
import java.lang.Object;
import java.io.*;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 在hibernate中映射zzt_maintain表的类
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2011
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author xucc
 * @version 1.0 2011/02/23
 */
public class Maintain implements Serializable {
	private String devno;
	
	
	private String bankid;
	
	
	private String trbtype;
	
	
	private String trbdate;
	
	
	private String trbtime;
	
	
	private String state;
	
	
	private String repairs;
	
	
	private String trbphen;
	
	
	private String dutyno;
	
	
	private String obvidate;
	
	
	private String obvitime;
	
	
	private String remark;
	
	
	private String remark1;
	
	
	private String remark2;
	
	
	private String remark3;
	
	
	private String remark4;
	
	
	
	public void setDevno(String devno) {
		this.devno = devno;
	}
	
	
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	
	
	public void setTrbtype(String trbtype) {
		this.trbtype = trbtype;
	}
	
	
	public void setTrbdate(String trbdate) {
		this.trbdate = trbdate;
	}
	
	
	public void setTrbtime(String trbtime) {
		this.trbtime = trbtime;
	}
	
	
	public void setState(String state) {
		this.state = state;
	}
	
	
	public void setRepairs(String repairs) {
		this.repairs = repairs;
	}
	
	
	public void setTrbphen(String trbphen) {
		this.trbphen = trbphen;
	}
	
	
	public void setDutyno(String dutyno) {
		this.dutyno = dutyno;
	}
	
	
	public void setObvidate(String obvidate) {
		this.obvidate = obvidate;
	}
	
	
	public void setObvitime(String obvitime) {
		this.obvitime = obvitime;
	}
	
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	
	
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	
	
	public String getDevno() {
		return this.devno;
	}
	
	
	public String getBankid() {
		return this.bankid;
	}
	
	
	public String getTrbtype() {
		return this.trbtype;
	}
	
	
	public String getTrbdate() {
		return this.trbdate;
	}
	
	
	public String getTrbtime() {
		return this.trbtime;
	}
	
	
	public String getState() {
		return this.state;
	}
	
	
	public String getRepairs() {
		return this.repairs;
	}
	
	
	public String getTrbphen() {
		return this.trbphen;
	}
	
	
	public String getDutyno() {
		return this.dutyno;
	}
	
	
	public String getObvidate() {
		return this.obvidate;
	}
	
	
	public String getObvitime() {
		return this.obvitime;
	}
	
	
	public String getRemark() {
		return this.remark;
	}
	
	
	public String getRemark1() {
		return this.remark1;
	}
	
	
	public String getRemark2() {
		return this.remark2;
	}
	
	
	public String getRemark3() {
		return this.remark3;
	}
	
	
	public String getRemark4() {
		return this.remark4;
	}
	
	
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof Maintain))
			return false;
		
		Maintain maintain = (Maintain) other;
		
		if (!getDevno().equals(maintain.getDevno()))
			return false;
		if (!getTrbtype().equals(maintain.getTrbtype()))
			return false;
		if (!getTrbdate().equals(maintain.getTrbdate()))
			return false;
		
		return true;
	}
	
	
	public int hashCode() {
		return new HashCodeBuilder().append(getDevno()).append(getTrbtype()).append(getTrbdate()).toHashCode();
	}
	
}
