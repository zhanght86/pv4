package com.lcjr.pvxp.bean;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lcjr.pvxp.bean.interfac.BaseDAO;
import com.lcjr.pvxp.bean.interfac.IPropertiesDAO;
import com.lcjr.pvxp.orm.IniProperties;
import com.lcjr.pvxp.orm.util.HibernateUtil;

/**
 * 
 * @author 武坤鹏
 * @version pvxp(wf3)
 * @date 2014-9-23
 */
@Service
public class PropertiesBean extends BaseDAO<IniProperties> implements IPropertiesDAO {

	Logger log = Logger.getLogger(PropertiesBean.class.getName());

	@Override
	public String selectKey(String section, String keyname, String defstr, String filename) throws HibernateException {
		// TODO Auto-generated method stub
		log.info(" section==>" + section + " keyname==>" + keyname + " defstr==>" + defstr + " filename==>" + filename);

		String result = defstr;
		try {
			Session session = HibernateUtil.currentSession();
			CallableStatement proc = session.connection().prepareCall("{call properties_proc(?,?,?,?)}");
			proc.setString(1, filename.trim());
			proc.setString(2, section.trim());
			proc.setString(3, keyname.trim());
			proc.registerOutParameter(4, Types.VARCHAR);
			proc.execute();

			result = proc.getString(4);
			log.info("插入操作结果==>" + result);
			if (result.equals("null")) {
				result = defstr;
			}
		} catch (Exception e) {
			log.error("Exception e", e);
			HibernateUtil.closeFactory();
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
				log.error("ex", ex);
			}
		}
		return result;
	}

	@Override
	public Map<String, String> getAllProperties() throws HibernateException {

		Map<String, String> map = new HashMap<String, String>();
		try {
			Session session = HibernateUtil.currentSession();
			CallableStatement proc = session.connection().prepareCall("{call Properties_PACKKAGE.Properties_proc(?)}");
			proc.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);

			proc.execute();
			ResultSet rs = (ResultSet) proc.getObject(1);

			while (rs.next()) {
				System.err.println("TD_ID=" + rs.getString(1) + ";USR_NM=" + rs.getString(2) + ";USR_ID=" + rs.getString(3) + ";TD_DATE=" + rs.getString(4) + ";|");
				log.info("TD_ID=" + rs.getString(1) + ";USR_NM=" + rs.getString(2) + ";USR_ID=" + rs.getString(3) + ";TD_DATE=" + rs.getString(4) + ";|");
				map.put(rs.getString(1) + rs.getString(2) + rs.getString(3), rs.getString(4));
			}

		} catch (Exception e) {
			log.error("Exception e", e);
//			HibernateUtil.closeFactory();
		} finally {
			try {
				HibernateUtil.closeSession();
			} catch (Exception ex) {
				log.error("ex ", ex);
			}
		}
		return map;
	}

}
