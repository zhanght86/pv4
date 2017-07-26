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
 * <b>Description:</b> 在hibernate中映射zzt_invhistory表的类
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2006
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author
 * @version 1.0 2006/01/09
 */
public class InvoiceDistill implements Serializable {
	private String termnum;
	
	
	private String pdate;
	
	
	private String ptime;
	
	
	private String tellerno;
	
	
	private String firstnum;
	
	
	private String lastnum;
	
	
	private String applytoken;
	
	
	private String appynum;
	
	
	private String printtoken;
	
	
	private String remark1;
	
	
	private String remark2;
	
	
	private String remark3;
	
	
	
	public String getTermnum() {
		return this.termnum;
	}
	
	
	public String getPdate() {
		return this.pdate;
	}
	
	
	public String getPtime() {
		return this.ptime;
	}
	
	
	public String getTellerno() {
		return this.tellerno;
	}
	
	
	public String getFirstnum() {
		return this.firstnum;
	}
	
	
	public String getLastnum() {
		return this.lastnum;
	}
	
	
	public String getApplytoken() {
		return this.applytoken;
	}
	
	
	public String getAppynum() {
		return this.appynum;
	}
	
	
	public String getPrinttoken() {
		return this.printtoken;
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
	
	
	public void setTermnum(String termnum) {
		this.termnum = termnum;
	}
	
	
	public void setPdate(String pdate) {
		this.pdate = pdate;
	}
	
	
	public void setPtime(String ptime) {
		this.ptime = ptime;
	}
	
	
	public void setTellerno(String tellerno) {
		this.tellerno = tellerno;
	}
	
	
	public void setFirstnum(String firstnum) {
		this.firstnum = firstnum;
	}
	
	
	public void setLastnum(String lastnum) {
		this.lastnum = lastnum;
	}
	
	
	public void setApplytoken(String applytoken) {
		this.applytoken = applytoken;
	}
	
	
	public void setAppynum(String appynum) {
		this.appynum = appynum;
	}
	
	
	public void setPrinttoken(String printtoken) {
		this.printtoken = printtoken;
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
	
	
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof InvoiceDistill))
			return false;
		
		InvoiceDistill InvoiceDistill = (InvoiceDistill) other;
		
		if (!getTermnum().equals(InvoiceDistill.getTermnum()))
			return false;
		if (!getPdate().equals(InvoiceDistill.getPdate()))
			return false;
		if (!getPtime().equals(InvoiceDistill.getPtime()))
			return false;
		
		return true;
	}
	
	
	public int hashCode() {
		return new HashCodeBuilder().append(getTermnum()).append(getPdate()).append(getPtime()).toHashCode();
	}
}
