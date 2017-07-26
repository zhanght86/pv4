package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 添加机构ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/3/2
 */
public final class BankinfoAddForm extends ActionForm {
	
	private String banklevel = null;
	private String parent_bankid = null;
	private String bankname = null;
	private String bankaddr = null;
	private String banktel = null;
	private String bankstate = null;

	public String getBanklevel() {
		return (this.banklevel);
	}
	public String getParent_bankid() {
		return (this.parent_bankid);
	}
	public String getBankname() {
		return (this.bankname);
	}
	public String getbankaddr() {
		return (this.bankaddr);
	}
	public String getBanktel() {
		return (this.banktel);
	}
	public String getBankstate() {
		return (this.bankstate);
	}

	public void setBanklevel(String banklevel) {
		this.banklevel = banklevel;
	}
	public void setParent_bankid(String parent_bankid) {
		this.parent_bankid = parent_bankid;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public void setBankaddr(String bankaddr) {
		this.bankaddr = bankaddr;
	}
	public void setBanktel(String banktel) {
		this.banktel = banktel;
	}
	public void setBankstate(String bankstate) {
		this.bankstate = bankstate;
	}

	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.banklevel = null;
		this.parent_bankid = null;
		this.bankname = null;
		this.bankaddr = null;
		this.banktel = null;
		this.bankstate = null;
	}

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();

		if ((bankname == null) || (bankname.length() < 1))
		{
			errors.add("bankname", new ActionMessage("pvxp.errors.form.no.bankname"));
		}
		if ((bankaddr == null) || (bankaddr.length() < 1))
		{
			errors.add("bankaddr", new ActionMessage("pvxp.errors.form.no.bankaddr"));
		}
		if ((banktel == null) || (banktel.length() < 1))
		{
			errors.add("banktel", new ActionMessage("pvxp.errors.form.no.banktel"));
		}

		return errors;
	}
}