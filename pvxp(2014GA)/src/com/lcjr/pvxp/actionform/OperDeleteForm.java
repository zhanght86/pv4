package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 操作员删除ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/2/28
 */
public final class OperDeleteForm extends ActionForm {

	private String[] operArry = new String[0];

	public String[] getOperArry() {
		return (this.operArry);
	}

	public void setOperArry(String[] operArry) {
		this.operArry = operArry;
	}

	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.operArry=new String[0];
	}

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();

		return errors;
	}
	
}