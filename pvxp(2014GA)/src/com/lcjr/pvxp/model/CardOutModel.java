package com.lcjr.pvxp.model;

import java.util.List;

import com.lcjr.pvxp.bean.CardOutBean;
import com.lcjr.pvxp.orm.util.HibernateUtil;

import net.sf.hibernate.HibernateException;

/**
 * 
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> �ͱ�zzt_card_out��ص�ҵ���߼�</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2006</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @version 1.0 2011/10/10
 * @author ������
 *
 */
public class CardOutModel {
	/**
	 * ���캯��
	 *
	 */
	public CardOutModel()throws HibernateException{
		
	}

	/**
	 * ��ѯ�����������Ľ���ĸ���
	 * @param termnum	�豸��
	 * @param date1		��ʼ����
	 * @param date2		��ֹ����
	 * @param time1		��ʼʱ��
	 * @param time2		��ֹʱ��
	 * @param id		���֤��
	 * @param cardno	��������
	 * @return		���������Ľ���ĸ���	
	 * @throws HibernateException
	 */
	public static int getCardOutCount(String[] termnum, String date1, String date2, String time1, String time2, String id, String cardno) throws HibernateException {
		int  count=CardOutBean.getCardOutCount(termnum,date1,date2,time1,time2,id,cardno);
		HibernateUtil.closeSession();
		return count;
	}

	
	/**
	 * ��ѯ�����������Ľ�����༯
	 * @param termnum	�豸��
	 * @param date1		��ʼ����
	 * @param date2		��ֹ����
	 * @param time1		��ʼʱ��
	 * @param time2		��ֹʱ��
	 * @param id		���֤��
	 * @param cardno	��������
	 * @return		���������Ľ���ĸ���	
	 * @throws HibernateException
	 */
	public static List getCardOutList(String[] termnum, String date1, String date2, String time1, String time2, String id, String cardno, int firstResult, int maxResults) throws HibernateException{
		List result=CardOutBean.getCardOutList(termnum, date1, date2,time1 ,time2,id,cardno, firstResult, maxResults );
		HibernateUtil.closeSession();
		return result;
		
	}
	
}
