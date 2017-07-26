package com.lcjr.pvxp.bean;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * 
 * Title: 文件上传类 Description: 既能对文件进行上传,又能取得输入框的值
 * 
 * @version 1.0
 */

public class UploadBean {
	/**
	 * 源文件名
	 */
	private String sourceFile = ""; // 源文件名

	/**
	 * 文件后缀名
	 */
	private String suffix = ""; // 文件后缀名

	/**
	 * 可上传的文件后缀名
	 */
	private String canSuffix = ".gif.jpg.jpeg.png"; // 可上传的文件后缀名

	/**
	 * 目标文件目录
	 */
	private String objectPath = "C:/"; // 目标文件目录

	/**
	 * 目标文件名
	 */
	private String objectFileName = ""; // 目标文件名

	/**
	 * 描述状态
	 */
	private String description = ""; // 描述状态

	/**
	 * 输入流
	 */
	private ServletInputStream sis = null; // 输入流

	/**
	 * 限制大小
	 */
	private long size = 100 * 1024; // 限制大小

	/**
	 * 已传输文件数目
	 */
	private int count = 0; // 已传输文件数目

	/**
	 * 字节流存放数组
	 */
	private byte[] b = new byte[4096]; // 字节流存放数组

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

	// 设置上传文件的后缀名

	public void setSuffix(String canSuffix) {
		this.canSuffix = canSuffix;
	}

	// 设置文件保存路径

	public void setObjectPath(String objectPath) {
		this.objectPath = objectPath;
	}


/**
 * 设置文件保存路径
 */
	public void setSize(long maxSize) {
		this.size = maxSize;
	}

	// 文件上传处理程序

	public void setSourceFile(HttpServletRequest request) throws IOException {
		
		sis = request.getInputStream();//获得输入流
		int a = 0;	//读取一行数据的长度
		int k = 0;	//读取特定字符串所在位置
		String s = "";	//
		// for循环，读取每一行
		while ((a = sis.readLine(b, 0, b.length)) != -1) {// 如果一行数据不为空

			s = new String(b, 0, a);

			if ((k = s.indexOf("filename=\"")) != -1) {// 如果读取的第一行数据中有filename=\的字符串
				// 取得文件数据
				s = s.substring(k + 10); // 新截取去除filename=\后的字符串
				k = s.indexOf("\"");	 // 在新截取的字符串中再找 '\'
				s = s.substring(0, k); 	// 截取\之前的字符串
				sourceFile = s; 		// 将位置s赋值给sourceFile
				k = s.lastIndexOf(".");		 //取得‘.’所在位置
				suffix = s.substring(k + 1);	//取得文件后缀名
			
				
				if (canTransfer()) {	//判断上传文件的类型
					transferFile();// 上传文件转换
				}

				
			} else if ((k = s.indexOf("name=\"")) != -1) {	// 如果读取的第一行数据中有name=“的字符串

				//普通表单输入元素，获取输入元素名字
				String fieldName = s.substring(k + 6, s.length() - 3);//取出出去name=和后缀后的文件名称
				sis.readLine(b, 0, b.length);	//读取特定长度

				StringBuffer fieldValue = new StringBuffer(b.length);	//定义字符串 fieldValue

				while ((a = sis.readLine(b, 0, b.length)) != -1) { //如果输入的行不为空
					
					s = new String(b, 0, a - 2);	//输入的字符串的长度出去末尾的两个字符
					
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

	// 取得表单元素值

	public String getFieldValue(String fieldName) {
		if (fields == null || fieldName == null) {
			return null;
		}
		return (String) fields.get(fieldName);
	}

	// 取得上传文件数

	public int getCount() {
		return count;
	}

	// 取得目标路径

	public String getObjectPath() {
		return objectPath;
	}

	// 取得源文件名

	public String getSourceFile() {
		return sourceFile;
	}

	// 取得目标文件名

	public String getObjectFileName() {
		return objectFileName;
	}

	// 取得上传状态描述

	public String getDescription() {
		return description;
	}

	/**
	 * 判断上传文件的类型
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
	 * 上传文件转换
	 */
	private void transferFile() {
		try {
			int pos = sourceFile.lastIndexOf("\\");
			objectFileName = sourceFile.substring(pos + 1);

			FileOutputStream out = new FileOutputStream(objectPath + objectFileName);
			out.flush();
			
			int a = 0;
			long hastransfered = 0; // 标示已经传输的字节数
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
