package com.lcjr.pvxp.util;

/**
 功能： 数据加解密处理
 作者： tz
 版本： 1.00 
 日期： Mar 25, 2013
 */

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 数据加解密处理
 * 
 */
public class MyDes {

	public String charSet = "GB2312";

	/**
	 * @return DES算法密钥
	 */
	public static byte[] generateKey() {
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();

			// 生成一个DES算法的KeyGenerator对象
			KeyGenerator kg = KeyGenerator.getInstance("DES");
			kg.init(sr);

			// 生成密钥
			SecretKey secretKey = kg.generateKey();

			// 获取密钥数据
			byte[] key = secretKey.getEncoded();

			return key;
		} catch (NoSuchAlgorithmException e) {
			System.err.println("DES算法，生成密钥出错!");
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 加密函数 DES加密
	 * 
	 * @param data
	 *            加密数据
	 * @param key
	 *            密钥
	 * @return 返回加密后的数据
	 */
	public static byte[] encrypt(byte[] data, byte[] key) {
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();

			// 从原始密钥数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);

			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);

			// using DES in ECB mode
			// "DES/ECB/PKCS5Padding" or "DES/ECB/NoPadding" or "DES"
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");

			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

			// 不足8的倍数 补0处理
			byte[] dataPad = byte2bytepad(data);

			// 执行加密操作
			byte encryptedData[] = cipher.doFinal(dataPad);

			return encryptedData;
		} catch (Exception e) {
			System.err.println("DES算法，加密数据出错!");
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 解密函数
	 * 
	 * @param data
	 *            解密数据
	 * @param key
	 *            密钥
	 * @return 返回解密后的数据
	 */
	public static byte[] decrypt(byte[] data, byte[] key) {
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();

			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);

			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);

