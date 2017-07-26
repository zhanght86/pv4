package com.lcjr.pvxp.util;

import java.io.*;

public class WriteFile {
	private String path; // 文件路径
	
	private String something; // 写入的字符串
	
	
	
	// 初始化
	public WriteFile() {
		path = null;
		something = "缺省文字";
	}
	
	
	
	// 设置文件路径
	public void setPath(String apath) {
		path = apath;
	}
	
	
	
	// 得到文件路径
	public String getPath() {
		return path;
	}
	
	
	
	// 得到字符串
	public void setSomething(String asomething) {
		something = asomething;
	}
	
	
	
	// 设置字符串
	public String getSomething() {
		return something;
	}
	
	
	public int writeSomething() {
		try {
			File f = new File(path);
			PrintWriter out = new PrintWriter(new FileWriter(f));
			
			out.print(this.getSomething() + "\n");
			out.close();
			return 0;
		} catch (IOException e) {
			return -1;
		}
	}
	
	
	public int writeClose() {
		try {
			File f = new File(path);
			PrintWriter out = new PrintWriter(new FileWriter(f));
			out.print(this.getSomething());
			out.close();
			return 0;
		} catch (IOException e) {
			return -1;
		}
	}
}