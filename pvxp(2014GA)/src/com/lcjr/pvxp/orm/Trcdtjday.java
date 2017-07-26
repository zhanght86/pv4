package com.lcjr.pvxp.orm;

import com.lcjr.pvxp.util.CharSet;
import java.lang.Object;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.*;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> ��hibernate��ӳ��zzt_trcdtjday�����
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * 
 * @author ��̫��
 * @version 1.0 2005/3/30
 */
public class Trcdtjday implements Serializable {
	private String devno;
	
	
	private String devdate;
	
	
	private String trcdtype;
	
	
	private String ttrnum;
	
	
	private String strnum;
	
	
	private String moneytype;
	
	
	private String ttrsum;
	
	
	private String strsum;
	
	
	
	public void setDevno(String mdevno) {
		devno = mdevno;
	}
	
	
	public void setDevdate(String mdevdate) {
		devdate = mdevdate;
	}
	
	
	public void setTrcdtype(String mtrcdtype) {
		trcdtype = mtrcdtype;
	}
	
	
	public void setTtrnum(String mttrnum) {
		ttrnum = mttrnum;
	}
	
	
	public void setStrnum(String mstrnum) {
		strnum = mstrnum;
	}
	
	
	public void setMoneytype(String mmoneytype) {
		moneytype = mmoneytype;
	}
	
	
	public void setTtrsum(String mttrsum) {
		ttrsum = mttrsum;
	}
	
	
	public void setStrsum(String mstrsum) {
		strsum = mstrsum;
	}
	
	
	public String getDevno() {
		return devno;
	}
	
	
	public String getDevdate() {
		return devdate;
	}
	
	
	public String getTrcdtype() {
		return trcdtype;
	}
	
	
	public String getTtrnum() {
		return ttrnum;
	}
	
	
	public String getStrnum() {
		return strnum;
	}
	
	
	public String getMoneytype() {
		return moneytype;
	}
	
	
	
	/**
	 * �ܽ��ױ���
	 * 
	 * @return
	 */
	public String getTtrsum() {
		return ttrsum;
	}
	
	
	
	/**
	 * �ɹ����ױ���
	 * 
	 * @return
	 */
	public String getStrsum() {
		return strsum;
	}
	
	
	public boolean equals(Object other) {
		if (!(other instanceof Trcdtjday))
			return false;
		Trcdtjday castOther = (Trcdtjday) other;
		return new EqualsBuilder().append(this.getDevno(), castOther.getDevno()).append(this.getDevdate(), castOther.getDevdate()).append(this.getTrcdtype(), castOther.getTrcdtype()).append(
				this.getTtrnum(), castOther.getTtrnum()).append(this.getStrnum(), castOther.getStrnum()).append(this.getMoneytype(), castOther.getMoneytype()).append(this.getTtrsum(),
				castOther.getTtrsum()).append(this.getStrsum(), castOther.getStrsum()).isEquals();
	}
	
	
	public int hashCode() {
		return new HashCodeBuilder().append(getDevno()).append(getDevdate()).append(getTrcdtype()).append(getTtrnum()).append(getStrnum()).append(getMoneytype()).append(getTtrsum()).append(
				getStrsum()).toHashCode();
	}
	
}
