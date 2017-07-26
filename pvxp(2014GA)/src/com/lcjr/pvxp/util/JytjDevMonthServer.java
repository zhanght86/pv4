package com.lcjr.pvxp.util;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;

import com.lcjr.pvxp.bean.StaMissionBean;
import com.lcjr.pvxp.bean.TrcdtjmonthBean;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.DevtypeModel;
import com.lcjr.pvxp.model.StaMissionModel;
import com.lcjr.pvxp.orm.Bankinfo;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.orm.Devtype;
import com.lcjr.pvxp.orm.StaMission;
import com.lcjr.pvxp.orm.Trcdtjmonth;

/**
 * 
 * 交易统计(按设备) 月报
 * 
 * @author 武坤鹏
 * 
 * @time 2012-03-26
 */
public class JytjDevMonthServer extends Thread {

	Logger log = Logger.getLogger(JytjDevMonthServer.class.getName());

	private String bankid = null;

	private String operid = null;

	private String repnm = null;

	private String qbegin = null;

	private String qend = null;

	private String moneytype = null;

	private String filepath = null;

	private String statmode = "";

	private List trcdlist = new ArrayList();

	private List devnoList = new ArrayList();

	private String HQLstr = "";

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

	public List getDevnoList() {
		return (this.devnoList);
	}

	/**
	 * @return the hQLstr
	 */
	public String getHQLstr() {
		return HQLstr;
	}

	/**
	 * @param lstr
	 *            the hQLstr to set
	 */
	public void setHQLstr(String lstr) {
		HQLstr = lstr;
	}

	/**
	 * @return the moneytype
	 */
	public String getMoneytype() {
		return moneytype;
	}

