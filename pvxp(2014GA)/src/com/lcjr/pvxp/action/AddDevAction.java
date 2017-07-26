package com.lcjr.pvxp.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.AddDevForm;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

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
 * @author 吴学坤
 * @version 1.0 2005/03/14
 */
public final class AddDevAction extends Action {

	Logger log = Logger.getLogger(AddDevAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("execute开始");
		ActionForward myForward = mapping.findForward("SystemError");
		PubUtil pubUtil = new PubUtil();
		try {
			String operid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
			int myPower = new OperatorModel().getPower(0, request);
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			DevinfoModel devinfoModel = new DevinfoModel();
			CharSet charSet = new CharSet();

			// 读取表单信息
			String devno = ((AddDevForm) form).getDevno().trim();
			// 判断设备是否已存在
			if (DevinfoModel.indexOfDevList(devno) >= 0) {
				request.setAttribute(Constants.REQUEST_ADDDEV_DONE, "pvxp.dev.add.exist");
				return mapping.findForward("Add_Dev_Done");
			}

			String devip = ((AddDevForm) form).getDevip().trim();
			// 判断IP是否已存在
			List<Devinfo> devs = devinfoModel.getDevList();
			Iterator<Devinfo> iter = devs.iterator();
			while (iter.hasNext()) {
				if (((Devinfo) iter.next()).getDevip().trim().equals(devip)) {
					request.setAttribute(Constants.REQUEST_ADDDEV_DONE, "pvxp.dev.add.ipexist");
					return mapping.findForward("Add_Dev_Done");
				}
			}

			String typeno = ((AddDevForm) form).getTypeno().trim();
			String devmac = ((AddDevForm) form).getDevmac().trim();
			String typestate = ((AddDevForm) form).getTypestate().trim();
			String packtype = ((AddDevForm) form).getPacktype().trim();
			String localtag = ((AddDevForm) form).getLocaltag().trim();
			String polltag = ((AddDevForm) form).getPolltag().trim();
			String bankid = ((AddDevForm) form).getBankid().trim();
			String sysid = ((AddDevForm) form).getSysid().trim();
			String organno = ((AddDevForm) form).getOrganno().trim();
			String tellerno = ((AddDevForm) form).getTellerno().trim();
			String devaddr = charSet.form2db(((AddDevForm) form).getDevaddr().trim());
			String dutyname = charSet.form2db(((AddDevForm) form).getDutyname().trim());
			String organcontact = ((AddDevForm) form).getOrgancontact().trim();
			String autherno = ((AddDevForm) form).getAutherno().trim();
			String devkey = ((AddDevForm) form).getDevkey().trim();
			String pinkey = ((AddDevForm) form).getPinkey().trim();
			String mackey = ((AddDevForm) form).getMackey().trim();
			String remark2 = ((AddDevForm) form).getRemark2().trim();

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

			/*
			 * log.debug("devno=\"" + devno + "\""); log.debug("typeno=\"" +
			 * typeno + "\""); log.debug("devip=\"" + devip + "\"");
			 * log.debug("devmac=\"" + devmac + "\""); log.debug("typestate=\""
			 * + typestate + "\""); log.debug("packtype=\"" + packtype + "\"");
			 * log.debug("localtag=\"" + localtag + "\"");
			 * log.debug("polltag=\"" + polltag + "\""); log.debug("bankid=\"" +
			 * bankid + "\""); log.debug("sysid=\"" + sysid + "\"");
			 * log.debug("organno=\"" + organno + "\""); log.debug("tellerno=\""
			 * + tellerno + "\""); log.debug("devaddr=\"" + devaddr + "\"");
			 * log.debug("dutyname=\"" + dutyname + "\"");
			 * log.debug("organcontact=\"" + organcontact + "\"");
			 * log.debug("autherno=\"" + autherno + "\"");
			 */
			Devinfo dev = new Devinfo();
			dev.setDevno(devno);
			dev.setTypeno(typeno);
			dev.setDevip(devip);
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
			dev.setMackey(mackey);
			dev.setPinkey(pinkey);

			dev.setOpentag("1");
			dev.setRemark2(remark2);
			dev.setRemark3("remark3");
			dev.setRemark4("remark4");
			dev.setSysdatet("0");
			dev.setUpdatetag("2");
			dev.setWaterno("0");

			switch (DevinfoModel.addDev(dev)) {
			case -1:
				request.setAttribute(Constants.REQUEST_ADDDEV_DONE, "pvxp.dev.add.fail");
				OplogModel.insertOplog(operid, "2", "0", "pvxp.oplog.devinfo.add.fail|" + devno);
				break;
			case 0:
				request.setAttribute(Constants.REQUEST_ADDDEV_DONE, "pvxp.dev.add.success");
				OplogModel.insertOplog(operid, "2", "0", "pvxp.oplog.devinfo.add.success|" + devno);
				break;
			case 1:
				DevinfoModel.resetNow();
				request.setAttribute(Constants.REQUEST_ADDDEV_DONE, "pvxp.dev.add.success");
				OplogModel.insertOplog(operid, "2", "0", "pvxp.oplog.devinfo.add.success|" + devno);
				break;
			}
			myForward = mapping.findForward("Add_Dev_Done");

		} catch (Exception e) {
			log.error("ERROR", e);
			myForward = mapping.findForward("SystemError");
		}

		return myForward;
	}
}
