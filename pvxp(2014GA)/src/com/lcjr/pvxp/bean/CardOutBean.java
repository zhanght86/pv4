package com.lcjr.pvxp.bean;

import java.util.ArrayList;
import java.util.List;

import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.util.PubUtil;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.ScrollableResults;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

/**
 * �����ݿ��е�zzt_card_out���Ӧ��bean��
 * 
 * @author ������
 * 
 */
public class CardOutBean {
	
	
	/**
	 * ���캯��
	 * 
	 * @throws HibernateException
	 */
	public CardOutBean() throws HibernateException {
	}
	
	
	
	/**
	 * ��ѯ�����������Ľ���ĸ���
	 * 
	 * @param termnum
	 *            �豸��
	 * @param date1
	 *            ��ʼ����
	 * @param date2
	 *            ��ֹ����
	 * @param time1
	 *            ��ʼʱ��
	 * @param time2
	 *            ��ֹʱ��
	 * @param id
	 *            ���֤��
	 * @param cardno
	 *            ��������
	 * @return ���������Ľ���ĸ���
	 * @throws HibernateException
	 */
	public static int getCardOutCount(String[] termnum, String date1, String date2, String time1, String time2, String id, String cardno) throws HibernateException {
		
		int count = 0;
		String queryString = "";
		count = termnum.length / 150 + 1;
		List result = null;
		int sum = 0;
		// �ֶβ�ѯ������ �豸�������ർ�±��� �ļ���
		for (int i = 0; i < count; i++) {
			
			queryString = " select count(*) from CardOut where devno in (";
			
			for (int j = 0; (i * 150) + j < termnum.length && j < 150; j++) {
				queryString += "'" + termnum[i * 150 + j] + "',";
			}
			
			
			// ȥ���ַ��������ġ�����
			queryString = queryString.substring(0, queryString.length() - 1) + ")";
			
			
			// û�п�ʼ���ڣ��Ͳ�����sql���
			if (!date1.equals("")) {
				queryString += " and outcarddate>='" + date1 + "'";
			}
			// û�н������ڣ��Ͳ�����sql���
			if (!date2.equals("")) {
				queryString += " and outcarddate<='" + date2 + "'";
			}
			// û�п�ʼʱ�䣬�Ͳ�����sql���
			if (!time1.equals("")) {
				queryString += " and outcardtime>='" + time1 + "'";
			}
			// û�н�ֹʱ�䣬�Ͳ�����sql���
			if (!time2.equals("")) {
				queryString += " and outcardtime<='" + time2 + "'";
			}
			
			
			// û�У����֤�ţ��Ͳ�����sql���
			if (!id.equals("")) {
				queryString += " and idcardno='" + id + "'";
			}
			// û�з������ţ��Ͳ�����sql���
			if (!cardno.equals("")) {
				queryString += " and outcardno like '" + cardno + "%'";
			}
			
			
			// ���в�ѯ
			try {
				Session session = HibernateUtil.currentSession();
				Transaction tx = session.beginTransaction();
				Query query = session.createQuery(queryString);
				result = query.list();
				sum += ((Integer) result.get(0)).intValue();
				tx.commit();// �ȴ�������commit xucc 20090624
			} catch (Exception e) {
				HibernateUtil.closeFactory();
				return 0;
			} finally {
				try {
					HibernateUtil.closeSession();
				} catch (Exception ex) {
				}
			}
		}
		return sum;
	}
	
	
	
