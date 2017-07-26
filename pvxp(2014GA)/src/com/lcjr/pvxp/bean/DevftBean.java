package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Devftinfo;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> 和表zzt_devftinfo相关的业务逻辑</p>
 * <p><b>Copyright:</b> Copyright (c) 2010</p>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p>
 * @author xucc
 * @version 1.0 2010/09/26
 */
public class DevftBean
{
	public DevftBean()throws HibernateException{

	}
	
	
	/**
	 * 添加一个Devftinfo对象到数据库
	 * 
	 * @param st 设备厂商对象
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean addDevft(Devftinfo st)throws HibernateException{
		boolean result = false;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			session.save(st);
			tx.commit();
			result=tx.wasCommitted();
		}catch(Exception e){
			result = false;
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
				
			}
		}
		return result;
	}
		
	/**
	 * 更新Devftinfo对象到数据库
	 * 
	 * @param st 设备厂商对象
	 * @param devftno 厂商编号
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean updateDevft(Devftinfo st)throws HibernateException{
		boolean result = false;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			session.update(st);
			tx.commit();
			result=tx.wasCommitted();
		}catch(Exception e){
			result = false;
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
				
			}
		}
		return result;
	}
	
	public static boolean updateDevft(Devftinfo st , String devftno)throws HibernateException{
		return updateDevft(st);
	}
	/**
	 * 查询系统中所有的设备厂商信息<br>
	 * 返回的是包含有Devftinfo持久对象的Iterator
	 * 
	 * @return Iterator
	 * @throws HibernateException
	 */
	public Iterator getAllDevft()throws HibernateException
	{
		String queryString = "select devft from Devftinfo as devft";
		Iterator it = null;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			it= query.iterate();
			tx.commit();
		}catch(Exception e){
			return null;	
		}
		  return it;
	}
	
	
	/**
	 * 查询系统中所有的设备厂商信息<br>
	 * 返回的是包含有Devftinfo对象的List
	 * 
	 * @return List
	 * @throws HibernateException
	 */
	public List getAllDevftList()throws HibernateException
	{
		String queryString = "select devft from Devftinfo as devft order by devft.devftno";
		List it = null;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			it= query.list();
			tx.commit();
		}catch(Exception e){
			return null;	
		}
		  return it;
	}
		
	/**
	 * 删除给定ID(厂商编号devftno)的厂商信息
	 * 
	 * @param devftno 厂商编号
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean deleteDevft(String devftno)throws HibernateException
	{
	 	boolean result = false;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();   	
			session.delete("select c from Devftinfo as c where c.devftno='"+devftno+"'");
			tx.commit();
			result=tx.wasCommitted();
		}catch(Exception e){
			result = false;
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}
		return result;
	}
	
	
	/**
	 * 按厂商编号进行模糊查找<br>
	 * 返回的是包含有Devftinfo持久对象的Iterator
	 * 
	 * @param devftno 厂商编号
	 * @return Iterator
	 * @throws HibernateException
	 */
	public Iterator getSomeDevft(String devftno)throws HibernateException
	{
		String queryString = "select c from Devftinfo as c where c.devftno like :devftno order by c.devftno" ;
		Iterator it = null;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setString("devftno", "%"+devftno+"%");
			it= query.iterate();
			tx.commit();
		}catch(Exception e){
			return null;	
		}
		return it;
	}    
		
}
