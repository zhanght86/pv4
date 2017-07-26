package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> �豸������ϸ��ϢActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author ����
 * @version 1.0 2004/12/31
 */
public final class DevTpDetailForm extends ActionForm {
	
	private String devTypeNo = null;
	
	public String getDevTypeNo() {
		return (this.devTypeNo);
	}

	public void setDevTypeNo(String devTypeNo) {
		this.devTypeNo = devTypeNo;
	}
 
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.devTypeNo = null;
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {	
		ActionErrors errors = new ActionErrors();
		return errors;
	}	
}