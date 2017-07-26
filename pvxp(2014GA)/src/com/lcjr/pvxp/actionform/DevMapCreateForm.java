package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 设备监控地图创建</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.0 2005/03/11
 */
public final class DevMapCreateForm extends ActionForm {
	
	private String bankid = null;
	private String imgname = null;
	
	
	public String getBankid() {
		return this.bankid;
	}
	public String getImgname() {
		return this.imgname;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public void setImgname(String imgname) {
		this.imgname = imgname;
	}
  
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.bankid = null;
		this.imgname = null;
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();
			
		return errors;
	}
	
}