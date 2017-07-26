package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Bankinfo;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> �ͱ�zzt_devtype��ص�ҵ���߼�</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p>
 * @author ����
 * @version 1.0 2004/12/08
 */

/**
 * <p>
 * <b>Description:</b>�������쳣ʱ����closeFactory����
 * </p>
 * 
 * @author xucc
 * @version 1.0 2007/04/09
 */

public class BankinfoBean {
	public BankinfoBean() throws HibernateException {
		
	}
	
	
	
	/**
	 * ���һ��Bankinfo�������ݿ�
	 * 
	 * @param st
	 *            ������ϢBankinfo����
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
	 * ����Bankinfo�������ݿ�
	 * 
	 * @param st
	 *            ������Ϣ����
	 * @param bankid
	 *            �������
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
	 * ��ѯϵͳ�����еĻ�����Ϣ<br>
	 * ���ص��ǰ�����Bankinfo�־ö����Iterator
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
	 * ��ѯϵͳ�����еĻ���������Ϣ<br>
	 * ���ص��ǰ�����Bankinfo��List
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
	 * ɾ������ID(�������bankid)�Ļ�����Ϣ
	 * 
	 * @param bankid
	 *            �������
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
	 * ɾ������ID���л���(������ű�ʾbankid)�Ļ�����Ϣ
	 * 
	 * @param bankid
	 *            ������ű�ʾ
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
	 * ��������Ž���ģ������<br>
	 * ���ص��ǰ�����Bankinfo�־ö����Iterator
	 * 
	 * @param bankid
	 *            �������
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
