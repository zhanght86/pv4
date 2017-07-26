package com.lcjr.pvxp.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.ScrollableResults;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.lcjr.pvxp.orm.StaMission;
import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> 和表zzt_stamission相关的业务逻辑</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p>
 * @author 吴学坤
 * @version 1.0 2005/03/28
 */

/**
 * <p>
 * <b>Description:</b>当捕获到异常时调用closeFactory方法
 * </p>
 * 
 * @author xucc
 * @version 1.0 2007/04/09
 */

public class StaMissionBean {

	Logger log = Logger.getLogger(StaMissionBean.class.getName());

	public StaMissionBean() throws HibernateException {
	}

	/**
	 * 返回指定机构中指定分类的统计任务表列表
	 * 
	 * @author 吴学坤
	 * @param Vector
	 *            bankids 机构ID
	 * @param int firstRow 返回的第一条记录
	 * @param int maxResults 最多返回的记录数
	 * @return 指定机构的统计任务表列表
	 */
	public static List getAllStaMission(Vector bankids, String statesort, int firstRow, int maxResults) throws HibernateException {

		String qs = "from StaMission where statesort='" + statesort + "'";
		if (bankids != null) {
			Iterator iter = bankids.iterator();
			qs += " and bankid in ('" + (String) iter.next() + "'";
			while (iter.hasNext()) {
				qs += ", '" + (String) iter.next() + "'";
			}
			qs += ")";
		}
		PubUtil myPubUtil = new PubUtil();
		if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
			qs += "order by timeid desc";

		// log.debug("qs=" + qs);

		List lt = null;
		try {
			String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent", "ServType", "0", "PowerView.ini")).trim();
			if (servtype.equals(""))
				servtype = "0";

			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();

			// 通过配置文件判断所用分页方式 xucc 20090624
			if (servtype.equals("0")) {
				lt = session.createQuery(qs).setFirstResult(firstRow).setMaxResults(maxResults).list();

			} else if (servtype.equals("1")) {

				lt = new ArrayList();
				Query query = session.createQuery(qs);
				ScrollableResults rs = query.scroll();
				for (int i = firstRow; i < firstRow + maxResults; i++) {
					if (rs.setRowNumber(i)) {
						lt.add(rs.get(0));
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
		return lt;
	}

	/**
	 * 返回指定机构中指定分类的统计任务表中指定状态的列表
	 * 
	 * @author 吴学坤
	 * @param Vector
	 *            bankids 机构ID
	 * @param String
	 *            currentflag 任务状态
	 * @param int firstRow 返回的第一条记录
	 * @param int maxResults 最多返回的记录数
	 * @return 指定机构的统计任务表列表
	 */
	public static List getStaMission(Vector bankids, String currentflag, String statesort, int firstRow, int maxResults) throws HibernateException {

		String qs = "from StaMission where currentflag='" + currentflag + "'";
		qs += " and statesort='" + statesort + "'";
		if (bankids != null) {
			Iterator iter = bankids.iterator();
			qs += " and bankid in ('" + (String) iter.next() + "'";
			while (iter.hasNext()) {
				qs += ", '" + (String) iter.next() + "'";
			}
			qs += ")";
		}
		PubUtil myPubUtil = new PubUtil();
		if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
			qs += "order by timeid desc";

		// log.debug("qs=" + qs);

		List lt = null;
		try {
			String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent", "ServType", "0", "PowerView.ini")).trim();
			if (servtype.equals(""))
				servtype = "0";

			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();

			// 通过配置文件判断所用分页方式 xucc 20090624
			if (servtype.equals("0")) {
				lt = session.createQuery(qs).setFirstResult(firstRow).setMaxResults(maxResults).list();

			} else if (servtype.equals("1")) {

				lt = new ArrayList();
				Query query = session.createQuery(qs);
				ScrollableResults rs = query.scroll();
				for (int i = firstRow; i < firstRow + maxResults; i++) {
					if (rs.setRowNumber(i)) {
						lt.add(rs.get(0));
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
		return lt;
	}

	/**
	 * 返回指定机构中指定分类的统计任务表列表的总数
	 * 
	 * @author 吴学坤
	 * @param Vector
	 *            bankids 机构ID
	 * @return 指定机构的统计任务表列表总数
	 */
	public static int getAllStaMissionCount(Vector bankids, String statesort) throws HibernateException {
		String qs = "select count(*) from StaMission where statesort='" + statesort + "'";
		if (bankids != null) {
			Iterator iter = bankids.iterator();
			qs += " and bankid in ('" + (String) iter.next() + "'";
			while (iter.hasNext()) {
				qs += ", '" + (String) iter.next() + "'";
			}
			qs += ")";
		}

		// log.debug("qs=" + qs);

		List lt = null;
		int count = 0;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();

			lt = session.createQuery(qs).list();
			count = ((Integer) lt.get(0)).intValue();
			tx.commit();// 取到值再commit xucc 20090624
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

	/**
	 * 返回指定机构中指定分类的统计任务表中指定状态的列表总数
	 * 
	 * @author 吴学坤
	 * @param Vector
	 *            bankids 机构ID
	 * @param String
	 *            currentflag 任务状态
	 * @return 指定机构的统计任务表列表总数
	 */
	public static int getStaMissionCount(Vector bankids, String currentflag, String statesort) throws HibernateException {
		String qs = "select count(*) from StaMission where currentflag='" + currentflag + "'";
		qs += " and statesort='" + statesort + "'";
		if (bankids != null) {
			Iterator iter = bankids.iterator();
			qs += " and bankid in ('" + (String) iter.next() + "'";
			while (iter.hasNext()) {
				qs += ", '" + (String) iter.next() + "'";
			}
			qs += ")";
		}

		// log.debug("qs=" + qs);

		List lt = null;
		int count = 0;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();

			lt = session.createQuery(qs).list();
			count = ((Integer) lt.get(0)).intValue();
			tx.commit();// 取到值再commit xucc 20090624
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

	/**
	 * 删除指定的报表
	 * 
	 * @author 吴学坤
	 * @param String
	 *            fileName 要删除的报表文件名
	 * @return 如果删除成功返回true
	 */
	public static boolean delStaMission(String fileName) throws HibernateException {

		String createtype = fileName.substring(0, 1);
		String statesort = fileName.substring(2, 4);
		String ownerid = fileName.substring(5, 8);
		String timeid;
		if (ownerid.equals("SYS")) {
			timeid = fileName.substring(9, 23);
		} else {
			ownerid = fileName.substring(5, 9);
			timeid = fileName.substring(10, 24);
		}

		String query = "from StaMission where createtype='" + createtype + "' and statesort='" + statesort + "' and ownerid='" + ownerid + "' and timeid='" + timeid + "'";

		int n;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();

			n = session.delete(query);

			tx.commit();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			return false;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
			}
		}

		if (n != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 添加一个StaMission对象到数据库
	 * 
	 * @param st
	 *            系统任务对象
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean addStaMission(StaMission st) throws HibernateException {
		boolean result = true;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			session.save(st);
			tx.commit();
			result = tx.wasCommitted();
		} catch (Exception e) {
			System.out.println("addStaMission=" + e);
			HibernateUtil.closeFactory();
			result = false;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {

			}
		}
		return result;
	}

	/**
	 * <p>
	 * 根据统计开始时间、报表分类、报表产生类型<br>
	 * 取出系统任务表
	 * </p>
	 * 
	 * @param timeid
	 *            统计开始时间
	 * @param statesort
	 *            报表分类
	 * @param createtype
	 *            报表产生类型
	 * @param ownerid
	 *            创建者
	 * 
	 * @return StaMission 返回StaMission对象
	 * 
	 * @throws HibernateException
	 */
	public StaMission getOneStaMission(String timeid, String statesort, String createtype, String ownerid) throws HibernateException {
		String queryString = "from StaMission where ";
		queryString += "timeid='" + timeid + "' and ";
		queryString += "statesort='" + statesort + "' and ";
		queryString += "createtype='" + createtype + "' and ";
		queryString += "ownerid='" + ownerid + "'";

		Iterator it = null;
		StaMission temp = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);

			it = query.iterate();
			// tx.commit();
			if (it.hasNext()) {
				temp = (StaMission) it.next();
			}

			tx.commit();// 取到next值再commit xucc 20090624

		} catch (Exception e) {
			HibernateUtil.closeFactory();
			System.out.println("SMBEAN \t Exception = [" + e + "]");
			return null;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {

			}
		}
		return temp;
	}

	/**
	 * <p>
	 * 根据统计开始时间、报表分类、报表产生类型<br>
	 * 删除系统任务表
	 * </p>
	 * 
	 * @param timeid
	 *            统计开始时间
	 * @param statesort
	 *            报表分类
	 * @param createtype
	 *            报表产生类型
	 * @param ownerid
	 *            创建者
	 * 
	 * @return StaMission 返回StaMission对象
	 * 
	 * @throws HibernateException
	 */
	public static int delStaMission(String timeid, String statesort, String createtype, String ownerid) throws HibernateException {
		String queryString = "from StaMission where ";
		queryString += "timeid='" + timeid + "' and ";
		queryString += "statesort='" + statesort + "' and ";
		queryString += "createtype='" + createtype + "' and ";
		queryString += "ownerid='" + ownerid + "'";

		int n = 0;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();

			n = session.delete(queryString);

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
		return n;
	}

	/**
	 * 更新StaMission对象到数据库
	 * 
	 * @param st
	 *            StaMission对象
	 * 
	 * @return boolean
	 * 
	 * @throws HibernateException
	 */
	public static boolean updateStaMission(StaMission st) throws HibernateException {
		boolean result = false;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			session.update(st);
			tx.commit();
			result = tx.wasCommitted();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			System.out.println("Exception = [" + e + "]");
			result = false;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {

			}
		}
		return result;
	}

	/**
	 * 获取并更新StaMission对象的currentflag
	 * 
	 * @param timeid
	 * @param statesort
	 * @param createtype
	 * @param ownerid
	 * @param flag
	 * 
	 * @return boolean
	 * 
	 * @throws HibernateException
	 */
	public boolean updateStaMission(String timeid, String statesort, String createtype, String ownerid, String flag) throws HibernateException {
		/*
		 * System.out.println("************************************************")
		 * ; System.out.println("**  timeid =     ["+timeid+"]");
		 * System.out.println("**  statesort =  ["+statesort+"]");
		 * System.out.println("**  createtype = ["+createtype+"]");
		 * System.out.println("**  ownerid =    ["+ownerid+"]");
		 * System.out.println("**  flag =       ["+flag+"]");
		 * System.out.println(
		 * "************************************************");
		 */
		String queryString = "from StaMission where ";
		queryString += "timeid='" + timeid + "' and ";
		queryString += "statesort='" + statesort + "' and ";
		queryString += "createtype='" + createtype + "' and ";
		queryString += "ownerid='" + ownerid + "'";
		boolean result = false;
		Iterator it = null;
		Session session = null;
		StaMission myStaMission = null;
		try {
			session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);

			it = query.iterate();
			// tx.commit();
			if (it.hasNext()) {
				myStaMission = (StaMission) it.next();
			}
			tx.commit();// 先处理结果再commit xucc 20090624

			PubUtil myPubUtil = new PubUtil();
			myStaMission.setCurrentflag(flag);
			String statename = myPubUtil.dealNull(myStaMission.getStatename()).trim();
			myStaMission.setStatename(statename);
			Transaction tx1 = session.beginTransaction();
			session.update(myStaMission);
			tx1.commit();
			result = tx.wasCommitted();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			result = false;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {

			}
		}
		return result;
	}
}
