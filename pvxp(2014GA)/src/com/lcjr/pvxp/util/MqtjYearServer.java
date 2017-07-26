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
 * ���޼�¼ͳ�� ���걨��
 * 
 * @author ������
 * 
 *         ʱ�䣺20120327
 * 
 */

public class MqtjYearServer extends Thread {

	Logger log = Logger.getLogger("web.lcjr.pvxp.util.MqtjYearServer.java");

	private String HQLstr = null;

	private String bankid = null;

	private String operid = null;

	private String repnm = null;

	private String qbegin = null;

	private String qend = null;

	private String filepath = null;

	private List devnoList = new ArrayList();

	private List subdevice = new ArrayList();

	private List mqState = new ArrayList();

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

	public List getSubdevice() {
		return (this.subdevice);
	}

	/**
	 * @return the mqState
	 */
	public List getMqState() {
		return mqState;
	}

	/**
	 * @param mqState
	 *            the mqState to set
	 */
	public void setMqState(List mqState) {
		this.mqState = mqState;
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

	public void setSubdevice(List subdevice) {
		this.subdevice = subdevice;
	}

	public void setState(List state) {
		this.mqState = state;
	}

	public MqtjYearServer() {

	}

	public void run() {
		// �������߳� ���޼�¼ͳ�� ���걨��

		log.info("���޼�¼ͳ �� ��ʼ");

		try {
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();

			List result = new ArrayList();
			String parts = "";
			String devno = "";
			String tmpstate = "";
			int times = 0;
			int iflag = 0;

			List devtimes = new ArrayList();
			List parttimes = new ArrayList();

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

				myStaMission.setStatename(myCharSet.form2db(repnm));
				repnm = myCharSet.form2file(repnm);

				myStaMission.setBankid(bankid);
				myStaMission.setOwnerid(operid);
				myStaMission.setStatesort("12");
				myStaMission.setCreatetype("M");
				myStaMission.setCurrentflag("1");
				myStaMission.setRemark1(" ");
				myStaMission.setRemark2(" ");
				myStaMission.setRemark3(" ");

				myStaMissionBean.addStaMission(myStaMission);

				// ִ�в�ѯ
				MaintainBean myMaintainBean = new MaintainBean();
				Maintain myMaintain = new Maintain();

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
					result.addAll((List) myMaintainBean.getQueryList(HQLstr));

				}

				// result = (List)myMaintainBean.getQueryList( HQLstr );

				// ����ÿ̨�豸�������ĸ�״̬����
				for (int i = 0; i < devnoList.size(); i++) {
					devno = (String) devnoList.get(i);
					for (int j = 0; j < subdevice.size(); j++) {
						parts = (String) subdevice.get(j);
						for (int t = 0; t < mqState.size(); t++) {
							tmpstate = (String) mqState.get(t);
							times = 0;
							for (int k = 0; k < result.size(); k++) {
								myMaintain = (Maintain) result.get(k);
								if (devno.equals(myMaintain.getDevno().trim()) && parts.equals(myMaintain.getTrbtype().trim()) && tmpstate.equals(myMaintain.getState().trim())) {
									times++;
								}
							}
							devtimes.add(String.valueOf(times));
						}
					}
				}

				// ����������ĸ�״̬�ܴ���
				for (int i = 0; i < subdevice.size(); i++) {
					parts = (String) subdevice.get(i);
					for (int t = 0; t < mqState.size(); t++) {
						tmpstate = (String) mqState.get(t);
						times = 0;
						for (int j = 0; j < result.size(); j++) {
							myMaintain = (Maintain) result.get(j);
							if (parts.equals(myMaintain.getTrbtype().trim()) && tmpstate.equals(myMaintain.getState().trim())) {
								times++;
							}
						}
						parttimes.add(String.valueOf(times));
					}
				}

				// ���������
				StringBuffer outstr = new StringBuffer("");
				outstr.append("<html>\n");
				outstr.append("<head>\n");
				outstr.append("\t<title>���޼�¼ͳ��</title>\n");
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
				outstr.append("\t\t\t<font color=blue>PowerView XP </font>--- <font class='location'>���޼�¼ͳ��</font>\n");
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

				outstr.append("\t\t\t\t&nbsp;&nbsp;��λ������&nbsp;\n");
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
					outstr.append("\t\t<td width='10%' class='list_td_prom' rowspan='2'><b>�豸���</b></td>\n");
					outstr.append("\t\t<td width='10%' class='list_td_prom' rowspan='2'><b>��������</b></td>\n");

					// ȡ��������Ŷ�Ӧ�����ı�ʾ
					DevErrorsUtil myDevErrorsUtil = new DevErrorsUtil();
					String[][] subdevs = myDevErrorsUtil.getSubDevice();
					String subdevno = "";
					String subdevnm = "";

					// ����
					for (int i = 0; i < subdevice.size(); i++) {
						outstr.append("\t\t<td class='list_td_prom' colspan='" + mqState.size() + "'><b>");
						subdevno = (String) subdevice.get(i);
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
					for (int i = 0; i < subdevice.size(); i++) {
						for (int j = 0; j < mqState.size(); j++) {
							tmpstate = ((String) mqState.get(j)).trim();
							if (tmpstate.equals("0")) {
								tmpstate = "δ����";
							} else if (tmpstate.equals("1")) {
								tmpstate = "�Ѵ���";
							}
							outstr.append("\t\t<td class='list_td_prom'><b>" + tmpstate + "</b></td>\n");
						}
					}
					outstr.append("\t</tr>\n");

					// ͳ��
					String bankname = "";
					for (int i = 0; i < devnoList.size(); i++) {
						devno = (String) devnoList.get(i);
						bankname = myCharSet.db2web(BankinfoModel.getBankinfoFromList(DevinfoModel.getDevFromList(devno).getBankid()).getBanknm());
						outstr.append("\t<tr class='list_tr" + i % 2 + "'>\n");
						outstr.append("\t\t<td class='list_td_title' align='center'>&nbsp;<b>" + devno + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_title' align='center'>&nbsp;<b>" + bankname + "</b></td>\n");

						for (int j = 0; j < subdevice.size(); j++) {
							for (int k = 0; k < mqState.size(); k++) {
								outstr.append("\t\t<td class='list_td_title' align='center'><nobr>&nbsp;" + devtimes.get(i * subdevice.size() * mqState.size() + j * mqState.size() + k)
										+ "</nobr></td>\n");
							}
						}
						outstr.append("\t</tr>\n");
					}

					// ���
					outstr.append("\t<tr>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>�ϼ�</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;</td>\n");
					for (int i = 0; i < subdevice.size(); i++) {
						for (int j = 0; j < mqState.size(); j++) {
							outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + parttimes.get(i * mqState.size() + j) + "</b></td>\n");
						}
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
				String filename = "M_12_" + operid + "_" + DateTimeStr + ".htm";
				FileOutputStream outStream = new FileOutputStream(filepath + filename);
				OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "GB2312");
				outStreamWriter.write(OutStr);
				outStreamWriter.flush();
				outStreamWriter.close();
				// */

				// ����ϵͳ�����
				myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "12", "M", operid);
				myStaMission.setCurrentflag("0");
				myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());

				if (!myStaMissionBean.updateStaMission(myStaMission)) {
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "12", "M", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				}
				log.info("���޼�¼ͳ �� ����");
			} catch (HibernateException he) {
				log.error("ERROR", he);
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "12", "M", operid);
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
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "12", "M", operid);
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
}