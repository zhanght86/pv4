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
 * <p><b>Description:</b> �ͱ�zzt_pvplugin��ص�ҵ��ģ��</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author ����
 * @version 1.0 2005/03/28
 */
public class PluginModel
{

	public PluginModel()throws HibernateException{
		
	}
	

	/**
	 * <p>��ȡPlugin�б�</p>
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
	 * <p>���Plugin�����ݿ�</p>
	 * 
	 * @param st Plugin����
	 * @return int -1:��Ӳ���Աʧ�� 0:��Ӳ���Ա�ɹ�
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
	 * <p>ɾ��ָ��ID��Plugin</p>
	 * 
	 * @param plugid Plugin���
	 * @return int -1:ɾ��ʧ�� 0:ɾ���ɹ�
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
	 * <p>����Plugin��Ϣ</p>
	 * 
	 * @param st Plugin����
	 * @return int -1:����ʧ�� 0:���³ɹ�
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
	 * ��plugid���Plugin�־ö���
	 * 
	 * @param plugid ����Ա���
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
	 * ��ѯϵͳ�����е�Plugin����<br>
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
	 * ��ѯϵͳ�з���������Plugin����<br>
	 * 
	 * @param sqlwhere �������
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
	 * ��ѯϵͳ�в���Plugin��Ϣ<br>
	 * ���ص��ǰ�����Plugin�����List
	 *
	 * @param intstart ��ʼ��¼
	 * @param maxlen 	 ��෵�ض�����
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
	 * ��ѯϵͳ�з��������Ĳ���Plugin��Ϣ<br>
	 * ���ص��ǰ�����Plugin�����List
	 *
	 * @param intstart ��ʼ��¼
	 * @param maxlen 	 ��෵�ض�����
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
