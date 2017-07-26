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


/**
 * <p><b>Title:</b> PowerViewXP</p>
 * <p><b>Description:</b> 和表zzt_maintain相关的业务逻辑</p>
 * <p><b>Copyright:</b> Copyright (c) 2011</p>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p>
 * @author xucc
 * @version 1.0 2011/02/23
 */
 
public class MaintainModel
{

	public MaintainModel()throws HibernateException{
		
	}
	
	
	/**
	 * <p>添加报修记录到数据库</p>
	 * 
	 * @param st 报修记录对象
	 * @return int -1:添加报修记录失败 0:添加报修记录成功
	 * @throws HibernateException
	 */
	public static int addMaintain(Maintain st)throws HibernateException{
		boolean result = false;

		int ret = -1;
		MaintainBean myMaintainBean = new MaintainBean();
		try{
			result = myMaintainBean.addMaintain(st);
		}catch(Exception e){
			result = false;
		}
		if( result ) ret = 0;
		
		return ret;
	}
	
	
	/**
	 * <p>更新指定的报修记录信息</p>
	 * 
	 * @param st 报修记录对象
	 * @param devno 设备编号
	 * @param parts 报修部件
	 * @param trbdate 报修日期
	 * @param trbtime 报修时间
	 * @return int -1:更新报修记录失败 0:更新报修记录成功
	 * @throws HibernateException
	 */
	public static int updateMaintain(Maintain st , String devno, String parts, String trbdate, String trbtime)throws HibernateException{
		boolean result = false;
		int ret = -1;
		MaintainBean myMaintainBean = new MaintainBean();
		try{
			result = myMaintainBean.updateMaintain(st,devno,parts,trbdate,trbtime);
		}catch(Exception e){
			result = false;
		}
		if( result ) ret = 0;
		
		return ret;
		
	}
	
	public static List getMaintain(String[] devno, String pdate1, String pdate2, String[] subdevice, String[] state, int firstResult, int maxResults) throws HibernateException {
		List result = MaintainBean.getMaintain(devno, pdate1, pdate2, subdevice, state, firstResult, maxResults );
		HibernateUtil.closeSession();
		return result;
	}

	public static int getMaintainCount(String[] devno, String pdate1, String pdate2, String[] subdevice, String[] state) throws HibernateException {
		int result = MaintainBean.getMaintainCount(devno, pdate1, pdate2, subdevice, state);
		HibernateUtil.closeSession();
		return result;
	}
	
	public List getMaintainList()throws HibernateException{
		MaintainBean maintainBean = new MaintainBean();
		List maintainList = maintainBean.getMaintainList();
		HibernateUtil.closeSession();
		return maintainList;
	}
	
	public List getMaintainList(String devno,String date1,String date2)throws HibernateException{
		MaintainBean maintainBean = new MaintainBean();
		List maintainList = maintainBean.getMaintainList(devno,date1,date2);
		HibernateUtil.closeSession();
		return maintainList;
	}
	
	public static List getAMaintain(String devno,String parts,String trbdate,String trbtime)throws HibernateException{
		MaintainBean maintainBean = new MaintainBean();
		List maintainList = maintainBean.getAMaintain(devno,parts,trbdate,trbtime);
		HibernateUtil.closeSession();
		return maintainList;
	}
	
	public static List getSomeMaintain(String devno,String parts,String trbdate,String trbtime)throws HibernateException{
		MaintainBean maintainBean = new MaintainBean();
		List maintainList = maintainBean.getSomeMaintain(devno,parts,trbdate,trbtime);
		HibernateUtil.closeSession();
		return maintainList;
	}
	
	public static List getMaintainList(String state)throws HibernateException{
		MaintainBean maintainBean = new MaintainBean();
		List maintainList = maintainBean.getMaintainList(state);
		HibernateUtil.closeSession();
		return maintainList;
	}
	
}
