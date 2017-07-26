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
 * <b>Description:</b> 操作记录统计相关工具类
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2011
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.0 2011/03/08
 */
public class OperTrcdUtil {
	
	Logger log = Logger.getLogger("web.lcjr.pvxp.util.OperTrcdUtil.java");
	
	private PubUtil myPubUtil = new PubUtil();
	
	/**
	 * <p>
	 * 获取功能编号、名称二维数组
	 * </p>
	 * 
	 * @return String[][]
	 */
	public String[][] getOperTrcd() {
		BufferedRandomAccessFile tmpfile = myPubUtil.openFile("ini", "OperTrcdSta.ini");
		if (tmpfile == null)
			return null;
		try {
			int ttnum = Integer.parseInt(myPubUtil.ReadConfig("OperTrcd", "Num", "0", tmpfile).trim());
			String[][] reArray = new String[ttnum][2];
			for (int i = 0; i < ttnum; i++) {
				String tmpstr = myPubUtil.dealNull(myPubUtil.ReadConfig("OperTrcd", "trcd" + String.valueOf(i + 1), "", tmpfile)).trim();
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