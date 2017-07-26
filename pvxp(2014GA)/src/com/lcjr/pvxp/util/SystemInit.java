package com.lcjr.pvxp.util;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 
 * Tomcat��������ʼ���к�Ϳ�ʼ���еĵط�
 * 
 * @author ������
 * 
 * 
 *         TIME:2012-03-12
 * 
 *         �ο�ʼ�ĵط�
 * 
 */
public class SystemInit extends HttpServlet {

	Logger log = Logger.getLogger(SystemInit.class);

	/**
	 * doGet����
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			// ��ʼ�Զ�����Ĵ���
			StatAServer myServer = new StatAServer();
			myServer.start();
		} catch (Exception e) {
			log.error("����", e);
		}
	}

	/**
	 * doPost����
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	}

	/**
	 * ������ò���
	 */
	public String getServletInfo() {
		return super.getServletInfo();
	}

	/**
	 * ��ʼ������ʼ�����߳�
	 */
	public void init(ServletConfig sc) throws ServletException {

		log.info("pvxp����");

		super.init(sc);

		try {

			// ���׼���߳�
			TradeMoniServer myTradeMoniServer = new TradeMoniServer();
			myTradeMoniServer.startMoni();

			PubUtil pub = new PubUtil();
			pub.getAllProperties();
			
		} catch (Exception e) {
			log.error("catch����", e);
		}
	}

	/**
	 * �������ֳ���
	 */
	public void destroy() {
		log.info("pvxp����ر�");
		DevMoniServer myDevMoniServer = new DevMoniServer();
		myDevMoniServer.stopMoni();
		super.destroy();
	}
}