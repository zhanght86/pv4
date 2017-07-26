package com.lcjr.pvxp.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.TradeQueryForm;
import com.lcjr.pvxp.bean.TradeLogModel;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PageBean;
import com.lcjr.pvxp.util.PubUtil;
import com.lcjr.pvxp.util.iniread;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 交易查询Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author 吴学坤
 * @version 1.0 2005/03/25
 */
public final class TradeQueryAction extends Action {

	Logger log = Logger.getLogger(TradeQueryAction.class.getName());

	/**
	 * 交易代码
	 */
	private HashMap tradeCode = new HashMap();

	/**
	 * 返回码
	 */
	private HashMap returnCode = new HashMap();

	/**
	 * 币种
	 */
	private HashMap moneyType = new HashMap();

	/**
	 * 各种币种
	 */
	private HashMap moneyTypes = new HashMap();

	/**
	 * 构造函数，<font size='2' color='red'>主要完成从ini文件夹中读取Trade.ini配置文件信息</font>
	 * 
	 */
	public TradeQueryAction() {
		PubUtil pubUtil = new PubUtil();
		CharSet myCharSet = new CharSet();

		iniread myiniread = new iniread("Trade.ini");// 修改读配置文件方法 xucc
		// 20090625
		String tempStr;
		String code;
		String type;

		int TCode_Num = Integer.parseInt(myiniread.getIni("TCode_Num").trim()); // 交易类型数目

		for (int i = 0; i < TCode_Num; i++) {
			tempStr = pubUtil.dealNull(myiniread.getIni("TCode_" + String.valueOf(i + 1)));
//			tempStr = myCharSet.form2GB(tempStr.trim());

			int pos = tempStr.indexOf(',');
			code = tempStr.substring(0, pos);
			type = tempStr.substring(pos + 1);
			tradeCode.put(code, type);
		}

		int RCode_Num = Integer.parseInt(myiniread.getIni("RCode_Num").trim()); // 返回码数目
		for (int i = 0; i < RCode_Num; i++) {
			tempStr = pubUtil.dealNull(myiniread.getIni("RCode_" + String.valueOf(i + 1)));
//			tempStr = myCharSet.form2GB(tempStr.trim());

			int pos = tempStr.indexOf(',');
			code = tempStr.substring(0, pos);
			type = tempStr.substring(pos + 1);
			returnCode.put(code, type);
		}

		int MoneyType_Num = Integer.parseInt(myiniread.getIni("MoneyType_Num").trim());// 币种数目
		for (int i = 0; i < MoneyType_Num; i++) {
			tempStr = pubUtil.dealNull(myiniread.getIni("Type_" + String.valueOf(i + 1)));
//			tempStr = myCharSet.form2GB(tempStr.trim());

			int pos = tempStr.indexOf(',');
			code = tempStr.substring(0, pos);
			type = tempStr.substring(pos + 1);
			moneyType.put(code, type);
		}

		int MoneyTypes_Num = Integer.parseInt(myiniread.getIni("MoneyTypes_Num").trim());// 汇率数目
		for (int i = 0; i < MoneyTypes_Num; i++) {
			tempStr = pubUtil.dealNull(myiniread.getIni("Types_" + String.valueOf(i + 1)));
//			tempStr = myCharSet.form2GB(tempStr.trim());

			int pos = tempStr.indexOf(',');
			code = tempStr.substring(0, pos);
			type = tempStr.substring(pos + 1);
			moneyTypes.put(code, type);
		}

		moneyType.putAll(moneyTypes);

	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setAttribute("tradeCode", tradeCode);
		request.setAttribute("returnCode", returnCode);
		request.setAttribute("moneyType", moneyType);

		try {

			// 判断是否有权限
			int myPower = new OperatorModel().getPower(6, request);
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			// 从网页中读取信息
			String[] devno = ((TradeQueryForm) form).getDevno();// 设备信息
			String[] devtrcd = ((TradeQueryForm) form).getDevtrcd();// 交易信息
			String[] moneytype = ((TradeQueryForm) form).getMoneytype();// 币种
			String[] returnno = ((TradeQueryForm) form).getReturnno();// 返回码
			String bankid = ((TradeQueryForm) form).getBankid();// 机构编号
			String accno1 = ((TradeQueryForm) form).getAccno1();// 第一个账户
			String accno2 = ((TradeQueryForm) form).getAccno2();// 第二个账户
			String money1 = ((TradeQueryForm) form).getMoney1();// 起始金额
			String money2 = ((TradeQueryForm) form).getMoney2();// 截至金额
			String date1 = ((TradeQueryForm) form).getDate1();// 起始日期
			String date2 = ((TradeQueryForm) form).getDate2();// 截至日期

			if (devno == null) {
				devno = new String[0];
			}
			if (devtrcd == null) {
				devtrcd = new String[0];
			}
			if (moneytype == null) {
				moneytype = new String[0];
			}
			if (returnno == null) {
				returnno = new String[0];
			}

			// 是否包含子机构
			String incSubBank = (String) request.getParameter("includeSubBank");

			if (incSubBank != null) {
				bankid = new BankinfoModel().getBankRange(bankid);
			}

			// 查看查询的范围
			boolean f_trcdlog = request.getParameter("f_trcdlog").equals("true");
			boolean f_mxb = request.getParameter("f_mxb").equals("true");
			boolean f_mxb_tmp = request.getParameter("f_mxb_tmp").equals("true");

			request.setAttribute("f_trcdlog", request.getParameter("f_trcdlog"));
			request.setAttribute("f_mxb", request.getParameter("f_mxb"));
			request.setAttribute("f_mxb_tmp", request.getParameter("f_mxb_tmp"));

			String page = request.getParameter("page");

			TradeLogModel tradeLogModel = new TradeLogModel();
			if (page == null) {

				// 生产SQL查询语句
				String condition = tradeLogModel.setCondition(bankid, devno, accno1, accno2, devtrcd, moneytype, money1, money2, date1, date2, returnno);
				request.setAttribute("condition", condition);

				int cTrcdlog = 0; // 获得 交易流水表 zzt_trcdlog 中统计结果数量
				int cMxb = 0; // 获得交易明细表zzt_mxb 中统计结果数量
				int cMxb_tmp = 0; // 获得 交易明细历史表zzt_mxb_tmp 中统计结果数量

				if (f_trcdlog) {
					cTrcdlog = tradeLogModel.getTrcdlogCount(condition);
				}

				if (f_mxb) {
					cMxb = tradeLogModel.getMxbCount(condition);
				}

				if (f_mxb_tmp) {
					cMxb_tmp = tradeLogModel.getMxb_tmpCount(condition);
				}

				request.setAttribute("cTrcdlog", String.valueOf(cTrcdlog));
				request.setAttribute("cMxb", String.valueOf(cMxb));
				request.setAttribute("cMxb_tmp", String.valueOf(cMxb_tmp));
				// 获得总查询结果条数
				int totalRow = cTrcdlog + cMxb + cMxb_tmp;

				PageBean pb = new PageBean(totalRow, 10);
				request.setAttribute("PageBean", pb);

				List result = null;

				if (f_trcdlog) {
					result = tradeLogModel.getTrcdlog(condition, 0, pb.getPageSize());
				}

				if (result == null && f_mxb) {
					result = tradeLogModel.getMxb(condition, 0, pb.getPageSize());
				} else if (result != null && result.size() < 10 && f_mxb) {
					result.addAll(tradeLogModel.getMxb(condition, pb.beginRow + result.size() - cTrcdlog, pb.getPageSize() - result.size()));
				}

				if (result == null && f_mxb_tmp) {
					result = tradeLogModel.getMxb_tmp(condition, 0, pb.getPageSize());
				} else if (result != null && result.size() < 10 && f_mxb_tmp) {
					result.addAll(tradeLogModel.getMxb_tmp(condition, pb.beginRow + result.size() - cMxb - cTrcdlog, pb.getPageSize() - result.size()));
				}

				request.setAttribute("Result", result);

			} else { // 翻页
				log.info("翻页");
				int totalRow = Integer.parseInt(request.getParameter("totalRow"));
				PageBean pb = new PageBean(totalRow, 10);
				int currentPage;

				log.info("totalRow==>"+totalRow);
				// 获得当前页
				try {
					currentPage = Integer.parseInt(page);
				} catch (Exception e) {
					log.error("Integer.parseInt error: ",e);
					e.printStackTrace();
					currentPage = 1;
				}

				pb.setCurrentPage(currentPage);
				request.setAttribute("PageBean", pb);

				f_trcdlog = request.getParameter("f_trcdlog").equals("true");
				f_mxb = request.getParameter("f_mxb").equals("true");
				f_mxb_tmp = request.getParameter("f_mxb_tmp").equals("true");

				log.info("f_trcdlog==>"+f_trcdlog+"  f_mxb==>"+f_mxb+"  f_mxb_tmp==>"+f_mxb_tmp);
				
				request.setAttribute("f_trcdlog", String.valueOf(f_trcdlog));
				request.setAttribute("f_mxb", String.valueOf(f_mxb));
				request.setAttribute("f_mxb_tmp", String.valueOf(f_mxb_tmp));

				
				int cTrcdlog = Integer.parseInt(request.getParameter("cTrcdlog"));
				int cMxb = Integer.parseInt(request.getParameter("cMxb"));
				int cMxb_tmp = Integer.parseInt(request.getParameter("cMxb_tmp"));

				log.info("cTrcdlog==>"+cTrcdlog+"  cMxb==>"+cMxb+"  cMxb_tmp==>"+cMxb_tmp);
				
				request.setAttribute("cTrcdlog", String.valueOf(cTrcdlog));
				request.setAttribute("cMxb", String.valueOf(cMxb));
				request.setAttribute("cMxb_tmp", String.valueOf(cMxb_tmp));

				String condition = request.getParameter("condition");
				log.info("condition==>"+condition);
				request.setAttribute("condition", condition);

				List result = null;

				if (f_trcdlog) {
					result = tradeLogModel.getTrcdlog(condition, pb.beginRow, pb.getPageSize());
				}

				if (result == null && f_mxb) {
					result = tradeLogModel.getMxb(condition, pb.beginRow - cTrcdlog, pb.getPageSize());
				} else if (result != null && result.size() < 10 && f_mxb) {
					result.addAll(tradeLogModel.getMxb(condition, pb.beginRow + result.size() - cTrcdlog, pb.getPageSize() - result.size()));
				}

				if (result == null && f_mxb_tmp) {
					result = tradeLogModel.getMxb_tmp(condition, pb.beginRow - cTrcdlog - cMxb_tmp, pb.getPageSize());

				} else if (result != null && result.size() < 10 && f_mxb_tmp) {
					result.addAll(tradeLogModel.getMxb_tmp(condition, pb.beginRow + result.size() - cMxb - cTrcdlog, pb.getPageSize() - result.size()));
				}
				
				log.info("result==>"+result.size());
				
				request.setAttribute("Result", result);
			}

		} catch (Exception e) {
			log.error("ERROR", e);
			e.printStackTrace();
			return mapping.findForward("SystemError");
		}
		return mapping.findForward("TradeQuery");
	}
}