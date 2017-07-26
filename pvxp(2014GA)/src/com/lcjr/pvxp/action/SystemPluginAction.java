package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.bean.*;
import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.actionform.*;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 系统插件管理-插件列表Action</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.0 2005/03/28
 */
public class SystemPluginAction extends Action {

	public List plugins;
	public PageBean pb;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		ActionForward myforward = null;
		PubUtil pubUtil = new PubUtil();
		try {
			String authlist = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST,request)).trim();
			
			if( !authlist.equals("*") ) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				myforward = mapping.findForward("SystemError");
				return myforward;
			}
			String page = request.getParameter("page");
			String pagesize = request.getParameter("pagesize");
			String ttcount = request.getParameter("totalRow");
			int intpage = 0;
			int intpagesize = Integer.parseInt(pagesize);
			int intmcount = 0;
			PluginModel myPluginModel = new PluginModel();
			
			
			if (page == null) { //第一次,取得个数
				intpage = 1;
				intmcount = myPluginModel.getCount();
			
			} else { //翻页
				intpage = Integer.parseInt(page);
				intmcount = Integer.parseInt(ttcount);
			}
			
			pb = new PageBean(intmcount, intpagesize);
			int ttpage = pb.getTotalPage();
			if( intpage>ttpage ) intpage = ttpage;
			if( intpage<1 ) intpage = 1;
			pb.setCurrentPage(intpage);
			plugins = myPluginModel.getPlugins((intpage-1)*intpagesize,intpagesize);
			
			request.setAttribute(Constants.REQUEST_PLUGINMANAGE_LIST, plugins);
			request.setAttribute(Constants.REQUEST_PLUGINMANAGE_PAGEBEAN, pb);
			
			myforward = mapping.findForward("SysPluginList");
		} catch(Exception e) {
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.system.syserror");
		}
		
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
	
}
