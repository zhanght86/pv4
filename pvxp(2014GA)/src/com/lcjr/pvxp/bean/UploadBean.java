package com.lcjr.pvxp.bean;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * 
 * Title: �ļ��ϴ��� Description: ���ܶ��ļ������ϴ�,����ȡ��������ֵ
 * 
 * @version 1.0
 */

public class UploadBean {
	/**
	 * Դ�ļ���
	 */
	private String sourceFile = ""; // Դ�ļ���

	/**
	 * �ļ���׺��
	 */
	private String suffix = ""; // �ļ���׺��

	/**
	 * ���ϴ����ļ���׺��
	 */
	private String canSuffix = ".gif.jpg.jpeg.png"; // ���ϴ����ļ���׺��

	/**
	 * Ŀ���ļ�Ŀ¼
	 */
	private String objectPath = "C:/"; // Ŀ���ļ�Ŀ¼

	/**
	 * Ŀ���ļ���
	 */
	private String objectFileName = ""; // Ŀ���ļ���

	/**
	 * ����״̬
	 */
	private String description = ""; // ����״̬

	/**
	 * ������
	 */
	private ServletInputStream sis = null; // ������

	/**
	 * ���ƴ�С
	 */
	private long size = 100 * 1024; // ���ƴ�С

	/**
	 * �Ѵ����ļ���Ŀ
	 */
	private int count = 0; // �Ѵ����ļ���Ŀ

	/**
	 * �ֽ����������
	 */
	private byte[] b = new byte[4096]; // �ֽ����������

	/**
	 * 
	 * 
	 */
	private boolean successful = true;

	/**
	 * 
	 */
	private Hashtable fields = new Hashtable();

	/**
	 * 
	 */
	public UploadBean() {

	}

	// �����ϴ��ļ��ĺ�׺��

	public void setSuffix(String canSuffix) {
		this.canSuffix = canSuffix;
	}

	// �����ļ�����·��

	public void setObjectPath(String objectPath) {
		this.objectPath = objectPath;
	}


/**
 * �����ļ�����·��
 */
	public void setSize(long maxSize) {
		this.size = maxSize;
	}

	// �ļ��ϴ��������

	public void setSourceFile(HttpServletRequest request) throws IOException {
		
		sis = request.getInputStream();//���������
		int a = 0;	//��ȡһ�����ݵĳ���
		int k = 0;	//��ȡ�ض��ַ�������λ��
		String s = "";	//
		// forѭ������ȡÿһ��
		while ((a = sis.readLine(b, 0, b.length)) != -1) {// ���һ�����ݲ�Ϊ��

			s = new String(b, 0, a);

			if ((k = s.indexOf("filename=\"")) != -1) {// �����ȡ�ĵ�һ����������filename=\���ַ���
				// ȡ���ļ�����
				s = s.substring(k + 10); // �½�ȡȥ��filename=\����ַ���
				k = s.indexOf("\"");	 // ���½�ȡ���ַ��������� '\'
				s = s.substring(0, k); 	// ��ȡ\֮ǰ���ַ���
				sourceFile = s; 		// ��λ��s��ֵ��sourceFile
				k = s.lastIndexOf(".");		 //ȡ�á�.������λ��
				suffix = s.substring(k + 1);	//ȡ���ļ���׺��
			
				
				if (canTransfer()) {	//�ж��ϴ��ļ�������
					transferFile();// �ϴ��ļ�ת��
				}

				
			} else if ((k = s.indexOf("name=\"")) != -1) {	// �����ȡ�ĵ�һ����������name=�����ַ���

				//��ͨ������Ԫ�أ���ȡ����Ԫ������
				String fieldName = s.substring(k + 6, s.length() - 3);//ȡ����ȥname=�ͺ�׺����ļ�����
				sis.readLine(b, 0, b.length);	//��ȡ�ض�����

				StringBuffer fieldValue = new StringBuffer(b.length);	//�����ַ��� fieldValue

				while ((a = sis.readLine(b, 0, b.length)) != -1) { //���������в�Ϊ��
					
					s = new String(b, 0, a - 2);	//������ַ����ĳ��ȳ�ȥĩβ�������ַ�
					
					if ((b[0] == 45) && (b[1] == 45) && (b[2] == 45) && (b[3] == 45) && (b[4] == 45)) {
						break;
					} else {
						fieldValue.append(s);
					}
				}
				
				
				if (fieldName.equals("filepath")) {
					objectPath = fieldValue.toString();
				}

				fields.put(fieldName, fieldValue.toString());
			}

			if (!successful)
				break;
		}
	}

	// ȡ�ñ�Ԫ��ֵ

	public String getFieldValue(String fieldName) {
		if (fields == null || fieldName == null) {
			return null;
		}
		return (String) fields.get(fieldName);
	}

	// ȡ���ϴ��ļ���

	public int getCount() {
		return count;
	}

	// ȡ��Ŀ��·��

	public String getObjectPath() {
		return objectPath;
	}

	// ȡ��Դ�ļ���

	public String getSourceFile() {
		return sourceFile;
	}

	// ȡ��Ŀ���ļ���

	public String getObjectFileName() {
		return objectFileName;
	}

	// ȡ���ϴ�״̬����

	public String getDescription() {
		return description;
	}

	/**
	 * �ж��ϴ��ļ�������
	 */
	private boolean canTransfer() {
		if (sourceFile.equals("")) {
			description = "ERR: File is wrong.";
			return false;
		} else {
			return true;
		}
	}

	

	/**
	 * �ϴ��ļ�ת��
	 */
	private void transferFile() {
		try {
			int pos = sourceFile.lastIndexOf("\\");
			objectFileName = sourceFile.substring(pos + 1);

			FileOutputStream out = new FileOutputStream(objectPath + objectFileName);
			out.flush();
			
			int a = 0;
			long hastransfered = 0; // ��ʾ�Ѿ�������ֽ���
			String s = "";

			while ((a = sis.readLine(b, 0, b.length)) != -1) {
				s = new String(b, 0, a);
				if ((s.indexOf("Content-Type:")) != -1) {
					break;
				}
			}

			sis.readLine(b, 0, b.length);

			while ((a = sis.readLine(b, 0, b.length)) != -1) {
				s = new String(b, 0, a);
				if ((b[0] == 45) && (b[1] == 45) && (b[2] == 45) && (b[3] == 45) && (b[4] == 45)) {
					break;
				}

				out.write(b, 0, a);
				hastransfered += a;
				if (hastransfered >= size) {
					description = "ERR: The file "
							+ sourceFile
							+ " is too large to transfer. The whole process is interrupted.";
					successful = false;
					break;
				}
			}

			if (successful) {
				description = "Right: The file " + sourceFile
						+ " has been transfered successfully.";
			}
			out.flush();
			out.close();



			if (!successful) {
				sis.close();
				File tmp = new File(objectPath + objectFileName);
				tmp.delete();
			}
		} catch (IOException ioe) {
			description = ioe.toString();
		}
	}

	public static void main(String[] args) {
		System.out.println("Test OK");
	}

}
