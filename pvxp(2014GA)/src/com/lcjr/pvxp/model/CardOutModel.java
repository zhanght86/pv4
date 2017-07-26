package com.lcjr.pvxp.model;

import java.util.List;

import com.lcjr.pvxp.bean.CardOutBean;
import com.lcjr.pvxp.orm.util.HibernateUtil;

import net.sf.hibernate.HibernateException;

/**
 * 
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 和表zzt_card_out相关的业务逻辑</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2006</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @version 1.0 2011/10/10
 * @author 武坤鹏
 *
 */
public class CardOutModel {
	/**
	 * 构造函数
	 *
	 */
	public CardOutModel()throws HibernateException{
		
	}

	/**
	 * 查询出符合条件的结果的个数
	 * @param termnum	设备号
	 * @param date1		开始日期
	 * @param date2		截止日期
	 * @param time1		开始时间
	 * @param time2		截止时间
	 * @param id		身份证号
	 * @param cardno	发卡卡号
	 * @return		符合条件的结果的个数	
	 * @throws HibernateException
	 */
	public static int getCardOutCount(String[] termnum, String date1, String date2, String time1, String time2, String id, String cardno) throws HibernateException {
		int  count=CardOutBean.getCardOutCount(termnum,date1,date2,time1,time2,id,cardno);
		HibernateUtil.closeSession();
		return count;
	}

	
	/**
	 * 查询出符合条件的结果的类集
	 * @param termnum	设备号
	 * @param date1		开始日期
	 * @param date2		截止日期
	 * @param time1		开始时间
	 * @param time2		截止时间
	 * @param id		身份证号
	 * @param cardno	发卡卡号
	 * @return		符合条件的结果的个数	
	 * @throws HibernateException
	 */
	public static List getCardOutList(String[] termnum, String date1, String date2, String time1, String time2, String id, String cardno, int firstResult, int maxResults) throws HibernateException{
		List result=CardOutBean.getCardOutList(termnum, date1, date2,time1 ,time2,id,cardno, firstResult, maxResults );
		HibernateUtil.closeSession();
		return result;
		
	}
	
}
