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
 * <b>Description:</b> 和表zzt_bankinfo相关的业务模型
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author 杨旭
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
	 * 重置机构信息列表标志，用于对zzt_bankinfo写操作后<br>
	 * 目的：下次创建实例时重新加载bankinfolist
	 */
	public static void reset() {
		resetflag = 1;
	}

	/**
	 * <p>
	 * 获取机构信息列表
	 * </p>
	 * 
	 * @return List
	 */
	public List<Bankinfo> getBankinfoList() throws HibernateException {
		myobjlist = initBankinfolist();// 改为直接读数据库 xucc 20090625
		return myobjlist;
	}

	/**
	 * <p>
	 * 获取机构信息在当前机构信息列表缓存中的位置
	 * </p>
	 * 
	 * @param bankid
	 *            机构编号
	 * @return int 机构信息在当前机构信息列表缓存中的位置(-1:该机构信息不存在于其中)
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
	 * 从当前机构列表缓存中获取机构信息实例
	 * @param bankid
	 *            机构编号
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
	 * 添加机构信息到数据库并更新机构信息列表
	 * </p>
	 * 
	 * @param st
	 *            机构信息对象
	 * @return int -1:添加机构信息失败 0:添加机构信息成功 1:添加机构信息成功但是更新机构信息列表失败，需要重建机构信息列表缓存
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
		// 如果添加机构信息到数据库成功，则更新机构信息列表
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
	 * 删除指定机构编号的机构信息
	 * </p>
	 * 
	 * @param bankid
	 *            机构编号
	 * @return int -1:删除机构信息失败 0:删除机构信息成功 1:删除机构信息成功但是更新机构信息列表失败，需要重建机构信息列表缓存
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
		// 如果删除机构成功，则更新机构信息列表
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
	 * 更新指定机构编号的机构信息
	 * </p>
	 * 
	 * @param st
	 *            机构信息对象
	 * @param bankid
	 *            机构编号
	 * @return int -1:更新机构信息失败 0:更新机构信息成功 1:更新机构信息成功但是更新机构信息列表失败，需要重建机构信息列表缓存
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
		// 如果更新机构成功，则更新机构信息列表

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
	 * 立即重建机构信息列表缓存
	 * </p>
	 * 
	 * @return boolean 重建是否成功
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
	 * 根据机构编号获取机构范围
	 * </p>
	 * 
	 * @param bankid
	 *            机构编号
	 * @return String 机构范围（编号）<br>
	 *         如果是A结尾表示后面所有位通配；<br>
	 *         如果输入机构是最低级别机构则直接返回该机构编号。
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
	 * 根据机构编号获取下属机构列表
	 * </p>
	 * 
	 * @param bankid
	 *            机构编号
	 * @param flag
	 *            是否包含当前机构 1-包含 其它-不包含
	 * @return Vector 下属机构信息<br>
	 *         bankid为空返回null<br>
	 *         bankid没有下属机构返回null
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
	 * 根据机构编号获取下属机构列表
	 * </p>
	 * 
	 * @param bankid
	 *            机构编号
	 * @return Vector 下属机构信息<br>
	 *         bankid为空返回null<br>
	 *         bankid没有下属机构返回null
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
	 * 根据机构编号获取下属机构列表
	 * </p>
	 * 
	 * @param bankid
	 *            机构编号
	 * @param banktag
	 *            机构级别
	 * @return Vector 下属机构信息<br>
	 *         bankid和banktag为空返回null<br>
	 *         bankid没有下属机构返回null
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

		// 算出机构范围标示
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

		// 循环取出当前机构子机构
		int iBankflag = myBankflag.length();
		int itmp0 = tmp0.length();
		Vector<Bankinfo> myVector2 = new Vector<Bankinfo>();

		for (int i = 0; i < myBankList.size(); i++) {
			myBankinfo = (Bankinfo) myBankList.get(i);
			myBankid = myBankinfo.getBankid().trim();

			// 判断是否当前机构
			if (myBankid.equals(bankid)) {
				myVector.add(myBankinfo);

				// 判断是否当前机构子机构
			} else if (myBankid.substring(0, iBankflag).equals(myBankflag)
					&& myBankid.substring(Constants.INT_BANKID_LEN - itmp0)
							.equals(tmp0)) {
				myVector.add(myBankinfo);

				// 判断是否还有子机构
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
