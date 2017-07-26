package com.lcjr.pvxp.model;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Devtype;
import com.lcjr.pvxp.bean.DevtypeBean;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 和表zzt_devtype相关的业务模型</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.0 2005/02/25
 */
public class DevtypeModel
{
	private static List mydevtplist = null;
	private static int len = 0;
	private static int resetflag = 0;
	
	public DevtypeModel()throws HibernateException{
		if( mydevtplist == null ){
			rebiuld();
		}else if( resetflag == 1 ){
			mydevtplist.clear();
			rebiuld();
			resetflag = 0;
		}
	}
	
	private static List initDevTplist()throws HibernateException{
		DevtypeBean myDevtypeBean = new DevtypeBean();
		List mydevtplist1 = myDevtypeBean.getAllDevtypeList();
		HibernateUtil.closeSession();
		return mydevtplist1;
	}
	
	private void setLen(){
		this.len = this.mydevtplist.size();
	}
	
	private void rebiuld()throws HibernateException{
		this.mydevtplist = initDevTplist();
		setLen();
	}
	/**
	 * 重置设备类型信息列表标志，用于对zzt_devtype写操作后<br>
	 * 目的：下次创建实例时重新加载devtypelist
	 */
	public static void reset(){
		resetflag = 1;
	 }
	/**
	 * <p>获取设备类型列表</p>
	 * 
	 * @return List
	 */
	public List getDevTpList()throws HibernateException{
		this.mydevtplist = initDevTplist();//改为直接读数据库 xucc 20090625
		return mydevtplist;
	}
	/**
	 * <p>获取设备类型在当前设备类型列表缓存中的位置</p>
	 * 
	 * @param typeno 设备类型编号
	 * @return int 设备类型在当前设备类型列表缓存中的位置(-1:该设备类型不存在于其中)
	 */
	public static int indexOfDevTpList(String typeno){
		if(mydevtplist==null||typeno==null) return -1;
		int len = mydevtplist.size();
		int index = -1;
		for( int i=0;i<len;i++ ){
			Devtype temp=(Devtype)mydevtplist.get(i);
			if( typeno.trim().equals(temp.getTypeno().trim()) ){
				index = i;
				break;
			}
		}
		return index;
	}
	
	/**
	 * <p>从当前设备类型列表缓存中获取设备类型实例</p>
	 * 
	 * @param typeno 设备编号
	 * @return Devtype
	 */
	public static Devtype getDevTpFromList(String typeno){
		if(mydevtplist==null||typeno==null) return null;
		int len = mydevtplist.size();
		Devtype reDevtype = null;
		for( int i=0;i<len;i++ ){
			Devtype temp=(Devtype)mydevtplist.get(i);
			if( typeno.trim().equals(temp.getTypeno().trim()) ){
				reDevtype = temp;
				break;
			}
		}
		return reDevtype;
	}
		
	/**
	 * <p>添加设备类型到数据库并更新设备类型列表</p>
	 * 
	 * @param st 设备类型对象
	 * @return int -1:添加设备类型失败 0:添加设备类型成功 1:添加设备类型成功但是更新设备类型列表失败，需要重建设备类型列表缓存
	 * @throws HibernateException
	 */
	public static int addDevTp(Devtype st)throws HibernateException{
		boolean result = false;
		boolean result1 = false;
		int ret = 0;
		DevtypeBean myDevtypeBean = new DevtypeBean();
		try{
			result = myDevtypeBean.addDevtype(st);
		}catch(Exception e){
			result = false;
		}
		//如果添加设备类型到数据库成功，则更新设备类型列表
		if( result ) result1 = mydevtplist.add(st);
		if( !result ) ret = -1;
		else if( !result1 ) ret = 1;
		else ret = 0;
		
		return ret;
	}
	
	
	/**
	 * <p>删除指定设备类型编号的设备</p>
	 * 
	 * @param typeno 设备类型编号
	 * @return int -1:删除设备类型失败 0:删除设备类型成功 1:删除设备类型成功但是更新设备类型列表失败，需要重建设备类型列表缓存
	 * @throws HibernateException
	 */
	public static int deleteDevTp(String typeno)throws HibernateException{
		boolean result = false;
		boolean result1 = false;
		int ret = 0;
		DevtypeBean myDevtypeBean = new DevtypeBean();
		try{
			result = myDevtypeBean.deleteDevtype(typeno);
		}catch(Exception e){
			result = false;
		}
		
		
		Devtype devtpobj = getDevTpFromList(typeno);
		//如果删除设备成功，则更新设备列表
		if( result&&devtpobj!=null ) result1 = mydevtplist.remove(devtpobj);

		
		if( !result ) ret = -1;
		else if( !result1 ) ret = 1;
		else ret = 0;
		
		return ret;
	}
	
	/**
	 * <p>更新指定设备类型编号的设备类型信息</p>
	 * 
	 * @param st 设备类型对象
	 * @param typeno 设备类型编号
	 * @return int -1:更新设备类型失败 0:更新设备类型成功 1:更新设备类型成功但是更新设备类型列表失败，需要重建设备类型列表缓存
	 * @throws HibernateException
	 */
	public static int updateDevTp(Devtype st , String typeno)throws HibernateException{
		boolean result = false;
		boolean result1 = false;
		int ret = 0;
		DevtypeBean myDevtypeBean = new DevtypeBean();
		try{
			result = myDevtypeBean.updateDevtype(st,typeno);
		}catch(Exception e){
			result = false;
		}
		
		int devtpindex = indexOfDevTpList(typeno);
		Devtype devtpobj = getDevTpFromList(typeno);
		//如果更新设备成功，则更新设备列表

		if( result&&devtpobj!=null ){
			if( devtpobj.equals(mydevtplist.set(devtpindex,st)) ) result1 = true;
			else result1 = false;
		}

		
		if( !result ) ret = -1;
		else if( !result1 ) ret = 1;
		else ret = 0;
		
		return ret;
		
	}
	/**
	 * <p>立即重建设备类型列表缓存</p>
	 * 
	 * @return boolean 重建是否成功
	 * @throws HibernateException
	 */
	public static boolean resetNow()throws HibernateException{
		try{
			DevtypeBean myDevtypeBean = new DevtypeBean();
			mydevtplist = myDevtypeBean.getAllDevtypeList();
			HibernateUtil.closeSession();
			len = mydevtplist.size();
			resetflag = 0;
		}catch(Exception e){
			return false;	
		}
		return true;
	}
}
