package com.lcjr.pvxp.util;

/**
 * 用于存储从数据库中查询出来的结果集，
 * 然后按照显示页面需要，将数据写入该类中
 * @author 武坤鹏
 */
public final class CardOutResult {
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
	 * 身份证号
	 */
	private String idcardno;
	/**
	 * 卡密码
	 */
	private String passwd;
	/**
	 * 条码号
	 */
	private String strcode;
	/**
	 * 卡号
	 */
	private String outcardno;
	/**
	 * 出卡日期
	 */
	private String outcarddate;
	/**
	 * 出卡时间
	 */
	private String outcardtime;
	/**
	 * 出卡状态（1：成功0：失败）
	 */
	private String outcardstatus;
	
	/**
	 * 出卡年月
	 */
	private String outcardmonth;
	
	
	
	/**
	 * 流水号
	 */
	private String serialno;
	
	
	/**
	 * 备用字段
	 */
	private String remark1;
	/**
	 * 备用字段
	 */
	private String remark2;
	/**
	 * 备用字段
	 */
	private String remark3;
	/**
	 * 备用字段
	 */
	private String remark4;
	
	//使用获得和设置
	
	
	/**
	 * 获得 卡类型（1：借记卡，2：信用卡，3：其他）
	 * @return the cardtype
	 */
	public String getCardtype() {
		return cardtype;
	}
	/**
	 * 设置 卡类型（1：借记卡，2：信用卡，3：其他）
	 * @param cardtype the cardtype to set
	 */
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	/**
	 * 获得 设备号
	 * @return the devno
	 */
	public String getDevno() {
		return devno;
	}
	/**
	 * 设置 设备号
	 * @param devno the devno to set
	 */
	public void setDevno(String devno) {
		this.devno = devno;
	}
	/**
	 * 获得 身份证号
	 * @return the idcardno
	 */
	public String getIdcardno() {
		return idcardno;
	}
	/**
	 * 设置 身份证号 
	 * @param idcardno the idcardno to set
	 */
	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}
	/**
	 * 获得 机构号
	 * @return the organno
	 */
	public String getOrganno() {
		return organno;
	}
	/**
	 * 设置 机构号 
	 * @param organno the organno to set
	 */
	public void setOrganno(String organno) {
		this.organno = organno;
	}
	/**
	 * 获得 出卡日期
	 * @return the outcarddate
	 */
	public String getOutcarddate() {
		return outcarddate;
	}
	/**
	 * 设置 出卡日期 
	 * @param outcarddate the outcarddate to set
	 */
	public void setOutcarddate(String outcarddate) {
		this.outcarddate = outcarddate;
	}
	/**
	 * 获得 发出卡的卡号
	 * @return the outcardno
	 */
	public String getOutcardno() {
		return outcardno;
	}
	/**
	 * 设置 发出卡的卡号 
	 * @param outcardno the outcardno to set
	 */
	public void setOutcardno(String outcardno) {
		this.outcardno = outcardno;
	}
	/**
	 * 获得 出卡状态（1：成功0：失败）
	 * @return the outcardstatus
	 */
	public String getOutcardstatus() {
		return outcardstatus;
	}
	/**
	 * 设置 出卡状态（1：成功0：失败）
	 * @param outcardstatus the outcardstatus to set
	 */
	public void setOutcardstatus(String outcardstatus) {
		this.outcardstatus = outcardstatus;
	}
	/**
	 * 获得 出卡时间
	 * @return the outcardtime
	 */
	public String getOutcardtime() {
		return outcardtime;
	}
	/**
	 * 设置 出卡时间 
	 * @param outcardtime the outcardtime to set
	 */
	public void setOutcardtime(String outcardtime) {
		this.outcardtime = outcardtime;
	}
	/**
	 * 获得 卡密码
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}
	/**
	 * 设置 卡密码 
	 * @param passwd the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	/**
	 * 获得remark1
	 * @return the remark1
	 */
	public String getRemark1() {
		return remark1;
	}
	/**
	 * 设置 remark1 
	 * @param remark1 the remark1 to set
	 */
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	/**
	 * 获得remark2
	 * @return the remark2
	 */
	public String getRemark2() {
		return remark2;
	}
	/**
	 * 设置 remark2 
	 * @param remark2 the remark2 to set
	 */
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	/**
	 * 获得remark3
	 * @return the remark3
	 */
	public String getRemark3() {
		return remark3;
	}
	/**
	 * 设置 remark3 
	 * @param remark3 the remark3 to set
	 */
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	/**
	 * 获得remark4
	 * @return the remark4
	 */
	public String getRemark4() {
		return remark4;
	}
	/**
	 * 设置 remark4 
	 * @param remark4 the remark4 to set
	 */
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	/**
	 * 获得 条码号
	 * @return the strcode
	 */
	public String getStrcode() {
		return strcode;
	}
	/**
	 * 设置 条码号 
	 * @param strcode the strcode to set
	 */
	public void setStrcode(String strcode) {
		this.strcode = strcode;
	}
	/**
	 * 获得 设备类型编号
	 * @return the typeno
	 */
	public String getTypeno() {
		return typeno;
	}
	/**
	 * 设置 设备类型编号 
	 * @param typeno the typeno to set
	 */
	public void setTypeno(String typeno) {
		this.typeno = typeno;
	}
	/**
	 * @return the outcardmonth
	 */
	public String getOutcardmonth() {
		return outcardmonth;
	}
	/**
	 * @param outcardmonth the outcardmonth to set
	 */
	public void setOutcardmonth(String outcardmonth) {
		this.outcardmonth = outcardmonth;
	}
	/**
	 * @return the serialno
	 */
	public String getSerialno() {
		return serialno;
	}
	/**
	 * @param serialno the serialno to set
	 */
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
}
