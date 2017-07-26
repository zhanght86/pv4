package com.lcjr.pvxp.orm;


/**
 * 
 * @author Œ‰¿§≈Ù
 * @version pvxp
 * @date 2014-3-17
 */
public class CashStock {

	/** identifier field */
	private String batchid;

	/** identifier field */
	private String devno;

	/** identifier field */
	private String state;

	/** identifier field */
	private String trcddate;

	/** identifier field */
	private String trcdtime;

	/** identifier field */
	private String cashcount;

	/** identifier field */
	private String totalcash;

	/** identifier field */
	private String remark1;

	/** identifier field */
	private String remark2;

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

	/**
	 * @return the cashcount
	 */
	public String getCashcount() {
		return cashcount;
	}

	/**
	 * @param cashcount
	 *            the cashcount to set
	 */
	public void setCashcount(String cashcount) {
		this.cashcount = cashcount;
	}

	/**
	 * @return the devno
	 */
	public String getDevno() {
		return devno;
	}

	/**
	 * @param devno
	 *            the devno to set
	 */
	public void setDevno(String devno) {
		this.devno = devno;
	}

	/**
	 * @return the remark1
	 */
	public String getRemark1() {
		return remark1;
	}

	/**
	 * @param remark1
	 *            the remark1 to set
	 */
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	/**
	 * @return the remark2
	 */
	public String getRemark2() {
		return remark2;
	}

	/**
	 * @param remark2
	 *            the remark2 to set
	 */
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the totalcash
	 */
	public String getTotalcash() {
		return totalcash;
	}

	/**
	 * @param totalcash
	 *            the totalcash to set
	 */
	public void setTotalcash(String totalcash) {
		this.totalcash = totalcash;
	}

	/**
	 * @return the trcddate
	 */
	public String getTrcddate() {
		return trcddate;
	}

	/**
	 * @param trcddate
	 *            the trcddate to set
	 */
	public void setTrcddate(String trcddate) {
		this.trcddate = trcddate;
	}

	/**
	 * @return the trcdtime
	 */
	public String getTrcdtime() {
		return trcdtime;
	}

	/**
	 * @param trcdtime
	 *            the trcdtime to set
	 */
	public void setTrcdtime(String trcdtime) {
		this.trcdtime = trcdtime;
	}
}