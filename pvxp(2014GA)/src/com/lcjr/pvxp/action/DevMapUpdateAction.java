package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.lcjr.pvxp.actionform.DevMapUpdateForm;
import com.lcjr.pvxp.model.DevMapModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.pojo.BankPosition;
import com.lcjr.pvxp.pojo.DevMap;
import com.lcjr.pvxp.pojo.DevPosition;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 设备监控地图编辑保存Action
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
 * @version 1.1 2005/03/16
 */

public final class DevMapUpdateAction extends Action {

	Logger log = Logger.getLogger(DevMapUpdateAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("execute开始");

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String bankid = (String) ((DevMapUpdateForm) form).getBankid();
		String banksstr = (String) ((DevMapUpdateForm) form).getBanksstr();
		String devsstr = (String) ((DevMapUpdateForm) form).getDevsstr();
		String imgnmstr = (String) ((DevMapUpdateForm) form).getImgnmstr();
		ActionForward myforward = mapping.findForward("DevMapUpdate_Fail");

		try {
			OperatorModel myOperatorModel = new OperatorModel();
			int mypower = myOperatorModel.getPower(5, request);
			if (mypower < 2) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			PubUtil myPubUtil = new PubUtil();

			String[] tmpbankstr = myPubUtil.split(banksstr, "|");
			int banknum = Integer.parseInt(tmpbankstr[0]);
			BankPosition[] myBankPositionArray = new BankPosition[banknum];
			for (int i = 0; i < banknum; i++) {
				myBankPositionArray[i] = (new BankPosition()).newPosition(tmpbankstr[i + 1]);
			}

			String[] tmpdevstr = myPubUtil.split(devsstr, "|");
			int devnum = Integer.parseInt(tmpdevstr[0]);
			DevPosition[] myDevPositionArray = new DevPosition[devnum];
			for (int i = 0; i < devnum; i++) {
				myDevPositionArray[i] = (new DevPosition()).newPosition(tmpdevstr[i + 1]);
			}

			DevMapModel myDevMapModel = new DevMapModel(bankid);
			DevMap tmpMap = myDevMapModel.getDevMap();

			tmpMap.setBanknum(banknum);
			tmpMap.setBanks(myBankPositionArray);
			tmpMap.setDevnum(devnum);
			tmpMap.setDevs(myDevPositionArray);
			tmpMap.setImgname(imgnmstr);
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
			tmpMap.setLastediter(operid);

			boolean updateSuccess = myDevMapModel.savDevMap(tmpMap);
			OplogModel myOplogModel = new OplogModel();
			if (updateSuccess) {
				myOplogModel.insertOplog(operid, "0", "5", "pvxp.oplog.devmap.edit.success|" + bankid);
				myforward = mapping.findForward("DevMapUpdate_Success");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			} else {
				myOplogModel.insertOplog(operid, "0", "5", "pvxp.oplog.devmap.edit.failed|" + bankid);
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

		} catch (Exception mex) {
			log.error("execute", mex);
			myforward = mapping.findForward("DevMapUpdate_Fail");
			request.removeAttribute(mapping.getAttribute());
			return myforward;
		}
	}

}