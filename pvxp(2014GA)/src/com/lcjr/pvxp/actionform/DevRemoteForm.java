package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 终端重启标志修改ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2010</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2010/01/20
 */
public final class DevRemoteForm extends ActionForm {
	private String[] devlist = new String[0];
	private String devopt = null;

	public String[] getDevlist() {
		return (this.devlist);
	}
	public String getDevopt() {
		return (this.devopt);
	}
	
	public void setDevlist(String[] devlist) {
		this.devlist = devlist;
	}
	public void setDevopt(String devopt) {
		this.devopt = devopt;
	}
	
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.devlist = new String[0];
		this.devopt = null;
	}
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();

		return errors;
	}


}