	/**
	 * @param moneytype
	 *            the moneytype to set
	 */
	public void setMoneytype(String moneytype) {
		this.moneytype = moneytype;
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

	public void setDevnoList(List devnoList) {
		this.devnoList = devnoList;
	}

	/**
	 * 构造函数
	 * 
	 */
	public JytjDevMonthServer() {

	}

	/**
	 * 进入主线程
	 */
	public void run() {
		// 交易统计(按设备) 月报
		log.info("交易统计(按设备) 月 开始");

		try {
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();

			List result = new ArrayList();
			List bizhArray = new ArrayList();
			List rateArray = new ArrayList();

			int[][] tableNumContent = new int[devnoList.size()][trcdlist.size()];
			// wukp1128 double[][] tablePriceContent = new
			// double[devnoList.size()][trcdlist .size()];
			long[][] tablePriceContent = new long[devnoList.size()][trcdlist.size()];

			for (int i = 0; i < devnoList.size(); i++) {
				for (int j = 0; j < trcdlist.size(); j++) {
					tableNumContent[i][j] = 0;
					tablePriceContent[i][j] = 0;
				}
			}

			int[] totalNum = new int[trcdlist.size()];
			// wukp1128 double[] totalPrice = new double[trcdlist.size()];
			long[] totalPrice = new long[trcdlist.size()];

			String bizh = "";
			String devno = "";
			String trcdno = "";
			String moneyno = "";
			String tmpHQLstr = "";
			// wukp1128 double ftmp1 = 0.00;
			// wukp1128 double ftmp2 = 0.00;
			long ftmp1 = 0;
			long ftmp2 = 0;

			int strnum = 0;

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

			String TimeStr = myPubUtil.getNowTime();
			TimeStr = myPubUtil.replace(TimeStr, ":", "");
			String DateTimeStr = myPubUtil.getNowDate(1) + TimeStr;

			try {
				// 月统计报表

				// 记录系统任务表
				StaMissionModel myStaMissionModel = new StaMissionModel();
				StaMissionBean myStaMissionBean = new StaMissionBean();
				StaMission myStaMission = new StaMission();

				myStaMission.setTimeid(DateTimeStr);
				myStaMission.setStatename(myCharSet.form2db(repnm));
				repnm = myCharSet.form2file(repnm);
				myStaMission.setBankid(bankid);
				myStaMission.setOwnerid(operid);
				myStaMission.setStatesort("05");
				myStaMission.setCreatetype("M");
				myStaMission.setCurrentflag("1");
				myStaMission.setRemark1("");
				myStaMission.setRemark2("");
				myStaMission.setRemark3("");

				myStaMissionBean.addStaMission(myStaMission);

				// 币种类型、汇率
				String moneytypename = myPubUtil.ReadConfig("MoneyType", "Type" + moneytype + "_Name", "人民币", "MoneyType.ini");
				int moneytypenum = Integer.parseInt(myPubUtil.ReadConfig("MoneyType", "Type" + moneytype + "_Num", "0", "MoneyType.ini"));

				for (int i = 1; i <= moneytypenum; i++) {
					String tmp = myPubUtil.ReadConfig("MoneyType", "Type" + moneytype + "_" + i, "0", "MoneyType.ini");
					int pos1 = tmp.indexOf(",");
					int pos2 = tmp.lastIndexOf(",");
					rateArray.add(tmp.substring(pos2 + 1));
					bizhArray.add(tmp.substring(pos1 + 1, pos2));
				}

				// 执行查询
				TrcdtjmonthBean myTjMonthBean = new TrcdtjmonthBean();
				Trcdtjmonth myTjMonth = new Trcdtjmonth();

				// 计算每台设备各类交易的交易量和金额
				// 处理大量设备编号代码
				// 分批次进行查询
				// 将结果添加到类集reault中

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

				for (int i = 0; i < result.size(); i++) {

					myTjMonth = (Trcdtjmonth) result.get(i);

					for (int j = 0; j < devnoList.size(); j++) {
						devno = (String) devnoList.get(j);
						if (devno.equals(myTjMonth.getDevno().trim())) {
							for (int x = 0; x < trcdlist.size(); x++) {
								trcdno = (String) trcdlist.get(x);
								if (trcdno.equals(myTjMonth.getTrcdtype().trim())) {
									strnum = Integer.parseInt(myTjMonth.getStrnum().trim());
									tableNumContent[j][x] += strnum;
									totalNum[x] += strnum;

									moneyno = myTjMonth.getMoneytype().trim();

									if (moneyno == null || moneyno.length() == 0)
										moneyno = "001";

									int pos = moneyno.indexOf("|");
									if (pos != -1) {
										moneyno = moneyno.substring(0, pos);
									}

									for (int y = 0; y < bizhArray.size(); y++) {
										bizh = (String) bizhArray.get(y);
										if (bizh.equals(moneyno)) {
											try {
												ftmp1 = myPubUtil.str2long(myTjMonth.getStrsum().trim());
												// System.out.println("转换前
												// "+myTjMonth.getStrsum().trim()+"
												// 转换后"+ftmp1);
												ftmp2 = myPubUtil.str2long((String) rateArray.get(y));
												// System.out.println("转换前
												// "+(String) rateArray
												// .get(y)+" 转换后"+ftmp2);

											} catch (NumberFormatException e) {
												System.out.println(e);
											}
											// wukp1128 两个长整型相乘，结果扩大10000倍
											tablePriceContent[j][x] += ftmp1 * ftmp2;
											totalPrice[x] += ftmp1 * ftmp2;
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
				outstr.append("\t<title>交易统计(按设备)</title>\n");
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
				outstr.append("\t\t\t<font color=blue>PowerView XP </font>--- <font class='location'>交易统计</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td>\n");
				outstr.append("\t\t\t<font class='location'>" + repnm + "</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td align='right'>\n");
				outstr.append("\t\t\t<font class='location'>(月报)\n");
				if (!qbegin.equals(qend)) {
					outstr.append("\t\t\t\t" + qbegin.substring(0, 4) + "年" + Integer.parseInt(qbegin.substring(4, 6)) + "月");
					outstr.append("---" + qend.substring(0, 4) + "年" + Integer.parseInt(qend.substring(4, 6)) + "月&nbsp;&nbsp;\n");
				} else {
					outstr.append("\t\t\t\t" + qend.substring(0, 4) + "年" + Integer.parseInt(qend.substring(4, 6)) + "月&nbsp;&nbsp;\n");
				}

				outstr.append("\t\t\t\t&nbsp;&nbsp;币种：" + moneytypename + "&nbsp;\n");

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
					int sortnum = 1;
					for (int i = 1; i <= sortnum; i++) {
						typenum = Integer.parseInt(myPubUtil.ReadConfig("TradeType", "Type" + i + "_Num", "0", "Trade.ini"));
						totalsortnum += typenum;
					}

					int[][] sortnumContent = new int[devnoList.size()][totalsortnum];
					// wukp1128 double[][] sortpriceContent = new
					// double[devnoList.size()][totalsortnum];
					long[][] sortpriceContent = new long[devnoList.size()][totalsortnum];

					for (int i = 0; i < devnoList.size(); i++) {
						for (int j = 0; j < totalsortnum; j++) {
							sortnumContent[i][j] = 0;
							sortpriceContent[i][j] = 0;
						}
					}

					int ttnum = 0;
					String[] sortname = new String[totalsortnum];
					int[] sortTotalnum = new int[totalsortnum];
					// wukp1128 double[] sortTotalprice = new
					// double[totalsortnum];
					long[] sortTotalprice = new long[totalsortnum];

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
					String[][] tradecode = myPubUtil.Ini2Array("ini", "Trade.ini", "TradeCode", "TCode_Num", "TCode_", ",", "");

					outstr.append("\t\t<td  rowspan='2'  class='list_td_prom' ><b>设备编号</b></td>\n");
					outstr.append("\t\t<td  rowspan='2'  class='list_td_prom' ><b>设备类型</b></td>\n");
					outstr.append("\t\t<td  rowspan='2'  class='list_td_prom' ><b>所属机构</b></td>\n");
					outstr.append("\t\t<td  rowspan='2'  class='list_td_prom' ><b>安装地址</b></td>\n");
					outstr.append("\t\t<td  colspan='" + (tradecode.length + 2) + "'  class='list_td_prom' ><b>设备交易</b></td>\n");
					outstr.append("\t</tr>\n");

					outstr.append("\t<tr align='center'>\n");

					for (int i = 0; i < totalsortnum; i++) {
						outstr.append("\t\t<td   class='list_td_prom'><b>" + sortname[i] + "</b></td>\n");
						outstr.append("\t\t<td  class='list_td_prom'><b>金额</b></td>\n");
					}

					outstr.append("\t\t<td   class='list_td_prom'><b>合计</b></td>\n");
					outstr.append("\t\t<td  class='list_td_prom'><b>金额</b></td>\n");
					outstr.append("\t</tr>\n");

					// outstr.append("\t<tr align='center'>\n");
					// // for (int i = 0; i <= totalsortnum; i++) {
					// //
					// // outstr.append("\t\t<td
					// class='list_td_prom'>&nbsp;<nobr><b>笔数</b></nobr></td>\n");
					// // outstr.append("\t\t<td
					// class='list_td_prom'>&nbsp;<nobr><b>金额</b></nobr></td>\n");
					// //
					// // }
					//
					// outstr.append("\t</tr>\n");

					// 表体
					int[] devTotalnum = new int[devnoList.size()];
					// wukp1128 double[] devTotalprice = new
					// double[devnoList.size()];
					long[] devTotalprice = new long[devnoList.size()];

					String tmpString = "";

					for (int i = 0; i < devnoList.size(); i++) {

						outstr.append("\t<tr class='list_tr" + i % 2 + "' align='center'>\n");
						devno = (String) devnoList.get(i);
						Devinfo devtemp = DevinfoModel.getDevFromList(devno);
						Bankinfo bank = BankinfoModel.getBankinfoFromList(devtemp.getBankid());
						Devtype type = DevtypeModel.getDevTpFromList(devtemp.getTypeno());
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + devno + "</b></td>\n");

						if (type != null) {
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + type.getDevname() + "</b></td>\n");
						} else {
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + devtemp.getTypeno() + "</b></td>\n");
						}

						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + bank.getBanknm() + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + devtemp.getDevaddr() + "</b></td>\n");
						for (int j = 0; j < totalsortnum; j++) {
							// wukp1128 tmpString = myPubUtil.double2String(
							// sortpriceContent[i][j], 2);

							tmpString = myPubUtil.long2str10000(sortpriceContent[i][j]);

							devTotalnum[i] += sortnumContent[i][j];
							devTotalprice[i] += sortpriceContent[i][j];
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + String.valueOf(sortnumContent[i][j]) + "</b></td>\n");
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + tmpString + "</b></td>\n");
						}
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + String.valueOf(devTotalnum[i]) + "</b></td>\n");
						outstr.append("\t\t<td  class='list_td_title'>&nbsp;<b>" + myPubUtil.long2str10000(devTotalprice[i]) + "</b></td>\n");
						outstr.append("\t</tr>\n");
					}

					// 表脚
					int num = 0;
					// double price = 0.00;
					long price = 0;
					String tmpStr = "";
					outstr.append("\t<tr align='center'>\n");
					outstr.append("\t\t<td class='list_td_prom' ><b>合计</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' ><b>-</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' ><b>-</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' ><b>-</b></td>\n");
					for (int i = 0; i < totalsortnum; i++) {

						tmpStr = myPubUtil.long2str10000(sortTotalprice[i]);
						num += sortTotalnum[i];
						price += sortTotalprice[i];

						outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + String.valueOf(sortTotalnum[i]) + "</b></td>\n");
						outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<b>" + tmpStr + "</b></td>\n");

					}

					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + String.valueOf(num) + "</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + myPubUtil.long2str10000(price) + "</b></td>\n");

					outstr.append("\t</tr>\n");

					outstr.append("</table>\n");

				} else {
					outstr.append("<table id='excel' width='100%' cellspacing='0' cellpadding='0' class='list_table_border'>\n");
					// 表头
					String[][] tradecode = myPubUtil.Ini2Array("ini", "Trade.ini", "TradeCode", "TCode_Num", "TCode_", ",", "");
					outstr.append("\t<tr align='center'>\n");
					outstr.append("\t\t<td  rowspan='2'  class='list_td_prom' ><b>设备编号</b></td>\n");
					outstr.append("\t\t<td  rowspan='2'  class='list_td_prom' ><b>设备类型</b></td>\n");
					outstr.append("\t\t<td  rowspan='2'  class='list_td_prom' ><b>所属机构</b></td>\n");
					outstr.append("\t\t<td  rowspan='2'  class='list_td_prom' ><b>安装地址</b></td>\n");
					outstr.append("\t\t<td  colspan='" + (tradecode.length + 2) + "'  class='list_td_prom' ><b>点击次数</b></td>\n");
					outstr.append("\t</tr>\n");

					outstr.append("\t<tr align='center'>\n");
					for (int i = 0; i < trcdlist.size(); i++) {
						trcdno = (String) trcdlist.get(i);
						for (int j = 0; j < tradecode.length; j++) {
							String trcdnotmp = tradecode[j][0];
							if (trcdno.equals(tradecode[j][0])) {
								outstr.append("\t\t<td   class='list_td_prom'><b>" + tradecode[j][1] + "</b></td>\n");
								outstr.append("\t\t<td  class='list_td_prom'><b>金额</b></td>\n");
							}
						}
					}
					outstr.append("\t\t<td class='list_td_prom'><b>合计</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom'><b>金额</b></td>\n");
					outstr.append("\t</tr>\n");

					// 表体
					int[] devTotalnum = new int[devnoList.size()];
					long[] devTotalprice = new long[devnoList.size()];
					String tmpString = "";
					for (int i = 0; i < devnoList.size(); i++) {
						outstr.append("\t<tr class='list_tr" + i % 2 + "' align='center'>\n");

						devno = (String) devnoList.get(i);

						Devinfo devtemp = DevinfoModel.getDevFromList(devno);
						Bankinfo bank = BankinfoModel.getBankinfoFromList(devtemp.getBankid());
						Devtype type = DevtypeModel.getDevTpFromList(devtemp.getTypeno());

						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + devno + "</b></td>\n");

						if (type != null) {
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + type.getDevname() + "</b></td>\n");
						} else {
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + devtemp.getTypeno() + "</b></td>\n");
						}

						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + bank.getBanknm() + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + devtemp.getDevaddr() + "</b></td>\n");

