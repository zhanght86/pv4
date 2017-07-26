package com.lcjr.pvxp.bean.interfac;

import java.util.Map;

import net.sf.hibernate.HibernateException;
import com.lcjr.pvxp.orm.IniProperties;

/**
 * 
 * @author
 * @version pvxp(2014GA)
 * @date 2014-11-8
 */
public interface IPropertiesDAO extends IBaseDAO<IniProperties> {

	/**
	 * 
	 * @param section
	 * @param keyname
	 * @param defstr
	 * @param path
	 * @param filename
	 * @return
	 * @throws HibernateException
	 */
	public String selectKey(String section, String keyname, String defstr, String filename) throws HibernateException;

	/**
	 * 
	 * @return
	 * @throws HibernateException
	 */
	public Map<String, String> getAllProperties() throws HibernateException;
}