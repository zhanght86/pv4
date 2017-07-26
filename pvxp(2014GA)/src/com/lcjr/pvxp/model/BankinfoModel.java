package com.lcjr.pvxp.model;

import java.util.List;
import java.util.Vector;

import net.sf.hibernate.HibernateException;

import com.lcjr.pvxp.bean.BankinfoBean;
import com.lcjr.pvxp.orm.Bankinfo;
import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.util.Constants;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> �ͱ�zzt_bankinfo��ص�ҵ��ģ��
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
public class BankinfoModel {
	private static List<Bankinfo> myobjlist = null;

	private static int len = 0;

	private static int resetflag = 0;

	public BankinfoModel() throws HibernateException {
		if (myobjlist == null) {
			rebiuld();
		} else if (resetflag == 1) {
			myobjlist.clear();
			rebiuld();
			resetflag = 0;
		}
	}

	private static List<Bankinfo> initBankinfolist() throws HibernateException {
		BankinfoBean myBankinfoBean = new BankinfoBean();
		List<Bankinfo> myobjlist1 = myBankinfoBean.getAllBankinfoList();
		HibernateUtil.closeSession();
		return myobjlist1;
	}

	private void setLen() {
		len = myobjlist.size();
	}

	private void rebiuld() throws HibernateException {
		myobjlist = initBankinfolist();
		setLen();
	}

	/**
	 * ���û�����Ϣ�б��־�����ڶ�zzt_bankinfoд������<br>
	 * Ŀ�ģ��´δ���ʵ��ʱ���¼���bankinfolist
	 */
	public static void reset() {
		resetflag = 1;
	}

	/**
	 * <p>
	 * ��ȡ������Ϣ�б�
	 * </p>
	 * 
	 * @return List
	 */
	public List<Bankinfo> getBankinfoList() throws HibernateException {
		myobjlist = initBankinfolist();// ��Ϊֱ�Ӷ����ݿ� xucc 20090625
		return myobjlist;
	}

