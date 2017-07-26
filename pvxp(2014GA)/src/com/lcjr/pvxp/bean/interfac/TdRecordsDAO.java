package com.lcjr.pvxp.bean.interfac;

import java.util.Map;

import net.sf.hibernate.HibernateException;
import com.lcjr.pvxp.orm.TDRecords;

/**
 * 
 * @author 武坤鹏
 * @version pvxp(2014GA)
 * @date 2014-9-12
 */
public interface TdRecordsDAO extends IBaseDAO<TDRecords> {

	/**
	 * 插入交易记录，有则更换，无则加入
	 * 
	 * @param map
	 * @return
	 * @throws HibernateException
	 */
	public String insertProc(Map<String, String> map) throws HibernateException;

	/**
	 * 查询用户填单的概要交易信息，在map中（"TD_MSG"）返回用户历史填单概要信息列表
	 * 
	 * @param map
	 * @return	 
	 * @throws HibernateException
	 */
	public String selectGYProc(Map<String, String> map) throws HibernateException;
	
	/**
	 * 查询用户填单的详细交易信息
	 * 
	 * @param map
	 * @return
	 * @throws HibernateException
	 */
	public String selectMXProc(Map<String, String> map) throws HibernateException;
}