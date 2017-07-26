package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Operator;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> �ͱ�zzt_operator��ص�ҵ���߼�</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p>
 * @author ����
 * @version 1.0 2004/12/14
 */

/**
 * <p>
 * <b>Description:</b>�������쳣ʱ����closeFactory����
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
	 * ���һ��Operator�������ݿ�
	 * 
	 * @param st
	 *            ����Ա��Ϣ����
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
	 * ����Operator�������ݿ�
	 * 
	 * @param st
	 *            ����Ա��Ϣ����
	 * @param operid
	 *            ����Ա���
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
	 * ��ѯϵͳ�����еĲ���Ա��Ϣ<br>
	 * ���ص��ǰ�����Operator�־ö����Iterator
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
	 * ��ѯϵͳ�����еĲ���Ա��Ϣ<br>
	 * ���ص��ǰ�����Operator�����List
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
	 * ɾ������ID(����Ա���operid)�Ĳ���Ա��Ϣ
	 * 
	 * @param operid
	 *            ����Ա���
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
	 * ɾ������ID(������ű�ʾbankid)�Ĳ���Ա��Ϣ
	 * 
	 * @param bankid
	 *            ������ű�ʾ
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
	 * ������Ա��Ž���ģ������<br>
	 * ���ص��ǰ�����Operator�־ö����Iterator
	 * 
	 * @param operid
	 *            ����Ա���
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
	 * ������Ա��Ż��Operator�־ö���(��Ҫֱ��ʹ�ã�����OperatorModel�еķ���)
	 * 
	 * @param operid
	 *            ����Ա���
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
			// tx.commit(); //20090414 gemler informix���ݿ�ע�͵�
			if (it.hasNext()) {
				temp = (Operator) it.next();
			}
			tx.commit();// ȡ��nextֵ��commit��informixȡֵǰcommit���� xucc 20090622
			
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
