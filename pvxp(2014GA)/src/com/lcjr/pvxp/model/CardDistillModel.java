package com.lcjr.pvxp.model;

import java.util.List;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;

import com.lcjr.pvxp.bean.CardDistillBean;
import com.lcjr.pvxp.orm.util.HibernateUtil;

/**
 * 
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 和表zzt_card_ly相关的业务逻辑
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2006
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @version 1.0 2011/10/08
 * @author 武坤鹏
 * 
 */
public class CardDistillModel {

	Logger log = Logger.getLogger(CardDistillModel.class.getName());

	/**
	 * 构造函数
	 * 
	 * @throws HibernateException
	 */
	public CardDistillModel() throws HibernateException {
	}

	/**
	 * 返回符合查询条件的结果类集，主要查询zzt_card_ly表中数据
	 * 
	 * @param termnum
	 *            设备编号
	 * @param pdate1
	 *            起始日期
	 * @param pdate2
	 *            截止日期
	 * @param firstResult
	 *            第一条记录
	 * @param maxResults
	 *            最大记录数
	 * @return 返回符合查询条件的结果类集
	 * @throws HibernateException
	 */
	public static List getCardDistillList(String[] termnum, String pdate1, String pdate2, int firstResult, int maxResults) throws HibernateException {
		List result = CardDistillBean.getCardDistill(termnum, pdate1, pdate2, firstResult, maxResults);
		HibernateUtil.closeSession();
		return result;
	}

	/**
	 * 获得符合查询条件的数据条数 （总条数）by wukp
	 * 
	 * @param termnum
	 * @param pdate1
	 * @param pdate2
	 * @return
	 * @throws HibernateException
	 */
	public static int getInvoiceDistillCount(String[] termnum, String pdate1, String pdate2) throws HibernateException {
		int result = CardDistillBean.getInvoiceDistillCount(termnum, pdate1, pdate2);
		HibernateUtil.closeSession();

		return result;
	}

}
