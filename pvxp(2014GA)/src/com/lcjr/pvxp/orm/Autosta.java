package com.lcjr.pvxp.orm;

import com.lcjr.pvxp.util.CharSet;

import java.io.Serializable;
import java.lang.Object;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 在hibernate中映射zzt_autosta表的类
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author 刘太沛
 * @version 1.0 2005/4/7
 */
public class Autosta implements Serializable {
	private String id;
	
	
	private String statename;
	
	
	private String bankid;
	
	
	private String statesort;
	
	
	private String statetype;
	
	
	private String afterday;
	
	
	private String aftertime;
	
	
	private String nextstatday;
	
	
	private String laststat;
	
	
	private String count;
	
	
	private String adddate;
	
	
	private String opentag;
	
	
	private String trcdtypes;
	
	
	private String info;
	
	
	private String remark1;
	
	
	private String remark2;
	
	
	
	public void setId(String mid) {
		id = mid;
	}
	
	
	public void setStatename(String mstatename) {
		statename = mstatename;
	}
	
	
	public void setBankid(String mbankid) {
		bankid = mbankid;
	}
	
	
	public void setStatesort(String mstatesort) {
		statesort = mstatesort;
	}
	
	
	public void setStatetype(String mstatetype) {
		statetype = mstatetype;
	}
	
	
	public void setAfterday(String mafterday) {
		afterday = mafterday;
	}
	
	
	public void setAftertime(String maftertime) {
		aftertime = maftertime;
	}
	
	
	public void setNextstatday(String mnextstatday) {
		nextstatday = mnextstatday;
	}
	
	
	public void setLaststat(String mlaststat) {
		laststat = mlaststat;
	}
	
	
	public void setCount(String mcount) {
		count = mcount;
	}
	
	
	public void setAdddate(String madddate) {
		adddate = madddate;
	}
	
	
	public void setOpentag(String mopentag) {
		opentag = mopentag;
	}
	
	
	public void setTrcdtypes(String mtrcdtypes) {
		trcdtypes = mtrcdtypes;
	}
	
	
	public void setInfo(String minfo) {
		info = minfo;
	}
	
	
	public void setRemark1(String mremark1) {
		remark1 = mremark1;
	}
	
	
	public void setRemark2(String mremark2) {
		remark2 = mremark2;
	}
	
	
	public String getId() {
		return id;
	}
	
	
	public String getStatename() {
		return statename;
	}
	
	
	public String getBankid() {
		return bankid;
	}
	
	
	public String getStatesort() {
		return statesort;
	}
	
	
	public String getStatetype() {
		return statetype;
	}
	
	
	public String getAfterday() {
		return afterday;
	}
	
	
	public String getAftertime() {
		return aftertime;
	}
	
	
	public String getNextstatday() {
		return nextstatday;
	}
	
	
	public String getLaststat() {
		return laststat;
	}
	
	
	public String getCount() {
		return count;
	}
	
	
	public String getAdddate() {
		return adddate;
	}
	
	
	public String getOpentag() {
		return opentag;
	}
	
	
	public String getTrcdtypes() {
		return trcdtypes;
	}
	
	
	public String getInfo() {
		return info;
	}
	
	
	public String getRemark1() {
		return remark1;
	}
	
	
	public String getRemark2() {
		return remark2;
	}
	
	
	public Object clone() throws CloneNotSupportedException {
		Autosta result = (Autosta) super.clone();
		return result;
	}
	
}
