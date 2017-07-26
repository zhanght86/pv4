package com.lcjr.pvxp.model;

import  com.lcjr.pvxp.util.*;
import	com.lcjr.pvxp.pojo.*;
import  java.io.*;
import  java.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 设备监控地图模型</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.0 2005/03/11
 */
public class DevMapModel{
	private String bankid;
	private PubUtil myPubUtil = new PubUtil();
	
	
	public DevMapModel(String mbankid){
		bankid = mbankid;
	}

	/**
	 * <p>判断某机构是否存在地图</p>
	 * 
	 * @return String	存在地图返回地图文件名，否则返回null
	 */	
	public String getMapname(){
		String mapname = bankid.trim() + ".map";
		String filepath = myPubUtil.getFilepath();
		String mapfilename = filepath + "/map/" + mapname;
		if( myPubUtil.iFexistfile(mapfilename) )
			return mapname;
		else
			return null;
	}
	
	/**
	 * <p>创建当前机构的空白地图</p>
	 * 
	 * @return int 0：成功 -1：失败 1：地图已存在
	 */		
	public synchronized int creatBlankMap(String operid , String imgname){
		try{
			if(getMapname()!=null) return 1;
			DevMap devmap = new DevMap();
			devmap.setImgname(imgname);
			devmap.setOwner(operid);
			devmap.setOwner(operid);
			devmap.setLastediter(operid);
			devmap.setBanknum(0);
			devmap.setDevnum(0);
			
			if(savDevMap(devmap)) return 0;
			else return -1;
		}catch(Exception e){
			return -1;
		}
	}
	/**
	 * <p>保存地图对象到文件</p>
	 * 
	 * @return boolean
	 */	
	public synchronized boolean savDevMap(DevMap devmap){
		try{
			StringBuffer mapstr = new StringBuffer();
			mapstr.append("[SYS]\n");	//SYS小节
			String imgname = myPubUtil.dealNull(devmap.getImgname());
			String owner = myPubUtil.dealNull(devmap.getOwner());
			String nowdate = myPubUtil.getNowDate(3);
			String nowtime = myPubUtil.getNowTime();
			String lastChangeBy = myPubUtil.dealNull(devmap.getLastediter())+","+nowdate+" "+nowtime;
			mapstr.append("ImageName="+imgname+"\n");
			mapstr.append("Owner="+owner+"\n");
			mapstr.append("LastChangeBy="+lastChangeBy+"\n\n");
			
			mapstr.append("[Bank]\n");	//Bank小节
			int banknum = devmap.getBanknum();
			mapstr.append("BankNum="+String.valueOf(banknum)+"\n");
			if( banknum>0 ){
				BankPosition[] banks = devmap.getBanks();
				if( banknum!=banks.length )	return false;
				for(int i=0;i<banknum;i++){
					String bankid =	myPubUtil.dealNull(banks[i].getBankid());
					String xpos =	myPubUtil.dealNull(banks[i].getXpos());
					String ypos =	myPubUtil.dealNull(banks[i].getYpos());
					mapstr.append("Bank"+String.valueOf(i+1)+"="+bankid+","+xpos+","+ypos+"\n");
				}
			}
			
			mapstr.append("\n[Dev]\n");	//Dev小节
			int devnum = devmap.getDevnum();
			mapstr.append("DevNum="+String.valueOf(devnum)+"\n");
			if( devnum>0 ){
				DevPosition[] devs = devmap.getDevs();
				if( devnum!=devs.length )	return false;
				for(int i=0;i<devnum;i++){
					String devno =	myPubUtil.dealNull(devs[i].getDevno());
					String xpos =	myPubUtil.dealNull(devs[i].getXpos());
					String ypos =	myPubUtil.dealNull(devs[i].getYpos());
					mapstr.append("Dev"+String.valueOf(i+1)+"="+devno+","+xpos+","+ypos+"\n");
				}
			}
			return biuldMap(mapstr.toString());
		}catch(Exception e){
			return false;
		}
		
	}

