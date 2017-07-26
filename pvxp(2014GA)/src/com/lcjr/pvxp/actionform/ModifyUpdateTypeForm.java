package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.lcjr.pvxp.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> �޸ĸ�������ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2009</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2009/12/07
 */
public final class ModifyUpdateTypeForm extends ActionForm {

	PubUtil pubUtil = new PubUtil();
	CharSet charSet = new CharSet();

	/*ModifyUpdateTypeAction����modify��ֵ�жϱ��Ǵ����ύ�ġ�
	 *modify == false ��������UpdateTypeDetail.jsp�ύ��
	 *modify == true  ��������ModifyUpdateType.jsp�ύ��
	 */
	private String modify;

	private String updateno;
	private String updatename;
	private String info;
	
	//get method
	public String getModify() {
		return this.modify;
	}
	
	public String getUpdateno() {
		return this.updateno;
	}
	
	public String getUpdatename() {
		return this.updatename;
	}
	
	public String getInfo() {
		return this.info;
	}
	
	//set method
	public void setModify(String modify) {
		this.modify = modify;
	}
	
	public void setUpdateno(String updateno) {
		this.updateno = updateno;
	}
	
	public void setUpdatename(String updatename) {
		this.updatename = updatename;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
}
