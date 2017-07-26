package com.lcjr.pvxp.util;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

import org.apache.log4j.Logger;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.bean.*;
import com.lcjr.pvxp.model.*;

import net.sf.hibernate.*;

/**
 * �Զ�ͳ�Ʊ��� ����ͳ�� ���±���
 * 
 * @author ������
 * 
 * ʱ�䣺20120307
 * 
 */
public class JytjMonthAServer extends Thread {
	
	Logger log = Logger.getLogger("web.lcjr.pvxp.util.JytjMonthAServer.java");
	
	
	private String HQLstr = null;
	
	
	private String bankid = null;
	
	
	private String operid = null;
	
	
	private String repnm = null;
	
	
	private String qbegin = null;
	
	
	private String qend = null;
	
	
	private String uflag = null;
	
	
	private String filepath = null;
	
	
	private String missionId = null;
	
	
	private String statmode = null;
	
	
	private List TcList = new ArrayList();
	
	
	private List devnoList = new ArrayList();
	
	
	
	public String getHQLstr() {
		return (this.HQLstr);
	}
	
	
	public String getBankid() {
		return (this.bankid);
	}
	
	
	public String getOperid() {
		return (this.operid);
	}
	
	
	public String getRepnm() {
		return (this.repnm);
	}
	
	
	public String getQbegin() {
		return (this.qbegin);
	}
	
	
	public String getQend() {
		return (this.qend);
	}
	
	
	public String getUflag() {
		return (this.uflag);
	}
	
	
	public String getFilepath() {
		return (this.filepath);
	}
	
	
	
	public String getStatmode() {
		return (this.statmode);
	}
	
	
	public List getTcList() {
		return (this.TcList);
	}
	
	
	public List getDevnoList() {
		return (this.devnoList);
	}
	
	
	public void setHQLstr(String HQLstr) {
		this.HQLstr = HQLstr;
	}
	
	
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	
	
	public void setOperid(String operid) {
		this.operid = operid;
	}
	
	
	public void setRepnm(String repnm) {
		this.repnm = repnm;
	}
	
	
	public void setQbegin(String qbegin) {
		this.qbegin = qbegin;
	}
	
	
	public void setQend(String qend) {
		this.qend = qend;
	}
	
	
	public void setUflag(String uflag) {
		this.uflag = uflag;
	}
	
	
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	
	
