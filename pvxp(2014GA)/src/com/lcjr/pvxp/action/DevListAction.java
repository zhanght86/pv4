package com.lcjr.pvxp.action;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.DevListForm;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.DevtypeModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.Bankinfo;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.orm.Devtype;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 设备列表Action
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
 * @author 吴学坤
 * @version 1.0 2005/02/25
 */
public final class DevListAction extends Action {

	Logger log = Logger.getLogger(DevListAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.debug("execute开始..");

		ActionForward myforward = mapping.findForward("SystemError");

		PubUtil pubUtil = new PubUtil();
		try {
			int myPower = new OperatorModel().getPower(0, request);

			// 没有权限报错
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			boolean includeSubBank = ((DevListForm) form).getIncludeSubBank();
			String bankID = ((DevListForm) form).getBankID();

			if (bankID == null) {
				if (myPower == 3) {
					bankID = "A";
					((DevListForm) form).setIncludeSubBank(true);
				} else {
					bankID = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request));
				}
			}

			String bankRage;
			if (includeSubBank) {
				if (bankID.equals("A")) {
					bankRage = "A";
				} else {
					bankRage = new BankinfoModel().getBankRange(bankID);
				}
			} else {
				bankRage = bankID;
			}

			DevinfoModel devinfoModel = new DevinfoModel();
			List<Devinfo> devs = devinfoModel.getDevListOfBank(bankRage);

			String devno = ((DevListForm) form).getDevno();
			String devip = ((DevListForm) form).getDevip();
			String tellerno = ((DevListForm) form).getTellerno();
			if ((devno != null && devno.length() > 0) || (devip != null && devip.length() > 0) || (tellerno != null && tellerno.length() > 0)) {
				Iterator<Devinfo> iter = devs.iterator();
				while (iter.hasNext()) {
					Devinfo dev = (Devinfo) iter.next();
					if (devno.length() > 0 && dev.getDevno().trim().indexOf(devno) == -1) {
						iter.remove();
					} else if (devip.length() > 0 && dev.getDevip().trim().indexOf(devip) == -1) {
						iter.remove();
					} else if (tellerno.length() > 0) {
						if (dev.getTellerno() == null) {
							iter.remove();
						} else if (dev.getTellerno().trim().indexOf(tellerno) == -1) {
							iter.remove();
						}
					}
				}
			}

			// 获得设备总数并计算页数
			int currentPage;
			int pageSize;
			int totalPages;
			int totalDevCount;

			if (devs == null) {
				totalDevCount = 0;
			} else {
				totalDevCount = devs.size();
			}
			pageSize = Integer.parseInt(((DevListForm) form).getPagesize());
			totalPages = (totalDevCount / pageSize) + (totalDevCount % pageSize == 0 ? 0 : 1);
			currentPage = Integer.parseInt(((DevListForm) form).getPage());
			if (currentPage > totalPages) {
				currentPage = totalPages;
			}
			if (currentPage < 1) {
				currentPage = 1;
			}

			// 保存需要的设备信息
			int begin = pageSize * (currentPage - 1);
			int end = begin + pageSize;
			if (end > totalDevCount) {
				end = totalDevCount;
			}

			Devinfo devInfo = new Devinfo();
			Bankinfo bankInfo = null;
			Devtype devType = null;
			Vector<String[]> devVector = new Vector<String[]>(pageSize);
			CharSet charSet = new CharSet();

			for (int i = begin; i < end; i++) {
				devInfo = (Devinfo) devs.get(i);

				bankInfo = BankinfoModel.getBankinfoFromList(devInfo.getBankid());
				devType = DevtypeModel.getDevTpFromList(devInfo.getTypeno());

				String[] devItem = new String[7];

				devItem[0] = (devInfo.getDevno());
				if (devType != null) {
					devItem[1] = (charSet.db2web(devType.getDevname()));
				} else {
					devItem[1] = "未知";
				}
				if (bankInfo != null) {
					devItem[2] = (charSet.db2web(bankInfo.getBanknm()));
				} else {
					devItem[2] = "未知";
				}
				devItem[3] = (devInfo.getDevip());
				devItem[4] = (devInfo.getTypestate());
				devItem[5] = (charSet.db2web(pubUtil.dealNull(devInfo.getDevaddr()).trim()));
				devItem[6] = (charSet.db2web(pubUtil.dealNull(devInfo.getDevkey()).trim()));

				devVector.add(devItem);
			}

			request.setAttribute(Constants.REQUEST_DEVLIST_CURRENTPAGE, new Integer(currentPage));
			request.setAttribute(Constants.REQUEST_DEVLIST_DEVVECTOR, devVector);
			request.setAttribute(Constants.REQUEST_DEVLIST_TOTALDEVCOUNT, new Integer(totalDevCount));
			request.setAttribute(Constants.REQUEST_DEVLIST_TOTALPAGES, new Integer(totalPages));

			myforward = mapping.findForward("Dev_List");
		} catch (Exception e) {
			log.error("ERROR", e);
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
		}
		return myforward;
	}
}