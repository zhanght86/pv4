package com.lcjr.pvxp.bean.interfac;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring服务管理
 * 
 * @author ggui
 * 
 */
public class ApplicationContextProvider {

	private static ApplicationContext context = null;

	/**
	 * 
	 * @methodName 得到spring访问�?
	 * @return
	 */
	public static ApplicationContext getContext() {
		if (context == null)
			initContext(null);
		return context;
	}

	/**
	 * 
	 * @methodName 得到spring访问�?
	 * @param contextFilePath
	 *            配置文件名称
	 * @return
	 */
	public static ApplicationContext getContext(String contextFilePath) {
		if (context == null)
			initContext(contextFilePath);
		return context;
	}

	/**
	 * @methodName 得到实列bean
	 * @param beanName
	 *            Bean名称
	 * @return
	 */
	public static Object getBean(String beanName) {
		return getContext().getBean(beanName);
	}

	/**
	 * @methodName 得到实列bean
	 * @param beanName
	 *            Bean名称
	 * @param clsType
	 *            Bean �?
	 * @return
	 */
	public static Object getBean(String beanName, Class clsType) {
		return getContext().getBean(beanName, clsType);
	}

	/**
	 * 
	 * @methodName:初始化spring
	 * @param contextFilePath
	 */
	private static void initContext(String contextFilePath) {
		if (contextFilePath == null)
			contextFilePath = "beans.xml";
		context = new ClassPathXmlApplicationContext(contextFilePath);
	}					
}