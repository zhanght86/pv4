package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.lcjr.pvxp.actionform.DevTpDetailForm;
import com.lcjr.pvxp.model.DevftModel;
import com.lcjr.pvxp.model.DevtypeModel;
import com.lcjr.pvxp.orm.Devftinfo;
import com.lcjr.pvxp.orm.Devtype;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 设备类型详细信息Action</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 吴学坤
 * @version 1.0 2005/03/13
 */

/**
 * @author xucc
 * @Description:修改厂商名称为编号，去掉联系方式
 * @version 1.1 2010/09/29
 */
public final class DevTpDetailAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// debug
		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String devTypeNo = (String) ((DevTpDetailForm) form).getDevTypeNo();

		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");

		if (devTypeNo == null || devTypeNo.length() == 0) {
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

			Devtype devType = DevtypeModel.getDevTpFromList(devTypeNo);
			// 设备类型不存在
			if (devType == null) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.devtype.no");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			CharSet charSet = new CharSet();

			String[] devTypeDetail = new String[7];
			devTypeDetail[0] = devType.getTypeno();
			devTypeDetail[1] = devType.getTypestate();
			devTypeDetail[2] = devType.getDevtype();
			devTypeDetail[3] = devType.getDevname();
			devTypeDetail[4] = devType.getDevinfo();
			devTypeDetail[5] = devType.getPacktype();
			devTypeDetail[6] = devType.getDevftno();

			devTypeDetail[0] = myPubUtil.dealNull(devTypeDetail[0]);
			devTypeDetail[1] = myPubUtil.dealNull(devTypeDetail[1]);
			devTypeDetail[2] = myPubUtil.dealNull(devTypeDetail[2]);
			devTypeDetail[3] = myPubUtil.dealNull(devTypeDetail[3]);
			devTypeDetail[4] = myPubUtil.dealNull(devTypeDetail[4]);
			devTypeDetail[5] = myPubUtil.dealNull(devTypeDetail[5]);
			devTypeDetail[6] = myPubUtil.dealNull(devTypeDetail[6]);

			devTypeDetail[2] = charSet.db2web(devTypeDetail[2]);
			devTypeDetail[3] = charSet.db2web(devTypeDetail[3]);
			devTypeDetail[4] = charSet.db2web(devTypeDetail[4]);

			Devftinfo devft = DevftModel.getDevftFromList(devTypeDetail[6]);
			if (devft == null) {
				devTypeDetail[6] = "未知";
			} else {
				devTypeDetail[6] = charSet.db2web(devft.getDevftname());
			}

			request.setAttribute(Constants.REQUEST_DEVTP, devTypeDetail);

			myforward = mapping.findForward("DevTp_Detail");
		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		// debug
		request.removeAttribute(mapping.getAttribute());
		return myforward;

	}

}
