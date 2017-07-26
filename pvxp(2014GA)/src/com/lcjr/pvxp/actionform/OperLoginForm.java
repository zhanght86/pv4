package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 操作员登录处理ActionForm
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
 * @version 1.0 2004/12/14
 */
public final class OperLoginForm extends ActionForm {

	private String operid = null;
	private String operpwd = null;

	public String getOperid() {
		return (this.operid);
	}

	public String getOperpwd() {
		return (this.operpwd);
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}

	public void setOperpwd(String operpwd) {
		this.operpwd = operpwd;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.operid = null;
		this.operpwd = null;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		// 定义错误信息
		ActionErrors errors = new ActionErrors();

		if ((operid == null) || (operid.length() < 1)) {
			errors.add("system", new ActionMessage("pvxp.errors.form.no.operid"));
			return errors;
		}

		if ((operpwd == null) || (operpwd.length() < 1)) {
			errors.add("system", new ActionMessage("pvxp.errors.form.no.operpwd"));
			return errors;
		}
		return errors;

	}

}