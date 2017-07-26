package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.actionform.UpdateTypeListForm;
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
 * <b>Description:</b> 更新类型列表Action
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
public final class UpdateTypeListAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward myforward = mapping.findForward("SystemError");

		PubUtil util = new PubUtil();
		try {
			String authlist = util.dealNull(util.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			String mybankid = util.dealNull(util.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request));
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

			UpdateTypeModel aUpdateTypeModel = new UpdateTypeModel();

			// 获得类型总数并计算页数
			int currentPage;
			int pageSize;
			int totalPages;
			int totalDevCount;

			List updatetypeList = aUpdateTypeModel.getUpdateTypeList();

			if (updatetypeList == null) {
				totalDevCount = 0;
			} else {
				totalDevCount = updatetypeList.size();
			}
			pageSize = Integer.parseInt(((UpdateTypeListForm) form).getPagesize());
			totalPages = (totalDevCount / pageSize) + (totalDevCount % pageSize == 0 ? 0 : 1);
			try {
				currentPage = Integer.parseInt(((UpdateTypeListForm) form).getPage());
			} catch (Exception e) {
				currentPage = 1;
			}
			if (currentPage > totalPages) {
				currentPage = totalPages;
			}
			if (currentPage < 1) {
				currentPage = 1;
			}

			// 保存需要的设备类型信息
			int begin = pageSize * (currentPage - 1);
			int end = begin + pageSize;
			if (end > totalDevCount) {
				end = totalDevCount;
			}

			Updatetype updatetype = new Updatetype();
			Vector updatetypeVector = new Vector(pageSize);
			CharSet charSet = new CharSet();

			for (int i = begin; i < end; i++) {
				updatetype = (Updatetype) updatetypeList.get(i);

				String[] updatetypeItem = new String[3];

				updatetypeItem[0] = updatetype.getUpdateno();
				updatetypeItem[1] = updatetype.getUpdatename();
				updatetypeItem[2] = updatetype.getInfo();

				updatetypeItem[0] = util.dealNull(updatetypeItem[0]).trim();
				updatetypeItem[1] = util.dealNull(updatetypeItem[1]).trim();
				updatetypeItem[2] = util.dealNull(updatetypeItem[2]).trim();

				updatetypeItem[1] = charSet.db2web(updatetypeItem[1]);
				updatetypeItem[2] = charSet.db2web(updatetypeItem[2]);

				updatetypeVector.add(updatetypeItem);
			}

			request.setAttribute(Constants.REQUEST_UPDATETYPE_CURRENTPAGE, new Integer(currentPage));
			request.setAttribute(Constants.REQUEST_UPDATETYPE_VECTOR, updatetypeVector);
			request.setAttribute(Constants.REQUEST_UPDATETYPE_TOTALDEVCOUNT, new Integer(totalDevCount));
			request.setAttribute(Constants.REQUEST_UPDATETYPE_TOTALPAGES, new Integer(totalPages));

			myforward = mapping.findForward("UpdateType_List");
		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
		}

		request.removeAttribute(mapping.getAttribute());

		return myforward;
	}
}
