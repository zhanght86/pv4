package com.lcjr.pvxp.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 读取ini配置文件类
 * 
 * @author 武坤鹏
 * 
 */
public class iniread {

	Logger log = Logger.getLogger(iniread.class.getName());

	private Properties iniProps;

	private String FILENAME;

	private static final Config myConfig;

	private static final String filepath;

	static {
		myConfig = new Config("pvxp.properties");
		filepath = myConfig.getProperty("filepath");
	}

	/**
	 * 
	 * @param filesname
	 */
	public iniread(String filesname) {
		iniProps = new Properties();
		FILENAME = filepath + "/ini/" + filesname;
		BufferedInputStream bufferedinputstream = null;
		try {
			bufferedinputstream = new BufferedInputStream(new FileInputStream(FILENAME));
			BufferedReader bf = new BufferedReader(new InputStreamReader(bufferedinputstream));
			iniProps.load(bf);
		} catch (Exception exception) {
			log.error("Can't find file");
		} finally {
			try {
				if (bufferedinputstream != null)
					bufferedinputstream.close();
			} catch (Exception exception2) {
				log.error("bufferedinputstream != null", exception2);
			}
		}
	}

	/**
	 * 
	 * @param temp
	 * @return
	 */
	public synchronized String getIni(String temp) {
		String tmp = "";
		try {
			tmp = (String) iniProps.getProperty(temp);
			tmp = new String(tmp.getBytes(), "UTF-8");
			log.error("");
		} catch (Exception e) {
			log.error("ERROR", e);
		}
		return tmp;
	}

}
