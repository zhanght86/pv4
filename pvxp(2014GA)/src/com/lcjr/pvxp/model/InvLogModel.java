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
 * <p><b>Description:</b> 和表zzt_invlog相关的业务逻辑</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2006</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 
 * @version 1.0 2006/01/14
 */
public class InvLogModel
{

	public InvLogModel()throws HibernateException{
	}

	public static List getInvLog(String[] devno, String pdate1, String pdate2, String invno1, String invno2,String accno,int firstResult, int maxResults) throws HibernateException {
		List result = InvLogBean.getInvLog(devno, pdate1, pdate2, invno1, invno2, accno, firstResult, maxResults );
		HibernateUtil.closeSession();

		return result;
	}

	public static int getInvLogCount(String[] devno, String pdate1, String pdate2, String invno1, String invno2, String accno) throws HibernateException {
		int result = InvLogBean.getInvLogCount(devno, pdate1, pdate2, invno1, invno2, accno);
		HibernateUtil.closeSession();

		return result;
	}

}
