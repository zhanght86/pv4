package com.lcjr.pvxp.model;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Devtype;
import com.lcjr.pvxp.bean.DevtypeBean;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> �ͱ�zzt_devtype��ص�ҵ��ģ��</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author ����
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
	 * �����豸������Ϣ�б��־�����ڶ�zzt_devtypeд������<br>
	 * Ŀ�ģ��´δ���ʵ��ʱ���¼���devtypelist
	 */
	public static void reset(){
		resetflag = 1;
	 }
	/**
	 * <p>��ȡ�豸�����б�</p>
	 * 
	 * @return List
	 */
	public List getDevTpList()throws HibernateException{
		this.mydevtplist = initDevTplist();//��Ϊֱ�Ӷ����ݿ� xucc 20090625
		return mydevtplist;
	}
	/**
	 * <p>��ȡ�豸�����ڵ�ǰ�豸�����б����е�λ��</p>
	 * 
	 * @param typeno �豸���ͱ��
	 * @return int �豸�����ڵ�ǰ�豸�����б����е�λ��(-1:���豸���Ͳ�����������)
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
	 * <p>�ӵ�ǰ�豸�����б����л�ȡ�豸����ʵ��</p>
	 * 
	 * @param typeno �豸���
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
	 * <p>����豸���͵����ݿⲢ�����豸�����б�</p>
	 * 
	 * @param st �豸���Ͷ���
	 * @return int -1:����豸����ʧ�� 0:����豸���ͳɹ� 1:����豸���ͳɹ����Ǹ����豸�����б�ʧ�ܣ���Ҫ�ؽ��豸�����б���
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
		//�������豸���͵����ݿ�ɹ���������豸�����б�
		if( result ) result1 = mydevtplist.add(st);
		if( !result ) ret = -1;
		else if( !result1 ) ret = 1;
		else ret = 0;
		
		return ret;
	}
	
	
	/**
	 * <p>ɾ��ָ���豸���ͱ�ŵ��豸</p>
	 * 
	 * @param typeno �豸���ͱ��
	 * @return int -1:ɾ���豸����ʧ�� 0:ɾ���豸���ͳɹ� 1:ɾ���豸���ͳɹ����Ǹ����豸�����б�ʧ�ܣ���Ҫ�ؽ��豸�����б���
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
		//���ɾ���豸�ɹ���������豸�б�
		if( result&&devtpobj!=null ) result1 = mydevtplist.remove(devtpobj);

		
		if( !result ) ret = -1;
		else if( !result1 ) ret = 1;
		else ret = 0;
		
		return ret;
	}
	
	/**
	 * <p>����ָ���豸���ͱ�ŵ��豸������Ϣ</p>
	 * 
	 * @param st �豸���Ͷ���
	 * @param typeno �豸���ͱ��
	 * @return int -1:�����豸����ʧ�� 0:�����豸���ͳɹ� 1:�����豸���ͳɹ����Ǹ����豸�����б�ʧ�ܣ���Ҫ�ؽ��豸�����б���
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
		//��������豸�ɹ���������豸�б�

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
	 * <p>�����ؽ��豸�����б���</p>
	 * 
	 * @return boolean �ؽ��Ƿ�ɹ�
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
