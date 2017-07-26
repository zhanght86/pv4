package com.lcjr.pvxp.bean;

import java.util.ArrayList;
import java.util.List;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.util.PubUtil;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.ScrollableResults;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

/**
 * 与数据库中的zzt_card_out表对应的bean类
 * 
 * @author 武坤鹏
 * 
 */
public class CardOutBean {
	
	
	/**
	 * 构造函数
	 * 
	 * @throws HibernateException
	 */
	public CardOutBean() throws HibernateException {
	}
	
	
	
	/**
	 * 查询出符合条件的结果的个数
	 * 
	 * @param termnum
	 *            设备号
	 * @param date1
	 *            开始日期
	 * @param date2
	 *            截止日期
	 * @param time1
	 *            开始时间
	 * @param time2
	 *            截止时间
	 * @param id
	 *            身份证号
	 * @param cardno
	 *            发卡卡号
	 * @return 符合条件的结果的个数
	 * @throws HibernateException
	 */
	public static int getCardOutCount(String[] termnum, String date1, String date2, String time1, String time2, String id, String cardno) throws HibernateException {
		
		int count = 0;
		String queryString = "";
		count = termnum.length / 150 + 1;
		List result = null;
		int sum = 0;
		// 分段查询，减少 设备数量过多导致报错 的几率
		for (int i = 0; i < count; i++) {
			
			queryString = " select count(*) from CardOut where devno in (";
			
			for (int j = 0; (i * 150) + j < termnum.length && j < 150; j++) {
				queryString += "'" + termnum[i * 150 + j] + "',";
			}
			
			
			// 去除字符串后多余的“，”
			queryString = queryString.substring(0, queryString.length() - 1) + ")";
			
			
			// 没有开始日期，就不加入sql语句
			if (!date1.equals("")) {
				queryString += " and outcarddate>='" + date1 + "'";
			}
			// 没有截至日期，就不加入sql语句
			if (!date2.equals("")) {
				queryString += " and outcarddate<='" + date2 + "'";
			}
			// 没有开始时间，就不加入sql语句
			if (!time1.equals("")) {
				queryString += " and outcardtime>='" + time1 + "'";
			}
			// 没有截止时间，就不加入sql语句
			if (!time2.equals("")) {
				queryString += " and outcardtime<='" + time2 + "'";
			}
			
			
			// 没有；身份证号，就不加入sql语句
			if (!id.equals("")) {
				queryString += " and idcardno='" + id + "'";
			}
			// 没有发卡卡号，就不加入sql语句
			if (!cardno.equals("")) {
				queryString += " and outcardno like '" + cardno + "%'";
			}
			
			
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
	
	
	
	/**
	 * 查询出符合条件的结果的个数
	 * 
	 * @param termnum
	 *            设备号
	 * @param date1
	 *            开始日期
	 * @param date2
	 *            截止日期
	 * @param time1
	 *            开始时间
	 * @param time2
	 *            截止时间
	 * @param id
	 *            身份证号
	 * @param cardno
	 *            发卡卡号
	 * @return 符合条件的结果的个数
	 * @throws HibernateException
	 */
	public static List getCardOutList(String[] termnum, String date1, String date2, String time1, String time2, String id, String cardno, int firstResult, int maxResults) throws HibernateException {
		//
		// String queryString = "";// 查询语句
		// // make query language as usual
		// queryString = " from CardOut where devno in ('" + termnum[0] + "'";
		// for (int i = 1; i < termnum.length; i++) {
		// queryString += ", '" + termnum[i] + "'";
		// }
		// queryString += ")";
		// // 没有开始日期，就不加入sql语句
		// if (!date1.equals("")) {
		// queryString += " and outcarddate>='" + date1 + "'";
		// }
		// // 没有截至日期，就不加入sql语句
		// if (!date2.equals("")) {
		// queryString += " and outcarddate<='" + date2 + "'";
		// }
		// // 没有开始时间，就不加入sql语句
		// if (!time1.equals("")) {
		// queryString += " and outcardtime>='" + time1 + "'";
		// }
		// // 没有截止时间，就不加入sql语句
		// if (!time2.equals("")) {
		// queryString += " and outcardtime<='" + time2 + "'";
		// }
		//
		// // 没有；身份证号，就不加入sql语句
		// if (!id.equals("")) {
		// queryString += " and idcardno='" + id + "'";
		// }
		// // 没有发卡卡号，就不加入sql语句
		// if (!cardno.equals("")) {
		// queryString += " and outcardno like '" + cardno + "%'";
		// }
		//
		// PubUtil myPubUtil = new PubUtil();
		//
		// List result = null;
		// try {
		// String servtype = myPubUtil.dealNull(
		// myPubUtil.ReadConfig("DBagent", "ServType", "0",
		// "PowerView.ini")).trim();
		// if (servtype.equals(""))
		// servtype = "0";
		//
		// System.out.println(queryString);
		//
		// Session session = HibernateUtil.currentSession();
		// Transaction tx = session.beginTransaction();
		// Query query = session.createQuery(queryString);
		//
		// // 通过配置文件判断所用分页方式 xucc 20090624
		// // wukp20110914 原始为：if(servtype.equals("0")){
		// if (servtype.equals("0")) {
		// result =
		// query.setFirstResult(firstResult).setMaxResults(maxResults).list();
		// // wukp20110914 数据库管理系统中的 游标 使用
		// } else if (servtype.equals("1")) {
		// result = new ArrayList();
		// result = query.list();
		//
		// ScrollableResults sr = query.scroll();
		// for (int i = firstResult; i < firstResult + maxResults; i++) {
		// System.out.println(" " + sr.getRowNumber());
		// if (sr.setRowNumber(i)) {
		// result.add(sr.get(0));
		// }
		// }
		// }
		// tx.commit();
		// } catch (Exception e) {
		// HibernateUtil.closeFactory();
		// return null;
		// } finally {
		// try {
		// HibernateUtil.closeSession();
		// } catch (Exception ex) {
		// }
		// }
		// return result;
		// }
		
		int count = 0;
		String queryString = "";
		count = termnum.length / 150 + 1;
		List result = new ArrayList();
		List result1 = null;
		PubUtil myPubUtil = new PubUtil();
		
		String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent", "ServType", "0", "PowerView.ini")).trim();
		if (servtype.equals(""))
			servtype = "0";
		
		
		// 分段查询，减少 设备数量过多导致报错 的几率
		for (int i = 0; i < count; i++) {
			
			queryString = "from CardOut where devno in (";
			
			for (int j = 0; (i * 150) + j < termnum.length && j < 150; j++) {
				queryString += "'" + termnum[i * 150 + j] + "',";
			}
			
			
			// 去除字符串后多余的“，”
			queryString = queryString.substring(0, queryString.length() - 1) + ")";
			
			
			// 没有开始日期，就不加入sql语句
			if (!date1.equals("")) {
				queryString += " and outcarddate>='" + date1 + "'";
			}
			// 没有截至日期，就不加入sql语句
			if (!date2.equals("")) {
				queryString += " and outcarddate<='" + date2 + "'";
			}
			// 没有开始时间，就不加入sql语句
			if (!time1.equals("")) {
				queryString += " and outcardtime>='" + time1 + "'";
			}
			// 没有截止时间，就不加入sql语句
			if (!time2.equals("")) {
				queryString += " and outcardtime<='" + time2 + "'";
			}
			
			
			// 没有；身份证号，就不加入sql语句
			if (!id.equals("")) {
				queryString += " and idcardno='" + id + "'";
			}
			// 没有发卡卡号，就不加入sql语句
			if (!cardno.equals("")) {
				queryString += " and outcardno like '" + cardno + "%'";
			}
			
			queryString=queryString+" order by outcarddate,outcardtime ";
			
			try {
				Session session = HibernateUtil.currentSession();
				Transaction tx = session.beginTransaction();
				Query query = session.createQuery(queryString);
				
				
				// 通过配置文件判断所用分页方式 xucc 20090624
				// wukp20110914 原始为：if(servtype.equals("0")){
				if (servtype.equals("0")) {
					result1 = query.setFirstResult(firstResult).setMaxResults(maxResults).list();
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
				HibernateUtil.closeFactory();
				return null;
			} finally {
				try {
					HibernateUtil.closeSession();
				} catch (Exception ex) {
				}
			}
		}
		return result;
	}
}