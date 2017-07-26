package com.lcjr.pvxp.orm;

import java.io.Serializable;

/**
 * 
 * @author Œ‰¿§≈Ù
 * @version pvxp
 * @date 2014-3-17
 */
public class CashDetail implements Serializable{

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addcount == null) ? 0 : addcount.hashCode());
		result = prime * result + ((bankls == null) ? 0 : bankls.hashCode());
		result = prime * result + ((batchid == null) ? 0 : batchid.hashCode());
		result = prime * result + ((cardno == null) ? 0 : cardno.hashCode());
		result = prime * result + ((devno == null) ? 0 : devno.hashCode());
		result = prime * result + ((dzflag == null) ? 0 : dzflag.hashCode());
		result = prime * result + ((fiftycount == null) ? 0 : fiftycount.hashCode());
		result = prime * result + ((fivecount == null) ? 0 : fivecount.hashCode());
		result = prime * result + ((hundredcount == null) ? 0 : hundredcount.hashCode());
		result = prime * result + ((idcardno == null) ? 0 : idcardno.hashCode());
		result = prime * result + ((onecount == null) ? 0 : onecount.hashCode());
		result = prime * result + ((remark1 == null) ? 0 : remark1.hashCode());
		result = prime * result + ((remark2 == null) ? 0 : remark2.hashCode());
		result = prime * result + ((retflag == null) ? 0 : retflag.hashCode());
		result = prime * result + ((serverls == null) ? 0 : serverls.hashCode());
		result = prime * result + ((tencount == null) ? 0 : tencount.hashCode());
		result = prime * result + ((totalamount == null) ? 0 : totalamount.hashCode());
		result = prime * result + ((trcddate == null) ? 0 : trcddate.hashCode());
		result = prime * result + ((trcdtime == null) ? 0 : trcdtime.hashCode());
		result = prime * result + ((trcdtype == null) ? 0 : trcdtype.hashCode());
		result = prime * result + ((twentycount == null) ? 0 : twentycount.hashCode());
		result = prime * result + ((twocount == null) ? 0 : twocount.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CashDetail other = (CashDetail) obj;
		if (addcount == null) {
			if (other.addcount != null)
				return false;
		} else if (!addcount.equals(other.addcount))
			return false;
		if (bankls == null) {
			if (other.bankls != null)
				return false;
		} else if (!bankls.equals(other.bankls))
			return false;
		if (batchid == null) {
			if (other.batchid != null)
				return false;
		} else if (!batchid.equals(other.batchid))
			return false;
		if (cardno == null) {
			if (other.cardno != null)
				return false;
		} else if (!cardno.equals(other.cardno))
			return false;
		if (devno == null) {
			if (other.devno != null)
				return false;
		} else if (!devno.equals(other.devno))
			return false;
		if (dzflag == null) {
			if (other.dzflag != null)
				return false;
		} else if (!dzflag.equals(other.dzflag))
			return false;
		if (fiftycount == null) {
			if (other.fiftycount != null)
				return false;
		} else if (!fiftycount.equals(other.fiftycount))
			return false;
		if (fivecount == null) {
			if (other.fivecount != null)
				return false;
		} else if (!fivecount.equals(other.fivecount))
			return false;
		if (hundredcount == null) {
			if (other.hundredcount != null)
				return false;
		} else if (!hundredcount.equals(other.hundredcount))
			return false;
		if (idcardno == null) {
			if (other.idcardno != null)
				return false;
		} else if (!idcardno.equals(other.idcardno))
			return false;
		if (onecount == null) {
			if (other.onecount != null)
				return false;
		} else if (!onecount.equals(other.onecount))
			return false;
		if (remark1 == null) {
			if (other.remark1 != null)
				return false;
		} else if (!remark1.equals(other.remark1))
			return false;
		if (remark2 == null) {
			if (other.remark2 != null)
				return false;
		} else if (!remark2.equals(other.remark2))
			return false;
		if (retflag == null) {
			if (other.retflag != null)
				return false;
		} else if (!retflag.equals(other.retflag))
			return false;
		if (serverls == null) {
			if (other.serverls != null)
				return false;
		} else if (!serverls.equals(other.serverls))
			return false;
		if (tencount == null) {
			if (other.tencount != null)
				return false;
		} else if (!tencount.equals(other.tencount))
			return false;
		if (totalamount == null) {
			if (other.totalamount != null)
				return false;
		} else if (!totalamount.equals(other.totalamount))
			return false;
		if (trcddate == null) {
			if (other.trcddate != null)
				return false;
		} else if (!trcddate.equals(other.trcddate))
			return false;
		if (trcdtime == null) {
			if (other.trcdtime != null)
				return false;
		} else if (!trcdtime.equals(other.trcdtime))
			return false;
		if (trcdtype == null) {
			if (other.trcdtype != null)
				return false;
		} else if (!trcdtype.equals(other.trcdtype))
			return false;
		if (twentycount == null) {
			if (other.twentycount != null)
				return false;
		} else if (!twentycount.equals(other.twentycount))
			return false;
		if (twocount == null) {
			if (other.twocount != null)
				return false;
		} else if (!twocount.equals(other.twocount))
			return false;
		return true;
	}

	private String trcdtype;

	private String devno;

	private String trcddate;

	private String trcdtime;

	private String serverls;

	private String bankls;

	private String cardno;

	private String idcardno;

	private String onecount;

	private String twocount;

	private String fivecount;

	private String tencount;

	private String twentycount;

	private String fiftycount;

	private String hundredcount;

	private String addcount;

	private String totalamount;

	private String retflag;

	private String dzflag;

	private String batchid;

	private String remark1;

	private String remark2;
	/**
	 * @return the addcount
	 */
	public String getAddcount() {
		return addcount;
	}

	/**
	 * @param addcount
	 *            the addcount to set
	 */
	public void setAddcount(String addcount) {
		this.addcount = addcount;
	}

	/**
	 * @return the bankls
	 */
	public String getBankls() {
		return bankls;
	}

	/**
	 * @param bankls
	 *            the bankls to set
	 */
	public void setBankls(String bankls) {
		this.bankls = bankls;
	}

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
	 * @return the cardno
	 */
	public String getCardno() {
		return cardno;
	}

	/**
	 * @param cardno
	 *            the cardno to set
	 */
	public void setCardno(String cardno) {
		this.cardno = cardno;
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
	 * @return the dzflag
	 */
	public String getDzflag() {
		return dzflag;
	}

	/**
	 * @param dzflag
	 *            the dzflag to set
	 */
	public void setDzflag(String dzflag) {
		this.dzflag = dzflag;
	}

	/**
	 * @return the fiftycount
	 */
	public String getFiftycount() {
		return fiftycount;
	}

	/**
	 * @param fiftycount
	 *            the fiftycount to set
	 */
	public void setFiftycount(String fiftycount) {
		this.fiftycount = fiftycount;
	}

	/**
	 * @return the fivecount
	 */
	public String getFivecount() {
		return fivecount;
	}

	/**
	 * @param fivecount
	 *            the fivecount to set
	 */
	public void setFivecount(String fivecount) {
		this.fivecount = fivecount;
	}

	/**
	 * @return the hundredcount
	 */
	public String getHundredcount() {
		return hundredcount;
	}

	/**
	 * @param hundredcount
	 *            the hundredcount to set
	 */
	public void setHundredcount(String hundredcount) {
		this.hundredcount = hundredcount;
	}

	/**
	 * @return the idcardno
	 */
	public String getIdcardno() {
		return idcardno;
	}

	/**
	 * @param idcardno
	 *            the idcardno to set
	 */
	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}

	/**
	 * @return the onecount
	 */
	public String getOnecount() {
		return onecount;
	}

	/**
	 * @param onecount
	 *            the onecount to set
	 */
	public void setOnecount(String onecount) {
		this.onecount = onecount;
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
	 * @return the retflag
	 */
	public String getRetflag() {
		return retflag;
	}

	/**
	 * @param retflag
	 *            the retflag to set
	 */
	public void setRetflag(String retflag) {
		this.retflag = retflag;
	}

	/**
	 * @return the serverls
	 */
	public String getServerls() {
		return serverls;
	}

	/**
	 * @param serverls
	 *            the serverls to set
	 */
	public void setServerls(String serverls) {
		this.serverls = serverls;
	}

	/**
	 * @return the tencount
	 */
	public String getTencount() {
		return tencount;
	}

	/**
	 * @param tencount
	 *            the tencount to set
	 */
	public void setTencount(String tencount) {
		this.tencount = tencount;
	}

	/**
	 * @return the totalamount
	 */
	public String getTotalamount() {
		return totalamount;
	}

	/**
	 * @param totalamount
	 *            the totalamount to set
	 */
	public void setTotalamount(String totalamount) {
		this.totalamount = totalamount;
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

	/**
	 * @return the trcdtype
	 */
	public String getTrcdtype() {
		return trcdtype;
	}

	/**
	 * @param trcdtype
	 *            the trcdtype to set
	 */
	public void setTrcdtype(String trcdtype) {
		this.trcdtype = trcdtype;
	}

	/**
	 * @return the twentycount
	 */
	public String getTwentycount() {
		return twentycount;
	}

	/**
	 * @param twentycount
	 *            the twentycount to set
	 */
	public void setTwentycount(String twentycount) {
		this.twentycount = twentycount;
	}

	/**
	 * @return the twocount
	 */
	public String getTwocount() {
		return twocount;
	}

	/**
	 * @param twocount
	 *            the twocount to set
	 */
	public void setTwocount(String twocount) {
		this.twocount = twocount;
	}

}
