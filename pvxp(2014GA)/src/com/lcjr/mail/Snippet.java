package com.lcjr.mail;

/**
 * 
 * @author
 * @version pvxp(2014GA)
 * @date 2014-11-17
 */
public class Snippet {

	public static void main(String[] args) {

		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();

		mailInfo.setMailServerHost("mail.inspur.com");
		mailInfo.setMailServerPort("587");
		mailInfo.setValidate(true);
		mailInfo.setUserName("wukp@inspur.com");
		mailInfo.setPassword("wukunpeng@2");// 您的邮箱密码
		mailInfo.setFromAddress("wukp@inspur.com");
		mailInfo.setToAddress("wukp@inspur.com");
		mailInfo.setSubject("设置邮箱标题");
		mailInfo.setContent("设置邮箱内容");
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发送文体格式
		// sms.sendHtmlMail(mailInfo);// 发送html格式
	}

}
