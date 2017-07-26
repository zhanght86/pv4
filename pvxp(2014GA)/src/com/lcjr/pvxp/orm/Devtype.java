package com.lcjr.pvxp.orm;

import com.lcjr.pvxp.util.*;

import java.io.Serializable;
import java.lang.Object;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> 在hibernate中映射zzt_devtype表的类</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p>
 * @author 杨旭
 * @version 1.0 2004/12/14
 */

/**
 * @author xucc
 * @version 1.1 2010/09/29
 */
public class Devtype implements Serializable {
	private String typeno;
	
	
	private String typestate;
	
	
	private String packtype;
	
	
	private String devtype;
	
	
	private String devname;
	
	
	private String devftno;
	
	
	private String contact1;
	
	
	private String contact2;
	
	
	private String filepath;
	
	
	private String devinfo;
	
	
	private String remark1;
	
	
	private String remark2;
	
	
	private String remark3;
	
	
	private String remark4;
	
	
	
	public void setTypeno(String mtypeno) {
		this.typeno = mtypeno;
	}
	
	
	public void setTypestate(String mtypestate) {
		this.typestate = mtypestate;
	}
	
	
	public void setPacktype(String mpacktype) {
		this.packtype = mpacktype;
	}
	
	
	public void setDevtype(String mdevtype) {
		this.devtype = mdevtype;
	}
	
	
	public void setDevname(String mdevname) {
		this.devname = mdevname;
	}
	
	
	public void setDevftno(String mdevftno) {
		this.devftno = mdevftno;
	}
	
	
	public void setContact1(String mcontact1) {
		this.contact1 = mcontact1;
	}
	
	
	public void setContact2(String mcontact2) {
		this.contact2 = mcontact2;
	}
	
	
	public void setFilepath(String mfilepath) {
		this.filepath = mfilepath;
	}
	
	
	public void setDevinfo(String mdevinfo) {
		this.devinfo = mdevinfo;
	}
	
	
	public void setRemark1(String mremark1) {
		this.remark1 = mremark1;
	}
	
	
	public void setRemark2(String mremark2) {
		this.remark2 = mremark2;
	}
	
	
	public void setRemark3(String mremark3) {
		this.remark3 = mremark3;
	}
	
	
	public void setRemark4(String mremark4) {
		this.remark4 = mremark4;
	}
	
	
	public String getTypeno() {
		return this.typeno;
	}
	
	
	public String getTypestate() {
		return this.typestate;
	}
	
	
	public String getPacktype() {
		return this.packtype;
	}
	
	
	public String getDevtype() {
		return this.devtype;
	}
	
	
	public String getDevname() {
		return this.devname;
	}
	
	
	public String getDevftno() {
		return this.devftno;
	}
	
	
	public String getContact1() {
		return this.contact1;
	}
	
	
	public String getContact2() {
		return this.contact2;
	}
	
	
	public String getFilepath() {
		return this.filepath;
	}
	
	
	public String getDevinfo() {
		return this.devinfo;
	}
	
	
	public String getRemark1() {
		return this.remark1;
	}
	
	
	public String getRemark2() {
		return this.remark2;
	}
	
	
	public String getRemark3() {
		return this.remark3;
	}
	
	
	public String getRemark4() {
		return this.remark4;
	}
	
	
	public Object clone() throws CloneNotSupportedException {
		Devtype result = (Devtype) super.clone();
		return result;
	}
	
}
