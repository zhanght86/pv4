package com.lcjr.pvxp.util;

/**
 ���ܣ� ���ݼӽ��ܴ���
 ���ߣ� tz
 �汾�� 1.00 
 ���ڣ� Mar 25, 2013
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
 * ���ݼӽ��ܴ���
 * 
 */
public class MyDes {

	public String charSet = "GB2312";

	/**
	 * @return DES�㷨��Կ
	 */
	public static byte[] generateKey() {
		try {
			// DES�㷨Ҫ����һ�������ε������Դ
			SecureRandom sr = new SecureRandom();

			// ����һ��DES�㷨��KeyGenerator����
			KeyGenerator kg = KeyGenerator.getInstance("DES");
			kg.init(sr);

			// ������Կ
			SecretKey secretKey = kg.generateKey();

			// ��ȡ��Կ����
			byte[] key = secretKey.getEncoded();

			return key;
		} catch (NoSuchAlgorithmException e) {
			System.err.println("DES�㷨��������Կ����!");
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ���ܺ��� DES����
	 * 
	 * @param data
	 *            ��������
	 * @param key
	 *            ��Կ
	 * @return ���ؼ��ܺ������
	 */
	public static byte[] encrypt(byte[] data, byte[] key) {
		try {
			// DES�㷨Ҫ����һ�������ε������Դ
			SecureRandom sr = new SecureRandom();

			// ��ԭʼ��Կ���ݴ���DESKeySpec����
			DESKeySpec dks = new DESKeySpec(key);

			// ����һ���ܳ׹�����Ȼ��������DESKeySpecת����
			// һ��SecretKey����
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);

			// using DES in ECB mode
			// "DES/ECB/PKCS5Padding" or "DES/ECB/NoPadding" or "DES"
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");

			// ���ܳ׳�ʼ��Cipher����
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

			// ����8�ı��� ��0����
			byte[] dataPad = byte2bytepad(data);

			// ִ�м��ܲ���
			byte encryptedData[] = cipher.doFinal(dataPad);

			return encryptedData;
		} catch (Exception e) {
			System.err.println("DES�㷨���������ݳ���!");
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ���ܺ���
	 * 
	 * @param data
	 *            ��������
	 * @param key
	 *            ��Կ
	 * @return ���ؽ��ܺ������
	 */
	public static byte[] decrypt(byte[] data, byte[] key) {
		try {
			// DES�㷨Ҫ����һ�������ε������Դ
			SecureRandom sr = new SecureRandom();

			// ��ԭʼ�ܳ����ݴ���һ��DESKeySpec����
			DESKeySpec dks = new DESKeySpec(key);

			// ����һ���ܳ׹�����Ȼ��������DESKeySpec����ת����
			// һ��SecretKey����
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);

			// using DES in ECB mode
			// "DES/ECB/PKCS5Padding" or "DES/ECB/NoPadding" or "DES" �����ܵĲ�������һ��
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");

			// ���ܳ׳�ʼ��Cipher����
			cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);

			// ��ʽִ�н��ܲ���
			byte decryptedData[] = cipher.doFinal(data);

			return decryptedData;
		} catch (Exception e) {
			System.err.println("DES�㷨�����ܳ���");
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ������ת16�����ַ���
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		// һ���ֽڵ�����ת��16�����ַ���
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			// ����ת��ʮ�����Ʊ�ʾ
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase(); // ת�ɴ�д
	}

	/**
	 * 16�����ַ���ת������
	 * 
	 * @param b
	 * @return
	 */
	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("���Ȳ���ż��");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			// ��λһ�飬��ʾһ���ֽ�,��������ʾ��16�����ַ�������ԭ��һ�������ֽ�
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}

		return b2;
	}

