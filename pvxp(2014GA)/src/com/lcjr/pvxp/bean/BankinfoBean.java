package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Bankinfo;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> 和表zzt_devtype相关的业务逻辑</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p>
 * @author 杨旭
 * @version 1.0 2004/12/08
 */

/**
 * <p>
 * <b>Description:</b>当捕获到异常时调用closeFactory方法
 * </p>
 * 
 * @author xucc
 * @version 1.0 2007/04/09
 */

public class BankinfoBean {
	public BankinfoBean() throws HibernateException {
		
	}
	
	
	
	/**
	 * 添加一个Bankinfo对象到数据库
	 * 
	 * @param st
	 *            机构信息Bankinfo对象
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean addBankinfo(Bankinfo st) throws HibernateException {
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
	 * 更新Bankinfo对象到数据库
	 * 
	 * @param st
	 *            机构信息对象
	 * @param bankid
	 *            机构编号
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean updateBankinfo(Bankinfo st) throws HibernateException {
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
	
	
	public static boolean updateBankinfo(Bankinfo st, String bankid) throws HibernateException {
		return updateBankinfo(st);
	}
	
	/**
	 * 查询系统中所有的机构信息<br>
	 * 返回的是包含有Bankinfo持久对象的Iterator
	 * 
	 * @return Iterator
	 * @throws HibernateException
	 */
	public Iterator<Bankinfo> getAllBankinfos() throws HibernateException {
		String queryString = "select bankinfos from Bankinfo as bankinfos";
		Iterator<Bankinfo> it = null;
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
	 * 查询系统中所有的机构类型信息<br>
	 * 返回的是包含有Bankinfo的List
	 * 
	 * @return List
	 * @throws HibernateException
	 */
	public List<Bankinfo> getAllBankinfoList() throws HibernateException {
		String queryString = "select bankinfos from Bankinfo as bankinfos order by bankinfos.bankid";
		List<Bankinfo> it = null;
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
	 * 删除给定ID(机构编号bankid)的机构信息
	 * 
	 * @param bankid
	 *            机构编号
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean deleteBankinfo(String bankid) throws HibernateException {
		boolean result = false;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			session.delete("select c from Bankinfo as c where c.bankid='" + bankid + "'");
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
	 * 删除给定ID所有机构(机构编号标示bankid)的机构信息
	 * 
	 * @param bankid
	 *            机构编号标示
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean deleteSomeBankinfo(String bankid) throws HibernateException {
		boolean result = false;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			session.delete("select c from Bankinfo as c where c.bankid like '" + bankid + "%'");
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
	 * 按机构编号进行模糊查找<br>
	 * 返回的是包含有Bankinfo持久对象的Iterator
	 * 
	 * @param bankid
	 *            机构编号
	 * @return Iterator
	 * @throws HibernateException
	 */
	public Iterator getSomeBankinfo(String bankid) throws HibernateException {
		String queryString = "select c from Bankinfo as c where c.bankid like :bankid order by c.bankid";
		Iterator it = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setString("bankid", "%" + bankid + "%");
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
	
}
