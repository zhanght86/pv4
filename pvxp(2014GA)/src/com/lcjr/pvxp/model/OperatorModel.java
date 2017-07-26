package com.lcjr.pvxp.model;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.util.MD5;
import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.bean.*;
import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 和表zzt_operator相关的业务模型
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
 * @version 1.0 2004/12/14
 */
public class OperatorModel {
	private static List myoplist = null;
	Logger log = Logger.getLogger("web.com.lcjr.pvxp.model.OperatorModel.java");
	
	
	public OperatorModel() throws HibernateException {
	}
	
	
	
	/**
	 * <p>
	 * 获取操作员信息列表
	 * </p>
	 * 
	 * @return List
	 * @throws HibernateException
	 */
	public List getOperList() throws HibernateException {
		// if(myoplist==null){//改为直接读数据库 xucc 20090625
		
		OperatorBean myOperatorBean = new OperatorBean();
		myoplist = myOperatorBean.getAllOperList();
		HibernateUtil.closeSession();
		// }
		return myoplist;
	}
	
	
	
	/**
	 * <p>
	 * 获取指定机构编号的操作员信息列表 xucc 20110304
	 * </p>
	 * 
	 * @param mbankid
	 *            机构编号（如果包含字母A，表示这以后若干位匹配任意字符）
	 * @return List
	 */
	public List getOperListOfBank(String mbankid) {
		
		Vector myVector = new Vector();
		PubUtil myPubUtil = new PubUtil();
		
		try {
			List oplist = getOperList();
			if (oplist == null || mbankid == null)
				return null;
			int len = oplist.size();
			int allpos = mbankid.indexOf("A");
			if (allpos == -1) {
				for (int i = 0; i < len; i++) {
					Operator temp = (Operator) oplist.get(i);
					if (mbankid.trim().equals(myPubUtil.dealNull(temp.getBankid()).trim())) {
						myVector.add(temp);
					}
				}
			} else {
				for (int i = 0; i < len; i++) {
					Operator temp = (Operator) oplist.get(i);
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
	 * 从当前给定操作员列表中获取操作员信息实例
	 * </p>
	 * 
	 * @param operid
	 *            操作员编号
	 * @param oplist
	 *            操作员列表
	 * @return Operator
	 */
	public static Operator getOperFromList(String operid, List oplist) {
		if (oplist == null || operid == null)
			return null;
		int len = oplist.size();
		Operator reOperator = null;
		// 从操作员列表中寻找operid的那一条信息，并返回
		for (int i = 0; i < len; i++) {
			Operator temp = (Operator) oplist.get(i);
			if (operid.trim().equals(temp.getOperid().trim())) {
				reOperator = temp;
				break;
			}
		}
		return reOperator;
	}
	
	
	
	/**
	 * <p>
	 * 添加操作员到数据库
	 * </p>
	 * 
	 * @param st
	 *            操作员信息对象
	 * @return int -1:添加操作员失败 0:添加操作员成功
	 * @throws HibernateException
	 */
	public static int addOper(Operator st) throws HibernateException {
		boolean result = false;
		
		int ret = -1;
		OperatorBean myOperatorBean = new OperatorBean();
		try {
			result = myOperatorBean.addOperator(st);
		} catch (Exception e) {
			result = false;
		}
		if (result)
			ret = 0;
		
		return ret;
	}
	
	
	
	/**
	 * <p>
	 * 删除指定操作员编号的操作员
	 * </p>
	 * 
	 * @param operid
	 *            操作员编号
	 * @return int -1:删除操作员失败 0:删除操作员成功
	 * @throws HibernateException
	 */
	public static int deleteOper(String operid) throws HibernateException {
		boolean result = false;
		
		int ret = -1;
		OperatorBean myOperatorBean = new OperatorBean();
		try {
			result = myOperatorBean.deleteOperator(operid);
		} catch (Exception e) {
			result = false;
		}
		
		if (result)
			ret = 0;
		
		return ret;
	}
	
	
	
	/**
	 * <p>
	 * 更新指定操作员编号的操作员信息
	 * </p>
	 * 
	 * @param st
	 *            操作员信息对象
	 * @param operid
	 *            操作员编号
	 * @return int -1:更新操作员失败 0:更新操作员成功
	 * @throws HibernateException
	 */
	public static int updateOper(Operator st, String operid) throws HibernateException {
		boolean result = false;
		int ret = -1;
		OperatorBean myOperatorBean = new OperatorBean();
		try {
			result = myOperatorBean.updateOperator(st, operid);
		} catch (Exception e) {
			result = false;
		}
		if (result)
			ret = 0;
		
		return ret;
		
	}
	
	
	
	/**
	 * <p>
	 * 验证操作员密码
	 * </p>
	 * 
	 * @param operid
	 *            操作员编号
	 * @param password
	 *            操作员密码（加密前）
	 * @return Operator 如果验证失败返回null
	 * @throws HibernateException
	 */
	public Operator validateOper(String operid, String password) throws HibernateException {
		Operator myOperator = null;
		try {
			OperatorBean myOperatorBean = new OperatorBean();
			Operator tmp = myOperatorBean.getOperator(operid);
			if (tmp == null)
				return null;
			MD5 mymd5 = new MD5();
			// 数据库中的密码是加密过的，只有将输入的密码按照统一加密算法进行加密，才能跟从数据库中的密码进行比较
			if ((mymd5.getMD5ofStr(password)).equals((tmp.getPassword()).trim())) {
				myOperator = (Operator) tmp.clone();// 为什么要用克隆
			}
			HibernateUtil.closeSession();
		} catch (Exception e) {
			log.error("validateOper()", e);
			return null;
		}
		
		return myOperator;
		
	}
	
	/**
	 * <p>
	 * 修改操作员密码
	 * </p>
	 * 
	 * @param operid
	 *            操作员编号
	 * @param password
	 *            操作员密码（加密前）
	 * @return int -1:密码错误,0:更改成功,1:更改失败
	 * @throws HibernateException
	 */
	
	public int changePasswd(String operid, String password, String newPasswd) throws HibernateException {
		try {
			Operator myOperator = validateOper(operid, password);
			if (myOperator == null)
				return -1;
			MD5 mymd5 = new MD5();
			newPasswd = mymd5.getMD5ofStr(newPasswd);
			PubUtil myPubUtil = new PubUtil();
			myOperator.setName(myPubUtil.dealNull(myOperator.getName()).trim());
			myOperator.setPassword(newPasswd);
			if (updateOper(myOperator, operid) == 0)
				return 0;
			else
				return 1;
		} catch (Exception e) {
			return 1;
		}
	}
	
	
	
	/**
	 * <p>
	 * 获取当前操作员对某功能的权限
	 * </p>
	 * 
	 * @param funcid
	 *            功能ID号（在authlist中的位置）
	 * @param request
	 *            HttpServletRequest对象
	 * @return int 权限值 0-没有任何权限 1-仅浏览权限 2-浏览和写操作权限 3-系统管理员
	 */
	public int getPower(int funcid, HttpServletRequest request) {
		try {
			PubUtil myPubUtil = new PubUtil();
			String alist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (alist.equals("*")) {
				return 3;
			} else if (alist.length() < funcid + 1) {// 功能ID号的数值大于权限数字串的长度，返回没有任何权限
				return 0;
			} else {
				return Integer.parseInt(alist.substring(funcid, funcid + 1));// 返回权限数字串上funcid和funcid+1位置上的数值
			}
		} catch (Exception e) {
			return 0;
		}
	}
	
	
	
	/**
	 * 按操作员编号获得Operator持久对象
	 * 
	 * @param operid
	 *            操作员编号
	 * @return Operator
	 * @throws HibernateException
	 */
	public Operator getOperator(String operid) throws HibernateException {
		OperatorBean myOperatorBean = new OperatorBean();
		Operator tmp = myOperatorBean.getOperator(operid);
		HibernateUtil.closeSession();
		return tmp;
	}
	
	/**
	 * <p>
	 * 判断当前操作员是否为系统管理员
	 * </p>
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return boolean
	 */
	public boolean isSuperOP(HttpServletRequest request) {
		try {
			PubUtil myPubUtil = new PubUtil();
			String alist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (alist.equals("*")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
	/**
	 * <p>
	 * 判断当前操作员是否为指定插件的用户
	 * </p>
	 * 
	 * @param plugid
	 *            插件ID
	 * @param request
	 *            HttpServletRequest对象
	 * @return boolean
	 */
	public boolean isPluginUser(String plugid, HttpServletRequest request) {
		try {
			PubUtil myPubUtil = new PubUtil();
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			if (isSuperOP(request)) {
				return true;
			} else {
				PluginModel myPluginModel = new PluginModel();
				
				Plugin myPlugin = myPluginModel.getPlugin(plugid);
				
				String mytype = myPubUtil.dealNull(myPlugin.getPlugtype()).trim();
				if (mytype.equals("1")) { // 系统插件
					return false;
				} else if (mytype.equals("2")) { // 开放插件
					return true;
				} else if (mytype.equals("3")) { // 认证插件
					String userslist = myPubUtil.dealNull(myPlugin.getUserslist()).trim();
					if (userslist.indexOf("," + operid + ",") == -1) {
						return false;
					} else {
						return true;
					}
				} else {
					return false;
				}
				
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * <p>
	 * 判断当前操作员是否为已经登录插件的用户
	 * </p>
	 * 
	 * @param plugid
	 *            插件ID
	 * @param request
	 *            HttpServletRequest对象
	 * @return boolean
	 */
	public boolean isPluginUserIn(String plugid, HttpServletRequest request) {
		try {
			PubUtil myPubUtil = new PubUtil();
			String tmpstr = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_USEROF_PLUGIN + plugid, request)).trim();
			if (tmpstr.equals("yes")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * <p>
	 * 重置密码过期日
	 * </p>
	 * 
	 * @param operid
	 *            操作员编号
	 * @return boolean
	 */
	public boolean resetPassWDvalid(String operid) {
		try {
			
			Operator myOperator = getOperator(operid);
			if (myOperator == null)
				return false;
			
			PubUtil myPubUtil = new PubUtil();
			
			String passWDvalid = myPubUtil.dealNull(myPubUtil.ReadConfig("Users", "PassWDvalid", "7", "PowerView.ini")).trim();
			
			String newDate = myPubUtil.getOtherDate(Integer.parseInt(passWDvalid));
			myOperator.setAdddate(newDate);
			myOperator.setName(myPubUtil.dealNull(myOperator.getName()).trim());
			if (updateOper(myOperator, operid) == 0)
				return true;
			else
				return false;
			
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * <p>
	 * 重置操作员状态
	 * </p>
	 * 
	 * @param operid
	 *            操作员编号
	 * @return boolean
	 */
	public boolean resetState(String operid, String state) {
		try {
			PubUtil myPubUtil = new PubUtil();
			Operator myOperator = getOperator(operid);
			if (myOperator == null)
				return false;
			myOperator.setState(state);
			myOperator.setName(myPubUtil.dealNull(myOperator.getName()).trim());
			if (updateOper(myOperator, operid) == 0)
				return true;
			else
				return false;
			
		} catch (Exception e) {
			return false;
		}
	}
}
