package com.lcjr.pvxp.actionform;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.lcjr.pvxp.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> �޸��豸����ActionForm</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author ��ѧ��
 * @version 1.0 2005/03/12
 */
 
/**@author xucc
 * @Description:�޸ĳ�������Ϊ��ţ�ȥ����ϵ��ʽ
 * @version 1.1 2010/09/28
 */
public final class ModifyDevTypeForm extends ActionForm {

	PubUtil pubUtil = new PubUtil();
	CharSet charSet = new CharSet();

	/*ModifyDevTypeAction����modify��ֵ�жϱ��Ǵ����ύ�ġ�
	 *modify == false ��������DevTypeDetail.jsp�ύ��
	 *modify == true  ��������ModifyDevType.jsp�ύ��
	 */
	private String modify;

	private String devTypeNo;
	private String devTypeState;
	private String devType;
	private String devName;
	private String devEquipt;
	private String dataPackageType;
	private String devftno;
	
	//get method
	public String getModify() {
		return this.modify;
	}

	public String getDevTypeNo() {
		return this.devTypeNo;
	}
	
	public String getDevTypeState() {
		return this.devTypeState;
	}
	
	public String getDevType() {
		return this.devType;
	}
	
	public String getDevName() {
		return this.devName;
	}
	
	public String getDevEquipt() {
		return this.devEquipt;
	}

	public String getDataPackageType() {
		return this.dataPackageType;
	}
	
	public String getDevftno() {
		return this.devftno;
	}
	
	
	//set method
	public void setModify(String modify) {
		this.modify = modify;
	}
	
	public void setDevTypeNo(String devTypeNo) {
		this.devTypeNo = devTypeNo;
	}

	public void setDevTypeState(String devTypeState) {
		this.devTypeState = devTypeState;
	}
	
	public void setDevType(String devType) {
		this.devType = devType;
	}
	
	public void setDevName(String devName) {
		this.devName = devName;
	}
	
	public void setDevEquipt(String devEquipt) {
		this.devEquipt = devEquipt;
	}
	
	public void setDataPackageType(String dataPackageType) {
		this.dataPackageType = dataPackageType;
	}
	
	public void setDevftno(String devftno) {
		this.devftno = devftno;
	}
	
}
