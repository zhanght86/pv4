package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> �̿���ˮɾ��ActionForm
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
 * @version 1.0 2005/3/31
 */
public final class EatCardLogDeleteForm extends ActionForm {

	private String fromdate = null;

	public String getFromdate() {
		return (this.fromdate);
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.fromdate = null;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		PubUtil myPubUtil = new PubUtil();
		if (fromdate == null || fromdate.length() == 0) {
			errors.add("deleatcardlog", new ActionMessage("pvxp.syssetup.deleclog.nofromdate"));
			return errors;
		}
		if (!myPubUtil.isDate8(fromdate)) {
			errors.add("deleatcardlog", new ActionMessage("pvxp.errors.wrongdate8", fromdate));
			return errors;
		}
		return errors;
	}
}