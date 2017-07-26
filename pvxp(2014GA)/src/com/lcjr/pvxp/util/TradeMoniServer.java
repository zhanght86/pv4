package com.lcjr.pvxp.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.websocket.sent.SendTradeMessage;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> ���׼�ط������
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
public class TradeMoniServer extends Thread {

	Logger log = Logger.getLogger("web.lcjr.pvxp.util.TradeMoniServer.java");

	// private static final ThreadLocal serverContext = new ThreadLocal();

	private static Vector threadVector = new Vector();

	private static Vector alldataVector = new Vector();

	private static boolean serverrunflag = false;

	private static boolean rsrunflag = false;

	private static ServerSocket server = null;

	private static boolean stopflag = false;

	private static TradeMoniMain myTradeMoniMain = null;

	public Vector getThreadVector() {
		return this.threadVector;
	}

	/**
	 * 
	 * 
	 */
	public TradeMoniServer() {

	}

	public synchronized boolean startMoni() {
		try {
			// �������ս���ƽ̨���ݵ��̣߳�����������д�������ͼ�����ݵ��߳�
			if (!rsrunflag) {
				rsrunflag = true;
				myTradeMoniMain = null;
				myTradeMoniMain = new TradeMoniMain();
				myTradeMoniMain.start();
			}

			// �������̣߳��������տͻ��˵�����ͽ�������
			if (!serverrunflag) {
				serverrunflag = true;
				if (server == null) {
					start();
				}
			}
			stopflag = false;

			if (rsrunflag && serverrunflag)
				return true;
			else
				return false;

		} catch (Exception e) {
			log.warn("startMoni()", e);
			return false;
		}
	}

	public void stopMoni() {
		serverrunflag = false;
		rsrunflag = false;
		stopflag = true;
	}

	public synchronized void run() {

		// �Զ������߳�
		StatAServer myServer = new StatAServer();
		// myServer.start(); // ������StatAServer���д������������̹߳��캯��������������������ͻᱨ�߳��쳣
		
		
		
		SendTradeMessage sm = new SendTradeMessage();
//		for (int i = 0; i < 100; i++) {
//			sm.sendTradeMsg("��ǵ���", true);
//			try {
//				Thread.sleep(10*1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

		// �������߳�
		PubUtil myPubUtil = new PubUtil();
		Socket client = null;
		DataInputStream in = null;
		byte[] buf = new byte[1024];
		String siginFlag = "SignIn";
		String tradeFlag = "Tdata";
		String resnFlag = "ReSIN";

		try {
			String myportstr = myPubUtil.dealNull(myPubUtil.ReadConfig("System", "TradeMoniPort", "8088", "PowerView.ini")).trim();
			if (myportstr.equals(""))
				myportstr = "8088";
			int myport = Integer.parseInt(myportstr);
			server = new ServerSocket(myport);
			threadVector = new Vector();
			// �����ļ�
			String xml = "<cross-domain-policy>";
			xml += "<allow-access-from domain=\"*\" to-ports='" + myportstr + "'/>";
			xml += "</cross-domain-policy>";

			System.out.println("=================TradeMoniServer Listenning Server started=================");
			while (true) {

				client = server.accept();
				PrintWriter pw = new PrintWriter(client.getOutputStream());
				// �������Ͳ����ļ�
				pw.print(xml + "\0");
				pw.flush();

				String message = null;
				// System.out.println("Server accepted");
				try {

					in = new DataInputStream(client.getInputStream());
					// System.out.println("input");
					in.read(buf, 0, 8);
					int len1 = 8;
					String tmpstr = new String(buf, 0, 8);
					// System.out.println("tmpstr = "+tmpstr);
					if (tmpstr.equals("<policy-")) {

						// in.close();
						// client.close();
						continue;
					} else {
						pw.print("���ӳɹ�!\0");
						len1 = Integer.parseInt(tmpstr);
						in.read(buf, 0, len1);
						message = new String(buf, 0, len1);
						System.out.println("message = " + message);
					}
				} catch (Exception nume) {
					log.warn("run()", nume);
					nume.printStackTrace();
					continue;

				}

				if (message == null)
					continue;
				if (!serverrunflag) // ���ֹͣ��أ��������κδ���
					continue;

				// System.out.println("===========recieved msg =["+message+"]");
				if (message.equals(siginFlag)) { // �ͻ���ǩ��
					// �������ͼ�����ݵ��߳�
					XMLServer myXMLServer = new XMLServer(client);
					// ����̵߳��߳��б�
					threadVector.add(myXMLServer);
					// client.setSoLinger(true,0);
					// in.close();
					// client.close();

				} else if (message.indexOf(tradeFlag) == 0) { // ��������
					// message = message.substring(tradeFlag.length());
					alldataVector.add(message);
					// client.setSoLinger(true,0);
					in.close();
					client.close();
				} else if (message.indexOf(resnFlag) == 0 && threadVector.size() > 0) { // ��ǩ��

					KeepThreadAlive myKeepThreadAlive = new KeepThreadAlive(message.substring(resnFlag.length()));

					myKeepThreadAlive.start();
					// client.setSoLinger(true,0);
					in.close();
					client.close();
				}

			}
		} catch (IOException ex) {
			serverrunflag = false;
			log.warn("run()", ex);
			try {
				in.close();
				client.close();
			} catch (IOException e) {
				log.warn("run()", e);
			}

			// System.out.println("============================TradeMoniServer
			// Listenning clients stopped (Exception)..."+ex);
		} finally {
			try {
				in.close();
				client.close();
			} catch (IOException e) {
				log.warn("finally {}", e);
			}
		}

	}

