package com.lcjr.pvxp.util;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;

import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.orm.Devinfo;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b>Socket通讯工具类
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
 * @version 1.0 2005/03/02
 */
public class CommUtil {

	Logger log = Logger.getLogger(CommUtil.class.getName());

	BaseCommBean commbean = new BaseCommBean();

	PubUtil myPubUtil = new PubUtil();

	/**
	 * <p>
	 * 建立socket连接，发送并接收数据包
	 * </p>
	 * 
	 * @param commstr
	 *            数据包
	 * @param commip
	 *            IP地址
	 * @param commport
	 *            端口
	 * @param timeout
	 *            超时时间(ms)
	 * @param flag
	 *            是否等待接收(0-不等待 其他-等待)
	 * @return String 返回数据包(AAAA-发送完毕不接收 null-发送失败 其它-返回数据包)
	 */
	public String commFunc(String commstr, String commip, int commport, int timeout, int flag) {
		int packlen = 0;
		int intpacklen = 0;
		String ret = "";
		String recvpack = "";
		String strpacklen = "";
		try {
			// 整理发送数据包的长度
			packlen = commstr.length();
			strpacklen = String.valueOf(packlen);
			intpacklen = strpacklen.length();

			for (int i = intpacklen; i < 8; i++) {
				strpacklen = "0" + strpacklen;
			}

			commstr = strpacklen + commstr;

			// 连接通信平台；
			if (commbean.connSocket(commip, commport) == -1) {
				return null;
			}

			// 获得输入流和输出流
			if ((commbean.getInStream() == null) || (commbean.getOutStream() == null)) {
				commbean.socketFree();
				return null;
			}

			// 发送数据包；
			if (commbean.sendPacket(commstr) == 0) {
				commbean.socketFree();
				return null;
			}

			if (flag == 0) {
				// 释放连接
				commbean.socketFree();
				return "AAAA";
			}

			// 设置超时时间；
			commbean.setTimeOut(timeout);

			// 设置空闲时间4秒
			commbean.setLinger(4);

			// 接收数据包
			recvpack = commbean.recvFullPacket();

			// 释放连接
			commbean.socketFree();

			return recvpack;

		} catch (Exception e) {
			log.warn("警告", e);
			return null;
		}
	}

	/**
	 * <p>
	 * 建立socket连接，发送并接收数据包
	 * </p>
	 * 
	 * @param commstr
	 *            数据包
	 * @param commip
	 *            IP地址
	 * @param commport
	 *            端口
	 * @param timeout
	 *            超时时间(ms)
	 * @return String 返回数据包(null-发送失败)
	 */
	public String commFunc(String commstr, String commip, int commport, int timeout) {
		String ret = "";
		try {
			// 整理发送数据包的长度
			int packlen = commstr.length();
			String strpacklen = String.valueOf(packlen);
			int intpacklen = strpacklen.length();

			for (int i = intpacklen; i < 8; i++)
				strpacklen = "0" + strpacklen;
			commstr = strpacklen + commstr;
			log.info("发送的报文："+commstr);
			// 连接通信平台；
			if (commbean.connSocket(commip, commport) == -1) {
				log.error("connSocket=-1");
				return null;
			}
			
			
			// 获得输入流和输出流
			if ((commbean.getInStream() == null) || (commbean.getOutStream() == null)) {
				log.error("commbean.getInStream() == null) || (commbean.getOutStream() == null");
				commbean.socketFree();
				return null;
			}

			// 发送数据包；
			if (commbean.sendPacket(commstr) == 0) {
				log.error("commbean.sendPacket(commstr) == 0");
				commbean.socketFree();
				return null;
			}

			// 设置超时时间；
			commbean.setTimeOut(timeout);

			// 设置空闲时间4秒
			commbean.setLinger(4);

			// 接收数据包
			String recvpack = commbean.recvFullPacket();
			log.info("返回值recvpack=="+recvpack);
			// 释放连接
			commbean.socketFree();

			return recvpack;
		} catch (Exception e) {
			log.warn("警告", e);
			return null;
		}
	}

