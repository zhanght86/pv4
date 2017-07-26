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

public class CardTypeDeleteAction extends Action {

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
			session.setAttribute("ini",ini);
		}
		StringBuffer all = ini.getAll();
		String allStr = all.substring(0,all.length());
		CardTypeDeleteForm cardTypeDeleteForm = (CardTypeDeleteForm)form;
		String[] cardTypeList = cardTypeDeleteForm.getCardTypeList();
		ini.remove("CARDTYPE", cardTypeList);
		try{
			ini.write();
			myForward=mapping.findForward("iniCardTypeDeleted");
			request.setAttribute("message", "É¾³ý³É¹¦");
		} catch(Exception e){
			ini.setAll(new StringBuffer(allStr));
		}
		return myForward;
	}
	
}
