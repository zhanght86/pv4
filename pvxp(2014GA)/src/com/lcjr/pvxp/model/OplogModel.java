package com.lcjr.pvxp.model;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.util.MD5;
import com.lcjr.pvxp.orm.Oplog;
import com.lcjr.pvxp.bean.OplogBean;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.Operator;
import com.lcjr.pvxp.util.*;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;
import javax.servlet.http.*;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 和表zzt_oplog相关的业务模型</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/3/22
 */
public class OplogModel
{

	public OplogModel()throws HibernateException{

	}


	/**
	 * <p>获取操作员操作流水列表</p>
	 *
	 * @return List
	 * @throws HibernateException
	 */
	public List getOplogList()throws HibernateException{
		OplogBean myOplogBean = new OplogBean();
		List myoplist = myOplogBean.getAllOplogList();
		HibernateUtil.closeSession();
		return myoplist;
	}


	/**
	 * <p>添加操作流水到数据库</p>
	 *
	 * @param st 操作员操作流水对象
	 * @return int -1:添加失败 0:添加成功
	 * @throws HibernateException
	 */
	public static int addOplog(Oplog st)throws HibernateException{
		boolean result = false;

		int ret = -1;
		OplogBean myOplogBean = new OplogBean();
		try{
			result = myOplogBean.addOplog(st);
		}catch(Exception e){
			result = false;
		}
		if( result ) ret = 0;

		return ret;
	}


	/**
	 * <p>删除小于等于给定日期的操作员操作记录</p>
	 *
	 * @param opdate 操作日期
	 * @return int -1:删除失败 0:删除成功
	 * @throws HibernateException
	 */
	public static int deleteOplog(String opdate)throws HibernateException{
		boolean result = false;
		int ret = -1;
		PubUtil myPubUtil = new PubUtil();
		try{
			if( ( myPubUtil.dealNull(Constants.DB_OP_TYPE) ).equals("1") ){
				OplogBean myOplogBean = new OplogBean();
				result = myOplogBean.deleteOplog(opdate);
			}else{
				String sqlstr = "delete from zzt_oplog where opdate<='"+opdate+"'";
				DatabaseBean mydbbean = new DatabaseBean();
				try{
					result = mydbbean.executeUpdate(sqlstr,1);
				}catch(Exception jdbce){
					result = false;
				}finally{
					try{
						mydbbean.closeConn();
					}catch(Exception cle){
					}
				}
			}
		}catch(Exception e){
			result = false;
		}

		if( result ) ret = 0;

		return ret;
	}

	/**
	 * <p>添加操作流水到数据库</p>
	 *
	 * @param soperid 操作员编号
	 * @param stype 操作类型(0修改、1删除、2添加)
	 * @param strcd 操作编码(同操作权限)
	 * @param sinfo 操作描述,bean:message的key值|参数1|参数2...... 参数个数0-5。
	 * @return int -1:添加失败 0:添加成功
	 * @throws HibernateException
	 */
	public static int insertOplog( String soperid, String stype, String strcd, String sinfo )throws HibernateException{
		boolean result = false;
		int ret = -1;

		PubUtil myPubUtil = new PubUtil();

		OplogBean myOplogBean = new OplogBean();
		Oplog myOplog = new Oplog();
		String mbankid = "";
		String moperid = soperid.trim();
		String mopdate = myPubUtil.getNowDate(1);
		String moptime = myPubUtil.replace( myPubUtil.getNowTime(), ":", "" );
		String mtype = stype.trim();
		String mtrcd = strcd.trim();
		String minfo = sinfo.trim();
		String mremark1 = "";
		String mremark2 = "";

		OperatorModel myOperatorModel = new OperatorModel();
		Operator myOperator = myOperatorModel.getOperator( soperid.trim() );
		mbankid = (myOperator.getBankid()).trim();

		if ( ((myOperator.getAuthlist()).trim()).equals("*") ) {
			myOplog.setBankid( "" );
		}else {
			myOplog.setBankid( mbankid );
		}
		myOplog.setOperid( moperid );
		myOplog.setOpdate( mopdate );
		myOplog.setOptime( moptime );
		myOplog.setType( mtype );
		myOplog.setTrcd( mtrcd );
		myOplog.setInfo( minfo );
		myOplog.setRemark1( "" );
		myOplog.setRemark2( "" );


		try{
			result = myOplogBean.addOplog(myOplog);
		}catch(Exception e){
			result = false;
		}
		if( result ) ret = 0;

		return ret;
	}

	/**
	 * 根据操作员编号、操作日期、操作编号、操作类型<br>
	 * 查询系统中的操作流水信息<br><br>
	 *
	 * @param moperid 操作员编号
	 * @param mopdate1 查询起始日期
	 * @param mopdate2 查询结束日期
	 * @param mtrcd 操作对象
	 * @param mtype 操作类型
	 * @return List 返回的是包含有Oplog对象的List
	 * @throws HibernateException
	 */
	public List getSomeOplogList( String moperid, String mopdate1,
		String mopdate2, String mtrcd, String mtype )throws HibernateException
	{
		String soperid = moperid.trim();
		String sopdate1 = mopdate1.trim();
		String sopdate2 = mopdate2.trim();
		String strcd = mtrcd.trim();
		String stype = mtype.trim();

		OplogBean myOplogBean = new OplogBean();
		List myoplist = myOplogBean.getSomeOplogList( soperid, sopdate1, sopdate2, strcd, stype);
		HibernateUtil.closeSession();
		return myoplist;
	}

	/**
	 * 查询系统中部分Oplog信息<br>
	 * 返回的是包含有Oplog对象的List
	 *
	 * @param intstart 起始记录
	 * @param maxlen 	 最多返回多少条
	 *
	 * @return List
	 * @throws HibernateException
	 */
	public List getOplogs( String moperid, String mopdate1,String mopdate2, String mtrcd, String mtype, int intstart, int maxlen )throws HibernateException
	{
		OplogBean myOplogBean = new OplogBean();
		List tmp = myOplogBean.getOplogList( moperid, mopdate1, mopdate2, mtrcd, mtype, intstart, maxlen );
		HibernateUtil.closeSession();
		return tmp;
	}

	/**
	 * 查询系统中所有的Oplog个数<br>
	 *
	 * @return int
	 * @throws HibernateException
	 */
	public int getCount()throws HibernateException {
		OplogBean myOplogBean = new OplogBean();
		int mycount = myOplogBean.getAllCount();
		HibernateUtil.closeSession();
		return mycount;
	}
}
