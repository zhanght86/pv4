package com.lcjr.pvxp.orm;

public class CardsStatistics {
	/**
	 * 设备编号
	 */
	private String devno;
	/**
	 * 出卡状态
	 */
	private String outcardstatus;
	/**
	 * 状态统计次数
	 */
	private int A;
	/**
	 * 获得a
	 * @return the a
	 */
	public int getA() {
		return A;
	}
	/**
	 * 设置 a 
	 * @param a the a to set
	 */
	public void setA(int a) {
		A = a;
	}
	/**
	 * 获得devno
	 * @return the devno
	 */
	public String getDevno() {
		return devno;
	}
	/**
	 * 设置 devno 
	 * @param devno the devno to set
	 */
	public void setDevno(String devno) {
		this.devno = devno;
	}
	/**
	 * 获得outcardstatus
	 * @return the outcardstatus
	 */
	public String getOutcardstatus() {
		return outcardstatus;
	}
	/**
	 * 设置 outcardstatus 
	 * @param outcardstatus the outcardstatus to set
	 */
	public void setOutcardstatus(String outcardstatus) {
		this.outcardstatus = outcardstatus;
	}
	

}
