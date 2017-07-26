package com.lcjr.pvxp.actionform;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 卡凭证状态查询CardState 表 zzt_card_status
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2011
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author 武坤鹏
 * @version 1.0 2011/10/08
 */
public class CardStatusForm extends ActionForm {
	/**
	 * 设备编号
	 */
	private String[] termnum;
	
	
	
	/**
	 * 机构编号
	 */
	private String bankID;
	
	
	
	
	/**
	 * 获得bankID
	 * 
	 * @return the bankID
	 */
	public String getBankID() {
		return bankID;
	}
	
	
	
	/**
	 * 设置 bankID
	 * 
	 * @param bankID
	 *            the bankID to set
	 */
	public void setBankID(String bankID) {
		this.bankID = bankID;
	}
	
	
	
	/**
	 * @return the termnum
	 */
	public String[] getTermnum() {
		return termnum;
	}
	
	
	
	/**
	 * @param termnum
	 *            the termnum to set
	 */
	public void setTermnum(String[] termnum) {
		this.termnum = termnum;
		// for(int i=0;i<termnum.length;i++){
		// System.out.println(termnum[i]);
		// }
	}
	
	
	
	/**
	 * 
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		return errors;
	}
}
