package com.lcjr.pvxp.action;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lcjr.pvxp.actionform.ReportFormListForm;
import com.lcjr.pvxp.model.BankinfoModel;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.model.StaMissionModel;
import com.lcjr.pvxp.orm.Bankinfo;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PageBean;
import com.lcjr.pvxp.util.PubUtil;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b> �б���Action
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * <br>
 * 
 * @author ��ѧ��
 * @version 1.0 2005/03/28
 */
public final class ReportFormListAction extends Action {

	// private Logger log = Logger.getLogger(ReportFormListAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// log.debug("Enter");

		PubUtil pubUtil = new PubUtil();

		try {
			// �鿴�Ƿ������Ȩ��
			String mybankid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
			String operid = pubUtil.dealNull(pubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
			int myPower = new OperatorModel().getPower(10, request);

			// û��Ȩ�ޱ���
			if (myPower == 0) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG, "pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}

			// ҳ��ҳ��
			String page = request.getParameter("page");
			String query = request.getParameter("query");
			// ��������
			String currentflag = ((ReportFormListForm) form).getCurrentflag();
			// log.debug("currentflag=\"" + currentflag + "\"");

			// ���ѡ����ɾ�����ܣ���ִ��ɾ������
			String del = request.getParameter("del");
			if (del != null && del.equals("true")) {
				String[] arryDel = ((ReportFormListForm) form).getArryDel();

				if (myPower == 2 || myPower == 3) {
					String path = getServlet().getServletContext().getRealPath("/statresult");

					for (int i = 0; i < arryDel.length; i++) {
						if (StaMissionModel.delStaMission(arryDel[i])) {
							new File(path + "/" + arryDel[i]).delete();
						}
					}
				}
				// ��¼����Ա������ˮ
				OplogModel myOplogModel = new OplogModel();
				String id = "";
				// ��ɾ���ı��д��һ���ַ����У�д����־��
				for (int i = 0; i < arryDel.length; i++) {
					id += arryDel[i] + ", ";
				}
				id = id.substring(0, id.length() - 2);

				OplogModel.insertOplog(operid, "1", "10", "pvxp.oplog.stamission.delete|" + id);
				query = "true";
			}

			Vector subBanks = new BankinfoModel().getSubBank(mybankid);
			Vector bankIDs = new Vector();
			String statesort = request.getParameter("statesort");
			request.setAttribute("statesort", statesort);

			if (page == null || query.equals("true")) { // ��ѯ

				// ��ȡ��ǰ�������ӻ���ID�б�
				if (myPower == 3) {
					bankIDs = null;
				} else {
					bankIDs.add(mybankid);
					if (subBanks != null) {
						Iterator iter = subBanks.iterator();
						while (iter.hasNext()) {
							bankIDs.add(((Bankinfo) iter.next()).getBankid().trim());
						}
					}
				}

				// ���ڷ����ض�״̬���͵��б�
				int totalRow;
				if (currentflag == null || currentflag.equals("-1")) {
					totalRow = StaMissionModel.getAllStaMissionCount(bankIDs, statesort);
				} else {
					totalRow = StaMissionModel.getStaMissionCount(bankIDs, currentflag, statesort);
				}

				PageBean pb = new PageBean(totalRow, 10);
				List result = getResult(bankIDs, pb, currentflag, statesort);

				// log.debug("result{" + result.size() + "}=" + result);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
			} else { // ��ҳ
				// ��ȡ��ǰ�������ӻ���ID�б�
				if (myPower == 3) {
					bankIDs = null;
				} else {
					bankIDs.add(mybankid);
					if (subBanks != null) {
						Iterator iter = subBanks.iterator();
						while (iter.hasNext()) {
							bankIDs.add(((Bankinfo) iter.next()).getBankid().trim());
						}
					}
				}

				String totalRow = request.getParameter("totalRow");

				PageBean pb = new PageBean(Integer.parseInt(totalRow), 10);
				int currentPage;
				try {
					currentPage = Integer.parseInt(page);
				} catch (Exception e) {
					e.printStackTrace();
					currentPage = 1;
				}
				pb.setCurrentPage(currentPage);
				List result = getResult(bankIDs, pb, currentflag, statesort);

				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.debug("e=" + e);
			return mapping.findForward("SystemError");
		}

		// log.debug("Exit\n");
		return mapping.findForward("ReportFormList");
	}

	private List getResult(Vector bankIDs, PageBean pb, String currentflag, String statesort) {

		try {
			List result = null;
			if (currentflag == null || currentflag.equals("-1")) {
				result = StaMissionModel.getAllStaMission(bankIDs, statesort, pb.beginRow, pb.getPageSize());
			} else {
				result = StaMissionModel.getStaMission(bankIDs, currentflag, statesort, pb.beginRow, pb.getPageSize());
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			// log.debug("in getResult e=" + e);
			return null;
		}
	}
}
