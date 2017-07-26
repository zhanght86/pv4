package com.lcjr.pvxp.ini;

import org.apache.struts.action.ActionForm;

public class CardTypeDeleteForm extends ActionForm {
	private String[] cardTypeList;

	public String[] getCardTypeList() {
		return cardTypeList;
	}

	public void setCardTypeList(String[] cardTypeList) {
		this.cardTypeList = cardTypeList;
	}

}
