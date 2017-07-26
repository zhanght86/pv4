package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> ɾ���豸ActionForm
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * <br>
 * 
 * @author ��ѧ��
 * @version 1.0 2005/03/10
 */
public final class DevDeleteForm extends ActionForm {
	private String[] devList = new String[0];
	
	
	
	public String[] getDevList() {
		// debug
		return this.devList;
	}
	
	
	public void setDevList(String[] devList) {
		// debug
		for (int i = 0; i < devList.length; i++) {
			System.out.println("devList[" + i + "]=" + devList[i]);
		}
		
		this.devList = devList;
	}
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.devList = new String[0];
	}
	
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		return errors;
	}
}
