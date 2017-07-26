package com.lcjr.pvxp.model;

import java.util.List;
import java.util.Vector;

import net.sf.hibernate.HibernateException;

import com.lcjr.pvxp.bean.DevinfoBean;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> �ͱ�zzt_devinfo��ص�ҵ��ģ��
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
 * @author ����
 * @version 1.0 2004/12/08
 */
public class DevinfoModel {

	private static List<Devinfo> mydevlist = null;
	private static int len = 0;
	private static int resetflag = 0;

	public DevinfoModel() throws HibernateException {
		/*
		 * if (mydevlist == null) { rebiuld(); } else if (resetflag == 1) {
		 * mydevlist.clear(); rebiuld(); resetflag = 0; }
		 */
		rebiuld();// ��Ϊֱ�ӵ���rebuild�����ж�״̬ xucc 20100105
	}

	public List<Devinfo> initDevlist() throws HibernateException {
		DevinfoBean myDevinfoBean = new DevinfoBean();
		List<Devinfo> mydevlist1 = myDevinfoBean.getAllDevList();
		HibernateUtil.closeSession();
		return mydevlist1;
	}

	private void setLen() {
		len = mydevlist.size();
	}

	private void rebiuld() throws HibernateException {
		mydevlist = initDevlist();
		setLen();
	}

	/**
	 * <p>
	 * �����豸��Ϣ�б��־�����ڶ�zzt_devinfoд������
	 * </p>
	 * <p>
	 * Ŀ�ģ��´δ���ʵ��ʱ���¼���devlist
	 * </p>
	 */
	public static void reset() {
		resetflag = 1;
	}

	/**
	 * <p>
	 * ��ȡ�豸��Ϣ�б�
	 * </p>
	 * 
	 * @return List
	 */
	public List<Devinfo> getDevList() throws HibernateException {
		// this.mydevlist = initDevlist();//��Ϊֱ�Ӷ����ݿ� xucc 20090625
		resetNow();// ��Ϊֱ�ӵ����ؽ����� xucc 20100105
		return mydevlist;
	}

