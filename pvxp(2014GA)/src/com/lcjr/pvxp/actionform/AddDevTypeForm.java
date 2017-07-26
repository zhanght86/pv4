package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 添加设备类型ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 吴学坤
 * @version 1.0 2005/03/12
 */

/**
 * @author xucc
 * @version 1.1 2010/09/29
 */
public final class AddDevTypeForm extends ActionForm {
	
	private String devTypeNo;
	
	private String devTypeState;
	
	private String devType;
	
	private String devName;
	
	private String devEquipt;
	
	private String dataPackageType;
	
	private String devftno;
	
	
	
	
	// get method
	public String getDevTypeNo() {
		return this.devTypeNo;
	}
	
	
	public String getDevTypeState() {
		return this.devTypeState;
	}
	
	
	public String getDevType() {
		return this.devType;
	}
	
	
	public String getDevName() {
		return this.devName;
	}
	
	
	public String getDevEquipt() {
		return this.devEquipt;
	}
	
	
	public String getDataPackageType() {
		return this.dataPackageType;
	}
	
	
	public String getDevftno() {
		return this.devftno;
	}
	
	
	
	// set method
	public void setDevTypeNo(String devTypeNo) {
		this.devTypeNo = devTypeNo.trim();
	}
	
	
	public void setDevTypeState(String devTypeState) {
		this.devTypeState = devTypeState.trim();
	}
	
	
	public void setDevType(String devType) {
		this.devType = devType.trim();
	}
	
	
	public void setDevName(String devName) {
		this.devName = devName.trim();
	}
	
	
	public void setDevEquipt(String devEquipt) {
		this.devEquipt = devEquipt.trim();
	}
	
	
	public void setDataPackageType(String dataPackageType) {
		this.dataPackageType = dataPackageType.trim();
	}
	
	
	public void setDevftno(String devftno) {
		this.devftno = devftno.trim();
	}
	
	
	
	// reset method
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// debug
	}
	
	
	
	// validate method
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		// debug
		ActionErrors errors = new ActionErrors();
		return errors;
	}
}
