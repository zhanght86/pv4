package com.lcjr.pvxp.action;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.DevTpListForm;
import com.lcjr.pvxp.model.DevftModel;
import com.lcjr.pvxp.model.DevtypeModel;
import com.lcjr.pvxp.orm.Devftinfo;
import com.lcjr.pvxp.orm.Devtype;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 设备类型列表Action</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.0 2004/12/31
 */

/**
 * @author xucc
 * @version 1.1 2010/09/29
 */

public final class DevTpListAction extends Action {

	Logger log = Logger.getLogger(DevTpListAction.class.getName());

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

			DevtypeModel aDevtypeModel = new DevtypeModel();

			// 获得设备总数并计算页数
			int currentPage;
			int pageSize;
			int totalPages;
			int totalDevCount;

			List devTypeList = aDevtypeModel.getDevTpList();

			if (devTypeList == null) {
				totalDevCount = 0;
			} else {
				totalDevCount = devTypeList.size();
			}
			pageSize = Integer.parseInt(((DevTpListForm) form).getPagesize());
			totalPages = (totalDevCount / pageSize) + (totalDevCount % pageSize == 0 ? 0 : 1);
			try {
				currentPage = Integer.parseInt(((DevTpListForm) form).getPage());
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

			Devtype devType = new Devtype();
			Vector devTypeVector = new Vector(pageSize);
			CharSet charSet = new CharSet();

			for (int i = begin; i < end; i++) {
				devType = (Devtype) devTypeList.get(i);

				String[] devTypeItem = new String[5];

				devTypeItem[0] = devType.getTypeno();
				devTypeItem[1] = devType.getTypestate();
				devTypeItem[2] = devType.getDevtype();
				devTypeItem[3] = devType.getDevname();
				devTypeItem[4] = devType.getDevftno();

				devTypeItem[0] = util.dealNull(devTypeItem[0]).trim();
				devTypeItem[1] = util.dealNull(devTypeItem[1]).trim();
				devTypeItem[2] = util.dealNull(devTypeItem[2]).trim();
				devTypeItem[3] = util.dealNull(devTypeItem[3]).trim();
				devTypeItem[4] = util.dealNull(devTypeItem[4]).trim();

				devTypeItem[2] = charSet.db2web(devTypeItem[2]);
				devTypeItem[3] = charSet.db2web(devTypeItem[3]);
				devTypeItem[4] = charSet.db2web(devTypeItem[4]);

				Devftinfo devft = DevftModel.getDevftFromList(devTypeItem[4]);
				if (devft == null) {
					devTypeItem[4] = "未知";
				} else {
					devTypeItem[4] = charSet.db2web(devft.getDevftname());
				}

				devTypeVector.add(devTypeItem);
			}

			request.setAttribute(Constants.REQUEST_DEVTP_CURRENTPAGE, new Integer(currentPage));
			request.setAttribute(Constants.REQUEST_DEVTP_VECTOR, devTypeVector);
			request.setAttribute(Constants.REQUEST_DEVTP_TOTALDEVCOUNT, new Integer(totalDevCount));
			request.setAttribute(Constants.REQUEST_DEVTP_TOTALPAGES, new Integer(totalPages));

			myforward = mapping.findForward("DevTp_List");
		} catch (Exception e) {
			// debug
			log.error("ERROR", e);
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
		}

		request.removeAttribute(mapping.getAttribute());

		return myforward;
	}
}
