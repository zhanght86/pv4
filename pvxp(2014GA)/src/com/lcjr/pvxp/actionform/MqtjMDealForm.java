package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 报修记录统计ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2011</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2011/03/02
 */
public final class MqtjMDealForm extends ActionForm {

	private String[] devlist = new String[0];
	private String[] subdevice = new String[0];
	private String[] state = new String[0];
	private String repnm = "";
	private String qseq = "";
	private String qbegin = "";
	private String qend = "";

	public String[] getDevlist() {
		return (this.devlist);
	}
	public String[] getSubdevice() {
		return (this.subdevice);
	}
	public String[] getState() {
		return (this.state);
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

	public void setDevlist(String[] devlist) {
		this.devlist = devlist;
	}
	public void setSubdevice(String[] subdevice) {
		this.subdevice = subdevice;
	}
	public void setState(String[] state) {
		this.state = state;
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
		this.subdevice = new String[0];
		this.state = new String[0];
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