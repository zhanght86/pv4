package com.lcjr.pvxp.util;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.bean.*;
import com.lcjr.pvxp.model.*;

import net.sf.hibernate.*;

public class CardTjServer extends Thread {
	
	Logger log = Logger.getLogger("web.lcjr.pvxp.util.CardTjServer.java");
	
	
	
	/**
	 * SQL��䣬����ͳ��
	 */
	private String HQLstr = null;
	
	
	
	/**
	 * Cookie�д洢�Ĳ���Ա�����������
	 */
	private String bankid = null;
	
	
	
	/**
	 * Cookie�д洢�Ĳ���Ա���2
	 */
	private String operid = null;
	
	
	
	/**
	 * ��������
	 */
	private String repnm = null;
	
	
	
	/**
	 * ����ͳ�Ʊ�����ļ��洢λ��
	 */
	private String filepath = null;
	
	
	
	/**
	 * �豸����༯
	 */
	private List devnoList = new ArrayList();
	
	
	
	/**
	 * ��������
	 */
	private String outcarddate1 = null;
	
	
	
	/**
	 * ��ֹ����
	 */
	private String outcarddate2 = null;
	
	
	
	/**
	 * ��ʼʱ��
	 */
	private String outcardtime1 = null;
	
	
	
	/**
	 * ��ֹʱ��
	 */
	private String outcardtime2 = null;
	
	
	
	/**
	 * ͳ�Ƴ���״̬
	 */
	private String[] outcardstatus;
	
	
	
	/**
	 * ������
	 */
	private String[] cardtype;
	
	
	
	
	/**
	 * ��� ͳ�Ƴ���״̬
	 * 
	 * @return the outcardstatus
	 */
	public String[] getOutcardstatus() {
		return outcardstatus;
	}
	
	
	
	/**
	 * ���� ͳ�Ƴ���״̬
	 * 
	 * @param outcardstatus
	 *            the outcardstatus to set
	 */
	public void setOutcardstatus(String[] outcardstatus) {
		this.outcardstatus = outcardstatus;
	}
	
	
	
	/**
	 * Ĭ�Ϲ��캯��
	 */
	public CardTjServer() {
		
	}
	
	
	
	/**
	 * ���Cookie�д洢�Ĳ���Ա�����������
	 * 
	 * @return the bankid
	 */
	public String getBankid() {
		return bankid;
	}
	
	
	
	/**
	 * ���� Cookie�д洢�Ĳ���Ա�����������
	 * 
	 * @param bankid
	 *            the bankid to set
	 */
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	
	
	
	/**
	 * ����豸����༯
	 * 
	 * @return the devnoList
	 */
	public List getDevnoList() {
		return devnoList;
	}
	
	
	
	/**
	 * ���cardtype
	 * 
	 * @return the cardtype
	 */
	public String[] getCardtype() {
		return cardtype;
	}
	
	
	
	/**
	 * ���� cardtype
	 * 
	 * @param cardtype
	 *            the cardtype to set
	 */
	public void setCardtype(String[] cardtype) {
		this.cardtype = cardtype;
	}
	
	
	
	/**
	 * ���� �豸����༯
	 * 
	 * @param devnoList
	 *            the devnoList to set
	 */
	public void setDevnoList(List devnoList) {
		this.devnoList = devnoList;
	}
	
	
	
	/**
	 * �������ͳ�Ʊ�����ļ��洢λ��
	 * 
	 * @return the filepath
	 */
	public String getFilepath() {
		return filepath;
	}
	
	
	
	/**
	 * ���� ����ͳ�Ʊ�����ļ��洢λ��
	 * 
	 * @param filepath
	 *            the filepath to set
	 */
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	
	
	/**
	 * ���SQL��䣬����ͳ��
	 * 
	 * @return the hQLstr
	 */
	public String getHQLstr() {
		return HQLstr;
	}
	
	
	
	/**
	 * ���� SQL��䣬����ͳ��
	 * 
	 * @param lstr
	 *            the hQLstr to set
	 */
	public void setHQLstr(String lstr) {
		HQLstr = lstr;
	}
	
	
	
	/**
	 * ���operid
	 * 
	 * @return the operid
	 */
	public String getOperid() {
		return operid;
	}
	
	
	
	/**
	 * ���� operid
	 * 
	 * @param operid
	 *            the operid to set
	 */
	public void setOperid(String operid) {
		this.operid = operid;
	}
	
	
	
