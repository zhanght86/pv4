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
	 * 用户名
	 */
	String userName = null;

	/**
	 * 密码
	 */
	String password = null;

	public MyAuthenticator() {
	}

	public MyAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	/**
	 * 校验用户名和密码
	 */
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
