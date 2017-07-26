package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Plugin;
import com.lcjr.pvxp.util.PubUtil;
import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> 和表zzt_pvplugin相关的业务逻辑</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p>
 * @author 杨旭
 * @version 1.0 2005/03/28
 */

/**
 * <p><b>Description:</b>当捕获到异常时调用closeFactory方法</p>
 * @author xucc
 * @version 1.0 2007/04/09
 */
public class PluginBean
{
	public PluginBean()throws HibernateException
	{

	}
	
	/**
	 * 添加一个Plugin对象到数据库
	 * 
	 * @param st 操作员信息对象
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
	 * 更新Plugin对象到数据库
	 * 
	 * @param st 操作员信息对象
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
	 * 查询系统中所有的Plugin信息<br>
	 * 返回的是包含有Plugin持久对象的Iterator
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
	 * 查询系统中所有的Plugin个数<br>
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
			//tx.commit();//20090414 gemler informix数据库注释掉 
			if( it==null || !it.hasNext() ) return 0;
		  
			mcount = ( (Integer)( it.next() ) ).intValue();
			tx.commit();//取到next值再commit（informix取值前commit报错） xucc 20090624
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
	 * 查询系统中符合条件的Plugin个数<br>
	 * 
	 * @param sqlwhere 条件语句
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
			//tx.commit();//20090414 gemler informix数据库注释掉
			if( it==null || !it.hasNext() ) return 0;
		  
			mcount = ( (Integer)( it.next() ) ).intValue();
			tx.commit();//取到next值再commit（informix取值前commit报错） xucc 20090624
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
	 * 查询系统中所有的Plugin信息<br>
	 * 返回的是包含有Plugin对象的List
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
	 * 查询系统中部分Plugin信息<br>
	 * 返回的是包含有Plugin对象的List
	 *
	 * @param intstart 起始记录
	 * @param maxlen 	 最多返回多少条
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
			//通过配置文件判断用哪种分页方式 xucc 20090624
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
	 * 查询系统中部分Plugin信息<br>
	 * 返回的是包含有Plugin对象的List
	 *
	 * @param intstart 起始记录
	 * @param maxlen 	 最多返回多少条
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
			
			//通过配置文件判断用哪种分页方式 xucc 20090624
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
	 * 删除给定ID(plugid)的Plugin信息
	 * 
	 * @param plugid Plugin编号
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
	 * 按ID获得Plugin持久对象(不要直接使用，请用PluginModel中的方法)
	 * 
	 * @param plugid 操作员编号
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
			//tx.commit(); //20090414 gemler informix数据库注释掉
			if( it.hasNext()){
				temp=(Plugin)it.next();
			}
			
			tx.commit();//取到next值再提commit（informix取值前commit报错  xucc 20090624
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
