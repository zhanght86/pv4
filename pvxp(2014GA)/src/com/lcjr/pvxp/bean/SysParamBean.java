package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.*;

import net.sf.hibernate.*;
import java.util.*;


/**
 * <p><b>Title:</b>PowerViewXP</p>
 * <p><b>Description:</b> 和表zzt_sysparam相关的业务逻辑</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p>
 * <p><b>Description:</b>当捕获到异常时调用closeFactory方法</p>
 * @author xucc
 * @version 1.0 2007/04/09
 * 
 * @author 武坤鹏
 * @version 1.0 2012/03/07
 */
public class SysParamBean {
	
	/**
	 * 构造函数
	 * @throws HibernateException
	 */
	public SysParamBean()throws HibernateException {}
	
	/**
	 * 查询系统信息<br>
	 *
	 * @return 系统信息对象SysParam
	 * @throws HibernateException
	 */
	public static SysParam getSysParam() throws HibernateException {
		
		String queryString = "from SysParam";
		List lt = null;
		SysParam sysParam;

		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			lt= query.list();
			tx.commit();
			if (lt.size() > 0) {		//数据库中只有一条，用以系统的配置
				sysParam = (SysParam)lt.get(0);
			} else {
				sysParam = null;
			}
			HibernateUtil.closeSession();
		} catch(Exception e) {
			HibernateUtil.closeFactory();
			return null;
		}
		return sysParam;
	}
  
  
	/**
	 * 更新SysParam对象到数据库
	 * 
	 * @param st SysParam对象
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean updateSysParam(SysParam st)throws HibernateException{
		boolean result = false;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			session.update(st);
			tx.commit();
			result=tx.wasCommitted();
		}catch(Exception e){
			HibernateUtil.closeFactory();
			result = false;
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
				
			}
		}
		return result;
	}
}
