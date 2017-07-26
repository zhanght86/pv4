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
 * <b>Description:</b> 文件发布Action
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
 * @version 1.0 2005/3/16
 */
public final class FileDistributeAction extends Action {

	Logger log = Logger.getLogger(FileDistributeAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();

		// 获得设备编号列表，下发文件列表，设备路径
		String[] devlist = (String[]) ((FileDistributeForm) form).getDevlist();
		String filestr = (String) ((FileDistributeForm) form).getFilestr();
		String devpath = (String) ((FileDistributeForm) form).getDevpath().trim();

		// 配置公共方法库类 ，配置错误报告路径
		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");

		try {

			// 检查用户是否有权限
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

			// 检查是否有设备信息选中
			if ((devlist == null) || (devlist.length == 0)) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devinfo.no.selected");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			// 将要下发的文件分割 并压缩到类集中
			StringTokenizer wb = new StringTokenizer(filestr, ",");
			Vector fileVector = new Vector();

			while (wb.hasMoreTokens()) {
				fileVector.add(wb.nextToken());
			}

			// 获得文件上传路径和ini文件路径
			String[][][] result = null;
			String path_upload = getServlet().getServletContext().getRealPath("") + "/upload/" + operid + "/";
			String path_ini = getServlet().getServletContext().getRealPath("") + "/ini/";
			String myDevip = "";

			try {
				// 设置设备模型和类
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

					// 取出设备信息
					myDevinfo = (Devinfo) myDevinfoModel.getDevFromList(devlist[i].trim());
					myDevip = (myDevinfo.getDevip()).trim();

					// 读取MCS的相关配置
					BaseCommBean commbean = new BaseCommBean();// Socket通讯基础类

					iport = Integer.parseInt(myPubUtil.ReadConfig("RemoteControl", "RCPort", "8255", "PowerView.ini")); // socket通信端口
					itimeout = Integer.parseInt(myPubUtil.ReadConfig("RemoteControl", "RCTimeOut", "10000", "PowerView.ini")); // 超时时间
					filedelname = "";
					sendpack = ""; // 发送包
					recvpack = ""; // 接收包
					temp = "";
					retcode = "";

					for (int j = 0; j < fileVector.size(); j++) {

						// 处理请求报文
						sendpack = "0030" + devpath + (String) fileVector.get(j) + "|";

						// 整理发送数据包的长度
						FileInputStream inStream = new FileInputStream(path_upload + (String) fileVector.get(j));

						int packlen = sendpack.length() + inStream.available();
						String strpacklen = String.valueOf(packlen);
						int intpacklen = strpacklen.length();

						for (int k = intpacklen; k < 8; k++)
							strpacklen = "0" + strpacklen;// 发送报文头文件要固定长度，不足左补0
						sendpack = strpacklen + sendpack;

						// 连接通信平台
						if (commbean.connSocket(myDevip, iport) == -1) {
							result[i][j][0] = "-1";
							continue;
						} else if ((commbean.getInStream() == null) || (commbean.getOutStream() == null)) {
							// 获得输入流和输出流
							commbean.socketFree();
							result[i][j][0] = "-1";
							continue;
						} else if (commbean.sendPacket(sendpack) == 0) { // 当发送的数据包长度为0时
							// 发送数据包头
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
							// 发送数据包内容
							if (commbean.sendFileStream(outStream) == 0) {
								commbean.socketFree();
								result[i][j][0] = "-1";
								continue;
							} else {
								// 设置超时时间
								commbean.setTimeOut(itimeout);
								// 设置空闲时间4秒
								commbean.setLinger(4);
								// 接收数据包
								recvpack = commbean.recvFullPacket();
								// 释放连接
								commbean.socketFree();
							}
						}

						// 分析响应报文
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

			// 记录操作员操作流水
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