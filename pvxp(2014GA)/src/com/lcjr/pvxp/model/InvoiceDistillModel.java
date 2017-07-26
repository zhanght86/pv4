package com.lcjr.pvxp.model;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.util.MD5;
import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.bean.*;
import com.lcjr.pvxp.util.*;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;
import javax.servlet.http.*;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 和表zzt_invhistory相关的业务逻辑</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2006</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 
 * @version 1.0 2006/01/09
 */
public class InvoiceDistillModel
{

	public InvoiceDistillModel()throws HibernateException{
	}

	public static List getInvoiceDistill(String[] termnum, String pdate1, String pdate2, int firstResult, int maxResults) throws HibernateException {
		List result = InvoiceDistillBean.getInvoiceDistill(termnum, pdate1, pdate2,  firstResult, maxResults );
		HibernateUtil.closeSession();

		return result;
	}

	public static int getInvoiceDistillCount(String[] termnum, String pdate1, String pdate2) throws HibernateException {
		int result = InvoiceDistillBean.getInvoiceDistillCount(termnum, pdate1, pdate2);
		HibernateUtil.closeSession();

		return result;
	}

}
