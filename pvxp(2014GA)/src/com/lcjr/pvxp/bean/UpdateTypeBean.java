package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Updatetype;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 和表zzt_updatetype相关的业务逻辑
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2009
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author xucc
 * @version 1.0 2009/12/07
 */

public class UpdateTypeBean {
	
	Logger log = Logger.getLogger("web.com.lcjr.pvxp.bo.UpdateTypeBean.java");
	
	
	
	public UpdateTypeBean() throws HibernateException {
		
	}
	
	
	
	/**
	 * 添加一个Updatetype对象到数据库
	 * 
	 * @param st
	 *            类型对象
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean addUpdateType(Updatetype st) throws HibernateException {
		boolean result = false;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			session.save(st);
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
	 * 更新Updatetype对象到数据库
	 * 
	 * @param st
	 *            类型对象
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean updateUpdateType(Updatetype st) throws HibernateException {
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
	
	
	public static boolean updateUpdateType(Updatetype st, String updateno) throws HibernateException {
		return updateUpdateType(st);
	}
	
	/**
	 * 查询系统中所有的更新类型信息<br>
	 * 返回的是包含有Updatetype持久对象的Iterator
	 * 
	 * @return Iterator
	 * @throws HibernateException
	 */
	public Iterator getAllUpdateTypes() throws HibernateException {
		String queryString = "select updatetypes from Updatetype as updatetypes";
		Iterator it = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			it = query.iterate();
			tx.commit();
		} catch (Exception e) {
			log.error("ERROR", e);
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
	 * 查询系统中所有的更新类型信息<br>
	 * 返回的是包含有Updatetype对象的List
	 * 
	 * @return List
	 * @throws HibernateException
	 */
	public List getAllUpdateTypeList() throws HibernateException {
		String queryString = "select updatetypes from Updatetype as updatetypes order by updatetypes.updateno";
		List it = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			it = query.list();
			tx.commit();
		} catch (Exception e) {
			log.error("ERROR", e);
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
	 * 删除给定ID(更新类型编号updateno)的类型信息
	 * 
	 * @param updateno
	 *            类型编号
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean deleteUpdateType(String updateno) throws HibernateException {
		boolean result = false;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			session.delete("select c from Updatetype as c where c.updateno='" + updateno + "'");
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
	 * 按类型编号进行模糊查找<br>
	 * 返回的是包含有Updatetype持久对象的Iterator
	 * 
	 * @param updateno
	 *            类型编号
	 * @return Iterator
	 * @throws HibernateException
	 */
	public Iterator getSomeUpdateType(String updateno) throws HibernateException {
		String queryString = "select c from Updatetype as c where c.updateno like :updateno order by c.updateno";
		Iterator it = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setString("updateno", "%" + updateno + "%");
			it = query.iterate();
			tx.commit();
		} catch (Exception e) {
			log.error("ERROR", e);
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
	 * 按编号获得持久对象
	 * 
	 * @param updateno
	 *            编号
	 * @return Updatetype
	 * @throws HibernateException
	 */
	public Updatetype getUpdateType(String updateno) throws HibernateException {
		String queryString = "select c from Updatetype as c where c.updateno='" + updateno + "'";
		Iterator it = null;
		Updatetype temp = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			
			it = query.iterate();
			
			if (it.hasNext()) {
				temp = (Updatetype) it.next();
			}
			tx.commit();
			
		} catch (Exception e) {
			log.error("ERROR", e);
			HibernateUtil.closeFactory();
			return null;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
				log.error("ERROR", ex);
			}
		}
		return temp;
	}
	
}
