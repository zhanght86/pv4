package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> �豸��ϸ��ϢActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author ��ѧ��
 * @version 1.0 2005/03/16
 */
public final class DevDetailForm extends ActionForm {

	private String devNo = null;

	public String getDevNo() {
		return (this.devNo);
	}

	public void setDevNo(String devNo) {
		this.devNo = devNo;
	}

	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.devNo = null;
	}

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		return errors;
	}
}