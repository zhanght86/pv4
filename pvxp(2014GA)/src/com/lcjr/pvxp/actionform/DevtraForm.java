package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class DevtraForm extends ActionForm {

	/**
	 * 设备编号
	 */
	private String devNo;
	/**
	 * 设备名称
	 */
	private String devName;
	/**
	 * 设备联系电话
	 */
	private String phone;
	/**
	 * 设备地址
	 */
	private String addr;
	/**
	 * 设备交易次数
	 */
	private String tranum;
	/**
	 * 设备维护人员名称
	 */
	private String mainpeople;
	
	//
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
	public String getphone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setphone(String phone) {
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

	//validate method
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
  	
		ActionErrors errors = new ActionErrors();
		ActionForward myforward = mapping.findForward("LoginSuccess");
		if(devNo==null||devNo.length()<1){
			errors.add("system",new ActionMessage("pvxp.errors.form.no.devno"));
			myforward = new ActionForward(mapping.getInput());
		} 
		
		return errors;
	}

}
