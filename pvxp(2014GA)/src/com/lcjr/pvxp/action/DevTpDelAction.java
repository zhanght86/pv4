package com.lcjr.pvxp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.lcjr.pvxp.actionform.DevTpDelForm;
import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.model.DevtypeModel;
import com.lcjr.pvxp.model.OplogModel;
import com.lcjr.pvxp.util.Constants;
import com.lcjr.pvxp.util.PubUtil;


/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> 设备类型删除Action</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.0 2004/12/31
 */
 
/**@author xucc
 * @Description:修改操作记录编号
 * @version 1.1 2010/09/28
 */
public final class DevTpDelAction extends Action {


	Logger log = Logger.getLogger(DevTpDelAction.class.getName());

  public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {

    MessageResources messages = getResources(request);
    ActionMessages errors = new ActionMessages();
    String[] devtpArry = (String[])((DevTpDelForm) form).getDevtpArry();

    PubUtil myPubUtil = new PubUtil();
    String operid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_OPERID,request));
    ActionForward myforward = mapping.findForward("SystemError");
    try {
      String authlist = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST,request)).trim();
      if( authlist.equals("*") ) {
        //超级管理员
      } else {
        //没有权限
        request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.op.no.power");
        request.removeAttribute(mapping.getAttribute());
        return myforward;
      }

      if(devtpArry==null||devtpArry.length==0) {
        request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.devtype.no.selected");
        request.removeAttribute(mapping.getAttribute());
        return myforward;
      }

      String[] result = null;

      try {
        DevtypeModel myDevTpModel = new DevtypeModel();
        int num = devtpArry.length;
        result = new String[num];

        DevinfoModel	devinfoModel	= new DevinfoModel();

        boolean success;
        for(int i=0;i<num;i++) {
          result[i] = String.valueOf(myDevTpModel.deleteDevTp(devtpArry[i]));
          switch (Integer.parseInt(result[i])) {
          case -1:
          	OplogModel.insertOplog(operid, "1", "20", "pvxp.oplog.devtype.delete.fail|" + devtpArry[i]);
            break;
          case 0:
          	OplogModel.insertOplog(operid, "1", "20", "pvxp.oplog.devtype.delete.success|" + devtpArry[i]);
            break;
          case 1:
          	OplogModel.insertOplog(operid, "1", "20", "pvxp.oplog.devtype.delete.needrebuild|" + devtpArry[i]);
            break;
          }

          success = DevinfoModel.deleteDevByType(devtpArry[i]);
          /*if (success) {
            OplogModel.insertOplog(operid, "1", "0", "pvxp.oplog.devinfo.deletebytype.sucess|" + devtpArry[i]);
          } else {
            OplogModel.insertOplog(operid, "1", "0", "pvxp.oplog.devinfo.deletebytype.fail|" + devtpArry[i]);
          }*/
        }
      } catch(Exception e) {
        request.setAttribute(Constants.REQUEST_SYSTEM_ERRMSG,"pvxp.errors.system.syserror");
        request.removeAttribute(mapping.getAttribute());
        return myforward;
      }

      request.setAttribute(Constants.REQUEST_DEVTP_DEL_DONE,result);
      myforward = mapping.findForward("DevTpDelDone");

    } catch(Exception e) {
      myforward = mapping.findForward("SystemError");
      errors.add("errormsg", new ActionMessage("pvxp.errors.system.syserror"));
    }
    request.removeAttribute(mapping.getAttribute());
		return myforward;
	}

}
