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
 * <b>Description:</b> 和表zzt_stamission相关的业务逻辑
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
 * @author 吴学坤
 * @version 1.0 2005/03/28
 */
public class StaMissionModel {
	public StaMissionModel() throws HibernateException {
	}

	// private static Logger log = Logger.getLogger("wuxk");

	/**
	 * 返回指定机构的统计任务表列表
	 * 
	 * @author 吴学坤
	 * @param String
	 *            bankid 机构ID
	 * @param int firstRow 返回的第一条记录
	 * @param int maxResults 最多返回的记录数
	 * @return 指定机构的统计任务表列表
	 */
	public static List getAllStaMission(Vector bankids, String statesort, int firstRow, int maxResults) throws HibernateException {
		List result = StaMissionBean.getAllStaMission(bankids, statesort, firstRow, maxResults);
		HibernateUtil.closeSession();
		return result;
	}

	/**
	 * 返回指定机构的统计任务表中指定状态的列表
	 * 
	 * @author 吴学坤
	 * @param String
	 *            bankid 机构ID
	 * @param String
	 *            currentflag 任务状态
	 * @param int firstRow 返回的第一条记录
	 * @param int maxResults 最多返回的记录数
	 * @return 指定机构的统计任务表列表
	 */
	public static List getStaMission(Vector bankids, String currentflag, String statesort, int firstRow, int maxResults) throws HibernateException {
		List result = StaMissionBean.getStaMission(bankids, currentflag, statesort, firstRow, maxResults);
		HibernateUtil.closeSession();
		return result;
	}

	/**
	 * 返回指定机构的统计任务表列表的总数
	 * 
	 * @author 吴学坤
	 * @param Vector
	 *            bankids 机构ID
	 * @return 指定机构的统计任务表列表总数
	 */
	public static int getAllStaMissionCount(Vector bankids, String statesort) throws HibernateException {
		int count = StaMissionBean.getAllStaMissionCount(bankids, statesort);
		HibernateUtil.closeSession();
		return count;
	}

	/**
	 * 返回指定机构的统计任务表中指定状态的列表总数
	 * 
	 * @author 吴学坤
	 * @param Vector
	 *            bankids 机构ID
	 * @param String
	 *            currentflag 任务状态
	 * @return 指定机构的统计任务表列表总数
	 */
	public static int getStaMissionCount(Vector bankids, String currentflag, String statesort) throws HibernateException {
		int count = StaMissionBean.getStaMissionCount(bankids, currentflag, statesort);
		HibernateUtil.closeSession();
		return count;
	}

	/**
	 * 删除指定的报表
	 * 
	 * @author 吴学坤
	 * @param String
	 *            [] statename 要删除的报表名数组
	 * @return 删除的报表数
	 */
	public static boolean delStaMission(String fileName) throws HibernateException {
		boolean result = StaMissionBean.delStaMission(fileName);
		HibernateUtil.closeSession();
		return result;
	}

	/**
	 * <p>
	 * 添加一个StaMission对象到数据库
	 * </p>
	 * 
	 * @param st
	 *            StaMission对象
	 * 
	 * @return int -1:添加失败 0:添加成功
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
	 * 根据统计开始时间、报表分类、报表产生类型<br>
	 * 取出系统任务表
	 * </p>
	 * 
	 * @param timeid
	 *            统计开始时间
	 * @param statesort
	 *            报表分类
	 * @param createtype
	 *            报表产生类型
	 * @param ownerid
	 *            创建者
	 * 
	 * @return StaMission 返回StaMission对象
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
	 * 更新StaMission记录
	 * </p>
	 * 
	 * @param st
	 *            StaMission对象
	 * 
	 * @return int -1:更新失败 0:更新成功
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
