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

import com.lcjr.pvxp.actionform.FileStateForm;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.util.BaseCommBean;
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
 * <b>Description:</b> Ŀ¼���ݲ�ѯAction
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2008
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.0 2009/9/30
 */
public final class FileStateAction extends Action {

	Logger log = Logger.getLogger(FileStateAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String devno = (String) ((FileStateForm) form).getDevno().trim();
		String devpath = (String) ((FileStateForm) form).getDevpath().trim();

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

			String result = "";
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

				// ȡ���豸��Ϣ
				myDevinfo = (Devinfo) myDevinfoModel.getDevFromList(devno.trim());
				myDevip = (myDevinfo.getDevip()).trim();

				// ��ȡMCS���������
				BaseCommBean commbean = new BaseCommBean();
				CommUtil myCommUtil = new CommUtil();
				iport = Integer.parseInt(myPubUtil.ReadConfig("RemoteControl", "RCPort", "8255", "PowerView.ini"));
				itimeout = Integer.parseInt(myPubUtil.ReadConfig("RemoteControl", "RCTimeOut", "10000", "PowerView.ini"));

				EncUtil myEncUtil = new EncUtil();
				String passwd = "84888259";
				// ����������
				sendpack = "0066" + devpath;
				sendpack = myEncUtil.enPack(sendpack, passwd);
				// ��MCSͨѶ
				recvpack = myCommUtil.commFunc(sendpack, myDevip, iport, itimeout);
				// ������Ӧ����
				if (recvpack == null) {
					result = "-1";
				} else {
					recvpack = recvpack.trim();
					// recvpack = myEncUtil.unPack(recvpack,passwd);

					result = recvpack;

				}

			} catch (Exception e) {
				log.error("ERROR", e);
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			request.setAttribute(Constants.REQUEST_REMOTECONTROL_RESULT, result);
			myforward = mapping.findForward("filestate_result");

		} catch (Exception e) {
			log.error("ERROR", e);
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}