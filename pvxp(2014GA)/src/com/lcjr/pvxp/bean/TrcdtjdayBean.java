package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Trcdtjday;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> 和表zzt_trcdtjday相关的业务逻辑</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p>
 * @author 刘太沛
 * @version 1.0 2005/3/30
 */

/**
 * <p><b>Description:</b>当捕获到异常时调用closeFactory方法</p>
 * @author xucc
 * @version 1.0 2007/04/09
 */
public class TrcdtjdayBean
{
	public TrcdtjdayBean()throws HibernateException
	{

	}

	/**
	 * 根据操输入的HQL统计日报表<br><br>
	 *
	 * @param HQLstr String型HQL查询语句
	 * @return List 包含有Trcdtjday对象的List
	 * @throws HibernateException
	 */
	public List getQueryList( String HQLstr )throws HibernateException
	{
		
		String queryString = HQLstr.trim();

		List it = null;
//		int p1=queryString.indexOf("(");
//		int p2=queryString.indexOf(")");
//		String firstpart=queryString.substring(0, p1);
//		String middlepart=queryString.substring(p1+1, p2)+"or";
//		String endpart=queryString.substring(p2+1, queryString.length());
		
//		( devno='13180107' or devno='13180108' or
//		devno='13180109' or devno='13180110' or devno='13180111' or devno='13180112' or
//		devno='13180113' or devno='13180114' or devno='13180115' or devno='13180116' or
//		devno='13180117' or devno='13210002' or devno='13210003' or devno='13210007' )
		// System.out.println("第一部分是"+firstpart);
//		System.out.println("中间部分是"+middlepart);
//		System.out.println("最后一部分是"+endpart);
//		
//		int pernum=middlepart.length()/20+1;
//		
//		for(int i=0;i<pernum;i++){
//			
//			
//		}
		

		try{
			Session session = HibernateUtil.currentSession();
			
			Transaction tx= session.beginTransaction();

			Query query = session.createQuery(queryString);

			it= query.list();

			tx.commit();

		}catch(Exception e){
//			System.out.println("组成的sql语句为："+HQLstr+"  是第 "+j+" 条查询语句");
//			System.out.println("出现错误...在trcdtjdaybean.java 中");
			HibernateUtil.closeFactory();
			e.printStackTrace();
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
	 * 插入数据<br><br>
	 *
	 * @param HQLstr String型HQL查询语句
	 * @return List 包含有Trcdtjday对象的List
	 * @throws HibernateException
	 */
	public void addTrcdtjday( Trcdtjday result )throws HibernateException
	{

		try{
			Session session = HibernateUtil.currentSession();
			
			Transaction tx= session.beginTransaction();
			
			for(int i=0;i<10;i++){
			session.save(result);

			}
//			session.save(result);

//			it= query.list();

			tx.commit();

		}catch(Exception e){
			System.out.println("出现错误"+e);
			HibernateUtil.closeFactory();
			e.printStackTrace();
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}
	}

}
