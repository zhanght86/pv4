package com.lcjr.pvxp.util;

import com.lcjr.pvxp.util.*;
import java.util.*;
import java.io.*;

import org.apache.log4j.Logger;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> �ַ���ת��
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
public class CharSet {
	
	Logger log = Logger.getLogger("web.lcjr.pvxp.util.CharSet.java");
	
	
	private static String dbInputCode;
	
	
	private static String dbOutputCode;
	
	
	private static String webCode;
	
	
	private static String formCode;
	
	
	private static String fileWrCode;
	
	static {
		Config myConfig = new Config("pvxp.properties");
		dbInputCode = myConfig.getProperty("dbinputcharset");
		dbOutputCode = myConfig.getProperty("dboutputcharset");
		webCode = myConfig.getProperty("webcharset");
		formCode = myConfig.getProperty("formcharset");
		fileWrCode = myConfig.getProperty("filewrencoding");
		
		if (dbInputCode == null)
			dbInputCode = "ISO8859-1";
		if (dbOutputCode == null)
			dbOutputCode = "ISO8859-1";
		if (webCode == null)
			webCode = "ISO8859-1";
		if (formCode == null)
			formCode = "ISO8859-1";
		if (fileWrCode == null)
			fileWrCode = "GB2312";
		
	}
	
	
	
	
	/**
	 * ���캯��
	 */
	public CharSet() {
		
	}
	
	
	
	/**
	 * <p>
	 * ��WEB�ַ���ת�����ݿ������ַ���
	 * </p>
	 * 
	 * @param str
	 *            WEB�ַ���
	 * @return String �������ݿ���ַ���
	 */
	public String web2db(String str) {
		
		if (str == null)
			return str;
		if (str.length() == 0)
			return str;
		if (webCode.equals(dbInputCode))
			return str;
		try {
			str = new String(str.getBytes(webCode), dbInputCode);
		} catch (UnsupportedEncodingException e) {
			log.error("charSet.inputdataConvert exception", e);
		}
		
		return str;
	}
	
	
	
	/**
	 * <p>
	 * ��������ַ���ת�����ݿ������ַ���
	 * </p>
	 * 
	 * @param str
	 *            form�ַ���
	 * @return String �������ݿ���ַ���
	 */
	public String form2db(String str) {
		if (str == null)
			return str;
		if (str.length() == 0)
			return str;
		if (formCode.equals(dbInputCode))
			return str;
		try {
			str = new String(str.getBytes(formCode), dbInputCode);
		} catch (UnsupportedEncodingException e) {
			log.error("charSet.inputdataConvert exception", e);
		}
		
		return str;
	}
	
	
	
	/**
	 * <p>
	 * ��������ַ���ת�����ݿ������ַ���
	 * </p>
	 * 
	 * @param str
	 *            form�ַ���
	 * @return String �������ݿ���ַ���
	 */
	public String form2GB(String str) {
		if (str == null)
			return str;
		if (str.length() == 0)
			return str;
		if (formCode.equals("GB2312"))
			return str;
		try {
			str = new String(str.getBytes(formCode), "GB2312");
		} catch (UnsupportedEncodingException e) {
			log.error("charSet.inputdataConvert exception", e);
		}
		
		return str;
	}
	
	
	
	/**
	 * <p>
	 * �����ݿ�����ַ���ת��WEB�ַ���
	 * </p>
	 * 
	 * @param str
	 *            ���ݿ�����ַ���
	 * @return String WEB��ʾ���ַ���
	 */
	public String db2web(String str) {
		if (str == null)
			return str;
		if (str.length() == 0)
			return str;
		if (dbOutputCode.equals(webCode))
			return str;
		try {
			str = new String(str.getBytes(dbOutputCode), webCode);
		} catch (UnsupportedEncodingException e) {
			log.error("charSet.inputdataConvert exception", e);
		}
		
		return str;
	}
	
	
	
	/**
	 * <p>
	 * ���ַ���תΪд�ļ��ַ���
	 * </p>
	 * 
	 * @param str
	 *            ������ַ���
	 * @return String д�ļ��ַ���
	 */
	public String form2file(String str) {
		if (str == null)
			return str;
		if (str.length() == 0)
			return str;
		if (formCode.equals(fileWrCode))
			return str;
		try {
			str = new String(str.getBytes(formCode), fileWrCode);
		} catch (UnsupportedEncodingException e) {
			log.error("charSet.inputdataConvert exception", e);
		}
		
		return str;
	}
	
	
	
	/**
	 * <p>
	 * ���ݿ�����ַ���תΪ���ݿ������ַ���
	 * </p>
	 * 
	 * @param str
	 *            �����ݿ�����ַ���
	 * @return String ���ݿ������ַ���
	 */
	public String db2db(String str) {
		if (str == null)
			return str;
		if (str.length() == 0)
			return str;
		if (dbOutputCode.equals(dbInputCode))
			return str;
		try {
			str = new String(str.getBytes(dbOutputCode), dbInputCode);
		} catch (UnsupportedEncodingException e) {
			log.error("charSet.inputdataConvert exception", e);
		}
		
		return str;
	}
	
	
	
	/**
	 * <p>
	 * ���ݿ��ַ���תΪд�ļ��ַ���
	 * </p>
	 * 
	 * @param str
	 *            �����ݿ��ַ���
	 * @return String д�ļ��ַ���
	 */
	public String db2file(String str) {
		if (str == null)
			return str;
		if (str.length() == 0)
			return str;
		if (dbOutputCode.equals(fileWrCode))
			return str;
		try {
			str = new String(str.getBytes(dbOutputCode), fileWrCode);
		} catch (UnsupportedEncodingException e) {
			log.error("charSet.inputdataConvert exception", e);
		}
		
		return str;
	}
	
}