	public void setStatmode(String statmode) {
		this.statmode = statmode;
	}
	
	
	public void setTcList(List TcList) {
		this.TcList = TcList;
	}
	
	
	public void setDevnoList(List devnoList) {
		this.devnoList = devnoList;
	}
	
	
	public JytjMonthAServer() {
		
	}
	
	
	public void run() {
		// �������߳�
		log.info("�� ����ͳ �� ��ʼ");
		
		try {
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();
			
			List result = new ArrayList();
			List bizhArray = new ArrayList();
			List devnoArray = new ArrayList();
			List ttrnumArray = new ArrayList();
			List strnumArray = new ArrayList();
			List ttrsumArray = new ArrayList();
			List strsumArray = new ArrayList();
			List AllttrsumArray = new ArrayList();
			List AllstrsumArray = new ArrayList();
			String bizh = "";
			String tmpbizh = "";
			String devno = "";
			
			String tmppr1 = "";
			String tmppr2 = "";
			
			long ttrnum = 0;
			long strnum = 0;
			
			long ttrsum = 0;
			long strsum = 0;
			
			int iflag = 0;
			int tmplen = 0;
			int tmppos = 0;
			Vector DevStrsumVector = new Vector();
			Vector DevTtrsumVector = new Vector();
			
			Vector ResultVector = new Vector();
			String ResultString = new String();
			String[] devaddr = new String[devnoList.size()];
			String TimeStr = myPubUtil.getNowTime();
			TimeStr = myPubUtil.replace(TimeStr, ":", "");
			String DateTimeStr = myPubUtil.getNowDate(1) + TimeStr;
			
			try {
				// ��ͳ�Ʊ���
				
				// ��¼ϵͳ�����
				StaMissionModel myStaMissionModel = new StaMissionModel();
				StaMissionBean myStaMissionBean = new StaMissionBean();
				StaMission myStaMission = new StaMission();
				
				myStaMission.setTimeid(DateTimeStr);
				myStaMission.setStatename(myCharSet.db2db(repnm));
				
				repnm = myCharSet.db2file(repnm);
				myStaMission.setBankid(bankid);
				myStaMission.setOwnerid(operid);
				myStaMission.setStatesort("01");
				myStaMission.setCreatetype("A");
				myStaMission.setCurrentflag("1");
				myStaMission.setRemark1("");
				myStaMission.setRemark2("");
				myStaMission.setRemark3("");
				
				myStaMissionBean.addStaMission(myStaMission);
				
				
				// �����Զ�����
				AutostaModel myAsModel = new AutostaModel();
				Autosta myAs = (Autosta) myAsModel.getAutosta(missionId);
				
				
				// �����´ο�ʼͳ������
				String afterday = myAs.getAfterday().trim();
				String nextstatday = "";
				String nowdate = myPubUtil.getNowDate(1);
				String tmpdate = "";
				tmpdate = nowdate.substring(0, 6);
				tmpdate = Integer.toString(Integer.parseInt(tmpdate) + 1);
				if (myPubUtil.isYYYYMM(tmpdate)) {
					if (afterday.length() == 1) {
						afterday = "0" + afterday;
					}
					nextstatday = tmpdate + afterday;
				} else {
					tmpdate = Integer.toString(Integer.parseInt(tmpdate.substring(0, 4)) + 1);
					nextstatday = tmpdate + "01";
					if (afterday.length() == 1) {
						afterday = "0" + afterday;
					}
					nextstatday += afterday;
				}
				
				myAs.setNextstatday(nextstatday);
				myAs.setStatename(myPubUtil.dealNull(myAs.getStatename()).trim());
				myAs.setInfo(myPubUtil.dealNull(myAs.getInfo()).trim());
				
				myAsModel.updateAutosta(myAs);
				
				
				// ȡ���豸��Ӧ�İ�װ��ַ
				DevinfoModel myDevinfoModel = new DevinfoModel();
				List myDevList = myDevinfoModel.getDevList();
				if (myDevList != null && !myDevList.isEmpty()) {
					int devnum = myDevList.size();
					for (int i = 0; i < devnoList.size(); i++) {
						devno = (String) devnoList.get(i);
						for (int j = 0; j < devnum; j++) {
							Devinfo tmp = (Devinfo) myDevList.get(j);
							if (devno.equals(myPubUtil.dealNull(tmp.getDevno()).trim())) {
								devaddr[i] = myCharSet.db2web(myPubUtil.dealNull(tmp.getDevaddr()).trim());
							}
						}
					}
				}
				
				
				// ִ�в�ѯ
				TrcdtjmonthBean myTjMonthBean = new TrcdtjmonthBean();
				Trcdtjmonth myTjMonth = new Trcdtjmonth();
				
				
				// wukp 20120301
				// ��������豸��Ŵ���
				// �����ν��в�ѯ
				// �������ӵ��༯reault��
				
				int p1 = HQLstr.indexOf("(");
				int p2 = HQLstr.indexOf(")");
				String firstpart = HQLstr.substring(0, p1);
				String middlepart = HQLstr.substring(p1 + 1, p2) + "or";
				String endpart = HQLstr.substring(p2 + 1, HQLstr.length());
				
				int pernum = devnoList.size() / 150 + 1;
				
				for (int i = 0; i < pernum; i++) {
					middlepart = "devno in (  ";
					for (int j = 0; j < 150 && (i * 150 + j) < devnoList.size(); j++) {
						middlepart += "'" + (String) devnoList.get(i * 150 + j) + "',";
					}
					middlepart = middlepart.substring(0, middlepart.length() - 1) + ")";
					HQLstr = firstpart + middlepart + endpart;
					result.addAll((List) myTjMonthBean.getQueryList(HQLstr));
				}
				// result = (List)myTjMonthBean.getQueryList( HQLstr );
				
				
				// ȡ���豸��źͱ���
				for (int i = 0; i < result.size(); i++) {
					myTjMonth = (Trcdtjmonth) result.get(i);
					
					
					// ȡ������
					bizh = myTjMonth.getMoneytype().trim();
					
					
					// wukp120229
					if (bizh.equals("") || bizh == null) {
						bizh = "001";
					}
					
					if (!bizh.equals("")) {
						iflag = 0;
						for (int j = 0; j < bizhArray.size(); j++) {
							if (bizh.equals(bizhArray.get(j))) {
								iflag = 1;
								break;
							}
						}
						if (iflag == 0) {
							bizhArray.add(bizh);
						}
					}
				}
				
				
				// ����ÿ̨�豸�Ľ�����
				for (int i = 0; i < devnoList.size(); i++) {
					devno = (String) devnoList.get(i);
					strnum = 0;
					ttrnum = 0;
					for (int j = 0; j < result.size(); j++) {
						myTjMonth = (Trcdtjmonth) result.get(j);
						if (devno.equals(myTjMonth.getDevno().trim())) {
							strnum += Long.parseLong(myTjMonth.getStrnum().trim());
							ttrnum += Long.parseLong(myTjMonth.getTtrnum().trim());
						}
					}
					strnumArray.add(Long.toString(strnum));
					ttrnumArray.add(Long.toString(ttrnum));
				}
				
				
				// ����ÿ̨�豸�����ֵ��ܽ��
				for (int i = 0; i < devnoList.size(); i++) {
					devno = (String) devnoList.get(i);
					strsumArray = new ArrayList();
					ttrsumArray = new ArrayList();
					for (int j = 0; j < bizhArray.size(); j++) {
						bizh = (String) bizhArray.get(j);
						strsum = 0;
						ttrsum = 0;
						for (int k = 0; k < result.size(); k++) {
							myTjMonth = (Trcdtjmonth) result.get(k);
							tmpbizh = myTjMonth.getMoneytype().trim();
							if (devno.equals(myTjMonth.getDevno().trim())) {
								if (bizh.equals(tmpbizh) || (bizh.equals("001") && (tmpbizh == null || tmpbizh.equals("")))) {
									
									tmppr1 = myTjMonth.getStrsum().trim();
									
									if (tmppr1 != null) {
										tmppos = tmppr1.indexOf(".");
										if (tmppos != -1) {
											tmppr2 = tmppr1.substring(0, tmppos) + tmppr1.substring(tmppos + 1, tmppos + 3);
										} else {
											tmppr2 = tmppr1 + "00";
										}
										strsum += Long.parseLong(tmppr2);
									}
									
									tmppr1 = myTjMonth.getTtrsum().trim();
									if (tmppr1 != null) {
										tmppos = tmppr1.indexOf(".");
										if (tmppos != -1) {
											tmppr2 = tmppr1.substring(0, tmppos) + tmppr1.substring(tmppos + 1, tmppos + 3);
										} else {
											tmppr2 = tmppr1 + "00";
										}
										ttrsum += Long.parseLong(tmppr2);
									}
								}
							}
						}
						tmppr1 = String.valueOf(strsum);
						tmplen = tmppr1.length();
						if (tmplen == 1) {
							tmppr2 = "0.0" + tmppr1;
						} else if (tmplen == 2) {
							tmppr2 = "0." + tmppr1;
						} else {
							tmppr2 = tmppr1.substring(0, tmplen - 2) + "." + tmppr1.substring(tmplen - 2);
						}
						strsumArray.add(tmppr2);
						
						tmppr1 = String.valueOf(ttrsum);
						tmplen = tmppr1.length();
						if (tmplen == 1) {
							tmppr2 = "0.0" + tmppr1;
						} else if (tmplen == 2) {
							tmppr2 = "0." + tmppr1;
						} else {
							tmppr2 = tmppr1.substring(0, tmplen - 2) + "." + tmppr1.substring(tmplen - 2);
						}
						ttrsumArray.add(tmppr2);
					}
					DevStrsumVector.add(strsumArray);
					DevTtrsumVector.add(ttrsumArray);
				}
				
				
				// ��������ֵ��ܽ��,size�����bizhArrayһ��
				for (int i = 0; i < bizhArray.size(); i++) {
					bizh = (String) bizhArray.get(i);
					strsum = 0;
					ttrsum = 0;
					for (int j = 0; j < result.size(); j++) {
						myTjMonth = (Trcdtjmonth) result.get(j);
						tmpbizh = myTjMonth.getMoneytype().trim();
						if (bizh.equals(tmpbizh) || (bizh.equals("001") && (tmpbizh == null || tmpbizh.equals("")))) {
							tmppr1 = myTjMonth.getStrsum().trim();
							
							if (tmppr1 != null) {
								tmppos = tmppr1.indexOf(".");
								if (tmppos != -1) {
									tmppr2 = tmppr1.substring(0, tmppos) + tmppr1.substring(tmppos + 1, tmppos + 3);
								} else {
									tmppr2 = tmppr1 + "00";
								}
								strsum += Long.parseLong(tmppr2);
							}
							
							tmppr1 = myTjMonth.getTtrsum().trim();
							if (tmppr1 != null) {
								tmppos = tmppr1.indexOf(".");
								if (tmppos != -1) {
									tmppr2 = tmppr1.substring(0, tmppos) + tmppr1.substring(tmppos + 1, tmppos + 3);
								} else {
									tmppr2 = tmppr1 + "00";
								}
								ttrsum += Long.parseLong(tmppr2);
							}
						}
					}
					tmppr1 = String.valueOf(strsum);
					tmplen = tmppr1.length();
					if (tmplen == 1) {
						tmppr2 = "0.0" + tmppr1;
					} else if (tmplen == 2) {
						tmppr2 = "0." + tmppr1;
					} else {
						tmppr2 = tmppr1.substring(0, tmplen - 2) + "." + tmppr1.substring(tmplen - 2);
					}
					AllstrsumArray.add(tmppr2);
					
					tmppr1 = String.valueOf(ttrsum);
					tmplen = tmppr1.length();
					if (tmplen == 1) {
						tmppr2 = "0.0" + tmppr1;
					} else if (tmplen == 2) {
						tmppr2 = "0." + tmppr1;
					} else {
						tmppr2 = tmppr1.substring(0, tmplen - 2) + "." + tmppr1.substring(tmplen - 2);
					}
					AllttrsumArray.add(tmppr2);
				}
				
				
				// ����ͳ�ƺ������
				List tempStrsumList = new ArrayList();
				List tempTtrsumList = new ArrayList();
				for (int i = 0; i < devnoList.size(); i++) {
					ResultString = "";
					ResultString += devnoList.get(i) + ",";
					ResultString += ttrnumArray.get(i) + ",";
					ResultString += strnumArray.get(i) + ",";
					
					tempStrsumList = (List) DevStrsumVector.get(i);
					tempTtrsumList = (List) DevTtrsumVector.get(i);
					for (int j = 0; j < tempStrsumList.size(); j++) {
						ResultString += tempStrsumList.get(j) + "|" + tempTtrsumList.get(j) + ",";
					}
					
					ResultString = ResultString.substring(0, ResultString.length() - 1);
					
					ResultVector.add(ResultString);
				}
				
				
				// ���������
				StringBuffer outstr = new StringBuffer("");
				outstr.append("<html>\n");
				outstr.append("<head>\n");
				outstr.append("\t<title>����ͳ��</title>\n");
				outstr.append("\t<style>\n");
				outstr.append("\t\tbody {color: #000000;font-family:'Tahoma','Helvetica','Arial','sans-serif';font-size: 9pt;}\n");
				outstr.append("\t\ttd {font-size: 9pt;text-decoration: none;line-height: 17pt;}\n");
				outstr.append("\t\t.location {font-size: 12px;font-weight: bold;text-decoration: none;}\n");
				outstr.append("\t\t.list_table_border {border: 1px solid #333333;}\n");
				outstr.append("\t\t.list_td_title {border: 1px solid #333333;color: #000000E;font-size: 12px;}\n");
				outstr.append("\t\t.list_tr0 {border: 1px solid #333333;background: #E7EBF7;font-size: 12px;}\n");
				outstr.append("\t\t.list_tr1 {border: 1px solid #333333;background: #E7EEF7;font-size: 12px;}\n");
				outstr.append("\t\t.list_td_prom{border: 1px solid #333333;background: #E1EDFF;font-size: 12px;}\n");
				outstr.append("\t</style>\n");
				outstr.append("\t<style media='print'>\n");
				outstr.append("\t\t.noprint{display:none;}\n");
				outstr.append("\t</style>\n");
				outstr.append("\t<script language='javascript' src='../js/excel.js'></script>\n");
				outstr.append("</head>\n");
				outstr.append("<body>\n");
				
				
				// ����
				outstr.append("<table width='100%' cellspacing='0' cellpadding='0'>\n");
				outstr.append("\t<tr>\n");
				outstr.append("\t\t<td align='left' valign='center' width='30' height='40'>\n");
				outstr.append("\t\t\t&nbsp;\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td>\n");
				outstr.append("\t\t\t<font color=blue>PowerView XP </font>--- <font class='location'>����ͳ��</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td>\n");
				outstr.append("\t\t\t<font class='location'>" + repnm + "</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td align='right'>\n");
				outstr.append("\t\t\t<font class='location'>(�±�)\n");
				if (!qbegin.equals(qend)) {
					outstr.append("\t\t\t\t" + qbegin.substring(0, 4) + "��" + Integer.parseInt(qbegin.substring(4, 6)) + "��");
					outstr.append("---" + qend.substring(0, 4) + "��" + Integer.parseInt(qend.substring(4, 6)) + "��\n");
				} else {
					outstr.append("\t\t\t\t" + qend.substring(0, 4) + "��" + Integer.parseInt(qend.substring(4, 6)) + "��\n");
				}
				outstr.append("\t\t\t</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t</tr>\n");
				outstr.append("</table>\n");
				
				
				// ������ʾ����
				if (devnoList.size() == 0) {
					outstr.append("There is no transaction on device you selected, so that is no Statistics.\n");
				} else {
					outstr.append("<table id='excel' width='100%' cellspacing='0' cellpadding='0' class='list_table_border'>\n");
					// ��ͷ
					outstr.append("\t<tr align='center'>\n");
					if (uflag.equals("1")) {
						outstr.append("\t\t<td width='25%' class='list_td_prom'><b>�豸���</b></td>\n");
						outstr.append("\t\t<td width='25%' class='list_td_prom'><b>��װ��ַ</b></td>\n");
						outstr.append("\t\t<td width='25%' class='list_td_prom'><b>�ܱ���</b></td>\n");
						outstr.append("\t\t<td width='25%' class='list_td_prom'><b>�ɹ�����</b></td>\n");
					} else if (uflag.equals("2")) {
						outstr.append("\t\t<td width='10%' rowspan='2' class='list_td_prom'><b>�豸���</b></td>\n");
						outstr.append("\t\t<td width='10%' rowspan='2' class='list_td_prom'><b>��װ��ַ</b></td>\n");
					} else if (uflag.equals("3")) {
						outstr.append("\t\t<td width='10%' rowspan='2' class='list_td_prom'><b>�豸���</b></td>\n");
						outstr.append("\t\t<td width='10%' rowspan='2' class='list_td_prom'><b>��װ��ַ</b></td>\n");
						outstr.append("\t\t<td width='10%' rowspan='2' class='list_td_prom'><b>�ܱ���</b></td>\n");
						outstr.append("\t\t<td width='10%' rowspan='2' class='list_td_prom'><b>�ɹ�����</b></td>\n");
					}
					if (uflag.equals("2") || uflag.equals("3")) {
						outstr.append("\t\t<td colspan='" + bizhArray.size() + "' class='list_td_prom'><b>���׽��</b></td>\n");
					}
					outstr.append("\t</tr>\n");
					
					
					// ����
					devno = "";
					ttrnum = 0;
					strnum = 0;
					ttrsum = 0;
					strsum = 0;
					int splitflag = 0;
					int splitflag1 = 0;
					String bal1 = "";
					String bal2 = "";
					long Allttrnum = 0;
					long Allstrnum = 0;
					bizh = "";
					String bizh1 = "";
					String bizh2 = "";
					String bizhtmp = "";
					int bizhnum = 0;
					int forflag = 0;
					String tmpstr = "";
					String tmpstr1 = "";
					
					if (uflag.equals("2") || uflag.equals("3")) {
						outstr.append("\t<tr align='center'>\n");
						bizhnum = Integer.parseInt(myPubUtil.ReadConfig("MoneyType", "MoneyType_Num", "0", "Trade.ini"));
						
						if (bizhArray.size() == 0) {
							outstr.append("\t\t<td width='" + (100 - 35) + "%' class='list_td_prom'>&nbsp;<nobr><b>�����</b></nobr></td>\n");
						} else {
							for (int i = 0; i < bizhArray.size(); i++) {
								bizh = (String) bizhArray.get(i);
								splitflag = bizh.lastIndexOf("|");
								if (splitflag == -1) {
									bizh1 = bizh;
									BZ1: for (int j = 0; j < bizhnum; j++) {
										bizhtmp = myPubUtil.ReadConfig("MoneyType", "Type_" + j, bizh1, "Trade.ini");
										if ((!bizh1.equals(bizhtmp)) && (bizh1.equals(bizhtmp.substring(0, 3)))) {
											bizh = bizhtmp.substring(4);
											break BZ1;
										}
									}
								} else {
									bizh1 = bizh.substring(0, splitflag);
									bizh2 = bizh.substring(splitflag + 1);
									forflag = 0;
									BZ2: for (int j = 0; j < bizhnum; j++) {
										bizhtmp = myPubUtil.ReadConfig("MoneyType", "Type_" + j, bizh1, "Trade.ini");
										if ((!bizh1.equals(bizhtmp)) && (bizh1.equals(bizhtmp.substring(0, 3)))) {
											bizh1 = bizhtmp.substring(4);
											if (forflag == 1) {
												break BZ2;
											}
											forflag = 1;
										}
										if ((!bizh2.equals(bizhtmp)) && (bizh2.equals(bizhtmp.substring(0, 3)))) {
											bizh2 = bizhtmp.substring(4);
											if (forflag == 1) {
												break BZ2;
											}
											forflag = 1;
										}
									}
									bizh = "���� " + bizh1 + "|���� " + bizh2;
								}
								outstr.append("\t\t<td width='" + (100 - 35) / bizhArray.size() + "%' class='list_td_prom'>&nbsp;<nobr><b>" + bizh + "</b></nobr></td>\n");
							}
						}
						outstr.append("\t</tr>\n");
					}
					
					
					// ͳ��
					for (int i = 0; i < ResultVector.size(); i++) {
						StringTokenizer wb = new StringTokenizer((String) ResultVector.get(i), ",");
						devno = wb.nextToken();
						ttrnum = Long.parseLong(wb.nextToken());
						strnum = Long.parseLong(wb.nextToken());
						Allttrnum += ttrnum;
						Allstrnum += strnum;
						outstr.append("\t<tr class='list_tr" + i % 2 + "'>\n");
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + devno + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_title'>&nbsp;" + devaddr[i] + "</td>\n");
						if (uflag.equals("1") || uflag.equals("3")) {
							outstr.append("\t\t<td class='list_td_title'>&nbsp;" + ttrnum + "</td>\n");
							outstr.append("\t\t<td class='list_td_title'>&nbsp;" + strnum + "</td>\n");
						}
						if (uflag.equals("2") || uflag.equals("3")) {
							if (bizhArray.size() == 0) {
								outstr.append("\t\t<td class='list_td_title' align='center'>&nbsp;0.00</td>\n");
							} else {
								for (int j = 0; j < bizhArray.size(); j++) {
									tmpstr = wb.nextToken();
									tmpstr1 = (String) bizhArray.get(j);
									splitflag = tmpstr.lastIndexOf("|");
									splitflag1 = tmpstr1.lastIndexOf("|");
									if ((splitflag == -1) && (splitflag1 == -1)) {
										tmpstr = "0.00";
									} else {
										bal1 = tmpstr.substring(0, splitflag);
										bal2 = tmpstr.substring(splitflag + 1);
										if ((Double.parseDouble(bal1) <= 0) && (Double.parseDouble(bal2) <= 0) && (splitflag1 != -1)) {
											tmpstr = "0.00|0.00";
										} else if (Double.parseDouble(bal1) <= 0) {
											tmpstr = bal2;
										} else if (Double.parseDouble(bal2) <= 0) {
											tmpstr = bal1;
										}
									}
									outstr.append("\t\t<td class='list_td_title' align='center'>&nbsp;" + tmpstr + "</td>\n");
								}
							}
						}
						outstr.append("\t</tr>\n");
					}
					
					
					// ���
					outstr.append("\t<tr>\n");
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>�ϼ�</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;&nbsp;</td>\n");
					if (uflag.equals("1") || uflag.equals("3")) {
						outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + Allttrnum + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + Allstrnum + "</b></td>\n");
					}
					if (uflag.equals("2") || uflag.equals("3")) {
						if (bizhArray.size() == 0) {
							outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;0.00</td>\n");
						} else {
							for (int i = 0; i < bizhArray.size(); i++) {
								tmpstr1 = (String) bizhArray.get(i);
								splitflag1 = tmpstr1.lastIndexOf("|");
								bal1 = (String) AllstrsumArray.get(i);
								bal2 = (String) AllttrsumArray.get(i);
								if ((Double.parseDouble(bal1) <= 0) && (Double.parseDouble(bal2) <= 0) && (splitflag1 == -1)) {
									tmpstr = "0.00";
								} else if ((Double.parseDouble(bal1) <= 0) && (Double.parseDouble(bal2) <= 0) && (splitflag1 != -1)) {
									tmpstr = "0.00|0.00";
								} else if (Double.parseDouble(bal1) <= 0) {
									tmpstr = bal2;
								} else if (Double.parseDouble(bal2) <= 0) {
									tmpstr = bal1;
								} else {
									tmpstr = bal1 + "|" + bal2;
								}
								outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + tmpstr + "</b></td>\n");
							}
						}
					}
					outstr.append("\t</tr>\n");
					
					outstr.append("</table>\n");
				}
				
				
				// �����б�
				statmode = statmode + "_";
				String[][] trcdtypes = myPubUtil.Ini2Array("Trade.ini", "TradeType", statmode + "Num", statmode, "|");
				List trcdcode = new ArrayList();
				List trcdname = new ArrayList();
				String trcdstr = "";
				String trcdtmp = "";
				for (int i = 0; i < trcdtypes.length; i++) {
					trcdcode = new ArrayList();
					trcdcode.add(trcdtypes[i][0]);
					trcdstr = trcdtypes[i][1];
					for (int j = 0; j < TcList.size(); j++) {
						trcdtmp = (String) TcList.get(j);
						if (trcdstr.indexOf(trcdtmp) != -1) {
							trcdcode.add(trcdtmp);
						}
					}
					if (trcdcode.size() > 1) {
						trcdname.add(trcdcode);
					}
				}
				List tmplist = new ArrayList();
				String trcd = "";
				String trtmp = "";
				String[][] trcdnames = myPubUtil.Ini2Array("Trade.ini", "TradeCode", "TCode_Num", "TCode_", ",");
				for (int i = 0; i < trcdname.size(); i++) {
					trtmp = "";
					tmplist = (List) trcdname.get(i);
					outstr.append("<br><b>" + tmplist.get(0) + "��</b>");
					for (int j = 1; j < tmplist.size(); j++) {
						trcd = (String) tmplist.get(j);
						for (int k = 0; k < trcdnames.length; k++) {
							if (trcd.equals(trcdnames[k][0])) {
								trtmp += trcdnames[k][1] + ",";
							}
						}
					}
					if (trtmp.length() > 1) {
						trtmp = trtmp.substring(0, trtmp.length() - 1);
						trtmp = trtmp.replaceAll(",", "��");
					}
					outstr.append(trtmp);
					outstr.append("��<br>\n");
				}
				
				
				// ��ӡ��ť
				outstr.append("<p align='center'>\n");
				outstr.append("\t<OBJECT id='WebBrowser' classid='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2' height='0' width='0' VIEWASTEXT></OBJECT>\n");
				outstr.append("\t<input type='button' value=' ��  ӡ ' onclick='document.all.WebBrowser.ExecWB(6,1)' class='noprint'>\n");
				outstr.append("\t<input type='button' value='ֱ�Ӵ�ӡ' onclick='document.all.WebBrowser.ExecWB(6,6)' class='noprint'>\n");
				outstr.append("\t<input type='button' value='ҳ������' onclick='document.all.WebBrowser.ExecWB(8,1)' class='noprint'>\n");
				outstr.append("\t<input type='button' value='��ӡԤ��' onclick='document.all.WebBrowser.ExecWB(7,1)' class='noprint'>\n");
				outstr.append("\t<input type='button' value='����Excel' onclick='javascript:PrintTableToExcelEx1(excel)' class='noprint'>\n");
				outstr.append("</p>\n");
				
				outstr.append("</body>\n");
				outstr.append("</html>\n");
				
				String OutStr = outstr.toString();
				
				
				// ��¼�ļ�
				String filename = "A_01_" + operid + "_" + DateTimeStr + ".htm";
				FileOutputStream outStream = new FileOutputStream(filepath + filename);
				OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "GB2312");
				outStreamWriter.write(OutStr);
				outStreamWriter.flush();
				outStreamWriter.close();
				// */
				
