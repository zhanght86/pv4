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
 * <b>Description:</b> �豸��ط������
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
 * @version 1.0 2005/02/28
 */
public class DevMoniServer extends Thread {

	Logger log = Logger.getLogger(DevMoniServer.class.getName());

	/**
	 * Hashtable����
	 */
	private static Hashtable allHashtable = new Hashtable();

	/**
	 * ����socket����
	 */
	private static ServerSocket server = null;

	/**
	 * ��Ҫ�����߳�
	 */
	private static boolean mainrun = false;

	/**
	 * ���������߳�
	 */
	private static boolean serverrun = false;

	/**
	 * ֹͣ��־
	 */
	private static boolean stopflag = false;

	PubUtil myPubUtil = new PubUtil();

	/**
	 * ���캯��
	 */
	public DevMoniServer() {
	}

	/**
	 * <p>
	 * �����豸��ط������
	 * </p>
	 * 
	 */
	public void startMoni() {

		log.info("�����豸��س���,serverrun=>" + serverrun);
		stopflag = false;
		if (!serverrun) {
			start();
			log.info("��ʼִ��start()");
		}
	}

	/**
	 * ֹͣ�߳�����
	 */
	public void stopMoni() {
		log.info("ֹͣ�豸��س���,serverrun=>" + serverrun);
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
	 * �����߳�
	 */
	public synchronized void run() {
		// �������߳�
 
		log.info("�������߳�,serverrun=>" + serverrun);

		try {
			// �����˿ں�
			String port = myPubUtil.dealNull(myPubUtil.ReadConfig("System", "DevJKPort", "12315", "PowerView.ini")).trim();
			log.info("����豸��ض˿ں�=>" + port);
			if (port.equals(""))
				port = "12315";
			int myport = Integer.parseInt(port);

			try {

				// �����˿ڵ�socketͨ��
				server = new ServerSocket(myport);
				log.info("��ʼ�����˿�socket=>" + server.toString());
			} catch (Exception e) {
				log.error("ServerSocket():myport:"+myport, e);
				serverrun = false;
			}
			serverrun = true;

			// �����ļ�
			String xml = "<cross-domain-policy>";
			xml += "<allow-access-from domain=\"*\" to-ports='" + port + "'/>";
			xml += "</cross-domain-policy>";

			log.info("=================Devmoni Started=================");
			while (true) {
				// ����client socket�����ڽ����ض�9099�˿ڹ�������Ϣ
				Socket client = null;
				client = server.accept(); // �ȴ�������Ϣ
				log.info("client==>" + client.toString());
				// ��������

				PrintWriter pw = new PrintWriter(client.getOutputStream());
				// �������Ͳ����ļ�
				pw.print(xml + "\0");
				pw.flush();

				String message = null;
				try {
					DataInputStream in = null;
					byte[] buf = new byte[8];

					// ���������
					in = new DataInputStream(client.getInputStream());
					in.read(buf, 0, 8);
					int len1 = 0;
					String tmpstr = new String(buf, 0, 8);

					// �������ͷ���� <policy- ���������һѭ��

					if (tmpstr.equals("<policy-")) {
						log.info("�յ�    <policy-  ");

						continue;

						// client.close();

					} else {

						pw.print("���ӳɹ�!\0");
						log.info("���ӳɹ�!�յ��ı���" + tmpstr);
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

				// �ж��ǲ��Ǽ������
				if (message.indexOf(siginFlag) == 0) { // �������
					// �������ͼ�����ݵ��߳�
					log.info("ȷ���������" + message);
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
	 * ����߳�
	 * 
	 * @author ������
	 * 
	 */
	class MoniThread extends Thread {

		/**
		 * socketͨ��
		 */
		private Socket mys = null;

		/**
		 * ����豸��Ϣ���༯
		 */
		private List devlist;

		/**
		 * �������
		 * 
		 * @param s
		 *            socket
		 * @param bank
		 */
		public MoniThread(Socket s, String bank) {

			try {

				this.mys = s;
				log.info("MoniThread(" + s.toString() + "," + bank + ")");
				// �豸��Ϣģ��
				DevinfoModel myDevinfoModel = new DevinfoModel();

				// �����ؽ��豸�б���
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
				log.info("MoniThread  ��ѯ���ݿ⿪ʼ");
				// ����ǩ���ɹ�����...
				DataOutputStream out = new DataOutputStream(mys.getOutputStream());

				// wukp 111124 �����豸��Ϣ����
				int devnum = 0;
				if (devlist == null || devlist.isEmpty())
					devnum = 0;
				else
					devnum = devlist.size();
				log.warn("==========My dev num=[" + devnum + "]");
				String[] devnoarry = new String[devnum];

				// wukp 111124 ��д�豸����ַ��� ����sql���
				String devnostr = "";

				// wukp 111124 ���豸�����ӵ�������
				for (int j = 0; j < devnum; j++) {
					devnoarry[j] = ((Devinfo) devlist.get(j)).getDevno();
					devnostr += ("'" + devnoarry[j] + "',");
				}

				log.info("�豸�������==>" + devnostr);
				// wukp 111124 �趨 ��ʱʱ��
				String timeout = myPubUtil.dealNull(myPubUtil.ReadConfig("System", "DevMoniTimeOut", "600", "PowerView.ini")).trim();

				int inttimeout = Integer.parseInt(timeout);

				// wukp 111124 �豸״̬���� ���
				String statenos = myPubUtil.dealNull(myPubUtil.ReadConfig("System", "DevMoniStateno", "Z001", "PowerView.ini")).trim();

				String statecode = myPubUtil.dealNull(myPubUtil.ReadConfig("System", "DevMoniStatecode", "001", "PowerView.ini")).trim();
				log.info("statenos==" + statenos + "timeout==" + timeout + "statecode==" + statecode);

				List mydevstatelist = null;
				Hashtable tmpHashtable = new Hashtable();

				// �����豸����ַ�������sql��ѯ
				if (devnostr.length() > 0) {

					String polltag = "";
					String edate = "";
					String etime = "";
					String stateno = "";

					int tmptime1 = 0;
					int tmptime2 = 0;

					// wukp 111124 ��õ�ǰʱ�������
					String nowdate = myPubUtil.getNowDate(1);
					String nowtime = myPubUtil.getNowTime();

					devnostr = devnostr.substring(0, devnostr.length() - 1);

					DevstateModel myDevstateModel = new DevstateModel();

					// ����豸״̬ �༯
					mydevstatelist = myDevstateModel.getSomeDevStateList(devnostr);

					if (mydevstatelist != null) {

						int len = mydevstatelist.size();
						log.info("��õ��豸״̬��Ŀ��=" + len);
						for (int i = 0; i < len; i++) {

							Devstate temp = (Devstate) mydevstatelist.get(i);
							String tmpstr = (String) tmpHashtable.get(temp.getDevno());
							edate = temp.getEdate().trim();
							etime = temp.getEtime().trim();
							stateno = temp.getStateno().trim();
							// ���豸��Ϣ�����ҵ������Ϣ
							polltag = ((Devinfo) DevinfoModel.getDevFromList(temp.getDevno())).getPolltag().trim();

							// ����ǰʱ�䣬����Ϊ��λ
							tmptime1 = Integer.parseInt(nowtime.substring(0, 2)) * 3600 + Integer.parseInt(nowtime.substring(3, 5)) * 60 + Integer.parseInt(nowtime.substring(6, 8));
							if (etime.length() == 6)
								tmptime2 = Integer.parseInt(etime.substring(0, 2)) * 3600 + Integer.parseInt(etime.substring(2, 4)) * 60 + Integer.parseInt(etime.substring(4, 6));

							// ����������ڲ��ǵ�ǰ���ڣ����ҵ�ǰʱ����ڳ�ʱʱ�䣿
							if ((!edate.equals(nowdate)) && (tmptime1 > inttimeout) && (stateno.equals(statenos))) {

								if (tmpstr == null) { // �����tmpHashtable�в������������
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
							log.info("���͵�״̬==���=" + temp.getDevno() + ",״̬=" + tmpstr);
						}
					}
				}

				// ����ҳ�з������ݣ�������ʾ���
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