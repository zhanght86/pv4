package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Trcdtjyear;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> 和表zzt_trcdtjyear相关的业务逻辑</p>
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

public class TrcdtjyearBean
{
	public TrcdtjyearBean()throws HibernateException
	{

	}

	/**
	 * 根据操输入的HQL统计年报表<br><br>
	 *
	 * @param HQLstr String型HQL查询语句
	 * @return List 包含有Trcdtjyear对象的List
	 * @throws HibernateException
	 */
	public List getQueryList( String HQLstr )throws HibernateException
	{
		String queryString = HQLstr.trim();
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

}
