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
	 * SQL语句，用于统计
	 */
	private String HQLstr = null;
	
	
	
	/**
	 * Cookie中存储的操作员所属机构编号
	 */
	private String bankid = null;
	
	
	
	/**
	 * Cookie中存储的操作员编号2
	 */
	private String operid = null;
	
	
	
	/**
	 * 报表名称
	 */
	private String repnm = null;
	
	
	
	/**
	 * 生成统计报表的文件存储位置
	 */
	private String filepath = null;
	
	
	
	/**
	 * 设备编号类集
	 */
	private List devnoList = new ArrayList();
	
	
	
	/**
	 * 出卡日期
	 */
	private String outcarddate1 = null;
	
	
	
	/**
	 * 截止日期
	 */
	private String outcarddate2 = null;
	
	
	
	/**
	 * 开始时间
	 */
	private String outcardtime1 = null;
	
	
	
	/**
	 * 截止时间
	 */
	private String outcardtime2 = null;
	
	
	
	/**
	 * 统计出卡状态
	 */
	private String[] outcardstatus;
	
	
	
	/**
	 * 卡类型
	 */
	private String[] cardtype;
	
	
	
	
	/**
	 * 获得 统计出卡状态
	 * 
	 * @return the outcardstatus
	 */
	public String[] getOutcardstatus() {
		return outcardstatus;
	}
	
	
	
	/**
	 * 设置 统计出卡状态
	 * 
	 * @param outcardstatus
	 *            the outcardstatus to set
	 */
	public void setOutcardstatus(String[] outcardstatus) {
		this.outcardstatus = outcardstatus;
	}
	
	
	
	/**
	 * 默认构造函数
	 */
	public CardTjServer() {
		
	}
	
	
	
	/**
	 * 获得Cookie中存储的操作员所属机构编号
	 * 
	 * @return the bankid
	 */
	public String getBankid() {
		return bankid;
	}
	
	
	
	/**
	 * 设置 Cookie中存储的操作员所属机构编号
	 * 
	 * @param bankid
	 *            the bankid to set
	 */
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	
	
	
	/**
	 * 获得设备编号类集
	 * 
	 * @return the devnoList
	 */
	public List getDevnoList() {
		return devnoList;
	}
	
	
	
	/**
	 * 获得cardtype
	 * 
	 * @return the cardtype
	 */
	public String[] getCardtype() {
		return cardtype;
	}
	
	
	
	/**
	 * 设置 cardtype
	 * 
	 * @param cardtype
	 *            the cardtype to set
	 */
	public void setCardtype(String[] cardtype) {
		this.cardtype = cardtype;
	}
	
	
	
	/**
	 * 设置 设备编号类集
	 * 
	 * @param devnoList
	 *            the devnoList to set
	 */
	public void setDevnoList(List devnoList) {
		this.devnoList = devnoList;
	}
	
	
	
	/**
	 * 获得生成统计报表的文件存储位置
	 * 
	 * @return the filepath
	 */
	public String getFilepath() {
		return filepath;
	}
	
	
	
	/**
	 * 设置 生成统计报表的文件存储位置
	 * 
	 * @param filepath
	 *            the filepath to set
	 */
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	
	
	/**
	 * 获得SQL语句，用于统计
	 * 
	 * @return the hQLstr
	 */
	public String getHQLstr() {
		return HQLstr;
	}
	
	
	
	/**
	 * 设置 SQL语句，用于统计
	 * 
	 * @param lstr
	 *            the hQLstr to set
	 */
	public void setHQLstr(String lstr) {
		HQLstr = lstr;
	}
	
	
	
	/**
	 * 获得operid
	 * 
	 * @return the operid
	 */
	public String getOperid() {
		return operid;
	}
	
	
	
	/**
	 * 设置 operid
	 * 
	 * @param operid
	 *            the operid to set
	 */
	public void setOperid(String operid) {
		this.operid = operid;
	}
	
	
	
	/**
	 * 获得出卡日期
	 * 
	 * @return the outcarddate1
	 */
	public String getOutcarddate1() {
		return outcarddate1;
	}
	
	
	
	/**
	 * 设置 出卡日期
	 * 
	 * @param outcarddate1
	 *            the outcarddate1 to set
	 */
	public void setOutcarddate1(String outcarddate1) {
		this.outcarddate1 = outcarddate1;
	}
	
	
	
	/**
	 * 获得截至日期
	 * 
	 * @return the outcarddate2
	 */
	public String getOutcarddate2() {
		return outcarddate2;
	}
	
	
	
	/**
	 * 设置 截至日期
	 * 
	 * @param outcarddate2
	 *            the outcarddate2 to set
	 */
	public void setOutcarddate2(String outcarddate2) {
		this.outcarddate2 = outcarddate2;
	}
	
	
	
	/**
	 * 获得起始时间
	 * 
	 * @return the outcardtime1
	 */
	public String getOutcardtime1() {
		return outcardtime1;
	}
	
	
	
	/**
	 * 设置 起始时间
	 * 
	 * @param outcardtime1
	 *            the outcardtime1 to set
	 */
	public void setOutcardtime1(String outcardtime1) {
		this.outcardtime1 = outcardtime1;
	}
	
	
	
	/**
	 * 获得截止时间
	 * 
	 * @return the outcardtime2
	 */
	public String getOutcardtime2() {
		return outcardtime2;
	}
	
	
	
	/**
	 * 设置 截止时间
	 * 
	 * @param outcardtime2
	 *            the outcardtime2 to set
	 */
	public void setOutcardtime2(String outcardtime2) {
		this.outcardtime2 = outcardtime2;
	}
	
	
	
	/**
	 * 获得 报表名称
	 * 
	 * @return the repnm
	 */
	public String getRepnm() {
		return repnm;
	}
	
	
	
	/**
	 * 设置 报表名称
	 * 
	 * @param repnm
	 *            the repnm to set
	 */
	public void setRepnm(String repnm) {
		this.repnm = repnm;
	}
	
	
	
	/**
	 * 进入主线程
	 */
	public void run() {
		
		log.info("开始发卡统计");
		
		try {
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();
			
			List result = new ArrayList();
			
			
			// 用于计算发卡成功与失败
			int resultarray[][] = new int[devnoList.size()][2];
			// 用于存储名字
			String name[] = new String[devnoList.size()];
			
			float ttrnum = 0;
			float strnum = 0;
			
			
			// 获得当前时间
			String TimeStr = myPubUtil.getNowTime();
			TimeStr = myPubUtil.replace(TimeStr, ":", "");
			String DateTimeStr = myPubUtil.getNowDate(1) + TimeStr;
			
			try {
				// 年统计报表
				log.info("统计任务写入表");
				// 记录系统任务表 初始化StaMission相关的类
				StaMissionModel myStaMissionModel = new StaMissionModel();
				StaMissionBean myStaMissionBean = new StaMissionBean();
				StaMission myStaMission = new StaMission();
				
				
				// 设置统计任务开始的时间
				myStaMission.setTimeid(DateTimeStr);
				// 设置统计任务报表的名称
				myStaMission.setStatename(myCharSet.form2db(repnm));
				repnm = myCharSet.form2file(repnm);
				// 设置统计所属机构
				myStaMission.setBankid(bankid);
				// 设置统计任务操作员编号
				myStaMission.setOwnerid(operid);
				// 报表分类01：交易统计 02：设备总体运行情况统计 03：设备故障统计
				myStaMission.setStatesort("21");
				// 报表产生类型：M：手动 A：自动
				myStaMission.setCreatetype("M");
				// 任务当前状态 0：结束 1：正在执行 2：失败
				myStaMission.setCurrentflag("1");
				// 备注123
				myStaMission.setRemark1("");
				myStaMission.setRemark2("");
				myStaMission.setRemark3("");
				// 将统计任务写入到数据库
				myStaMissionBean.addStaMission(myStaMission);
				
				
				// 执行查询
				CardTjBean cardtjbean = new CardTjBean();
				// 实例化发卡流水
				CardOut acardout = new CardOut();
				
				
				// 处理大量的设备编号的时候，将分批次进行查询
				int p1 = HQLstr.indexOf("(");
				int p2 = HQLstr.indexOf(")");
				String firstpart = HQLstr.substring(0, p1);
				String middlepart = HQLstr.substring(p1 + 1, p2) + "or";
				String endpart = HQLstr.substring(p2 + 1, HQLstr.length());
				
				int pernum = devnoList.size() / 150 + 1;
				// System.out.println("devList的长度是："+devnoList.size());
				
				for (int i = 0; i < pernum; i++) {
					middlepart = "devno in (  ";
					for (int j = 0; j < 150 && (i * 150 + j) < devnoList.size(); j++) {
						middlepart += "'" + (String) devnoList.get(i * 150 + j) + "',";
					}
					middlepart = middlepart.substring(0, middlepart.length() - 1) + ")";
					HQLstr = firstpart + middlepart + endpart;
					result.addAll((List) cardtjbean.getQueryList(HQLstr));
					
				}
				
				log.info("获得统计信息");
				
				
				// 取出查询后的结果，进行统计计算,在二维数组中存储
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
				
				log.info("开始生产统计结果");
				// 整理输出流  402030001
				StringBuffer outstr = new StringBuffer("");
				outstr.append("<html>\n");
				outstr.append("<head>\n");
				outstr.append("\t<title>出卡状态记录统计报表</title>\n");
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
				outstr.append("\t\t\t<font color=blue>PowerView XP </font>--- <font class='location'>出卡状态记录统计报表</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td>\n");
				outstr.append("\t\t\t<font class='location'>" + repnm + "</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td align='right'>\n");
				outstr.append("\t\t\t<font class='location'>\n");
				if (!outcarddate1.equals(outcarddate2)) {
					outstr.append("\t\t\t\t" + outcarddate1.substring(0, 4) + "年" + outcarddate1.substring(4, 6) + "月" + outcarddate1.substring(6, 8) + "日");
					outstr.append("---" + outcarddate2.substring(0, 4) + "年" + outcarddate2.substring(4, 6) + "月" + outcarddate2.substring(6, 8) + "日&nbsp;\n");
					outstr.append("\t\t\t</font>\n");
					outstr.append("\t\t</td>\n");
					outstr.append("\t</tr>\n");
					
					outstr.append("\t<tr>\n");
					outstr.append("\t\t<td align='left' valign='center' width='30' height='40'>\n");
					outstr.append("\t\t\t&nbsp;\n");
					outstr.append("\t\t</td>\n");
					outstr.append("\t\t<td>\n");
					outstr.append("\t\t\t<font class='location'  color='blue'>统计条件：</font>\n");
					outstr.append("\t\t</td>\n");
					outstr.append("\t\t<td><font class='location' color='red' >卡类型:</font> \n");
					for (int i = 0; i < cardtype.length; i++) {
						if (cardtype[i].trim().equals("1")) {
							outstr.append("\t\t\t<font class='location'>借记卡</font>\n");
						} else if (cardtype[i].trim().equals("2")) {
							outstr.append("\t\t\t<font class='location'>信用卡</font>\n");
						} else {
							outstr.append("\t\t\t<font class='location'>其他卡种</font>\n");
						}
					}
					
					outstr.append("\t\t\t&nbsp;&nbsp;<font class='location'  color='red'>统计出卡状态:</font>\n");

					for (int i = 0; i < outcardstatus.length; i++) {
						if (outcardstatus[i].trim().equals("1")) {
							outstr.append("\t\t\t<font class='location'>发卡成功</font>\n");
						} else if (outcardstatus[i].trim().equals("0")) {
							outstr.append("\t\t\t<font class='location'>发卡失败</font>\n");
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

						// 表头
						outstr.append("\t<tr align='center'>\n");
						if (outcardstatus.length == 2) {
							outstr.append("\t\t<td width='20%' class='list_td_prom'><b>设备编号</b></td>\n");
							outstr.append("\t\t<td width='20%' class='list_td_prom'><b>出卡成功次数</b></td>\n");
							outstr.append("\t\t<td width='20%' class='list_td_prom'><b>出卡失败次数</b></td>\n");
							outstr.append("\t\t<td width='20%' class='list_td_prom'><b>出卡成功率</b></td>\n");
							outstr.append("\t\t<td width='20%' class='list_td_prom'><b>出卡失败率</b></td>\n");
						} else if (outcardstatus.length == 1 && outcardstatus[0].equals("1")) {
							outstr.append("\t\t<td width='10%'  class='list_td_prom'><b>设备编号</b></td>\n");
							outstr.append("\t\t<td width='10%'  class='list_td_prom'><b>出卡成功次数</b></td>\n");
							outstr.append("\t\t<td width='25%'  class='list_td_prom'><b>出卡成功率</b></td>\n");
						} else if (outcardstatus.length == 1 && outcardstatus[0].equals("0")) {
							outstr.append("\t\t<td width='10%' class='list_td_prom'><b>设备编号</b></td>\n");
							outstr.append("\t\t<td width='25%' class='list_td_prom'><b>出卡失败次数</b></td>\n");
							outstr.append("\t\t<td width='25%' class='list_td_prom'><b>出卡失败率</b></td>\n");
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
				
				
				// 计算合计
				int sum1 = 0;
				int sum0 = 0;
				
				for (int i = 0; i < resultarray.length; i++) {
					sum1 += resultarray[i][0];
					sum0 += resultarray[i][1];
				}
				
				
				// 统计合计失败率
				if (outcardstatus.length == 2) {
					if (sum1 + sum0 == 0) {
						ttrnum = 0;
					} else
						ttrnum = (float) sum0 / (float) (sum1 + sum0);
					    strnum = (float) sum1 / (float) (sum1 + sum0);
					// 表脚
					outstr.append("\t<tr align='center' >\n");
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>合计</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + sum1 + "</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + sum0 + "</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + strnum * 100 + "%</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + ttrnum * 100 + "%</b></td>\n");
					outstr.append("\t</tr>\n");
					outstr.append("</table>\n");

				}
				
				// 统计合计成功率
				else if (outcardstatus.length == 1 && outcardstatus[0].equals("1")) {
					if (sum1 + sum0 == 0) {
						ttrnum = 0;
					} else
						ttrnum = (float) sum1 / (float) (sum1 + sum0);
					// 表脚
					outstr.append("\t<tr>\n");
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>合计</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + sum1 + "</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + ttrnum * 100 + "%</b></td>\n");
					outstr.append("\t</tr>\n");
					outstr.append("</table>\n");
				}
				// 统计合计失败率
				else if (outcardstatus.length == 1 && outcardstatus[0].equals("0")) {
					if (sum1 + sum0 == 0) {
						ttrnum = 0;
					} else
						ttrnum = (float) sum0 / (float) (sum1 + sum0);
					// 表脚
					outstr.append("\t<tr>\n");
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>合计</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + sum0 + "</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' align='center'>&nbsp;<b>" + ttrnum * 100 + "%</b></td>\n");
					outstr.append("\t</tr>\n");
					outstr.append("</table>\n");
					
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
				
				log.info("文档内容生产完毕，开始生产文件");
				String OutStr = outstr.toString();
				String filename = "M_21_" + operid + "_" + DateTimeStr + ".htm";
				log.info("filename="+filename);
				FileOutputStream outStream = new FileOutputStream(filepath + filename);
				log.info("文件名字="+filepath + filename);
				OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "GB2312");
				log.info("生产文件开始");
				
				outStreamWriter.write(OutStr);
				outStreamWriter.flush();
				outStreamWriter.close();
				log.info("生产文件成功");
				
				// 更新系统任务表
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
				log.error("统计失败", he);
				// 报错后，返回为统计失败
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "21", "M", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				} catch (Exception e) {
					log.error("统计失败", e);
				}
				
			} catch (Exception ex) {
				log.error("统计失败", ex);
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "21", "M", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				} catch (Exception e) {
					log.error("统计失败", e);
				}
			}
		} catch (Exception e) {
			log.error("处理失败", e);
		}
	}
	
}