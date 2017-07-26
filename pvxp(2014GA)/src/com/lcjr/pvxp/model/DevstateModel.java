package com.lcjr.pvxp.model;

import java.util.List;

import net.sf.hibernate.HibernateException;

import com.lcjr.pvxp.bean.DevstateBean;
import com.lcjr.pvxp.orm.util.HibernateUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 和表zzt_devstate相关的业务模型
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
 * @author 杨旭
 * @version 1.0 2005/02/25
 */
public class DevstateModel {

	public DevstateModel() throws HibernateException {

	}

	/**
	 * <p>
	 * 获取设备状态列表
	 * </p>
	 * 
	 * @return List
	 * @throws HibernateException
	 */
	public List getDevStateList() throws HibernateException {
		DevstateBean myDevstateBean = new DevstateBean();
		List mylist = myDevstateBean.getAllDevstates();
		HibernateUtil.closeSession();
		return mylist;
	}

	/**
	 * <p>
	 * 获取若干设备状态列表
	 * </p>
	 * 
	 * @return List
	 * @throws HibernateException
	 */
	public List getSomeDevStateList(String devstr) throws HibernateException {
		DevstateBean myDevstateBean = new DevstateBean();
		List mylist = myDevstateBean.getSomeDevstates(devstr);
		HibernateUtil.closeSession();
		return mylist;
	}
	
	/**
	 * <p>获取某一设备状态列表</p>
	 * 
	 * @return List
	 * @throws HibernateException
	 */
	public static List getSomeDevState(String devno)throws HibernateException{
		DevstateBean myDevstateBean = new DevstateBean();
		List mylist = myDevstateBean.getSomeDevstate(devno);
		HibernateUtil.closeSession();
		return mylist;
	}

}


