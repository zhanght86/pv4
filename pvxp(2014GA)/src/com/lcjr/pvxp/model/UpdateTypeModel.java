package com.lcjr.pvxp.model;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Updatetype;
import com.lcjr.pvxp.bean.UpdateTypeBean;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 和表zzt_updatetype相关的业务模型</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2009</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2009/12/07
 */
public class UpdateTypeModel
{
	private static List myUpdateTypelist = null;
	private static int len = 0;
	private static int resetflag = 0;
	
	public UpdateTypeModel()throws HibernateException{
		if( myUpdateTypelist == null ){
			rebiuld();
		}else if( resetflag == 1 ){
			myUpdateTypelist.clear();
			rebiuld();
			resetflag = 0;
		}
	}
	
	private static List initUpdateTypelist()throws HibernateException{
		UpdateTypeBean myUpdateTypeBean = new UpdateTypeBean();
		List myUpdateTypelist1 = myUpdateTypeBean.getAllUpdateTypeList();
		HibernateUtil.closeSession();
		return myUpdateTypelist1;
	}
	
	private void setLen(){
		this.len = this.myUpdateTypelist.size();
	}
	
	private void rebiuld()throws HibernateException{
		this.myUpdateTypelist = initUpdateTypelist();
		setLen();
	}
	/**
	 * 重置更新类型信息列表标志，用于对zzt_updatetype写操作后<br>
	 * 目的：下次创建实例时重新加载UpdateTypelist
	 */
	public static void reset(){
		resetflag = 1;
	 }
	/**
	 * <p>获取更新类型列表</p>
	 * 
	 * @return List
	 */
	public List getUpdateTypeList()throws HibernateException{
		//this.myUpdateTypelist = initUpdateTypelist();//改为直接读数据库 xucc 20090625
		resetNow();
		return myUpdateTypelist;
	}
	/**
	 * <p>获取更新类型在当前更新类型列表缓存中的位置</p>
	 * 
	 * @param updateno 更新类型编号
	 * @return int 更新类型在当前更新类型列表缓存中的位置(-1:该更新类型不存在于其中)
	 */
	public static int indexOfUpdateTypeList(String updateno){
		if(myUpdateTypelist==null||updateno==null) return -1;
		int len = myUpdateTypelist.size();
		int index = -1;
		for( int i=0;i<len;i++ ){
			Updatetype temp=(Updatetype)myUpdateTypelist.get(i);
			if( updateno.trim().equals(temp.getUpdateno().trim()) ){
				index = i;
				break;
			}
		}
		return index;
	}
	
	/**
	 * <p>从当前更新类型列表缓存中获取更新类型实例</p>
	 * 
	 * @param updateno 更新编号
	 * @return Updatetype
	 */
	public static Updatetype getUpdateTypeFromList(String updateno){
		if(myUpdateTypelist==null||updateno==null) return null;
		int len = myUpdateTypelist.size();
		Updatetype reUpdateType = null;
		for( int i=0;i<len;i++ ){
			Updatetype temp=(Updatetype)myUpdateTypelist.get(i);
			if( updateno.trim().equals(temp.getUpdateno().trim()) ){
				reUpdateType = temp;
				break;
			}
		}
		return reUpdateType;
	}
		
	/**
	 * <p>添加更新类型到数据库并更新更新类型列表</p>
	 * 
	 * @param st 更新类型对象
	 * @return int -1:添加更新类型失败 0:添加更新类型成功 1:添加更新类型成功但是更新类型列表失败，需要重建更新类型列表缓存
	 * @throws HibernateException
	 */
	public static int addUpdateType(Updatetype st)throws HibernateException{
		boolean result = false;
		boolean result1 = false;
		int ret = 0;
		UpdateTypeBean myUpdateTypeBean = new UpdateTypeBean();
		try{
			result = myUpdateTypeBean.addUpdateType(st);
		}catch(Exception e){
			result = false;
		}
		//如果添加更新类型到数据库成功，则更新更新类型列表
		if( result ) result1 = myUpdateTypelist.add(st);
		if( !result ) ret = -1;
		else if( !result1 ) ret = 1;
		else ret = 0;
		
		return ret;
	}
	
	
	/**
	 * <p>删除指定更新类型编号的更新</p>
	 * 
	 * @param updateno 更新类型编号
	 * @return int -1:删除更新类型失败 0:删除更新类型成功 1:删除更新类型成功但是更新类型列表失败，需要重建更新类型列表缓存
	 * @throws HibernateException
	 */
	public static int deleteUpdateType(String updateno)throws HibernateException{
		boolean result = false;
		boolean result1 = false;
		int ret = 0;
		UpdateTypeBean myUpdateTypeBean = new UpdateTypeBean();
		try{
			result = myUpdateTypeBean.deleteUpdateType(updateno);
		}catch(Exception e){
			result = false;
		}
		
		
		Updatetype updatetypeobj = getUpdateTypeFromList(updateno);
		//如果删除更新成功，则更新更新列表
		if( result&&updatetypeobj!=null ) result1 = myUpdateTypelist.remove(updatetypeobj);

		
		if( !result ) ret = -1;
		else if( !result1 ) ret = 1;
		else ret = 0;
		
		return ret;
	}
	
	/**
	 * <p>更新指定更新类型编号的更新类型信息</p>
	 * 
	 * @param st 更新类型对象
	 * @param updateno 更新类型编号
	 * @return int -1:更新更新类型失败 0:更新更新类型成功 1:更新更新类型成功但是更新类型列表失败，需要重建更新类型列表缓存
	 * @throws HibernateException
	 */
	public static int updateUpdateType(Updatetype st , String updateno)throws HibernateException{
		boolean result = false;
		boolean result1 = false;
		int ret = 0;
		UpdateTypeBean myUpdateTypeBean = new UpdateTypeBean();
		try{
			result = myUpdateTypeBean.updateUpdateType(st,updateno);
		}catch(Exception e){
			result = false;
		}
		
		int updatetypeindex = indexOfUpdateTypeList(updateno);
		Updatetype updatetypeobj = getUpdateTypeFromList(updateno);
		//如果更新更新成功，则更新更新列表

		if( result&&updatetypeobj!=null ){
			if( updatetypeobj.equals(myUpdateTypelist.set(updatetypeindex,st)) ) result1 = true;
			else result1 = false;
		}

		
		if( !result ) ret = -1;
		else if( !result1 ) ret = 1;
		else ret = 0;
		
		return ret;
		
	}
	/**
	 * <p>立即重建更新类型列表缓存</p>
	 * 
	 * @return boolean 重建是否成功
	 * @throws HibernateException
	 */
	public static boolean resetNow()throws HibernateException{
		try{
			UpdateTypeBean myUpdateTypeBean = new UpdateTypeBean();
			myUpdateTypelist = myUpdateTypeBean.getAllUpdateTypeList();
			HibernateUtil.closeSession();
			len = myUpdateTypelist.size();
			resetflag = 0;
		}catch(Exception e){
			return false;	
		}
		return true;
	}
	
	/**
	 * 按编号获得持久对象
	 * 
	 * @param updateno 更新类型编号
	 * @return Updatetype
	 * @throws HibernateException
	 */
	public static Updatetype getUpdateType(String updateno)throws HibernateException{
		UpdateTypeBean myUpdateTypeBean = new UpdateTypeBean();
		Updatetype tmp = myUpdateTypeBean.getUpdateType(updateno);
		HibernateUtil.closeSession();
		return tmp;
	}
}
