package com.lcjr.pvxp.bean;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.springframework.stereotype.Component;

import com.lcjr.pvxp.bean.interfac.BaseDAO;
import com.lcjr.pvxp.bean.interfac.CashDetailDAO;
import com.lcjr.pvxp.orm.CashDetail;
import com.lcjr.pvxp.orm.util.HibernateUtil;
/**
 * 
* <p>Title: CashDetailDAOImpl.java</p>
* <p>Description: �ֽ�����ϸģ���dao��</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: inspur</p>
* @author wang-jl
* @date 2014-3-19
* @version 1.0
 */
@Component("cashDetailDAO")
public class CashDetailDAOImpl extends BaseDAO<CashDetail> implements CashDetailDAO {

	@Override
	public List<CashDetail> select(CashDetail t, String other) {
		Session session = null;
		Query query = null;
		List<CashDetail> list = null;
		try {
			session = HibernateUtil.currentSession();
			//��ȡһ�����ϲ�ѯ 
			String sql = getquerySql(t);
			if(other!=null&&!other.equals(""))
				sql+=other;
			query = session.createQuery(sql);
			
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				HibernateUtil.closeSession();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
/**
 * ���÷���ķ�ʽ�������ݵĲ�ѯ
 * �������Ա�����if�������Ĵ�������
 * ����ʵ�ַ�����������
 * �����ݿ�����ݽṹ�����仯ʱ�����ɿ���ʹ�ã��ȽϽ�׳
 * wang-jl 2014-03-19
 * @param t
 * @return
 */
	private String getquerySql(CashDetail t) {
		// �õ������class��
		Class<?> c = t.getClass();
		// �õ����������еķ���
		Method[] methods = c.getMethods();
		// �õ����������е�����
//		Field[] fields = c.getFields();
		// �õ������������
		String cName = c.getName();
		// ����������н���������
		String tableName = cName.substring(cName.lastIndexOf(".") + 1,
				cName.length());
		String sql = " from " + tableName + " where remark1 <> '1'";
		List<String> mList = new ArrayList<String>();
		List vList = new ArrayList();
		for (Method method : methods) {
			String mName = method.getName();
			if (mName.startsWith("get") && !mName.startsWith("getClass")) {
				String fieldName = mName.substring(3, mName.length());
				mList.add(fieldName);
				// System.out.println("�ֶ�����----->" + fieldName);
				try {
					Object value = method.invoke(t, null);
					// System.out.println("ִ�з������ص�ֵ��" + value);
					if (value instanceof String) {
						vList.add("'" + value.toString().trim() + "'");
						// System.out.println("�ֶ�ֵ------>" + value);
					} else {
						vList.add(value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		for (int i = 0; i < vList.size(); i++) {
			if (vList.get(i) != null&&!vList.get(i).equals("''")) {
				if(mList.get(i).equals("Cardno")){
					String vString =  vList.get(i).toString();
					sql += " and " + mList.get(i) + " like " +vString.substring(0, vString.length()-1)+"%'";
				}else{
					sql += " and " + mList.get(i) + " = " + vList.get(i);
				}
			}
		}
		return sql;
	}
	
}