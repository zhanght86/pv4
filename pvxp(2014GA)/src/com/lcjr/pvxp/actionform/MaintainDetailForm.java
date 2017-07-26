package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 报修记录详细信息ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2011</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2011/03/01
 */
public final class MaintainDetailForm extends ActionForm {
	
	private String devno 	= null;
	private String trbtype 	= null;
	private String trbdate 	= null;
	private String trbtime 	= null;
	
	public String getDevno() {
		return (this.devno);
	}
	public String getTrbtype() {
		return (this.trbtype);
	}
	public String getTrbdate() {
		return (this.trbdate);
	}
	public String getTrbtime() {
		return (this.trbtime);
	}

	public void setDevno(String devno) {
		this.devno = devno;
	}
	public void setTrbtype(String trbtype) {
		this.trbtype = trbtype;
	}
	public void setTrbdate(String trbdate) {
		this.trbdate = trbdate;
	}
	public void setTrbtime(String trbtime) {
		this.trbtime = trbtime;
	}
 
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.devno 	= null;
		this.trbtype 	= null;
		this.trbdate 	= null;
		this.trbtime 	= null;
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {	
		ActionErrors errors = new ActionErrors();
		return errors;
	}	
}