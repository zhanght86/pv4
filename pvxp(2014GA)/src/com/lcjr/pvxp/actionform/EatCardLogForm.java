package com.lcjr.pvxp.actionform;
import java.io.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 吞卡记录查询ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 吴学坤
 * @version 1.0 2005/03/20
 */
public final class EatCardLogForm extends ActionForm {
	private String cardNo;
	private String date1;
	private String date2;
	private String[] devNo;
	private String bankID;
	private String flag;

	public String getCardNo() {
		return this.cardNo;
	}

	public String getDate1() {
		return this.date1;
	}

	public String getDate2() {
		return this.date2;
	}

	public String[] getDevNo() {
		return this.devNo;
	}

	public String getBankID() {
		return this.bankID;
	}
	
	public String getFlag() {
		return this.flag;
	}
	
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	public void setDevNo(String[] devNo) {
		this.devNo = devNo;
	}

	public void setBankID(String bankID) {
		this.bankID = bankID;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		return errors;
	}
}