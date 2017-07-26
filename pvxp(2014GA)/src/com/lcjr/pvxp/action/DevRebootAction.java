package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.lcjr.pvxp.actionform.DevRebootForm;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.util.CommUtil;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.EncUtil;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> �ն�����ʱ���޸�Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2009
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.0 2009/10/10
 */
public final class DevRebootAction extends Action {

	Logger log = Logger.getLogger(DevRebootAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.error("execute��ʼ");
		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String devlist[] = (String[]) ((DevRebootForm) form).getDevlist();
		String devtime = (String) ((DevRebootForm) form).getDevtime().trim();

		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");
		try {
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (authlist.equals("*")) {
				// ��������Ա
			} else if (authlist.length() > 0 && Integer.parseInt(authlist.substring(3, 4)) > 1) {
				// �й���Ȩ��
			} else {
				// û��Ȩ��
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			if ((devlist == null) || (devlist.length == 0)) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devinfo.no.selected");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			String result[] = null;
			String myDevip = "";

			try {
				DevinfoModel myDevinfoModel = new DevinfoModel();
				Devinfo myDevinfo = new Devinfo();
				int iport = -1;
				int itimeout = -1;
				String sendpack = "";
				String recvpack = "";
				String temp = "";
				String retcode = "";
				result = new String[devlist.length];

				EncUtil myEncUtil = new EncUtil();
				String passwd = "84888259";

				// ��ȡMCS���������
				CommUtil myCommUtil = new CommUtil();
				iport = Integer.parseInt(myPubUtil.ReadConfig("RemoteControl", "RCPort", "8255", "PowerView.ini"));
				itimeout = Integer.parseInt(myPubUtil.ReadConfig("RemoteControl", "RCTimeOut", "10000", "PowerView.ini"));

				for (int i = 0; i < devlist.length; i++) {
					// ȡ���豸��Ϣ
					myDevinfo = (Devinfo) myDevinfoModel.getDevFromList(devlist[i].trim());
					myDevip = (myDevinfo.getDevip()).trim();

					// ����������
					sendpack = "0026../ini/Mcs.ini|Time|ShutdownTime|" + devtime;
					sendpack = myEncUtil.enPack(sendpack, passwd);
					// ��MCSͨѶ
					recvpack = myCommUtil.commFunc(sendpack, myDevip, iport, itimeout);
					// ������Ӧ����
					if (recvpack == null) {
						result[i] = "-1";
					} else {
						recvpack = recvpack.trim();
						recvpack = myEncUtil.unPack(recvpack, passwd);

						result[i] = recvpack;

					}
				}

			} catch (Exception e) {
				log.error("ERROR", e);
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			request.setAttribute(Constants.REQUEST_REMOTECONTROL_RESULT, result);
			myforward = mapping.findForward("devreboot_result");

		} catch (Exception e) {
			log.error("ERROR", e);
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}