package com.lcjr.pvxp.util;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.bean.*;
import com.lcjr.pvxp.model.*;

import net.sf.hibernate.*;

/**
 * 
 * 交易统计(按类型) 年报
 * 
 * @author 武坤鹏
 * 
 * 时间：20120327
 * 
 */
public class JytjTypeYearServer extends Thread {
	
	Logger log = Logger.getLogger("web.lcjr.pvxp.util.JytjTypeYearServer.java");
	
	
	private String HQLstr = null;
	
	
	private String bankid = null;
	
	
	private String operid = null;
	
	
	private String repnm = null;
	
	
	private String qbegin = null;
	
	
	private String qend = null;
	
	
	private String moneytype = null;
	
	
	private String filepath = null;
	
	
	private String statmode = null;
	
	
	private String uflag = null;
	
	
	private List trcdlist = new ArrayList();
	
	
	private List typelist = new ArrayList();
	
	
	
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
	
	
	public String getMoneyType() {
		return (this.moneytype);
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
	
	
	public List getTypelist() {
		return (this.typelist);
	}
	
	
	public String getUflag() {
		return (this.uflag);
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
	
	
	public void setMoneyType(String moneytype) {
		this.moneytype = moneytype;
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
	
	
	public void setTypelist(List typelist) {
		this.typelist = typelist;
	}
	
	
	public void setUflag(String uflag) {
		this.uflag = uflag;
	}
	
	
	public JytjTypeYearServer() {
		
	}
	
	
	public void run() {
		// 进入主线程 交易统计(按类型) 年报
		
		log.info("交易统计(按类型) 年 开始");
		
		try {
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();
			
			List result = new ArrayList();
			List bizhArray = new ArrayList();
			List rateArray = new ArrayList();
			List typenameList = new ArrayList();
			
			if (statmode.equals("all")) {
				int typenums = Integer.parseInt(myPubUtil.ReadConfig("TradeCode", "TCode_Num", "0", "Trade.ini"));
				
				for (int i = 1; i <= typenums; i++) {
					String tmpstring1 = myPubUtil.ReadConfig("TradeCode", "TCode_" + i, "0", "Trade.ini");
					
					int tmppos1 = tmpstring1.indexOf(",");
					String tmpstring2 = tmpstring1.substring(0, tmppos1);
					trcdlist.add(tmpstring2);
				}
				trcdlist = trcdlist.subList(trcdlist.size() - typenums, trcdlist.size());
				
			}
			
			int[] totalNum = new int[trcdlist.size()];
			long[] totalPrice = new long[trcdlist.size()];
			int[][] tableNumContent = new int[typelist.size()][trcdlist.size()];
			long[][] tablePriceContent = new long[typelist.size()][trcdlist.size()];
			for (int i = 0; i < typelist.size(); i++) {
				for (int j = 0; j < trcdlist.size(); j++) {
					tableNumContent[i][j] = 0;
					tablePriceContent[i][j] = 0;
				}
			}
			
			for (int i = 0; i < trcdlist.size(); i++) {
				totalPrice[i] = 0;
			}
			
			String bizh = "";
			String typeno = "";
			String trcdno = "";
			String moneyno = "";
			String typename = "";
			String devno = "";
			String typeid = "";
			String tmppr1 = "";
			String tmppr2 = "";
			long ftmp1 = 0;
			long ftmp2 = 0;
			int strnum = 0;
			int iflag = 0;
			int pos = 0;
			
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
				myStaMission.setStatesort("11");
				myStaMission.setCreatetype("M");
				myStaMission.setCurrentflag("1");
				myStaMission.setRemark1("");
				myStaMission.setRemark2("");
				myStaMission.setRemark3("");
				
				myStaMissionBean.addStaMission(myStaMission);
				
				DevtypeModel myDevtypeModel = new DevtypeModel();
				List myDevtypeList = myDevtypeModel.getDevTpList();
				if (myDevtypeList != null && !myDevtypeList.isEmpty()) {
					int typenum = myDevtypeList.size();
					
					for (int i = 0; i < typenum; i++) {
						Devtype tmptype = (Devtype) myDevtypeList.get(i);
						for (int j = 0; j < typelist.size(); j++) {
							typeno = (String) typelist.get(j);
							if ((myPubUtil.dealNull(tmptype.getTypeno()).trim()).equals(typeno)) {
								typename = myCharSet.db2web(myPubUtil.dealNull(tmptype.getDevname()).trim());
								typenameList.add(typename);
							}
						}
					}
				}
				
				DevinfoModel myDevinfoModel = new DevinfoModel();
				List myDevList = myDevinfoModel.getDevList();
				List trcddevnum = new ArrayList();
				int devnum = 0;
				Devinfo tmp = null;
				
				if (myDevList != null && !myDevList.isEmpty()) {
					devnum = myDevList.size();
				}
				
				
				// 执行查询
				TrcdtjyearBean myTjYearBean = new TrcdtjyearBean();
				Trcdtjyear myTjYear = new Trcdtjyear();
				
				result = (List) myTjYearBean.getQueryList(HQLstr);
				
				
				// 取出币种
				/*
				 * for( int i = 0; i < result.size(); i++ ) { myTjDay =
				 * (Trcdtjday)result.get(i); bizh =
				 * myTjDay.getMoneytype().trim();
				 * 
				 * if(bizh.equals("")||bizh==null) bizh = "001";
				 * 
				 * iflag = 0; for( int j = 0; j < bizhArray.size(); j++ ) { if(
				 * bizh.equals( bizhArray.get(j) ) ) { iflag = 1; break; } } if(
				 * iflag == 0 ) { System.out.println("bizh="+bizh);
				 * bizhArray.add( bizh ); } }
				 */

				// 计算每种设备类型各类交易的交易量和金额
				for (int i = 0; i < result.size(); i++) {
					myTjYear = (Trcdtjyear) result.get(i);
					for (int j = 0; j < typelist.size(); j++) {
						typeno = (String) typelist.get(j);
						for (int k = 0; k < devnum; k++) {
							tmp = (Devinfo) myDevList.get(k);
							if (typeno.equals(myPubUtil.dealNull(tmp.getTypeno()).trim())) {
								devno = myPubUtil.dealNull(tmp.getDevno()).trim();
								if (devno.equals(myTjYear.getDevno().trim())) {
									for (int x = 0; x < trcdlist.size(); x++) {
										trcdno = (String) trcdlist.get(x);
										if (trcdno.equals(myTjYear.getTrcdtype().trim())) {
											strnum = Integer.parseInt(myTjYear.getStrnum().trim());
											tableNumContent[j][x] += strnum;
											totalNum[x] += strnum;
											
											tmppr1 = myTjYear.getStrsum().trim();
											if (tmppr1 != null) {
												pos = tmppr1.indexOf(".");
												if (pos != -1) {
													tmppr2 = tmppr1.substring(0, pos) + tmppr1.substring(pos + 1, pos + 3);
												} else {
													tmppr2 = tmppr1 + "00";
												}
												
												ftmp1 = Long.parseLong(tmppr2);
												
												tablePriceContent[j][x] += ftmp1;
												totalPrice[x] += ftmp1;
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
				outstr.append("\t<title>交易统计(按类型)</title>\n");
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
				outstr.append("\t\t\t<font color=blue>PowerView XP </font>--- <font class='location'>设备类型交易统计</font>\n");
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
				// if( typenoArray.size() == 0 ){
				if (typelist.size() == 0) {
					outstr.append("There is no transaction on type you selected, so that is no Statistics.\n");
				} else {
					outstr.append("<table id='excel' width='100%' cellspacing='0' cellpadding='0' class='list_table_border'>\n");
					// 表头
					if (uflag.equals("1")) {
						outstr.append("\t<tr align='center'>\n");
						outstr.append("\t\t<td class='list_td_prom'><b>设备类型</b></td>\n");
						String[][] tradecode = myPubUtil.Ini2Array("ini", "Trade.ini", "TradeCode", "TCode_Num", "TCode_", ",", "");
						for (int i = 0; i < trcdlist.size(); i++) {
							trcdno = (String) trcdlist.get(i);
							for (int j = 0; j < tradecode.length; j++) {
								String trcdnotmp = tradecode[j][0];
								if (trcdno.equals(tradecode[j][0])) {
									outstr.append("\t\t<td class='list_td_prom'><b>" + tradecode[j][1] + "</b></td>\n");
								}
							}
						}
						outstr.append("\t\t<td class='list_td_prom'><b>合计</b></td>\n");
						outstr.append("\t</tr>\n");
						
						
						// 表体
						int[] typeTotalnum = new int[typelist.size()];
						String tmpString = "";
						int totaldev = 0;
						int trcddev = 0;
						for (int i = 0; i < typelist.size(); i++) {
							outstr.append("\t<tr class='list_tr" + i % 2 + "' align='center'>\n");
							typename = (String) typenameList.get(i);
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + typename + "</b></td>\n");
							for (int j = 0; j < trcdlist.size(); j++) {
								typeTotalnum[i] += tableNumContent[i][j];
								outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + String.valueOf(tableNumContent[i][j]) + "</b></td>\n");
							}
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + String.valueOf(typeTotalnum[i]) + "</b></td>\n");
							outstr.append("\t</tr>\n");
						}
						
						
						// 表脚
						int num = 0;
						long price = 0;
						String tmpStr = "";
						outstr.append("\t<tr align='center'>\n");
						outstr.append("\t\t<td class='list_td_prom' ><b>合计</b></td>\n");
						for (int i = 0; i < trcdlist.size(); i++) {
							num += totalNum[i];
							outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + String.valueOf(totalNum[i]) + "</b></td>\n");
						}
						outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + String.valueOf(num) + "</b></td>\n");
						
						outstr.append("\t</tr>\n");
					} else if (uflag.equals("2")) {
						outstr.append("\t<tr align='center'>\n");
						outstr.append("\t\t<td class='list_td_prom'><b>设备类型</b></td>\n");
						String[][] tradecode = myPubUtil.Ini2Array("ini", "Trade.ini", "TradeCode", "TCode_Num", "TCode_", ",", "");
						for (int i = 0; i < trcdlist.size(); i++) {
							trcdno = (String) trcdlist.get(i);
							for (int j = 0; j < tradecode.length; j++) {
								String trcdnotmp = tradecode[j][0];
								if (trcdno.equals(tradecode[j][0])) {
									outstr.append("\t\t<td class='list_td_prom'><b>" + tradecode[j][1] + "</b></td>\n");
								}
							}
						}
						outstr.append("\t\t<td class='list_td_prom'><b>合计</b></td>\n");
						outstr.append("\t</tr>\n");
						
						
						// 表体
						long[] typeTotalprice = new long[typelist.size()];
						String tmpString1 = "";
						String tmpString2 = "";
						int tmplen = 0;
						int totaldev = 0;
						int trcddev = 0;
						for (int i = 0; i < typelist.size(); i++) {
							outstr.append("\t<tr class='list_tr" + i % 2 + "' align='center'>\n");
							typename = (String) typenameList.get(i);
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + typename + "</b></td>\n");
							for (int j = 0; j < trcdlist.size(); j++) {
								typeTotalprice[i] += tablePriceContent[i][j];
								tmpString1 = String.valueOf(tablePriceContent[i][j]);
								tmplen = tmpString1.length();
								if (tmplen == 1) {
									tmpString2 = "0.0" + tmpString1;
								} else if (tmplen == 2) {
									tmpString2 = "0." + tmpString1;
								} else {
									tmpString2 = tmpString1.substring(0, tmplen - 2) + "." + tmpString1.substring(tmplen - 2);
								}
								outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + tmpString2 + "</b></td>\n");
							}
							tmpString1 = String.valueOf(typeTotalprice[i]);
							tmplen = tmpString1.length();
							if (tmplen == 1) {
								tmpString2 = "0.0" + tmpString1;
							} else if (tmplen == 2) {
								tmpString2 = "0." + tmpString1;
							} else {
								tmpString2 = tmpString1.substring(0, tmplen - 2) + "." + tmpString1.substring(tmplen - 2);
							}
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + tmpString2 + "</b></td>\n");
							outstr.append("\t</tr>\n");
						}
						
						
						// 表脚
						long price = 0;
						outstr.append("\t<tr align='center'>\n");
						outstr.append("\t\t<td class='list_td_prom' ><b>合计</b></td>\n");
						for (int i = 0; i < trcdlist.size(); i++) {
							
							price += totalPrice[i];
							tmpString1 = String.valueOf(totalPrice[i]);
							tmplen = tmpString1.length();
							if (tmplen == 1) {
								tmpString2 = "0.0" + tmpString1;
							} else if (tmplen == 2) {
								tmpString2 = "0." + tmpString1;
							} else {
								tmpString2 = tmpString1.substring(0, tmplen - 2) + "." + tmpString1.substring(tmplen - 2);
							}
							outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + tmpString2 + "</b></td>\n");
						}
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
						
						outstr.append("\t</tr>\n");
					} else if (uflag.equals("3")) {
						outstr.append("\t<tr align='center'>\n");
						outstr.append("\t\t<td class='list_td_prom' rowspan='2'><b>设备类型</b></td>\n");
						String[][] tradecode = myPubUtil.Ini2Array("ini", "Trade.ini", "TradeCode", "TCode_Num", "TCode_", ",", "");
						for (int i = 0; i < trcdlist.size(); i++) {
							trcdno = (String) trcdlist.get(i);
							for (int j = 0; j < tradecode.length; j++) {
								String trcdnotmp = tradecode[j][0];
								if (trcdno.equals(tradecode[j][0])) {
									outstr.append("\t\t<td colspan='2' class='list_td_prom'><b>" + tradecode[j][1] + "</b></td>\n");
								}
							}
						}
						outstr.append("\t\t<td colspan='2' class='list_td_prom'><b>合计</b></td>\n");
						outstr.append("\t</tr>\n");
						
						outstr.append("\t<tr align='center'>\n");
						for (int i = 0; i < trcdlist.size(); i++) {
							trcdno = (String) trcdlist.get(i);
							for (int j = 0; j < tradecode.length; j++) {
								String trcdnotmp = tradecode[j][0];
								if (trcdno.equals(tradecode[j][0])) {
									outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>笔数</b></nobr></td>\n");
									outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>金额</b></nobr></td>\n");
								}
							}
						}
						outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>笔数</b></nobr></td>\n");
						outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>金额</b></nobr></td>\n");
						
						outstr.append("\t</tr>\n");
						
						
						// 表体
						int[] typeTotalnum = new int[typelist.size()];
						long[] typeTotalprice = new long[typelist.size()];
						String tmpString1 = "";
						String tmpString2 = "";
						int totaldev = 0;
						int trcddev = 0;
						int tmplen = 0;
						for (int i = 0; i < typelist.size(); i++) {
							outstr.append("\t<tr class='list_tr" + i % 2 + "' align='center'>\n");
							typename = (String) typenameList.get(i);
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + typename + "</b></td>\n");
							for (int j = 0; j < trcdlist.size(); j++) {
								
								typeTotalnum[i] += tableNumContent[i][j];
								typeTotalprice[i] += tablePriceContent[i][j];
								tmpString1 = String.valueOf(tablePriceContent[i][j]);
								tmplen = tmpString1.length();
								if (tmplen == 1) {
									tmpString2 = "0.0" + tmpString1;
								} else if (tmplen == 2) {
									tmpString2 = "0." + tmpString1;
								} else {
									tmpString2 = tmpString1.substring(0, tmplen - 2) + "." + tmpString1.substring(tmplen - 2);
								}
								outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + String.valueOf(tableNumContent[i][j]) + "</b></td>\n");
								outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + tmpString2 + "</b></td>\n");
							}
							tmpString1 = String.valueOf(typeTotalprice[i]);
							tmplen = tmpString1.length();
							if (tmplen == 1) {
								tmpString2 = "0.0" + tmpString1;
							} else if (tmplen == 2) {
								tmpString2 = "0." + tmpString1;
							} else {
								tmpString2 = tmpString1.substring(0, tmplen - 2) + "." + tmpString1.substring(tmplen - 2);
							}
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + String.valueOf(typeTotalnum[i]) + "</b></td>\n");
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + tmpString2 + "</b></td>\n");
							outstr.append("\t</tr>\n");
						}
						
						
						// 表脚
						int num = 0;
						long price = 0;
						String tmpStr = "";
						outstr.append("\t<tr align='center'>\n");
						outstr.append("\t\t<td class='list_td_prom' ><b>合计</b></td>\n");
						for (int i = 0; i < trcdlist.size(); i++) {
							
							num += totalNum[i];
							price += totalPrice[i];
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
						}
						tmpString1 = String.valueOf(price);
						tmplen = tmpString1.length();
						if (tmplen == 1) {
							tmpString2 = "0.0" + tmpString1;
						} else if (tmplen == 2) {
							tmpString2 = "0." + tmpString1;
						} else {
							tmpString2 = tmpString1.substring(0, tmplen - 2) + "." + tmpString1.substring(tmplen - 2);
						}
						outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + String.valueOf(num) + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + tmpString2 + "</b></td>\n");
						
						outstr.append("\t</tr>\n");
					}
					
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
				
				String OutStr = outstr.toString();
				String filename = "M_11_" + operid + "_" + DateTimeStr + ".htm";
				FileOutputStream outStream = new FileOutputStream(filepath + filename);
				OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "GB2312");
				
				outStreamWriter.write(OutStr);
				outStreamWriter.flush();
				outStreamWriter.close();
				
				
				// 更新系统任务表
				myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "11", "M", operid);
				myStaMission.setCurrentflag("0");
				myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
				
				if (!myStaMissionBean.updateStaMission(myStaMission)) {
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "11", "M", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				}
				log.info("交易统计(按类型) 年 结束");
			} catch (HibernateException he) {
				log.error("ERROR", he);
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "11", "M", operid);
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
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "11", "M", operid);
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