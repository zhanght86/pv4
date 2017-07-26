package com.lcjr.pvxp.orm.util;

import org.apache.log4j.Logger;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> 使用HibernateUtil来管理hibernate的Session</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p>
 * @author 杨旭
 * @version 1.0 2004/12/08
 */

/**
 * <p>
 * <b>Description:</b>增加closeFactory方法
 * </p>
 * 
 * @author 武坤鹏
 * @version 1.0 2012/07/11
 */
public class HibernateUtil {

	static Logger log = Logger.getLogger("web.com.lcjr.pvxp.orm.HibernateUtil.java");

	// 使用ThreadLocal来为当前工作线程保存HibernateUtil对象
	private static final ThreadLocal sessionContext = new ThreadLocal();

	private Session session;

	private int level;

	private static SessionFactory factory;

	static {// 静态代码块，类加载的同时运行
		try {
			// 创建SessionFactory，通常只是被初始化一次,方法链编程风格。分行写，利用调试。
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
	 * 从ThreadLocal获取HibernateUtil对象并取得会话session
	 * 如果已存在HibernateUtil对象，则使用它的session，为单session设计
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
		// 单session设计，session要进行的任务加一
		tlSession.level++;
		// System.out.println("level="+tlSession.level);
		// logger.info("所需的session会话次数加一，目前总数目：" + tlSession.level);
		return tlSession.session;
	}

	/**
	 * 关闭已经完成所有任务的session 清除ThreadLocal为当前工作线程保存的HibernateUtil对象
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
		// 最后一次会话完成后关闭session
		if (tlSession.level <= 0) {
			if (tlSession.session != null && tlSession.session.isOpen()) {
				tlSession.session.close();
				// System.out.println("I'm sure closed session.");
			}
			sessionContext.set(null);
		}
	}

	/**
	 * 关掉之前的factory然后开启新的factory。
	 * 
	 * @throws HibernateException
	 */
	public static void closeFactory() throws HibernateException {
		log.debug("closeFactory()");
		factory.close();
		try {
			// 重新创建SessionFactory
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			log.error("closeFactory()", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
}
