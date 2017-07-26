package com.lcjr.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 
 * @author
 * @version pvxp(2014GA)
 * @date 2014-11-17
 */
public class MyAuthenticator extends Authenticator {
	/**
	 * �û���
	 */
	String userName = null;

	/**
	 * ����
	 */
	String password = null;

	public MyAuthenticator() {
	}

	public MyAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	/**
	 * У���û���������
	 */
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
