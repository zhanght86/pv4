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

public class IniAddAreaAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward myForward = mapping.findForward("SystemError");
		PubUtil pubUtil = new PubUtil();
		String operid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
		int myPower = new OperatorModel().getPower(0, request);
		if (myPower == 0) {
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
			request.removeAttribute(mapping.getAttribute());
			return mapping.findForward("SystemError");
		}
		
		AreaForm areaForm = (AreaForm) form;
		String areaNo = areaForm.getAreaNo();
		IniOperation ini = (IniOperation) request.getSession().getAttribute("ini");
		StringBuffer all = ini.getAll();
		String allStr = all.substring(0, all.length());
		if (ini.getValue("AREANO", areaNo) != null) {
			request.setAttribute("message", "该地区编号已经存在");
			myForward = mapping.findForward("addAreaDone");
		} else {
			String areaName = areaForm.getAreaName();
			areaName = PubUtil.native2unicode(areaName);
			ini.add("AREANO", areaNo, areaNo + "|" + areaName);
			try {
				ini.write();
				myForward = mapping.findForward("addAreaDone");
				request.setAttribute("message", "添加成功");
			} catch (Exception e) {
				ini.setAll(new StringBuffer(allStr));
			}
		}
		return myForward;
	}
	
}
