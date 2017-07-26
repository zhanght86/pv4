package com.lcjr.pvxp.util;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;

import com.lcjr.pvxp.bean.InvtjdayBean;
import com.lcjr.pvxp.bean.StaMissionBean;
import com.lcjr.pvxp.model.StaMissionModel;
import com.lcjr.pvxp.orm.InvLog;
import com.lcjr.pvxp.orm.StaMission;

/**
 * 发票打印统计 日报
 * 
 * @author 武坤鹏
 * 
 * @时间 2012-03-26
 * 
 */
public class InvtjDayServer extends Thread {

	Logger log = Logger.getLogger("web.lcjr.pvxp.util.InvtjDayServer.java");

	private String HQLstr = null;

	private String bankid = null;

	private String operid = null;

	private String repnm = null;

	private String qbegin = null;

	private String qend = null;

	private String filepath = null;

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

	public InvtjDayServer() {

	}

	public void run() {
		// 进入主线程

		log.info("发票打印统 日 开始");
		try {
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();

			List result = new ArrayList();
			List invtypeArray = new ArrayList();
			List devnoArray = new ArrayList();
			List ttrnumArray = new ArrayList();
			List strnumArray = new ArrayList();
			List AllstrnumArray = new ArrayList();
			List AllttrnumArray = new ArrayList();

			String devno = "";
			String invtype = "";

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
				// 日统计报表

				// 记录系统任务表
				StaMissionModel myStaMissionModel = new StaMissionModel();
				StaMissionBean myStaMissionBean = new StaMissionBean();
				StaMission myStaMission = new StaMission();

				myStaMission.setTimeid(DateTimeStr);

				myStaMission.setStatename(myCharSet.form2db(repnm));
				repnm = myCharSet.form2db(repnm);

				myStaMission.setBankid(bankid);
				myStaMission.setOwnerid(operid);
				myStaMission.setStatesort("04");
				myStaMission.setCreatetype("M");
				myStaMission.setCurrentflag("1");
				myStaMission.setRemark1(" ");
				myStaMission.setRemark2(" ");
				myStaMission.setRemark3(" ");

				myStaMissionBean.addStaMission(myStaMission);
				// 执行查询
				InvtjdayBean myTjDayBean = new InvtjdayBean();

				InvLog myTjDay = new InvLog();
				result = (List) myTjDayBean.getQueryList(HQLstr);
				// 取出发票类型
				for (int i = 0; i < result.size(); i++) {
					myTjDay = (InvLog) result.get(i);
					invtype = myTjDay.getType().trim();
					if (!invtype.equals("")) {
						iflag = 0;
						for (int j = 0; j < invtypeArray.size(); j++) {
							if (invtype.equals(invtypeArray.get(j))) {
								iflag = 1;
								break;
							}
						}
						if (iflag == 0) {
							invtypeArray.add(invtype);
						}
					}
				}

				// 计算每台设备打印每类发票的总量
				List tmplist = new ArrayList();
				String tmpinv = "";
				for (int i = 0; i < devnoList.size(); i++) {
					devno = (String) devnoList.get(i);
					strnumArray = new ArrayList();
					ttrnumArray = new ArrayList();
					for (int j = 0; j < invtypeArray.size(); j++) {
						invtype = (String) invtypeArray.get(j);
						strnum = 0;
						ttrnum = 0;
						for (int k = 0; k < result.size(); k++) {
							myTjDay = (InvLog) result.get(k);
							if (devno.equals(myTjDay.getDevno().trim()) && invtype.equals(myTjDay.getType().trim())) {
								ttrnum++;
								strnum++;
							}
						}
						strnumArray.add(Long.toString(strnum));
						ttrnumArray.add(Long.toString(ttrnum));
					}
					DevStrnumVector.add(strnumArray);
					DevTtrnumVector.add(ttrnumArray);
				}

				/*
				 * //计算每台设备的发票打印总量 strnumArray = new ArrayList(); ttrnumArray =
				 * new ArrayList(); String str1=""; String str2=""; for( int
				 * i=0; i<devnoList.size(); i++ ) { devno =
				 * (String)devnoList.get(i); strnum = 0; ttrnum = 0;
				 * 
				 * strnumArray.add( Long.toString( strnum ) ); ttrnumArray.add(
				 * Long.toString( ttrnum ) ); }
				 */

				// 计算每类发票的打印总量
				AllstrnumArray = new ArrayList();
				AllttrnumArray = new ArrayList();
				for (int i = 0; i < invtypeArray.size(); i++) {
					invtype = (String) invtypeArray.get(i);
					strnum = 0;
					ttrnum = 0;

					for (int j = 0; j < result.size(); j++) {
						myTjDay = (InvLog) result.get(j);
						if (invtype.equals(myTjDay.getType().trim())) {
							strnum++;
							ttrnum++;
						}
					}
					AllstrnumArray.add(Long.toString(strnum));
					AllttrnumArray.add(Long.toString(ttrnum));
				}

				// 处理统计后的数据
				List tempStrnumList = new ArrayList();
				List tempTtrnumList = new ArrayList();
				String str1 = "";
				String str2 = "";

				for (int i = 0; i < devnoList.size(); i++) {
					strnum = 0;
					ttrnum = 0;
					ResultString = "";
					ResultString += devnoList.get(i) + ",";
					tempStrnumList = (List) DevStrnumVector.get(i);
					tempTtrnumList = (List) DevTtrnumVector.get(i);
					for (int k = 0; k < tempStrnumList.size(); k++) {
						str1 = String.valueOf(tempStrnumList.get(k));
						str2 = String.valueOf(tempTtrnumList.get(k));
						ResultString += str1 + "|";
						ResultString += str2 + ",";
						strnum += Long.parseLong(str1);
						ttrnum += Long.parseLong(str2);
					}
					ResultString += strnum + "|";
					ResultString += ttrnum;

					ResultVector.add(ResultString);
				}

				// 整理输出流
				StringBuffer outstr = new StringBuffer("");
				outstr.append("<html>\n");
				outstr.append("<head>\n");
				outstr.append("\t<title>发票打印统计</title>\n");
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
				outstr.append("\t\t\t<font color=blue>PowerView XP </font>--- <font class='location'>发票打印统计</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td>\n");
				outstr.append("\t\t\t<font class='location'>" + repnm + "</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t\t<td align='right'>\n");
				outstr.append("\t\t\t<font class='location'>(日报)\n");
				if (!qbegin.equals(qend)) {
					outstr.append("\t\t\t\t" + qbegin.substring(0, 4) + "年" + Integer.parseInt(qbegin.substring(4, 6)) + "月" + Integer.parseInt(qbegin.substring(6, 8)) + "日");
					outstr.append("---" + qend.substring(0, 4) + "年" + Integer.parseInt(qend.substring(4, 6)) + "月" + Integer.parseInt(qend.substring(6, 8)) + "日&nbsp;\n");
				} else {
					outstr.append("\t\t\t\t" + qend.substring(0, 4) + "年" + Integer.parseInt(qend.substring(4, 6)) + "月" + Integer.parseInt(qend.substring(6, 8)) + "日&nbsp;\n");
				}
				outstr.append("\t\t\t</font>\n");
				outstr.append("\t\t</td>\n");
				outstr.append("\t</tr>\n");
				outstr.append("</table>\n");

				// 处理显示报表
				if (devnoList.size() == 0) {
					outstr.append("There is no transaction on device you selected, so that is no Statistics.\n");
				} else {
					outstr.append("<table id='excel' width='100%' cellspacing='0' cellpadding='0' class='list_table_border'>\n");
					// 表头
					outstr.append("\t<tr align='center'>\n");
					outstr.append("\t\t<td width='15%' class='list_td_prom'><b>设备编号</b></td>\n");

					String totalType = (String) myPubUtil.ReadConfig("InvoiceType", "InvoiceType_Num", "", "ini", "Invoice.ini");
					int total = Integer.parseInt(totalType);
					String temp = "";
					String code = "";
					String invty = "";
					String type = "";

					for (int i = 0; i < invtypeArray.size(); i++) {
						outstr.append("\t\t<td width='" + (100 - 15) / (invtypeArray.size() + 1) + "%' class='list_td_prom'><b>");
						type = (String) invtypeArray.get(i);
						for (int j = 0; j < total; j++) {
							temp = (String) myPubUtil.ReadConfig("InvoiceType", "Type_" + (j + 1), "", "ini", "Invoice.ini");
							code = temp.substring(0, temp.indexOf(','));
							invty = temp.substring(temp.indexOf(',') + 1);
							if (code.equals(type)) {
								type = invty;
							}
						}
						outstr.append(type + "</b></td>\n");
					}
					outstr.append("\t\t<td width='10%' class='list_td_prom'><b>合计</b></td>\n");
					outstr.append("\t</tr>\n");

					// 统计
					String tmpstr = "";
					String bal1 = "";
					String bal2 = "";
					int splitflag = 0;
					for (int i = 0; i < ResultVector.size(); i++) {
						wb = new StringTokenizer((String) ResultVector.get(i), ",");
						devno = wb.nextToken();
						outstr.append("\t<tr class='list_tr" + i % 2 + "'>\n");
						outstr.append("\t\t<td class='list_td_title'><b>" + devno + "</b></td>\n");
						for (int j = 0; j < invtypeArray.size() + 1; j++) {
							tmpstr = wb.nextToken();
							splitflag = tmpstr.lastIndexOf("|");
							if (splitflag == -1) {
								tmpstr = "";
							} else {
								bal1 = tmpstr.substring(0, splitflag);
								bal2 = tmpstr.substring(splitflag + 1);
								tmpstr = bal2;
							}
							if (j == invtypeArray.size()) {
								outstr.append("\t\t<td align='center' class='list_td_title'><b>" + tmpstr + "</b></td>\n");
							} else {
								outstr.append("\t\t<td align='center' class='list_td_title'>" + tmpstr + "</td>\n");
							}
						}
						outstr.append("\t</tr>\n");
					}

					// 表脚
					outstr.append("\t<tr class='list_td_title'>\n");
					outstr.append("\t\t<td class='list_td_prom'><b>合计</b></td>\n");
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

				// 记录文件
				String filename = "M_04_" + operid + "_" + DateTimeStr + ".htm";
				FileOutputStream outStream = new FileOutputStream(filepath + filename);
				OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "GB2312");
				outStreamWriter.write(OutStr);
				outStreamWriter.flush();
				outStreamWriter.close();
				// */

				// 更新系统任务表
				myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "04", "M", operid);
				myStaMission.setCurrentflag("0");
				myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());

				if (!myStaMissionBean.updateStaMission(myStaMission)) {
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "04", "M", operid);
					myStaMission.setCurrentflag("2");
					myStaMission.setStatename(myPubUtil.dealNull(myStaMission.getStatename()).trim());
					myStaMissionBean.updateStaMission(myStaMission);
				}
				log.info("发票打印统 日 结束");
			} catch (HibernateException he) {
				log.error("ERROR", he);
				try {
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "04", "M", operid);
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
					myStaMission = (StaMission) myStaMissionModel.getOneStaMission(DateTimeStr, "04", "M", operid);
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