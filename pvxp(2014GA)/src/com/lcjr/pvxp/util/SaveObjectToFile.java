package com.lcjr.pvxp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

import com.lcjr.pvxp.action.DevWSMoniListAction;

/**
 * 
 * @author
 * @version pvxp(2014GA)
 * @date 2014-12-2
 */
public class SaveObjectToFile {

	Logger log = Logger.getLogger(DevWSMoniListAction.class.getName());

	
	/**
	 * 
	 * @param obj
	 * @param filePathAndName
	 */
	public void writeObjectToFile(Object obj, String filePathAndName) {

		File file = new File(filePathAndName);
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			ObjectOutputStream objOut = new ObjectOutputStream(out);

			objOut.writeObject(obj);
			objOut.flush();
			objOut.close();
			log.info("保存文件：" + filePathAndName + "成功！save success" + obj.getClass().getName());
			System.out.println("write object success!");
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param filePathAndName
	 * @return
	 */
	public Object readObjectFromFile(String filePathAndName) {
		Object temp = null;
		File file = new File(filePathAndName);
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			ObjectInputStream objIn = new ObjectInputStream(in);
			temp = objIn.readObject();
			objIn.close();
			log.info("保存文件：" + filePathAndName + "save success" );
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		return temp;
	}
}
