package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Trcdtjyear;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> �ͱ�zzt_trcdtjyear��ص�ҵ���߼�</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p>
 * @author ��̫��
 * @version 1.0 2005/3/30
 */

/**
 * <p><b>Description:</b>�������쳣ʱ����closeFactory����</p>
 * @author xucc
 * @version 1.0 2007/04/09
 */

public class TrcdtjyearBean
{
	public TrcdtjyearBean()throws HibernateException
	{

	}

	/**
	 * ���ݲ������HQLͳ���걨��<br><br>
	 *
	 * @param HQLstr String��HQL��ѯ���
	 * @return List ������Trcdtjyear�����List
	 * @throws HibernateException
	 */
	public List getQueryList( String HQLstr )throws HibernateException
	{
		String queryString = HQLstr.trim();
		List it = null;

		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			it= query.list();
			tx.commit();
		}catch(Exception e){
			HibernateUtil.closeFactory();
			return null;
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}

		return it;
	}

}
