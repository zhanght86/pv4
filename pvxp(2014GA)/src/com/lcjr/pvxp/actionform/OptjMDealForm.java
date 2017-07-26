package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 操作记录统计ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2011</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2011/03/04
 */
public final class OptjMDealForm extends ActionForm {

	private String[] operlist = new String[0];
	private String[] trcd = new String[0];
	private String[] type = new String[0];
	private String repnm = "";
	private String qseq = "";
	private String qbegin = "";
	private String qend = "";

	public String[] getOperlist() {
		return (this.operlist);
	}
	public String[] getTrcd() {
		return (this.trcd);
	}
	public String[] getType() {
		return (this.type);
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

	public void setOperlist(String[] operlist) {
		this.operlist = operlist;
	}
	public void setTrcd(String[] trcd) {
		this.trcd = trcd;
	}
	public void setType(String[] type) {
		this.type = type;
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
		this.operlist = new String[0];
		this.trcd = new String[0];
		this.type = new String[0];
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