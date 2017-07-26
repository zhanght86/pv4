package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 设备类型删除ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.0 2004/12/31
 */
public final class DevTpDelForm extends ActionForm {
	
	private String[] devtpArry = new String[0];
	
	
	public String[] getDevtpArry() {
		return (this.devtpArry);
	}

	public void setDevtpArry(String[] devtpArry) {
		this.devtpArry = devtpArry;
	}
  
  
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.devtpArry=new String[0];
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();
			
		return errors;
	}
	
}