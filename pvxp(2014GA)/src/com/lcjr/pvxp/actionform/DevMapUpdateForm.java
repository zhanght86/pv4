package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 设备监控地图编辑保存ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.0 2005/03/16
 */
public final class DevMapUpdateForm extends ActionForm {
	
	private String bankid = null;
	private String banksstr = null;
	private String devsstr = null;
	private String imgnmstr = null;

	public String getBankid() {
		return this.bankid;
	}
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	
	public String getBanksstr() {
		return this.banksstr;
	}
	public void setBanksstr(String banksstr) {
		this.banksstr = banksstr;
	}
	
	public String getDevsstr() {
		return this.devsstr;
	}
	public void setDevsstr(String devsstr) {
		this.devsstr = devsstr;
	}
	
	public String getImgnmstr() {
		return this.imgnmstr;
	}
	public void setImgnmstr(String imgnmstr) {
		this.imgnmstr = imgnmstr;
	}
	
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.bankid = null;
		this.banksstr = null;
		this.devsstr = null;
		this.imgnmstr = null;
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		return errors;
	}
	
}