package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 设备厂商删除ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2010</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2010/09/26
 */
public final class DevftDelForm extends ActionForm {
	
	private String[] devftArry = new String[0];
	
	
	public String[] getDevftArry() {
		return (this.devftArry);
	}

	public void setDevftArry(String[] devftArry) {
		this.devftArry = devftArry;
	}
  
  
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.devftArry=new String[0];
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();
			
		return errors;
	}
	
}