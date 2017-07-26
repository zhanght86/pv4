package com.lcjr.pvxp.pojo;


/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 设备地图对象
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author 杨旭
 * @version 1.0 2005/3/11
 */
public class DevMap {
	private String imgname;
	private String owner;
	private String lastediter;
	private String lastedittime;
	private int banknum;
	private BankPosition[] banks;
	private int devnum;
	private DevPosition[] devs;

	public DevMap() {
	}

	public void setImgname(String imgname) {
		this.imgname = imgname;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setLastediter(String lastediter) {
		this.lastediter = lastediter;
	}

	public void setLastedittime(String lastedittime) {
		this.lastedittime = lastedittime;
	}

	public void setBanknum(int banknum) {
		this.banknum = banknum;
	}

	public void setBanks(BankPosition[] banks) {
		this.banks = banks;
	}

	public void setDevnum(int devnum) {
		this.devnum = devnum;
	}

	public void setDevs(DevPosition[] devs) {
		this.devs = devs;
	}

	public String getImgname() {
		return this.imgname;
	}

	public String getOwner() {
		return this.owner;
	}

	public String getLastediter() {
		return this.lastediter;
	}

	public String getLastedittime() {
		return this.lastedittime;
	}

	public int getBanknum() {
		return this.banknum;
	}

	public BankPosition[] getBanks() {
		return this.banks;
	}

	public int getDevnum() {
		return this.devnum;
	}

	public DevPosition[] getDevs() {
		return this.devs;
	}
}
