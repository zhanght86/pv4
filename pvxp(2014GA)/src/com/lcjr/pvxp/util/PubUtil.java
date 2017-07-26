package com.lcjr.pvxp.util;

import java.io.File;
import java.net.URLDecoder;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lcjr.pvxp.bean.PropertiesBean;
import com.lcjr.pvxp.bean.interfac.IPropertiesDAO;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> Pvxp公共方法库
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
 * @version 1.0 2004/12/15
 */
public class PubUtil {

	private static Logger log = Logger.getLogger(PubUtil.class.getName());

	private IPropertiesDAO propertiesDAO = new PropertiesBean();

	private static Map<String, String> map = new HashMap<String, String>();

	private static final Config myConfig;

	private static final String filepath;

	private static final String fileinencoding;

	private static final String webcharset;

	static {
		myConfig = new Config("pvxp.properties");
		filepath = myConfig.getProperty("filepath");
		fileinencoding = myConfig.getProperty("fileinencoding");
		webcharset = myConfig.getProperty("webcharset");
		log.info("filepath==>" + filepath + " /n fileinencoding==>" + fileinencoding + "  /n  webcharset==>" + webcharset);
	}

	public void getAllProperties() {
		try {

			Map<String, String> localMap = propertiesDAO.getAllProperties();
			setMap(localMap);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			log.error("ReadConfig db", e);
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		System.out.println("rmi服务端启动");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		IPropertiesDAO dao = (IPropertiesDAO) ctx.getBean("propertiesBean");

		try {
			dao.getAllProperties();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("rmi服务端启动完成。。。");
	}

	// ///↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param section
	 * @param keyname
	 * @param defstr
	 * @param filename
	 * @return
	 */
	public String ReadConfig(String section, String keyname, String defstr, String filename) {
		Map<String, String> map = getMap();

		String str = map.get(filename + section + keyname);
		if (str == null || str.equals("")) {
			str = ReadConfig1(section, keyname, defstr, filename);
		} else {
			log.info("从数据库中找到keyname的值：" + str);
		}

		return str;
	}

	/**
	 * <p>
	 * 读取配置文件
	 * </p>
	 * 
	 * @param section
	 *            小节名
	 * @param keyname
	 *            变量名
	 * @param defstr
	 *            默认值
	 * @param filename
	 *            文件名
	 * @return String
	 */
	public String ReadConfig1(String section, String keyname, String defstr, String filename) {
		section = "[" + section + "]";

		try {
			String path = null;
			path = URLDecoder.decode(filepath, "utf-8");
			int index = path.lastIndexOf("pvxp");
			path = path.substring(0, index + 4) + "/ini/";
			BufferedRandomAccessFile m_file = new BufferedRandomAccessFile(path + filename, "r", 2048);
			// log.error("filepath:"+filepath+"  filename:"+filename);
			long filelen = m_file.length();
			// log.error("filelen:"+filelen);
			byte[] b = new byte[(int) filelen];

			try {
				m_file.readFully(b, 0, (int) filelen);
			} catch (Exception e) {
				log.warn("catch报错--ReadConfig()", e);
				return null;
			}

			String filecont = unicode2native(new String(b, 0, (int) filelen));

			int secpos = 0;
			try {
				secpos = filecont.indexOf(section);
			} catch (Exception e) {
				log.warn("catch报错--ReadConfig()", e);
				return null;
			}

			if (secpos == -1) {
				m_file.close();
				return null;
			}

			m_file.seek((long) secpos + section.length());

			String linestr = "";
			while (m_file.getFilePointer() != filelen) {
				linestr = m_file.readLine().trim();

				if (linestr.length() > 0) {
					// note
					if (linestr.charAt(0) == '#')
						continue;

					// clear note
					int len0 = linestr.indexOf('#');
					if (len0 != -1) {
						linestr = linestr.substring(0, len0).trim();
					}

					// next section
					if (linestr.charAt(0) == '[') {
						break;
					}

					// get keyvaule
					int len1 = linestr.indexOf('=');
					if (len1 != -1) {
						String tmp = linestr.substring(0, len1).trim();
						if (tmp.equals(keyname)) {
							String keyvalue = linestr.substring(len1 + 1, linestr.length()).trim();

							m_file.close();
							keyvalue = native2unicode(keyvalue);
							return file2gb(keyvalue);
						}
					}
				}
			}

			m_file.close();
			return defstr;
		} catch (Exception e) {
			log.warn("catch报错--ReadConfig()", e);
			return null;
		}
	}

	/**
	 * <p>
	 * 读取站点中的配置文件
	 * </p>
	 * 
	 * @param section
	 *            小节名
	 * @param keyname
	 *            变量名
	 * @param defstr
	 *            默认值
	 * @param path
	 *            从站点开始的相对路径名
	 * @param filename
	 *            文件名
	 * @return String
	 */
	public String ReadConfig(String section, String keyname, String defstr, String path, String filename) {
		section = "[" + section + "]";

		log.info("ReadConfig(  String path)");
		try {
			BufferedRandomAccessFile m_file = new BufferedRandomAccessFile(filepath + "/" + path + "/" + filename, "r", 2048);
			long filelen = m_file.length();
			byte[] b = new byte[(int) filelen];

			try {
				m_file.readFully(b, 0, (int) filelen);
			} catch (Exception e) {
				log.warn("catch报错--ReadConfig()", e);
				return null;
			}

			String filecont = unicode2native(new String(b, 0, (int) filelen));

			int secpos = 0;
			try {
				secpos = filecont.indexOf(section);
			} catch (Exception e) {
				log.warn("catch报错--ReadConfig()", e);
				return null;
			}

			if (secpos == -1) {
				m_file.close();
				return null;
			}

			m_file.seek((long) secpos + section.length());

			String linestr = "";
			while (m_file.getFilePointer() != filelen) {
				linestr = m_file.readLine().trim();

				if (linestr.length() > 0) {
					// note
					if (linestr.charAt(0) == '#')
						continue;

					// clear note
					int len0 = linestr.indexOf('#');
					if (len0 != -1) {
						linestr = linestr.substring(0, len0).trim();
					}

					// next section
					if (linestr.charAt(0) == '[') {
						break;
					}

					// get keyvaule
					int len1 = linestr.indexOf('=');
					if (len1 != -1) {
						String tmp = linestr.substring(0, len1).trim();
						if (tmp.equals(keyname)) {
							String keyvalue = linestr.substring(len1 + 1, linestr.length()).trim();

							m_file.close();
							keyvalue = native2unicode(keyvalue);
							return file2gb(keyvalue);
						}
					}
				}
			}

			m_file.close();
			return defstr;
		} catch (Exception e) {
			log.warn("catch报错--ReadConfig()", e);
			return null;
		}
	}

	/**
	 * <p>
	 * 从BufferedRandomAccessFile对象读取配置文件，不打开关闭文件
	 * </p>
	 * 
	 * @param section
	 *            小节名
	 * @param keyname
	 *            变量名
	 * @param defstr
	 *            默认值
	 * @param filename
	 *            文件名
	 * @return String
	 */
	public String ReadConfig(String section, String keyname, String defstr, BufferedRandomAccessFile m_file) {
		log.info("ReadConfig(  section="+section+",  keyname="+keyname+", defstr="+defstr+"");
		if (m_file == null)
			return null;
		section = "[" + section + "]";

		try {
			long filelen = m_file.length();
			byte[] b = new byte[(int) filelen];

			StringBuffer input = new StringBuffer();
			int c = -1;
			while ((c = m_file.read()) != -1) {
				input.append((char) c);
			}
			
			
			
//			try {
//				m_file.readFully(b, 0, (int) filelen);
//			} catch (Exception e) {
//				log.warn("catch报错--ReadConfig()", e);
//				return null;
//			}

//			String filecont = native2unicode(new String(b, 0, (int) filelen));
			String filecont = input.toString();
			int secpos = 0;
			try {
				secpos = filecont.indexOf(section);
			} catch (Exception e) {
				log.warn("catch报错--ReadConfig()", e);
				return null;
			}

			if (secpos == -1) {
				return null;
			}

			m_file.seek((long) secpos + section.length());

			String linestr = "";
			while (m_file.getFilePointer() != filelen) {
				linestr = m_file.readLine().trim();
				linestr=native2unicode(linestr);
				if (linestr.length() > 0) {
					// note
					if (linestr.charAt(0) == '#')
						continue;

					// clear note
					int len0 = linestr.indexOf('#');
					if (len0 != -1) {
						linestr = linestr.substring(0, len0).trim();
					}

					// next section
					if (linestr.charAt(0) == '[') {
						break;
					}

					// get keyvaule
					int len1 = linestr.indexOf('=');
					if (len1 != -1) {
						String tmp = linestr.substring(0, len1).trim();
						if (tmp.equals(keyname)) {
							String keyvalue = linestr.substring(len1 + 1, linestr.length()).trim();
//							keyvalue = native2unicode(keyvalue);
							return file2gb(keyvalue);
						}
					}
				}
			}
			return defstr;
		} catch (Exception e) {
			log.warn("catch报错--ReadConfig()", e);
			return null;
		} finally {
			try {
				m_file.seek(0);
			} catch (Exception e) {
				log.error("catch报错--ReadConfig()", e);
			}
		}
	}

	// ///↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 获得文件路径
	 * 
	 * @return 文件路径
	 */
	public String getFilepath() {
		return this.filepath;
	}

	/**
	 * 如果编码不统一
	 * 
	 * @param str
	 *            要转换编码方式的字符串
	 * @return 编码统一后的字符串
	 */
	public String file2gb(String str) {
		if (str == null)
			return null;
		try {
			if (fileinencoding.equals(webcharset)) {
			} else {
				// 转换字符编码方式
				str = new String(str.getBytes(fileinencoding), webcharset);
			}
		} catch (Exception e) {
			log.error("catch报错--file2gb", e);
		}
		return str;
	}

	/**
	 * 格式化字符串
	 * 
	 * @param str
	 *            原字符串
	 * @param len
	 *            格式化后长度
	 * @param flag
	 *            补位方向：0-右补 其它-左补
	 * @param c
	 *            补充的字符
	 * @return String 格式化后的字符串
	 */
	public static String strFormat(String str, int len, int flag, char rechar) {
		int i = 0;
		int len1 = 0;
		String fstr = "";
		String c = String.valueOf(rechar);
		if (str == null) {
			for (i = 0; i < len; i++) {
				fstr += c;
			}
		} else {
			len1 = str.length();

			if (len1 >= len) {
				return str;
			} else {
				if (flag == 0) {
					for (i = len1; i < len; i++) {
						str = str + c;
					}
				} else {
					for (i = len1; i < len; i++) {
						str = c + str;
					}
				}
			}
		}

		return str;
	}

	/**
	 * 转换字符编码
	 * 
	 * @param s
	 * @return
	 */
	public static String native2unicode(String s) {
		if (s == null || s.length() == 0) {
			return s;
		}

		byte[] buffer = new byte[s.length()];

		for (int i = 0; i < s.length(); i++) {
			buffer[i] = (byte) s.charAt(i);
		}

		return new String(buffer);
	}

	/**
	 * 转换字符编码
	 */

	public static String unicode2native(String s) {
		if (s == null || s.length() == 0) {
			return s;
		}

		char[] buffer = new char[s.length() * 2];
		char c;
		int j = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) >= 0x100) {
				c = s.charAt(i);
				try {
					byte[] buf = ("" + c).getBytes();
					buffer[j++] = (char) buf[0];
					buffer[j++] = (char) buf[1];
				} catch (Exception e) {

				}
			} else {
				buffer[j++] = s.charAt(i);
			}
		}

