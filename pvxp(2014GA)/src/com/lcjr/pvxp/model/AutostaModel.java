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
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 和表zzt_autosta相关的业务模型</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/4/7
 */
public class AutostaModel{

	public AutostaModel()throws HibernateException{

	}


	/**
	 * <p>根据机构编号获取自动统计任务信息列表</p>
	 *
	 * @return List
	 * @throws HibernateException
	 */
	public List getAutostaListByBankid( String bankid )throws HibernateException{
		List myoplist = getAutostaList();
		Autosta myAutosta = new Autosta();

		bankid = bankid.trim();

		for( int i=0; i<myoplist.size(); i++ ) {
			myAutosta = (Autosta)myoplist.get(i);
			if( !bankid.equals( myAutosta.getBankid().trim() ) ) {
				myoplist.remove(i);
			}
		}

		HibernateUtil.closeSession();
		return myoplist;
	}

	/**
	 * <p>获取自动统计任务信息列表</p>
	 *
	 * @return List
	 * @throws HibernateException
	 */
	public List getAutostaList()throws HibernateException{
		AutostaBean myAutostaBean = new AutostaBean();
		List myoplist = myAutostaBean.getAllAutostaList();
		HibernateUtil.closeSession();
		return myoplist;
	}


	/**
	 * <p>从当前给定自动统计任务列表中获取自动统计任务信息实例</p>
	 *
	 * @param id 自动统计任务编号
	 * @param oplist 自动统计任务列表
	 * @return Autosta
	 */
	public Autosta getAutostaFromList(String id,List oplist){
		if(oplist==null||id==null) return null;
		int len = oplist.size();
		Autosta reAutosta = null;
		for( int i=0;i<len;i++ ){
			Autosta temp=(Autosta)oplist.get(i);
			if( id.trim().equals(temp.getId().trim()) ){
				reAutosta = temp;
				break;
			}
		}
		return reAutosta;
	}

	/**
	 * <p>添加自动统计任务到数据库</p>
	 *
	 * @param st 自动统计任务信息对象
	 * @return int -1:添加自动统计任务失败 0:添加自动统计任务成功
	 * @throws HibernateException
	 */
	public synchronized static int addAutosta(Autosta st)throws HibernateException{
		boolean result = false;

		int ret = -1;
		AutostaBean myAutostaBean = new AutostaBean();
		Autosta myAutosta = new Autosta();
		PubUtil myPubUtil = new PubUtil();
		List myList = new ArrayList();

		try {
			String curid = "";

			myList = (List)myAutostaBean.getAllAutostaList();
			if( myList.size() > 0 ) {
				myAutosta = (Autosta)myList.get(0);
				String maxid = myAutosta.getId().trim();
				if( maxid.equals("") ) {
					curid = "0000000001";
				}else {
					curid = Integer.toString(Integer.parseInt(maxid) + 1);
					curid = myPubUtil.strFormat( curid, 10, 1, '0' );
				}
			}else {
				curid = "0000000001";
			}
			st.setId( curid );
		}catch ( Exception e ) {
		}

		try{
			result = myAutostaBean.addAutosta(st);
		}catch(Exception e){
			result = false;
		}
		if( result ) ret = 0;

		HibernateUtil.closeSession();
		return ret;
	}


	/**
	 * <p>删除指定自动统计任务编号的自动统计任务</p>
	 *
	 * @param id 自动统计任务编号
	 * @return int -1:删除自动统计任务失败 0:删除自动统计任务成功
	 * @throws HibernateException
	 */
	public static int deleteAutosta(String id)throws HibernateException{
		boolean result = false;

		int ret = -1;
		AutostaBean myAutostaBean = new AutostaBean();
		try{
			result = myAutostaBean.deleteAutosta(id);
		}catch(Exception e){
			result = false;
		}

		if( result ) ret = 0;

		HibernateUtil.closeSession();
		return ret;
	}

	/**
	 * <p>更新指定自动统计任务编号的自动统计任务信息</p>
	 *
	 * @param st 自动统计任务信息对象
	 * @param id 自动统计任务编号
	 * @return int -1:更新自动统计任务失败 0:更新自动统计任务成功
	 * @throws HibernateException
	 */
	public static int updateAutosta(Autosta st)throws HibernateException{
		boolean result = false;
		int ret = -1;
		AutostaBean myAutostaBean = new AutostaBean();
		try{
			result = myAutostaBean.updateAutosta(st);
		}catch(Exception e){
			result = false;
		}
		if( result ) ret = 0;

		HibernateUtil.closeSession();
		return ret;

	}

	/**
	 * 按自动统计任务编号获得Autosta持久对象
	 *
	 * @param id 自动统计任务编号
	 * @return Autosta
	 * @throws HibernateException
	 */
	public Autosta getAutosta(String id)throws HibernateException{
		AutostaBean myAutostaBean = new AutostaBean();
		Autosta tmp = myAutostaBean.getAutosta(id);
		HibernateUtil.closeSession();
		return tmp;
	}

	/**
	 *返回指定机构的自动统计任务表列表的总数
	 *
	 *@param String bankid 机构ID
	 *@return 指定机构的统计自动任务表列表总数
	 */
	public int getAllCount( String bankid) throws HibernateException {
		AutostaBean myAsBean = new AutostaBean();
		int count = myAsBean.getAllCount(bankid);
		HibernateUtil.closeSession();
		return count;
	}
	
	/**
	 *返回指定机构中指定分类的统计任务表中指定状态的列表总数
	 *
	 *@param String bankid 机构ID
	 *@param String opentag 任务状态
	 *@return 指定机构的统计任务表列表总数
	 */
	public int getCount( String bankid, String opentag ) throws HibernateException {
		AutostaBean myAsBean = new AutostaBean();
		int count = myAsBean.getCount(bankid, opentag);
		HibernateUtil.closeSession();
		return count;
	}
	
	/**
	 *返回指定机构的统计任务表列表
	 *
	 *@param String bankid 机构ID
	 *@param int firstRow 返回的第一条记录
	 *@param int maxResults 最多返回的记录数
	 *@return 指定机构的统计任务表列表
	 */
	public List getListByBankid( String bankid, int firstRow, int maxResults ) throws HibernateException {
		AutostaBean myAsBean = new AutostaBean();
		List result = myAsBean.getListByBankid(bankid, firstRow, maxResults);
		HibernateUtil.closeSession();
		return result;
	}
	
	/**
	*返回指定机构的统计任务表中指定状态的列表
	*
	*@param String bankid 机构ID
	*@param String opentag 任务状态
	*@param int firstRow 返回的第一条记录
	*@param int maxResults 最多返回的记录数
	*@return 指定机构的统计任务表列表
	*/
	public List getListByBankid_Opentag( String bankid, String opentag, int firstRow, int maxResults ) throws HibernateException {
		AutostaBean myAsBean = new AutostaBean();
		List result = myAsBean.getListByBankid_Opentag(bankid, opentag, firstRow, maxResults);
		HibernateUtil.closeSession();
		return result;
	}

}
