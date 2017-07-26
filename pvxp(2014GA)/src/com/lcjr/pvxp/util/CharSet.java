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
 * <b>Description:</b> 字符集转换
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author 杨旭
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
	 * 构造函数
	 */
	public CharSet() {
		
	}
	
	
	
	/**
	 * <p>
	 * 从WEB字符集转到数据库输入字符集
	 * </p>
	 * 
	 * @param str
	 *            WEB字符串
	 * @return String 插入数据库的字符串
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
	 * 从输入表单字符集转到数据库输入字符集
	 * </p>
	 * 
	 * @param str
	 *            form字符串
	 * @return String 插入数据库的字符串
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
	 * 从输入表单字符集转到数据库输入字符集
	 * </p>
	 * 
	 * @param str
	 *            form字符串
	 * @return String 插入数据库的字符串
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
	 * 从数据库输出字符集转到WEB字符集
	 * </p>
	 * 
	 * @param str
	 *            数据库输出字符串
	 * @return String WEB显示的字符串
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
	 * 表单字符集转为写文件字符集
	 * </p>
	 * 
	 * @param str
	 *            表单输出字符串
	 * @return String 写文件字符串
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
	 * 数据库输出字符集转为数据库输入字符集
	 * </p>
	 * 
	 * @param str
	 *            表数据库输出字符串
	 * @return String 数据库输入字符串
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
	 * 数据库字符集转为写文件字符集
	 * </p>
	 * 
	 * @param str
	 *            表数据库字符串
	 * @return String 写文件字符串
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