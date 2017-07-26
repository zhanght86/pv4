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
 * 交易手续费统计 年报
 * 
 * 
 * @author 武坤鹏
 * 
 * 时间： 2012-03-27
 * 
 */
public class JytjFeesYearServer extends Thread {
	
	Logger log = Logger.getLogger("web.lcjr.pvxp.util.JytjFeesYearServer.java");
	
	
	private String bankid = null;
	
	
	private String operid = null;
	
	
	private String repnm = null;
	
	
	private String qbegin = null;
	
	
	private String qend = null;
	
	
	
	// private String moneytype= null;
	private String filepath = null;
	
	
	private String statmode = "";
	
	
	private List trcdlist = new ArrayList();
	
	
	private List devnoList = new ArrayList();
	
	
	private List HQLstr = new ArrayList();
	
	
	
	public List getHQLstr() {
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
	
	
	public List getTrcdlist() {
		return (this.trcdlist);
	}
	
	
	public String getStatmode() {
		return (this.statmode);
	}
	
	
	public List getDevnoList() {
		return (this.devnoList);
	}
	
	
	public void setHQLstr(List HQLstr) {
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
	
	
	public void setTrcdlist(List trcdlist) {
		this.trcdlist = trcdlist;
	}
	
	
	public void setStatmode(String statmode) {
		this.statmode = statmode;
	}
	
	
	public void setDevnoList(List devnoList) {
		this.devnoList = devnoList;
	}
	
	
	public JytjFeesYearServer() {
		
	}
	
	
	public void run() {
		// 进入主线程
		
		log.info("交易手续费统计 年 开始");
		
		try {
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();
			
			List result = new ArrayList();
			List trcdArray = new ArrayList();
			List feesArray = new ArrayList();
			List devnoArray = new ArrayList();
			
			String[] devaddr = new String[devnoList.size()];
			
			int[][] tableNumContent = new int[devnoList.size()][trcdlist.size()];
			long[][] tablePriceContent = new long[devnoList.size()][trcdlist.size()];
			long[][] feesContent = new long[devnoList.size()][trcdlist.size()];
			
			int[] totalNum = new int[trcdlist.size()];
			long[] totalPrice = new long[trcdlist.size()];
			long[] fees = new long[trcdlist.size()];
			
			for (int i = 0; i < devnoList.size(); i++) {
				for (int j = 0; j < trcdlist.size(); j++) {
					tableNumContent[i][j] = 0;
					tablePriceContent[i][j] = 0;
					feesContent[i][j] = 0;
					
					totalNum[j] = 0;
					totalPrice[j] = 0;
					fees[j] = 0;
				}
			}
			
			String tmpfee = "";
			String devno = "";
			String trcdno = "";
			String moneyno = "";
			String tmpHQLstr = "";
			String tmppr1 = "";
			String tmppr2 = "";
			long ftmp1 = 0;
			long ftmp2 = 0;
			int strnum = 0;
			int len = 0;
			
			String TimeStr = myPubUtil.getNowTime();
			TimeStr = myPubUtil.replace(TimeStr, ":", "");
			String DateTimeStr = myPubUtil.getNowDate(1) + TimeStr;
			
			try {
				// 年统计报表
				
				// 记录系统任务表
				StaMissionModel myStaMissionModel = new StaMissionModel();
				StaMissionBean myStaMissionBean = new StaMissionBean();
				StaMission myStaMission = new StaMission();
				
				myStaMission.setTimeid(DateTimeStr);
				myStaMission.setStatename(myCharSet.form2db(repnm));
				repnm = myCharSet.form2file(repnm);
				myStaMission.setBankid(bankid);
				myStaMission.setOwnerid(operid);
				myStaMission.setStatesort("14");
				myStaMission.setCreatetype("M");
				myStaMission.setCurrentflag("1");
				myStaMission.setRemark1("");
				myStaMission.setRemark2("");
				myStaMission.setRemark3("");
				
				myStaMissionBean.addStaMission(myStaMission);
				
				
				// 取出设备对应的安装地址
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
				
				
				// 手续费率
				int typenums = Integer.parseInt(myPubUtil.ReadConfig("CodeFees", "Code_Num", "0", "Trade.ini"));
				
				for (int i = 1; i <= typenums; i++) {
					String tmp = myPubUtil.ReadConfig("CodeFees", "Code_" + i, "0,0.0", "Trade.ini");
					int pos = tmp.indexOf(",");
					String tmpfees = tmp.substring(pos + 1);
					int pos1 = tmpfees.indexOf(".");
					if (pos != -1) {
						tmpfees = tmpfees.substring(pos1);
					}
					
					if (len < tmpfees.length()) {
						len = tmpfees.length();
					}
					
				}
				
				for (int i = 1; i <= typenums; i++) {
					String tmp = myPubUtil.ReadConfig("CodeFees", "Code_" + i, "0,0.0", "Trade.ini");
					int pos = tmp.indexOf(",");
					trcdArray.add(tmp.substring(0, pos));
					String tmpfees = tmp.substring(pos + 1);
					int pos1 = tmpfees.indexOf(".");
					if (pos != -1) {
						tmpfees = tmpfees.substring(pos1 + 1);
					}
					
					int templen = tmpfees.length();
					for (int j = 0; j < templen; j++) {
						pos = j;
						if (!tmpfees.substring(j, j + 1).equals("0"))
							break;
					}
					
					tmpfees = tmpfees.substring(pos);
					for (int j = 0; j < len - templen; j++) {
						tmpfees += "0";
					}
					
					feesArray.add(tmpfees);
				}
				
				
				// 执行查询
				TrcdtjyearBean myTjYearBean = new TrcdtjyearBean();
				Trcdtjyear myTjYear = new Trcdtjyear();
				
				
				// 计算每台设备各类交易的交易量和金额
				for (int z = 0; z < HQLstr.size(); z++) {
					tmpHQLstr = (String) HQLstr.get(z);
					result = (List) myTjYearBean.getQueryList(tmpHQLstr);
					for (int i = 0; i < result.size(); i++) {
						myTjYear = (Trcdtjyear) result.get(i);
						for (int j = 0; j < devnoList.size(); j++) {
							devno = (String) devnoList.get(j);
							if (devno.equals(myTjYear.getDevno().trim())) {
								for (int x = 0; x < trcdlist.size(); x++) {
									trcdno = (String) trcdlist.get(x);
									if (trcdno.equals(myTjYear.getTrcdtype().trim())) {
										strnum = Integer.parseInt(myTjYear.getStrnum().trim());
										tableNumContent[j][x] += strnum;
										totalNum[x] += strnum;
										
										tmppr1 = myTjYear.getStrsum().trim();
										if (tmppr1 != null) {
											int pos = tmppr1.indexOf(".");
											if (pos != -1) {
												tmppr2 = tmppr1.substring(0, pos) + tmppr1.substring(pos + 1, pos + 3);
											} else {
												tmppr2 = tmppr1 + "00";
											}
										}
										
										ftmp1 = Long.parseLong(tmppr2);
										
										tablePriceContent[j][x] += ftmp1;
										totalPrice[x] += ftmp1;
										for (int y = 0; y < feesArray.size(); y++) {
											tmpfee = (String) trcdArray.get(y);
											if (tmpfee.equals(trcdno)) {
												ftmp2 = Long.parseLong((String) feesArray.get(y));
												feesContent[j][x] += ftmp1 * ftmp2;
												fees[x] += ftmp1 * ftmp2;
												
											}
										}
									}
								}
							}
						}
					}
				}
				
				
				// 整理输出流
				StringBuffer outstr = new StringBuffer("");
				outstr.append("<html>\n");
				outstr.append("<head>\n");
				outstr.append("\t<title>交易手续费统计</title>\n");
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
				
				
				// 标题
				outstr.append("<table width='100%' cellspacing='0' cellpadding='0'>\n");
				outstr.append("\t<tr>\n");
				outstr.append("\t\t<td align='left' valign='center' width='30' height='40'>\n");
				outstr.append("\t\t\t&nbsp;\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td>\n");
				outstr.append("\t\t\t<font color=blue>PowerView XP </font>--- <font class='location'>交易手续费统计</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td>\n");
				outstr.append("\t\t\t<font class='location'>" + repnm + "</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td align='right'>\n");
				outstr.append("\t\t\t<font class='location'>(年报)\n");
				if (!qbegin.equals(qend)) {
					outstr.append("\t\t\t\t" + qbegin.substring(0, 4) + "年");
					outstr.append("---" + qend.substring(0, 4) + "年&nbsp;&nbsp;\n");
				} else {
					outstr.append("\t\t\t\t" + qend.substring(0, 4) + "年&nbsp;&nbsp;\n");
				}
				
				outstr.append("\t\t\t</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t</tr>\n");
				outstr.append("</table>\n");
				
				
				// 处理显示报表
				// if( devnoArray.size() == 0 ){
				if (devnoList.size() == 0) {
					outstr.append("There is no transaction on device you selected, so that is no Statistics.\n");
				} else if (statmode.equals("all")) {
					int totalsortnum = 0;
					int typenum = 0;
					int sortnum = Integer.parseInt(myPubUtil.ReadConfig("TradeType", "TypeSortNum", "0", "Trade.ini"));
					for (int i = 1; i <= sortnum; i++) {
						typenum = Integer.parseInt(myPubUtil.ReadConfig("TradeType", "Type" + i + "_Num", "0", "Trade.ini"));
						totalsortnum += typenum;
					}
					
					int[][] sortnumContent = new int[devnoList.size()][totalsortnum];
					long[][] sortpriceContent = new long[devnoList.size()][totalsortnum];
					long[][] sortfeesContent = new long[devnoList.size()][totalsortnum];
					for (int i = 0; i < devnoList.size(); i++) {
						for (int j = 0; j < totalsortnum; j++) {
							sortnumContent[i][j] = 0;
							sortpriceContent[i][j] = 0;
							sortfeesContent[i][j] = 0;
						}
					}
					
					int ttnum = 0;
					String[] sortname = new String[totalsortnum];
					int[] sortTotalnum = new int[totalsortnum];
					long[] sortTotalprice = new long[totalsortnum];
					long[] sortTotalfees = new long[totalsortnum];
					for (int i = 1; i <= sortnum; i++) {
						int typenum1 = Integer.parseInt(myPubUtil.ReadConfig("TradeType", "Type" + i + "_Num", "0", "Trade.ini"));
						
						for (int j = 1; j <= typenum1; j++) {
							String tmpstr1 = myPubUtil.ReadConfig("TradeType", "Type" + i + "_" + j, "0", "Trade.ini");
							
							int tmppos = tmpstr1.indexOf("|");
							sortname[ttnum] = tmpstr1.substring(0, tmppos);
							String tmpstr2 = tmpstr1.substring(tmppos + 1);
							StringTokenizer wb = new StringTokenizer(tmpstr2, ",");
							int tnum = wb.countTokens();
							for (int z = 0; z < tnum; z++) {
								String tmpstr3 = wb.nextToken();
								for (int x = 0; x < devnoList.size(); x++) {
									for (int y = 0; y < trcdlist.size(); y++) {
										trcdno = (String) trcdlist.get(y);
										if (tmpstr3.equals(trcdno)) {
											sortnumContent[x][ttnum] += tableNumContent[x][y];
											sortTotalnum[ttnum] += tableNumContent[x][y];
											sortpriceContent[x][ttnum] += tablePriceContent[x][y];
											sortTotalprice[ttnum] += tablePriceContent[x][y];
											
											for (int m = 0; m < trcdArray.size(); m++) {
												tmpfee = (String) trcdArray.get(m);
												if (tmpfee.equals(trcdno)) {
													ftmp2 = Long.parseLong((String) feesArray.get(m));
													
													sortfeesContent[x][ttnum] += tablePriceContent[x][y] * ftmp2;
													sortTotalfees[ttnum] += feesContent[x][y];
													
												}
											}
										}
									}
									
								}
								
							}
							ttnum++;
						}
					}
					
					outstr.append("<table id='excel' width='100%' cellspacing='0' cellpadding='0' class='list_table_border'>\n");
					// 表头
					outstr.append("\t<tr align='center'>\n");
					outstr.append("\t\t<td class='list_td_prom' rowspan='2'><b>设备编号</b></td>\n");
					outstr.append("\t\t<td rowspan='2' class='list_td_prom'><b>安装地址</b></td>\n");
					String[][] tradecode = myPubUtil.Ini2Array("ini", "Trade.ini", "TradeCode", "TCode_Num", "TCode_", ",", "");
					for (int i = 0; i < totalsortnum; i++) {
						outstr.append("\t\t<td colspan='3' class='list_td_prom'><b>" + sortname[i] + "</b></td>\n");
						
					}
					outstr.append("\t\t<td colspan='3' class='list_td_prom'><b>合计</b></td>\n");
					outstr.append("\t</tr>\n");
					
					outstr.append("\t<tr align='center'>\n");
					for (int i = 0; i <= totalsortnum; i++) {
						
						outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>笔数</b></nobr></td>\n");
						outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>金额</b></nobr></td>\n");
						outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>手续费</b></nobr></td>\n");
						
					}
					
					outstr.append("\t</tr>\n");
					
					
					// 表体
					int[] devTotalnum = new int[devnoList.size()];
					long[] devTotalprice = new long[devnoList.size()];
					long[] devTotalfees = new long[devnoList.size()];
					String tmpString = "";
					String tmpString1 = "";
					String tmpString2 = "";
					int tmplen = 0;
					for (int i = 0; i < devnoList.size(); i++) {
						outstr.append("\t<tr class='list_tr" + i % 2 + "' align='center'>\n");
						devno = (String) devnoList.get(i);
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + devno + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_title'>&nbsp;" + devaddr[i] + "</td>\n");
						for (int j = 0; j < totalsortnum; j++) {
							devTotalnum[i] += sortnumContent[i][j];
							devTotalprice[i] += sortpriceContent[i][j];
							devTotalfees[i] += sortfeesContent[i][j];
							tmpString1 = String.valueOf(sortpriceContent[i][j]);
							tmplen = tmpString1.length();
							if (tmplen == 1) {
								tmpString2 = "0.0" + tmpString1;
							} else if (tmplen == 2) {
								tmpString2 = "0." + tmpString1;
							} else {
								tmpString2 = tmpString1.substring(0, tmplen - 2) + "." + tmpString1.substring(tmplen - 2);
							}
							
							outstr.append("\t\t<td class='list_td_title'>&nbsp;" + String.valueOf(sortnumContent[i][j]) + "</td>\n");
							outstr.append("\t\t<td class='list_td_title'>&nbsp;" + tmpString2 + "</td>\n");
							tmpString1 = String.valueOf(sortfeesContent[i][j]);
							tmplen = tmpString1.length();
							if (tmplen <= len + 2) {
								tmpString2 = "0.";
								for (int k = 0; k < len + 2 - tmplen; k++) {
									tmpString2 += "0";
								}
								tmpString2 += tmpString1;
								
							} else {
								tmpString2 = tmpString1.substring(0, tmplen - len - 2) + "." + tmpString1.substring(tmplen - len - 2);
							}
							tmpString2 = tmpString2.substring(0, tmpString2.length() - len);
							outstr.append("\t\t<td class='list_td_title'>&nbsp;" + tmpString2 + "</td>\n");
						}
						outstr.append("\t\t<td class='list_td_title'>&nbsp;" + String.valueOf(devTotalnum[i]) + "</td>\n");
						tmpString1 = String.valueOf(devTotalprice[i]);
						tmplen = tmpString1.length();
						if (tmplen == 1) {
							tmpString2 = "0.0" + tmpString1;
						} else if (tmplen == 2) {
							tmpString2 = "0." + tmpString1;
						} else {
							tmpString2 = tmpString1.substring(0, tmplen - 2) + "." + tmpString1.substring(tmplen - 2);
						}
						outstr.append("\t\t<td class='list_td_title'>&nbsp;" + tmpString2 + "</td>\n");
						tmpString1 = String.valueOf(devTotalfees[i]);
						tmplen = tmpString1.length();
						if (tmplen <= len + 2) {
							tmpString2 = "0.";
							for (int k = 0; k < len + 2 - tmplen; k++) {
								tmpString2 += "0";
							}
							tmpString2 += tmpString1;
							
						} else {
							tmpString2 = tmpString1.substring(0, tmplen - len - 2) + "." + tmpString1.substring(tmplen - len - 2);
						}
						tmpString2 = tmpString2.substring(0, tmpString2.length() - len);
						outstr.append("\t\t<td class='list_td_title'>&nbsp;" + tmpString2 + "</td>\n");
						outstr.append("\t</tr>\n");
					}
					
					
					// 表脚
					int num = 0;
					long price = 0;
					long fee = 0;
					String tmpStr = "";
					outstr.append("\t<tr align='center'>\n");
					outstr.append("\t\t<td class='list_td_prom' ><b>合计</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' >&nbsp;</td>\n");
					for (int i = 0; i < totalsortnum; i++) {
						num += sortTotalnum[i];
						price += sortTotalprice[i];
						fee += sortTotalfees[i];
						tmpString1 = String.valueOf(sortTotalprice[i]);
						tmplen = tmpString1.length();
						if (tmplen == 1) {
							tmpString2 = "0.0" + tmpString1;
						} else if (tmplen == 2) {
							tmpString2 = "0." + tmpString1;
						} else {
							tmpString2 = tmpString1.substring(0, tmplen - 2) + "." + tmpString1.substring(tmplen - 2);
						}
						
						outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + String.valueOf(sortTotalnum[i]) + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + tmpString2 + "</b></td>\n");
						tmpString1 = String.valueOf(sortTotalfees[i]);
						tmplen = tmpString1.length();
						if (tmplen <= len + 2) {
							tmpString2 = "0.";
							for (int k = 0; k < len + 2 - tmplen; k++) {
								tmpString2 += "0";
							}
							tmpString2 += tmpString1;
							
						} else {
							tmpString2 = tmpString1.substring(0, tmplen - len - 2) + "." + tmpString1.substring(tmplen - len - 2);
						}
						tmpString2 = tmpString2.substring(0, tmpString2.length() - len);
						outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + tmpString2 + "</b></td>\n");
					}
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + String.valueOf(num) + "</b></td>\n");
					tmpString1 = String.valueOf(price);
					tmplen = tmpString1.length();
					if (tmplen == 1) {
						tmpString2 = "0.0" + tmpString1;
					} else if (tmplen == 2) {
						tmpString2 = "0." + tmpString1;
					} else {
						tmpString2 = tmpString1.substring(0, tmplen - 2) + "." + tmpString1.substring(tmplen - 2);
					}
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + tmpString2 + "</b></td>\n");
					tmpString1 = String.valueOf(fee);
					tmplen = tmpString1.length();
					if (tmplen <= len + 2) {
						tmpString2 = "0.";
						for (int k = 0; k < len + 2 - tmplen; k++) {
							tmpString2 += "0";
						}
						tmpString2 += tmpString1;
						
					} else {
						tmpString2 = tmpString1.substring(0, tmplen - len - 2) + "." + tmpString1.substring(tmplen - len - 2);
					}
					tmpString2 = tmpString2.substring(0, tmpString2.length() - len);
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + tmpString2 + "</b></td>\n");
					
					outstr.append("\t</tr>\n");
					
					outstr.append("</table>\n");
					
				} else {
					outstr.append("<table id='excel' width='100%' cellspacing='0' cellpadding='0' class='list_table_border'>\n");
					// 表头
					outstr.append("\t<tr align='center'>\n");
					outstr.append("\t\t<td class='list_td_prom' rowspan='2'><b>设备编号</b></td>\n");
					outstr.append("\t\t<td rowspan='2' class='list_td_prom'><b>安装地址</b></td>\n");
					String[][] tradecode = myPubUtil.Ini2Array("ini", "Trade.ini", "TradeCode", "TCode_Num", "TCode_", ",", "");
					for (int i = 0; i < trcdlist.size(); i++) {
						trcdno = (String) trcdlist.get(i);
						for (int j = 0; j < tradecode.length; j++) {
							if (trcdno.equals(tradecode[j][0])) {
								outstr.append("\t\t<td colspan='3' class='list_td_prom'><b>" + tradecode[j][1] + "</b></td>\n");
							}
						}
					}
					outstr.append("\t\t<td colspan='3' class='list_td_prom'><b>合计</b></td>\n");
					outstr.append("\t</tr>\n");
					
					outstr.append("\t<tr align='center'>\n");
					for (int i = 0; i < trcdlist.size(); i++) {
						trcdno = (String) trcdlist.get(i);
						for (int j = 0; j < tradecode.length; j++) {
							if (trcdno.equals(tradecode[j][0])) {
								outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>笔数</b></nobr></td>\n");
								outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>金额</b></nobr></td>\n");
								outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>手续费</b></nobr></td>\n");
							}
						}
					}
					outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>笔数</b></nobr></td>\n");
					outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>金额</b></nobr></td>\n");
					outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>手续费</b></nobr></td>\n");
					
					outstr.append("\t</tr>\n");
					
					
					// 表体
					int[] devTotalnum = new int[devnoList.size()];
					long[] devTotalprice = new long[devnoList.size()];
					long[] devTotalfees = new long[devnoList.size()];
					String tmpString1 = "";
					String tmpString2 = "";
					int tmplen = 0;
					for (int i = 0; i < devnoList.size(); i++) {
						outstr.append("\t<tr class='list_tr" + i % 2 + "' align='center'>\n");
						devno = (String) devnoList.get(i);
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + devno + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_title'>&nbsp;" + devaddr[i] + "</td>\n");
						for (int j = 0; j < trcdlist.size(); j++) {
							devTotalnum[i] += tableNumContent[i][j];
							devTotalprice[i] += tablePriceContent[i][j];
							devTotalfees[i] += feesContent[i][j];
							tmpString1 = String.valueOf(tablePriceContent[i][j]);
							tmplen = tmpString1.length();
							if (tmplen == 1) {
								tmpString2 = "0.0" + tmpString1;
							} else if (tmplen == 2) {
								tmpString2 = "0." + tmpString1;
							} else {
								tmpString2 = tmpString1.substring(0, tmplen - 2) + "." + tmpString1.substring(tmplen - 2);
							}
							outstr.append("\t\t<td class='list_td_title'>&nbsp;" + String.valueOf(tableNumContent[i][j]) + "</td>\n");
							outstr.append("\t\t<td class='list_td_title'>&nbsp;" + tmpString2 + "</td>\n");
							tmpString1 = String.valueOf(feesContent[i][j]);
							tmplen = tmpString1.length();
							if (tmplen <= len + 2) {
								tmpString2 = "0.";
								for (int k = 0; k < len + 2 - tmplen; k++) {
									tmpString2 += "0";
								}
								tmpString2 += tmpString1;
								
							} else {
								tmpString2 = tmpString1.substring(0, tmplen - len - 2) + "." + tmpString1.substring(tmplen - len - 2);
							}
							tmpString2 = tmpString2.substring(0, tmpString2.length() - len);
							outstr.append("\t\t<td class='list_td_title'>&nbsp;" + tmpString2 + "</td>\n");
						}
						outstr.append("\t\t<td class='list_td_title'>&nbsp;" + String.valueOf(devTotalnum[i]) + "</td>\n");
						tmpString1 = String.valueOf(devTotalprice[i]);
						tmplen = tmpString1.length();
						if (tmplen == 1) {
							tmpString2 = "0.0" + tmpString1;
						} else if (tmplen == 2) {
							tmpString2 = "0." + tmpString1;
						} else {
							tmpString2 = tmpString1.substring(0, tmplen - 2) + "." + tmpString1.substring(tmplen - 2);
						}
						outstr.append("\t\t<td class='list_td_title'>&nbsp;" + tmpString2 + "</td>\n");
						tmpString1 = String.valueOf(devTotalfees[i]);
						tmplen = tmpString1.length();
						if (tmplen <= len + 2) {
							tmpString2 = "0.";
							for (int k = 0; k < len + 2 - tmplen; k++) {
								tmpString2 += "0";
							}
							tmpString2 += tmpString1;
							
						} else {
							tmpString2 = tmpString1.substring(0, tmplen - len - 2) + "." + tmpString1.substring(tmplen - len - 2);
						}
						tmpString2 = tmpString2.substring(0, tmpString2.length() - len);
						outstr.append("\t\t<td class='list_td_title'>&nbsp;" + tmpString2 + "</td>\n");
						outstr.append("\t</tr>\n");
					}
					
					
					// 表脚
					int num = 0;
					long price = 0;
					long fee = 0;
					String tmpStr = "";
					outstr.append("\t<tr align='center'>\n");
					outstr.append("\t\t<td class='list_td_prom' ><b>合计</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' >&nbsp;</td>\n");
					for (int i = 0; i < trcdlist.size(); i++) {
						num += totalNum[i];
						price += totalPrice[i];
						fee += fees[i];
						
						tmpString1 = String.valueOf(totalPrice[i]);
						tmplen = tmpString1.length();
						if (tmplen == 1) {
							tmpString2 = "0.0" + tmpString1;
						} else if (tmplen == 2) {
							tmpString2 = "0." + tmpString1;
						} else {
							tmpString2 = tmpString1.substring(0, tmplen - 2) + "." + tmpString1.substring(tmplen - 2);
						}
						outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + String.valueOf(totalNum[i]) + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + tmpString2 + "</b></td>\n");
						tmpString1 = String.valueOf(fees[i]);
						tmplen = tmpString1.length();
						if (tmplen <= len + 2) {
							tmpString2 = "0.";
							for (int k = 0; k < len + 2 - tmplen; k++) {
								tmpString2 += "0";
							}
							tmpString2 += tmpString1;
							
						} else {
							tmpString2 = tmpString1.substring(0, tmplen - len - 2) + "." + tmpString1.substring(tmplen - len - 2);
						}
						tmpString2 = tmpString2.substring(0, tmpString2.length() - len);
						outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + tmpString2 + "</b></td>\n");
					}
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + String.valueOf(num) + "</b></td>\n");
					tmpString1 = String.valueOf(price);
					tmplen = tmpString1.length();
					if (tmplen == 1) {
						tmpString2 = "0.0" + tmpString1;
					} else if (tmplen == 2) {
						tmpString2 = "0." + tmpString1;
					} else {
						tmpString2 = tmpString1.substring(0, tmplen - 2) + "." + tmpString1.substring(tmplen - 2);
					}
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + tmpString2 + "</b></td>\n");
					tmpString1 = String.valueOf(fee);
					tmplen = tmpString1.length();
					if (tmplen <= len + 2) {
						tmpString2 = "0.";
						for (int k = 0; k < len + 2 - tmplen; k++) {
							tmpString2 += "0";
						}
						tmpString2 += tmpString1;
						
					} else {
						tmpString2 = tmpString1.substring(0, tmplen - len - 2) + "." + tmpString1.substring(tmplen - len - 2);
					}
					tmpString2 = tmpString2.substring(0, tmpString2.length() - len);
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + tmpString2 + "</b></td>\n");
					outstr.append("\t</tr>\n");
					
					outstr.append("</table>\n");
				}
				
				
				// 交易列表
				outstr.append("<br>\n");
				if (statmode.equals("all")) {
					outstr.append("您统计的是所有交易。\n");
				} else {
					statmode = "Type" + statmode + "_";
					String[][] trcdtypes = myPubUtil.Ini2Array("Trade.ini", "TradeType", statmode + "Num", statmode, "|");
					List trcdcode = new ArrayList();
					List trcdname = new ArrayList();
					String trcdstr = "";
					String trcdtmp = "";
					for (int i = 0; i < trcdtypes.length; i++) {
						trcdcode = new ArrayList();
						trcdcode.add(trcdtypes[i][0]);
						trcdstr = trcdtypes[i][1];
						for (int j = 0; j < trcdlist.size(); j++) {
							trcdtmp = (String) trcdlist.get(j);
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
						outstr.append("<br><b>" + tmplist.get(0) + "：</b>");
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
							trtmp = trtmp.replaceAll(",", "，");
						}
						outstr.append(trtmp);
						outstr.append("。<br>\n");
					}
				}
				
				
				// 打印按钮
				outstr.append("<p align='center'>\n");
				outstr.append("\t<OBJECT id='WebBrowser' classid='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2' height='0' width='0' VIEWASTEXT></OBJECT>\n");
				outstr.append("\t<input type='button' value=' 打  印 ' onclick='document.all.WebBrowser.ExecWB(6,1)' class='noprint'>\n");
				outstr.append("\t<input type='button' value='直接打印' onclick='document.all.WebBrowser.ExecWB(6,6)' class='noprint'>\n");
				outstr.append("\t<input type='button' value='页面设置' onclick='document.all.WebBrowser.ExecWB(8,1)' class='noprint'>\n");
				outstr.append("\t<input type='button' value='打印预览' onclick='document.all.WebBrowser.ExecWB(7,1)' class='noprint'>\n");
				outstr.append("\t<input type='button' value='导出Excel' onclick='javascript:PrintTableToExcelEx1(excel)' class='noprint'>\n");
				outstr.append("</p>\n");
				
				outstr.append("</body>\n");
				outstr.append("</html>\n");
				
				
				// 汉字转码
				// String OutStr = myPubUtil.unicode2native(outstr.toString());
				// FileOutputStream outStream = new FileOutputStream( filepath +
				// filename );
				// 记录文件
				// byte[] InputByte = OutStr.getBytes();
				// outStream.write( OutStr );
				// outStream.close()
				
				String OutStr = outstr.toString();
				String filename = "M_14_" + operid + "_" + DateTimeStr + ".htm";
				FileOutputStream outStream = new FileOutputStream(filepath + filename);
				OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "GB2312");
				
				outStreamWriter.write(OutStr);
				outStreamWriter.flush();
				outStreamWriter.close();
				// */
				
				// 更新系统任务表
				myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "14", "M", operid);
				myStaMission.setCurrentflag("0");
				myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
				
				if (!myStaMissionBean.updateStaMission(myStaMission)) {
					
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "14", "M", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				}
				log.info("交易手续费统计 年 结束");
			} catch (HibernateException he) {
				log.error("ERROR", he);
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "14", "M", operid);
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
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "14", "M", operid);
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