package com.lcjr.pvxp.bean;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lcjr.pvxp.bean.interfac.BaseDAO;
import com.lcjr.pvxp.bean.interfac.DevsStateDAO;
import com.lcjr.pvxp.orm.DevsState;
import com.lcjr.pvxp.orm.util.HibernateUtil;

/**
 * 
 * @author 武坤鹏
 * @version pvxp(2014GA)
 * @date 2014-9-11
 */
@Service
public class DevsStateBean extends BaseDAO<DevsState> implements DevsStateDAO {

	Logger log = Logger.getLogger(DevsStateBean.class.getName());

	@Override
	public String insertProc(Map<String, String> map) throws HibernateException {
		// TODO Auto-generated method stub
		String result = "UNKNOWN";
		try {
			log.info("插入的设备状态的设备编号==>" + map.get("Dveice_Code"));
			Session session = HibernateUtil.currentSession();
			CallableStatement proc = session.connection().prepareCall("{call DEVSTATEADD_PROC(?,?,?,?,?,?,?,?)}");
			proc.setString(1, map.get("DevNo"));
			proc.setString(2, map.get("DevState"));
			proc.setString(3, map.get("DevState"));
			proc.setString(4, map.get("Prt_State"));
			proc.setString(5, map.get("IdCard_State"));
			proc.setString(6, map.get("Bill_State"));
			proc.setString(7, map.get("BrushCard_State"));
			proc.registerOutParameter(8, Types.VARCHAR);
			proc.execute();

			result = proc.getString(8);
			log.info("插入操作结果==>" + result);
		} catch (Exception e) {
			log.error("e", e);
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
}
