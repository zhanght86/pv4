package com.lcjr.pvxp.bean.interfac;

import net.sf.hibernate.HibernateException;
import com.lcjr.pvxp.orm.Devinfo;

/**
 * 
 * @author 武坤�?
 * @version pvxp(2014GA)
 * @date 2014-9-9
 */
public interface DevInfoDAO extends IBaseDAO<Devinfo> {

	/**
	 * 数据库查�?
	 * 
	 * @param sqlString
	 * @return
	 * @throws HibernateException
	 */
	public Devinfo select(String sqlString) throws HibernateException;
}