	/**
	 * <p>获取地图对象</p>
	 * 
	 * @return DevMap
	 */		
	public DevMap getDevMap(){
		try{
			DevMap tmp = new DevMap();
			String mapname = bankid.trim()+".map";
			String imgname = myPubUtil.dealNull(myPubUtil.ReadConfig("SYS","ImageName","","map",mapname));
			String owner = myPubUtil.dealNull(myPubUtil.ReadConfig("SYS","Owner","","map",mapname));
			String lastChangeBy = myPubUtil.dealNull(myPubUtil.ReadConfig("SYS","LastChangeBy",",","map",mapname));
			if( lastChangeBy.trim().length()==0 ) lastChangeBy = ",";
			int pos = lastChangeBy.indexOf(',');
			String lastediter = lastChangeBy.substring(0,pos);
			String lastedittime = lastChangeBy.substring(pos+1);
			int banknum = Integer.parseInt( myPubUtil.dealNull(myPubUtil.ReadConfig("Bank","BankNum","0","map",mapname)).trim() );
			int devnum = Integer.parseInt( myPubUtil.dealNull(myPubUtil.ReadConfig("Dev","DevNum","0","map",mapname)).trim() );
			
			BankPosition[] banks = new BankPosition[banknum];
			for( int i=0;i<banknum;i++ ){
				String tmpstr = myPubUtil.dealNull(myPubUtil.ReadConfig("Bank","Bank"+String.valueOf(i+1),",,","map",mapname));
				if( tmpstr.trim().length()==0 ) tmpstr = ",,";
				String[] tmpArray = myPubUtil.split(tmpstr,",");
				String bankid = tmpArray[0];
				String xpos = tmpArray[1];
				String ypos = tmpArray[2];
				BankPosition tmpbank = new BankPosition();
				tmpbank.setBankid(bankid);
				tmpbank.setXpos(xpos);
				tmpbank.setYpos(ypos);
				banks[i]=tmpbank;
			}
			
			DevPosition[] devs = new DevPosition[devnum];
			for( int i=0;i<devnum;i++ ){
				String tmpstr = myPubUtil.dealNull(myPubUtil.ReadConfig("Dev","Dev"+String.valueOf(i+1),",,","map",mapname));
				if( tmpstr.trim().length()==0 ) tmpstr = ",,";
				String[] tmpArray = myPubUtil.split(tmpstr,",");
				String devno = tmpArray[0];
				String xpos = tmpArray[1];
				String ypos = tmpArray[2];
				DevPosition tmpDev = new DevPosition();
				tmpDev.setDevno(devno);
				tmpDev.setXpos(xpos);
				tmpDev.setYpos(ypos);
				devs[i]=tmpDev;
			}
			
			tmp.setImgname(imgname);
			tmp.setOwner(owner);
			tmp.setLastediter(lastediter);
			tmp.setLastedittime(lastedittime);
			tmp.setBanknum(banknum);
			tmp.setDevnum(devnum);
			tmp.setBanks(banks);
			tmp.setDevs(devs);
			
			return tmp;
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * <p>创建或重写地图文件</p>
	 * 
	 * @param newstr 文件内容
	 * @return boolean
	 */	
	public synchronized boolean biuldMap(String newstr){
		try{
			String filepath = myPubUtil.getFilepath();
			if(myPubUtil.writeFile(newstr,filepath+"/map/",bankid+".map") == 0 ){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * <p>创建或重写地图文件并备份</p>
	 * 
	 * @param newstr 新的文件内容(自动备份上次文件为.tmp)
	 * @return boolean
	 */	
	public synchronized boolean biuldANDBakMap(String newstr){
		try{
			String mapname = getMapname();
			String filepath = myPubUtil.getFilepath();
			if( mapname==null ){		//新文件
				if(myPubUtil.writeFile(newstr,filepath+"/map/",bankid+".tmp") != 0 )
					return false;
				if(myPubUtil.writeFile(newstr,filepath+"/map/",bankid+".map") != 0 )
					return false;
				else
					return true;
			}else{
				String oldstr = myPubUtil.ReadFile(filepath+"/map/",bankid+".map");
				
				if(myPubUtil.writeFile(oldstr,filepath+"/map/",bankid+".tmp") != 0 )
					return false;

				if(myPubUtil.writeFile(newstr,filepath+"/map/",bankid+".map") != 0 )
					return false;
				else
					return true;

			}
		}catch(Exception e){
			return false;
		}
	}
}
