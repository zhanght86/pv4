package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.actionform.RemoteControlForm;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.orm.Devinfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 远程管理Action
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
 * @version 1.0 2015/5/4
 */
public final class RemoteControlAction extends Action {
	
	Logger log = Logger.getLogger(RemoteControlAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		String[] devlist = (String[]) ((RemoteControlForm) form).getDevlist();
		String rc_op = (String) ((RemoteControlForm) form).getRc_operation();
		log.info("设备台数"+devlist.length);
		log.info("rc_op命令："+rc_op);
		
		PubUtil myPubUtil = new PubUtil();
		ActionForward myforward = mapping.findForward("SystemError");
		try {
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
			
			if ((devlist == null) || (devlist.length == 0)) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.devinfo.no.selected");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
			
			if ((rc_op == null) || (rc_op.equals(""))) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.rcop.no.selected");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
			
			String[] result = null;
			String myDevip = "";
			
			try {
				DevinfoModel myDevinfoModel = new DevinfoModel();
				Devinfo myDevinfo = new Devinfo();
				
				result = new String[devlist.length];
				int iport = Integer.parseInt(myPubUtil.ReadConfig("RemoteControl", "RCPort", "8255", "PowerView.ini"));
				int itimeout = Integer.parseInt(myPubUtil.ReadConfig("RemoteControl", "RCTimeOut", "10000", "PowerView.ini"));
				
				log.info("iport="+iport);
				log.info("itimeout="+itimeout);
				
				EncUtil myEncUtil = new EncUtil();
				log.info("EncUtil建立成功");
				String passwd = "84888259";
				
				for (int i = 0; i < devlist.length; i++) {
					// 取出设备信息
					myDevinfo = (Devinfo) myDevinfoModel.getDevFromList(devlist[i].trim());
					myDevip = (myDevinfo.getDevip()).trim();
					log.info("远程设备："+myDevinfo.getDevno());
					log.info("远程设备myDevip："+myDevip);
					
					CommUtil myCommUtil = new CommUtil();
					String sendpack = "";
					String recvpack = "";
					String temp = "";
					String retcode = "";
					
					
					// 判断远程管理操作
					if (rc_op.equals("0010") || rc_op.equals("0012")) {// 关机、重启
						try {
							sendpack = rc_op;
							recvpack = myCommUtil.commFunc(sendpack, myDevip, iport, itimeout);
							log.info("关机、重启--结果："+recvpack);
							// 分析响应报文
							if (recvpack == null) {
								result[i] = "-1";
							} else {
								recvpack = recvpack.trim();
								temp = Integer.toString(Integer.parseInt(rc_op) + 1);
								retcode = myPubUtil.strFormat(temp, 4, 1, '0');
								if (recvpack.equals(retcode)) {
									result[i] = "0";
								} else {
									result[i] = "-1";
								}
							}
						} catch (Exception e) {
							log.error("Exception e",e);
							result[i] = "-1";
						}
					} else if (rc_op.equals("0014")) {// 校对时间
						try {
							temp = myPubUtil.getNowTime();
							temp = myPubUtil.replace(temp, ":", "");
							sendpack = rc_op + myPubUtil.getNowDate(1) + temp;
							
							sendpack = myEncUtil.enPack(sendpack, passwd);
							recvpack = myCommUtil.commFunc(sendpack, myDevip, iport, itimeout);
							log.info("校对时间--结果："+recvpack);
							// 分析响应报文
							if (recvpack == null) {
								result[i] = "-1";
							} else {
								recvpack = recvpack.trim();
								recvpack = myEncUtil.unPack(recvpack, passwd);
								
								temp = Integer.toString(Integer.parseInt(rc_op) + 1);
								retcode = myPubUtil.strFormat(temp, 4, 1, '0');
								if (recvpack.equals(retcode) || recvpack.substring(4, 6).equals("00")) {
									result[i] = "0";
								} else {
									result[i] = recvpack.substring(6);
								}
							}
						} catch (Exception e) {
							result[i] = "-1";
						}
					} else if (rc_op.equals("0016")) {// 暂停使用
						myDevinfo.setOpentag("0");
						result[i] = Integer.toString(myDevinfoModel.updateDev(myDevinfo, (myDevinfo.getDevno()).trim()));
						if (result[i].equals("0")) {
							try {
								sendpack = "0062";
								
								sendpack = myEncUtil.enPack(sendpack, passwd);
								recvpack = myCommUtil.commFunc(sendpack, myDevip, iport, itimeout);
								log.info("暂停使用--结果："+recvpack);
								
								// 分析响应报文
								if (recvpack == null) {
									result[i] = "-1";
								} else {
									recvpack = recvpack.trim();
									recvpack = myEncUtil.unPack(recvpack, passwd);
									
									temp = "0063";
									retcode = myPubUtil.strFormat(temp, 4, 1, '0');
									if (recvpack.equals(retcode) || recvpack.substring(4, 6).equals("00")) {
										result[i] = "0";
										
									} else {
										result[i] = "-1";
										
									}
								}
							} catch (Exception e) {
								result[i] = "-1";
							}
						}
						
					} else if (rc_op.equals("6100")) {// 启用设备
						myDevinfo.setOpentag("1");
						result[i] = Integer.toString(myDevinfoModel.updateDev(myDevinfo, (myDevinfo.getDevno()).trim()));
						if (result[i].equals("0")) {
							try {
								sendpack = "0064";
								
								sendpack = myEncUtil.enPack(sendpack, passwd);
								recvpack = myCommUtil.commFunc(sendpack, myDevip, iport, itimeout);
								log.info("启用设备--结果："+recvpack);
								
								// 分析响应报文
								if (recvpack == null) {
									result[i] = "-1";
								} else {
									recvpack = recvpack.trim();
									recvpack = myEncUtil.unPack(recvpack, passwd);
									
									temp = "0065";
									retcode = myPubUtil.strFormat(temp, 4, 1, '0');
									if (recvpack.equals(retcode) || recvpack.substring(4, 6).equals("00")) {
										result[i] = "0";
									} else {
										result[i] = "-1";
										
									}
								}
							} catch (Exception e) {
								result[i] = "-1";
							}
						}
						
					}
				}
			} catch (Exception e) {
				log.error("ERROR", e);
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
			
			
			// 记录操作员操作流水
			OplogModel myOplogModel = new OplogModel();
			String soperid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			int logflag = 0;
			logflag = myOplogModel.insertOplog(soperid, "0", "3", "pvxp.remotecontrol.title|" + myDevip);
			
			request.setAttribute(Constants.REQUEST_REMOTECONTROL_RESULT, result);
			myforward = mapping.findForward("RC_result");
			
		} catch (Exception e) {
			log.error("ERROR", e);
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}
		
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}