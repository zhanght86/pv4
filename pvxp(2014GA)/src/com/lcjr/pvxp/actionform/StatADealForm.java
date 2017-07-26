package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 自动报表定制ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/4/7
 */
public final class StatADealForm extends ActionForm {

	private String bankid = "";
	private String statesort = "";
	private String statename = "";
	private String statetype = "";
	private String afterday = "";
	private String aftertime = "";
	private String opentag = "";
	private String info = "";

	private String[] sortname = new String[0];
	private String strcdlist = "";

	public String getBankid() {
		return (this.bankid);
	}
	public String getStatesort() {
		return (this.statesort);
	}
	public String getStatename() {
		return (this.statename);
	}
	public String getStatetype() {
		return (this.statetype);
	}
	public String getAfterday() {
		return (this.afterday);
	}
	public String getAftertime() {
		return (this.aftertime);
	}
	public String getOpentag() {
		return (this.opentag);
	}
	public String getInfo() {
		return (this.info);
	}
	public String[] getSortname() {
		return (this.sortname);
	}
	public String getStrcdlist() {
		return (this.strcdlist);
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public void setStatesort(String statesort) {
		this.statesort = statesort;
	}
	public void setStatename(String statename) {
		this.statename = statename;
	}
	public void setStatetype(String statetype) {
		this.statetype = statetype;
	}
	public void setAfterday(String afterday) {
		this.afterday = afterday;
	}
	public void setAftertime(String aftertime) {
		this.aftertime = aftertime;
	}
	public void setOpentag(String opentag) {
		this.opentag = opentag;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public void setSortname(String[] sortname) {
		this.sortname = sortname;
	}
	public void setStrcdlist(String strcdlist) {
		this.strcdlist = strcdlist;
	}

	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.bankid = "";
		this.statesort = "";
		this.statename = "";
		this.statetype = "";
		this.afterday = "";
		this.aftertime = "";
		this.opentag = "";
		this.info = "";

		this.sortname = new String[0];
		this.strcdlist = "";
	}

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();

		return errors;
	}

}