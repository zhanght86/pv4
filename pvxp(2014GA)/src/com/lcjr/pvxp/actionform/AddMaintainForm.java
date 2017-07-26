package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 添加报修记录ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2011</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2011/02/24
 */
public final class AddMaintainForm extends ActionForm {
	private String devno;
	private String trbphen;
	private String trbdate;
	private String trbtime;
	private String repairs;
	private String repbank;
	private String[] subdevice;

	//get method
	public String getDevno() {
		return this.devno;
		
	}
  
	public String getTrbphen() {
		return this.trbphen;
	}

	public String getTrbdate() {
		return this.trbdate;
		
	}

	public String getTrbtime() {
		return this.trbtime;
	}
	
	
	public String getRepairs() {
		return this.repairs;
	}

	public String getRepbank() {
		return this.repbank;
	}
	
	public String[] getSubdevice() {
		return this.subdevice;
	}

	//set method
	public void setDevno(String devno) {
		this.devno = devno.trim();
	}
  
	public void setTrbphen(String trbphen) {
		this.trbphen = trbphen.trim();
	}

	public void setTrbdate(String trbdate) {
		this.trbdate = trbdate.trim();
	}

	public void setTrbtime(String trbtime) {
 		this.trbtime = trbtime.trim();
	}
	
	
  
	public void setRepairs(String repairs) {
		this.repairs = repairs.trim();
	}

	public void setRepbank(String repbank) {
 		this.repbank = repbank.trim();
	}
	
	public void setSubdevice(String[] subdevice) {
 		this.subdevice = subdevice;
	}

	public void reset(ActionMapping mapping , HttpServletRequest request) {
		
	}
	//validate method
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
  	
		ActionErrors errors = new ActionErrors();
		return errors;
	}
}
