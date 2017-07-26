package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.ModifyDevForm;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

//import org.apache.log4j.*;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 添加设备Action
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
 * @author 武坤鹏
 * @version 1.0 2012/03/07
 */
public final class ModifyDevAction extends Action {

	Logger log = Logger.getLogger(ModifyDevAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward myForward = mapping.findForward("SystemError");
		PubUtil pubUtil = new PubUtil();
		try {
			String operid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
			int myPower = new OperatorModel().getPower(0, request);
			// 没有权限报错
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			CharSet charSet = new CharSet();

			String devno = ((ModifyDevForm) form).getDevno();
			String devip = ((ModifyDevForm) form).getDevip();
			String typeno = ((ModifyDevForm) form).getTypeno();
			String devmac = ((ModifyDevForm) form).getDevmac();
			String typestate = ((ModifyDevForm) form).getTypestate();
			String packtype = ((ModifyDevForm) form).getPacktype();
			String localtag = ((ModifyDevForm) form).getLocaltag();
			String polltag = ((ModifyDevForm) form).getPolltag();
			String bankid = ((ModifyDevForm) form).getBankid();
			String sysid = ((ModifyDevForm) form).getSysid();
			String organno = ((ModifyDevForm) form).getOrganno();
			String tellerno = ((ModifyDevForm) form).getTellerno();
			String devaddr = charSet.form2db(((ModifyDevForm) form).getDevaddr());
			String dutyname = charSet.form2db(((ModifyDevForm) form).getDutyname());
			String organcontact = ((ModifyDevForm) form).getOrgancontact();
			String autherno = charSet.form2db(((ModifyDevForm) form).getAutherno());
			String devkey = ((ModifyDevForm) form).getDevkey();
			String pinkey = ((ModifyDevForm) form).getPinkey();
			String mackey = ((ModifyDevForm) form).getMackey();
			String remark2 = ((ModifyDevForm) form).getRemark2();

			/*
			 * 由于目前的交易平台不允许数据库字段含有空值， 有些数据库会把空串和只含有空格的串转化为空值， 所以这里把空串替换成"-"。
			 */
			if (devmac.length() == 0) {
				devmac = "-";
			}

			if (sysid.length() == 0) {
				sysid = "-";
			}

			if (organno.length() == 0) {
				organno = "-";
			}

			if (tellerno.length() == 0) {
				tellerno = "-";
			}

			if (devaddr.length() == 0) {
				devaddr = "-";
			}

			if (organcontact.length() == 0) {
				organcontact = "-";
			}

			if (autherno.length() == 0) {
				autherno = "-";
			}

			if (devkey.length() == 0) {
				devkey = "-";
			}

			if (pinkey.length() == 0) {
				pinkey = "-";
			}

			if (mackey.length() == 0) {
				mackey = "-";
			}

			if (remark2.length() == 0) {
				remark2 = "-";
			}

			Devinfo dev = DevinfoModel.getDevFromList(devno);
			if (dev != null) {
				dev.setDevip(devip);
				dev.setTypeno(typeno);
				dev.setDevmac(devmac);
				dev.setTypestate(typestate);
				dev.setPacktype(packtype);
				dev.setLocaltag(localtag);
				dev.setPolltag(polltag);
				dev.setBankid(bankid);
				dev.setSysid(sysid);
				dev.setOrganno(organno);
				dev.setTellerno(tellerno);
				dev.setDevaddr(devaddr);
				dev.setDutyname(dutyname);
				dev.setOrgancontact(organcontact);
				dev.setAutherno(autherno);
				dev.setDevkey(devkey);
				dev.setPinkey(pinkey);
				dev.setMackey(mackey);
				dev.setRemark2(remark2);
			}

			int ret = DevinfoModel.updateDev(dev, devno);
			if (ret == -1) {
				OplogModel.insertOplog(operid, "0", "0", "pvxp.oplog.devinfo.modify.fail|" + devno);
				request.setAttribute(Constants.REQUEST_MODIFYDEV_DONE, "pvxp.dev.modify.fail");
			} else if (ret == 0) {
				OplogModel.insertOplog(operid, "0", "0", "pvxp.oplog.devinfo.modify.success|" + devno);
				request.setAttribute(Constants.REQUEST_MODIFYDEV_DONE, "pvxp.dev.modify.success");
			} else if (ret == 1) {
				OplogModel.insertOplog(operid, "0", "0", "pvxp.oplog.devinfo.modify.success|" + devno);
				DevinfoModel.resetNow();
				request.setAttribute(Constants.REQUEST_MODIFYDEV_DONE, "pvxp.dev.modify.success");
			}

			myForward = mapping.findForward("Modify_Dev_Done");
		} catch (Exception e) {
			myForward = mapping.findForward("SystemError");
		}
		return myForward;
	}
}
