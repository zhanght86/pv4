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
 * <b>Description:</b> 和表zzt_devinfo相关的业务模型
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
public class DevinfoModel {

	private static List<Devinfo> mydevlist = null;
	private static int len = 0;
	private static int resetflag = 0;

	public DevinfoModel() throws HibernateException {
		/*
		 * if (mydevlist == null) { rebiuld(); } else if (resetflag == 1) {
		 * mydevlist.clear(); rebiuld(); resetflag = 0; }
		 */
		rebiuld();// 改为直接调用rebuild，不判断状态 xucc 20100105
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
	 * 重置设备信息列表标志，用于对zzt_devinfo写操作后
	 * </p>
	 * <p>
	 * 目的：下次创建实例时重新加载devlist
	 * </p>
	 */
	public static void reset() {
		resetflag = 1;
	}

	/**
	 * <p>
	 * 获取设备信息列表
	 * </p>
	 * 
	 * @return List
	 */
	public List<Devinfo> getDevList() throws HibernateException {
		// this.mydevlist = initDevlist();//改为直接读数据库 xucc 20090625
		resetNow();// 改为直接调用重建缓存 xucc 20100105
		return mydevlist;
	}

	/**
	 * <p>
	 * 获取指定机构编号的设备信息列表
	 * </p>
	 * 
	 * @param mbankid
	 *            机构编号（如果包含字母A，表示这以后若干位匹配任意字符）
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
			// wukp 111124 没有A
			if (allpos == -1) {
				for (int i = 0; i < len; i++) {
					Devinfo temp = (Devinfo) mydevlist.get(i);
					if (mbankid.trim().equals(myPubUtil.dealNull(temp.getBankid()).trim())) {
						myVector.add(temp);
					}
				}
			} else {// wukp 111124 有A
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
	 * 获取设备在当前设备列表缓存中的位置
	 * </p>
	 * 
	 * @param devno
	 *            设备编号
	 * @return int 设备在当前设备列表缓存中的位置(-1:该设备不存在于其中)
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
	 * 从当前设备列表缓存中获取设备实例
	 * </p>
	 * 
	 * @param devno
	 *            设备编号
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
	 * 添加设备到数据库并更新设备列表
	 * </p>
	 * 
	 * @param st
	 *            设备信息对象
	 * @return int -1:添加设备失败 0:添加设备成功 1:添加设备成功但是更新设备列表失败，需要重建设备列表缓存
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
		// 如果添加设备到数据库成功，则更新设备列表
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
	 * 删除指定设备编号的设备
	 * </p>
	 * 
	 * @param devno
	 *            设备编号
	 * @return int -1:删除设备失败 0:删除设备成功 1:删除设备成功但是更新设备列表失败，需要重建设备列表缓存
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
		// 如果删除设备成功，则更新设备列表
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
	 * 更新指定设备编号的设备信息
	 * </p>
	 * 
	 * @param st
	 *            设备信息对象
	 * @param devno
	 *            设备编号
	 * @return int -1:更新设备失败 0:更新设备成功 1:更新设备成功但是更新设备列表失败，需要重建设备列表缓存
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
		// 如果更新设备成功，则更新设备列表

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
	 * 立即重建设备列表缓存
	 * </p>
	 * 
	 * @return boolean 重建是否成功
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
	 * 删除指定类型的设备
	 * 
	 * @param typeno
	 *            设备类型编号 return boolean
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
