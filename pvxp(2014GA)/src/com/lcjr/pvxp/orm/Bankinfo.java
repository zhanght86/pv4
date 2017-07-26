package com.lcjr.pvxp.orm;

import com.lcjr.pvxp.util.CharSet;

import java.io.Serializable;
import java.lang.Object;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 在hibernate中映射zzt_bankinfo表的类
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author 杨旭
 * @version 1.0 2004/12/14
 */
public class Bankinfo implements Serializable {
	private String bankid;
	
	private String banknm;
	
	private String bankaddr;
	
	private String banktel;
	
	private String state;
	
	private String banktag;
	
	private String adddate;
	
	private String remark1;
	
	private String remark2;
	
	
	
	public void setBankid(String mbankid) {
		bankid = mbankid;
	}
	
	public void setBanknm(String mbanknm) {
		banknm = mbanknm;
	}
	
	public void setBankaddr(String mbankaddr) {
		bankaddr = mbankaddr;
	}
	
	public void setBanktel(String mbanktel) {
		banktel = mbanktel;
	}
	
	public void setState(String mstate) {
		state = mstate;
	}
	
	public void setBanktag(String mbanktag) {
		banktag = mbanktag;
	}
	
	public void setAdddate(String madddate) {
		adddate = madddate;
	}
	
	public void setRemark1(String mremark1) {
		remark1 = mremark1;
	}
	
	public void setRemark2(String mremark2) {
		remark2 = mremark2;
	}
	
	
	public String getBankid() {
		return bankid;
	}
	
	public String getBanknm() {
		return banknm;
	}
	
	public String getBankaddr() {
		return bankaddr;
	}
	
	public String getBanktel() {
		return banktel;
	}
	
	public String getState() {
		return state;
	}
	
	public String getBanktag() {
		return banktag;
	}
	
	public String getAdddate() {
		return adddate;
	}
	
	public String getRemark1() {
		return remark1;
	}
	
	public String getRemark2() {
		return remark2;
	}
	
	
	public Object clone() throws CloneNotSupportedException {
		Bankinfo result = (Bankinfo) super.clone();
		return result;
	}
	
}
