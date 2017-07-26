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
 * <p><b>Description:</b> �ͱ�zzt_stamission��ص�ҵ���߼�</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p>
 * @author ��ѧ��
 * @version 1.0 2005/03/28
 */

/**
 * <p>
 * <b>Description:</b>�������쳣ʱ����closeFactory����
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
	 * ����ָ��������ָ�������ͳ��������б�
	 * 
	 * @author ��ѧ��
	 * @param Vector
	 *            bankids ����ID
	 * @param int firstRow ���صĵ�һ����¼
	 * @param int maxResults ��෵�صļ�¼��
	 * @return ָ��������ͳ��������б�
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

			// ͨ�������ļ��ж����÷�ҳ��ʽ xucc 20090624
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
	 * ����ָ��������ָ�������ͳ���������ָ��״̬���б�
	 * 
	 * @author ��ѧ��
	 * @param Vector
	 *            bankids ����ID
	 * @param String
	 *            currentflag ����״̬
	 * @param int firstRow ���صĵ�һ����¼
	 * @param int maxResults ��෵�صļ�¼��
	 * @return ָ��������ͳ��������б�
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

			// ͨ�������ļ��ж����÷�ҳ��ʽ xucc 20090624
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
	 * ����ָ��������ָ�������ͳ��������б������
	 * 
	 * @author ��ѧ��
	 * @param Vector
	 *            bankids ����ID
	 * @return ָ��������ͳ��������б�����
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
			tx.commit();// ȡ��ֵ��commit xucc 20090624
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
	 * ����ָ��������ָ�������ͳ���������ָ��״̬���б�����
	 * 
	 * @author ��ѧ��
	 * @param Vector
	 *            bankids ����ID
	 * @param String
	 *            currentflag ����״̬
	 * @return ָ��������ͳ��������б�����
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
			tx.commit();// ȡ��ֵ��commit xucc 20090624
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
	 * ɾ��ָ���ı���
	 * 
	 * @author ��ѧ��
	 * @param String
	 *            fileName Ҫɾ���ı����ļ���
	 * @return ���ɾ���ɹ�����true
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
	 * ���һ��StaMission�������ݿ�
	 * 
	 * @param st
	 *            ϵͳ�������
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
	 * ����ͳ�ƿ�ʼʱ�䡢������ࡢ�����������<br>
	 * ȡ��ϵͳ�����
	 * </p>
	 * 
	 * @param timeid
	 *            ͳ�ƿ�ʼʱ��
	 * @param statesort
	 *            �������
	 * @param createtype
	 *            �����������
	 * @param ownerid
	 *            ������
	 * 
	 * @return StaMission ����StaMission����
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

			tx.commit();// ȡ��nextֵ��commit xucc 20090624

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
	 * ����ͳ�ƿ�ʼʱ�䡢������ࡢ�����������<br>
	 * ɾ��ϵͳ�����
	 * </p>
	 * 
	 * @param timeid
	 *            ͳ�ƿ�ʼʱ��
	 * @param statesort
	 *            �������
	 * @param createtype
	 *            �����������
	 * @param ownerid
	 *            ������
	 * 
	 * @return StaMission ����StaMission����
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
	 * ����StaMission�������ݿ�
	 * 
	 * @param st
	 *            StaMission����
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
	 * ��ȡ������StaMission�����currentflag
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
			tx.commit();// �ȴ�������commit xucc 20090624

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
