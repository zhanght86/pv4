package com.lcjr.pvxp.util;

import java.io.*;

public class WriteAppend {
	private String path; // �ļ�·��
	
	private String something; // ׷�ӵ��ַ�������
	
	
	
	// ��ʼ��
	public WriteAppend() {
		path = null;
		something = "Default message";
	}
	
	
	
	// �����ļ�·��
	public void setPath(String apath) {
		path = apath;
	}
	
	
	
	// �õ��ļ�·��
	public String getPath() {
		return path;
	}
	
	
	
	// ����Ҫ׷�ӵ��ַ���
	public void setSomething(String asomething) {
		something = asomething;
	}
	
	
	
	// �õ�Ҫ׷�ӵ��ַ���
	public String getSomething() {
		return something;
	}
	
	
	
	// ׷���ַ���
	public int writeSomething() {
		try {
			// �����ļ�path��д��something�ַ�����ע���д��ƪ������
			FileWriter theFile = new FileWriter(path, true);
			PrintWriter out = new PrintWriter(theFile);
			
			out.print(something + "\n");
			out.close();
			
			
			// �ر��ļ�
			theFile.close();
			
			return 0;
		} catch (IOException e) {
			return -1;
		}
	}
}
