package com.lcjr.pvxp.orm;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.*;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 在hibernate中映射zzt_devstate表的类
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author 吴学坤
 * @version 1.0 2005/03/18
 */
public class EatCardLog implements Serializable {
	private String devno;
	
	
	private String edate;
	
	
	private String etime;
	
	
	private String flag;
	
	
	private String msg;
	
	
	private String accno;
	
	
	
	public String getDevno() {
		return this.devno;
	}
	
	
	public String getEdate() {
		return this.edate;
	}
	
	
	public String getEtime() {
		return this.etime;
	}
	
	
	public String getFlag() {
		return this.flag;
	}
	
	
	public String getMsg() {
		return this.msg;
	}
	
	
	public String getAccno() {
		return this.accno;
	}
	
	
	public void setDevno(String devno) {
		this.devno = devno;
	}
	
	
	public void setEdate(String edate) {
		this.edate = edate;
	}
	
	
	public void setEtime(String etime) {
		this.etime = etime;
	}
	
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	public void setAccno(String accno) {
		this.accno = accno;
	}
	
	
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof EatCardLog))
			return false;
		
		EatCardLog eatCardLog = (EatCardLog) other;
		
		if (!getDevno().equals(eatCardLog.getDevno()))
			return false;
		if (!getEdate().equals(eatCardLog.getEdate()))
			return false;
		if (!getEtime().equals(eatCardLog.getEtime()))
			return false;
		
		return true;
	}
	
	
	public int hashCode() {
		return new HashCodeBuilder().append(getDevno()).append(getEdate()).append(getEtime()).toHashCode();
	}
}
