package com.lcjr.pvxp.util;

import  com.lcjr.pvxp.util.*;

import java.io.File; 
import java.io.FileFilter;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 文件过滤基本类</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
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
	 * <p>判断输入文件是否具有合法的文件名</p>
	 * 
	 * @param file 文件
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
	 * <p>获取指定路径下指定文件类型文件名数组</p>
	 * 
	 * @param mydir 指定路径
	 * @param myextension 指定文件扩展名
	 * @return String[] 文件名数组
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
