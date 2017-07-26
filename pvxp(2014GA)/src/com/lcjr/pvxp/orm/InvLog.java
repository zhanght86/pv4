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
 * <b>Description:</b> 在hibernate中映射zzt_invlog表的类
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2006
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author
 * @version 1.0 2006/01/14
 */
public class InvLog implements Serializable {
	private String devno;
	
	
	private String pdate;
	
	
	private String ptime;
	
	
	private String tellerno;
	
	
	private String invoiceno;
	
	
	private String type;
	
	
	private String accno1;
	
	
	private String accno2;
	
	
	private String money;
	
	
	private String printresult;
	
	
	private String printtoken;
	
	
	private String remark1;
	
	
	private String remark2;
	
	
	private String remark3;
	
	
	
	public String getDevno() {
		return this.devno;
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
	
	
	public String getInvoiceno() {
		return this.invoiceno;
	}
	
	
	public String getType() {
		return this.type;
	}
	
	
	public String getAccno1() {
		return this.accno1;
	}
	
	
	public String getAccno2() {
		return this.accno2;
	}
	
	
	public String getMoney() {
		return this.money;
	}
	
	
	public String getPrintresult() {
		return this.printresult;
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
	
	
	public void setDevno(String devno) {
		this.devno = devno;
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
	
	
	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}
	
	
	public void setType(String type) {
		this.type = type;
	}
	
	
	public void setAccno1(String accno1) {
		this.accno1 = accno1;
	}
	
	
	public void setAccno2(String accno2) {
		this.accno2 = accno2;
	}
	
	
	public void setMoney(String money) {
		this.money = money;
	}
	
	
	public void setPrintresult(String printresult) {
		this.printresult = printresult;
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
		if (!(other instanceof InvLog))
			return false;
		
		InvLog invLog = (InvLog) other;
		
		if (!getDevno().equals(invLog.getDevno()))
			return false;
		if (!getPdate().equals(invLog.getPdate()))
			return false;
		if (!getPtime().equals(invLog.getPtime()))
			return false;
		
		return true;
	}
	
	
	public int hashCode() {
		return new HashCodeBuilder().append(getDevno()).append(getPdate()).append(getPtime()).toHashCode();
	}
}
