package com.lcjr.pvxp.model;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.orm.Devftinfo;
import com.lcjr.pvxp.bean.DevftBean;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> �ͱ�zzt_devftinfo��ص�ҵ��ģ��</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2010</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
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
	 * �����豸������Ϣ�б��־�����ڶ�zzt_devftinfoд������<br>
	 * Ŀ�ģ��´δ���ʵ��ʱ���¼���devftlist
	 */
	public static void reset(){
			resetflag = 1;
	 }
	/**
	 * <p>��ȡ�豸�����б�</p>
	 * 
	 * @return List
	 */
	public List getDevftList(){
		return mydevftlist;
	}
	/**
	 * <p>��ȡ�豸�����ڵ�ǰ�豸�����б����е�λ��</p>
	 * 
	 * @param devftno �豸���̱��
	 * @return int �豸�����ڵ�ǰ�豸�����б����е�λ��(-1:���豸���̲�����������)
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
	 * <p>�ӵ�ǰ�豸�����б����л�ȡ�豸����ʵ��</p>
	 * 
	 * @param devftno �豸���̱��
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
	 * <p>����豸���̵����ݿⲢ�����豸�����б�</p>
	 * 
	 * @param st �豸���̶���
	 * @return int -1:����豸����ʧ�� 0:����豸���̳ɹ� 1:����豸���̳ɹ����Ǹ����豸�����б�ʧ�ܣ���Ҫ�ؽ��豸�����б���
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
		//�������豸���̵����ݿ�ɹ���������豸�����б�
		if( result ) result1 = mydevftlist.add(st);
		if( !result ) ret = -1;
		else if( !result1 ) ret = 1;
		else ret = 0;
		
		return ret;
	}
	
	
	/**
	 * <p>ɾ��ָ���豸���̱�ŵ��豸</p>
	 * 
	 * @param devftno �豸���̱��
	 * @return int -1:ɾ���豸����ʧ�� 0:ɾ���豸���̳ɹ� 1:ɾ���豸���̳ɹ����Ǹ����豸�����б�ʧ�ܣ���Ҫ�ؽ��豸�����б���
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
		//���ɾ���豸���̳ɹ���������豸�����б�
		if( result&&devftobj!=null ) result1 = mydevftlist.remove(devftobj);

		
		if( !result ) ret = -1;
		else if( !result1 ) ret = 1;
		else ret = 0;
		
		return ret;
	}
	
	/**
	 * <p>����ָ���豸���̱�ŵ��豸������Ϣ</p>
	 * 
	 * @param st �豸���̶���
	 * @param devftno �豸���̱��
	 * @return int -1:�����豸����ʧ�� 0:�����豸���̳ɹ� 1:�����豸���̳ɹ����Ǹ����豸�����б�ʧ�ܣ���Ҫ�ؽ��豸�����б���
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
		//��������豸���̳ɹ���������豸�����б�

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
	 * <p>�����ؽ��豸�����б���</p>
	 * 
	 * @return boolean �ؽ��Ƿ�ɹ�
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
