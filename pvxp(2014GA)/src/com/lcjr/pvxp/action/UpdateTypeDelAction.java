package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.actionform.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.orm.*;

import java.util.Locale;
import java.io.*;
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
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 更新类型删除Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2009
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.0 2009/12/07
 */
public final class UpdateTypeDelAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] updateTypeArry = (String[]) ((UpdateTypeDelForm) form).getUpdateTypeArry();

		PubUtil myPubUtil = new PubUtil();
		String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
		ActionForward myforward = mapping.findForward("SystemError");
		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (authlist.equals("*")) {
				// 超级管理员
			} else {
				// 没有权限
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			if (updateTypeArry == null || updateTypeArry.length == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.updatetype.no.selected");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			String[] result = null;

			try {
				UpdateTypeModel myUpdateTypeModel = new UpdateTypeModel();
				int num = updateTypeArry.length;
				result = new String[num];

				boolean success;
				for (int i = 0; i < num; i++) {
					result[i] = String.valueOf(myUpdateTypeModel.deleteUpdateType(updateTypeArry[i]));
					switch (Integer.parseInt(result[i])) {
					case -1:
						OplogModel.insertOplog(operid, "1", "40", "pvxp.oplog.updatetype.delete.fail|" + updateTypeArry[i]);
						break;
					case 0:
						OplogModel.insertOplog(operid, "1", "40", "pvxp.oplog.updatetype.delete.success|" + updateTypeArry[i]);
						break;
					case 1:
						OplogModel.insertOplog(operid, "1", "40", "pvxp.oplog.updatetype.delete.needrebuild|" + updateTypeArry[i]);
						break;
					}

					// success =
					// devinfoModel.deleteDevByUpdateType(updateTypeArry[i]);

				}
			} catch (Exception e) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			request.setAttribute(Constants.REQUEST_UPDATETYPE_DEL_DONE, result);
			myforward = mapping.findForward("UpdateTypeDelDone");

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}

}
