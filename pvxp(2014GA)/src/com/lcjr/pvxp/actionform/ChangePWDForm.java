package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 操作员修改密码ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.0 2005/04/01
 */
public final class ChangePWDForm extends ActionForm {
	
	private String operpwd = null;
	private String newpwd1 = null;
	private String newpwd2 = null;
	

	public String getOperpwd() {
		return (this.operpwd);
	}
	public String getNewpwd1() {
		return (this.newpwd1);
	}	
	public String getNewpwd2() {
		return (this.newpwd2);
	}	
	

	public void setOperpwd(String operpwd) {
		this.operpwd = operpwd;
	}
	public void setNewpwd1(String newpwd1) {
		this.newpwd1 = newpwd1;
	}
	public void setNewpwd2(String newpwd2) {
		this.newpwd2 = newpwd2;
	}
  
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.operpwd = null;
		this.newpwd1 = null;
		this.newpwd2 = null;
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();
		
		if ((operpwd == null) || (operpwd.length() < 1)){
			errors.add("changepwd", new ActionMessage("pvxp.changepwd.errors.no.operpwd"));
			return errors;
		}
		if ((newpwd1 == null) || (newpwd1.length() < 1)){
			errors.add("changepwd", new ActionMessage("pvxp.changepwd.errors.no.newpwd"));
			return errors;
		}
		if ((newpwd2 == null) || (newpwd2.length() < 1)){
			errors.add("changepwd", new ActionMessage("pvxp.changepwd.errors.no.newpwd"));
			return errors;
		}
		if ( !newpwd1.equals(newpwd2) ){
			errors.add("changepwd", new ActionMessage("pvxp.changepwd.errors.newpwd.notmatch"));
			return errors;
		}
		return errors;
	}
	
}