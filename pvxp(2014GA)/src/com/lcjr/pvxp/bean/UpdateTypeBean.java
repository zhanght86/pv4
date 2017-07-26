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
 * <b>Description:</b> �ͱ�zzt_updatetype��ص�ҵ���߼�
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2009
 * </p>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
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
	 * ���һ��Updatetype�������ݿ�
	 * 
	 * @param st
	 *            ���Ͷ���
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
	 * ����Updatetype�������ݿ�
	 * 
	 * @param st
	 *            ���Ͷ���
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
	 * ��ѯϵͳ�����еĸ���������Ϣ<br>
	 * ���ص��ǰ�����Updatetype�־ö����Iterator
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
	 * ��ѯϵͳ�����еĸ���������Ϣ<br>
	 * ���ص��ǰ�����Updatetype�����List
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
	 * ɾ������ID(�������ͱ��updateno)��������Ϣ
	 * 
	 * @param updateno
	 *            ���ͱ��
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
	 * �����ͱ�Ž���ģ������<br>
	 * ���ص��ǰ�����Updatetype�־ö����Iterator
	 * 
	 * @param updateno
	 *            ���ͱ��
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
	 * ����Ż�ó־ö���
	 * 
	 * @param updateno
	 *            ���
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
