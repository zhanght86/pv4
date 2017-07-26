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
 * <b>Description:</b> 在hibernate中映射zzt_trcdlog表的类
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author 吴学坤
 * @version 1.0 2005/03/24
 */
public class TradeLog implements Serializable {
	private String serverwater;
	
	
	private String devwater;
	
	
	private String devno;
	
	
	private String devip;
	
	
	private String devdate;
	
	
	private String devtime;
	
	
	private String devtrcd;
	
	
	private String servertrcd;
	
	
	private String returnno;
	
	
	private String errmsg;
	
	
	private String accno1;
	
	
	private String accno2;
	
	
	private String money1;
	
	
	private String moneytype1;
	
	
	private String value1;
	
	
	private String money2;
	
	
	private String moneytype2;
	
	
	private String value2;
	
	
	private String other1;
	
	
	private String other2;
	
	
	private String other3;
	
	
	private String other4;
	
	
	
	
	// getter
	public String getServerwater() {
		return this.serverwater;
	}
	
	
	public String getDevwater() {
		return this.devwater;
	}
	
	
	public String getDevno() {
		return this.devno;
	}
	
	
	public String getDevip() {
		return this.devip;
	}
	
	
	public String getDevdate() {
		return this.devdate;
	}
	
	
	public String getDevtime() {
		return this.devtime;
	}
	
	
	public String getDevtrcd() {
		return this.devtrcd;
	}
	
	
	public String getServertrcd() {
		return this.servertrcd;
	}
	
	
	public String getReturnno() {
		return this.returnno;
	}
	
	
	public String getErrmsg() {
		return this.errmsg;
	}
	
	
	public String getAccno1() {
		return this.accno1;
	}
	
	
	public String getAccno2() {
		return this.accno2;
	}
	
	
	public String getMoney1() {
		return this.money1;
	}
	
	
	public String getMoneytype1() {
		return this.moneytype1;
	}
	
	
	public String getValue1() {
		return this.value1;
	}
	
	
	public String getMoney2() {
		return this.money2;
	}
	
	
	public String getMoneytype2() {
		return this.moneytype2;
	}
	
	
	public String getValue2() {
		return this.value2;
	}
	
	
	public String getOther1() {
		return this.other1;
	}
	
	
	public String getOther2() {
		return this.other2;
	}
	
	
	public String getOther3() {
		return this.other3;
	}
	
	
	public String getOther4() {
		return this.other4;
	}
	
	
	
	// setter
	public void setServerwater(String serverwater) {
		this.serverwater = serverwater;
	}
	
	
	public void setDevwater(String devwater) {
		this.devwater = devwater;
	}
	
	
	public void setDevno(String devno) {
		this.devno = devno;
	}
	
	
	public void setDevip(String devip) {
		this.devip = devip;
	}
	
	
	public void setDevdate(String devdate) {
		this.devdate = devdate;
	}
	
	
	public void setDevtime(String devtime) {
		this.devtime = devtime;
	}
	
	
	public void setDevtrcd(String devtrcd) {
		this.devtrcd = devtrcd;
	}
	
	
	public void setServertrcd(String servertrcd) {
		this.servertrcd = servertrcd;
	}
	
	
	public void setReturnno(String returnno) {
		this.returnno = returnno;
	}
	
	
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	
	public void setAccno1(String accno1) {
		this.accno1 = accno1;
	}
	
	
	public void setAccno2(String accno2) {
		this.accno2 = accno2;
	}
	
	
	public void setMoney1(String money1) {
		this.money1 = money1;
	}
	
	
	public void setMoneytype1(String moneytype1) {
		this.moneytype1 = moneytype1;
	}
	
	
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	
	
	public void setMoney2(String money2) {
		this.money2 = money2;
	}
	
	
	public void setMoneytype2(String moneytype2) {
		this.moneytype2 = moneytype2;
	}
	
	
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	
	
	public void setOther1(String other1) {
		this.other1 = other1;
	}
	
	
	public void setOther2(String other2) {
		this.other2 = other2;
	}
	
	
	public void setOther3(String other3) {
		this.other3 = other3;
	}
	
	
	public void setOther4(String other4) {
		this.other4 = other4;
	}
	
	
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof TradeLog))
			return false;
		
		TradeLog tradeLog = (TradeLog) other;
		
		if (!getServerwater().equals(tradeLog.getServerwater()))
			return false;
		if (!getDevno().equals(tradeLog.getDevno()))
			return false;
		if (!getDevtrcd().equals(tradeLog.getDevtrcd()))
			return false;
		
		return true;
	}
	
	
	public int hashCode() {
		return new HashCodeBuilder().append(getServerwater()).append(getDevno()).append(getDevtrcd()).toHashCode();
	}
}
