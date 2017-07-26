package com.lcjr.pvxp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.model.PluginModel;
import com.lcjr.pvxp.orm.Plugin;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PageBean;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 系统插件管理-插件添加Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005 <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author 杨旭
 * @version 1.0 2005/03/30
 */
public class SystemPluginAddAction extends Action {

	public List plugins;

	public PageBean pb;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward myforward = new ActionForward(mapping.getInput());
		ActionMessages errors = new ActionMessages();
		PubUtil myPubUtil = new PubUtil();
		try {
			// OplogModel myOplogModel = new OplogModel();
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();

			if (!authlist.equals("*")) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				myforward = mapping.findForward("SystemError");
				return myforward;
			}

			request.setAttribute(Constants.REQUEST_PLUGINADD_FLAG, "1");

			String plugid = request.getParameter("plugid");
			String plugname = request.getParameter("plugname");
			String firsturl = request.getParameter("firsturl");
			String plugintype = request.getParameter("plugintype");
			String info = request.getParameter("info");
			String userslist = request.getParameter("userslist");

			String[] usersArray = myPubUtil.split(userslist, "\n");
			if (usersArray == null) {
				userslist = "";
			} else {
				int arraylen = usersArray.length;
				userslist = ",";
				for (int i = 0; i < arraylen; i++) {
					String tmpstr = usersArray[i].trim();
					if (tmpstr.length() > 0 && userslist.indexOf("," + tmpstr + ",") == -1)
						userslist += (tmpstr + ",");
				}
			}

			plugid = myPubUtil.strFormat(plugid.trim(), 10, 1, '0');

			// PluginModel myPluginModel = new PluginModel();

			Plugin tmp = new Plugin();
			CharSet myCharSet = new CharSet();
			tmp = new Plugin();
			tmp.setPlugid(plugid);
			tmp.setPlugname(myCharSet.form2db(plugname));
			tmp.setPlugtype(plugintype);
			tmp.setFirsturl(firsturl);
			tmp.setInfo(info);
			tmp.setUserslist(userslist);

			if (PluginModel.addPlugin(tmp) == 0) {
				OplogModel.insertOplog(operid, "2", "sys", "pvxp.oplog.syssetup.plugin.add.success|" + plugid);
				request.setAttribute(Constants.REQUEST_PLUGINADD_FLAG, "0");
				errors.add("plugaddresult", new ActionMessage("pvxp.syssetup.plugin.add.success", plugid));
			} else {
				OplogModel.insertOplog(operid, "2", "sys", "pvxp.oplog.syssetup.plugin.add.failed");
				request.setAttribute(Constants.REQUEST_PLUGINADD_FLAG, "0");
				errors.add("plugaddresult", new ActionMessage("pvxp.syssetup.plugin.add.failed", plugid));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
			}

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}

}
