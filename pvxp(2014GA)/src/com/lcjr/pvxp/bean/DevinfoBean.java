package com.lcjr.pvxp.bean;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import com.lcjr.pvxp.bean.interfac.BaseDAO;
import com.lcjr.pvxp.bean.interfac.DevInfoDAO;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.pojo.JGResult;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> �ͱ�zzt_devinfo��ص�ҵ���߼�</p>
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
@Service
public class DevinfoBean extends BaseDAO<Devinfo> implements DevInfoDAO {

	public DevinfoBean() throws HibernateException {

	}

	/**
	 * ���һ��Devinfo�������ݿ�
	 * 
	 * @param st
	 *            �豸��Ϣ����
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean addDevinfo(Devinfo st) throws HibernateException {
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
	 * ����Devinfo�������ݿ�
	 * 
	 * @param st
	 *            �豸��Ϣ����
	 * @param devno
	 *            �豸���
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean updateDevinfo(Devinfo st) throws HibernateException {
		boolean result = false;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			session.update(st);
			tx.commit();
			result = tx.wasCommitted();
		} catch (Exception e) {
			e.printStackTrace();
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

	public static boolean updateDevinfo(Devinfo st, String devno) throws HibernateException {
		return updateDevinfo(st);
	}

	/**
	 * ��ѯϵͳ�����е��豸��Ϣ<br>
	 * ���ص��ǰ�����Devinfo�־ö����Iterator
	 * 
	 * @return Iterator
	 * @throws HibernateException
	 */
	public Iterator<?> getAllDevinfos() throws HibernateException {
		String queryString = "select devinfos from Devinfo as devinfos";
		Iterator<?> it = null;
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
	 * ��ѯϵͳ�����е��豸��Ϣ<br>
	 * ���ص��ǰ�����Devinfo�����List
	 * 
	 * @return List
	 * @throws HibernateException
	 */
	public List<Devinfo> getAllDevList() throws HibernateException {
		String queryString = "select devinfos from Devinfo as devinfos order by devinfos.devno";
		List<Devinfo> it = null;
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
	 * ɾ������ID(�豸���devno)���豸��Ϣ
	 * 
	 * @param devno
	 *            �豸���
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean deleteDevinfo(String devno) throws HibernateException {
		boolean result = false;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			session.delete("select c from Devinfo as c where c.devno='" + devno + "'");
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
	 * ɾ������ID(������ű�ʾbankid)���豸��Ϣ
	 * 
	 * @param bankid
	 *            ������ű�ʾ
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean deleteSomeDevinfo(String bankid) throws HibernateException {
		boolean result = false;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			session.delete("select c from Devinfo as c where c.bankid like '" + bankid + "%'");
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
	 * ���豸��Ž���ģ������<br>
	 * ���ص��ǰ�����Devinfo�־ö����Iterator
	 * 
	 * @param devno
	 *            �豸���
	 * @return Iterator
	 * @throws HibernateException
	 */
	public Iterator<?> getSomeDevinfo(String devno) throws HibernateException {
		String queryString = "select c from Devinfo as c where c.devno like :devno order by c.devno";
		Iterator<?> it = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setString("devno", "%" + devno + "%");
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
	 * ɾ��ָ�����͵��豸
	 * 
	 * @param typeno
	 *            �豸���ͱ�� return boolean
	 * @throws HibernateException
	 */
	public static boolean deleteDevByType(String typeno) throws HibernateException {
		boolean result = false;
		// log.debug("qs=from Devinfo where typeno='"+typeno+"'");
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			session.delete("from Devinfo where typeno='" + typeno + "'");
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

	@Override
	public Devinfo select(String sqlString) throws HibernateException {

		Session session = null;
		Devinfo devinfo = null;
		try {
			session = (Session) HibernateUtil.currentSession();
			Query query = session.createQuery("from ");
			devinfo = (Devinfo) query.uniqueResult();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession();
		}
		return devinfo;
	}
	
	
	public int getTypeNum(String bankid) throws HibernateException {
		List<String> it = new ArrayList<String>();
		String queryString = "SELECT  count(*) from ZZT_DEVINFO WHERE remark1='"+bankid+"' GROUP BY typeno"; 
		Session session = HibernateUtil.currentSession();
		Transaction trans = null;
		trans = session.beginTransaction();
		JGResult jgr = null;
		Statement statement;
		try {
			
			statement = session.connection().createStatement();
			ResultSet rs = statement.executeQuery(queryString);
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			while (rs.next()) {
				
				 it.add(rs.getObject(1).toString());
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		trans.commit();
		return it.size();
	}

}
