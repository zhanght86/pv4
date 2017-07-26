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
 * <p><b>Description:</b> �ͱ�zzt_autosta��ص�ҵ��ģ��</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author ��̫��
 * @version 1.0 2005/4/7
 */
public class AutostaModel{

	public AutostaModel()throws HibernateException{

	}


	/**
	 * <p>���ݻ�����Ż�ȡ�Զ�ͳ��������Ϣ�б�</p>
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
	 * <p>��ȡ�Զ�ͳ��������Ϣ�б�</p>
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
	 * <p>�ӵ�ǰ�����Զ�ͳ�������б��л�ȡ�Զ�ͳ��������Ϣʵ��</p>
	 *
	 * @param id �Զ�ͳ��������
	 * @param oplist �Զ�ͳ�������б�
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
	 * <p>����Զ�ͳ���������ݿ�</p>
	 *
	 * @param st �Զ�ͳ��������Ϣ����
	 * @return int -1:����Զ�ͳ������ʧ�� 0:����Զ�ͳ������ɹ�
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
	 * <p>ɾ��ָ���Զ�ͳ�������ŵ��Զ�ͳ������</p>
	 *
	 * @param id �Զ�ͳ��������
	 * @return int -1:ɾ���Զ�ͳ������ʧ�� 0:ɾ���Զ�ͳ������ɹ�
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
	 * <p>����ָ���Զ�ͳ�������ŵ��Զ�ͳ��������Ϣ</p>
	 *
	 * @param st �Զ�ͳ��������Ϣ����
	 * @param id �Զ�ͳ��������
	 * @return int -1:�����Զ�ͳ������ʧ�� 0:�����Զ�ͳ������ɹ�
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
	 * ���Զ�ͳ�������Ż��Autosta�־ö���
	 *
	 * @param id �Զ�ͳ��������
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
	 *����ָ���������Զ�ͳ��������б������
	 *
	 *@param String bankid ����ID
	 *@return ָ��������ͳ���Զ�������б�����
	 */
	public int getAllCount( String bankid) throws HibernateException {
		AutostaBean myAsBean = new AutostaBean();
		int count = myAsBean.getAllCount(bankid);
		HibernateUtil.closeSession();
		return count;
	}
	
	/**
	 *����ָ��������ָ�������ͳ���������ָ��״̬���б�����
	 *
	 *@param String bankid ����ID
	 *@param String opentag ����״̬
	 *@return ָ��������ͳ��������б�����
	 */
	public int getCount( String bankid, String opentag ) throws HibernateException {
		AutostaBean myAsBean = new AutostaBean();
		int count = myAsBean.getCount(bankid, opentag);
		HibernateUtil.closeSession();
		return count;
	}
	
	/**
	 *����ָ��������ͳ��������б�
	 *
	 *@param String bankid ����ID
	 *@param int firstRow ���صĵ�һ����¼
	 *@param int maxResults ��෵�صļ�¼��
	 *@return ָ��������ͳ��������б�
	 */
	public List getListByBankid( String bankid, int firstRow, int maxResults ) throws HibernateException {
		AutostaBean myAsBean = new AutostaBean();
		List result = myAsBean.getListByBankid(bankid, firstRow, maxResults);
		HibernateUtil.closeSession();
		return result;
	}
	
	/**
	*����ָ��������ͳ���������ָ��״̬���б�
	*
	*@param String bankid ����ID
	*@param String opentag ����״̬
	*@param int firstRow ���صĵ�һ����¼
	*@param int maxResults ��෵�صļ�¼��
	*@return ָ��������ͳ��������б�
	*/
	public List getListByBankid_Opentag( String bankid, String opentag, int firstRow, int maxResults ) throws HibernateException {
		AutostaBean myAsBean = new AutostaBean();
		List result = myAsBean.getListByBankid_Opentag(bankid, opentag, firstRow, maxResults);
		HibernateUtil.closeSession();
		return result;
	}

}
