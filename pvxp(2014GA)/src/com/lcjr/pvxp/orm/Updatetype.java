package com.lcjr.pvxp.orm;

import com.lcjr.pvxp.util.*;

import java.io.Serializable;
import java.lang.Object;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 在hibernate中映射zzt_updatetype表的类
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2009
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author xucc
 * @version 1.0 2009/12/07
 */
public class Updatetype implements Serializable {
	private String updateno;
	
	
	private String updatename;
	
	
	private String info;
	
	
	private String remark1;
	
	
	private String remark2;
	
	
	private String remark3;
	
	
	
	public void setUpdateno(String updateno) {
		this.updateno = updateno;
	}
	
	
	public void setUpdatename(String updatename) {
		this.updatename = updatename;
	}
	
	
	public void setInfo(String info) {
		this.info = info;
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
	
	
	public String getUpdateno() {
		return this.updateno;
	}
	
	
	public String getUpdatename() {
		return this.updatename;
	}
	
	
	public String getInfo() {
		return this.info;
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
	
	
	public Object clone() throws CloneNotSupportedException {
		Updatetype result = (Updatetype) super.clone();
		return result;
	}
	
}
