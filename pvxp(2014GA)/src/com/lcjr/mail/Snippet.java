package com.lcjr.mail;

/**
 * 
 * @author
 * @version pvxp(2014GA)
 * @date 2014-11-17
 */
public class Snippet {

	public static void main(String[] args) {

		// �������Ҫ�������ʼ�
		MailSenderInfo mailInfo = new MailSenderInfo();

		mailInfo.setMailServerHost("mail.inspur.com");
		mailInfo.setMailServerPort("587");
		mailInfo.setValidate(true);
		mailInfo.setUserName("wukp@inspur.com");
		mailInfo.setPassword("wukunpeng@2");// ������������
		mailInfo.setFromAddress("wukp@inspur.com");
		mailInfo.setToAddress("wukp@inspur.com");
		mailInfo.setSubject("�����������");
		mailInfo.setContent("������������");
		// �������Ҫ�������ʼ�
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// ���������ʽ
		// sms.sendHtmlMail(mailInfo);// ����html��ʽ
	}

}
