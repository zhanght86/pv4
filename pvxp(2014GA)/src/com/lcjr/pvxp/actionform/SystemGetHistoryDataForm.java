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
 * <p><b>Description:</b> 系统设置-倒入历史数据ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.0 2005/03/31
 */
public final class SystemGetHistoryDataForm extends ActionForm {
	
	private String months = null;
	private String tmpdatastart = null;
	private String tmpmonths = null;
	
	
	public void setMonths(String months){
		this.months = months;
	}
	public void setTmpdatastart(String tmpdatastart){
		this.tmpdatastart = tmpdatastart;
	}
	public void setTmpmonths(String tmpmonths){
		this.tmpmonths = tmpmonths;
	}
	
	
	public String getMonths(){
		return this.months;
	}
	public String getTmpdatastart(){
		return this.tmpdatastart;
	}
	public String getTmpmonths(){
		return this.tmpmonths;
	}
	
	
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.months = null;
		this.tmpdatastart = null;
		this.tmpmonths = null;
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
		
		ActionErrors errors = new ActionErrors();
		PubUtil pubUtil = new PubUtil();
	   	if( months == null || months.trim().length() == 0 ){
	   		errors.add("getdata", new ActionMessage("pvxp.syssetup.getdata.nomonths"));
	   		return errors;
	   	}
	   	
	   	String[] tmpArray = pubUtil.split( months , "," );
	   	if( tmpArray == null || tmpArray.length == 0 ){
	   		errors.add("getdata", new ActionMessage("pvxp.syssetup.getdata.months.wrongformat"));
	   		return errors;	
	   	}
	   	int len = tmpArray.length;
	   	int monthscount = 0;

	   	tmpmonths = "";
	   	for( int i=0;i<len;i++ ){
			String tmpstr = tmpArray[i].trim();
			if( tmpstr.length()>0 ){
				if( tmpstr.length()==6  && pubUtil.isYYYYMM(tmpstr)  ){
					if( Integer.parseInt(tmpstr)>=Integer.parseInt(tmpdatastart.trim())&&Integer.parseInt(tmpstr)<=Integer.parseInt(pubUtil.getBeforePreMonth()) ){
						tmpmonths += ( "|" + tmpstr );
						monthscount++;
					}else{
						errors.add("getdata", new ActionMessage("pvxp.syssetup.getdata.months.outofrange",tmpstr));
						return errors;
					}
				}else{
					errors.add("getdata", new ActionMessage("pvxp.syssetup.getdata.months.wrongformat"));
					return errors;
				}
			}
		}
	   	if( monthscount>24 ){
			errors.add("getdata", new ActionMessage("pvxp.syssetup.getdata.months.morethan24"));
			return errors;
	   	}else if(monthscount==0){
			errors.add("getdata", new ActionMessage("pvxp.syssetup.getdata.nomonths"));
			return errors;
		}
		if( tmpmonths.length()>0 ) tmpmonths = tmpmonths.substring(1);


		return errors;
	}
}