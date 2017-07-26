package com.lcjr.pvxp.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b>Socket通讯基础类
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
public class BaseCommBean {

	Logger log = Logger.getLogger(BaseCommBean.class.getName());

	private Socket s = null;
	private DataInputStream inStream = null;
	private DataOutputStream outStream = null;

	public BaseCommBean() {
		super();
	}

	/*************************************
	 * 功能:利用构造器建立socket连接 参数：ip地址，端口 返回：void 作者：joycarry 时间：2002/05/06
	 *************************************/
	/**
	 * <p>
	 * 利用构造器建立socket连接
	 * </p>
	 * 
	 * @param ip
	 *            IP地址
	 * @param port
	 *            端口
	 */
	public BaseCommBean(String ip, int port) {
		try {
			s = new Socket(ip, port);
		} catch (Exception e) {
			log.error("BaseCommBean(ip=" + ip + " , port=" + port + ")", e);
			return;
		}
	}

	/**
	 * <p>
	 * 建立socket连接
	 * </p>
	 * 
	 * @param ip
	 *            IP地址
	 * @param port
	 *            端口
	 * @return int 0：成功 -1：失败
	 */
	public int connSocket(String ip, int port) {
		try {
			s = new Socket(ip, port);
			return 0;
		} catch (Exception e) {
			log.error("connSocket(ip=" + ip + " , port=" + port + ")", e);
			return -1;
		}
	}

	/**
	 * <p>
	 * 获取输入流
	 * </p>
	 * 
	 * @return DataInputStream
	 */
	public DataInputStream getInStream() {
		inStream = null;
		try {
			inStream = new DataInputStream(s.getInputStream());
		} catch (IOException e) {
			log.error("getInStream", e);
			return null;
		}
		return inStream;
	}

	/**
	 * <p>
	 * 获取输出流
	 * </p>
	 * 
	 * @return DataOutputStream
	 */
	public DataOutputStream getOutStream() {
		outStream = null;
		try {
			outStream = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			log.error("getOutStream", e);
			return null;
		}
		return outStream;
	}

	/**
	 * <p>
	 * 设置超时/p>
	 * 
	 * @param ms
	 *            时间（毫秒）
	 * @return int 0：成功 -1：失败
	 */
	public int setTimeOut(int ms) {
		try {
			s.setSoTimeout(ms);
			return 0;
		} catch (Exception e) {
			log.error("setTimeOut", e);
			return -1;
		}
	}

	/**
	 * <p>
	 * 设置空闲时间/p>
	 * 
	 * @param ss
	 *            时间（毫秒）
	 * @return int 0：成功 -1：失败
	 */
	public int setLinger(int ss) {
		try {
			s.setSoLinger(true, ss);
			return 0;
		} catch (Exception e) {
			log.error("setLinger（） ss=" + ss, e);
			return -1;
		}
	}

	/**
	 * <p>
	 * 发送数据包/p>
	 * 
	 * @param instr
	 *            发送字符串
	 * @return int 数据包长度
	 */
	public int sendPacket(String instr) {
		int lent = 0;
		try {
			outStream.writeBytes(instr);
			lent = instr.length();
		} catch (IOException e) {
			log.error("sendPacket（） instr=" + instr, e);
			return -1;
		}
		return lent;
	}

	/**
	 * <p>
	 * 发送数据包，仅用于MCS 0030 分发文件
	 * </p>
	 * 
	 * @param DataOutputStream
	 *            发送字符流
	 * @return int 数据包长度
	 */
	public int sendFileStream(DataOutputStream instr) {
		int lent = 0;
		outStream = instr;
		lent = instr.size();
		return lent;
	}

	/**
	 * <p>
	 * 接收数据包/p>
	 * 
	 * @return String 数据包
	 */
	public String recvFullPacket() {
		byte b[] = new byte[8];
		try {
			inStream.readFully(b, 0, 8);
		} catch (IOException e) {
			log.error("recvFullPacket", e);
			return null;
		}
		String strlen = new String(b);
		int len = Integer.parseInt(strlen);
		byte bs[] = new byte[len + 1];
		try {
			inStream.readFully(bs, 0, len);
		} catch (IOException e) {
			log.error("recvFullPacket", e);
			return null;
		}
		String rcvstr = new String(bs);
		return rcvstr;
	}

	/**
	 * <p>
	 * 接收数据包/p>
	 * 
	 * @return String 数据包
	 */
	public String recvPacket() {
		byte b[] = new byte[14];
		try {
			inStream.readFully(b, 0, 14);
		} catch (IOException e) {
			log.error("recvFullPacket", e);
			return null;
		}
		String strlen = new String(b);
		return strlen;
	}

	/**
	 * <p>
	 * 接收数据包，仅用于MCS 0028 获取文件
	 * </p>
	 * 
	 * @return DataInputStream 数据包
	 */
	public DataInputStream recvFileStream() {
		byte b[] = new byte[8];
		try {
			inStream.readFully(b, 0, 8);
		} catch (IOException e) {
			log.error("recvFileStream", e);
			return null;
		}
		String strlen = new String(b);
		int len = Integer.parseInt(strlen);

		byte f[] = new byte[6];
		try {
			inStream.readFully(f, 0, 6);
		} catch (IOException e) {
			log.error("recvFileStream", e);
			return null;
		}
		String flag = new String(f);
		if (flag.equals("002900")) {
			return inStream;
		} else {
			return null;
		}
	}

	/**
	 * <p>
	 * 释放socket连接/p>
	 * 
	 * @return int 0：成功 -1：失败
	 */
	public int socketFree() {
		try {
			inStream.close();
			outStream.close();
			s.close();

			return 0;
		} catch (Exception e) {
			log.error("socketFree", e);
			return -1;
		}
	}
}