	/**
	 * <p>
	 * 设备状态即时检测
	 * </p>
	 * 
	 * @param devno
	 *            设备编号
	 * @return String 返回数据包(null-失败)
	 * @throws HibernateException
	 */
	public String getDevStateNow(String devno) throws HibernateException {
		String sendstr = "0060";
		String devip = "";
		int printport = 0, outtime = 0;
		if (devno == null)
			return null;

		// 取得设备的ip地址
		DevinfoModel myDevinfoModel = new DevinfoModel();
		Devinfo tmp = myDevinfoModel.getDevFromList(devno);
		if (tmp == null)
			return null;

		devip = myPubUtil.dealNull(tmp.getDevip()).trim();
		if (devip.length() == 0)
			return null;

		// EncUtil myEncUtil = new EncUtil();

		String passwd = "84888259";
		String recvpack = "";

		// 取设备通信配置：端口，超时时间
		try {
			printport = Integer.parseInt(myPubUtil.ReadConfig("DevCheck", "DevSevPort", "", "PowerView.ini"));
			outtime = Integer.parseInt(myPubUtil.ReadConfig("DevCheck", "DevCommTimeOut", "15000", "PowerView.ini"));
			// 使用ENC.DLL加密
			// sendstr = EncUtil.enPack(sendstr,passwd);

			recvpack = commFunc(sendstr, devip, printport, outtime);
			if (recvpack == null) {
				recvpack = "-1";
			}
			// 使用ENC.DLL解密
			// else{
			// recvpack = EncUtil.unPack(recvpack,passwd);
			// }

		} catch (Exception e) {
			log.warn("警告", e);
			return null;
		}
		// 发送数据包
		return recvpack;
	}

	/**
	 * <p>
	 * 历史明细数据导入
	 * </p>
	 * 
	 * @param months
	 *            要导入的年月
	 * @return String 返回数据包(null-失败)
	 * @throws HibernateException
	 */
	public String getHistoryData(String months) throws HibernateException {
		String sendstr = "TDATA" + months;
		String commip = "";
		int printport = 0, outtime = 0;

		// 取设备通信配置：IP，端口，超时时间
		try {
			commip = myPubUtil.ReadConfig("DBagent", "ServIP", "127.0.0.1", "PowerView.ini");
			printport = Integer.parseInt(myPubUtil.ReadConfig("DBagent", "ServPort", "6633", "PowerView.ini"));
			outtime = Integer.parseInt(myPubUtil.ReadConfig("DBagent", "ServTimeOut", "30000", "PowerView.ini"));
			// 发送数据包
			return commFunc(sendstr, commip, printport, outtime);
		} catch (Exception e) {
			log.warn("警告", e);
			return null;
		}
	}

	/**
	 * <p>
	 * 删除历史明细数据文件
	 * </p>
	 * 
	 * @param month
	 *            要删除的终止年月
	 * @return String 返回数据包(null-失败)
	 * @throws HibernateException
	 */
	public String delHistoryDataFile(String month) throws HibernateException {
		String sendstr = "DELET" + month;
		String commip = "";
		int printport = 0, outtime = 0;

		// 取设备通信配置：IP，端口，超时时间
		try {

			commip = myPubUtil.ReadConfig("DBagent", "ServIP", "127.0.0.1", "PowerView.ini");
			printport = Integer.parseInt(myPubUtil.ReadConfig("DBagent", "ServPort", "6633", "PowerView.ini"));
			outtime = Integer.parseInt(myPubUtil.ReadConfig("DBagent", "ServTimeOut", "30000", "PowerView.ini"));
			// 发送数据包
			return commFunc(sendstr, commip, printport, outtime);

		} catch (Exception e) {
			log.warn("警告", e);
			return null;
		}
	}
}
