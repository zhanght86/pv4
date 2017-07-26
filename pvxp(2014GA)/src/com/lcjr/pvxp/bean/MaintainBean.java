package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Maintain;
import com.lcjr.pvxp.util.*;
import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> 和表zzt_maintain相关的业务逻辑</p>
 * <p><b>Copyright:</b> Copyright (c) 2011</p>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p>
 * @author xucc
 * @version 1.0 2011/02/23
 */
public class MaintainBean
{
	public MaintainBean()throws HibernateException{

	}

	/**
	 * 添加一个Maintain对象到数据库
	 *
	 * @param st 报修记录对象
	 * @return boolean
	 * @throws HibernateException
	 */
	public static boolean addMaintain(Maintain st)throws HibernateException{
		boolean result = true;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			session.save(st);
			tx.commit();
			result=tx.wasCommitted();
		}catch(Exception e){
			result = false;
			System.out.println("================"+e.getMessage());
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){

			}
		}
		return result;
	}

	/**
	 * 更新Maintain对象到数据库
	 *
	* @param st 报修记录对象
	 * @throws HibernateException
	 */
	public static boolean updateMaintain(Maintain st)throws HibernateException{
		boolean result = false;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			session.update(st);
			tx.commit();
			result=tx.wasCommitted();
		}catch(Exception e){
			e.printStackTrace();
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

	public static boolean updateMaintain(Maintain st , String devno, String trbtype, String trbdate, String trbtime)throws HibernateException{
		return updateMaintain(st);
	}

	public static List getMaintain(String[] devno, String pdate1, String pdate2, String[] subdevice, String[] state, int firstResult, int maxResults )throws HibernateException {
		String queryString = "";
		queryString = "  from Maintain where devno in ('" + devno[0] + "'";
		for (int i=1; i<devno.length; i++) {
			queryString += ", '" + devno[i] + "'";
		}
		queryString += ")";
		
		if (!pdate1.equals("")) { 
  			queryString += "  and trbdate>='"+pdate1+"'";
		}
		if (!pdate2.equals("")) {
			queryString += " and trbdate<='"+pdate2+"'";
		}
  	
		if (subdevice != null) {
			queryString += " and trbtype in ('" + subdevice[0] + "'";
			for (int i = 1; i < subdevice.length; i++) {
				queryString += ", '" + subdevice[i] + "'";
			}
			queryString += ")";
		}
		
		if (state!=null) {
			queryString += " and state in ('" + state[0] + "'";
			for (int i = 1; i < state.length; i++) {
				queryString += ", '" + state[i] + "'";
			}
			queryString += ")";
		}
  	
		PubUtil myPubUtil = new PubUtil();
		if( ( myPubUtil.dealNull(Constants.DB_OP_TYPE) ).equals("1") )
			queryString += " order by trbdate desc,trbtime desc";

		List result = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx	= session.beginTransaction();
			Query query	= session.createQuery(queryString);
			result 		= query.setFirstResult(firstResult).setMaxResults(maxResults).list();
			tx.commit();
		} catch (Exception e) {
			return null;
		}

		return result;
	}

	public static int getMaintainCount(String[] devno, String pdate1, String pdate2, String[] subdevice, String[] state)throws HibernateException {

		String queryString = "";
		queryString = " select count(*) from Maintain where devno in ('" + devno[0] + "'";
		for (int i=1; i<devno.length; i++) {
			queryString += ", '" + devno[i] + "'";
		}
		queryString += ")";
		
		if (!pdate1.equals("")) { 
			queryString += " and trbdate>='"+pdate1+"'";
		}
		if (!pdate2.equals("")) {
			queryString += " and trbdate<='"+pdate2+"'";
		}
		
		if (subdevice!=null) {
			queryString += " and trbtype in ('" + subdevice[0] + "'";
			for (int i = 1; i < subdevice.length; i++) {
				queryString += ", '" + subdevice[i] + "'";
			}
			queryString += ")";
		}
		
		if (state!=null) {
			queryString += " and state in ('" + state[0] + "'";
			for (int i = 1; i < state.length; i++) {
				queryString += ", '" + state[i] + "'";
			}
			queryString += ")";
		}
		
		List result = null;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx 	= session.beginTransaction();
			Query query 	= session.createQuery(queryString);
			result 		= query.list();
			tx.commit();
		} catch (Exception e) {
			return 0;
		}
		return ((Integer)result.get(0)).intValue(); 
	}
	
	public static List getQueryList(String HQLstr)throws HibernateException{
		String queryString = HQLstr.trim();
		List it = null;

		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			it= query.list();
			tx.commit();
		}catch(Exception e){
			return null;
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}

		return it;
	}
	
	public static List getMaintainList()throws HibernateException{
		String queryString = "select maintain from Maintain as maintain where (maintain.state='0' or maintain.state='1')";
		
		PubUtil myPubUtil = new PubUtil();
		if( ( myPubUtil.dealNull(Constants.DB_OP_TYPE) ).equals("1") )
			queryString += " order by maintain.devno, maintain.trbdate desc, maintain.trbtime desc";
		
		List it = null;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			it= query.list();
			tx.commit();
		}catch(Exception e){
			return null;	
		}
		return it;
	}
	
	
	public static List getMaintainList(String devno,String date1,String date2)throws HibernateException{
		String queryString = " from Maintain where trbdate<='"+date2+"' ";
		
		if(!date1.equals("")){
			queryString += " and trbdate>='"+date1+"'";
		}	
		if(!devno.equals("")){
			queryString += " and devno='"+devno+"'";
		}
		
		//System.out.println("queryString="+queryString);
		
		PubUtil myPubUtil = new PubUtil();
		if( ( myPubUtil.dealNull(Constants.DB_OP_TYPE) ).equals("1") )
			queryString += " order by devno, trbdate desc, trbtime desc";
		
		List it = null;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			it= query.list();
			tx.commit();
		}catch(Exception e){
			System.out.println("e="+e);
			return null;	
		}
		return it;
	}
	
	public static List getAMaintain(String devno,String parts,String trbdate,String trbtime)throws HibernateException{
		String queryString = "from Maintain where devno='"+devno+"'";
		
		if(parts!=null&&!parts.equals("")){
			queryString += " and trbtype='"+parts+"'";
		}
		
		if(trbdate!=null&&!trbdate.equals("")){
			queryString += " and trbdate='"+trbdate+"'";
		}
		
		if(trbtime!=null&&!trbtime.equals("")){
			queryString += " and trbtime='"+trbtime+"'";	
		}
		
		
		List it = null;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			it= query.list();
			tx.commit();
		}catch(Exception e){
			return null;	
		}
		return it;
	}
	
	public static List getSomeMaintain(String devno,String parts,String trbdate,String trbtime)throws HibernateException{
		String queryString = "from Maintain where devno='"+devno+"'";
		
		if(parts!=null||!parts.equals("")){
			queryString += " and trbtype='"+parts+"'";
		}
		
		if(trbdate!=null||!trbdate.equals("")){
			queryString += " and trbdate='"+trbdate+"'";
		}
		
		if(trbtime!=null||!trbtime.equals("")){
			queryString += " and trbtime='"+trbtime+"'";	
		}
		
		
		List it = null;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			it= query.list();
			tx.commit();
		}catch(Exception e){
			return null;	
		}
		return it;
	}
	
	public static List getMaintainList(String state)throws HibernateException{
		String queryString = "select maintain from Maintain as maintain where maintain.state='"+state+"'";
		
		PubUtil myPubUtil = new PubUtil();
		if( ( myPubUtil.dealNull(Constants.DB_OP_TYPE) ).equals("1") )
			queryString += " order by maintain.devno, maintain.trbdate desc, maintain.trbtime desc";
		
		List it = null;
		try{
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(queryString);
			it= query.list();
			tx.commit();
		}catch(Exception e){
			return null;	
		}
		return it;
	}
}
