package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> ������ϸ��ϢActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author ��̫��
 * @version 1.0 2005/3/2
 */
public final class BankinfoDetailForm extends ActionForm {

	private String bankid = null;

	public String getBankid() {
		return (this.bankid);
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.bankid = null;
	}

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();

		return errors;
	}
}