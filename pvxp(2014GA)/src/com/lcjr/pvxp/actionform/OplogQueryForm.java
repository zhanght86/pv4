package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 操作员操作流水查询ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/3/22
 */
public final class OplogQueryForm extends ActionForm {
	private String operid = null;
	private String bopdate = null;
	private String eopdate = null;
	private String trcd = null;
	private String type = null;

	public String getOperid() {
		return (this.operid);
	}
	public String getBopdate() {
		return (this.bopdate);
	}
	public String getEopdate() {
		return (this.eopdate);
	}
	public String getTrcd() {
		return (this.trcd);
	}
	public String getType() {
		return (this.type);
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}
	public void setBopdate(String bopdate) {
		this.bopdate = bopdate;
	}
	public void setEopdate(String eopdate) {
		this.eopdate = eopdate;
	}
	public void setTrcd(String trcd) {
		this.trcd = trcd;
	}
	public void setType(String type) {
		this.type = type;
	}

	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.operid = "";
		this.bopdate = "";
		this.eopdate = "";
		this.trcd = "";
		this.type = "";
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();

		return errors;
	}
}