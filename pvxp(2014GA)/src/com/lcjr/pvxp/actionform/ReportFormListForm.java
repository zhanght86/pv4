package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

//import org.apache.log4j.*;

public final class ReportFormListForm extends ActionForm {

	//private Logger log = Logger.getLogger("wuxk");

	private String currentflag = "0";
	/**
	 * 要删除的选项数组
	 */
	private String[] arryDel;

	//getter
	public String getCurrentflag() {
		return this.currentflag;
	}

	public String[] getArryDel() {
		return this.arryDel;
	}

	//setter
	public void setCurrentflag(String currentflag) {
		this.currentflag = currentflag;
	}

	public void setArryDel(String[] arryDel) {
		this.arryDel = arryDel;
	}

}
