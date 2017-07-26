package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @version pvxp(wf3)
 * @date 2014-5-13
 */
public class CashDetailForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * »ú¹¹±àºÅ
	 */

	private String page = null;

	private String pagesize = null;

	public String getPage() {
		return (this.page);
	}

	public String getPagesize() {
		return (this.pagesize);
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	private String[] termnum;
	// ³®Ïä×´Ì¬
	private String[] outboxstatus;
	// ½»Ò××´Ì¬
	private String[] tradestatus;
	private String startdate;

	private String starttime;

	private String enddate;

	private String endtime;

	private String cardnum;

	private String cardstate;

	private String boxstate;

	private String personnum;

	private String batchid;

	/**
	 * @return the batchid
	 */
	public String getBatchid() {
		return batchid;
	}

	/**
	 * @param batchid
	 *            the batchid to set
	 */
	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public String getCardstate() {
		return cardstate;
	}

	public void setCardstate(String cardstate) {
		this.cardstate = cardstate;
	}

	public String getBoxstate() {
		return boxstate;
	}

	public void setBoxstate(String boxstate) {
		this.boxstate = boxstate;
	}

	public String getPersonnum() {
		return personnum;
	}

	public void setPersonnum(String personnum) {
		this.personnum = personnum;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.page = "1";
		this.pagesize = "10";
	}

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return super.validate(mapping, request);
	}

	public String[] getTermnum() {
		return termnum;
	}

	public void setTermnum(String[] termnum) {
		this.termnum = termnum;
	}

	public String[] getOutboxstatus() {
		return outboxstatus;
	}

	public void setOutboxstatus(String[] outboxstatus) {
		this.outboxstatus = outboxstatus;
	}

	public String[] getTradestatus() {
		return tradestatus;
	}

	public void setTradestatus(String[] tradestatus) {
		this.tradestatus = tradestatus;
	}

}
