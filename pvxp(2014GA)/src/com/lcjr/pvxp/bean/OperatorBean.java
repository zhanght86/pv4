package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Operator;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> 和表zzt_operator相关的业务逻辑</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p>
 * @author 杨旭
 * @version 1.0 2004/12/14
 */

/**
 * <p>
 * <b>Description:</b>当捕获到异常时调用closeFactory方法
 * </p>
 * 
 * @author xucc
 * @version 1.0 2007/04/09
 */

public class OperatorBean {
	
	static Logger log = Logger.getLogger("web.com.lcjr.pvxp.bo.OperatorBean.java");
	
	
	public OperatorBean() throws HibernateException {
		
	}
	
	
	
	/**
	 * 添加一个Operator对象到数据库
	 * 
	 * @param st
	 *            操作员信息对象
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean addOperator(Operator st) throws HibernateException {
		boolean result = true;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			session.save(st);
			tx.commit();
			result = tx.wasCommitted();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			result = false;
			log.error("addOperator", e);
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
				log.error("addOperator finally {", ex);
			}
		}
		return result;
	}
	
	
	
	/**
	 * 更新Operator对象到数据库
	 * 
	 * @param st
	 *            操作员信息对象
	 * @param operid
	 *            操作员编号
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean updateOperator(Operator st) throws HibernateException {
		boolean result = false;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			session.update(st);
			tx.commit();
			result = tx.wasCommitted();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			result = false;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
				
			}
		}
		return result;
	}
	
	
	public static boolean updateOperator(Operator st, String operid) throws HibernateException {
		return updateOperator(st);
	}
	
	
	
	/**
	 * 查询系统中所有的操作员信息<br>
	 * 返回的是包含有Operator持久对象的Iterator
	 * 
	 * @return Iterator
	 * @throws HibernateException
	 */
	public Iterator getAllOperators() throws HibernateException {
		String queryString = "select opers from Operator as opers";
		Iterator it = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			it = query.iterate();
			tx.commit();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			return null;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
			}
		}
		return it;
	}
	
	
	
	/**
	 * 查询系统中所有的操作员信息<br>
	 * 返回的是包含有Operator对象的List
	 * 
	 * @return List
	 * @throws HibernateException
	 */
	public List getAllOperList() throws HibernateException {
		String queryString = "select opers from Operator as opers order by opers.operid";
		List it = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			it = query.list();
			tx.commit();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			return null;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
			}
		}
		return it;
	}
	
	
	
	/**
	 * 删除给定ID(操作员编号operid)的操作员信息
	 * 
	 * @param operid
	 *            操作员编号
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean deleteOperator(String operid) throws HibernateException {
		boolean result = false;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			
			session.delete("select c from Operator as c where c.operid='" + operid + "'");
			tx.commit();
			result = tx.wasCommitted();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			result = false;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
			}
		}
		return result;
	}
	
	
	
	/**
	 * 删除给定ID(机构编号标示bankid)的操作员信息
	 * 
	 * @param bankid
	 *            机构编号标示
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean deleteSomeOperator(String bankid) throws HibernateException {
		boolean result = false;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			
			session.delete("select c from Operator as c where c.bankid like '" + bankid + "%'");
			tx.commit();
			result = tx.wasCommitted();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			result = false;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
			}
		}
		return result;
	}
	
	
	
	/**
	 * 按操作员编号进行模糊查找<br>
	 * 返回的是包含有Operator持久对象的Iterator
	 * 
	 * @param operid
	 *            操作员编号
	 * @return Iterator
	 * @throws HibernateException
	 */
	public Iterator getSomeOperator(String operid) throws HibernateException {
		String queryString = "select c from Operator as c where c.operid like :operid order by c.operid";
		Iterator it = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setString("operid", "%" + operid + "%");
			it = query.iterate();
			tx.commit();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			return null;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
			}
		}
		return it;
	}
	
	
	
	/**
	 * 按操作员编号获得Operator持久对象(不要直接使用，请用OperatorModel中的方法)
	 * 
	 * @param operid
	 *            操作员编号
	 * @return Operator
	 * @throws HibernateException
	 */
	public Operator getOperator(String operid) throws HibernateException {
		String queryString = "select c from Operator as c where c.operid='" + operid + "'";
		Iterator it = null;
		Operator temp = null;
		try {
			
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			// query.setString("operid",operid);
			
			it = query.iterate();
			// tx.commit(); //20090414 gemler informix数据库注释掉
			if (it.hasNext()) {
				temp = (Operator) it.next();
			}
			tx.commit();// 取到next值再commit（informix取值前commit报错） xucc 20090622
			
		} catch (Exception e) {
			log.error("getOperator(", e);
			HibernateUtil.closeFactory();
			return null;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
				log.error("getOperator(", ex);
			}
		}
		return temp;
	}
}
