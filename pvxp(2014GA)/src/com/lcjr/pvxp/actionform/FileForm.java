package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 文件上传ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2008</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2008/5/5
 */
public final class FileForm extends ActionForm {
	
	private String filepath = null;

	public String getFilepath() {
		return (this.filepath);
	}
	
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.filepath = null;
	}

}