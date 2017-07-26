package com.lcjr.pvxp.orm;

import java.io.Serializable;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 在hibernate中映射zzt_sysparam表的类
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2012
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author 武坤鹏
 * @version 1.0 2012/06/01
 */
public class SysParam implements Serializable {
	
	
	/**
	 * 系统版本
	 */
	private String sysver;
	
	
	
	/**
	 * 明细表开始日期
	 */
	private String bdofmxb;
	
	
	
	/**
	 * 交易统计日汇总开始日期
	 */
	private String bdofjytjday;
	
	
	
	/**
	 * 交易统计月汇总开始月份
	 */
	private String bdofjytjmonth;
	
	
	
	/**
	 * 交易统计年汇总开始年份
	 */
	private String bdofjytjyear;
	
	
	
	/**
	 * 设备统计日汇总开始日期
	 */
	private String bdofsbtjday;
	
	
	
	/**
	 * 设备统计月汇总开始月份
	 */
	private String bdofsbtjmonth;
	
	
	
	/**
	 * 设备统计年汇总开始年份
	 */
	private String bdofsbtjyear;
	
	
	
	/**
	 * 明细表保留月数
	 */
	private String kmofmxb;
	
	
	
	/**
	 * 交易统计日汇总保留月数
	 */
	private String kmofjytjday;
	
	
	
	/**
	 * 交易统计月汇总保留年数
	 */
	private String kmofjytjmonth;
	
	
	
	/**
	 * 交易统计年汇总保留年数
	 */
	private String kmofjytjyear;
	
	
	
	/**
	 * 设备统计日汇总保留日数
	 */
	private String kmofsbtjday;
	
	
	
	/**
	 * 设备统计日汇总保留月数
	 */
	private String kmofsbtjmonth;
	
	
	
	/**
	 * 设备统计月汇总保留年数
	 */
	private String kmofsbtjyear;
	
	
	
	/**
	 * 交易历史明细表状态 F：可用 B：正在倒入
	 * 
	 */
	private String statusoftmptable;
	
	
	
	/**
	 * 当前系统保留的历史交易明细临时文件起始月份
	 */
	private String tmpdatastart;
	
	
	
	/**
	 * 交易历史明细表中数据情况： 200403,200404,200412,200503 最多允许倒入24个月的数据
	 * 
	 */
	private String monthsoftmptable;
	
	
	
	
	/**
	 * @return the bdofjytjday
	 */
	public String getBdofjytjday() {
		return bdofjytjday;
	}
	
	
	
	/**
	 * @param bdofjytjday
	 *            the bdofjytjday to set
	 */
	public void setBdofjytjday(String bdofjytjday) {
		this.bdofjytjday = bdofjytjday;
	}
	
	
	
	/**
	 * @return the bdofjytjmonth
	 */
	public String getBdofjytjmonth() {
		return bdofjytjmonth;
	}
	
	
	
	/**
	 * @param bdofjytjmonth
	 *            the bdofjytjmonth to set
	 */
	public void setBdofjytjmonth(String bdofjytjmonth) {
		this.bdofjytjmonth = bdofjytjmonth;
	}
	
	
	
	/**
	 * @return the bdofjytjyear
	 */
	public String getBdofjytjyear() {
		return bdofjytjyear;
	}
	
	
	
	/**
	 * @param bdofjytjyear
	 *            the bdofjytjyear to set
	 */
	public void setBdofjytjyear(String bdofjytjyear) {
		this.bdofjytjyear = bdofjytjyear;
	}
	
	
	
	/**
	 * @return the bdofmxb
	 */
	public String getBdofmxb() {
		return bdofmxb;
	}
	
	
	
	/**
	 * @param bdofmxb
	 *            the bdofmxb to set
	 */
	public void setBdofmxb(String bdofmxb) {
		this.bdofmxb = bdofmxb;
	}
	
	
	
	/**
	 * @return the bdofsbtjday
	 */
	public String getBdofsbtjday() {
		return bdofsbtjday;
	}
	
	
	
	/**
	 * @param bdofsbtjday
	 *            the bdofsbtjday to set
	 */
	public void setBdofsbtjday(String bdofsbtjday) {
		this.bdofsbtjday = bdofsbtjday;
	}
	
	
	
	/**
	 * @return the bdofsbtjmonth
	 */
	public String getBdofsbtjmonth() {
		return bdofsbtjmonth;
	}
	
	
	
	/**
	 * @param bdofsbtjmonth
	 *            the bdofsbtjmonth to set
	 */
	public void setBdofsbtjmonth(String bdofsbtjmonth) {
		this.bdofsbtjmonth = bdofsbtjmonth;
	}
	
	
	
