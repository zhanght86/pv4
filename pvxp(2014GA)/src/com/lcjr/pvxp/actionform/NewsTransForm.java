package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 消息发送ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2010</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2010/10/09
 */
public final class NewsTransForm extends ActionForm {
	private String[] devlist = new String[0];
	private String news = null;

	public String[] getDevlist() {
		return (this.devlist);
	}
	public String getNews() {
		return (this.news);
	}
	
	public void setDevlist(String[] devlist) {
		this.devlist = devlist;
	}
	public void setNews(String news) {
		this.news = news;
	}
	
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		this.devlist = new String[0];
		this.news = null;
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
	
		ActionErrors errors = new ActionErrors();

		return errors;
	}

}