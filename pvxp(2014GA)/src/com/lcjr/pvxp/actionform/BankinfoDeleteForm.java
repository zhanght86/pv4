package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 机构删除ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/3/2
 */
public final class BankinfoDeleteForm extends ActionForm {

	private String[] bankinfoArry = new String[0];

	public String[] getBankinfoArry() {
		return (this.bankinfoArry);
	}

	public void setBankinfoArry(String[] bankinfoArry) {
		this.bankinfoArry = bankinfoArry;
	}

	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.bankinfoArry=new String[0];
	}

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();

		return errors;
	}
	
}