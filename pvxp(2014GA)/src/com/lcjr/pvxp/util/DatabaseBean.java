package com.lcjr.pvxp.util;

import java.text.*; 
import java.util.*; 
import java.sql.*; 

import org.apache.log4j.Logger;

import com.lcjr.pvxp.util.*;

/**
 * 数据库读取配置文件
 * @author 武坤鹏
 *
 */
public class DatabaseBean{
	
	Logger log = Logger.getLogger("web.lcjr.pvxp.util.DatabaseBean.java");
	
	Config myConfig = new Config("pvxp.properties");
	String sDBDriver= myConfig.getProperty("ordinary_DBDriver");
	String sConnStr= myConfig.getProperty("ordinary_DBUrl");
	String sUser = myConfig.getProperty("ordinary_DBUser");
	String sPwd = myConfig.getProperty("ordinary_DBPasswd");

	
	ResultSet Rslt= null; 
	Connection Conn = null; 
	Statement Stmt = null;
	
	PubUtil pubfunc = new PubUtil();

	/**
	 * 构造函数
	 *
	 */
	public DatabaseBean() {
		try{
			Class.forName(sDBDriver);
		}catch( Exception e){
			log.error("ERROR", e);
		}
	}
	/**
	 * 重新执行sql语句
	 * @param sql 
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String sql) throws SQLException{ 
		try{ 
			Conn = DriverManager.getConnection( sConnStr, sUser, sPwd ); 
			Stmt = Conn.createStatement( ); 
			Rslt = Stmt.executeQuery( sql ); 
		}catch( SQLException ex ){ 
			log.error("ERROR", ex);
		}
		
		return Rslt;
	} 

	
	
	
	/**
	 * 连接数据库
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean executeUpdate(String sql) throws SQLException,Exception{
		boolean		bupdate = false;
		Connection 	connection = DriverManager.getConnection( sConnStr, sUser, sPwd ); 
		
		if(connection == null) { 
			
        		throw new SQLException("连接数据库失败");
		}
		try {
			if(Stmt!=null){
				Stmt.close();
			}
			Stmt = connection.createStatement();
        	
			Stmt.executeUpdate(sql);
			bupdate=true;
		}catch(SQLException e){
			log.error("ERROR", e);
			throw new SQLException("数据库操作出错:"+e.getMessage());
			
		}finally{
			
		}
    		return bupdate;
	}
    	
	
	public boolean executeUpdate(String sql,int flag) throws SQLException{
 		try{
 			Conn = DriverManager.getConnection( sConnStr, sUser, sPwd ); 
					
			if ( flag == 1 ){
 				Conn.setAutoCommit(true);
			}
					
			Stmt = Conn.createStatement(); 
 			Stmt.executeUpdate( sql );
 			
 			return true;
 		}catch(SQLException ex){
 			log.warn("WARN", ex);
 			return false;
 		}
	}

	/**
	 * 关闭数据库相关方法
	 * @return
	 */
	public boolean closeConn() { 
		try{ 
			if ( Rslt != null ) 
				Rslt.close( ); 
			if ( Stmt != null ) 
				Stmt.close( ); 
			if ( Conn != null ) 
				Conn.close( ); 
			return true;
		}catch( SQLException ex ){ 
			log.warn("WARN", ex);
			return false; 
		} 
	}

}