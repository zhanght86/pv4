package com.lcjr.pvxp.util;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;

import com.lcjr.pvxp.bean.StaMissionBean;
import com.lcjr.pvxp.bean.TrcdtjdayBean;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.StaMissionModel;
import com.lcjr.pvxp.orm.Bankinfo;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.orm.StaMission;
import com.lcjr.pvxp.orm.Trcdtjday;

/**
 * 机构交易统计 日报
 * 
 * @author 武坤鹏
 * 
 */
public class JytjBankDayServer extends Thread {

	Logger log = Logger.getLogger(JytjBankDayServer.class.getName());

	/**
	 * 机构信息
	 */
	private String bankid = null;

	/**
	 * 操作员的用户id
	 */
	private String operid = null;

	/**
	 * 统计类型，天，月，年
	 */
	private String repnm = null;

	/**
	 * 起始日期
	 */
	private String qbegin = null;

	/**
	 * 截至日期
	 */
	private String qend = null;

	/**
	 * 钱币类型
	 */
	private String moneytype = null;

	/**
	 * 文件类型
	 */
	private String filepath = null;

	/**
	 * 统计方式
	 */
	private String statmode = null;

	/**
	 * 银行级别
	 */
	private String banktag = null;

	/**
	 * 业务类型编码
	 */
	private List trcdlist = new ArrayList();

	/**
	 * 银行编号列表
	 */
	private List banknoList = new ArrayList();

	/**
	 * 查询语句 类集
	 */
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

	public List getBanknoList() {
		return (this.banknoList);
	}

