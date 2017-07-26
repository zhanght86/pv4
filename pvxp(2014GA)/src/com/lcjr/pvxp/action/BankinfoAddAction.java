package com.lcjr.pvxp.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.lcjr.pvxp.actionform.BankinfoAddForm;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.orm.Bankinfo;
import com.lcjr.pvxp.util.CharSet;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 添加机构Action
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
 * @author 刘太沛
 * @version 1.0 2005/3/1<br>
 *          1.1 2005/3/10
 */
public final class BankinfoAddAction extends Action {

	Logger log = Logger.getLogger(BankinfoAddAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("execute开始");
		ActionMessages errors = new ActionMessages();
		ActionForward myforward = mapping.findForward("SystemError");
		CharSet myCharSet = new CharSet();
		PubUtil myPubUtil = new PubUtil();

		String mybankid = "";

		String mybanklevel = (String) ((BankinfoAddForm) form).getBanklevel().trim();
		String myparent_bankid = (String) ((BankinfoAddForm) form).getParent_bankid().trim();
		String mybankname = myCharSet.form2db((String) ((BankinfoAddForm) form).getBankname()).trim();
		String mybankaddr = myCharSet.form2db((String) ((BankinfoAddForm) form).getbankaddr()).trim();
		String mybanktel = myCharSet.form2db((String) ((BankinfoAddForm) form).getBanktel()).trim();
		String mybankstate = "0";

		if (mybankname == null || mybankname.length() == 0) {
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.submit.wrong");
			request.removeAttribute(mapping.getAttribute());
			return myforward;
		}

		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			String myPower = "";
			if (authlist.equals("*")) {
				// 超级管理员
				myPower = "*";
			} else if (authlist.length() > 0 && Integer.parseInt(authlist.substring(1, 2)) > 0) {
				// 有浏览以上级别权限
				myPower = authlist.substring(1, 2);
			} else {
				// 没有权限
				myPower = "0";
			}
			// 保存当前操作的权限
			request.setAttribute(Constants.REQUEST_OPER_POWER, myPower);
			// 没有权限报错
			if (myPower.equals("0")) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			BankinfoModel myBankinfoModel = new BankinfoModel();
			Bankinfo myBankinfo = new Bankinfo();
			String banktag = Integer.toString((Integer.parseInt(mybanklevel) - 1));
			if (banktag.equals("0")) {
				for (int i = 0; i < Constants.INT_BANKID_LEN; i++) {
					myparent_bankid = myparent_bankid + "0";
				}
			}
			Vector<Bankinfo> myVector = myBankinfoModel.getSubBank(myparent_bankid, banktag);

			if (myVector == null) {
				myforward = mapping.findForward("SystemError");
				errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}
			// 筛选出添加的机构同级别的机构
			for (int i = 0; i < myVector.size(); i++) {
				myBankinfo = (Bankinfo) myVector.get(i);
				if (!(myBankinfo.getBanktag().trim()).equals(mybanklevel)) {
					myVector.remove(i);
					i--;
				}
			}

			String mybankidtemp = "";
			String mybanktagtemp = "";
			String mybankflag = "";
			String mybankflagtemp = "";
			int mybankflaglen = 0;

			// 生成添加的机构编号
			if (myVector.size() > 0) {
				// 当前级别已经有机构
				myBankinfo = (Bankinfo) myVector.get(myVector.size() - 1);
				mybankidtemp = myBankinfo.getBankid().trim();
				mybanktagtemp = myBankinfo.getBanktag().trim();
				mybankflag = myBankinfoModel.getBankRange(mybankidtemp);
				if (mybankflag.indexOf("A") != -1) {
					mybankflag = mybankflag.substring(0, mybankflag.length() - 1);
				}
				mybankflaglen = Constants.INT_BANKLEVEL_LEN[Integer.parseInt(mybanktagtemp) - 1];
				mybankflagtemp = mybankflag.substring((mybankflag.length() - mybankflaglen), mybankflag.length());
				mybankflagtemp = Integer.toString(Integer.parseInt(mybankflagtemp) + 1);
				mybankflagtemp = PubUtil.strFormat(mybankflagtemp, mybankflaglen, 1, '0');
				mybankid = mybankflag.substring(0, (mybankflag.length() - mybankflaglen)) + mybankflagtemp;
			} else {
				// 当前级别没有机构
				mybankidtemp = myparent_bankid;
				mybankflag = myBankinfoModel.getBankRange(mybankidtemp);
				if (mybankflag.indexOf("A") != -1) {
					mybankflag = mybankflag.substring(0, mybankflag.length() - 1);
				}
				mybankflaglen = Constants.INT_BANKLEVEL_LEN[Integer.parseInt(mybanklevel) - 1];
				mybankflagtemp = Integer.toString(1);
				mybankflagtemp = PubUtil.strFormat(mybankflagtemp, mybankflaglen, 1, '0');
				if (Integer.parseInt(mybanklevel) == 1) {
					mybankid = mybankflagtemp;
				} else if (Integer.parseInt(mybanklevel) > 1) {
					mybankid = mybankflag + mybankflagtemp;
				}
			}
			for (int i = mybankid.length(); i < Constants.INT_BANKID_LEN; i++) {
				mybankid = mybankid + "0";
			}

			Bankinfo temp = (Bankinfo) BankinfoModel.getBankinfoFromList(mybankid);
			// 机构编号已存在
			if (temp != null) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.bankinfo.exist");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			myBankinfo = new Bankinfo();

			myBankinfo.setBankid((String) mybankid);
			myBankinfo.setBanknm((String) mybankname);
			myBankinfo.setBankaddr((String) mybankaddr);
			myBankinfo.setBanktel((String) mybanktel);
			myBankinfo.setState((String) mybankstate);

			myBankinfo.setBanktag((String) mybanklevel);
			myBankinfo.setAdddate((String) myPubUtil.getNowDate(1));
			myBankinfo.setRemark1("");
			myBankinfo.setRemark2("");

			int iRetflag = BankinfoModel.addBank(myBankinfo);
			// 添加信息写数据库失败
			if (iRetflag != 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.bankinfo.adderror");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			BankinfoModel.reset();

			// 记录操作员操作流水
			request.setAttribute(Constants.REQUEST_BANKINFO, (Bankinfo) BankinfoModel.getBankinfoFromList(mybankid));

			myforward = mapping.findForward("Bankinfo_AddDone");
		} catch (Exception e) {
			log.error("execute", e);
			myforward = mapping.findForward("SystemError");
			errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
		}

		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}