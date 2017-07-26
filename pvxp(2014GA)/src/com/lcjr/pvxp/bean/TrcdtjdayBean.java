package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Trcdtjday;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> �ͱ�zzt_trcdtjday��ص�ҵ���߼�</p>
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
public class TrcdtjdayBean
{
	public TrcdtjdayBean()throws HibernateException
	{

	}

	/**
	 * ���ݲ������HQLͳ���ձ���<br><br>
	 *
	 * @param HQLstr String��HQL��ѯ���
	 * @return List ������Trcdtjday�����List
	 * @throws HibernateException
	 */
	public List getQueryList( String HQLstr )throws HibernateException
	{
		
		String queryString = HQLstr.trim();

		List it = null;
//		int p1=queryString.indexOf("(");
//		int p2=queryString.indexOf(")");
//		String firstpart=queryString.substring(0, p1);
//		String middlepart=queryString.substring(p1+1, p2)+"or";
//		String endpart=queryString.substring(p2+1, queryString.length());
		
//		( devno='13180107' or devno='13180108' or
//		devno='13180109' or devno='13180110' or devno='13180111' or devno='13180112' or
//		devno='13180113' or devno='13180114' or devno='13180115' or devno='13180116' or
//		devno='13180117' or devno='13210002' or devno='13210003' or devno='13210007' )
		// System.out.println("��һ������"+firstpart);
//		System.out.println("�м䲿����"+middlepart);
//		System.out.println("���һ������"+endpart);
//		
//		int pernum=middlepart.length()/20+1;
//		
//		for(int i=0;i<pernum;i++){
//			
//			
//		}
		

		try{
			Session session = HibernateUtil.currentSession();
			
			Transaction tx= session.beginTransaction();

			Query query = session.createQuery(queryString);

			it= query.list();

			tx.commit();

		}catch(Exception e){
//			System.out.println("��ɵ�sql���Ϊ��"+HQLstr+"  �ǵ� "+j+" ����ѯ���");
//			System.out.println("���ִ���...��trcdtjdaybean.java ��");
			HibernateUtil.closeFactory();
			e.printStackTrace();
			return null;
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}

		return it;
	}
	
	
	/**
	 * ��������<br><br>
	 *
	 * @param HQLstr String��HQL��ѯ���
	 * @return List ������Trcdtjday�����List
	 * @throws HibernateException
	 */
	public void addTrcdtjday( Trcdtjday result )throws HibernateException
	{

		try{
			Session session = HibernateUtil.currentSession();
			
			Transaction tx= session.beginTransaction();
			
			for(int i=0;i<10;i++){
			session.save(result);

			}
//			session.save(result);

//			it= query.list();

			tx.commit();

		}catch(Exception e){
			System.out.println("���ִ���"+e);
			HibernateUtil.closeFactory();
			e.printStackTrace();
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}
	}

}
