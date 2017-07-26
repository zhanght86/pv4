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
 * <p><b>Description:</b> 插件修改ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.0 2005/03/30
 */
public final class SystemPluginModifyForm extends ActionForm {
	
	private String plugid = null;
	private String plugname = null;
	private String firsturl = null;
	private String plugintype = null;
	private String info = null;
	private String userslist = null;
	
	public void setPlugid(String plugid){
		this.plugid = plugid;
	}
	public void setPlugname(String plugname){
		this.plugname = plugname;
	}
	public void setFirsturl(String firsturl){
		this.firsturl = firsturl;
	}
	public void setPlugintype(String plugintype){
		this.plugintype = plugintype;
	}
	public void setInfo(String info){
		this.info = info;
	}
	public void setUserslist(String userslist){
		this.userslist = userslist;
	}
	
	public String getPlugid(){
		return this.plugid;
	}
	public String getPlugname(){
		return this.plugname;
	}
	public String getFirsturl(){
		return this.firsturl;
	}
	public String getPlugintype(){
		return this.plugintype;
	}
	public String getInfo(){
		return this.info;
	}
	public String getUserslist(){
		return this.userslist;
	}


	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.plugname = null;
		this.firsturl = null;
		this.plugintype = null;
		this.info = null;
		this.userslist = null;
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();
        	
		return errors;
	}
	
}