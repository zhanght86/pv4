package com.lcjr.pvxp.bean.interfac;

import java.util.Map;

import net.sf.hibernate.HibernateException;

import com.lcjr.pvxp.orm.DevsState;

/**
 * 
 * @author 武坤�?
 * @version pvxp(2014GA)
 * @date 2014-9-9
 */
public interface DevsStateDAO extends IBaseDAO<DevsState> {

	/**
	 * 插入设备状�?，有则更换，无则加入
	 * 
	 * @param map
	 * @return
	 * @throws HibernateException
	 */
	public String insertProc(Map<String, String> map) throws HibernateException;
}