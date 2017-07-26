package com.lcjr.pvxp.orm;

import com.lcjr.pvxp.util.CharSet;
import java.lang.Object;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.*;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 在hibernate中映射zzt_devtjday表的类
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author 刘太沛
 * @version 1.0 2005/4/5
 */
public class Devtjday implements Serializable {
	private String devno;
	
	private String stateno;
	
	private String devdate;
	
	private String times;
	
	private String timelen;
	
	
	
	public void setDevno(String mdevno) {
		devno = mdevno;
	}
	
	public void setStateno(String mstateno) {
		stateno = mstateno;
	}
	
	public void setDevdate(String mdevdate) {
		devdate = mdevdate;
	}
	
	public void setTimes(String mtimes) {
		times = mtimes;
	}
	
	public void setTimelen(String mtimelen) {
		timelen = mtimelen;
	}
	
	
	public String getDevno() {
		return devno;
	}
	
	public String getStateno() {
		return stateno;
	}
	
	public String getDevdate() {
		return devdate;
	}
	
	public String getTimes() {
		return times;
	}
	
	public String getTimelen() {
		return timelen;
	}
	
	
	public boolean equals(Object other) {
		if (!(other instanceof Devtjday))
			return false;
		Devtjday castOther = (Devtjday) other;
		return new EqualsBuilder().append(this.getDevno(), castOther.getDevno()).append(this.getStateno(), castOther.getStateno()).append(this.getDevdate(), castOther.getDevdate()).append(
				this.getTimes(), castOther.getTimes()).append(this.getTimelen(), castOther.getTimelen()).isEquals();
	}
	
	
	public int hashCode() {
		return new HashCodeBuilder().append(getDevno()).append(getStateno()).append(getDevdate()).append(getTimes()).append(getTimelen()).toHashCode();
	}
	
}
