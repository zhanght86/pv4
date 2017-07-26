package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Devftinfo;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> �ͱ�zzt_devftinfo��ص�ҵ���߼�</p>
 * <p><b>Copyright:</b> Copyright (c) 2010</p>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p>
 * @author xucc
 * @version 1.0 2010/09/26
 */
public class DevftBean
{
	public DevftBean()throws HibernateException{

	}
	
	
	/**
	 * ���һ��Devftinfo�������ݿ�
	 * 
	 * @param st �豸���̶���
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
	 * ����Devftinfo�������ݿ�
	 * 
	 * @param st �豸���̶���
	 * @param devftno ���̱��
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
	 * ��ѯϵͳ�����е��豸������Ϣ<br>
	 * ���ص��ǰ�����Devftinfo�־ö����Iterator
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
	 * ��ѯϵͳ�����е��豸������Ϣ<br>
	 * ���ص��ǰ�����Devftinfo�����List
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
	 * ɾ������ID(���̱��devftno)�ĳ�����Ϣ
	 * 
	 * @param devftno ���̱��
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
	 * �����̱�Ž���ģ������<br>
	 * ���ص��ǰ�����Devftinfo�־ö����Iterator
	 * 
	 * @param devftno ���̱��
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
