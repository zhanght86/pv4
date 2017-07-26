package com.lcjr.pvxp.util;

import java.io.*;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class ReadFile {
	
	Logger log = Logger.getLogger("web.lcjr.pvxp.util.ReadFile.java");
	
	private String currentRecord = "";
	
	
	private BufferedReader file;
	
	
	private String path;
	
	
	private StringTokenizer token;
	
	
	String filestr = "";
	
	
	
	
	// 创建文件对象
	public ReadFile() {
		file = new BufferedReader(new InputStreamReader(System.in), 1);
	}
	
	
	public ReadFile(String filePath) throws FileNotFoundException {
		path = filePath;
		file = new BufferedReader(new FileReader(path));
	}
	
	
	
	// 设置文件路径
	public void setPath(String filename) {
		path = filename;
		try {
			file = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			log.error("file not found", e);
		}
	}
	
	
	
	// 得到文件路径
	public String getPath() {
		return path;
	}
	
	
	
	// 关闭文件
	public void fileClose() throws IOException {
		file.close();
	}
	
	
	
	// 读取下一行记录，若没有则返回-1
	public int nextRecord() {
		int returnInt = -1;
		try {
			currentRecord = file.readLine();
			if (currentRecord != null)
				filestr = filestr + currentRecord;
		} catch (IOException e) {
			log.error("readLine problem, terminating.");
		}
		
		if (currentRecord == null) {
			returnInt = -1;
		} else {
			token = new StringTokenizer(currentRecord);
			returnInt = token.countTokens();
		}
		
		return returnInt;
	}
	
	
	
	// 以字符串的形式返回整个记录
	public String returnRecord() {
		return currentRecord;
	}
	
	
	public String getFilestr() {
		return filestr;
	}
}
