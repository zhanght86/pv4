package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.EatCardLog;
import com.lcjr.pvxp.util.*;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

//import org.apache.log4j.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> 和表zzt_eatcardlog相关的业务逻辑</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p>
 * @author 吴学坤
 * @version 1.0 2005/03/18
 */

/**
 * <p>
 * <b>Description:</b>当捕获到异常时调用closeFactory方法
 * </p>
 * 
 * @author xucc
 * @version 1.0 2007/04/09
 */
public class EatCardLogBean {
	
	
	// static Logger log = Logger.getLogger("wuxk");
	public EatCardLogBean() throws HibernateException {
	}
	
	
	public static List getEatCardLog(String[] devno, String edate1, String edate2, String accno, int firstResult, int maxResults, String flag) throws HibernateException {
		String queryString = "";
		if (flag.equals("A"))
			queryString = "from EatCardLog where flag like '%'";
		else
			queryString = "from EatCardLog where flag='" + flag + "'";
		
		queryString += " and devno in ('" + devno[0] + "'";
		for (int i = 1; i < devno.length; i++) {
			queryString += ", '" + devno[i] + "'";
		}
		queryString += ")";
		
		if (!edate1.equals("")) {
			queryString += " and edate>='" + edate1 + "'";
		}
		
		if (!edate2.equals("")) {
			queryString += " and edate<='" + edate2 + "'";
		}
		
		if (!accno.equals("")) {
			queryString += " and accno like '%" + accno + "%'";
		}
		PubUtil myPubUtil = new PubUtil();
		if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
			queryString += "order by edate desc, etime desc";
		
		List result = null;
		try {
			String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent", "ServType", "0", "PowerView.ini")).trim();
			if (servtype.equals(""))
				servtype = "0";
			
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			
			
			// 通过配置文件判断所用分页方式 xucc 20090624
			if (servtype.equals("0")) {
				result = query.setFirstResult(firstResult).setMaxResults(maxResults).list();
			} else if (servtype.equals("1")) {
				
				result = new ArrayList();
				ScrollableResults sr = query.scroll();
				for (int i = firstResult; i < firstResult + maxResults; i++) {
					if (sr.setRowNumber(i)) {
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
		
		return result;
	}
	
	
	public static int getEatCardLogCount(String[] devno, String edate1, String edate2, String accno, String flag) throws HibernateException {
		
		String queryString = "";
		if (flag.equals("A"))
			queryString = "select count(*) from EatCardLog where flag like '%'";
		else
			queryString = "select count(*) from EatCardLog where flag='" + flag + "'";
		
		queryString += " and devno in ('" + devno[0] + "'";
		for (int i = 1; i < devno.length; i++) {
			queryString += ", '" + devno[i] + "'";
		}
		queryString += ")";
		
		if (!edate1.equals("")) {
			queryString += " and edate>='" + edate1 + "'";
		}
		
		if (!edate2.equals("")) {
			queryString += " and edate<='" + edate2 + "'";
		}
		
		if (!accno.equals("")) {
			queryString += " and accno like '%" + accno + "%'";
		}
		
		List result = null;
		int count = 0;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			result = query.list();
			count = ((Integer) result.get(0)).intValue();
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
		
		return count;// 先处理结果再commit xucc 20090624
	}
	
	
	public static boolean deleteEcLog(String date) throws HibernateException {
		PubUtil pubUtil = new PubUtil();
		String day = pubUtil.getOtherDate(-7);
		if (date.compareTo(day) > 0) {
			date = day;
		}
		
		String queryString = "from EatCardLog where edate<'" + date + "'";
		
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			
			session.delete(queryString);
			
			tx.commit();
			return true;
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			return false;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
			}
		}
		
	}
}
