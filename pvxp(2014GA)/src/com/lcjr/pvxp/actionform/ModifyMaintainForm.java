package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.lcjr.pvxp.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 修改报修记录信息ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2011</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2011/03/01
 */
public final class ModifyMaintainForm extends ActionForm {

	private String devno;
	private String trbtype;
	private String trbdate;
	private String trbtime;
	
	private String state;
	private String obvidate;
	private String obvitime;
	private String dutyno;
	private String remark;

  	//get method
	public String getDevno() {
		return this.devno;
	}
	
	public String getTrbtype() {
		return this.trbtype;
	}

	public String getTrbdate() {
		return this.trbdate;
	}

	public String getTrbtime() {
		return this.trbtime;
	}
	
	public String getObvidate() {
		return this.obvidate;
	}
	
	public String getObvitime() {
		return this.obvitime;
	}

	public String getRemark() {
		return this.remark;
	}

	
	public String getDutyno() {
		return this.dutyno;
	}

	
	public String getState() {
		return this.state;
	}


	//set method
	public void setDevno(String devno) {
		this.devno = devno;
	}
	
	
	public void setTrbdate(String trbdate) {
		this.trbdate = trbdate;
	}

	public void setTrbtime(String trbtime) {
		this.trbtime = trbtime;
	}
	
	
	public void setObvidate(String obvidate) {
		this.obvidate = obvidate;
	}
	
	public void setObvitime(String obvitime) {
		this.obvitime = obvitime;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTrbtype(String trbtype) {
		this.trbtype = trbtype;
	}
	
	public void setDutyno(String dutyno) {
		this.dutyno = dutyno;
	}

	public void setState(String state) {
		this.state = state;
	}

}


