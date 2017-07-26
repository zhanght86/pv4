package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.DevDetailForm;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.DevtypeModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.model.UpdateTypeModel;
import com.lcjr.pvxp.orm.Bankinfo;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.orm.Devtype;
import com.lcjr.pvxp.orm.Updatetype;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 设备详细信息Action
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
 * @version 1.0 2005/03/15
 */
public final class DevDetailAction extends Action {

	Logger log = Logger.getLogger(DevDetailAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("execute开始");
		ActionForward myforward = mapping.findForward("SystemError");

		PubUtil pubUtil = new PubUtil();
		try {
			int myPower = new OperatorModel().getPower(0, request);
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			String devNo = (String) ((DevDetailForm) form).getDevNo();

			Devinfo dev = DevinfoModel.getDevFromList(devNo);
			if (dev == null) {
				myforward = mapping.findForward("SystemError");
				return myforward;
			}

			CharSet charSet = new CharSet();
			String[] devDetail = new String[19];
			devDetail[0] = pubUtil.dealNull(dev.getDevno()).trim(); // 设备编号
			devDetail[1] = pubUtil.dealNull(dev.getDevip()).trim(); // IP地址
			devDetail[2] = pubUtil.dealNull(dev.getDevmac()).trim(); // 网卡MAC
			devDetail[3] = pubUtil.dealNull(dev.getTypeno()).trim(); // 设备类型
			devDetail[4] = pubUtil.dealNull(dev.getSysid()).trim(); // 更新类型
			devDetail[5] = pubUtil.dealNull(dev.getOrganno()).trim(); // 网点编号
			devDetail[6] = pubUtil.dealNull(dev.getTellerno()).trim(); // 柜员编号
			devDetail[7] = pubUtil.dealNull(dev.getBankid()).trim(); // 所属机构
			devDetail[8] = charSet.db2web(pubUtil.dealNull(dev.getDevaddr()).trim()); // 安装地址
			devDetail[9] = charSet.db2web(pubUtil.dealNull(dev.getDutyname()).trim());// 维
																						// 护
			// 人
			devDetail[10] = pubUtil.dealNull(dev.getOrgancontact()).trim(); // 联系电话
			devDetail[11] = pubUtil.dealNull(dev.getAutherno()).trim(); // 授权柜员
			devDetail[12] = pubUtil.dealNull(dev.getTypestate()).trim(); // 设备状态
			devDetail[13] = pubUtil.dealNull(dev.getPacktype()).trim(); // 数据类型
			devDetail[14] = pubUtil.dealNull(dev.getLocaltag()).trim(); // 本地标志
			devDetail[15] = pubUtil.dealNull(dev.getPolltag()).trim(); // 轮询标志
			devDetail[16] = pubUtil.dealNull(dev.getDevkey()).trim(); // 版本号
			devDetail[17] = pubUtil.dealNull(dev.getPinkey()).trim(); // 更新时间
			devDetail[18] = pubUtil.dealNull(dev.getRemark2()).trim(); // 更新时间

			Devtype devType = DevtypeModel.getDevTpFromList(devDetail[3]);
			if (devType == null) {
				devDetail[3] = "未知";
			} else {
				devDetail[3] = charSet.db2web(devType.getDevname());
			}

			Bankinfo bankInfo = BankinfoModel.getBankinfoFromList(devDetail[7]);
			if (bankInfo == null) {
				devDetail[7] = "未知";
			} else {
				devDetail[7] = charSet.db2web(bankInfo.getBanknm());
			}

			UpdateTypeModel updateTypeModel = new UpdateTypeModel();
			Updatetype updateType = updateTypeModel.getUpdateTypeFromList(devDetail[4]);
			if (updateType == null) {
				devDetail[4] = "未知";
			} else {
				devDetail[4] = charSet.db2web(updateType.getUpdatename());
			}

			if (devDetail[17].length() == 14) {
				String tmp = devDetail[17];
				devDetail[17] = tmp.substring(0, 4) + "-" + tmp.substring(4, 6) + "-" + tmp.substring(6, 8) + " " + tmp.substring(8, 10) + ":" + tmp.substring(10, 12) + ":" + tmp.substring(12, 14);
			}

			request.setAttribute(Constants.REQUEST_DEVDETAIL, devDetail);

			myforward = mapping.findForward("Dev_Detail");
		} catch (Exception e) {
			log.error("ERROR", e);
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
		}

		request.removeAttribute(mapping.getAttribute());

		// log.debug("Exit execute\n");
		return myforward;
	}
}