	/**
	 * ��ó�������
	 * 
	 * @return the outcarddate1
	 */
	public String getOutcarddate1() {
		return outcarddate1;
	}
	
	
	
	/**
	 * ���� ��������
	 * 
	 * @param outcarddate1
	 *            the outcarddate1 to set
	 */
	public void setOutcarddate1(String outcarddate1) {
		this.outcarddate1 = outcarddate1;
	}
	
	
	
	/**
	 * ��ý�������
	 * 
	 * @return the outcarddate2
	 */
	public String getOutcarddate2() {
		return outcarddate2;
	}
	
	
	
	/**
	 * ���� ��������
	 * 
	 * @param outcarddate2
	 *            the outcarddate2 to set
	 */
	public void setOutcarddate2(String outcarddate2) {
		this.outcarddate2 = outcarddate2;
	}
	
	
	
	/**
	 * �����ʼʱ��
	 * 
	 * @return the outcardtime1
	 */
	public String getOutcardtime1() {
		return outcardtime1;
	}
	
	
	
	/**
	 * ���� ��ʼʱ��
	 * 
	 * @param outcardtime1
	 *            the outcardtime1 to set
	 */
	public void setOutcardtime1(String outcardtime1) {
		this.outcardtime1 = outcardtime1;
	}
	
	
	
	/**
	 * ��ý�ֹʱ��
	 * 
	 * @return the outcardtime2
	 */
	public String getOutcardtime2() {
		return outcardtime2;
	}
	
	
	
	/**
	 * ���� ��ֹʱ��
	 * 
	 * @param outcardtime2
	 *            the outcardtime2 to set
	 */
	public void setOutcardtime2(String outcardtime2) {
		this.outcardtime2 = outcardtime2;
	}
	
	
	
	/**
	 * ��� ��������
	 * 
	 * @return the repnm
	 */
	public String getRepnm() {
		return repnm;
	}
	
	
	
	/**
	 * ���� ��������
	 * 
	 * @param repnm
	 *            the repnm to set
	 */
	public void setRepnm(String repnm) {
		this.repnm = repnm;
	}
	
	
	
