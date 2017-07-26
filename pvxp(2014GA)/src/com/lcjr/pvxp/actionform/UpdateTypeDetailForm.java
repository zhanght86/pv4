package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 更新类型详细信息ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2009</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2009/12/07
 */
public final class UpdateTypeDetailForm extends ActionForm {
	
	private String updateno = null;
	
	public String getUpdateno() {
		return (this.updateno);
	}

	public void setUpdateno(String updateno) {
		this.updateno = updateno;
	}
 
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.updateno = null;
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {	
		ActionErrors errors = new ActionErrors();
		return errors;
	}	
}