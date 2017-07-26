package com.lcjr.pvxp.actionform;

import org.apache.struts.action.ActionForm;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 添加设备ActionForm
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author 吴学坤
 * @version 1.0 2005/03/14
 */
public final class AddDevForm extends ActionForm {

	// private Logger log = Logger.getLogger(AddDevForm.class);

	private String devno;
	private String typeno;
	private String packtype;
	private String typestate;
	private String devip;
	private String devmac;
	private String devkey;
	private String pinkey;
	private String mackey;
	private String waterno;
	private String organno;
	private String tellerno;
	private String autherno;
	private String devaddr;
	private String organcontact;
	private String dutyname;
	private String polltag;
	private String updatetag;
	private String sysdatet;
	private String opentag;
	private String sysid;
	private String localtag;
	/** remark1暂时作为所属机构编号bankid */
	private String bankid;

	private String remark2;
	private String remark3;
	private String remark4;

	public void setDevno(String mdevno) {
		devno = mdevno;
	}

	public void setTypeno(String mtypeno) {
		typeno = mtypeno;
	}

	public void setPacktype(String mpacktype) {
		packtype = mpacktype;
	}

	public void setTypestate(String mtypestate) {
		typestate = mtypestate;
	}

	public void setDevip(String mdevip) {
		devip = mdevip;
	}

	public void setDevmac(String mdevmac) {
		devmac = mdevmac;
	}

	public void setDevkey(String mdevkey) {
		devkey = mdevkey;
	}

	public void setPinkey(String mpinkey) {
		pinkey = mpinkey;
	}

	public void setMackey(String mmackey) {
		mackey = mmackey;
	}

	public void setWaterno(String mwaterno) {
		waterno = mwaterno;
	}

	public void setOrganno(String morganno) {
		organno = morganno;
	}

	public void setTellerno(String mtellerno) {
		tellerno = mtellerno;
	}

	public void setAutherno(String mautherno) {
		autherno = mautherno;
	}

	public void setDevaddr(String mdevaddr) {
		devaddr = mdevaddr;
	}

	public void setOrgancontact(String morgancontact) {
		organcontact = morgancontact;
	}

	public void setDutyname(String mdutyname) {
		dutyname = mdutyname;
	}

	public void setPolltag(String mpolltag) {
		polltag = mpolltag;
	}

	public void setUpdatetag(String mupdatetag) {
		updatetag = mupdatetag;
	}

	public void setSysdatet(String msysdatet) {
		sysdatet = msysdatet;
	}

	public void setOpentag(String mopentag) {
		opentag = mopentag;
	}

	public void setSysid(String msysid) {
		sysid = msysid;
	}

	public void setLocaltag(String mlocaltag) {
		localtag = mlocaltag;
	}

	public void setBankid(String mbankid) {
		bankid = mbankid;
	}

	public void setRemark2(String mremark2) {
		remark2 = mremark2;
	}

	public void setRemark3(String mremark3) {
		remark3 = mremark3;
	}

	public void setRemark4(String mremark4) {
		remark4 = mremark4;
	}

	public String getDevno() {
		return devno;
	}

	public String getTypeno() {
		return typeno;
	}

	public String getPacktype() {
		return packtype;
	}

	public String getTypestate() {
		return typestate;
	}

	public String getDevip() {
		return devip;
	}

	public String getDevmac() {
		return devmac;
	}

	public String getDevkey() {
		return devkey;
	}

	public String getPinkey() {
		return pinkey;
	}

	public String getMackey() {
		return mackey;
	}

	public String getWaterno() {
		return waterno;
	}

	public String getOrganno() {
		return organno;
	}

	public String getTellerno() {
		return tellerno;
	}

	public String getAutherno() {
		return autherno;
	}

	public String getDevaddr() {
		return devaddr;
	}

	public String getOrgancontact() {
		return organcontact;
	}

	public String getDutyname() {
		return dutyname;
	}

	public String getPolltag() {
		return polltag;
	}

	public String getUpdatetag() {
		return updatetag;
	}

	public String getSysdatet() {
		return sysdatet;
	}

	public String getOpentag() {
		return opentag;
	}

	public String getSysid() {
		return sysid;
	}

	public String getLocaltag() {
		return localtag;
	}

	public String getBankid() {
		return bankid;
	}

	public String getRemark2() {
		return remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public String getRemark4() {
		return remark4;
	}
}
