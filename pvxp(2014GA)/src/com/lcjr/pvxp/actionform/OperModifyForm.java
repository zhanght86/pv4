package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 操作员修改信息ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/2/28
 */
public final class OperModifyForm extends ActionForm {

	private String operid = null;
	private String opername = null;
	private String operstate = null;
	private String opertype = null;
	private String authlist = null;
	private String operpasswd = null;

	public String getOperid() {
		return (this.operid);
	}
	public String getOpername() {
		return (this.opername);
	}
	public String getOperstate() {
		return (this.operstate);
	}
	public String getOpertype() {
		return (this.opertype);
	}
	public String getAuthlist() {
		return (this.authlist);
	}
	public String getOperpasswd() {
		return (this.operpasswd);
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}
	public void setOpername(String opername) {
		this.opername = opername;
	}
	public void setOperstate(String operstate) {
		this.operstate = operstate;
	}
	public void setOpertype(String opertype) {
		this.opertype = opertype;
	}
	public void setAuthlist(String authlist) {
		this.authlist = authlist;
	}
	public void setOperpasswd(String operpasswd) {
		this.operpasswd = operpasswd;
	}

	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.operid = null;
		this.opername = null;
		this.operstate = null;
		this.opertype = null;
		this.authlist = null;
		this.operpasswd = null;
	}

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();

		if ((opername == null) || (opername.length() < 1))
		{
			errors.add("opername", new ActionMessage("pvxp.errors.form.no.opername"));
		}

		return errors;
	}

}