package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Trcdtjyear;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> 和表zzt_trcdtjyear相关的业务逻辑
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2011
 * </p>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * 
 * @author 武坤鹏
 * @version20110113
 * 
 */
public class CardTjBean {


	/**
	 * 构造函数初始化
	 * 
	 * @throws HibernateException
	 */
	public CardTjBean() throws HibernateException {

	}

	/**
	 * 根据操输入的HQL统计年报表<br>
	 * <br>
	 * 
	 * @param HQLstr
	 *            String型HQL查询语句
	 * @return List 包含有Trcdtjyear对象的List
	 * @throws HibernateException
	 */
	public List getQueryList(String HQLstr) throws HibernateException {
		String queryString = HQLstr.trim();
		List it = null;

		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			it = query.list();
			tx.commit();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			System.out.println("CardTjBean类出错了..");
			return null;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
			}
		}
		return it;
	}
	
	
	
	
	
	
	/**
	 * 根据操输入的HQL统计年报表<br>
	 * <br>
	 * 
	 * @param HQLstr
	 *            String型HQL查询语句
	 * @return List 包含有Trcdtjyear对象的List
	 * @throws HibernateException
	 */
	public Object[] getQueryArray(String HQLstr) throws HibernateException {
//		String queryString = HQLstr.trim();
		String queryString = "select devno,outcardstatus,count(*) as A " +
				"from CardOut where outcardstatus in ('0','1') group by devno,outcardstatus";
		List it = null;
		Object obj[] =null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			it = query.list();
			for (int i=0;i<it.size(); i++)
			{
				obj[i]= (Object[])it.get(i);
			}
			tx.commit();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			System.out.println("CardTjBean类出错了..");
			return null;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
			}
		}
		return obj;
	}
	
	
	
	
	
	
}
