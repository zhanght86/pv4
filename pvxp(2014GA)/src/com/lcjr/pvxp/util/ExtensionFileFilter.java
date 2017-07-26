package com.lcjr.pvxp.util;

import  com.lcjr.pvxp.util.*;

import java.io.File; 
import java.io.FileFilter;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> �ļ����˻�����</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author ����
 * @version 1.0 2005/03/11
 */
public class ExtensionFileFilter implements FileFilter{ 
	private String extension; 
	
	public ExtensionFileFilter(String extension){ 
		this.extension = extension; 
	} 
	
	public ExtensionFileFilter(){ 

	} 
	/**
	 * <p>�ж������ļ��Ƿ���кϷ����ļ���</p>
	 * 
	 * @param file �ļ�
	 * @return boolean
	 */	
	public boolean accept(File file){
		try{
			if(file.isDirectory( )){ 
				return false; 
			} 
		 	
			String name = file.getName( ); 
			int index = name.lastIndexOf("."); 
			if(index == -1){
				return false; 
			}else{
				if(index == name.length( ) -1){ 
					return false; 
				}else{
					if( name.substring(index+1).equalsIgnoreCase(extension) )
						return true;
					else
						return false;
				}
			}
		}catch(Exception e){
			return false;
		}
	} 
	/**
	 * <p>��ȡָ��·����ָ���ļ������ļ�������</p>
	 * 
	 * @param mydir ָ��·��
	 * @param myextension ָ���ļ���չ��
	 * @return String[] �ļ�������
	 */	
	public String[] listFilenames(String mydir , String myextension){
		if( myextension==null||myextension.length()==0 )	return null;
		try{
			File file = new File(mydir); 
			File[] files = file.listFiles(new ExtensionFileFilter(myextension)); 
			int j=0;
			int filenum = files.length;
			String[] ret = new String[filenum];
			for( j=0;j<filenum;j++ ){
				ret[j] = files[j].getName();
			}
			return ret;
		}catch(Exception e){
			return null;
		}
	}
}  
