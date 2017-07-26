package com.lcjr.pvxp.actionform;
import java.io.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 发票明细查询ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2006</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 
 * @version 1.0 2006/01/14
 */
public final class InvLogForm extends ActionForm {
	
	private String date1;
	private String date2;
	private String[] devno;
	private String bankID;
	private String invno1;
	private String invno2;
	private String accno;

	public String getDate1() {
		return this.date1;
	}

	public String getDate2() {
		return this.date2;
	}

	public String[] getDevno() {
		return this.devno;
	}
	
	public String getBankID() {
		return this.bankID;
	}
	
	public String getInvno1() {
		return this.invno1;
	}
	
	public String getInvno2() {
		return this.invno2;
	}
	
	public String getAccno() {
		return this.accno;
	}

	
	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	public void setDevno(String[] devno) {
		this.devno = devno;
	}

	public void setBankID(String bankID) {
		this.bankID = bankID;
	}
	
	public void setInvno1(String invno1) {
		this.invno1 = invno1;
	}
	
	public void setInvno2(String invno2) {
		this.invno2 = invno2;
	}
	
	public void setAccno(String accno) {
		this.accno = accno;
	}

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		return errors;
	}
}