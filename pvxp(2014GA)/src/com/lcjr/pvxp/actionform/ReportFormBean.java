package com.lcjr.pvxp.actionform;

public class ReportFormBean {
	private String name;
	private String bank;
	private String owner;
	private String type;
	private String currentflag;

	//getter
	public String getName() {
		return this.name;
	}

	public String getBank() {
		return this.bank;
	}

	public String getOwner() {
		return this.owner;
	}

	public String getType() {
		return this.type;
	}

	public String getCurrentflag() {
		return this.currentflag;
	}

	//setter
	public void setName(String name) {
		this.name = name;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCurrentflag(String currentflag) {
		this.currentflag = currentflag;
	}

}