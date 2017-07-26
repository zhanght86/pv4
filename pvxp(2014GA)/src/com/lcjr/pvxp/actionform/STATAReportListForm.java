package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

//import org.apache.log4j.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 自动报表列表ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/04/12
 */
public final class STATAReportListForm extends ActionForm {

	private String opentag = "1";
	private String[] arryDel;

	//getter
	public String getOpentag() {
		return this.opentag;
	}

	public String[] getArryDel() {
		return this.arryDel;
	}

	//setter
	public void setOpentag(String opentag) {
		this.opentag = opentag;
	}

	public void setArryDel(String[] arryDel) {
		this.arryDel = arryDel;
	}

}
