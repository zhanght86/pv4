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

import com.lcjr.pvxp.actionform.MaintainListForm;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.MaintainModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.Bankinfo;
import com.lcjr.pvxp.orm.Maintain;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 报修记录列表Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2011
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.0 2011/02/23
 */
public final class MaintainListAction extends Action {

	Logger log = Logger.getLogger(MaintainListAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward myforward = mapping.findForward("SystemError");

		PubUtil pubUtil = new PubUtil();
		try {
			int myPower = new OperatorModel().getPower(32, request);

			// 没有权限报错
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			String devno = null;
			String date1 = null;
			String date2 = null;

			devno = ((MaintainListForm) form).getDevno();
			date1 = ((MaintainListForm) form).getDate1();
			date2 = ((MaintainListForm) form).getDate2();

			if (date2 == null || date2.length() == 0) {
				date2 = pubUtil.getNowDate(1);
			}

			if (devno == null) {
				devno = "";
			}
			if (date1 == null) {
				date1 = "";
			}

			// 获得错帐总数并计算页数
			int currentPage;
			int pageSize;
			int totalPages;
			int totalMaintainCount;
			int totalCount;

			MaintainModel maintainModel = new MaintainModel();
			List maintainList = null;
			maintainList = maintainModel.getMaintainList(devno, date1, date2);

			if (maintainList == null) {
				totalMaintainCount = 0;
			} else {
				totalMaintainCount = maintainList.size();
			}

			totalCount = totalMaintainCount;

			pageSize = Integer.parseInt(((MaintainListForm) form).getPagesize());
			totalPages = (totalCount / pageSize) + (totalCount % pageSize == 0 ? 0 : 1);
			currentPage = Integer.parseInt(((MaintainListForm) form).getPage());

			if (currentPage > totalPages) {
				currentPage = totalPages;
			}
			if (currentPage < 1) {
				currentPage = 1;
			}

			// 保存需要的信息
			int begin = pageSize * (currentPage - 1);
			int end = begin + pageSize;
			if (end > totalCount) {
				end = totalCount;
			}

			Maintain maintain = new Maintain();
			Vector maintainVector = new Vector(pageSize);
			CharSet charSet = new CharSet();

			for (int i = begin; i < end; i++) {
				maintain = (Maintain) maintainList.get(i);

				String[] maintainItem = new String[8];

				Bankinfo tmp = null;
				maintainItem[0] = pubUtil.dealNull(maintain.getDevno()).trim();

				tmp = BankinfoModel.getBankinfoFromList(pubUtil.dealNull(maintain.getBankid()).trim());
				if (tmp != null)
					maintainItem[1] = tmp.getBanknm();
				else
					maintainItem[1] = pubUtil.dealNull(maintain.getBankid()).trim();

				maintainItem[2] = pubUtil.dealNull(maintain.getTrbtype()).trim();
				maintainItem[3] = pubUtil.dealNull(maintain.getTrbdate()).trim();
				maintainItem[4] = pubUtil.dealNull(maintain.getTrbtime()).trim();
				maintainItem[5] = pubUtil.dealNull(maintain.getState()).trim();
				maintainItem[6] = pubUtil.dealNull(maintain.getRepairs()).trim();
				maintainItem[7] = pubUtil.dealNull(maintain.getTrbphen()).trim();
				maintainVector.add(maintainItem);

			}

			request.setAttribute(Constants.REQUEST_MAINTAIN_CURRENTPAGE, String.valueOf(currentPage));
			request.setAttribute(Constants.REQUEST_MAINTAIN_VECTOR, maintainVector);
			request.setAttribute(Constants.REQUEST_MAINTAIN_TOTALMAINTAINCOUNT, String.valueOf(totalCount));
			request.setAttribute(Constants.REQUEST_MAINTAIN_TOTALPAGES, String.valueOf(totalPages));

			myforward = mapping.findForward("Maintain_List");
		} catch (Exception e) {
			log.error("ERROR", e);
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
		}

		return myforward;
	}
}
