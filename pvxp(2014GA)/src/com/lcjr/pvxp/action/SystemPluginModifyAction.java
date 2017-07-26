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
 * <b>Description:</b> 系统插件管理-插件修改Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author 杨旭
 * @version 1.0 2005/03/30
 */
public class SystemPluginModifyAction extends Action {

	public List plugins;
	public PageBean pb;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward myforward = new ActionForward(mapping.getInput());
		ActionMessages errors = new ActionMessages();
		PubUtil pubUtil = new PubUtil();
		try {
			String authlist = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			OplogModel myOplogModel = new OplogModel();
			String operid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
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

			String[] usersArray = pubUtil.split(userslist, "\n");
			if (usersArray == null) {
				userslist = "";
			} else {
				int arraylen = usersArray.length;
				userslist = ",";
				for (int i = 0; i < arraylen; i++) {
					String tmpstr = usersArray[i].trim();
					if (tmpstr.length() > 0)
						userslist += (tmpstr + ",");
				}
			}

			plugid = pubUtil.strFormat(plugid.trim(), 10, 1, '0');

			PluginModel myPluginModel = new PluginModel();

			Plugin tmp = myPluginModel.getPlugin(plugid);

			CharSet myCharSet = new CharSet();
			tmp.setPlugname(myCharSet.form2db(plugname));
			tmp.setPlugtype(plugintype);
			tmp.setFirsturl(firsturl);
			tmp.setInfo(myCharSet.form2db(info));// 解决描述信息乱码问题 xucc 20090625
			tmp.setUserslist(userslist);

			if (myPluginModel.updatePlugin(tmp) == 0) {
				myOplogModel.insertOplog(operid, "0", "sys", "pvxp.oplog.syssetup.plugin.modify.success|" + plugid);
				errors.add("plugmodifyresult", new ActionMessage("pvxp.syssetup.plugin.modify.success", plugid));
			} else {
				myOplogModel.insertOplog(operid, "0", "sys", "pvxp.oplog.syssetup.plugin.modify.failed");
				errors.add("plugmodifyresult", new ActionMessage("pvxp.syssetup.plugin.modify.failed", plugid));
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
