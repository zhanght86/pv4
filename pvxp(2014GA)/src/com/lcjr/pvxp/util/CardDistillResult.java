package com.lcjr.pvxp.util;

/**
 * 用于存储从数据库中查询出来的结果集， 然后按照显示页面需要，将数据写入该类中
 * 
 * @author 武坤鹏
 * 
 */
public final class CardDistillResult {
	/**
	 * 卡类型（1：借记卡，2：信用卡，3：其他）
	 */
	private String cardtype;
	/**
	 * 设备号
	 */
	private String devno;
	/**
	 * 设备类型编号
	 */
	private String typeno;
	/**
	 * 机构号
	 */
	private String organno;
	/**
	 * 领用日期
	 */
	private String lydate;
	/**
	 * 领用时间
	 */
	private String lytime;
	/**
	 * 领用张数
	 */
	private String lynums;
	/**
	 * 领用状态（1：领用0：领用撤销）
	 */
	private String lystatus;
	/**
	 * 备用1字段
	 */
	private String remark1;
	/**
	 * 备用2字段
	 */
	private String remark2;

	/**
	 * 获得卡类型
	 * 
	 * @return
	 */
	public String getCardtype() {
		return cardtype;
	}

	/**
	 * 设置卡类型
	 * 
	 * @param cardtype
	 *            卡类型
	 */
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	/**
	 * 获得设备号
	 * 
	 * @return
	 */
	public String getDevno() {
		return devno;
	}

	/**
	 * 设置设备号
	 * 
	 * @param devno
	 *            设备号
	 */
	public void setDevno(String devno) {
		this.devno = devno;
	}

	/**
	 * 获得领用日期
	 * 
	 * @return
	 */
	public String getLydate() {
		return lydate;
	}

	/**
	 * 设置领用日期
	 * 
	 * @param lydate
	 *            领用日期
	 */
	public void setLydate(String lydate) {
		this.lydate = lydate;
	}

	/**
	 * 获得领用张数
	 * 
	 * @return
	 */
	public String getLynums() {
		return lynums;
	}

	/**
	 * 设置领用张数
	 * 
	 * @param lynums
	 *            领用张数
	 */
	public void setLynums(String lynums) {
		this.lynums = lynums;
	}

	/**
	 * 获得领用状态
	 * 
	 * @return
	 */
	public String getLystatus() {
		return lystatus;
	}

	/**
	 * 设置领用状态
	 * 
	 * @param lystatus
	 *            领用状态
	 */
	public void setLystatus(String lystatus) {
		this.lystatus = lystatus;
	}

	/**
	 * 获得领用时间
	 * 
	 * @return
	 */
	public String getLytime() {
		return lytime;
	}

	/**
	 * 设置领用时间
	 * 
	 * @param lytime
	 *            领用时间
	 */
	public void setLytime(String lytime) {
		this.lytime = lytime;
	}

	/**
	 * 获得机构号
	 * 
	 * @return
	 */
	public String getOrganno() {
		return organno;
	}

	/**
	 * 设置机构号
	 * 
	 * @param organno
	 *            机构号
	 */
	public void setOrganno(String organno) {
		this.organno = organno;
	}

	/**
	 * 备用1
	 * 
	 * @return
	 */
	public String getRemark1() {
		return remark1;
	}

	/**
	 * 备用1
	 * 
	 * @param remark1
	 */
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	/**
	 * 备用2
	 * 
	 * @return
	 */
	public String getRemark2() {
		return remark2;
	}

	/**
	 * 备用2
	 * 
	 * @param remark2
	 */
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	/**
	 * 获得设备类型编号
	 * 
	 * @return
	 */
	public String getTypeno() {
		return typeno;
	}

	/**
	 * 设置设备类型编号
	 * 
	 * @param typeno
	 */
	public void setTypeno(String typeno) {
		this.typeno = typeno;
	}

}
