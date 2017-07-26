package com.lcjr.pvxp.bean;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.orm.*;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

//import org.apache.log4j.*;

/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> �ͱ�zzt_trcdlog��ص�ҵ���߼�</p>
 * <p><b>Copyright:</b> Copyright (c) 2005</p>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p>
 * @author ��ѧ��
 * @version 1.0 2005/03/25
 */
public class TradeLogModel {
	//private Logger log = Logger.getLogger(TradeLogModel.class);

	/**
	 * ��������sql��ѯ���
	 * @param bankid
	 * @param devno
	 * @param accno1
	 * @param accno2
	 * @param devtrcd
	 * @param moneytype
	 * @param money1
	 * @param money2
	 * @param date1
	 * @param date2
	 * @param returnno
	 * @return  ����SQL��ѯ���
	 */
	public String setCondition(String bankid, String[] devno, String accno1, String accno2, String[] devtrcd,
	                        String[] moneytype, String money1, String money2, String date1, String date2,
	                        String[] returnno) {
	
		String condition;
		
		condition = " where";
		
		if (bankid.indexOf('A') == -1) {
			condition += " other1='" + bankid + "'";
		} else {
			bankid = bankid.replace('A', '%');
			condition += " other1 like '" + bankid + "'";
		}
//<-----���׳���ĵط�---->
		if (devno.length > 0) {
			condition += " and devno in ('" + devno[0] + "'";
			for (int i=1; i<devno.length; i++) {
				condition += ", '" + devno[i] + "'";
			}
			condition += ")";
		}
		
		if (devtrcd.length > 0) {
			condition += " and devtrcd in ('" + devtrcd[0] + "'";
			for (int i=1; i<devtrcd.length; i++) {
				condition += ", '" + devtrcd[i] + "'";
			}
			condition += ")";
		}
		
		if (moneytype.length > 0) {
			condition += " and moneytype1 in ('" + moneytype[0] + "'";
			for (int i=1; i<moneytype.length; i++) {
				condition += ", '" + moneytype[i] + "'";
			}
			condition += ")";
			
			condition += " or other3 in ('" + moneytype[0] + "'";
			for (int i=1; i<moneytype.length; i++) {
				condition += ", '" + moneytype[i] + "'";
			}
			condition += ")";
		}
		
		if (returnno.length > 0) {
			condition += " and returnno in ('" + returnno[0] + "'";
			for (int i=1; i<returnno.length; i++) {
				condition += ", '" + returnno[i] + "'";
			}
			condition += ")";
		}
		
		if (!accno1.equals("")) {
			condition += " and accno1 like '%" + accno1 + "%'";
		}
		
		if (!accno2.equals("")) {
			condition += " and accno2 like '%" + accno2 + "%'";
		}
		
		if (!money1.equals("")) {
			condition += " and (money1>=" + money1 + " or money2>=" + money1 + " or other2>=" + money1 + ")";
		}
		
		if (!money2.equals("")) {
			condition += " and (money1<=" + money2 + " or money2<=" + money2 + " or other2<=" + money2 + ")";
		}
		
		if (!date1.equals("")) {
			condition += " and devdate>='" + date1 + "'";
		}
		
		if (!date2.equals("")) {
			condition += " and devdate<='" + date2 + "'";
		}
		PubUtil myPubUtil = new PubUtil();
		if( ( myPubUtil.dealNull(Constants.DB_OP_TYPE) ).equals("1") )
			condition += " order by devdate desc, devtime desc";
		//log.debug("condition=\"" + condition + "\"");
		return condition;
	}

	/**
	 *  ���<font color=red> ������ˮ�� zzt_trcdlog</font> ��ͳ�ƽ������
	 * @param condition
	 * @return
	 * @throws HibernateException
	 */
	public int getTrcdlogCount(String condition) throws HibernateException {
		String sql = "select count(*) from Trcdlog" + condition;
		int count = TradeLogBean.getTradeLogCount(sql);
		return count;
	}

	/**
	 * ���<font color=red>������ϸ��zzt_mxb </font>��ͳ�ƽ������
	 * @param condition
	 * @return
	 * @throws HibernateException
	 */
	public int getMxbCount(String condition) throws HibernateException {
		String sql = "select count(*) from Mxb" + condition;
		int count = TradeLogBean.getTradeLogCount(sql);
		return count;
	}

	/**
	 * ���<font color=red> ������ϸ��ʷ��zzt_mxb_tmp</font> ��ͳ�ƽ������
	 * @param condition
	 * @return
	 * @throws HibernateException
	 */
	public int getMxb_tmpCount(String condition) throws HibernateException {
		String sql = "select count(*) from Mxb_tmp" + condition;
		int count = TradeLogBean.getTradeLogCount(sql);
		return count;
	}

	/**
	 * ���<font color=red> ������ˮ�� zzt_trcdlog</font> ��ͳ�ƽ�� �༯
	 * 
	 * @param condition
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws HibernateException
	 */
	public List getTrcdlog(String condition, int firstResult, int maxResults) throws HibernateException {
		String sql = "from Trcdlog" + condition;
		List result = TradeLogBean.getTradeLog(sql, firstResult, maxResults);
		HibernateUtil.closeSession();
		
		return result;
	}

	/**
	 * ���<font color=red>������ϸ��zzt_mxb </font>��ͳ�ƽ�� �༯
	 * @param condition
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws HibernateException
	 */
	public List getMxb(String condition, int firstResult, int maxResults) throws HibernateException {
		String sql = "from Mxb" + condition;
		List result = TradeLogBean.getTradeLog(sql, firstResult, maxResults);
		  	
		HibernateUtil.closeSession();
		
		return result;
	}

	/**
	 * ���<font color=red>������ϸ��ʷ��zzt_mxb_tmp</font>��ͳ�ƽ�� �༯
	 * @param condition
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws HibernateException
	 */
	public List getMxb_tmp(String condition, int firstResult, int maxResults) throws HibernateException {
		String sql = "from Mxb_tmp" + condition;
		List result = TradeLogBean.getTradeLog(sql, firstResult, maxResults);
		  	
		HibernateUtil.closeSession();
		
	
		return result;
	}
}
