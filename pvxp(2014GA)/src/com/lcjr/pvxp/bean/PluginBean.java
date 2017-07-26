package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Plugin;
import com.lcjr.pvxp.util.PubUtil;
import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> �ͱ�zzt_pvplugin��ص�ҵ���߼�</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p>
 * @author ����
 * @version 1.0 2005/03/28
 */

/**
 * <p><b>Description:</b>�������쳣ʱ����closeFactory����</p>
 * @author xucc
 * @version 1.0 2007/04/09
 */
public class PluginBean
{
	public PluginBean()throws HibernateException
	{

	}
	
	/**
	 * ���һ��Plugin�������ݿ�
	 * 
	 * @param st ����Ա��Ϣ����
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean addPlugin(Plugin st)throws HibernateException{
		boolean result = true;
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
	 * ����Plugin�������ݿ�
	 * 
	 * @param st ����Ա��Ϣ����
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean updatePlugin(Plugin st)throws HibernateException{
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
	

	/**
	 * ��ѯϵͳ�����е�Plugin��Ϣ<br>
	 * ���ص��ǰ�����Plugin�־ö����Iterator
	 * 
	 * @return Iterator
	 * @throws HibernateException
	 */
	public Iterator getAllPlugins()throws HibernateException
	{
		String queryString = "select pls from Plugin as pls";
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
	 * ��ѯϵͳ�����е�Plugin����<br>
	 * 
	 * @return int
	 * @throws HibernateException
	 */
	public int getAllCount()throws HibernateException {
	
		String queryString = "select count(*) from Plugin";
		Iterator it = null;
		int mcount = 0;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			it= query.iterate();
			//tx.commit();//20090414 gemler informix���ݿ�ע�͵� 
			if( it==null || !it.hasNext() ) return 0;
		  
			mcount = ( (Integer)( it.next() ) ).intValue();
			tx.commit();//ȡ��nextֵ��commit��informixȡֵǰcommit���� xucc 20090624
		}catch(Exception e){
			HibernateUtil.closeFactory();
			System.out.println("-------------["+e+"]--------");
			return 0;	
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}
		return mcount;
	}
	/**
	 * ��ѯϵͳ�з���������Plugin����<br>
	 * 
	 * @param sqlwhere �������
	 * @return int
	 * @throws HibernateException
	 */
	public int getCount(String sqlwhere)throws HibernateException {
	
		String queryString = "select count(*) from Plugin"+sqlwhere;
		Iterator it = null;
		int mcount = 0;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			it= query.iterate();
			//tx.commit();//20090414 gemler informix���ݿ�ע�͵�
			if( it==null || !it.hasNext() ) return 0;
		  
			mcount = ( (Integer)( it.next() ) ).intValue();
			tx.commit();//ȡ��nextֵ��commit��informixȡֵǰcommit���� xucc 20090624
		}catch(Exception e){
			HibernateUtil.closeFactory();
			System.out.println("-------------["+e+"]--------");
			return 0;	
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}
		return mcount;
	}
	/**
	 * ��ѯϵͳ�����е�Plugin��Ϣ<br>
	 * ���ص��ǰ�����Plugin�����List
	 * 
	 * @return List
	 * @throws HibernateException
	 */
	public List getAllPluginList()throws HibernateException
	{
		String queryString = "select pls from Plugin as pls order by pls.plugid";
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
	 * ��ѯϵͳ�в���Plugin��Ϣ<br>
	 * ���ص��ǰ�����Plugin�����List
	 *
	 * @param intstart ��ʼ��¼
	 * @param maxlen 	 ��෵�ض�����
	 *
	 * @return List
	 * @throws HibernateException
	 */
	public List getPluginList(int intstart,int maxlen)throws HibernateException {
		String queryString = "select pls from Plugin as pls";
		//String queryString = "select pls from Plugin as pls order by pls.plugid";
		List it = null;
		PubUtil myPubUtil = new PubUtil();
		try{
			String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent","ServType","0","PowerView.ini")).trim();
			if( servtype.equals("") ) servtype = "0";
		
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			//ͨ�������ļ��ж������ַ�ҳ��ʽ xucc 20090624
			if(servtype.equals("0")){
				it= query.setFirstResult(intstart).setMaxResults(maxlen).list();
			}else if(servtype.equals("1")){
				it = new ArrayList();
				//ScrollableResults rs = query.scroll();
				List tmp = query.list();
				for (int i = intstart; i < intstart+maxlen; i++) {
					if(tmp.size()>i){
						it.add((Plugin)tmp.get(i));
					}
				}
			}
			
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
	 * ��ѯϵͳ�в���Plugin��Ϣ<br>
	 * ���ص��ǰ�����Plugin�����List
	 *
	 * @param intstart ��ʼ��¼
	 * @param maxlen 	 ��෵�ض�����
	 *
	 * @return List
	 * @throws HibernateException
	 */
	public List getPluginList(int intstart,int maxlen,String sqlwhere)throws HibernateException {
		String queryString = "from Plugin "+sqlwhere;
		//String queryString = "from Plugin "+sqlwhere+" order by plugid";
		List it = null;
		PubUtil myPubUtil = new PubUtil();
		try{
			String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent","ServType","0","PowerView.ini")).trim();
			if( servtype.equals("") ) servtype = "0";
		
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			
			Query query = session.createQuery(queryString);
			
			//ͨ�������ļ��ж������ַ�ҳ��ʽ xucc 20090624
			if( servtype.equals("0") ){
				it= query.setFirstResult(intstart).setMaxResults(maxlen).list();
			}else if(servtype.equals("1")){
				it = new ArrayList();
				//ScrollableResults rs = query.scroll();
				List tmp = query.list();
				for (int i = intstart; i < intstart+maxlen; i++) {
					if(tmp.size()>i){
						it.add((Plugin)tmp.get(i));
					}
				}
			}

			tx.commit();
		}catch(Exception e){
			System.out.println("ee="+e);
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
	 * ɾ������ID(plugid)��Plugin��Ϣ
	 * 
	 * @param plugid Plugin���
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean deletePlugin(String plugid)throws HibernateException
	{
	 	boolean result = false;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();   	

			session.delete("select c from Plugin as c where c.plugid='"+plugid+"'");
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
	 * ��ID���Plugin�־ö���(��Ҫֱ��ʹ�ã�����PluginModel�еķ���)
	 * 
	 * @param plugid ����Ա���
	 * @return Operator
	 * @throws HibernateException
	 */
	public Plugin getPlugin(String plugid)throws HibernateException{
		String queryString = "select c from Plugin as c where c.plugid='"+plugid+"'" ;
		Iterator it = null;
		Plugin temp = null;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			it = query.iterate();
			//tx.commit(); //20090414 gemler informix���ݿ�ע�͵�
			if( it.hasNext()){
				temp=(Plugin)it.next();
			}
			
			tx.commit();//ȡ��nextֵ����commit��informixȡֵǰcommit����  xucc 20090624
		}catch(Exception e){
			HibernateUtil.closeFactory();
			return null;	
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}
		return temp;
	}  
}
