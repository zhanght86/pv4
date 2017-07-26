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
	
	
	
	
	// �����ļ�����
	public ReadFile() {
		file = new BufferedReader(new InputStreamReader(System.in), 1);
	}
	
	
	public ReadFile(String filePath) throws FileNotFoundException {
		path = filePath;
		file = new BufferedReader(new FileReader(path));
	}
	
	
	
	// �����ļ�·��
	public void setPath(String filename) {
		path = filename;
		try {
			file = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			log.error("file not found", e);
		}
	}
	
	
	
	// �õ��ļ�·��
	public String getPath() {
		return path;
	}
	
	
	
	// �ر��ļ�
	public void fileClose() throws IOException {
		file.close();
	}
	
	
	
	// ��ȡ��һ�м�¼����û���򷵻�-1
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
	
	
	
	// ���ַ�������ʽ����������¼
	public String returnRecord() {
		return currentRecord;
	}
	
	
	public String getFilestr() {
		return filestr;
	}
}
