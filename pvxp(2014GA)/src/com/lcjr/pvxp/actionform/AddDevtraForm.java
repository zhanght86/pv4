package com.lcjr.pvxp.actionform;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class AddDevtraForm extends ActionForm{
	private String devno;
	private String devname;
	private String addr;
	private String tranum;
	private String mainpeople;
	private String phone;
	
	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

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



	/**
	 * @return the mainpeople
	 */
	public String getMainpeople() {
		return mainpeople;
	}

	/**
	 * @param mainpeople the mainpeople to set
	 */
	public void setMainpeople(String mainpeople) {
		this.mainpeople = mainpeople;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the tranum
	 */
	public String getTranum() {
		return tranum;
	}

	/**
	 * @param tranum the tranum to set
	 */
	public void setTranum(String tranum) {
		this.tranum = tranum;
	}

	/**
	 * Втбщ
	 */
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	  	
		ActionErrors errors = new ActionErrors();
		
		
		if ((devno == null) || (devno.length() < 1)){
			errors.add("bankname", new ActionMessage("pvxp.errors.form.no.bankname"));
		}
		
		if ((devname == null) || (devname.length() < 1)){
			errors.add("bankname", new ActionMessage("pvxp.errors.form.no.bankname"));
		}
		if ((phone == null) || (phone.length() < 1)){
			errors.add("bankaddr", new ActionMessage("pvxp.errors.form.no.bankaddr"));
		}
		if ((tranum == null) || (tranum.length() < 1)){
			errors.add("banktel", new ActionMessage("pvxp.errors.form.no.banktel"));
		}
		if ((mainpeople == null) || (mainpeople.length() < 1)){
			errors.add("banktel", new ActionMessage("pvxp.errors.form.no.banktel"));
		}
		if ((addr == null) || (addr.length() < 1)){
			errors.add("banktel", new ActionMessage("pvxp.errors.form.no.banktel"));
		}

		return errors;
	}

	/**
	 * @return the devno
	 */
	public String getDevno() {
		return devno;
	}

	/**
	 * @param devno the devno to set
	 */
	public void setDevno(String devno) {
		this.devno = devno;
	}
}
