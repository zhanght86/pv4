package com.lcjr.pvxp.model;

import java.util.List;
import java.util.Vector;

import net.sf.hibernate.HibernateException;

import com.lcjr.pvxp.bean.StaMissionBean;
import com.lcjr.pvxp.orm.StaMission;
import com.lcjr.pvxp.orm.util.HibernateUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> �ͱ�zzt_stamission��ص�ҵ���߼�
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * <br>
 * 
 * @author ��ѧ��
 * @version 1.0 2005/03/28
 */
public class StaMissionModel {
	public StaMissionModel() throws HibernateException {
	}

	// private static Logger log = Logger.getLogger("wuxk");

	/**
	 * ����ָ��������ͳ��������б�
	 * 
	 * @author ��ѧ��
	 * @param String
	 *            bankid ����ID
	 * @param int firstRow ���صĵ�һ����¼
	 * @param int maxResults ��෵�صļ�¼��
	 * @return ָ��������ͳ��������б�
	 */
	public static List getAllStaMission(Vector bankids, String statesort, int firstRow, int maxResults) throws HibernateException {
		List result = StaMissionBean.getAllStaMission(bankids, statesort, firstRow, maxResults);
		HibernateUtil.closeSession();
		return result;
	}

	/**
	 * ����ָ��������ͳ���������ָ��״̬���б�
	 * 
	 * @author ��ѧ��
	 * @param String
	 *            bankid ����ID
	 * @param String
	 *            currentflag ����״̬
	 * @param int firstRow ���صĵ�һ����¼
	 * @param int maxResults ��෵�صļ�¼��
	 * @return ָ��������ͳ��������б�
	 */
	public static List getStaMission(Vector bankids, String currentflag, String statesort, int firstRow, int maxResults) throws HibernateException {
		List result = StaMissionBean.getStaMission(bankids, currentflag, statesort, firstRow, maxResults);
		HibernateUtil.closeSession();
		return result;
	}

	/**
	 * ����ָ��������ͳ��������б������
	 * 
	 * @author ��ѧ��
	 * @param Vector
	 *            bankids ����ID
	 * @return ָ��������ͳ��������б�����
	 */
	public static int getAllStaMissionCount(Vector bankids, String statesort) throws HibernateException {
		int count = StaMissionBean.getAllStaMissionCount(bankids, statesort);
		HibernateUtil.closeSession();
		return count;
	}

	/**
	 * ����ָ��������ͳ���������ָ��״̬���б�����
	 * 
	 * @author ��ѧ��
	 * @param Vector
	 *            bankids ����ID
	 * @param String
	 *            currentflag ����״̬
	 * @return ָ��������ͳ��������б�����
	 */
	public static int getStaMissionCount(Vector bankids, String currentflag, String statesort) throws HibernateException {
		int count = StaMissionBean.getStaMissionCount(bankids, currentflag, statesort);
		HibernateUtil.closeSession();
		return count;
	}

	/**
	 * ɾ��ָ���ı���
	 * 
	 * @author ��ѧ��
	 * @param String
	 *            [] statename Ҫɾ���ı���������
	 * @return ɾ���ı�����
	 */
	public static boolean delStaMission(String fileName) throws HibernateException {
		boolean result = StaMissionBean.delStaMission(fileName);
		HibernateUtil.closeSession();
		return result;
	}

	/**
	 * <p>
	 * ���һ��StaMission�������ݿ�
	 * </p>
	 * 
	 * @param st
	 *            StaMission����
	 * 
	 * @return int -1:���ʧ�� 0:��ӳɹ�
	 * 
	 * @throws HibernateException
	 */
	public static int addStaMission(StaMission st) throws HibernateException {
		boolean result = false;
		int ret = -1;
		StaMissionBean myStaMissionBean = new StaMissionBean();
		try {
			result = myStaMissionBean.addStaMission(st);
		} catch (Exception e) {
			result = false;
		}
		if (result)
			ret = 0;
		return ret;
	}

	/**
	 * <p>
	 * ����ͳ�ƿ�ʼʱ�䡢������ࡢ�����������<br>
	 * ȡ��ϵͳ�����
	 * </p>
	 * 
	 * @param timeid
	 *            ͳ�ƿ�ʼʱ��
	 * @param statesort
	 *            �������
	 * @param createtype
	 *            �����������
	 * @param ownerid
	 *            ������
	 * 
	 * @return StaMission ����StaMission����
	 * 
	 * @throws HibernateException
	 */
	public static StaMission getOneStaMission(String timeid, String statesort, String createtype, String ownerid) throws HibernateException {
		if (timeid == null || statesort == null || createtype == null || ownerid == null)
			return null;

		StaMissionBean myStaMissionBean = new StaMissionBean();
		try {
			StaMission myStaMission = myStaMissionBean.getOneStaMission(timeid, statesort, createtype, ownerid);
			return myStaMission;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <p>
	 * ����StaMission��¼
	 * </p>
	 * 
	 * @param st
	 *            StaMission����
	 * 
	 * @return int -1:����ʧ�� 0:���³ɹ�
	 * 
	 * @throws HibernateException
	 */
	public static int updateStaMission(StaMission st) throws HibernateException {
		boolean result = false;
		int ret = -1;
		StaMissionBean myStaMissionBean = new StaMissionBean();
		try {
			result = myStaMissionBean.updateStaMission(st);
		} catch (Exception e) {
			result = false;
		}
		if (result)
			ret = 0;

		return ret;
	}

}
