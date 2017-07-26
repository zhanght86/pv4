package com.lcjr.pvxp.orm;

import com.lcjr.pvxp.util.*;
import java.lang.Object;
import java.io.*;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 在hibernate中映射zzt_devftinfo表的类
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2010
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author xucc
 * @version 1.0 2010/09/26
 */
public class Devftinfo implements Serializable {
	private String devftno;
	
	
	private String devftname;
	
	
	private String contact1;
	
	
	private String phone1;
	
	
	private String mobile1;
	
	
	private String contact2;
	
	
	private String phone2;
	
	
	private String mobile2;
	
	
	private String remark1;
	
	
	private String remark2;
	
	
	private String remark3;
	
	
	private String remark4;
	
	
	
	public void setDevftno(String devftno) {
		this.devftno = devftno;
	}
	
	
	public void setDevftname(String devftname) {
		this.devftname = devftname;
	}
	
	
	public void setContact1(String contact1) {
		this.contact1 = contact1;
	}
	
	
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	
	
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	
	
	public void setContact2(String contact2) {
		this.contact2 = contact2;
	}
	
	
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	
	
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	
	
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	
	
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	
	
	public String getDevftno() {
		return this.devftno;
	}
	
	
	public String getDevftname() {
		return this.devftname;
	}
	
	
	public String getContact1() {
		return this.contact1;
	}
	
	
	public String getPhone1() {
		return this.phone1;
	}
	
	
	public String getMobile1() {
		return this.mobile1;
	}
	
	
	public String getContact2() {
		return this.contact2;
	}
	
	
	public String getPhone2() {
		return this.phone2;
	}
	
	
	public String getMobile2() {
		return this.mobile2;
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
		Operator result = (Operator) super.clone();
		return result;
	}
	
}
