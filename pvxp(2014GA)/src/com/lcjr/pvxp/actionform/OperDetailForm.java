package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> ����Ա��ϸ��ϢActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author ��̫��
 * @version 1.0 2005/2/25
 */
public final class OperDetailForm extends ActionForm {
	
	private String operid = null;
	public String getOperid() {
		return (this.operid);
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}
 
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.operid = null;
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();

		return errors;
	}
	
}