package com.lcjr.pvxp.ini;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * ini配置文件的读、修改
 * 
 * @author gemelr inspurjr 2009-03-02
 */
public class IniOperation {
	private BufferedReader reader;
	private BufferedWriter writer;
	protected HashMap sections = new HashMap();
	private transient String currentSecion;
	private transient Properties current;
	private StringBuffer all = new StringBuffer();
	private String file = "";

	public IniOperation(String file) throws IOException {
		this.file = file;
		reader = new BufferedReader(new FileReader(file));
		read(reader);
		reader.close();
	}

	/**
	 * 读取ini文件
	 * 
	 * @param reader
	 * @throws IOException
	 */
	private void read(BufferedReader reader) throws IOException {
		String line;
		all = new StringBuffer(",");
		while ((line = reader.readLine()) != null) {
			if (line.matches(" *")) {
				line = "";
			}
			all.append(line + ",");
			if (line.startsWith("#")) {// 以#号开始则为注释
				continue;
			}
			if (line.indexOf("#") != -1) {
				line = line.substring(0, line.indexOf("#"));
			}
			line = line.trim();
			if (line.startsWith("[") && line.endsWith("]")) {
				if (current != null) {
					sections.put(currentSecion, current);
				}
				currentSecion = line.substring(1, line.length() - 1);
				current = new Properties();
			} else if (line.matches(".*=.*")) {
				int i = line.indexOf('=');
				String name = line.substring(0, i);
				String value = line.substring(i + 1);
				current.setProperty(name, value);
			}
		}
		if (line == null) {
			sections.put(currentSecion, current);
		}
	}

	/**
	 * 从新读取文件
	 * 
	 * @throws IOException
	 */
	public void reRead() throws IOException {
		read(reader);
	}

	/**
	 * 写文件
	 * 
	 * @throws IOException
	 */
	public void write() throws IOException {
		writer = new BufferedWriter(new FileWriter(file));
		String[] allLine = all.toString().split(",");

		for (int i = 0; i < allLine.length; i++) {
			writer.write(allLine[i]);
			writer.newLine();
		}
		writer.close();
		// writer.flush();
	}

	// public void close() throws IOException{
	// if(writer!=null){
	// writer.close();
	// }
	// }

	/**
	 * 修改配置项
	 * 
	 * @param sectionName
	 *            小节名
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public void modify(String sectionName, String key, String value) {
		int start = all.indexOf(","+key+"=", all.indexOf(sectionName)
				+ sectionName.length())
				+ key.length() + 1;
		if(start==-1){
			return;
		} else {
			start=start+1;
		}
		int end = all.indexOf(",", start);
		if (all.indexOf("#", start) < end && all.indexOf("#", start) != -1) {
			end = all.indexOf("#", start);
		}
		all.replace(start, end, value);
	}

	/**
	 * 获得提供小节的所有配置项
	 * 
	 * @param section
	 *            小节名
	 * @return String
	 */
	public List getValue(String section) {
		int start = all.indexOf("[" + section + "]") + section.length() + 3;
		int end = all.indexOf("[", start + 1);
		if (end == -1) {
			end = all.length();
		}
		String[] temp = (all.substring(start, end)).split(",");
		List allInTheSection = new ArrayList();
		for (int i = 0; i < temp.length; i++) {
			String str = temp[i];
			if (str.startsWith("#") || str.matches(" *")) {
				continue;
			}
			if (str.indexOf("#") > 0) {
				str = str.substring(0, str.indexOf("#"));
			}
			allInTheSection.add(str);
		}
		return allInTheSection;
	}

	/**
	 * 根据提供的小节名和键获得配置的值
	 * 
	 * @param section
	 *            小节名
	 * @param key
	 *            键
	 * @return
	 */
	public String getValue(String section, String key) {
		// Properties p = (Properties) sections.get(section);
		// if (p == null) {
		// return null;
		// }
		//		
		// String value = p.getProperty(name);

		// int start = all.indexOf(section);
		// int end = all.indexOf("[", start);
		// if(end==-1){
		// end=all.length();
		// }
		int start = all.indexOf(","+key+"=", all.indexOf(section));
		if (start != -1) {
			start=start+1;
			if (start > all.indexOf("[", all.indexOf(section))) {
				return null;
			} else {
				
				int end = all.indexOf(",", start);
				String value = all.substring(start + key.length() + 1, end);
				return value;
			}
		} else {
			return null;
		}

	}

