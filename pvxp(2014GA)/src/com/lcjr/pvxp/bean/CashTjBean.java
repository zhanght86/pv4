package com.lcjr.pvxp.bean;

import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import com.lcjr.pvxp.orm.CashDetail;
import com.lcjr.pvxp.orm.util.HibernateUtil;
/**
 * 
* <p>Title: CashTjBean.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: inspur</p>
* @author wang-jl
* @date 2014-3-21
* @version 1.0
 */
public class CashTjBean {
	/**
	 * ���캯����ʼ��
	 * 
	 * @throws HibernateException
	 */
	public CashTjBean() throws HibernateException {

	}

	/**
	 * ���ݲ������HQLͳ���걨��<br>
	 * <br>
	 * 
	 * @param HQLstr
	 *            String��HQL��ѯ���
	 * @return List ������Trcdtjyear�����List
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
			System.out.println("CardTjBean�������..");
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
	 * ���ݲ������HQLͳ���걨��<br>
	 * <br>
	 * 
	 * @param HQLstr
	 *            String��HQL��ѯ���
	 * @return List ������Trcdtjyear�����List
	 * @throws HibernateException
	 */
	public List<CashDetail> getQueryArray(String HQLstr) throws HibernateException {
		List<CashDetail> list = null;
		String queryString = "select count(*),cd.devno,sum(cd.totalamount)  from CashDetail as cd group by cd.devno";
		try {
			
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			list = query.list();
			tx.commit();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			System.out.println("CashTjBean�������..");
			return null;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
			}
		}
		return list;
	}
}
