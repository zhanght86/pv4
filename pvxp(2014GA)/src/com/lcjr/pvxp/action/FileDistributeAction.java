package com.lcjr.pvxp.action;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.util.StringTokenizer;
import java.util.Vector;

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

import com.lcjr.pvxp.actionform.FileDistributeForm;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.util.BaseCommBean;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> �ļ�����Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * <br>
 * 
 * @author ��̫��
 * @version 1.0 2005/3/16
 */
public final class FileDistributeAction extends Action {

	Logger log = Logger.getLogger(FileDistributeAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();

		// ����豸����б��·��ļ��б��豸·��
		String[] devlist = (String[]) ((FileDistributeForm) form).getDevlist();
		String filestr = (String) ((FileDistributeForm) form).getFilestr();
		String devpath = (String) ((FileDistributeForm) form).getDevpath().trim();

		// ���ù����������� �����ô��󱨸�·��
		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");

		try {

			// ����û��Ƿ���Ȩ��
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

			// ����Ƿ����豸��Ϣѡ��
			if ((devlist == null) || (devlist.length == 0)) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devinfo.no.selected");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			// ��Ҫ�·����ļ��ָ� ��ѹ�����༯��
			StringTokenizer wb = new StringTokenizer(filestr, ",");
			Vector fileVector = new Vector();

			while (wb.hasMoreTokens()) {
				fileVector.add(wb.nextToken());
			}

			// ����ļ��ϴ�·����ini�ļ�·��
			String[][][] result = null;
			String path_upload = getServlet().getServletContext().getRealPath("") + "/upload/" + operid + "/";
			String path_ini = getServlet().getServletContext().getRealPath("") + "/ini/";
			String myDevip = "";

			try {
				// �����豸ģ�ͺ���
				DevinfoModel myDevinfoModel = new DevinfoModel();
				Devinfo myDevinfo = new Devinfo();

				int iport = -1;
				int itimeout = -1;
				String filedelname = "";
				String sendpack = "";
				String recvpack = "";
				String temp = "";
				String retcode = "";

				// ReadFile myReadFile = new ReadFile();

				result = new String[devlist.length][fileVector.size()][1];

				for (int i = 0; i < devlist.length; i++) {

					// ȡ���豸��Ϣ
					myDevinfo = (Devinfo) myDevinfoModel.getDevFromList(devlist[i].trim());
					myDevip = (myDevinfo.getDevip()).trim();

					// ��ȡMCS���������
					BaseCommBean commbean = new BaseCommBean();// SocketͨѶ������

					iport = Integer.parseInt(myPubUtil.ReadConfig("RemoteControl", "RCPort", "8255", "PowerView.ini")); // socketͨ�Ŷ˿�
					itimeout = Integer.parseInt(myPubUtil.ReadConfig("RemoteControl", "RCTimeOut", "10000", "PowerView.ini")); // ��ʱʱ��
					filedelname = "";
					sendpack = ""; // ���Ͱ�
					recvpack = ""; // ���հ�
					temp = "";
					retcode = "";

					for (int j = 0; j < fileVector.size(); j++) {

						// ����������
						sendpack = "0030" + devpath + (String) fileVector.get(j) + "|";

						// ���������ݰ��ĳ���
						FileInputStream inStream = new FileInputStream(path_upload + (String) fileVector.get(j));

						int packlen = sendpack.length() + inStream.available();
						String strpacklen = String.valueOf(packlen);
						int intpacklen = strpacklen.length();

						for (int k = intpacklen; k < 8; k++)
							strpacklen = "0" + strpacklen;// ���ͱ���ͷ�ļ�Ҫ�̶����ȣ�������0
						sendpack = strpacklen + sendpack;

						// ����ͨ��ƽ̨
						if (commbean.connSocket(myDevip, iport) == -1) {
							result[i][j][0] = "-1";
							continue;
						} else if ((commbean.getInStream() == null) || (commbean.getOutStream() == null)) {
							// ����������������
							commbean.socketFree();
							result[i][j][0] = "-1";
							continue;
						} else if (commbean.sendPacket(sendpack) == 0) { // �����͵����ݰ�����Ϊ0ʱ
							// �������ݰ�ͷ
							commbean.socketFree();
							result[i][j][0] = "-1";
							continue;
						} else {
							DataOutputStream outStream = new DataOutputStream(commbean.getOutStream());
							boolean eof = false;
							while (!eof) {
								int c = inStream.read();
								if (c == -1)
									eof = true;
								outStream.write((char) c);
							}
							// �������ݰ�����
							if (commbean.sendFileStream(outStream) == 0) {
								commbean.socketFree();
								result[i][j][0] = "-1";
								continue;
							} else {
								// ���ó�ʱʱ��
								commbean.setTimeOut(itimeout);
								// ���ÿ���ʱ��4��
								commbean.setLinger(4);
								// �������ݰ�
								recvpack = commbean.recvFullPacket();
								// �ͷ�����
								commbean.socketFree();
							}
						}

						// ������Ӧ����
						if (recvpack == null) {
							result[i][j][0] = "-1";
						} else {
							retcode = recvpack.substring(0, 6);
							if (retcode.equals("003100")) {
								result[i][j][0] = "0";
							} else {
								result[i][j][0] = recvpack.substring(6);
							}
						}
					}
				}
			} catch (Exception e) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			// ��¼����Ա������ˮ
			OplogModel myOplogModel = new OplogModel();
			String soperid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			int logflag = 0;
			logflag = myOplogModel.insertOplog(soperid, "0", "3", "pvxp.filedistribute.title|" + myDevip);

			request.setAttribute(Constants.REQUEST_FILEDISTRIBUTE_FILES, fileVector);
			request.setAttribute(Constants.REQUEST_FILEDISTRIBUTE_RESULT, result);
			myforward = mapping.findForward("FD_result");

		} catch (Exception e) {
			log.error("ERROR", e);
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}