	/**
	 * 向指定小节添加配置项。
	 * 
	 * @param sectionName
	 *            小节名
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public void add(String sectionName, String key, String value) {
		String addStr = "," + key + "=" + value;
		int start = all.indexOf("[" + sectionName + "]");
		int len = ("[" + sectionName + "]").length();
		int addPoint = all.indexOf("[", start + len);
		if (addPoint > all.indexOf(",,", start + len)
				&& all.indexOf(",,", start + len) != -1) {
			addPoint = all.indexOf(",,", start + len);
		}
		if (addPoint > all.indexOf(",#", start + len)
				&& all.indexOf(",#", start + len) != -1) {
			addPoint = all.indexOf(",#", start + len);
		}
		if (addPoint == -1) {
			addPoint = all.length();
			addStr = addStr.substring(1, addStr.length());
		}
		all.insert(addPoint, addStr);
	}

	/**
	 * 向指定小节添加配置项（此配置项有规律，有条数统计）
	 * 
	 * @param sectionName
	 *            小节名
	 * @param value
	 *            值
	 */
	public void add(String sectionName, String value) {
		int count = Integer.parseInt(getValue(sectionName, "count"));
		modify(sectionName, "count", String.valueOf(++count));
		add(sectionName, "type" + String.valueOf(count), value);
	}

	/**
	 * 删除小节配置项（此配置项有规律，有条数统计）
	 * 
	 * @param sectionName
	 *            小节名
	 * @param key
	 *            键
	 * @param type
	 *            键的类型（例如type）
	 */
	public void remove(String sectionName, String[] key) {
		for (int i = 0; i < key.length; i++) {
			remove(sectionName, key[i]);
		}
		int i = 0;
		List list = getValue(sectionName);
		for (int j = 1; j < list.size(); j++) {
			String[] one = ((String) list.get(j)).split("=");
			remove(sectionName, one[0]);
			add(sectionName, "type" + (++i), one[1]);
		}
		modify(sectionName, "count", String.valueOf(i));
	}

	/**
	 * 删除小节配置项
	 * 
	 * @param sectionName
	 *            小节名
	 * @param key
	 *            键
	 */
	public void remove(String sectionName, String key) {
		int start = all.indexOf(","+key+"=", all.indexOf(sectionName));
		if(start==-1){
			return;
		} else {
			start=start+1;
		}
		int end = all.indexOf(",", start);
		if (all.indexOf("#", start) < end) {
			end = all.indexOf("#", start);
		}
		all.replace(start, end + 1, "");
	}

	public StringBuffer getAll() {
		return all;
	}

	public void setAll(StringBuffer all) {
		this.all = all;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			IniOperation iniOperation = new IniOperation(
					"../pvxp/src/com/lcjr/pvxp/test/zzsxf.ini");
			// iniOperation.modify("", "1oo");
			// iniOperation.add("SPECCARD","123");
			// iniOperation.remove("FL","XYKZZZTOXYKZZZ");
			// iniOperation.write();
			// List allInTheSection = iniOperation.getValue("FL");
			// for (int i = 0; i < allInTheSection.size(); i++) {
			// String string = (String)allInTheSection.get(i);
			// System.out.println(string);
			// }
			// for (Iterator iterator = allInTheSection.iterator(); iterator
			// .hasNext();) {
			// Object object = (Object) iterator.next();
			// String[] oneFL = (object.toString()).split("=");
			// String[] strArray = oneFL[1].split("\\|");
			// System.out.println(strArray.length);
			// for (int i = 0; i < strArray.length; i++) {
			// System.out.println(strArray[i]);
			// }
			// }
//			String[] one = new String[7];
//			for (int i = 1; i < 8; i++) {
//				one[i - 1] = "type" + i;
//			}
//			iniOperation.remove("CARDTYPE", one);
//			iniOperation.write();
			iniOperation.modify("SXFFLAG", "FLAG","0");
			iniOperation.write();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