						for (int j = 0; j < trcdlist.size(); j++) {
							tmpString = myPubUtil.long2str10000(tablePriceContent[i][j]);

							devTotalnum[i] += tableNumContent[i][j];

							devTotalprice[i] += tablePriceContent[i][j];
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + String.valueOf(tableNumContent[i][j]) + "</b></td>\n");
							outstr.append("\t\t<td  class='list_td_title'>&nbsp;<b>" + tmpString + "</b></td>\n");
						}

						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + String.valueOf(devTotalnum[i]) + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + myPubUtil.long2str10000(devTotalprice[i]) + "</b></td>\n");
						outstr.append("\t</tr>\n");
					}

					// 表脚
					int num = 0;
					// double price = 0.00;
					long price = 0;

					String tmpStr = "";

					outstr.append("\t<tr align='center'>\n");
					outstr.append("\t\t<td class='list_td_prom'><b>合计</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom'><b>-</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom'><b>-</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom'><b>-</b></td>\n");

					for (int i = 0; i < trcdlist.size(); i++) {

						tmpStr = myPubUtil.long2str10000(totalPrice[i]);

						num += totalNum[i];
						price += totalPrice[i];
						outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + String.valueOf(totalNum[i]) + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + tmpStr + "</b></td>\n");
					}
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + String.valueOf(num) + "</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + myPubUtil.long2str10000(price) + "</b></td>\n");

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
				String filename = "M_05_" + operid + "_" + DateTimeStr + ".htm";
				FileOutputStream outStream = new FileOutputStream(filepath + filename);
				OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "GB2312");

				outStreamWriter.write(OutStr);
				outStreamWriter.flush();
				outStreamWriter.close();
				// */

				// 更新系统任务表
				myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "05", "M", operid);
				myStaMission.setCurrentflag("0");
				myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());

				if (!myStaMissionBean.updateStaMission(myStaMission)) {

					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "05", "M", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				}
				log.info("交易统计(按设备) 月 结束");

			} catch (HibernateException he) {
				log.error("ERROR", he);
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "05", "M", operid);
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
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "05", "M", operid);
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