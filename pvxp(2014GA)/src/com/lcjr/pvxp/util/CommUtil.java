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
public class CommUtil {

	Logger log = Logger.getLogger(CommUtil.class.getName());

	BaseCommBean commbean = new BaseCommBean();

	PubUtil myPubUtil = new PubUtil();

	/**
	 * <p>
	 * ����socket���ӣ����Ͳ��������ݰ�
	 * </p>
	 * 
	 * @param commstr
	 *            ���ݰ�
	 * @param commip
	 *            IP��ַ
	 * @param commport
	 *            �˿�
	 * @param timeout
	 *            ��ʱʱ��(ms)
	 * @param flag
	 *            �Ƿ�ȴ�����(0-���ȴ� ����-�ȴ�)
	 * @return String �������ݰ�(AAAA-������ϲ����� null-����ʧ�� ����-�������ݰ�)
	 */
	public String commFunc(String commstr, String commip, int commport, int timeout, int flag) {
		int packlen = 0;
		int intpacklen = 0;
		String ret = "";
		String recvpack = "";
		String strpacklen = "";
		try {
			// ���������ݰ��ĳ���
			packlen = commstr.length();
			strpacklen = String.valueOf(packlen);
			intpacklen = strpacklen.length();

			for (int i = intpacklen; i < 8; i++) {
				strpacklen = "0" + strpacklen;
			}

			commstr = strpacklen + commstr;

			// ����ͨ��ƽ̨��
			if (commbean.connSocket(commip, commport) == -1) {
				return null;
			}

			// ����������������
			if ((commbean.getInStream() == null) || (commbean.getOutStream() == null)) {
				commbean.socketFree();
				return null;
			}

			// �������ݰ���
			if (commbean.sendPacket(commstr) == 0) {
				commbean.socketFree();
				return null;
			}

			if (flag == 0) {
				// �ͷ�����
				commbean.socketFree();
				return "AAAA";
			}

			// ���ó�ʱʱ�䣻
			commbean.setTimeOut(timeout);

			// ���ÿ���ʱ��4��
			commbean.setLinger(4);

			// �������ݰ�
			recvpack = commbean.recvFullPacket();

			// �ͷ�����
			commbean.socketFree();

			return recvpack;

		} catch (Exception e) {
			log.warn("����", e);
			return null;
		}
	}

	/**
	 * <p>
	 * ����socket���ӣ����Ͳ��������ݰ�
	 * </p>
	 * 
	 * @param commstr
	 *            ���ݰ�
	 * @param commip
	 *            IP��ַ
	 * @param commport
	 *            �˿�
	 * @param timeout
	 *            ��ʱʱ��(ms)
	 * @return String �������ݰ�(null-����ʧ��)
	 */
	public String commFunc(String commstr, String commip, int commport, int timeout) {
		String ret = "";
		try {
			// ���������ݰ��ĳ���
			int packlen = commstr.length();
			String strpacklen = String.valueOf(packlen);
			int intpacklen = strpacklen.length();

			for (int i = intpacklen; i < 8; i++)
				strpacklen = "0" + strpacklen;
			commstr = strpacklen + commstr;
			log.info("���͵ı��ģ�"+commstr);
			// ����ͨ��ƽ̨��
			if (commbean.connSocket(commip, commport) == -1) {
				log.error("connSocket=-1");
				return null;
			}
			
			
			// ����������������
			if ((commbean.getInStream() == null) || (commbean.getOutStream() == null)) {
				log.error("commbean.getInStream() == null) || (commbean.getOutStream() == null");
				commbean.socketFree();
				return null;
			}

			// �������ݰ���
			if (commbean.sendPacket(commstr) == 0) {
				log.error("commbean.sendPacket(commstr) == 0");
				commbean.socketFree();
				return null;
			}

			// ���ó�ʱʱ�䣻
			commbean.setTimeOut(timeout);

			// ���ÿ���ʱ��4��
			commbean.setLinger(4);

			// �������ݰ�
			String recvpack = commbean.recvFullPacket();
			log.info("����ֵrecvpack=="+recvpack);
			// �ͷ�����
			commbean.socketFree();

			return recvpack;
		} catch (Exception e) {
			log.warn("����", e);
			return null;
		}
	}

