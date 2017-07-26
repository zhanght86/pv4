package com.lcjr.pvxp.bean.interfac;
import java.util.List;

import com.lcjr.pvxp.orm.CashDetail;

/**
 * 
 * @author 武坤�?
 * @version pvxp
 * @date 2014-3-17
 */
public interface CashDetailDAO extends IBaseDAO<CashDetail> {
	
	/**
	 * 
	 */
	public List<CashDetail> select(CashDetail t,String other);
}