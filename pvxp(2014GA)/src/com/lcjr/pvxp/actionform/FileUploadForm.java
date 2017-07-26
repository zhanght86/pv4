package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 文件获取ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/3/20
 */
public final class FileUploadForm extends ActionForm {

	private String devno = "";
	private String filestr = "";
	private String devpath = "";

	public String getDevno() {
		return (this.devno);
	}
	public String getFilestr() {
		return (this.filestr);
	}
	public String getDevpath() {
		return (this.devpath);
	}

	public void setDevno(String devno) {
		this.devno = devno;
	}
	public void setFilestr(String filestr) {
		this.filestr = filestr;
	}
	public void setDevpath(String devpath) {
		this.devpath = devpath;
	}

	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.devno = "";
		this.filestr = "";
		this.devpath = "";
	}

	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();

		return errors;
	}
	
}