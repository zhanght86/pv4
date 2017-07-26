package com.lcjr.pvxp.ini;

import org.apache.struts.action.ActionForm;

public class SpecialCardDeleteForm extends ActionForm {
	String[] specialCardList;

	public String[] getSpecialCardList() {
		return specialCardList;
	}

	public void setSpecialCardList(String[] specialCardList) {
		this.specialCardList = specialCardList;
	}
}
