package com.lcjr.pvxp.bean;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lcjr.pvxp.bean.interfac.BaseDAO;
import com.lcjr.pvxp.bean.interfac.TdRecordsDAO;
import com.lcjr.pvxp.orm.TDRecords;
import com.lcjr.pvxp.orm.util.HibernateUtil;

/**
 * 将交易记录插入到数据库中
 * 
 * @author 武坤鹏
 * @version pvxp(2014GA)
 * @date 2014-9-12
 */
@Service
public class TdRecordsBean extends BaseDAO<TDRecords> implements TdRecordsDAO {

	Logger log = Logger.getLogger(TdRecordsBean.class.getName());

	@Override
	public String insertProc(Map<String, String> map) throws HibernateException {
		// TODO Auto-generated method stub
		String result = "UNKNOWN";
		if(map.get("Usr_Nm")==null||map.get("Usr_Nm").equals("")){
			map.put("Usr_Nm", "对公");
		}
		if(map.get("Usr_IdNo")==null||map.get("Usr_IdNo").equals("")){
			map.put("Usr_IdNo", "000000000000000000");
		}
		
		if(map.get("ENTITY.XML_TYPE_NAME")==null||map.get("ENTITY.XML_TYPE_NAME").equals("")){
			map.put("ENTITY.XML_TYPE_NAME", "未知业务");
		}
		
		log.info("Usr_Nm==>" + map.get("Usr_Nm"));
		log.info("Usr_IdNo==>" + map.get("Usr_IdNo"));
		log.info("ENTITY.XML_TYPE_NAME==>" + map.get("ENTITY.XML_TYPE_NAME"));

		try {
			Session session = HibernateUtil.currentSession();
			CallableStatement proc = session.connection().prepareCall("{call TDRECORDS_PROC(?,?,?,?,?)}");
			proc.setString(1, map.get("Usr_Nm"));
			proc.setString(2, map.get("Usr_IdNo"));
			proc.setString(3, map.get("ENTITY.XML_TYPE_NAME"));
			proc.setString(4, map.get("TD_MSG"));
			proc.registerOutParameter(5, Types.VARCHAR);
			proc.execute();

			result = proc.getString(5);
			log.info("保存记录的结果是：" + result);
		} catch (Exception e) {
			log.error(" 报错" + e);
			HibernateUtil.closeFactory();
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
				log.error(" 报错" + ex);
			}
		}
		return result;
	}

	@Override
	public String selectGYProc(Map<String, String> map) throws HibernateException {
		// TODO Auto-generated method stub

		String result = "";
		log.info("Usr_IdNo==>" + map.get("Usr_IdNo"));
		log.info("XML_TYPE_NAME==>" + map.get("XML_TYPE_NAME"));
		try {

			Session session = HibernateUtil.currentSession();
			CallableStatement proc = session.connection().prepareCall("{call TD_TRADE_PACKKAGE.TD_ARRY_proc(?,?,?)}");
			proc.setString(1, map.get("Usr_IdNo"));
			proc.setString(2, map.get("XML_TYPE_NAME"));
			proc.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);

			proc.execute();
			ResultSet rs = (ResultSet) proc.getObject(3);

			while (rs.next()) {
				result += "|TD_ID=" + rs.getString(1) + ";USR_NM=" + rs.getString(2) + ";USR_ID=" + rs.getString(3) + ";TD_DATE=" + rs.getString(4) + ";";
			}

			log.info("查询记录的结果是：" + result);
			map.put("result", result);
			log.info("查询报文是==>" + map.get("TD_MSG"));
		} catch (Exception e) {
			log.error(" 报错" + e);
			HibernateUtil.closeFactory();
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
				log.error(" 报错" + ex);
			}
		}
		return result;
	}

	@Override
	public String selectMXProc(Map<String, String> map) throws HibernateException {
		// TODO Auto-generated method stub

		String result = "";
		log.info("TD_ID==>" + map.get("TD_ID"));

		try {
			Session session = HibernateUtil.currentSession();
			CallableStatement proc = session.connection().prepareCall("{call CHECK_TDRECORDS_MX_PROC(?,?,?)}");
			proc.setString(1, map.get("TD_ID"));
			proc.registerOutParameter(2, Types.CLOB);
			proc.registerOutParameter(3, Types.VARCHAR);
			proc.execute();

			Clob c = proc.getClob(2);
			result = proc.getString(3);

			map.put("TD_MSG", c != null ? c.getSubString(1, (int) c.length()) : null);

			map.put("查询结果是==>", result);
			log.info("查询明细报文存入TD_MSG==>" + map.get("TD_MSG"));
		} catch (Exception e) {
			log.error(" 报错" + e);
			HibernateUtil.closeFactory();
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
				log.error(" 报错" + ex);
			}
		}
		return map.get("TD_MSG");
	}
}