	/**
	 * �������߳�
	 */
	public void run() {
		
		log.info("��ʼ����ͳ��");
		
		try {
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();
			
			List result = new ArrayList();
			
			
			// ���ڼ��㷢���ɹ���ʧ��
			int resultarray[][] = new int[devnoList.size()][2];
			// ���ڴ洢����
			String name[] = new String[devnoList.size()];
			
			float ttrnum = 0;
			float strnum = 0;
			
			
			// ��õ�ǰʱ��
			String TimeStr = myPubUtil.getNowTime();
			TimeStr = myPubUtil.replace(TimeStr, ":", "");
			String DateTimeStr = myPubUtil.getNowDate(1) + TimeStr;
			
			try {
				// ��ͳ�Ʊ���
				log.info("ͳ������д���");
				// ��¼ϵͳ����� ��ʼ��StaMission��ص���
				StaMissionModel myStaMissionModel = new StaMissionModel();
				StaMissionBean myStaMissionBean = new StaMissionBean();
				StaMission myStaMission = new StaMission();
				
				
				// ����ͳ������ʼ��ʱ��
				myStaMission.setTimeid(DateTimeStr);
				// ����ͳ�����񱨱������
				myStaMission.setStatename(myCharSet.form2db(repnm));
				repnm = myCharSet.form2file(repnm);
				// ����ͳ����������
				myStaMission.setBankid(bankid);
				// ����ͳ���������Ա���
				myStaMission.setOwnerid(operid);
				// �������01������ͳ�� 02���豸�����������ͳ�� 03���豸����ͳ��
				myStaMission.setStatesort("21");
				// ����������ͣ�M���ֶ� A���Զ�
				myStaMission.setCreatetype("M");
				// ����ǰ״̬ 0������ 1������ִ�� 2��ʧ��
				myStaMission.setCurrentflag("1");
				// ��ע123
				myStaMission.setRemark1("");
				myStaMission.setRemark2("");
				myStaMission.setRemark3("");
				// ��ͳ������д�뵽���ݿ�
				myStaMissionBean.addStaMission(myStaMission);
				
				
				// ִ�в�ѯ
				CardTjBean cardtjbean = new CardTjBean();
				// ʵ����������ˮ
				CardOut acardout = new CardOut();
				
				
				// ����������豸��ŵ�ʱ�򣬽������ν��в�ѯ
				int p1 = HQLstr.indexOf("(");
				int p2 = HQLstr.indexOf(")");
				String firstpart = HQLstr.substring(0, p1);
				String middlepart = HQLstr.substring(p1 + 1, p2) + "or";
				String endpart = HQLstr.substring(p2 + 1, HQLstr.length());
				
				int pernum = devnoList.size() / 150 + 1;
				// System.out.println("devList�ĳ����ǣ�"+devnoList.size());
				
				for (int i = 0; i < pernum; i++) {
					middlepart = "devno in (  ";
					for (int j = 0; j < 150 && (i * 150 + j) < devnoList.size(); j++) {
						middlepart += "'" + (String) devnoList.get(i * 150 + j) + "',";
					}
					middlepart = middlepart.substring(0, middlepart.length() - 1) + ")";
					HQLstr = firstpart + middlepart + endpart;
					result.addAll((List) cardtjbean.getQueryList(HQLstr));
					
				}
				
				log.info("���ͳ����Ϣ");
				
				
				// ȡ����ѯ��Ľ��������ͳ�Ƽ���,�ڶ�ά�����д洢
				for (int j = 0; j < devnoList.size(); j++) {
					String adevno = devnoList.get(j).toString().trim();
					name[j] = devnoList.get(j).toString().trim();
					for (int i = 0; i < result.size(); i++) {
						acardout = (CardOut) result.get(i);
						if (acardout.getDevno().trim().equals(adevno)) {
							if (acardout.getOutcardstatus().equals("1")) {
								resultarray[j][0]++;
							} else if (acardout.getOutcardstatus().equals("0")||acardout.getOutcardstatus().equals("2")) {
								resultarray[j][1]++;
							}
						}
					}
				}
				
				log.info("��ʼ����ͳ�ƽ��");
				// ���������  402030001
				StringBuffer outstr = new StringBuffer("");
				outstr.append("<html>\n");
				outstr.append("<head>\n");
				outstr.append("\t<title>����״̬��¼ͳ�Ʊ���</title>\n");
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
				outstr.append("\t\t\t<font color=blue>PowerView XP </font>--- <font class='location'>����״̬��¼ͳ�Ʊ���</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td>\n");
				outstr.append("\t\t\t<font class='location'>" + repnm + "</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td align='right'>\n");
				outstr.append("\t\t\t<font class='location'>\n");
				if (!outcarddate1.equals(outcarddate2)) {
					outstr.append("\t\t\t\t" + outcarddate1.substring(0, 4) + "��" + outcarddate1.substring(4, 6) + "��" + outcarddate1.substring(6, 8) + "��");
					outstr.append("---" + outcarddate2.substring(0, 4) + "��" + outcarddate2.substring(4, 6) + "��" + outcarddate2.substring(6, 8) + "��&nbsp;\n");
					outstr.append("\t\t\t</font>\n");
					outstr.append("\t\t</td>\n");
					outstr.append("\t</tr>\n");
					
					outstr.append("\t<tr>\n");
					outstr.append("\t\t<td align='left' valign='center' width='30' height='40'>\n");
					outstr.append("\t\t\t&nbsp;\n");
					outstr.append("\t\t</td>\n");
					outstr.append("\t\t<td>\n");
					outstr.append("\t\t\t<font class='location'  color='blue'>ͳ��������</font>\n");
					outstr.append("\t\t</td>\n");
					outstr.append("\t\t<td><font class='location' color='red' >������:</font> \n");
					for (int i = 0; i < cardtype.length; i++) {
						if (cardtype[i].trim().equals("1")) {
							outstr.append("\t\t\t<font class='location'>��ǿ�</font>\n");
						} else if (cardtype[i].trim().equals("2")) {
							outstr.append("\t\t\t<font class='location'>���ÿ�</font>\n");
						} else {
							outstr.append("\t\t\t<font class='location'>��������</font>\n");
						}
					}
					
					outstr.append("\t\t\t&nbsp;&nbsp;<font class='location'  color='red'>ͳ�Ƴ���״̬:</font>\n");

					for (int i = 0; i < outcardstatus.length; i++) {
						if (outcardstatus[i].trim().equals("1")) {
							outstr.append("\t\t\t<font class='location'>�����ɹ�</font>\n");
						} else if (outcardstatus[i].trim().equals("0")) {
							outstr.append("\t\t\t<font class='location'>����ʧ��</font>\n");
						}
					}
					
					outstr.append("\t\t</td>\n");
					outstr.append("\t\t<td align='right'>\n");
					outstr.append("\t\t\t<font class='location'>\n");
					
					
					if (!outcardtime1.equals(outcardtime2)) {
						outstr.append("\t\t\t\t" + outcardtime1.substring(0, 2) + ":" + outcardtime1.substring(2, 4) + ":" + outcardtime1.substring(4, 6) + "");
						outstr.append("---" + outcardtime2.substring(0, 2) + ":" + outcardtime2.substring(2, 4) + ":" + outcardtime2.substring(4, 6) + "&nbsp;\n");
					}

					outstr.append("\t\t\t</font>\n");
					outstr.append("\t\t</td>\n");
					outstr.append("\t</tr>\n");
					
					outstr.append("</table>\n");
					
					if (devnoList.size() == 0) {
						outstr.append("There is no transaction on device you selected, so that is no Statistics.\n");
					} else {
						outstr.append("<table id='excel' width='100%' cellspacing='0' cellpadding='0' class='list_table_border'>\n");

						// ��ͷ
						outstr.append("\t<tr align='center'>\n");
						if (outcardstatus.length == 2) {
							outstr.append("\t\t<td width='20%' class='list_td_prom'><b>�豸���</b></td>\n");
							outstr.append("\t\t<td width='20%' class='list_td_prom'><b>�����ɹ�����</b></td>\n");
							outstr.append("\t\t<td width='20%' class='list_td_prom'><b>����ʧ�ܴ���</b></td>\n");
							outstr.append("\t\t<td width='20%' class='list_td_prom'><b>�����ɹ���</b></td>\n");
							outstr.append("\t\t<td width='20%' class='list_td_prom'><b>����ʧ����</b></td>\n");
						} else if (outcardstatus.length == 1 && outcardstatus[0].equals("1")) {
							outstr.append("\t\t<td width='10%'  class='list_td_prom'><b>�豸���</b></td>\n");
							outstr.append("\t\t<td width='10%'  class='list_td_prom'><b>�����ɹ�����</b></td>\n");
							outstr.append("\t\t<td width='25%'  class='list_td_prom'><b>�����ɹ���</b></td>\n");
						} else if (outcardstatus.length == 1 && outcardstatus[0].equals("0")) {
							outstr.append("\t\t<td width='10%' class='list_td_prom'><b>�豸���</b></td>\n");
							outstr.append("\t\t<td width='25%' class='list_td_prom'><b>����ʧ�ܴ���</b></td>\n");
							outstr.append("\t\t<td width='25%' class='list_td_prom'><b>����ʧ����</b></td>\n");
						}

						outstr.append("\t</tr>\n");

						for (int i = 0; i < resultarray.length; i++) {
							outstr.append("\t<tr align='center' class='list_tr" + i % 2 + "'>\n");
							outstr.append("\t\t<td class='list_td_title'>&nbsp;" + name[i] + "</td>\n");
							if (outcardstatus.length == 2) {
								outstr.append("\t\t<td class='list_td_title'>&nbsp;" + resultarray[i][0] + "</td>\n");
								outstr.append("\t\t<td class='list_td_title'>&nbsp;" + resultarray[i][1] + "</td>\n");
								if (resultarray[i][0] + resultarray[i][1] == 0) {
									ttrnum = 0;
								} else{
									strnum = (float) resultarray[i][0] / (float) (resultarray[i][0] + resultarray[i][1]);
									ttrnum = (float) resultarray[i][1] / (float) (resultarray[i][0] + resultarray[i][1]);
								}
								outstr.append("\t\t<td class='list_td_title'>&nbsp;" + strnum * 100 + "%</td>\n");
								outstr.append("\t\t<td class='list_td_title'>&nbsp;" + ttrnum * 100 + "%</td>\n");
							} else if (outcardstatus.length == 1 && outcardstatus[0].equals("1")) {
								outstr.append("\t\t<td class='list_td_title'>&nbsp;" + resultarray[i][0] + "</td>\n");
								if (resultarray[i][0] + resultarray[i][1] == 0) {
									ttrnum = 0;
								} else
									ttrnum = (float) resultarray[i][0] / (float) (resultarray[i][0] + resultarray[i][1]);
								outstr.append("\t\t<td class='list_td_title'>&nbsp;" + ttrnum * 100 + "%</td>\n");
							} else if (outcardstatus.length == 1 && outcardstatus[0].equals("0")) {
								outstr.append("\t\t<td class='list_td_title'>&nbsp;" + resultarray[i][1] + "</td>\n");
								if (resultarray[i][0] + resultarray[i][1] == 0) {
									ttrnum = 0;
								} else
									ttrnum = (float) resultarray[i][1] / (float) (resultarray[i][0] + resultarray[i][1]);
								outstr.append("\t\t<td class='list_td_title'>&nbsp;" + ttrnum * 100 + "%</td>\n");
							}
							outstr.append("\t</tr>\n");
						}
						ttrnum = 0;
					}
				}
				
				
				// ����ϼ�
				int sum1 = 0;
				int sum0 = 0;
				
				for (int i = 0; i < resultarray.length; i++) {
					sum1 += resultarray[i][0];
					sum0 += resultarray[i][1];
				}
				
				
				// ͳ�ƺϼ�ʧ����
				if (outcardstatus.length == 2) {
					if (sum1 + sum0 == 0) {
						ttrnum = 0;
					} else
						ttrnum = (float) sum0 / (float) (sum1 + sum0);
					    strnum = (float) sum1 / (float) (sum1 + sum0);
					// ���
					outstr.append("\t<tr align='center' >\n");
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>�ϼ�</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + sum1 + "</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + sum0 + "</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + strnum * 100 + "%</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + ttrnum * 100 + "%</b></td>\n");
					outstr.append("\t</tr>\n");
					outstr.append("</table>\n");

				}
				
				// ͳ�ƺϼƳɹ���
				else if (outcardstatus.length == 1 && outcardstatus[0].equals("1")) {
					if (sum1 + sum0 == 0) {
						ttrnum = 0;
					} else
						ttrnum = (float) sum1 / (float) (sum1 + sum0);
					// ���
					outstr.append("\t<tr>\n");
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>�ϼ�</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + sum1 + "</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + ttrnum * 100 + "%</b></td>\n");
					outstr.append("\t</tr>\n");
					outstr.append("</table>\n");
				}
				// ͳ�ƺϼ�ʧ����
				else if (outcardstatus.length == 1 && outcardstatus[0].equals("0")) {
					if (sum1 + sum0 == 0) {
						ttrnum = 0;
					} else
						ttrnum = (float) sum0 / (float) (sum1 + sum0);
					// ���
					outstr.append("\t<tr>\n");
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>�ϼ�</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + sum0 + "</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + ttrnum * 100 + "%</b></td>\n");
					outstr.append("\t</tr>\n");
					outstr.append("</table>\n");
					
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
				
				log.info("�ĵ�����������ϣ���ʼ�����ļ�");
				String OutStr = outstr.toString();
				String filename = "M_21_" + operid + "_" + DateTimeStr + ".htm";
				log.info("filename="+filename);
				FileOutputStream outStream = new FileOutputStream(filepath + filename);
				log.info("�ļ�����="+filepath + filename);
				OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "GB2312");
				log.info("�����ļ���ʼ");
				
				outStreamWriter.write(OutStr);
				outStreamWriter.flush();
				outStreamWriter.close();
				log.info("�����ļ��ɹ�");
				
				// ����ϵͳ�����
				myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "21", "M", operid);
				myStaMission.setCurrentflag("0");
				myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
				
				if (!myStaMissionBean.updateStaMission(myStaMission)) {
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "21", "M", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				}
				
			} catch (HibernateException he) {
				log.error("ͳ��ʧ��", he);
				// ����󣬷���Ϊͳ��ʧ��
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "21", "M", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				} catch (Exception e) {
					log.error("ͳ��ʧ��", e);
				}
				
			} catch (Exception ex) {
				log.error("ͳ��ʧ��", ex);
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "21", "M", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				} catch (Exception e) {
					log.error("ͳ��ʧ��", e);
				}
			}
		} catch (Exception e) {
			log.error("����ʧ��", e);
		}
	}
	
}