	/**
	 * <p>
	 * ��ȡָ��������ŵ��豸��Ϣ�б�
	 * </p>
	 * 
	 * @param mbankid
	 *            ������ţ����������ĸA����ʾ���Ժ�����λƥ�������ַ���
	 * @return List
	 */
	public List<Devinfo> getDevListOfBank(String mbankid) {

		if (mydevlist == null || mbankid == null)
			return null;
		int allpos = mbankid.indexOf("A");

		Vector<Devinfo> myVector = new Vector<Devinfo>();
		PubUtil myPubUtil = new PubUtil();
		len = mydevlist.size();

		try {
			// wukp 111124 û��A
			if (allpos == -1) {
				for (int i = 0; i < len; i++) {
					Devinfo temp = (Devinfo) mydevlist.get(i);
					if (mbankid.trim().equals(myPubUtil.dealNull(temp.getBankid()).trim())) {
						myVector.add(temp);
					}
				}
			} else {// wukp 111124 ��A
				for (int i = 0; i < len; i++) {
					Devinfo temp = (Devinfo) mydevlist.get(i);
					if ((myPubUtil.dealNull(temp.getBankid()).trim()).indexOf(mbankid.substring(0, allpos)) == 0) {
						myVector.add(temp);
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		if (myVector == null) {
			return null;
		} else {
			return myVector.subList(0, myVector.size());
		}
	}

	/**
	 * <p>
	 * ��ȡ�豸�ڵ�ǰ�豸�б����е�λ��
	 * </p>
	 * 
	 * @param devno
	 *            �豸���
	 * @return int �豸�ڵ�ǰ�豸�б����е�λ��(-1:���豸������������)
	 */
	public static int indexOfDevList(String devno) {
		if (mydevlist == null || devno == null)
			return -1;
		int len = mydevlist.size();
		int index = -1;
		for (int i = 0; i < len; i++) {
			Devinfo temp = (Devinfo) mydevlist.get(i);
			if (devno.trim().equals(temp.getDevno().trim())) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * <p>
	 * �ӵ�ǰ�豸�б����л�ȡ�豸ʵ��
	 * </p>
	 * 
	 * @param devno
	 *            �豸���
	 * @return Devinfo
	 */
	public static Devinfo getDevFromList(String devno) {
		if (mydevlist == null || devno == null)
			return null;
		int len = mydevlist.size();
		Devinfo reDevinfo = null;
		for (int i = 0; i < len; i++) {
			Devinfo temp = (Devinfo) mydevlist.get(i);
			if (devno.trim().equals(temp.getDevno().trim())) {
				reDevinfo = temp;
				break;
			}
		}
		return reDevinfo;
	}

	/**
	 * <p>
	 * ����豸�����ݿⲢ�����豸�б�
	 * </p>
	 * 
	 * @param st
	 *            �豸��Ϣ����
	 * @return int -1:����豸ʧ�� 0:����豸�ɹ� 1:����豸�ɹ����Ǹ����豸�б�ʧ�ܣ���Ҫ�ؽ��豸�б���
	 * @throws HibernateException
	 */
	public static int addDev(Devinfo st) throws HibernateException {
		// System.out.println(st.getAutherno()+":"+st.getBankid()+":"+st.getDevaddr()+":"+st.getDevip()+":"+st.getDevkey()+":"+st.getDevmac()+":"+st.getDevno()+":"+st.getDutyname()+":"+st.getLocaltag()+":"+st.getMackey()+":"+st.getOpentag()+":"+st.getOrgancontact()+":"+st.getOrganno()+":"+st.getPacktype()+":"+st.getPinkey());
		boolean result = false;
		boolean result1 = false;
		int ret = 0;
		DevinfoBean myDevinfoBean = new DevinfoBean();
		try {
			result = myDevinfoBean.addDevinfo(st);
		} catch (Exception e) {
			result = false;
		}
		// �������豸�����ݿ�ɹ���������豸�б�
		if (result)
			result1 = mydevlist.add(st);
		if (!result)
			ret = -1;
		else if (!result1)
			ret = 1;
		else
			ret = 0;

		return ret;
	}

	/**
	 * <p>
	 * ɾ��ָ���豸��ŵ��豸
	 * </p>
	 * 
	 * @param devno
	 *            �豸���
	 * @return int -1:ɾ���豸ʧ�� 0:ɾ���豸�ɹ� 1:ɾ���豸�ɹ����Ǹ����豸�б�ʧ�ܣ���Ҫ�ؽ��豸�б���
	 * @throws HibernateException
	 */
	public static int deleteDev(String devno) throws HibernateException {
		boolean result = false;
		boolean result1 = false;
		int ret = 0;
		DevinfoBean myDevinfoBean = new DevinfoBean();
		try {
			result = myDevinfoBean.deleteDevinfo(devno);
		} catch (Exception e) {
			result = false;
		}

		Devinfo devobj = getDevFromList(devno);
		// ���ɾ���豸�ɹ���������豸�б�
		if (result && devobj != null)
			result1 = mydevlist.remove(devobj);

		if (!result)
			ret = -1;
		else if (!result1)
			ret = 1;
		else
			ret = 0;

		return ret;
	}

	/**
	 * <p>
	 * ����ָ���豸��ŵ��豸��Ϣ
	 * </p>
	 * 
	 * @param st
	 *            �豸��Ϣ����
	 * @param devno
	 *            �豸���
	 * @return int -1:�����豸ʧ�� 0:�����豸�ɹ� 1:�����豸�ɹ����Ǹ����豸�б�ʧ�ܣ���Ҫ�ؽ��豸�б���
	 * @throws HibernateException
	 */
	public static int updateDev(Devinfo st, String devno) throws HibernateException {
		boolean result = false;
		boolean result1 = false;
		int ret = 0;
		DevinfoBean myDevinfoBean = new DevinfoBean();
		try {
			result = myDevinfoBean.updateDevinfo(st, devno);
		} catch (Exception e) {
			System.out.println("ee=" + e);
			result = false;
		}

		int devindex = indexOfDevList(devno);
		Devinfo devobj = getDevFromList(devno);
		// ��������豸�ɹ���������豸�б�

		if (result && devobj != null) {
			if (devobj.equals(mydevlist.set(devindex, st)))
				result1 = true;
			else
				result1 = false;
		}

		if (!result)
			ret = -1;
		else if (!result1)
			ret = 1;
		else
			ret = 0;

		return ret;

	}

	/**
	 * <p>
	 * �����ؽ��豸�б���
	 * </p>
	 * 
	 * @return boolean �ؽ��Ƿ�ɹ�
	 * @throws HibernateException
	 */
	public static boolean resetNow() throws HibernateException {
		try {
			DevinfoBean myDevinfoBean = new DevinfoBean();
			mydevlist = myDevinfoBean.getAllDevList();
			HibernateUtil.closeSession();
			len = mydevlist.size();
			resetflag = 0;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * ɾ��ָ�����͵��豸
	 * 
	 * @param typeno
	 *            �豸���ͱ�� return boolean
	 * @throws HibernateException
	 */
	public static boolean deleteDevByType(String typeno) throws HibernateException {
		boolean result = false;
		try {
			result = DevinfoBean.deleteDevByType(typeno);
		} catch (Exception e) {
			result = false;
		}

		if (result == true) {
			resetNow();
		}

		return result;
	}
}
