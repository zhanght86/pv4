package com.lcjr.pvxp.orm;

import java.io.Serializable;


/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 在hibernate中映射zzt_pvplugin表的类
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2012
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author 武坤鹏
 * @version 1.0 2012/06/01（儿童节）
 */
public class Plugin implements Serializable {
	
	
	/**
	 * 插件ID
	 */
	private String plugid;
	
	
	
	/**
	 * 插件名称
	 */
	private String plugname;
	
	
	
	/**
	 * 入口程序地址
	 */
	private String firsturl;
	
	
	
	/**
	 * 插件类型： 1 系统插件（仅系统管理员） 2 开放插件，不校验权限 3 认证插件，仅认证列表用户可用
	 * 
	 */
	private String plugtype;
	
	
	
	/**
	 * 描述
	 */
	private String info;
	
	
	
	/**
	 * 认证用户ID列表，如： ,0000,0001,0002,0100,0020, 两端有逗号
	 * 
	 */
	private String userslist;



	/**
	 * @return the firsturl
	 */
	public String getFirsturl() {
		return firsturl;
	}



	/**
	 * @param firsturl the firsturl to set
	 */
	public void setFirsturl(String firsturl) {
		this.firsturl = firsturl;
	}



	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}



	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}



	/**
	 * @return the plugid
	 */
	public String getPlugid() {
		return plugid;
	}



	/**
	 * @param plugid the plugid to set
	 */
	public void setPlugid(String plugid) {
		this.plugid = plugid;
	}



	/**
	 * @return the plugname
	 */
	public String getPlugname() {
		return plugname;
	}



	/**
	 * @param plugname the plugname to set
	 */
	public void setPlugname(String plugname) {
		this.plugname = plugname;
	}



	/**
	 * @return the plugtype
	 */
	public String getPlugtype() {
		return plugtype;
	}



	/**
	 * @param plugtype the plugtype to set
	 */
	public void setPlugtype(String plugtype) {
		this.plugtype = plugtype;
	}



	/**
	 * @return the userslist
	 */
	public String getUserslist() {
		return userslist;
	}



	/**
	 * @param userslist the userslist to set
	 */
	public void setUserslist(String userslist) {
		this.userslist = userslist;
	}
	
	
	
	
}
