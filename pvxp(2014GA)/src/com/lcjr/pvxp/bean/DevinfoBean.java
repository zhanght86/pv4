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
 * <p><b>Description:</b> 和表zzt_devinfo相关的业务逻辑</p>
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
@Service
public class DevinfoBean extends BaseDAO<Devinfo> implements DevInfoDAO {

	public DevinfoBean() throws HibernateException {

	}

	/**
	 * 添加一个Devinfo对象到数据库
	 * 
	 * @param st
	 *            设备信息对象
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
	 * 更新Devinfo对象到数据库
	 * 
	 * @param st
	 *            设备信息对象
	 * @param devno
	 *            设备编号
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
	 * 查询系统中所有的设备信息<br>
	 * 返回的是包含有Devinfo持久对象的Iterator
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
	 * 查询系统中所有的设备信息<br>
	 * 返回的是包含有Devinfo对象的List
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
	 * 删除给定ID(设备编号devno)的设备信息
	 * 
	 * @param devno
	 *            设备编号
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
	 * 删除给定ID(机构编号标示bankid)的设备信息
	 * 
	 * @param bankid
	 *            机构编号标示
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
	 * 按设备编号进行模糊查找<br>
	 * 返回的是包含有Devinfo持久对象的Iterator
	 * 
	 * @param devno
	 *            设备编号
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
	 * 删除指定类型的设备
	 * 
	 * @param typeno
	 *            设备类型编号 return boolean
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