	/**
	 * ��ѯ�����������Ľ���ĸ���
	 * 
	 * @param termnum
	 *            �豸��
	 * @param date1
	 *            ��ʼ����
	 * @param date2
	 *            ��ֹ����
	 * @param time1
	 *            ��ʼʱ��
	 * @param time2
	 *            ��ֹʱ��
	 * @param id
	 *            ���֤��
	 * @param cardno
	 *            ��������
	 * @return ���������Ľ���ĸ���
	 * @throws HibernateException
	 */
	public static List getCardOutList(String[] termnum, String date1, String date2, String time1, String time2, String id, String cardno, int firstResult, int maxResults) throws HibernateException {
		//
		// String queryString = "";// ��ѯ���
		// // make query language as usual
		// queryString = " from CardOut where devno in ('" + termnum[0] + "'";
		// for (int i = 1; i < termnum.length; i++) {
		// queryString += ", '" + termnum[i] + "'";
		// }
		// queryString += ")";
		// // û�п�ʼ���ڣ��Ͳ�����sql���
		// if (!date1.equals("")) {
		// queryString += " and outcarddate>='" + date1 + "'";
		// }
		// // û�н������ڣ��Ͳ�����sql���
		// if (!date2.equals("")) {
		// queryString += " and outcarddate<='" + date2 + "'";
		// }
		// // û�п�ʼʱ�䣬�Ͳ�����sql���
		// if (!time1.equals("")) {
		// queryString += " and outcardtime>='" + time1 + "'";
		// }
		// // û�н�ֹʱ�䣬�Ͳ�����sql���
		// if (!time2.equals("")) {
		// queryString += " and outcardtime<='" + time2 + "'";
		// }
		//
		// // û�У����֤�ţ��Ͳ�����sql���
		// if (!id.equals("")) {
		// queryString += " and idcardno='" + id + "'";
		// }
		// // û�з������ţ��Ͳ�����sql���
		// if (!cardno.equals("")) {
		// queryString += " and outcardno like '" + cardno + "%'";
		// }
		//
		// PubUtil myPubUtil = new PubUtil();
		//
		// List result = null;
		// try {
		// String servtype = myPubUtil.dealNull(
		// myPubUtil.ReadConfig("DBagent", "ServType", "0",
		// "PowerView.ini")).trim();
		// if (servtype.equals(""))
		// servtype = "0";
		//
		// System.out.println(queryString);
		//
		// Session session = HibernateUtil.currentSession();
		// Transaction tx = session.beginTransaction();
		// Query query = session.createQuery(queryString);
		//
		// // ͨ�������ļ��ж����÷�ҳ��ʽ xucc 20090624
		// // wukp20110914 ԭʼΪ��if(servtype.equals("0")){
		// if (servtype.equals("0")) {
		// result =
		// query.setFirstResult(firstResult).setMaxResults(maxResults).list();
		// // wukp20110914 ���ݿ����ϵͳ�е� �α� ʹ��
		// } else if (servtype.equals("1")) {
		// result = new ArrayList();
		// result = query.list();
		//
		// ScrollableResults sr = query.scroll();
		// for (int i = firstResult; i < firstResult + maxResults; i++) {
		// System.out.println(" " + sr.getRowNumber());
		// if (sr.setRowNumber(i)) {
		// result.add(sr.get(0));
		// }
		// }
		// }
		// tx.commit();
		// } catch (Exception e) {
		// HibernateUtil.closeFactory();
		// return null;
		// } finally {
		// try {
		// HibernateUtil.closeSession();
		// } catch (Exception ex) {
		// }
		// }
		// return result;
		// }
		
		int count = 0;
		String queryString = "";
		count = termnum.length / 150 + 1;
		List result = new ArrayList();
		List result1 = null;
		PubUtil myPubUtil = new PubUtil();
		
		String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent", "ServType", "0", "PowerView.ini")).trim();
		if (servtype.equals(""))
			servtype = "0";
		
		
		// �ֶβ�ѯ������ �豸�������ർ�±��� �ļ���
		for (int i = 0; i < count; i++) {
			
			queryString = "from CardOut where devno in (";
			
			for (int j = 0; (i * 150) + j < termnum.length && j < 150; j++) {
				queryString += "'" + termnum[i * 150 + j] + "',";
			}
			
			
			// ȥ���ַ��������ġ�����
			queryString = queryString.substring(0, queryString.length() - 1) + ")";
			
			
			// û�п�ʼ���ڣ��Ͳ�����sql���
			if (!date1.equals("")) {
				queryString += " and outcarddate>='" + date1 + "'";
			}
			// û�н������ڣ��Ͳ�����sql���
			if (!date2.equals("")) {
				queryString += " and outcarddate<='" + date2 + "'";
			}
			// û�п�ʼʱ�䣬�Ͳ�����sql���
			if (!time1.equals("")) {
				queryString += " and outcardtime>='" + time1 + "'";
			}
			// û�н�ֹʱ�䣬�Ͳ�����sql���
			if (!time2.equals("")) {
				queryString += " and outcardtime<='" + time2 + "'";
			}
			
			
			// û�У����֤�ţ��Ͳ�����sql���
			if (!id.equals("")) {
				queryString += " and idcardno='" + id + "'";
			}
			// û�з������ţ��Ͳ�����sql���
			if (!cardno.equals("")) {
				queryString += " and outcardno like '" + cardno + "%'";
			}
			
			queryString=queryString+" order by outcarddate,outcardtime ";
			
			try {
				Session session = HibernateUtil.currentSession();
				Transaction tx = session.beginTransaction();
				Query query = session.createQuery(queryString);
				
				
				// ͨ�������ļ��ж����÷�ҳ��ʽ xucc 20090624
				// wukp20110914 ԭʼΪ��if(servtype.equals("0")){
				if (servtype.equals("0")) {
					result1 = query.setFirstResult(firstResult).setMaxResults(maxResults).list();
					result.addAll(result1);
					// wukp20110914 ���ݿ����ϵͳ�е� �α� ʹ��
					
				} else if (servtype.equals("1")) {
					result = new ArrayList();
					result = query.list();
					ScrollableResults sr = query.scroll();
					for (int k = firstResult; k < firstResult + maxResults; k++) {
						System.out.println("  " + sr.getRowNumber());
						if (sr.setRowNumber(k)) {
							result.add(sr.get(0));
						}
					}
				}
				
				tx.commit();
			} catch (Exception e) {
				HibernateUtil.closeFactory();
				return null;
			} finally {
				try {
					HibernateUtil.closeSession();
				} catch (Exception ex) {
				}
			}
		}
		return result;
	}
}