	/**
	 * <p>
	 * ��ȡ������Ϣ�ڵ�ǰ������Ϣ�б����е�λ��
	 * </p>
	 * 
	 * @param bankid
	 *            �������
	 * @return int ������Ϣ�ڵ�ǰ������Ϣ�б����е�λ��(-1:�û�����Ϣ������������)
	 */
	public static int indexOfBankinfoList(String bankid) {
		if (myobjlist == null || bankid == null)
			return -1;
		int len = myobjlist.size();
		int index = -1;
		for (int i = 0; i < len; i++) {
			Bankinfo temp = (Bankinfo) myobjlist.get(i);
			if (bankid.trim().equals(temp.getBankid().trim())) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * �ӵ�ǰ�����б����л�ȡ������Ϣʵ��
	 * @param bankid
	 *            �������
	 * @return Bankinfo
	 */
	public static Bankinfo getBankinfoFromList(String bankid) {
		if (myobjlist == null || bankid == null)
			return null;
		int len = myobjlist.size();
		Bankinfo reBankinfo = null;
		for (int i = 0; i < len; i++) {
			Bankinfo temp = (Bankinfo) myobjlist.get(i);
			if (bankid.trim().equals(temp.getBankid().trim())) {
				reBankinfo = temp;
				break;
			}
		}
		return reBankinfo;
	}

	/**
	 * <p>
	 * ��ӻ�����Ϣ�����ݿⲢ���»�����Ϣ�б�
	 * </p>
	 * 
	 * @param st
	 *            ������Ϣ����
	 * @return int -1:��ӻ�����Ϣʧ�� 0:��ӻ�����Ϣ�ɹ� 1:��ӻ�����Ϣ�ɹ����Ǹ��»�����Ϣ�б�ʧ�ܣ���Ҫ�ؽ�������Ϣ�б���
	 * @throws HibernateException
	 */
	public static int addBank(Bankinfo st) throws HibernateException {
		boolean result = false;
		boolean result1 = false;
		int ret = 0;
		BankinfoBean myBankinfoBean = new BankinfoBean();
		try {
			result = myBankinfoBean.addBankinfo(st);
		} catch (Exception e) {
			result = false;
		}
		// �����ӻ�����Ϣ�����ݿ�ɹ�������»�����Ϣ�б�
		if (result)
			result1 = myobjlist.add(st);
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
	 * ɾ��ָ��������ŵĻ�����Ϣ
	 * </p>
	 * 
	 * @param bankid
	 *            �������
	 * @return int -1:ɾ��������Ϣʧ�� 0:ɾ��������Ϣ�ɹ� 1:ɾ��������Ϣ�ɹ����Ǹ��»�����Ϣ�б�ʧ�ܣ���Ҫ�ؽ�������Ϣ�б���
	 * @throws HibernateException
	 */
	public static int deleteBank(String bankid) throws HibernateException {
		boolean result = false;
		boolean result1 = false;
		int ret = 0;
		BankinfoBean myBankinfoBean = new BankinfoBean();
		try {
			result = myBankinfoBean.deleteBankinfo(bankid);
		} catch (Exception e) {
			result = false;
		}

		Bankinfo oldobj = getBankinfoFromList(bankid);
		// ���ɾ�������ɹ�������»�����Ϣ�б�
		if (result && oldobj != null)
			result1 = myobjlist.remove(oldobj);

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
	 * ����ָ��������ŵĻ�����Ϣ
	 * </p>
	 * 
	 * @param st
	 *            ������Ϣ����
	 * @param bankid
	 *            �������
	 * @return int -1:���»�����Ϣʧ�� 0:���»�����Ϣ�ɹ� 1:���»�����Ϣ�ɹ����Ǹ��»�����Ϣ�б�ʧ�ܣ���Ҫ�ؽ�������Ϣ�б���
	 * @throws HibernateException
	 */
	public static int updateBank(Bankinfo st, String bankid)
			throws HibernateException {
		boolean result = false;
		boolean result1 = false;
		int ret = 0;
		BankinfoBean myBankinfoBean = new BankinfoBean();
		try {
			result = myBankinfoBean.updateBankinfo(st, bankid);
		} catch (Exception e) {
			result = false;
		}

		int devtpindex = indexOfBankinfoList(bankid);
		Bankinfo oldobj = getBankinfoFromList(bankid);
		// ������»����ɹ�������»�����Ϣ�б�

		if (result && oldobj != null) {
			if (oldobj.equals(myobjlist.set(devtpindex, st)))
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
	 * �����ؽ�������Ϣ�б���
	 * </p>
	 * 
	 * @return boolean �ؽ��Ƿ�ɹ�
	 * @throws HibernateException
	 */
	public static boolean resetNow() throws HibernateException {
		try {
			BankinfoBean myBankinfoBean = new BankinfoBean();
			myobjlist = myBankinfoBean.getAllBankinfoList();
			HibernateUtil.closeSession();
			len = myobjlist.size();
			resetflag = 0;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * ���ݻ�����Ż�ȡ������Χ
	 * </p>
	 * 
	 * @param bankid
	 *            �������
	 * @return String ������Χ����ţ�<br>
	 *         �����A��β��ʾ��������λͨ�䣻<br>
	 *         ��������������ͼ��������ֱ�ӷ��ظû�����š�
	 */
	public String getBankRange(String bankid) {
		if (bankid == null || (bankid.trim()).length() == 0) {
			return "A";
		}
		try {
			if (Integer.parseInt(bankid) == 0)
				return "A";
		} catch (Exception e) {
			return "A";
		}
		String tmp0 = "";
		try {
			for (int i = 0; i < Constants.INT_BANKLEVEL_TOTAL - 1; i++) {
				tmp0 = "";
				for (int j = i + 1; j < Constants.INT_BANKLEVEL_TOTAL; j++) {
					for (int m = 0; m < Constants.INT_BANKLEVEL_LEN[j]; m++) {
						tmp0 = tmp0 + "0";
					}
				}

				if (bankid.substring(Constants.INT_BANKID_LEN - tmp0.length())
						.equals(tmp0)) {
					return bankid.substring(0, Constants.INT_BANKID_LEN
							- tmp0.length())
							+ "A";
				}
			}
		} catch (Exception e) {
			return bankid;
		}

		return bankid;
	}

	/**
	 * <p>
	 * ���ݻ�����Ż�ȡ���������б�
	 * </p>
	 * 
	 * @param bankid
	 *            �������
	 * @param flag
	 *            �Ƿ������ǰ���� 1-���� ����-������
	 * @return Vector ����������Ϣ<br>
	 *         bankidΪ�շ���null<br>
	 *         bankidû��������������null
	 */
	public Vector<Bankinfo> getSubBank(String bankid, int flag) {
		Vector<Bankinfo> tmpVector = null;
		Bankinfo tmp = null;
		try {
			if (Integer.parseInt(bankid) == 0) {
				return getSubBank("0", "0");
			} else {
				tmp = getBankinfoFromList(bankid);
				if (tmp == null) {
					return null;
				}
				String banktag = tmp.getBanktag();
				tmpVector = getSubBank(bankid, banktag);
				if (tmpVector == null) {
					if (flag == 1) {
						tmpVector = new Vector<Bankinfo>();
						tmpVector.add(0, tmp);
					}
				} else {
					if (flag != 1) {
						tmpVector.remove(0);
					}
				}
				return tmpVector;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <p>
	 * ���ݻ�����Ż�ȡ���������б�
	 * </p>
	 * 
	 * @param bankid
	 *            �������
	 * @return Vector ����������Ϣ<br>
	 *         bankidΪ�շ���null<br>
	 *         bankidû��������������null
	 */
	public Vector<Bankinfo> getSubBank(String bankid) {
		try {
			if (Integer.parseInt(bankid) == 0) {
				return getSubBank("0", "0");
			} else {
				Bankinfo tmp = getBankinfoFromList(bankid);
				if (tmp == null)
					return null;
				String banktag = tmp.getBanktag();
				return getSubBank(bankid, banktag);
			}

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <p>
	 * ���ݻ�����Ż�ȡ���������б�
	 * </p>
	 * 
	 * @param bankid
	 *            �������
	 * @param banktag
	 *            ��������
	 * @return Vector ����������Ϣ<br>
	 *         bankid��banktagΪ�շ���null<br>
	 *         bankidû��������������null
	 */
	public Vector<Bankinfo> getSubBank(String bankid, String banktag)
			throws HibernateException {
		if (bankid == null || bankid.equals("")) {
			return null;
		}
		if (banktag == null || banktag.equals("")) {
			return null;
		}
		bankid = bankid.trim();
		banktag = banktag.trim();

		List<Bankinfo> myBankList = getBankinfoList();
		Bankinfo myBankinfo = new Bankinfo();
		Vector<Bankinfo> myVector = new Vector<Bankinfo>();
		String myBankid = "";
		String myBanktag = "";
		String tmp0 = "";
		String myBankflag = "";

		// ���������Χ��ʾ
		try {
			if (Integer.parseInt(bankid) == 0) {
				myBankflag = "A";
			} else {
				myBankflag = getBankRange(bankid);
			}
		} catch (Exception e) {
			myBankflag = getBankRange(bankid);
		}

		if (myBankflag.indexOf("A") != -1) {
			myBankflag = myBankflag.substring(0, myBankflag.length() - 1);
			for (int i = Integer.parseInt(banktag) + 1; i < Constants.INT_BANKLEVEL_TOTAL; i++) {
				for (int j = 0; j < Constants.INT_BANKLEVEL_LEN[i]; j++) {
					tmp0 = tmp0 + "0";
				}
			}
		} else if (myBankflag.equals(bankid)) {
			return null;
		}

		// ѭ��ȡ����ǰ�����ӻ���
		int iBankflag = myBankflag.length();
		int itmp0 = tmp0.length();
		Vector<Bankinfo> myVector2 = new Vector<Bankinfo>();

		for (int i = 0; i < myBankList.size(); i++) {
			myBankinfo = (Bankinfo) myBankList.get(i);
			myBankid = myBankinfo.getBankid().trim();

			// �ж��Ƿ�ǰ����
			if (myBankid.equals(bankid)) {
				myVector.add(myBankinfo);

				// �ж��Ƿ�ǰ�����ӻ���
			} else if (myBankid.substring(0, iBankflag).equals(myBankflag)
					&& myBankid.substring(Constants.INT_BANKID_LEN - itmp0)
							.equals(tmp0)) {
				myVector.add(myBankinfo);

				// �ж��Ƿ����ӻ���
				if ((Integer.parseInt(banktag) + 1) != Constants.INT_BANKLEVEL_TOTAL) {
					myBanktag = Integer.toString(Integer.parseInt(banktag) + 1);
					myVector2 = getSubBank(myBankid, myBanktag);

					if (myVector2 != null) {
						for (int j = 1; j < myVector2.size(); j++) {
							myVector.add(myVector2.get(j));
						}
					}
				}
			}
		}

		return myVector;
	}

}
