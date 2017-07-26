package com.lcjr.pvxp.model;

import java.util.List;

import net.sf.hibernate.HibernateException;

import com.lcjr.pvxp.bean.CardDistillBean;
import com.lcjr.pvxp.bean.CardStatusBean;
import com.lcjr.pvxp.orm.util.HibernateUtil;

/**
 * 
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 和表zzt_card_Status相关的业务逻辑</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2006</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @version 1.0 2011/10/09
 * @author 武坤鹏
 */
public class CardStatusModel {
	
	/**
	 * 构造函数
	 * @throws HibernateException
	 */
	public CardStatusModel() throws HibernateException{
		
	}
	
	/**
	 * 返回符合查询条件的结果类集，主要查询zzt_card_ly表中数据
	 * 
	 * @param termnum 设备编号
	 * @param firstResult	第一条记录
	 * @param maxResults	最大记录数
	 * @return	 返回符合查询条件的结果类集
	 * @throws HibernateException
	 */
	public static List getCardStatusList(String[] termnum,int firstResult, int maxResults) throws HibernateException{
		List result=CardStatusBean.getCardStatusList(termnum, firstResult, maxResults );
		HibernateUtil.closeSession();
		return result;
	}
	
	/**
	 * 获得符合查询条件的数据条数 （总条数）by wukp
	 * @param termnum
	 * @return
	 * @throws HibernateException
	 */
	public static int getCardStatusCount(String[] termnum) throws HibernateException {
		int result = CardStatusBean.getCardStatusCount(termnum);
		HibernateUtil.closeSession();
		return result;
	}
}
