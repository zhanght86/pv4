package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.orm.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 系统设置-删除历史数据文件ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.0 2005/03/31
 */
public final class SystemDelHistoryDataForm extends ActionForm {
	
	private String months = null;
	private String tmpdatastart = null;
	
	public void setMonths(String months){
		this.months = months;
	}
	public void setTmpdatastart(String tmpdatastart){
		this.tmpdatastart = tmpdatastart;
	}
	
	public String getMonths(){
		return this.months;
	}
	public String getTmpdatastart(){
		return this.tmpdatastart;
	}

	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.months = null;
		this.tmpdatastart = null;
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();
		PubUtil pubUtil = new PubUtil();
	   	if( months == null || months.trim().length() == 0 ){
	   		errors.add("deletedata", new ActionMessage("pvxp.syssetup.deldata.nomonths"));
	   		return errors;
	   	}
		if( !pubUtil.isYYYYMM(months.trim()) ){
			errors.add("deletedata", new ActionMessage("pvxp.syssetup.deldata.months.wrongformat"));
			return errors;
		}
		
		if( Integer.parseInt(months)>=Integer.parseInt(tmpdatastart.trim())&&Integer.parseInt(months)<=Integer.parseInt(pubUtil.getBeforePreMonth()) ){
			return errors;
		}else{
			errors.add("deletedata", new ActionMessage("pvxp.syssetup.deldata.months.outofrange"));
			return errors;
		}
	}
}