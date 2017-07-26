package com.lcjr.pvxp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.stereotype.Component;

import com.lcjr.pvxp.actionform.DevWSMoniListForm;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.orm.Operator;

/**
 * 
 * @author
 * @version pvxp(2014GA)
 * @date 2014-11-4
 */
@Component("/devWSMoniList")
public class DevWSMoniListAction extends DispatchAction {

	Logger log = Logger.getLogger(DevWSMoniListAction.class.getName());

	/**
	 * 根据操作管理员等级筛选设备
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DevWSMoniListForm forms = (DevWSMoniListForm) form;
		String operatorId = forms.getOperid();
		log.info("开始查询CashDetailAction");

		DevinfoModel dm = new DevinfoModel();
		OperatorModel om = new OperatorModel();
		Operator op = om.getOperator(operatorId);
		String bankRage = new BankinfoModel().getBankRange(op.getBankid().trim());
		
		List<Devinfo> devInfoList = dm.getDevListOfBank(bankRage);
		
		ActionForward forward = mapping.findForward("Success");
		request.setAttribute("devInfoList", devInfoList);
		return (forward);
	}

}
