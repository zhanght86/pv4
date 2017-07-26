package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 终端重启时间修改ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2008</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2009/10/10
 */
public final class DevRebootForm extends ActionForm {
	private String[] devlist = new String[0];
	private String devtime = null;

	public String[] getDevlist() {
		return (this.devlist);
	}
	public String getDevtime() {
		return (this.devtime);
	}
	
	public void setDevlist(String[] devlist) {
		this.devlist = devlist;
	}
	public void setDevtime(String devtime) {
		this.devtime = devtime;
	}
	
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.devlist = new String[0];
		this.devtime = null;
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();

		return errors;
	}

}