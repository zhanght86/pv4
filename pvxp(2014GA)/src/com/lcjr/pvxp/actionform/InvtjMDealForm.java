package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b>发票打印统计条件统计ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2006</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 
 * @version 1.0 2006/01/17
 */
public final class InvtjMDealForm extends ActionForm {

	private String[] devlist = new String[0];
	private String[] invtypelist = new String[0];
	private String repnm = "";
	private String qseq = "";
	private String qbegin = "";
	private String qend = "";

	public String[] getDevlist() {
		return this.devlist;
	}
	public String[] getInvtypelist() {
		return this.invtypelist;
	}
	
	public String getRepnm() {
		return this.repnm;
	}
	public String getQseq() {
		return this.qseq;
	}
	public String getQbegin() {
		return this.qbegin;
	}
	public String getQend() {
		return this.qend;
	}

	public void setDevlist(String[] devlist) {
		this.devlist = devlist;
	}
	
	public void setInvtypelist(String[] invtypelist) {
		this.invtypelist = invtypelist;
	}
	public void setRepnm(String repnm) {
		this.repnm = repnm;
	}
	public void setQseq(String qseq) {
		this.qseq = qseq;
	}
	public void setQbegin(String qbegin) {
		this.qbegin = qbegin;
	}
	public void setQend(String qend) {
		this.qend = qend;
	}

	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.devlist = new String[0];
		this.invtypelist = new String[0];
		this.repnm = "";
		this.qseq = "";
		this.qbegin = "";
		this.qend = "";
	}

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();

		return errors;
	}

}