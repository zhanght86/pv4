package com.lcjr.pvxp.orm;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <p>
 * <b>Description:</b> ��hibernate��ӳ��zzt_stamission�����
 * </p>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2012
 * </p>
 * <p>
 * <b>Company:</b> �˳�������ҵ��(LCJR)
 * </p>
 * 
 * @author ������
 * @version 1.0 2012/06/01����ͯ�ڣ�
 */
public class StaMission implements Serializable {
	/**
	 * ͳ�ƿ�ʼʱ��
	 */
	private String timeid;
	
	
	
	/**
	 * ��������
	 */
	private String statename;
	
	
	
	/**
	 * ��������
	 */
	private String bankid;
	
	
	
	/**
	 * ����Ա���
	 */
	private String ownerid;
	
	
	
	/**
	 * �������
	 */
	private String statesort;
	
	
	
	/**
	 * �����������
	 */
	private String createtype;
	
	
	
	/**
	 * ����ǰ״̬
	 */
	private String currentflag;
	
	
	
	/**
	 * ��ע1
	 */
	private String remark1;
	
	
	
	/**
	 * ��ע2
	 */
	private String remark2;
	
	
	
	/**
	 * ��ע3
	 */
	private String remark3;
	
	
	
	
	/**
	 * @return the bankid
	 */
	public String getBankid() {
		return bankid;
	}
	
	
	
	/**
	 * @param bankid
	 *            the bankid to set
	 */
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	
	
	
	/**
	 * @return the createtype
	 */
	public String getCreatetype() {
		return createtype;
	}
	
	
	
	/**
	 * @param createtype
	 *            the createtype to set
	 */
	public void setCreatetype(String createtype) {
		this.createtype = createtype;
	}
	
	
	
	/**
	 * @return the currentflag
	 */
	public String getCurrentflag() {
		return currentflag;
	}
	
	
	
	/**
	 * @param currentflag
	 *            the currentflag to set
	 */
	public void setCurrentflag(String currentflag) {
		this.currentflag = currentflag;
	}
	
	
	
	/**
	 * @return the ownerid
	 */
	public String getOwnerid() {
		return ownerid;
	}
	
	
	
	/**
	 * @param ownerid
	 *            the ownerid to set
	 */
	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}
	
	
	
	/**
	 * @return the remark1
	 */
	public String getRemark1() {
		return remark1;
	}
	
	
	
	/**
	 * @param remark1
	 *            the remark1 to set
	 */
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	
	
	/**
	 * @return the remark2
	 */
	public String getRemark2() {
		return remark2;
	}
	
	
	
	/**
	 * @param remark2
	 *            the remark2 to set
	 */
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	
	
	/**
	 * @return the remark3
	 */
	public String getRemark3() {
		return remark3;
	}
	
	
	
	/**
	 * @param remark3
	 *            the remark3 to set
	 */
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	
	
	
	/**
	 * @return the statename
	 */
	public String getStatename() {
		return statename;
	}
	
	
	
	/**
	 * @param statename
	 *            the statename to set
	 */
	public void setStatename(String statename) {
		this.statename = statename;
	}
	
	
	
	/**
	 * @return the statesort
	 */
	public String getStatesort() {
		return statesort;
	}
	
	
	
	/**
	 * @param statesort
	 *            the statesort to set
	 */
	public void setStatesort(String statesort) {
		this.statesort = statesort;
	}
	
	
	
	/**
	 * @return the timeid
	 */
	public String getTimeid() {
		return timeid;
	}
	
	
	
	/**
	 * @param timeid
	 *            the timeid to set
	 */
	public void setTimeid(String timeid) {
		this.timeid = timeid;
	}
	
	
	public boolean equals(Object other) {
		if (!(other instanceof StaMission))
			return false;
		StaMission castOther = (StaMission) other;
		return new EqualsBuilder().append(this.getTimeid(), castOther.getTimeid()).append(this.getOwnerid(), castOther.getOwnerid()).append(this.getStatesort(), castOther.getStatesort()).append(
				this.getCreatetype(), castOther.getCreatetype()).isEquals();
	}
	
	
	public int hashCode() {
		return new HashCodeBuilder().append(getTimeid()).append(getOwnerid()).append(getStatesort()).append(getCreatetype()).toHashCode();
	}
	
}