	/**
	 * @return the bdofsbtjyear
	 */
	public String getBdofsbtjyear() {
		return bdofsbtjyear;
	}
	
	
	
	/**
	 * @param bdofsbtjyear
	 *            the bdofsbtjyear to set
	 */
	public void setBdofsbtjyear(String bdofsbtjyear) {
		this.bdofsbtjyear = bdofsbtjyear;
	}
	
	
	
	/**
	 * @return the kmofjytjday
	 */
	public String getKmofjytjday() {
		return kmofjytjday;
	}
	
	
	
	/**
	 * @param kmofjytjday
	 *            the kmofjytjday to set
	 */
	public void setKmofjytjday(String kmofjytjday) {
		this.kmofjytjday = kmofjytjday;
	}
	
	
	
	/**
	 * @return the kmofjytjmonth
	 */
	public String getKmofjytjmonth() {
		return kmofjytjmonth;
	}
	
	
	
	/**
	 * @param kmofjytjmonth
	 *            the kmofjytjmonth to set
	 */
	public void setKmofjytjmonth(String kmofjytjmonth) {
		this.kmofjytjmonth = kmofjytjmonth;
	}
	
	
	
	/**
	 * @return the kmofjytjyear
	 */
	public String getKmofjytjyear() {
		return kmofjytjyear;
	}
	
	
	
	/**
	 * @param kmofjytjyear
	 *            the kmofjytjyear to set
	 */
	public void setKmofjytjyear(String kmofjytjyear) {
		this.kmofjytjyear = kmofjytjyear;
	}
	
	
	
	/**
	 * @return the kmofmxb
	 */
	public String getKmofmxb() {
		return kmofmxb;
	}
	
	
	
	/**
	 * @param kmofmxb
	 *            the kmofmxb to set
	 */
	public void setKmofmxb(String kmofmxb) {
		this.kmofmxb = kmofmxb;
	}
	
	
	
	/**
	 * @return the kmofsbtjday
	 */
	public String getKmofsbtjday() {
		return kmofsbtjday;
	}
	
	
	
	/**
	 * @param kmofsbtjday
	 *            the kmofsbtjday to set
	 */
	public void setKmofsbtjday(String kmofsbtjday) {
		this.kmofsbtjday = kmofsbtjday;
	}
	
	
	
	/**
	 * @return the kmofsbtjmonth
	 */
	public String getKmofsbtjmonth() {
		return kmofsbtjmonth;
	}
	
	
	
	/**
	 * @param kmofsbtjmonth
	 *            the kmofsbtjmonth to set
	 */
	public void setKmofsbtjmonth(String kmofsbtjmonth) {
		this.kmofsbtjmonth = kmofsbtjmonth;
	}
	
	
	
	/**
	 * @return the kmofsbtjyear
	 */
	public String getKmofsbtjyear() {
		return kmofsbtjyear;
	}
	
	
	
	/**
	 * @param kmofsbtjyear
	 *            the kmofsbtjyear to set
	 */
	public void setKmofsbtjyear(String kmofsbtjyear) {
		this.kmofsbtjyear = kmofsbtjyear;
	}
	
	
	
	/**
	 * @return the monthsoftmptable
	 */
	public String getMonthsoftmptable() {
		return monthsoftmptable;
	}
	
	
	
	/**
	 * @param monthsoftmptable
	 *            the monthsoftmptable to set
	 */
	public void setMonthsoftmptable(String monthsoftmptable) {
		this.monthsoftmptable = monthsoftmptable;
	}
	
	
	
	/**
	 * @return the statusoftmptable
	 */
	public String getStatusoftmptable() {
		return statusoftmptable;
	}
	
	
	
	/**
	 * @param statusoftmptable
	 *            the statusoftmptable to set
	 */
	public void setStatusoftmptable(String statusoftmptable) {
		this.statusoftmptable = statusoftmptable;
	}
	
	
	
	/**
	 * @return the sysver
	 */
	public String getSysver() {
		return sysver;
	}
	
	
	
	/**
	 * @param sysver
	 *            the sysver to set
	 */
	public void setSysver(String sysver) {
		this.sysver = sysver;
	}
	
	
	
	/**
	 * @return the tmpdatastart
	 */
	public String getTmpdatastart() {
		return tmpdatastart;
	}
	
	
	
	/**
	 * @param tmpdatastart
	 *            the tmpdatastart to set
	 */
	public void setTmpdatastart(String tmpdatastart) {
		this.tmpdatastart = tmpdatastart;
	}
	
	
	
	/**
	 * 
	 */
	public Object clone() throws CloneNotSupportedException {
		SysParam result = (SysParam) super.clone();
		return result;
	}
}
