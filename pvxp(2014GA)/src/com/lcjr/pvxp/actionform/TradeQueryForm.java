package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

//import org.apache.log4j.*;

public final class TradeQueryForm extends ActionForm {

	//private Logger log = Logger.getLogger("wuxk");

	private String[] devno;
	private String[] devtrcd;
	private String[] moneytype;
	private String[] returnno;
	private String bankid;
	private String accno1;
	private String accno2;
	private String money1;
	private String money2;
	private String date1;
	private String date2;

	//getter
	public String[] getDevno() {
		return this.devno;
	}

	public String getBankid() {
		return this.bankid;
	}

	public String getAccno1() {
		return this.accno1;
	}

	public String getAccno2() {
		return this.accno2;
	}

	public String[] getDevtrcd() {
		return this.devtrcd;
	}

	public String[] getMoneytype() {
		return this.moneytype;
	}

	public String getMoney1() {
		return this.money1;
	}

	public String getMoney2() {
		return this.money2;
	}

	public String[] getReturnno() {
		return this.returnno;
	}

	public String getDate1() {
		return this.date1;
	}

	public String getDate2() {
		return this.date2;
	}

	//setter
	public void setDevno(String[] devno) {
		this.devno = devno;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	public void setAccno1(String accno1) {
		this.accno1 = accno1;
	}

	public void setAccno2(String accno2) {
		this.accno2 = accno2;
	}

	public void setDevtrcd(String[] devtrcd) {
		this.devtrcd = devtrcd;
	}

	public void setMoneytype(String[] moneytype) {
		this.moneytype = moneytype;
	}

	public void setMoney1(String money1) {
		this.money1 = money1;
	}

	public void setMoney2(String money2) {
		this.money2 = money2;
	}

	public void setReturnno(String[] returnno) {
		this.returnno = returnno;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}
}
