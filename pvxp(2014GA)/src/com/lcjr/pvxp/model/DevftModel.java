package com.lcjr.pvxp.model;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Devftinfo;
import com.lcjr.pvxp.bean.DevftBean;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 和表zzt_devftinfo相关的业务模型</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2010</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2010/09/26
 */
public class DevftModel
{
	private static List mydevftlist = null;
	private static int len = 0;
	private static int resetflag = 0;
	
	public DevftModel()throws HibernateException{
		if( mydevftlist == null ){
			rebiuld();
		}else if( resetflag == 1 ){
			mydevftlist.clear();
			rebiuld();
			resetflag = 0;
		}
	}
	
	private static List initDevftlist()throws HibernateException{
		DevftBean myDevftBean = new DevftBean();
		List mydevftlist1 = myDevftBean.getAllDevftList();
		HibernateUtil.closeSession();
		return mydevftlist1;
	}
	
	private void setLen(){
		this.len = this.mydevftlist.size();
	}
	
	private void rebiuld()throws HibernateException{
		this.mydevftlist = initDevftlist();
		setLen();
	}
	/**
	 * 重置设备厂商信息列表标志，用于对zzt_devftinfo写操作后<br>
	 * 目的：下次创建实例时重新加载devftlist
	 */
	public static void reset(){
			resetflag = 1;
	 }
	/**
	 * <p>获取设备厂商列表</p>
	 * 
	 * @return List
	 */
	public List getDevftList(){
		return mydevftlist;
	}
	/**
	 * <p>获取设备厂商在当前设备厂商列表缓存中的位置</p>
	 * 
	 * @param devftno 设备厂商编号
	 * @return int 设备厂商在当前设备厂商列表缓存中的位置(-1:该设备厂商不存在于其中)
	 */
	public static int indexOfDevftList(String devftno){
		if(mydevftlist==null||devftno==null) return -1;
		int len = mydevftlist.size();
		int index = -1;
		for( int i=0;i<len;i++ ){
			Devftinfo temp=(Devftinfo)mydevftlist.get(i);
			if( devftno.trim().equals(temp.getDevftno().trim()) ){
				index = i;
				break;
			}
		}
		return index;
	}
	
	/**
	 * <p>从当前设备厂商列表缓存中获取设备厂商实例</p>
	 * 
	 * @param devftno 设备厂商编号
	 * @return Devftinfo
	 */
	public static Devftinfo getDevftFromList(String devftno){
		if(mydevftlist==null||devftno==null) return null;
		int len = mydevftlist.size();
		Devftinfo reDevft = null;
		for( int i=0;i<len;i++ ){
			Devftinfo temp=(Devftinfo)mydevftlist.get(i);
			if( devftno.trim().equals(temp.getDevftno().trim()) ){
				reDevft = temp;
				break;
			}
		}
		return reDevft;
	}
		
	/**
	 * <p>添加设备厂商到数据库并更新设备厂商列表</p>
	 * 
	 * @param st 设备厂商对象
	 * @return int -1:添加设备厂商失败 0:添加设备厂商成功 1:添加设备厂商成功但是更新设备厂商列表失败，需要重建设备厂商列表缓存
	 * @throws HibernateException
	 */
	public static int addDevft(Devftinfo st)throws HibernateException{
		boolean result = false;
		boolean result1 = false;
		int ret = 0;
		DevftBean myDevftBean = new DevftBean();
		try{
			result = myDevftBean.addDevft(st);
		}catch(Exception e){
			result = false;
		}
		//如果添加设备厂商到数据库成功，则更新设备厂商列表
		if( result ) result1 = mydevftlist.add(st);
		if( !result ) ret = -1;
		else if( !result1 ) ret = 1;
		else ret = 0;
		
		return ret;
	}
	
	
	/**
	 * <p>删除指定设备厂商编号的设备</p>
	 * 
	 * @param devftno 设备厂商编号
	 * @return int -1:删除设备厂商失败 0:删除设备厂商成功 1:删除设备厂商成功但是更新设备厂商列表失败，需要重建设备厂商列表缓存
	 * @throws HibernateException
	 */
	public static int deleteDevft(String devftno)throws HibernateException{
		boolean result = false;
		boolean result1 = false;
		int ret = 0;
		DevftBean myDevftBean = new DevftBean();
		try{
			result = myDevftBean.deleteDevft(devftno);
		}catch(Exception e){
			result = false;
		}
		
		
		Devftinfo devftobj = getDevftFromList(devftno);
		//如果删除设备厂商成功，则更新设备厂商列表
		if( result&&devftobj!=null ) result1 = mydevftlist.remove(devftobj);

		
		if( !result ) ret = -1;
		else if( !result1 ) ret = 1;
		else ret = 0;
		
		return ret;
	}
	
	/**
	 * <p>更新指定设备厂商编号的设备厂商信息</p>
	 * 
	 * @param st 设备厂商对象
	 * @param devftno 设备厂商编号
	 * @return int -1:更新设备厂商失败 0:更新设备厂商成功 1:更新设备厂商成功但是更新设备厂商列表失败，需要重建设备厂商列表缓存
	 * @throws HibernateException
	 */
	public static int updateDevft(Devftinfo st , String devftno)throws HibernateException{
		boolean result = false;
		boolean result1 = false;
		int ret = 0;
		DevftBean myDevftBean = new DevftBean();
		try{
			result = myDevftBean.updateDevft(st,devftno);
		}catch(Exception e){
			result = false;
		}
		
		int devftindex = indexOfDevftList(devftno);
		Devftinfo devftobj = getDevftFromList(devftno);
		//如果更新设备厂商成功，则更新设备厂商列表

		if( result&&devftobj!=null ){
			if( devftobj.equals(mydevftlist.set(devftindex,st)) ) result1 = true;
			else result1 = false;
		}

		
		if( !result ) ret = -1;
		else if( !result1 ) ret = 1;
		else ret = 0;
		
		return ret;
		
	}
	/**
	 * <p>立即重建设备厂商列表缓存</p>
	 * 
	 * @return boolean 重建是否成功
	 * @throws HibernateException
	 */
	public static boolean resetNow()throws HibernateException{
		try{
			DevftBean myDevftBean = new DevftBean();
			mydevftlist = myDevftBean.getAllDevftList();
			HibernateUtil.closeSession();
			len = mydevftlist.size();
			resetflag = 0;
		}catch(Exception e){
			return false;	
		}
		return true;
	}
}
