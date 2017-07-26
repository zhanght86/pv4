package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.lcjr.pvxp.actionform.SystemSetDataForm;
import com.lcjr.pvxp.bean.SysParamBean;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.orm.SysParam;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 系统设置-数据设置Action
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
 * @author 杨旭
 * @version 1.0 2005/03/28
 */
public final class SystemSetDataAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();
		// OplogModel myOplogModel = new OplogModel();
		PubUtil myPubUtil = new PubUtil();
		String kmofmxb = myPubUtil.dealNull((String) ((SystemSetDataForm) form).getKmofmxb()).trim();
		String kmofjytjday = myPubUtil.dealNull((String) ((SystemSetDataForm) form).getKmofjytjday()).trim();
		String kmofjytjmonth = myPubUtil.dealNull((String) ((SystemSetDataForm) form).getKmofjytjmonth()).trim();
		String kmofjytjyear = myPubUtil.dealNull((String) ((SystemSetDataForm) form).getKmofjytjyear()).trim();
		String kmofsbtjday = myPubUtil.dealNull((String) ((SystemSetDataForm) form).getKmofsbtjday()).trim();
		String kmofsbtjmonth = myPubUtil.dealNull((String) ((SystemSetDataForm) form).getKmofsbtjmonth()).trim();
		String kmofsbtjyear = myPubUtil.dealNull((String) ((SystemSetDataForm) form).getKmofsbtjyear()).trim();
		String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request));
		ActionForward myforward = mapping.findForward("SystemError");

		try {
			String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (!authlist.equals("*")) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return myforward;
			}

			SysParam tmp = SysParamBean.getSysParam();

			kmofmxb = String.valueOf(Integer.parseInt(kmofmxb));

			kmofjytjday = String.valueOf(Integer.parseInt(kmofjytjday));
			kmofjytjmonth = String.valueOf(Integer.parseInt(kmofjytjmonth));
			kmofjytjyear = String.valueOf(Integer.parseInt(kmofjytjyear));

			kmofsbtjday = String.valueOf(Integer.parseInt(kmofsbtjday));
			kmofsbtjmonth = String.valueOf(Integer.parseInt(kmofsbtjmonth));
			kmofsbtjyear = String.valueOf(Integer.parseInt(kmofsbtjyear));

			tmp.setKmofmxb(kmofmxb);
			tmp.setKmofjytjday(kmofjytjday);
			tmp.setKmofjytjmonth(kmofjytjmonth);
			tmp.setKmofjytjyear(kmofjytjyear);
			tmp.setKmofsbtjday(kmofsbtjday);
			tmp.setKmofsbtjmonth(kmofsbtjmonth);
			tmp.setKmofsbtjyear(kmofsbtjyear);

			if (SysParamBean.updateSysParam(tmp)) { // 更新成功
				OplogModel.insertOplog(operid, "0", "sys", "pvxp.oplog.syssetup.setdata.success");
				errors.add("result", new ActionMessage("pvxp.syssetup.setdata.success"));
				myforward = new ActionForward(mapping.getInput());
			} else {
				OplogModel.insertOplog(operid, "0", "sys", "pvxp.oplog.syssetup.setdata.failed");
				errors.add("result", new ActionMessage("pvxp.syssetup.setdata.failed"));
				myforward = new ActionForward(mapping.getInput());
			}

			if (!errors.isEmpty()) {
				saveErrors(request, errors);
			}

			request.removeAttribute(mapping.getAttribute());
			return myforward;
		} catch (Exception e) {
			myforward = mapping.findForward("SystemError");
			request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.system.syserror");
			request.removeAttribute(mapping.getAttribute());
			return myforward;
		}
	}
}