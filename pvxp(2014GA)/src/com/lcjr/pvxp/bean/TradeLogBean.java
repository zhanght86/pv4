package com.lcjr.pvxp.bean;

import java.util.ArrayList;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.ScrollableResults;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import com.lcjr.pvxp.orm.TradeLog;
import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> 和表zzt_trcdlog相关的业务逻辑</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p>
 * @author 吴学坤
 * @version 1.0 2005/03/25
 */

/**@author xucc
 * @Description:增加方法getTradeLog(String qs)
 * @version 1.1 2006/12/18
 */

/**
 * <p>
 * <b>Description:</b>当捕获到异常时调用closeFactory方法
 * </p>
 * 
 * @author xucc
 * @version 1.0 2007/04/09
 */

public class TradeLogBean {

	public static List<TradeLog> getTradeLog(String qs, int firstResult, int maxResults) throws HibernateException {
		PubUtil myPubUtil = new PubUtil();

		List<TradeLog> result = null;
		try {
			String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent", "ServType", "0", "PowerView.ini")).trim();
			if (servtype.equals(""))
				servtype = "0";

			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(qs);
			// 通过配置文件判断所用分页方式 xucc 20090624
			if (servtype.equals("0")) {
				result = query.setFirstResult(firstResult).setMaxResults(maxResults).list();
			} else if (servtype.equals("1")) {
				result = new ArrayList<TradeLog>();
				ScrollableResults rs = query.scroll();
				for (int i = firstResult; i < firstResult + maxResults; i++) {
					if (rs.setRowNumber(i)) {
						result.add((TradeLog)rs.get(0));
					}
				}
			}
			tx.commit();
		} catch (Exception e) {

			System.out.println("e=" + e);
			HibernateUtil.closeFactory();
			return null;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
			}
		}

		return result;

	}

	public static int getTradeLogCount(String qs) throws HibernateException {

		List result = null;
		int count = 0;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(qs);
			result = query.list();
			if (result == null || result.size() == 0) {
				count = 0;
			} else {
				count = ((Integer) result.get(0)).intValue();
			}
			tx.commit();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			return 0;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
			}
		}
		return count;
	}

	public static List<TradeLog> getTradeLog(String qs) throws HibernateException {

		List<TradeLog> result = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(qs);
			result = query.list();
			tx.commit();
		} catch (Exception e) {
			System.out.println(e);
			HibernateUtil.closeFactory();
			return null;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
		return result;
	}
}
