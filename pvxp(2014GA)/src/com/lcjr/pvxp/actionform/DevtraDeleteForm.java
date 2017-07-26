package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class DevtraDeleteForm extends ActionForm {
	/**
	 * Éè±¸Ãû³Æ
	 */
	String devname;

	/**
	 * @return the devname
	 */
	public String getDevname() {
		return devname;
	}

	/**
	 * @param devname the devname to set
	 */
	public void setDevname(String devname) {
		this.devname = devname;
	}
	
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.devname="";
	}

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
		
		ActionErrors errors = new ActionErrors();
		if ((devname == null) || (devname.length() < 1))
		{
			errors.add("bankname", new ActionMessage("pvxp.errors.form.no.bankname"));
		}
		return errors;
	}
	
}
