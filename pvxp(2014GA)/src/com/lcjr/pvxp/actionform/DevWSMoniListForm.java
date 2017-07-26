package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @author
 * @version pvxp(2014GA)
 * @date 2014-11-4
 */
public class DevWSMoniListForm extends ActionForm {

	/**
	 * ²Ù×÷Ô±±àºÅ
	 */
	private String operid = null;

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.operid = "wukp";
	}

	/**
	 * @return the operid
	 */
	public String getOperid() {
		return operid;
	}

	/**
	 * @param operid
	 *            the operid to set
	 */
	public void setOperid(String operid) {
		this.operid = operid;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return super.validate(mapping, request);
	}

}
