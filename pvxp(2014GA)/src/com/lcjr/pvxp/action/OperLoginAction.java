package com.lcjr.pvxp.action;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.actionform.OperLoginForm;
import com.lcjr.pvxp.model.OperatorModel;
import com.lcjr.pvxp.orm.Operator;

import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 操作员登录处理Action</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.1 2004/12/15
 */

/**
 * <p>
 * <b>Description:</b>屏蔽掉登录时对操作员状态的限制
 * </p>
 * <br>
 * 
 * @author xucc
 * @version 1.1 2007/09/20
 */
public final class OperLoginAction extends Action {
	
	Logger log = Logger.getLogger("web.com.lcjr.pvxp.action.OperLoginAction.java");
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// MessageResources messages = getResources(request);
		ActionMessages errors = new ActionMessages();// 错误信息
		// 强制转换成OperLoginForm类型的form类
		String operid = (String) ((OperLoginForm) form).getOperid();
		String operpwd = (String) ((OperLoginForm) form).getOperpwd();
		// 获得从文件
		PubUtil myPubUtil = new PubUtil();
		// CharSet myCharSet = new CharSet();
		ActionForward myforward = mapping.findForward("LoginSuccess");
		
		OperatorModel myOpModel = new OperatorModel();
		String tmpoperid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID, request)).trim();
		if (!tmpoperid.equals("")) { // 如果字符串不为空，就寻找input所指引的方向，返回登录界面，用于显示已经登录的用户编号
			myforward = new ActionForward(mapping.getInput());
			request.removeAttribute(mapping.getAttribute());
			return myforward;
		}
		
		
		// 如果字符串不为空，就进行登录验证
		try {
			Operator myOper = myOpModel.validateOper(operid, operpwd);// 用到operator表
			String nowdate = myPubUtil.getNowDate(1);// 获得当前日期，按“1”类型进行显示登录日期
			if (myOper == null) {// 如果登录不成功
				log.warn("登录编号：" + operid + "--登录失败");
				errors.add("system", new ActionMessage("pvxp.errors.database.no.suchop"));
				myforward = new ActionForward(mapping.getInput());
			} else {// 登录成功
				log.info("登录编号：" + operid + "--登录成功");
				/*
				 * if((myOper.getState()).equals("1")){ //操作员状态为不可用
				 * errors.add("system", new
				 * ActionMessage("pvxp.state.op.disable")); myforward = new
				 * ActionForward( mapping.getInput() ); }else
				 */

				if ((!((myPubUtil.dealNull(myOper.getAuthlist())).trim().equals("*"))) && Integer.parseInt(myPubUtil.dealNull(myOper.getAdddate()).trim()) <= Integer.parseInt(nowdate)) // 验证密码是否过期
				{
					log.warn("登录编号：" + operid + "--密码过期");
					errors.add("system", new ActionMessage("pvxp.state.op.passwd.outdate"));
					myforward = new ActionForward(mapping.getInput());
				} else {
					
					
					// Cookie名字和值都不能包含空白字符以及下列字符：[ ] ( ) = , " / ? @ : ;
					// 所以添加操作员的时候要进行过滤
					
					// 添加cookie
					Cookie opCookie = null;
					// 操作员编号
					opCookie = new Cookie(Constants.COOKIE_OPER_OPERID, myOper.getOperid().trim());
					opCookie.setMaxAge(-1);
					opCookie.setPath("/");
					response.addCookie(opCookie);
					
					
					// 操作员名称
					/*
					 * Cookie不能存汉字 System.out.println("...Start save opname");
					 * opCookie = new Cookie(Constants.COOKIE_OPER_OPERNAME,
					 * myCharSet.db2web(myOper.getName()).trim());
					 * opCookie.setMaxAge(-1); opCookie.setPath("/");
					 * response.addCookie(opCookie); System.out.println("...End
					 * save opname");
					 */

					String myAuthlist = myOper.getAuthlist().trim();
					String mybankid = "";
					
					
					// 获得登录用户所在的银行编号
					if (!myAuthlist.equals("*")) {
						mybankid = myOper.getBankid().trim();
						// BankinfoModel myBankinfoModel = new BankinfoModel();
						if (BankinfoModel.getBankinfoFromList(mybankid) == null) {
							log.warn("登录编号：" + operid + "--所属机构未知");
							errors.add("system", new ActionMessage("pvxp.state.op.bankid.unknown"));
							myforward = new ActionForward(mapping.getInput());
							saveErrors(request, errors);
							request.removeAttribute(mapping.getAttribute());
							return myforward;
						}
					} else {
						mybankid = PubUtil.strFormat("0", Constants.INT_BANKID_LEN, 0, '0');
					}
					
					
					// 操作员权限编码
					opCookie = new Cookie(Constants.COOKIE_OPER_AUTHLIST, myAuthlist);
					opCookie.setMaxAge(-1);
					opCookie.setPath("/");
					response.addCookie(opCookie);
					
					
					// 操作员所属机构编号
					opCookie = new Cookie(Constants.COOKIE_OPER_BANKID, mybankid);
					opCookie.setMaxAge(-1);
					opCookie.setPath("/");
					response.addCookie(opCookie);
					
					
					// 操作员类型
					opCookie = new Cookie(Constants.COOKIE_OPER_OPERTYPE, myOper.getOpertype().trim());
					opCookie.setMaxAge(-1);
					opCookie.setPath("/");
					response.addCookie(opCookie);
					// 操作员登录次数
					String strloginnum = myPubUtil.dealNull(myOper.getLoginnum()).trim();
					int loginnum = 0;
					try {
						loginnum = Integer.parseInt(strloginnum) + 1;
						strloginnum = String.valueOf(loginnum);
					} catch (Exception e) {
						loginnum = 1;
						strloginnum = "1";
					}
					opCookie = new Cookie(Constants.COOKIE_OPER_OPERLOGINNUM, strloginnum);
					opCookie.setMaxAge(-1);
					opCookie.setPath("/");
					response.addCookie(opCookie);
					// 最后登录时间
					String lastlogin = myPubUtil.dealNull(myOper.getLastlogin()).trim();
					opCookie = new Cookie(Constants.COOKIE_OPER_OPERLASTLOGIN, lastlogin);
					opCookie.setMaxAge(-1);
					opCookie.setPath("/");
					response.addCookie(opCookie);
					
					
					// 更新操作员信息
					myOper.setLoginnum(strloginnum);
					myOper.setState("1");
					lastlogin = myPubUtil.getNowDate(3) + " " + myPubUtil.getNowTime();
					myOper.setLastlogin(lastlogin);
					myOper.setName(myPubUtil.dealNull(myOper.getName()).trim());
					OperatorModel.updateOper(myOper, myOper.getOperid());
				}
			}
		} catch (Exception mex) {
			log.warn("WARN", mex);
			errors.add("system", new ActionMessage("pvxp.errors.system.syserror"));
			myforward = new ActionForward(mapping.getInput());
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		
		request.removeAttribute(mapping.getAttribute());
		return myforward;
	}
}