package com.lcjr.pvxp.actionform;
import java.io.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 发票状态查询ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2006</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 
 * @version 1.0 2006/01/13
 */
public final class InvStateForm extends ActionForm {
	
	private String[] devno;
	private String bankID;

	public String[] getDevno() {
		return this.devno;
	}
	
	public String getBankID() {
		return this.bankID;
	}

	public void setDevno(String[] devno) {
		this.devno = devno;
	}

	public void setBankID(String bankID) {
		this.bankID = bankID;
	}
	

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		return errors;
	}
}