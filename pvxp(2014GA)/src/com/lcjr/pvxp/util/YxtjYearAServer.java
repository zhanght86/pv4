package com.lcjr.pvxp.util;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

import org.apache.log4j.Logger;

import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.bean.*;
import com.lcjr.pvxp.model.*;

import net.sf.hibernate.*;

/**
 * 
 * �� �豸�����������ͳ�� ���걨��
 * 
 * @author ������
 * 
 *         ʱ�䣺20120327
 * 
 */
public class YxtjYearAServer extends Thread {

	Logger log = Logger.getLogger("web.lcjr.pvxp.util.YxtjYearAServer.java");

	private String HQLstr = null;

	private String bankid = null;

	private String operid = null;

	private String repnm = null;

	private String qbegin = null;

	private String qend = null;

	private String filepath = null;

	private String missionId = null;

	private String[] sortlist = new String[0];

	private List trcdnmlist = new ArrayList();

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

	public String[] getSortlist() {
		return (this.sortlist);
	}

	public List getTrcdnmlist() {
		return (this.trcdnmlist);
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

	public void setSortlist(String[] sortlist) {
		this.sortlist = sortlist;
	}

	public void setTrcdnmlist(List trcdnmlist) {
		this.trcdnmlist = trcdnmlist;
	}

	public void setDevnoList(List devnoList) {
		this.devnoList = devnoList;
	}

	public YxtjYearAServer() {

	}

	public void run() {
		// �������߳�

		log.info("�� �豸�����������ͳ �� ��ʼ");

		try {
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();

			List result = new ArrayList();

			List devnoArray = new ArrayList();
			List ttrnumArray = new ArrayList();
			List strnumArray = new ArrayList();
			List AllstrnumArray = new ArrayList();
			List AllttrnumArray = new ArrayList();

			String devno = "";
			String trcdtype = "";

			long ttrnum = 0;
			long strnum = 0;

			int iflag = 0;

			Vector DevStrnumVector = new Vector();
			Vector DevTtrnumVector = new Vector();

			Vector ResultVector = new Vector();
			String ResultString = new String();

			StringTokenizer wb;

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
				myStaMission.setStatesort("02");
				myStaMission.setCreatetype("A");
				myStaMission.setCurrentflag("1");
				myStaMission.setRemark1(" ");
				myStaMission.setRemark2(" ");
				myStaMission.setRemark3(" ");

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
				TrcdtjyearBean myTjYearBean = new TrcdtjyearBean();
				Trcdtjyear myTjYear = new Trcdtjyear();

				result = (List) myTjYearBean.getQueryList(HQLstr);

				/*
				 * //ȡ���豸��� for( int i=0; i<result.size(); i++ ) { myTjYear =
				 * (Trcdtjyear)result.get(i); devno =
				 * myTjYear.getDevno().trim(); iflag = 0; for( int j=0;
				 * j<devnoArray.size(); j++ ) { if( devno.equals(
				 * devnoArray.get(j) ) ) { iflag = 1; break; } } if( iflag == 0
				 * ) { devnoArray.add( devno ); } }
				 */

				// ����ÿ̨�豸ÿ�����׵�����
				List tmplist = new ArrayList();
				String tmptrcd = "";
				for (int i = 0; i < devnoList.size(); i++) {
					strnum = 0;
					ttrnum = 0;
					devno = (String) devnoList.get(i);
					strnumArray = new ArrayList();
					ttrnumArray = new ArrayList();
					for (int j = 0; j < sortlist.length; j++) {
						strnum = 0;
						ttrnum = 0;
						tmptrcd = sortlist[j];
						tmplist = new ArrayList();
						wb = new StringTokenizer(tmptrcd, ",");
						while (wb.hasMoreTokens()) {
							tmplist.add(wb.nextToken());
						}
						for (int k = 0; k < tmplist.size(); k++) {
							trcdtype = (String) tmplist.get(k);
							for (int l = 0; l < result.size(); l++) {
								myTjYear = (Trcdtjyear) result.get(l);
								if (devno.equals(myTjYear.getDevno().trim()) && trcdtype.equals(myTjYear.getTrcdtype().trim())) {
									strnum += Long.parseLong(myTjYear.getStrnum().trim());
									ttrnum += Long.parseLong(myTjYear.getTtrnum().trim());
								}
							}
						}
						strnumArray.add(Long.toString(strnum));
						ttrnumArray.add(Long.toString(ttrnum));
					}
					DevStrnumVector.add(strnumArray);
					DevTtrnumVector.add(ttrnumArray);
				}

				// ����ÿ̨�豸�Ľ�������
				strnumArray = new ArrayList();
				ttrnumArray = new ArrayList();
				for (int i = 0; i < devnoList.size(); i++) {
					devno = (String) devnoList.get(i);
					strnum = 0;
					ttrnum = 0;
					for (int k = 0; k < result.size(); k++) {
						myTjYear = (Trcdtjyear) result.get(k);
						if (devno.equals(myTjYear.getDevno().trim())) {
							strnum += Long.parseLong(myTjYear.getStrnum().trim());
							ttrnum += Long.parseLong(myTjYear.getTtrnum().trim());
						}
					}
					strnumArray.add(Long.toString(strnum));
					ttrnumArray.add(Long.toString(ttrnum));
				}

				// ����ÿ��Ľ�������
				AllstrnumArray = new ArrayList();
				AllttrnumArray = new ArrayList();
				for (int i = 0; i < sortlist.length; i++) {
					strnum = 0;
					ttrnum = 0;
					tmptrcd = sortlist[i];
					tmplist = new ArrayList();
					wb = new StringTokenizer(tmptrcd, ",");
					while (wb.hasMoreTokens()) {
						tmplist.add(wb.nextToken());
					}
					for (int j = 0; j < tmplist.size(); j++) {
						trcdtype = (String) tmplist.get(j);
						for (int k = 0; k < result.size(); k++) {
							myTjYear = (Trcdtjyear) result.get(k);
							if (trcdtype.equals(myTjYear.getTrcdtype().trim())) {
								strnum += Long.parseLong(myTjYear.getStrnum().trim());
								ttrnum += Long.parseLong(myTjYear.getTtrnum().trim());
							}
						}
					}
					AllstrnumArray.add(Long.toString(strnum));
					AllttrnumArray.add(Long.toString(ttrnum));
				}

				// ����ͳ�ƺ������
				List tempStrnumList = new ArrayList();
				List tempTtrnumList = new ArrayList();
				for (int i = 0; i < devnoList.size(); i++) {
					ResultString = "";
					ResultString += devnoList.get(i) + ",";
					tempStrnumList = (List) DevStrnumVector.get(i);
					tempTtrnumList = (List) DevTtrnumVector.get(i);
					for (int k = 0; k < tempStrnumList.size(); k++) {
						ResultString += tempStrnumList.get(k) + "|";
						ResultString += tempTtrnumList.get(k) + ",";
					}
					ResultString += strnumArray.get(i) + "|";
					ResultString += ttrnumArray.get(i);

					ResultVector.add(ResultString);
				}

				// ���������
				StringBuffer outstr = new StringBuffer("");
				outstr.append("<html>\n");
				outstr.append("<head>\n");
				outstr.append("\t<title>�豸�����������ͳ��</title>\n");
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
				outstr.append("\t\t\t<font color=blue>PowerView XP </font>--- <font class='location'>�豸�����������ͳ��</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td>\n");
				outstr.append("\t\t\t<font class='location'>" + repnm + "</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td align='right'>\n");
				outstr.append("\t\t\t<font class='location'>(�걨)\n");
				if (!qbegin.equals(qend)) {
					outstr.append("\t\t\t\t" + qbegin.substring(0, 4) + "��");
					outstr.append("---" + qend.substring(0, 4) + "��\n");
				} else {
					outstr.append("\t\t\t\t" + qend.substring(0, 4) + "��\n");
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
					outstr.append("\t\t<td width='15%' class='list_td_prom'><b>�豸���</b></td>\n");
					for (int i = 0; i < trcdnmlist.size(); i++) {
						outstr.append("\t\t<td width='" + (100 - 15) / (trcdnmlist.size() + 1) + "%' class='list_td_prom'><b>" + trcdnmlist.get(i) + "</b></td>\n");
					}
					outstr.append("\t\t<td width='10%' class='list_td_prom'><b>�ϼ�</b></td>\n");
					outstr.append("\t</tr>\n");

					// ͳ��
					String tmpstr = "";
					String bal1 = "";
					String bal2 = "";
					int splitflag = 0;
					for (int i = 0; i < ResultVector.size(); i++) {
						wb = new StringTokenizer((String) ResultVector.get(i), ",");
						devno = wb.nextToken();
						outstr.append("\t<tr class='list_tr" + i % 2 + "'>\n");
						outstr.append("\t\t<td class='list_td_title'><b>" + devno + "</b></td>\n");
						for (int j = 0; j <= sortlist.length; j++) {
							tmpstr = wb.nextToken();
							splitflag = tmpstr.lastIndexOf("|");
							if (splitflag == -1) {
								tmpstr = "";
							} else {
								bal1 = tmpstr.substring(0, splitflag);
								bal2 = tmpstr.substring(splitflag + 1);
								tmpstr = bal2;
							}
							if (j == sortlist.length) {
								outstr.append("\t\t<td align='center' class='list_td_title'><b>" + tmpstr + "</b></td>\n");
							} else {
								outstr.append("\t\t<td align='center' class='list_td_title'>" + tmpstr + "</td>\n");
							}
						}
						outstr.append("\t</tr>\n");
					}

					// ���
					outstr.append("\t<tr class='list_td_title'>\n");
					outstr.append("\t\t<td class='list_td_prom'><b>�ϼ�</b></td>\n");
					long allsum = 0;
					for (int i = 0; i < AllstrnumArray.size(); i++) {
						bal1 = (String) AllstrnumArray.get(i);
						bal2 = (String) AllttrnumArray.get(i);
						// tmpstr = "";
						// tmpstr = bal1;
						tmpstr = bal2;
						// tmpstr = bal1 + "|" + bal2;
						outstr.append("\t\t<td align='center' class='list_td_prom'><b>" + tmpstr + "</b></td>\n");
						allsum += Long.parseLong(tmpstr);
					}
					outstr.append("\t\t<td align='center' class='list_td_prom'><b>" + allsum + "</b></td>\n");
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
				String filename = "A_02_" + operid + "_" + DateTimeStr + ".htm";
				FileOutputStream outStream = new FileOutputStream(filepath + filename);
				OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "GB2312");
				outStreamWriter.write(OutStr);
				outStreamWriter.flush();
				outStreamWriter.close();
				// */

				// ����ϵͳ�����
				myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "02", "A", operid);
				myStaMission.setCurrentflag("0");
				myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());

				if (!myStaMissionBean.updateStaMission(myStaMission)) {
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "02", "A", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				}

				// �����Զ�����
				String tmpstr = myAs.getCount().trim();
				tmpstr = Integer.toString(Integer.parseInt(tmpstr) + 1);

				myAs.setLaststat((myPubUtil.getNowDate(1)).substring(0, 4));
				myAs.setCount(tmpstr);
				myAs.setStatename(myPubUtil.dealNull(myAs.getStatename()).trim());
				myAs.setInfo(myPubUtil.dealNull(myAs.getInfo()).trim());

				myAsModel.updateAutosta(myAs);

				log.info("�� �豸�����������ͳ �� ����");

			} catch (HibernateException he) {
				log.error("ERROR", he);
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "02", "A", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				} catch (Exception e) {
					log.error("ERROR", e);
				}

			} catch (Exception ex) {
				log.error("ERROR", ex);
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "02", "A", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				} catch (Exception e) {
					log.error("ERROR", e);
				}
			}
		} catch (Exception e) {
			log.error("ERROR", e);
		}
	}

	/**
	 * @return the missionId
	 */
	public String getMissionId() {
		return missionId;
	}

	/**
	 * @param missionId
	 *            the missionId to set
	 */
	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}
}