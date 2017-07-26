package com.lcjr.pvxp.ini;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

public class SpecialCardAddAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward myForward = mapping.findForward("SystemError");
		PubUtil pubUtil = new PubUtil();
		String operid = pubUtil.dealNull(pubUtil.getValueFromCookie(
				Constants.COOKIE_OPER_OPERID, request));
		int myPower = new OperatorModel().getPower(0, request);
		if (myPower == 0) {
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,
					"pvxp.errors.op.no.power");
			request.removeAttribute(mapping.getAttribute());
			return mapping.findForward("SystemError");
		}

		HttpSession session = request.getSession();
		IniOperation ini = (IniOperation) session.getAttribute("ini");
		if (ini == null) {
			ini = new IniOperation(pubUtil.ReadConfig("iniPath", "ini", "",
					"PowerView.ini"));
			session.setAttribute("ini", ini);
		}
		StringBuffer all = ini.getAll();
		String allStr = all.substring(0, all.length());

		SpecialCardForm specialCard = (SpecialCardForm) form;
		String card = specialCard.getCard().trim();
		String cardType = specialCard.getCardType().trim();
		String outRate = specialCard.getOutRate().trim();
		String outRateUp = specialCard.getOutRateUp().trim();
		String outRateLow = specialCard.getOutRateLow().trim();
		String inRate = specialCard.getInRate().trim();
		String inRateUp = specialCard.getInRateUp().trim();
		String inRateLow = specialCard.getInRateLow();
		String remark = specialCard.getRemark().trim();
		if("".equals(remark)){
			remark=" ";
		}
		remark = PubUtil.native2unicode(remark);

		String value = card + "|" + cardType + "|" + outRate
				+ "|" + outRateLow + "|" + outRateUp + "|" + inRate + "|"
				+ inRateLow + "|" + inRateUp + "|" + remark;
		ini.add("SPECCARD", value);
		try {
			ini.write();
			myForward = mapping.findForward("iniSpecialCardAdded");
			request.setAttribute("message", "Ìí¼Ó³É¹¦");
		} catch (Exception e) {
			ini.setAll(new StringBuffer(allStr));
		}
		return myForward;
	}

}
