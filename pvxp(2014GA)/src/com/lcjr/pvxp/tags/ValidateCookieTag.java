package com.lcjr.pvxp.tags;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.Operator;

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
 * <b>Description:</b> Cookie登录信息验证
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
 * @version 1.0 2004/12/15
 */
public final class ValidateCookieTag extends TagSupport {
	private String operidname = Constants.COOKIE_OPER_OPERID;
	
	private String loginpage = Constants.SYS_LOGIN_JSP;
	
	
	
	public void setOperidname(String operidname) {
		this.operidname = operidname;
	}
	
	public void setLoginpage(String loginpage) {
		this.loginpage = loginpage;
	}
	
	
	public String getOperidname() {
		return (this.operidname);
	}
	
	public String getLoginpage() {
		return (this.loginpage);
	}
	
	
	public void release() {
		super.release();
		this.operidname = Constants.COOKIE_OPER_OPERID;
		this.loginpage = Constants.SYS_LOGIN_JSP;
	}
	
	
	public int doEndTag() throws JspException {
		boolean valid = false;
		
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			if (request != null) {
				PubUtil myPubUtil = new PubUtil();
				String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(operidname, request)).trim();
				if (operid.length() == 0) {
					valid = false;
				} else {
					valid = true;
				}
			}
		} catch (Exception e) {
			valid = false;
		}
		
		if (valid)
			return (EVAL_PAGE);
		else {
			try {
				pageContext.forward(loginpage);
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
