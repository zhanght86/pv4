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
 * Tomcat服务器开始运行后就开始运行的地方
 * 
 * @author 武坤鹏
 * 
 * 
 *         TIME:2012-03-12
 * 
 *         梦开始的地方
 * 
 */
public class SystemInit extends HttpServlet {

	Logger log = Logger.getLogger(SystemInit.class);

	/**
	 * doGet方法
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			// 开始自动报表的处理
			StatAServer myServer = new StatAServer();
			myServer.start();
		} catch (Exception e) {
			log.error("报错：", e);
		}
	}

	/**
	 * doPost方法
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	}

	/**
	 * 获得配置参数
	 */
	public String getServletInfo() {
		return super.getServletInfo();
	}

	/**
	 * 初始化，开始各种线程
	 */
	public void init(ServletConfig sc) throws ServletException {

		log.info("pvxp启动");

		super.init(sc);

		try {

			// 交易监控线程
			TradeMoniServer myTradeMoniServer = new TradeMoniServer();
			myTradeMoniServer.startMoni();

			PubUtil pub = new PubUtil();
			pub.getAllProperties();
			
		} catch (Exception e) {
			log.error("catch报错", e);
		}
	}

	/**
	 * 析构各种程序。
	 */
	public void destroy() {
		log.info("pvxp服务关闭");
		DevMoniServer myDevMoniServer = new DevMoniServer();
		myDevMoniServer.stopMoni();
		super.destroy();
	}
}