package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 维修记录列表ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2011</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2011/02/23
 */
public final class MaintainListForm extends ActionForm {

	private String page 		= null;
	private String pagesize 	= null;
	private String devno		= null;
	private String date1		= null;
	private String date2		= null;
	
	public String getPage() {
		return (this.page);
	}
	
	public String getPagesize() {
		return (this.pagesize);
	}
	
	public String getDevno() {
		return this.devno;
	}
	
	public String getDate1() {
		return this.date1;
	}
	
	public String getDate2() {
		return this.date2;
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
	
	public void setDevno(String devno) {
		this.devno = devno;
	}
	
	public void setDate1(String date1) {
		this.date1 = date1;
	}
	
	public void setDate2(String date2) {
		this.date2 = date2;
	}
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.page = "1";
		this.pagesize = "10";
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		return errors;
	}
}
