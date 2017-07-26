package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.*;
import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.orm.Autosta;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> �ͱ�zzt_autosta��ص�ҵ���߼�</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p>
 * @author ��̫��
 * @version 1.0 2005/4/7
 */

/**
 * <p><b>Description:</b>�������쳣ʱ����closeFactory����</p>
 * @author xucc
 * @version 1.0 2007/04/09
 */

public class AutostaBean{
	public AutostaBean()throws HibernateException{

	}

	/**
	 * ���һ��Autosta�������ݿ�
	 *
	 * @param st �Զ�ͳ��������Ϣ����
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean addAutosta(Autosta st)throws HibernateException{
		boolean result = true;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			session.save(st);
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
		}return result;
	}
	/**
	 * ����Autosta�������ݿ�
	 *
	 * @param st �Զ�ͳ��������Ϣ����
	 * @param id �Զ�ͳ��������
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean updateAutosta(Autosta st)throws HibernateException{
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
	
	public static boolean updateAutosta(Autosta st , String id)throws HibernateException{
		return updateAutosta(st);
	}

	/**
	 * ɾ������ID(�Զ�ͳ��������id)���Զ�ͳ��������Ϣ
	 *
	 * @param id �Զ�ͳ��������
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean deleteAutosta(String id)throws HibernateException{
	 	boolean result = false;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();

			session.delete("select c from Autosta as c where c.id='"+id+"'");
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

	/**
	 * ɾ������ID(������ű�ʾbankid)���Զ�ͳ��������Ϣ
	 *
	 * @param bankid ������ű�ʾ
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean deleteSomeAutosta(String bankid)throws HibernateException{
	 	boolean result = false;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();

			session.delete("select c from Autosta as c where c.bankid like '"+bankid+"%'");
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

	/**
	 * ��ѯϵͳ�����е��Զ�ͳ��������Ϣ<br>
	 * ���ص��ǰ�����Autosta�����List
	 *
	 * @return List
	 * @throws HibernateException
	 */
	public List getAllAutostaList()throws HibernateException{
		String queryString = "from Autosta as Autostas order by Autostas.id desc";
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

	/**
	 * ��ѯϵͳ�����е��Զ�ͳ��������Ϣ<br>
	 * ���ص��ǰ�����Autosta�����List
	 * @return List
	 * @throws HibernateException
	 */
	public List getListByBankid( String bankid, int firstRow, int maxResults )throws HibernateException{
		String queryString = "from Autosta where bankid like '%"+bankid+"%'";
		PubUtil myPubUtil = new PubUtil();
		if( ( myPubUtil.dealNull(Constants.DB_OP_TYPE) ).equals("1") )
			queryString += " order by id desc";
		List it = null;
		try{
			String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent","ServType","0","PowerView.ini")).trim();
			if( servtype.equals("") ) servtype = "0";
		
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			//ͨ�������ļ��ж����÷�ҳ��ʽ xucc 20090624
			if(servtype.equals("0")){
				it= session.createQuery(queryString)
	      					 .setFirstResult(firstRow)
	      					 .setMaxResults(maxResults)
	      					 .list();
			
			}else if(servtype.equals("1")){
				it = new ArrayList();
				Query query = session.createQuery(queryString);
				ScrollableResults rs = query.scroll();
				for (int i = firstRow; i < firstRow+maxResults; i++) {
					if(rs.setRowNumber(i)){
						it.add(rs.get(0));
					}
				}
			}
		
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

	/**
	 * ��ѯϵͳ�����е��Զ�ͳ��������Ϣ<br>
	 * ���ص��ǰ�����Autosta�����List
	 *
	 * @return List
	 * @throws HibernateException
	 */
	public List getListByBankid_Opentag( String bankid, String opentag, int firstRow, int maxResults )throws HibernateException{
		String queryString = "from Autosta ";
		queryString += "where bankid like '%"+bankid+"%' and opentag='"+opentag+"' ";
		PubUtil myPubUtil = new PubUtil();
		if( ( myPubUtil.dealNull(Constants.DB_OP_TYPE) ).equals("1") )
			queryString += "order by id desc";
		List it = null;

		try{
			String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent","ServType","0","PowerView.ini")).trim();
			if( servtype.equals("") ) servtype = "0";
		
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			
			//ͨ�������ļ��ж����÷�ҳ��ʽ xucc 20090624
			if(servtype.equals("0")){
				it= session.createQuery(queryString)
	      					 .setFirstResult(firstRow)
	      					 .setMaxResults(maxResults)
	      					 .list();
				
			}else if(servtype.equals("1")){
				it = new ArrayList();
				Query query = session.createQuery(queryString);
				ScrollableResults rs = query.scroll();
				for (int i = firstRow; i < firstRow+maxResults; i++) {
					if(rs.setRowNumber(i)){
						it.add(rs.get(0));
					}
				}
			}

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

	/**
	 * ���Զ�ͳ�������Ż��Autosta�־ö���(��Ҫֱ��ʹ�ã�����AutostaModel�еķ���)
	 *
	 * @param id �Զ�ͳ��������
	 * @return Autosta
	 * @throws HibernateException
	 */
	public Autosta getAutosta(String id)throws HibernateException{
		String queryString = "select c from Autosta as c where c.id='"+id+"'" ;
		Iterator it = null;
		Autosta temp = null;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			//query.setString("id",id);

			it = query.iterate();
			//tx.commit();
			if( it.hasNext()){
				temp=(Autosta)it.next();
			}
			
			tx.commit();//ȡ��nextֵ��commit��informixȡֵǰcommit���� xucc 20090624
			
		}catch(Exception e){
			HibernateUtil.closeFactory();
			return null;
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}
		return temp;
	}

	/**
	 *����ָ���������Զ�ͳ��������б������
	 *
	 *@param String bankid ����ID
	 *@return ָ��������ͳ���Զ�������б�����
	 */
	public int getAllCount( String bankid ) throws HibernateException {
		String qs = "select count(*) from Autosta where bankid like '%" + bankid + "%'";

		List lt = null;
		int count = 0;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();

			lt= session.createQuery(qs).list();
			count = ((Integer)lt.get(0)).intValue();
			tx.commit();//�ȴ�������commit xucc 20090624
		} catch(Exception e) {
			HibernateUtil.closeFactory();
			return 0;
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}
		return count;
	}

	/**
	 *����ָ��������ָ�������ͳ���������ָ��״̬���б�����
	 *
	 *@param String bankid ����ID
	 *@param String opentag ����״̬
	 *@return ָ��������ͳ��������б�����
	 */
	public int getCount( String bankid, String opentag ) throws HibernateException {
		String qs = "select count(*) from Autosta where opentag='" + opentag + "'";
		qs += " and bankid like '%" + bankid + "%'";

		List lt = null;
		int count = 0;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			lt = session.createQuery(qs).list();
			count = ((Integer)lt.get(0)).intValue();
			tx.commit();//�ȴ�������commit xucc 20090624
		} catch(Exception e) {
			HibernateUtil.closeFactory();
			return 0;
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}
		return count;
	}

}
