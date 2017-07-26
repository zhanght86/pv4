package com.lcjr.pvxp.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.DevMapListForm;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.DevMapModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.Bankinfo;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 设备地图列表Action
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
 * @version 1.0 2005/03/19
 */

public final class DevMapListAction extends Action {

	Logger log = Logger.getLogger(DevMapListAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward myforward = mapping.findForward("SystemError");

		PubUtil myPubUtil = new PubUtil();
		try {
			String mybankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request));
			OperatorModel myOperatorModel = new OperatorModel();
			int mypower = myOperatorModel.getPower(5, request);
			if (mypower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
			BankinfoModel myBankinfoModel = new BankinfoModel();
			Vector myBankVector = myBankinfoModel.getSubBank(mybankid, 1);

			// 获得设备总数并计算页数
			int currentPage;
			int pageSize;
			int totalPages;
			int totalDevMapCount = 0;
			Vector devMapBankVector = new Vector();

			// List devMapList = DevtypeModel.getDevMapList();

			if (myBankVector == null) {
				totalDevMapCount = 0;
			} else {
				int len = myBankVector.size();
				for (int i = 0; i < len; i++) {
					Bankinfo tmpBankinfo = (Bankinfo) myBankVector.get(i);
					if (tmpBankinfo != null) {
						DevMapModel tmpDevMapModel = new DevMapModel(myPubUtil.dealNull((tmpBankinfo.getBankid())).trim());
						// if(tmpDevMapModel.getMapname()!=null){
						devMapBankVector.add(tmpBankinfo);
						// }
					}
				}
				if (devMapBankVector == null)
					totalDevMapCount = 0;
				else
					totalDevMapCount = devMapBankVector.size();
			}
			pageSize = Integer.parseInt(((DevMapListForm) form).getPagesize());
			totalPages = (totalDevMapCount / pageSize) + (totalDevMapCount % pageSize == 0 ? 0 : 1);
			currentPage = Integer.parseInt(((DevMapListForm) form).getPage());
			if (currentPage > totalPages) {
				currentPage = totalPages;
			}
			if (currentPage < 1) {
				currentPage = 1;
			}

			// 保存需要的设备类型信息
			int begin = pageSize * (currentPage - 1);
			int end = begin + pageSize;
			if (end > totalDevMapCount) {
				end = totalDevMapCount;
			}

			Vector retDevMapBankVector = new Vector();

			for (int i = begin; i < end; i++) {
				retDevMapBankVector.add(devMapBankVector.get(i));
			}

			request.setAttribute(Constants.REQUEST_DEVMAP_CURRENTPAGE, new Integer(currentPage));
			request.setAttribute(Constants.REQUEST_DEVMAP_VECTOR, retDevMapBankVector);
			request.setAttribute(Constants.REQUEST_DEVMAP_TOTALDEVCOUNT, new Integer(totalDevMapCount));
			request.setAttribute(Constants.REQUEST_DEVMAP_TOTALPAGES, new Integer(totalPages));

			myforward = mapping.findForward("DevMap_List");
		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}