	/**
	 * <p>
	 * �豸״̬��ʱ���
	 * </p>
	 * 
	 * @param devno
	 *            �豸���
	 * @return String �������ݰ�(null-ʧ��)
	 * @throws HibernateException
	 */
	public String getDevStateNow(String devno) throws HibernateException {
		String sendstr = "0060";
		String devip = "";
		int printport = 0, outtime = 0;
		if (devno == null)
			return null;

		// ȡ���豸��ip��ַ
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

		// ȡ�豸ͨ�����ã��˿ڣ���ʱʱ��
		try {
			printport = Integer.parseInt(myPubUtil.ReadConfig("DevCheck", "DevSevPort", "", "PowerView.ini"));
			outtime = Integer.parseInt(myPubUtil.ReadConfig("DevCheck", "DevCommTimeOut", "15000", "PowerView.ini"));
			// ʹ��ENC.DLL����
			// sendstr = EncUtil.enPack(sendstr,passwd);

			recvpack = commFunc(sendstr, devip, printport, outtime);
			if (recvpack == null) {
				recvpack = "-1";
			}
			// ʹ��ENC.DLL����
			// else{
			// recvpack = EncUtil.unPack(recvpack,passwd);
			// }

		} catch (Exception e) {
			log.warn("����", e);
			return null;
		}
		// �������ݰ�
		return recvpack;
	}

	/**
	 * <p>
	 * ��ʷ��ϸ���ݵ���
	 * </p>
	 * 
	 * @param months
	 *            Ҫ���������
	 * @return String �������ݰ�(null-ʧ��)
	 * @throws HibernateException
	 */
	public String getHistoryData(String months) throws HibernateException {
		String sendstr = "TDATA" + months;
		String commip = "";
		int printport = 0, outtime = 0;

		// ȡ�豸ͨ�����ã�IP���˿ڣ���ʱʱ��
		try {
			commip = myPubUtil.ReadConfig("DBagent", "ServIP", "127.0.0.1", "PowerView.ini");
			printport = Integer.parseInt(myPubUtil.ReadConfig("DBagent", "ServPort", "6633", "PowerView.ini"));
			outtime = Integer.parseInt(myPubUtil.ReadConfig("DBagent", "ServTimeOut", "30000", "PowerView.ini"));
			// �������ݰ�
			return commFunc(sendstr, commip, printport, outtime);
		} catch (Exception e) {
			log.warn("����", e);
			return null;
		}
	}

	/**
	 * <p>
	 * ɾ����ʷ��ϸ�����ļ�
	 * </p>
	 * 
	 * @param month
	 *            Ҫɾ������ֹ����
	 * @return String �������ݰ�(null-ʧ��)
	 * @throws HibernateException
	 */
	public String delHistoryDataFile(String month) throws HibernateException {
		String sendstr = "DELET" + month;
		String commip = "";
		int printport = 0, outtime = 0;

		// ȡ�豸ͨ�����ã�IP���˿ڣ���ʱʱ��
		try {

			commip = myPubUtil.ReadConfig("DBagent", "ServIP", "127.0.0.1", "PowerView.ini");
			printport = Integer.parseInt(myPubUtil.ReadConfig("DBagent", "ServPort", "6633", "PowerView.ini"));
			outtime = Integer.parseInt(myPubUtil.ReadConfig("DBagent", "ServTimeOut", "30000", "PowerView.ini"));
			// �������ݰ�
			return commFunc(sendstr, commip, printport, outtime);

		} catch (Exception e) {
			log.warn("����", e);
			return null;
		}
	}
}
