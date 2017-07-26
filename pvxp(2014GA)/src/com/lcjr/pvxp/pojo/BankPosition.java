package com.lcjr.pvxp.pojo;

import com.lcjr.pvxp.util.*;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 设备地图中子机构对象
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
public class BankPosition {
	private String banid;
	private String xpos;
	private String ypos;

	public BankPosition() {
	}

	/**
	 * <p>
	 * 用字符串为对象设置属性
	 * </p>
	 * 
	 * @param allstr
	 *            字符串：机构编号,XPOS,YPOS
	 * @return boolean
	 */
	public BankPosition newPosition(String allstr) {
		try {
			PubUtil myPubUtil = new PubUtil();
			String[] tmp = myPubUtil.split(allstr, ",");
			this.setBankid(tmp[0]);
			this.setXpos(tmp[1]);
			this.setYpos(tmp[2]);
			return this;
		} catch (Exception e) {
			return null;
		}
	}

	public void setBankid(String banid) {
		this.banid = banid;
	}

	public void setXpos(String xpos) {
		this.xpos = xpos;
	}

	public void setYpos(String ypos) {
		this.ypos = ypos;
	}

	public String getBankid() {
		return this.banid;
	}

	public String getXpos() {
		return this.xpos;
	}

	public String getYpos() {
		return this.ypos;
	}

}
