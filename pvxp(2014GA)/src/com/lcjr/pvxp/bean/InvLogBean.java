package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.InvoiceDistill;
import com.lcjr.pvxp.util.*;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

//import org.apache.log4j.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> 和表zzt_invlog相关的业务逻辑</p>
 * <p><b>Copyright:</b> Copyright (c) 2006</p>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p>
 * @author xucc
 * @version 1.0 2006/01/14
 */

/**
 * <p><b>Description:</b>当捕获到异常时调用closeFactory方法</p>
 * @author xucc
 * @version 1.0 2007/04/09
 */
public class InvLogBean {

	public InvLogBean()throws HibernateException {
	}

	public static List getInvLog(String[] devno, String pdate1, String pdate2, String invno1, String invno2, String accno, int firstResult, int maxResults ) throws HibernateException{
		String queryString = "";
		queryString = "  from InvLog where devno in ('" + devno[0] + "'";
		for (int i=1; i<devno.length; i++) {
			queryString += ", '" + devno[i] + "'";
		}
		queryString += ")";
		
		if (!pdate1.equals("")) { 
			queryString += "  and pdate>='"+pdate1+"'";
		}
		if (!pdate2.equals("")) {
			queryString += " and pdate<='"+pdate2+"'";
		}
  	
		if (!invno1.equals("")){
			queryString += "  and invoiceno>='"+invno1+"'";
		}
  	
		if (!invno2.equals("")){
			queryString += "  and invoiceno<='"+invno2+"'";
		}
  	
		if (!accno.equals("")){
			queryString += "  and accno1='"+accno+"'";
		}
  	
		PubUtil myPubUtil = new PubUtil();
		if( ( myPubUtil.dealNull(Constants.DB_OP_TYPE) ).equals("1") )
			queryString += " order by pdate desc, ptime desc";

		List result = null;
		try {
			String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent","ServType","0","PowerView.ini")).trim();
			if( servtype.equals("") ) servtype = "0";
		
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			
			//通过配置文件判断所用分页方式 xucc 20090624
			if(servtype.equals("0")){
				result = query.setFirstResult(firstResult).setMaxResults(maxResults).list();
			}else if(servtype.equals("1")){
			
				result = new ArrayList();
				ScrollableResults rs = query.scroll();
				for (int i = firstResult; i < firstResult+maxResults; i++) {
					if(rs.setRowNumber(i)){
						result.add(rs.get(0));
					}
				}
			}
			
			tx.commit();
		} catch (Exception e) {
			HibernateUtil.closeFactory();
			return null;
		}finally{
			try{
				HibernateUtil.closeSession();
			}catch(Exception ex){
			}
		}

		return result;
	}

	public static int getInvLogCount(String[] devno, String pdate1, String pdate2, String invno1, String invno2, String accno) throws HibernateException{

		String queryString = "";
  	
		queryString = " select count(*) from InvLog where devno in ('" + devno[0] + "'";
		for (int i=1; i<devno.length; i++) {
			queryString += ", '" + devno[i] + "'";
		}
		queryString += ")";
		
		if (!pdate1.equals("")) { 
			queryString += " and pdate>='"+pdate1+"'";
		}
		if (!pdate2.equals("")) {
			queryString += " and pdate<='"+pdate2+"'";
		}
  	
		if (!invno1.equals("")){
	 		queryString += "  and invoiceno>='"+invno1+"'";
		}
  	
		if (!invno2.equals("")){
			queryString += "  and invoiceno<='"+invno2+"'";
		}
  	
		if (!accno.equals("")){
			queryString += "  and accno1='"+accno+"'";
		}

		List result = null;
		int count = 0;
		try {
			Session session = HibernateUtil.currentSession();
			Transaction tx= session.beginTransaction();
			Query query = session.createQuery(queryString);
			result = query.list();
			count = ((Integer)result.get(0)).intValue();
			tx.commit();//先处理结果再commit xucc 20090624
		} catch (Exception e) {
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
