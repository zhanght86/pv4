package com.lcjr.pvxp.model;

import java.util.List;

import net.sf.hibernate.HibernateException;

import com.lcjr.pvxp.bean.CardDistillBean;
import com.lcjr.pvxp.bean.CardStatusBean;
import com.lcjr.pvxp.orm.util.HibernateUtil;

/**
 * 
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> �ͱ�zzt_card_Status��ص�ҵ���߼�</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2006</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @version 1.0 2011/10/09
 * @author ������
 */
public class CardStatusModel {
	
	/**
	 * ���캯��
	 * @throws HibernateException
	 */
	public CardStatusModel() throws HibernateException{
		
	}
	
	/**
	 * ���ط��ϲ�ѯ�����Ľ���༯����Ҫ��ѯzzt_card_ly��������
	 * 
	 * @param termnum �豸���
	 * @param firstResult	��һ����¼
	 * @param maxResults	����¼��
	 * @return	 ���ط��ϲ�ѯ�����Ľ���༯
	 * @throws HibernateException
	 */
	public static List getCardStatusList(String[] termnum,int firstResult, int maxResults) throws HibernateException{
		List result=CardStatusBean.getCardStatusList(termnum, firstResult, maxResults );
		HibernateUtil.closeSession();
		return result;
	}
	
	/**
	 * ��÷��ϲ�ѯ�������������� ����������by wukp
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
