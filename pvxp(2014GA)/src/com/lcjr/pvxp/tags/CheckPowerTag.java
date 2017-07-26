package com.lcjr.pvxp.tags;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.OperatorModel;

import javax.servlet.http.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> ��֤����Ա����Ȩ�޵ı�ǩ
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
 * @author ����
 * @version 1.0 2005/03/28
 */
public final class CheckPowerTag extends TagSupport {
	
	
	/** ��Ӧ�Ĺ��ܱ�� */
	private String funcid = null;
	
	
	
	/** ��Ҫ����СȨ��ֵ��0-û���κ�Ȩ�� 1-�����Ȩ�� 2-�����д����Ȩ�� 3-ϵͳ����Ա */
	private String minpower = null;
	
	
	private String errorpage = Constants.SYS_ERROR_JSP;
	
	
	
	public void setFuncid(String funcid) {
		this.funcid = funcid;
	}
	
	
	public String getFuncid() {
		return this.funcid;
	}
	
	
	public void setMinpower(String minpower) {
		this.minpower = minpower;
	}
	
	
	public String getMinpower() {
		return this.minpower;
	}
	
	
	public void setErrorpage(String errorpage) {
		this.errorpage = errorpage;
	}
	
	
	public String getErrorpage() {
		return this.errorpage;
	}
	
	
	public void release() {
		super.release();
		this.funcid = funcid;
		this.minpower = minpower;
		this.errorpage = Constants.SYS_ERROR_JSP;
	}
	
	
	public int doEndTag() throws JspException {
		boolean valid = false;
		HttpServletRequest request = null;
		try {
			request = (HttpServletRequest) pageContext.getRequest();
			if (request != null) {
				OperatorModel myOperatorModel = new OperatorModel();
				if (myOperatorModel.getPower(Integer.parseInt(funcid), request) >= Integer.parseInt(minpower))
					valid = true;
				else
					valid = false;
			}
		} catch (Exception e) {
			valid = false;
		}
		
		if (valid) {
			return (EVAL_PAGE);
		} else {
			try {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				pageContext.forward(errorpage);
			} catch (Exception e) {
				throw new JspException(e.toString());
			}
			return (SKIP_PAGE);
		}
	}
	
	
	public int doStartTag() throws JspException {
		return (SKIP_BODY);
	}
	
}
