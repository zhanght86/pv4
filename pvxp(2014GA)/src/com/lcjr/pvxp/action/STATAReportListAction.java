package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.actionform.*;
import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.bean.*;

import java.util.*;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 自动报表列表Action</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 刘太沛
 * @version 1.0 2005/04/12
 */
public final class STATAReportListAction extends Action {
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception {
	
		PubUtil myPubUtil = new PubUtil();
		try {
			String bankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID,request)).trim();
			String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID,request)).trim();
			if( Integer.parseInt(bankid) == 0 ) {
				bankid = "";
			}
			
			int myPower1 = new OperatorModel().getPower(10, request);
			int myPower2 = new OperatorModel().getPower(11, request);
			int myPower3 = new OperatorModel().getPower(12, request);
			
			//没有权限报错
			if((myPower1<2) || (myPower2<2) || (myPower3<2) ) {
				request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.op.no.power");
				request.removeAttribute(mapping.getAttribute());
				return mapping.findForward("SystemError");
			}
			
			String page = request.getParameter("page");
			String query = request.getParameter("query");
			String opentag = request.getParameter("opentag");
			if (opentag == null) {
				opentag = "1";
			}
			
			AutostaModel myAsModel = new AutostaModel();
			
			//删除记录
			String del = request.getParameter("del");
			if (del != null && del.equals("true")) {
				String[] arryDel = ((STATAReportListForm)form).getArryDel();
			
				if ( (myPower1>=2) && (myPower2>=2) && (myPower3>=2) ) {
					for (int i=0; i<arryDel.length; i++) {
						myAsModel.deleteAutosta(arryDel[i]);
					}
			
					//记录操作员操作流水
					OplogModel myOplogModel = new OplogModel();
					String id = "";
					for( int i=0; i<arryDel.length; i++ ) {
						id += arryDel[i] + ", ";
					}
					id = id.substring(0,id.length()-2);
					myOplogModel.insertOplog( operid, "1", "10", "pvxp.oplog.autosta.delete|"+id );
			
				}
				query = "true";
			}
			
			//获得自动报表列表
			List AsList = myAsModel.getAutostaList();
			Autosta myAs = new Autosta();
			
			//关闭任务
			String closeflag = request.getParameter("closeflag");
			if (closeflag != null && closeflag.equals("true")) {
				String[] arryDel = ((STATAReportListForm)form).getArryDel();
			
				if ( (myPower1>=2) && (myPower2>=2) && (myPower3>=2) ) {
					for (int i=0; i<arryDel.length; i++) {
						myAs = myAsModel.getAutostaFromList( arryDel[i], AsList );
						myAs.setOpentag( "0" );
						myAs.setStatename( myPubUtil.dealNull(myAs.getStatename()).trim() );
						myAs.setInfo( myPubUtil.dealNull(myAs.getInfo()).trim() );
						myAsModel.updateAutosta( myAs );
					}
			
					//记录操作员操作流水
					OplogModel myOplogModel = new OplogModel();
					String id = "";
					for( int i=0; i<arryDel.length; i++ ) {
						id += arryDel[i] + ", ";
					}
					id = id.substring(0,id.length()-2);
					myOplogModel.insertOplog( operid, "0", "10", "pvxp.oplog.autosta.modify|"+id );
			
				}
				query = "true";
			}
			
			//开启任务
			String openflag = request.getParameter("openflag");
			if ( openflag != null && openflag.equals("true") ) {
				String[] arryDel = ((STATAReportListForm)form).getArryDel();
			
				if ( (myPower1>=2) && (myPower2>=2) && (myPower3>=2) ) {
					for (int i=0; i<arryDel.length; i++) {
						myAs = myAsModel.getAutostaFromList( arryDel[i], AsList );
						myAs.setOpentag( "1" );
						myAs.setStatename( myPubUtil.dealNull(myAs.getStatename()).trim() );
						myAs.setInfo( myPubUtil.dealNull(myAs.getInfo()).trim() );
			
						//处理下次开始统计日期
						String nowdate = myPubUtil.getNowDate(1);
						String tmpdate = "";
						String tmpyear = "";
						String nextstatday = "";
						String statetype = myPubUtil.dealNull(myAs.getStatetype()).trim();
						String afterday = myPubUtil.dealNull(myAs.getAfterday()).trim();
			
						if( statetype.equals("1") ) {//日报
							nextstatday = myPubUtil.getOtherDate(1);
			
						}else if( statetype.equals("2") ) {//月报
							tmpdate = nowdate.substring(0,6);
							tmpdate = Integer.toString(Integer.parseInt(tmpdate) + 1);
							if( myPubUtil.isYYYYMM( tmpdate ) ) {
								if( afterday.length() == 1 ) {
									afterday = "0" + afterday;
								}
								nextstatday = tmpdate + afterday;
							}else {
								tmpdate = Integer.toString(Integer.parseInt(tmpdate.substring(0,4)) + 1);
								nextstatday = tmpdate + "01";
								if( afterday.length() == 1 ) {
									afterday = "0" + afterday;
								}
								nextstatday += afterday;
							}
			
						}else if( statetype.equals("3") ) {//年报
							tmpdate = nowdate.substring(0,4);
							tmpdate = Integer.toString(Integer.parseInt(tmpdate) + 1);
							nextstatday = tmpdate + "01";
							if( afterday.length() == 1 ) {
								afterday = "0" + afterday;
							}
							nextstatday += afterday;
			
						}else if( statetype.equals("4") ) {//季报
							tmpdate = nowdate.substring(4,6);
							tmpyear = Integer.toString(Integer.parseInt(nowdate.substring(0,4)) + 1);
							if( afterday.length() == 1 ) {
								afterday = "0" + afterday;
							}
							int i_month = Integer.parseInt(tmpdate);
							if( i_month < 3 ) {
								nextstatday = nowdate.substring(0,4) + "04" + afterday;
							}else if( (i_month > 3) && (i_month <= 6) ) {
								nextstatday = nowdate.substring(0,4) + "07" + afterday;
							}else if( (i_month > 6) && (i_month <= 9) ) {
								nextstatday = nowdate.substring(0,4) + "10" + afterday;
							}else if( (i_month > 9) && (i_month <= 12) ) {
								nextstatday = tmpyear + "01" + afterday;
							}
			
						}else if( statetype.equals("5") ) {//半年报
							tmpdate = nowdate.substring(4,6);
							tmpyear = Integer.toString(Integer.parseInt(nowdate.substring(0,4)) + 1);
							if( afterday.length() == 1 ) {
								afterday = "0" + afterday;
							}
							int i_month = Integer.parseInt(tmpdate);
							if( i_month < 7 ) {
								nextstatday = nowdate.substring(0,4) + "07" + afterday;
							}else if( (i_month > 6) && (i_month <= 12) ) {
								nextstatday = tmpyear + "01" + afterday;
							}
			
						}
			
						myAs.setNextstatday( nextstatday );
						//更新自动任务报表
						myAsModel.updateAutosta( myAs );
					}
			
					//记录操作员操作流水
					OplogModel myOplogModel = new OplogModel();
					String id = "";
					for( int i=0; i<arryDel.length; i++ ) {
						id += arryDel[i] + ", ";
					}
					id = id.substring(0,id.length()-2);
					myOplogModel.insertOplog( operid, "0", "10", "pvxp.oplog.autosta.modify|"+id );
			
				}
				query = "true";
			}
			
			if (page == null || query.equals("true")) { //查询
			
				int totalRow;
				if ( opentag == null ) {
					totalRow = myAsModel.getAllCount(bankid);
				} else {
					totalRow = myAsModel.getCount(bankid, opentag);
				}
			
				PageBean pb = new PageBean(totalRow, 10);
				List result = getResult(bankid, pb, opentag);
			
				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
			} else { //翻页
			
				String totalRow = request.getParameter("totalRow");
			
				PageBean pb = new PageBean(Integer.parseInt(totalRow), 10);
				int currentPage;
				try {
					currentPage = Integer.parseInt(page);
				} catch (Exception e) {
					currentPage = 1;
				}
				pb.setCurrentPage(currentPage);
				List result = getResult(bankid, pb, opentag);
			
				request.setAttribute("PageBean", pb);
				request.setAttribute("Result", result);
			}
		} catch(Exception e) {
			return mapping.findForward("SystemError");
		}
		
		return mapping.findForward("STATAReportList");
	}
	
	private List getResult( String bankid, PageBean pb, String opentag ){
	
		try {
			List result = null;
			AutostaModel myAsModel = new AutostaModel();
			if (opentag == null ) {
				result = myAsModel.getListByBankid(bankid, pb.beginRow, pb.getPageSize());
			} else {
				result = myAsModel.getListByBankid_Opentag(bankid, opentag, pb.beginRow, pb.getPageSize());
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}


