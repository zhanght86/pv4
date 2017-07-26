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

import com.lcjr.pvxp.actionform.DevftListForm;
import com.lcjr.pvxp.model.DevftModel;
import com.lcjr.pvxp.orm.Devftinfo;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 设备厂商列表Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2010
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.0 2010/09/26
 */
public final class DevftListAction extends Action {

	Logger log = Logger.getLogger(DevftListAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("execute开始");
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

			DevftModel aDevftModel = new DevftModel();

			// 获得设备厂商总数并计算页数
			int currentPage = 0;
			int pageSize = 0;
			int totalPages = 0;
			int totalDevftCount = 0;

			List devftList = aDevftModel.getDevftList();

			if (devftList == null) {
				totalDevftCount = 0;
			} else {
				totalDevftCount = devftList.size();
			}
			pageSize = Integer.parseInt(((DevftListForm) form).getPagesize());
			totalPages = (totalDevftCount / pageSize) + (totalDevftCount % pageSize == 0 ? 0 : 1);
			try {
				currentPage = Integer.parseInt(((DevftListForm) form).getPage());
			} catch (Exception e) {
				currentPage = 1;
			}
			if (currentPage > totalPages) {
				currentPage = totalPages;
			}
			if (currentPage < 1) {
				currentPage = 1;
			}

			// 保存需要的设备厂商信息
			int begin = pageSize * (currentPage - 1);
			int end = begin + pageSize;
			if (end > totalDevftCount) {
				end = totalDevftCount;
			}

			Devftinfo devft = new Devftinfo();
			Vector devftVector = new Vector(pageSize);
			CharSet charSet = new CharSet();

			for (int i = begin; i < end; i++) {
				devft = (Devftinfo) devftList.get(i);

				String[] devftItem = new String[9];

				devftItem[0] = devft.getDevftno();
				devftItem[1] = devft.getDevftname();
				devftItem[2] = devft.getContact1();
				devftItem[3] = devft.getPhone1();
				devftItem[4] = devft.getMobile1();
				devftItem[5] = devft.getContact2();
				devftItem[6] = devft.getPhone2();
				devftItem[7] = devft.getMobile2();
				devftItem[8] = devft.getRemark4();

				devftItem[0] = util.dealNull(devftItem[0]).trim();
				devftItem[1] = util.dealNull(devftItem[1]).trim();
				devftItem[2] = util.dealNull(devftItem[2]).trim();
				devftItem[3] = util.dealNull(devftItem[3]).trim();
				devftItem[4] = util.dealNull(devftItem[4]).trim();
				devftItem[5] = util.dealNull(devftItem[5]).trim();
				devftItem[6] = util.dealNull(devftItem[6]).trim();
				devftItem[7] = util.dealNull(devftItem[7]).trim();
				devftItem[8] = util.dealNull(devftItem[8]).trim();

				devftItem[1] = charSet.db2web(devftItem[1]);
				devftItem[2] = charSet.db2web(devftItem[2]);
				devftItem[3] = charSet.db2web(devftItem[3]);
				devftItem[4] = charSet.db2web(devftItem[4]);
				devftItem[5] = charSet.db2web(devftItem[5]);
				devftItem[6] = charSet.db2web(devftItem[6]);
				devftItem[7] = charSet.db2web(devftItem[7]);
				devftItem[8] = charSet.db2web(devftItem[8]);

				devftVector.add(devftItem);
			}

			request.setAttribute(Constants.REQUEST_DEVFT_CURRENTPAGE, String.valueOf(currentPage));
			request.setAttribute(Constants.REQUEST_DEVFT_VECTOR, devftVector);
			request.setAttribute(Constants.REQUEST_DEVFT_TOTALDEVFTCOUNT, String.valueOf(totalDevftCount));
			request.setAttribute(Constants.REQUEST_DEVFT_TOTALPAGES, String.valueOf(totalPages));

			myforward = mapping.findForward("Devft_List");
		} catch (Exception e) {
			log.info("execute", e);
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
		}

		request.removeAttribute(mapping.getAttribute());

		return myforward;
	}
}
