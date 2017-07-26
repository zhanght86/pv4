package com.lcjr.pvxp.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.DevstateModel;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.orm.Devstate;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 设备监控服务程序
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
 * @version 1.0 2005/02/28
 */
public class DevMoniServer extends Thread {

	Logger log = Logger.getLogger(DevMoniServer.class.getName());

	/**
	 * Hashtable类型
	 */
	private static Hashtable allHashtable = new Hashtable();

	/**
	 * 建立socket服务
	 */
	private static ServerSocket server = null;

	/**
	 * 主要运行线程
	 */
	private static boolean mainrun = false;

	/**
	 * 服务运行线程
	 */
	private static boolean serverrun = false;

	/**
	 * 停止标志
	 */
	private static boolean stopflag = false;

	PubUtil myPubUtil = new PubUtil();

	/**
	 * 构造函数
	 */
	public DevMoniServer() {
	}

	/**
	 * <p>
	 * 启动设备监控服务程序
	 * </p>
	 * 
	 */
	public void startMoni() {

		log.info("启动设备监控程序,serverrun=>" + serverrun);
		stopflag = false;
		if (!serverrun) {
			start();
			log.info("开始执行start()");
		}
	}

	/**
	 * 停止线程运行
	 */
	public void stopMoni() {
		log.info("停止设备监控程序,serverrun=>" + serverrun);
		mainrun = false;
		serverrun = false;
		stopflag = true;
		try {
			server.close();
		} catch (IOException e) {
			log.error("stopMoni()", e);
		}
	}

	/**
	 * 运行线程
	 */
	public synchronized void run() {
		// 进入主线程
 
		log.info("进入主线程,serverrun=>" + serverrun);

		try {
			// 建立端口号
			String port = myPubUtil.dealNull(myPubUtil.ReadConfig("System", "DevJKPort", "12315", "PowerView.ini")).trim();
			log.info("获得设备监控端口号=>" + port);
			if (port.equals(""))
				port = "12315";
			int myport = Integer.parseInt(port);

			try {

				// 建立端口的socket通信
				server = new ServerSocket(myport);
				log.info("开始建立端口socket=>" + server.toString());
			} catch (Exception e) {
				log.error("ServerSocket():myport:"+myport, e);
				serverrun = false;
			}
			serverrun = true;

			// 策略文件
			String xml = "<cross-domain-policy>";
			xml += "<allow-access-from domain=\"*\" to-ports='" + port + "'/>";
			xml += "</cross-domain-policy>";

			log.info("=================Devmoni Started=================");
			while (true) {
				// 建立client socket，用于接收特定9099端口过来的信息
				Socket client = null;
				client = server.accept(); // 等待接收信息
				log.info("client==>" + client.toString());
				// 获得输出流

				PrintWriter pw = new PrintWriter(client.getOutputStream());
				// 立即发送策略文件
				pw.print(xml + "\0");
				pw.flush();

				String message = null;
				try {
					DataInputStream in = null;
					byte[] buf = new byte[8];

					// 获得输入流
					in = new DataInputStream(client.getInputStream());
					in.read(buf, 0, 8);
					int len1 = 0;
					String tmpstr = new String(buf, 0, 8);

					// 如果报文头等于 <policy- ，则进入下一循环

					if (tmpstr.equals("<policy-")) {
						log.info("收到    <policy-  ");

						continue;

						// client.close();

					} else {

						pw.print("连接成功!\0");
						log.info("连接成功!收到的报文" + tmpstr);
						len1 = Integer.parseInt(tmpstr);
						buf = new byte[len1 + 1];
						in.read(buf, 0, len1);
						message = new String(buf, 0, len1);

					}

				} catch (Exception nume) {
					log.error("DevMoniServer-nume ERROR", nume);
					continue;
				}

				if (message == null)
					continue;

				String siginFlag = "DevMoni";

				// 判断是不是监控请求
				if (message.indexOf(siginFlag) == 0) { // 监控请求
					// 创建发送监控数据的线程
					log.info("确定监控请求" + message);
					String tmpstr = "";
					try {
						tmpstr = message.substring(7);
					} catch (Exception e) {
						tmpstr = "";
					}

					MoniThread myMoniThread = new MoniThread(client, tmpstr);

				}
			}
		} catch (IOException ex) {
			log.warn("DevMoniServer WARN", ex);
			try {
				server.close();
				log.info("DevMoniServer WARN");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("DevMoniServer IOException", e);
			}

			serverrun = false;
		}
	}

	/**
	 * 监控线程
	 * 
	 * @author 武坤鹏
	 * 
	 */
	class MoniThread extends Thread {

		/**
		 * socket通信
		 */
		private Socket mys = null;

		/**
		 * 存放设备信息的类集
		 */
		private List devlist;

		/**
		 * 启动监控
		 * 
		 * @param s
		 *            socket
		 * @param bank
		 */
		public MoniThread(Socket s, String bank) {

			try {

				this.mys = s;
				log.info("MoniThread(" + s.toString() + "," + bank + ")");
				// 设备信息模型
				DevinfoModel myDevinfoModel = new DevinfoModel();

				// 立即重建设备列表缓存
				if (DevinfoModel.resetNow())
					devlist = myDevinfoModel.getDevListOfBank(bank);
				start();
			} catch (Exception e) {
				log.error("MoniThread ERROR", e);
			}
		}

