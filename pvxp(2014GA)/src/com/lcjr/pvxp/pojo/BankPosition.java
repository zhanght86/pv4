package com.lcjr.pvxp.pojo;

import com.lcjr.pvxp.util.*;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> �豸��ͼ���ӻ�������
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * 
 * @author ����
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
	 * ���ַ���Ϊ������������
	 * </p>
	 * 
	 * @param allstr
	 *            �ַ������������,XPOS,YPOS
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
