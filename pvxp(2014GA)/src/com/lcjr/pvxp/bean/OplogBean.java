package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Oplog;
import com.lcjr.pvxp.util.*;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> 和表zzt_oplog相关的业务逻辑</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p>
 * @author 刘太沛
 * @version 1.0 2005/3/22
 */

/**
 * <p><b>Description:</b>当捕获到异常时调用closeFactory方法</p>
 * @author xucc
 * @version 1.0 2007/04/09
 */
public class OplogBean
{
	public OplogBean()throws HibernateException
	{

	}

	/**
	 * 添加一个Oplog对象到数据库
	 *
	 * @param st 操作员信息对象
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean addOplog(Oplog st)throws HibernateException{
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
	 * 查询系统中所有的操作流水信息<br>
	 * 返回的是包含有Oplog对象的List
	 *
	 * @return List
	 * @throws HibernateException
	 */
	public List getAllOplogList()throws HibernateException
	{
		String queryString = "select opers from Oplog as opers order by opers.operid";
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
	 * 根据操作员编号、操作日期、操作编号、操作类型<br>
	 * 查询系统中的操作流水信息<br><br>
	 * 返回的是包含有Oplog对象的List
	 *
	 * @return List
	 * @throws HibernateException
	 */
	public List getSomeOplogList( String moperid, String mopdate1,
		String mopdate2, String mtrcd, String mtype )throws HibernateException
	{
		String queryString = "from Oplog ";
		queryString += "where operid like '%"+moperid+"%' ";
		if ( !mopdate1.equals("") ) { queryString += "and opdate>='"+mopdate1+"' "; }
		if ( !mopdate2.equals("") ) { queryString += "and opdate<='"+mopdate2+"' "; }
		if ( !mtrcd.equals("") ) { queryString += "and trcd='"+mtrcd+"' "; }
		if ( !mtype.equals("") ) { queryString += "and type='"+mtype+"' "; }
		queryString += "order by opdate desc, optime desc";

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
	 * 删除小于等于给定日期的操作员操作记录
	 *
	 * @param opdate 操作日期
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean deleteOplog(String opdate)throws HibernateException
	{
	 	boolean result = false;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();

			session.delete("select c from Oplog as c where c.opdate<='"+opdate+"'");
			session.flush();
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
	 * 查询系统中部分Oplog信息<br>
	 * 返回的是包含有Oplog对象的List
	 *
	 * @param intstart 起始记录
	 * @param maxlen 	 最多返回多少条
	 *
	 * @return List
	 * @throws HibernateException
	 */
	public List getOplogList( String moperid, String mopdate1,
		String mopdate2, String mtrcd, String mtype, int intstart, int maxlen )throws HibernateException
	{
		String queryString = "from Oplog as c ";
		queryString += "where c.operid like '%"+moperid+"%' ";
		if ( !mopdate1.equals("") ) { queryString += "and c.opdate>='"+mopdate1+"' "; }
		if ( !mopdate2.equals("") ) { queryString += "and c.opdate<='"+mopdate2+"' "; }
		if ( !mtrcd.equals("") ) { queryString += "and c.trcd='"+mtrcd+"' "; }
		if ( !mtype.equals("") ) { queryString += "and c.type='"+mtype+"' "; }
		PubUtil myPubUtil = new PubUtil();
		if( ( myPubUtil.dealNull(Constants.DB_OP_TYPE) ).equals("1") )
			queryString += "order by c.opdate desc, c.optime desc";

		List it = null;
		try{
			String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent","ServType","0","PowerView.ini")).trim();
			if( servtype.equals("") ) servtype = "0";
		
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			
			//通过配置文件判断所用分页方式 xucc 20090624
			if(servtype.equals("0")){
				it= session.createQuery(queryString)
					.setFirstResult(intstart)
					.setMaxResults(maxlen)
					.list();
			
			}else if(servtype.equals("1")){
				it = new ArrayList();
				Query query = session.createQuery(queryString);
				ScrollableResults rs = query.scroll();
				for (int i = intstart; i < intstart+maxlen; i++) {
					if(rs.setRowNumber(i)){
						it.add(rs.get(0));
					}
				}
			}
			
			tx.commit();
		}catch(Exception e){ 
			e.printStackTrace();  
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
	 *返回指定Oplog信息的总数
	 *
	 *
	 *@return 指定Oplog信息总数
	 */
	public static int getOplogCount( String moperid, String mopdate1,
		String mopdate2, String mtrcd, String mtype )throws HibernateException
	{
		String queryString = "select count(*) from Oplog ";
		queryString += "where operid like '%"+moperid+"%' ";
		if ( !mopdate1.equals("") ) { queryString += "and opdate>='"+mopdate1+"' "; }
		if ( !mopdate2.equals("") ) { queryString += "and opdate<='"+mopdate2+"' "; }
		if ( !mtrcd.equals("") ) { queryString += "and trcd='"+mtrcd+"' "; }
		if ( !mtype.equals("") ) { queryString += "and type='"+mtype+"' "; }

		List lt = null;
		int count = 0;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			
			lt= session.createQuery(queryString).list();
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
	 * 查询系统中所有的Oplog个数<br>
	 * 
	 * @return int
	 * @throws HibernateException
	 */
	public int getAllCount()throws HibernateException {
	
		String queryString = "select count(*) from Oplog";
		Iterator it = null;
		int mcount = 0;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			it= query.iterate();
			//tx.commit(); ////20090414 gemler informix数据库注释掉 
			if( it==null || !it.hasNext() ) return 0;
		  
			mcount = ( (Integer)( it.next() ) ).intValue();
			
			tx.commit();//取到next值再commit xucc 20090624
			
		}catch(Exception e){
			HibernateUtil.closeFactory();
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
	 * 查询系统中所有复合条件的Oplog<br>
	 * 
	 * @return List
	 * @throws HibernateException
	 */
	public static List getQueryList(String HQLstr)throws HibernateException{
		String queryString = HQLstr.trim();
		List it = null;

		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			it= query.list();
			tx.commit();
		}catch(Exception e){
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
