package com.lcjr.pvxp.util;

import java.util.*;

import org.apache.log4j.Logger;

import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.model.*;

/**
 * �Զ�ͳ�Ʊ���
 * 
 * @author ������
 * 
 * ʱ�䣺20120302
 * 
 * 
 */
public class StatAServer extends Thread {

	private static boolean startflag = false;

	Logger log = Logger.getLogger("web.lcjr.pvxp.util.StatAServer.java");




	/**
	 * ���캯��
	 */
	public StatAServer() {
		if (!startflag)
			this.start();
		log.info("�߳�����");
	}
	
	
	public void run() {
		// �������߳�
		try {
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();
			
			
			// �Զ�����
			AutostaModel myAsModel = new AutostaModel();
			Autosta myAs = new Autosta();
			
			List AsList = new ArrayList();
			String opentag = "";
			String nextstatday = "";
			String aftertime = "";
			String statetype = "";
			String statesort = "";
			String bankid = "";
			String trcdtypes = "";
			String statename = "";
			String bankRage = "";
			String id = "";
			
			
			// ����ʱ��
			String datestr = "";
			String timestr = "";
			
			
			// �豸��Ϣ
			DevinfoModel myDevinfoModel = new DevinfoModel();
			Devinfo myDevinfo = new Devinfo();
			
			List DiList = new ArrayList();
			List DnList = new ArrayList();
			
			
			// ���ס����豸�б�
			StringTokenizer wb;
			StringTokenizer wb1;
			List TcList = new ArrayList();
			List SnList = new ArrayList();
			String[][] SnArray = myPubUtil.Ini2Array("DevErrSta.ini", "SubDevice", "Num", "Device", "|");
			String[] sortlist = new String[0];
			List trcdnmlist = new ArrayList();
			
			
			// ��ʼ��������
			String qbegin = "";
			String qend = "";
			String laststat = "";
			
			
			// SQL
			String HQLstr1 = "";
			String HQLstr2 = "";
			String HQLstr3 = "";
			
			
			// filepath
			Config myConfig = new Config("pvxp.properties");
			String filepath = myConfig.getProperty("filepath") + "/statresult/";
			
			
			// tmp
			String tmpstr1 = "";
			String tmpstr2 = "";
			String tmpstr3 = "";
			
			while (true) {// ����ѭ��
			
				AsList = (List) myAsModel.getAutostaList();
				for (int i = 0; i < AsList.size(); i++) {
					// ���ͳ�Ʊ���
					DiList = new ArrayList();
					DnList = new ArrayList();
					
					TcList = new ArrayList();
					SnList = new ArrayList();
					sortlist = new String[0];
					trcdnmlist = new ArrayList();
					
					qbegin = "";
					qend = "";
					laststat = "";
					
					HQLstr1 = "";
					HQLstr2 = "";
					HQLstr3 = "";
					
					tmpstr1 = "";
					tmpstr2 = "";
					tmpstr3 = "";
					
					
					// ��ȡ����ʱ��
					datestr = myPubUtil.getNowDate(1);
					timestr = myPubUtil.getNowTime();
					timestr = myPubUtil.replace(timestr, ":", "");
					timestr = timestr.substring(0, 4);
					
					myAs = (Autosta) AsList.get(i);
					
					opentag = myAs.getOpentag().trim();
					nextstatday = myAs.getNextstatday().trim();
					aftertime = myAs.getAftertime().trim();
					
					if (opentag.equals("1")) {// �Ƿ���ͳ��
						if (Long.parseLong(nextstatday) <= Long.parseLong(datestr)) {// �Ƿ��´�ͳ������
							if (Long.parseLong(aftertime) < Long.parseLong(timestr)) {// �Ƿ��Ѿ�����ͳ��ʱ��
								// ͳ�����
								statetype = myAs.getStatetype().trim();
								statesort = myAs.getStatesort().trim();
								
								
								// ��������
								statename = myAs.getStatename().trim();
								
								
								// �豸�б�
								bankid = myAs.getBankid().trim();
								bankRage = new BankinfoModel().getBankRange(bankid);
								DiList = (List) myDevinfoModel.getDevListOfBank(bankRage);
								
								for (int j = 0; j < DiList.size(); j++) {
									myDevinfo = (Devinfo) DiList.get(j);
									DnList.add(myDevinfo.getDevno().trim());
								}
								
								
								// ���ס����豸�б�
								trcdtypes = myAs.getTrcdtypes().trim();
								wb = new StringTokenizer(trcdtypes, ",");
								tmpstr1 = wb.nextToken();
								tmpstr2 = tmpstr1.substring(0, 4);
								
								if (tmpstr2.equals("Type")) {// ����
									tmpstr3 = tmpstr1.substring(0, 5);
									wb = new StringTokenizer(trcdtypes, ",");
									// ����ͳ��
									sortlist = new String[wb.countTokens()];
									for (int x = 1; x <= wb.countTokens(); x++) {
										tmpstr1 = myPubUtil.ReadConfig("TradeType", tmpstr3 + "_" + x, "", "Trade.ini");
										int pos = tmpstr1.indexOf("|");
										if (pos != -1) {
											trcdnmlist.add(tmpstr1.substring(0, pos));
											sortlist[x - 1] = tmpstr1.substring(pos + 1);
										}
									}
									
									
									// �����б�
									while (wb.hasMoreTokens()) {
										tmpstr1 = wb.nextToken();
										tmpstr2 = myPubUtil.ReadConfig("TradeType", tmpstr1, "", "Trade.ini");
										tmpstr2 = tmpstr2.substring(tmpstr2.lastIndexOf("|") + 1);
										
										wb1 = new StringTokenizer(tmpstr2, ",");
										while (wb1.hasMoreTokens()) {
											TcList.add(wb1.nextToken());
										}
									}
								} else {// ���豸
									wb = new StringTokenizer(trcdtypes, ",");
									while (wb.hasMoreTokens()) {
										SnList.add(wb.nextToken());
									}
								}
								
								
								// ������
								id = myAs.getId().trim();
								int tmp = 1;
								
								
								// ͳ�Ʒ�֧����
								switch (Integer.parseInt(statetype)) {
									
									
									// �ձ�
									case 1:

										while (Long.parseLong(nextstatday) <= (Long.parseLong(myPubUtil.getOtherDate(-tmp)) + 1)) {
											// ��ʼ��������
											qbegin = myPubUtil.getOtherDate(-tmp);
											// qend = datestr;
											qend = qbegin;
											
											
											// ����HQL1
											HQLstr1 = "from Trcdtjday where (";
											for (int j = 0; j < DnList.size(); j++) {// �豸
												HQLstr1 += " devno='" + DnList.get(j) + "' or";
											}
											if (DnList.size() != 0) {
												HQLstr1 = HQLstr1.substring(0, HQLstr1.length() - 3) + " ) and (";
											} else {
												continue;
											}
											for (int j = 0; j < TcList.size(); j++) {// ����
												HQLstr1 += " trcdtype='" + TcList.get(j) + "' or";
											}
											if (TcList.size() != 0) {
												HQLstr1 = HQLstr1.substring(0, HQLstr1.length() - 3) + " ) and ";
											}
											HQLstr1 += " devdate='" + qbegin + "' ";
											if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
												HQLstr1 += " order by devno, moneytype";
											
											if (statesort.equals("01")) {// ����ͳ��
											
												log.info("��-����ͳ��-��-��");
												if (DnList.size() != 0) {
													JytjDayAServer myJytjDayAServer = new JytjDayAServer();
													
													myJytjDayAServer.setHQLstr(HQLstr1);
													myJytjDayAServer.setBankid(bankid);
													myJytjDayAServer.setOperid("SYS");
													myJytjDayAServer.setRepnm(statename);
													myJytjDayAServer.setQbegin(qbegin);
													myJytjDayAServer.setQend(qend);
													myJytjDayAServer.setUflag("3");
													myJytjDayAServer.setFilepath(filepath);
													myJytjDayAServer.setMissionId(id);
													myJytjDayAServer.setTcList(TcList);
													myJytjDayAServer.setStatmode(trcdtypes.substring(0, 5));
													myJytjDayAServer.setDevnoList(DnList);
													
													myJytjDayAServer.start();
													myJytjDayAServer.join();
													tmp++;
												}
											} else if (statesort.equals("02")) {// �豸�����������ͳ��
											
												log.info("��-�豸�����������ͳ��-��-��");
												if (DnList.size() != 0) {
													YxtjDayAServer myYxtjDayAServer = new YxtjDayAServer();
													
													myYxtjDayAServer.setHQLstr(HQLstr1);
													myYxtjDayAServer.setBankid(bankid);
													myYxtjDayAServer.setOperid("SYS");
													myYxtjDayAServer.setRepnm(statename);
													myYxtjDayAServer.setQbegin(qbegin);
													myYxtjDayAServer.setQend(qend);
													myYxtjDayAServer.setFilepath(filepath);
													myYxtjDayAServer.setMissionId(id);
													myYxtjDayAServer.setSortlist(sortlist);
													myYxtjDayAServer.setTrcdnmlist(trcdnmlist);
													myYxtjDayAServer.setDevnoList(DnList);
													
													myYxtjDayAServer.start();
													myYxtjDayAServer.join();
													tmp++;
												}
											} else if (statesort.equals("03")) {// �豸����ͳ��
											
												log.info("��-�豸����ͳ��-��-��");
												// ����HQL2
												HQLstr2 = "from Devtjday where (";
												for (int j = 0; j < DnList.size(); j++) {// �豸
													HQLstr2 += " devno='" + DnList.get(j) + "' or";
												}
												
												if (DnList.size() != 0) {
													HQLstr2 = HQLstr2.substring(0, HQLstr2.length() - 3) + " ) and (";
												} else {
													continue;
												}
												
												if (SnList.size() != 0) {
													HQLstr2 += " stateno='Z001' or ";
												}
												
												for (int j = 0; j < SnList.size(); j++) {
													HQLstr2 += " stateno='" + SnList.get(j) + "' or";
												}
												
												if (SnList.size() != 0) {
													HQLstr2 = HQLstr2.substring(0, HQLstr2.length() - 3) + " ) and ";
												}
												
												HQLstr2 += " devdate='" + qbegin + "' ";
												if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
													HQLstr2 += " order by devno, stateno";
												
												if (DnList.size() != 0) {
													DevtjDayAServer myDevtjDayAServer = new DevtjDayAServer();
													
													myDevtjDayAServer.setHQLstr(HQLstr2);
													myDevtjDayAServer.setBankid(bankid);
													myDevtjDayAServer.setOperid("SYS");
													myDevtjDayAServer.setRepnm(statename);
													myDevtjDayAServer.setQbegin(qbegin);
													myDevtjDayAServer.setQend(qend);
													myDevtjDayAServer.setFilepath(filepath);
													myDevtjDayAServer.setMissionId(id);
													myDevtjDayAServer.setDevnoList(DnList);
													
													myDevtjDayAServer.start();
													myDevtjDayAServer.join();
													tmp++;
												}
											} else if (statesort.equals("15")) {// ������ϸ��
												log.info("��-������ϸ��-��-��");
												// ����HQL3
												HQLstr3 = "from Mxb where (";
												for (int j = 0; j < DnList.size(); j++) {// �豸
													HQLstr3 += " devno='" + DnList.get(j) + "' or";
												}
												if (DnList.size() != 0) {
													HQLstr3 = HQLstr3.substring(0, HQLstr3.length() - 3) + " ) and (";
												} else {
													continue;
												}
												for (int j = 0; j < TcList.size(); j++) {// ����
													HQLstr3 += " devtrcd='" + TcList.get(j) + "' or";
												}
												if (TcList.size() != 0) {
													HQLstr3 = HQLstr3.substring(0, HQLstr3.length() - 3) + " ) and ";
												}
												HQLstr3 += " devdate='" + qbegin + "' ";
												if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
													HQLstr3 += " order by devno, devdate";
												
												if (DnList.size() != 0) {
													JymlDayAServer myJymlDayAServer = new JymlDayAServer();
													
													myJymlDayAServer.setHQLstr(HQLstr3);
													myJymlDayAServer.setBankid(bankid);
													myJymlDayAServer.setOperid("SYS");
													myJymlDayAServer.setRepnm(statename);
													myJymlDayAServer.setQbegin(qbegin);
													myJymlDayAServer.setQend(qend);
													myJymlDayAServer.setUflag("3");
													myJymlDayAServer.setFilepath(filepath);
													myJymlDayAServer.setMissionId(id);
													myJymlDayAServer.setTcList(TcList);
													myJymlDayAServer.setStatmode(trcdtypes.substring(0, 5));
													myJymlDayAServer.setDevnoList(DnList);
													
													myJymlDayAServer.start();
													myJymlDayAServer.join();
													tmp++;
												}
											}
											sleep(1000);
										}
										break;
									
									
									// �±�
									case 2:
										// ��ʼ��������
										qbegin = myPubUtil.getPreMonth();
										// qend = datestr.substring(0,6);
										qend = qbegin;
										
										
										// ����HQL1
										HQLstr1 = "from Trcdtjmonth where (";
										for (int j = 0; j < DnList.size(); j++) {// �豸
											HQLstr1 += " devno='" + DnList.get(j) + "' or";
										}
										if (DnList.size() != 0) {
											HQLstr1 = HQLstr1.substring(0, HQLstr1.length() - 3) + " ) and (";
										} else {
											continue;
										}
										for (int j = 0; j < TcList.size(); j++) {// ����
											HQLstr1 += " trcdtype='" + TcList.get(j) + "' or";
										}
										if (TcList.size() != 0) {
											HQLstr1 = HQLstr1.substring(0, HQLstr1.length() - 3) + " ) and ";
										}
										HQLstr1 += " devdate='" + qbegin + "' ";
										if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
											HQLstr1 += " order by devno, moneytype";
										
										if (statesort.equals("01")) {// ����ͳ��
										
											log.info("��-����ͳ��-��-��");
											if (DnList.size() != 0) {
												JytjMonthAServer myJytjMonthAServer = new JytjMonthAServer();
												
												myJytjMonthAServer.setHQLstr(HQLstr1);
												myJytjMonthAServer.setBankid(bankid);
												myJytjMonthAServer.setOperid("SYS");
												myJytjMonthAServer.setRepnm(statename);
												myJytjMonthAServer.setQbegin(qbegin);
												myJytjMonthAServer.setQend(qend);
												myJytjMonthAServer.setUflag("3");
												myJytjMonthAServer.setFilepath(filepath);
												myJytjMonthAServer.setMissionId(id);
												myJytjMonthAServer.setTcList(TcList);
												myJytjMonthAServer.setStatmode(trcdtypes.substring(0, 5));
												myJytjMonthAServer.setDevnoList(DnList);
												
												myJytjMonthAServer.start();
											}
										} else if (statesort.equals("02")) {// �豸�����������ͳ��
										
											log.info("��-�豸�����������ͳ��-��-��");
											if (DnList.size() != 0) {
												YxtjMonthAServer myYxtjMonthAServer = new YxtjMonthAServer();
												
												myYxtjMonthAServer.setHQLstr(HQLstr1);
												myYxtjMonthAServer.setBankid(bankid);
												myYxtjMonthAServer.setOperid("SYS");
												myYxtjMonthAServer.setRepnm(statename);
												myYxtjMonthAServer.setQbegin(qbegin);
												myYxtjMonthAServer.setQend(qend);
												myYxtjMonthAServer.setFilepath(filepath);
												myYxtjMonthAServer.setMissionId(id);
												myYxtjMonthAServer.setSortlist(sortlist);
												myYxtjMonthAServer.setTrcdnmlist(trcdnmlist);
												myYxtjMonthAServer.setDevnoList(DnList);
												
												myYxtjMonthAServer.start();
											}
										} else if (statesort.equals("03")) {// �豸����ͳ��
											
											log.info("��-�豸����ͳ��-��-��");
											// ����HQL2
											HQLstr2 = "from Devtjmonth where (";
											for (int j = 0; j < DnList.size(); j++) {// �豸
												HQLstr2 += " devno='" + DnList.get(j) + "' or";
											}
											if (DnList.size() != 0) {
												HQLstr2 = HQLstr2.substring(0, HQLstr2.length() - 3) + " ) and (";
											} else {
												continue;
											}
											
											if (SnList.size() != 0) {
												HQLstr2 += " stateno='Z001' or ";
											}
											
											for (int j = 0; j < SnList.size(); j++) {
												HQLstr2 += " stateno='" + SnList.get(j) + "' or";
											}
											if (SnList.size() != 0) {
												HQLstr2 = HQLstr2.substring(0, HQLstr2.length() - 3) + " ) and ";
											}
											HQLstr2 += " devdate='" + qbegin + "' ";
											if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
												HQLstr2 += " order by devno, stateno";
											
											if (DnList.size() != 0) {
												DevtjMonthAServer myDevtjMonthAServer = new DevtjMonthAServer();
												
												myDevtjMonthAServer.setHQLstr(HQLstr2);
												myDevtjMonthAServer.setBankid(bankid);
												myDevtjMonthAServer.setOperid("SYS");
												myDevtjMonthAServer.setRepnm(statename);
												myDevtjMonthAServer.setQbegin(qbegin);
												myDevtjMonthAServer.setQend(qend);
												myDevtjMonthAServer.setFilepath(filepath);
												myDevtjMonthAServer.setMissionId(id);
												myDevtjMonthAServer.setDevnoList(DnList);
												
												myDevtjMonthAServer.start();
											}
										}
										break;
									
									
									// �걨
									case 3:
										// ��ʼ��������
										qbegin = Integer.toString(Integer.parseInt(datestr.substring(0, 4)) - 1);
										// qend = datestr.substring(0,4);
										qend = qbegin;
										
										
										// ����HQL1
										HQLstr1 = "from Trcdtjyear where (";
										for (int j = 0; j < DnList.size(); j++) {// �豸
											HQLstr1 += " devno='" + DnList.get(j) + "' or";
										}
										if (DnList.size() != 0) {
											HQLstr1 = HQLstr1.substring(0, HQLstr1.length() - 3) + " ) and (";
										} else {
											continue;
										}
										for (int j = 0; j < TcList.size(); j++) {// ����
											HQLstr1 += " trcdtype='" + TcList.get(j) + "' or";
										}
										if (TcList.size() != 0) {
											HQLstr1 = HQLstr1.substring(0, HQLstr1.length() - 3) + " ) and ";
										}
										HQLstr1 += " devdate='" + qbegin + "' ";
										if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
											HQLstr1 += " order by devno, moneytype";
										
										if (statesort.equals("01")) {// ����ͳ��
										
											log.info("��-����ͳ��-��-��");
											if (DnList.size() != 0) {
												JytjYearAServer myJytjYearAServer = new JytjYearAServer();
												
												myJytjYearAServer.setHQLstr(HQLstr1);
												myJytjYearAServer.setBankid(bankid);
												myJytjYearAServer.setOperid("SYS");
												myJytjYearAServer.setRepnm(statename);
												myJytjYearAServer.setQbegin(qbegin);
												myJytjYearAServer.setQend(qend);
												myJytjYearAServer.setUflag("3");
												myJytjYearAServer.setFilepath(filepath);
												myJytjYearAServer.setMissionId(id);
												myJytjYearAServer.setTcList(TcList);
												myJytjYearAServer.setStatmode(trcdtypes.substring(0, 5));
												myJytjYearAServer.setDevnoList(DnList);
												
												myJytjYearAServer.start();
											}
										} else if (statesort.equals("02")) {// �豸�����������ͳ��
										
											log.info("��-�豸�����������ͳ��-��-��");
											if (DnList.size() != 0) {
												YxtjYearAServer myYxtjYearAServer = new YxtjYearAServer();
												
												myYxtjYearAServer.setHQLstr(HQLstr1);
												myYxtjYearAServer.setBankid(bankid);
												myYxtjYearAServer.setOperid("SYS");
												myYxtjYearAServer.setRepnm(statename);
												myYxtjYearAServer.setQbegin(qbegin);
												myYxtjYearAServer.setQend(qend);
												myYxtjYearAServer.setFilepath(filepath);
												myYxtjYearAServer.setMissionId(id);
												myYxtjYearAServer.setSortlist(sortlist);
												myYxtjYearAServer.setTrcdnmlist(trcdnmlist);
												myYxtjYearAServer.setDevnoList(DnList);
												
												myYxtjYearAServer.start();
											}
										} else if (statesort.equals("03")) {// �豸����ͳ��
										
											log.info("��-�豸����ͳ��-��-��");
											// ����HQL2
											HQLstr2 = "from Devtjyear where (";
											for (int j = 0; j < DnList.size(); j++) {// �豸
												HQLstr2 += " devno='" + DnList.get(j) + "' or";
											}
											if (DnList.size() != 0) {
												HQLstr2 = HQLstr2.substring(0, HQLstr2.length() - 3) + " ) and (";
											} else {
												continue;
											}
											
											if (SnList.size() != 0) {
												HQLstr2 += " stateno='Z001' or ";
											}
											
											for (int j = 0; j < SnList.size(); j++) {
												HQLstr2 += " stateno='" + SnList.get(j) + "' or";
											}
											if (SnList.size() != 0) {
												HQLstr2 = HQLstr2.substring(0, HQLstr2.length() - 3) + " ) and ";
											}
											HQLstr2 += " devdate='" + qbegin + "' ";
											if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
												HQLstr2 += " order by devno, stateno";
											
											if (DnList.size() != 0) {
												DevtjYearAServer myDevtjYearAServer = new DevtjYearAServer();
												
												myDevtjYearAServer.setHQLstr(HQLstr2);
												myDevtjYearAServer.setBankid(bankid);
												myDevtjYearAServer.setOperid("SYS");
												myDevtjYearAServer.setRepnm(statename);
												myDevtjYearAServer.setQbegin(qbegin);
												myDevtjYearAServer.setQend(qend);
												myDevtjYearAServer.setFilepath(filepath);
												myDevtjYearAServer.setMissionId(id);
												myDevtjYearAServer.setDevnoList(DnList);
												
												myDevtjYearAServer.start();
											}
										}
										break;
									
									
									// ����
									case 4:
										// ��ʼ��������
										qend = myPubUtil.getPreMonth();
										// qend = datestr.substring(0,6);
										qbegin = Integer.toString(Integer.parseInt(qend) - 2);
										if (!myPubUtil.isYYYYMM(qbegin)) {
											qbegin = datestr.substring(0, 4) + "01";
										}
										
										
										// ����HQL1
										HQLstr1 = "from Trcdtjmonth where (";
										for (int j = 0; j < DnList.size(); j++) {// �豸
											HQLstr1 += " devno='" + DnList.get(j) + "' or";
										}
										if (DnList.size() != 0) {
											HQLstr1 = HQLstr1.substring(0, HQLstr1.length() - 3) + " ) and (";
										} else {
											continue;
										}
										for (int j = 0; j < TcList.size(); j++) {// ����
											HQLstr1 += " trcdtype='" + TcList.get(j) + "' or";
										}
										if (TcList.size() != 0) {
											HQLstr1 = HQLstr1.substring(0, HQLstr1.length() - 3) + " ) and ";
										}
										
										HQLstr1 += " devdate >= '" + qbegin + "' and  devdate <='" + qend + "'";
										if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
											HQLstr1 += " order by devno, moneytype";
										
										if (statesort.equals("01")) {// ����ͳ��
										
											log.info("��-����ͳ��-��-��");
											if (DnList.size() != 0) {
												JytjQuarterAServer myJytjQuarterAServer = new JytjQuarterAServer();
												
												myJytjQuarterAServer.setHQLstr(HQLstr1);
												myJytjQuarterAServer.setBankid(bankid);
												myJytjQuarterAServer.setOperid("SYS");
												myJytjQuarterAServer.setRepnm(statename);
												myJytjQuarterAServer.setQbegin(qbegin);
												myJytjQuarterAServer.setQend(qend);
												myJytjQuarterAServer.setUflag("3");
												myJytjQuarterAServer.setFilepath(filepath);
												myJytjQuarterAServer.setMissionId(id);
												myJytjQuarterAServer.setTcList(TcList);
												myJytjQuarterAServer.setStatmode(trcdtypes.substring(0, 5));
												myJytjQuarterAServer.setDevnoList(DnList);
												
												myJytjQuarterAServer.start();
											}
										} else if (statesort.equals("02")) {// �豸�����������ͳ��
										
											log.info("��-�豸�����������ͳ��-��-��");
											if (DnList.size() != 0) {
												YxtjQuarterAServer myYxtjQuarterAServer = new YxtjQuarterAServer();
												
												myYxtjQuarterAServer.setHQLstr(HQLstr1);
												myYxtjQuarterAServer.setBankid(bankid);
												myYxtjQuarterAServer.setOperid("SYS");
												myYxtjQuarterAServer.setRepnm(statename);
												myYxtjQuarterAServer.setQbegin(qbegin);
												myYxtjQuarterAServer.setQend(qend);
												myYxtjQuarterAServer.setFilepath(filepath);
												myYxtjQuarterAServer.setMissionId(id);
												myYxtjQuarterAServer.setSortlist(sortlist);
												myYxtjQuarterAServer.setTrcdnmlist(trcdnmlist);
												myYxtjQuarterAServer.setDevnoList(DnList);
												
												myYxtjQuarterAServer.start();
											}
										} else if (statesort.equals("03")) {// �豸����ͳ��
										
											log.info("��-�豸����ͳ��-��-��");
											// ����HQL2
											HQLstr2 = "from Devtjmonth where (";
											for (int j = 0; j < DnList.size(); j++) {// �豸
												HQLstr2 += " devno='" + DnList.get(j) + "' or";
											}
											if (DnList.size() != 0) {
												HQLstr2 = HQLstr2.substring(0, HQLstr2.length() - 3) + " ) and (";
											} else {
												continue;
											}
											
											if (SnList.size() != 0) {
												HQLstr2 += " stateno='Z001' or ";
											}
											
											for (int j = 0; j < SnList.size(); j++) {
												HQLstr2 += " stateno='" + SnList.get(j) + "' or";
											}
											if (SnList.size() != 0) {
												HQLstr2 = HQLstr2.substring(0, HQLstr2.length() - 3) + " ) and ";
											}
											HQLstr2 += " devdate >= '" + qbegin + "' and  devdate <='" + qend + "'";
											if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
												HQLstr2 += " order by devno, stateno";
											
											if (DnList.size() != 0) {
												DevtjQuarterAServer myDevtjQuarterAServer = new DevtjQuarterAServer();
												
												myDevtjQuarterAServer.setHQLstr(HQLstr2);
												myDevtjQuarterAServer.setBankid(bankid);
												myDevtjQuarterAServer.setOperid("SYS");
												myDevtjQuarterAServer.setRepnm(statename);
												myDevtjQuarterAServer.setQbegin(qbegin);
												myDevtjQuarterAServer.setQend(qend);
												myDevtjQuarterAServer.setFilepath(filepath);
												myDevtjQuarterAServer.setMissionId(id);
												myDevtjQuarterAServer.setDevnoList(DnList);
												
												myDevtjQuarterAServer.start();
											}
										}
										break;
									
									
									// ���걨
									case 5:
										// ��ʼ��������
										qend = myPubUtil.getPreMonth();
										// qend = datestr.substring(0,6);
										qbegin = Integer.toString(Integer.parseInt(qend) - 5);
										if (!myPubUtil.isYYYYMM(qbegin)) {
											qbegin = datestr.substring(0, 4) + "01";
										}
										
										
										// ����HQL1
										HQLstr1 = "from Trcdtjmonth where (";
										for (int j = 0; j < DnList.size(); j++) {// �豸
											HQLstr1 += " devno='" + DnList.get(j) + "' or";
										}
										if (DnList.size() != 0) {
											HQLstr1 = HQLstr1.substring(0, HQLstr1.length() - 3) + " ) and (";
										} else {
											continue;
										}
										for (int j = 0; j < TcList.size(); j++) {// ����
											HQLstr1 += " trcdtype='" + TcList.get(j) + "' or";
										}
										if (TcList.size() != 0) {
											HQLstr1 = HQLstr1.substring(0, HQLstr1.length() - 3) + " ) and ";
										}
										
										HQLstr1 += " devdate>='" + qbegin + "' and devdate <='" + qend + "'";
										if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
											HQLstr1 += " order by devno, moneytype";
										
										if (statesort.equals("01")) {// ����ͳ��
										
											log.info("��-����ͳ��-����-��");
											if (DnList.size() != 0) {
												JytjHalfAServer myJytjHalfAServer = new JytjHalfAServer();
												
												myJytjHalfAServer.setHQLstr(HQLstr1);
												myJytjHalfAServer.setBankid(bankid);
												myJytjHalfAServer.setOperid("SYS");
												myJytjHalfAServer.setRepnm(statename);
												myJytjHalfAServer.setQbegin(qbegin);
												myJytjHalfAServer.setQend(qend);
												myJytjHalfAServer.setUflag("3");
												myJytjHalfAServer.setFilepath(filepath);
												myJytjHalfAServer.setMissionId(id);
												myJytjHalfAServer.setTcList(TcList);
												myJytjHalfAServer.setStatmode(trcdtypes.substring(0, 5));
												myJytjHalfAServer.setDevnoList(DnList);
												
												myJytjHalfAServer.start();
											}
										} else if (statesort.equals("02")) {// �豸�����������ͳ��
										
											log.info("��-�豸�����������ͳ��-����-��");
											if (DnList.size() != 0) {
												YxtjHalfAServer myYxtjHalfAServer = new YxtjHalfAServer();
												
												myYxtjHalfAServer.setHQLstr(HQLstr1);
												myYxtjHalfAServer.setBankid(bankid);
												myYxtjHalfAServer.setOperid("SYS");
												myYxtjHalfAServer.setRepnm(statename);
												myYxtjHalfAServer.setQbegin(qbegin);
												myYxtjHalfAServer.setQend(qend);
												myYxtjHalfAServer.setFilepath(filepath);
												myYxtjHalfAServer.setMissionId(id);
												myYxtjHalfAServer.setSortlist(sortlist);
												myYxtjHalfAServer.setTrcdnmlist(trcdnmlist);
												myYxtjHalfAServer.setDevnoList(DnList);
												
												myYxtjHalfAServer.start();
											}
										} else if (statesort.equals("03")) {// �豸����ͳ��
										
											log.info("��-�豸����ͳ��-����-��");
											// ����HQL2
											HQLstr2 = "from Devtjmonth where (";
											for (int j = 0; j < DnList.size(); j++) {// �豸
												HQLstr2 += " devno='" + DnList.get(j) + "' or";
											}
											if (DnList.size() != 0) {
												HQLstr2 = HQLstr2.substring(0, HQLstr2.length() - 3) + " ) and (";
											} else {
												continue;
											}
											
											if (SnList.size() != 0) {
												HQLstr2 += " stateno='Z001' or ";
											}
											
											for (int j = 0; j < SnList.size(); j++) {
												HQLstr2 += " stateno='" + SnList.get(j) + "' or";
											}
											if (SnList.size() != 0) {
												HQLstr2 = HQLstr2.substring(0, HQLstr2.length() - 3) + " ) and ";
											}
											HQLstr2 += " devdate>='" + qbegin + "' and devdate <='" + qend + "'";
											if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
												HQLstr2 += " order by devno, stateno";
											
											if (DnList.size() != 0) {
												DevtjHalfAServer myDevtjHalfAServer = new DevtjHalfAServer();
												
												myDevtjHalfAServer.setHQLstr(HQLstr2);
												myDevtjHalfAServer.setBankid(bankid);
												myDevtjHalfAServer.setOperid("SYS");
												myDevtjHalfAServer.setRepnm(statename);
												myDevtjHalfAServer.setQbegin(qbegin);
												myDevtjHalfAServer.setQend(qend);
												myDevtjHalfAServer.setFilepath(filepath);
												myDevtjHalfAServer.setMissionId(id);
												myDevtjHalfAServer.setDevnoList(DnList);
												
												myDevtjHalfAServer.start();
											}
										}
										break;
									
								}
							}
						}
					}
					sleep(1000);
				}
				
				long stime = Integer.parseInt(myPubUtil.ReadConfig("Statistic", "LoopTimeOut", "3600", "PowerView.ini"));
				sleep(stime);
			}
			
		} catch (Exception e) {
			log.error("catch����", e);
		}
	}
	
}