package com.lcjr.pvxp.util;

import java.io.*;

public class WriteFile {
	private String path; // �ļ�·��
	
	private String something; // д����ַ���
	
	
	
	// ��ʼ��
	public WriteFile() {
		path = null;
		something = "ȱʡ����";
	}
	
	
	
	// �����ļ�·��
	public void setPath(String apath) {
		path = apath;
	}
	
	
	
	// �õ��ļ�·��
	public String getPath() {
		return path;
	}
	
	
	
	// �õ��ַ���
	public void setSomething(String asomething) {
		something = asomething;
	}
	
	
	
	// �����ַ���
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