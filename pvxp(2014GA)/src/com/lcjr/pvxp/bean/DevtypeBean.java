package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Devtype;

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
 * <p><b>Description:</b>�������쳣ʱ����closeFactory����</p>
 * @author xucc
 * @version 1.0 2007/04/09
 */

public class DevtypeBean{
	public DevtypeBean()throws HibernateException{

	}
	
	
	/**
	 * ���һ��Devtype�������ݿ�
	 * 
	 * @param st �豸���Ͷ���
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
	 * ����Devtype�������ݿ�
	 * 
	 * @param st �豸���Ͷ���
	 * @param typeno �豸���ͱ��
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
	 * ��ѯϵͳ�����е��豸������Ϣ<br>
	 * ���ص��ǰ�����Devtype�־ö����Iterator
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
	 * ��ѯϵͳ�����е��豸������Ϣ<br>
	 * ���ص��ǰ�����Devtype�����List
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
	 * ɾ������ID(�豸���ͱ��typeno)���豸������Ϣ
	 * 
	 * @param typeno �豸���ͱ��
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
	 * ���豸���ͱ�Ž���ģ������<br>
	 * ���ص��ǰ�����Devtype�־ö����Iterator
	 * 
	 * @param typeno �豸���ͱ��
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
