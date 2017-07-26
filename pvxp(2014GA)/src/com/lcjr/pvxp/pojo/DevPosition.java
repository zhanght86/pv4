package com.lcjr.pvxp.pojo;

import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> �豸��ͼ���豸����
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
public class DevPosition {
	private String devno;
	private String xpos;
	private String ypos;

	public DevPosition() {
	}

	/**
	 * <p>
	 * ���ַ���Ϊ������������
	 * </p>
	 * 
	 * @param allstr
	 *            �ַ������豸���,XPOS,YPOS
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
