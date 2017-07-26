package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 添加操作员ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/3/1
 */
public final class OperAddForm extends ActionForm {
	
	private String operid = null;
	private String opername = null;
	private String operstate = null;
	private String operpasswd = null;
	private String bankid = null;
	private String opertype = null;
	private String authlist = null;

	public String getOperid() {
		return (this.operid);
	}
	public String getOpername() {
		return (this.opername);
	}
	public String getOperstate() {
		return (this.operstate);
	}
	public String getOperpasswd() {
		return (this.operpasswd);
	}
	public String getBankid() {
		return (this.bankid);
	}
	public String getOpertype() {
		return (this.opertype);
	}
	public String getAuthlist() {
		return (this.authlist);
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
	public void setOperpasswd(String operpasswd) {
		this.operpasswd = operpasswd;
	}
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public void setOpertype(String opertype) {
		this.opertype = opertype;
	}
	public void setAuthlist(String authlist) {
		this.authlist = authlist;
	}

	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.operid = null;
		this.opername = null;
		this.operstate = null;
		this.operpasswd = null;
		this.bankid = null;
		this.opertype = null;
		this.authlist = null;
	}

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();

		if ((operid == null) || (operid.length() < 1)){
			errors.add("operid", new ActionMessage("pvxp.errors.form.no.operid"));
		}
		if ((opername == null) || (opername.length() < 1)){
			errors.add("opername", new ActionMessage("pvxp.errors.form.no.opername"));
		}
		if ((operpasswd == null) || (operpasswd.length() < 1)){
			errors.add("operpwd", new ActionMessage("pvxp.errors.form.no.operpwd"));
		}

		return errors;
	}
}