				// ����ϵͳ�����
				myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "01", "A", operid);
				myStaMission.setCurrentflag("0");
				myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
				
				if (!myStaMissionBean.updateStaMission(myStaMission)) {
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "01", "A", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				}
				
				
				// �����Զ�����
				myAs = (Autosta) myAsModel.getAutosta(missionId);
				String tmpstr = myAs.getCount().trim();
				tmpstr = Integer.toString(Integer.parseInt(tmpstr) + 1);
				
				myAs.setLaststat((myPubUtil.getNowDate(1)).substring(0, 6));
				myAs.setCount(tmpstr);
				myAs.setStatename(myPubUtil.dealNull(myAs.getStatename()).trim());
				myAs.setInfo(myPubUtil.dealNull(myAs.getInfo()).trim());
				
				myAsModel.updateAutosta(myAs);
				
				log.info("�� ����ͳ �� ����");
				
			} catch (HibernateException he) {
				log.error("HibernateException1", he);
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "01", "A", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				} catch (Exception e) {
					log.error("Exception12", e);
				}
				
			} catch (Exception ex) {
				log.error("Exception2", ex);
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "01", "A", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				} catch (Exception e) {
					log.error("Exception21", ex);
				}
				
			}
			
		} catch (Exception e) {
			log.error("Exception0", e);
		}
	}


	/**
	 * @return the missionId
	 */
	public String getMissionId() {
		return missionId;
	}


	/**
	 * @param missionId the missionId to set
	 */
	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}
	
}