package com.lcjr.pvxp.util;

import java.io.*;

public class WriteAppend {
	private String path; // 文件路径
	
	private String something; // 追加的字符串变量
	
	
	
	// 初始化
	public WriteAppend() {
		path = null;
		something = "Default message";
	}
	
	
	
	// 设置文件路径
	public void setPath(String apath) {
		path = apath;
	}
	
	
	
	// 得到文件路径
	public String getPath() {
		return path;
	}
	
	
	
	// 设置要追加的字符串
	public void setSomething(String asomething) {
		something = asomething;
	}
	
	
	
	// 得到要追加的字符串
	public String getSomething() {
		return something;
	}
	
	
	
	// 追加字符串
	public int writeSomething() {
		try {
			// 创建文件path并写入something字符串，注意和写入篇的区别
			FileWriter theFile = new FileWriter(path, true);
			PrintWriter out = new PrintWriter(theFile);
			
			out.print(something + "\n");
			out.close();
			
			
			// 关闭文件
			theFile.close();
			
			return 0;
		} catch (IOException e) {
			return -1;
		}
	}
}
