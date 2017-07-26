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
 * <b>Description:</b> ���ײ�ѯAction
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * <br>
 * 
 * @author ��ѧ��
 * @version 1.0 2005/03/25
 */
public final class TradeQueryAction extends Action {

	Logger log = Logger.getLogger(TradeQueryAction.class.getName());

	/**
	 * ���״���
	 */
	private HashMap tradeCode = new HashMap();

	/**
	 * ������
	 */
	private HashMap returnCode = new HashMap();

	/**
	 * ����
	 */
	private HashMap moneyType = new HashMap();

	/**
	 * ���ֱ���
	 */
	private HashMap moneyTypes = new HashMap();

	/**
	 * ���캯����<font size='2' color='red'>��Ҫ��ɴ�ini�ļ����ж�ȡTrade.ini�����ļ���Ϣ</font>
	 * 
	 */
	public TradeQueryAction() {
		PubUtil pubUtil = new PubUtil();
		CharSet myCharSet = new CharSet();

		iniread myiniread = new iniread("Trade.ini");// �޸Ķ������ļ����� xucc
		// 20090625
		String tempStr;
		String code;
		String type;

		int TCode_Num = Integer.parseInt(myiniread.getIni("TCode_Num").trim()); // ����������Ŀ

		for (int i = 0; i < TCode_Num; i++) {
			tempStr = pubUtil.dealNull(myiniread.getIni("TCode_" + String.valueOf(i + 1)));
//			tempStr = myCharSet.form2GB(tempStr.trim());

			int pos = tempStr.indexOf(',');
			code = tempStr.substring(0, pos);
			type = tempStr.substring(pos + 1);
			tradeCode.put(code, type);
		}

		int RCode_Num = Integer.parseInt(myiniread.getIni("RCode_Num").trim()); // ��������Ŀ
		for (int i = 0; i < RCode_Num; i++) {
			tempStr = pubUtil.dealNull(myiniread.getIni("RCode_" + String.valueOf(i + 1)));
//			tempStr = myCharSet.form2GB(tempStr.trim());

			int pos = tempStr.indexOf(',');
			code = tempStr.substring(0, pos);
			type = tempStr.substring(pos + 1);
			returnCode.put(code, type);
		}

		int MoneyType_Num = Integer.parseInt(myiniread.getIni("MoneyType_Num").trim());// ������Ŀ
		for (int i = 0; i < MoneyType_Num; i++) {
			tempStr = pubUtil.dealNull(myiniread.getIni("Type_" + String.valueOf(i + 1)));
//			tempStr = myCharSet.form2GB(tempStr.trim());

			int pos = tempStr.indexOf(',');
			code = tempStr.substring(0, pos);
			type = tempStr.substring(pos + 1);
			moneyType.put(code, type);
		}

		int MoneyTypes_Num = Integer.parseInt(myiniread.getIni("MoneyTypes_Num").trim());// ������Ŀ
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

			// �ж��Ƿ���Ȩ��
			int myPower = new OperatorModel().getPower(6, request);
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			// ����ҳ�ж�ȡ��Ϣ
			String[] devno = ((TradeQueryForm) form).getDevno();// �豸��Ϣ
			String[] devtrcd = ((TradeQueryForm) form).getDevtrcd();// ������Ϣ
			String[] moneytype = ((TradeQueryForm) form).getMoneytype();// ����
			String[] returnno = ((TradeQueryForm) form).getReturnno();// ������
			String bankid = ((TradeQueryForm) form).getBankid();// �������
			String accno1 = ((TradeQueryForm) form).getAccno1();// ��һ���˻�
			String accno2 = ((TradeQueryForm) form).getAccno2();// �ڶ����˻�
			String money1 = ((TradeQueryForm) form).getMoney1();// ��ʼ���
			String money2 = ((TradeQueryForm) form).getMoney2();// �������
			String date1 = ((TradeQueryForm) form).getDate1();// ��ʼ����
			String date2 = ((TradeQueryForm) form).getDate2();// ��������

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

			// �Ƿ�����ӻ���
			String incSubBank = (String) request.getParameter("includeSubBank");

			if (incSubBank != null) {
				bankid = new BankinfoModel().getBankRange(bankid);
			}

			// �鿴��ѯ�ķ�Χ
			boolean f_trcdlog = request.getParameter("f_trcdlog").equals("true");
			boolean f_mxb = request.getParameter("f_mxb").equals("true");
			boolean f_mxb_tmp = request.getParameter("f_mxb_tmp").equals("true");

			request.setAttribute("f_trcdlog", request.getParameter("f_trcdlog"));
			request.setAttribute("f_mxb", request.getParameter("f_mxb"));
			request.setAttribute("f_mxb_tmp", request.getParameter("f_mxb_tmp"));

			String page = request.getParameter("page");

			TradeLogModel tradeLogModel = new TradeLogModel();
			if (page == null) {

				// ����SQL��ѯ���
				String condition = tradeLogModel.setCondition(bankid, devno, accno1, accno2, devtrcd, moneytype, money1, money2, date1, date2, returnno);
				request.setAttribute("condition", condition);

				int cTrcdlog = 0; // ��� ������ˮ�� zzt_trcdlog ��ͳ�ƽ������
				int cMxb = 0; // ��ý�����ϸ��zzt_mxb ��ͳ�ƽ������
				int cMxb_tmp = 0; // ��� ������ϸ��ʷ��zzt_mxb_tmp ��ͳ�ƽ������

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
				// ����ܲ�ѯ�������
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

			} else { // ��ҳ
				log.info("��ҳ");
				int totalRow = Integer.parseInt(request.getParameter("totalRow"));
				PageBean pb = new PageBean(totalRow, 10);
				int currentPage;

				log.info("totalRow==>"+totalRow);
				// ��õ�ǰҳ
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