package com.lcjr.pvxp.orm.util;

import org.apache.log4j.Logger;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> ʹ��HibernateUtil������hibernate��Session</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p>
 * @author ����
 * @version 1.0 2004/12/08
 */

/**
 * <p>
 * <b>Description:</b>����closeFactory����
 * </p>
 * 
 * @author ������
 * @version 1.0 2012/07/11
 */
public class HibernateUtil {

	static Logger log = Logger.getLogger("web.com.lcjr.pvxp.orm.HibernateUtil.java");

	// ʹ��ThreadLocal��Ϊ��ǰ�����̱߳���HibernateUtil����
	private static final ThreadLocal sessionContext = new ThreadLocal();

	private Session session;

	private int level;

	private static SessionFactory factory;

	static {// ��̬����飬����ص�ͬʱ����
		try {
			// ����SessionFactory��ͨ��ֻ�Ǳ���ʼ��һ��,��������̷�񡣷���д�����õ��ԡ�
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			log.error("Initial SessionFactory creation failed.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public int getLevel() {
		return this.level;
	}

	/**
	 * ��ThreadLocal��ȡHibernateUtil����ȡ�ûỰsession
	 * ����Ѵ���HibernateUtil������ʹ������session��Ϊ��session���
	 * 
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session currentSession() throws HibernateException {
		log.debug("currentSession()");
		HibernateUtil tlSession = (HibernateUtil) sessionContext.get();
		if (tlSession == null) {
			tlSession = new HibernateUtil();
			try {
				tlSession.session = factory.openSession();
			} catch (Exception e) {
				log.error("currentSession()", e);
			}
			tlSession.level = 0;
			sessionContext.set(tlSession);
		}
		// ��session��ƣ�sessionҪ���е������һ
		tlSession.level++;
		// System.out.println("level="+tlSession.level);
		// logger.info("�����session�Ự������һ��Ŀǰ����Ŀ��" + tlSession.level);
		return tlSession.session;
	}

	/**
	 * �ر��Ѿ�������������session ���ThreadLocalΪ��ǰ�����̱߳����HibernateUtil����
	 * 
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		log.debug("closeSession()");
		HibernateUtil tlSession = (HibernateUtil) sessionContext.get();
		if (tlSession == null) {
			return;
		}

		tlSession.level--;
		// System.out.println("Done one session , current session count =[" +
		// tlSession.level+"]");
		// ���һ�λỰ��ɺ�ر�session
		if (tlSession.level <= 0) {
			if (tlSession.session != null && tlSession.session.isOpen()) {
				tlSession.session.close();
				// System.out.println("I'm sure closed session.");
			}
			sessionContext.set(null);
		}
	}

	/**
	 * �ص�֮ǰ��factoryȻ�����µ�factory��
	 * 
	 * @throws HibernateException
	 */
	public static void closeFactory() throws HibernateException {
		log.debug("closeFactory()");
		factory.close();
		try {
			// ���´���SessionFactory
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			log.error("closeFactory()", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
}
