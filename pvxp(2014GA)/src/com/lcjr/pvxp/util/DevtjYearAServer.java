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
 * �Զ�ͳ�Ʊ��� �豸����ͳ�� ���걨��
 * 
 * @author ������
 * 
 * ʱ�䣺20120302
 * 
 * 
 */
public class DevtjYearAServer extends Thread {
	
	Logger log = Logger.getLogger("web.lcjr.pvxp.util.DevtjYearAServer.java");
	
	
	private String HQLstr = null;
	
	
	private String bankid = null;
	
	
	private String operid = null;
	
	
	private String repnm = null;
	
	
	private String qbegin = null;
	
	
	private String qend = null;
	
	
	private String filepath = null;
	
	
	private String missionId = null;
	
	
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
	
	
	public String getFilepath() {
		return (this.filepath);
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
	
	
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	
	
	public void setDevnoList(List devnoList) {
		this.devnoList = devnoList;
	}
	
	
	public DevtjYearAServer() {
		
	}
	
	
	public void run() {
		
		// �������߳�
		log.info("�� �豸����ͳ �� ��ʼ");
		
		try {
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();
			
			List result = new ArrayList();
			List statArray = new ArrayList();
			List devnoArray = new ArrayList();
			List ttrnumArray = new ArrayList();
			List strnumArray = new ArrayList();
			List timelenArray = new ArrayList();
			List timesArray = new ArrayList();
			List AlltimelenArray = new ArrayList();
			List AlltimesArray = new ArrayList();
			String statno = "";
			String devno = "";
			long ttrnum = 0;
			long strnum = 0;
			long timelen = 0;
			long times = 0;
			int iflag = 0;
			
			
			// wukp20120302 ͳ���豸ͨѶ����ʱ��,�ų��豸ͨѶʱ��
			List Z001lenVector = new ArrayList();
			long Z001len = 0;
			// wukp20120302
			
			Vector DevtimesVector = new Vector();
			Vector DevtimelenVector = new Vector();
			
			Vector ResultVector = new Vector();
			String ResultString = new String();
			
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
				myStaMission.setStatesort("03");
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
				tmpdate = nowdate.substring(0, 4);
				tmpdate = Integer.toString(Integer.parseInt(tmpdate) + 1);
				nextstatday = tmpdate + "01";
				if (afterday.length() == 1) {
					afterday = "0" + afterday;
				}
				nextstatday += afterday;
				
				myAs.setNextstatday(nextstatday);
				myAs.setStatename(myPubUtil.dealNull(myAs.getStatename()).trim());
				myAs.setInfo(myPubUtil.dealNull(myAs.getInfo()).trim());
				
				myAsModel.updateAutosta(myAs);
				
				
				// ִ�в�ѯ
				DevtjyearBean myDevYearBean = new DevtjyearBean();
				Devtjyear myDevYear = new Devtjyear();
				
				
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
					result.addAll((List) myDevYearBean.getQueryList(HQLstr));
				}
				
				
				// result = (List)myDevYearBean.getQueryList( HQLstr );
				
				// ȡ���豸��ź��豸����
				for (int i = 0; i < result.size(); i++) {
					myDevYear = (Devtjyear) result.get(i);
					
					
					// ȡ���豸����
					statno = myDevYear.getStateno().trim();
					if (!statno.equals("")) {
						iflag = 0;
						for (int j = 0; j < statArray.size(); j++) {
							if (statno.equals(statArray.get(j))) {
								iflag = 1;
								break;
							}
						}
						if (iflag == 0) {
							statArray.add(statno);
						}
					}
				}
				
				
				// ����ÿ̨�豸���豸���͵Ĺ��ϴ�����ʱ��
				for (int i = 0; i < devnoList.size(); i++) {
					devno = (String) devnoList.get(i);
					timesArray = new ArrayList();
					timelenArray = new ArrayList();
					for (int j = 0; j < statArray.size(); j++) {
						statno = (String) statArray.get(j);
						times = 0;
						timelen = 0;
						
						
						// wukp20120302
						Z001len = 0;
						// wukp20120302
						
						for (int k = 0; k < result.size(); k++) {
							myDevYear = (Devtjyear) result.get(k);
							if (devno.equals(myDevYear.getDevno().trim()) && statno.equals(myDevYear.getStateno().trim())) {
								
								
								// wukp20120302 �����豸ͨѶ����ʱ��
								if (statno.equals("Z001")) {
									Z001len += Long.parseLong(myDevYear.getTimelen().trim());
								} else {
									times += Long.parseLong(myDevYear.getTimes().trim());
									timelen += Long.parseLong(myDevYear.getTimelen().trim());
									
								}
								// wukp20120302
							}
						}
						timesArray.add(Long.toString(times));
						timelenArray.add(Long.toString(timelen));
					}
					DevtimesVector.add(timesArray);
					DevtimelenVector.add(timelenArray);
					
					
					// wukp20120302
					Z001lenVector.add(Long.toString(Z001len));
					// wukp20120302
					
				}
				
				
				// ������豸���͵Ĺ����ܴ�������ʱ��,size���豸����statArrayһ��
				for (int i = 0; i < statArray.size(); i++) {
					statno = (String) statArray.get(i);
					times = 0;
					timelen = 0;
					for (int j = 0; j < result.size(); j++) {
						myDevYear = (Devtjyear) result.get(j);
						if (statno.equals(myDevYear.getStateno().trim())) {
							times += Long.parseLong(myDevYear.getTimes().trim());
							timelen += Long.parseLong(myDevYear.getTimelen().trim());
						}
					}
					AlltimesArray.add(Long.toString(times));
					AlltimelenArray.add(Long.toString(timelen));
				}
				
				
				// ����ͳ�ƺ������
				String tmpstr = "";
				long dtdiff = 0;
				double dpercent = 0.00;
				String spercent = "";
				double summin = 0.00;
				
				for (int i = 0; i < 365 * 3; i++) {
					tmpstr = myPubUtil.getOtherDate(qend + "1231", -i);
					if (tmpstr.equals(qbegin + "0101")) {
						dtdiff = i + 1;
						break;
					}
				}
				summin = 60 * 24 * dtdiff;
				
				List temptimesList = new ArrayList();
				List temptimelenList = new ArrayList();
				for (int i = 0; i < devnoList.size(); i++) {
					ResultString = "";
					ResultString += devnoList.get(i) + ",";
					
					temptimesList = (List) DevtimesVector.get(i);
					temptimelenList = (List) DevtimelenVector.get(i);
					
					
					// wukp20120302 ��¼ͨѶʱ��
					Z001len = 0;
					Z001len = Long.parseLong((String) Z001lenVector.get(i));
					// wukp20120302
					for (int j = 1; j < temptimesList.size(); j++) {
						ResultString += temptimesList.get(j) + ",";
						ResultString += temptimelenList.get(j) + ",";
						
						
						// wukp20120302 �㲿������ʱ�� /(60��*24Сʱ*����-ͨѶ����ʱ��)
						dpercent = (Double.parseDouble((String) temptimelenList.get(j)) / (summin - Z001len)) * 100.00;
						spercent = myPubUtil.double2String(dpercent, 0);
						ResultString += spercent + "%,";
						dpercent = 0.00;
						// wukp20120302
					}
					
					ResultString = ResultString.substring(0, ResultString.length() - 1);
					
					ResultVector.add(ResultString);
				}
				
				
				// ���������
				StringBuffer outstr = new StringBuffer("");
				outstr.append("<html>\n");
				outstr.append("<head>\n");
				outstr.append("\t<title>�豸����ͳ��</title>\n");
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
				outstr.append("\t\t\t<font color=blue>PowerView XP </font>--- <font class='location'>�豸����ͳ��</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td>\n");
				outstr.append("\t\t\t<font class='location'>" + repnm + "</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td align='right'>\n");
				outstr.append("\t\t\t<font class='location'>(�걨)\n");
				if (!qbegin.equals(qend)) {
					outstr.append("\t\t\t\t" + qbegin.substring(0, 4) + "��");
					outstr.append("---" + qend.substring(0, 4) + "��&nbsp;\n");
				} else {
					outstr.append("\t\t\t\t" + qend.substring(0, 4) + "��&nbsp;\n");
				}
				outstr.append("\t\t\t</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t</tr>\n");
				outstr.append("</table>\n");
				
				
				// ������ʾ����
				// ������ʾ����
				if ((devnoList.size() == 0) || (statArray.size() == 0)) {
					outstr.append("<table id='excel' width='100%' cellspacing='0' cellpadding='0' class='list_table_border'>\n");
					outstr.append("\t\t<td width='15%' rowspan='2' class='list_td_prom'><b><font color='red'>���ݿ���û���������..</font></b></td>\n");
					outstr.append("\t</tr>\n");
					outstr.append("</table>\n");
				} else {
					outstr.append("<table id='excel' width='100%' cellspacing='0' cellpadding='0' class='list_table_border'>\n");
					// ��ͷ
					outstr.append("\t<tr align='center'>\n");
					outstr.append("\t\t<td width='15%' rowspan='2' class='list_td_prom'><b>�豸���</b></td>\n");
					
					
					// ȡ�����豸��Ŷ�Ӧ�����ı�ʾ
					DevErrorsUtil myDevErrorsUtil = new DevErrorsUtil();
					String[][] subdevs = myDevErrorsUtil.getSubDevice();
					String subdevno = "";
					String subdevnm = "";
					
					
					// �豸����
					for (int i = 1; i < statArray.size(); i++) {
						outstr.append("\t\t<td width='" + (100 - 15) / statArray.size() + "%' colspan='3' class='list_td_prom'><b>");
						subdevno = (String) statArray.get(i);
						for (int j = 0; j < subdevs.length; j++) {
							if (subdevs[j][0].equals(subdevno)) {
								subdevnm = subdevs[j][1];
								break;
							}
						}
						outstr.append(subdevnm + "</b></td>\n");
					}
					outstr.append("\t</tr>\n");
					outstr.append("\t<tr align='center'>\n");
					for (int i = 1; i < statArray.size(); i++) {
						outstr.append("\t\t<td width='" + (100 - 15) / (statArray.size() * 3) + "%' class='list_td_prom'>&nbsp;<nobr><b>����</b></nobr></td>\n");
						outstr.append("\t\t<td width='" + (100 - 15) / (statArray.size() * 3) + "%' class='list_td_prom'>&nbsp;<nobr><b>ʱ��(Сʱ)</b></nobr></td>\n");
						outstr.append("\t\t<td width='" + (100 - 15) / (statArray.size() * 3) + "%' class='list_td_prom'>&nbsp;<nobr><b>������</b></nobr></td>\n");
					}
					outstr.append("\t</tr>\n");
					
					
					// ͳ��
					long bal1 = 0;
					long bal2 = 0;
					for (int i = 0; i < ResultVector.size(); i++) {
						StringTokenizer wb = new StringTokenizer((String) ResultVector.get(i), ",");
						devno = wb.nextToken();
						outstr.append("\t<tr class='list_tr" + i % 2 + "'>\n");
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + devno + "</b></td>\n");
						for (int j = 1; j < statArray.size(); j++) {
							for (int k = 0; k < 3; k++) {
								tmpstr = wb.nextToken();
								if (k == 1) {
									bal2 = Long.parseLong(tmpstr);
									tmpstr = Long.toString(bal2 / 60) + "Сʱ" + Long.toString(bal2 % 60) + "����";
								}
								outstr.append("\t\t<td class='list_td_title' align='center'>&nbsp;<nobr>" + tmpstr + "</nobr></td>\n");
							}
						}
						outstr.append("\t</tr>\n");
					}
					
					
					// ���
					outstr.append("\t<tr>\n");
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>�ϼ�</b></td>\n");
					for (int i = 1; i < statArray.size(); i++) {
						tmpstr = (String) AlltimesArray.get(i);
						outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + tmpstr + "</b></td>\n");
						tmpstr = (String) AlltimelenArray.get(i);
						bal1 = Long.parseLong(tmpstr);
						tmpstr = Long.toString(bal1 / 60) + "Сʱ" + Long.toString(bal1 % 60) + "����";
						outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<nobr><b>" + tmpstr + "</b></nobr></td>\n");
						outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>-</b></td>\n");
					}
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
				
				String OutStr = outstr.toString();
				
				
				// ��¼�ļ�
				String filename = "A_03_" + operid + "_" + DateTimeStr + ".htm";
				FileOutputStream outStream = new FileOutputStream(filepath + filename);
				OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "GB2312");
				outStreamWriter.write(OutStr);
				outStreamWriter.flush();
				outStreamWriter.close();
				// */
				
				// ����ϵͳ�����
				myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "03", "A", operid);
				myStaMission.setCurrentflag("0");
				myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
				
				if (!myStaMissionBean.updateStaMission(myStaMission)) {
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "03", "A", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				}
				
				
				// �����Զ�����
				myAs = (Autosta) myAsModel.getAutosta(missionId);
				tmpstr = myAs.getCount().trim();
				tmpstr = Integer.toString(Integer.parseInt(tmpstr) + 1);
				
				myAs.setLaststat((myPubUtil.getNowDate(1)).substring(0, 4));
				myAs.setCount(tmpstr);
				myAs.setStatename(myPubUtil.dealNull(myAs.getStatename()).trim());
				myAs.setInfo(myPubUtil.dealNull(myAs.getInfo()).trim());
				
				myAsModel.updateAutosta(myAs);
				
				log.info("�� �豸����ͳ �� ����");
			} catch (HibernateException he) {
				log.error("HibernateException1", he);
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "03", "A", operid);
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
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "03", "A", operid);
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