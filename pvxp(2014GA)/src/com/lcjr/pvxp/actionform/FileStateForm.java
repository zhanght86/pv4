package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> Ŀ¼���ݲ�ѯActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2008</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2009/9/30
 */
public final class FileStateForm extends ActionForm {
	private String devno = null;
	private String devpath = null;

	public String getDevno() {
		return (this.devno);
	}
	public String getDevpath() {
		return (this.devpath);
	}
	
	public void setDevno(String devno) {
		this.devno = devno;
	}
	public void setDevpath(String devpath) {
		this.devpath = devpath;
	}
	
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.devno = null;
		this.devpath = null;
	}

}