		return new String(buffer, 0, j);
	}

	/**
	 * <p>
	 * 处理Null
	 * </p>
	 * 
	 * @param str
	 *            输入String
	 * @return String 如果str == null 返回""，否则返回str原值
	 */
	public String dealNull(String str) {
		String returnstr = null;
		if (str == null) {
			returnstr = "";
		} else {
			returnstr = str;
		}
		return returnstr;
	}

	/**
	 * <p>
	 * 获取当前日期之后(前)若干天的日期
	 * </p>
	 * 
	 * @param last
	 *            天数差值，负数为之前，正数为之后
	 * @return String YYYYMMDD格式的日期
	 */
	public String getOtherDate(int last) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String str = getNowDate(1);
		StringBuffer stringbuffer = new StringBuffer("");
		Date dt = sdf.parse(str, new ParsePosition(0));
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.DATE, last);// 你要加减的日期
		Date dt1 = rightNow.getTime();
		String reStr = (sdf.format(dt1, stringbuffer, new FieldPosition(0))).toString();
		return reStr;
	}

	/**
	 * <p>
	 * 获取指定日期之后(前)若干天的日期
	 * </p>
	 * 
	 * @param dtstr
	 *            指定日期，格式YYYYMMDD
	 * @param last
	 *            天数差值，负数为之前，正数为之后
	 * @return String YYYYMMDD格式的日期
	 */
	public String getOtherDate(String dtstr, int last) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String str = dtstr;
		StringBuffer stringbuffer = new StringBuffer("");
		Date dt = sdf.parse(str, new ParsePosition(0));
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.DATE, last);// 你要加减的日期
		Date dt1 = rightNow.getTime();
		String reStr = (sdf.format(dt1, stringbuffer, new FieldPosition(0))).toString();
		return reStr;
	}

	/**
	 * <p>
	 * 获取当前日期
	 * </p>
	 * 
	 * @param df
	 *            日期格式
	 * @return String df-1返回格式如20041215 df-2返回格式如2004/12/15 df-3返回格式如2004-12-15
	 *         其他返回2004:12:15
	 */
	public String getNowDate(int df) {
		String mydate = "", ystr = "", mstr = "", dstr = "";
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		Date trialTime = new Date();
		calendar.setTime(trialTime);

		int yint = calendar.get(Calendar.YEAR);
		ystr = String.valueOf(yint);

		int mint = 1 + calendar.get(Calendar.MONTH);
		if (mint < 10)
			mstr = "0" + mint;
		else
			mstr = String.valueOf(mint);

		int dint = calendar.get(Calendar.DAY_OF_MONTH);
		if (dint < 10)
			dstr = "0" + dint;
		else
			dstr = String.valueOf(dint);

		if (df == 1)
			mydate = ystr + mstr + dstr;
		else if (df == 2)
			mydate = ystr + "/" + mstr + "/" + dstr;
		else if (df == 3)
			mydate = ystr + "-" + mstr + "-" + dstr;
		else
			mydate = ystr + ":" + mstr + ":" + dstr;

		return mydate;
	}

	/**
	 * <p>
	 * 获取当前时间
	 * </p>
	 * 
	 * @return String 返回格式如12:05:34
	 */
	public String getNowTime() {
		String mydate = "", hstr = "", mmstr = "", sstr = "";
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		Date trialTime = new Date();
		calendar.setTime(trialTime);

		int hint = calendar.get(Calendar.HOUR_OF_DAY);
		if (hint < 10)
			hstr = "0" + hint;
		else
			hstr = String.valueOf(hint);

		int mmint = calendar.get(Calendar.MINUTE);
		if (mmint < 10)
			mmstr = "0" + mmint;
		else
			mmstr = String.valueOf(mmint);

		int sint = calendar.get(Calendar.SECOND);
		if (sint < 10)
			sstr = "0" + sint;
		else
			sstr = String.valueOf(sint);

		mydate = hstr + ":" + mmstr + ":" + sstr;
		return mydate;
	}

	/**
	 * <p>
	 * 获取当前时间
	 * </p>
	 * 
	 * @return String 返回格式如120534
	 */
	public String getNowTime2() {
		String mydate = "", hstr = "", mmstr = "", sstr = "";
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		Date trialTime = new Date();
		calendar.setTime(trialTime);

		int hint = calendar.get(Calendar.HOUR_OF_DAY);
		if (hint < 10)
			hstr = "0" + hint;
		else
			hstr = String.valueOf(hint);

		int mmint = calendar.get(Calendar.MINUTE);
		if (mmint < 10)
			mmstr = "0" + mmint;
		else
			mmstr = String.valueOf(mmint);

		int sint = calendar.get(Calendar.SECOND);
		if (sint < 10)
			sstr = "0" + sint;
		else
			sstr = String.valueOf(sint);

		mydate = hstr + mmstr + sstr;
		return mydate;
	}

	/**
	 * <p>
	 * 获取当前日期和时间
	 * </p>
	 * 
	 * @return String 返回格式如2004/12/15/12:05:34
	 */
	public String getNowDateTime() {
		String mydate = "", ystr = "", mstr = "", dstr = "", hstr = "", mmstr = "", sstr = "";
		Calendar calendar = null;
		calendar = Calendar.getInstance();
		Date trialTime = new Date();
		calendar.setTime(trialTime);

		int yint = calendar.get(Calendar.YEAR);
		ystr = String.valueOf(yint);

		int mint = 1 + calendar.get(Calendar.MONTH);
		if (mint < 10)
			mstr = "0" + mint;
		else
			mstr = String.valueOf(mint);

		int dint = calendar.get(Calendar.DAY_OF_MONTH);
		if (dint < 10)
			dstr = "0" + dint;
		else
			dstr = String.valueOf(dint);

		int hint = calendar.get(Calendar.HOUR_OF_DAY);
		if (hint < 10)
			hstr = "0" + hint;
		else
			hstr = String.valueOf(hint);

		int mmint = calendar.get(Calendar.MINUTE);
		if (mmint < 10)
			mmstr = "0" + mmint;
		else
			mmstr = String.valueOf(mmint);

		int sint = calendar.get(Calendar.SECOND);
		if (sint < 10)
			sstr = "0" + sint;
		else
			sstr = String.valueOf(sint);

		mydate = ystr + "/" + mstr + "/" + dstr + "/" + hstr + ":" + mmstr + ":" + sstr;
		return mydate;
	}

	/**
	 * <p>
	 * 从Cookie获取指定名字的值
	 * </p>
	 * 
	 * @param cookiename
	 *            要获取的变量名
	 * @param request
	 *            HttpServletRequest对象
	 * @return String 对应的Cookie值
	 */
	public String getValueFromCookie(String cookiename, HttpServletRequest request) {
		String retstr = null;
		try {
			Cookie[] cookies = request.getCookies();
			Cookie thisCookie = null;

			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					thisCookie = cookies[i];

					if (thisCookie.getName().equals(cookiename)) {
						retstr = thisCookie.getValue();
						break;
					}
				}
			}
		} catch (Exception e) {
			log.warn("catch报错--getValueFromCookie()", e);
			retstr = null;
		}
		return retstr;
	}

	/**
	 * <p>
	 * 替换字符串中的子字符串
	 * </p>
	 * 
	 * @param str
	 *            源串
	 * @param substr
	 *            被替换的子字符串
	 * @param restr
	 *            新的子字符串
	 * @return String 替换后的字符串
	 */
	public String replace(String str, String substr, String restr) {
		String[] tmp = split(str, substr);
		String returnstr = null;

		if (tmp.length != 0) {
			returnstr = tmp[0];
			for (int i = 0; i < tmp.length - 1; i++)
				returnstr = dealNull(returnstr) + restr + tmp[i + 1];
		}

		return dealNull(returnstr);
	}

	/**
	 * 
	 * @param source
	 * @param div
	 * @return
	 */
	public String[] split(String source, String div) {
		int arynum = 0, intIdx = 0, intIdex = 0, div_length = div.length();
		if (source.compareTo("") != 0) {
			if (source.indexOf(div) != -1) {
				intIdx = source.indexOf(div);
				for (int intCount = 1;; intCount++) {
					if (source.indexOf(div, intIdx + div_length) != -1) {
						intIdx = source.indexOf(div, intIdx + div_length);
						arynum = intCount;
					} else {
						arynum += 2;
						break;
					}
				}
			} else
				arynum = 1;
		} else
			arynum = 0;

		intIdx = 0;
		intIdex = 0;
		String[] returnStr = new String[arynum];

		if (source.compareTo("") != 0) {
			if (source.indexOf(div) != -1) {
				intIdx = (int) source.indexOf(div);
				returnStr[0] = (String) source.substring(0, intIdx);

				for (int intCount = 1;; intCount++) {
					if (source.indexOf(div, intIdx + div_length) != -1) {
						intIdex = (int) source.indexOf(div, intIdx + div_length);
						returnStr[intCount] = (String) source.substring(intIdx + div_length, intIdex);

						intIdx = (int) source.indexOf(div, intIdx + div_length);
					} else {
						returnStr[intCount] = (String) source.substring(intIdx + div_length, source.length());
						break;
					}
				}
			} else {
				returnStr[0] = (String) source.substring(0, source.length());
				return returnStr;
			}
		} else {
			return returnStr;
		}

		return returnStr;
	}

	/**
	 * <p>
	 * 判断文件是否存在
	 * </p>
	 * 
	 * @param filename
	 *            文件全名
	 * @return boolean
	 */
	public boolean iFexistfile(String filename) {
		File myfile = new File(filename);
		return myfile.exists();
	}

	/**
	 * <p>
	 * 写文件
	 * </p>
	 * 
	 * @param str
	 *            文件内容
	 * @param filepath
	 *            路径
	 * @param filename
	 *            文件名
	 * @return int 0：成功 -1：失败
	 */
	public int writeFile(String str, String filepath, String filename) {
		try {
			WriteFile writefile = new WriteFile();

			File curfile = new File(filepath);
			filename = curfile.getAbsolutePath() + "/" + filename;

			writefile.setPath(filename);

			writefile.setSomething(str);
			writefile.writeSomething();
			return 0;
		} catch (Exception e) {
			log.warn("catch报错--writeFile()", e);
			return -1;
		}
	}

	/**
	 * <p>
	 * 读文件
	 * </p>
	 * 
	 * @param filepath
	 *            路径
	 * @param filename
	 *            文件名
	 * @return String
	 */
	public String ReadFile(String filepath, String filename) {
		String filecontxt = null;

		try {
			File curfile = new File(filepath);
			filename = curfile.getAbsolutePath() + "/" + filename;

			ReadFile readfile = new ReadFile(filename);
			while (readfile.nextRecord() != -1) {
				// 依次将文件读出;
			}
			filecontxt = readfile.getFilestr();

			return filecontxt;
		} catch (Exception e) {
			log.warn("catch报错--ReadFile()", e);
			return filecontxt;
		}
	}

	/**
	 * <p>
	 * 判断字符串是否为合法年月
	 * </p>
	 * 
	 * @param str
	 *            字符串
	 * @return boolean
	 */
	public boolean isYYYYMM(String str) {
		if (!isInteger(str))
			return false;
		if (str.length() != 6)
			return false;
		try {
			int month = Integer.parseInt(str.substring(4));
			if (month > 12 || month < 1)
				return false;
		} catch (Exception e) {
			log.warn("catch报错--isYYYYMM()", e);
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * 判断字符串是否为整数
	 * </p>
	 * 
	 * @param str
	 *            字符串
	 * @return boolean
	 */
	public boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			log.warn("catch报错--isInteger()", e);
			return false;
		}
	}

	/**
	 * <p>
	 * 获取当前前一个月（YYYYMM）
	 * </p>
	 * 
	 * @return String
	 */
	public String getPreMonth() {
		String currentymd = getNowDate(1);
		String currentyear = currentymd.substring(0, 4);
		String currentmonth = currentymd.substring(4, 6);
		int intpreyear = Integer.parseInt(currentyear);
		int intpremonth = Integer.parseInt(currentmonth) - 1;
		if (intpremonth == 0) {
			intpremonth = 12;
			intpreyear--;
		}
		String preyear = String.valueOf(intpreyear);
		String premonth = String.valueOf(intpremonth);
		if (premonth.length() == 1)
			premonth = "0" + premonth;

		return (preyear + premonth);
	}

	/**
	 * <p>
	 * 获取当前前一个月前一个月（YYYYMM）
	 * </p>
	 * 
	 * @return String
	 */
	public String getBeforePreMonth() {
		String currentymd = getPreMonth();
		String currentyear = currentymd.substring(0, 4);
		String currentmonth = currentymd.substring(4, 6);
		int intpreyear = Integer.parseInt(currentyear);
		int intpremonth = Integer.parseInt(currentmonth) - 1;
		if (intpremonth == 0) {
			intpremonth = 12;
			intpreyear--;
		}
		String preyear = String.valueOf(intpreyear);
		String premonth = String.valueOf(intpremonth);
		if (premonth.length() == 1)
			premonth = "0" + premonth;

		return (preyear + premonth);
	}

	/**
	 * <p>
	 * 将Double形科学计数法转换成字符串
	 * </p>
	 * 
	 * @param d
	 *            double
	 * @param prec
	 *            保留小数位
	 * @return String
	 */
	public static String double2String(double d, int prec) {
		StringBuffer buf;

		boolean negative = false;

		d = ((double) Math.round(d * Math.pow(10, prec))) / Math.pow(10, prec);

		if (d < 0) {
			negative = true;
			d = Math.abs(d);
		}

		long wholeNum = (long) d;
		double frac = d - (double) wholeNum;

		buf = new StringBuffer();

		if (negative) {
			buf.append('-');
		}

		buf.append(wholeNum);

		if (prec > 0) {
			buf.append('.');
			wholeNum = (long) (frac * Math.pow(10, prec));
			while (--prec >= 0) {
				int digit = (int) (wholeNum / (long) Math.pow(10, prec));
				wholeNum -= (long) digit * (long) Math.pow(10, prec);
				buf.append(digit);
			}
		}

		return buf.toString();
	}

	/**
	 * <p>
	 * 校验是否为8位合法日期
	 * </p>
	 * 
	 * @param datestr
	 *            输入字符串
	 * @return boolean
	 */
	public boolean isDate8(String datestr) {
		if (datestr == null)
			return false;
		if (datestr.length() != 8)
			return false;
		else {
			String tmpy = "";
			String tmpm = "";
			String tmpd = "";
			int status = 0;

			tmpy = datestr.substring(0, 4);
			tmpm = datestr.substring(4, 6);
			tmpd = datestr.substring(6, 8);

			if ((tmpy.length() != 4) || (tmpm.length() != 2) || (tmpd.length() != 2))
				return false;

			int year = Integer.parseInt(tmpy);
			int month = Integer.parseInt(tmpm);
			int day = Integer.parseInt(tmpd);

			if (!((1 <= month) && (12 >= month) && (31 >= day) && (1 <= day)))
				return false;

			if (!((year % 4) == 0) && (month == 2) && (day == 29))
				return false;

			if ((year > 2100) || (year < 1950))
				return false;

			if ((month <= 7) && ((month % 2) == 0) && (day >= 31))
				return false;

			if ((month >= 8) && ((month % 2) == 1) && (day >= 31))
				return false;

			if ((month == 2) && (day == 30))
				return false;

			return true;
		}
	}

	/**
	 * <p>
	 * 打开文件BufferedRandomAccessFile对象
	 * </p>
	 * 
	 * @param path
	 *            从站点开始的相对路径名
	 * @param filename
	 *            文件名
	 * @return BufferedRandomAccessFile
	 */
	public BufferedRandomAccessFile openFile(String path, String filename) {

		try {
			String fp = null;
			fp = URLDecoder.decode(filepath, "utf-8");
			int index = fp.lastIndexOf("pvxp");
			fp = fp.substring(0, index + 4);
			BufferedRandomAccessFile m_file = new BufferedRandomAccessFile(fp + "/" + path + "/" + filename, "r", 2048);
			/*
			 * if(m_file==null){ log.error("can't find the file:"+
			 * m_file+"  filename:"+filename); }
			 */
			log.error("openFile==" + filepath + "/" + path + "/" + filename);
			return m_file;
		} catch (Exception e) {
			log.warn("catch报错--BufferedRandomAccessFile()", e);
			return null;
		}

	}

	/**
	 * <p>
	 * 将配置文件转存为二维数组<br>
	 * 路径为"ini"，默认值""
	 * </p>
	 * 
	 * @param filename
	 *            文件名
	 * @param section
	 *            小节名
	 * @param keynum
	 *            变量个数名
	 * @param keyname
	 *            变量名
	 * @param splitflag
	 *            值的分割符
	 * 
	 * @return String[][]
	 */
	public String[][] Ini2Array(String filename, String section, String keynum, String keyname, String splitflag) {

		return Ini2Array("ini", filename, section, keynum, keyname, splitflag, "");
	}

	/**
	 * <p>
	 * 将配置文件转存为二维数组
	 * </p>
	 * 
	 * @param path
	 *            站点相对路经
	 * @param filename
	 *            文件名
	 * @param section
	 *            小节名
	 * @param keynum
	 *            变量个数名
	 * @param keyname
	 *            变量名
	 * @param splitflag
	 *            值的分割符
	 * @param defstr
	 *            默认值
	 * 
	 * @return String[][]
	 */
	public String[][] Ini2Array(String path, String filename, String section, String keynum, String keyname, String splitflag, String defstr) {

		if ((path == null) || (filename == null) || (section == null) || (keynum == null) || (keyname == null) || (splitflag == null) || (defstr == null)) {
			return null;
		}
		log.error("Ini2Array--filepath:" + path + "  filename:" + filename);
		BufferedRandomAccessFile tmpfile = openFile(path, filename);
		if (tmpfile == null) {
			log.error("tmpfile == null");
			return null;
		}

		try {

			int ttnum = Integer.parseInt(ReadConfig(section, keynum, "0", tmpfile).trim());
			int num = 0;

			String tmpstr = dealNull(ReadConfig(section, keyname + "1", defstr, tmpfile)).trim();
			StringTokenizer wb = new StringTokenizer(tmpstr, splitflag);
			num = wb.countTokens();
			String[][] reArray = new String[ttnum][num];

			for (int i = 0; i < ttnum; i++) {
				tmpstr = dealNull(ReadConfig(section, keyname + String.valueOf(i + 1), defstr, tmpfile)).trim();
				if (tmpstr.length() > 0) {
					if (!splitflag.equals("")) {
						int pos = tmpstr.indexOf(splitflag);
						if (pos != -1) {
							wb = new StringTokenizer(tmpstr, splitflag);
							while (wb.hasMoreTokens()) {
								for (int j = 0; j < num; j++) {
									reArray[i][j] = wb.nextToken();
								}
							}
						}
					} else {
						reArray[i][0] = tmpstr;
					}
				}
			}
			return reArray;
		} catch (Exception e) {
			log.warn("catch报错--Ini2Array()", e);
			return null;
		} finally {
			try {
				tmpfile.close();
			} catch (Exception e) {
				log.error("catch报错--Ini2Array()", e);
			}
		}
	}

	/**
	 * 将字符串转换成长整型
	 * 
	 * @param str
	 * @return
	 */
	public long str2long(String str) {

		int tmppos = -1;
		String tmppr2 = "";

		long strsum = 0;

		if (str != null) {
			tmppos = str.indexOf(".");

			if (tmppos != -1) {
				// wukp1129 保证数字拥有两位小数
				while (tmppos + 3 > str.length()) {
					str += "0";
				}
				while (tmppos + 3 < str.length()) {
					str = str.substring(0, str.length() - 1);
				}

				tmppr2 = str.substring(0, tmppos) + str.substring(tmppos + 1, str.length());
			} else {
				tmppr2 = str + "00";
			}
		}

		strsum = Long.parseLong(tmppr2);

		return strsum;
	}

	/**
	 * 长整型转换为字符串 缩小100倍 将金额装换成字符串并处理小数点位数
	 * 
	 * @param str
	 * @return
	 */
	public String long2str(long strsum) {

		String tmppr1 = "";
		String tmppr2 = "";
		int tmplen = 0;

		tmppr1 = String.valueOf(strsum);
		tmplen = tmppr1.length();
		if (tmplen == 1) {
			tmppr2 = "0.0" + tmppr1;
		} else if (tmplen == 2) {
			tmppr2 = "0." + tmppr1;
		} else {
			tmppr2 = tmppr1.substring(0, tmplen - 2) + "." + tmppr1.substring(tmplen - 2);
		}

		return tmppr2;
	}

	/**
	 * 长整型转换为字符串 缩小10000倍(应对两个长整型相乘的情况) 将金额装换成字符串并处理小数点位数
	 * 
	 * @param str
	 * @return 保留2位小数的 字符型结果
	 */
	public String long2str10000(long strsum) {

		String tmppr1 = "";
		String tmppr2 = "";
		int tmplen = 0;

		try {
			tmppr1 = String.valueOf(strsum);
			tmplen = tmppr1.length();
			if (tmplen == 1) {
				tmppr2 = "0.00";
			} else if (tmplen == 2) {
				tmppr2 = "0.00";
			} else if (tmplen == 3) {
				tmppr2 = "0.0" + tmppr1.substring(0, 1);
			} else if (tmplen == 4) {
				tmppr2 = "0." + tmppr1.substring(0, 2);
			}

			else {
				tmppr2 = tmppr1.substring(0, tmplen - 4) + "." + tmppr1.substring(tmplen - 4, tmplen - 2);
			}
		} catch (Exception e) {
			log.error("catch报错--long2str10000()", e);
		}
		return tmppr2;
	}

	/**
	 * @return the map
	 */
	public static Map<String, String> getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public static void setMap(Map<String, String> map) {
		PubUtil.map = map;
	}
}