package com.lcjr.pvxp.util;

import com.lcjr.pvxp.util.*;
import java.io.*;
import java.util.*;
import java.beans.*;
import javax.servlet.*;
import java.lang.String;
import java.text.DateFormat;
import java.util.Enumeration;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import java.text.*;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> �豸����ͳ����ع�����
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
 * @version 1.0 2005/04/04
 */
public class DevErrorsUtil {
	
	Logger log = Logger.getLogger("web.lcjr.pvxp.util.DevErrorsUtil.java");
	
	
	private PubUtil myPubUtil = new PubUtil();
	
	
	
	
	/**
	 * <p>
	 * ��ȡ���豸���롢���ƶ�ά����
	 * </p>
	 * 
	 * @return String[][]
	 */
	public String[][] getSubDevice() {
		BufferedRandomAccessFile tmpfile = myPubUtil.openFile("ini", "DevErrSta.ini");
		if (tmpfile == null)
			return null;
		try {
			int ttnum = Integer.parseInt(myPubUtil.ReadConfig("SubDevice", "Num", "0", tmpfile).trim());
			String[][] reArray = new String[ttnum][2];
			for (int i = 0; i < ttnum; i++) {
				String tmpstr = myPubUtil.dealNull(myPubUtil.ReadConfig("SubDevice", "Device" + String.valueOf(i + 1), "", tmpfile)).trim();
				if (tmpstr.length() > 0) {
					int pos = tmpstr.indexOf(",");
					if (pos != -1) {
						reArray[i][0] = tmpstr.substring(0, pos);
						reArray[i][1] = tmpstr.substring(pos + 1);
					}
				}
			}
			return reArray;
		} catch (Exception e) {
			log.warn("WARN", e);
			return null;
		} finally {
			try {
				tmpfile.close();
			} catch (Exception e) {
				log.warn("WARN", e);
			}
		}
	}
	
	
	
	/**
	 * <p>
	 * ��ȡ���豸���롢���ƶ�ά���飬<font color='red'>ȥ��ͨѶ״̬</font>
	 * </p>
	 * 
	 * @return String[][]
	 */
	public String[][] getSubDevice2() {
		BufferedRandomAccessFile tmpfile = myPubUtil.openFile("ini", "DevErrSta.ini");
		if (tmpfile == null)
			return null;
		try {
			int ttnum = Integer.parseInt(myPubUtil.ReadConfig("SubDevice", "Num", "0", tmpfile).trim()) - 1;
			String[][] reArray = new String[ttnum][2];
			for (int i = 0; i < ttnum; i++) {
				String tmpstr = myPubUtil.dealNull(myPubUtil.ReadConfig("SubDevice", "Device" + String.valueOf(i + 2), "", tmpfile)).trim();
				if (tmpstr.length() > 0) {
					int pos = tmpstr.indexOf(",");
					if (pos != -1) {
						reArray[i][0] = tmpstr.substring(0, pos);
						reArray[i][1] = tmpstr.substring(pos + 1);
					}
				}
			}
			return reArray;
		} catch (Exception e) {
			log.warn("WARN", e);
			return null;
		} finally {
			try {
				tmpfile.close();
			} catch (Exception e) {
				log.warn("WARN", e);
			}
		}
	}
	
}