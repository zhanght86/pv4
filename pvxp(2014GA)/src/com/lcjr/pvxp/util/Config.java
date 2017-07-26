package com.lcjr.pvxp.util;

import java.util.*;
import java.io.*;

import org.apache.log4j.Logger;

/**
 * A very small class to load the jive_init.properties file. The class is needed
 * since loading files from the classpath in a static context often fails.
 * 
 * 用于连接pvxp.properties文件，并从中提取信息..
 * 
 */
class InitPropLoader {
	
	Logger log = Logger.getLogger("web.lcjr.pvxp.util.InitPropLoader.java");
	
	
	private String mfilename;
	
	
	
	public InitPropLoader(String filename) {
		mfilename = filename;
	}
	
	
	
	/**
	 * 寻找properties所在的路径，并加载。
	 * 
	 * @return Properties实例化
	 */
	public Properties Init() {
		Properties initProps = new Properties();
		InputStream in = null;
		try {
			// 寻找输入的文件名称，并加载。
			in = getClass().getResourceAsStream("/" + mfilename);
			initProps.load(in);
		} catch (Exception e) {
			// 如果出现错误，就输出找不到文件。
			log.error("Can't find file " + mfilename);
		} finally {
			try {
				// 最后关闭输入流
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
			}
		}
		return initProps;
	}
}

/**
 * Config类文件中有两个类，initproploader类用于寻找并加载文件，
 * config类从initproploader中获得加载的文件并初始化，添加一个 获得属性所对应值的方法。
 * 
 * 
 * 
 */
public class Config {
	private static Properties initProps;
	
	
	
	
	/**
	 * 构造函数
	 * 
	 * @param filename
	 *            文件名称
	 */
	public Config(String filename) {
		// 寻找文件加载并初始化
		initProps = new InitPropLoader(filename).Init();
	}
	
	
	
	/**
	 * 根据属性寻找属性的值。
	 * 
	 * @param name
	 *            属性名称
	 * @return 属性的值
	 */
	public static synchronized String getProperty(String name) {
		String s = null;
		
		if (initProps != null) {
			s = initProps.getProperty(name);
		}
		
		return s;
	}
}