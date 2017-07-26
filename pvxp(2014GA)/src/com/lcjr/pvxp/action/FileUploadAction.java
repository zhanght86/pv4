package com.lcjr.pvxp.action;

import java.io.DataInputStream;
import java.io.FileOutputStream;

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

import com.lcjr.pvxp.actionform.FileUploadForm;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.util.BaseCommBean;
import com.lcjr.pvxp.util.CommUtil;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 文件获取Action
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
 * @author 刘太沛
 * @version 1.0 2005/3/20
 */
public final class FileUploadAction extends Action {

	Logger log = Logger.getLogger(FileUploadAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String devno = (String) ((FileUploadForm) form).getDevno();
		String devpath = (String) ((FileUploadForm) form).getDevpath().trim();
		String filestr = (String) ((FileUploadForm) form).getFilestr();

		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");
		try {
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (authlist.equals("*")) {
				// 超级管理员
			} else if (authlist.length() > 0 && Integer.parseInt(authlist.substring(3, 4)) > 1) {
				// 有管理权限
			} else {
				// 没有权限
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			if ((devno == null) || (devno.equals(""))) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devinfo.no.selected");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			String result = "";
			String path_upload = getServlet().getServletContext().getRealPath("") + "/upload/" + operid + "/";
			String path_ini = getServlet().getServletContext().getRealPath("") + "/ini/";
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

				// 取出设备信息
				myDevinfo = (Devinfo) myDevinfoModel.getDevFromList(devno.trim());
				myDevip = (myDevinfo.getDevip()).trim();

				// 读取MCS的相关配置
				BaseCommBean commbean = new BaseCommBean();
				CommUtil myCommUtil = new CommUtil();
				iport = Integer.parseInt(myPubUtil.ReadConfig("RemoteControl", "RCPort", "8255", "PowerView.ini"));
				itimeout = Integer.parseInt(myPubUtil.ReadConfig("RemoteControl", "RCTimeOut", "10000", "PowerView.ini"));

				// 处理请求报文
				sendpack = "0028" + devpath;
				// 与MCS通讯
				// recvpack = myCommUtil.commFunc( sendpack, myDevip, iport,
				// itimeout );
				// 整理发送数据包的长度
				int packlen = sendpack.length();
				String strpacklen = String.valueOf(packlen);
				int intpacklen = strpacklen.length();

				for (int i = intpacklen; i < 8; i++)
					strpacklen = "0" + strpacklen;
				sendpack = strpacklen + sendpack;

				// 连接通信平台
				if (commbean.connSocket(myDevip, iport) == -1) {
					result = "-1";
				}

				if (!result.equals("-1")) {
					// 获得输入流和输出流
					if ((commbean.getInStream() == null) || (commbean.getOutStream() == null)) {
						commbean.socketFree();
						result = "-1";
					}
				}

				if (!result.equals("-1")) {
					// 发送数据包
					if (commbean.sendPacket(sendpack) == 0) {
						commbean.socketFree();
						result = "-1";
					}
				}

				if (!result.equals("-1")) {
					// 设置超时时间
					commbean.setTimeOut(itimeout);

					// 设置空闲时间4秒
					commbean.setLinger(4);
				}

				if (!result.equals("-1")) {
					try {
						// 接收数据包
						// String recvpack = commbean.recvFullPacket();
						DataInputStream recvstream = commbean.recvFileStream();
						if (recvstream == null) {
							result = "-1";
						} else {
							String filename = path_upload + devpath.substring(devpath.lastIndexOf("\\") + 1);
							FileOutputStream outStream = new FileOutputStream(filename);
							boolean eof = false;
							while (!eof) {
								int c = recvstream.read();
								if (c == -1)
									eof = true;
								outStream.write((char) c);
							}
							outStream.close();
							recvstream.close();
							result = "0";
						}
						// 释放连接
						commbean.socketFree();
					} catch (Exception e) {
						result = "-1";
					}
				}
			} catch (Exception e) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			// 记录操作员操作流水
			OplogModel myOplogModel = new OplogModel();
			String soperid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			int logflag = 0;
			logflag = myOplogModel.insertOplog(soperid, "0", "3", "pvxp.fileupload.title|" + myDevip);

			request.setAttribute(Constants.REQUEST_FILEUPLOAD_RESULT, result);
			myforward = mapping.findForward("FU_result");

		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}