package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.util.Constants;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 出卡失败记录统计报表列表 
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
 * @version 1.0 2011/10/11
 */
public class CardStaAddForm extends ActionForm {

	/**
	 * 设备编号
	 */
	private String[] devlist;
	/**
	 * 报表名称
	 */
	private String repnm;
	/**
	 * 卡类型
	 */
	private String[] cardtype;
	/**
	 * 出卡日期
	 */
	private String outcarddate1;
	/**
	 * 截止日期
	 */
	private String outcarddate2;
	/**
	 * 开始时间
	 */
	private String outcardtime1;
	/**
	 * 截止时间
	 */
	private String outcardtime2;
	/**
	 * 统计出卡状态
	 */
	private String[] outcardstatus;
	
	
	
	
	
	/**
	 * 获得统计出卡状态
	 * @return the outcardstatus
	 */
	public String[] getOutcardstatus() {
		return outcardstatus;
	}





	/**
	 * 设置 统计出卡状态 
	 * @param outcardstatus the outcardstatus to set
	 */
	public void setOutcardstatus(String[] outcardstatus) {
		this.outcardstatus = outcardstatus;
	}





	/**
	 * 获得卡类型
	 * @return the cardtype
	 */
	public String[] getCardtype() {
		return cardtype;
	}





	/**
	 * 设置 卡类型 
	 * @param cardtype the cardtype to set
	 */
	public void setCardtype(String[] cardtype) {
		this.cardtype = cardtype;
	}





	/**
	 * 获得 设备编号
	 * @return the devlist
	 */
	public String[] getDevlist() {
		return devlist;
	}





	/**
	 * 设置  设备编号
	 * @param devlist the devlist to set
	 */
	public void setDevlist(String[] devlist) {
		this.devlist = devlist;
	}





	/**
	 * 获得开始日期
	 * @return the outcarddate1
	 */
	public String getOutcarddate1() {
		return outcarddate1;
	}





	/**
	 * 设置 开始日期
	 * @param outcarddate1 the outcarddate1 to set
	 */
	public void setOutcarddate1(String outcarddate1) {
		this.outcarddate1 = outcarddate1;
	}





	/**
	 * 获得截止日期
	 * @return the outcarddate2
	 */
	public String getOutcarddate2() {
		return outcarddate2;
	}





	/**
	 * 设置 截止日期
	 * @param outcarddate2 the outcarddate2 to set
	 */
	public void setOutcarddate2(String outcarddate2) {
		this.outcarddate2 = outcarddate2;
	}





	/**
	 * 获得开始时间
	 * @return the outcardtime1
	 */
	public String getOutcardtime1() {
		return outcardtime1;
	}





	/**
	 * 设置 开始时间 
	 * @param outcardtime1 the outcardtime1 to set
	 */
	public void setOutcardtime1(String outcardtime1) {
		this.outcardtime1 = outcardtime1;
	}





	/**
	 * 获得截止时间
	 * @return the outcardtime2
	 */
	public String getOutcardtime2() {
		return outcardtime2;
	}





	/**
	 * 设置 截止时间 
	 * @param outcardtime2 the outcardtime2 to set
	 */
	public void setOutcardtime2(String outcardtime2) {
		this.outcardtime2 = outcardtime2;
	}





	/**
	 * 获得 报表名称
	 * @return the repnm
	 */
	public String getRepnm() {
		return repnm;
	}


	/**
	 * 设置 报表名称 
	 * @param repnm the repnm to set
	 */
	public void setRepnm(String repnm) {
		this.repnm = repnm;
	}

	/**
	 * 验证..
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		
		if ((devlist == null) || (devlist.length == 0)) {
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devinfo.no.selected");
			request.removeAttribute(mapping.getAttribute());
			return errors;
		}
		return errors;
	}
}
