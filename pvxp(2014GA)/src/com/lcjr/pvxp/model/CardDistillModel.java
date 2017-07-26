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
 * <b>Description:</b> �ͱ�zzt_card_ly��ص�ҵ���߼�
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2006
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * <br>
 * 
 * @version 1.0 2011/10/08
 * @author ������
 * 
 */
public class CardDistillModel {

	Logger log = Logger.getLogger(CardDistillModel.class.getName());

	/**
	 * ���캯��
	 * 
	 * @throws HibernateException
	 */
	public CardDistillModel() throws HibernateException {
	}

	/**
	 * ���ط��ϲ�ѯ�����Ľ���༯����Ҫ��ѯzzt_card_ly��������
	 * 
	 * @param termnum
	 *            �豸���
	 * @param pdate1
	 *            ��ʼ����
	 * @param pdate2
	 *            ��ֹ����
	 * @param firstResult
	 *            ��һ����¼
	 * @param maxResults
	 *            ����¼��
	 * @return ���ط��ϲ�ѯ�����Ľ���༯
	 * @throws HibernateException
	 */
	public static List getCardDistillList(String[] termnum, String pdate1, String pdate2, int firstResult, int maxResults) throws HibernateException {
		List result = CardDistillBean.getCardDistill(termnum, pdate1, pdate2, firstResult, maxResults);
		HibernateUtil.closeSession();
		return result;
	}

	/**
	 * ��÷��ϲ�ѯ�������������� ����������by wukp
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