	class XMLServer extends Thread {
		private Socket mys = null;

		private Vector msgVector = new Vector();

		private int backcount = 600000; // һ�ְ�ĵ���ʱ����=60*1.5/15

		public void addmsg(String msg) {
			try {
				msgVector.add(msg);
			} catch (Exception e) {
			}
		}

		public void setBackcount(int backcount) {
			this.backcount = backcount;
		}

		public int getBackcount() {
			return this.backcount;
		}

		public XMLServer(Socket s) {
			this.mys = s;
			start();
		}

		public void stopNow() {
			stopflag = true;
		}

		public void run() {
			try {
				// ����ǩ���ɹ�����...
				DataOutputStream out = new DataOutputStream(mys.getOutputStream());
				String signinstr = "SignInBack" + this.getName();
				byte[] signins = signinstr.getBytes();
				out.write(signins);
				out.write(0);
				out.flush();
				// out.close();

				int retrycount = 0;

				while (true) {
					backcount--;
					if (backcount < 0)
						break;
					if (stopflag)
						break;
					if (msgVector == null || msgVector.size() == 0) {

					} else {

						// ��ȡҪ���͵�����
						String msgtemp = (String) msgVector.get(0);
						if (msgtemp == null) {
							msgVector.remove(0);
							this.sleep(15);
							continue;
						}
						/*
						 * //�ֽ�Ҫ���͵����� StringTokenizer st = new
						 * StringTokenizer(msgtemp,"|||"); if(
						 * st.countTokens()<2 ){ msgVector.remove(0);
						 * this.sleep(15); continue; } //���Ҫ���͵����� msgtemp =
						 * "<Tdata devno=\""+st.nextToken()+"\"
						 * tcode=\""+st.nextToken()+"\" />";
						 */
						try {
							out = new DataOutputStream(mys.getOutputStream());
							byte[] s = msgtemp.getBytes();
							out.write(s);
							out.write(0);
							out.flush();
						} catch (Exception exx) {
							retrycount++;
							if (retrycount >= 3) {
								break;
							} else {
								this.sleep(15);
								continue;
							}
						}
						retrycount = 0;
						msgVector.remove(0);
					}
					this.sleep(15);
				}
				out.close();
				mys.close();
				// System.out.println("============================"+this.getName()+"
				// Send to a client
				// stopped...backcount=["+String.valueOf(backcount)+"]");
			} catch (Exception ex) {
				log.warn("Exception ex", ex);
				// System.out.println("============================Send to a
				// client stopped(Exception)..."+ex);
			} finally {
				try {
					mys.close();
				} catch (Exception e) {
					log.error("error", e);
				}
			}
		}

	}

	class TradeMoniMain extends Thread {

		private boolean stopflag = false;

		public TradeMoniMain() {
		}