	public String getBanktag() {
		return (this.banktag);
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

	public void setBanknoList(List banknoList) {
		this.banknoList = banknoList;
	}

	public void setBanktag(String banktag) {
		this.banktag = banktag;
	}

	/**
	 * 构造函数
	 */
	public JytjBankDayServer() {

	}

	/**
	 * 进入主线程
	 */
	public void run() {

		log.info("机构交易统 日 开始");

		try {
			PubUtil myPubUtil = new PubUtil();
			// 字符集转换
			CharSet myCharSet = new CharSet();

			List result = new ArrayList();
			List bizhArray = new ArrayList();
			List rateArray = new ArrayList();
			List banknameList = new ArrayList();

			int[][] tableNumContent = new int[banknoList.size()][trcdlist.size()];
			// wukp1129 double[][] tablePriceContent = new
			// double[banknoList.size()][trcdlist.size()];
			long[][] tablePriceContent = new long[banknoList.size()][trcdlist.size()];

			// 为二维数组赋初值 用于每行每列的计算
			for (int i = 0; i < banknoList.size(); i++) {
				for (int j = 0; j < trcdlist.size(); j++) {
					tableNumContent[i][j] = 0;
					tablePriceContent[i][j] = 0;
				}
			}

			// 用于 合计
			int[] totalNum = new int[trcdlist.size()];
			// double[] totalPrice = new double[trcdlist.size()];
			long[] totalPrice = new long[trcdlist.size()];

			String bizh = "";
			String bankno = "";
			String trcdno = "";
			String moneyno = "";
			// double ftmp1 = 0.00;
			// double ftmp2 = 0.00;
			long ftmp1 = 0;
			long ftmp2 = 0;
			String bankname = "";
			String tmpbankname = "";
			String tmpHQLstr = "";
			String devno = "";
			String bankid = "";
			int strnum = 0;

			// 从Trade.ini文件中找到相关的业务
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

			// 设置当前日期和时间
			String TimeStr = myPubUtil.getNowTime();
			TimeStr = myPubUtil.replace(TimeStr, ":", "");
			String DateTimeStr = myPubUtil.getNowDate(1) + TimeStr;

			try {
				// 日统计报表

				// 记录系统任务表
				StaMissionModel myStaMissionModel = new StaMissionModel();
				StaMissionBean myStaMissionBean = new StaMissionBean();
				StaMission myStaMission = new StaMission();

				// 实例化myStaMission
				myStaMission.setTimeid(DateTimeStr);
				myStaMission.setStatename(myCharSet.form2db(repnm));
				repnm = myCharSet.form2file(repnm);
				myStaMission.setBankid(bankid);
				myStaMission.setOwnerid(operid);
				myStaMission.setStatesort("06");
				myStaMission.setCreatetype("M");
				myStaMission.setCurrentflag("1");
				myStaMission.setRemark1("");
				myStaMission.setRemark2("");
				myStaMission.setRemark3("");
				// 将myStaMission持久化到数据库中
				myStaMissionBean.addStaMission(myStaMission);

				// 从数据库中搜索 机构 列表
				BankinfoModel myBankinfoModel = new BankinfoModel();
				List myBankList = myBankinfoModel.getBankinfoList();
				if (myBankList != null && !myBankList.isEmpty()) {
					int banknum = myBankList.size();// 机构列表中的机构个数

					// 该段完成为从网页中传过来的 机构编号找出对应的机构名称
					for (int i = 0; i < banknum; i++) {
						Bankinfo tmpbank = (Bankinfo) myBankList.get(i);
						for (int j = 0; j < banknoList.size(); j++) {
							bankno = (String) banknoList.get(j);
							// 如果选中的机构编号等于机构列表中的项，把该项添加到banknameList中
							if ((myPubUtil.dealNull(tmpbank.getBankid()).trim()).equals(bankno)) {
								tmpbankname = myCharSet.db2web(myPubUtil.dealNull(tmpbank.getBanknm()).trim());
								banknameList.add(tmpbankname);
							}
						}
					}
				}

				// DevinfoModel 设备信息模型
				DevinfoModel myDevinfoModel = new DevinfoModel();
				List myDevList = myDevinfoModel.getDevList();
				List trcddevnum = new ArrayList();
				int[] trcddevs = new int[banknoList.size()];
				int[] totaldevs = new int[banknoList.size()];
				int devnum = 0;
				Devinfo tmp = null;

				if (myDevList != null && !myDevList.isEmpty()) {
					// 从数据库中找到的设备的个数
					devnum = myDevList.size();
				}

				// 币种类型名称，默认为人民币
				String moneytypename = myPubUtil.ReadConfig("MoneyType", "Type" + moneytype + "_Name", "人民币", "MoneyType.ini");

				// 币种编号与所兑换的币种的个数
				int moneytypenum = Integer.parseInt(myPubUtil.ReadConfig("MoneyType", "Type" + moneytype + "_Num", "0", "MoneyType.ini"));

				// 兑换汇率
				for (int i = 1; i <= moneytypenum; i++) {
					String tmptype = myPubUtil.ReadConfig("MoneyType", "Type" + moneytype + "_" + i, "0", "MoneyType.ini");
					int pos1 = tmptype.indexOf(",");
					int pos2 = tmptype.lastIndexOf(",");
					rateArray.add(tmptype.substring(pos2 + 1));// 币种汇率
					bizhArray.add(tmptype.substring(pos1 + 1, pos2));// 币种编号
				}

				// 执行查询
				TrcdtjdayBean myTjDayBean = new TrcdtjdayBean();
				Trcdtjday myTjDay = new Trcdtjday();

				// 计算每台设备各类交易的交易量和金额
				for (int z = 0; z < HQLstr.size(); z++) {
					tmpHQLstr = (String) HQLstr.get(z);
					// 根据sql语句查询的数据库结果
					result = (List) myTjDayBean.getQueryList(tmpHQLstr);

					for (int i = 0; i < result.size(); i++) {
						myTjDay = (Trcdtjday) result.get(i);
						for (int j = 0; j < banknoList.size(); j++) {
							bankno = (String) banknoList.get(j);
							for (int k = 0; k < devnum; k++) {
								tmp = (Devinfo) myDevList.get(k);
								// 设备所属机构ID
								bankid = myPubUtil.dealNull(tmp.getBankid()).trim();
								if (banktag.equals("1")) {
									bankno = bankno.substring(0, 2);
									bankid = bankid.substring(0, 2);
								} else if (banktag.equals("2")) {
									bankno = bankno.substring(0, 6);
									bankid = bankid.substring(0, 6);
								} else {

								}

								// 如果从页面上取得的机构编号等于从数据库表中提取的设备所属机构编号
								if (bankno.equals(bankid)) {
									// 获得数据库表中的设备编号
									devno = myPubUtil.dealNull(tmp.getDevno()).trim();
									// 如果设备编号与要统计的设备编号相同
									if (devno.equals(myTjDay.getDevno().trim())) {
										// 如果链表为空就添加设备编号
										if (trcddevnum.size() == 0) {
											trcddevnum.add(devno);
										} else {
											// 检查要添加的设备编号是否在链表中，不存在，则添加
											int nums = 0;
											for (int m = 0; m < trcddevnum.size(); m++) {
												if (devno.equals(trcddevnum.get(m))) {
													break;
												}
												nums++;
											}

											// 向链表的最末位添加设备编号
											if (nums == trcddevnum.size()) {
												trcddevnum.add(devno);
											}
										}

										for (int x = 0; x < trcdlist.size(); x++) {
											trcdno = (String) trcdlist.get(x);
											// 如果页面要求的交易类型与数据库中的交易类型（交易码）相同，就计算笔数，总笔数
											if (trcdno.equals(myTjDay.getTrcdtype().trim())) {
												strnum = Integer.parseInt(myTjDay.getStrnum().trim());
												tableNumContent[j][x] += strnum;
												totalNum[x] += strnum;

												// 币种
												moneyno = myTjDay.getMoneytype().trim();

												// 如果币种为空，就默认为“001”
												if (moneyno == null || moneyno.length() == 0)
													moneyno = "001";

												int pos = moneyno.indexOf("|");
												if (pos != -1) {
													moneyno = moneyno.substring(0, pos);
												}

												for (int y = 0; y < bizhArray.size(); y++) {// 币种编号
													bizh = (String) bizhArray.get(y);
													// 如果ini文件中币种编号跟网页中的一样，就计算金额*汇率。总金额*汇率
													// myPubUtil
													if (bizh.equals(moneyno)) {
														try {
															// wukp1129 ftmp1 =
															// Double.parseDouble(myTjDay.getStrsum().trim());
															// wukp1129 ftmp2 =
															// Double.parseDouble((String)
															// rateArray.get(y));

															ftmp1 = myPubUtil.str2long(myTjDay.getStrsum().trim());
															ftmp2 = myPubUtil.str2long((String) rateArray.get(y));

														} catch (NumberFormatException e) {
														}
														// wukp1129
														// 两个长整型相乘，结果扩大10000倍
														tablePriceContent[j][x] += ftmp1 * ftmp2;
														totalPrice[x] += ftmp1 * ftmp2;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}

				// 计算 机构所含设备总数及做交易的台数
				for (int i = 0; i < devnum; i++) {
					// 从数据库中找出的设备列表
					tmp = (Devinfo) myDevList.get(i);
					// 获得设备的所属机构编号
					bankid = myPubUtil.dealNull(tmp.getBankid()).trim();
					for (int j = 0; j < banknoList.size(); j++) {
						// 从网页中传过来的银行编号
						bankno = (String) banknoList.get(j);
						// 根据不同的机构类别，寻找不同的机构编号
						if (banktag.equals("1")) {
							bankno = bankno.substring(0, 2);
							bankid = bankid.substring(0, 2);
						} else if (banktag.equals("2")) {
							bankno = bankno.substring(0, 6);
							bankid = bankid.substring(0, 6);
						}

						if (bankno.equals(bankid)) {
							totaldevs[j]++;
							for (int k = 0; k < trcddevnum.size(); k++) {
								devno = myPubUtil.dealNull(tmp.getDevno()).trim();
								if (devno.equals(trcddevnum.get(k))) {
									trcddevs[j]++;
								}
							}
						}
					}
				}

				// 整理输出流
				StringBuffer outstr = new StringBuffer("");
				outstr.append("<html>\n");
				outstr.append("<head>\n");
				outstr.append("\t<title>交易统计(按机构)</title>\n");
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
				outstr.append("\t\t\t<font class='location'>(日报)\n");
				if (!qbegin.equals(qend)) {
					outstr.append("\t\t\t\t" + qbegin.substring(0, 4) + "年" + Integer.parseInt(qbegin.substring(4, 6)) + "月" + Integer.parseInt(qbegin.substring(6, 8)) + "日");
					outstr.append("---" + qend.substring(0, 4) + "年" + Integer.parseInt(qend.substring(4, 6)) + "月" + Integer.parseInt(qend.substring(6, 8)) + "日&nbsp;&nbsp;\n");
				} else {
					outstr.append("\t\t\t\t" + qend.substring(0, 4) + "年" + Integer.parseInt(qend.substring(4, 6)) + "月" + Integer.parseInt(qend.substring(6, 8)) + "日&nbsp;&nbsp;\n");
				}

				outstr.append("\t\t\t\t&nbsp;&nbsp;币种：" + moneytypename + "&nbsp;\n");
				outstr.append("\t\t\t</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t</tr>\n");
				outstr.append("</table>\n");

				// 处理显示报表
				// if( banknoArray.size() == 0 ){
				if (banknoList.size() == 0) {
					outstr.append("There is no transaction on device you selected, so that is no Statistics.\n");
				}
				// 如果选择的 交易统计方式 为全部交易统计
				else if (statmode.equals("all")) {
					int totalsortnum = 0;
					int typenum = 0;
					int sortnum = 1;

					for (int i = 1; i <= sortnum; i++) {
						typenum = Integer.parseInt(myPubUtil.ReadConfig("TradeType", "Type" + i + "_Num", "0", "Trade.ini"));
						totalsortnum += typenum;
					}

					// 用于统计行
					int[][] sortnumContent = new int[banknoList.size()][totalsortnum];
					// wukp1129 double[][] sortpriceContent = new
					// double[banknoList.size()][totalsortnum];
					long[][] sortpriceContent = new long[banknoList.size()][totalsortnum];

					// 初始化
					for (int i = 0; i < banknoList.size(); i++) {
						for (int j = 0; j < totalsortnum; j++) {
							sortnumContent[i][j] = 0;
							sortpriceContent[i][j] = 0;
						}
					}

					int ttnum = 0;
					String[] sortname = new String[totalsortnum];
					int[] sortTotalnum = new int[totalsortnum];
					// wukp1129 double[] sortTotalprice = new
					// double[totalsortnum];
					long[] sortTotalprice = new long[totalsortnum];

					for (int i = 1; i <= sortnum; i++) {
						// 从Trade.ini文件中挨个读取业务类型个数
						int typenum1 = Integer.parseInt(myPubUtil.ReadConfig("TradeType", "Type" + i + "_Num", "0", "Trade.ini"));

						for (int j = 1; j <= typenum1; j++) {
							String tmpstr1 = myPubUtil.ReadConfig("TradeType", "Type" + i + "_" + j, "0", "Trade.ini");

							int tmppos = tmpstr1.indexOf("|");
							sortname[ttnum] = tmpstr1.substring(0, tmppos);
							// tmpstr2 中包含的|之后的项，并根据逗号分别保存
							String tmpstr2 = tmpstr1.substring(tmppos + 1);
							StringTokenizer wb = new StringTokenizer(tmpstr2, ",");

							int tnum = wb.countTokens();
							for (int z = 0; z < tnum; z++) {
								String tmpstr3 = wb.nextToken();
								for (int x = 0; x < banknoList.size(); x++) {
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
					outstr.append("\t\t<td class='list_td_prom' rowspan='2'><b>机构名称</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' rowspan='2'><b>总设备数</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' rowspan='2'><b>交易设备数</b></td>\n");
					String[][] tradecode = myPubUtil.Ini2Array("ini", "Trade.ini", "TradeCode", "TCode_Num", "TCode_", ",", "");
					for (int i = 0; i < totalsortnum; i++) {
						outstr.append("\t\t<td colspan='2' class='list_td_prom'><b>" + sortname[i] + "</b></td>\n");

					}
					outstr.append("\t\t<td colspan='2' class='list_td_prom'><b>合计</b></td>\n");
					outstr.append("\t</tr>\n");
					outstr.append("\t<tr align='center'>\n");
					for (int i = 0; i <= totalsortnum; i++) {
						outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>笔数</b></nobr></td>\n");
						outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>金额</b></nobr></td>\n");
					}

					outstr.append("\t</tr>\n");

					// 表体
					int[] bankTotalnum = new int[banknoList.size()];
					// wukp1129 double[] bankTotalprice = new
					// double[banknoList.size()];
					long[] bankTotalprice = new long[banknoList.size()];

					String tmpString = "";
					int totaldev = 0;
					int trcddev = 0;
					for (int i = 0; i < banknoList.size(); i++) {
						outstr.append("\t<tr class='list_tr" + i % 2 + "' align='center'>\n");
						bankname = (String) banknameList.get(i);
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + bankname + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + String.valueOf(totaldevs[i]) + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + String.valueOf(trcddevs[i]) + "</b></td>\n");
						totaldev += totaldevs[i];
						trcddev += trcddevs[i];
						for (int j = 0; j < totalsortnum; j++) {

							tmpString = myPubUtil.long2str10000(sortpriceContent[i][j]);
							bankTotalnum[i] += sortnumContent[i][j];
							bankTotalprice[i] += sortpriceContent[i][j];
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + String.valueOf(sortnumContent[i][j]) + "</b></td>\n");
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + tmpString + "</b></td>\n");
						}
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + String.valueOf(bankTotalnum[i]) + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + myPubUtil.long2str10000(bankTotalprice[i]) + "</b></td>\n");
						outstr.append("\t</tr>\n");
					}

					// 表脚
					int num = 0;
					// double price = 0.00;
					long price = 0;

					String tmpStr = "";
					outstr.append("\t<tr align='center'>\n");
					outstr.append("\t\t<td class='list_td_prom' ><b>合计</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' ><b>" + String.valueOf(totaldev) + "</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' ><b>" + String.valueOf(trcddev) + "</b></td>\n");
					for (int i = 0; i < totalsortnum; i++) {
						tmpStr = myPubUtil.long2str10000(sortTotalprice[i]);
						num += sortTotalnum[i];
						price += sortTotalprice[i];
						outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + String.valueOf(sortTotalnum[i]) + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + tmpStr + "</b></td>\n");
					}

					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + String.valueOf(num) + "</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom'>&nbsp;<b>" + myPubUtil.long2str10000(price) + "</b></td>\n");
					outstr.append("\t</tr>\n");
					outstr.append("</table>\n");

					// 如果业务类型是单独选中的
				} else {
					outstr.append("<table id='excel' width='100%' cellspacing='0' cellpadding='0' class='list_table_border'>\n");

					// 表头
					outstr.append("\t<tr align='center'>\n");
					outstr.append("\t\t<td class='list_td_prom' rowspan='2'><b>设备编号</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' rowspan='2'><b>总设备数</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' rowspan='2'><b>交易设备数</b></td>\n");
					String[][] tradecode = myPubUtil.Ini2Array("ini", "Trade.ini", "TradeCode", "TCode_Num", "TCode_", ",", "");
					for (int i = 0; i < trcdlist.size(); i++) {
						trcdno = (String) trcdlist.get(i);
						for (int j = 0; j < tradecode.length; j++) {
							// String trcdnotmp = tradecode[j][0];
							// 这里是用从ini文件中提取的所有类型的业务编号同网页中选择的业务编号进行一一比对，符合的就找出对应的业务名称，用于显示
							if (trcdno.equals(tradecode[j][0])) {
								outstr.append("\t\t<td colspan='2' class='list_td_prom'><b>" + tradecode[j][1] + "</b></td>\n");
							}
						}
					}

					outstr.append("\t\t<td colspan='2' class='list_td_prom'><b>合计</b></td>\n");
					outstr.append("\t</tr>\n");

					outstr.append("\t<tr align='center'>\n");

					// 有几个业务编号，就显示几个笔数和金额
					for (int i = 0; i < trcdlist.size(); i++) {
						trcdno = (String) trcdlist.get(i);
						for (int j = 0; j < tradecode.length; j++) {
							// String trcdnotmp = tradecode[j][0];
							if (trcdno.equals(tradecode[j][0])) {
								outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>笔数</b></nobr></td>\n");
								outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>金额</b></nobr></td>\n");
							}
						}
					}

					// 输出总的笔数和金额
					outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>笔数</b></nobr></td>\n");
					outstr.append("\t\t<td  class='list_td_prom'>&nbsp;<nobr><b>金额</b></nobr></td>\n");
					outstr.append("\t</tr>\n");

					// 表体
					int[] bankTotalnum = new int[banknoList.size()];
					// wukp1129 double[] bankTotalprice = new
					// double[banknoList.size()];
					long[] bankTotalprice = new long[banknoList.size()];

					String tmpString = "";
					int totaldev = 0;
					int trcddev = 0;
					for (int i = 0; i < banknoList.size(); i++) {
						outstr.append("\t<tr class='list_tr" + i % 2 + "' align='center'>\n");
						bankname = (String) banknameList.get(i);
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + bankname + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + String.valueOf(totaldevs[i]) + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + String.valueOf(trcddevs[i]) + "</b></td>\n");

						totaldev += totaldevs[i];
						trcddev += trcddevs[i];
						for (int j = 0; j < trcdlist.size(); j++) {

							tmpString = myPubUtil.long2str10000(tablePriceContent[i][j]);
							bankTotalnum[i] += tableNumContent[i][j];
							bankTotalprice[i] += tablePriceContent[i][j];
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + String.valueOf(tableNumContent[i][j]) + "</b></td>\n");
							outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + tmpString + "</b></td>\n");
						}

						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + String.valueOf(bankTotalnum[i]) + "</b></td>\n");
						outstr.append("\t\t<td class='list_td_title'>&nbsp;<b>" + myPubUtil.long2str10000(bankTotalprice[i]) + "</b></td>\n");
						outstr.append("\t</tr>\n");
					}

					// 表脚
					int num = 0;
					// double price = 0.00;
					long price = 0;
					String tmpStr = "";
					outstr.append("\t<tr align='center'>\n");
					outstr.append("\t\t<td class='list_td_prom' ><b>合计</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' ><b>" + String.valueOf(totaldev) + "</b></td>\n");
					outstr.append("\t\t<td class='list_td_prom' ><b>" + String.valueOf(trcddev) + "</b></td>\n");
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

					// 当选中的业务类型不是所有的时候
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

				// 建立新的文件，文件名称，并将设计好的html代码导入到新生成的文件中去，设置编码类型，
				String OutStr = outstr.toString();
				String filename = "M_06_" + operid + "_" + DateTimeStr + ".htm";
				FileOutputStream outStream = new FileOutputStream(filepath + filename);
				OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "GB2312");
				outStreamWriter.write(OutStr);
				outStreamWriter.flush();
				outStreamWriter.close();

				// 更新系统任务表
				myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "06", "M", operid);
				myStaMission.setCurrentflag("0");
				myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());

				if (!myStaMissionBean.updateStaMission(myStaMission)) {
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "06", "M", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				}

				log.info("机构交易统 日 结束");
			} catch (HibernateException he) {
				log.error("ERROR", he);
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "06", "M", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				} catch (Exception e) {
					log.error("ERROR", e);
				}
			} catch (Exception ex) {
				log.error("ERROR", ex);
				ex.printStackTrace();
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "06", "M", operid);
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