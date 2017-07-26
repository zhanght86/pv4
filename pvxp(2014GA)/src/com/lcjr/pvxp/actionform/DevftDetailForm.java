package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 设备厂商详细信息ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2010</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2010/09/26
 */
public final class DevftDetailForm extends ActionForm {
	
	private String devftNo = null;
	
	public String getDevftNo() {
		return (this.devftNo);
	}

	public void setDevftNo(String devftNo) {
		this.devftNo = devftNo;
	}
 
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.devftNo = null;
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {	
		ActionErrors errors = new ActionErrors();
		return errors;
	}	
}