package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.*;

import net.sf.hibernate.*;
import java.util.*;


/**
 * <p><b>Title:</b>PowerViewXP</p>
 * <p><b>Description:</b> �ͱ�zzt_sysparam��ص�ҵ���߼�</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p>
 * <p><b>Description:</b>�������쳣ʱ����closeFactory����</p>
 * @author xucc
 * @version 1.0 2007/04/09
 * 
 * @author ������
 * @version 1.0 2012/03/07
 */
public class SysParamBean {
	
	/**
	 * ���캯��
	 * @throws HibernateException
	 */
	public SysParamBean()throws HibernateException {}
	
	/**
	 * ��ѯϵͳ��Ϣ<br>
	 *
	 * @return ϵͳ��Ϣ����SysParam
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
			if (lt.size() > 0) {		//���ݿ���ֻ��һ��������ϵͳ������
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
	 * ����SysParam�������ݿ�
	 * 
	 * @param st SysParam����
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
