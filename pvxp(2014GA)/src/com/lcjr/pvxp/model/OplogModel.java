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
 * <p><b>Description:</b> �ͱ�zzt_oplog��ص�ҵ��ģ��</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author ��̫��
 * @version 1.0 2005/3/22
 */
public class OplogModel
{

	public OplogModel()throws HibernateException{

	}


	/**
	 * <p>��ȡ����Ա������ˮ�б�</p>
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
	 * <p>��Ӳ�����ˮ�����ݿ�</p>
	 *
	 * @param st ����Ա������ˮ����
	 * @return int -1:���ʧ�� 0:��ӳɹ�
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
	 * <p>ɾ��С�ڵ��ڸ������ڵĲ���Ա������¼</p>
	 *
	 * @param opdate ��������
	 * @return int -1:ɾ��ʧ�� 0:ɾ���ɹ�
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
	 * <p>��Ӳ�����ˮ�����ݿ�</p>
	 *
	 * @param soperid ����Ա���
	 * @param stype ��������(0�޸ġ�1ɾ����2���)
	 * @param strcd ��������(ͬ����Ȩ��)
	 * @param sinfo ��������,bean:message��keyֵ|����1|����2...... ��������0-5��
	 * @return int -1:���ʧ�� 0:��ӳɹ�
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
	 * ���ݲ���Ա��š��������ڡ�������š���������<br>
	 * ��ѯϵͳ�еĲ�����ˮ��Ϣ<br><br>
	 *
	 * @param moperid ����Ա���
	 * @param mopdate1 ��ѯ��ʼ����
	 * @param mopdate2 ��ѯ��������
	 * @param mtrcd ��������
	 * @param mtype ��������
	 * @return List ���ص��ǰ�����Oplog�����List
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
	 * ��ѯϵͳ�в���Oplog��Ϣ<br>
	 * ���ص��ǰ�����Oplog�����List
	 *
	 * @param intstart ��ʼ��¼
	 * @param maxlen 	 ��෵�ض�����
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
	 * ��ѯϵͳ�����е�Oplog����<br>
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
