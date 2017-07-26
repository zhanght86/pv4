package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 远程维护ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2010</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2010/10/09
 */
public final class DevMaintForm extends ActionForm {
	private String[] devlist = new String[0];
	private String maintflag = null;

	public String[] getDevlist() {
		return (this.devlist);
	}
	public String getMaintflag() {
		return (this.maintflag);
	}
	
	public void setDevlist(String[] devlist) {
		this.devlist = devlist;
	}
	public void setMaintflag(String maintflag) {
		this.maintflag = maintflag;
	}
	
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.devlist = new String[0];
		this.maintflag = null;
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();

		return errors;
	}

}