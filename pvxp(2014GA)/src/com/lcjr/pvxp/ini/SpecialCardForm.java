package com.lcjr.pvxp.ini;

import org.apache.struts.action.ActionForm;

public class SpecialCardForm extends ActionForm {
	private String card;
	private String cardType;
	private String outRate;
	private String outRateUp;
	private String outRateLow;
	private String inRate;
	private String inRateUp;
	private String inRateLow;
	private String remark;

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getOutRate() {
		return outRate;
	}

	public void setOutRate(String outRate) {
		this.outRate = outRate;
	}

	public String getOutRateUp() {
		return outRateUp;
	}

	public void setOutRateUp(String outRateUp) {
		this.outRateUp = outRateUp;
	}

	public String getOutRateLow() {
		return outRateLow;
	}

	public void setOutRateLow(String outRateLow) {
		this.outRateLow = outRateLow;
	}

	public String getInRate() {
		return inRate;
	}

	public void setInRate(String inRate) {
		this.inRate = inRate;
	}

	public String getInRateUp() {
		return inRateUp;
	}

	public void setInRateUp(String inRateUp) {
		this.inRateUp = inRateUp;
	}

	public String getInRateLow() {
		return inRateLow;
	}

	public void setInRateLow(String inRateLow) {
		this.inRateLow = inRateLow;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
