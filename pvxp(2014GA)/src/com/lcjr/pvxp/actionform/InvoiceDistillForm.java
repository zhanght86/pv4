package com.lcjr.pvxp.actionform;
import java.io.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 发票领用查询ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2006</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 
 * @version 1.0 2006/01/09
 */
public final class InvoiceDistillForm extends ActionForm {
	
	private String date1;
	private String date2;
	private String[] termnum;
	private String bankID;

	public String getDate1() {
		return this.date1;
	}

	public String getDate2() {
		return this.date2;
	}

	public String[] getTermnum() {
		return this.termnum;
	}
	
	public String getBankID() {
		return this.bankID;
	}

	
	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	public void setTermnum(String[] termnum) {
		this.termnum = termnum;
	}

	public void setBankID(String bankID) {
		this.bankID = bankID;
	}
	

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		return errors;
	}
}