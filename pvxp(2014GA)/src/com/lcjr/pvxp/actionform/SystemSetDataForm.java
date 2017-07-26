package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.bean.*;
import com.lcjr.pvxp.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 系统设置-数据设置ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.0 2005/03/28
 */
public final class SystemSetDataForm extends ActionForm {

	private String kmofmxb = null;				//交易明细保留月数
	
	private String kmofjytjday = null;		//交易日汇总保留月数
	private String kmofjytjmonth = null;	//交易月汇总保留年数
	private String kmofjytjyear = null;		//交易年汇总保留年数
	
	private String kmofsbtjday = null;		//设备故障日汇总保留月数
	private String kmofsbtjmonth = null;	//设备故障月汇总保留年数
	private String kmofsbtjyear = null;		//设备故障年汇总保留月年数
	
	private PubUtil myPubUtil = new PubUtil();

	public void setKmofmxb(String kmofmxb){
		this.kmofmxb = kmofmxb;
	}
	public void setKmofjytjday(String kmofjytjday){
		this.kmofjytjday = kmofjytjday;
	}
	public void setKmofjytjmonth(String kmofjytjmonth){
		this.kmofjytjmonth = kmofjytjmonth;
	}
	public void setKmofjytjyear(String kmofjytjyear){
		this.kmofjytjyear = kmofjytjyear;
	}
	public void setKmofsbtjday(String kmofsbtjday){
		this.kmofsbtjday = kmofsbtjday;
	}
	public void setKmofsbtjmonth(String kmofsbtjmonth){
		this.kmofsbtjmonth = kmofsbtjmonth;
	}
	public void setKmofsbtjyear(String kmofsbtjyear){
		this.kmofsbtjyear = kmofsbtjyear;
	}
	
	
	public String getKmofmxb(){
		return this.kmofmxb;
	}
	public String getKmofjytjday(){
		return this.kmofjytjday;
	}
	public String getKmofjytjmonth(){
		return this.kmofjytjmonth;
	}
	public String getKmofjytjyear(){
		return this.kmofjytjyear;
	}
	public String getKmofsbtjday(){
		return this.kmofsbtjday;
	}
	public String getKmofsbtjmonth(){
		return this.kmofsbtjmonth;
	}
	public String getKmofsbtjyear(){
		return this.kmofsbtjyear;
	}
	
	
	public void reset(ActionMapping mapping , HttpServletRequest request) {
		try{
			SysParamBean mySysParamBean = new SysParamBean();
			SysParam tmp = mySysParamBean.getSysParam();
			if( tmp!=null ){
			  this.kmofmxb = myPubUtil.dealNull( tmp.getKmofmxb() ).trim();
				this.kmofjytjday = myPubUtil.dealNull( tmp.getKmofjytjday() ).trim();
				this.kmofjytjmonth = myPubUtil.dealNull( tmp.getKmofjytjmonth() ).trim();
				this.kmofjytjyear = myPubUtil.dealNull( tmp.getKmofjytjyear() ).trim();
				this.kmofsbtjday = myPubUtil.dealNull( tmp.getKmofsbtjday() ).trim();
				this.kmofsbtjmonth = myPubUtil.dealNull( tmp.getKmofsbtjmonth() ).trim();
				this.kmofsbtjyear = myPubUtil.dealNull( tmp.getKmofsbtjyear() ).trim();
			}
		}catch(Exception e){}
	}
	
	public ActionErrors validate(ActionMapping mapping , HttpServletRequest request) {
		
		ActionErrors errors = new ActionErrors();
		
		if ((kmofmxb == null) || (kmofmxb.length() < 1) || !myPubUtil.isInteger(kmofmxb)) {
			errors.add("kmofmxb", new ActionMessage("pvxp.syssetup.setdata.error.kmofmxb"));
		}
		
		if ((kmofjytjday == null) || (kmofjytjday.length() < 1) || !myPubUtil.isInteger(kmofjytjday)) {
			errors.add("kmofjytj", new ActionMessage("pvxp.syssetup.setdata.error.tjday"));
		}else if ((kmofjytjmonth == null) || (kmofjytjmonth.length() < 1) || !myPubUtil.isInteger(kmofjytjmonth)) {
			errors.add("kmofjytj", new ActionMessage("pvxp.syssetup.setdata.error.tjmonth"));
		}else if ((kmofjytjyear == null) || (kmofjytjyear.length() < 1) || !myPubUtil.isInteger(kmofjytjyear)) {
			errors.add("kmofjytj", new ActionMessage("pvxp.syssetup.setdata.error.tjyear"));
		}
		
		if ((kmofsbtjday == null) || (kmofsbtjday.length() < 1) || !myPubUtil.isInteger(kmofsbtjday)) {
			errors.add("kmofsbtj", new ActionMessage("pvxp.syssetup.setdata.error.tjday"));
		}else if ((kmofsbtjmonth == null) || (kmofsbtjmonth.length() < 1) || !myPubUtil.isInteger(kmofsbtjmonth)) {
			errors.add("kmofsbtj", new ActionMessage("pvxp.syssetup.setdata.error.tjmonth"));
		}else if ((kmofsbtjyear == null) || (kmofsbtjyear.length() < 1) || !myPubUtil.isInteger(kmofsbtjyear)) {
			errors.add("kmofsbtj", new ActionMessage("pvxp.syssetup.setdata.error.tjyear"));
		}
		
		return errors;
	}
	
}