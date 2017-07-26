package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 更新类型删除ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2009</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2009/12/07
 */
public final class UpdateTypeDelForm extends ActionForm {
	
	private String[] updateTypeArry = new String[0];
	
	
	public String[] getUpdateTypeArry() {
		return (this.updateTypeArry);
	}

	public void setUpdateTypeArry(String[] updateTypeArry) {
		this.updateTypeArry = updateTypeArry;
	}
  
  
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.updateTypeArry=new String[0];
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();
			
		return errors;
	}
	
}