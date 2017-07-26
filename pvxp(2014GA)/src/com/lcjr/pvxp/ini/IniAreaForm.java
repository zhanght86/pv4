package com.lcjr.pvxp.ini;

import org.apache.struts.action.ActionForm;

public class IniAreaForm extends ActionForm {
	private String[] areaList;

	public String[] getAreaList() {
		return areaList;
	}

	public void setAreaList(String[] areaList) {
		this.areaList = areaList;
	}
}
