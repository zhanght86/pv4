package com.lcjr.pvxp.pojo;

import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 设备地图中设备对象
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
public class DevPosition {
	private String devno;
	private String xpos;
	private String ypos;

	public DevPosition() {
	}

	/**
	 * <p>
	 * 用字符串为对象设置属性
	 * </p>
	 * 
	 * @param allstr
	 *            字符串：设备编号,XPOS,YPOS
	 * @return boolean
	 */
	public DevPosition newPosition(String allstr) {
		try {
			PubUtil myPubUtil = new PubUtil();
			String[] tmp = myPubUtil.split(allstr, ",");
			this.setDevno(tmp[0]);
			this.setXpos(tmp[1]);
			this.setYpos(tmp[2]);
			return this;
		} catch (Exception e) {
			return null;
		}
	}

	public void setDevno(String devno) {
		this.devno = devno;
	}

	public void setXpos(String xpos) {
		this.xpos = xpos;
	}

	public void setYpos(String ypos) {
		this.ypos = ypos;
	}

	public String getDevno() {
		return this.devno;
	}

	public String getXpos() {
		return this.xpos;
	}

	public String getYpos() {
		return this.ypos;
	}

}