		/**
		 * 
		 */
		public void run() {

			try {
				log.info("MoniThread  查询数据库开始");
				// 发送签到成功反馈...
				DataOutputStream out = new DataOutputStream(mys.getOutputStream());

				// wukp 111124 设置设备信息数量
				int devnum = 0;
				if (devlist == null || devlist.isEmpty())
					devnum = 0;
				else
					devnum = devlist.size();
				log.warn("==========My dev num=[" + devnum + "]");
				String[] devnoarry = new String[devnum];

				// wukp 111124 编写设备编号字符串 用于sql语句
				String devnostr = "";

				// wukp 111124 将设备编号添加到数组中
				for (int j = 0; j < devnum; j++) {
					devnoarry[j] = ((Devinfo) devlist.get(j)).getDevno();
					devnostr += ("'" + devnoarry[j] + "',");
				}

				log.info("设备编号数组==>" + devnostr);
				// wukp 111124 设定 超时时间
				String timeout = myPubUtil.dealNull(myPubUtil.ReadConfig("System", "DevMoniTimeOut", "600", "PowerView.ini")).trim();

				int inttimeout = Integer.parseInt(timeout);

				// wukp 111124 设备状态编码 编号
				String statenos = myPubUtil.dealNull(myPubUtil.ReadConfig("System", "DevMoniStateno", "Z001", "PowerView.ini")).trim();

				String statecode = myPubUtil.dealNull(myPubUtil.ReadConfig("System", "DevMoniStatecode", "001", "PowerView.ini")).trim();
				log.info("statenos==" + statenos + "timeout==" + timeout + "statecode==" + statecode);

				List mydevstatelist = null;
				Hashtable tmpHashtable = new Hashtable();

				// 存在设备编号字符串用于sql查询
				if (devnostr.length() > 0) {

					String polltag = "";
					String edate = "";
					String etime = "";
					String stateno = "";

					int tmptime1 = 0;
					int tmptime2 = 0;

					// wukp 111124 获得当前时间和日期
					String nowdate = myPubUtil.getNowDate(1);
					String nowtime = myPubUtil.getNowTime();

					devnostr = devnostr.substring(0, devnostr.length() - 1);

					DevstateModel myDevstateModel = new DevstateModel();

					// 获得设备状态 类集
					mydevstatelist = myDevstateModel.getSomeDevStateList(devnostr);

					if (mydevstatelist != null) {

						int len = mydevstatelist.size();
						log.info("获得的设备状态数目是=" + len);
						for (int i = 0; i < len; i++) {

							Devstate temp = (Devstate) mydevstatelist.get(i);
							String tmpstr = (String) tmpHashtable.get(temp.getDevno());
							edate = temp.getEdate().trim();
							etime = temp.getEtime().trim();
							stateno = temp.getStateno().trim();
							// 从设备信息表中找到相关信息
							polltag = ((Devinfo) DevinfoModel.getDevFromList(temp.getDevno())).getPolltag().trim();

							// 处理当前时间，以秒为单位
							tmptime1 = Integer.parseInt(nowtime.substring(0, 2)) * 3600 + Integer.parseInt(nowtime.substring(3, 5)) * 60 + Integer.parseInt(nowtime.substring(6, 8));
							if (etime.length() == 6)
								tmptime2 = Integer.parseInt(etime.substring(0, 2)) * 3600 + Integer.parseInt(etime.substring(2, 4)) * 60 + Integer.parseInt(etime.substring(4, 6));

							// 如果结束日期不是当前日期，并且当前时间大于超时时间？
							if ((!edate.equals(nowdate)) && (tmptime1 > inttimeout) && (stateno.equals(statenos))) {

								if (tmpstr == null) { // 如果在tmpHashtable中不存在相关数据
									tmpstr = temp.getStateno().trim() + "," + statecode + "," + temp.getBdate().trim() + "," + temp.getBtime().trim() + "," + edate + "," + etime;
								} else {
									tmpstr = tmpstr + "|" + temp.getStateno().trim() + "," + statecode + "," + temp.getBdate().trim() + "," + temp.getBtime().trim() + "," + edate + "," + etime;
								}
							} else {
								if (((tmptime1 - tmptime2) > inttimeout) && (stateno.equals(statenos))) {
									if (tmpstr == null) {
										tmpstr = temp.getStateno().trim() + "," + statecode + "," + temp.getBdate().trim() + "," + temp.getBtime().trim() + "," + edate + "," + etime;
									} else {
										tmpstr = tmpstr + "|" + temp.getStateno().trim() + "," + statecode + "," + temp.getBdate().trim() + "," + temp.getBtime().trim() + "," + edate + "," + etime;
									}
								} else {
									if (tmpstr == null) {
										tmpstr = temp.getStateno().trim() + "," + temp.getStatecode().trim() + "," + temp.getBdate().trim() + "," + temp.getBtime().trim() + "," + edate + "," + etime;
									} else {
										tmpstr = tmpstr + "|" + temp.getStateno().trim() + "," + temp.getStatecode().trim() + "," + temp.getBdate().trim() + "," + temp.getBtime().trim() + "," + edate
												+ "," + etime;
									}
								}

							}
							tmpHashtable.put(temp.getDevno(), tmpstr);
							log.info("发送的状态==编号=" + temp.getDevno() + ",状态=" + tmpstr);
						}
					}
				}

				// 向网页中发送数据，用来显示结果
				for (int i = 0; i < devnum; i++) {
					String sendstr = (String) tmpHashtable.get(devnoarry[i]);

					if (sendstr != null) {
						sendstr = "Ddata" + devnoarry[i] + ":" + sendstr;
						byte[] senddata = sendstr.getBytes();
						out.write(senddata);
						out.write(0);
						out.flush();
					}
					this.sleep(600);
				}
				out.close();
				mys.close();
			} catch (Exception ex) {
				log.error("MoniThread ERROR", ex);
				log.error("MoniThread  ==========I am out.(DevMoniServer)==========");
			}
		}

	}
}