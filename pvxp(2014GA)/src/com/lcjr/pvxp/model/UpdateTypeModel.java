package com.lcjr.pvxp.model;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Updatetype;
import com.lcjr.pvxp.bean.UpdateTypeBean;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> �ͱ�zzt_updatetype��ص�ҵ��ģ��</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2009</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
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
	 * ���ø���������Ϣ�б��־�����ڶ�zzt_updatetypeд������<br>
	 * Ŀ�ģ��´δ���ʵ��ʱ���¼���UpdateTypelist
	 */
	public static void reset(){
		resetflag = 1;
	 }
	/**
	 * <p>��ȡ���������б�</p>
	 * 
	 * @return List
	 */
	public List getUpdateTypeList()throws HibernateException{
		//this.myUpdateTypelist = initUpdateTypelist();//��Ϊֱ�Ӷ����ݿ� xucc 20090625
		resetNow();
		return myUpdateTypelist;
	}
	/**
	 * <p>��ȡ���������ڵ�ǰ���������б����е�λ��</p>
	 * 
	 * @param updateno �������ͱ��
	 * @return int ���������ڵ�ǰ���������б����е�λ��(-1:�ø������Ͳ�����������)
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
	 * <p>�ӵ�ǰ���������б����л�ȡ��������ʵ��</p>
	 * 
	 * @param updateno ���±��
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
	 * <p>��Ӹ������͵����ݿⲢ���¸��������б�</p>
	 * 
	 * @param st �������Ͷ���
	 * @return int -1:��Ӹ�������ʧ�� 0:��Ӹ������ͳɹ� 1:��Ӹ������ͳɹ����Ǹ��������б�ʧ�ܣ���Ҫ�ؽ����������б���
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
		//�����Ӹ������͵����ݿ�ɹ�������¸��������б�
		if( result ) result1 = myUpdateTypelist.add(st);
		if( !result ) ret = -1;
		else if( !result1 ) ret = 1;
		else ret = 0;
		
		return ret;
	}
	
	
	/**
	 * <p>ɾ��ָ���������ͱ�ŵĸ���</p>
	 * 
	 * @param updateno �������ͱ��
	 * @return int -1:ɾ����������ʧ�� 0:ɾ���������ͳɹ� 1:ɾ���������ͳɹ����Ǹ��������б�ʧ�ܣ���Ҫ�ؽ����������б���
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
		//���ɾ�����³ɹ�������¸����б�
		if( result&&updatetypeobj!=null ) result1 = myUpdateTypelist.remove(updatetypeobj);

		
		if( !result ) ret = -1;
		else if( !result1 ) ret = 1;
		else ret = 0;
		
		return ret;
	}
	
	/**
	 * <p>����ָ���������ͱ�ŵĸ���������Ϣ</p>
	 * 
	 * @param st �������Ͷ���
	 * @param updateno �������ͱ��
	 * @return int -1:���¸�������ʧ�� 0:���¸������ͳɹ� 1:���¸������ͳɹ����Ǹ��������б�ʧ�ܣ���Ҫ�ؽ����������б���
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
		//������¸��³ɹ�������¸����б�

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
	 * <p>�����ؽ����������б���</p>
	 * 
	 * @return boolean �ؽ��Ƿ�ɹ�
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
	 * ����Ż�ó־ö���
	 * 
	 * @param updateno �������ͱ��
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
