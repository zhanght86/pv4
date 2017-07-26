package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class ModefyDevtraForm extends ActionForm {
	/**
	 * �豸���
	 */
	private String devNo;
	/**
	 * �豸����
	 */
	private String devName;
	/**
	 * �豸��ϵ�绰
	 */
	private String phone;
	/**
	 * �豸��ַ
	 */
	private String addr;
	/**
	 * �豸���״���
	 */
	private String tranum;
	/**
	 * �豸ά����Ա����
	 */
	private String mainpeople;
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
	 * @return the devName
	 */
	public String getDevName() {
		return devName;
	}
	/**
	 * @param devName the devName to set
	 */
	public void setDevName(String devName) {
		this.devName = devName;
	}
	/**
	 * @return the devNo
	 */
	public String getDevNo() {
		return devNo;
	}
	/**
	 * @param devNo the devNo to set
	 */
	public void setDevNo(String devNo) {
		this.devNo = devNo;
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
	
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();

		if ((devNo == null) || (devNo.length() < 1)){
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

}
