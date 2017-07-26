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
 * ������¼ͳ�� ���ձ���
 * 
 * @author ������
 * 
 * ʱ�䣺20120327
 * 
 */
public class OptjDayServer extends Thread {
	
	Logger log = Logger.getLogger("web.lcjr.pvxp.util.OptjDayServer.java");
	
	
	private String HQLstr = null;
	
	
	private String bankid = null;
	
	
	private String operid = null;
	
	
	private String repnm = null;
	
	
	private String qbegin = null;
	
	
	private String qend = null;
	
	
	private String filepath = null;
	
	
	private List operList = new ArrayList();
	
	
	private List trcd = new ArrayList();
	
	
	private List type = new ArrayList();
	
	
	
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
	
	
	public List getOperList() {
		return (this.operList);
	}
	
	
	public List getTrcd() {
		return (this.trcd);
	}
	
	
	public List getType() {
		return (this.type);
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
	
	
	public void setOperList(List operList) {
		this.operList = operList;
	}
	
	
	public void setTrcd(List trcd) {
		this.trcd = trcd;
	}
	
	
	public void setType(List type) {
		this.type = type;
	}
	
	
	public OptjDayServer() {
		
	}
	
	
	public void run() {
		// �������߳� 
		
		log.info("������¼ͳ �� ��ʼ");
		try {
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();
			
			List result = new ArrayList();
			String tmptrcd = "";
			String operno = "";
			String tmptype = "";
			int times = 0;
			int iflag = 0;
			
			List opertimes = new ArrayList();
			List trcdtimes = new ArrayList();
			
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
				myStaMission.setStatesort("18");
				myStaMission.setCreatetype("M");
				myStaMission.setCurrentflag("1");
				myStaMission.setRemark1(" ");
				myStaMission.setRemark2(" ");
				myStaMission.setRemark3(" ");
				
				myStaMissionBean.addStaMission(myStaMission);
				
				
				// ִ�в�ѯ
				OplogBean myOplogBean = new OplogBean();
				Oplog myOplog = new Oplog();
				
				result = (List) myOplogBean.getQueryList(HQLstr);
				
				
				// ����ÿ����Ա�������ĸ�״̬����
				for (int i = 0; i < operList.size(); i++) {
					operno = (String) operList.get(i);
					for (int j = 0; j < trcd.size(); j++) {
						tmptrcd = (String) trcd.get(j);
						for (int t = 0; t < type.size(); t++) {
							tmptype = (String) type.get(t);
							times = 0;
							for (int k = 0; k < result.size(); k++) {
								myOplog = (Oplog) result.get(k);
								if (operno.equals(myOplog.getOperid().trim()) && tmptrcd.equals(myOplog.getTrcd().trim()) && tmptype.equals(myOplog.getType().trim())) {
									times++;
								}
							}
							opertimes.add(String.valueOf(times));
						}
					}
				}
				
				
				// ��������͵ĸ�״̬�ܴ���
				for (int i = 0; i < trcd.size(); i++) {
					tmptrcd = (String) trcd.get(i);
					for (int t = 0; t < type.size(); t++) {
						tmptype = (String) type.get(t);
						times = 0;
						for (int j = 0; j < result.size(); j++) {
							myOplog = (Oplog) result.get(j);
							if (tmptrcd.equals(myOplog.getTrcd().trim()) && tmptype.equals(myOplog.getType().trim())) {
								times++;
							}
						}
						trcdtimes.add(String.valueOf(times));
					}
				}
				
				
				// ���������
				StringBuffer outstr = new StringBuffer("");
				outstr.append("<html>\n");
				outstr.append("<head>\n");
				outstr.append("\t<title>������¼ͳ��</title>\n");
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
				outstr.append("\t\t\t<font color=blue>PowerView XP </font>--- <font class='location'>������¼ͳ��</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td>\n");
				outstr.append("\t\t\t<font class='location'>" + repnm + "</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td align='right'>\n");
				outstr.append("\t\t\t<font class='location'>(�ձ�)\n");
				if (!qbegin.equals(qend)) {
					outstr.append("\t\t\t\t" + qbegin.substring(0, 4) + "��" + Integer.parseInt(qbegin.substring(4, 6)) + "��" + Integer.parseInt(qbegin.substring(6, 8)) + "��");
					outstr.append("---" + qend.substring(0, 4) + "��" + Integer.parseInt(qend.substring(4, 6)) + "��" + Integer.parseInt(qend.substring(6, 8)) + "��&nbsp;\n");
				} else {
					outstr.append("\t\t\t\t" + qend.substring(0, 4) + "��" + Integer.parseInt(qend.substring(4, 6)) + "��" + Integer.parseInt(qend.substring(6, 8)) + "��&nbsp;\n");
				}
				
				outstr.append("\t\t\t\t&nbsp;&nbsp;��λ������&nbsp;\n");
				outstr.append("\t\t\t</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t</tr>\n");
				outstr.append("</table>\n");
				
				
				// ������ʾ����
				if (operList.size() == 0) {
					outstr.append("There is no transaction on device you selected, so that is no Statistics.\n");
				} else {
					outstr.append("<table id='excel' width='100%' cellspacing='0' cellpadding='0' class='list_table_border'>\n");
					// ��ͷ
					outstr.append("\t<tr align='center'>\n");
					outstr.append("\t\t<td width='10%' class='list_td_prom' rowspan='2'><b>����Ա���</b></td>\n");
					outstr.append("\t\t<td width='10%' class='list_td_prom' rowspan='2'><b>����Ա����</b></td>\n");
					outstr.append("\t\t<td width='10%' class='list_td_prom' rowspan='2'><b>��������</b></td>\n");
					
					
					// ȡ��������Ŷ�Ӧ�����ı�ʾ
					OperTrcdUtil myTrcdUtil = new OperTrcdUtil();
					String[][] trcds = myTrcdUtil.getOperTrcd();
					String trcdno = "";
					String trcdnm = "";
					
					
					// ����
					for (int i = 0; i < trcd.size(); i++) {
						outstr.append("\t\t<td class='list_td_prom' colspan='" + type.size() + "'><b>");
						trcdno = (String) trcd.get(i);
						for (int j = 0; j < trcds.length; j++) {
							if (trcds[j][0].equals(trcdno)) {
								trcdnm = trcds[j][1];
								break;
							}
						}
						outstr.append(trcdnm + "</b></td>\n");
					}
					outstr.append("\t</tr>\n");
					
					outstr.append("\t<tr align='center'>\n");
					for (int i = 0; i < trcd.size(); i++) {
						for (int j = 0; j < type.size(); j++) {
							tmptype = ((String) type.get(j)).trim();
							if (tmptype.equals("0")) {
								tmptype = "�޸�";
							} else if (tmptype.equals("1")) {
								tmptype = "ɾ��";
							} else if (tmptype.equals("2")) {
								tmptype = "���";
							}
							outstr.append("\t\t<td class='list_td_prom'><b>" + tmptype + "</b></td>\n");
						}
					}
					outstr.append("\t</tr>\n");
					
					
					// ͳ��
					String opername = "";
					String bankname = "";
					OperatorModel myOperatorModel = new OperatorModel();
					for (int i = 0; i < operList.size(); i++) {
						operno = (String) operList.get(i);
						Operator myOperator = myOperatorModel.getOperator(operno);
						if (myOperator != null) {
							opername = myOperator.getName();
							bankname = myOperator.getBankid();
							Bankinfo myBank = BankinfoModel.getBankinfoFromList(bankname);
							if (myBank != null)
								bankname = myCharSet.db2web(myBank.getBanknm());
						}
						outstr.append("\t<tr class='list_tr" + i % 2 + "'>\n");
						outstr.append("\t\t<td class='list_td_title' align='center'>&nbsp;<b>" + operno + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_title' align='center'>&nbsp;<b>" + opername + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_title' align='center'>&nbsp;<b>" + bankname + "</b></td>\n");
						
						for (int j = 0; j < trcd.size(); j++) {
							for (int k = 0; k < type.size(); k++) {
								outstr.append("\t\t<td class='list_td_title' align='center'><nobr>&nbsp;" + opertimes.get(i * trcd.size() * type.size() + j * type.size() + k) + "</nobr></td>\n");
							}
						}
						outstr.append("\t</tr>\n");
					}
					
					
					// ���
					outstr.append("\t<tr>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>�ϼ�</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;</td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;</td>\n");
					for (int i = 0; i < trcd.size(); i++) {
						for (int j = 0; j < type.size(); j++) {
							outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + trcdtimes.get(i * type.size() + j) + "</b></td>\n");
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
				String filename = "M_18_" + operid + "_" + DateTimeStr + ".htm";
				FileOutputStream outStream = new FileOutputStream(filepath + filename);
				OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "GB2312");
				outStreamWriter.write(OutStr);
				outStreamWriter.flush();
				outStreamWriter.close();
				// */
				
				// ����ϵͳ�����
				myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "18", "M", operid);
				myStaMission.setCurrentflag("0");
				myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
				
				if (!myStaMissionBean.updateStaMission(myStaMission)) {
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "18", "M", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				}
				
				log.info("������¼ͳ �� ����");
			} catch (HibernateException he) {
				log.error("ERROR", he);
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "18", "M", operid);
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
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "18", "M", operid);
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