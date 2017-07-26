package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Devtype;

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
 * <p><b>Description:</b>当捕获到异常时调用closeFactory方法</p>
 * @author xucc
 * @version 1.0 2007/04/09
 */

public class DevtypeBean{
	public DevtypeBean()throws HibernateException{

	}
	
	
	/**
	 * 添加一个Devtype对象到数据库
	 * 
	 * @param st 设备类型对象
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean addDevtype(Devtype st)throws HibernateException{
		boolean result = false;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			session.save(st);
			tx.commit();
			result=tx.wasCommitted();
		}catch(Exception e){
			HibernateUtil.closeFactory();
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
	 * 更新Devtype对象到数据库
	 * 
	 * @param st 设备类型对象
	 * @param typeno 设备类型编号
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean updateDevtype(Devtype st)throws HibernateException{
		boolean result = false;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			session.update(st);
			tx.commit();
			result=tx.wasCommitted();
		}catch(Exception e){
			HibernateUtil.closeFactory();
			result = false;
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
				
			}
		}
		return result;
	}
	
	public static boolean updateDevtype(Devtype st , String typeno)throws HibernateException{
		return updateDevtype(st);
	}
	/**
	 * 查询系统中所有的设备类型信息<br>
	 * 返回的是包含有Devtype持久对象的Iterator
	 * 
	 * @return Iterator
	 * @throws HibernateException
	 */
	public Iterator getAllDevtypes()throws HibernateException{
		String queryString = "select devtypes from Devtype as devtypes";
		Iterator it = null;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			it= query.iterate();
			tx.commit();
		}catch(Exception e){
			HibernateUtil.closeFactory();
			return null;	
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}
		  return it;
	}
	
	
	/**
	 * 查询系统中所有的设备类型信息<br>
	 * 返回的是包含有Devtype对象的List
	 * 
	 * @return List
	 * @throws HibernateException
	 */
	public List getAllDevtypeList()throws HibernateException{
		String queryString = "select devtypes from Devtype as devtypes order by devtypes.typeno";
		List it = null;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			it= query.list();
			tx.commit();
		}catch(Exception e){
			HibernateUtil.closeFactory();
			return null;	
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}
		  return it;
	}
		
	/**
	 * 删除给定ID(设备类型编号typeno)的设备类型信息
	 * 
	 * @param typeno 设备类型编号
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean deleteDevtype(String typeno)throws HibernateException{
	 	boolean result = false;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();   	
			session.delete("select c from Devtype as c where c.typeno='"+typeno+"'");
			tx.commit();
			result=tx.wasCommitted();
		}catch(Exception e){
			HibernateUtil.closeFactory();
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
	 * 按设备类型编号进行模糊查找<br>
	 * 返回的是包含有Devtype持久对象的Iterator
	 * 
	 * @param typeno 设备类型编号
	 * @return Iterator
	 * @throws HibernateException
	 */
	public Iterator getSomeDevtype(String typeno)throws HibernateException{
		String queryString = "select c from Devtype as c where c.typeno like :typeno order by c.typeno" ;
		Iterator it = null;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setString("typeno", "%"+typeno+"%");
			it= query.iterate();
			tx.commit();
		}catch(Exception e){
			HibernateUtil.closeFactory();
			return null;	
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}
		return it;
	}    
		
}
