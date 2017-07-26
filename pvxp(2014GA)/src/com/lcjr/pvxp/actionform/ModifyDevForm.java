package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 添加设备ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 吴学坤
 * @version 1.0 2005/03/14
 */
public final class ModifyDevForm extends ActionForm {

	PubUtil pubUtil = new PubUtil();

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
	/**remark1暂时作为所属机构编号bankid*/
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
		return pubUtil.dealNull(devno).trim();
	}
	public String getTypeno() {
		return pubUtil.dealNull(typeno).trim();
	}
	public String getPacktype() {
		return pubUtil.dealNull(packtype).trim();
	}
	public String getTypestate() {
		return pubUtil.dealNull(typestate).trim();
	}
	public String getDevip() {
		return pubUtil.dealNull(devip).trim();
	}
	public String getDevmac() {
		return pubUtil.dealNull(devmac).trim();
	}
	public String getDevkey() {
		return pubUtil.dealNull(devkey).trim();
	}
	public String getPinkey() {
		return pubUtil.dealNull(pinkey).trim();
	}
	public String getMackey() {
		return pubUtil.dealNull(mackey).trim();
	}
	public String getWaterno() {
		return pubUtil.dealNull(waterno).trim();
	}
	public String getOrganno() {
		return pubUtil.dealNull(organno).trim();
	}
	public String getTellerno() {
		return pubUtil.dealNull(tellerno).trim();
	}
	public String getAutherno() {
		return pubUtil.dealNull(autherno).trim();
	}
	public String getDevaddr() {
		return pubUtil.dealNull(devaddr).trim();
	}
	public String getOrgancontact() {
		return pubUtil.dealNull(organcontact).trim();
	}
	public String getDutyname() {
		return pubUtil.dealNull(dutyname).trim();
	}
	public String getPolltag() {
		return pubUtil.dealNull(polltag).trim();
	}
	public String getUpdatetag() {
		return pubUtil.dealNull(updatetag).trim();
	}
	public String getSysdatet() {
		return pubUtil.dealNull(sysdatet).trim();
	}
	public String getOpentag() {
		return pubUtil.dealNull(opentag).trim();
	}
	public String getSysid() {
		return pubUtil.dealNull(sysid).trim();
	}
	public String getLocaltag() {
		return pubUtil.dealNull(localtag).trim();
	}
	public String getBankid() {
		return pubUtil.dealNull(bankid).trim();
	}
	public String getRemark2() {
		return pubUtil.dealNull(remark2).trim();
	}
	public String getRemark3() {
		return pubUtil.dealNull(remark3).trim();
	}
	public String getRemark4() {
		return pubUtil.dealNull(remark4).trim();
	}
}
