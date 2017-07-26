package com.lcjr.pvxp.model;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.util.MD5;
import com.lcjr.pvxp.orm.Plugin;
import com.lcjr.pvxp.bean.PluginBean;
import com.lcjr.pvxp.util.*;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;
import javax.servlet.http.*;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 和表zzt_pvplugin相关的业务模型</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.0 2005/03/28
 */
public class PluginModel
{

	public PluginModel()throws HibernateException{
		
	}
	

	/**
	 * <p>获取Plugin列表</p>
	 * 
	 * @return List
	 * @throws HibernateException
	 */
	public List getPluginList()throws HibernateException{
		PluginBean myPluginBean = new PluginBean();
		List myplglist = myPluginBean.getAllPluginList();
		HibernateUtil.closeSession();
		return myplglist;
	}
	
	

	/**
	 * <p>添加Plugin到数据库</p>
	 * 
	 * @param st Plugin对象
	 * @return int -1:添加操作员失败 0:添加操作员成功
	 * @throws HibernateException
	 */
	public static int addPlugin(Plugin st)throws HibernateException{
		boolean result = false;

		int ret = -1;
		PluginBean myPluginBean = new PluginBean();
		try{
			result = myPluginBean.addPlugin(st);
		}catch(Exception e){
			result = false;
		}
		if( result ) ret = 0;
		
		return ret;
	}
	
	
	/**
	 * <p>删除指定ID的Plugin</p>
	 * 
	 * @param plugid Plugin编号
	 * @return int -1:删除失败 0:删除成功
	 * @throws HibernateException
	 */
	public static int deletePlugin(String plugid)throws HibernateException{
		boolean result = false;

		int ret = -1;
		PluginBean myPluginBean = new PluginBean();
		try{
			result = myPluginBean.deletePlugin(plugid);
		}catch(Exception e){
			result = false;
		}
		
		if( result ) ret = 0;
		
		return ret;
	}
	
	/**
	 * <p>更新Plugin信息</p>
	 * 
	 * @param st Plugin对象
	 * @return int -1:更新失败 0:更新成功
	 * @throws HibernateException
	 */
	public static int updatePlugin(Plugin st)throws HibernateException{
		boolean result = false;
		int ret = -1;
		PluginBean myPluginBean = new PluginBean();
		try{
			result = myPluginBean.updatePlugin(st);
		}catch(Exception e){
			result = false;
		}
		if( result ) ret = 0;
		
		return ret;
		
	}
	
	/**
	 * 按plugid获得Plugin持久对象
	 * 
	 * @param plugid 操作员编号
	 * @return Plugin
	 * @throws HibernateException
	 */
	public Plugin getPlugin(String plugid)throws HibernateException{
		PluginBean myPluginBean = new PluginBean();
		Plugin tmp = myPluginBean.getPlugin(plugid);
		HibernateUtil.closeSession();
		return tmp;
	}
	/**
	 * 查询系统中所有的Plugin个数<br>
	 * 
	 * @return int
	 * @throws HibernateException
	 */
	public int getCount()throws HibernateException {
		PluginBean myPluginBean = new PluginBean();
		int mycount = myPluginBean.getAllCount();
		HibernateUtil.closeSession();
		return mycount;
	}
	
	/**
	 * 查询系统中符合条件的Plugin个数<br>
	 * 
	 * @param sqlwhere 条件语句
	 * @return int
	 * @throws HibernateException
	 */
	public int getCount(String sqlwhere)throws HibernateException {
		PluginBean myPluginBean = new PluginBean();
		int mycount = myPluginBean.getCount(sqlwhere);
		HibernateUtil.closeSession();
		return mycount;
	}
	/**
	 * 查询系统中部分Plugin信息<br>
	 * 返回的是包含有Plugin对象的List
	 *
	 * @param intstart 起始记录
	 * @param maxlen 	 最多返回多少条
	 *
	 * @return List
	 * @throws HibernateException
	 */
	public List getPlugins(int intstart,int maxlen)throws HibernateException {
		PluginBean myPluginBean = new PluginBean();
		List tmp = myPluginBean.getPluginList(intstart,maxlen);
		HibernateUtil.closeSession();
		return tmp;
	}
	/**
	 * 查询系统中符合条件的部分Plugin信息<br>
	 * 返回的是包含有Plugin对象的List
	 *
	 * @param intstart 起始记录
	 * @param maxlen 	 最多返回多少条
	 *
	 * @return List
	 * @throws HibernateException
	 */
	public List getPlugins(int intstart,int maxlen,String sqlwhere)throws HibernateException {
		PluginBean myPluginBean = new PluginBean();
		List tmp = myPluginBean.getPluginList(intstart,maxlen,sqlwhere);
		HibernateUtil.closeSession();
		return tmp;
	}
}