		public synchronized void run() {
			try {
				// System.out.println("============================TradeMoniServer
				// Read&Send Data thread started...");

				int testcount = 0;

				while (true) {
					if (!rsrunflag)
						break;
					// if( threadVector==null||threadVector.size()==0 ){
					// this.sleep(2000);
					// }else{
					String msgtmp = "";
					if (alldataVector == null || alldataVector.size() == 0) {
						this.sleep(20);
						continue;
					}
					try {
						msgtmp = (String) alldataVector.get(0);
						alldataVector.remove(0);
						if (msgtmp.length() == 0) {
							this.sleep(20);
							continue;
						}
					} catch (Exception ex) {
						this.sleep(20);
						continue;
					}

					// �������ݵ����߳�
					int threadCount = threadVector.size();
					for (int i = 0; i < threadCount; i++) {
						try {
							XMLServer temp = (XMLServer) threadVector.get(i);
							if (temp == null || (!temp.isAlive())) {
								threadVector.remove(temp);
								i--;
								continue;
							} else {
								temp.addmsg(msgtmp);
							}
						} catch (Exception ex) {
							log.error("error", ex);
						}
					}
					// }
					this.sleep(20);
				}
				rsrunflag = false;
				// System.out.println("============================TradeMoniServer
				// Read&Send Data thread stopped...");
			} catch (Exception e) {

				rsrunflag = false;
				log.warn("Exception e", e);
				// System.out.println("============================TradeMoniServer
				// Read&Send Data thread stopped(Exception)..."+e);
			}

		}

	}

	class KeepThreadAlive extends Thread {
		private String threadName;

		public KeepThreadAlive(String threadName) {
			this.threadName = threadName;
		}

		public void run() {
			// System.out.println("I'm saving "+threadName+"'s life!");
			if (threadVector != null && threadVector.size() > 0) {
				int threadCount = threadVector.size();
				for (int i = 0; i < threadCount; i++) {
					XMLServer temp = (XMLServer) threadVector.get(i);
					if (temp != null) {
						if (threadName.equals(temp.getName())) {
							temp.setBackcount(6000);
							break;
						}
					}
				}
			}
		}

	}

	/*
	 * 
	 * class TradeDataServer extends Thread { public TradeDataServer(){ }
	 * 
	 * public void run() { //System.out.println("TCPServer run ....");
	 * 
	 * String message = null; ServerSocket s = null; DataInputStream in = null;
	 * byte[] buf = new byte[1024];
	 * 
	 * try { s = new ServerSocket(9009); while(true) { Socket socket =
	 * s.accept(); try { //System.out.println("socket = "+socket); in = new
	 * DataInputStream(socket.getInputStream()); in.read(buf,0,8); int len =
	 * Integer.parseInt(new String(buf,0,8)); in.read(buf,0,len); message = new
	 * String(buf,0,len); //System.out.println("message = "+message); //
	 * �����ǩ�����ݰ� if(message.equals("SignIn")) { //System.out.println("signin");
	 * // ע�⣬����Ҫ���Ƕ��ǩ������� String temp =
	 * socket.getInetAddress().getHostAddress();
	 * 
	 * int i = 0; for(;i<clientTable.size();i++) { Vessel v =
	 * (Vessel)clientTable.get(i);
	 * if(temp.compareTo(v.address.getHostAddress())==0) {
	 * //System.out.println("address repeat"); v.used = true; break; } }
	 * 
	 * if(i==clientTable.size()) { //System.out.println("address a new ip
	 * "+socket.getInetAddress()); clientTable.add(new
	 * Vessel(socket.getInetAddress())); } } else { for(int
	 * i=0;i<clientTable.size();i++) { Vessel v = (Vessel)clientTable.get(i);
	 * if(v.used) v.pot.add(message); } }
	 * 
	 * try{socket.close();} catch(IOException e){} } catch(Exception e) {
	 * try{socket.close();} catch(IOException ioe){} } } } catch(IOException se)
	 * { try{s.close();} catch(IOException ioe){} } } }
	 */
	public static void main(String[] args) {
		// ���׼���߳�
		TradeMoniServer myTradeMoniServer = new TradeMoniServer();
		myTradeMoniServer.startMoni();

	}
}