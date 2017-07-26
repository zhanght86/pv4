package com.lcjr.pvxp.ini;

import org.apache.struts.action.ActionForm;

public class AreaForm extends ActionForm {
	String areaNo;
	String areaName;

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
