package com.lcjr.pvxp.util;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> 加密解密
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
 * @version 1.0 2011/02/16
 */
public class EncUtil {

	static { 

		System.loadLibrary("Enc");

	}

	public native static String EnDataPack(String ins, String pwd);

	public native static String UnDataPack(String ins, String pwd);

	

	/**
	 * 加密
	 * 
	 * @param instr
	 * @param passwd
	 * @return
	 */
	public static String enPack(String instr, String passwd) {
		// String outstr = "";
		// EncUtil enc = new EncUtil();
		// outstr = enc.EnDataPack(instr, passwd);
		// return outstr;

		return instr;
	}

	/**
	 * 解密
	 * 
	 * @param instr
	 * @param passwd
	 * @return
	 */
	public static String unPack(String instr, String passwd) {
		// String outstr = "";
		// EncUtil enc = new EncUtil();
		// outstr = enc.UnDataPack(instr, passwd);
		// return outstr;
		return instr;
	}

}