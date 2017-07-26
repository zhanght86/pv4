package com.lcjr.pvxp.bean;

import org.springframework.stereotype.Component;

import com.lcjr.pvxp.bean.interfac.BaseDAO;
import com.lcjr.pvxp.bean.interfac.CashStockDAO;
import com.lcjr.pvxp.orm.CashStock;

@Component("cashStockDAO")
public class CashStockDAOImpl extends BaseDAO<CashStock> implements CashStockDAO {

}