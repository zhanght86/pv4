package com.lcjr.pvxp.bean.interfac;

import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import com.lcjr.pvxp.orm.util.HibernateUtil;

/**
 * 这个类作为所有dao的基类，实现针对hibernate的增产改查等基本的功�?使用泛型的概念，从�?更好的�?应更多的类继�?
 * 
 * @author wang-jl
 * 
 * @param <T>
 */
public class BaseDAO<T> {

	/**
	 * 数据库的插入功能
	 * 
	 * @param t
	 * @return
	 * @throws HibernateException
	 *             e
	 */
	public T insert(T t) throws HibernateException {
		Session session = null;
		Transaction tx = null;
		try {
			session = (Session) HibernateUtil.currentSession();
			tx = session.beginTransaction();
			session.save(t);
			tx.commit();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			e.printStackTrace();
		} finally {
			if (tx != null)
				tx.rollback();
			HibernateUtil.closeSession();
		}
		return t;
	}

	/**
	 * 数据库的更新操作
	 * 
	 * @param t
	 * @return
	 * @throws HibernateException
	 *             e
	 */
	public T update(T t) throws HibernateException {
		Session session = null;
		Transaction tx = null;
		try {
			session = (Session) HibernateUtil.currentSession();
			tx = session.beginTransaction();
			session.update(t);
			tx.commit();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			e.printStackTrace();
		} finally {
			if (tx != null)
				tx.rollback();
			HibernateUtil.closeSession();
		}
		return t;
	}

	/**
	 * 数据库的删除操作
	 * 
	 * @param t
	 * @return
	 * @throws HibernateException
	 *             e
	 */
	public T delete(T t) throws HibernateException {
		Session session = null;
		Transaction tx = null;
		try {
			session = (Session) HibernateUtil.currentSession();
			tx = session.beginTransaction();
			session.delete(t);
			tx.commit();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			e.printStackTrace();
		} finally {
			if (tx != null)
				tx.rollback();
			HibernateUtil.closeSession();
		}
		return t;
	}

	/**
	 * 数据库查询所有的操作
	 * 
	 * @param t
	 * @return
	 * @throws HibernateException
	 */
	@SuppressWarnings("unchecked")
	public List<T> list(String TABLENAME) throws HibernateException {
		Session session = null;
		Query query = null;
		List<T> list = null;
		try {
			session = (Session) HibernateUtil.currentSession();
			query = session.createQuery("from " + TABLENAME);
			list = query.list();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession();
		}
		return list;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws HibernateException
	 *             e
	 */
	@SuppressWarnings("unchecked")
	public T select(String TABLENAME, String key, String value) throws HibernateException {
		Session session = null;
		T searchT = null;
		try {
			session = (Session) HibernateUtil.currentSession();
			Query query = session.createQuery("from " + TABLENAME + " where " + key + " = " + value);
			searchT = (T) query.uniqueResult();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession();
		}
		return searchT;
	}
}