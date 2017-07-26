package com.lcjr.pvxp.bean;

import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.lcjr.pvxp.orm.util.HibernateUtil;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> �ͱ�zzt_devstate��ص�ҵ���߼�</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p>
 * @author ����
 * @version 1.0 2005/02/25
 */

/**
 * <p>
 * <b>Description:</b>�������쳣ʱ����closeFactory����
 * </p>
 * 
 * @author xucc
 * @version 1.0 2007/04/09
 */
public class DevstateBean {

	Logger log = Logger.getLogger("web.com.lcjr.pvxp.bo.DevstateBean.java");

	public DevstateBean() throws HibernateException {

	}

	/**
	 * ��ѯzzt_devstate�������豸״̬<br>
	 * 
	 * @return Iterator ������Devstate�־ö����Iterator
	 * @throws HibernateException
	 */
	public List getAllDevstates() throws HibernateException {
		String queryString = "select devstates from Devstate as devstates";
		List it = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			it = query.list();
			tx.commit();
		} catch (Exception e) {
			log.warn("WARN", e);
			HibernateUtil.closeFactory();
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
	 * ��ѯzzt_devstate�������豸״̬<br>
	 * 
	 * @return Iterator ������Devstate�־ö����Iterator
	 * @throws HibernateException
	 */
	public List getSomeDevstates(String devstr) throws HibernateException {
		int inLen = 100;// in��ѯ��������������
		String[] temp = devstr.split(",");
		String queryString;
		if (temp.length < inLen) {
			queryString = "select devstates from Devstate as devstates where devno in ( " + devstr + " )";
		} else {
			int num = temp.length / inLen + (temp.length % inLen == 0 ? 0 : 1);
			String in = "";
			for (int i = 0; i < num; i++) {
				int jend = 0;// j����
				int jbegin = 0;// j��ʼ
				if (i + 1 == num) {
					jend = temp.length;
				} else {
					jend = (i + 1) * inLen;
				}
				jbegin = i * inLen;
				String tempStr = "";
				for (int j = jbegin; j < jend; j++) {
					tempStr = tempStr + temp[j] + ",";
				}
				in = in + "devno in (" + tempStr.substring(0, tempStr.length() - 1) + ") or ";
			}
			queryString = "select devstates from Devstate as devstates where " + in.substring(0, in.length() - 4);
		}
		List it = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			// Query query =
			// session.createQuery("select devstates from Devstate as devstates where devno in ( "+devstr+" )");
			Query query = session.createQuery(queryString);
			it = query.list();
			tx.commit();
		} catch (Exception e) {
			log.warn("WARN", e);
			e.printStackTrace();
			HibernateUtil.closeFactory();
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
	 * ��ѯzzt_devstate��ĳһ�豸״̬<br>
	 * 
	 * @return Iterator ������Devstate�־ö����Iterator
	 * @throws HibernateException
	 */
	public static List getSomeDevstate(String devno) throws HibernateException {
		String queryString = "from Devstate where devno='" + devno + "'";
		List it = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			it = query.list();
			tx.commit();
		} catch (Exception e) {
			System.out.println("e=" + e);
			return null;
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {

			}
		}
		return it;
	}
}