	/**
	 * �Բ���8�ı������ֽڽ��в�0����
	 * 
	 * @param b
	 * @return
	 */
	public static byte[] byte2bytepad(byte[] data) {
		byte[] retData = new byte[8];
		try {

			// System.out.println("data.length="+data.length);
			// �����ֽڳ��Ȳ��� 8 �ı������� 0 �Ҳ���
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
	 * �����������Ա��Ľ��н��ܣ�ȥͷβ��ֻ���ɽ��ױ���
	 * 
	 * @param initKeyStr
	 *            ������Կ
	 * @param allByteData
	 *            ��������
	 * @return ���ױ����������� �޸����ڣ�Mar 28, 2013 9:37:55 AM
	 * 
	 */
	public String baowenjiemi(String initKeyStr, byte[] allByteData) {

		// log.info("Ҫ���н��ܵ�����");
		// tool.printHexLog(allByteData);
		// ȡͷ��β
		// ���ȣ�4���ֽ�
		byte[] length = new byte[4];
		for (int i = 0; i < 4; i++) {
			length[i] = allByteData[i];
		}
		String lenthS;
		try {
			lenthS = new String(length, 0, 4, charSet);

			// log.info("lenthS=" + lenthS);
			int lengthInt = Integer.parseInt(lenthS);
			// ��Ϣͷ��3���ֽ�
			byte[] head = new byte[3];
			for (int i = 4; i < 7; i++) {
				head[i - 4] = allByteData[i];
			}
			String headS = new String(head, 0, 3, charSet);
			// log.info("headS=" + headS);
			// �����룺4���ֽ�
			byte[] trade = new byte[4];
			for (int i = 7; i < 11; i++) {
				trade[i - 7] = allByteData[i];
			}
			String tradeS = new String(trade, 0, 4, charSet);

			// �ж��Ƿ�Ϊǩ�����ף�����ǩ�����ף�����ԿΪ12345678���н��ܴ���
			if (tradeS.equals("1000"))
				initKeyStr = "12345678";

			// log.info("tradeS=" + tradeS);
			// ������־
			byte[] tail = new byte[3];
			for (int i = allByteData.length - 3; i < allByteData.length; i++) {
				tail[i - allByteData.length + 3] = allByteData[i];
			}
			String tailS = new String(tail, 0, 3, charSet);
			// log.info("tailS=" + tailS);
			// ��Ϣ�����ģ��ܳ���-14 ���ֽ�
			byte[] data = new byte[allByteData.length - 14];
			for (int i = 11; i < allByteData.length - 3; i++) {
				data[i - 11] = allByteData[i];
			}
			// log.info("����ǰ����������");
			// tool.printHexLog(data);
			// ����
			byte[] dataDecrypt = MyDes.decrypt(data, initKeyStr.getBytes(charSet));

			//

			// log.info("���ܺ������");
			// tool.printHexLog(dataDecrypt);
			// log.info(byte2hex(dataDecrypt));
			String dataS = new String(dataDecrypt, charSet);
			// System.out.println(dataS.length());

			// log.info("�Է����������ٴν��м��ܣ��Խ��жԱ�");
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
	 * �������������Ľ���DES����
	 * 
	 * @param key
	 *            ��Կ
	 * @param headstr
	 *            ��Ϣͷ
	 * @param tradestr
	 *            ������
	 * @param datastr
	 *            Ӧ������
	 * @param tailStr
	 *            ������־
	 * @return ���ؼ��ܺ������ �޸����ڣ�Mar 28, 2013 10:14:39 AM
	 * 
	 */
	public byte[] baowenjiami(String key, String headstr, String tradestr, String datastr, String tailStr) {

		byte[] dataEnDes = null;

		// tool.printHexLog(datastr);

		// ǩ�������ó�ʼ��Կ���м��ܴ���
		if (tradestr.equals("1000"))
			key = "12345678";

		try {

			// Ϊ����������ͨѶͷ�������־
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

			// ���ܱ��Ĳ���
			dataEnDes = MyDes.encrypt(datastr.getBytes(charSet), key.getBytes(charSet));

			// ��֯��������Ϣ����
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

			// log.info("���ܺ������");
			// tool.printHexLog(allByteData);
			return allByteData;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * �ַ�����߲�λ
	 * 
	 * @param c
	 *            ��λ�ַ�
	 * @param string
	 *            ����λ�ַ�
	 * @param i
	 *            ��λ���ַ�����
	 * @return ���ز�λ����ַ���
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
	 * main�������Գ���
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String data = "11|33|44|22|55|";
		MyDes mydes = new MyDes();

		// ����
		byte[] baowen = mydes.baowenjiami("12345878", "ABC", "1000", data, "***");
		System.out.println("���ɼ��ܽ������=" + baowen.length);
		String isoString;
		try {
			isoString = new String(baowen, "ISO-8859-1");
			System.err.println("���ܽ����" + isoString);

			// ����
			String retdata = mydes.baowenjiemi("12345678", isoString.getBytes("ISO-8859-1"));
			System.out.println("���ܽ����" + retdata);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
