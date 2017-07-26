package com.lcjr.pvxp.bean;

import java.util.ArrayList;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.ScrollableResults;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import com.lcjr.pvxp.orm.CardLy;
import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.util.PubUtil;

public class CardDistillBean {

	/**
	 * ���캯��
	 * 
	 * @throws HibernateException
	 */
	public CardDistillBean() throws HibernateException {

	}

	/**
	 * @param termnum
	 * @param pdate1
	 * @param pdate2
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws HibernateException
	 */
	public static List getCardDistill(String[] termnum, String pdate1, String pdate2, int firstResult, int maxResults) throws HibernateException {

		// String queryString = "";// ��ѯ���
		// // make query language as usual
		// queryString = "from CardLy where devno in ('" + termnum[0] + "'";
		// for (int i = 1; i < termnum.length; i++) {
		// queryString += ", '" + termnum[i] + "'";
		// }
		// queryString += ")";
		// // if time not exist ,sql won't contain
		// if (!pdate1.equals("")) {
		// queryString += " and lydate>='" + pdate1 + "'";
		// }
		// if (!pdate2.equals("")) {
		// queryString += " and lydate<='" + pdate2 + "'";
		// }
		//
		// PubUtil myPubUtil = new PubUtil();
		// // if oracle version equal '1' then
		// // if ((myPubUtil.dealNull(Constants.DB_OP_TYPE)).equals("1"))
		// // queryString += " order by pdate desc, ptime desc";
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
		// result = query.setFirstResult(firstResult).setMaxResults(
		// maxResults).list();
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
		//
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
		//
		// return result;

		int count = 0;
		String queryString = "";
		count = termnum.length / 150 + 1;
		List<CardLy> result = new ArrayList<CardLy>();
		List<CardLy> result1 = null;
		PubUtil myPubUtil = new PubUtil();

		String servtype = myPubUtil.dealNull(myPubUtil.ReadConfig("DBagent", "ServType", "0", "PowerView.ini")).trim();
		if (servtype.equals(""))
			servtype = "0";

		// �ֶβ�ѯ������ �豸�������ർ�±��� �ļ���
		for (int i = 0; i < count; i++) {

			queryString = "from CardLy where devno in (";

			for (int j = 0; (i * 150) + j < termnum.length && j < 150; j++) {
				queryString += "'" + termnum[i * 150 + j] + "',";
			}

			// ȥ���ַ��������ġ�����
			queryString = queryString.substring(0, queryString.length() - 1) + ")";
			if (!pdate1.equals("")) {
				queryString += " and lydate>='" + pdate1 + "'";
			}
			if (!pdate2.equals("")) {
				queryString += " and lydate<='" + pdate2 + "'";
			}

			queryString = queryString + " order by lydate , lytime ";

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
							result.add((CardLy)sr.get(0));
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

	/**
	 * ��÷��ϲ�ѯ��������������
	 * 
	 * @param termnum
	 * @param pdate1
	 * @param pdate2
	 * @return ��÷��ϲ�ѯ�������������� by wukp
	 * @throws HibernateException
	 */
	public static int getInvoiceDistillCount(String[] termnum, String pdate1, String pdate2) throws HibernateException {

		// String queryString = "";
		//
		// queryString = " select count(*) from CardLy where devno in ('"
		// + termnum[0] + "'";
		// for (int i = 1; i < termnum.length; i++) {
		// queryString += ", '" + termnum[i] + "'";
		// }
		// queryString += ")";
		//
		// if (!pdate1.equals("")) {
		// queryString += " and lydate>='" + pdate1 + "'";
		// }
		// if (!pdate2.equals("")) {
		// queryString += " and lydate<='" + pdate2 + "'";
		// }
		//
		// List result = null;
		// int count = 0;
		// try {
		// Session session = HibernateUtil.currentSession();
		// Transaction tx = session.beginTransaction();
		// Query query = session.createQuery(queryString);
		//
		// result = query.list();
		//
		// count = ((Integer) result.get(0)).intValue();
		// tx.commit();// �ȴ�������commit xucc 20090624
		// } catch (Exception e) {
		// HibernateUtil.closeFactory();
		// return 0;
		// } finally {
		// try {
		// HibernateUtil.closeSession();
		// } catch (Exception ex) {
		// }
		// }
		// return count;
		// }

		int count = 0;
		String queryString = "";
		count = termnum.length / 150 + 1;
		List result = null;
		int sum = 0;
		// �ֶβ�ѯ������ �豸�������ർ�±��� �ļ���
		for (int i = 0; i < count; i++) {

			queryString = " select count(*) from CardLy where devno in (";

			for (int j = 0; (i * 150) + j < termnum.length && j < 150; j++) {
				queryString += "'" + termnum[i * 150 + j] + "',";
			}

			// ȥ���ַ��������ġ�����
			queryString = queryString.substring(0, queryString.length() - 1) + ")";
			if (!pdate1.equals("")) {
				queryString += " and lydate>='" + pdate1 + "'";
			}
			if (!pdate2.equals("")) {
				queryString += " and lydate<='" + pdate2 + "'";
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
}
