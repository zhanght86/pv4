package com.lcjr.pvxp.util;

import java.util.*;
import java.io.*;

import org.apache.log4j.Logger;

/**
 * A very small class to load the jive_init.properties file. The class is needed
 * since loading files from the classpath in a static context often fails.
 * 
 * ��������pvxp.properties�ļ�����������ȡ��Ϣ..
 * 
 */
class InitPropLoader {
	
	Logger log = Logger.getLogger("web.lcjr.pvxp.util.InitPropLoader.java");
	
	
	private String mfilename;
	
	
	
	public InitPropLoader(String filename) {
		mfilename = filename;
	}
	
	
	
	/**
	 * Ѱ��properties���ڵ�·���������ء�
	 * 
	 * @return Propertiesʵ����
	 */
	public Properties Init() {
		Properties initProps = new Properties();
		InputStream in = null;
		try {
			// Ѱ��������ļ����ƣ������ء�
			in = getClass().getResourceAsStream("/" + mfilename);
			initProps.load(in);
		} catch (Exception e) {
			// ������ִ��󣬾�����Ҳ����ļ���
			log.error("Can't find file " + mfilename);
		} finally {
			try {
				// ���ر�������
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
 * Config���ļ����������࣬initproploader������Ѱ�Ҳ������ļ���
 * config���initproploader�л�ü��ص��ļ�����ʼ�������һ�� �����������Ӧֵ�ķ�����
 * 
 * 
 * 
 */
public class Config {
	private static Properties initProps;
	
	
	
	
	/**
	 * ���캯��
	 * 
	 * @param filename
	 *            �ļ�����
	 */
	public Config(String filename) {
		// Ѱ���ļ����ز���ʼ��
		initProps = new InitPropLoader(filename).Init();
	}
	
	
	
	/**
	 * ��������Ѱ�����Ե�ֵ��
	 * 
	 * @param name
	 *            ��������
	 * @return ���Ե�ֵ
	 */
	public static synchronized String getProperty(String name) {
		String s = null;
		
		if (initProps != null) {
			s = initProps.getProperty(name);
		}
		
		return s;
	}
}