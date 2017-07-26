package com.lcjr.pvxp.ini;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.hibernate.HibernateException;

import com.lcjr.pvxp.bean.StaMissionBean;
import com.lcjr.pvxp.bean.TradeLogBean;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.StaMissionModel;
import com.lcjr.pvxp.orm.Bankinfo;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.orm.Mxb;
import com.lcjr.pvxp.orm.Mxb_tmp;
import com.lcjr.pvxp.orm.StaMission;
import com.lcjr.pvxp.orm.Trcdlog;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.PubUtil;

/**
 * 卡卡转账交易明细
 * 
 * @author ibmsz
 * 
 */

public class CczzjymxDayServer extends Thread {
	private String HQLstr1 = null;
	private String HQLstr2 = null;
	private String HQLstr3 = null;
	private String bankid = null;
	private String operid = null;
	private String repnm = null;
	private String qbegin = null;
	private String qend = null;
	private String filepath = null;
	private String statmode = null;

	private List trcdlist = new ArrayList();
	private List devnoList = new ArrayList();

	public String getHQLstr1() {
		return (this.HQLstr1);
	}

	public String getHQLstr2() {
		return (this.HQLstr2);
	}

	public String getHQLstr3() {
		return (this.HQLstr3);
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

	public void setHQLstr1(String HQLstr1) {
		this.HQLstr1 = HQLstr1;
	}

	public void setHQLstr2(String HQLstr2) {
		this.HQLstr2 = HQLstr2;
	}

	public void setHQLstr3(String HQLstr3) {
		this.HQLstr3 = HQLstr3;
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

	public CczzjymxDayServer() {

	}

	public void run() {
		// 进入主线程

		try {
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();

			String TimeStr = myPubUtil.getNowTime();
			TimeStr = myPubUtil.replace(TimeStr, ":", "");
			String DateTimeStr = myPubUtil.getNowDate(1) + TimeStr;

			try {

				// 记录系统任务表
				StaMissionModel myStaMissionModel = new StaMissionModel();
				StaMissionBean myStaMissionBean = new StaMissionBean();
				StaMission myStaMission = new StaMission();

				myStaMission.setTimeid(DateTimeStr);
				myStaMission.setStatename(myCharSet.form2db(repnm));
				repnm = myCharSet.form2file(repnm);
				myStaMission.setBankid(bankid);
				myStaMission.setOwnerid(operid);
				myStaMission.setStatesort("20");
				myStaMission.setCreatetype("M");
				myStaMission.setCurrentflag("1");
				myStaMission.setRemark1("");
				myStaMission.setRemark2("");
				myStaMission.setRemark3("");

				myStaMissionBean.addStaMission(myStaMission);

				// 执行查询
				TradeLogBean myTradeLogBean = new TradeLogBean();

				List result1 = (List) myTradeLogBean.getTradeLog(HQLstr1);
				List result2 = (List) myTradeLogBean.getTradeLog(HQLstr2);
				List result3 = (List) myTradeLogBean.getTradeLog(HQLstr3);

				// 整理输出流
				StringBuffer outstr = new StringBuffer("");
				outstr.append("<html>\n");
				outstr.append("<head>\n");
				outstr.append("\t<title>交易明细表</title>\n");
				outstr.append("\t<style>\n");
				outstr
						.append("\t\tbody {color: #000000;font-family:'Tahoma','Helvetica','Arial','sans-serif';font-size: 9pt;}\n");
				outstr
						.append("\t\ttd {font-size: 9pt;text-decoration: none;line-height: 17pt;}\n");
				outstr
						.append("\t\t.location {font-size: 12px;font-weight: bold;text-decoration: none;}\n");
				outstr
						.append("\t\t.list_table_border {border: 1px solid #333333;}\n");
				outstr
						.append("\t\t.list_td_title {border: 1px solid #333333;color: #000000E;font-size: 12px;}\n");
				outstr
						.append("\t\t.list_tr0 {border: 1px solid #333333;background: #E7EBF7;font-size: 12px;}\n");
				outstr
						.append("\t\t.list_tr1 {border: 1px solid #333333;background: #E7EEF7;font-size: 12px;}\n");
				outstr
						.append("\t\t.list_td_prom{border: 1px solid #333333;background: #E1EDFF;font-size: 12px;}\n");
				outstr.append("\t</style>\n");
				outstr.append("\t<style media='print'>\n");
				outstr.append("\t\t.noprint{display:none;}\n");
				outstr.append("\t</style>\n");
				outstr.append("</head>\n");
				outstr.append("<body>\n");

				// 标题
				outstr
						.append("<table width='100%' cellspacing='0' cellpadding='0'>\n");
				outstr.append("\t<tr>\n");
				outstr
						.append("\t\t<td align='left' valign='center' width='30' height='40'>\n");
				outstr.append("\t\t\t&nbsp;\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td>\n");
				outstr
						.append("\t\t\t<font color=blue>PowerView XP </font>--- <font class='location'>交易明细表</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td>\n");
				outstr.append("\t\t\t<font class='location'>" + repnm
						+ "</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td align='right'>\n");
				outstr.append("\t\t\t<font class='location'>\n");
				if (!qbegin.equals(qend)) {
					outstr.append("\t\t\t\t" + qbegin.substring(0, 4) + "年"
							+ Integer.parseInt(qbegin.substring(4, 6)) + "月"
							+ Integer.parseInt(qbegin.substring(6, 8)) + "日");
					outstr.append("---" + qend.substring(0, 4) + "年"
							+ Integer.parseInt(qend.substring(4, 6)) + "月"
							+ Integer.parseInt(qend.substring(6, 8))
							+ "日&nbsp;\n");
				} else {
					outstr.append("\t\t\t\t" + qend.substring(0, 4) + "年"
							+ Integer.parseInt(qend.substring(4, 6)) + "月"
							+ Integer.parseInt(qend.substring(6, 8))
							+ "日&nbsp;\n");
				}
				outstr.append("\t\t\t</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t</tr>\n");
				outstr.append("</table>\n");

				// 处理显示报表

				if (devnoList.size() == 0) {
					outstr
							.append("There is no transaction on device you selected, so that is no Statistics.\n");
				} else {
					outstr
							.append("<table width='100%' cellspacing='0' cellpadding='0' class='list_table_border'>\n");
					// 表头
					outstr.append("\t<tr align='center'>\n");
					outstr
							.append("\t\t<td class='list_td_prom'><b>编号</b></td>\n");
					outstr
							.append("\t\t<td class='list_td_prom'><b>设备编号</b></td>\n");
					outstr
							.append("\t\t<td class='list_td_prom'><b>安装地址</b></td>\n");
					// outstr.append( "\t\t<td
					// class='list_td_prom'><b>所属机构</b></td>\n" );
					outstr
							.append("\t\t<td class='list_td_prom'><b>交易类型</b></td>\n");
					outstr
							.append("\t\t<td class='list_td_prom'><b>交易金额</b></td>\n");
					outstr
							.append("\t\t<td class='list_td_prom'><b>交易手续费</b></td>\n");
					outstr
							.append("\t\t<td class='list_td_prom'><b>交易结果</b></td>\n");
					outstr
							.append("\t\t<td class='list_td_prom'><b>交易日期</b></td>\n");
					outstr
							.append("\t\t<td class='list_td_prom'><b>交易时间</b></td>\n");
					outstr.append("\t</tr>\n");

					String devno = "";
					String tCode = "";
					String tType = "";
					String rCode = "";
					String rType = "";
					String money = "";//交易金额
					String tradeDate = "";
					String tradeTime = "";
					String bankid = "";
					String bankName = "";
					String addr = "";
					String accno1 = "";
					String accno2 = "";
					String temp = "";
					String money2="";//手续费
					
					float totalMoney = 0;
					float totalMoney2 = 0;
					DecimalFormat format = new DecimalFormat("0.00");
					
					int nums = 0;

					String[][] tradecode = myPubUtil.Ini2Array("ini",
							"Trade.ini", "Rate", "Rate_Num", "Rate_",
							",", "");
					String[][] returnCode = myPubUtil.Ini2Array("ini",
							"Trade.ini", "ReturnCode", "RCode_Num", "RCode_",
							",", "");

					// 显示内容
					if (result1 != null) {
						for (int i = 0; i < result1.size(); i++) {
							nums++;

							Trcdlog trcdmx = (Trcdlog) result1.get(i);
							devno = trcdmx.getDevno().trim();
							Devinfo tmp = DevinfoModel.getDevFromList(devno);
							addr = myCharSet.db2web(myPubUtil.dealNull(
									tmp.getDevaddr()).trim());

							bankid = myPubUtil.dealNull(trcdmx.getOther1())
									.trim();
							Bankinfo bankInfo = BankinfoModel
									.getBankinfoFromList(bankid);
							if (bankInfo != null) {
								bankName = myCharSet.db2web(bankInfo
										.getBanknm());

							} else {
								bankName = bankid;
							}

							accno1 = trcdmx.getAccno1();
							accno2 = trcdmx.getAccno2();
							if (accno2 == null) {
								accno2 = "";
							}

							tCode = trcdmx.getDevtrcd().trim();
							for (int k = 0; k < tradecode.length; k++) {
								if (tCode.equals(tradecode[k][0])) {
									tType = tradecode[k][1];
									break;
								} else {
									tType = tCode;
								}
							}

							rCode = trcdmx.getReturnno().trim();
							for (int k = 0; k < returnCode.length; k++) {
								if (rCode.equals(returnCode[k][0])) {
									rType = returnCode[k][1];
									break;
								} else {
									rType = rCode;
								}
							}

							money = trcdmx.getMoney1();
							if (money == null) {
								money = "";
							}
							
							money2 = trcdmx.getMoney2();
							if (money2 == null) {
								money2 = "";
							}
							
							if(returnCode[0][0].equals(rCode)){
								totalMoney = totalMoney+Float.parseFloat(money);
								totalMoney2 = totalMoney2+Float.parseFloat(money2);
							}
							
							temp = myPubUtil.dealNull(trcdmx.getDevdate())
									.trim();
							tradeDate = temp.substring(0, 4) + "-"
									+ temp.substring(4, 6) + "-"
									+ temp.substring(6, 8);
							temp = myPubUtil.dealNull(trcdmx.getDevtime())
									.trim();
							tradeTime = temp.substring(0, 2) + ":"
									+ temp.substring(2, 4) + ":"
									+ temp.substring(4, 6);

							outstr
									.append("\t<tr class='list_tr1' align='center'>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ nums + "</td>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ devno + "</td>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ addr + "</td>\n");

							// outstr.append( "\t\t<td
							// class='list_td_title'>&nbsp;" + bankName +
							// "</td>\n" );
							// outstr.append( "\t\t<td
							// class='list_td_title'>&nbsp;" + accno1 +
							// "</td>\n" );
							// outstr.append( "\t\t<td
							// class='list_td_title'>&nbsp;" + accno2 +
							// "</td>\n" );
							outstr.append("\t\t<td class='list_td_title'>&nbsp;"
											+ tType + "</td>\n");
							outstr.append("\t\t<td class='list_td_title'>&nbsp;"
											+ money + "</td>\n");
							outstr.append("\t\t<td class='list_td_title'>&nbsp;"
									+ money2 + "</td>\n");
							outstr.append("\t\t<td class='list_td_title'>&nbsp;"
											+ rType + "</td>\n");
							outstr.append("\t\t<td class='list_td_title'>&nbsp;"
											+ tradeDate + "</td>\n");
							outstr.append("\t\t<td class='list_td_title'>&nbsp;"
											+ tradeTime + "</td>\n");
							outstr.append("\t</tr>\n");

						}
					}

					if (result2 != null) {

						for (int i = 0; i < result2.size(); i++) {
							nums++;

							Mxb trcdmx = (Mxb) result2.get(i);
							devno = trcdmx.getDevno().trim();
							Devinfo tmp = DevinfoModel.getDevFromList(devno);
							addr = myCharSet.db2web(myPubUtil.dealNull(
									tmp.getDevaddr()).trim());

							bankid = myPubUtil.dealNull(trcdmx.getOther1())
									.trim();
							Bankinfo bankInfo = BankinfoModel
									.getBankinfoFromList(bankid);
							if (bankInfo != null) {
								bankName = myCharSet.db2web(bankInfo
										.getBanknm());

							} else {
								bankName = bankid;
							}

							accno1 = trcdmx.getAccno1();
							accno2 = trcdmx.getAccno2();
							if (accno2 == null) {
								accno2 = "";
							}

							tCode = trcdmx.getDevtrcd().trim();
							for (int k = 0; k < tradecode.length; k++) {
								if (tCode.equals(tradecode[k][0])) {
									tType = tradecode[k][1];
									break;
								} else {
									tType = tCode;
								}
							}

							rCode = trcdmx.getReturnno().trim();
							for (int k = 0; k < returnCode.length; k++) {
								if (rCode.equals(returnCode[k][0])) {
									rType = returnCode[k][1];
									break;
								} else {
									rType = rCode;
								}
							}

							money = trcdmx.getMoney1();
							if (money == null) {
								money = "";
							}

							money2 = trcdmx.getMoney2();
							if (money2 == null) {
								money2 = "";
							}
							if(returnCode[0][0].equals(rCode)){
								totalMoney = totalMoney+Float.parseFloat(money);
								totalMoney2 = totalMoney2+Float.parseFloat(money2);
							}
							temp = myPubUtil.dealNull(trcdmx.getDevdate())
									.trim();
							tradeDate = temp.substring(0, 4) + "-"
									+ temp.substring(4, 6) + "-"
									+ temp.substring(6, 8);
							temp = myPubUtil.dealNull(trcdmx.getDevtime())
									.trim();
							tradeTime = temp.substring(0, 2) + ":"
									+ temp.substring(2, 4) + ":"
									+ temp.substring(4, 6);

							outstr
									.append("\t<tr class='list_tr1' align='center'>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ nums + "</td>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ devno + "</td>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ addr + "</td>\n");

							// outstr.append( "\t\t<td
							// class='list_td_title'>&nbsp;" + bankName +
							// "</td>\n" );
							// outstr.append( "\t\t<td
							// class='list_td_title'>&nbsp;" + accno1 +
							// "</td>\n" );
							// outstr.append( "\t\t<td
							// class='list_td_title'>&nbsp;" + accno2 +
							// "</td>\n" );
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ tType + "</td>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ money + "</td>\n");
							outstr.append("\t\t<td class='list_td_title'>&nbsp;"
									+ money2 + "</td>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ rType + "</td>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ tradeDate + "</td>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ tradeTime + "</td>\n");

							outstr.append("\t</tr>\n");

						}
					}

					if (result3 != null) {
						for (int i = 0; i < result3.size(); i++) {
							nums++;

							Mxb_tmp trcdmx = (Mxb_tmp) result3.get(i);
							devno = trcdmx.getDevno().trim();
							Devinfo tmp = DevinfoModel.getDevFromList(devno);
							addr = myCharSet.db2web(myPubUtil.dealNull(
									tmp.getDevaddr()).trim());

							bankid = myPubUtil.dealNull(trcdmx.getOther1())
									.trim();
							Bankinfo bankInfo = BankinfoModel
									.getBankinfoFromList(bankid);
							if (bankInfo != null) {
								bankName = myCharSet.db2web(bankInfo
										.getBanknm());

							} else {
								bankName = bankid;
							}

							accno1 = trcdmx.getAccno1();
							accno2 = trcdmx.getAccno2();
							if (accno2 == null) {
								accno2 = "";
							}

							tCode = trcdmx.getDevtrcd().trim();
							for (int k = 0; k < tradecode.length; k++) {
								if (tCode.equals(tradecode[k][0])) {
									tType = tradecode[k][1];
									break;
								} else {
									tType = tCode;
								}
							}

							rCode = trcdmx.getReturnno().trim();
							for (int k = 0; k < returnCode.length; k++) {
								if (rCode.equals(returnCode[k][0])) {
									rType = returnCode[k][1];
									break;
								} else {
									rType = rCode;
								}
							}

							money = trcdmx.getMoney1();
							if (money == null) {
								money = "";
							}
							
							money2 = trcdmx.getMoney2();
							if (money2 == null) {
								money2 = "";
							}
							
							if(returnCode[0][0].equals(rCode)){
								totalMoney = totalMoney+Float.parseFloat(money);
								totalMoney2 = totalMoney2+Float.parseFloat(money2);
							}
							
							temp = myPubUtil.dealNull(trcdmx.getDevdate())
									.trim();
							tradeDate = temp.substring(0, 4) + "-"
									+ temp.substring(4, 6) + "-"
									+ temp.substring(6, 8);
							temp = myPubUtil.dealNull(trcdmx.getDevtime())
									.trim();
							tradeTime = temp.substring(0, 2) + ":"
									+ temp.substring(2, 4) + ":"
									+ temp.substring(4, 6);

							outstr
									.append("\t<tr class='list_tr1' align='center'>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ nums + "</td>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ devno + "</td>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ addr + "</td>\n");

							// outstr.append( "\t\t<td
							// class='list_td_title'>&nbsp;" + bankName +
							// "</td>\n" );
							// outstr.append( "\t\t<td
							// class='list_td_title'>&nbsp;" + accno1 +
							// "</td>\n" );
							// outstr.append( "\t\t<td
							// class='list_td_title'>&nbsp;" + accno2 +
							// "</td>\n" );
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ tType + "</td>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ money + "</td>\n");
							outstr.append("\t\t<td class='list_td_title'>&nbsp;"
									+ money2 + "</td>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ rType + "</td>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ tradeDate + "</td>\n");
							outstr
									.append("\t\t<td class='list_td_title'>&nbsp;"
											+ tradeTime + "</td>\n");

							outstr.append("\t</tr>\n");

						}
					}
					
					outstr.append("\t<tr class='list_tr1' align='center'>\n");
					outstr.append("\t\t<td class='list_td_title'>&nbsp;合计</td>\n");
					outstr.append("\t\t<td class='list_td_title'>&nbsp;</td>\n");
					outstr.append("\t\t<td class='list_td_title'>&nbsp;</td>\n");
					outstr.append("\t\t<td class='list_td_title'>&nbsp;</td>\n");
					outstr.append("\t\t<td class='list_td_title'>&nbsp;"
									+ format.format(totalMoney) + "</td>\n");
					outstr.append("\t\t<td class='list_td_title'>&nbsp;"
							+ format.format(totalMoney2) + "</td>\n");
					outstr.append("\t\t<td class='list_td_title'>&nbsp;</td>\n");
					outstr.append("\t\t<td class='list_td_title'>&nbsp;</td>\n");
					outstr.append("\t\t<td class='list_td_title'>&nbsp;</td>\n");
					outstr.append("\t</tr>\n");
					outstr.append("</table>\n");
				}

				// 交易列表
				outstr.append("<br>\n");
				if (statmode.equals("all")) {
					outstr.append("\n");
				} else {
					statmode = "Type" + statmode + "_";
					String[][] trcdtypes = myPubUtil.Ini2Array("Trade.ini",
							"RateType", statmode + "Num", statmode, "|");
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
					String[][] trcdnames = myPubUtil.Ini2Array("Trade.ini",
							"Rate", "Rate_Num", "Rate_", ",");
					for (int i = 0; i < trcdname.size(); i++) {
						tmplist = (List) trcdname.get(i);
						outstr.append("<br><b>" + tmplist.get(0) + "：</b>");
						for (int j = 1; j < tmplist.size(); j++) {
							trcd = (String) tmplist.get(j);
							for (int k = 0; k < trcdnames.length; k++) {
								if (trcd.equals(trcdnames[k][0])) {
									outstr.append(trcdnames[k][1] + "，");
								}
							}
						}
						outstr.setLength(outstr.length() - 1);
						outstr.append("。<br>\n");
					}
				}

				// 打印按钮
				outstr.append("<p align='center'>\n");
				outstr
						.append("\t<OBJECT id='WebBrowser' classid='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2' height='0' width='0' VIEWASTEXT></OBJECT>\n");
				outstr
						.append("\t<input type='button' value=' 打  印 ' onclick='document.all.WebBrowser.ExecWB(6,1)' class='noprint'>\n");
				outstr
						.append("\t<input type='button' value='直接打印' onclick='document.all.WebBrowser.ExecWB(6,6)' class='noprint'>\n");
				outstr
						.append("\t<input type='button' value='页面设置' onclick='document.all.WebBrowser.ExecWB(8,1)' class='noprint'>\n");
				outstr
						.append("\t<input type='button' value='打印预览' onclick='document.all.WebBrowser.ExecWB(7,1)' class='noprint'>\n");
				outstr.append("</p>\n");

				outstr.append("</body>\n");
				outstr.append("</html>\n");

				String OutStr = outstr.toString();
				String filename = "M_20_" + operid + "_" + DateTimeStr + ".htm";
				FileOutputStream outStream = new FileOutputStream(filepath
						+ filename);
				OutputStreamWriter outStreamWriter = new OutputStreamWriter(
						outStream, "GB2312");

				outStreamWriter.write(OutStr);
				outStreamWriter.flush();
				outStreamWriter.close();
				// */

				// 更新系统任务表
				myStaMission = (StaMission) myStaMissionModel.getOneStaMission(
						DateTimeStr, "20", "M", operid);
				myStaMission.setCurrentflag("0");
				myStaMission.setStatename(myPubUtil.dealNull(
						myStaMission.getStatename()).trim());

				if (!myStaMissionBean.updateStaMission(myStaMission)) {
					myStaMission = (StaMission) myStaMissionModel
							.getOneStaMission(DateTimeStr, "20", "M", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(
							myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				}

			} catch (HibernateException he) {

				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel
							.getOneStaMission(DateTimeStr, "20", "M", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(
							myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				} catch (Exception e) {
				}

			} catch (Exception ex) {

				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel
							.getOneStaMission(DateTimeStr, "20", "M", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(
							myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				} catch (Exception e) {

				}
			}
		} catch (Exception e) {

		}
	}
}