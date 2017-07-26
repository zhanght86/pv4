package com.lcjr.pvxp.pojo;
/**
 * 机构开机率报表模型
 * @author use
 *
 */
public class CSResult {

	private String devname;
	private String factoryname;
	private String times;
	private String timelen;
	private String normaltimes;
	private String devnum;
	public String getDevname() {
		return devname;
	}
	public void setDevname(String devname) {
		this.devname = devname;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getTimelen() {
		return timelen;
	}
	public void setTimelen(String timelen) {
		this.timelen = timelen;
	}
	public String getNormaltimes() {
		return normaltimes;
	}
	public void setNormaltimes(String normaltimes) {
		this.normaltimes = normaltimes;
	}
	public String getFactoryname() {
		return factoryname;
	}
	public void setFactoryname(String factoryname) {
		this.factoryname = factoryname;
	}
	public String getDevnum() {
		return devnum;
	}
	public void setDevnum(String devnum) {
		this.devnum = devnum;
	}
	
}
