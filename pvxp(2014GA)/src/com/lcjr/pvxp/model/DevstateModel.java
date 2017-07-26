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
 * <b>Description:</b> �ͱ�zzt_devstate��ص�ҵ��ģ��
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * <br>
 * 
 * @author ����
 * @version 1.0 2005/02/25
 */
public class DevstateModel {

	public DevstateModel() throws HibernateException {

	}

	/**
	 * <p>
	 * ��ȡ�豸״̬�б�
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
	 * ��ȡ�����豸״̬�б�
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
	 * <p>��ȡĳһ�豸״̬�б�</p>
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


