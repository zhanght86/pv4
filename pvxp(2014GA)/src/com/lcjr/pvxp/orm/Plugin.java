package com.lcjr.pvxp.orm;

import java.io.Serializable;


/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> ��hibernate��ӳ��zzt_pvplugin�����
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2012
 * </p>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * 
 * @author ������
 * @version 1.0 2012/06/01����ͯ�ڣ�
 */
public class Plugin implements Serializable {
	
	
	/**
	 * ���ID
	 */
	private String plugid;
	
	
	
	/**
	 * �������
	 */
	private String plugname;
	
	
	
	/**
	 * ��ڳ����ַ
	 */
	private String firsturl;
	
	
	
	/**
	 * ������ͣ� 1 ϵͳ�������ϵͳ����Ա�� 2 ���Ų������У��Ȩ�� 3 ��֤���������֤�б��û�����
	 * 
	 */
	private String plugtype;
	
	
	
	/**
	 * ����
	 */
	private String info;
	
	
	
	/**
	 * ��֤�û�ID�б��磺 ,0000,0001,0002,0100,0020, �����ж���
	 * 
	 */
	private String userslist;



	/**
	 * @return the firsturl
	 */
	public String getFirsturl() {
		return firsturl;
	}



	/**
	 * @param firsturl the firsturl to set
	 */
	public void setFirsturl(String firsturl) {
		this.firsturl = firsturl;
	}



	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}



	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}



	/**
	 * @return the plugid
	 */
	public String getPlugid() {
		return plugid;
	}



	/**
	 * @param plugid the plugid to set
	 */
	public void setPlugid(String plugid) {
		this.plugid = plugid;
	}



	/**
	 * @return the plugname
	 */
	public String getPlugname() {
		return plugname;
	}



	/**
	 * @param plugname the plugname to set
	 */
	public void setPlugname(String plugname) {
		this.plugname = plugname;
	}



	/**
	 * @return the plugtype
	 */
	public String getPlugtype() {
		return plugtype;
	}



	/**
	 * @param plugtype the plugtype to set
	 */
	public void setPlugtype(String plugtype) {
		this.plugtype = plugtype;
	}



	/**
	 * @return the userslist
	 */
	public String getUserslist() {
		return userslist;
	}



	/**
	 * @param userslist the userslist to set
	 */
	public void setUserslist(String userslist) {
		this.userslist = userslist;
	}
	
	
	
	
}
