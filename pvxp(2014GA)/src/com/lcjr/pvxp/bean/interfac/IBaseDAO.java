package com.lcjr.pvxp.bean.interfac;

import java.util.List;

import net.sf.hibernate.HibernateException;

/**
 * 
 * @author wang-jl
 * 
 */
public interface IBaseDAO<T> {

	/**
	 * 
	 * @param t
	 * @return
	 * @throws HibernateException
	 */
	public T insert(T t) throws HibernateException;

	/**
	 * 
	 * @param t
	 * @return
	 * @throws HibernateException
	 */
	public T update(T t) throws HibernateException;

	/**
	 * 
	 * @param t
	 * @return
	 * @throws HibernateException
	 */
	public T delete(T t) throws HibernateException;

	/**
	 * 
	 * @param TABLENAME
	 * @return
	 * @throws HibernateException
	 */
	public List<T> list(String TABLENAME) throws HibernateException;

	/**
	 * 数据库查询
	 * 
	 * @param TABLENAME
	 * @param key
	 * @param value
	 * @return
	 * @throws HibernateException
	 */
	public T select(String TABLENAME, String key, String value) throws HibernateException;

}
