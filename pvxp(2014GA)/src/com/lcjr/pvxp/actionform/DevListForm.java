package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

//import org.apache.log4j.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 设备列表ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 吴学坤
 * @version 1.0 2005/02/25
 */
public final class DevListForm extends ActionForm {
	//private Logger log = Logger.getLogger(DevListForm.class);

	private String page 		= null;
	private String pagesize 	= null;
	private String bankID		= null;
	private String devno		= null;
	private String devip		= null;
	private String tellerno 	= null;//柜员编号
	private boolean includeSubBank = false;
	
	public String getPage() {
		return (this.page);
	}
	
	public String getPagesize() {
		return (this.pagesize);
	}
	
	public String getBankID() {
		return this.bankID;
	}
	
	public String getDevno() {
		return this.devno;
	}
	
	public String getDevip() {
		return this.devip;
	}
	
	public boolean getIncludeSubBank() {
		return this.includeSubBank;
	}
	
	public void setPage(String page) {
		try {
			Integer.parseInt(page);
			this.page = page;
		} catch (NumberFormatException e) {
			this.page = "0";
		}
	}
	
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	
	public void setBankID(String bankID) {
		this.bankID = bankID;
	}
	
	public void setDevno(String devno) {
		this.devno = devno;
	}
	
	public void setDevip(String devip) {
		this.devip = devip;
	}
	
	public void setIncludeSubBank(boolean includeSubBank) {
		this.includeSubBank = includeSubBank;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.page = "1";
		this.pagesize = "10";
		this.bankID = null;
		includeSubBank = false;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		return errors;
	}
	
	public String getTellerno() {
		return tellerno;
	}

	public void setTellerno(String tellerno) {
		this.tellerno = tellerno;
	}
}
