package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 设备地图列表ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.0 2005/03/19
 */
public final class DevMapListForm extends ActionForm {
	
	private String page = null;
	private String pagesize = null;
	public String getPage() {
		return (this.page);
	}
	public String getPagesize() {
		return (this.pagesize);
	}
	public void setPage(String page) {
		this.page = page;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.page = "1";
		this.pagesize = "12";
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();

		return errors;
	}
	
}