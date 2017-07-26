package com.lcjr.pvxp.tags;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.OperatorModel;

import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> ��֤����Ա�Ƿ�Ϊ�Ѿ���¼������û�
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
public final class IsPlugUserInTag extends TagSupport {
	/** ��Ӧ�Ĺ��ܱ�� */
	private String plugid = null;
	
	
	private String errorpage = Constants.SYS_ERROR_JSP;
	
	
	
	public void setPlugid(String plugid) {
		this.plugid = plugid;
	}
	
	
	public String getPlugid() {
		return this.plugid;
	}
	
	
	public void setErrorpage(String errorpage) {
		this.errorpage = errorpage;
	}
	
	
	public String getErrorpage() {
		return this.errorpage;
	}
	
	
	public void release() {
		super.release();
		this.plugid = plugid;
		this.errorpage = Constants.SYS_ERROR_JSP;
	}
	
	
	public int doEndTag() throws JspException {
		boolean valid = false;
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			request = (HttpServletRequest) pageContext.getRequest();
			if (request != null) {
				OperatorModel myOperatorModel = new OperatorModel();
				if (myOperatorModel.isPluginUserIn(plugid, request)) {
					valid = true;
				} else {
					valid = false;
				}
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
