package com.lcjr.pvxp.orm;

import java.io.Serializable;
import java.sql.Clob;
import java.sql.Date;

/**
 * 
 * @author 武坤鹏
 * @version pvxp(2014GA)
 * @date 2014-9-12
 */
public class TDRecords implements Serializable {

	public static final String TABLENAME = "TDRecords";

	/**
	 * 填单交易id
	 */
	private Integer tdId;

	/**
	 * 用户姓名
	 */
	private String usrNm;

	/**
	 * 证件号码
	 */
	private String usrId;

	/**
	 * 扫描图片路径
	 */
	private String xmlTypeCode;

	/**
	 * 交易报文
	 */
	private Clob tdMsg;

	/**
	 * 交易日期
	 */
	private Date tdDate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TDRecords [tdId=" + tdId + ", usrNm=" + usrNm + ", usrId=" + usrId + ", xmlTypeCode=" + xmlTypeCode + ", tdMsg=" + tdMsg + ", tdDate=" + tdDate + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tdDate == null) ? 0 : tdDate.hashCode());
		result = prime * result + ((tdId == null) ? 0 : tdId.hashCode());
		result = prime * result + ((tdMsg == null) ? 0 : tdMsg.hashCode());
		result = prime * result + ((usrId == null) ? 0 : usrId.hashCode());
		result = prime * result + ((usrNm == null) ? 0 : usrNm.hashCode());
		result = prime * result + ((xmlTypeCode == null) ? 0 : xmlTypeCode.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		TDRecords other = (TDRecords) obj;
		if (tdDate == null) {
			if (other.tdDate != null)
				return false;
		} else if (!tdDate.equals(other.tdDate))
			return false;
		if (tdId == null) {
			if (other.tdId != null)
				return false;
		} else if (!tdId.equals(other.tdId))
			return false;
		if (tdMsg == null) {
			if (other.tdMsg != null)
				return false;
		} else if (!tdMsg.equals(other.tdMsg))
			return false;
		if (usrId == null) {
			if (other.usrId != null)
				return false;
		} else if (!usrId.equals(other.usrId))
			return false;
		if (usrNm == null) {
			if (other.usrNm != null)
				return false;
		} else if (!usrNm.equals(other.usrNm))
			return false;
		if (xmlTypeCode == null) {
			if (other.xmlTypeCode != null)
				return false;
		} else if (!xmlTypeCode.equals(other.xmlTypeCode))
			return false;
		return true;
	}

	/**
	 * @return the tdId
	 */
	public Integer getTdId() {
		return tdId;
	}

	/**
	 * @param tdId
	 *            the tdId to set
	 */
	public void setTdId(Integer tdId) {
		this.tdId = tdId;
	}

	/**
	 * @return the usrNm
	 */
	public String getUsrNm() {
		return usrNm;
	}

	/**
	 * @param usrNm
	 *            the usrNm to set
	 */
	public void setUsrNm(String usrNm) {
		this.usrNm = usrNm;
	}

	/**
	 * @return the usrId
	 */
	public String getUsrId() {
		return usrId;
	}

	/**
	 * @param usrId
	 *            the usrId to set
	 */
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	/**
	 * @return the xmlTypeCode
	 */
	public String getXmlTypeCode() {
		return xmlTypeCode;
	}

	/**
	 * @param xmlTypeCode
	 *            the xmlTypeCode to set
	 */
	public void setXmlTypeCode(String xmlTypeCode) {
		this.xmlTypeCode = xmlTypeCode;
	}

	/**
	 * @return the tdMsg
	 */
	public Clob getTdMsg() {
		return tdMsg;
	}

	/**
	 * @param tdMsg
	 *            the tdMsg to set
	 */
	public void setTdMsg(Clob tdMsg) {
		this.tdMsg = tdMsg;
	}

	/**
	 * @return the tdDate
	 */
	public Date getTdDate() {
		return tdDate;
	}

	/**
	 * @param tdDate
	 *            the tdDate to set
	 */
	public void setTdDate(Date tdDate) {
		this.tdDate = tdDate;
	}

	/**
	 * @return the tablename
	 */
	public static String getTablename() {
		return TABLENAME;
	}

}
