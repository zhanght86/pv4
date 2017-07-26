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
 * <b>Description:</b>SocketͨѶ������
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
 * @author ����
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
	 * ����:���ù���������socket���� ������ip��ַ���˿� ���أ�void ���ߣ�joycarry ʱ�䣺2002/05/06
	 *************************************/
	/**
	 * <p>
	 * ���ù���������socket����
	 * </p>
	 * 
	 * @param ip
	 *            IP��ַ
	 * @param port
	 *            �˿�
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
	 * ����socket����
	 * </p>
	 * 
	 * @param ip
	 *            IP��ַ
	 * @param port
	 *            �˿�
	 * @return int 0���ɹ� -1��ʧ��
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
	 * ��ȡ������
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
	 * ��ȡ�����
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
	 * ���ó�ʱ/p>
	 * 
	 * @param ms
	 *            ʱ�䣨���룩
	 * @return int 0���ɹ� -1��ʧ��
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
	 * ���ÿ���ʱ��/p>
	 * 
	 * @param ss
	 *            ʱ�䣨���룩
	 * @return int 0���ɹ� -1��ʧ��
	 */
	public int setLinger(int ss) {
		try {
			s.setSoLinger(true, ss);
			return 0;
		} catch (Exception e) {
			log.error("setLinger���� ss=" + ss, e);
			return -1;
		}
	}

	/**
	 * <p>
	 * �������ݰ�/p>
	 * 
	 * @param instr
	 *            �����ַ���
	 * @return int ���ݰ�����
	 */
	public int sendPacket(String instr) {
		int lent = 0;
		try {
			outStream.writeBytes(instr);
			lent = instr.length();
		} catch (IOException e) {
			log.error("sendPacket���� instr=" + instr, e);
			return -1;
		}
		return lent;
	}

	/**
	 * <p>
	 * �������ݰ���������MCS 0030 �ַ��ļ�
	 * </p>
	 * 
	 * @param DataOutputStream
	 *            �����ַ���
	 * @return int ���ݰ�����
	 */
	public int sendFileStream(DataOutputStream instr) {
		int lent = 0;
		outStream = instr;
		lent = instr.size();
		return lent;
	}

	/**
	 * <p>
	 * �������ݰ�/p>
	 * 
	 * @return String ���ݰ�
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
	 * �������ݰ�/p>
	 * 
	 * @return String ���ݰ�
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
	 * �������ݰ���������MCS 0028 ��ȡ�ļ�
	 * </p>
	 * 
	 * @return DataInputStream ���ݰ�
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
	 * �ͷ�socket����/p>
	 * 
	 * @return int 0���ɹ� -1��ʧ��
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
