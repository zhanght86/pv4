package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.lcjr.pvxp.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 修改设备厂商信息ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2010</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2010/09/27
 */
public final class ModifyDevftForm extends ActionForm {

	PubUtil pubUtil = new PubUtil();
	CharSet charSet = new CharSet();

	/*ModifyDevftAction根据modify的值判断表单是从哪提交的。
	 *modify == false 表明表单从devftDetail.jsp提交，
	 *modify == true  表明表单从ModifyDevft.jsp提交。
	 */
	private String modify;

	private String devftNo;
	private String devftName;
	private String contact1;
	private String phone1;
	private String mobile1;
	private String contact2;
	private String phone2;
	private String mobile2;
	private String addr;


  	//get method
  	public String getModify() {
		return this.modify;
	}

	public String getDevftNo() {
		return this.devftNo;
	}

	public String getDevftName() {
		return this.devftName;
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
	
	public String getAddr() {
		return this.addr;
	}


	//set method
	public void setModify(String modify) {
		this.modify = modify;
	}

	public void setDevftNo(String devftNo) {
		this.devftNo = devftNo;
	}

	public void setDevftName(String devftName) {
		this.devftName = devftName;
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
	
	public void setAddr(String addr) {
		this.addr = addr;
	}	
	

}