			// using DES in ECB mode
			// "DES/ECB/PKCS5Padding" or "DES/ECB/NoPadding" or "DES" 跟加密的参数保持一致
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");

			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);

			// 正式执行解密操作
			byte decryptedData[] = cipher.doFinal(data);

			return decryptedData;
		} catch (Exception e) {
			System.err.println("DES算法，解密出错。");
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 二行制转16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		// 一个字节的数，转成16进制字符串
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			// 整数转成十六进制表示
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase(); // 转成大写
	}

	/**
	 * 16进制字符串转二行制
	 * 
	 * @param b
	 * @return
	 */
	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			// 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}

		return b2;
	}

	/**
	 * 对不足8的倍数的字节进行补0处理
	 * 
	 * @param b
	 * @return
	 */
	public static byte[] byte2bytepad(byte[] data) {
		byte[] retData = new byte[8];
		try {

			// System.out.println("data.length="+data.length);
			// 明文字节长度不是 8 的倍数的用 0 右补齐
			int mod = data.length % 8;
			byte[] zoreByte;

			zoreByte = "\0".getBytes("GB2312");

			if (mod == 0) {
				retData = data;
			} else {
				int sub = 8 - mod;
				byte[] temp = new byte[data.length + sub];
				for (int j = 0; j < data.length; j++) {
					temp[j] = data[j];
				}
				for (int i = 0; i < sub; i++) {
					temp[data.length + i] = zoreByte[0];
				}
				retData = temp;
			}
			// System.out.println("data.length="+data.length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return retData;
	}

	/**
	 * 
	 * 功能描述：对报文进行解密，去头尾，只生成交易报文
	 * 
	 * @param initKeyStr
	 *            解密密钥
	 * @param allByteData
	 *            密文数据
	 * @return 交易报文明文数据 修改日期：Mar 28, 2013 9:37:55 AM
	 * 
	 */
	public String baowenjiemi(String initKeyStr, byte[] allByteData) {

		// log.info("要进行解密的数据");
		// tool.printHexLog(allByteData);
		// 取头和尾
		// 长度：4个字节
		byte[] length = new byte[4];
		for (int i = 0; i < 4; i++) {
			length[i] = allByteData[i];
		}
		String lenthS;
		try {
			lenthS = new String(length, 0, 4, charSet);

			// log.info("lenthS=" + lenthS);
			int lengthInt = Integer.parseInt(lenthS);
			// 消息头：3个字节
			byte[] head = new byte[3];
			for (int i = 4; i < 7; i++) {
				head[i - 4] = allByteData[i];
			}
			String headS = new String(head, 0, 3, charSet);
			// log.info("headS=" + headS);
			// 交易码：4个字节
			byte[] trade = new byte[4];
			for (int i = 7; i < 11; i++) {
				trade[i - 7] = allByteData[i];
			}
			String tradeS = new String(trade, 0, 4, charSet);

			// 判断是否为签到交易，如是签到交易，其密钥为12345678进行解密处理
			if (tradeS.equals("1000"))
				initKeyStr = "12345678";

			// log.info("tradeS=" + tradeS);
			// 结束标志
			byte[] tail = new byte[3];
			for (int i = allByteData.length - 3; i < allByteData.length; i++) {
				tail[i - allByteData.length + 3] = allByteData[i];
			}
			String tailS = new String(tail, 0, 3, charSet);
			// log.info("tailS=" + tailS);
			// 消息体密文：总长度-14 个字节
			byte[] data = new byte[allByteData.length - 14];
			for (int i = 11; i < allByteData.length - 3; i++) {
				data[i - 11] = allByteData[i];
			}
			// log.info("解密前的密文数据");
			// tool.printHexLog(data);
			// 解密
			byte[] dataDecrypt = MyDes.decrypt(data, initKeyStr.getBytes(charSet));

			//

			// log.info("解密后的数据");
			// tool.printHexLog(dataDecrypt);
			// log.info(byte2hex(dataDecrypt));
			String dataS = new String(dataDecrypt, charSet);
			// System.out.println(dataS.length());

			// log.info("对发来的数据再次进行加密，以进行对比");
			//
			// byte[] csb=baowenjiami(initKeyStr,headS,tradeS,dataS,tailS);
			// tool.printHexLog(csb);

			dataS = dataS.substring(0, dataS.lastIndexOf("|") + 1);

			return dataS;

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * 功能描述：报文进行DES加密
	 * 
	 * @param key
	 *            密钥
	 * @param headstr
	 *            消息头
	 * @param tradestr
	 *            交易码
	 * @param datastr
	 *            应用数据
	 * @param tailStr
	 *            结束标志
	 * @return 返回加密后的数据 修改日期：Mar 28, 2013 10:14:39 AM
	 * 
	 */
	public byte[] baowenjiami(String key, String headstr, String tradestr, String datastr, String tailStr) {

		byte[] dataEnDes = null;

		// tool.printHexLog(datastr);

		// 签到交易用初始密钥进行加密处理
		if (tradestr.equals("1000"))
			key = "12345678";

		try {

			// 为报文体增加通讯头与结束标志
			// datastr=headstr+tradestr+datastr+tailStr;
			// int datalen=datastr.getBytes(charSet).length;
			// datastr=tool.str_l('0', "" + datalen, 4)+datastr;

			byte[] headByte = headstr.getBytes(charSet);
			byte[] tradeByte = tradestr.getBytes(charSet);
			byte[] tailByte = tailStr.getBytes(charSet);

			int datalen = datastr.getBytes(charSet).length;
			int bulen = datalen % 8;
			if (bulen != 0)
				datalen = datalen + 8 - bulen;

			int baowenleng = headByte.length + tradeByte.length + datalen + tailByte.length;

			String lengstr = str_l('0', "" + baowenleng, 4);
			byte[] lengthByte = lengstr.getBytes(charSet);

			// 加密报文部分
			dataEnDes = MyDes.encrypt(datastr.getBytes(charSet), key.getBytes(charSet));

			// 组织完整的消息数据
			int ln = lengthByte.length + headByte.length + tradeByte.length + dataEnDes.length + tailByte.length;
			byte[] allByteData = new byte[ln];
			for (int i = 0; i < ln; i++) {
				if (i < lengthByte.length) {
					allByteData[i] = lengthByte[i];
				} else if (i < (lengthByte.length + headByte.length)) {
					allByteData[i] = headByte[i - lengthByte.length];
				} else if (i < (lengthByte.length + headByte.length + tradeByte.length)) {
					allByteData[i] = tradeByte[i - lengthByte.length - headByte.length];
				} else if (i < (lengthByte.length + headByte.length + tradeByte.length + dataEnDes.length)) {
					allByteData[i] = dataEnDes[i - lengthByte.length - headByte.length - tradeByte.length];
				} else {
					allByteData[i] = tailByte[i - lengthByte.length - headByte.length - tradeByte.length - dataEnDes.length];
				}
			}

			// log.info("加密后的数据");
			// tool.printHexLog(allByteData);
			return allByteData;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 字符串左边补位
	 * 
	 * @param c
	 *            补位字符
	 * @param string
	 *            被补位字符
	 * @param i
	 *            补位后字符长度
	 * @return 返回补位后的字符串
	 */
	public String str_l(char c, String string, int i) {
		int length = string.length();
		String str = string;
		for (int j = length; j < i; j++) {
			str = c + str;
		}
		return str;
	}

	/**
	 * main函数测试程序
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String data = "11|33|44|22|55|";
		MyDes mydes = new MyDes();

		// 加密
		byte[] baowen = mydes.baowenjiami("12345878", "ABC", "1000", data, "***");
		System.out.println("生成加密结果长度=" + baowen.length);
		String isoString;
		try {
			isoString = new String(baowen, "ISO-8859-1");
			System.err.println("加密结果：" + isoString);

			// 解密
			String retdata = mydes.baowenjiemi("12345678", isoString.getBytes("ISO-8859-1"));
			System.out.println("解密结果：" + retdata);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
