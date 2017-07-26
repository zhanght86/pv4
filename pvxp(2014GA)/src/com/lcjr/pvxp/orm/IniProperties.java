package com.lcjr.pvxp.orm;

import java.io.Serializable;
import java.sql.Date;

/**
 * 
 * @author 武坤鹏
 * @version pvxp
 * @date 2014-3-17
 */
public class IniProperties implements Serializable {

	/**
	 * 填单交易id
	 */
	private Integer noID;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Properties [noID=" + noID + ", filename=" + filename + ", isection=" + isection + ", keyname=" + keyname + ", keyvalue=" + keyvalue + ", cdate=" + cdate + ", remark1=" + remark1 + "]";
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
		result = prime * result + ((cdate == null) ? 0 : cdate.hashCode());
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + ((isection == null) ? 0 : isection.hashCode());
		result = prime * result + ((keyname == null) ? 0 : keyname.hashCode());
		result = prime * result + ((keyvalue == null) ? 0 : keyvalue.hashCode());
		result = prime * result + ((noID == null) ? 0 : noID.hashCode());
		result = prime * result + ((remark1 == null) ? 0 : remark1.hashCode());
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
		IniProperties other = (IniProperties) obj;
		if (cdate == null) {
			if (other.cdate != null)
				return false;
		} else if (!cdate.equals(other.cdate))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (isection == null) {
			if (other.isection != null)
				return false;
		} else if (!isection.equals(other.isection))
			return false;
		if (keyname == null) {
			if (other.keyname != null)
				return false;
		} else if (!keyname.equals(other.keyname))
			return false;
		if (keyvalue == null) {
			if (other.keyvalue != null)
				return false;
		} else if (!keyvalue.equals(other.keyvalue))
			return false;
		if (noID == null) {
			if (other.noID != null)
				return false;
		} else if (!noID.equals(other.noID))
			return false;
		if (remark1 == null) {
			if (other.remark1 != null)
				return false;
		} else if (!remark1.equals(other.remark1))
			return false;
		return true;
	}

	/**
	 * @return the noID
	 */
	public Integer getNoID() {
		return noID;
	}

	/**
	 * @param noID
	 *            the noID to set
	 */
	public void setNoID(Integer noID) {
		this.noID = noID;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename
	 *            the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the isection
	 */
	public String getIsection() {
		return isection;
	}

	/**
	 * @param isection
	 *            the isection to set
	 */
	public void setIsection(String isection) {
		this.isection = isection;
	}

	/**
	 * @return the keyname
	 */
	public String getKeyname() {
		return keyname;
	}

	/**
	 * @param keyname
	 *            the keyname to set
	 */
	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}

	/**
	 * @return the keyvalue
	 */
	public String getKeyvalue() {
		return keyvalue;
	}

	/**
	 * @param keyvalue
	 *            the keyvalue to set
	 */
	public void setKeyvalue(String keyvalue) {
		this.keyvalue = keyvalue;
	}

	/**
	 * @return the cdate
	 */
	public Date getCdate() {
		return cdate;
	}

	/**
	 * @param cdate
	 *            the cdate to set
	 */
	public void setCdate(Date cdate) {
		this.cdate = cdate;
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
	 * 文件名称
	 */
	private String filename;

	/**
	 * 小结
	 */
	private String isection;

	/**
	 * 键
	 */
	private String keyname;

	/**
	 * 键值
	 */
	private String keyvalue;

	/**
	 * 日期
	 */
	private Date cdate;

	private String remark1;

}
