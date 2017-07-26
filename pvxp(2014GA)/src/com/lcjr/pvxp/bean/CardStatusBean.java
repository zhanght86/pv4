package com.lcjr.pvxp.bean;

import java.util.ArrayList;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.ScrollableResults;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.util.PubUtil;

public class CardStatusBean {
	
	/**
	 * 构造函数
	 * @throws HibernateException
	 */
	public CardStatusBean() throws HibernateException {
	}

	/**
	 * 根据查询条件查询发卡机发卡状态
	 * @param termnum	设备编号
	 * @param firstResult	页首记录
	 * @param maxResults	总记录数
	 * @return	返回查询记录
	 * @throws HibernateException
	 */
	public static List getCardStatusList(String[] termnum, int firstResult, int maxResults)
			throws HibernateException {

//		String queryString = "";// 查询语句
//		// make query language as usual
//		queryString = "  from CardStatus where devno in ('" + termnum[0] + "'";
//		for (int i = 1; i < termnum.length; i++) {
//			queryString += ", '" + termnum[i] + "'";
//		}
//		queryString += ")";
//	
//		PubUtil myPubUtil = new PubUtil();
//
//		List result = null;
//		try {
//			String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent", "ServType", "0","PowerView.ini")).trim();
//			if (servtype.equals(""))
//				servtype = "0";
//
//			Session session = HibernateUtil.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery(queryString);
//			System.out.println(queryString);
//			// 通过配置文件判断所用分页方式 xucc 20090624
//			// wukp20110914 原始为：if(servtype.equals("0")){
//			if (servtype.equals("0")) {
//				result = query.setFirstResult(firstResult).setMaxResults(
//						maxResults).list();
//				// wukp20110914 数据库管理系统中的 游标 使用
//			} else if (servtype.equals("1")) {
//				result = new ArrayList();
//				result = query.list();
//
//				ScrollableResults sr = query.scroll();
//				for (int i = firstResult; i < firstResult + maxResults; i++) {
//					System.out.println("  " + sr.getRowNumber());
//					if (sr.setRowNumber(i)) {
//						result.add(sr.get(0));
//					}
//				}
//			}
//
//			tx.commit();
//		} catch (Exception e) {
//			HibernateUtil.closeFactory();
//			System.out.println("可能是设备太多导致出错了..");
//			return null;
//		} finally {
//			try {
//				HibernateUtil.closeSession();
//			} catch (Exception ex) {
//			}
//		}
//
//		return result;
		
		
		
		int count = 0;
		String queryString = "";
		count = termnum.length / 150 + 1;
		List result = new ArrayList();
		List result1 =null;
		PubUtil myPubUtil = new PubUtil();

		String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent", "ServType", "0","PowerView.ini")).trim();
		if (servtype.equals(""))
			servtype = "0";

		// 分段查询，减少 设备数量过多导致报错 的几率
		for (int i = 0; i < count; i++) {

			queryString = "from CardStatus where devno in (";

			for (int j = 0; (i * 150) + j < termnum.length && j < 150; j++) {
				queryString += "'" + termnum[i * 150 + j].trim() + "',";
			}

			// 去除字符串后多余的“，”
			queryString = queryString.substring(0, queryString.length() - 1) + ")";

			try {
				Session session = HibernateUtil.currentSession();
				Transaction tx = session.beginTransaction();
				Query query = session.createQuery(queryString);

				// 通过配置文件判断所用分页方式 xucc 20090624
				// wukp20110914 原始为：if(servtype.equals("0")){
				if (servtype.equals("0")) {
					result1 = query.setFirstResult(firstResult).setMaxResults(maxResults).list();
//					result1 = query.setFirstResult(firstResult).setMaxResults(maxResults).list();
					result.addAll(result1);
					// wukp20110914 数据库管理系统中的 游标 使用
				} else if (servtype.equals("1")) {
					result = new ArrayList();
					result = query.list();
					ScrollableResults sr = query.scroll();
					for (int k = firstResult; k < firstResult + maxResults; k++) {
						System.out.println("  " + sr.getRowNumber());
						if (sr.setRowNumber(k)) {
							result.add(sr.get(0));
						}
					}
				}

				tx.commit();
			} catch (Exception e) {
				System.out.println("cardStatusbean报错.."+e);
				HibernateUtil.closeFactory();
				return result;
			} finally {
				try {
					HibernateUtil.closeSession();
				} catch (Exception ex) {
				}
			}
		}
		return result;
	}

	/**
	 * 获得符合查询条件的数据条数
	 * @param termnum
	 * @return 获得符合查询条件的数据条数 by wukp
	 * @throws HibernateException
	 */
	public static int getCardStatusCount(String[] termnum) throws HibernateException {

//		String queryString = "";
//
//		queryString = " select count(*) from CardStatus where devno in ('"
//				+ termnum[0] + "'";
//		for (int i = 1; i < termnum.length; i++) {
//			queryString += ", '" + termnum[i] + "'";
//		}
//		queryString += ")";
//
//		List result = null;
//		int count = 0;
//		try {
//			Session session = HibernateUtil.currentSession();
//			Transaction tx = session.beginTransaction();
//			Query query = session.createQuery(queryString);
//
//			result = query.list();
//
//			count = ((Integer) result.get(0)).intValue();
//			tx.commit();// 先处理结果再commit xucc 20090624
//		} catch (Exception e) {
//			HibernateUtil.closeFactory();
//			return 0;
//		} finally {
//			try {
//				HibernateUtil.closeSession();
//			} catch (Exception ex) {
//			}
//		}
//		return count;
//	}
		
		
		int count = 0;
		String queryString = "";
		count = termnum.length / 150 + 1;
		List result = null;
		int sum = 0;
		// 分段查询，减少 设备数量过多导致报错 的几率
		for (int i = 0; i < count; i++) {

			queryString = " select count(*) from CardStatus where devno in (";

			for (int j = 0; (i * 150) + j < termnum.length && j < 150; j++) {
				queryString += "'" + termnum[i * 150 + j] + "',";
			}

			// 去除字符串后多余的“，”
			queryString = queryString.substring(0, queryString.length() - 1)
					+ ")";
	
			// 进行查询
			try {
				Session session = HibernateUtil.currentSession();
				Transaction tx = session.beginTransaction();
				Query query = session.createQuery(queryString);
				result = query.list();
				sum += ((Integer) result.get(0)).intValue();
				tx.commit();// 先处理结果再commit xucc 20090624
			} catch (Exception e) {
				
				HibernateUtil.closeFactory();
				return 0;
			} finally {
				try {
					HibernateUtil.closeSession();
				} catch (Exception ex) {
				}
			}
		}
		return sum;
	}
		
}
