package com.lcjr.pvxp.model;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.util.MD5;
import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.bean.*;
import  com.lcjr.pvxp.util.*;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import java.util.*;
import  javax.servlet.http.*;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 和表zzt_eatcardlog相关的业务逻辑</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 吴学坤
 * @version 1.0 2004/03/28
 */
public class EatCardLogModel
{

	public EatCardLogModel()throws HibernateException{
	}

	public static List getEatCardLog(String[] devno, String edate1, String edate2, String accno, int firstResult, int maxResults , String flag) throws HibernateException {
		List result = EatCardLogBean.getEatCardLog(devno, edate1, edate2, accno, firstResult, maxResults , flag);
		HibernateUtil.closeSession();

		return result;
	}

	public static int getEatCardLogCount(String[] devno, String edate1, String edate2, String accno , String flag) throws HibernateException {
		int result = EatCardLogBean.getEatCardLogCount(devno, edate1, edate2, accno , flag);
		HibernateUtil.closeSession();

		return result;
	}

	public static boolean deleteEcLog(String date) throws HibernateException{
		return EatCardLogBean.deleteEcLog(date);
	}
}
