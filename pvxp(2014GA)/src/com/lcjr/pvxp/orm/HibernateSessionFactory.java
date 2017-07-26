package com.lcjr.pvxp.orm;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

import org.apache.log4j.Logger;


/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see
 * {@link http://hibernate.org/42.html }.
 */
public class HibernateSessionFactory {

	static Logger log = Logger.getLogger("web.com.lcjr.pvxp.orm.HibernateSessionFactory.java");

	/**
	 * Location of hibernate.cfg.xml file. Location should be on the classpath
	 * as Hibernate uses #resourceAsStream style lookup for its configuration
	 * file. The default classpath location of the hibernate config file is in
	 * the default package. Use #setConfigFile() to update the location of the
	 * configuration file for the current session.
	 */
	private static String CONFIG_FILE_LOCATION = "/pvxp/web/WEB-INF/classes/hibernate.cfg.xml";

	private static final ThreadLocal threadLocal = new ThreadLocal();

	private static Configuration configuration = new Configuration();

	private static SessionFactory sessionFactory;

	private static String configFile = CONFIG_FILE_LOCATION;

	private HibernateSessionFactory() {
	}

	/**
	 * Returns the ThreadLocal Session instance. Lazy initialize the
	 * <code>SessionFactory</code> if needed.
	 * 
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session getSession() throws HibernateException {
		log.info(" getSession() 开始..");
		Session session = (Session) threadLocal.get();

		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession() : null;
			threadLocal.set(session);
		}

		return session;
	}

	/**
	 * Rebuild hibernate session factory
	 * 
	 */
	public static void rebuildSessionFactory() {
		log.info(" rebuildSessionFactory()开始..");
		try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			log.error("rebuildSessionFactory()", e);
			e.printStackTrace();
		}
	}

	/**
	 * Close the single hibernate session instance.
	 * 
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		log.info("closeSession()开始..");
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);

		if (session != null) {
			session.close();
		}

	}

	/**
	 * return session factory
	 * 
	 */
	public static SessionFactory getSessionFactory() {
		log.info("getSessionFactory()开始..");
		return sessionFactory;
	}

	/**
	 * return session factory
	 * 
	 * session factory will be rebuilded in the next call
	 */
	public static void setConfigFile(String configFile) {
		log.info("setConfigFile()开始..");
		HibernateSessionFactory.configFile = configFile;
		sessionFactory = null;
	}

	/**
	 * return hibernate configuration
	 * 
	 */
	public static Configuration getConfiguration() {
		log.info("getConfiguration()开始..");
		return configuration;
	}

}