package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.*;
import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.orm.Autosta;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> 和表zzt_autosta相关的业务逻辑</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p>
 * @author 刘太沛
 * @version 1.0 2005/4/7
 */

/**
 * <p><b>Description:</b>当捕获到异常时调用closeFactory方法</p>
 * @author xucc
 * @version 1.0 2007/04/09
 */

public class AutostaBean{
	public AutostaBean()throws HibernateException{

	}

	/**
	 * 添加一个Autosta对象到数据库
	 *
	 * @param st 自动统计任务信息对象
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean addAutosta(Autosta st)throws HibernateException{
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
		}return result;
	}
	/**
	 * 更新Autosta对象到数据库
	 *
	 * @param st 自动统计任务信息对象
	 * @param id 自动统计任务编号
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean updateAutosta(Autosta st)throws HibernateException{
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
	
	public static boolean updateAutosta(Autosta st , String id)throws HibernateException{
		return updateAutosta(st);
	}

	/**
	 * 删除给定ID(自动统计任务编号id)的自动统计任务信息
	 *
	 * @param id 自动统计任务编号
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean deleteAutosta(String id)throws HibernateException{
	 	boolean result = false;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();

			session.delete("select c from Autosta as c where c.id='"+id+"'");
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
	 * 删除给定ID(机构编号标示bankid)的自动统计任务信息
	 *
	 * @param bankid 机构编号标示
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean deleteSomeAutosta(String bankid)throws HibernateException{
	 	boolean result = false;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();

			session.delete("select c from Autosta as c where c.bankid like '"+bankid+"%'");
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
	 * 查询系统中所有的自动统计任务信息<br>
	 * 返回的是包含有Autosta对象的List
	 *
	 * @return List
	 * @throws HibernateException
	 */
	public List getAllAutostaList()throws HibernateException{
		String queryString = "from Autosta as Autostas order by Autostas.id desc";
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
	 * 查询系统中所有的自动统计任务信息<br>
	 * 返回的是包含有Autosta对象的List
	 * @return List
	 * @throws HibernateException
	 */
	public List getListByBankid( String bankid, int firstRow, int maxResults )throws HibernateException{
		String queryString = "from Autosta where bankid like '%"+bankid+"%'";
		PubUtil myPubUtil = new PubUtil();
		if( ( myPubUtil.dealNull(Constants.DB_OP_TYPE) ).equals("1") )
			queryString += " order by id desc";
		List it = null;
		try{
			String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent","ServType","0","PowerView.ini")).trim();
			if( servtype.equals("") ) servtype = "0";
		
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			//通过配置文件判断所用分页方式 xucc 20090624
			if(servtype.equals("0")){
				it= session.createQuery(queryString)
	      					 .setFirstResult(firstRow)
	      					 .setMaxResults(maxResults)
	      					 .list();
			
			}else if(servtype.equals("1")){
				it = new ArrayList();
				Query query = session.createQuery(queryString);
				ScrollableResults rs = query.scroll();
				for (int i = firstRow; i < firstRow+maxResults; i++) {
					if(rs.setRowNumber(i)){
						it.add(rs.get(0));
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
	 * 查询系统中所有的自动统计任务信息<br>
	 * 返回的是包含有Autosta对象的List
	 *
	 * @return List
	 * @throws HibernateException
	 */
	public List getListByBankid_Opentag( String bankid, String opentag, int firstRow, int maxResults )throws HibernateException{
		String queryString = "from Autosta ";
		queryString += "where bankid like '%"+bankid+"%' and opentag='"+opentag+"' ";
		PubUtil myPubUtil = new PubUtil();
		if( ( myPubUtil.dealNull(Constants.DB_OP_TYPE) ).equals("1") )
			queryString += "order by id desc";
		List it = null;

		try{
			String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent","ServType","0","PowerView.ini")).trim();
			if( servtype.equals("") ) servtype = "0";
		
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			
			//通过配置文件判断所用分页方式 xucc 20090624
			if(servtype.equals("0")){
				it= session.createQuery(queryString)
	      					 .setFirstResult(firstRow)
	      					 .setMaxResults(maxResults)
	      					 .list();
				
			}else if(servtype.equals("1")){
				it = new ArrayList();
				Query query = session.createQuery(queryString);
				ScrollableResults rs = query.scroll();
				for (int i = firstRow; i < firstRow+maxResults; i++) {
					if(rs.setRowNumber(i)){
						it.add(rs.get(0));
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
	 * 按自动统计任务编号获得Autosta持久对象(不要直接使用，请用AutostaModel中的方法)
	 *
	 * @param id 自动统计任务编号
	 * @return Autosta
	 * @throws HibernateException
	 */
	public Autosta getAutosta(String id)throws HibernateException{
		String queryString = "select c from Autosta as c where c.id='"+id+"'" ;
		Iterator it = null;
		Autosta temp = null;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			//query.setString("id",id);

			it = query.iterate();
			//tx.commit();
			if( it.hasNext()){
				temp=(Autosta)it.next();
			}
			
			tx.commit();//取到next值再commit（informix取值前commit报错） xucc 20090624
			
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

	/**
	 *返回指定机构的自动统计任务表列表的总数
	 *
	 *@param String bankid 机构ID
	 *@return 指定机构的统计自动任务表列表总数
	 */
	public int getAllCount( String bankid ) throws HibernateException {
		String qs = "select count(*) from Autosta where bankid like '%" + bankid + "%'";

		List lt = null;
		int count = 0;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();

			lt= session.createQuery(qs).list();
			count = ((Integer)lt.get(0)).intValue();
			tx.commit();//先处理结果再commit xucc 20090624
		} catch(Exception e) {
			HibernateUtil.closeFactory();
			return 0;
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}
		return count;
	}

	/**
	 *返回指定机构中指定分类的统计任务表中指定状态的列表总数
	 *
	 *@param String bankid 机构ID
	 *@param String opentag 任务状态
	 *@return 指定机构的统计任务表列表总数
	 */
	public int getCount( String bankid, String opentag ) throws HibernateException {
		String qs = "select count(*) from Autosta where opentag='" + opentag + "'";
		qs += " and bankid like '%" + bankid + "%'";

		List lt = null;
		int count = 0;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			lt = session.createQuery(qs).list();
			count = ((Integer)lt.get(0)).intValue();
			tx.commit();//先处理结果再commit xucc 20090624
		} catch(Exception e) {
			HibernateUtil.closeFactory();
			return 0;
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}
		return count;
	}

}
