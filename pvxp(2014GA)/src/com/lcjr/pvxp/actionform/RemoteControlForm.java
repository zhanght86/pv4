package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 远程管理ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/3/14
 */
public final class RemoteControlForm extends ActionForm {

	private String[] devlist = new String[0];
	private String rc_operation = "";

	public String[] getDevlist() {
		return (this.devlist);
	}
	public String getRc_operation() {
		return (this.rc_operation);
	}

	public void setDevlist(String[] devlist) {
		this.devlist = devlist;
	}
	public void setRc_operation(String rc_operation) {
		this.rc_operation = rc_operation;
	}

	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.devlist = new String[0];
		this.rc_operation = "";
	}

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();

		return errors;
	}
	
}