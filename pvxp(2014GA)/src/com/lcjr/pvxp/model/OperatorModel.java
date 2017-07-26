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

import org.apache.log4j.Logger;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> �ͱ�zzt_operator��ص�ҵ��ģ��
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * <br>
 * 
 * @author ����
 * @version 1.0 2004/12/14
 */
public class OperatorModel {
	private static List myoplist = null;
	Logger log = Logger.getLogger("web.com.lcjr.pvxp.model.OperatorModel.java");
	
	
	public OperatorModel() throws HibernateException {
	}
	
	
	
	/**
	 * <p>
	 * ��ȡ����Ա��Ϣ�б�
	 * </p>
	 * 
	 * @return List
	 * @throws HibernateException
	 */
	public List getOperList() throws HibernateException {
		// if(myoplist==null){//��Ϊֱ�Ӷ����ݿ� xucc 20090625
		
		OperatorBean myOperatorBean = new OperatorBean();
		myoplist = myOperatorBean.getAllOperList();
		HibernateUtil.closeSession();
		// }
		return myoplist;
	}
	
	
	
	/**
	 * <p>
	 * ��ȡָ��������ŵĲ���Ա��Ϣ�б� xucc 20110304
	 * </p>
	 * 
	 * @param mbankid
	 *            ������ţ����������ĸA����ʾ���Ժ�����λƥ�������ַ���
	 * @return List
	 */
	public List getOperListOfBank(String mbankid) {
		
		Vector myVector = new Vector();
		PubUtil myPubUtil = new PubUtil();
		
		try {
			List oplist = getOperList();
			if (oplist == null || mbankid == null)
				return null;
			int len = oplist.size();
			int allpos = mbankid.indexOf("A");
			if (allpos == -1) {
				for (int i = 0; i < len; i++) {
					Operator temp = (Operator) oplist.get(i);
					if (mbankid.trim().equals(myPubUtil.dealNull(temp.getBankid()).trim())) {
						myVector.add(temp);
					}
				}
			} else {
				for (int i = 0; i < len; i++) {
					Operator temp = (Operator) oplist.get(i);
					if ((myPubUtil.dealNull(temp.getBankid()).trim()).indexOf(mbankid.substring(0, allpos)) == 0) {
						myVector.add(temp);
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		if (myVector == null) {
			return null;
		} else {
			return myVector.subList(0, myVector.size());
		}
	}
	
	
	
	/**
	 * <p>
	 * �ӵ�ǰ��������Ա�б��л�ȡ����Ա��Ϣʵ��
	 * </p>
	 * 
	 * @param operid
	 *            ����Ա���
	 * @param oplist
	 *            ����Ա�б�
	 * @return Operator
	 */
	public static Operator getOperFromList(String operid, List oplist) {
		if (oplist == null || operid == null)
			return null;
		int len = oplist.size();
		Operator reOperator = null;
		// �Ӳ���Ա�б���Ѱ��operid����һ����Ϣ��������
		for (int i = 0; i < len; i++) {
			Operator temp = (Operator) oplist.get(i);
			if (operid.trim().equals(temp.getOperid().trim())) {
				reOperator = temp;
				break;
			}
		}
		return reOperator;
	}
	
	
	
	/**
	 * <p>
	 * ��Ӳ���Ա�����ݿ�
	 * </p>
	 * 
	 * @param st
	 *            ����Ա��Ϣ����
	 * @return int -1:��Ӳ���Աʧ�� 0:��Ӳ���Ա�ɹ�
	 * @throws HibernateException
	 */
	public static int addOper(Operator st) throws HibernateException {
		boolean result = false;
		
		int ret = -1;
		OperatorBean myOperatorBean = new OperatorBean();
		try {
			result = myOperatorBean.addOperator(st);
		} catch (Exception e) {
			result = false;
		}
		if (result)
			ret = 0;
		
		return ret;
	}
	
	
	
	/**
	 * <p>
	 * ɾ��ָ������Ա��ŵĲ���Ա
	 * </p>
	 * 
	 * @param operid
	 *            ����Ա���
	 * @return int -1:ɾ������Աʧ�� 0:ɾ������Ա�ɹ�
	 * @throws HibernateException
	 */
	public static int deleteOper(String operid) throws HibernateException {
		boolean result = false;
		
		int ret = -1;
		OperatorBean myOperatorBean = new OperatorBean();
		try {
			result = myOperatorBean.deleteOperator(operid);
		} catch (Exception e) {
			result = false;
		}
		
		if (result)
			ret = 0;
		
		return ret;
	}
	
	
	
	/**
	 * <p>
	 * ����ָ������Ա��ŵĲ���Ա��Ϣ
	 * </p>
	 * 
	 * @param st
	 *            ����Ա��Ϣ����
	 * @param operid
	 *            ����Ա���
	 * @return int -1:���²���Աʧ�� 0:���²���Ա�ɹ�
	 * @throws HibernateException
	 */
	public static int updateOper(Operator st, String operid) throws HibernateException {
		boolean result = false;
		int ret = -1;
		OperatorBean myOperatorBean = new OperatorBean();
		try {
			result = myOperatorBean.updateOperator(st, operid);
		} catch (Exception e) {
			result = false;
		}
		if (result)
			ret = 0;
		
		return ret;
		
	}
	
	
	
	/**
	 * <p>
	 * ��֤����Ա����
	 * </p>
	 * 
	 * @param operid
	 *            ����Ա���
	 * @param password
	 *            ����Ա���루����ǰ��
	 * @return Operator �����֤ʧ�ܷ���null
	 * @throws HibernateException
	 */
	public Operator validateOper(String operid, String password) throws HibernateException {
		Operator myOperator = null;
		try {
			OperatorBean myOperatorBean = new OperatorBean();
			Operator tmp = myOperatorBean.getOperator(operid);
			if (tmp == null)
				return null;
			MD5 mymd5 = new MD5();
			// ���ݿ��е������Ǽ��ܹ��ģ�ֻ�н���������밴��ͳһ�����㷨���м��ܣ����ܸ������ݿ��е�������бȽ�
			if ((mymd5.getMD5ofStr(password)).equals((tmp.getPassword()).trim())) {
				myOperator = (Operator) tmp.clone();// ΪʲôҪ�ÿ�¡
			}
			HibernateUtil.closeSession();
		} catch (Exception e) {
			log.error("validateOper()", e);
			return null;
		}
		
		return myOperator;
		
	}
	
	/**
	 * <p>
	 * �޸Ĳ���Ա����
	 * </p>
	 * 
	 * @param operid
	 *            ����Ա���
	 * @param password
	 *            ����Ա���루����ǰ��
	 * @return int -1:�������,0:���ĳɹ�,1:����ʧ��
	 * @throws HibernateException
	 */
	
	public int changePasswd(String operid, String password, String newPasswd) throws HibernateException {
		try {
			Operator myOperator = validateOper(operid, password);
			if (myOperator == null)
				return -1;
			MD5 mymd5 = new MD5();
			newPasswd = mymd5.getMD5ofStr(newPasswd);
			PubUtil myPubUtil = new PubUtil();
			myOperator.setName(myPubUtil.dealNull(myOperator.getName()).trim());
			myOperator.setPassword(newPasswd);
			if (updateOper(myOperator, operid) == 0)
				return 0;
			else
				return 1;
		} catch (Exception e) {
			return 1;
		}
	}
	
	
	
	/**
	 * <p>
	 * ��ȡ��ǰ����Ա��ĳ���ܵ�Ȩ��
	 * </p>
	 * 
	 * @param funcid
	 *            ����ID�ţ���authlist�е�λ�ã�
	 * @param request
	 *            HttpServletRequest����
	 * @return int Ȩ��ֵ 0-û���κ�Ȩ�� 1-�����Ȩ�� 2-�����д����Ȩ�� 3-ϵͳ����Ա
	 */
	public int getPower(int funcid, HttpServletRequest request) {
		try {
			PubUtil myPubUtil = new PubUtil();
			String alist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (alist.equals("*")) {
				return 3;
			} else if (alist.length() < funcid + 1) {// ����ID�ŵ���ֵ����Ȩ�����ִ��ĳ��ȣ�����û���κ�Ȩ��
				return 0;
			} else {
				return Integer.parseInt(alist.substring(funcid, funcid + 1));// ����Ȩ�����ִ���funcid��funcid+1λ���ϵ���ֵ
			}
		} catch (Exception e) {
			return 0;
		}
	}
	
	
	
	/**
	 * ������Ա��Ż��Operator�־ö���
	 * 
	 * @param operid
	 *            ����Ա���
	 * @return Operator
	 * @throws HibernateException
	 */
	public Operator getOperator(String operid) throws HibernateException {
		OperatorBean myOperatorBean = new OperatorBean();
		Operator tmp = myOperatorBean.getOperator(operid);
		HibernateUtil.closeSession();
		return tmp;
	}
	
	/**
	 * <p>
	 * �жϵ�ǰ����Ա�Ƿ�Ϊϵͳ����Ա
	 * </p>
	 * 
	 * @param request
	 *            HttpServletRequest����
	 * @return boolean
	 */
	public boolean isSuperOP(HttpServletRequest request) {
		try {
			PubUtil myPubUtil = new PubUtil();
			String alist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (alist.equals("*")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
	/**
	 * <p>
	 * �жϵ�ǰ����Ա�Ƿ�Ϊָ��������û�
	 * </p>
	 * 
	 * @param plugid
	 *            ���ID
	 * @param request
	 *            HttpServletRequest����
	 * @return boolean
	 */
	public boolean isPluginUser(String plugid, HttpServletRequest request) {
		try {
			PubUtil myPubUtil = new PubUtil();
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			if (isSuperOP(request)) {
				return true;
			} else {
				PluginModel myPluginModel = new PluginModel();
				
				Plugin myPlugin = myPluginModel.getPlugin(plugid);
				
				String mytype = myPubUtil.dealNull(myPlugin.getPlugtype()).trim();
				if (mytype.equals("1")) { // ϵͳ���
					return false;
				} else if (mytype.equals("2")) { // ���Ų��
					return true;
				} else if (mytype.equals("3")) { // ��֤���
					String userslist = myPubUtil.dealNull(myPlugin.getUserslist()).trim();
					if (userslist.indexOf("," + operid + ",") == -1) {
						return false;
					} else {
						return true;
					}
				} else {
					return false;
				}
				
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * <p>
	 * �жϵ�ǰ����Ա�Ƿ�Ϊ�Ѿ���¼������û�
	 * </p>
	 * 
	 * @param plugid
	 *            ���ID
	 * @param request
	 *            HttpServletRequest����
	 * @return boolean
	 */
	public boolean isPluginUserIn(String plugid, HttpServletRequest request) {
		try {
			PubUtil myPubUtil = new PubUtil();
			String tmpstr = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_USEROF_PLUGIN + plugid, request)).trim();
			if (tmpstr.equals("yes")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * <p>
	 * �������������
	 * </p>
	 * 
	 * @param operid
	 *            ����Ա���
	 * @return boolean
	 */
	public boolean resetPassWDvalid(String operid) {
		try {
			
			Operator myOperator = getOperator(operid);
			if (myOperator == null)
				return false;
			
			PubUtil myPubUtil = new PubUtil();
			
			String passWDvalid = myPubUtil.dealNull(myPubUtil.ReadConfig("Users", "PassWDvalid", "7", "PowerView.ini")).trim();
			
			String newDate = myPubUtil.getOtherDate(Integer.parseInt(passWDvalid));
			myOperator.setAdddate(newDate);
			myOperator.setName(myPubUtil.dealNull(myOperator.getName()).trim());
			if (updateOper(myOperator, operid) == 0)
				return true;
			else
				return false;
			
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * <p>
	 * ���ò���Ա״̬
	 * </p>
	 * 
	 * @param operid
	 *            ����Ա���
	 * @return boolean
	 */
	public boolean resetState(String operid, String state) {
		try {
			PubUtil myPubUtil = new PubUtil();
			Operator myOperator = getOperator(operid);
			if (myOperator == null)
				return false;
			myOperator.setState(state);
			myOperator.setName(myPubUtil.dealNull(myOperator.getName()).trim());
			if (updateOper(myOperator, operid) == 0)
				return true;
			else
				return false;
			
		} catch (Exception e) {
			return false;
		}
	}
}
