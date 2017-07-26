package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 设备交易失败情况明细表ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2006</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2006/12/18
 */
public final class TfmlMDealForm extends ActionForm {

	private String[] devlist= new String[0];
	private String qunit 	= "";
	private String repnm 	= "";
	private String qseq 	= "";
	private String qbegin 	= "";
	private String qend 	= "";
	private String strcdlist= "";
	private String statmode = "";

	public String[] getDevlist() {
		return (this.devlist);
	}
	public String getStrcdlist() {
		return (this.strcdlist);
	}
	public String getQunit() {
		return (this.qunit);
	}
	public String getRepnm() {
		return (this.repnm);
	}
	public String getQseq() {
		return (this.qseq);
	}
	public String getQbegin() {
		return (this.qbegin);
	}
	public String getQend() {
		return (this.qend);
	}
	public String getStatmode() {
		return (this.statmode);
	}

	public void setDevlist(String[] devlist) {
		this.devlist = devlist;
	}
	public void setStrcdlist(String strcdlist) {
		this.strcdlist = strcdlist;
	}
	public void setQunit(String qunit) {
		this.qunit = qunit;
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
	public void setStatmode(String statmode) {
		this.statmode = statmode;
	}

	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.devlist 	= new String[0];
		this.qunit 	= "";
		this.repnm 	= "";
		this.qseq 	= "";
		this.qbegin 	= "";
		this.qend 	= "";
		this.strcdlist 	= "";
		this.statmode 	= "";
	}

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();

		return errors;
	}

}