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
 * <p><b>Description:</b> �ͱ�zzt_invstate��ص�ҵ���߼�</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2006</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author 
 * @version 1.0 2006/01/09
 */
public class InvStateModel
{

	public InvStateModel()throws HibernateException{
	}

	public static List getInvState(String[] devno, int firstResult, int maxResults) throws HibernateException {
		List result = InvStateBean.getInvState(devno, firstResult, maxResults );
		HibernateUtil.closeSession();

		return result;
	}

	public static int getInvStateCount(String[] devno) throws HibernateException {
		int result = InvStateBean.getInvStateCount(devno);
		HibernateUtil.closeSession();

		return result;
	}

}
