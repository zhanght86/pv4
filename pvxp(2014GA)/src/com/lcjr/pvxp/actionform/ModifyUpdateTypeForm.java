package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.lcjr.pvxp.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 修改更新类型ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2009</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2009/12/07
 */
public final class ModifyUpdateTypeForm extends ActionForm {

	PubUtil pubUtil = new PubUtil();
	CharSet charSet = new CharSet();

	/*ModifyUpdateTypeAction根据modify的值判断表单是从哪提交的。
	 *modify == false 表明表单从UpdateTypeDetail.jsp提交，
	 *modify == true  表明表单从ModifyUpdateType.jsp提交。
	 */
	private String modify;

	private String updateno;
	private String updatename;
	private String info;
	
	//get method
	public String getModify() {
		return this.modify;
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
	
	//set method
	public void setModify(String modify) {
		this.modify = modify;
	}
	
	public void setUpdateno(String updateno) {
		this.updateno = updateno;
	}
	
	public void setUpdatename(String updatename) {
		this.updatename = updatename;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
}
