package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.actionform.UpdateTypeDetailForm;
import com.lcjr.pvxp.model.UpdateTypeModel;
import com.lcjr.pvxp.orm.Updatetype;

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
 * <b>Description:</b> 更新类型详细信息Action
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
public final class UpdateTypeDetailAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String updateno = (String) ((UpdateTypeDetailForm) form).getUpdateno();

		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");

		if (updateno == null || updateno.length() == 0) {
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.submit.wrong");
			request.removeAttribute(mapping.getAttribute());
			return myforward;
		}

		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			String myPower = "";
			if (authlist.equals("*")) {
				// 超级管理员
				myPower = "*";
			} else if (authlist.length() > 0 && Integer.parseInt(authlist.substring(0, 1)) > 0) {
				// 有浏览以上级别权限
				myPower = authlist.substring(0, 1);
			} else {
				// 没有权限
				myPower = "0";
			}
			// 保存当前操作的权限
			request.setAttribute(Constants.REQUEST_OPER_POWER, myPower);
			// 没有权限报错
			if (myPower.equals("0")) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			Updatetype updatetype = UpdateTypeModel.getUpdateTypeFromList(updateno);
			// 设备类型不存在
			if (updatetype == null) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.updatetype.no");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			CharSet charSet = new CharSet();

			String[] updatetypeDetail = new String[3];
			updatetypeDetail[0] = updatetype.getUpdateno();
			updatetypeDetail[1] = updatetype.getUpdatename();
			updatetypeDetail[2] = updatetype.getInfo();

			updatetypeDetail[0] = myPubUtil.dealNull(updatetypeDetail[0]);
			updatetypeDetail[1] = myPubUtil.dealNull(updatetypeDetail[1]);
			updatetypeDetail[2] = myPubUtil.dealNull(updatetypeDetail[2]);

			updatetypeDetail[1] = charSet.db2web(updatetypeDetail[1]);
			updatetypeDetail[2] = charSet.db2web(updatetypeDetail[2]);

			request.setAttribute(Constants.REQUEST_UPDATETYPE, updatetypeDetail);

			myforward = mapping.findForward("UpdateType_Detail");
		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;

	}

}
